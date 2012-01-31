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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.HttpCookies;
import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.DAO.StoredItemsDAO;
import cz.fi.muni.xkremser.editor.server.DAO.UserDAO;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

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

        if (action.getDetail() == null) {
            List<StoredItem> storedItems = new ArrayList<StoredItem>();
            try {
                storedItems = storeDao.getStoredItems(userId);
            } catch (DatabaseException e) {
                throw new ActionException(e);
            }

            return new StoredItemsResult(storedItems, null);

        } else {

            //            String workingCopyFoxml =
            //                    FedoraUtils.createWorkingCopyFoxmlAndStreams(action.getDetail(), true)[0];
            return new StoredItemsResult(null, null);
        }
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
