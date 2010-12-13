/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.handler;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.HttpCookies;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.shared.rpc.action.LogoutAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.LogoutResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class LogoutHandler implements ActionHandler<LogoutAction, LogoutResult> {

	/** The logger. */
	private final Log logger;

	/** The configuration. */
	private final EditorConfiguration configuration;

	private final Provider<HttpSession> httpSessionProvider;

	/**
	 * Instantiates a new put recently modified handler.
	 * 
	 * @param logger
	 *          the logger
	 * @param configuration
	 *          the configuration
	 */
	@Inject
	public LogoutHandler(final Log logger, final EditorConfiguration configuration, Provider<HttpSession> httpSessionProvider) {
		this.logger = logger;
		this.configuration = configuration;
		this.httpSessionProvider = httpSessionProvider;
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
		logger.debug("Processing action: LogoutAction user:" + session.getAttribute(HttpCookies.SESSION_ID_KEY));
		session.setAttribute(HttpCookies.SESSION_ID_KEY, null);
		return new LogoutResult();
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