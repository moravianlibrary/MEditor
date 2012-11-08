/*
 * Metadata Editor
 * @author Jiri Kremser
 * @author Matous Jobanek
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

import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.shared.rpc.action.GetUsersInfoAction;
import cz.mzk.editor.shared.rpc.action.GetUsersInfoResult;

// TODO: Auto-generated Javadoc 
/**
 * The Class GetRecentlyModifiedHandler.
 */
public class GetUsersInfoHandler
        implements ActionHandler<GetUsersInfoAction, GetUsersInfoResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(GetUsersInfoHandler.class.getPackage().toString());

    /** The configuration. */
    @SuppressWarnings("unused")
    private final EditorConfiguration configuration;

    /** The recently modified dao. */
    private final UserDAO userDAO;

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    /**
     * Instantiates a new gets the recently modified handler.
     * 
     * @param logger
     *        the logger
     * @param configuration
     *        the configuration
     * @param userDAO
     *        the user dao
     */
    @Inject
    public GetUsersInfoHandler(final EditorConfiguration configuration, final UserDAO userDAO) {
        this.configuration = configuration;
        this.userDAO = userDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetUsersInfoResult execute(final GetUsersInfoAction action, final ExecutionContext context)
            throws ActionException {
        LOGGER.debug("Processing action: GetUserInfoAction");
        //        ServerUtils.checkExpiredSession(httpSessionProvider);

        try {
            return new GetUsersInfoResult(userDAO.getUsers());
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<GetUsersInfoAction> getActionType() {
        return GetUsersInfoAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(GetUsersInfoAction action, GetUsersInfoResult result, ExecutionContext context)
            throws ActionException {
        // TODO Auto-generated method stub

    }
}