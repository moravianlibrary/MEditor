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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.shared.rpc.action.SendGreeting;
import cz.fi.muni.xkremser.editor.shared.rpc.result.SendGreetingResult;

// TODO: Auto-generated Javadoc
/**
 * The Class SendGreetingHandler.
 */
public class SendGreetingHandler implements ActionHandler<SendGreeting, SendGreetingResult> {

	/** The logger. */
	private static final Logger LOGGER = Logger.getLogger(SendGreetingHandler.class.getPackage().toString());

	/** The servlet context. */
	private final Provider<ServletContext> servletContext;

	/** The servlet request. */
	private final Provider<HttpServletRequest> servletRequest;

	/** The conf. */
	@Inject
	private EditorConfiguration conf;

	/**
	 * Instantiates a new send greeting handler.
	 * 
	 * @param servletContext
	 *          the servlet context
	 * @param servletRequest
	 *          the servlet request
	 */
	@Inject
	public SendGreetingHandler(final Provider<ServletContext> servletContext, final Provider<HttpServletRequest> servletRequest) {
		this.servletContext = servletContext;
		this.servletRequest = servletRequest;
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
	public SendGreetingResult execute(final SendGreeting action, final ExecutionContext context) throws ActionException {
		final String name = action.getName();

		try {
			String serverInfo = servletContext.get().getServerInfo();

			String userAgent = servletRequest.get().getHeader("User-Agent");

			final String message = "Hello, " + name + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>" + userAgent;

			// final String message = "Hello " + action.getName();
			System.out.println("valim");
			System.out.println(conf.getConfiguration().getProperty("key"));
			LOGGER.error("slapu");
			LOGGER.error(conf.getConfiguration().getProperty("key"));

			return new SendGreetingResult(name, message);
		} catch (Exception cause) {
			LOGGER.error("Unable to send message", cause);

			throw new ActionException(cause);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<SendGreeting> getActionType() {
		return SendGreeting.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(SendGreeting action, SendGreetingResult result, ExecutionContext context) throws ActionException {
		// TODO Auto-generated method stub

	}
}