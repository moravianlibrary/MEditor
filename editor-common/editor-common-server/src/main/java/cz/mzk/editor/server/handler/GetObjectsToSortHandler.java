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

package cz.mzk.editor.server.handler;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import cz.mzk.editor.server.fedora.FedoraAccess;
import cz.mzk.editor.server.fedora.utils.DCUtils;
import cz.mzk.editor.server.fedora.utils.FedoraUtils;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.domain.FedoraRelationship;
import cz.mzk.editor.shared.domain.NamedGraphModel;
import cz.mzk.editor.shared.rpc.DigitalObjectRelationships;
import cz.mzk.editor.shared.rpc.action.GetObjectsToSortAction;
import cz.mzk.editor.shared.rpc.action.GetObjectsToSortResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class GetObjectsToSortHandler.
 * 
 * @author Matous Jobanek
 * @version Jan 30, 2013
 */
@Service
public class GetObjectsToSortHandler
        implements ActionHandler<GetObjectsToSortAction, GetObjectsToSortResult> {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(GetRelationshipsHandler.class.getPackage()
            .toString());

    /** The fedora access. */
    private final FedoraAccess fedoraAccess;

    /**
     * Instantiates a new gets the objects to sort handler.
     * 
     * @param fedoraAccess
     *        the fedora access
     */
    @Inject
    public GetObjectsToSortHandler(@Qualifier("securedFedoraAccess") FedoraAccess fedoraAccess) {
        this.fedoraAccess = fedoraAccess;
    }

    /**
     * Execute.
     * 
     * @param action
     *        the action
     * @param context
     *        the context
     * @return the gets the objects to sort result
     * @throws ActionException
     *         the action exception {@inheritDoc}
     */
    @Override
    public GetObjectsToSortResult execute(GetObjectsToSortAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: GetObjectsToSortResult " + action.getUuid());

        DigitalObjectRelationships digObjRel = new DigitalObjectRelationships(action.getUuid());
        getChildren(digObjRel);

        return new GetObjectsToSortResult(digObjRel);
    }

    /**
     * Gets the children.
     * 
     * @param digObjRel
     *        the dig obj rel
     * @return the children
     * @throws ActionException
     */
    private DigitalObjectRelationships getChildren(DigitalObjectRelationships digObjRel)
            throws ActionException {

        digObjRel.setTitle(getDCTitle(digObjRel.getUuid()));

        DigitalObjectModel parentModel;
        try {
            parentModel = FedoraUtils.getModel(digObjRel.getUuid());
            digObjRel.setModel(parentModel);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new ActionException(e);
        }

        for (DigitalObjectModel possibleChildModel : NamedGraphModel.getChildren(parentModel)) {

            List<String> childrenUuid;
            try {
                childrenUuid =
                        fedoraAccess.getChildrenUuid(digObjRel.getUuid(), parentModel, possibleChildModel);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
                throw new ActionException(e);
            }

            FedoraRelationship relationship =
                    NamedGraphModel.getRelationship(parentModel, possibleChildModel);

            if (childrenUuid != null) {
                for (String child : childrenUuid) {

                    if (FedoraRelationship.hasPage != relationship
                            && FedoraRelationship.isOnPage != relationship) {

                        DigitalObjectRelationships newDigObjRel =
                                getChildren(new DigitalObjectRelationships(child));

                        if (digObjRel.getChildren().containsKey(relationship.getStringRepresentation())) {
                            digObjRel.getChildren().get(relationship.getStringRepresentation())
                                    .add(newDigObjRel);
                        } else {
                            List<DigitalObjectRelationships> digObjList =
                                    new ArrayList<DigitalObjectRelationships>();
                            digObjList.add(newDigObjRel);
                            digObjRel.getChildren().put(relationship.getStringRepresentation(), digObjList);
                        }
                    }
                }
            }

        }
        return digObjRel;
    }

    /**
     * Gets the dC title.
     * 
     * @param uuid
     *        the uuid
     * @return the dC title
     */
    private String getDCTitle(String uuid) {
        Document dcDocument = null;
        try {
            dcDocument = fedoraAccess.getDC(uuid);
        } catch (IOException e) {
            LOGGER.error("Unable to get DC metadata for " + uuid + "[" + e.getMessage() + "]", e);
        }
        if (dcDocument != null) {
            return DCUtils.titleFromDC(dcDocument.getDocumentElement());
        }
        return "";
    }

    /**
     * Gets the action type.
     * 
     * @return the action type {@inheritDoc}
     */
    @Override
    public Class<GetObjectsToSortAction> getActionType() {
        return GetObjectsToSortAction.class;
    }

    /**
     * Undo.
     * 
     * @param action
     *        the action
     * @param result
     *        the result
     * @param context
     *        the context
     * @throws ActionException
     *         the action exception {@inheritDoc}
     */
    @Override
    public void undo(GetObjectsToSortAction action, GetObjectsToSortResult result, ExecutionContext context)
            throws ActionException {
        throw new ActionException("Undo is not supported on " + this.getClass().getSimpleName());

    }

}
