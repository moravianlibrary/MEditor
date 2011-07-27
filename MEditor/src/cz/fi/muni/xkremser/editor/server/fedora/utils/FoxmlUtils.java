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

import java.util.List;

import org.w3c.dom.Element;

import static cz.fi.muni.xkremser.editor.client.domain.FedoraNamespaces.FOXML_NAMESPACE_URI;

// TODO: Auto-generated Javadoc
/**
 * The Class DCUtils.
 */
public class FoxmlUtils {

    private static final String PROP_ELEMENT_NAME = "objectProperties";
    private static final String VALUE_ATRIBUTE = "VALUE";
    private static final String NAME_ATRIBUTE = "NAME";
    private static final String LABEL_VALUE = "info:fedora/fedora-system:def/model#label";

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

}
