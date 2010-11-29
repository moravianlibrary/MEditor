package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.DateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.HierarchicalGeographicTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NameTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PlaceAuthorityClient;
import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient.CartographicsClient;
import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient.GeographicCodeClient;
import cz.fi.muni.xkremser.editor.client.mods.TitleInfoTypeClient;

public class SubjectHolder extends ListOfSimpleValuesHolder {
	private final ListOfSimpleValuesHolder topics;
	private final ListOfSimpleValuesHolder geographics;
	private final ListOfSimpleValuesHolder genres;
	private final ListOfSimpleValuesHolder occupations;
	private final ListOfListOfSimpleValuesHolder geoCodes;
	private final List<DateHolder> temporals;
	private final List<TitleInfoHolder> titleInfo;
	private final List<NameHolder> names;
	private final List<HierarchicalGeographicHolder> hieararchicalGeo;
	private final List<CartographicsHolder> cartographics;

	public SubjectHolder() {
		this.topics = new ListOfSimpleValuesHolder();
		this.geographics = new ListOfSimpleValuesHolder();
		this.genres = new ListOfSimpleValuesHolder();
		this.occupations = new ListOfSimpleValuesHolder();
		this.geoCodes = new ListOfListOfSimpleValuesHolder(ModsConstants.GEO_CODE, ModsConstants.AUTHORITY);
		temporals = new ArrayList<DateHolder>();
		titleInfo = new ArrayList<TitleInfoHolder>();
		names = new ArrayList<NameHolder>();
		hieararchicalGeo = new ArrayList<HierarchicalGeographicHolder>();
		cartographics = new ArrayList<CartographicsHolder>();
	}

	public SubjectTypeClient getSubject() {
		SubjectTypeClient subjectTypeClient = new SubjectTypeClient();
		if (getAttributeForm() != null) {
			subjectTypeClient.setAuthority(getAttributeForm().getValueAsString(ModsConstants.AUTHORITY));
			subjectTypeClient.setXlink(getAttributeForm().getValueAsString(ModsConstants.XLINK));
			subjectTypeClient.setId(getAttributeForm().getValueAsString(ModsConstants.ID));
			subjectTypeClient.setLang(getAttributeForm().getValueAsString(ModsConstants.LANG));
			subjectTypeClient.setXmlLang(getAttributeForm().getValueAsString(ModsConstants.XML_LANG));
			subjectTypeClient.setTransliteration(getAttributeForm().getValueAsString(ModsConstants.TRANSLITERATION));
			subjectTypeClient.setScript(getAttributeForm().getValueAsString(ModsConstants.SCRIPT));
		}
		subjectTypeClient.setTopic(topics.getValues());
		subjectTypeClient.setGeographic(geographics.getValues());
		subjectTypeClient.setGenre(genres.getValues());
		subjectTypeClient.setOccupation(occupations.getValues());
		subjectTypeClient.setTemporal(getDatesFromHolders(temporals));

		// title info
		List<TitleInfoTypeClient> value = new ArrayList<TitleInfoTypeClient>(titleInfo.size());
		for (TitleInfoHolder holder : titleInfo) {
			value.add(holder.getTitleInfo());
		}
		subjectTypeClient.setTitleInfo(value);

		// name
		List<NameTypeClient> value2 = new ArrayList<NameTypeClient>(names.size());
		for (NameHolder holder : names) {
			value2.add(holder.getName());
		}
		subjectTypeClient.setName(value2);

		// geoCodes
		List<GeographicCodeClient> list = null;
		List<List<String>> listOfValues = geoCodes.getListOfList();
		if (listOfValues != null && listOfValues.size() != 0) {
			list = new ArrayList<GeographicCodeClient>();
			for (List<String> values : listOfValues) {
				GeographicCodeClient val = new GeographicCodeClient();
				val.setValue(values.get(0));
				val.setAuthority(PlaceAuthorityClient.fromValue(values.get(1)));
				list.add(val);
			}
		}
		subjectTypeClient.setGeographicCode(list);

		// hieararchicalGeo
		List<HierarchicalGeographicTypeClient> list2 = null;
		if (hieararchicalGeo.size() > 0) {
			list2 = new ArrayList<HierarchicalGeographicTypeClient>();
			for (HierarchicalGeographicHolder holder : hieararchicalGeo) {
				list2.add(holder.getHierarchicalGeographic());
			}
		}

		subjectTypeClient.setHierarchicalGeographic(list2);

		// cartographics
		List<CartographicsClient> list3 = null;
		if (cartographics.size() > 0) {
			list3 = new ArrayList<CartographicsClient>();
			for (CartographicsHolder holder : cartographics) {
				list3.add(holder.getCartographics());
			}
		}
		subjectTypeClient.setCartographics(list3);

		return subjectTypeClient;
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

	public ListOfSimpleValuesHolder getTopics() {
		return topics;
	}

	public ListOfSimpleValuesHolder getGeographics() {
		return geographics;
	}

	public ListOfSimpleValuesHolder getGenres() {
		return genres;
	}

	public ListOfSimpleValuesHolder getOccupations() {
		return occupations;
	}

	public ListOfListOfSimpleValuesHolder getGeoCodes() {
		return geoCodes;
	}

	public List<DateHolder> getTemporals() {
		return temporals;
	}

	public List<TitleInfoHolder> getTitleInfo() {
		return titleInfo;
	}

	public List<NameHolder> getNames() {
		return names;
	}

	public List<HierarchicalGeographicHolder> getHieararchicalGeo() {
		return hieararchicalGeo;
	}

	public List<CartographicsHolder> getCartographics() {
		return cartographics;
	}

}
