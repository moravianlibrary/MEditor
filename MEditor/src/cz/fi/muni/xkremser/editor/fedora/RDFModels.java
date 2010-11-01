/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.xkremser.editor.fedora;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;

// TODO: Auto-generated Javadoc
/**
 * The Class RDFModels.
 *
 * @author Administrator
 */

public class RDFModels {

	/** The Constant LOGGER. */
	public static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(RDFModels.class.getName());

	/**
	 * Convert rdf to model.
	 *
	 * @param rdf the rdf
	 * @return the kramerius model
	 */
	public static KrameriusModel convertRDFToModel(String rdf) {
		if (rdf.contains("hasPage")) {
			return KrameriusModel.PAGE;
		} else if (rdf.contains("hasUnit")) {
			return KrameriusModel.MONOGRAPHUNIT;
		} else if (rdf.contains("hasVolume")) {
			return KrameriusModel.PERIODICALVOLUME;
		} else if (rdf.contains("hasItem")) {
			return KrameriusModel.PERIODICALITEM;
		} else if (rdf.contains("hasIntCompPart")) {
			return KrameriusModel.INTERNALPART;
		} else if (rdf.contains("isOnPage")) {
			return KrameriusModel.PAGE;
			// } else if (rdf.contains("hasDonator")) {
			// return KrameriusModel.DONATOR;
		} else {
			System.out.println("Unfffsupported rdf: " + rdf);
			return null;
		}
	}

	/**
	 * Convert to rdf.
	 *
	 * @param km the km
	 * @return the string
	 */
	public static String convertToRdf(KrameriusModel km) {
		switch (km) {
			case MONOGRAPH:
				return "monograph";
			case MONOGRAPHUNIT:
				return "hasUnit";
			case PERIODICAL:
				return "periodical";
			case PERIODICALVOLUME:
				return "hasVolume";
			case PERIODICALITEM:
				return "hasItem";
			case INTERNALPART:
				return "hasIntCompPart";
			case PAGE:
				return "hasPage";
				// case DONATOR:
				// return "hasDonator";
			default:
				return km.toString();
		}
	}
}
