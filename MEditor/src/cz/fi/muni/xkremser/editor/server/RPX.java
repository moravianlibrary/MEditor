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

package cz.fi.muni.xkremser.editor.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/**
 * The Class RPX. taken from janrain example (therfore no generics)
 */
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class RPX {

    /** The api key. */
    private final String apiKey;

    /** The base url. */
    private final String baseUrl;

    /**
     * Instantiates a new rPX.
     * 
     * @param apiKey
     *        the api key
     * @param baseUrl
     *        the base url
     */
    public RPX(String apiKey, String baseUrl) {
        while (baseUrl.endsWith("/"))
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    /**
     * Gets the api key.
     * 
     * @return the api key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Gets the base url.
     * 
     * @return the base url
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Auth info.
     * 
     * @param token
     *        the token
     * @return the element
     */
    public Element authInfo(String token) {
        Map query = new HashMap();
        query.put("token", token);
        return apiCall("auth_info", query);
    }

    /**
     * All mappings.
     * 
     * @return the hash map
     */
    public HashMap allMappings() {
        Element rsp = apiCall("all_mappings", null);
        Element mappings_node = (Element) rsp.getFirstChild();
        HashMap result = new HashMap();
        NodeList mappings = getNodeList("/rsp/mappings/mapping", rsp);
        for (int i = 0; i < mappings.getLength(); i++) {
            Element mapping = (Element) mappings.item(i);
            List identifiers = new ArrayList();
            NodeList rk_list = getNodeList("primaryKey", mapping);
            NodeList id_list = getNodeList("identifiers/identifier", mapping);
            String remote_key = ((Element) rk_list.item(0)).getTextContent();
            for (int j = 0; j < id_list.getLength(); j++) {
                Element ident = (Element) id_list.item(j);
                identifiers.add(ident.getTextContent());
            }
            result.put(remote_key, identifiers);
        }
        return result;
    }

    /**
     * Gets the node list.
     * 
     * @param xpath_expr
     *        the xpath_expr
     * @param root
     *        the root
     * @return the node list
     */
    private NodeList getNodeList(String xpath_expr, Element root) {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        try {
            return (NodeList) xpath.evaluate(xpath_expr, root, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            return null;
        }
    }

    /**
     * Mappings.
     * 
     * @param primaryKey
     *        the primary key
     * @return the list
     */
    public List mappings(Object primaryKey) {
        Map query = new HashMap();
        query.put("primaryKey", primaryKey);
        Element rsp = apiCall("mappings", query);
        Element oids = (Element) rsp.getFirstChild();
        List result = new ArrayList();
        NodeList nl = oids.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);
            result.add(e.getTextContent());
        }
        return result;
    }

    /**
     * Map.
     * 
     * @param identifier
     *        the identifier
     * @param primaryKey
     *        the primary key
     */
    public void map(String identifier, Object primaryKey) {
        Map query = new HashMap();
        query.put("identifier", identifier);
        query.put("primaryKey", primaryKey);
        apiCall("map", query);
    }

    /**
     * Unmap.
     * 
     * @param identifier
     *        the identifier
     * @param primaryKey
     *        the primary key
     */
    public void unmap(String identifier, Object primaryKey) {
        Map query = new HashMap();
        query.put("identifier", identifier);
        query.put("primaryKey", primaryKey);
        apiCall("unmap", query);
    }

    /**
     * Api call.
     * 
     * @param methodName
     *        the method name
     * @param partialQuery
     *        the partial query
     * @return the element
     */
    private Element apiCall(String methodName, Map partialQuery) {
        Map query = null;
        if (partialQuery == null) {
            query = new HashMap();
        } else {
            query = new HashMap(partialQuery);
        }
        query.put("format", "xml");
        query.put("apiKey", apiKey);

        StringBuffer sb = new StringBuffer();
        for (Iterator it = query.entrySet().iterator(); it.hasNext();) {
            if (sb.length() > 0) sb.append('&');
            try {
                Map.Entry e = (Map.Entry) it.next();
                sb.append(URLEncoder.encode(e.getKey().toString(), "UTF-8"));
                sb.append('=');
                sb.append(URLEncoder.encode(e.getValue().toString(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Unexpected encoding error", e);
            }
        }
        String data = sb.toString();
        try {
            URL url = new URL(baseUrl + "/api/v2/" + methodName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.connect();
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            osw.write(data);
            osw.close();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringElementContentWhitespace(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(conn.getInputStream());
            Element response = (Element) doc.getFirstChild();
            if (!response.getAttribute("stat").equals("ok")) {
                throw new RuntimeException("Unexpected API error");
            }
            return response;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unexpected URL error", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected IO error", e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Unexpected XML error", e);
        } catch (SAXException e) {
            throw new RuntimeException("Unexpected XML error", e);
        }
    }

}
