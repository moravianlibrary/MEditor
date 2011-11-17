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

import java.util.List;

import org.apache.log4j.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import cz.fi.muni.xkremser.editor.client.CreateObjectException;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.fedora.utils.Dom4jUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FoxmlUtils;

import cz.fi.muni.xkremser.editor.shared.domain.NamedGraphModel;
import cz.fi.muni.xkremser.editor.shared.rpc.NewDigitalObject;

/**
 * @author Jiri Kremser
 * @version 29.10.2011
 */
public class CreateObjectUtils {

    private static final Logger LOGGER = Logger.getLogger(CreateObjectUtils.class);

    private static String insertFOXML(NewDigitalObject node, Document mods, Document dc)
            throws CreateObjectException {
        return insertFOXML(node, mods, dc, Constants.MAX_NUMBER_OF_INGEST_ATTEMPTS);
    }

    private static String insertFOXML(NewDigitalObject node, Document mods, Document dc, int attempt)
            throws CreateObjectException {
        if (attempt == 0) {
            throw new CreateObjectException("max number of attempts has been reached");
        }
        if (node.getExist()) {
            // do not create, but append only 
            return node.getUuid();
        }
        FoxmlBuilder builder = FOXMLBuilderMapping.getBuilder(node);
        if (builder == null) {
            return null;
        }
        if (node.getUuid() == null || attempt != Constants.MAX_NUMBER_OF_INGEST_ATTEMPTS) {
            node.setUuid(FoxmlUtils.getRandomUuid());
        }
        builder.setUuid(node.getUuid());
        builder.setDcXmlContent(dc);
        builder.setModsXmlContent(mods);
        builder.setBundle(node.getBundle());
        builder.setLabel(node.getName());
        List<NewDigitalObject> childrenToAdd = node.getChildren();
        if (childrenToAdd != null && !childrenToAdd.isEmpty()) {
            List<RelsExtRelation> relations = builder.getChildren();
            for (NewDigitalObject child : childrenToAdd) {
                if (!child.getExist()) {
                    String uuid = insertFOXML(child, mods, dc);
                    child.setUuid(uuid);
                }
                relations.add(new RelsExtRelation(child.getUuid(), NamedGraphModel.getRelationship(node
                        .getModel(), child.getModel())));
            }
        }
        builder.createDocument();
        String foxmlRepresentation = builder.getDocument(false);
        boolean success = ingest(foxmlRepresentation);
        if (!success) {
            insertFOXML(node, mods, dc, attempt - 1);
        }
        return node.getUuid();
    }

    private static boolean ingest(String foxml) {
        // logging
        System.out.println("\n\n\n\n\n\n\n\n\n...ingesting:" + foxml);

        return true;
    }

    public static boolean insertAllTheStructureToFOXMLs(NewDigitalObject node) {

        String modsString = FedoraUtils.createNewModsPart(node.getBundle().getMods());
        String dcString = FedoraUtils.createNewDublinCorePart(node.getBundle().getDc());
        Document mods = null, dc = null;
        try {
            mods = Dom4jUtils.loadDocument(modsString, true);
            dc = Dom4jUtils.loadDocument(dcString, true);
        } catch (DocumentException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        try {
            insertFOXML(node, mods, dc);
        } catch (CreateObjectException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
