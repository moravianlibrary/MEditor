package cz.fi.muni.xkremser.editor.client.view;

import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.KrameriusModel;

public class RecentlyModifiedRecord extends ListGridRecord {

	private String uuid;
	private String name;
	private String description;
	private KrameriusModel model;

	// @SuppressWarnings("unused")
	public RecentlyModifiedRecord() {

	}

	public RecentlyModifiedRecord(String uuid, String name, String description, KrameriusModel model) {
		super();
		setUuid(uuid);
		setName(name);
		setDescription(description);
		setModel(model);
	}

	public String getUuid() {
		return getAttribute(Constants.ATTR_UUID);
	}

	public void setUuid(String uuid) {
		setAttribute(Constants.ATTR_UUID, uuid);
	}

	public String getName() {
		return getAttribute(Constants.ATTR_NAME);
	}

	public void setName(String name) {
		setAttribute(Constants.ATTR_NAME, name);
	}

	public String getDescription() {
		return getAttribute(Constants.ATTR_DESC);
	}

	public void setDescription(String description) {
		setAttribute(Constants.ATTR_DESC, description);
	}

	public KrameriusModel getModel() {
		return KrameriusModel.parseString(getAttribute(Constants.ATTR_MODEL));
	}

	public void setModel(KrameriusModel model) {
		setAttribute(Constants.ATTR_MODEL, model);
	}

	@Override
	public String toString() {
		return "RecentlyModifiedRecord [uuid=" + uuid + ", name=" + name + ", description=" + description + ", model=" + model + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecentlyModifiedRecord other = (RecentlyModifiedRecord) obj;
		if (getUuid() == null) {
			if (other.getUuid() != null)
				return false;
		} else if (!getUuid().equals(other.getUuid()))
			return false;
		return true;
	}

}
