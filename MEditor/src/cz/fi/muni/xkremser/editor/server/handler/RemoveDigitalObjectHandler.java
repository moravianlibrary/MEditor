/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
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

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.DAO.RecentlyModifiedItemDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;
import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FoxmlUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.RESTHelper;
import cz.fi.muni.xkremser.editor.server.modelHandler.DigitalObjectHandler;
import cz.fi.muni.xkremser.editor.server.newObject.CreateObjectUtils;

import cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces;
import cz.fi.muni.xkremser.editor.shared.rpc.Foxml;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveDigitalObjectAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveDigitalObjectResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class RemoveDigitalObjectHandler
        implements ActionHandler<RemoveDigitalObjectAction, RemoveDigitalObjectResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(RemoveDigitalObjectHandler.class.getPackage()
            .toString());

    /** The http session provider. */
    private final Provider<HttpSession> httpSessionProvider;

    /** The configuration. */
    private final EditorConfiguration configuration;

    /** The get do model handler */
    private final GetDOModelHandler getDoModelHandler;

    /** The fedora access. */
    private final FedoraAccess fedoraAccess;

    /** The list of digital objects which have been removed */
    private final List<RemovedDigitalObject> removedDigitalObjects = new ArrayList<RemovedDigitalObject>();

    /** The recently modified dao */
    @Inject
    private RecentlyModifiedItemDAO recModDao;

    private final int fail = 0;

    private static final class RemovedDigitalObject {

        private final String uuid;
        private final ArrayList<ArrayList<String>> removedRelationships;
        private final Foxml foxml;

        public RemovedDigitalObject(String uuid,
                                    Foxml foxml,
                                    ArrayList<ArrayList<String>> removedRelationships) {
            this.uuid = uuid;
            this.removedRelationships = removedRelationships;
            this.foxml = foxml;
        }

        /**
         * @return the uuid
         */

        public String getUuid() {
            return uuid;
        }

        /**
         * @return the removedRelationships
         */

        public ArrayList<ArrayList<String>> getRemovedRelationships() {
            return removedRelationships;
        }

        /**
         * @return the foxml
         */

        public Foxml getFoxml() {
            return foxml;
        }
    }

    /**
     * Instantiates a new gets the remove digital object handler.
     * 
     * @param configuration
     *        the configuration
     * @param httpSessionProvider
     *        the http session provider
     */
    @Inject
    public RemoveDigitalObjectHandler(final EditorConfiguration configuration,
                                      Provider<HttpSession> httpSessionProvider,
                                      final DigitalObjectHandler objectHandler,
                                      @Named("securedFedoraAccess") FedoraAccess fedoraAccess) {
        this.configuration = configuration;
        this.httpSessionProvider = httpSessionProvider;
        this.getDoModelHandler = new GetDOModelHandler(objectHandler);
        this.fedoraAccess = fedoraAccess;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public RemoveDigitalObjectResult execute(final RemoveDigitalObjectAction action,
                                             final ExecutionContext context) throws ActionException {

        ServerUtils.checkExpiredSession(httpSessionProvider);

        String uuid = action.getUuid();
        List<String> uuidNotToRemove = action.getUuidNotToRemove();

        String returnedMessage = remove(uuid, context, uuidNotToRemove);
        if (!"".equals(returnedMessage)) {
            return new RemoveDigitalObjectResult(returnedMessage, null);
        }
        List<String> removedUuid = new ArrayList<String>();

        for (RemovedDigitalObject removedObj : removedDigitalObjects) {
            removedUuid.add(removedObj.getUuid());
            try {
                recModDao.deleteRemovedItem(removedObj.getUuid());
            } catch (DatabaseException e) {
                LOGGER.error("The digital object: " + removedObj.getUuid() + " could not be removed from "
                        + Constants.TABLE_RECENTLY_MODIFIED_NAME + " " + e);
            }
        }

        return new RemoveDigitalObjectResult(null, removedUuid);
    }

    private String remove(String uuid, final ExecutionContext context, List<String> uuidNotToRemove)
            throws ActionException {

        ArrayList<ArrayList<String>> children = FedoraUtils.getAllChildren(uuid);

        for (ArrayList<String> child : children) {
            if (!uuidNotToRemove.contains(child.get(0)) && getDoModelHandler.getModel(uuid) != null) {
                String returnedMessage = remove(child.get(0), context, uuidNotToRemove);
                if (!"".equals(returnedMessage)) {
                    return returnedMessage;
                }
            }
        }
        if (!uuidNotToRemove.contains(uuid) && getDoModelHandler.getModel(uuid) != null) {
            return removeDigObjAndRel(uuid, removedDigitalObjects, context);
        } else {
            LOGGER.debug("Digital object with uuid: " + uuid + " has not been found.");
            return "";
        }

    }

    private String removeDigObjAndRel(String uuid,
                                      final List<RemovedDigitalObject> removedDigitalObjects,
                                      final ExecutionContext context) throws ActionException {
        ArrayList<ArrayList<String>> parents = FedoraUtils.getRelated(uuid);

        StringBuffer message = new StringBuffer("");
        String url = null;
        String usr = configuration.getFedoraLogin();
        String pass = configuration.getFedoraPassword();

        ArrayList<ArrayList<String>> removedRelationships = new ArrayList<ArrayList<String>>();
        Foxml foxml = FoxmlUtils.handleFoxml(uuid, fedoraAccess);

        boolean successful = true;

        /** ----- For Fedora 3.4 and higher ---- */
        for (ArrayList<String> parentRel : parents) {
            try {
                url =
                        configuration.getFedoraHost()
                                + "/objects/"
                                + parentRel.get(0)
                                + "/relationships?subject="
                                + Constants.FEDORA_INFO_PREFIX
                                + parentRel.get(0)
                                + "&predicate="
                                + java.net.URLEncoder
                                        .encode(FedoraNamespaces.ONTOLOGY_RELATIONSHIP_NAMESPACE_URI, "UTF-8")
                                + parentRel.get(1) + "&object=" + Constants.FEDORA_INFO_PREFIX + uuid
                                + "&isLiteral=false" + "&datatype=null";
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            int attempt = 0;
            successful = false;
            while (!successful && attempt++ < 3) {
                LOGGER.debug("Processing action: RemoveDigitalObjectAction the " + attempt
                        + ". attempt of removing relationship with subject: " + parentRel.get(0)
                        + " predicate: " + parentRel.get(1) + " and object: " + uuid);
                String result = RESTHelper.deleteWithStringResult(url, usr, pass, true);
                successful = result.contains("true");
            }

            if (successful) {
                removedRelationships.add(parentRel);
            } else {
                LOGGER.error(message
                        .append("Processing action RemoveDigitalObjectAction failed during removing relationship with subject: "
                                + parentRel.get(0)
                                + " predicate: "
                                + parentRel.get(1)
                                + " and object: "
                                + uuid).toString());
                message.append("<br>");
                break;
            }
        }
        /** --------------------------------------------------- */

        if (successful) {
            url = configuration.getFedoraHost() + "/objects/" + uuid;

            int attempt = 0;
            successful = false;
            //            fail++;
            while (!successful && attempt++ < 3 && fail < 2) {
                LOGGER.debug("Processing action: RemoveDigitalObjectAction the " + attempt
                        + ". attempt of removing digital object with uuid:" + uuid);
                if (RESTHelper.deleteWithBooleanResult(url, usr, pass, true))
                    successful = getDoModelHandler.getModel(uuid) == null;
            }

            if (successful) {
                removedDigitalObjects.add(new RemovedDigitalObject(uuid, foxml, removedRelationships));
                return "";

            } else {
                if (getDoModelHandler.getModel(uuid) == null) {//getDoModelHandler.execute(new GetDOModelAction(uuid), context).getModel() == null) {
                    removedDigitalObjects.add(new RemovedDigitalObject(uuid, foxml, removedRelationships));
                    return "";

                } else {
                    LOGGER.error(message
                            .append("Processing action RemoveDigitalObjectAction failed during removing digital object with uuid: "
                                    + uuid).toString());
                    message.append("<br>");
                    message.append(rollbackRemoval(removedRelationships, uuid, removedDigitalObjects));
                    return message.toString();

                }
            }
        } else {
            message.append(rollbackRemoval(removedRelationships, uuid, removedDigitalObjects));
            return message.toString();
        }
    }

    /**
     * @param removedRelationships
     */

    private String rollbackRemoval(ArrayList<ArrayList<String>> removedRelationships,
                                   String uuid,
                                   final List<RemovedDigitalObject> removedDigitalObjects) {

        StringBuffer message = new StringBuffer("");
        message.append(addRelationships(removedRelationships, uuid));

        for (RemovedDigitalObject removed : removedDigitalObjects) {
            int attempt = 0;
            boolean successful = false;
            while (!successful && attempt++ < 3) {
                successful =
                        CreateObjectUtils.ingest(removed.getFoxml().getNoCodedfoxml(), removed.getFoxml()
                                .getLabel(), removed.getUuid());
            }
            if (!successful) {

                LOGGER.error(message
                        .append("Processing rollback action: RemoveDigitalObjectAction failed during rollback removal digital object with uuid: "
                                + removed.getUuid()).toString());
                message.append("<br>");
            }
            message.append(addRelationships(removed.getRemovedRelationships(), removed.getUuid()));
        }
        return message.toString();
    }

    /**
     * @param removedRelationships
     * @return
     */

    private String addRelationships(ArrayList<ArrayList<String>> removedRelationships, String uuid) {

        StringBuffer message = new StringBuffer("");

        for (ArrayList<String> parentRel : removedRelationships) {
            String url = null;
            try {
                url =
                        configuration.getFedoraHost()
                                + "/objects/"
                                + parentRel.get(0)
                                + "/relationships/new?subject="
                                + Constants.FEDORA_INFO_PREFIX
                                + parentRel.get(0)
                                + "&predicate="
                                + java.net.URLEncoder
                                        .encode(FedoraNamespaces.ONTOLOGY_RELATIONSHIP_NAMESPACE_URI, "UTF-8")
                                + parentRel.get(1) + "&object=" + Constants.FEDORA_INFO_PREFIX + uuid
                                + "&isLiteral=false" + "&datatype=null";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String usr = configuration.getFedoraLogin();
            String pass = configuration.getFedoraPassword();

            int attempt = 0;
            boolean successful = false;
            while (!successful && attempt++ < 3) {
                LOGGER.debug("Processing rollback action: RemoveDigitalObjectAction the " + attempt
                        + ". attempt of rollback removal relationship with subject: " + parentRel.get(0)
                        + " predicate: " + parentRel.get(1) + " and object: " + uuid);
                successful = RESTHelper.post(url, null, usr, pass, true);
            }

            if (!successful) {
                LOGGER.error(message
                        .append("Processing rollback action RemoveDigitalObjectAction failed during rollback removal relationship with subject: "
                                + parentRel.get(0)
                                + " predicate: "
                                + parentRel.get(1)
                                + " and object: "
                                + uuid).toString());
                message.append("<br>");
            }
        }
        return message.toString();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<RemoveDigitalObjectAction> getActionType() {
        return RemoveDigitalObjectAction.class;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
     * gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.shared.Result,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public void undo(RemoveDigitalObjectAction action,
                     RemoveDigitalObjectResult result,
                     ExecutionContext context) throws ActionException {
        // TODO undo method

    }
}