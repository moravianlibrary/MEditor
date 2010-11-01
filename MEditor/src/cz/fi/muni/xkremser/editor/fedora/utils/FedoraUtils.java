/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.xkremser.editor.fedora.utils;

/**
 *
 * @author incad
 */
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.fedora.api.RelationshipTuple;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.inject.Inject;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;
import cz.fi.muni.xkremser.editor.fedora.RDFModels;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class FedoraUtils.
 */
public class FedoraUtils {

	/** The Constant LOGGER. */
	public static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(FedoraUtils.class.getName());

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
	 * @param pid the pid
	 * @param relation the relation
	 * @return the rdf pids
	 */
	public static ArrayList<String> getRdfPids(String pid, String relation) {
		ArrayList<String> pids = new ArrayList<String>();
		try {

			String command = configuration.getFedoraHost() + "/get/" + pid + "/" + RELS_EXT_STREAM;
			InputStream is = RESTHelper.inputStream(command, configuration.getFedoraLogin(), configuration.getFedoraPassword());
			Document contentDom = XMLUtils.parseDocument(is);
			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			String xPathStr = "/RDF/Description/" + relation;
			XPathExpression expr = xpath.compile(xPathStr);
			NodeList nodes = (NodeList) expr.evaluate(contentDom, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				Node childnode = nodes.item(i);
				if (!childnode.getNodeName().contains("hasModel")) {
					pids.add(childnode.getNodeName() + " " + childnode.getAttributes().getNamedItem("rdf:resource").getNodeValue().split("/")[1]);
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			// ??
			// e.printStackTrace();
			// pids.add(e.toString());
		}
		return pids;
	}

	// http://localhost:8080/fedora/risearch?type=triples&lang=spo&format=N-Triples&limit=20&query=*%20*%20%3Cinfo:fedora/uuid:4a8a8630-af36-11dd-ae9c-000d606f5dc6%3E
	// http://localhost:8080/fedora/risearch?type=triples&lang=spo&format=N-Triples&query=*%20*%20*

	/**
	 * Gets the subject pids.
	 *
	 * @param objectPid the object pid
	 * @return the subject pids
	 */
	public static List<RelationshipTuple> getSubjectPids(String objectPid) {
		List<RelationshipTuple> retval = new ArrayList<RelationshipTuple>();
		String command = configuration.getFedoraHost() + "/risearch?type=triples&lang=spo&format=N-Triples&query=*%20*%20%3Cinfo:fedora/" + objectPid + "%3E";
		try {
			String result = IOUtils.readAsString(RESTHelper.inputStream(command, configuration.getFedoraLogin(), configuration.getFedoraPassword()),
					Charset.forName("UTF-8"), true);
			String[] lines = result.split("\n");
			for (String line : lines) {
				String[] tokens = line.split(" ");
				if (tokens.length < 3)
					continue;
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
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return retval;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		getSubjectPids("uuid:4a8a8630-af36-11dd-ae9c-000d606f5dc6");
	}

	/**
	 * Fill first page pid.
	 *
	 * @param pids the pids
	 * @param models the models
	 * @return true, if successful
	 */
	public static boolean fillFirstPagePid(ArrayList<String> pids, ArrayList<String> models) {

		String pid = pids.get(pids.size() - 1);
		try {
			String command = configuration.getFedoraHost() + "/get/uuid:" + pid + "/RELS-EXT";
			InputStream is = RESTHelper.inputStream(command, configuration.getFedoraLogin(), configuration.getFedoraPassword());
			Document contentDom = XMLUtils.parseDocument(is);
			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			XPathExpression expr = xpath.compile("/RDF/Description/*");
			NodeList nodes = (NodeList) expr.evaluate(contentDom, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				Node childnode = nodes.item(i);
				String nodeName = childnode.getNodeName();
				if (nodeName.contains("hasPage") || nodeName.contains("isOnPage")) {
					if (childnode.getAttributes().getNamedItem("rdf:resource").getNodeValue().contains("uuid:")) {
						pids.add(childnode.getAttributes().getNamedItem("rdf:resource").getNodeValue().split("uuid:")[1]);
					} else {
						// obcas import neni v poradku a chybi uuid:. zustaneme u
						// info:fedora/
						pids.add(childnode.getAttributes().getNamedItem("rdf:resource").getNodeValue().split("info:fedora/")[1]);
					}
					models.add("page");
					return true;
				} else if (nodeName.contains("hasItem") || nodeName.contains("hasVolume") || nodeName.contains("hasUnit")) {
					if (childnode.getAttributes().getNamedItem("rdf:resource").getNodeValue().contains("uuid:")) {
						pids.add(childnode.getAttributes().getNamedItem("rdf:resource").getNodeValue().split("uuid:")[1]);
					} else {
						// obcas import neni v poradku a chybi uuid:. zustaneme u
						// info:fedora/
						pids.add(childnode.getAttributes().getNamedItem("rdf:resource").getNodeValue().split("info:fedora/")[1]);
					}
					models.add(KrameriusModel.toString(RDFModels.convertRDFToModel(nodeName)));
					return FedoraUtils.fillFirstPagePid(pids, models);
				}
			}

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return false;
	}

	/**
	 * Find first page pid.
	 *
	 * @param pid the pid
	 * @return the string
	 */
	public static String findFirstPagePid(String pid) {

		ArrayList<String> pids = new ArrayList<String>();
		try {
			String command = configuration.getFedoraHost() + "/get/" + pid + "/RELS-EXT";
			InputStream is = RESTHelper.inputStream(command, configuration.getFedoraLogin(), configuration.getFedoraPassword());
			Document contentDom = XMLUtils.parseDocument(is);
			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			XPathExpression expr = xpath.compile("/RDF/Description/*");
			NodeList nodes = (NodeList) expr.evaluate(contentDom, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				Node childnode = nodes.item(i);
				String nodeName = childnode.getNodeName();
				if (nodeName.contains("hasPage")) {
					return childnode.getAttributes().getNamedItem("rdf:resource").getNodeValue().split("uuid:")[1];
				} else if (!nodeName.contains("hasModel") && childnode.hasAttributes() && childnode.getAttributes().getNamedItem("rdf:resource") != null) {

					pids.add(childnode.getAttributes().getNamedItem("rdf:resource").getNodeValue().split("/")[1]);
				}
			}
			for (String relpid : pids) {
				return FedoraUtils.findFirstPagePid(relpid);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Vraci url na stream s DJVU.
	 *
	 * @param uuid objektu
	 * @return the dj vu image
	 */
	public static String getDjVuImage(String uuid) {
		String imagePath = configuration.getFedoraHost() + "/get/uuid:" + uuid + "/" + IMG_FULL_STREAM;
		return imagePath;
	}

	/**
	 * Vraci url na stream THUMB.
	 *
	 * @param uuid the uuid
	 * @return the thumbnail from fedora
	 */
	public static String getThumbnailFromFedora(String uuid) {
		String imagePath = configuration.getFedoraHost() + "/get/uuid:" + uuid + "/" + IMG_THUMB_STREAM;
		return imagePath;
	}

	/**
	 * Gets the fedora datastreams list.
	 *
	 * @param uuid the uuid
	 * @return the fedora datastreams list
	 */
	public static String getFedoraDatastreamsList(String uuid) {
		String datastreamsListPath = configuration.getFedoraHost() + "/objects/uuid:" + uuid + "/datastreams?format=xml";
		return datastreamsListPath;
	}
}
