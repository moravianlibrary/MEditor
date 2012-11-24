/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.StoredItemsDAO;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.fedora.utils.FedoraUtils;
import cz.mzk.editor.server.modelHandler.StoredDigitalObjectHandlerImpl;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.StoredItem;
import cz.mzk.editor.shared.rpc.action.StoredItemsAction;
import cz.mzk.editor.shared.rpc.action.StoredItemsResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class StoredItemsHandler
        implements ActionHandler<StoredItemsAction, StoredItemsResult> {

    /** The store dao */
    @Inject
    private StoredItemsDAO storeDao;

    /** The user DAO **/
    @Inject
    private UserDAO userDAO;

    /** The configuration. */
    private final EditorConfiguration configuration;

    /** The constant LOGGER */
    private static final Logger LOGGER = Logger.getLogger(StoredDigitalObjectHandlerImpl.class);

    @Inject
    public StoredItemsHandler(EditorConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public StoredItemsResult execute(StoredItemsAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: StoredItemsAction " + action.getStoredItem().getFileName());
        ServerUtils.checkExpiredSession();

        long userId = 0;
        try {
            userId = userDAO.getUsersId();
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }

        if (action.getVerb() == Constants.VERB.GET) {
            List<StoredItem> storedItems = new ArrayList<StoredItem>();
            try {
                storedItems = storeDao.getStoredItems(userId);
            } catch (DatabaseException e) {
                throw new ActionException(e);
            }

            return new StoredItemsResult(storedItems);

        } else if (action.getVerb() == Constants.VERB.PUT) {
            String workingCopyFoxml =
                    FedoraUtils.createWorkingCopyFoxmlAndStreams(action.getDetail(), true)[0];
            String userDirsPath = configuration.getUserDirectoriesPath();
            File userDir = new File(userDirsPath + File.separator + userId);
            if (!userDir.exists()) userDir.mkdirs();

            String foxmlFile =
                    userDirsPath + File.separator + userId + File.separator
                            + action.getStoredItem().getFileName();
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(foxmlFile));
                out.write(workingCopyFoxml);
                out.flush();
                out.close();
            } catch (IOException e) {
                LOGGER.error("An error occured when the digital object: " + action.getDetail().getUuid()
                        + " was storing on the file path: " + foxmlFile + " " + e);
                throw new ActionException(e);
            }

            try {
                action.getStoredItem().setFileName(foxmlFile);
                if (storeDao.checkStoredDigitalObject(userId, action.getStoredItem())) {
                    return new StoredItemsResult(new ArrayList<StoredItem>());
                } else {
                    return new StoredItemsResult(null);
                }

            } catch (DatabaseException e) {
                throw new ActionException(e);
            }

        } else if (action.getVerb() == Constants.VERB.DELETE) {
            try {
                if (storeDao.deleteItem(action.getStoredItem().getId())) {
                    File deleteFile = new File(action.getStoredItem().getFileName());
                    if (deleteFile.exists()) {
                        deleteFile.delete();
                    }
                    return new StoredItemsResult(new ArrayList<StoredItem>());
                } else {
                    return new StoredItemsResult(null);
                }
            } catch (DatabaseException e) {
                throw new ActionException(e);
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Class<StoredItemsAction> getActionType() {
        return StoredItemsAction.class;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void undo(StoredItemsAction arg0, StoredItemsResult arg1, ExecutionContext arg2)
            throws ActionException {
        // TODO Auto-generated method stub
    }

}
