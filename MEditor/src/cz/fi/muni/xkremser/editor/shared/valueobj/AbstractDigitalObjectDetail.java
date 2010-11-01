/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.valueobj;

import com.google.gwt.user.client.rpc.IsSerializable;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDigitalObjectDetail.
 */
public abstract class AbstractDigitalObjectDetail implements IsSerializable {

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public abstract KrameriusModel getModel();

	/**
	 * Gets the dc.
	 *
	 * @return the dc
	 */
	public abstract DublinCore getDc();

	/**
	 * Checks for pages.
	 *
	 * @return true, if successful
	 */
	public abstract boolean hasPages();

	// public abstract boolean hasMods();

	// public Class<? extends AbstractDigitalObjectDetail> getClazz() {
	// return getClass();
	// }

	/**
	 * Instantiates a new abstract digital object detail.
	 */
	public AbstractDigitalObjectDetail() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "model: " + getModel() + "\nDC: " + getDc();
	}

}