package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;

public class ListOfListOfSimpleValuesHolder extends MetadataHolder {
	private final String[] keys;

	public ListOfListOfSimpleValuesHolder(String... keys) {
		this.keys = keys;
	}

	@Override
	public List<MetadataHolder> getSubelements() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getValue() {
		throw new UnsupportedOperationException();
	}

	public List<List<String>> getListOfList() {
		if (this.layout == null) {
			throw new IllegalStateException("bind the holder to some layout with data first");
		}
		Canvas[] canvases = this.layout.getMembers();
		List<List<String>> values = new ArrayList<List<String>>(canvases.length);
		int i = 0;
		for (Canvas canvas : canvases) {
			values.add(new ArrayList<String>());
			DynamicForm form = (DynamicForm) canvas;
			for (String key : keys) {
				String value = form.getValueAsString(key);
				values.get(i).add(value == null ? null : value.trim());
			}
			i++;
		}
		return values;
	}

	@Override
	public List<String> getValues() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> getAttributes() {
		throw new UnsupportedOperationException();
	}

}
