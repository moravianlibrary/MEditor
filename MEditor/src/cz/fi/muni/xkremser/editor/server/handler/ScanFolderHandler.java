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

package cz.fi.muni.xkremser.editor.server.handler;

import java.io.File;
import java.io.FileFilter;

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

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.DAO.ImageResolverDAO;
import cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.config.EditorConfigurationImpl;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;
import cz.fi.muni.xkremser.editor.server.fedora.utils.IOUtils;

import cz.fi.muni.xkremser.editor.shared.rpc.ImageItem;
import cz.fi.muni.xkremser.editor.shared.rpc.ServerActionResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanFolderAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanFolderResult;

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
        // parse input
        final String model = action.getModel();
        final String code = action.getCode();
        if (model == null || code == null) {
            return null;
        }
        final String base = configuration.getScanInputQueuePath();
        LOGGER.debug("Scanning folder: (model = " + model + ", code = " + code + ")");
        HttpServletRequest req = requestProvider.get();
        ServerUtils.checkExpiredSession(req.getSession());

        try {
            String name = action.getName();
            if (name != null && !"".equals(name)) {
                String path =
                        File.separator + model + File.separator
                                + ((code.contains("/")) ? code.substring(0, code.indexOf("/")) : code);
                inputQueueDAO.updateName(path, name);
            }
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }

        if (base == null || "".equals(base)) {
            LOGGER.error("Scanning folder: Action failed because attribut "
                    + EditorConfiguration.ServerConstants.INPUT_QUEUE + " is not set.");
            throw new ActionException("Scanning input queue: Action failed because attribut "
                    + EditorConfiguration.ServerConstants.INPUT_QUEUE + " is not set.");
        }
        String[] imageTypes = configuration.getImageExtensions();
        String prefix = base + File.separator + model + File.separator + code + File.separator;
        List<String> wrongNames = new ArrayList<String>();
        List<String> imgFileNames = scanDirectoryStructure(prefix, imageTypes, wrongNames);
        if (imgFileNames == null) {
            throw new ActionException("No images found in " + prefix);
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
                String newIdentifier = null;
                String resolvedIdentifier = resolvedIdentifiers.get(i);
                if (resolvedIdentifier == null) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(model).append('#').append(code).append('#').append(i);
                    newIdentifier = UUID.nameUUIDFromBytes(sb.toString().getBytes()).toString();
                    sb = new StringBuffer();
                    sb.append(EditorConfigurationImpl.DEFAULT_IMAGES_LOCATION).append(newIdentifier)
                            .append(Constants.JPEG_2000_EXTENSION);
                    resolvedIdentifier = sb.toString();
                    toAdd.add(new ImageItem(newIdentifier, resolvedIdentifier, imgFileNames.get(i)));
                }
                String uuid =
                        newIdentifier != null ? newIdentifier : resolvedIdentifier
                                .substring(resolvedIdentifier.lastIndexOf('/') + 1,
                                           resolvedIdentifier.lastIndexOf('.'));
                result.add(new ImageItem(uuid, resolvedIdentifier, imgFileNames.get(i)));
            }
            if (!toAdd.isEmpty()) {
                imageResolverDAO.insertItems(toAdd);
            }
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }

        if (toAdd.size() > 10 && !configuration.getImageServerInternal()) {
            removeOldImages();
        }

        if (wrongNames.size() == 0) {
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

    /**
     * Scan directory structure.
     * 
     * @param wrongNames
     * @param pathPrefix
     *        the path prefix
     * @param relativePath
     *        the relative path
     * @param list
     *        the list
     * @param level
     *        the level
     * @return the list
     */
    private List<String> scanDirectoryStructure(String path,
                                                final String[] imageTypes,
                                                List<String> wrongNames) {
        File dir = new File(path);
        FileFilter filter = new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                for (String suffix : imageTypes) {
                    if (pathname.getName().toLowerCase().endsWith("." + suffix.toLowerCase())) {
                        return true;
                    }
                }
                return false;
            }

        };
        File[] imgs = dir.listFiles(filter);
        if (imgs == null || imgs.length == 0) {
            return null;
        }
        ArrayList<String> list = new ArrayList<String>(imgs != null ? imgs.length : 0);
        for (int i = 0; i < imgs.length; i++) {

            String name = imgs[i].getName();
            if (!IOUtils.containsIllegalCharacter(name)) {
                list.add(path + name);
            } else {
                wrongNames.add(name);
                LOGGER.error("This image contains some illegal character(s): " + path + name);
            }
        }
        return list;
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