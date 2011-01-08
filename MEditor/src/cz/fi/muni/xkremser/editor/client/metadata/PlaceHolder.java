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

import cz.fi.muni.xkremser.editor.client.mods.CodeOrTextClient;
import cz.fi.muni.xkremser.editor.client.mods.PlaceAuthorityClient;
import cz.fi.muni.xkremser.editor.client.mods.PlaceTermTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PlaceTypeClient;

// TODO: Auto-generated Javadoc
/**
 * The Class PlaceHolder.
 */
public class PlaceHolder extends ListOfListOfSimpleValuesHolder {

	/**
	 * Instantiates a new place holder.
	 */
	public PlaceHolder() {
		super("place_term", ModsConstants.TYPE, ModsConstants.AUTHORITY);
	}

	/**
	 * Gets the place.
	 * 
	 * @return the place
	 */
	public PlaceTypeClient getPlace() {
		List<List<String>> listOfValues = getListOfList();
		if (listOfValues == null || listOfValues.size() == 0) {
			return null;
		}
		PlaceTypeClient placeTypeClient = new PlaceTypeClient();
		List<PlaceTermTypeClient> list = new ArrayList<PlaceTermTypeClient>();
		for (List<String> values : listOfValues) {
			PlaceTermTypeClient placeTermClient = new PlaceTermTypeClient();
			placeTermClient.setValue(values.get(0));
			placeTermClient.setType(CodeOrTextClient.fromValue(values.get(1)));
			placeTermClient.setAuthority(PlaceAuthorityClient.fromValue(values.get(2)));
			list.add(placeTermClient);
		}
		placeTypeClient.setPlaceTerm(list);
		return placeTypeClient;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.metadata.ListOfListOfSimpleValuesHolder
	 * #getSubelements()
	 */
	@Override
	public List<MetadataHolder> getSubelements() {
		throw new UnsupportedOperationException("Mods");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.metadata.ListOfListOfSimpleValuesHolder
	 * #getValue()
	 */
	@Override
	public String getValue() {
		throw new UnsupportedOperationException("Mods");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.metadata.ListOfListOfSimpleValuesHolder
	 * #getValues()
	 */
	@Override
	public List<String> getValues() {
		throw new UnsupportedOperationException("Mods");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.metadata.ListOfListOfSimpleValuesHolder
	 * #getAttributes()
	 */
	@Override
	public List<String> getAttributes() {
		throw new UnsupportedOperationException("Mods");
	}

}
