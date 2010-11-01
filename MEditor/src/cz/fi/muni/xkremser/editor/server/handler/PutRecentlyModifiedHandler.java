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
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutRecentlyModifiedAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutRecentlyModifiedResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class PutRecentlyModifiedHandler implements ActionHandler<PutRecentlyModifiedAction, PutRecentlyModifiedResult> {
	
	/** The logger. */
	private final Log logger;
	
	/** The configuration. */
	private final EditorConfiguration configuration;

	/** The recently modified dao. */
	@Inject
	private RecentlyModifiedItemDAO recentlyModifiedDAO;

	/**
	 * Instantiates a new put recently modified handler.
	 *
	 * @param logger the logger
	 * @param configuration the configuration
	 */
	@Inject
	public PutRecentlyModifiedHandler(final Log logger, final EditorConfiguration configuration) {
		this.logger = logger;
		this.configuration = configuration;
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com.gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public PutRecentlyModifiedResult execute(final PutRecentlyModifiedAction action, final ExecutionContext context) throws ActionException {
		if (action.getItem() == null)
			throw new NullPointerException("getItem()");
		if (action.getItem().getUuid() == null || "".equals(action.getItem().getUuid()))
			throw new NullPointerException("getItem().getUuid()");
		logger.debug("Processing action: PutRecentlyModified item:" + action.getItem());
		return new PutRecentlyModifiedResult(recentlyModifiedDAO.put(action.getItem()));
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<PutRecentlyModifiedAction> getActionType() {
		return PutRecentlyModifiedAction.class;
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result, com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(PutRecentlyModifiedAction action, PutRecentlyModifiedResult result, ExecutionContext context) throws ActionException {
		// TODO undo method

	}
}