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
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserInfoAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserInfoResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class RemoveUserInfoHandler implements ActionHandler<RemoveUserInfoAction, RemoveUserInfoResult> {

	/** The logger. */
	private static final Logger LOGGER = Logger.getLogger(RemoveUserInfoHandler.class.getPackage().toString());

	/** The recently modified dao. */
	@Inject
	private UserDAO userDAO;

	/**
	 * Instantiates a new put recently modified handler.
	 * 
	 * @param logger
	 *          the logger
	 * @param configuration
	 *          the configuration
	 */
	@Inject
	public RemoveUserInfoHandler() {

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
	public RemoveUserInfoResult execute(final RemoveUserInfoAction action, final ExecutionContext context) throws ActionException {
		if (action.getId() == null)
			throw new NullPointerException("getId()");
		LOGGER.debug("Processing action: RemoveUserInfoAction user id:" + action.getId());
		try {
			userDAO.removeUser(Long.parseLong(action.getId()));
		} catch (NumberFormatException e) {
			throw new ActionException(e);
		} catch (DatabaseException e) {
			throw new ActionException(e);
		}
		return new RemoveUserInfoResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<RemoveUserInfoAction> getActionType() {
		return RemoveUserInfoAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(RemoveUserInfoAction action, RemoveUserInfoResult result, ExecutionContext context) throws ActionException {
		// TODO undo method

	}
}