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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLConnection;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.utils.RESTHelper;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAvailability;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAvailabilityAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAvailabilityResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class CheckAvailabilityHandler implements ActionHandler<CheckAvailabilityAction, CheckAvailabilityResult> {

	/** The logger. */
	private static final Logger LOGGER = Logger.getLogger(CheckAvailabilityHandler.class.getPackage().toString());

	/** The configuration. */
	private final EditorConfiguration configuration;

	/** The http session provider. */
	@Inject
	private Provider<HttpSession> httpSessionProvider;

	/**
	 * Instantiates a new put recently modified handler.
	 * 
	 * @param configuration
	 *          the configuration
	 */
	@Inject
	public CheckAvailabilityHandler(final EditorConfiguration configuration) {
		this.configuration = configuration;
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
	public CheckAvailabilityResult execute(final CheckAvailabilityAction action, final ExecutionContext context) throws ActionException {
		if (LOGGER.isDebugEnabled()) {
			String serverName = null;
			if (action.getServerId() == CheckAvailability.FEDORA_ID) {
				serverName = "fedora";
			} else if (action.getServerId() == CheckAvailability.KRAMERIUS_ID) {
				serverName = "kramerius";
			}
			LOGGER.debug("Processing action: CheckAvailability: " + serverName);
		}
		ServerUtils.checkExpiredSession(httpSessionProvider);

		boolean status = true;
		String url = null;
		String usr = "";
		String pass = "";
		if (action.getServerId() == CheckAvailability.FEDORA_ID) {
			url = configuration.getFedoraHost();
			usr = configuration.getFedoraLogin();
			pass = configuration.getFedoraPassword();
		} else if (action.getServerId() == CheckAvailability.KRAMERIUS_ID) {
			url = configuration.getKrameriusHost();
		} else {
			throw new ActionException("Unknown server id");
		}
		try {
			URLConnection con = RESTHelper.openConnection(url, usr, pass);
			if (con instanceof HttpURLConnection) {
				HttpURLConnection httpConnection = (HttpURLConnection) con;
				if (httpConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
					status = false;
					LOGGER.info("Server " + url + " answered with HTTP code " + httpConnection.getResponseCode());
				}
			} else {
				status = false;
			}
		} catch (MalformedURLException e) {
			status = false;
			e.printStackTrace();
		} catch (IOException e) {
			status = false;
			e.printStackTrace();
		}
		return new CheckAvailabilityResult(status, url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<CheckAvailabilityAction> getActionType() {
		return CheckAvailabilityAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(CheckAvailabilityAction action, CheckAvailabilityResult result, ExecutionContext context) throws ActionException {
		// TODO undo method

	}
}