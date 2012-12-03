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

import java.io.File;

import javax.inject.Inject;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.StoredAndLocksDAO;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.StoredItem;
import cz.mzk.editor.shared.rpc.action.RemoveStoredWorkingCopyItemsAction;
import cz.mzk.editor.shared.rpc.action.RemoveStoredWorkingCopyItemsResult;

/**
 * @author Matous Jobanek
 * @version Dec 3, 2012
 */
public class RemoveStoredWorkingCopyHandler
        implements ActionHandler<RemoveStoredWorkingCopyItemsAction, RemoveStoredWorkingCopyItemsResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(RemoveStoredWorkingCopyHandler.class.getPackage()
            .toString());

    /** The stored and locks dao. */
    @Inject
    private StoredAndLocksDAO storedAndLocksDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public RemoveStoredWorkingCopyItemsResult execute(RemoveStoredWorkingCopyItemsAction action,
                                                      ExecutionContext context) throws ActionException {

        LOGGER.debug("Processing action: RemoveStoredWorkingCopyItemsAction");
        ServerUtils.checkExpiredSession();

        boolean successful = true;
        try {
            for (StoredItem item : action.getItems()) {
                if (storedAndLocksDAO.deleteStoredWorkingCopyItem(item.getId())) {
                    File deleteFile = new File(item.getFileName());
                    if (deleteFile.exists()) {
                        deleteFile.delete();
                    }
                    successful &= true;
                } else {
                    successful = false;
                }
            }
        } catch (DatabaseException e) {
            LOGGER.error(e);
            e.printStackTrace();
            throw new ActionException(e);
        }

        return new RemoveStoredWorkingCopyItemsResult(successful);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<RemoveStoredWorkingCopyItemsAction> getActionType() {
        return RemoveStoredWorkingCopyItemsAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(RemoveStoredWorkingCopyItemsAction action,
                     RemoveStoredWorkingCopyItemsResult result,
                     ExecutionContext context) throws ActionException {
        // TODO Auto-generated method stub

    }

}
