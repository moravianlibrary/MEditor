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
import org.w3c.dom.Document;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.fedora.utils.DCUtils;
import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.InternalPartDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PageDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PeriodicalItemDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class MonographUnitHandler.
 */
public class MonographUnitHandler extends DigitalObjectHandler {
	private transient final PageHandler pageHandler;
	private transient final InternalPartHandler intPartHandler;

	/**
	 * Instantiates a new monograph unit handler.
	 * 
	 * @param logger
	 *          the logger
	 * @param fedoraAccess
	 *          the fedora access
	 */
	@Inject
	public MonographUnitHandler(Log logger, @Named("securedFedoraAccess") FedoraAccess fedoraAccess, PageHandler pageHandler, InternalPartHandler intPartHandler) {
		super(logger, fedoraAccess);
		this.pageHandler = pageHandler;
		this.intPartHandler = intPartHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.server.modelHandler.DigitalObjectHandler#
	 * getDigitalObject(java.lang.String)
	 */
	@Override
	public AbstractDigitalObjectDetail getDigitalObject(String uuid, final boolean findRelated) {

		// TODO: create MonographUnitDetail
		PeriodicalItemDetail detail = new PeriodicalItemDetail(findRelated ? getRelated(uuid) : null);
		DublinCore dc = null;
		ArrayList<PageDetail> pages = new ArrayList<PageDetail>();
		ArrayList<InternalPartDetail> intParts = new ArrayList<InternalPartDetail>();
		Document dcDocument = null;
		try {
			dcDocument = getFedoraAccess().getDC(uuid);
			if (findRelated) {
				dc = DCUtils.getDC(dcDocument);
				List<String> pageUuids = getFedoraAccess().getPagesUuid(uuid);
				for (String pageUuid : pageUuids) {
					pages.add((PageDetail) pageHandler.getDigitalObject(pageUuid, false));
				}
				List<String> internalPartsUuids = getFedoraAccess().getIntCompPartUuid(uuid);
				for (String intPartUuid : internalPartsUuids) {
					intParts.add((InternalPartDetail) intPartHandler.getDigitalObject(intPartUuid, false));
				}
			} else {
				dc = new DublinCore();
				dc.addTitle(DCUtils.titleFromDC(dcDocument));
				dc.addIdentifier(uuid);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		detail.setDc(dc);
		detail.setPages(pages);
		detail.setIntParts(intParts);

		return detail;
	}

}
