/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.view;

import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.KrameriusModel;

// TODO: Auto-generated Javadoc
/**
 * The Class RecentlyModifiedRecord.
 */
public class RecentlyModifiedRecord extends ListGridRecord {

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
	 * Instantiates a new recently modified record.
	 */
	public RecentlyModifiedRecord() {

	}

	/**
	 * Instantiates a new recently modified record.
	 *
	 * @param uuid the uuid
	 * @param name the name
	 * @param description the description
	 * @param model the model
	 */
	public RecentlyModifiedRecord(String uuid, String name, String description, KrameriusModel model) {
		super();
		setUuid(uuid);
		setName(name);
		setDescription(description);
		setModel(model);
	}

	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	public String getUuid() {
		return getAttribute(Constants.ATTR_UUID);
	}

	/**
	 * Sets the uuid.
	 *
	 * @param uuid the new uuid
	 */
	public void setUuid(String uuid) {
		setAttribute(Constants.ATTR_UUID, uuid);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return getAttribute(Constants.ATTR_NAME);
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		setAttribute(Constants.ATTR_NAME, name);
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return getAttribute(Constants.ATTR_DESC);
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		setAttribute(Constants.ATTR_DESC, description);
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public KrameriusModel getModel() {
		return KrameriusModel.parseString(getAttribute(Constants.ATTR_MODEL));
	}

	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel(KrameriusModel model) {
		setAttribute(Constants.ATTR_MODEL, model);
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
		result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
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
		RecentlyModifiedRecord other = (RecentlyModifiedRecord) obj;
		if (getUuid() == null) {
			if (other.getUuid() != null)
				return false;
		} else if (!getUuid().equals(other.getUuid()))
			return false;
		return true;
	}

}
