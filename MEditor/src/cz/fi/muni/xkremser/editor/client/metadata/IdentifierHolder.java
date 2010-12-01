package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.ClientUtils;
import cz.fi.muni.xkremser.editor.client.mods.IdentifierTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.YesClient;

public class IdentifierHolder extends ListOfListOfSimpleValuesHolder {

	public IdentifierHolder() {
		this("identifier", ModsConstants.TYPE, ModsConstants.DISPLAY_LABEL, ModsConstants.LANG, ModsConstants.XML_LANG, ModsConstants.TRANSLITERATION,
				ModsConstants.SCRIPT, ModsConstants.INVALID);
	}

	public IdentifierHolder(String... keys) {
		super(keys);
	}

	public List<IdentifierTypeClient> getIdentifier() {
		List<IdentifierTypeClient> list = null;
		List<List<String>> listOfValues = getListOfList();
		if (listOfValues != null && listOfValues.size() != 0) {
			list = new ArrayList<IdentifierTypeClient>();
			for (List<String> values : listOfValues) {
				if (values != null) {
					IdentifierTypeClient val = new IdentifierTypeClient();
					val.setValue(values.get(0));
					val.setType(values.get(1));
					val.setDisplayLabel(values.get(2));
					val.setLang(values.get(3));
					val.setXmlLang(values.get(4));
					val.setTransliteration(values.get(5));
					val.setScript(values.get(6));
					if (ClientUtils.toBoolean(values.get(7)))
						val.setInvalid(YesClient.YES);
					list.add(val);
				}
			}
		}
		return list;
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
