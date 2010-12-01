package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.CopyInformationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.DateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.ExtensionTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.HoldingSimpleTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.LocationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PhysicalLocationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.UrlTypeClient;

public class LocationHolder extends MetadataHolder {
	private final ListOfSimpleValuesHolder shelfLocators;
	private final ListOfSimpleValuesHolder holdingExternals;
	private final ListOfListOfSimpleValuesHolder physicalLocations;
	private final ListOfListOfSimpleValuesHolder urls;
	private final List<CopyInformationHolder> holdingSimples;

	public LocationHolder() {
		this.shelfLocators = new ListOfSimpleValuesHolder();
		this.holdingExternals = new ListOfSimpleValuesHolder();
		this.physicalLocations = new ListOfListOfSimpleValuesHolder(ModsConstants.PHYSICAL_LOCATION, ModsConstants.DISPLAY_LABEL, ModsConstants.TYPE,
				ModsConstants.AUTHORITY, ModsConstants.XLINK, ModsConstants.LANG, ModsConstants.XML_LANG, ModsConstants.TRANSLITERATION, ModsConstants.SCRIPT);
		this.urls = new ListOfListOfSimpleValuesHolder(ModsConstants.URL, ModsConstants.DISPLAY_LABEL, ModsConstants.DATE_LAST_ACCESSED, ModsConstants.NOTE,
				ModsConstants.ACCESS, ModsConstants.USAGE);
		this.holdingSimples = new ArrayList<CopyInformationHolder>();
	}

	public LocationTypeClient getLocation() {
		LocationTypeClient locationTypeClient = new LocationTypeClient();
		locationTypeClient.setShelfLocator(shelfLocators.getValues());
		if (holdingExternals.getValues() != null && holdingExternals.getValues().size() > 0) {
			ExtensionTypeClient ex = new ExtensionTypeClient();
			ex.setContent(holdingExternals.getValues());
			locationTypeClient.setHoldingExternal(ex);
		}

		// physical locations
		List<PhysicalLocationTypeClient> list = null;
		List<List<String>> listOfValues = physicalLocations.getListOfList();
		if (listOfValues != null && listOfValues.size() != 0) {
			list = new ArrayList<PhysicalLocationTypeClient>();
			for (List<String> values : listOfValues) {
				PhysicalLocationTypeClient val = new PhysicalLocationTypeClient();
				val.setValue(values.get(0));
				val.setDisplayLabel(values.get(1));
				val.setType(values.get(2));
				val.setAuthority(values.get(3));
				val.setXlink(values.get(4));
				val.setLang(values.get(5));
				val.setXmlLang(values.get(5));
				val.setTransliteration(values.get(6));
				val.setScript(values.get(7));
				list.add(val);
			}
		}
		locationTypeClient.setPhysicalLocation(list);

		// urls
		List<UrlTypeClient> list2 = null;
		List<List<String>> listOfValues2 = urls.getListOfList();
		if (listOfValues2 != null && listOfValues2.size() != 0) {
			list2 = new ArrayList<UrlTypeClient>();
			for (List<String> values : listOfValues2) {
				UrlTypeClient val = new UrlTypeClient();
				val.setValue(values.get(0));
				val.setDisplayLabel(values.get(1));
				val.setDateLastAccessed(values.get(2));
				val.setNote(values.get(3));
				val.setAccess(values.get(4));
				val.setUsage(values.get(5));
				list2.add(val);
			}
		}
		locationTypeClient.setUrl(list2);

		// holding simple
		HoldingSimpleTypeClient holding = null;
		if (holdingSimples.size() > 0) {
			holding = new HoldingSimpleTypeClient();
			List<CopyInformationTypeClient> info = new ArrayList<CopyInformationTypeClient>(holdingSimples.size());
			for (CopyInformationHolder holder : holdingSimples) {
				info.add(holder.getCopyInfo());
			}
			holding.setCopyInformation(info);
		}
		locationTypeClient.setHoldingSimple(holding);

		return locationTypeClient;
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

	private static List<DateTypeClient> getDatesFromHolders(List<DateHolder> holders) {
		List<DateTypeClient> dates = new ArrayList<DateTypeClient>();
		for (DateHolder holder : holders) {
			dates.add(holder.getDate());
		}
		return dates;
	}

	public ListOfSimpleValuesHolder getShelfLocators() {
		return shelfLocators;
	}

	public ListOfSimpleValuesHolder getHoldingExternals() {
		return holdingExternals;
	}

	public ListOfListOfSimpleValuesHolder getPhysicalLocations() {
		return physicalLocations;
	}

	public ListOfListOfSimpleValuesHolder getUrls() {
		return urls;
	}

	public List<CopyInformationHolder> getHoldingSimples() {
		return holdingSimples;
	}

}
