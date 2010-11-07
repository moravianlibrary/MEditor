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
import cz.fi.muni.xkremser.editor.shared.valueobj.DublinCore;
import cz.fi.muni.xkremser.editor.shared.valueobj.InternalPartDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PageDetail;

// TODO: Auto-generated Javadoc
/**
 * The Class InternalPartHandler.
 */
public class InternalPartHandler extends DigitalObjectHandler {

	/** The page handler. */
	private transient final PageHandler pageHandler;

	/**
	 * Instantiates a new internal part handler.
	 * 
	 * @param logger
	 *          the logger
	 * @param fedoraAccess
	 *          the fedora access
	 * @param pageHandler
	 *          the page handler
	 */
	@Inject
	public InternalPartHandler(Log logger, @Named("securedFedoraAccess") FedoraAccess fedoraAccess, PageHandler pageHandler) {
		super(logger, fedoraAccess);
		this.pageHandler = pageHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.server.modelHandler.DigitalObjectHandler#
	 * getDigitalObject(java.lang.String)
	 */
	@Override
	public AbstractDigitalObjectDetail getDigitalObject(String uuid, final boolean findRelated) {
		InternalPartDetail detail = new InternalPartDetail(findRelated ? getRelated(uuid) : null);
		DublinCore dc = null;
		ArrayList<PageDetail> pages = new ArrayList<PageDetail>();
		Document dcDocument = null;
		try {
			dcDocument = getFedoraAccess().getDC(uuid);
			if (findRelated) {
				dc = DCUtils.getDC(dcDocument);
			} else {
				dc = new DublinCore();
				dc.addTitle(DCUtils.titleFromDC(dcDocument));
				dc.addIdentifier(uuid);
			}
			List<String> pageUuids = getFedoraAccess().getIsOnPagesUuid(uuid);
			// List<String> pageUuids = getFedoraAccess().getPagesUuid(uuid);
			for (String pageUuid : pageUuids) {
				pages.add((PageDetail) pageHandler.getDigitalObject(pageUuid, false));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		detail.setDc(dc);
		detail.setPages(pages);

		return detail;
	}

}
