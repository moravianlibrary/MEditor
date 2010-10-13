package cz.fi.muni.xkremser.editor.fedora;

import static cz.fi.muni.xkremser.editor.fedora.KrameriusModels.INTERNALPART;
import static cz.fi.muni.xkremser.editor.fedora.KrameriusModels.MONOGRAPHUNIT;
import static cz.fi.muni.xkremser.editor.fedora.KrameriusModels.PAGE;
import static cz.fi.muni.xkremser.editor.fedora.KrameriusModels.PERIODICALITEM;
import static cz.fi.muni.xkremser.editor.fedora.KrameriusModels.PERIODICALVOLUME;

/**
 * Relationships in fedora
 * 
 * @author pavels
 */
public enum FedoraRelationship {

	// our fedora relationship
	hasPage(PAGE), hasVolume(PERIODICALVOLUME), hasItem(PERIODICALITEM), hasUnit(MONOGRAPHUNIT), hasInternalPart(INTERNALPART), hasIntCompPart(INTERNALPART),

	isOnPage(null);

	private KrameriusModels poitingModel;

	private FedoraRelationship(KrameriusModels pointingModel) {
		this.poitingModel = pointingModel;
	}

	public KrameriusModels getPointingModel() {
		return poitingModel;
	}

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
