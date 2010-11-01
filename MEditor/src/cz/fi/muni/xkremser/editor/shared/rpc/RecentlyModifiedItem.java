/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.rpc;

import com.google.gwt.user.client.rpc.IsSerializable;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;

// TODO: Auto-generated Javadoc
/**
 * The Class RecentlyModifiedItem.
 */
public class RecentlyModifiedItem implements IsSerializable {

	/** The uuid. */
	private String uuid;
	
	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/** The model. */
	private KrameriusModel model;

	// @SuppressWarnings("unused")
	/**
	 * Instantiates a new recently modified item.
	 */
	public RecentlyModifiedItem() {

	}

	/**
	 * Instantiates a new recently modified item.
	 *
	 * @param uuid the uuid
	 * @param name the name
	 * @param description the description
	 * @param model the model
	 */
	public RecentlyModifiedItem(String uuid, String name, String description, KrameriusModel model) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.description = description;
		this.model = model;
	}

	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Sets the uuid.
	 *
	 * @param uuid the new uuid
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public KrameriusModel getModel() {
		return model;
	}

	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel(KrameriusModel model) {
		this.model = model;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RecentlyModifiedRecord [uuid=" + uuid + ", name=" + name + ", description=" + description + ", model=" + model + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
