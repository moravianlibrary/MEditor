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

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.client.ConnectionException;
import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.modelHandler.DigitalObjectHandler;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDigitalObjectDetailAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDigitalObjectDetailResult;
import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;

// TODO: Auto-generated Javadoc
/**
 * The Class GetDigitalObjectDetailHandler.
 */
public class GetDigitalObjectDetailHandler implements ActionHandler<GetDigitalObjectDetailAction, GetDigitalObjectDetailResult> {

	/** The logger. */
	private static final Logger LOGGER = Logger.getLogger(GetDigitalObjectDetailHandler.class.getPackage().toString());

	/** The handler. */
	private final DigitalObjectHandler handler;

	/** The http session provider. */
	@Inject
	private Provider<HttpSession> httpSessionProvider;

	/**
	 * Instantiates a new gets the digital object detail handler.
	 * 
	 * @param handler
	 *          the handler
	 */
	@Inject
	public GetDigitalObjectDetailHandler(final DigitalObjectHandler handler) {
		this.handler = handler;
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
	public GetDigitalObjectDetailResult execute(final GetDigitalObjectDetailAction action, final ExecutionContext context) throws ActionException {
		// parse input
		String uuid = action.getUuid();
		LOGGER.debug("Processing action: GetDigitalObjectDetailAction: " + action.getUuid());

		try {
			ServerUtils.checkExpiredSession(httpSessionProvider);
			AbstractDigitalObjectDetail obj = handler.getDigitalObject(uuid, true);
			return new GetDigitalObjectDetailResult(obj, action.isRefreshIn());
		} catch (IOException e) {
			String msg = null;
			if (ServerUtils.isCausedByException(e, FileNotFoundException.class)) {
				msg = "Digital object with uuid " + uuid + " is not present in the repository. ";
			} else if (ServerUtils.isCausedByException(e, ConnectionException.class)) {
				msg = "Connection cannot be established. Please check whether Fedora is running. ";
			} else {
				msg = "Unable to obtain digital object with uuid " + uuid + ". ";
			}
			throw new ActionException(msg);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<GetDigitalObjectDetailAction> getActionType() {
		return GetDigitalObjectDetailAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(GetDigitalObjectDetailAction action, GetDigitalObjectDetailResult result, ExecutionContext context) throws ActionException {
		// idempotency -> no need for undo
	}
}