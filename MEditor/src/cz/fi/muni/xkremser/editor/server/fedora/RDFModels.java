/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */
package cz.fi.muni.xkremser.editor.server.fedora;

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
	 * @param rdf
	 *          the rdf
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
	 * @param km
	 *          the km
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
			default:
				return km.toString();
		}
	}
}
