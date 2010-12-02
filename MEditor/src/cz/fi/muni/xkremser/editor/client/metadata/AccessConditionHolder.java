package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.AccessConditionTypeClient;

public class AccessConditionHolder extends ListOfSimpleValuesHolder {

	public AccessConditionTypeClient getAccessCondition() {
		AccessConditionTypeClient value = new AccessConditionTypeClient();
		if (getAttributeForm() != null) {
			value.setType(getAttributeForm().getValueAsString(ModsConstants.TYPE));
			value.setLang(getAttributeForm().getValueAsString(ModsConstants.LANG));
			value.setXmlLang(getAttributeForm().getValueAsString(ModsConstants.XML_LANG));
			value.setXlink(getAttributeForm().getValueAsString(ModsConstants.XLINK));
			value.setTransliteration(getAttributeForm().getValueAsString(ModsConstants.TRANSLITERATION));
			value.setScript(getAttributeForm().getValueAsString(ModsConstants.SCRIPT));
			value.setDisplayLabel(getAttributeForm().getValueAsString(ModsConstants.DISPLAY_LABEL));
		}
		if (getAttributeForm2() != null && getAttributeForm2().getValueAsString(ModsConstants.ACCESS_CONDITION) != null
				&& !"".equals(getAttributeForm2().getValueAsString(ModsConstants.ACCESS_CONDITION)))
			value.setContent(getAttributeForm2().getValueAsString(ModsConstants.ACCESS_CONDITION));
		return value;
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
