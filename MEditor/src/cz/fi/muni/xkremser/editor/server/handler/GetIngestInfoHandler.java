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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.XMLUtils;

import cz.fi.muni.xkremser.editor.shared.rpc.action.GetIngestInfoAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetIngestInfoResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class GetIngestInfoHandler
        implements ActionHandler<GetIngestInfoAction, GetIngestInfoResult> {

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    /** The configuration. */
    private final EditorConfiguration configuration;

    /**
     * Instantiates a new get ingest info handler.
     * 
     * @param configuration
     *        the configuration
     */
    @Inject
    public GetIngestInfoHandler(final EditorConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public GetIngestInfoResult execute(GetIngestInfoAction action, ExecutionContext context)
            throws ActionException {

        HttpSession ses = httpSessionProvider.get();
        ServerUtils.checkExpiredSession(ses);

        String path = action.getPath();

        List<String> pid = new ArrayList<String>();
        List<String> username = new ArrayList<String>();
        List<String> time = new ArrayList<String>();

        File ingestInfoFile =
                new File(configuration.getScanInputQueuePath() + path + "/" + Constants.INGEST_INFO_FILE_NAME);
        boolean fileExists = ingestInfoFile.exists();
        if (fileExists) {
            try {
                FileInputStream fileStream = new FileInputStream(ingestInfoFile);
                Document doc = XMLUtils.parseDocument(fileStream);

                String xPath =
                        "//" + Constants.NAME_ROOT_INGEST_ELEMENT + "[1]//" + Constants.NAME_INGEST_ELEMENT;

                XPathExpression all = FedoraUtils.makeNSAwareXpath().compile(xPath);

                NodeList nodesOfStream = (NodeList) all.evaluate(doc, XPathConstants.NODESET);
                if (nodesOfStream.getLength() != 0) {
                    for (int i = 0; i < nodesOfStream.getLength(); i++) {
                        Element parentElement = (Element) nodesOfStream.item(i);
                        NodeList childNodes = parentElement.getChildNodes();
                        for (int j = 0; j < childNodes.getLength() && j < 3; j++) {
                            if (childNodes.item(j).getNodeName().equals(Constants.PARAM_PID)
                                    && pid.size() < i + 1) pid.add(childNodes.item(j).getTextContent());
                            if (childNodes.item(j).getNodeName().equals(Constants.PARAM_USER_NAME)
                                    && username.size() < i + 1)
                                username.add(childNodes.item(j).getTextContent());
                            if (childNodes.item(j).getNodeName().equals(Constants.PARAM_TIME)
                                    && time.size() < i + 1) time.add(childNodes.item(j).getTextContent());
                        }
                        if (pid.size() < i + 1) pid.add("missing");
                        if (username.size() < i + 1) username.add("missing");
                        if (time.size() < i + 1) time.add("missing");
                    }
                }
                fileStream.close();
            } catch (FileNotFoundException e) {
                fileExists = false;
            } catch (SAXException e) {
                fileExists = false;
            } catch (IOException e) {
                fileExists = false;
            } catch (ParserConfigurationException e) {
                fileExists = false;
            } catch (XPathExpressionException e) {
                fileExists = false;
            }
        }
        if (fileExists) {
            return new GetIngestInfoResult(pid, username, time);
        } else {
            return new GetIngestInfoResult(null, null, null);
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Class<GetIngestInfoAction> getActionType() {
        return GetIngestInfoAction.class;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void undo(GetIngestInfoAction arg0, GetIngestInfoResult arg1, ExecutionContext arg2)
            throws ActionException {
        // TODO Auto-generated method stub
    }

}
