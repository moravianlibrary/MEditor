package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.ExtensionTypeClient;

public class ExtensionHolder extends ListOfSimpleValuesHolder {

	public ExtensionTypeClient getExtension() {
		ExtensionTypeClient value = new ExtensionTypeClient();
		if (getAttributeForm() != null) {
			value.setNameSpace(getAttributeForm().getValueAsString(ModsConstants.NAMESPACE));
		}
		if (getAttributeForm2() != null && getAttributeForm2().getValueAsString(ModsConstants.EXTENSION) != null
				&& !"".equals(getAttributeForm2().getValueAsString(ModsConstants.EXTENSION)))
			value.setContent(getAttributeForm2().getValueAsString(ModsConstants.EXTENSION));
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
