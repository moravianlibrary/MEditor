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

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.DAO.UserDAO;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserRoleAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserRoleResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class RemoveUserRoleHandler implements ActionHandler<RemoveUserRoleAction, RemoveUserRoleResult> {

	/** The logger. */
	private static final Logger LOGGER = Logger.getLogger(RemoveUserRoleHandler.class.getPackage().toString());

	/** The recently modified dao. */
	@Inject
	private UserDAO userDAO;

	/**
	 * Instantiates a new put recently modified handler.
	 * 
	 */
	@Inject
	public RemoveUserRoleHandler() {

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
	public RemoveUserRoleResult execute(final RemoveUserRoleAction action, final ExecutionContext context) throws ActionException {
		if (action.getId() == null)
			throw new NullPointerException("getId()");
		LOGGER.debug("Processing action: RemoveUserRoleAction user id:" + action.getId());
		try {
			userDAO.removeUserRole(Long.parseLong(action.getId()));
		} catch (NumberFormatException e) {
			throw new ActionException(e);
		} catch (DatabaseException e) {
			throw new ActionException(e);
		}
		return new RemoveUserRoleResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<RemoveUserRoleAction> getActionType() {
		return RemoveUserRoleAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(RemoveUserRoleAction action, RemoveUserRoleResult result, ExecutionContext context) throws ActionException {
		// TODO undo method

	}
}