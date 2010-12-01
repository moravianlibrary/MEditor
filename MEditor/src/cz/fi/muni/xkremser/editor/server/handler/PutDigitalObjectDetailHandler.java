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

import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.server.fedora.utils.BiblioModsUtils;
import cz.fi.muni.xkremser.editor.server.modelHandler.DigitalObjectHandler;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutDigitalObjectDetailAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutDigitalObjectDetailResult;

// TODO: Auto-generated Javadoc
/**
 * The Class GetDigitalObjectDetailHandler.
 */
public class PutDigitalObjectDetailHandler implements ActionHandler<PutDigitalObjectDetailAction, PutDigitalObjectDetailResult> {

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
	public PutDigitalObjectDetailHandler(final Log logger, final DigitalObjectHandler handler) {
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
	public PutDigitalObjectDetailResult execute(final PutDigitalObjectDetailAction action, final ExecutionContext context) throws ActionException {
		ModsCollectionClient modsCollection = action.getDetail().getMods();
		// parse input
		BiblioModsUtils.toXML(BiblioModsUtils.toMods(modsCollection));

		return new PutDigitalObjectDetailResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<PutDigitalObjectDetailAction> getActionType() {
		return PutDigitalObjectDetailAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(PutDigitalObjectDetailAction action, PutDigitalObjectDetailResult result, ExecutionContext context) throws ActionException {
		// idempotency -> no need for undo
	}
}