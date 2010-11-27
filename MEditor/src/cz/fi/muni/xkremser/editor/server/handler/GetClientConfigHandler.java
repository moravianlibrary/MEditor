/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.handler;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetClientConfigAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetClientConfigResult;

// TODO: Auto-generated Javadoc
/**
 * The Class GetClientConfigHandler.
 */
public class GetClientConfigHandler implements ActionHandler<GetClientConfigAction, GetClientConfigResult> {

	/** The logger. */
	private final Log logger;

	/** The configuration. */
	private final EditorConfiguration configuration;

	/**
	 * Instantiates a new gets the client config handler.
	 * 
	 * @param logger
	 *          the logger
	 * @param configuration
	 *          the configuration
	 */
	@Inject
	public GetClientConfigHandler(final Log logger, final EditorConfiguration configuration) {
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
	public GetClientConfigResult execute(final GetClientConfigAction action, final ExecutionContext context) throws ActionException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Iterator<String> it = configuration.getClientConfiguration().getKeys();
		while (it.hasNext()) {
			String key = it.next();
			result.put(key, configuration.getConfiguration().getProperty(EditorConfiguration.Constants.GUI_CONFIGURATION_PPREFIX + '.' + key));
		}
		result.put(EditorConfiguration.Constants.FEDORA_HOST, configuration.getFedoraHost());
		result.put(EditorConfiguration.Constants.KRAMERIUS_HOST, configuration.getKrameriusHost());
		return new GetClientConfigResult(result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<GetClientConfigAction> getActionType() {
		return GetClientConfigAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(GetClientConfigAction action, GetClientConfigResult result, ExecutionContext context) throws ActionException {
		// TODO Auto-generated method stub

	}
}