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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;

import org.apache.log4j.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import cz.fi.muni.xkremser.editor.client.CreateObjectException;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.config.EditorConfigurationImpl;
import cz.fi.muni.xkremser.editor.server.fedora.utils.Dom4jUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FoxmlUtils;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.domain.NamedGraphModel;
import cz.fi.muni.xkremser.editor.shared.rpc.NewDigitalObject;

/**
 * @author Jiri Kremser
 * @version 29.10.2011
 */
public class CreateObjectUtils {

    private static final Logger LOGGER = Logger.getLogger(CreateObjectUtils.class);

    private static String insertFOXML(NewDigitalObject node,
                                      Document mods,
                                      Document dc,
                                      String sysno,
                                      boolean first,
                                      EditorConfiguration config) throws CreateObjectException {
        return insertFOXML(node, mods, dc, Constants.MAX_NUMBER_OF_INGEST_ATTEMPTS, sysno, first, config);
    }

    private static String insertFOXML(NewDigitalObject node,
                                      Document mods,
                                      Document dc,
                                      int attempt,
                                      String sysno,
                                      boolean first,
                                      EditorConfiguration config) throws CreateObjectException {

        if (node.getModel() == DigitalObjectModel.PAGE) {
            controlFilesAndDirectories(sysno, node.getPath(), first, config);
            first = false;
        }

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
                    String uuid = insertFOXML(child, mods, dc, sysno, first, config);
                    child.setUuid(uuid);
                }
                relations.add(new RelsExtRelation(child.getUuid(), NamedGraphModel.getRelationship(node
                        .getModel(), child.getModel())));
            }
        }
        String newFilePath = null;
        if (node.getModel() == DigitalObjectModel.PAGE) {
            if (sysno == null) {
                String url = config.getImageServerUrl();
                if (!url.endsWith("/")) {
                    url += '/';
                }
                newFilePath = url + "unknown" + '/' + node.getUuid();
            } else {
                newFilePath = getSysnoPath(sysno, config) + node.getUuid();
            }
            builder.setNewFilePath(newFilePath);
        }

        builder.createDocument();

        String foxmlRepresentation = builder.getDocument(false);
        boolean success = ingest(foxmlRepresentation);

        if (success && node.getModel() == DigitalObjectModel.PAGE) {
            copyfile(newFilePath + ".jp2", node.getPath(), config);
        }

        if (!success) {
            insertFOXML(node, mods, dc, attempt - 1, sysno, first, config);
        }
        return node.getUuid();
    }

    private static boolean ingest(String foxml) {
        // logging
        System.out.println("\n\n\n\n\n\n\n\n\n...ingesting:" + foxml);

        return true;
    }

    /**
     * @param sysno
     * @param string
     * @throws CreateObjectException
     */
    private static void copyfile(String newFilePath, String path, EditorConfiguration config)
            throws CreateObjectException {

        File inputFile = new File(EditorConfigurationImpl.DEFAULT_IMAGES_LOCATION + path + ".jp2");
        File outputFile = new File(newFilePath);
        FileReader in;
        try {
            in = new FileReader(inputFile);

            FileWriter out = new FileWriter(outputFile);
            int c;

            while ((c = in.read()) != -1)
                out.write(c);

            in.close();
            out.close();
        } catch (IOException e) {
            throw new CreateObjectException(e.getMessage());
        }
    }

    private static String getSysnoPath(String sysno, EditorConfiguration config) {
        String url = config.getImageServerUrl();
        if (!url.endsWith("/")) {
            url += '/';
        }
        // TODO: stringbuffer
        return url + '/' + sysno.substring(0, 3) + '/' + sysno.substring(3, 6) + '/' + sysno.substring(6, 9)
                + '/';
    }

    private static void controlFilesAndDirectories(String sysno,
                                                   String path,
                                                   boolean first,
                                                   EditorConfiguration config) throws CreateObjectException {
        if (first) {
            File imagesDir;
            if (sysno != null && sysno.length() == 9) {
                imagesDir = new File(getSysnoPath(sysno, config));
            } else {
                imagesDir = new File(config.getImageServerUnknown());
            }

            if (!imagesDir.exists()) {
                boolean mkdirs = imagesDir.mkdirs();
                if (!mkdirs) {
                    LOGGER.error("cannot create directory '" + imagesDir.getAbsolutePath() + "'");
                    throw new CreateObjectException("cannot create directory '" + imagesDir.getAbsolutePath()
                            + "'");
                }
            } else {
                File testDir = new File(imagesDir.getAbsolutePath() + "testDir");
                boolean mkdirs = testDir.mkdirs();
                if (!mkdirs) {
                    LOGGER.error("cannot write into '" + imagesDir.getAbsolutePath() + "'");
                    throw new CreateObjectException("cannot write into '" + imagesDir.getAbsolutePath() + "'");
                } else {
                    testDir.delete();
                }
            }
        }

        File oldImagePath = new File(EditorConfigurationImpl.DEFAULT_IMAGES_LOCATION + path + ".jp2");
        if (!oldImagePath.exists()) {
            throw new CreateObjectException("cannot find the file '" + oldImagePath.getAbsolutePath() + "'");
        }

    }

    public static boolean insertAllTheStructureToFOXMLs(NewDigitalObject node, EditorConfiguration config)
            throws CreateObjectException {

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
        insertFOXML(node, mods, dc, node.getSysno(), true, config);
        return true;
    }
}
