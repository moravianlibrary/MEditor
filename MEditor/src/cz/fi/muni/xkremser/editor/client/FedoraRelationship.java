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
package cz.fi.muni.xkremser.editor.client;

import static cz.fi.muni.xkremser.editor.client.KrameriusModel.INTERNALPART;
import static cz.fi.muni.xkremser.editor.client.KrameriusModel.MONOGRAPHUNIT;
import static cz.fi.muni.xkremser.editor.client.KrameriusModel.PAGE;
import static cz.fi.muni.xkremser.editor.client.KrameriusModel.PERIODICALITEM;
import static cz.fi.muni.xkremser.editor.client.KrameriusModel.PERIODICALVOLUME;

// TODO: Auto-generated Javadoc
/**
 * Relationships in fedora.
 * 
 * @author pavels
 */
public enum FedoraRelationship {

	// our fedora relationship
	/** The has page. */
	hasPage(PAGE, "hasPage"),
	/** The has volume. */
	hasVolume(PERIODICALVOLUME, "hasVolume"),
	/** The has item. */
	hasItem(PERIODICALITEM, "hasItem"),
	/** The has unit. */
	hasUnit(MONOGRAPHUNIT, "hasUnit"),
	/** The has internal part. */
	hasInternalPart(INTERNALPART, "hasInternalPart"),
	/** The has int comp part. */
	hasIntCompPart(INTERNALPART, "hasIntCompPart"),

	/** The is on page. */
	isOnPage(null, "isOnPage");

	/** The poiting model. */
	private KrameriusModel poitingModel;

	/** The string representation. */
	private String stringRepresentation;

	/**
	 * Instantiates a new fedora relationship.
	 * 
	 * @param pointingModel
	 *          the pointing model
	 */
	private FedoraRelationship(KrameriusModel pointingModel) {
		this.poitingModel = pointingModel;
	}

	/**
	 * Instantiates a new fedora relationship.
	 * 
	 * @param pointingModel
	 *          the pointing model
	 * @param label
	 *          the label
	 */
	private FedoraRelationship(KrameriusModel pointingModel, String label) {
		this.poitingModel = pointingModel;
		this.stringRepresentation = label;
	}

	/**
	 * Gets the pointing model.
	 * 
	 * @return the pointing model
	 */
	public KrameriusModel getPointingModel() {
		return poitingModel;
	}

	/**
	 * Gets the string representation.
	 * 
	 * @return the string representation
	 */
	public String getStringRepresentation() {
		return stringRepresentation;
	}

	/**
	 * Find relation.
	 * 
	 * @param element
	 *          the element
	 * @return the fedora relationship
	 */
	public static FedoraRelationship findRelation(String element) {
		FedoraRelationship[] values = FedoraRelationship.values();
		for (FedoraRelationship fedoraRelationship : values) {
			if (fedoraRelationship.name().equals(element))
				return fedoraRelationship;
		}
		return null;
	}

	// relationship defined in Fedora Ontology Relationship
	// http://www.fedora-commons.org/definitions/1/0/fedora-relsext-ontology.rdfs
	// isPartOf,
	// hasPart,
	// isConstituentOf,
	// hasConstituent,
	// isMemberOf,
	// hasMember,
	// isSubsetOf,
	// hasSubset,
	// isMemberOfCollection,
	// hasCollectionMember,
	// isDerivationOf,
	// hasDerivation,
	// isDependentOf,
	// hasDependent,
	// isDescriptionOf,
	// HasDescription,
	// isMetadataFor,
	// HasMetadata,
	// isAnnotationOf,
	// HasAnnotation,
	// hasEquivalent,

}
