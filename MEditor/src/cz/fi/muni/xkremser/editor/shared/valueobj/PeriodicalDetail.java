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
package cz.fi.muni.xkremser.editor.shared.valueobj;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;

// TODO: Auto-generated Javadoc
/**
 * The Class InternalPartDetail.
 */
public class PeriodicalDetail extends AbstractDigitalObjectDetail {

	/** The per volumes. */
	private ArrayList<PeriodicalVolumeDetail> perVolumes;

	/**
	 * Instantiates a new periodical detail.
	 */
	@SuppressWarnings("unused")
	private PeriodicalDetail() {
		super();
	}

	/**
	 * Instantiates a new periodical detail.
	 *
	 * @param related the related
	 */
	public PeriodicalDetail(ArrayList<ArrayList<String>> related) {
		super(related);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail#
	 * getModel()
	 */
	@Override
	public KrameriusModel getModel() {
		return KrameriusModel.PERIODICAL;
	}

	/**
	 * Sets the per volumes.
	 *
	 * @param perVolumes the new per volumes
	 */
	public void setPerVolumes(ArrayList<PeriodicalVolumeDetail> perVolumes) {
		this.perVolumes = perVolumes;
		getContainers().add(perVolumes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail#
	 * hasPages()
	 */
	@Override
	public boolean hasPages() {
		return true;
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail#hasContainers()
	 */
	@Override
	public int hasContainers() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail#getChildContainerModels()
	 */
	@Override
	public List<KrameriusModel> getChildContainerModels() {
		return new ArrayList<KrameriusModel>() {
			{
				add(KrameriusModel.PERIODICALVOLUME);
			}
		};
	}

	// handle
	// policy

}
