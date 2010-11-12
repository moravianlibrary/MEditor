/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.view;

import com.smartgwt.client.widgets.tile.TileRecord;

import cz.fi.muni.xkremser.editor.client.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class PageRecord.
 */
public class ContainerRecord extends TileRecord {

	/**
	 * Instantiates a new page record.
	 */
	public ContainerRecord() {
	}

	/**
	 * Instantiates a new page record.
	 * 
	 * @param name
	 *          the name
	 * @param uuid
	 *          the uuid
	 * @param picture
	 *          the picture
	 */
	public ContainerRecord(String name, String uuid, String picture) {
		this(name, uuid, picture, null);
	}

	/**
	 * Instantiates a new page record.
	 * 
	 * @param name
	 *          the name
	 * @param uuid
	 *          the uuid
	 * @param picture
	 *          the picture
	 * @param description
	 *          the description
	 */
	public ContainerRecord(String name, String uuid, String picture, String description) {
		setName(name);
		setUuid(uuid);
		setPicture(picture);
		setDescription(description);
	}

	/**
	 * Set the name.
	 * 
	 * @param name
	 *          the name
	 */
	public void setName(String name) {
		setAttribute(Constants.ATTR_NAME, name);
	}

	/**
	 * Return the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return getAttribute(Constants.ATTR_NAME);
	}

	/**
	 * Set the uuid.
	 * 
	 * @param uuid
	 *          the uuid
	 */
	public void setUuid(String uuid) {
		setAttribute(Constants.ATTR_UUID, uuid);
	}

	/**
	 * Return the uuid.
	 * 
	 * @return the uuid
	 */
	public String getUuid() {
		return getAttribute(Constants.ATTR_UUID);
	}

	/**
	 * Set the picture.
	 * 
	 * @param picture
	 *          the picture
	 */
	public void setPicture(String picture) {
		setAttribute(Constants.ATTR_PICTURE, picture);
	}

	/**
	 * Return the picture.
	 * 
	 * @return the picture
	 */
	public String getPicture() {
		return getAttribute(Constants.ATTR_PICTURE);
	}

	/**
	 * Set the description.
	 * 
	 * @param description
	 *          the description
	 */
	public void setDescription(String description) {
		setAttribute(Constants.ATTR_DESC, description);
	}

	/**
	 * Return the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return getAttribute(Constants.ATTR_DESC);
	}

	public ContainerRecord deepCopy() {
		return new ContainerRecord(getName(), getUuid(), getPicture(), getDescription());
	}

	@Override
	public String toString() {
		return "ContainerRecord [getName()=" + getName() + ", getUuid()=" + getUuid() + ", getPicture()=" + getPicture() + ", getDescription()=" + getDescription()
				+ ", deepCopy()=" + deepCopy() + "]";
	}

}
