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
import cz.fi.muni.xkremser.editor.shared.valueobj.PageDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PeriodicalDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PeriodicalVolumeDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class PeriodicalHandler.
 */
public class PeriodicalHandler extends DigitalObjectHandler {
	private transient final PageHandler pageHandler;
	private transient final PeriodicalVolumeHandler perVolumeHandler;

	/**
	 * Instantiates a new periodical handler.
	 * 
	 * @param logger
	 *          the logger
	 * @param fedoraAccess
	 *          the fedora access
	 */
	@Inject
	public PeriodicalHandler(Log logger, @Named("securedFedoraAccess") FedoraAccess fedoraAccess, PageHandler pageHandler,
			PeriodicalVolumeHandler perVolumeHandler) {
		super(logger, fedoraAccess);
		this.pageHandler = pageHandler;
		this.perVolumeHandler = perVolumeHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.server.modelHandler.DigitalObjectHandler#
	 * getDigitalObject(java.lang.String)
	 */
	@Override
	public AbstractDigitalObjectDetail getDigitalObject(String uuid, final boolean findRelated) {
		PeriodicalDetail detail = new PeriodicalDetail(findRelated ? getRelated(uuid) : null);
		DublinCore dc = handleDc(uuid, findRelated);
		ArrayList<PageDetail> pages = new ArrayList<PageDetail>();
		ArrayList<PeriodicalVolumeDetail> volumes = new ArrayList<PeriodicalVolumeDetail>();
		try {
			if (findRelated) {
				List<String> pageUuids = getFedoraAccess().getPagesUuid(uuid);
				for (String pageUuid : pageUuids) {
					pages.add((PageDetail) pageHandler.getDigitalObject(pageUuid, false));
				}
				List<String> volumesUuids = getFedoraAccess().getVolumesUuid(uuid);
				for (String volumeUuid : volumesUuids) {
					volumes.add((PeriodicalVolumeDetail) perVolumeHandler.getDigitalObject(volumeUuid, false));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		detail.setDc(dc);
		detail.setPages(pages);
		detail.setPerVolumes(volumes);

		return detail;
	}

}
