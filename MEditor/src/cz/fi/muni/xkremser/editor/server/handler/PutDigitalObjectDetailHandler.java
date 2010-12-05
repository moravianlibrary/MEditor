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

import cz.fi.muni.xkremser.editor.client.KrameriusModel;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.server.fedora.RDFModels;
import cz.fi.muni.xkremser.editor.server.fedora.utils.BiblioModsUtils;
import cz.fi.muni.xkremser.editor.server.modelHandler.DigitalObjectHandler;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutDigitalObjectDetailAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutDigitalObjectDetailResult;
import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PageDetail;

// TODO: Auto-generated Javadoc
/**
 * The Class GetDigitalObjectDetailHandler.
 */
public class PutDigitalObjectDetailHandler implements ActionHandler<PutDigitalObjectDetailAction, PutDigitalObjectDetailResult> {

	/** The logger. */
	private final Log logger;
	private static final String PART_1 = "<kramerius:";
	private static final String PART_2 = " rdf:resource=\"info:fedora/uuid:";
	private static final String PART_3 = "\"></kramerius:";
	private static final String PART_4 = ">\n";

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
		if (action == null || action.getDetail() == null)
			throw new NullPointerException("getDetail()");
		AbstractDigitalObjectDetail detail = action.getDetail();
		// page structure
		if (detail.hasPages()) {
			if (detail.getPages() == null || detail.getPages().size() == 0) {
				System.out.println("no pages");
			} else {
				String relation = RDFModels.convertToRdf(KrameriusModel.PAGE);
				StringBuilder sb = new StringBuilder();
				for (PageDetail page : detail.getPages()) {
					sb.append(PART_1).append(relation).append(PART_2).append(page.getUuid()).append(PART_3).append(relation).append(PART_4);
				}
				System.out.println("pages:\n" + sb.toString());
			}
		}
		// container structure
		if (detail.hasContainers() != 0) {
			for (int i = 0; i < detail.hasContainers(); i++) {
				if (detail.getContainers().size() <= i || detail.getContainers().get(i) == null || detail.getContainers().get(i).size() == 0) {
					System.out.println("no container " + i);
				} else {
					String relation = RDFModels.convertToRdf(detail.getChildContainerModels().get(i));
					StringBuilder sb = new StringBuilder();
					for (AbstractDigitalObjectDetail obj : detail.getContainers().get(i)) {
						sb.append(PART_1).append(relation).append(PART_2).append(obj.getUuid()).append(PART_3).append(relation).append(PART_4);
					}
					System.out.println("container " + i + ":\n" + sb.toString());
				}
			}
		}

		// dublin core
		if (detail.isDcChanged()) {

		}
		// mods
		if (detail.isModsChanged()) {

		}
		ModsCollectionClient modsCollection = detail.getMods();
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