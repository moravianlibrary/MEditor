/*
 * Metadata Editor
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;

import cz.fi.muni.xkremser.editor.client.CreateObjectException;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.HttpCookies;
import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO;
import cz.fi.muni.xkremser.editor.server.DAO.UserDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;
import cz.fi.muni.xkremser.editor.server.fedora.utils.XMLUtils;
import cz.fi.muni.xkremser.editor.server.newObject.CreateObjectUtils;

import cz.fi.muni.xkremser.editor.shared.rpc.NewDigitalObject;
import cz.fi.muni.xkremser.editor.shared.rpc.action.InsertNewDigitalObjectAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.InsertNewDigitalObjectResult;

/**
 * @author Jiri Kremser
 * @version 14.11.2011
 */
public class InsertNewDigitalObjectHandler
        implements ActionHandler<InsertNewDigitalObjectAction, InsertNewDigitalObjectResult> {

    private static final Logger LOGGER = Logger.getLogger(InsertNewDigitalObjectHandler.class);

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    @Inject
    private InputQueueItemDAO inputQueueItemDAO;

    /** The user DAO **/
    @Inject
    private UserDAO userDAO;

    @Inject
    private EditorConfiguration config;

    public InsertNewDigitalObjectHandler() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InsertNewDigitalObjectResult execute(InsertNewDigitalObjectAction action, ExecutionContext context)
            throws ActionException {
        HttpSession ses = httpSessionProvider.get();
        ServerUtils.checkExpiredSession(ses);
        NewDigitalObject object = action.getObject();
        if (object == null) throw new NullPointerException("object");
        if (LOGGER.isInfoEnabled()) {
            LOGGER.debug("Inserting digital object: " + object.getName());
        }

        boolean ingestSuccess;
        boolean reindexSuccess;
        try {
            ingestSuccess = CreateObjectUtils.insertAllTheStructureToFOXMLs(object);
            reindexSuccess = ServerUtils.reindex(Constants.FEDORA_UUID_PREFIX + object.getUuid());

            if (ingestSuccess) {
                String username;
                try {
                    username =
                            userDAO.getName(String.valueOf(String.valueOf(ses
                                    .getAttribute(HttpCookies.SESSION_ID_KEY))), true);
                } catch (DatabaseException e) {
                    throw new ActionException(e);
                }
                try {
                    inputQueueItemDAO.updateIngestInfo(true, action.getInputPath());
                } catch (DatabaseException e) {
                    throw new ActionException(e);
                }
                createInfoXml(username,
                              object.getUuid(),
                              config.getScanInputQueuePath() + action.getInputPath());

            }

        } catch (CreateObjectException e) {
            throw new ActionException(e.getMessage());
        }
        return new InsertNewDigitalObjectResult(ingestSuccess, reindexSuccess, Constants.FEDORA_UUID_PREFIX
                + object.getUuid());

    }

    /**
     * @param name
     * @param uuid
     * @param path
     */

    private void createInfoXml(String username, String pid, String path) {

        try {
            File ingestInfoFile = new File(path + "/" + Constants.INGEST_INFO_FILE_NAME);
            Document doc = null;
            Element rootElement = null;
            boolean fileExists = ingestInfoFile.exists();
            final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

            if (fileExists) {
                try {
                    FileInputStream fileStream = new FileInputStream(ingestInfoFile);
                    doc = XMLUtils.parseDocument(fileStream);
                    rootElement = XMLUtils.getElement(doc, "//" + Constants.NAME_ROOT_INGEST_ELEMENT + "[1]");
                    fileStream.close();
                } catch (FileNotFoundException e) {
                    fileExists = false;
                } catch (SAXException e) {
                    fileExists = false;
                } catch (IOException e) {
                    fileExists = false;
                }
            }
            if (!fileExists) {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                doc = docBuilder.newDocument();
                rootElement = null;
            }
            int number = 0;
            if (rootElement == null) {
                rootElement = doc.createElement(Constants.NAME_ROOT_INGEST_ELEMENT);
                doc.appendChild(rootElement);
            } else {
                number =
                        Integer.parseInt(XMLUtils.getElement(doc,
                                                             "//" + Constants.NAME_ROOT_INGEST_ELEMENT
                                                                     + "[1]//"
                                                                     + Constants.NAME_INGEST_ELEMENT
                                                                     + "[position()=last()]")
                                .getAttribute("number"));
            }

            // ingest elements
            Element ingest = doc.createElement("ingest");
            ingest.setAttribute("number", String.valueOf(++number));
            rootElement.appendChild(ingest);

            // uuid element
            Element pidEl = doc.createElement(Constants.PARAM_PID);
            pidEl.appendChild(doc.createTextNode(pid));
            ingest.appendChild(pidEl);

            // name element
            Element nameEl = doc.createElement(Constants.PARAM_USER_NAME);
            nameEl.appendChild(doc.createTextNode(username));
            ingest.appendChild(nameEl);

            // time element
            Element time = doc.createElement(Constants.PARAM_TIME);
            time.appendChild(doc.createTextNode(FORMATTER.format(new java.util.Date())));
            ingest.appendChild(time);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(ingestInfoFile);
            transformer.transform(source, result);

        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<InsertNewDigitalObjectAction> getActionType() {
        return InsertNewDigitalObjectAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(InsertNewDigitalObjectAction action,
                     InsertNewDigitalObjectResult result,
                     ExecutionContext context) throws ActionException {
        // TODO Auto-generated method stub
    }

}
