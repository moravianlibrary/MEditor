/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.rpc;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class InputQueueItem.
 */
public class InputQueueItem implements IsSerializable {

	/** The path. */
	private String path;
	
	/** The name. */
	private String name;
	
	/** The issn. */
	private String issn;

	/**
	 * Instantiates a new input queue item.
	 */
	@SuppressWarnings("unused")
	private InputQueueItem() {

	}

	/**
	 * Instantiates a new input queue item.
	 *
	 * @param path the path
	 * @param issn the issn
	 * @param name the name
	 */
	public InputQueueItem(String path, String issn, String name) {
		super();
		this.path = path;
		this.name = name;
		this.issn = issn;
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
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Gets the issn.
	 *
	 * @return the issn
	 */
	public String getIssn() {
		return issn;
	}

	/**
	 * Sets the issn.
	 *
	 * @param issn the new issn
	 */
	public void setIssn(String issn) {
		this.issn = issn;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + this.path + ", " + this.name + ", " + this.issn + ")";
	}

}
