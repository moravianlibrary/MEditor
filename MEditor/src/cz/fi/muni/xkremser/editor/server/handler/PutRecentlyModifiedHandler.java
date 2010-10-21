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

public class PutRecentlyModifiedHandler implements ActionHandler<PutRecentlyModifiedAction, PutRecentlyModifiedResult> {
	private final Log logger;
	private final EditorConfiguration configuration;

	@Inject
	private RecentlyModifiedItemDAO recentlyModifiedDAO;

	@Inject
	public PutRecentlyModifiedHandler(final Log logger, final EditorConfiguration configuration) {
		this.logger = logger;
		this.configuration = configuration;
	}

	@Override
	public PutRecentlyModifiedResult execute(final PutRecentlyModifiedAction action, final ExecutionContext context) throws ActionException {
		if (action.getItem() == null)
			throw new NullPointerException("getItem()");
		if (action.getItem().getUuid() == null || "".equals(action.getItem().getUuid()))
			throw new NullPointerException("getItem().getUuid()");
		logger.debug("Processing action: PutRecentlyModified item:" + action.getItem());
		return new PutRecentlyModifiedResult(recentlyModifiedDAO.put(action.getItem()));
	}

	@Override
	public Class<PutRecentlyModifiedAction> getActionType() {
		return PutRecentlyModifiedAction.class;
	}

	@Override
	public void undo(PutRecentlyModifiedAction action, PutRecentlyModifiedResult result, ExecutionContext context) throws ActionException {
		// TODO undo method

	}
}