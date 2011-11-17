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

package cz.fi.muni.xkremser.editor.shared.rpc;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.fi.muni.xkremser.editor.client.DublinCoreConstants;
import cz.fi.muni.xkremser.editor.client.util.Constants;

// TODO: Auto-generated Javadoc
public class DublinCore
        implements Serializable {

    private static final long serialVersionUID = 4853881157734392676L;

    private int id;

    /** The title. */
    private List<String> title;

    /** The identifier. */
    private List<String> identifier;

    /** The coverage. */
    private List<String> coverage;

    /** The relation. */
    private List<String> relation;

    /** The source. */
    private List<String> source;

    /** The creator. */
    private List<String> creator;

    /** The publisher. */
    private List<String> publisher;

    /** The contributor. */
    private List<String> contributor;

    /** The date. */
    private List<String> date;

    /** The language. */
    private List<String> language;

    /** The description. */
    private List<String> description;

    /** The format. */
    private List<String> format;

    /** The subject. */
    private List<String> subject;

    /** The type. */
    private List<String> type;

    /** The rights. */
    private List<String> rights;

    /**
     * Adds the identifier.
     * 
     * @param id
     *        the id
     */
    public void addIdentifier(String id) {
        if (id == null || "".equals(id)) return;
        if (this.identifier == null) {
            this.identifier = new ArrayList<String>();
        }
        this.identifier.add(id);
    }

    /**
     * Adds the qualified identifier.
     * 
     * @param prefix
     *        the prefix
     * @param id
     *        the id
     */
    public void addQualifiedIdentifier(String prefix, String id) {
        if (id == null || "".equals(id)) return;
        if (this.identifier == null) {
            this.identifier = new ArrayList<String>();
        }
        this.identifier.add(prefix + ":" + id);
    }

    /**
     * Adds the title.
     * 
     * @param title
     *        the title
     */
    public void addTitle(String title) {
        if (title == null || "".equals(title)) return;
        if (this.title == null) {
            this.title = new ArrayList<String>();
        }
        this.title.add(title);
    }

    /**
     * Gets the title.
     * 
     * @return the title
     */
    public List<String> getTitle() {
        return title;
    }

    /**
     * Sets the title.
     * 
     * @param title
     *        the new title
     */
    public void setTitle(List<String> title) {
        this.title = title;
    }

    /**
     * Gets the identifier.
     * 
     * @return the identifier
     */
    public List<String> getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier.
     * 
     * @param identifier
     *        the new identifier
     */
    public void setIdentifier(List<String> identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets the creator.
     * 
     * @return the creator
     */
    public List<String> getCreator() {
        return creator;
    }

    /**
     * Sets the creator.
     * 
     * @param creator
     *        the new creator
     */
    public void setCreator(List<String> creator) {
        this.creator = creator;
    }

    /**
     * Gets the publisher.
     * 
     * @return the publisher
     */
    public List<String> getPublisher() {
        return publisher;
    }

    /**
     * Sets the publisher.
     * 
     * @param publisher
     *        the new publisher
     */
    public void setPublisher(List<String> publisher) {
        this.publisher = publisher;
    }

    /**
     * Gets the contributor.
     * 
     * @return the contributor
     */
    public List<String> getContributor() {
        return contributor;
    }

    /**
     * Sets the contributor.
     * 
     * @param contributor
     *        the new contributor
     */
    public void setContributor(List<String> contributor) {
        this.contributor = contributor;
    }

    /**
     * Gets the date.
     * 
     * @return the date
     */
    public List<String> getDate() {
        return date;
    }

    /**
     * Sets the date.
     * 
     * @param date
     *        the new date
     */
    public void setDate(List<String> date) {
        this.date = date;
    }

    /**
     * Gets the language.
     * 
     * @return the language
     */
    public List<String> getLanguage() {
        return language;
    }

    /**
     * Sets the language.
     * 
     * @param language
     *        the new language
     */
    public void setLanguage(List<String> language) {
        this.language = language;
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public List<String> getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * 
     * @param description
     *        the new description
     */
    public void setDescription(List<String> description) {
        this.description = description;
    }

    /**
     * Gets the format.
     * 
     * @return the format
     */
    public List<String> getFormat() {
        return format;
    }

    /**
     * Sets the format.
     * 
     * @param format
     *        the new format
     */
    public void setFormat(List<String> format) {
        this.format = format;
    }

    /**
     * Gets the subject.
     * 
     * @return the subject
     */
    public List<String> getSubject() {
        return subject;
    }

    /**
     * Sets the subject.
     * 
     * @param subject
     *        the new subject
     */
    public void setSubject(List<String> subject) {
        this.subject = subject;
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public List<String> getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type
     *        the new type
     */
    public void setType(List<String> type) {
        this.type = type;
    }

    /**
     * Gets the rights.
     * 
     * @return the rights
     */
    public List<String> getRights() {
        return rights;
    }

    /**
     * Sets the rights.
     * 
     * @param rights
     *        the new rights
     */
    public void setRights(List<String> rights) {
        this.rights = rights;
    }

    /**
     * Gets the coverage.
     * 
     * @return the coverage
     */
    public List<String> getCoverage() {
        return coverage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * Sets the coverage.
     * 
     * @param coverage
     *        the new coverage
     */
    public void setCoverage(List<String> coverage) {
        this.coverage = coverage;
    }

    /**
     * Gets the relation.
     * 
     * @return the relation
     */
    public List<String> getRelation() {
        return relation;
    }

    /**
     * Sets the relation.
     * 
     * @param relation
     *        the new relation
     */
    public void setRelation(List<String> relation) {
        this.relation = relation;
    }

    /**
     * Gets the source.
     * 
     * @return the source
     */
    public List<String> getSource() {
        return source;
    }

    /**
     * Sets the source.
     * 
     * @param source
     *        the new source
     */
    public void setSource(List<String> source) {
        this.source = source;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DublinCore [title=" + title + ", identifier=" + identifier + ", coverage=" + coverage
                + ", relation=" + relation + ", source=" + source + ", creator=" + creator + ", publisher="
                + publisher + ", contributor=" + contributor + ", date=" + date + ", language=" + language
                + ", description=" + description + ", format=" + format + ", subject=" + subject + ", type="
                + type + ", rights=" + rights + "]";
    }

    public ListGridRecord toRecord() {
        ListGridRecord record = new ListGridRecord();
        record.setAttribute(Constants.ATTR_GENERIC_ID, getId());
        if (getContributor() != null) {
            record.setAttribute(DublinCoreConstants.DC_CONTRIBUTOR, getContributor().toArray(new String[] {}));
        }
        if (getCoverage() != null) {
            record.setAttribute(DublinCoreConstants.DC_COVERAGE, getCoverage().toArray(new String[] {}));
        }
        if (getCreator() != null) {
            record.setAttribute(DublinCoreConstants.DC_CREATOR, getCreator().toArray(new String[] {}));
        }
        if (getDate() != null) {
            record.setAttribute(DublinCoreConstants.DC_DATE, getDate().toArray(new String[] {}));
        }
        if (getDescription() != null) {
            record.setAttribute(DublinCoreConstants.DC_DESCRIPTION, getDescription().toArray(new String[] {}));
        }
        if (getFormat() != null) {
            record.setAttribute(DublinCoreConstants.DC_FORMAT, getFormat().toArray(new String[] {}));
        }
        if (getIdentifier() != null) {
            record.setAttribute(DublinCoreConstants.DC_IDENTIFIER, getIdentifier().toArray(new String[] {}));
        }
        if (getLanguage() != null) {
            record.setAttribute(DublinCoreConstants.DC_LANGUAGE, getLanguage().toArray(new String[] {}));
        }
        if (getPublisher() != null) {
            record.setAttribute(DublinCoreConstants.DC_PUBLISHER, getPublisher().toArray(new String[] {}));
        }
        if (getRelation() != null) {
            record.setAttribute(DublinCoreConstants.DC_RELATION, getRelation().toArray(new String[] {}));
        }
        if (getRights() != null) {
            record.setAttribute(DublinCoreConstants.DC_RIGHTS, getRights().toArray(new String[] {}));
        }
        if (getSource() != null) {
            record.setAttribute(DublinCoreConstants.DC_SOURCE, getSource().toArray(new String[] {}));
        }
        if (getSubject() != null) {
            record.setAttribute(DublinCoreConstants.DC_SUBJECT, getSubject().toArray(new String[] {}));
        }
        if (getTitle() != null) {
            record.setAttribute(DublinCoreConstants.DC_TITLE, getTitle().toArray(new String[] {}));
        }
        if (getType() != null) {
            record.setAttribute(DublinCoreConstants.DC_TYPE, getType().toArray(new String[] {}));
        }
        return record;
    }
}
