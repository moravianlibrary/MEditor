package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.CodeOrTextClient;
import cz.fi.muni.xkremser.editor.client.mods.PlaceAuthorityClient;
import cz.fi.muni.xkremser.editor.client.mods.PlaceTermTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PlaceTypeClient;

public class PlaceHolder extends ListOfListOfSimpleValuesHolder {

	public PlaceHolder() {
		super("place_term", ModsConstants.TYPE, ModsConstants.AUTHORITY);
	}

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

}
