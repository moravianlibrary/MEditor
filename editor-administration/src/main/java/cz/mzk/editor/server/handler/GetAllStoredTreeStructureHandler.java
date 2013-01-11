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

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.server.DAO.DAOUtils;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.StoredAndLocksDAO;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.TreeStructureInfo;
import cz.mzk.editor.shared.rpc.action.GetAllStoredTreeStructureItemsAction;
import cz.mzk.editor.shared.rpc.action.GetAllStoredTreeStructureItemsResult;

/**
 * @author Matous Jobanek
 * @version Dec 3, 2012
 */
public class GetAllStoredTreeStructureHandler
        implements ActionHandler<GetAllStoredTreeStructureItemsAction, GetAllStoredTreeStructureItemsResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(GetAllStoredTreeStructureHandler.class.getPackage()
            .toString());

    /** The stored and locks dao. */
    @Inject
    private StoredAndLocksDAO storedAndLocksDAO;

    @Inject
    private DAOUtils daoUtils;

    /**
     * {@inheritDoc}
     */
    @Override
    public GetAllStoredTreeStructureItemsResult execute(GetAllStoredTreeStructureItemsAction action,
                                                        ExecutionContext context) throws ActionException {

        LOGGER.debug("Processing action: GetAllStoredTreeStructureHandler");
        ServerUtils.checkExpiredSession();

        List<TreeStructureInfo> strucItems = new ArrayList<TreeStructureInfo>();

        try {
            if (action.getUserId() != null
                    && !ServerUtils.checkUserRightOrAll(EDITOR_RIGHTS.SHOW_ALL_STORED_AND_LOCKS)
                    && daoUtils.getUserId(true) != action.getUserId()) {
                LOGGER.warn("Bad authorization in " + this.getClass().toString());
                throw new ActionException("Bad authorization in " + this.getClass().toString());
            }

            strucItems = storedAndLocksDAO.getAllSavedTreeStructures(action.getUserId());
        } catch (DatabaseException e) {
            LOGGER.error(e);
            e.printStackTrace();
            throw new ActionException(e);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return new GetAllStoredTreeStructureItemsResult(strucItems);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<GetAllStoredTreeStructureItemsAction> getActionType() {
        return GetAllStoredTreeStructureItemsAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(GetAllStoredTreeStructureItemsAction action,
                     GetAllStoredTreeStructureItemsResult result,
                     ExecutionContext context) throws ActionException {
        // TODO Auto-generated method stub

    }

}
