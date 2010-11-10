package cz.fi.muni.xkremser.editor.client.view.tab;

import java.util.List;

import com.smartgwt.client.widgets.layout.Layout;

public abstract class MetadataHolder {
	private final String name;
	protected Layout layout;

	public MetadataHolder(final String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "MetadataHolder [name=" + name + ", getSubelements()=" + getSubelements() + ", getAttributes()=" + getAttributes() + "]";
	}

}
