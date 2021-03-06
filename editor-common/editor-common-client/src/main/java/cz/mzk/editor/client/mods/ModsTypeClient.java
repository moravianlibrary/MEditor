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
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.13 at 05:02:55 odp. CET 
//

package cz.mzk.editor.client.mods;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ModsTypeClient.
 */
public class ModsTypeClient
        implements IsSerializable {

    /** The title info. */
    protected List<TitleInfoTypeClient> titleInfo;

    /** The name. */
    protected List<NameTypeClient> name;

    /** The type of resource. */
    protected List<TypeOfResourceTypeClient> typeOfResource;

    /** The genre. */
    protected List<GenreTypeClient> genre;

    /** The origin info. */
    protected List<OriginInfoTypeClient> originInfo;

    /** The language. */
    protected List<LanguageTypeClient> language;

    /** The physical description. */
    protected List<PhysicalDescriptionTypeClient> physicalDescription;

    /** The abstrac. */
    protected List<AbstractTypeClient> abstrac;

    /** The table of contents. */
    protected List<TableOfContentsTypeClient> tableOfContents;

    /** The target audience. */
    protected List<TargetAudienceTypeClient> targetAudience;

    /** The note. */
    protected List<NoteTypeClient> note;

    /** The subject. */
    protected List<SubjectTypeClient> subject;

    /** The classification. */
    protected List<ClassificationTypeClient> classification;

    /** The related item. */
    protected List<RelatedItemTypeClient> relatedItem;

    /** The identifier. */
    protected List<IdentifierTypeClient> identifier;

    /** The location. */
    protected List<LocationTypeClient> location;

    /** The access condition. */
    protected List<AccessConditionTypeClient> accessCondition;

    /** The part. */
    protected List<PartTypeClient> part;

    /** The extension. */
    protected List<ExtensionTypeClient> extension;

    /** The record info. */
    protected List<RecordInfoTypeClient> recordInfo;

    /** The id. */
    protected String id;

    /** The version. */
    protected String version;

    /**
     * Gets the title info.
     * 
     * @return the title info
     */
    public List<TitleInfoTypeClient> getTitleInfo() {
        return titleInfo;
    }

    /**
     * Sets the title info.
     * 
     * @param titleInfo
     *        the new title info
     */
    public void setTitleInfo(List<TitleInfoTypeClient> titleInfo) {
        this.titleInfo = titleInfo;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public List<NameTypeClient> getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *        the new name
     */
    public void setName(List<NameTypeClient> name) {
        this.name = name;
    }

    /**
     * Gets the type of resource.
     * 
     * @return the type of resource
     */
    public List<TypeOfResourceTypeClient> getTypeOfResource() {
        return typeOfResource;
    }

    /**
     * Sets the type of resource.
     * 
     * @param typeOfResource
     *        the new type of resource
     */
    public void setTypeOfResource(List<TypeOfResourceTypeClient> typeOfResource) {
        this.typeOfResource = typeOfResource;
    }

    /**
     * Gets the genre.
     * 
     * @return the genre
     */
    public List<GenreTypeClient> getGenre() {
        return genre;
    }

    /**
     * Sets the genre.
     * 
     * @param genre
     *        the new genre
     */
    public void setGenre(List<GenreTypeClient> genre) {
        this.genre = genre;
    }

    /**
     * Gets the origin info.
     * 
     * @return the origin info
     */
    public List<OriginInfoTypeClient> getOriginInfo() {
        return originInfo;
    }

    /**
     * Sets the origin info.
     * 
     * @param originInfo
     *        the new origin info
     */
    public void setOriginInfo(List<OriginInfoTypeClient> originInfo) {
        this.originInfo = originInfo;
    }

    /**
     * Gets the language.
     * 
     * @return the language
     */
    public List<LanguageTypeClient> getLanguage() {
        return language;
    }

    /**
     * Sets the language.
     * 
     * @param language
     *        the new language
     */
    public void setLanguage(List<LanguageTypeClient> language) {
        this.language = language;
    }

    /**
     * Gets the physical description.
     * 
     * @return the physical description
     */
    public List<PhysicalDescriptionTypeClient> getPhysicalDescription() {
        return physicalDescription;
    }

    /**
     * Sets the physical description.
     * 
     * @param physicalDescription
     *        the new physical description
     */
    public void setPhysicalDescription(List<PhysicalDescriptionTypeClient> physicalDescription) {
        this.physicalDescription = physicalDescription;
    }

    /**
     * Gets the abstrac.
     * 
     * @return the abstrac
     */
    public List<AbstractTypeClient> getAbstrac() {
        return abstrac;
    }

    /**
     * Sets the abstrac.
     * 
     * @param abstrac
     *        the new abstrac
     */
    public void setAbstrac(List<AbstractTypeClient> abstrac) {
        this.abstrac = abstrac;
    }

    /**
     * Gets the table of contents.
     * 
     * @return the table of contents
     */
    public List<TableOfContentsTypeClient> getTableOfContents() {
        return tableOfContents;
    }

    /**
     * Sets the table of contents.
     * 
     * @param tableOfContents
     *        the new table of contents
     */
    public void setTableOfContents(List<TableOfContentsTypeClient> tableOfContents) {
        this.tableOfContents = tableOfContents;
    }

    /**
     * Gets the target audience.
     * 
     * @return the target audience
     */
    public List<TargetAudienceTypeClient> getTargetAudience() {
        return targetAudience;
    }

    /**
     * Sets the target audience.
     * 
     * @param targetAudience
     *        the new target audience
     */
    public void setTargetAudience(List<TargetAudienceTypeClient> targetAudience) {
        this.targetAudience = targetAudience;
    }

    /**
     * Gets the note.
     * 
     * @return the note
     */
    public List<NoteTypeClient> getNote() {
        return note;
    }

    /**
     * Sets the note.
     * 
     * @param note
     *        the new note
     */
    public void setNote(List<NoteTypeClient> note) {
        this.note = note;
    }

    /**
     * Gets the subject.
     * 
     * @return the subject
     */
    public List<SubjectTypeClient> getSubject() {
        return subject;
    }

    /**
     * Sets the subject.
     * 
     * @param subject
     *        the new subject
     */
    public void setSubject(List<SubjectTypeClient> subject) {
        this.subject = subject;
    }

    /**
     * Gets the classification.
     * 
     * @return the classification
     */
    public List<ClassificationTypeClient> getClassification() {
        return classification;
    }

    /**
     * Sets the classification.
     * 
     * @param classification
     *        the new classification
     */
    public void setClassification(List<ClassificationTypeClient> classification) {
        this.classification = classification;
    }

    /**
     * Gets the related item.
     * 
     * @return the related item
     */
    public List<RelatedItemTypeClient> getRelatedItem() {
        return relatedItem;
    }

    /**
     * Sets the related item.
     * 
     * @param relatedItem
     *        the new related item
     */
    public void setRelatedItem(List<RelatedItemTypeClient> relatedItem) {
        this.relatedItem = relatedItem;
    }

    /**
     * Gets the identifier.
     * 
     * @return the identifier
     */
    public List<IdentifierTypeClient> getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier.
     * 
     * @param identifier
     *        the new identifier
     */
    public void setIdentifier(List<IdentifierTypeClient> identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets the location.
     * 
     * @return the location
     */
    public List<LocationTypeClient> getLocation() {
        return location;
    }

    /**
     * Sets the location.
     * 
     * @param location
     *        the new location
     */
    public void setLocation(List<LocationTypeClient> location) {
        this.location = location;
    }

    /**
     * Gets the access condition.
     * 
     * @return the access condition
     */
    public List<AccessConditionTypeClient> getAccessCondition() {
        return accessCondition;
    }

    /**
     * Sets the access condition.
     * 
     * @param accessCondition
     *        the new access condition
     */
    public void setAccessCondition(List<AccessConditionTypeClient> accessCondition) {
        this.accessCondition = accessCondition;
    }

    /**
     * Gets the part.
     * 
     * @return the part
     */
    public List<PartTypeClient> getPart() {
        return part;
    }

    /**
     * Sets the part.
     * 
     * @param part
     *        the new part
     */
    public void setPart(List<PartTypeClient> part) {
        this.part = part;
    }

    /**
     * Gets the extension.
     * 
     * @return the extension
     */
    public List<ExtensionTypeClient> getExtension() {
        return extension;
    }

    /**
     * Sets the extension.
     * 
     * @param extension
     *        the new extension
     */
    public void setExtension(List<ExtensionTypeClient> extension) {
        this.extension = extension;
    }

    /**
     * Gets the record info.
     * 
     * @return the record info
     */
    public List<RecordInfoTypeClient> getRecordInfo() {
        return recordInfo;
    }

    /**
     * Sets the record info.
     * 
     * @param recordInfo
     *        the new record info
     */
    public void setRecordInfo(List<RecordInfoTypeClient> recordInfo) {
        this.recordInfo = recordInfo;
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *        the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the version.
     * 
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version.
     * 
     * @param version
     *        the new version
     */
    public void setVersion(String version) {
        this.version = version;
    }

}
