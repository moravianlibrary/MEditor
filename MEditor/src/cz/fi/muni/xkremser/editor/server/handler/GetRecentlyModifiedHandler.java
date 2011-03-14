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

import cz.fi.muni.xkremser.editor.server.HttpCookies;
import cz.fi.muni.xkremser.editor.server.DAO.RecentlyModifiedItemDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRecentlyModifiedAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRecentlyModifiedResult;

// TODO: Auto-generated Javadoc
/**
 * The Class GetRecentlyModifiedHandler.
 */
public class GetRecentlyModifiedHandler implements ActionHandler<GetRecentlyModifiedAction, GetRecentlyModifiedResult> {

	/** The logger. */
	private static final Logger LOGGER = Logger.getLogger(GetRecentlyModifiedHandler.class.getPackage().toString());

	/** The configuration. */
	private final EditorConfiguration configuration;

	/** The recently modified dao. */
	private final RecentlyModifiedItemDAO recentlyModifiedDAO;

	/** The http session provider. */
	@Inject
	private Provider<HttpSession> httpSessionProvider;

	/**
	 * Instantiates a new gets the recently modified handler.
	 * 
	 * @param configuration
	 *          the configuration
	 * @param recentlyModifiedDAO
	 *          the recently modified dao
	 */
	@Inject
	public GetRecentlyModifiedHandler(final EditorConfiguration configuration, final RecentlyModifiedItemDAO recentlyModifiedDAO) {
		this.configuration = configuration;
		this.recentlyModifiedDAO = recentlyModifiedDAO;
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
	public GetRecentlyModifiedResult execute(final GetRecentlyModifiedAction action, final ExecutionContext context) throws ActionException {
		LOGGER.debug("Processing action: GetRecentlyModified");
		String openID = null;
		if (!action.isForAllUsers()) {
			HttpSession session = httpSessionProvider.get();
			openID = (String) session.getAttribute(HttpCookies.SESSION_ID_KEY);
		}
		try {
			return new GetRecentlyModifiedResult(recentlyModifiedDAO.getItems(configuration.getRecentlyModifiedNumber(), openID));
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
	public Class<GetRecentlyModifiedAction> getActionType() {
		return GetRecentlyModifiedAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(GetRecentlyModifiedAction action, GetRecentlyModifiedResult result, ExecutionContext context) throws ActionException {
		// TODO Auto-generated method stub

	}
}