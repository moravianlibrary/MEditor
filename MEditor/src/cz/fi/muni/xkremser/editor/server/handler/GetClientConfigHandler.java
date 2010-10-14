package cz.fi.muni.xkremser.editor.server.handler;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetClientConfigAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetClientConfigResult;

public class GetClientConfigHandler implements ActionHandler<GetClientConfigAction, GetClientConfigResult> {
	private final Log logger;
	private final EditorConfiguration configuration;

	@Inject
	private InputQueueItemDAO inputQueueDAO;

	@Inject
	public GetClientConfigHandler(final Log logger, final EditorConfiguration configuration) {
		this.logger = logger;
		this.configuration = configuration;
	}

	@Override
	public GetClientConfigResult execute(final GetClientConfigAction action, final ExecutionContext context) throws ActionException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Iterator<String> it = configuration.getClientConfiguration().getKeys();
		while (it.hasNext()) {
			String key = it.next();
			result.put(key, configuration.getConfiguration().getProperty(EditorConfiguration.Constants.GUI_CONFIGURATION_PPREFIX + '.' + key));
		}

		return new GetClientConfigResult(result);
	}

	@Override
	public Class<GetClientConfigAction> getActionType() {
		return GetClientConfigAction.class;
	}

	@Override
	public void undo(GetClientConfigAction action, GetClientConfigResult result, ExecutionContext context) throws ActionException {
		// TODO Auto-generated method stub

	}
}