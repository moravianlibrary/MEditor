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
package cz.fi.muni.xkremser.editor.server.handler;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.DAO.UserDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetUserRolesAndIdentitiesAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetUserRolesAndIdentitiesResult;

// TODO: Auto-generated Javadoc
/**
 * The Class GetRecentlyModifiedHandler.
 */
public class GetUserRolesAndIdentitiesHandler implements ActionHandler<GetUserRolesAndIdentitiesAction, GetUserRolesAndIdentitiesResult> {

	/** The logger. */
	private static final Logger LOGGER = Logger.getLogger(GetUserRolesAndIdentitiesHandler.class.getPackage().toString());

	/** The configuration. */
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
	 *          the logger
	 * @param configuration
	 *          the configuration
	 * @param userDAO
	 *          the user dao
	 */
	@Inject
	public GetUserRolesAndIdentitiesHandler(final EditorConfiguration configuration, final UserDAO userDAO) {
		this.configuration = configuration;
		this.userDAO = userDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
	 * .gwtplatform.dispatch.shared.Action,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public GetUserRolesAndIdentitiesResult execute(final GetUserRolesAndIdentitiesAction action, final ExecutionContext context) throws ActionException {
		if (action.getId() == null)
			return null;
		LOGGER.debug("Processing action: GetUserRolesAndIdentitiesAction");
		ServerUtils.checkExpiredSession(httpSessionProvider);

		try {
			return new GetUserRolesAndIdentitiesResult(userDAO.getRolesOfUser(Long.parseLong(action.getId())), userDAO.getIdentities(action.getId()));
		} catch (NumberFormatException e) {
			throw new ActionException(e);
		} catch (DatabaseException e) {
			throw new ActionException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<GetUserRolesAndIdentitiesAction> getActionType() {
		return GetUserRolesAndIdentitiesAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(GetUserRolesAndIdentitiesAction action, GetUserRolesAndIdentitiesResult result, ExecutionContext context) throws ActionException {
		// TODO Auto-generated method stub

	}
}