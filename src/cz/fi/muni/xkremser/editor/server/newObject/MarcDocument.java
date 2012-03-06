/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.xkremser.editor.server.newObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.XPath;

/**
 * @author Jiri Kremser
 * @author Martin Rehanek (rehan@mzk.cz)
 */
public class MarcDocument {

    private static final Map<String, String> prefixNamespaceMap;
    private static final Map<String, XPath> xpathCache = new HashMap<String, XPath>();
    private static final String X_PATH_OAI_PREFIX = "/oai:OAI-PMH/oai:GetRecord/oai:record/oai:metadata";

    static {
        prefixNamespaceMap = new HashMap<String, String>();
        prefixNamespaceMap.put("tei", "http://www.tei-c.org/ns/1.0");
        prefixNamespaceMap.put("marc21", "http://www.loc.gov/MARC21/slim");
        prefixNamespaceMap.put("oai", Namespaces.oai.getURI());
    }
    private final Document doc;

    public MarcDocument(Document doc) {
        this.doc = doc;
    }

    public String findSysno() {
        String xpath = X_PATH_OAI_PREFIX + "/marc21:record/marc21:controlfield[@tag='001']";
        return findValue(xpath);
    }

    public String findBase() {
        return "mzk03";//TODO: opravit
    }

    public String find040a() {
        return findDatafieldAndSubfield("040", 'a');
    }

    private String findDatafieldAndSubfield(String datafield, char subfield) {
        String xpath =
                X_PATH_OAI_PREFIX + "/marc21:record/marc21:datafield[@tag='" + datafield
                        + "']/marc21:subfield[@code='" + subfield + "']";
        return findValue(xpath);
    }

    public List<String> find080a() {
        String xpath =
                X_PATH_OAI_PREFIX + "/marc21:record/marc21:datafield[@tag='080']/marc21:subfield[@code='a']";
        return findValues(xpath);
    }

    public String find650a() {
        return findDatafieldAndSubfield("650", 'a');
    }

    public String find260aCorrected() {
        String content = findDatafieldAndSubfield("260", 'a');
        if (content != null) {
            content = removePrefix(content, "[");
            content = removeSuffix(content, " :");
            content = removeSuffix(content, ":");
        }
        return content;
    }

    public String find260bCorrected() {
        String content = findDatafieldAndSubfield("260", 'b');
        if (content != null) {
            content = removeSuffix(content, ",");
            content = removeSuffix(content, "]");
        }
        return content;
    }

    private String removePrefix(String string, String prefix) {
        if (string.startsWith(prefix)) {
            return string.substring(prefix.length());
        } else {
            return string;
        }
    }

    private String removeSuffix(String string, String suffix) {
        if (string.endsWith(suffix)) {
            int length = string.length() - suffix.length();
            return string.substring(0, length);
        } else {
            return string;
        }
    }

    public String find260c() {
        return findDatafieldAndSubfield("260", 'c');
    }

    public Document getDocument() {
        return doc;
    }

    private String findValue(String xpathString) {
        return findValue(xpathString, doc);
    }

    private String findValue(String xpathString, Node node) {
        XPath xpath = getXpath(xpathString);
        xpath.setNamespaceURIs(prefixNamespaceMap);
        Node resultNode = xpath.selectSingleNode(node);
        if (resultNode != null) {
            return resultNode.getText();
        } else {
            return null;
        }
    }

    private List<String> findValues(String xpathString) {
        return findValues(xpathString, doc);
    }

    private List<String> findValues(String xpathString, Node node) {
        XPath xpath = getXpath(xpathString);
        xpath.setNamespaceURIs(prefixNamespaceMap);
        List<? extends Node> nodeList = xpath.selectNodes(node);
        List<String> result = new ArrayList<String>(nodeList.size());
        for (Node foundNode : nodeList) {
            result.add(foundNode.getText());
        }
        return result;
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
