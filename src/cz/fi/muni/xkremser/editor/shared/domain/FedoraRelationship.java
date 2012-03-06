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

package cz.fi.muni.xkremser.editor.shared.domain;

import static cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel.INTERNALPART;
import static cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel.MONOGRAPHUNIT;
import static cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel.PAGE;
import static cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel.PERIODICALITEM;
import static cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel.PERIODICALVOLUME;

// TODO: Auto-generated Javadoc
public enum FedoraRelationship {

    // this class depends on FedoraNamespaceContext

    // our fedora relationship
    /** The has page. */
    hasPage(PAGE, "hasPage", "/rdf:RDF/rdf:Description/kramerius:hasPage"),
    /** The has volume. */
    hasVolume(PERIODICALVOLUME, "hasVolume", "/rdf:RDF/rdf:Description/kramerius:hasVolume"),
    /** The has item. */
    hasItem(PERIODICALITEM, "hasItem", "/rdf:RDF/rdf:Description/kramerius:hasItem"),
    /** The has unit. */
    hasUnit(MONOGRAPHUNIT, "hasUnit", "/rdf:RDF/rdf:Description/kramerius:hasUnit"),
    /** The has internal part. */
    hasInternalPart(INTERNALPART, "hasInternalPart", "/rdf:RDF/rdf:Description/kramerius:hasInternalPart"),
    /** The has int comp part. */
    hasIntCompPart(INTERNALPART, "hasIntCompPart", "/rdf:RDF/rdf:Description/kramerius:hasIntCompPart"),

    /** The is on page. */
    isOnPage(null, "isOnPage", "/rdf:RDF/rdf:Description/kramerius:isOnPage");

    public static final String ATTR_RESOURCE_SUFIX = "/@rdf:resource";

    /** The poiting model. */
    private DigitalObjectModel targetModel;

    /** The string representation. */
    private String stringRepresentation;

    private String xPathNamespaceAwareQuery;

    /**
     * Instantiates a new fedora relationship.
     * 
     * @param targetModel
     *        the pointing model
     */
    private FedoraRelationship(DigitalObjectModel targetModel) {
        this.targetModel = targetModel;
    }

    /**
     * Instantiates a new fedora relationship.
     * 
     * @param pointingModel
     *        the pointing model
     * @param label
     *        the label
     */
    private FedoraRelationship(DigitalObjectModel targetModel, String label) {
        this.targetModel = targetModel;
        this.stringRepresentation = label;
    }

    private FedoraRelationship(DigitalObjectModel targetModel, String label, String xPathNamespaceAwareQuery) {
        this.targetModel = targetModel;
        this.stringRepresentation = label;
        this.xPathNamespaceAwareQuery = xPathNamespaceAwareQuery;
    }

    /**
     * Gets the pointing model.
     * 
     * @return the pointing model
     */
    public DigitalObjectModel getTargetModel() {
        return targetModel;
    }

    /**
     * Gets the string representation.
     * 
     * @return the string representation
     */
    public String getStringRepresentation() {
        return stringRepresentation;
    }

    public String getXPathNamespaceAwareQuery() {
        return xPathNamespaceAwareQuery;
    }

    /**
     * Find relation.
     * 
     * @param element
     *        the element
     * @return the fedora relationship
     */
    public static FedoraRelationship findRelation(String element) {
        FedoraRelationship[] values = FedoraRelationship.values();
        for (FedoraRelationship fedoraRelationship : values) {
            if (fedoraRelationship.name().equals(element)) return fedoraRelationship;
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
