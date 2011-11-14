/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.xkremser.editor.server.newObject;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.XPath;

import cz.fi.muni.xkremser.editor.server.fedora.utils.Dom4jUtils;

/**
 * @author Martin Rehanek (rehan@mzk.cz)
 */
public class MetadataDocument {

    private static final Map<String, String> prefixNamespaceMap;
    private static final Map<String, XPath> xpathCache = new HashMap<String, XPath>();

    static {
        prefixNamespaceMap = new HashMap<String, String>();
        prefixNamespaceMap.put("tei", "http://www.tei-c.org/ns/1.0");
        prefixNamespaceMap.put("marc21", "http://www.loc.gov/MARC21/slim");
    }
    protected final Document doc;

    public MetadataDocument(File file)
            throws FileNotFoundException, DocumentException {
        doc = Dom4jUtils.loadDocumentIgnoreExternalDtd(file);
    }

    public MetadataDocument(Document doc) {
        this.doc = doc;
    }

    public Document getDocument() {
        return doc;
    }

    protected String findValue(String xpathString) {
        return findValue(xpathString, doc);
    }

    protected String findValue(String xpathString, Node node) {
        XPath xpath = getXpath(xpathString);
        xpath.setNamespaceURIs(prefixNamespaceMap);
        Node resultNode = xpath.selectSingleNode(node);
        if (resultNode != null) {
            return resultNode.getText();
        } else {
            return null;
        }
    }

    protected List<String> findValues(String xpathString) {
        return findValues(xpathString, doc);
    }

    protected List<String> findValues(String xpathString, Node node) {
        XPath xpath = getXpath(xpathString);
        xpath.setNamespaceURIs(prefixNamespaceMap);
        List<? extends Node> nodeList = xpath.selectNodes(node);
        List<String> result = new ArrayList<String>(nodeList.size());
        for (Node foundNode : nodeList) {
            result.add(foundNode.getText());
        }
        return result;
    }

    protected List<? extends Node> findNodes(String xpathString) {
        XPath xpath = getXpath(xpathString);
        xpath.setNamespaceURIs(prefixNamespaceMap);
        return xpath.selectNodes(doc);
    }

    private XPath getXpath(String xpathString) {
        XPath xpath = xpathCache.get(xpathString);
        if (xpath == null) {
            xpath = DocumentHelper.createXPath(xpathString);
            xpathCache.put(xpathString, xpath);
        }
        return xpath;
    }
}
