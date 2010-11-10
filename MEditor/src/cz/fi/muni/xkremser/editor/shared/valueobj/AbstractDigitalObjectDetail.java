/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.valueobj;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDigitalObjectDetail.
 */
public abstract class AbstractDigitalObjectDetail implements IsSerializable {
	private ArrayList<ArrayList<String>> related;

	/**
	 * Gets the model.
	 * 
	 * @return the model
	 */
	public abstract KrameriusModel getModel();

	public ArrayList<ArrayList<String>> getRelated() {
		return related;
	}

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

	public AbstractDigitalObjectDetail() {
	}

	/**
	 * Instantiates a new abstract digital object detail.
	 */
	public AbstractDigitalObjectDetail(ArrayList<ArrayList<String>> related) {
		this.related = related;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "model: " + getModel() + "\nDC: " + getDc();
	}

}