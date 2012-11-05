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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.server.DAO.ActionDAO;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.shared.rpc.HistoryItem;
import cz.mzk.editor.shared.rpc.action.GetHistoryAction;
import cz.mzk.editor.shared.rpc.action.GetHistoryResult;

// TODO: Auto-generated Javadoc
/**
 * The Class GetHistoryHandler.
 * 
 * @author Matous Jobanek
 * @version Nov 1, 2012
 */
public class GetHistoryHandler
        implements ActionHandler<GetHistoryAction, GetHistoryResult> {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(GetHistoryHandler.class.getPackage().toString());

    /** The action dao. */
    @Inject
    private ActionDAO actionDAO;

    /**
     * Execute.
     * 
     * @param action
     *        the action
     * @param context
     *        the context
     * @return the gets the history result
     * @throws ActionException
     *         the action exception {@inheritDoc}
     */
    @Override
    public GetHistoryResult execute(GetHistoryAction action, ExecutionContext context) throws ActionException {

        List<HistoryItem> historyItems = new ArrayList<HistoryItem>();
        try {
            historyItems =
                    actionDAO.getHistoryItems(action.getEditorUsedId(),
                                              action.getLowerLimit(),
                                              action.getUpperLimit());
        } catch (DatabaseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new ActionException(e);
        }

        return new GetHistoryResult(historyItems);
    }

    /**
     * Gets the action type.
     * 
     * @return the action type {@inheritDoc}
     */
    @Override
    public Class<GetHistoryAction> getActionType() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Undo.
     * 
     * @param action
     *        the action
     * @param result
     *        the result
     * @param context
     *        the context
     * @throws ActionException
     *         the action exception {@inheritDoc}
     */
    @Override
    public void undo(GetHistoryAction action, GetHistoryResult result, ExecutionContext context)
            throws ActionException {
        // TODO Auto-generated method stub

    }

}
