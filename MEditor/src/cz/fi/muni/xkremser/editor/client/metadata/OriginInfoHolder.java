package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.DateOtherTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.DateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.OriginInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PlaceTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityClient;

public class OriginInfoHolder extends ListOfSimpleValuesHolder {
	private final ListOfSimpleValuesHolder publishers;
	private final ListOfSimpleValuesHolder editions;
	private final ListOfListOfSimpleValuesHolder frequencies;
	private final ListOfSimpleValuesHolder issuances;
	private final List<DateHolder> datesIssued;
	private final List<DateHolder> datesCreated;
	private final List<DateHolder> datesCaptured;
	private final List<DateHolder> datesValid;
	private final List<DateHolder> datesModified;
	private final List<DateHolder> datesCopyright;
	private final List<DateHolder> datesOther;
	private final List<PlaceHolder> places;

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

	public ListOfSimpleValuesHolder getPublishers() {
		return publishers;
	}

	public ListOfSimpleValuesHolder getEditions() {
		return editions;
	}

	public ListOfListOfSimpleValuesHolder getFrequencies() {
		return frequencies;
	}

	public ListOfSimpleValuesHolder getIssuances() {
		return issuances;
	}

	public List<DateHolder> getDatesIssued() {
		return datesIssued;
	}

	public List<DateHolder> getDatesCreated() {
		return datesCreated;
	}

	public List<DateHolder> getDatesCaptured() {
		return datesCaptured;
	}

	public List<DateHolder> getDatesValid() {
		return datesValid;
	}

	public List<DateHolder> getDatesModified() {
		return datesModified;
	}

	public List<DateHolder> getDatesCopyright() {
		return datesCopyright;
	}

	public List<DateHolder> getDatesOther() {
		return datesOther;
	}

	public List<PlaceHolder> getPlaces() {
		return places;
	}

	private static List<DateTypeClient> getDatesFromHolders(List<DateHolder> holders) {
		List<DateTypeClient> dates = new ArrayList<DateTypeClient>();
		for (DateHolder holder : holders) {
			dates.add(holder.getDate());
		}
		return dates;
	}

}
