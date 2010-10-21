/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.xkremser.editor.fedora;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;

/**
 * 
 * @author Administrator
 */

public class RDFModels {

	public static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(RDFModels.class.getName());

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
