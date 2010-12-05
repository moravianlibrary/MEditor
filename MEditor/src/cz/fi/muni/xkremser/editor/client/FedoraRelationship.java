/**
 * Metadata Editor
 * @author Jiri Kremser
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
