package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.DetailTypeClient;

public class DetailHolder extends ListOfSimpleValuesHolder {
	private final ListOfSimpleValuesHolder numbers;
	private final ListOfSimpleValuesHolder captions;
	private final ListOfSimpleValuesHolder titles;

	public DetailHolder() {
		this.numbers = new ListOfSimpleValuesHolder();
		this.captions = new ListOfSimpleValuesHolder();
		this.titles = new ListOfSimpleValuesHolder();
	}

	public DetailTypeClient getDetail() {
		DetailTypeClient detailTypeClient = new DetailTypeClient();
		if (getAttributeForm() != null) {
			detailTypeClient.setType(getAttributeForm().getValueAsString(ModsConstants.TYPE));
			detailTypeClient.setLevel(getAttributeForm().getValueAsString(ModsConstants.LEVEL));
		}
		detailTypeClient.setNumber(numbers.getValues());
		detailTypeClient.setCaption(captions.getValues());
		detailTypeClient.setTitle(titles.getValues());

		return detailTypeClient;
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

	public ListOfSimpleValuesHolder getNumbers() {
		return numbers;
	}

	public ListOfSimpleValuesHolder getCaptions() {
		return captions;
	}

	public ListOfSimpleValuesHolder getTitles() {
		return titles;
	}

}
