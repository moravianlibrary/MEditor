package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.AbstractTypeClient;

public class AbstractHolder extends ListOfSimpleValuesHolder {

	public AbstractTypeClient getAbstract() {
		AbstractTypeClient abstractTypeClient = new AbstractTypeClient();
		if (getAttributeForm() != null) {
			abstractTypeClient.setAtType(getAttributeForm().getValueAsString(ModsConstants.TYPE));
			abstractTypeClient.setLang(getAttributeForm().getValueAsString(ModsConstants.LANG));
			abstractTypeClient.setXmlLang(getAttributeForm().getValueAsString(ModsConstants.XML_LANG));
			abstractTypeClient.setXlink(getAttributeForm().getValueAsString(ModsConstants.XLINK));
			abstractTypeClient.setTransliteration(getAttributeForm().getValueAsString(ModsConstants.TRANSLITERATION));
			abstractTypeClient.setScript(getAttributeForm().getValueAsString(ModsConstants.SCRIPT));
			abstractTypeClient.setDisplayLabel(getAttributeForm().getValueAsString(ModsConstants.DISPLAY_LABEL));
		}
		abstractTypeClient.setValue(getAttributeForm2().getValueAsString(ModsConstants.ABSTRACT));
		return abstractTypeClient;
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
