package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.List;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.Layout;

public abstract class MetadataHolder {
	protected Layout layout;
	private DynamicForm attributeForm;

	public MetadataHolder() {
	}

	public void setAttributeForm(DynamicForm form) {
		this.attributeForm = form;
	}

	public DynamicForm getAttributeForm() {
		return this.attributeForm;
	}

	public abstract List<MetadataHolder> getSubelements();

	public abstract List<String> getValues();

	public abstract String getValue();

	public abstract List<String> getAttributes();

	public MetadataHolder getSubelement() {
		return getSubelements().get(0);
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

}
