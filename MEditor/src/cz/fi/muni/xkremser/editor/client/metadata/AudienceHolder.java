package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.TargetAudienceTypeClient;

public class AudienceHolder extends ListOfListOfSimpleValuesHolder {

	public AudienceHolder() {
		this("target_audience", ModsConstants.AUTHORITY, ModsConstants.LANG, ModsConstants.XML_LANG, ModsConstants.TRANSLITERATION, ModsConstants.SCRIPT);
	}

	public AudienceHolder(String... keys) {
		super(keys);
	}

	public List<TargetAudienceTypeClient> getAudience() {
		List<TargetAudienceTypeClient> list = null;
		List<List<String>> listOfValues = getListOfList();
		if (listOfValues != null && listOfValues.size() != 0) {
			list = new ArrayList<TargetAudienceTypeClient>();
			for (List<String> values : listOfValues) {
				if (values != null) {
					TargetAudienceTypeClient val = new TargetAudienceTypeClient();
					val.setValue(values.get(0));
					val.setAuthority(values.get(1));
					val.setLang(values.get(2));
					val.setXmlLang(values.get(3));
					val.setTransliteration(values.get(4));
					val.setScript(values.get(5));
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
