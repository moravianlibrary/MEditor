package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.HierarchicalGeographicTypeClient;

public class HierarchicalGeographicHolder extends MetadataHolder {
	private final ListOfSimpleValuesHolder continents;
	private final ListOfSimpleValuesHolder provinces;
	private final ListOfSimpleValuesHolder regions;
	private final ListOfSimpleValuesHolder states;
	private final ListOfSimpleValuesHolder territories;
	private final ListOfSimpleValuesHolder counties;
	private final ListOfSimpleValuesHolder countries;
	private final ListOfSimpleValuesHolder cities;
	private final ListOfSimpleValuesHolder citySections;
	private final ListOfSimpleValuesHolder islands;
	private final ListOfSimpleValuesHolder areas;
	private final ListOfSimpleValuesHolder extraterrestrialAreas;

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

	@Override
	public List<MetadataHolder> getSubelements() {
		throw new UnsupportedOperationException("Mods");
	}

	@Override
	public String getValue() {
		throw new UnsupportedOperationException("Mods");
	}

	@Override
	public List<String> getValues() {
		throw new UnsupportedOperationException("Mods");
	}

	@Override
	public List<String> getAttributes() {
		throw new UnsupportedOperationException("Mods");
	}

	public ListOfSimpleValuesHolder getContinents() {
		return continents;
	}

	public ListOfSimpleValuesHolder getProvinces() {
		return provinces;
	}

	public ListOfSimpleValuesHolder getRegions() {
		return regions;
	}

	public ListOfSimpleValuesHolder getStates() {
		return states;
	}

	public ListOfSimpleValuesHolder getTerritories() {
		return territories;
	}

	public ListOfSimpleValuesHolder getCounties() {
		return counties;
	}

	public ListOfSimpleValuesHolder getCountries() {
		return countries;
	}

	public ListOfSimpleValuesHolder getCities() {
		return cities;
	}

	public ListOfSimpleValuesHolder getCitySections() {
		return citySections;
	}

	public ListOfSimpleValuesHolder getIslands() {
		return islands;
	}

	public ListOfSimpleValuesHolder getAreas() {
		return areas;
	}

	public ListOfSimpleValuesHolder getExtraterrestrialAreas() {
		return extraterrestrialAreas;
	}

}
