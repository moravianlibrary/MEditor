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

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import javax.inject.Inject;

import com.google.inject.name.Named;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.fedora.FedoraAccess;
import cz.mzk.editor.server.fedora.utils.FedoraUtils;
import cz.mzk.editor.server.fedora.utils.FoxmlUtils;
import cz.mzk.editor.server.util.RESTHelper;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.rpc.DigitalObjectDetail;
import cz.mzk.editor.shared.rpc.action.PutDigitalObjectDetailAction;
import cz.mzk.editor.shared.rpc.action.PutDigitalObjectDetailResult;

// TODO: Auto-generated Javadoc
/**
 * The Class GetDigitalObjectDetailHandler.
 */
public class PutDigitalObjectDetailHandler
        implements ActionHandler<PutDigitalObjectDetailAction, PutDigitalObjectDetailResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(PutDigitalObjectDetailHandler.class.getPackage()
            .toString());

    /** The user dao. */
    private final UserDAO userDAO;

    /** The configuration. */
    private final EditorConfiguration configuration;

    private final FedoraAccess fedoraAccess;

    // /** The injector. */
    // @Inject
    // Injector injector;

    /**
     * Instantiates a new gets the digital object detail handler.
     * 
     * @param userDAO
     *        the user dao
     * @param configuration
     *        the configuration
     * @param httpSessionProvider
     *        the http session provider
     */
    @Inject
    public PutDigitalObjectDetailHandler(final UserDAO userDAO,
                                         final EditorConfiguration configuration,
                                         @Named("securedFedoraAccess") FedoraAccess fedoraAccess) {
        this.configuration = configuration;
        this.userDAO = userDAO;
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
    public PutDigitalObjectDetailResult execute(final PutDigitalObjectDetailAction action,
                                                final ExecutionContext context) throws ActionException {

        LOGGER.debug("Processing action: PutDigitalObjectDetailAction " + action.getDetail().getUuid());
        ServerUtils.checkExpiredSession();

        if (action == null || action.getDetail() == null) throw new NullPointerException("getDetail()");
        //TODO
        boolean write = false;
        //        try {
        write = true;
        //                    userDAO.hasRole(UserDAO.CAN_PUBLISH_STRING, userDAO.getUsersId(openID))
        //                            || HttpCookies.ADMIN_YES.equals(session.getAttribute(HttpCookies.ADMIN));
        //        } catch (DatabaseException e) {
        //            throw new ActionException(e);
        //        }

        boolean shouldReindex = false;
        if (write) {
            DigitalObjectDetail detail = action.getDetail();
            shouldReindex = modifyRelations(detail, action.isVersioning());
            if (detail.isLabelChanged()) {
                modifyLabel(detail, action.isVersioning());
                shouldReindex = true;
            }
            if (detail.isDcChanged()) {
                modifyDublinCore(detail, action.isVersioning());
                shouldReindex = true;
            }

            if (detail.isModsChanged()) {
                modifyMods(detail, action.isVersioning());
                shouldReindex = true;
            }
            if (detail.isOcrChanged()) {
                if (detail.thereWasAnyOcr()) {
                    modifyOcr(detail, action.isVersioning());
                } else {
                    putOcr(detail);
                }

                shouldReindex = true;
            }

            try {
                shouldReindex = verifyModelElement(detail) || shouldReindex;
            } catch (XPathExpressionException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            } catch (TransformerException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }

            if (shouldReindex) {
                ServerUtils.reindex(detail.getUuid());
            }
        }
        return new PutDigitalObjectDetailResult(write);
    }

    /**
     * @param detail
     * @throws IOException
     * @throws XPathExpressionException
     * @throws TransformerException
     */
    private boolean verifyModelElement(DigitalObjectDetail detail) throws IOException,
            XPathExpressionException, TransformerException {
        String url =
                configuration.getFedoraHost() + "/objects/" + detail.getUuid()
                        + "/datastreams/DC?versionable=false&mimeType=text/xml";
        String usr = configuration.getFedoraLogin();
        String pass = configuration.getFedoraPassword();
        Document dc = fedoraAccess.getDC(detail.getUuid());

        XPathExpression allTypes = FedoraUtils.makeNSAwareXpath().compile("//dc:type");
        NodeList typeElements = (NodeList) allTypes.evaluate(dc, XPathConstants.NODESET);
        Element typeEl = null;
        if (typeElements != null && typeElements.getLength() == 1) {
            typeEl = (Element) typeElements.item(0);
        } else if (typeElements.getLength() > 1) {
            FedoraUtils.removeElements((Element) typeElements.item(0).getParentNode(), dc, "//dc:type");
        }

        String dcModel = null;
        if (typeEl != null) {
            String currentModel = typeEl.getTextContent();
            if (currentModel.startsWith(Constants.FEDORA_MODEL_PREFIX)) {
                DigitalObjectModel mod = null;
                try {
                    mod =
                            DigitalObjectModel.parseString(currentModel
                                    .substring(Constants.FEDORA_MODEL_PREFIX.length()));
                } catch (RuntimeException re) {
                }
                try {
                    if (mod != null && FedoraUtils.getModel(detail.getUuid()) == mod) dcModel = currentModel;
                } catch (IOException e) {
                }
            }
        } else {
            String dcStreamXpath = "/oai_dc:dc";
            Element dcStreamEl = FoxmlUtils.getElement(dc, dcStreamXpath);
            typeEl = dc.createElement("dc:type");
            dcStreamEl.appendChild(typeEl);
        }

        if (dcModel == null) {
            typeEl.setTextContent(Constants.FEDORA_MODEL_PREFIX
                    + FedoraUtils.getModel(detail.getUuid()).getValue());
            String content = FedoraUtils.getStringFromDocument(dc, true);
            if (content != null) {
                RESTHelper.put(url, content, usr, pass, false);
                return true;
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<PutDigitalObjectDetailAction> getActionType() {
        return PutDigitalObjectDetailAction.class;
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
    public void undo(PutDigitalObjectDetailAction action,
                     PutDigitalObjectDetailResult result,
                     ExecutionContext context) throws ActionException {
        // idempotency -> no need for undo
    }

    /**
     * Modify relations.
     * 
     * @param detail
     *        the detail
     * @param versionable
     */
    private boolean modifyRelations(DigitalObjectDetail detail, boolean versionable) {

        return FedoraUtils.putRelsExt(detail.getUuid(),
                                      FedoraUtils.createNewRealitonsPart(detail),
                                      versionable);
    }

    /**
     * Modify dublin core.
     * 
     * @param detail
     *        the detail
     * @param versionable
     */
    private void modifyDublinCore(DigitalObjectDetail detail, boolean versionable) {
        String url =
                configuration.getFedoraHost() + "/objects/" + detail.getUuid()
                        + "/datastreams/DC?versionable=" + (versionable ? "true" : "false")
                        + "&mimeType=text/xml";
        String usr = configuration.getFedoraLogin();
        String pass = configuration.getFedoraPassword();
        String content = FedoraUtils.createNewDublinCorePart(detail.getDc());
        if (content != null) {
            RESTHelper.put(url, content, usr, pass, false);
        }
    }

    /**
     * Modify mods.
     * 
     * @param detail
     *        the detail
     */
    private void modifyMods(DigitalObjectDetail detail, boolean versionable) {
        String url =
                configuration.getFedoraHost() + "/objects/" + detail.getUuid()
                        + "/datastreams/BIBLIO_MODS?versionable=" + (versionable ? "true" : "false")
                        + "&mimeType=text/xml";
        String usr = configuration.getFedoraLogin();
        String pass = configuration.getFedoraPassword();
        String content = FedoraUtils.createNewModsPart(detail.getMods());
        if (content != null) {
            RESTHelper.put(url, content, usr, pass, false);
        }
    }

    /**
     * Modify ocr.
     * 
     * @param detail
     *        the detail
     * @param versionable
     */
    private void modifyOcr(DigitalObjectDetail detail, boolean versionable) {
        if (detail.getOcr() != null) {
            String url =
                    configuration.getFedoraHost() + "/objects/" + detail.getUuid()
                            + "/datastreams/TEXT_OCR?versionable=" + (versionable ? "true" : "false")
                            + "&mimeType=text/plain";
            String usr = configuration.getFedoraLogin();
            String pass = configuration.getFedoraPassword();
            RESTHelper.put(url, detail.getOcr(), usr, pass, false);
        }
    }

    /**
     * Put ocr.
     * 
     * @param detail
     *        the detail
     */
    private void putOcr(DigitalObjectDetail detail) {
        if (detail.getOcr() != null) {
            String url =
                    configuration.getFedoraHost()
                            + "/objects/"
                            + detail.getUuid()
                            + "/datastreams/TEXT_OCR?controlGroup=M&dsState=A&versionable=false&mimeType=text/plain";
            String usr = configuration.getFedoraLogin();
            String pass = configuration.getFedoraPassword();
            RESTHelper.post(url, detail.getOcr(), usr, pass, false);
        }
    }

    private void modifyLabel(DigitalObjectDetail detail, boolean versionable) {
        if (detail.getLabel() != null) {
            String newLabel = FoxmlUtils.encodeToURL(detail.getLabel());
            String url =
                    configuration.getFedoraHost() + "/objects/" + detail.getUuid() + "?label=" + newLabel;
            String usr = configuration.getFedoraLogin();
            String pass = configuration.getFedoraPassword();

            RESTHelper.put(url, "", usr, pass, false);
        }

    }

}