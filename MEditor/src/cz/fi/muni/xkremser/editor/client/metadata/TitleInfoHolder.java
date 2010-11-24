package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.TitleInfoTypeClient;

public class TitleInfoHolder extends MetadataHolder {
	private ListOfSimpleValuesHolder titles;
	private ListOfSimpleValuesHolder subTitles;
	private ListOfSimpleValuesHolder partNumbers;
	private ListOfSimpleValuesHolder partNames;
	private ListOfSimpleValuesHolder nonSorts;

	public TitleInfoHolder() {
		this.titles = new ListOfSimpleValuesHolder();
		this.subTitles = new ListOfSimpleValuesHolder();
		this.partNumbers = new ListOfSimpleValuesHolder();
		this.partNames = new ListOfSimpleValuesHolder();
		this.nonSorts = new ListOfSimpleValuesHolder();
	}

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

	public ListOfSimpleValuesHolder getTitles() {
		return titles;
	}

	public void setTitles(ListOfSimpleValuesHolder titles) {
		this.titles = titles;
	}

	public ListOfSimpleValuesHolder getSubTitles() {
		return subTitles;
	}

	public void setSubTitles(ListOfSimpleValuesHolder subTitles) {
		this.subTitles = subTitles;
	}

	public ListOfSimpleValuesHolder getPartNumbers() {
		return partNumbers;
	}

	public void setPartNumbers(ListOfSimpleValuesHolder partNumbers) {
		this.partNumbers = partNumbers;
	}

	public ListOfSimpleValuesHolder getPartNames() {
		return partNames;
	}

	public void setPartNames(ListOfSimpleValuesHolder partNames) {
		this.partNames = partNames;
	}

	public ListOfSimpleValuesHolder getNonSorts() {
		return nonSorts;
	}

	public void setNonSorts(ListOfSimpleValuesHolder nonSorts) {
		this.nonSorts = nonSorts;
	}

}
