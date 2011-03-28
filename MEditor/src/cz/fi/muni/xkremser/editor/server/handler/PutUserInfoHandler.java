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
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutUserInfoAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutUserInfoResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class PutUserInfoHandler implements ActionHandler<PutUserInfoAction, PutUserInfoResult> {

	/** The logger. */
	private static final Logger LOGGER = Logger.getLogger(PutUserInfoHandler.class.getPackage().toString());

	/** The recently modified dao. */
	@Inject
	private UserDAO userDAO;

	/** The http session provider. */
	@Inject
	private Provider<HttpSession> httpSessionProvider;

	/**
	 * Instantiates a new put recently modified handler.
	 * 
	 */
	@Inject
	public PutUserInfoHandler() {
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
	public PutUserInfoResult execute(final PutUserInfoAction action, final ExecutionContext context) throws ActionException {
		if (action.getUser() == null)
			throw new NullPointerException("getUser()");
		LOGGER.debug("Processing action: PutUserInfoAction user:" + action.getUser());
		ServerUtils.checkExpiredSession(httpSessionProvider);

		String id;
		try {
			id = userDAO.addUser(action.getUser());
		} catch (DatabaseException e) {
			throw new ActionException(e);
		}
		return new PutUserInfoResult(id, "exist".equals(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<PutUserInfoAction> getActionType() {
		return PutUserInfoAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(PutUserInfoAction action, PutUserInfoResult result, ExecutionContext context) throws ActionException {
		// TODO undo method

	}
}