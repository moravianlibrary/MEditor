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

import javax.inject.Inject;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.StoredAndLocksDAO;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.action.RemoveStoredTreeStructureItemsAction;
import cz.mzk.editor.shared.rpc.action.RemoveStoredTreeStructureItemsResult;

/**
 * @author Matous Jobanek
 * @version Dec 3, 2012
 */
public class RemoveStoredTreeStructureHandler
        implements ActionHandler<RemoveStoredTreeStructureItemsAction, RemoveStoredTreeStructureItemsResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(RemoveStoredTreeStructureHandler.class.getPackage()
            .toString());

    /** The stored and locks dao. */
    @Inject
    private StoredAndLocksDAO storedAndLocksDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public RemoveStoredTreeStructureItemsResult execute(RemoveStoredTreeStructureItemsAction action,
                                                        ExecutionContext context) throws ActionException {

        LOGGER.debug("Processing action: RemoveStoredTreeStructureItemsAction");
        ServerUtils.checkExpiredSession();

        boolean successful = true;
        try {
            for (Long id : action.getItemsId()) {
                successful &= storedAndLocksDAO.removeSavedStructure(id);
            }
        } catch (DatabaseException e) {
            LOGGER.error(e);
            e.printStackTrace();
            throw new ActionException(e);
        }

        return new RemoveStoredTreeStructureItemsResult(successful);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<RemoveStoredTreeStructureItemsAction> getActionType() {
        return RemoveStoredTreeStructureItemsAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(RemoveStoredTreeStructureItemsAction action,
                     RemoveStoredTreeStructureItemsResult result,
                     ExecutionContext context) throws ActionException {
        // TODO Auto-generated method stub

    }

}
