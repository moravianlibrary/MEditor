package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.NoteTypeClient;

public class NoteHolder extends ListOfSimpleValuesHolder {

	public NoteTypeClient getNote() {
		NoteTypeClient abstractTypeClient = new NoteTypeClient();
		if (getAttributeForm() != null) {
			abstractTypeClient.setAtType(getAttributeForm().getValueAsString(ModsConstants.TYPE));
			abstractTypeClient.setLang(getAttributeForm().getValueAsString(ModsConstants.LANG));
			abstractTypeClient.setXmlLang(getAttributeForm().getValueAsString(ModsConstants.XML_LANG));
			abstractTypeClient.setXlink(getAttributeForm().getValueAsString(ModsConstants.XLINK));
			abstractTypeClient.setTransliteration(getAttributeForm().getValueAsString(ModsConstants.TRANSLITERATION));
			abstractTypeClient.setScript(getAttributeForm().getValueAsString(ModsConstants.SCRIPT));
			abstractTypeClient.setDisplayLabel(getAttributeForm().getValueAsString(ModsConstants.DISPLAY_LABEL));
		}
		if (getAttributeForm2() != null)
			abstractTypeClient.setValue(getAttributeForm2().getValueAsString(ModsConstants.NOTE));
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
