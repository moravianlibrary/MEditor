/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.handler;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.DAO.UserDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserRoleAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserRoleResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class RemoveUserRoleHandler implements ActionHandler<RemoveUserRoleAction, RemoveUserRoleResult> {

	/** The logger. */
	private final Log logger;

	/** The configuration. */
	private final EditorConfiguration configuration;

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
	public RemoveUserRoleHandler(final Log logger, final EditorConfiguration configuration) {
		this.logger = logger;
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
	public RemoveUserRoleResult execute(final RemoveUserRoleAction action, final ExecutionContext context) throws ActionException {
		if (action.getId() == null)
			throw new NullPointerException("getId()");
		logger.debug("Processing action: RemoveUserRoleAction user id:" + action.getId());
		userDAO.removeUserRole(Long.parseLong(action.getId()));
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