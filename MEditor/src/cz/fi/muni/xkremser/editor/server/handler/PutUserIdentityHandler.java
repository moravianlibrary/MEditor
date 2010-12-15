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
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutUserIdentityAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutUserIdentityResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class PutUserIdentityHandler implements ActionHandler<PutUserIdentityAction, PutUserIdentityResult> {

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
	public PutUserIdentityHandler(final Log logger, final EditorConfiguration configuration) {
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
	public PutUserIdentityResult execute(final PutUserIdentityAction action, final ExecutionContext context) throws ActionException {
		if (action.getIdentity() == null)
			throw new NullPointerException("getIdentity()");
		if (action.getUserId() == null || "".equals(action.getUserId()))
			throw new NullPointerException("getUserId()");
		logger.debug("Processing action: PutUserIdentityAction identity:" + action.getIdentity());
		String id = userDAO.addUserIdentity(action.getIdentity(), Long.parseLong(action.getUserId()));
		return new PutUserIdentityResult(id, "exist".equals(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<PutUserIdentityAction> getActionType() {
		return PutUserIdentityAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(PutUserIdentityAction action, PutUserIdentityResult result, ExecutionContext context) throws ActionException {
		// TODO undo method

	}
}