package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.form.DynamicForm;

import cz.fi.muni.xkremser.editor.client.mods.CodeOrTextClient;
import cz.fi.muni.xkremser.editor.client.mods.LanguageTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.LanguageTypeClient.LanguageTermClient;

public class LanguageHolder extends ListOfSimpleValuesHolder {
	private DynamicForm attributeForm;
	private final ListOfListOfSimpleValuesHolder langTerms;

	public LanguageHolder() {
		this.langTerms = new ListOfListOfSimpleValuesHolder("language_term", ModsConstants.TYPE, ModsConstants.AUTHORITY);
	}

	public LanguageTypeClient getLanguage() {
		LanguageTypeClient languageTypeClient = new LanguageTypeClient();
		if (getAttributeForm() != null) {
			languageTypeClient.setObjectPart(getAttributeForm().getValueAsString(ModsConstants.OBJECT_PART));
		}
		List<LanguageTermClient> list = null;
		List<List<String>> listOfValues = langTerms.getListOfList();
		if (listOfValues != null && listOfValues.size() != 0) {
			list = new ArrayList<LanguageTermClient>();
			for (List<String> values : listOfValues) {
				LanguageTermClient languageTermClient = new LanguageTermClient();
				languageTermClient.setValue(values.get(0));
				languageTermClient.setType(CodeOrTextClient.fromValue(values.get(1)));
				languageTermClient.setAuthority(values.get(2));
				list.add(languageTermClient);
			}
		}
		languageTypeClient.setLanguageTerm(list);
		return languageTypeClient;
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

	@Override
	public DynamicForm getAttributeForm() {
		return attributeForm;
	}

	@Override
	public void setAttributeForm(DynamicForm attributeForm) {
		this.attributeForm = attributeForm;
	}

	public ListOfListOfSimpleValuesHolder getLangTerms() {
		return langTerms;
	}

}
