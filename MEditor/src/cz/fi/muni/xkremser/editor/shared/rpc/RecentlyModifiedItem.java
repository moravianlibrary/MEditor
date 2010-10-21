package cz.fi.muni.xkremser.editor.shared.rpc;

import com.google.gwt.user.client.rpc.IsSerializable;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;

public class RecentlyModifiedItem implements IsSerializable {

	private String uuid;
	private String name;
	private String description;
	private KrameriusModel model;

	// @SuppressWarnings("unused")
	public RecentlyModifiedItem() {

	}

	public RecentlyModifiedItem(String uuid, String name, String description, KrameriusModel model) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.description = description;
		this.model = model;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public KrameriusModel getModel() {
		return model;
	}

	public void setModel(KrameriusModel model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "RecentlyModifiedRecord [uuid=" + uuid + ", name=" + name + ", description=" + description + ", model=" + model + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		RecentlyModifiedItem other = (RecentlyModifiedItem) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
