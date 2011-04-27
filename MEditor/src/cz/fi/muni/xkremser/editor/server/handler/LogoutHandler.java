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

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.HttpCookies;
import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.URLS;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration.ServerConstants;
import cz.fi.muni.xkremser.editor.shared.rpc.action.LogoutAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.LogoutResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class LogoutHandler implements ActionHandler<LogoutAction, LogoutResult> {

	/** The logger. */
	private static final Logger LOGGER = Logger.getLogger(LogoutHandler.class.getPackage().toString());
	private static final Logger ACCESS_LOGGER = Logger.getLogger(ServerConstants.ACCESS_LOG_ID);
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

	/** The http session provider. */
	private final Provider<HttpSession> httpSessionProvider;

	private final Provider<HttpServletRequest> reqProvider;

	/**
	 * Instantiates a new put recently modified handler.
	 * 
	 * @param httpSessionProvider
	 *          the http session provider
	 */
	@Inject
	public LogoutHandler(Provider<HttpSession> httpSessionProvider, Provider<HttpServletRequest> reqProvider) {
		this.httpSessionProvider = httpSessionProvider;
		this.reqProvider = reqProvider;
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
	public LogoutResult execute(final LogoutAction action, final ExecutionContext context) throws ActionException {
		HttpSession session = httpSessionProvider.get();
		LOGGER.debug("Processing action: LogoutAction");
		ServerUtils.checkExpiredSession(httpSessionProvider);

		ACCESS_LOGGER.info("LOG OUT: [" + FORMATTER.format(new Date()) + "] User " + session.getAttribute(HttpCookies.NAME_KEY) + " with openID "
				+ session.getAttribute(HttpCookies.SESSION_ID_KEY) + " and IP " + reqProvider.get().getRemoteAddr());
		session.setAttribute(HttpCookies.SESSION_ID_KEY, null);
		session.invalidate();
		return new LogoutResult(URLS.ROOT() + (URLS.LOCALHOST() ? URLS.LOGIN_LOCAL_PAGE : URLS.LOGIN_PAGE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<LogoutAction> getActionType() {
		return LogoutAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(LogoutAction action, LogoutResult result, ExecutionContext context) throws ActionException {
		// TODO undo method

	}
}