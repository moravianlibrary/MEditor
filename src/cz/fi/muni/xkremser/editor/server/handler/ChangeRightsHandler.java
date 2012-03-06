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

import javax.servlet.http.HttpSession;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.RESTHelper;
import cz.fi.muni.xkremser.editor.server.fedora.utils.XMLUtils;

import cz.fi.muni.xkremser.editor.shared.rpc.action.ChangeRightsAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ChangeRightsResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class ChangeRightsHandler
        implements ActionHandler<ChangeRightsAction, ChangeRightsResult> {

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    @Inject
    private @Named("securedFedoraAccess")
    FedoraAccess fedoraAccess;

    /** The configuration. */
    @Inject
    private EditorConfiguration configuration;

    private static final Logger LOGGER = Logger.getLogger(ChangeRightsHandler.class);

    /**
     * {@inheritDoc}
     */

    @Override
    public ChangeRightsResult execute(ChangeRightsAction action, ExecutionContext context)
            throws ActionException {

        ServerUtils.checkExpiredSession(httpSessionProvider);

        if (action.isForChildren()) {
            setChildrenRights(action.getParentUuid(), action.getRight());
        }
        return new ChangeRightsResult();
    }

    private void setChildrenRights(String uuid, String right) throws ActionException {
        ArrayList<ArrayList<String>> allChildren = FedoraUtils.getAllChildren(uuid);
        if (allChildren != null) {
            for (ArrayList<String> child : allChildren) {
                String childUuid = child.get(0);
                setChildrenRights(childUuid, right);
            }
        }
        changeRights(uuid, right);
    }

    private void changeRights(String uuid, String right) throws ActionException {
        Document dcDocument = getDc(uuid);

        String xPath = "/oai_dc:dc/dc:rights";

        try {
            Element element = XMLUtils.getElement(dcDocument, xPath);
            element.setTextContent(right);
        } catch (XPathExpressionException e) {
            LOGGER.error("XPath expression error while finding a DC-element with a right of the object: "
                    + uuid + " " + e);
            throw new ActionException(e);
        }

        String url =
                configuration.getFedoraHost() + "/objects/" + uuid
                        + "/datastreams/DC?versionable=false&mimeType=text/xml";
        String usr = configuration.getFedoraLogin();
        String pass = configuration.getFedoraPassword();
        String content = null;
        try {
            content = FedoraUtils.getStringFromDocument(dcDocument, true);
        } catch (TransformerException e) {
            LOGGER.error("Transformer error while getting the DC-string from DC-document of the object: "
                    + uuid + " " + e);
            throw new ActionException(e);
        }
        if (content != null) {
            RESTHelper.put(url, content, usr, pass, false);
        }
        LOGGER.info("The right of the object: " + uuid + " has been changed to: " + right);
    }

    private Document getDc(String uuid) throws ActionException {
        Document dcDocument = null;
        try {
            dcDocument = fedoraAccess.getDC(uuid);
        } catch (IOException e) {
            LOGGER.error("Unable to get DC document for " + uuid + "[" + e.getMessage() + "]", e);
            throw new ActionException(e);
        }
        return dcDocument;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Class<ChangeRightsAction> getActionType() {
        return ChangeRightsAction.class;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void undo(ChangeRightsAction arg0, ChangeRightsResult arg1, ExecutionContext arg2)
            throws ActionException {
        // TODO Auto-generated method stub
    }

}
