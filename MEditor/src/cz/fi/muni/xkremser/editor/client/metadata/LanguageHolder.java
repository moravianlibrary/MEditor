package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.List;

import com.smartgwt.client.widgets.form.DynamicForm;

import cz.fi.muni.xkremser.editor.client.ClientUtils;
import cz.fi.muni.xkremser.editor.client.mods.TypeOfResourceTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.YesClient;

public class LanguageHolder extends ListOfSimpleValuesHolder {
	private DynamicForm attributeForm2;

	public TypeOfResourceTypeClient getType() {
		TypeOfResourceTypeClient typeOfResourceTypeClient = new TypeOfResourceTypeClient();
		if (getAttributeForm() != null) {
			String val = getAttributeForm().getValueAsString(ModsConstants.COLLECTION);
			if (val != null && ClientUtils.toBoolean(val)) {
				typeOfResourceTypeClient.setCollection(YesClient.YES);
			}
			val = getAttributeForm().getValueAsString(ModsConstants.MANUSCRIPT);
			if (val != null && ClientUtils.toBoolean(val)) {
				typeOfResourceTypeClient.setManuscript(YesClient.YES);
			}
		}
		typeOfResourceTypeClient.setValue(getAttributeForm2().getValueAsString(ModsConstants.TYPE));
		return typeOfResourceTypeClient;
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

	public DynamicForm getAttributeForm2() {
		return attributeForm2;
	}

	public void setAttributeForm2(DynamicForm attributeForm2) {
		this.attributeForm2 = attributeForm2;
	}

}
