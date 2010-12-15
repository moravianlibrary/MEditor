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
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetAllRolesAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetAllRolesResult;

// TODO: Auto-generated Javadoc
/**
 * The Class GetRecentlyModifiedHandler.
 */
public class GetAllRolesHandler implements ActionHandler<GetAllRolesAction, GetAllRolesResult> {

	/** The logger. */
	private final Log logger;

	/** The configuration. */
	private final EditorConfiguration configuration;

	/** The recently modified dao. */
	private final UserDAO userDAO;

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
	public GetAllRolesHandler(final Log logger, final EditorConfiguration configuration, final UserDAO userDAO) {
		this.logger = logger;
		this.configuration = configuration;
		this.userDAO = userDAO;
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
	public GetAllRolesResult execute(final GetAllRolesAction action, final ExecutionContext context) throws ActionException {
		logger.debug("Processing action: GetAllRolesResult");
		// return null;
		return new GetAllRolesResult(userDAO.getRoles());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<GetAllRolesAction> getActionType() {
		return GetAllRolesAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(GetAllRolesAction action, GetAllRolesResult result, ExecutionContext context) throws ActionException {
		// TODO Auto-generated method stub

	}
}