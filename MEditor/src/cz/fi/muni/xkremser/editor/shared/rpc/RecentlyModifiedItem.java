package cz.fi.muni.xkremser.editor.shared.rpc;

import com.google.gwt.user.client.rpc.IsSerializable;

import cz.fi.muni.xkremser.editor.client.Constants;

public class RecentlyModifiedItem implements IsSerializable {

	private String uuid;
	private String name;
	private String description;
	private Constants.KrameriusModel model;

	// @SuppressWarnings("unused")
	public RecentlyModifiedItem() {

	}

	public RecentlyModifiedItem(String uuid, String name, String description, Constants.KrameriusModel model) {
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

	public Constants.KrameriusModel getModel() {
		return model;
	}

	public void setModel(Constants.KrameriusModel model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "RecentlyModifiedItem [uuid=" + uuid + ", name=" + name + ", description=" + description + ", model=" + model + "]";
	}

}
