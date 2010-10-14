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

public class GetDigitalObjectDetailHandler implements ActionHandler<GetDigitalObjectDetailAction, GetDigitalObjectDetailResult> {
	private final Log logger;
	private final DigitalObjectHandler handler;
	@Inject
	Injector injector;

	@Inject
	public GetDigitalObjectDetailHandler(final Log logger, final DigitalObjectHandler handler) {
		this.logger = logger;
		this.handler = handler;
	}

	@Override
	public GetDigitalObjectDetailResult execute(final GetDigitalObjectDetailAction action, final ExecutionContext context) throws ActionException {
		// parse input
		String uuid = action.getUuid();
		return new GetDigitalObjectDetailResult(handler.getDigitalObject(uuid));
	}

	@Override
	public Class<GetDigitalObjectDetailAction> getActionType() {
		return GetDigitalObjectDetailAction.class;
	}

	@Override
	public void undo(GetDigitalObjectDetailAction action, GetDigitalObjectDetailResult result, ExecutionContext context) throws ActionException {
		// idempotency -> no need for undo
	}
}