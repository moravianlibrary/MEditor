package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.List;

import com.smartgwt.client.widgets.form.DynamicForm;

import cz.fi.muni.xkremser.editor.client.mods.GenreTypeClient;

public class GenreHolder extends ListOfSimpleValuesHolder {
	private DynamicForm attributeForm2;

	public GenreTypeClient getGenre() {
		GenreTypeClient genreTypeClient = new GenreTypeClient();
		if (getAttributeForm() != null) {
			genreTypeClient.setAuthority(getAttributeForm().getValueAsString(ModsConstants.AUTHORITY));
			genreTypeClient.setType(getAttributeForm().getValueAsString(ModsConstants.TYPE));
			genreTypeClient.setLang(getAttributeForm().getValueAsString(ModsConstants.LANG));
			genreTypeClient.setXmlLang(getAttributeForm().getValueAsString(ModsConstants.XML_LANG));
			genreTypeClient.setTransliteration(getAttributeForm().getValueAsString(ModsConstants.TRANSLITERATION));
			genreTypeClient.setScript(getAttributeForm().getValueAsString(ModsConstants.SCRIPT));
		}
		genreTypeClient.setValue(getAttributeForm2().getValueAsString(ModsConstants.GENRE));
		return genreTypeClient;
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
