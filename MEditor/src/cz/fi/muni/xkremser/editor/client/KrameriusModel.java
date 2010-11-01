/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Enum KrameriusModel.
 */
public enum KrameriusModel implements IsSerializable {

	/** The MONOGRAPH. */
	MONOGRAPH("monograph"), /** The MONOGRAPHUNIT. */
 MONOGRAPHUNIT("monographunit"), /** The PERIODICAL. */
 PERIODICAL("periodical"), /** The PERIODICALVOLUME. */
 PERIODICALVOLUME("periodicalvolume"), /** The PERIODICALITEM. */
 PERIODICALITEM("periodicalitem"), /** The PAGE. */
 PAGE(
			"page"), 
 /** The INTERNALPART. */
 INTERNALPART("internalpart")/* , DONATOR("donator") *//**
 * Instantiates a new kramerius model.
 *
 * @param value the value
 */
;

	private KrameriusModel(String value) {
		this.value = value;
	}

	/** The value. */
	private final String value;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * To string.
	 *
	 * @param km the km
	 * @return the string
	 */
	public static String toString(KrameriusModel km) {
		return km.getValue();
	}

	/**
	 * Parses the string.
	 *
	 * @param s the s
	 * @return the kramerius model
	 */
	public static KrameriusModel parseString(String s) {
		KrameriusModel[] values = KrameriusModel.values();
		for (KrameriusModel model : values) {
			if (model.getValue().equalsIgnoreCase(s))
				return model;
		}
		throw new RuntimeException("Unsupported type");
	}
}