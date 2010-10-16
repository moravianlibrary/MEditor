package cz.fi.muni.xkremser.editor.fedora.utils;

import static cz.fi.muni.xkremser.editor.fedora.FedoraNamespaces.DC_NAMESPACE_URI;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DCUtils {

	public static String titleFromDC(org.w3c.dom.Document dc) {
		Element elm = XMLUtils.findElement(dc.getDocumentElement(), "title", DC_NAMESPACE_URI);
		if (elm == null)
			elm = XMLUtils.findElement(dc.getDocumentElement(), "identifier", DC_NAMESPACE_URI);
		String title = elm.getTextContent();
		return title;
	}

	public static String[] creatorsFromDC(org.w3c.dom.Document dc) {
		ArrayList<String> creator = new ArrayList<String>();
		Element documentElement = dc.getDocumentElement();
		NodeList childNodes = documentElement.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node item = childNodes.item(i);
			if (item.getNodeType() == Node.ELEMENT_NODE) {
				if (item.getLocalName().equals("creator")) {
					creator.add(item.getTextContent().trim());
				}
			}
		}
		return creator.toArray(new String[creator.size()]);
	}

}
