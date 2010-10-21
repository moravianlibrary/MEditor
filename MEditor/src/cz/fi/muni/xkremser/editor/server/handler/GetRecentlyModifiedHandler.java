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

public class GetRecentlyModifiedHandler implements ActionHandler<GetRecentlyModifiedAction, GetRecentlyModifiedResult> {
	private final Log logger;
	private final EditorConfiguration configuration;
	private final RecentlyModifiedItemDAO recentlyModifiedDAO;

	@Inject
	public GetRecentlyModifiedHandler(final Log logger, final EditorConfiguration configuration, final RecentlyModifiedItemDAO recentlyModifiedDAO) {
		this.logger = logger;
		this.configuration = configuration;
		this.recentlyModifiedDAO = recentlyModifiedDAO;
	}

	@Override
	public GetRecentlyModifiedResult execute(final GetRecentlyModifiedAction action, final ExecutionContext context) throws ActionException {
		logger.debug("Processing action: GetRecentlyModified");
		return new GetRecentlyModifiedResult(recentlyModifiedDAO.getItems(configuration.getRecentlyModifiedNumber(), action.isForAllUsers()));
	}

	@Override
	public Class<GetRecentlyModifiedAction> getActionType() {
		return GetRecentlyModifiedAction.class;
	}

	@Override
	public void undo(GetRecentlyModifiedAction action, GetRecentlyModifiedResult result, ExecutionContext context) throws ActionException {
		// TODO Auto-generated method stub

	}
}