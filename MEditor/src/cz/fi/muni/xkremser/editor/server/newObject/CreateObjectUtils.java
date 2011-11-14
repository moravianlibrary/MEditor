/*
 * Metadata Editor
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

package cz.fi.muni.xkremser.editor.server.newObject;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import cz.fi.muni.xkremser.editor.server.fedora.utils.Dom4jUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;

import cz.fi.muni.xkremser.editor.shared.rpc.NewDigitalObject;

/**
 * @author Jiri Kremser
 * @version 29.10.2011
 */
public class CreateObjectUtils {

    private static final Logger LOGGER = Logger.getLogger(CreateObjectUtils.class);

    private static String convertToFOXML(NewDigitalObject node, Document mods, Document dc) {
        FoxmlBuilder builder = FOXMLBuilderMapping.getBuilder(node.getModel());
        if (builder == null) {
            return null;
        }
        builder.setDcXmlContent(dc);
        builder.setModsXmlContent(mods);
        builder.setLabel(node.getName());
        builder.createDocument();
        return builder.getDocument();
    }

    public static List<String> convertAllTheStructureToFOXMLs(NewDigitalObject node) {
        List<String> retList = new ArrayList<String>();
        String modsString = FedoraUtils.createNewModsPart(node.getMods());
        String dcString = FedoraUtils.createNewDublinCorePart(node.getDc());
        Document mods = null, dc = null;
        try {
            mods = Dom4jUtils.loadDocument(modsString, true);
            dc = Dom4jUtils.loadDocument(dcString, true);
        } catch (DocumentException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        System.out.println(convertToFOXML(node, mods, dc));
        return null;
    }
}
