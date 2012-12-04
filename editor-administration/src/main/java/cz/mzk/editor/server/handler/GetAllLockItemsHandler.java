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

import java.util.List;

import javax.inject.Inject;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.StoredAndLocksDAO;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.ActiveLockItem;
import cz.mzk.editor.shared.rpc.action.GetAllLockItemsAction;
import cz.mzk.editor.shared.rpc.action.GetAllLockItemsResult;

/**
 * @author Matous Jobanek
 * @version Dec 4, 2012
 */
public class GetAllLockItemsHandler
        implements ActionHandler<GetAllLockItemsAction, GetAllLockItemsResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(GetAllLockItemsHandler.class.getPackage()
            .toString());

    /** The stored and locks dao. */
    @Inject
    private StoredAndLocksDAO storedAndLocksDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public GetAllLockItemsResult execute(GetAllLockItemsAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: GetAllStoredTreeStructureHandler");
        ServerUtils.checkExpiredSession();
        List<ActiveLockItem> items;
        try {
            items = storedAndLocksDAO.getAllActiveLocks(action.getUserId());
        } catch (DatabaseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new ActionException(e);
        }

        return new GetAllLockItemsResult(items);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<GetAllLockItemsAction> getActionType() {
        return GetAllLockItemsAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(GetAllLockItemsAction action, GetAllLockItemsResult result, ExecutionContext context)
            throws ActionException {
        // TODO Auto-generated method stub

    }

}
