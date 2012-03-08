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

package org.fedora.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * Java class for RelationshipTuple complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="RelationshipTuple">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="subject" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="predicate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="object" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isLiteral" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="datatype" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelationshipTuple", propOrder = {"subject", "predicate", "object", "isLiteral", "datatype"})
public class RelationshipTuple {

    /** The subject. */
    @XmlElement(required = true)
    protected String subject;

    /** The predicate. */
    @XmlElement(required = true)
    protected String predicate;

    /** The object. */
    @XmlElement(required = true)
    protected String object;

    /** The is literal. */
    protected boolean isLiteral;

    /** The datatype. */
    @XmlElement(required = true, nillable = true)
    protected String datatype;

    /**
     * Gets the value of the subject property.
     * 
     * @return the subject possible object is {@link String }
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setSubject(String value) {
        this.subject = value;
    }

    /**
     * Gets the value of the predicate property.
     * 
     * @return the predicate possible object is {@link String }
     */
    public String getPredicate() {
        return predicate;
    }

    /**
     * Sets the value of the predicate property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setPredicate(String value) {
        this.predicate = value;
    }

    /**
     * Gets the value of the object property.
     * 
     * @return the object possible object is {@link String }
     */
    public String getObject() {
        return object;
    }

    /**
     * Sets the value of the object property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setObject(String value) {
        this.object = value;
    }

    /**
     * Gets the value of the isLiteral property.
     * 
     * @return true, if is checks if is literal
     */
    public boolean isIsLiteral() {
        return isLiteral;
    }

    /**
     * Sets the value of the isLiteral property.
     * 
     * @param value
     *        the new checks if is literal
     */
    public void setIsLiteral(boolean value) {
        this.isLiteral = value;
    }

    /**
     * Gets the value of the datatype property.
     * 
     * @return the datatype possible object is {@link String }
     */
    public String getDatatype() {
        return datatype;
    }

    /**
     * Sets the value of the datatype property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setDatatype(String value) {
        this.datatype = value;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RelationshipTuple [subject=" + subject + ", predicate=" + predicate + ", object=" + object
                + ", isLiteral=" + isLiteral + ", datatype=" + datatype + "]";
    }

}
