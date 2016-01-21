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

package cz.mzk.editor.server.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathException;

import javax.inject.Inject;

import com.google.inject.name.Named;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.server.DAO.DigitalObjectDAO;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.fedora.FedoraAccess;
import cz.mzk.editor.server.fedora.utils.FedoraUtils;
import cz.mzk.editor.server.fedora.utils.FoxmlUtils;
import cz.mzk.editor.server.newObject.IngestUtils;
import cz.mzk.editor.server.util.RESTHelper;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.domain.FedoraNamespaces;
import cz.mzk.editor.shared.rpc.Foxml;
import cz.mzk.editor.shared.rpc.action.RemoveDigitalObjectAction;
import cz.mzk.editor.shared.rpc.action.RemoveDigitalObjectResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class RemoveDigitalObjectHandler
        implements ActionHandler<RemoveDigitalObjectAction, RemoveDigitalObjectResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(RemoveDigitalObjectHandler.class.getPackage()
            .toString());

    /** The configuration. */
    private final EditorConfiguration configuration;

    /** The get do model handler */
    private final GetDOModelHandler getDoModelHandler;

    /** The fedora access. */
    private final FedoraAccess fedoraAccess;

    /** The list of digital objects which have been removed */
    private List<RemovedDigitalObject> removedDigitalObjects;

    private static final String ROLLBACK_FLAG = "ROLLBACK_FLAG";

    /** The top digital object uuid. */
    private static String topDigitalObjectUuid;

    /** The digital object dao. */
    @Inject
    private DigitalObjectDAO digitalObjectDAO;

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
                                      @Named("securedFedoraAccess") FedoraAccess fedoraAccess) {
        this.configuration = configuration;
        this.getDoModelHandler = new GetDOModelHandler();
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

        LOGGER.debug("Processing action: RemoveDigitalObjectAction " + action.getUuid());
        ServerUtils.checkExpiredSession();

        if (!ServerUtils.checkUserRightOrAll(EDITOR_RIGHTS.DELETE)) {
            LOGGER.warn("Bad authorization in " + this.getClass().toString());
            throw new ActionException("Bad authorization in " + this.getClass().toString());
        }

        removedDigitalObjects = new ArrayList<RemovedDigitalObject>();
        List<String> uuidNotToRemove = action.getUuidNotToRemove();
        topDigitalObjectUuid = action.getUuid();
        String returnedMessage = remove(action.getUuid(), context, uuidNotToRemove);
        if (!"".equals(returnedMessage)) {
            return new RemoveDigitalObjectResult(returnedMessage, null);
        }
        List<String> removedUuid = new ArrayList<String>();

        for (RemovedDigitalObject removedObj : removedDigitalObjects) {
            removedUuid.add(removedObj.getUuid());
        }

        return new RemoveDigitalObjectResult(null, removedUuid);
    }

    private String remove(String uuid, final ExecutionContext context, List<String> uuidNotToRemove)
            throws ActionException {

        ArrayList<ArrayList<String>> children = FedoraUtils.getAllChildren(uuid);
        int childrenToRemove = 0;
        if (children != null) {
            for (ArrayList<String> child : children) {

                if (!uuidNotToRemove.contains(child.get(0)) && getDoModelHandler.getModel(uuid) != null) {
                    String returnedMessage = remove(child.get(0), context, uuidNotToRemove);
                    if (!"".equals(returnedMessage)) {
                        return returnedMessage;
                    }
                    childrenToRemove++;
                }
            }
        }
        DigitalObjectModel model = null;
        if (!uuidNotToRemove.contains(uuid) && (model = getDoModelHandler.getModel(uuid)) != null) {
            return removeDigObjAndRel(uuid, removedDigitalObjects, context, childrenToRemove < 20, model);
        } else {
            LOGGER.debug("Digital object with uuid: " + uuid + " has not been found.");
            return "";
        }

    }

    private String removeDigObjAndRel(String uuid,
                                      final List<RemovedDigitalObject> removedDigitalObjects,
                                      final ExecutionContext context,
                                      boolean downloadFoxml,
                                      DigitalObjectModel model) throws ActionException {
        ArrayList<ArrayList<String>> parents = FedoraUtils.getRelated(uuid);

        StringBuffer message = new StringBuffer("");
        String usr = configuration.getFedoraLogin();
        String pass = configuration.getFedoraPassword();

        ArrayList<ArrayList<String>> removedRelationships = new ArrayList<ArrayList<String>>();
        Foxml foxml = null;
        if (downloadFoxml) foxml = FoxmlUtils.handleFoxml(uuid, fedoraAccess);

        boolean successful = true;

        if (parents != null) {
            for (ArrayList<String> parentRel : parents) {

                int attempt = 0;
                successful = false;
                while (!successful && attempt++ < 3) {

                    LOGGER.debug("Processing action: RemoveDigitalObjectAction the " + attempt
                            + ". attempt of removing relationship with subject: " + parentRel.get(0)
                            + " predicate: " + parentRel.get(1) + " and object: " + uuid);

                    if (fedoraHasRelationshipsServices()) {

                        /** ----- For Fedora 3.4 and newer ---- */
                        successful =
                                removeRelationshipNew(parentRel,
                                                      uuid,
                                                      successful,
                                                      usr,
                                                      pass,
                                                      removedRelationships);
                    } else {
                        successful = removeRelationshipOld(parentRel, uuid, successful, removedRelationships);
                    }

                }

                if (!successful) {
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
        }

        if (successful) {
            String url = configuration.getFedoraHost() + "/objects/" + uuid;

            int attempt = 0;
            successful = false;
            while (!successful && attempt++ < 3) {
                LOGGER.debug("Processing action: RemoveDigitalObjectAction the " + attempt
                        + ". attempt of removing digital object with uuid:" + uuid);
                if (RESTHelper.deleteWithBooleanResult(url, usr, pass, true)) {
                    successful = getDoModelHandler.getModel(uuid) == null;
                }

            }
            if (successful) {
                RemovedDigitalObject removedDO = new RemovedDigitalObject(uuid, foxml, removedRelationships);
                removedDigitalObjects.add(removedDO);
                disableInDB(removedDO, model);
                return "";

            } else {
                if (getDoModelHandler.getModel(uuid) == null) {//getDoModelHandler.execute(new GetDOModelAction(uuid), context).getModel() == null) {
                    RemovedDigitalObject removedDO =
                            new RemovedDigitalObject(uuid, foxml, removedRelationships);
                    removedDigitalObjects.add(removedDO);
                    disableInDB(removedDO, model);
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

    private void disableInDB(RemovedDigitalObject removedDO, DigitalObjectModel model) {
        try {
            digitalObjectDAO.deleteDigitalObject(removedDO.getUuid(), model.getValue(), removedDO.getFoxml()
                    .getLabel(), topDigitalObjectUuid);
        } catch (Exception e) {
            LOGGER.error("Problem with database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean fedoraHasRelationshipsServices() {
        String fedoraVersion = configuration.getFedoraVersion();
        int secondNum = 0;
        if (fedoraVersion != null && fedoraVersion.length() > 2) {
            try {
                if (Integer.parseInt(fedoraVersion.substring(0, 1)) < 4) {
                    secondNum = Integer.parseInt(fedoraVersion.substring(2, 3));
                } else {
                    return true;
                }
            } catch (NumberFormatException nfe) {
            }
        }

        return (secondNum > 3);
    }

    /**
     * @param parentRel
     * @param uuid
     * @param successful
     * @param usr
     * @param pass
     * @param removedRelationships
     * @return successful
     */
    private boolean removeRelationshipNew(final ArrayList<String> parentRel,
                                          final String uuid,
                                          boolean successful,
                                          final String usr,
                                          final String pass,
                                          ArrayList<ArrayList<String>> removedRelationships) {
        String url = null;
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

        String result = RESTHelper.deleteWithStringResult(url, usr, pass, true);
        successful = result.contains("true");

        if (successful) {
            removedRelationships.add(parentRel);
        }

        return successful;
    }

    /**
     * @param parentRel
     * @param uuid
     * @param successful
     * @param removedRelationships
     * @return successful
     */
    private boolean removeRelationshipOld(final ArrayList<String> parentRel,
                                          final String uuid,
                                          boolean successful,
                                          ArrayList<ArrayList<String>> removedRelationships) {
        String originalRelsExt = null;
        try {
            Document relsExt = fedoraAccess.getRelsExt(parentRel.get(0));

            originalRelsExt = FedoraUtils.getStringFromDocument(relsExt, true);

            String xPath =
                    "/rdf:RDF/rdf:Description/kramerius:" + parentRel.get(1) + "[@rdf:resource=\'"
                            + Constants.FEDORA_INFO_PREFIX + uuid + "\']";

            FedoraUtils.removeElements(FoxmlUtils.getElement(relsExt, "//rdf:RDF/rdf:Description"),
                                       relsExt,
                                       xPath);

            String content = FedoraUtils.getStringFromDocument(relsExt, true);
            successful = FedoraUtils.putRelsExt(parentRel.get(0), content, false);

        } catch (IOException e) {
            LOGGER.warn("IO failure", e);
            successful = false;
        } catch (XPathException e) {
            LOGGER.warn("XPath failure", e);
            successful = false;
        } catch (TransformerException e) {
            LOGGER.warn("Document transformer failure", e);
            successful = false;
        }

        if (successful) {
            ArrayList<String> removedRel = new ArrayList<String>(2);
            removedRel.add(parentRel.get(0));
            removedRel.add(originalRelsExt);
            removedRelationships.add(removedRel);
        }

        return successful;
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
                        IngestUtils.ingest(removed.getFoxml().getNoCodedfoxml(), removed.getFoxml()
                                .getLabel(), removed.getUuid(), ROLLBACK_FLAG, null, null);
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
            int attempt = 0;
            boolean successful = false;
            while (!successful && attempt++ < 3) {
                LOGGER.debug("Processing rollback action: RemoveDigitalObjectAction the " + attempt
                        + ". attempt of rollback removal relationship with subject: " + parentRel.get(0)
                        + " predicate: " + parentRel.get(1) + " and object: " + uuid);

                if (fedoraHasRelationshipsServices()) {
                    /** ----- For Fedora 3.4 and newer ---- */
                    successful = addRelationshipNew(parentRel, uuid);
                } else {
                    successful = addRelationshipOld(parentRel);
                }
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

    /**
     * @param parentRel
     * @param uuid
     * @return successful
     */

    private boolean addRelationshipNew(ArrayList<String> parentRel, String uuid) {
        String url = null;
        String usr = configuration.getFedoraLogin();
        String pass = configuration.getFedoraPassword();
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

        boolean successful = RESTHelper.post(url, "", usr, pass, true);

        return successful;
    }

    /**
     * @param parentRel
     * @param uuid
     * @return successful
     */

    private boolean addRelationshipOld(ArrayList<String> parentRel) {
        return FedoraUtils.putRelsExt(parentRel.get(0), parentRel.get(1), false);
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