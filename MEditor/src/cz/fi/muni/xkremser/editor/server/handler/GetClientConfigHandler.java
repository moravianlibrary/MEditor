package cz.fi.muni.xkremser.editor.server.handler;

import java.util.HashMap;
import java.util.Iterator;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;

import cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetClientConfig;
import cz.fi.muni.xkremser.editor.shared.rpc.result.GetClientConfigResult;

public class GetClientConfigHandler implements ActionHandler<GetClientConfig, GetClientConfigResult> {
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
	public GetClientConfigResult execute(final GetClientConfig action, final ExecutionContext context) throws ActionException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Iterator<String> it = configuration.getClientConfiguration().getKeys();
		while (it.hasNext()) {
			String key = it.next();
			result.put(key, configuration.getConfiguration().getProperty(key));
		}

		return new GetClientConfigResult(result);
	}

	@Override
	public void rollback(final GetClientConfig action, final GetClientConfigResult result, final ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<GetClientConfig> getActionType() {
		return GetClientConfig.class;
	}
}