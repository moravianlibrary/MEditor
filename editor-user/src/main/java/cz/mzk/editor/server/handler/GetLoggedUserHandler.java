/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
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

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.action.GetLoggedUserAction;
import cz.mzk.editor.shared.rpc.action.GetLoggedUserResult;

// TODO: Auto-generated Javadoc
/**
 * The Class GetRecentlyModifiedHandler.
 */
public class GetLoggedUserHandler
        implements ActionHandler<GetLoggedUserAction, GetLoggedUserResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(GetLoggedUserHandler.class.getPackage().toString());

    /** The recently modified dao. */
    private final UserDAO userDAO;

    /**
     * Instantiates a new gets the recently modified handler.
     * 
     * @param userDAO
     *        the user dao
     * @param httpSessionProvider
     *        the http session provider
     */
    @Inject
    public GetLoggedUserHandler(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public GetLoggedUserResult execute(final GetLoggedUserAction action, final ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: GetLoggedUserAction");
        ServerUtils.checkExpiredSession();

        boolean editUsers;
        try {
            editUsers = true;
            return new GetLoggedUserResult(userDAO.getName(userDAO.getUsersId()),
                                           editUsers,
                                           userDAO.getUsersId());
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<GetLoggedUserAction> getActionType() {
        return GetLoggedUserAction.class;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
     * gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.shared.Result,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public void undo(GetLoggedUserAction action, GetLoggedUserResult result, ExecutionContext context)
            throws ActionException {
        // TODO Auto-generated method stub

    }
}