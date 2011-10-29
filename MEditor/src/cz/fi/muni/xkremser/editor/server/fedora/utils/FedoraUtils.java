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
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.namespace.NamespaceContext;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.fedora.api.RelationshipTuple;

import cz.fi.muni.xkremser.editor.client.DublinCoreConstants;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.mods.ModsCollection;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces;
import cz.fi.muni.xkremser.editor.shared.domain.FedoraRelationship;
import cz.fi.muni.xkremser.editor.shared.domain.NamedGraphModel;
import cz.fi.muni.xkremser.editor.shared.rpc.DigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;

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
        List<RelationshipTuple> retval = new ArrayList<RelationshipTuple>();
        String command =
                configuration.getFedoraHost()
                        + "/risearch?type=triples&lang=spo&format=N-Triples&query=*%20*%20%3Cinfo:fedora/"
                        + objectPid + "%3E";
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
     */
    public static void removeElements(Element parent, Document doc, XPathExpression expr) {
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
                    removeElements(parent, relsExt, makeNSAwareXpath().compile(xPathString));
                }
                modelId++;
            }
            if (!changed) return null;

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            try {
                transformer = transFactory.newTransformer();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            }
            StringWriter buffer = new StringWriter();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            try {
                transformer.transform(new DOMSource(relsExt), new StreamResult(buffer));
            } catch (TransformerException e) {
                e.printStackTrace();
            }

            String str = buffer.toString();
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
            ModsCollection collapsedMods = ServerUtils.collapseStructure(mods);
            String content = BiblioModsUtils.toXML(collapsedMods);

            return content;
        }
        return null;
    }
}
