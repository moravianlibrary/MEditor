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

package cz.fi.muni.xkremser.editor.server.fedora.utils;

/**
 *
 * @author incad
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.namespace.NamespaceContext;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import javax.inject.Inject;

import com.google.inject.name.Named;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.fedora.api.RelationshipTuple;

import cz.fi.muni.xkremser.editor.client.DublinCoreConstants;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.mods.ModsCollection;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces;
import cz.fi.muni.xkremser.editor.shared.domain.FedoraRelationship;
import cz.fi.muni.xkremser.editor.shared.domain.NamedGraphModel;
import cz.fi.muni.xkremser.editor.shared.rpc.DigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;

import static cz.fi.muni.xkremser.editor.client.util.Constants.DATASTREAM_ID.BIBLIO_MODS;
import static cz.fi.muni.xkremser.editor.client.util.Constants.DATASTREAM_ID.DC;
import static cz.fi.muni.xkremser.editor.client.util.Constants.DATASTREAM_ID.RELS_EXT;
import static cz.fi.muni.xkremser.editor.client.util.Constants.DATASTREAM_ID.TEXT_OCR;
import static cz.fi.muni.xkremser.editor.server.fedora.utils.FoxmlUtils.LABEL_VALUE;
import static cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces.BIBILO_MODS_URI;
import static cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces.OAI_DC_NAMESPACE_URI;
import static cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces.RELS_EXT_NAMESPACE_URI;

// TODO: Auto-generated Javadoc
/**
 * The Class FedoraUtils.
 */
public class FedoraUtils {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(FedoraUtils.class);

    /** The Constant RELS_EXT_STREAM. */
    public static final String RELS_EXT_STREAM = "RELS-EXT";

    /** The Constant IMG_THUMB_STREAM. */
    public static final String IMG_THUMB_STREAM = "IMG_THUMB";

    /** The Constant IMG_FULL_STREAM. */
    public static final String IMG_FULL_STREAM = "IMG_FULL";

    /** The configuration. */
    @Inject
    private static EditorConfiguration configuration;

    /** The Constant RELS_EXT_PART_1. */
    private static final String RELS_EXT_PART_11 = "<kramerius:";
    private static final String RELS_EXT_PART_12 = "<";

    /** The Constant RELS_EXT_PART_2. */
    private static final String RELS_EXT_PART_21 = " rdf:resource=\"info:fedora/";

    private static final String RELS_EXT_PART_22 =
            " xmlns=\"http://www.nsdl.org/ontologies/relationships#\" rdf:resource=\"info:fedora/";

    /** The Constant RELS_EXT_PART_3. */
    private static final String RELS_EXT_PART_31 = "\"></kramerius:";
    private static final String RELS_EXT_PART_32 = "\"></";

    /** The Constant TERMINATOR1. */
    private static final String TERMINATOR1 = ">\n";

    /** The Constant TERMINATOR2. */
    private static final String TERMINATOR2 = ">";

    /** The Constant DC_HEAD. */
    private static final String DC_HEAD =
            "<oai_dc:dc xmlns:oai_dc=\"http://www.openarchives.org/OAI/2.0/oai_dc/\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd\">\n";

    /** The Constant DC_TAIL. */
    private static final String DC_TAIL = "</oai_dc:dc>";

    /** The Constant DC_PART_1. */
    private static final String DC_PART_1 = "<dc:";

    /** The Constant DC_PART_2. */
    private static final String DC_PART_2 = "</dc:";

    /** The fedora access. */
    @Inject
    @Named("securedFedoraAccess")
    private static FedoraAccess fedoraAccess;

    /** The ns context. */
    @Inject
    private static NamespaceContext nsContext;

    /** The xpfactory. */
    private static XPathFactory xpfactory;

    /**
     * Gets the rdf pids.
     * 
     * @param pid
     *        the pid
     * @param relation
     *        the relation
     * @return the rdf pids
     */
    public static ArrayList<String> getRdfPids(String pid, String relation) {
        ArrayList<String> pids = new ArrayList<String>();
        try {

            String command = configuration.getFedoraHost() + "/get/" + pid + "/" + RELS_EXT_STREAM;
            InputStream is =
                    RESTHelper.get(command,
                                   configuration.getFedoraLogin(),
                                   configuration.getFedoraPassword(),
                                   true);
            Document contentDom = XMLUtils.parseDocument(is);
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            String xPathStr = "/RDF/Description/" + relation;
            XPathExpression expr = xpath.compile(xPathStr);
            NodeList nodes = (NodeList) expr.evaluate(contentDom, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                Node childnode = nodes.item(i);
                if (!childnode.getNodeName().contains("hasModel")) {
                    pids.add(childnode.getNodeName()
                            + " "
                            + childnode.getAttributes().getNamedItem("rdf:resource").getNodeValue()
                                    .split("/")[1]);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return pids;
    }

    /**
     * Gets the subject pids.
     * 
     * @param objectPid
     *        the object pid
     * @return the subject pids
     */
    public static List<RelationshipTuple> getSubjectPids(String objectPid) {
        return getSubjectOrObjectPids("*%20*%20%3Cinfo:fedora/" + objectPid + "%3E");
    }

    /**
     * Gets the object pids.
     * 
     * @param subjectPid
     *        the subject pid
     * @return the object pids
     */
    public static List<RelationshipTuple> getObjectPids(String subjectPid) {
        return getSubjectOrObjectPids("%3Cinfo:fedora/" + subjectPid + "%3E%20*%20*");
    }

    private static List<RelationshipTuple> getSubjectOrObjectPids(String restOfCommand) {
        List<RelationshipTuple> retval = new ArrayList<RelationshipTuple>();
        String command =
                configuration.getFedoraHost() + "/risearch?type=triples&lang=spo&format=N-Triples&query="
                        + restOfCommand;
        InputStream stream = null;
        try {
            stream =
                    RESTHelper.get(command,
                                   configuration.getFedoraLogin(),
                                   configuration.getFedoraPassword(),
                                   true);
            if (stream == null) return null;
            String result = IOUtils.readAsString(stream, Charset.forName("UTF-8"), true);
            String[] lines = result.split("\n");
            for (String line : lines) {
                String[] tokens = line.split(" ");
                if (tokens.length < 3) continue;
                try {
                    RelationshipTuple tuple = new RelationshipTuple();
                    tuple.setSubject(tokens[0].substring(1, tokens[0].length() - 1));
                    tuple.setPredicate(tokens[1].substring(1, tokens[1].length() - 1));
                    tuple.setObject(tokens[2].substring(1, tokens[2].length() - 1));
                    tuple.setIsLiteral(false);
                    retval.add(tuple);
                } catch (Exception ex) {
                    LOGGER.info("Problem parsing RDF, skipping line:" + Arrays.toString(tokens) + " : " + ex);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (stream != null) try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return retval;
    }

    /**
     * Find first page pid.
     * 
     * @param pid
     *        the pid
     * @return the string
     */
    public static String findFirstPagePid(String pid) {

        ArrayList<String> pids = new ArrayList<String>();
        try {
            String command = configuration.getFedoraHost() + "/get/" + pid + "/RELS-EXT";
            InputStream is =
                    RESTHelper.get(command,
                                   configuration.getFedoraLogin(),
                                   configuration.getFedoraPassword(),
                                   true);
            Document contentDom = XMLUtils.parseDocument(is);
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr = xpath.compile("/RDF/Description/*");
            NodeList nodes = (NodeList) expr.evaluate(contentDom, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                Node childnode = nodes.item(i);
                String nodeName = childnode.getNodeName();
                if (nodeName.contains(FedoraRelationship.hasPage.getStringRepresentation())
                        || nodeName.contains(FedoraRelationship.isOnPage.getStringRepresentation())) {
                    return childnode.getAttributes().getNamedItem("rdf:resource").getNodeValue()
                            .split("uuid:")[1];
                } else if (!nodeName.contains("hasModel") && childnode.hasAttributes()
                        && childnode.getAttributes().getNamedItem("rdf:resource") != null) {
                    pids.add(childnode.getAttributes().getNamedItem("rdf:resource").getNodeValue().split("/")[1]);
                }
            }
            for (String relpid : pids) {
                return FedoraUtils.findFirstPagePid(relpid);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Vraci url na stream s DJVU.
     * 
     * @param uuid
     *        objektu
     * @return the dj vu image
     */
    public static String getDjVuImage(String uuid) {
        String imagePath = configuration.getFedoraHost() + "/get/" + uuid + "/" + IMG_FULL_STREAM;
        return imagePath;
    }

    /**
     * Vraci url na stream THUMB.
     * 
     * @param uuid
     *        the uuid
     * @return the thumbnail from fedora
     */
    public static String getThumbnailFromFedora(String uuid) {
        String imagePath = configuration.getFedoraHost() + "/get/" + uuid + "/" + IMG_THUMB_STREAM;
        return imagePath;
    }

    /**
     * Gets the fedora datastreams list.
     * 
     * @param uuid
     *        the uuid
     * @return the fedora datastreams list
     */
    public static String getFedoraDatastreamsList(String uuid) {
        String datastreamsListPath =
                configuration.getFedoraHost() + "/objects/" + uuid + "/datastreams?format=xml";
        return datastreamsListPath;
    }

    /**
     * Removes the elements.
     * 
     * @param parent
     *        the parent
     * @param doc
     *        the doc
     * @param expr
     *        the expr
     * @throws XPathExpressionException
     */
    public static void removeElements(Element parent, Document doc, String xPath)
            throws XPathExpressionException {
        XPathExpression expr = makeNSAwareXpath().compile(xPath);
        NodeList nodes = null;
        try {
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0, lastIndex = nodes.getLength() - 1; i <= lastIndex; i++) {
                parent.removeChild(nodes.item(i));
            }
        } catch (XPathExpressionException e) {
            LOGGER.error("Unable to remove elements", e);
        }
    }

    /**
     * Make ns aware xpath.
     * 
     * @return the x path
     */
    public static XPath makeNSAwareXpath() {
        if (xpfactory == null) {
            xpfactory = XPathFactory.newInstance();
        }
        XPath xpath = xpfactory.newXPath();
        xpath.setNamespaceContext(nsContext);
        return xpath;
    }

    /**
     * Create new relations part.
     * 
     * @param detail
     *        the detail
     * @return content content of the new relations part
     */
    public static String createNewRealitonsPart(DigitalObjectDetail detail) {
        StringBuilder sb = new StringBuilder();
        if (detail.getAllItems() == null) return null;

        Document relsExt = null;
        StringBuilder contentBuilder = new StringBuilder();
        try {
            relsExt = fedoraAccess.getRelsExt(detail.getUuid());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            XPathExpression all = makeNSAwareXpath().compile("/rdf:RDF/rdf:Description");
            NodeList nodes1 = (NodeList) all.evaluate(relsExt, XPathConstants.NODESET);
            Element parent = null;
            if (nodes1.getLength() != 0) {
                parent = (Element) nodes1.item(0);
            }

            int modelId = 0;
            boolean changed = false;
            List<DigitalObjectModel> models = NamedGraphModel.getChildren(detail.getModel());
            for (List<DigitalObjectDetail> data : detail.getAllItems()) {
                if (data != null) { // is changed
                    changed = true;
                    String xPathString =
                            NamedGraphModel.getRelationship(detail.getModel(), models.get(modelId))
                                    .getXPathNamespaceAwareQuery();
                    removeElements(parent, relsExt, xPathString);
                }
                modelId++;
            }
            if (!changed) return null;

            String str = null;
            try {
                str = getStringFromDocument(relsExt, true);
            } catch (TransformerException e) {
                LOGGER.warn("Document transformer failure", e);
            }

            int lastIndex = str.indexOf(Constants.RELS_EXT_LAST_ELEMENT);
            if (lastIndex == -1) {
                // error
            }
            boolean lameNS = str.contains(FedoraNamespaces.KRAMERIUS_URI);
            String head = str.substring(0, lastIndex).trim();
            String tail = str.substring(lastIndex, str.length());

            // container structure
            int i = 0;
            for (List<DigitalObjectDetail> data : detail.getAllItems()) {
                if (data != null) { // is changed
                    String relation =
                            NamedGraphModel.getRelationship(detail.getModel(), models.get(i))
                                    .getStringRepresentation();
                    for (DigitalObjectDetail obj : data) {
                        sb.append(lameNS ? RELS_EXT_PART_12 : RELS_EXT_PART_11).append(relation)
                                .append(lameNS ? RELS_EXT_PART_22 : RELS_EXT_PART_21).append(obj.getUuid())
                                .append(lameNS ? RELS_EXT_PART_32 : RELS_EXT_PART_31).append(relation)
                                .append(TERMINATOR1);
                    }
                }
                i++;
            }

            contentBuilder.append(head).append(sb).append(tail);

        } catch (XPathExpressionException e) {
            LOGGER.warn("XPath failure", e);
        }

        String content = contentBuilder.toString();

        return content;
    }

    /**
     * Append dc element.
     * 
     * @param contentBuilder
     *        the content builder
     * @param values
     *        the values
     * @param elementName
     *        the element name
     */
    private static void appendDCElement(StringBuilder contentBuilder, List<String> values, String elementName) {
        if (values != null && values.size() > 0) {
            for (String value : values) {
                contentBuilder.append(DC_PART_1).append(elementName).append(TERMINATOR2).append(value)
                        .append(DC_PART_2).append(elementName).append(TERMINATOR1);
            }
        }
    }

    /**
     * Create new dublin core part.
     * 
     * @param dc
     *        the dublin core
     * @return content the content of the new dublin core part.
     */
    public static String createNewDublinCorePart(DublinCore dc) {
        if (dc != null) {
            StringBuilder contentBuilder = new StringBuilder();
            contentBuilder.append(DC_HEAD);
            appendDCElement(contentBuilder, dc.getContributor(), DublinCoreConstants.DC_CONTRIBUTOR);
            appendDCElement(contentBuilder, dc.getCoverage(), DublinCoreConstants.DC_COVERAGE);
            appendDCElement(contentBuilder, dc.getCreator(), DublinCoreConstants.DC_CREATOR);
            appendDCElement(contentBuilder, dc.getDate(), DublinCoreConstants.DC_DATE);
            appendDCElement(contentBuilder, dc.getDescription(), DublinCoreConstants.DC_DESCRIPTION);
            appendDCElement(contentBuilder, dc.getFormat(), DublinCoreConstants.DC_FORMAT);
            appendDCElement(contentBuilder, dc.getIdentifier(), DublinCoreConstants.DC_IDENTIFIER);
            appendDCElement(contentBuilder, dc.getLanguage(), DublinCoreConstants.DC_LANGUAGE);
            appendDCElement(contentBuilder, dc.getPublisher(), DublinCoreConstants.DC_PUBLISHER);
            appendDCElement(contentBuilder, dc.getRelation(), DublinCoreConstants.DC_RELATION);
            appendDCElement(contentBuilder, dc.getRights(), DublinCoreConstants.DC_RIGHTS);
            appendDCElement(contentBuilder, dc.getSource(), DublinCoreConstants.DC_SOURCE);
            appendDCElement(contentBuilder, dc.getSubject(), DublinCoreConstants.DC_SUBJECT);
            appendDCElement(contentBuilder, dc.getTitle(), DublinCoreConstants.DC_TITLE);
            appendDCElement(contentBuilder, dc.getType(), DublinCoreConstants.DC_TYPE);
            contentBuilder.append(DC_TAIL);

            String content = contentBuilder.toString();
            return content;
        }
        return null;
    }

    /**
     * Create new mods part.
     * 
     * @param modsClient
     *        the modsClient
     * @return content the content of the new mods part
     */
    public static String createNewModsPart(ModsCollectionClient modsClient) {
        if (modsClient != null) {
            ModsCollection mods = BiblioModsUtils.toMods(modsClient);
            return BiblioModsUtils.toXML(mods);
        }
        return null;
    }

    /**
     * @return String[], The array of Strings which contains a working copy of
     *         FOXML and individual datastreams in this order: String[0] =
     *         FOXML, String[1] = FOXML without the last version of the changed
     *         datastreams, String[2] = DC datastream, String[3] = MODS
     *         datastream, String[4] = RELS-EXT datastream.
     */
    public static String[] createWorkingCopyFoxmlAndStreams(DigitalObjectDetail detail, boolean onlyFoxml)
            throws ActionException {

        String[] stringsWithXml = new String[] {null, null, null, null, null};
        Document[] documentsWithXml = new Document[] {null, null, null, null, null};

        try {
            documentsWithXml[0] =
                    FoxmlUtils.getFoxmlDocument(fedoraAccess.getFOXMLInputStream(detail.getUuid()));
        } catch (IOException e) {
            throw new ActionException();
        }

        if (detail.isLabelChanged()) {
            modifyLabel(detail, documentsWithXml[0]);
        }
        if (detail.isDcChanged()) {
            stringsWithXml[2] = createNewDublinCorePart(detail.getDc());
            modifyStream(documentsWithXml[0], DC.getValue(), stringsWithXml[2]);
            stringsWithXml[2] = Constants.XML_HEADER_WITH_BACKSLASHES + stringsWithXml[2];

        } else if (!onlyFoxml) {
            try {
                documentsWithXml[2] = fedoraAccess.getDC(detail.getUuid());
            } catch (IOException e) {
                throw new ActionException();
            }
        }

        if (detail.isModsChanged()) {
            stringsWithXml[3] = createNewModsPart(detail.getMods());
            modifyStream(documentsWithXml[0], BIBLIO_MODS.getValue(), stringsWithXml[3]);
            stringsWithXml[3] = Constants.XML_HEADER_WITH_BACKSLASHES + stringsWithXml[3];

        } else if (!onlyFoxml) {
            try {
                documentsWithXml[3] = fedoraAccess.getBiblioMods(detail.getUuid());
            } catch (IOException e) {
                throw new ActionException();
            }
        }

        if (detail.getAllItems() != null) {
            stringsWithXml[4] = createNewRealitonsPart(detail);
            modifyStream(documentsWithXml[0], RELS_EXT.getValue(), stringsWithXml[4]);
            stringsWithXml[4] = Constants.XML_HEADER_WITH_BACKSLASHES + stringsWithXml[4];

        } else if (!onlyFoxml) {
            try {
                documentsWithXml[4] = fedoraAccess.getRelsExt(detail.getUuid());
            } catch (IOException e) {
                throw new ActionException();
            }
        }

        if (detail.isOcrChanged()) {
            String newContent = detail.getOcr();
            modifyStream(documentsWithXml[0], TEXT_OCR.getValue(), newContent);
        } else if (detail.thereWasAnyOcr() && !onlyFoxml) {
            String oldContent = fedoraAccess.getOcr(detail.getUuid());
            addOldOcr(documentsWithXml[0], oldContent);
        }

        return createStringsArray(detail, onlyFoxml, stringsWithXml, documentsWithXml);
    }

    /**
     * @param document
     */
    public static String getStringFromDocument(Document document, boolean omitXmlDeclaration)
            throws TransformerException {
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();
        StringWriter buffer = new StringWriter();
        if (omitXmlDeclaration) {
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        }
        transformer.transform(new DOMSource(document), new StreamResult(buffer));
        return buffer.toString();
    }

    private static String[] createStringsArray(DigitalObjectDetail detail,
                                               boolean onlyFoxml,
                                               String[] stringsWithXml,
                                               Document[] documentsWithXml) {

        int creatingLength = (onlyFoxml ? 1 : stringsWithXml.length);

        for (int i = 0; i < creatingLength; i++) {
            if (stringsWithXml[i] == null && documentsWithXml[i] != null) {
                try {
                    stringsWithXml[i] = getStringFromDocument(documentsWithXml[i], false);;
                    if (i == 0 && !onlyFoxml) {
                        try {
                            documentsWithXml[1] =
                                    FoxmlUtils.getFoxmlDocument(new ByteArrayInputStream(stringsWithXml[i]
                                            .getBytes("UTF-8")));
                        } catch (IOException e) {
                            LOGGER.warn("IO failure", e);
                        }
                        if (detail.isDcChanged()) {
                            removeNextToLastVersion(documentsWithXml[1], DC.getValue());
                        }
                        if (detail.isModsChanged()) {
                            removeNextToLastVersion(documentsWithXml[1], BIBLIO_MODS.getValue());
                        }
                        if (detail.getAllItems() != null) {
                            removeNextToLastVersion(documentsWithXml[1], RELS_EXT.getValue());
                        }
                        if (detail.isOcrChanged()) {
                            removeNextToLastVersion(documentsWithXml[1], TEXT_OCR.getValue());
                        }
                    }
                } catch (TransformerException e) {
                    LOGGER.warn("Document transformer failure", e);
                }
            }
        }
        return stringsWithXml;
    }

    /**
     * @param oldContent
     * @param document
     */

    private static void addOldOcr(Document foxmlDocument, String oldContent) {
        try {
            String lastContLocXPath =
                    "//foxml:datastream[@ID=\'" + TEXT_OCR.getValue()
                            + "\']/foxml:datastreamVersion[last()]/foxml:contentLocation[last()]";

            XPathExpression all = makeNSAwareXpath().compile(lastContLocXPath);
            NodeList listOfstream = (NodeList) all.evaluate(foxmlDocument, XPathConstants.NODESET);
            Element lastContLocElement = null;
            if (listOfstream.getLength() != 0) {
                lastContLocElement = (Element) listOfstream.item(0);
            }
            Element localContElement = foxmlDocument.createElement("foxml:content");

            localContElement.setTextContent(oldContent);
            lastContLocElement.appendChild(localContElement);

        } catch (XPathExpressionException e) {
            LOGGER.warn("XPath failure", e);
        }
    }

    private static int getVersionNumber(String id) {
        String[] splitedId = id.split("\\.");
        return Integer.parseInt(splitedId[1]);
    }

    /**
     * @param detail
     * @param foxmlDocument
     */

    private static void modifyStream(Document foxmlDocument, String streamToModify, String newContent) {

        if (newContent != null) {
            try {
                String lastStreamXPath =
                        "//foxml:datastream[@ID=\'" + streamToModify + "\']/foxml:datastreamVersion[last()]";
                int versionNumber =
                        getVersionNumber(XMLUtils.getElement(foxmlDocument, lastStreamXPath)
                                .getAttribute("ID"));

                String streamXPath = "//foxml:datastream[@ID=\'" + streamToModify + "\']";
                Element parentOfStream = XMLUtils.getElement(foxmlDocument, streamXPath);

                Element versionElement = foxmlDocument.createElement("foxml:datastreamVersion");

                if (streamToModify.equals(RELS_EXT.getValue())) {
                    versionElement.setAttribute("LABEL", "RDF Statements about this object");
                    versionElement.setAttribute("FORMAT_URI", RELS_EXT_NAMESPACE_URI);
                    versionElement.setAttribute("MIMETYPE", "application/rdf+xml");

                } else {
                    versionElement.setAttribute("MIMETYPE", "text/xml");

                    if (streamToModify.equals(DC.getValue())) {
                        versionElement.setAttribute("LABEL", "Dublin Core Record for this object");
                        versionElement.setAttribute("FORMAT_URI", OAI_DC_NAMESPACE_URI);

                    } else if (streamToModify.equals(BIBLIO_MODS.getValue())) {
                        versionElement.setAttribute("LABEL", "BIBLIO_MODS description of current object");
                        versionElement.setAttribute("FORMAT_URI", BIBILO_MODS_URI);

                    } else if (streamToModify.equals(TEXT_OCR.getValue())) {
                        versionElement.setAttribute("LABEL", "");
                        Element contLocElement = foxmlDocument.createElement("foxml:contentLocation");
                        contLocElement.setAttribute("TYPE", "INTERNAL_ID");
                        contLocElement.setAttribute("REF", "LOCAL");
                        Element localContElement = foxmlDocument.createElement("foxml:content");
                        localContElement.setTextContent(newContent);
                        contLocElement.appendChild(localContElement);
                        versionElement.appendChild(contLocElement);
                    }
                }

                versionElement.setAttribute("ID", streamToModify + "." + (versionNumber + 1));
                versionElement.setAttribute("CREATED", "NOT YET");
                versionElement.setAttribute("SIZE", "0");

                Element contentElement = foxmlDocument.createElement("foxml:xmlContent");

                try {
                    InputStream is = new ByteArrayInputStream(newContent.getBytes("UTF-8"));
                    Document newStreamDocument = FoxmlUtils.getFoxmlDocument(is);
                    NodeList streamNodeList = newStreamDocument.getChildNodes();
                    for (int i = 0; i < streamNodeList.getLength(); i++) {
                        Node myNewNode = foxmlDocument.importNode(streamNodeList.item(i), true);
                        contentElement.appendChild(myNewNode);
                    }
                } catch (IOException e) {
                    System.err.println("IO fauilure" + e);
                }

                versionElement.appendChild(contentElement);
                parentOfStream.appendChild(versionElement);

            } catch (XPathExpressionException e) {
                LOGGER.warn("XPath failure", e);
            }
        }
    }

    private static void removeNextToLastVersion(Document foxmlDocument, String streamToModify) {

        String nextToLastStreamXPath =
                "/foxml:digitalObject//foxml:datastream[@ID=\'" + streamToModify
                        + "\']/foxml:datastreamVersion[position()=last()-1]";

        String streamXPath = "//foxml:datastream[@ID=\'" + streamToModify + "\']";

        try {

            removeElements(XMLUtils.getElement(foxmlDocument, streamXPath),
                           foxmlDocument,
                           nextToLastStreamXPath);
        } catch (XPathExpressionException e) {
            LOGGER.warn("XPath failure", e);
        }

    }

    /**
     * @param detail
     */

    private static void modifyLabel(DigitalObjectDetail detail, Document foxmlDocument) {
        String propertyLabelXPath = "//foxml:objectProperties/foxml:property[@NAME=\'" + LABEL_VALUE + "\']";

        try {
            XMLUtils.getElement(foxmlDocument, propertyLabelXPath).setAttribute("VALUE", detail.getLabel());
        } catch (XPathExpressionException e) {
            LOGGER.warn("XPath failure", e);
        }
    }

    /**
     * Gets the related.
     * 
     * @param uuid
     *        the uuid
     * @return the related
     */
    public static ArrayList<ArrayList<String>> getRelated(final String uuid) {
        List<RelationshipTuple> triplets = FedoraUtils.getSubjectPids(uuid);
        if (triplets == null) { // RI can be disabled
            return null;
        }
        ArrayList<ArrayList<String>> returnList = new ArrayList<ArrayList<String>>(triplets.size());
        for (RelationshipTuple triplet : triplets) {
            //            System.err.println(triplet.toString());
            ArrayList<String> relatedRecord = new ArrayList<String>(2);
            String subject = triplet.getSubject().substring((Constants.FEDORA_INFO_PREFIX).length());
            String predicate = null;
            if (triplet.getPredicate().startsWith(Constants.FEDORA_INFO_PREFIX)) {
                predicate = triplet.getPredicate().substring(Constants.FEDORA_INFO_PREFIX.length());
            } else {
                predicate =
                        triplet.getPredicate()
                                .substring(FedoraNamespaces.ONTOLOGY_RELATIONSHIP_NAMESPACE_URI.length());
            }

            relatedRecord.add(subject);
            relatedRecord.add(predicate);
            returnList.add(relatedRecord);
        }
        return returnList;
    }

    @SuppressWarnings("serial")
    public static ArrayList<ArrayList<String>> getAllChildren(String uuid) {
        List<RelationshipTuple> triplets = FedoraUtils.getObjectPids(uuid);
        ArrayList<ArrayList<String>> children = new ArrayList<ArrayList<String>>();

        if (triplets != null) {
            for (final RelationshipTuple triplet : triplets) {
                if (triplet.getObject().contains("uuid")
                        && triplet.getObject().contains(Constants.FEDORA_INFO_PREFIX)) {

                    final String childUuid =
                            triplet.getObject().substring((Constants.FEDORA_INFO_PREFIX).length());

                    if (!childUuid.contains("/")) {
                        children.add(new ArrayList<String>() {

                            {
                                add(childUuid);
                                add(triplet.getPredicate());
                            }
                        });
                    }
                }
            }
        }
        return children;
    }

    public static boolean putRelsExt(String uuid, String content, boolean versionable) {
        String url = null;
        try {
            url =
                    configuration.getFedoraHost() + "/objects/" + uuid + "/datastreams/RELS-EXT?versionable="
                            + versionable
                            + java.net.URLEncoder.encode("&mimeType=application/rdf+xml", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.warn("Encoding failure", e);
        }

        String usr = configuration.getFedoraLogin();
        String pass = configuration.getFedoraPassword();
        if (content != null) {
            RESTHelper.put(url, content, usr, pass, false);
            return true;
        }
        return false;
    }
}
