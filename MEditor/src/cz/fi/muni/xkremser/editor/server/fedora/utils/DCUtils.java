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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cz.fi.muni.xkremser.editor.client.DublinCoreConstants;

import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;

import static cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces.DC_NAMESPACE_URI;

// TODO: Auto-generated Javadoc
/**
 * The Class DCUtils.
 */
public class DCUtils {

    private static final String DC_RECORD = "dc-record";

    private static final String OAI_DC_RECORD = "oai_dc:dc";

    private static final Logger LOGGER = Logger.getLogger(DCUtils.class.getPackage().toString());

    /**
     * Title from dc.
     * 
     * @param dc
     *        the dc
     * @return the string
     */
    public static String titleFromDC(org.w3c.dom.Document dc) {
        Element elm = XMLUtils.findElement(dc.getDocumentElement(), "title", DC_NAMESPACE_URI);
        if (elm == null) elm = XMLUtils.findElement(dc.getDocumentElement(), "identifier", DC_NAMESPACE_URI);
        String title = elm.getTextContent();
        return title;
    }

    /**
     * Elements from dc.
     * 
     * @param dc
     *        the dc
     * @param elementName
     *        the element name
     * @return the list
     */
    private static List<String> elementsFromDC(org.w3c.dom.Document dc, String elementName) {
        List<String> elements = new ArrayList<String>();
        Element documentElement = dc.getDocumentElement();
        NodeList childNodes = documentElement.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                if (item.getLocalName().equals(elementName)) {
                    elements.add(item.getTextContent().trim());
                }
            }
        }
        return elements;
    }

    /** The Constant DC_CONTRIBUTOR. */
    public static final String DC_CONTRIBUTOR = "contributor";

    /** The Constant DC_COVERAGE. */
    public static final String DC_COVERAGE = "coverage";

    /** The Constant DC_CREATOR. */
    public static final String DC_CREATOR = "creator";

    /** The Constant DC_DATE. */
    public static final String DC_DATE = "date";

    /** The Constant DC_DESCRIPTION. */
    public static final String DC_DESCRIPTION = "description";

    /** The Constant DC_FORMAT. */
    public static final String DC_FORMAT = "format";

    /** The Constant DC_IDENTIFIER. */
    public static final String DC_IDENTIFIER = "identifier";

    /**
     * Contributors from dc.
     * 
     * @param dc
     *        the dc
     * @return the list
     */
    public static List<String> contributorsFromDC(org.w3c.dom.Document dc) {
        return elementsFromDC(dc, DublinCoreConstants.DC_CONTRIBUTOR);
    }

    /**
     * Coverages from dc.
     * 
     * @param dc
     *        the dc
     * @return the list
     */
    public static List<String> coveragesFromDC(org.w3c.dom.Document dc) {
        return elementsFromDC(dc, DublinCoreConstants.DC_COVERAGE);
    }

    /**
     * Creators from dc.
     * 
     * @param dc
     *        the dc
     * @return the list
     */
    public static List<String> creatorsFromDC(org.w3c.dom.Document dc) {
        return elementsFromDC(dc, DublinCoreConstants.DC_CREATOR);
    }

    /**
     * Dates from dc.
     * 
     * @param dc
     *        the dc
     * @return the list
     */
    public static List<String> datesFromDC(org.w3c.dom.Document dc) {
        return elementsFromDC(dc, DublinCoreConstants.DC_DATE);
    }

    /**
     * Descriptions from dc.
     * 
     * @param dc
     *        the dc
     * @return the list
     */
    public static List<String> descriptionsFromDC(org.w3c.dom.Document dc) {
        return elementsFromDC(dc, DublinCoreConstants.DC_DESCRIPTION);
    }

    /**
     * Formats from dc.
     * 
     * @param dc
     *        the dc
     * @return the list
     */
    public static List<String> formatsFromDC(org.w3c.dom.Document dc) {
        return elementsFromDC(dc, DublinCoreConstants.DC_FORMAT);
    }

    /**
     * Identifiers from dc.
     * 
     * @param dc
     *        the dc
     * @return the list
     */
    public static List<String> identifiersFromDC(org.w3c.dom.Document dc) {
        return elementsFromDC(dc, DublinCoreConstants.DC_IDENTIFIER);
    }

    /**
     * Languages from dc.
     * 
     * @param dc
     *        the dc
     * @return the list
     */
    public static List<String> languagesFromDC(org.w3c.dom.Document dc) {
        return elementsFromDC(dc, DublinCoreConstants.DC_LANGUAGE);
    }

    /**
     * Publishers from dc.
     * 
     * @param dc
     *        the dc
     * @return the list
     */
    public static List<String> publishersFromDC(org.w3c.dom.Document dc) {
        return elementsFromDC(dc, DublinCoreConstants.DC_PUBLISHER);
    }

    /**
     * Relations from dc.
     * 
     * @param dc
     *        the dc
     * @return the list
     */
    public static List<String> relationsFromDC(org.w3c.dom.Document dc) {
        return elementsFromDC(dc, DublinCoreConstants.DC_RELATION);
    }

    /**
     * Rights from dc.
     * 
     * @param dc
     *        the dc
     * @return the list
     */
    public static List<String> rightsFromDC(org.w3c.dom.Document dc) {
        return elementsFromDC(dc, DublinCoreConstants.DC_RIGHTS);
    }

    /**
     * Sources from dc.
     * 
     * @param dc
     *        the dc
     * @return the list
     */
    public static List<String> sourcesFromDC(org.w3c.dom.Document dc) {
        return elementsFromDC(dc, DublinCoreConstants.DC_SOURCE);
    }

    /**
     * Subjects from dc.
     * 
     * @param dc
     *        the dc
     * @return the list
     */
    public static List<String> subjectsFromDC(org.w3c.dom.Document dc) {
        return elementsFromDC(dc, DublinCoreConstants.DC_SUBJECT);
    }

    /**
     * Titles from dc.
     * 
     * @param dc
     *        the dc
     * @return the list
     */
    public static List<String> titlesFromDC(org.w3c.dom.Document dc) {
        return elementsFromDC(dc, DublinCoreConstants.DC_TITLE);
    }

    /**
     * Types from dc.
     * 
     * @param dc
     *        the dc
     * @return the list
     */
    public static List<String> typesFromDC(org.w3c.dom.Document dc) {
        return elementsFromDC(dc, DublinCoreConstants.DC_TYPE);
    }

    public static DublinCore getDC(String xml) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOGGER.warn("Unable to parse xml document", e);
        }
        org.w3c.dom.Document doc = null;
        try {
            if (db != null) {
                InputSource is = new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8")));
                doc = db.parse(is);
            }
        } catch (SAXException e) {
            LOGGER.warn("Unable to parse xml document", e);
        } catch (IOException e) {
            LOGGER.warn("Unable to parse xml document", e);
        }

        if (doc != null) {
            Element root = doc.getDocumentElement();
            if (OAI_DC_RECORD.equals(root.getNodeName())) {
                return getDC(root, true);
            }
            NodeList childNodes = root.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (item != null
                        && (DC_RECORD.equals(item.getNodeName()) || OAI_DC_RECORD.equals(item.getNodeName()))) {
                    return getDC((Element) item, true);
                }
            }
            return null;
        } else
            return null;
    }

    public static DublinCore getDC(org.w3c.dom.Document doc) {
        return getDC(doc.getDocumentElement(), false);
    }

    /**
     * Gets the dC.
     * 
     * @param doc
     *        the doc
     * @return the dC
     */
    private static DublinCore getDC(Element root, boolean nodeName) {
        DublinCore dc = new DublinCore();
        List<String> contributor = new ArrayList<String>();
        List<String> coverage = new ArrayList<String>();
        List<String> creator = new ArrayList<String>();
        List<String> date = new ArrayList<String>();
        List<String> description = new ArrayList<String>();
        List<String> format = new ArrayList<String>();
        List<String> language = new ArrayList<String>();
        List<String> identifier = new ArrayList<String>();
        List<String> publisher = new ArrayList<String>();
        List<String> relation = new ArrayList<String>();
        List<String> rights = new ArrayList<String>();
        List<String> source = new ArrayList<String>();
        List<String> subject = new ArrayList<String>();
        List<String> title = new ArrayList<String>();
        List<String> type = new ArrayList<String>();

        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);

            if (item.getNodeType() == Node.ELEMENT_NODE
                    && ((!nodeName && item.getLocalName() != null) || (nodeName && item.getNodeName() != null))) {
                String name = nodeName ? item.getNodeName() : item.getLocalName();
                if (name.contains(":")) {
                    name = name.substring(name.lastIndexOf(':') + 1);
                }
                if (name.equals(DublinCoreConstants.DC_CONTRIBUTOR)) {
                    contributor.add(item.getTextContent().trim());
                } else if (name.equals(DublinCoreConstants.DC_COVERAGE)) {
                    coverage.add(item.getTextContent().trim());
                } else if (name.equals(DublinCoreConstants.DC_CREATOR)) {
                    creator.add(item.getTextContent().trim());
                } else if (name.equals(DublinCoreConstants.DC_DATE)) {
                    date.add(item.getTextContent().trim());
                } else if (name.equals(DublinCoreConstants.DC_DESCRIPTION)) {
                    description.add(item.getTextContent().trim());
                } else if (name.equals(DublinCoreConstants.DC_FORMAT)) {
                    format.add(item.getTextContent().trim());
                } else if (name.equals(DublinCoreConstants.DC_IDENTIFIER)) {
                    identifier.add(item.getTextContent().trim());
                } else if (name.equals(DublinCoreConstants.DC_LANGUAGE)) {
                    language.add(item.getTextContent().trim());
                } else if (name.equals(DublinCoreConstants.DC_PUBLISHER)) {
                    publisher.add(item.getTextContent().trim());
                } else if (name.equals(DublinCoreConstants.DC_RELATION)) {
                    relation.add(item.getTextContent().trim());
                } else if (name.equals(DublinCoreConstants.DC_RIGHTS)) {
                    rights.add(item.getTextContent().trim());
                } else if (name.equals(DublinCoreConstants.DC_SOURCE)) {
                    source.add(item.getTextContent().trim());
                } else if (name.equals(DublinCoreConstants.DC_SUBJECT)) {
                    subject.add(item.getTextContent().trim());
                } else if (name.equals(DublinCoreConstants.DC_TITLE)) {
                    title.add(item.getTextContent().trim());
                } else if (name.equals(DublinCoreConstants.DC_TYPE)) {
                    type.add(item.getTextContent().trim());
                }
            }
        }
        dc.setContributor(contributor);
        dc.setCoverage(coverage);
        dc.setCreator(creator);
        dc.setDate(date);
        dc.setDescription(description);
        dc.setFormat(format);
        dc.setIdentifier(identifier);
        dc.setLanguage(language);
        dc.setPublisher(publisher);
        dc.setRelation(relation);
        dc.setRights(rights);
        dc.setSource(source);
        dc.setSubject(subject);
        dc.setTitle(title);
        dc.setType(type);

        return dc;
    }
}
