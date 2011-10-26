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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.servlet.http.HttpSession;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FoxmlUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.RESTHelper;

import cz.fi.muni.xkremser.editor.shared.rpc.action.DownloadDigitalObjectDetailAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.DownloadDigitalObjectDetailResult;
import cz.fi.muni.xkremser.editor.shared.valueobj.DigitalObjectDetail;

import static cz.fi.muni.xkremser.editor.client.domain.FedoraNamespaces.BIBILO_MODS_URI;
import static cz.fi.muni.xkremser.editor.client.domain.FedoraNamespaces.OAI_DC_NAMESPACE_URI;
import static cz.fi.muni.xkremser.editor.client.domain.FedoraNamespaces.RELS_EXT_NAMESPACE_URI;
import static cz.fi.muni.xkremser.editor.client.util.Constants.BIBLIO_MODS;
import static cz.fi.muni.xkremser.editor.client.util.Constants.DC;
import static cz.fi.muni.xkremser.editor.client.util.Constants.RELS_EXT;
import static cz.fi.muni.xkremser.editor.server.fedora.utils.FoxmlUtils.LABEL_VALUE;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class DownloadDigitalObjectDetailHandler
        implements ActionHandler<DownloadDigitalObjectDetailAction, DownloadDigitalObjectDetailResult> {

    /** The fedora access. */
    @Inject
    @Named("securedFedoraAccess")
    private FedoraAccess fedoraAccess;

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(FedoraUtils.class);

    /** The configuration. */
    @Inject
    private EditorConfiguration config;

    /**
     * {@inheritDoc}
     */

    @Override
    public DownloadDigitalObjectDetailResult execute(DownloadDigitalObjectDetailAction action,
                                                     ExecutionContext context) throws ActionException {

        if (action == null || action.getDetail() == null) throw new NullPointerException("getDetail()");
        HttpSession session = httpSessionProvider.get();
        ServerUtils.checkExpiredSession(session);

        DigitalObjectDetail detail = action.getDetail();
        String newContent = null;
        Document newDocument = null;

        String desiredDatastream = action.getDatastream();
        if (desiredDatastream == null) {
            newDocument = handleFoxml(detail);

        } else if (desiredDatastream.equals(DC)) {
            if (detail.isDcChanged()) {
                newContent = FedoraUtils.createNewDublinCorePart(detail);
            } else {
                try {
                    newDocument = fedoraAccess.getDC(detail.getUuid());
                } catch (IOException e) {
                    throw new ActionException();
                }
            }

        } else if (desiredDatastream.equals(BIBLIO_MODS)) {
            if (detail.isModsChanged()) {
                newContent = FedoraUtils.createNewModsPart(detail);
            } else {
                try {
                    newDocument = fedoraAccess.getBiblioMods(detail.getUuid());
                } catch (IOException e) {
                    throw new ActionException();
                }
            }

        } else if (desiredDatastream.equals(RELS_EXT)) {
            if (detail.getAllItems() != null) {
                newContent = FedoraUtils.createNewRealitonsPart(detail);
            } else {
                try {
                    newDocument = fedoraAccess.getRelsExt(detail.getUuid());
                } catch (IOException e) {
                    throw new ActionException();
                }
            }
        }

        if (newContent == null && newDocument != null) {
            try {
                TransformerFactory transFactory = TransformerFactory.newInstance();
                Transformer transformer = transFactory.newTransformer();
                StringWriter buffer = new StringWriter();
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                transformer.transform(new DOMSource(newDocument), new StreamResult(buffer));
                newContent = buffer.toString();
            } catch (TransformerException e) {
                LOGGER.warn("Document transformer failure", e);
            }
        }
        //        System.out.println("new fowml: " + newContent);

        RESTHelper.post("http://127.0.0.1:8888/download/?gwt.codesvr=127.0.0.1:9997",
                        newContent,
                        null,
                        null,
                        true);

        return new DownloadDigitalObjectDetailResult();
    }

    private Document handleFoxml(DigitalObjectDetail detail) throws ActionException {
        Document foxmlDocument = null;
        try {
            foxmlDocument = FoxmlUtils.getFoxmlDocument(fedoraAccess.getFOXMLInputStream(detail.getUuid()));
        } catch (IOException e) {
            throw new ActionException();
        }

        if (detail.isLabelChanged()) {
            modifyLabel(detail, foxmlDocument);
        }
        if (detail.isDcChanged()) {
            modifyStream(detail, foxmlDocument, DC);
        }
        if (detail.isModsChanged()) {
            modifyStream(detail, foxmlDocument, BIBLIO_MODS);
        }
        if (detail.getAllItems() != null) {
            modifyStream(detail, foxmlDocument, RELS_EXT);
        }
        if (detail.isOcrChanged()) {

        }

        return foxmlDocument;
    }

    private int getNumberOfVersion(String id) {
        String[] splitedId = id.split("\\.");
        return Integer.parseInt(splitedId[1]);
    }

    /**
     * @param detail
     * @param foxmlDocument
     */

    private void modifyStream(DigitalObjectDetail detail, Document foxmlDocument, String streamToModify) {

        try {

            String lastStreamXPath =
                    "//foxml:datastream[@ID=\'" + streamToModify + "\']/foxml:datastreamVersion[last()]";

            XPathExpression all = FedoraUtils.makeNSAwareXpath().compile(lastStreamXPath);
            NodeList listOfstream = (NodeList) all.evaluate(foxmlDocument, XPathConstants.NODESET);
            Element elementOfVersion = null;
            if (listOfstream.getLength() != 0) {
                elementOfVersion = (Element) listOfstream.item(0);
            }
            //            System.err.println(elementOfVersion.getAttribute("ID"));
            int versionNumber = getNumberOfVersion(elementOfVersion.getAttribute("ID"));

            all =
                    FedoraUtils.makeNSAwareXpath().compile("//foxml:datastream[@ID=\'" + streamToModify
                            + "\']");

            NodeList nodesOfStream = (NodeList) all.evaluate(foxmlDocument, XPathConstants.NODESET);
            Element parentOfStream = null;
            if (nodesOfStream.getLength() != 0) {
                parentOfStream = (Element) nodesOfStream.item(0);
            }

            Element versionElement = foxmlDocument.createElement("foxml:datastreamVersion");
            String content = null;

            if (streamToModify.equals(RELS_EXT)) {
                versionElement.setAttribute("LABEL", "RDF Statements about this object");
                versionElement.setAttribute("FORMAT_URI", RELS_EXT_NAMESPACE_URI);
                versionElement.setAttribute("MIMETYPE", "application/rdf+xml");
                content = FedoraUtils.createNewRealitonsPart(detail);

            } else {
                versionElement.setAttribute("MIMETYPE", "text/xml");

                if (streamToModify.equals(DC)) {
                    versionElement.setAttribute("LABEL", "Dublin Core Record for this object");
                    versionElement.setAttribute("FORMAT_URI", OAI_DC_NAMESPACE_URI);
                    content = FedoraUtils.createNewDublinCorePart(detail);

                } else {
                    versionElement.setAttribute("LABEL", "BIBLIO_MODS description of current object");
                    versionElement.setAttribute("FORMAT_URI", BIBILO_MODS_URI);
                    content = FedoraUtils.createNewModsPart(detail);
                }
            }

            if (content != null) {

                FedoraUtils.removeElements(parentOfStream, foxmlDocument, FedoraUtils.makeNSAwareXpath()
                        .compile(lastStreamXPath));

                versionElement.setAttribute("ID", streamToModify + "." + (versionNumber + 1));
                versionElement.setAttribute("CREATED", "NOT YET");
                versionElement.setAttribute("SIZE", "0");

                Element contentElement = foxmlDocument.createElement("foxml:xmlContent");

                try {
                    InputStream is = new ByteArrayInputStream(content.getBytes("UTF-8"));
                    Document newStreamDocument = FoxmlUtils.getFoxmlDocument(is);
                    NodeList streamNodeList = newStreamDocument.getChildNodes();
                    for (int i = 0; i < streamNodeList.getLength(); i++) {
                        Node myNewNode = foxmlDocument.importNode(streamNodeList.item(i), true);
                        System.err.println(myNewNode);
                        contentElement.appendChild(myNewNode);
                    }
                } catch (IOException e) {
                    System.err.println("IO fauilure" + e);
                }

                versionElement.appendChild(contentElement);
                parentOfStream.appendChild(versionElement);
            }
        } catch (XPathExpressionException e) {
            LOGGER.warn("XPath failure", e);
        }
    }

    /**
     * @param detail
     */

    private void modifyLabel(DigitalObjectDetail detail, Document foxmlDocument) {
        String propertyLabelXPath = "//foxml:objectProperties/foxml:property[@NAME=\'" + LABEL_VALUE + "\']";

        try {
            XPathExpression all = FedoraUtils.makeNSAwareXpath().compile(propertyLabelXPath);
            NodeList listOfstream = (NodeList) all.evaluate(foxmlDocument, XPathConstants.NODESET);
            Element propertyLabelElement = null;
            if (listOfstream.getLength() != 0) {
                propertyLabelElement = (Element) listOfstream.item(0);
            }
            propertyLabelElement.setAttribute("VALUE", detail.getLabel());

        } catch (XPathExpressionException e) {
            LOGGER.warn("XPath failure", e);
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Class<DownloadDigitalObjectDetailAction> getActionType() {
        return DownloadDigitalObjectDetailAction.class;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void undo(DownloadDigitalObjectDetailAction arg0,
                     DownloadDigitalObjectDetailResult arg1,
                     ExecutionContext arg2) throws ActionException {
        // TODO Auto-generated method stub
    }

    public static void main(String... args) {
        RESTHelper
                .post("http://127.0.0.1:8888/download/?gwt.codesvr=127.0.0.1:9997", "foo", null, null, true);
    }

}
