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

import cz.fi.muni.xkremser.editor.client.mods.DateOtherTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.DateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.OriginInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PlaceTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityClient;

// TODO: Auto-generated Javadoc
/**
 * The Class OriginInfoHolder.
 */
public class OriginInfoHolder extends ListOfSimpleValuesHolder {

	/** The publishers. */
	private final ListOfSimpleValuesHolder publishers;

	/** The editions. */
	private final ListOfSimpleValuesHolder editions;

	/** The frequencies. */
	private final ListOfListOfSimpleValuesHolder frequencies;

	/** The issuances. */
	private final ListOfSimpleValuesHolder issuances;

	/** The dates issued. */
	private final List<DateHolder> datesIssued;

	/** The dates created. */
	private final List<DateHolder> datesCreated;

	/** The dates captured. */
	private final List<DateHolder> datesCaptured;

	/** The dates valid. */
	private final List<DateHolder> datesValid;

	/** The dates modified. */
	private final List<DateHolder> datesModified;

	/** The dates copyright. */
	private final List<DateHolder> datesCopyright;

	/** The dates other. */
	private final List<DateHolder> datesOther;

	/** The places. */
	private final List<PlaceHolder> places;

	/**
	 * Instantiates a new origin info holder.
	 */
	public OriginInfoHolder() {
		this.publishers = new ListOfSimpleValuesHolder();
		this.editions = new ListOfSimpleValuesHolder();
		this.frequencies = new ListOfListOfSimpleValuesHolder(ModsConstants.FREQUENCY, ModsConstants.AUTHORITY);
		this.issuances = new ListOfSimpleValuesHolder();
		datesIssued = new ArrayList<DateHolder>();
		datesCreated = new ArrayList<DateHolder>();
		datesCaptured = new ArrayList<DateHolder>();
		datesValid = new ArrayList<DateHolder>();
		datesModified = new ArrayList<DateHolder>();
		datesCopyright = new ArrayList<DateHolder>();
		datesOther = new ArrayList<DateHolder>();
		places = new ArrayList<PlaceHolder>();
	}

	/**
	 * Gets the origin info.
	 * 
	 * @return the origin info
	 */
	public OriginInfoTypeClient getOriginInfo() {
		OriginInfoTypeClient originInfoTypeClient = new OriginInfoTypeClient();
		if (getAttributeForm() != null) {
			originInfoTypeClient.setLang(getAttributeForm().getValueAsString(ModsConstants.LANG));
			originInfoTypeClient.setXmlLang(getAttributeForm().getValueAsString(ModsConstants.XML_LANG));
			originInfoTypeClient.setTransliteration(getAttributeForm().getValueAsString(ModsConstants.TRANSLITERATION));
			originInfoTypeClient.setScript(getAttributeForm().getValueAsString(ModsConstants.SCRIPT));
		}
		originInfoTypeClient.setPublisher(publishers.getValues());
		originInfoTypeClient.setEdition(editions.getValues());
		originInfoTypeClient.setIssuance(issuances.getValues());
		originInfoTypeClient.setDateIssued(getDatesFromHolders(datesIssued));
		originInfoTypeClient.setDateCreated(getDatesFromHolders(datesCreated));
		originInfoTypeClient.setDateCaptured(getDatesFromHolders(datesCaptured));
		originInfoTypeClient.setDateValid(getDatesFromHolders(datesValid));
		originInfoTypeClient.setDateModified(getDatesFromHolders(datesModified));
		originInfoTypeClient.setCopyrightDate(getDatesFromHolders(datesCopyright));

		List<DateOtherTypeClient> dates = new ArrayList<DateOtherTypeClient>();
		for (DateHolder holder : datesOther) {
			dates.add((DateOtherTypeClient) holder.getDate());
		}
		originInfoTypeClient.setDateOther(dates);

		List<PlaceTypeClient> placeList = new ArrayList<PlaceTypeClient>();
		for (PlaceHolder holder : places) {
			placeList.add(holder.getPlace());
		}
		originInfoTypeClient.setPlace(placeList);

		List<StringPlusAuthorityClient> list = null;
		List<List<String>> listOfValues = frequencies.getListOfList();
		if (listOfValues != null && listOfValues.size() != 0) {
			list = new ArrayList<StringPlusAuthorityClient>();
			for (List<String> values : listOfValues) {
				StringPlusAuthorityClient val = new StringPlusAuthorityClient();
				val.setValue(values.get(0));
				val.setAuthority(values.get(1));
				list.add(val);
			}
		}
		originInfoTypeClient.setFrequency(list);

		return originInfoTypeClient;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#
	 * getSubelements()
	 */
	@Override
	public List<MetadataHolder> getSubelements() {
		throw new UnsupportedOperationException("Mods");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#getValue
	 * ()
	 */
	@Override
	public String getValue() {
		throw new UnsupportedOperationException("Mods");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#getValues
	 * ()
	 */
	@Override
	public List<String> getValues() {
		throw new UnsupportedOperationException("Mods");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#
	 * getAttributes()
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
		return publishers;
	}

	/**
	 * Gets the editions.
	 * 
	 * @return the editions
	 */
	public ListOfSimpleValuesHolder getEditions() {
		return editions;
	}

	/**
	 * Gets the frequencies.
	 * 
	 * @return the frequencies
	 */
	public ListOfListOfSimpleValuesHolder getFrequencies() {
		return frequencies;
	}

	/**
	 * Gets the issuances.
	 * 
	 * @return the issuances
	 */
	public ListOfSimpleValuesHolder getIssuances() {
		return issuances;
	}

	/**
	 * Gets the dates issued.
	 * 
	 * @return the dates issued
	 */
	public List<DateHolder> getDatesIssued() {
		return datesIssued;
	}

	/**
	 * Gets the dates created.
	 * 
	 * @return the dates created
	 */
	public List<DateHolder> getDatesCreated() {
		return datesCreated;
	}

	/**
	 * Gets the dates captured.
	 * 
	 * @return the dates captured
	 */
	public List<DateHolder> getDatesCaptured() {
		return datesCaptured;
	}

	/**
	 * Gets the dates valid.
	 * 
	 * @return the dates valid
	 */
	public List<DateHolder> getDatesValid() {
		return datesValid;
	}

	/**
	 * Gets the dates modified.
	 * 
	 * @return the dates modified
	 */
	public List<DateHolder> getDatesModified() {
		return datesModified;
	}

	/**
	 * Gets the dates copyright.
	 * 
	 * @return the dates copyright
	 */
	public List<DateHolder> getDatesCopyright() {
		return datesCopyright;
	}

	/**
	 * Gets the dates other.
	 * 
	 * @return the dates other
	 */
	public List<DateHolder> getDatesOther() {
		return datesOther;
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
	 * Gets the dates from holders.
	 * 
	 * @param holders
	 *          the holders
	 * @return the dates from holders
	 */
	private static List<DateTypeClient> getDatesFromHolders(List<DateHolder> holders) {
		List<DateTypeClient> dates = new ArrayList<DateTypeClient>();
		for (DateHolder holder : holders) {
			dates.add(holder.getDate());
		}
		return dates;
	}

}
