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

package cz.mzk.editor.server.newObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.inject.name.Named;

import org.apache.log4j.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.DOMReader;

import cz.mzk.editor.client.CreateObjectException;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.DATASTREAM_ID;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.DigitalObjectDAO;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.fedora.FedoraAccess;
import cz.mzk.editor.server.fedora.utils.Dom4jUtils;
import cz.mzk.editor.server.fedora.utils.FedoraUtils;
import cz.mzk.editor.server.fedora.utils.FoxmlUtils;
import cz.mzk.editor.server.util.IOUtils;
import cz.mzk.editor.server.util.RESTHelper;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.domain.NamedGraphModel;
import cz.mzk.editor.shared.rpc.NewDigitalObject;

/**
 * @author Jiri Kremser
 * @version 29.10.2011
 */
public class CreateObject {

    public final Logger LOGGER = Logger.getLogger(CreateObject.class);

    /** The fedora access. */
    private final FedoraAccess fedoraAccess;

    /** The config. */
    private final EditorConfiguration config;

    /** The dao utils. */
    private final DigitalObjectDAO digitalObjectDAO;

    private String topLevelUuid = null;
    private final String inputDirPath;
    private final Map<String, String> processedPages;
    private String sysno;
    private String base;
    private final List<String> ingestedObjects;

    @Inject
    public CreateObject(String inputDirPath,
                        final EditorConfiguration config,
                        final DigitalObjectDAO digitalObjectDAO,
                        final @Named("securedFedoraAccess") FedoraAccess fedoraAccess) {
        this.inputDirPath = inputDirPath;
        this.processedPages = new HashMap<String, String>();
        this.ingestedObjects = new ArrayList<String>();
        this.fedoraAccess = fedoraAccess;
        this.config = config;
        this.digitalObjectDAO = digitalObjectDAO;
    }

    public boolean insertAllTheStructureToFOXMLs(NewDigitalObject node) throws CreateObjectException {
        String modsString = FedoraUtils.createNewModsPart(node.getBundle().getMods());
        String dcString = FedoraUtils.createNewDublinCorePart(node.getBundle().getDc());
        Document mods = null, dc = null;
        try {
            mods = Dom4jUtils.loadDocument(modsString, true);
            dc = Dom4jUtils.loadDocument(dcString, true);
        } catch (DocumentException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return false;
        }

        sysno = node.getSysno();
        base = node.getBase();
        if (node.getExist() && node.getUuid() != null) {
            try {
                digitalObjectDAO.insertNewDigitalObject(node.getUuid(),
                                                        node.getModel().getValue(),
                                                        node.getName(),
                                                        inputDirPath,
                                                        node.getUuid());
            } catch (DatabaseException e) {
                LOGGER.error("DB ERROR!!!: " + e.getMessage() + ": " + e);
                e.printStackTrace();
            }
        }

        checkAccessRightsAndCreateDirectories(sysno, base);
        insertFOXML(node, mods, dc);
        return true;
    }

    private String insertFOXML(NewDigitalObject node, Document mods, Document dc)
            throws CreateObjectException {
        return insertFOXML(node, mods, dc, Constants.MAX_NUMBER_OF_INGEST_ATTEMPTS);
    }

    private String insertFOXML(NewDigitalObject node, Document mods, Document dc, int attempt)
            throws CreateObjectException {
        if (attempt == 0) {
            throw new CreateObjectException("max number of attempts has been reached");
        }

        boolean isPdf =
                node.getModel().getTopLevelType() != null
                        && (node.getChildren() == null || node.getChildren().size() == 0)
                        && node.getPath() != null;

        if (isPdf && attempt == Constants.MAX_NUMBER_OF_INGEST_ATTEMPTS) {
            PDDocument document = null;
            try {
                document =
                        PDDocument.load(new File(config.getImagesPath() + File.separator + node.getPath()
                                + Constants.PDF_EXTENSION));
                int numberOfPages = document.getNumberOfPages();
                if (node.getPageIndex() > numberOfPages - 1)
                    throw new CreateObjectException("The number of page: " + node.getPageIndex()
                            + " to be used for thumbnail is bigger than count of pages in the file: "
                            + numberOfPages);

            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
                throw new CreateObjectException("Unable to read the pdf file: " + config.getImagesPath()
                        + File.separator + node.getPath() + Constants.PDF_EXTENSION);
            } finally {
                if (document != null)
                    try {
                        document.close();
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                        e.printStackTrace();
                        throw new CreateObjectException("Unable to close the pdf file: "
                                + config.getImagesPath() + File.separator + node.getPath()
                                + Constants.PDF_EXTENSION);
                    }
            }
        }

        if (processedPages.containsKey(node.getPath())) {
            node.setExist(true);
            node.setUuid(processedPages.get(node.getPath()));
        }
        if (node.getExist()) {
            // do not create, but append only 
            List<NewDigitalObject> childrenToAdd = node.getChildren();
            if (childrenToAdd != null && !childrenToAdd.isEmpty()) {
                for (NewDigitalObject child : childrenToAdd) {
                    if (!child.getExist()) {
                        String uuid = insertFOXML(child, mods, dc);
                        child.setUuid(uuid);
                        append(node, child);
                    }
                }
            }
            return node.getUuid();
        }
        FoxmlBuilder builder = FOXMLBuilderMapping.getBuilder(node);
        if (builder == null) {
            throw new CreateObjectException("unknown type " + node.getModel());
        }

        if (node.getUuid() == null || attempt != Constants.MAX_NUMBER_OF_INGEST_ATTEMPTS) {

            node.setUuid(FoxmlUtils.getRandomUuid());

            if (topLevelUuid == null) {
                topLevelUuid = node.getUuid();
                try {
                    digitalObjectDAO.insertNewDigitalObject(node.getUuid(),
                                                            node.getModel().getValue(),
                                                            node.getName(),
                                                            inputDirPath,
                                                            node.getUuid());
                } catch (DatabaseException e) {
                    LOGGER.error("DB ERROR!!!: " + e.getMessage() + ": " + e);
                    e.printStackTrace();
                }
            }
        }
        boolean isPage = node.getModel() == DigitalObjectModel.PAGE;

        builder.setSignature(node.getSignature());
        builder.setBase(base);
        builder.setUuid(node.getUuid());
        builder.setDcXmlContent(dc);
        builder.setModsXmlContent(mods);
        builder.setBundle(node.getBundle());
        builder.setType(node.getType());
        builder.setPolicy(node.getVisible() ? Policy.PUBLIC : Policy.PRIVATE);
        builder.setDateOrIntPartName(node.getDateOrIntPartName());
        builder.setNoteOrIntSubtitle(node.getNoteOrIntSubtitle());
        if (!isPage) {
            builder.setPartNumber(node.getPartNumberOrAlto());
            builder.setAditionalInfo(node.getAditionalInfoOrOcr());
        }
        if (node.getModel() == DigitalObjectModel.PAGE) {
            builder.setPageIndex(node.getPageIndex());
        }

        List<NewDigitalObject> childrenToAdd = node.getChildren();
        if (childrenToAdd != null && !childrenToAdd.isEmpty()) {
            List<RelsExtRelation> relations = builder.getChildren();
            for (NewDigitalObject child : childrenToAdd) {
                if (!child.getExist()) {
                    String uuid = insertFOXML(child, mods, dc);
                    child.setUuid(uuid);
                }
                relations.add(new RelsExtRelation(child.getUuid(), NamedGraphModel.getRelationship(node
                        .getModel(), child.getModel()), child.getName()));
            }
        }
        boolean internal = config.getImageServerInternal();
        String imageUrl = null;
        String newFilePath = null;

        if (isPage) {
            String url = config.getImageServerUrl();
            url = addSlash(url);
            if (!url.startsWith("http://")) {
                if (url.startsWith("https://")) {
                    url = url.substring(8);
                }
                url = "http://" + url;
            }
            if (!isSysno(sysno)) {
                imageUrl =
                        url + "meditor" + getPathFromNonSysno(sysno)
                                + (internal ? node.getPath() : node.getUuid());
                if (!internal) {
                    newFilePath =
                            addSlash(config.getImageServerUnknown()) + getPathFromNonSysno(sysno)
                                    + node.getUuid();
                }
            } else {
                String basePath = "";
                if (base != null && !"".equals(base)) {
                    basePath = base.toLowerCase() + "/";
                }
                imageUrl =
                        url + basePath + getSysnoPath(sysno) + (internal ? node.getPath() : node.getUuid());
                if (!internal) {
                    newFilePath =
                            addSlash(config.getImageServerKnown()) + basePath + getSysnoPath(sysno)
                                    + node.getUuid();
                }
            }
            builder.setImageUrl(imageUrl);
        }

        builder.createDocument();

        String foxmlRepresentation = builder.getDocument(false);
        boolean success =
                IngestUtils.ingest(foxmlRepresentation, node.getName(), node.getUuid(), node.getModel()
                        .toString(), topLevelUuid, inputDirPath);

        if (success) ingestedObjects.add(node.getUuid());

        if (isPage && success) {
            if (!internal) {
                // TODO: StringBuffer
                boolean copySuccess;
                try {
                    copySuccess =
                            internal ? true : IOUtils.copyFile(config.getImagesPath() + node.getPath()
                                    + Constants.JPEG_2000_EXTENSION, newFilePath
                                    + Constants.JPEG_2000_EXTENSION);

                    if (copySuccess && LOGGER.isInfoEnabled()) {
                        LOGGER.info("image " + config.getImagesPath() + node.getPath() + "."
                                + Constants.JPEG_2000_EXTENSION + "  was copied to  " + newFilePath
                                + Constants.JPEG_2000_EXTENSION);
                    }
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                    throw new CreateObjectException(e.getMessage(), e);
                }
            }

            String ocrPath = node.getAditionalInfoOrOcr();
            if (ocrPath != null && !"".equals(ocrPath)) {
                insertManagedDatastream(DATASTREAM_ID.TEXT_OCR, node.getUuid(), ocrPath, true, "text/xml");
            }

            String altoPath = node.getPartNumberOrAlto();
            if (altoPath != null && !"".equals(altoPath)) {
                insertManagedDatastream(DATASTREAM_ID.ALTO, node.getUuid(), altoPath, true, "text/xml");
            }
        }

        if (!success) {
            insertFOXML(node, mods, dc, attempt - 1);

        } else if (isPdf) {
            handlePdf(node);
        }
        if (node.getModel() == DigitalObjectModel.PAGE) processedPages.put(node.getPath(), node.getUuid());
        return node.getUuid();
    }

    /**
     * @param node
     * @throws CreateObjectException
     */
    private void handlePdf(NewDigitalObject node) throws CreateObjectException {
        String uuid = (node.getUuid().contains("uuid:") ? node.getUuid() : "uuid:".concat(node.getUuid()));
        String pathWithoutExtension = config.getImagesPath() + File.separator + node.getPath();
        if (insertManagedDatastream(DATASTREAM_ID.IMG_FULL, uuid, pathWithoutExtension
                + Constants.PDF_EXTENSION, true, Constants.PDF_MIMETYPE)) {
            createThumbPrewFromPdf(DATASTREAM_ID.IMG_THUMB,
                                   pathWithoutExtension,
                                   node.getPageIndex(),
                                   uuid,
                                   128);
            createThumbPrewFromPdf(DATASTREAM_ID.IMG_PREVIEW,
                                   pathWithoutExtension,
                                   node.getPageIndex(),
                                   uuid,
                                   500);
            String ocrPath = node.getAditionalInfoOrOcr();
            if (ocrPath != null && !"".equals(ocrPath)) {
                insertManagedDatastream(DATASTREAM_ID.TEXT_OCR, node.getUuid(), ocrPath, false, "text/xml");
            }
        }
    }

    /**
     * @param dsId
     * @param pathWithoutExtension
     * @param thumbPageNum
     * @param uuid
     * @param pageWidth
     * @throws CreateObjectException
     */
    private void createThumbPrewFromPdf(DATASTREAM_ID dsId,
                                        String pathWithoutExtension,
                                        int thumbPageNum,
                                        String uuid,
                                        int pageWidth) throws CreateObjectException {
        try {
            Process p =
                    Runtime.getRuntime().exec("convert " + pathWithoutExtension + Constants.PDF_EXTENSION
                            + "[" + (thumbPageNum - 1) + "] -thumbnail x" + pageWidth + " "
                            + pathWithoutExtension + ".jpg");

            int pNum;
            if ((pNum = p.waitFor()) == 0) {
                p.getInputStream().close();
                File thumb = new File(pathWithoutExtension + ".jpg");
                if (thumb.exists() && thumb.length() > 0) {
                    insertManagedDatastream(dsId, uuid, pathWithoutExtension + ".jpg", true, "image/jpeg");
                } else {
                    throw new CreateObjectException("After the conversion of the pdf file: "
                            + pathWithoutExtension + Constants.PDF_EXTENSION + " the image had zero size.");
                }
            } else {
                p.getInputStream().close();
                LOGGER.error("ERROR " + pNum + " : during the conversion of the pdf file: "
                        + pathWithoutExtension + Constants.PDF_EXTENSION + " the proces returned "
                        + IOUtils.readAsString(p.getErrorStream(), Charset.defaultCharset(), true));
                throw new CreateObjectException("Unable to run the convert proces on the pdf file: "
                        + pathWithoutExtension + Constants.PDF_EXTENSION);
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new CreateObjectException("Unable to run the convert proces on the pdf file: "
                    + pathWithoutExtension + Constants.PDF_EXTENSION);

        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new CreateObjectException("Unable to run the convert proces on the pdf file: "
                    + pathWithoutExtension + Constants.PDF_EXTENSION);

        } finally {
            File thumb = new File(pathWithoutExtension + ".jpg");
            if (thumb.exists()) thumb.delete();
        }
    }

    private void append(NewDigitalObject parrent, NewDigitalObject child) throws CreateObjectException {
        org.w3c.dom.Document doc = null;
        try {
            doc = fedoraAccess.getRelsExt(parrent.getUuid());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new CreateObjectException("Unable to append " + child.getName() + " (" + child.getUuid()
                    + ") to parrent named " + parrent.getName() + " (" + parrent.getUuid() + ")!");
        }
        DOMReader domReader = new DOMReader();
        Document document = domReader.read(doc);
        RelsExtRelation rel =
                new RelsExtRelation(child.getUuid(),
                                    NamedGraphModel.getRelationship(parrent.getModel(), child.getModel()),
                                    child.getName());
        FoxmlUtils.addRelationshipToRelsExt(document, rel);
        FedoraUtils.putRelsExt(parrent.getUuid(), document.asXML(), false);
    }

    private boolean insertManagedDatastream(DATASTREAM_ID dsId,
                                            String uuid,
                                            String filePathOrContent,
                                            boolean isFile,
                                            String mimeType) throws CreateObjectException {

        String prepUrl =
                "/objects/" + (uuid.contains("uuid:") ? uuid : "uuid:".concat(uuid)) + "/datastreams/"
                        + dsId.getValue() + "?controlGroup=M&versionable=true&dsState=A&mimeType=" + mimeType;

        String login = config.getFedoraLogin();
        String password = config.getFedoraPassword();
        String url = config.getFedoraHost().concat(prepUrl);
        boolean success;
        try {
            if (isFile) {
                success =
                        RESTHelper.post(url,
                                        new FileInputStream(new File(filePathOrContent)),
                                        login,
                                        password,
                                        false);
            } else {
                success = RESTHelper.post(url, filePathOrContent, login, password, false);
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new CreateObjectException("Unable to post "
                    + (isFile ? ("a file: " + filePathOrContent + " as a ") : "")
                    + "managed datastream to the object: " + uuid);
        }

        if (success) {
            LOGGER.info("An " + dsId.getValue() + (isFile ? (" file: " + filePathOrContent) : "")
                    + " has been inserted to the digital object: " + uuid + " as a " + dsId.getValue()
                    + " datastream.");

            return true;
        } else {
            LOGGER.error("An error occured during inserting an " + dsId.getValue()
                    + (isFile ? (" file: " + filePathOrContent) : "") + " to the digital object: " + uuid
                    + " as a " + dsId.getValue() + " datastream.");
            return false;

        }
    }

    private String getSysnoPath(String sysno) {
        StringBuffer sb = new StringBuffer();
        sb.append(sysno.substring(0, 3)).append('/').append(sysno.substring(3, 6)).append('/')
                .append(sysno.substring(6, 9)).append('/');
        return sb.toString();
    }

    private String getPathFromNonSysno(String nonSysno) {
        return (nonSysno == null || "".equals(nonSysno) ? "/" : '/' + nonSysno + '/');
    }

    private String addSlash(String string) {
        if (!string.endsWith("/")) {
            string += '/';
        }
        return string;
    }

    private void checkAccessRightsAndCreateDirectories(String sysno, String base)
            throws CreateObjectException {
        String unknown = config.getImageServerUnknown();
        String known = config.getImageServerKnown();
        String url = config.getImageServerUrl();
        boolean internal = config.getImageServerInternal();
        if (url == null || "".equals(url)) {
            String errorMsg = "URL of the imageserver has not been set in the configuration.";
            LOGGER.error(errorMsg);
            throw new CreateObjectException(errorMsg);
        }
        if (!internal && (unknown == null || "".equals(unknown) || known == null || "".equals(known))) {
            String errorMsg =
                    "Error, one of folloving compulsory options have not been set ["
                            + EditorConfiguration.ServerConstants.IMAGE_SERVER_KNOWN + " ,"
                            + EditorConfiguration.ServerConstants.IMAGE_SERVER_UNKNOWN + "]";
            LOGGER.error(errorMsg);
            throw new CreateObjectException(errorMsg);
        }

        File imagesDir = null;
        if (internal) {
            imagesDir = new File(config.getEditorHome() + '/' + ".images");
        } else {
            String basePath = "";
            if (base != null && !"".equals(base)) {
                basePath = base.toLowerCase() + "/";
            }
            imagesDir =
                    new File(isSysno(sysno) ? config.getImageServerKnown() + '/' + basePath
                            + getSysnoPath(sysno) : config.getImageServerUnknown()
                            + getPathFromNonSysno(sysno));
        }
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

    private boolean isSysno(String sysno) {
        return sysno != null && sysno.length() == 9;
    }

    /**
     * @return the topLevelUuid
     */
    public String getTopLevelUuid() {
        return topLevelUuid;
    }

    /**
     * @return the ingestedObjects
     */
    public List<String> getIngestedObjects() {
        return ingestedObjects;
    }

}
