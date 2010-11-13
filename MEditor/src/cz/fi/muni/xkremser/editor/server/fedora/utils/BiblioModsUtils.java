/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.fedora.utils;

import java.util.logging.Level;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.server.fedora.FedoraNamespaceContext;
import cz.fi.muni.xkremser.editor.server.mods.ModsCollection;

// TODO: Auto-generated Javadoc
/**
 * The Class BiblioModsUtils.
 */
public class BiblioModsUtils {

	/** The Constant LOGGER. */
	public static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(BiblioModsUtils.class.getName());

	/**
	 * Gets the page number.
	 * 
	 * @param doc
	 *          the doc
	 * @return the page number
	 */
	public static String getPageNumber(Document doc) {
		try {
			XPathFactory xpfactory = XPathFactory.newInstance();
			XPath xpath = xpfactory.newXPath();
			xpath.setNamespaceContext(new FedoraNamespaceContext());
			XPathExpression expr = xpath.compile("//mods:mods/mods:part/mods:detail[@type='pageNumber']/mods:number/text()");
			Object pageNumber = expr.evaluate(doc, XPathConstants.STRING);
			return (String) pageNumber;
		} catch (XPathExpressionException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			return null;
		} catch (DOMException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Gets the title.
	 * 
	 * @param doc
	 *          the doc
	 * @param model
	 *          the model
	 * @return the title
	 */
	public static String getTitle(Document doc, KrameriusModel model) {
		String title = titleFromBiblioMods(doc);
		if ((title == null) || (title.equals(""))) {
			switch (model) {
				case PERIODICALITEM:
					return PeriodicalItemUtils.getItemNumber(doc) + " (" + PeriodicalItemUtils.getDate(doc) + ")";
				case PERIODICALVOLUME:
					return PeriodicalItemUtils.getItemNumber(doc) + " (" + PeriodicalItemUtils.getDate(doc) + ")";
				default:
					throw new UnsupportedOperationException("'" + model + "'");
			}
		} else
			return title;
	}

	/**
	 * Title from biblio mods.
	 * 
	 * @param doc
	 *          the doc
	 * @return the string
	 */
	public static String titleFromBiblioMods(Document doc) {
		try {
			XPathFactory xpfactory = XPathFactory.newInstance();
			XPath xpath = xpfactory.newXPath();
			xpath.setNamespaceContext(new FedoraNamespaceContext());
			XPathExpression expr = xpath.compile("//mods:titleInfo/mods:title");
			Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
			if (node != null) {
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elm = (Element) node;
					return elm.getTextContent();
				} else
					return null;
			} else
				return null;
		} catch (XPathExpressionException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			return null;
		} catch (DOMException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}

	public static ModsCollection getMods(org.w3c.dom.Document doc) {
		ModsCollection collection = null;
		try {
			JAXBContext jc = JAXBContext.newInstance("cz.fi.muni.xkremser.editor.client.mods");
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			collection = (ModsCollection) unmarshaller.unmarshal(doc);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return collection;
	}

	public static void copy(ModsCollection mods, ModsCollectionClient modsClient) {
		// TODO Auto-generated method stub a naopak

	}
}
