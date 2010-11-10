package cz.fi.muni.xkremser.editor.client.view.tab;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;

public class DublinCoreHolder extends MetadataHolder {

	public DublinCoreHolder(String name) {
		super(name);
	}

	@Override
	public List<MetadataHolder> getSubelements() {
		throw new UnsupportedOperationException("DC");
	}

	@Override
	public String getValue() {
		throw new UnsupportedOperationException("DC");
	}

	@Override
	public List<String> getValues() {
		List<String> values = new ArrayList<String>();
		Canvas[] canvases = this.layout.getMembers();
		for (Canvas canvas : canvases) {
			DynamicForm form = (DynamicForm) canvas;
			Object o = form.getFields()[0].getValue();
			if (o != null) {
				String value = (String) o;
				if (!"".equals(value.trim())) {
					values.add(value);
				}
			}
		}
		return values;
	}

	@Override
	public List<String> getAttributes() {
		throw new UnsupportedOperationException("unqualified Dublin Core does not have any attributes");
	}

}
