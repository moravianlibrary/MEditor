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

import cz.fi.muni.xkremser.editor.server.DAO.RecentlyModifiedItemDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRecentlyModifiedAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRecentlyModifiedResult;

// TODO: Auto-generated Javadoc
/**
 * The Class GetRecentlyModifiedHandler.
 */
public class GetRecentlyModifiedHandler implements ActionHandler<GetRecentlyModifiedAction, GetRecentlyModifiedResult> {
	
	/** The logger. */
	private final Log logger;
	
	/** The configuration. */
	private final EditorConfiguration configuration;
	
	/** The recently modified dao. */
	private final RecentlyModifiedItemDAO recentlyModifiedDAO;

	/**
	 * Instantiates a new gets the recently modified handler.
	 *
	 * @param logger the logger
	 * @param configuration the configuration
	 * @param recentlyModifiedDAO the recently modified dao
	 */
	@Inject
	public GetRecentlyModifiedHandler(final Log logger, final EditorConfiguration configuration, final RecentlyModifiedItemDAO recentlyModifiedDAO) {
		this.logger = logger;
		this.configuration = configuration;
		this.recentlyModifiedDAO = recentlyModifiedDAO;
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com.gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public GetRecentlyModifiedResult execute(final GetRecentlyModifiedAction action, final ExecutionContext context) throws ActionException {
		logger.debug("Processing action: GetRecentlyModified");
		return new GetRecentlyModifiedResult(recentlyModifiedDAO.getItems(configuration.getRecentlyModifiedNumber(), action.isForAllUsers()));
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<GetRecentlyModifiedAction> getActionType() {
		return GetRecentlyModifiedAction.class;
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result, com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(GetRecentlyModifiedAction action, GetRecentlyModifiedResult result, ExecutionContext context) throws ActionException {
		// TODO Auto-generated method stub

	}
}