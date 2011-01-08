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
package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.BaseDateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.DetailTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.ExtentTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PartTypeClient;

// TODO: Auto-generated Javadoc
/**
 * The Class PartHolder.
 */
public class PartHolder extends ListOfSimpleValuesHolder {
	
	/** The texts. */
	private final ListOfSimpleValuesHolder texts;
	
	/** The dates. */
	private final List<DateHolder> dates;
	
	/** The places. */
	private final List<PlaceHolder> places;
	
	/** The extents. */
	private final List<ExtentHolder> extents;
	
	/** The details. */
	private final List<DetailHolder> details;

	/**
	 * Instantiates a new part holder.
	 */
	public PartHolder() {
		this.texts = new ListOfSimpleValuesHolder();
		dates = new ArrayList<DateHolder>();
		extents = new ArrayList<ExtentHolder>();
		details = new ArrayList<DetailHolder>();
		places = new ArrayList<PlaceHolder>();
	}

	/**
	 * Gets the part.
	 *
	 * @return the part
	 */
	public PartTypeClient getPart() {
		PartTypeClient partTypeClient = new PartTypeClient();
		if (getAttributeForm() != null) {
			partTypeClient.setType(getAttributeForm().getValueAsString(ModsConstants.TYPE));
			partTypeClient.setOrder(getAttributeForm().getValueAsString(ModsConstants.ORDER));
			partTypeClient.setId(getAttributeForm().getValueAsString(ModsConstants.ID));
		}

		partTypeClient.setText(texts.getValues());
		partTypeClient.setDate(getDatesFromHolders(dates));

		List<ExtentTypeClient> extent = new ArrayList<ExtentTypeClient>(extents.size());
		for (ExtentHolder holder : extents) {
			extent.add(holder.getExtent());
		}
		partTypeClient.setExtent(extent);

		List<DetailTypeClient> detail = new ArrayList<DetailTypeClient>(details.size());
		for (DetailHolder holder : details) {
			detail.add(holder.getDetail());
		}
		partTypeClient.setDetail(detail);

		return partTypeClient;
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#getSubelements()
	 */
	@Override
	public List<MetadataHolder> getSubelements() {
		throw new UnsupportedOperationException("Mods");
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#getValue()
	 */
	@Override
	public String getValue() {
		throw new UnsupportedOperationException("Mods");
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#getValues()
	 */
	@Override
	public List<String> getValues() {
		throw new UnsupportedOperationException("Mods");
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#getAttributes()
	 */
	@Override
	public List<String> getAttributes() {
		throw new UnsupportedOperationException("Mods");
	}

	/**
	 * Gets the publishers.
	 *
	 * @return the publishers
	 */
	public ListOfSimpleValuesHolder getPublishers() {
		return texts;
	}

	/**
	 * Gets the texts.
	 *
	 * @return the texts
	 */
	public ListOfSimpleValuesHolder getTexts() {
		return texts;
	}

	/**
	 * Gets the dates.
	 *
	 * @return the dates
	 */
	public List<DateHolder> getDates() {
		return dates;
	}

	/**
	 * Gets the places.
	 *
	 * @return the places
	 */
	public List<PlaceHolder> getPlaces() {
		return places;
	}

	/**
	 * Gets the extents.
	 *
	 * @return the extents
	 */
	public List<ExtentHolder> getExtents() {
		return extents;
	}

	/**
	 * Gets the details.
	 *
	 * @return the details
	 */
	public List<DetailHolder> getDetails() {
		return details;
	}

	/**
	 * Gets the dates from holders.
	 *
	 * @param holders the holders
	 * @return the dates from holders
	 */
	private static List<BaseDateTypeClient> getDatesFromHolders(List<DateHolder> holders) {
		List<BaseDateTypeClient> dates = new ArrayList<BaseDateTypeClient>();
		for (DateHolder holder : holders) {
			dates.add(holder.getDate());
		}
		return dates;
	}

}
