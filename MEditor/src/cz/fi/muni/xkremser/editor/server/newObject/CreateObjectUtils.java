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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.List;

import com.google.inject.Inject;

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
import cz.fi.muni.xkremser.editor.server.fedora.utils.RESTHelper;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.domain.NamedGraphModel;
import cz.fi.muni.xkremser.editor.shared.rpc.NewDigitalObject;

/**
 * @author Jiri Kremser
 * @version 29.10.2011
 */
public class CreateObjectUtils {

    private static final Logger LOGGER = Logger.getLogger(CreateObjectUtils.class);

    @Inject
    private static EditorConfiguration config;

    private static String insertFOXML(NewDigitalObject node, Document mods, Document dc, String sysno)
            throws CreateObjectException {
        return insertFOXML(node, mods, dc, Constants.MAX_NUMBER_OF_INGEST_ATTEMPTS, sysno);
    }

    private static String insertFOXML(NewDigitalObject node,
                                      Document mods,
                                      Document dc,
                                      int attempt,
                                      String sysno) throws CreateObjectException {
        if (attempt == 0) {
            throw new CreateObjectException("max number of attempts has been reached");
        }
        if (node.getExist()) {
            // do not create, but append only 
            return node.getUuid();
        }
        FoxmlBuilder builder = FOXMLBuilderMapping.getBuilder(node);
        if (builder == null) {
            throw new CreateObjectException("unknown type " + node.getModel());
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
                    String uuid = insertFOXML(child, mods, dc, sysno);
                    child.setUuid(uuid);
                }
                relations.add(new RelsExtRelation(child.getUuid(), NamedGraphModel.getRelationship(node
                        .getModel(), child.getModel())));
            }
        }
        String imageUrl = null;
        String newFilePath = null;
        boolean isPage = node.getModel() == DigitalObjectModel.PAGE;
        if (isPage) {
            String url = config.getImageServerUrl();
            if (!url.endsWith("/")) {
                url += '/';
            }
            if (!url.startsWith("http://")) {
                if (url.startsWith("https://")) {
                    url = url.substring(8);
                }
                url = "http://" + url;
            }
            if (sysno == null) {
                imageUrl = url + "meditor" + node.getUuid();
            } else {
                imageUrl = url + "mzk03/" + getSysnoPath(sysno) + node.getUuid();
            }
            builder.setImageUrl(imageUrl);

            String path = sysno == null ? config.getImageServerUnknown() : config.getImageServerKnown();
            if (!path.endsWith("/")) {
                path += '/';
            }
            newFilePath = path + getSysnoPath(sysno) + node.getUuid();
        }

        builder.createDocument();

        String foxmlRepresentation = builder.getDocument(false);
        boolean success = ingest(foxmlRepresentation, node.getName(), node.getUuid());

        if (isPage && success) {
            boolean copySuccess =
                    copyfile(EditorConfigurationImpl.DEFAULT_IMAGES_LOCATION + node.getPath()
                            + Constants.JPEG_2000_EXTENSION, newFilePath + ".Constants.JPEG_2000_EXTENSION");
            if (copySuccess && LOGGER.isInfoEnabled()) {
                LOGGER.info("image " + EditorConfigurationImpl.DEFAULT_IMAGES_LOCATION + node.getPath() + "."
                        + Constants.JPEG_2000_EXTENSION + "  was copied to  " + newFilePath + "."
                        + Constants.JPEG_2000_EXTENSION);
            }
        }

        if (!success) {
            insertFOXML(node, mods, dc, attempt - 1, sysno);
        }
        return node.getUuid();
    }

    private static boolean ingest(String foxml, String label, String uuid) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Ingesting the digital object with PID uuid:" + uuid + " and label " + label);
        }
        String login = config.getFedoraLogin();
        String password = config.getFedoraPassword();
        String url = config.getFedoraHost() + "/objects/new";
        boolean success = RESTHelper.post(url, foxml, login, password, false);
        if (LOGGER.isInfoEnabled() && success) {
            LOGGER.info("Object uuid:" + uuid + " [" + label + "] has been successfully ingested.");
        }
        if (!success) {
            LOGGER.error("Unable to ingest object uuid:" + uuid + " [" + label + "]");
        }
        return success;
    }

    /**
     * @param path
     *        from
     * @param path
     *        to
     * @throws CreateObjectException
     */
    private static boolean copyfile(String path, String newFilePath) throws CreateObjectException {

        File inputFile = new File(path);
        if (!inputFile.exists()) {
            LOGGER.error("file " + path + " does not exist");
            return false;
        }
        File outputFile = new File(newFilePath);

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(inputFile);
            out = new FileOutputStream(outputFile);
            byte[] buf = new byte[1024 * 128];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            return true;
        } catch (IOException e) {
            throw new CreateObjectException(e.getMessage(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    in = null;
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    out = null;
                }
            }
        }
    }

    private static String getSysnoPath(String sysno) {
        StringBuffer sb = new StringBuffer();
        sb.append(sysno.substring(0, 3)).append('/').append(sysno.substring(3, 6)).append('/')
                .append(sysno.substring(6, 9)).append('/');
        return sb.toString();
    }

    private static void checkAccessRightsAndCreateDirectories(String sysno) throws CreateObjectException {
        String unknown = config.getImageServerUnknown();
        String known = config.getImageServerKnown();
        String url = config.getImageServerUrl();
        if (unknown == null || "".equals(unknown) || known == null || "".equals(known) || url == null
                || "".equals(url)) {
            String errorMsg =
                    "Error, one of folloving compulsory options have not been set ["
                            + EditorConfiguration.ServerConstants.IMAGE_SERVER_KNOWN + " ,"
                            + EditorConfiguration.ServerConstants.IMAGE_SERVER_UNKNOWN + " ,"
                            + EditorConfiguration.ServerConstants.IMAGE_SERVER_URL + "]";
            LOGGER.error(errorMsg);
            throw new CreateObjectException(errorMsg);
        }

        File imagesDir =
                new File(sysno != null && sysno.length() == 9 ? config.getImageServerKnown() + '/'
                        + getSysnoPath(sysno) : config.getImageServerUnknown());
        if (!imagesDir.exists()) {
            boolean mkdirs = imagesDir.mkdirs();
            if (!mkdirs) {
                LOGGER.error("cannot create directory '" + imagesDir.getAbsolutePath() + "'");
                throw new CreateObjectException("cannot create directory '" + imagesDir.getAbsolutePath()
                        + "'");
            } else {
                if (!imagesDir.canRead() || !imagesDir.canWrite()) {
                    LOGGER.error("cannot write into '" + imagesDir.getAbsolutePath() + "'");
                    throw new CreateObjectException("cannot write into '" + imagesDir.getAbsolutePath() + "'");
                }
            }
        }
    }

    public static boolean insertAllTheStructureToFOXMLs(NewDigitalObject node) throws CreateObjectException {
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

        checkAccessRightsAndCreateDirectories(node.getSysno());
        insertFOXML(node, mods, dc, node.getSysno());
        return true;
    }
}
