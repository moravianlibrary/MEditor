/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.handler;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.client.ConnectionException;
import cz.fi.muni.xkremser.editor.server.ServerUtils;
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
	 * @param logger
	 *          the logger
	 * @param handler
	 *          the handler
	 */
	@Inject
	public GetDigitalObjectDetailHandler(final Log logger, final DigitalObjectHandler handler) {
		this.logger = logger;
		this.handler = handler;
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
	public GetDigitalObjectDetailResult execute(final GetDigitalObjectDetailAction action, final ExecutionContext context) throws ActionException {
		// parse input
		String uuid = action.getUuid();
		// List<RelationshipTuple> triplets = FedoraUtils.getSubjectPids("uuid:" +
		// uuid);
		// for (RelationshipTuple triplet : triplets) {
		// System.out.println(triplet);
		// }
		try {
			return new GetDigitalObjectDetailResult(handler.getDigitalObject(uuid, true));
		} catch (IOException e) {
			String msg = null;
			if (ServerUtils.isCausedByException(e, FileNotFoundException.class)) {
				msg = "Digital object with uuid " + uuid + " is not present in the repository. ";
			} else if (ServerUtils.isCausedByException(e, ConnectionException.class)) {
				msg = "Connection cannot be established. Please check whether Fedora is running. ";
			} else {
				msg = "Unable to obtain digital object with uuid " + uuid + ". ";
			}
			throw new ActionException(msg);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<GetDigitalObjectDetailAction> getActionType() {
		return GetDigitalObjectDetailAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(GetDigitalObjectDetailAction action, GetDigitalObjectDetailResult result, ExecutionContext context) throws ActionException {
		// idempotency -> no need for undo
	}
}