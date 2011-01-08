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
public class PeriodicalVolumeDetail extends AbstractDigitalObjectDetail {

	/** The int parts. */
	private ArrayList<InternalPartDetail> intParts;

	/** The per items. */
	private ArrayList<PeriodicalItemDetail> perItems;

	/**
	 * Instantiates a new periodical volume detail.
	 */
	@SuppressWarnings("unused")
	private PeriodicalVolumeDetail() {
		super();
	}

	/**
	 * Instantiates a new periodical volume detail.
	 *
	 * @param related the related
	 */
	public PeriodicalVolumeDetail(ArrayList<ArrayList<String>> related) {
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
		return KrameriusModel.PERIODICALVOLUME;
	}

	/**
	 * Sets the int parts.
	 *
	 * @param intParts the new int parts
	 */
	public void setIntParts(ArrayList<InternalPartDetail> intParts) {
		this.intParts = intParts;
		getContainers().add(0, intParts);
	}

	/**
	 * Sets the per items.
	 *
	 * @param perItems the new per items
	 */
	public void setPerItems(ArrayList<PeriodicalItemDetail> perItems) {
		this.perItems = perItems;
		getContainers().add(perItems);
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
		return 2;
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail#getChildContainerModels()
	 */
	@Override
	public List<KrameriusModel> getChildContainerModels() {
		return new ArrayList<KrameriusModel>() {
			{
				add(KrameriusModel.INTERNALPART);
				add(KrameriusModel.PERIODICALITEM);
			}
		};
	}

	// handle
	// policy

}
