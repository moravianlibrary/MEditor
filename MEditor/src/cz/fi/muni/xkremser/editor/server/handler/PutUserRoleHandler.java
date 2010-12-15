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
import cz.fi.muni.xkremser.editor.shared.rpc.RoleItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutUserRoleAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutUserRoleResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class PutUserRoleHandler implements ActionHandler<PutUserRoleAction, PutUserRoleResult> {

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
	public PutUserRoleHandler(final Log logger, final EditorConfiguration configuration) {
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
	public PutUserRoleResult execute(final PutUserRoleAction action, final ExecutionContext context) throws ActionException {
		if (action.getRole() == null)
			throw new NullPointerException("getRole()");
		if (action.getUserId() == null || "".equals(action.getUserId()))
			throw new NullPointerException("getUserId()");
		logger.debug("Processing action: PutUserRoleAction role:" + action.getRole());
		RoleItem role = userDAO.addUserRole(action.getRole(), Long.parseLong(action.getUserId()));
		return new PutUserRoleResult(role.getId(), "exist".equals(role.getId()), role.getDescription());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<PutUserRoleAction> getActionType() {
		return PutUserRoleAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(PutUserRoleAction action, PutUserRoleResult result, ExecutionContext context) throws ActionException {
		// TODO undo method

	}
}