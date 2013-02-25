/*   jijiji
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

package cz.mzk.editor.server.handler;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.mzk.editor.server.ScanFolder;
import cz.mzk.editor.server.ScanFolderImpl;
import cz.mzk.editor.server.util.AudioUtils;
import cz.mzk.editor.shared.rpc.ImageItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.client.util.Constants.SERVER_ACTION_RESULT;
import cz.mzk.editor.server.DAO.ConversionDAO;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.ImageResolverDAO;
import cz.mzk.editor.server.DAO.InputQueueItemDAO;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.util.IOUtils;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.ImageItem;
import cz.mzk.editor.shared.rpc.ServerActionResult;
import cz.mzk.editor.shared.rpc.action.ScanFolderAction;
import cz.mzk.editor.shared.rpc.action.ScanFolderResult;

// TODO: Auto-generated Javadoc
/**
 * The Class ScanFolderHandler.
 */
public class ScanFolderHandler
        implements ActionHandler<ScanFolderAction, ScanFolderResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(ScanFolderHandler.class.getPackage().toString());

    /** The configuration. */
    private final EditorConfiguration configuration;

    /** The input queue dao. */
    @Inject
    private ImageResolverDAO imageResolverDAO;

    @Inject
    private Provider<HttpServletRequest> requestProvider;

    /** The input queue dao. */
    @Inject
    private InputQueueItemDAO inputQueueDAO;

    @Inject
    private ConversionDAO conversionDAO;

    @Inject
    private ScanFolderImpl.ScanFolderFactory scanFolderFactory;

    /**
     * Instantiates a new scan input queue handler.
     * 
     * @param configuration
     *        the configuration
     */
    @Inject
    public ScanFolderHandler(final EditorConfiguration configuration) {
        this.configuration = configuration;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public ScanFolderResult execute(final ScanFolderAction action, final ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: ScanFolderAction " + action.getModel() + " - " + action.getCode());
        ServerUtils.checkExpiredSession();

        scanFolderFactory.create(action.getModel(), action.getCode());

        if (!ServerUtils.checkUserRightOrAll(EDITOR_RIGHTS.SCAN_FOLDER_TO_CONVERT)) {
            LOGGER.warn("Bad authorization in " + this.getClass().toString());
            throw new ActionException("Bad authorization in " + this.getClass().toString());
        }

        // parse input
        final String model = action.getModel();
        final String code = action.getCode();
        if (model == null || code == null) {
            return null;
        }

        ScanFolder scanFolder = scanFolderFactory.create(model, code);
        List<String> wrongNames = scanFolder.getWrongNames();
        List<String> imgFileNames = scanFolder.getFileNames();
        if (imgFileNames == null || imgFileNames.isEmpty()) {
            throw new ActionException("No images found in " + scanFolder.getPath());
        }

        if (imgFileNames.size() == 1 && imgFileNames.get(0).endsWith(Constants.PDF_EXTENSION)) {
            return handlePdf(imgFileNames.get(0));
        }

        Collections.sort(imgFileNames);
        // due to gwt performance issues, more
        // concrete interface is used
        ArrayList<ImageItem> result = new ArrayList<ImageItem>(imgFileNames.size());
        ArrayList<ImageItem> toAdd = new ArrayList<ImageItem>();
        ArrayList<String> resolvedIdentifiers;

        try {
            resolvedIdentifiers = imageResolverDAO.resolveItems(imgFileNames);
            for (int i = 0; i < resolvedIdentifiers.size(); i++) {
                if (imgFileNames.get(i).endsWith(Constants.PDF_EXTENSION)) {
                    throw new ActionException("There is more than one pdf file or one pdf file and some other file with enable extension in "
                            + scanFolder.getPath());
                }

                //get mimetype from extension (for audio)
                int position = imgFileNames.get(i).lastIndexOf('.');
                String extension = null;
                if (position > 0) {
                    extension = imgFileNames.get(i).substring(position);
                }
                Constants.AUDIO_MIMETYPES audioMimeType = Constants.AUDIO_MIMETYPES.findByExtension(extension);

                String newIdentifier = null;
                String resolvedIdentifier = resolvedIdentifiers.get(i);
                if (resolvedIdentifier == null) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(model).append('#').append(code).append('#').append(i);
                    newIdentifier = UUID.nameUUIDFromBytes(sb.toString().getBytes()).toString();
                    sb = new StringBuffer();
                    sb.append(configuration.getImagesPath()).append(newIdentifier)
                            .append(Constants.JPEG_2000_EXTENSION);
                    resolvedIdentifier = sb.toString();

                    ImageItem item = new ImageItem(newIdentifier, resolvedIdentifier, imgFileNames.get(i));
                    if (!audioMimeType.equals(Constants.AUDIO_MIMETYPES.UNKOWN_MIMETYPE)) {
                        item.setMimeType(audioMimeType.getMimeType());
                        sb = new StringBuffer();
                        sb.append(configuration.getImagesPath()).append(newIdentifier)
                                .append(Constants.AUDIO_MIMETYPES.WAV_MIMETYPE.getExtension());
                        item.setJpeg2000FsPath(sb.toString());
                    }

                    toAdd.add(item);
                }
                String uuid =
                        newIdentifier != null ? newIdentifier : resolvedIdentifier
                                .substring(resolvedIdentifier.lastIndexOf('/') + 1,
                                        resolvedIdentifier.lastIndexOf('.'));
                ImageItem item = new ImageItem(uuid, resolvedIdentifier, imgFileNames.get(i));

                String name = FilenameUtils.getBaseName(imgFileNames.get(i));
                String[] splits = name.split("-");

                /** audio files - special name convection */
                if (splits.length == 4 && "DS".equals(splits[0])) {
                    item.setName(splits[2] + "-" + splits[3]);
                }
                if (splits.length == 5 && "MC".equals(splits[0])) {
                    item.setName(splits[2] + "-" + splits[3] + "-" + splits[4]);
                }

                if (imgFileNames.get(i).endsWith(".wav")) {
                    item.setLength(AudioUtils.getLengthDigit(imgFileNames.get(i)));
                    item.setMimeType(Constants.AUDIO_MIMETYPES.WAV_MIMETYPE.getMimeType());
                }
                result.add(item);
            }
            if (!toAdd.isEmpty()) {
                imageResolverDAO.insertItems(toAdd);
            }
        } catch (DatabaseException e) {
            throw new ActionException(e);
        } catch (IOException e) {
            throw new ActionException(e);
        }

        if (toAdd.size() > 10 && !configuration.getImageServerInternal()) {
            removeOldImages();
        }

        if (wrongNames.size() == 0) {
            try {
                conversionDAO.insertConversionInfo(File.separator + model + File.separator + code
                        + File.separator);
            } catch (DatabaseException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
            return new ScanFolderResult(result,
                    toAdd,
                    new ServerActionResult(Constants.SERVER_ACTION_RESULT.OK));
        } else {
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < wrongNames.size() && i < 10; i++) {
                sb.append("<br>" + wrongNames.get(i));
                if (i < 9) {
                    if (i < wrongNames.size() - 1) sb.append(",");
                } else {
                    sb.append(",...");
                }
            }
            return new ScanFolderResult(result,
                    toAdd,
                    new ServerActionResult(Constants.SERVER_ACTION_RESULT.WRONG_FILE_NAME,
                            sb.toString()));
        }
    }

    private ScanFolderResult handlePdf(String pdfPath) throws ActionException {
        String uuid = UUID.nameUUIDFromBytes(new File(pdfPath).getAbsolutePath().getBytes()).toString();
        String newPdfPath = configuration.getImagesPath() + uuid + Constants.PDF_EXTENSION;
        try {
            IOUtils.copyFile(pdfPath, newPdfPath);
            LOGGER.info("Pdf file " + pdfPath + " has been copied to " + newPdfPath);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new ActionException(e);
        }
        ArrayList<ImageItem> result = new ArrayList<ImageItem>(1);
        result.add(new ImageItem(uuid, newPdfPath, pdfPath));
        try {
            imageResolverDAO.insertItems(result);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ActionException(e);
        }
        return new ScanFolderResult(result, null, new ServerActionResult(SERVER_ACTION_RESULT.OK_PDF));
    }

    /**
     * @throws ActionException
     */
    private void removeOldImages() throws ActionException {

        int numberOfDays = Integer.MIN_VALUE;
        String numberString = configuration.getGenImagesLifetime();

        if (numberString != null) {
            try {
                numberOfDays = Integer.parseInt(numberString);
                if (numberOfDays > 0) {
                    try {

                        ArrayList<String> oldImages = imageResolverDAO.cacheAgeingProcess(numberOfDays);
                        for (String oldImage : oldImages) {
                            new File(oldImage).delete();
                        }

                    } catch (DatabaseException e) {
                        throw new ActionException(e);
                    }
                }
            } catch (NumberFormatException nfe) {
            }
        }
    }


    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<ScanFolderAction> getActionType() {
        return ScanFolderAction.class;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
     * gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.shared.Result,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public void undo(ScanFolderAction action, ScanFolderResult result, ExecutionContext context)
            throws ActionException {
        // TODO Auto-generated method stub

    }

}