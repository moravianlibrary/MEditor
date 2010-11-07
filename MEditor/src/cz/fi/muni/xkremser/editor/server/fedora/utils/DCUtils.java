/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.fedora.utils;

import static cz.fi.muni.xkremser.editor.server.fedora.FedoraNamespaces.DC_NAMESPACE_URI;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.fi.muni.xkremser.editor.client.DublinCoreConstants;
import cz.fi.muni.xkremser.editor.shared.valueobj.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class DCUtils.
 */
public class DCUtils {

	/**
	 * Title from dc.
	 * 
	 * @param dc
	 *          the dc
	 * @return the string
	 */
	public static String titleFromDC(org.w3c.dom.Document dc) {
		Element elm = XMLUtils.findElement(dc.getDocumentElement(), "title", DC_NAMESPACE_URI);
		if (elm == null)
			elm = XMLUtils.findElement(dc.getDocumentElement(), "identifier", DC_NAMESPACE_URI);
		String title = elm.getTextContent();
		return title;
	}

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

	public static final String DC_CONTRIBUTOR = "contributor";
	public static final String DC_COVERAGE = "coverage";
	public static final String DC_CREATOR = "creator";
	public static final String DC_DATE = "date";
	public static final String DC_DESCRIPTION = "description";
	public static final String DC_FORMAT = "format";
	public static final String DC_IDENTIFIER = "identifier";

	public static List<String> contributorsFromDC(org.w3c.dom.Document dc) {
		return elementsFromDC(dc, DublinCoreConstants.DC_CONTRIBUTOR);
	}

	public static List<String> coveragesFromDC(org.w3c.dom.Document dc) {
		return elementsFromDC(dc, DublinCoreConstants.DC_COVERAGE);
	}

	public static List<String> creatorsFromDC(org.w3c.dom.Document dc) {
		return elementsFromDC(dc, DublinCoreConstants.DC_CREATOR);
	}

	public static List<String> datesFromDC(org.w3c.dom.Document dc) {
		return elementsFromDC(dc, DublinCoreConstants.DC_DATE);
	}

	public static List<String> descriptionsFromDC(org.w3c.dom.Document dc) {
		return elementsFromDC(dc, DublinCoreConstants.DC_DESCRIPTION);
	}

	public static List<String> formatsFromDC(org.w3c.dom.Document dc) {
		return elementsFromDC(dc, DublinCoreConstants.DC_FORMAT);
	}

	public static List<String> identifiersFromDC(org.w3c.dom.Document dc) {
		return elementsFromDC(dc, DublinCoreConstants.DC_IDENTIFIER);
	}

	public static List<String> languagesFromDC(org.w3c.dom.Document dc) {
		return elementsFromDC(dc, DublinCoreConstants.DC_LANGUAGE);
	}

	public static List<String> publishersFromDC(org.w3c.dom.Document dc) {
		return elementsFromDC(dc, DublinCoreConstants.DC_PUBLISHER);
	}

	public static List<String> relationsFromDC(org.w3c.dom.Document dc) {
		return elementsFromDC(dc, DublinCoreConstants.DC_RELATION);
	}

	public static List<String> rightsFromDC(org.w3c.dom.Document dc) {
		return elementsFromDC(dc, DublinCoreConstants.DC_RIGHTS);
	}

	public static List<String> sourcesFromDC(org.w3c.dom.Document dc) {
		return elementsFromDC(dc, DublinCoreConstants.DC_SOURCE);
	}

	public static List<String> subjectsFromDC(org.w3c.dom.Document dc) {
		return elementsFromDC(dc, DublinCoreConstants.DC_SUBJECT);
	}

	public static List<String> titlesFromDC(org.w3c.dom.Document dc) {
		return elementsFromDC(dc, DublinCoreConstants.DC_TITLE);
	}

	public static List<String> typesFromDC(org.w3c.dom.Document dc) {
		return elementsFromDC(dc, DublinCoreConstants.DC_TYPE);
	}

	public static DublinCore getDC(org.w3c.dom.Document doc) {
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

		Element documentElement = doc.getDocumentElement();
		NodeList childNodes = documentElement.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node item = childNodes.item(i);
			if (item.getNodeType() == Node.ELEMENT_NODE) {
				if (item.getLocalName().equals(DublinCoreConstants.DC_CONTRIBUTOR)) {
					contributor.add(item.getTextContent().trim());
				} else if (item.getLocalName().equals(DublinCoreConstants.DC_COVERAGE)) {
					coverage.add(item.getTextContent().trim());
				} else if (item.getLocalName().equals(DublinCoreConstants.DC_CREATOR)) {
					creator.add(item.getTextContent().trim());
				} else if (item.getLocalName().equals(DublinCoreConstants.DC_DATE)) {
					date.add(item.getTextContent().trim());
				} else if (item.getLocalName().equals(DublinCoreConstants.DC_DESCRIPTION)) {
					description.add(item.getTextContent().trim());
				} else if (item.getLocalName().equals(DublinCoreConstants.DC_FORMAT)) {
					format.add(item.getTextContent().trim());
				} else if (item.getLocalName().equals(DublinCoreConstants.DC_IDENTIFIER)) {
					identifier.add(item.getTextContent().trim());
				} else if (item.getLocalName().equals(DublinCoreConstants.DC_LANGUAGE)) {
					language.add(item.getTextContent().trim());
				} else if (item.getLocalName().equals(DublinCoreConstants.DC_PUBLISHER)) {
					publisher.add(item.getTextContent().trim());
				} else if (item.getLocalName().equals(DublinCoreConstants.DC_RELATION)) {
					relation.add(item.getTextContent().trim());
				} else if (item.getLocalName().equals(DublinCoreConstants.DC_RIGHTS)) {
					rights.add(item.getTextContent().trim());
				} else if (item.getLocalName().equals(DublinCoreConstants.DC_SOURCE)) {
					source.add(item.getTextContent().trim());
				} else if (item.getLocalName().equals(DublinCoreConstants.DC_SUBJECT)) {
					subject.add(item.getTextContent().trim());
				} else if (item.getLocalName().equals(DublinCoreConstants.DC_TITLE)) {
					title.add(item.getTextContent().trim());
				} else if (item.getLocalName().equals(DublinCoreConstants.DC_TYPE)) {
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
