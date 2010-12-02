package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.BaseDateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.DetailTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.ExtentTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PartTypeClient;

public class PartHolder extends ListOfSimpleValuesHolder {
	private final ListOfSimpleValuesHolder texts;
	private final List<DateHolder> dates;
	private final List<PlaceHolder> places;
	private final List<ExtentHolder> extents;
	private final List<DetailHolder> details;

	public PartHolder() {
		this.texts = new ListOfSimpleValuesHolder();
		dates = new ArrayList<DateHolder>();
		extents = new ArrayList<ExtentHolder>();
		details = new ArrayList<DetailHolder>();
		places = new ArrayList<PlaceHolder>();
	}

	public PartTypeClient getPart() {
		PartTypeClient partTypeClient = new PartTypeClient();
		if (getAttributeForm() != null) {
			partTypeClient.setType(getAttributeForm().getValueAsString(ModsConstants.TYPE));
			partTypeClient.setOrder(getAttributeForm().getValueAsString(ModsConstants.ORDER));
			partTypeClient.setId(getAttributeForm().getValueAsString(ModsConstants.ID));
		}

		partTypeClient.setText(texts.getValues());
		partTypeClient.setDate(getDatesFromHolders(dates));

		List<ExtentTypeClient> extent = new ArrayList<ExtentTypeClient>(extents.size());
		for (ExtentHolder holder : extents) {
			extent.add(holder.getExtent());
		}
		partTypeClient.setExtent(extent);

		List<DetailTypeClient> detail = new ArrayList<DetailTypeClient>(details.size());
		for (DetailHolder holder : details) {
			detail.add(holder.getDetail());
		}
		partTypeClient.setDetail(detail);

		return partTypeClient;
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
		return texts;
	}

	public ListOfSimpleValuesHolder getTexts() {
		return texts;
	}

	public List<DateHolder> getDates() {
		return dates;
	}

	public List<PlaceHolder> getPlaces() {
		return places;
	}

	public List<ExtentHolder> getExtents() {
		return extents;
	}

	public List<DetailHolder> getDetails() {
		return details;
	}

	private static List<BaseDateTypeClient> getDatesFromHolders(List<DateHolder> holders) {
		List<BaseDateTypeClient> dates = new ArrayList<BaseDateTypeClient>();
		for (DateHolder holder : holders) {
			dates.add(holder.getDate());
		}
		return dates;
	}

}
