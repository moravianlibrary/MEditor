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
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import com.uwyn.jhighlight.renderer.XhtmlRendererFactory;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;

import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.valueobj.metadata.Foxml;

import static cz.fi.muni.xkremser.editor.client.domain.FedoraNamespaces.FOXML_NAMESPACE_URI;

/**
 * The Class FoxmlUtils.
 */
public class FoxmlUtils {

    private static final String PROP_ELEMENT_NAME = "objectProperties";
    private static final String VALUE_ATRIBUTE = "VALUE";
    private static final String NAME_ATRIBUTE = "NAME";
    private static final String LABEL_VALUE = "info:fedora/fedora-system:def/model#label";
    private static final Logger LOGGER = Logger.getLogger(FoxmlUtils.class.getPackage().toString());

    /**
     * Title from Foxml.
     * 
     * @param foxml
     *        the foxml
     * @return the string
     */
    public static String getLabel(org.w3c.dom.Document foxml) {
        Element elm =
                XMLUtils.findElement(foxml.getDocumentElement(), PROP_ELEMENT_NAME, FOXML_NAMESPACE_URI);
        List<Element> elements = XMLUtils.getElements(elm);
        for (Element el : elements) {
            if (el.getAttribute(NAME_ATRIBUTE).equals(LABEL_VALUE)) {
                String nameValueTitle = el.getAttribute(VALUE_ATRIBUTE);
                return nameValueTitle;
            }
        }
        return null;
    }

    /**
     * Handle foxml.
     * 
     * @param uuid
     *        the uuid
     * @param onlyTitleAndUuid
     *        the only title and uuid
     * @return the Foxml
     */
    public static Foxml handleFoxml(String uuid, FedoraAccess fedoraAccess) {
        Foxml foxml = new Foxml();

        String stringFoxml = fedoraAccess.getFOXML(uuid);
        InputStream is = getInputStreamFromString(stringFoxml, uuid);

        try {
            foxml.setFoxml(handleFOXMLString(stringFoxml, uuid));
            Document foxmlDocument = getFoxmlDocument(is);
            foxml.setIdentifier(uuid);
            foxml.setLabel(FoxmlUtils.getLabel(foxmlDocument));

        } catch (IOException e) {
            LOGGER.error("Unable to get Foxml metadata for " + uuid + "[" + e.getMessage() + "]", e);
        }
        return foxml;
    }

    private static Document getFoxmlDocument(InputStream docStream) throws IOException {
        try {
            return XMLUtils.parseDocument(docStream, true);
        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        } catch (SAXException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        } finally {
            docStream.close();
        }
    }

    /**
     * Handle foxml.
     * 
     * @param uuid
     *        the uuid
     * @return the string
     */
    private static String handleFOXMLString(String returnString, String uuid) {
        String returnedString = returnString;
        String highlighted = null;
        try {
            highlighted =
                    XhtmlRendererFactory.getRenderer("xml").highlight("foxml",
                                                                      returnedString,
                                                                      "Windows-1250",
                                                                      true);
        } catch (IOException e) {
            LOGGER.error("Unable to get FOXML representation for " + uuid + "[" + e.getMessage() + "]", e);
            return returnedString;
        }
        return highlighted.substring(highlighted.indexOf('\n'));
    }

    private static InputStream getInputStreamFromString(String stringFoxml, String uuid) {
        try {
            return new ByteArrayInputStream(stringFoxml.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Unable to get InputStream of Foxml from String format for " + uuid + "["
                                 + e.getMessage() + "]",
                         e);
        }
        return null;
    }

    public static String encodeToURL(String string) {
        try {
            return java.net.URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Unable to encode string " + string, e);
            e.printStackTrace();
        }
        return null;
    }
}
