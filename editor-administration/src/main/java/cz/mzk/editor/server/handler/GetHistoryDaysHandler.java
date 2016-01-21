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

import java.util.List;

import javax.inject.Inject;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.server.DAO.ActionDAO;
import cz.mzk.editor.server.DAO.DAOUtils;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.EditorDate;
import cz.mzk.editor.shared.rpc.action.GetHistoryDaysAction;
import cz.mzk.editor.shared.rpc.action.GetHistoryDaysResult;

/**
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public class GetHistoryDaysHandler
        implements ActionHandler<GetHistoryDaysAction, GetHistoryDaysResult> {

    @Inject
    private ActionDAO actionDAO;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(GetHistoryDaysHandler.class);

    @Inject
    private DAOUtils daoUtils;

    /**
     * {@inheritDoc}
     */
    @Override
    public GetHistoryDaysResult execute(GetHistoryDaysAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: GetHistoryDaysAction");
        ServerUtils.checkExpiredSession();

        List<EditorDate> historyDays = null;
        try {
            if (action.getUserId() != null
                    && !ServerUtils.checkUserRightOrAll(EDITOR_RIGHTS.SHOW_ALL_HISTORY)
                    && daoUtils.getUserId(true) != action.getUserId()) {
                LOGGER.warn("Bad authorization in " + this.getClass().toString());
                throw new ActionException("Bad authorization in " + this.getClass().toString());
            }

            historyDays = actionDAO.getHistoryDays(action.getUserId(), action.getUuid());
        } catch (DatabaseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new ActionException(e);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return new GetHistoryDaysResult(historyDays);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<GetHistoryDaysAction> getActionType() {
        // TODO Auto-generated method stub
        return GetHistoryDaysAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(GetHistoryDaysAction action, GetHistoryDaysResult result, ExecutionContext context)
            throws ActionException {
        // TODO Auto-generated method stub

    }

}
