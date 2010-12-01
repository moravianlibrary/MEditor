/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.modelHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.InternalPartDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PageDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PeriodicalItemDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PeriodicalVolumeDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class PeriodicalHandler.
 */
public class PeriodicalVolumeHandler extends DigitalObjectHandler {
	private transient final PageHandler pageHandler;
	private transient final PeriodicalItemHandler perItemHandler;
	private transient final InternalPartHandler intPartHandler;

	/**
	 * Instantiates a new periodical handler.
	 * 
	 * @param logger
	 *          the logger
	 * @param fedoraAccess
	 *          the fedora access
	 */
	@Inject
	public PeriodicalVolumeHandler(Log logger, @Named("securedFedoraAccess") FedoraAccess fedoraAccess, PageHandler pageHandler,
			InternalPartHandler intPartHandler, PeriodicalItemHandler perItemHandler) {
		super(logger, fedoraAccess);
		this.pageHandler = pageHandler;
		this.intPartHandler = intPartHandler;
		this.perItemHandler = perItemHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.server.modelHandler.DigitalObjectHandler#
	 * getDigitalObject(java.lang.String)
	 */
	@Override
	public AbstractDigitalObjectDetail getDigitalObject(String uuid, final boolean findRelated) {
		PeriodicalVolumeDetail detail = new PeriodicalVolumeDetail(findRelated ? getRelated(uuid) : null);
		DublinCore dc = handleDc(uuid, findRelated);
		ArrayList<PageDetail> pages = new ArrayList<PageDetail>();
		ArrayList<InternalPartDetail> intParts = new ArrayList<InternalPartDetail>();
		ArrayList<PeriodicalItemDetail> perItems = new ArrayList<PeriodicalItemDetail>();
		try {
			if (findRelated) {
				List<String> pageUuids = getFedoraAccess().getPagesUuid(uuid);
				for (String pageUuid : pageUuids) {
					pages.add((PageDetail) pageHandler.getDigitalObject(pageUuid, false));
				}
				List<String> internalPartsUuids = getFedoraAccess().getIntCompPartsUuid(uuid);
				for (String intPartUuid : internalPartsUuids) {
					intParts.add((InternalPartDetail) intPartHandler.getDigitalObject(intPartUuid, false));
				}
				List<String> periodicalItemsUuids = getFedoraAccess().getPeriodicalItemsUuid(uuid);
				for (String perItemUuid : periodicalItemsUuids) {
					perItems.add((PeriodicalItemDetail) perItemHandler.getDigitalObject(perItemUuid, false));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		detail.setDc(dc);
		detail.setPages(pages);
		detail.setIntParts(intParts);
		detail.setPerItems(perItems);

		return detail;
	}

}
