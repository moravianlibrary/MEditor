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

import java.io.IOException;

import java.net.MalformedURLException;

import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.server.HttpCookies;
import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.DAO.UserDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FoxmlUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.RESTHelper;

import cz.fi.muni.xkremser.editor.shared.rpc.DigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutDigitalObjectDetailAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutDigitalObjectDetailResult;

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

    /** The http session provider. */
    private final Provider<HttpSession> httpSessionProvider;

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
                                         Provider<HttpSession> httpSessionProvider) {
        this.configuration = configuration;
        this.userDAO = userDAO;
        this.httpSessionProvider = httpSessionProvider;
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
        if (action == null || action.getDetail() == null) throw new NullPointerException("getDetail()");
        HttpSession session = httpSessionProvider.get();
        ServerUtils.checkExpiredSession(session);

        String openID = (String) session.getAttribute(HttpCookies.SESSION_ID_KEY);
        boolean write = false;
        try {
            write =
                    userDAO.openIDhasRole(UserDAO.CAN_PUBLISH_STRING, openID)
                            || HttpCookies.ADMIN_YES.equals(session.getAttribute(HttpCookies.ADMIN));
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }

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
            if (shouldReindex) {
                reindex(detail.getUuid());
            }
        }
        return new PutDigitalObjectDetailResult(write);
    }

    /**
     * Reindex.
     * 
     * @param uuid
     *        the uuid
     */
    private void reindex(String uuid) {
        String host = configuration.getKrameriusHost();
        String login = configuration.getKrameriusLogin();
        String password = configuration.getKrameriusPassword();
        if (host == null || login == null || password == null) {
            return;
        }
        String url =
                host + "/lr?action=start&def=reindex&out=text&params=fromKrameriusModel," + uuid + "," + uuid
                        + "&userName=" + login + "&pswd=" + password;
        try {
            RESTHelper.openConnection(url, login, password, false);
        } catch (MalformedURLException e) {
            LOGGER.error("Unable to reindex", e);
        } catch (IOException e) {
            LOGGER.error("Unable to reindex", e);
        }
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
        String url =
                configuration.getFedoraHost() + "/objects/" + detail.getUuid()
                        + "/datastreams/RELS-EXT?versionable=" + (versionable ? "true" : "false")
                        + "&mimeType=application/rdf+xml";
        String usr = configuration.getFedoraLogin();
        String pass = configuration.getFedoraPassword();
        String content = FedoraUtils.createNewRealitonsPart(detail);
        if (content != null) {
            RESTHelper.put(url, content, usr, pass, false);
            return true;
        }
        return false;
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