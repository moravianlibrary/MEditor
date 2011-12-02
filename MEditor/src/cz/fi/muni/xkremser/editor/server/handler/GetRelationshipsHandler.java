/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.fi.muni.xkremser.editor.server.handler;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;

import cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces;
import cz.fi.muni.xkremser.editor.shared.rpc.DigitalObjectRelationships;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRelationshipsAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRelationshipsResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class GetRelationshipsHandler
        implements ActionHandler<GetRelationshipsAction, GetRelationshipsResult> {

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    private List<String> uuidList;
    private List<String> sharedPages;
    private List<String> uuidNotToRemove;
    private int test;

    /**
     * {@inheritDoc}
     */

    @Override
    public GetRelationshipsResult execute(GetRelationshipsAction action, ExecutionContext context)
            throws ActionException {
        test = 0;
        uuidList = new ArrayList<String>();
        sharedPages = new ArrayList<String>();
        uuidNotToRemove = new ArrayList<String>();
        HttpSession ses = httpSessionProvider.get();
        ServerUtils.checkExpiredSession(ses);

        DigitalObjectRelationships digObjRel = new DigitalObjectRelationships(action.getUuid());
        getChildren(digObjRel, null);
        getParents(digObjRel);
        detectChildrenConflicts(digObjRel, true);
        return new GetRelationshipsResult(digObjRel, sharedPages, uuidNotToRemove);
    }

    /**
     * @param uuidToOmit
     * @param uuid
     * @throws IOException
     */

    private DigitalObjectRelationships getChildren(DigitalObjectRelationships digObjRel, String uuidToOmit) {

        uuidList.add(digObjRel.getUuid());
        //        System.err.println(digObjRel.getUuid());

        ArrayList<ArrayList<String>> children = FedoraUtils.getAllChildren(digObjRel.getUuid());
        for (ArrayList<String> child : children) {
            if (uuidToOmit == null || !uuidToOmit.equals(child.get(0))) {
                if (uuidList.contains(child.get(0))) {
                    sharedPages.add(child.get(0));
                }

                String predicate = getOnlyPredicate(child.get(1));
                DigitalObjectRelationships newDigObjRel =
                        getChildren(new DigitalObjectRelationships(child.get(0)), uuidToOmit);

                if (digObjRel.getChildren().containsKey(predicate)) {
                    digObjRel.getChildren().get(predicate).add(newDigObjRel);
                } else {
                    List<DigitalObjectRelationships> digObjList = new ArrayList<DigitalObjectRelationships>();
                    digObjList.add(newDigObjRel);
                    digObjRel.getChildren().put(predicate, digObjList);
                }
            }
        }
        return digObjRel;
    }

    private void getParents(final DigitalObjectRelationships digObjRel) {
        Map<String, List<DigitalObjectRelationships>> parentsMap =
                new HashMap<String, List<DigitalObjectRelationships>>();
        List<String> parentsUuid = new ArrayList<String>();

        ArrayList<ArrayList<String>> related = FedoraUtils.getRelated(digObjRel.getUuid());

        for (ArrayList<String> subject : related) {
            parentsUuid.add(subject.get(0));
            if (parentsMap.containsKey(subject.get(1))) {
                parentsMap.get(subject.get(1)).add(new DigitalObjectRelationships(subject.get(0)));
            } else {
                List<DigitalObjectRelationships> newDigObjRelList =
                        new ArrayList<DigitalObjectRelationships>();
                newDigObjRelList.add(new DigitalObjectRelationships(subject.get(0)));
                parentsMap.put(subject.get(1), newDigObjRelList);
            }
        }
        digObjRel.setParents(parentsMap);
        detectParentsConflicts(digObjRel, parentsUuid);

    }

    /**
      * 
      */

    private void detectParentsConflicts(final DigitalObjectRelationships digObjRel, List<String> parentsUuid) {
        for (String parentUuid : parentsUuid) {
            ArrayList<ArrayList<String>> grandParents = FedoraUtils.getRelated(parentUuid);
            for (ArrayList<String> grandParent : grandParents) {

                if (parentsUuid.contains(grandParent.get(0))) {
                    digObjRel.setConflict(Constants.CONFLICT.INHERITED);

                    removeDigObjRelFromMap(digObjRel.getParents(), grandParent.get(0));

                    final DigitalObjectRelationships parentConflict =
                            removeDigObjRelFromMap(digObjRel.getParents(), parentUuid);

                    if (grandParents.size() > 1) {
                        parentConflict.setConflict(Constants.CONFLICT.UNCLE_COUSINE);

                    } else {
                        ArrayList<ArrayList<String>> cousins = FedoraUtils.getAllChildren(parentUuid);
                        if (cousins.size() > 1) {
                            parentConflict.setConflict(Constants.CONFLICT.COUSIN);
                        } else {
                            parentConflict.setConflict(Constants.CONFLICT.SAME_PARENT_GRANDPARENT);
                        }
                    }
                    getChildren(parentConflict, digObjRel.getUuid());

                    if (digObjRel.getParents().containsKey(grandParent.get(0))) {
                        digObjRel.getParents().get(grandParent.get(0)).add(parentConflict);
                    } else {
                        List<DigitalObjectRelationships> newDigObjRelList =
                                new ArrayList<DigitalObjectRelationships>();
                        newDigObjRelList.add(parentConflict);
                        digObjRel.getParents().put(grandParent.get(0), newDigObjRelList);
                    }
                }
            }
        }
    }

    private DigitalObjectRelationships removeDigObjRelFromMap(final Map<String, List<DigitalObjectRelationships>> map,
                                                              final String uuid) {
        for (String rel : map.keySet()) {
            if (map.get(rel).contains(new DigitalObjectRelationships(uuid))) {
                return map.get(rel).remove(map.get(rel).indexOf(new DigitalObjectRelationships(uuid)));
            }
        }
        return null;
    }

    private boolean detectChildrenConflicts(final DigitalObjectRelationships digObjRel, boolean isRoot) {

        boolean childHasConflict = false;
        final Map<String, List<DigitalObjectRelationships>> childrenMap = digObjRel.getChildren();
        for (String relName : childrenMap.keySet()) {
            for (DigitalObjectRelationships child : childrenMap.get(relName)) {
                if (!childHasConflict) {
                    childHasConflict = detectChildrenConflicts(child, false);
                } else {
                    detectChildrenConflicts(child, false);
                }
            }
        }
        if (!childHasConflict) {
            if (!isRoot) {
                ArrayList<ArrayList<String>> related = FedoraUtils.getRelated(digObjRel.getUuid());
                test++;
                for (ArrayList<String> subject : related) {
                    if (!uuidList.contains(subject.get(0))) {
                        uuidNotToRemove.add(digObjRel.getUuid());
                        digObjRel.setConflict(Constants.CONFLICT.CHILD_EXTERNAL_REFERENCE);
                    }
                }
            }
        } else {
            digObjRel.setConflict(Constants.CONFLICT.INHERITED);
        }
        return digObjRel.getConflict().getConflictCode() > 0;
    }

    private String getOnlyPredicate(String toParse) {
        String predicate = null;
        if (toParse.startsWith(Constants.FEDORA_INFO_PREFIX)) {
            predicate = toParse.substring((Constants.FEDORA_INFO_PREFIX).length());
        } else {
            predicate = toParse.substring(FedoraNamespaces.ONTOLOGY_RELATIONSHIP_NAMESPACE_URI.length());
        }
        return predicate;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Class<GetRelationshipsAction> getActionType() {
        return GetRelationshipsAction.class;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void undo(GetRelationshipsAction arg0, GetRelationshipsResult arg1, ExecutionContext arg2)
            throws ActionException {
        // TODO Auto-generated method stub
    }
}
