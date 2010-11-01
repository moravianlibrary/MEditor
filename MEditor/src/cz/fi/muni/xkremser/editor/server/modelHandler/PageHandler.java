/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.modelHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.w3c.dom.Document;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import cz.fi.muni.xkremser.editor.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.fedora.utils.DCUtils;
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
	 * @param logger the logger
	 * @param fedoraAccess the fedora access
	 */
	@Inject
	public PageHandler(Log logger, @Named("securedFedoraAccess") FedoraAccess fedoraAccess) {
		super(logger, fedoraAccess);
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.server.modelHandler.DigitalObjectHandler#getDigitalObject(java.lang.String)
	 */
	@Override
	public AbstractDigitalObjectDetail getDigitalObject(final String uuid) {
		PageDetail detail = new PageDetail();
		DublinCore dc = new DublinCore();
		Document dcDocument = null;
		try {
			dcDocument = getFedoraAccess().getDC(uuid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dc.setTitle(DCUtils.titleFromDC(dcDocument));
		dc.setCreator(Arrays.asList(DCUtils.creatorsFromDC(dcDocument)));
		ArrayList<String> ids = new ArrayList<String>();
		ids.add(uuid);
		dc.setIdentifier(ids);
		detail.setDc(dc);

		return detail;
	}

}
