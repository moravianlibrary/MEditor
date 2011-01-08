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

import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.DetailTypeClient;

// TODO: Auto-generated Javadoc
/**
 * The Class DetailHolder.
 */
public class DetailHolder extends ListOfSimpleValuesHolder {
	
	/** The numbers. */
	private final ListOfSimpleValuesHolder numbers;
	
	/** The captions. */
	private final ListOfSimpleValuesHolder captions;
	
	/** The titles. */
	private final ListOfSimpleValuesHolder titles;

	/**
	 * Instantiates a new detail holder.
	 */
	public DetailHolder() {
		this.numbers = new ListOfSimpleValuesHolder();
		this.captions = new ListOfSimpleValuesHolder();
		this.titles = new ListOfSimpleValuesHolder();
	}

	/**
	 * Gets the detail.
	 *
	 * @return the detail
	 */
	public DetailTypeClient getDetail() {
		DetailTypeClient detailTypeClient = new DetailTypeClient();
		if (getAttributeForm() != null) {
			detailTypeClient.setType(getAttributeForm().getValueAsString(ModsConstants.TYPE));
			detailTypeClient.setLevel(getAttributeForm().getValueAsString(ModsConstants.LEVEL));
		}
		detailTypeClient.setNumber(numbers.getValues());
		detailTypeClient.setCaption(captions.getValues());
		detailTypeClient.setTitle(titles.getValues());

		return detailTypeClient;
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
	 * Gets the numbers.
	 *
	 * @return the numbers
	 */
	public ListOfSimpleValuesHolder getNumbers() {
		return numbers;
	}

	/**
	 * Gets the captions.
	 *
	 * @return the captions
	 */
	public ListOfSimpleValuesHolder getCaptions() {
		return captions;
	}

	/**
	 * Gets the titles.
	 *
	 * @return the titles
	 */
	public ListOfSimpleValuesHolder getTitles() {
		return titles;
	}

}
