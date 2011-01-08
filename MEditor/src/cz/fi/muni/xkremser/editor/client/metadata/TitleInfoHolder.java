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

import cz.fi.muni.xkremser.editor.client.mods.TitleInfoTypeClient;

// TODO: Auto-generated Javadoc
/**
 * The Class TitleInfoHolder.
 */
public class TitleInfoHolder extends MetadataHolder {
	
	/** The titles. */
	private ListOfSimpleValuesHolder titles;
	
	/** The sub titles. */
	private ListOfSimpleValuesHolder subTitles;
	
	/** The part numbers. */
	private ListOfSimpleValuesHolder partNumbers;
	
	/** The part names. */
	private ListOfSimpleValuesHolder partNames;
	
	/** The non sorts. */
	private ListOfSimpleValuesHolder nonSorts;

	/**
	 * Instantiates a new title info holder.
	 */
	public TitleInfoHolder() {
		this.titles = new ListOfSimpleValuesHolder();
		this.subTitles = new ListOfSimpleValuesHolder();
		this.partNumbers = new ListOfSimpleValuesHolder();
		this.partNames = new ListOfSimpleValuesHolder();
		this.nonSorts = new ListOfSimpleValuesHolder();
	}

	/**
	 * Gets the title info.
	 *
	 * @return the title info
	 */
	public TitleInfoTypeClient getTitleInfo() {
		TitleInfoTypeClient titleInfoTypeClient = new TitleInfoTypeClient();
		if (getAttributeForm() != null) {
			titleInfoTypeClient.setAuthority(getAttributeForm().getValueAsString(ModsConstants.AUTHORITY));
			titleInfoTypeClient.setDisplayLabel(getAttributeForm().getValueAsString(ModsConstants.DISPLAY_LABEL));
			titleInfoTypeClient.setType(getAttributeForm().getValueAsString(ModsConstants.TYPE));
			titleInfoTypeClient.setLang(getAttributeForm().getValueAsString(ModsConstants.LANG));
			titleInfoTypeClient.setXmlLang(getAttributeForm().getValueAsString(ModsConstants.XML_LANG));
			titleInfoTypeClient.setTransliteration(getAttributeForm().getValueAsString(ModsConstants.TRANSLITERATION));
			titleInfoTypeClient.setScript(getAttributeForm().getValueAsString(ModsConstants.SCRIPT));
			titleInfoTypeClient.setXlink(getAttributeForm().getValueAsString(ModsConstants.XLINK));
			titleInfoTypeClient.setId(getAttributeForm().getValueAsString(ModsConstants.ID));
		}
		titleInfoTypeClient.setTitle(titles.getValues());
		titleInfoTypeClient.setSubTitle(subTitles.getValues());
		titleInfoTypeClient.setPartNumber(partNumbers.getValues());
		titleInfoTypeClient.setPartName(partNames.getValues());
		titleInfoTypeClient.setNonSort(nonSorts.getValues());
		return titleInfoTypeClient;
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getSubelements()
	 */
	@Override
	public List<MetadataHolder> getSubelements() {
		throw new UnsupportedOperationException("Mods");
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getValue()
	 */
	@Override
	public String getValue() {
		throw new UnsupportedOperationException("Mods");
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getValues()
	 */
	@Override
	public List<String> getValues() {
		throw new UnsupportedOperationException("Mods");
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getAttributes()
	 */
	@Override
	public List<String> getAttributes() {
		throw new UnsupportedOperationException("Mods");
	}

	/**
	 * Gets the titles.
	 *
	 * @return the titles
	 */
	public ListOfSimpleValuesHolder getTitles() {
		return titles;
	}

	/**
	 * Sets the titles.
	 *
	 * @param titles the new titles
	 */
	public void setTitles(ListOfSimpleValuesHolder titles) {
		this.titles = titles;
	}

	/**
	 * Gets the sub titles.
	 *
	 * @return the sub titles
	 */
	public ListOfSimpleValuesHolder getSubTitles() {
		return subTitles;
	}

	/**
	 * Sets the sub titles.
	 *
	 * @param subTitles the new sub titles
	 */
	public void setSubTitles(ListOfSimpleValuesHolder subTitles) {
		this.subTitles = subTitles;
	}

	/**
	 * Gets the part numbers.
	 *
	 * @return the part numbers
	 */
	public ListOfSimpleValuesHolder getPartNumbers() {
		return partNumbers;
	}

	/**
	 * Sets the part numbers.
	 *
	 * @param partNumbers the new part numbers
	 */
	public void setPartNumbers(ListOfSimpleValuesHolder partNumbers) {
		this.partNumbers = partNumbers;
	}

	/**
	 * Gets the part names.
	 *
	 * @return the part names
	 */
	public ListOfSimpleValuesHolder getPartNames() {
		return partNames;
	}

	/**
	 * Sets the part names.
	 *
	 * @param partNames the new part names
	 */
	public void setPartNames(ListOfSimpleValuesHolder partNames) {
		this.partNames = partNames;
	}

	/**
	 * Gets the non sorts.
	 *
	 * @return the non sorts
	 */
	public ListOfSimpleValuesHolder getNonSorts() {
		return nonSorts;
	}

	/**
	 * Sets the non sorts.
	 *
	 * @param nonSorts the new non sorts
	 */
	public void setNonSorts(ListOfSimpleValuesHolder nonSorts) {
		this.nonSorts = nonSorts;
	}

}
