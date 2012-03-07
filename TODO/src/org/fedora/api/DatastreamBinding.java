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
 * binding key to datastream association
 * <p>
 * Java class for DatastreamBinding complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="DatastreamBinding">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bindKeyName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bindLabel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="datastreamID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="seqNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatastreamBinding", propOrder = {"bindKeyName", "bindLabel", "datastreamID", "seqNo"})
public class DatastreamBinding {

    /** The bind key name. */
    @XmlElement(required = true, nillable = true)
    protected String bindKeyName;

    /** The bind label. */
    @XmlElement(required = true, nillable = true)
    protected String bindLabel;

    /** The datastream id. */
    @XmlElement(required = true, nillable = true)
    protected String datastreamID;

    /** The seq no. */
    @XmlElement(required = true, nillable = true)
    protected String seqNo;

    /**
     * Gets the value of the bindKeyName property.
     * 
     * @return the bind key name possible object is {@link String }
     */
    public String getBindKeyName() {
        return bindKeyName;
    }

    /**
     * Sets the value of the bindKeyName property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setBindKeyName(String value) {
        this.bindKeyName = value;
    }

    /**
     * Gets the value of the bindLabel property.
     * 
     * @return the bind label possible object is {@link String }
     */
    public String getBindLabel() {
        return bindLabel;
    }

    /**
     * Sets the value of the bindLabel property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setBindLabel(String value) {
        this.bindLabel = value;
    }

    /**
     * Gets the value of the datastreamID property.
     * 
     * @return the datastream id possible object is {@link String }
     */
    public String getDatastreamID() {
        return datastreamID;
    }

    /**
     * Sets the value of the datastreamID property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setDatastreamID(String value) {
        this.datastreamID = value;
    }

    /**
     * Gets the value of the seqNo property.
     * 
     * @return the seq no possible object is {@link String }
     */
    public String getSeqNo() {
        return seqNo;
    }

    /**
     * Sets the value of the seqNo property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setSeqNo(String value) {
        this.seqNo = value;
    }

}
