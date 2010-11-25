package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.List;

import cz.fi.muni.xkremser.editor.client.ClientUtils;
import cz.fi.muni.xkremser.editor.client.mods.DateOtherTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.DateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.YesClient;

public class DateHolder extends ListOfSimpleValuesHolder {
	private final String dateName;
	private final boolean otherDate;

	public DateHolder(String dateName, boolean otherDate) {
		this.dateName = dateName;
		this.otherDate = otherDate;
	}

	public DateTypeClient getDate() {
		DateTypeClient dateTypeClient = otherDate ? new DateOtherTypeClient() : new DateTypeClient();
		if (getAttributeForm() != null) {
			dateTypeClient.setEncoding(getAttributeForm().getValueAsString(ModsConstants.ENCODING));
			String val = getAttributeForm().getValueAsString(ModsConstants.KEY_DATE);
			if (val != null && ClientUtils.toBoolean(val)) {
				dateTypeClient.setKeyDate(YesClient.YES);
			}
			dateTypeClient.setPoint(getAttributeForm().getValueAsString(ModsConstants.POINT));
			dateTypeClient.setQualifier(getAttributeForm().getValueAsString(ModsConstants.QUALIFIER));
			dateTypeClient.setValue(getAttributeForm().getValueAsString(dateName));
			if (otherDate)
				((DateOtherTypeClient) dateTypeClient).setType(getAttributeForm().getValueAsString(ModsConstants.TYPE));
		}
		return dateTypeClient;
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
