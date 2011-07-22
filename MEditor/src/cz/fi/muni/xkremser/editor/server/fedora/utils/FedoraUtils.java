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

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import com.google.inject.Inject;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.fedora.api.RelationshipTuple;

import cz.fi.muni.xkremser.editor.client.domain.FedoraRelationship;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;

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
                    RESTHelper.inputStream(command,
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
                    RESTHelper.inputStream(command,
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
                    RESTHelper.inputStream(command,
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
}
