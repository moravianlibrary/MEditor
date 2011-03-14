/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */
package cz.fi.muni.xkremser.editor.server.modelHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	/** The page handler. */
	private transient final PageHandler pageHandler;

	/** The per volume handler. */
	private transient final PeriodicalVolumeHandler perVolumeHandler;

	/**
	 * Instantiates a new periodical handler.
	 * 
	 * @param logger
	 *          the logger
	 * @param fedoraAccess
	 *          the fedora access
	 * @param pageHandler
	 *          the page handler
	 * @param perVolumeHandler
	 *          the per volume handler
	 */
	@Inject
	public PeriodicalHandler(@Named("securedFedoraAccess") FedoraAccess fedoraAccess, PageHandler pageHandler, PeriodicalVolumeHandler perVolumeHandler) {
		super(fedoraAccess);
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
