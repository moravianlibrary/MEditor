package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.ClassificationTypeClient;

public class ClassificationHolder extends ListOfListOfSimpleValuesHolder {

	public ClassificationHolder(String... keys) {
		super(keys);
	}

	public List<ClassificationTypeClient> getClassification() {
		List<ClassificationTypeClient> list = null;
		List<List<String>> listOfValues = getListOfList();
		if (listOfValues != null && listOfValues.size() != 0) {
			list = new ArrayList<ClassificationTypeClient>();
			for (List<String> values : listOfValues) {
				if (values != null) {
					ClassificationTypeClient val = new ClassificationTypeClient();
					val.setValue(values.get(0));
					val.setAuthority(values.get(1));
					val.setEdition(values.get(2));
					val.setDisplayLabel(values.get(3));
					val.setLang(values.get(4));
					val.setXmlLang(values.get(5));
					val.setTransliteration(values.get(6));
					val.setScript(values.get(7));
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
