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

import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.Z3950Client;
import cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;

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

    /** The client. */
    @Inject
    private Z3950Client client;

    /** The fedora access. */
    @Inject
    @Named("securedFedoraAccess")
    private FedoraAccess fedoraAccess;

    /** The input queue dao. */
    @Inject
    private InputQueueItemDAO inputQueueDAO;

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

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
        checkDocumentType(model);
        final String code = action.getCode();
        final String base = configuration.getScanInputQueuePath();
        LOGGER.debug("Processing input queue: (model = " + model + ", code = " + code + ")");
        ServerUtils.checkExpiredSession(httpSessionProvider);

        if (base == null || "".equals(base)) {
            LOGGER.error("Scanning folder: Action failed because attribut "
                    + EditorConfiguration.ServerConstants.INPUT_QUEUE + " is not set.");
            throw new ActionException("Scanning input queue: Action failed because attribut "
                    + EditorConfiguration.ServerConstants.INPUT_QUEUE + " is not set.");
        }
        String[] imageTypes = configuration.getImageExtensions();
        // due to gwt performance issues, more
        // concrete interface is used
        ArrayList<String> result =
                scanDirectoryStructure(base + File.separator + model + File.separator + code + File.separator,
                                       imageTypes);
        Collections.sort(result);
        return new ScanFolderResult(result);
    }

    /**
     * Check document types.
     * 
     * @param types
     *        the types
     * @throws ActionException
     *         the action exception
     */
    private void checkDocumentType(String model) throws ActionException {
        if (model == null
                || !fedoraAccess.isDigitalObjectPresent(Constants.FEDORA_MODEL_PREFIX
                        + DigitalObjectModel.toString(DigitalObjectModel.parseString(model)))) {
            LOGGER.error("Model " + model + " is not present in repository.");
            throw new ActionException(Constants.FEDORA_MODEL_PREFIX + model);
        }

    }

    /**
     * Scan directory structure.
     * 
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
    private ArrayList<String> scanDirectoryStructure(String path, final String[] imageTypes) {
        File dir = new File(path);
        FileFilter filter = new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                for (String suffix : imageTypes) {
                    if (pathname.getName().endsWith("." + suffix)) {
                        return true;
                    }
                }
                return false;
            }

        };
        File[] imgs = dir.listFiles(filter);
        ArrayList<String> list = new ArrayList<String>(imgs != null ? imgs.length : 0);
        for (int i = 0; i < imgs.length; i++) {
            list.add(imgs[i].getName());
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