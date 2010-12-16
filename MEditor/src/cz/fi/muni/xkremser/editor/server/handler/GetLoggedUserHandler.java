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
import cz.fi.muni.xkremser.editor.server.DAO.UserDAO;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetLoggedUserAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetLoggedUserResult;

// TODO: Auto-generated Javadoc
/**
 * The Class GetRecentlyModifiedHandler.
 */
public class GetLoggedUserHandler implements ActionHandler<GetLoggedUserAction, GetLoggedUserResult> {

	/** The logger. */
	private final Log logger;

	/** The recently modified dao. */
	private final UserDAO userDAO;

	private final Provider<HttpSession> httpSessionProvider;

	/**
	 * Instantiates a new gets the recently modified handler.
	 * 
	 * @param logger
	 *          the logger
	 * @param configuration
	 *          the configuration
	 * @param recentlyModifiedDAO
	 *          the recently modified dao
	 */
	@Inject
	public GetLoggedUserHandler(final Log logger, final UserDAO userDAO, Provider<HttpSession> httpSessionProvider) {
		this.logger = logger;
		this.userDAO = userDAO;
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
	public GetLoggedUserResult execute(final GetLoggedUserAction action, final ExecutionContext context) throws ActionException {
		logger.debug("Processing action: GetLoggedUserAction");
		HttpSession session = httpSessionProvider.get();
		String openID = (String) session.getAttribute(HttpCookies.SESSION_ID_KEY);
		boolean editUsers = HttpCookies.ADMIN_YES.equals(session.getAttribute(HttpCookies.ADMIN)) || userDAO.openIDhasRole(UserDAO.EDIT_USERS_STRING, openID);
		return new GetLoggedUserResult(userDAO.getName(openID), editUsers);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<GetLoggedUserAction> getActionType() {
		return GetLoggedUserAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(GetLoggedUserAction action, GetLoggedUserResult result, ExecutionContext context) throws ActionException {
		// TODO Auto-generated method stub

	}
}