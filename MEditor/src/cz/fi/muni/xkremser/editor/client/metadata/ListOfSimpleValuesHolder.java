package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;

public class ListOfSimpleValuesHolder extends MetadataHolder {

	private DynamicForm attributeForm2;

	public ListOfSimpleValuesHolder() {
	}

	@Override
	public List<MetadataHolder> getSubelements() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> getValues() {
		if (layout == null)
			return null;
		List<String> values = new ArrayList<String>();
		Canvas[] canvases = this.layout.getMembers();
		for (Canvas canvas : canvases) {
			DynamicForm form = (DynamicForm) canvas;
			Object o = form.getFields()[0].getValue();
			String value = null;
			if (o != null) {
				if (o instanceof String) {
					value = (String) o;
				} else if (o instanceof Date) {
					value = ((Date) o).toGMTString();
				}
				if (!"".equals(value.trim())) {
					values.add(value);
				}
			}
		}
		return values;
	}

	@Override
	public List<String> getAttributes() {
		throw new UnsupportedOperationException();
	}

	public DynamicForm getAttributeForm2() {
		return attributeForm2;
	}

	public void setAttributeForm2(DynamicForm attributeForm2) {
		this.attributeForm2 = attributeForm2;
	}

}
