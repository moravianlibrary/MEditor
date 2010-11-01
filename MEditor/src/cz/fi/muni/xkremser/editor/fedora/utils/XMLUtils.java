/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.fedora.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLUtils.
 */
public class XMLUtils {

	/**
	 * Parses the document.
	 *
	 * @param is the is
	 * @return the document
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the sAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Document parseDocument(InputStream is) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return builder.parse(is);
	}

	/**
	 * Parses the document.
	 *
	 * @param is the is
	 * @param namespaceaware the namespaceaware
	 * @return the document
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the sAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Document parseDocument(InputStream is, boolean namespaceaware) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(namespaceaware);
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(is);
	}

	/**
	 * Gets the elements.
	 *
	 * @param topElm the top elm
	 * @return the elements
	 */
	public static List<Element> getElements(Element topElm) {
		List<Element> retVals = new ArrayList<Element>();
		NodeList childNodes = topElm.getChildNodes();
		for (int i = 0, ll = childNodes.getLength(); i < ll; i++) {
			Node n = childNodes.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				retVals.add((Element) n);
			}
		}
		return retVals;
	}

	/**
	 * Namespaces are same.
	 *
	 * @param fNamespace the f namespace
	 * @param sNamespace the s namespace
	 * @return true, if successful
	 */
	private static boolean namespacesAreSame(String fNamespace, String sNamespace) {
		if ((fNamespace == null) && (sNamespace == null)) {
			return true;
		} else if (fNamespace != null) {
			return fNamespace.equals(sNamespace);
		} else
			return false;
	}

	/**
	 * Find element.
	 *
	 * @param topElm the top elm
	 * @param localName the local name
	 * @param namespace the namespace
	 * @return the element
	 */
	public static Element findElement(Element topElm, String localName, String namespace) {
		Stack<Element> stack = new Stack<Element>();
		stack.push(topElm);
		while (!stack.isEmpty()) {
			Element curElm = stack.pop();
			if ((curElm.getLocalName().equals(localName)) && (namespacesAreSame(curElm.getNamespaceURI(), namespace))) {
				return curElm;
			}
			NodeList childNodes = curElm.getChildNodes();
			for (int i = 0, ll = childNodes.getLength(); i < ll; i++) {
				Node item = childNodes.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					stack.push((Element) item);
				}
			}
		}
		return null;
	}

}
