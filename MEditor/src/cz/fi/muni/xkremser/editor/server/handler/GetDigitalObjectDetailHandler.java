/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.handler;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.modelHandler.DigitalObjectHandler;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDigitalObjectDetailAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDigitalObjectDetailResult;

// TODO: Auto-generated Javadoc
/**
 * The Class GetDigitalObjectDetailHandler.
 */
public class GetDigitalObjectDetailHandler implements ActionHandler<GetDigitalObjectDetailAction, GetDigitalObjectDetailResult> {
	
	/** The logger. */
	private final Log logger;
	
	/** The handler. */
	private final DigitalObjectHandler handler;
	
	/** The injector. */
	@Inject
	Injector injector;

	/**
	 * Instantiates a new gets the digital object detail handler.
	 *
	 * @param logger the logger
	 * @param handler the handler
	 */
	@Inject
	public GetDigitalObjectDetailHandler(final Log logger, final DigitalObjectHandler handler) {
		this.logger = logger;
		this.handler = handler;
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com.gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public GetDigitalObjectDetailResult execute(final GetDigitalObjectDetailAction action, final ExecutionContext context) throws ActionException {
		// parse input
		String uuid = action.getUuid();
		return new GetDigitalObjectDetailResult(handler.getDigitalObject(uuid));
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<GetDigitalObjectDetailAction> getActionType() {
		return GetDigitalObjectDetailAction.class;
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result, com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(GetDigitalObjectDetailAction action, GetDigitalObjectDetailResult result, ExecutionContext context) throws ActionException {
		// idempotency -> no need for undo
	}
}