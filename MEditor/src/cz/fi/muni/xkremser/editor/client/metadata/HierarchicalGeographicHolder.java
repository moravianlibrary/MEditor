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

import cz.fi.muni.xkremser.editor.client.mods.HierarchicalGeographicTypeClient;

// TODO: Auto-generated Javadoc
/**
 * The Class HierarchicalGeographicHolder.
 */
public class HierarchicalGeographicHolder extends MetadataHolder {
	
	/** The continents. */
	private final ListOfSimpleValuesHolder continents;
	
	/** The provinces. */
	private final ListOfSimpleValuesHolder provinces;
	
	/** The regions. */
	private final ListOfSimpleValuesHolder regions;
	
	/** The states. */
	private final ListOfSimpleValuesHolder states;
	
	/** The territories. */
	private final ListOfSimpleValuesHolder territories;
	
	/** The counties. */
	private final ListOfSimpleValuesHolder counties;
	
	/** The countries. */
	private final ListOfSimpleValuesHolder countries;
	
	/** The cities. */
	private final ListOfSimpleValuesHolder cities;
	
	/** The city sections. */
	private final ListOfSimpleValuesHolder citySections;
	
	/** The islands. */
	private final ListOfSimpleValuesHolder islands;
	
	/** The areas. */
	private final ListOfSimpleValuesHolder areas;
	
	/** The extraterrestrial areas. */
	private final ListOfSimpleValuesHolder extraterrestrialAreas;

	/**
	 * Instantiates a new hierarchical geographic holder.
	 */
	public HierarchicalGeographicHolder() {
		this.continents = new ListOfSimpleValuesHolder();
		this.provinces = new ListOfSimpleValuesHolder();
		this.regions = new ListOfSimpleValuesHolder();
		this.states = new ListOfSimpleValuesHolder();
		this.territories = new ListOfSimpleValuesHolder();
		this.counties = new ListOfSimpleValuesHolder();
		this.countries = new ListOfSimpleValuesHolder();
		this.cities = new ListOfSimpleValuesHolder();
		this.citySections = new ListOfSimpleValuesHolder();
		this.islands = new ListOfSimpleValuesHolder();
		this.areas = new ListOfSimpleValuesHolder();
		this.extraterrestrialAreas = new ListOfSimpleValuesHolder();
	}

	/**
	 * Gets the hierarchical geographic.
	 *
	 * @return the hierarchical geographic
	 */
	public HierarchicalGeographicTypeClient getHierarchicalGeographic() {
		HierarchicalGeographicTypeClient hierarchicalGeographicTypeClient = new HierarchicalGeographicTypeClient();
		hierarchicalGeographicTypeClient.setContinent(continents.getValues());
		hierarchicalGeographicTypeClient.setProvince(provinces.getValues());
		hierarchicalGeographicTypeClient.setRegion(regions.getValues());
		hierarchicalGeographicTypeClient.setState(states.getValues());
		hierarchicalGeographicTypeClient.setTerritory(territories.getValues());
		hierarchicalGeographicTypeClient.setCountry(countries.getValues());
		hierarchicalGeographicTypeClient.setCounty(counties.getValues());
		hierarchicalGeographicTypeClient.setCity(cities.getValues());
		hierarchicalGeographicTypeClient.setCitySection(citySections.getValues());
		hierarchicalGeographicTypeClient.setIsland(islands.getValues());
		hierarchicalGeographicTypeClient.setArea(areas.getValues());
		hierarchicalGeographicTypeClient.setExtraterrestrialArea(extraterrestrialAreas.getValues());
		return hierarchicalGeographicTypeClient;
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
	 * Gets the continents.
	 *
	 * @return the continents
	 */
	public ListOfSimpleValuesHolder getContinents() {
		return continents;
	}

	/**
	 * Gets the provinces.
	 *
	 * @return the provinces
	 */
	public ListOfSimpleValuesHolder getProvinces() {
		return provinces;
	}

	/**
	 * Gets the regions.
	 *
	 * @return the regions
	 */
	public ListOfSimpleValuesHolder getRegions() {
		return regions;
	}

	/**
	 * Gets the states.
	 *
	 * @return the states
	 */
	public ListOfSimpleValuesHolder getStates() {
		return states;
	}

	/**
	 * Gets the territories.
	 *
	 * @return the territories
	 */
	public ListOfSimpleValuesHolder getTerritories() {
		return territories;
	}

	/**
	 * Gets the counties.
	 *
	 * @return the counties
	 */
	public ListOfSimpleValuesHolder getCounties() {
		return counties;
	}

	/**
	 * Gets the countries.
	 *
	 * @return the countries
	 */
	public ListOfSimpleValuesHolder getCountries() {
		return countries;
	}

	/**
	 * Gets the cities.
	 *
	 * @return the cities
	 */
	public ListOfSimpleValuesHolder getCities() {
		return cities;
	}

	/**
	 * Gets the city sections.
	 *
	 * @return the city sections
	 */
	public ListOfSimpleValuesHolder getCitySections() {
		return citySections;
	}

	/**
	 * Gets the islands.
	 *
	 * @return the islands
	 */
	public ListOfSimpleValuesHolder getIslands() {
		return islands;
	}

	/**
	 * Gets the areas.
	 *
	 * @return the areas
	 */
	public ListOfSimpleValuesHolder getAreas() {
		return areas;
	}

	/**
	 * Gets the extraterrestrial areas.
	 *
	 * @return the extraterrestrial areas
	 */
	public ListOfSimpleValuesHolder getExtraterrestrialAreas() {
		return extraterrestrialAreas;
	}

}
