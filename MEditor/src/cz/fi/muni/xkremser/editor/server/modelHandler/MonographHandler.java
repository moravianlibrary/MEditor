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
import cz.fi.muni.xkremser.editor.shared.valueobj.MonographDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.MonographUnitDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PageDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class MonographHandler.
 */
public class MonographHandler extends DigitalObjectHandler implements CanGetObject {
	private transient final PageHandler pageHandler;
	private transient final MonographUnitHandler monUnitHandler;
	private transient final InternalPartHandler intPartHandler;

	/**
	 * Instantiates a new monograph handler.
	 * 
	 * @param logger
	 *          the logger
	 * @param fedoraAccess
	 *          the fedora access
	 */
	@Inject
	public MonographHandler(Log logger, @Named("securedFedoraAccess") FedoraAccess fedoraAccess, PageHandler pageHandler, MonographUnitHandler monUnitHandler,
			InternalPartHandler intPartHandler) {
		super(logger, fedoraAccess);
		this.pageHandler = pageHandler;
		this.intPartHandler = intPartHandler;
		this.monUnitHandler = monUnitHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.server.modelHandler.DigitalObjectHandler#
	 * getDigitalObject(java.lang.String)
	 */
	@Override
	public AbstractDigitalObjectDetail getDigitalObject(String uuid, final boolean findRelated) {
		MonographDetail detail = new MonographDetail(findRelated ? getRelated(uuid) : null);
		DublinCore dc = null;
		ArrayList<PageDetail> pages = new ArrayList<PageDetail>();
		ArrayList<InternalPartDetail> intParts = new ArrayList<InternalPartDetail>();
		ArrayList<MonographUnitDetail> monUnits = new ArrayList<MonographUnitDetail>();
		Document dcDocument = null;
		try {
			dcDocument = getFedoraAccess().getDC(uuid);
			if (findRelated) {
				dc = DCUtils.getDC(dcDocument);
				List<String> pageUuids = getFedoraAccess().getPagesUuid(uuid);
				for (String pageUuid : pageUuids) {
					pages.add((PageDetail) pageHandler.getDigitalObject(pageUuid, false));
				}
				List<String> internalPartsUuids = getFedoraAccess().getIntCompPartsUuid(uuid);
				for (String intPartUuid : internalPartsUuids) {
					intParts.add((InternalPartDetail) intPartHandler.getDigitalObject(intPartUuid, false));
				}
				List<String> monographUnitsUuids = getFedoraAccess().getMonographUnitsUuid(uuid);
				for (String monUnitUuid : monographUnitsUuids) {
					monUnits.add((MonographUnitDetail) monUnitHandler.getDigitalObject(monUnitUuid, false));
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
		detail.setMonUnits(monUnits);

		return detail;
	}

}
