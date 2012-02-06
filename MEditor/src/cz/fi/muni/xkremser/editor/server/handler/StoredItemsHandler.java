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

package cz.fi.muni.xkremser.editor.server.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.HttpCookies;
import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.DAO.StoredItemsDAO;
import cz.fi.muni.xkremser.editor.server.DAO.UserDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.server.modelHandler.StoredDigitalObjectHandlerImpl;

import cz.fi.muni.xkremser.editor.shared.rpc.StoredItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.StoredItemsAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.StoredItemsResult;

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

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

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

        HttpSession session = httpSessionProvider.get();
        ServerUtils.checkExpiredSession(session);

        long userId = 0;
        try {
            userId = userDAO.getUsersId(String.valueOf(session.getAttribute(HttpCookies.SESSION_ID_KEY)));
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

            String foxmlFile = userDirsPath + userId + File.separator + action.getStoredItem().getFileName();
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
                if (storeDao.storeDigitalObject(userId, action.getStoredItem())) {
                    return new StoredItemsResult(new ArrayList<StoredItem>());
                } else {
                    return new StoredItemsResult(null);
                }

            } catch (DatabaseException e) {
                throw new ActionException(e);
            }

        } else if (action.getVerb() == Constants.VERB.DELETE) {
            try {
                String fileName = action.getStoredItem().getFileName();
                if (storeDao.deleteItem(fileName)) {
                    File deleteFile = new File(fileName);
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
