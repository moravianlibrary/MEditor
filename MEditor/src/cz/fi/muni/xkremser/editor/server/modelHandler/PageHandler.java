/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.modelHandler;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.w3c.dom.Document;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.fedora.utils.DCUtils;
import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.DublinCore;
import cz.fi.muni.xkremser.editor.shared.valueobj.PageDetail;

// TODO: Auto-generated Javadoc
/**
 * The Class PageHandler.
 */
public class PageHandler extends DigitalObjectHandler {

	/**
	 * Instantiates a new page handler.
	 * 
	 * @param logger
	 *          the logger
	 * @param fedoraAccess
	 *          the fedora access
	 */
	@Inject
	public PageHandler(Log logger, @Named("securedFedoraAccess") FedoraAccess fedoraAccess) {
		super(logger, fedoraAccess);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.server.modelHandler.DigitalObjectHandler#
	 * getDigitalObject(java.lang.String)
	 */
	@Override
	public AbstractDigitalObjectDetail getDigitalObject(final String uuid, final boolean findRelated) {
		PageDetail detail = new PageDetail(findRelated ? getRelated(uuid) : null);
		DublinCore dc = null;
		Document dcDocument = null;
		try {
			dcDocument = getFedoraAccess().getDC(uuid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (findRelated) {
			dc = DCUtils.getDC(dcDocument);
		} else {
			dc = new DublinCore();
			dc.addTitle(DCUtils.titleFromDC(dcDocument));
			dc.addIdentifier(uuid);
		}
		detail.setDc(dc);

		return detail;
	}

}
