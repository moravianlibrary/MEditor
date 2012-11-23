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

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.server.DAO.ActionDAO;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.HistoryItemInfo;
import cz.mzk.editor.shared.rpc.action.GetHistoryItemInfoAction;
import cz.mzk.editor.shared.rpc.action.GetHistoryItemInfoResult;

/**
 * @author Matous Jobanek
 * @version Nov 7, 2012
 */
public class GetHistoryItemInfoHandler
        implements ActionHandler<GetHistoryItemInfoAction, GetHistoryItemInfoResult> {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(GetHistoryItemInfoHandler.class.getPackage()
            .toString());

    @Inject
    private ActionDAO actionDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public GetHistoryItemInfoResult execute(GetHistoryItemInfoAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: GetHistoryItemInfoAction");
        ServerUtils.checkExpiredSession();

        HistoryItemInfo historyItemInfo = null;
        try {
            historyItemInfo = actionDAO.getHistoryItemInfo(action.getId(), action.getTableName());
        } catch (DatabaseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new ActionException(e);
        }
        return new GetHistoryItemInfoResult(historyItemInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<GetHistoryItemInfoAction> getActionType() {
        return GetHistoryItemInfoAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(GetHistoryItemInfoAction action,
                     GetHistoryItemInfoResult result,
                     ExecutionContext context) throws ActionException {
        // TODO Auto-generated method stub
    }

}
