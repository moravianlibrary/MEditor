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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


// TODO: Auto-generated Javadoc
/**
 * <p>Java class for FieldSearchResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FieldSearchResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="listSession" type="{http://www.fedora.info/definitions/1/0/types/}ListSession" minOccurs="0"/>
 *         &lt;element name="resultList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="objectFields" type="{http://www.fedora.info/definitions/1/0/types/}ObjectFields" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FieldSearchResult", propOrder = {
    "listSession",
    "resultList"
})
public class FieldSearchResult {

    /** The list session. */
    @XmlElementRef(name = "listSession", type = JAXBElement.class)
    protected JAXBElement<ListSession> listSession;
    
    /** The result list. */
    @XmlElement(required = true)
    protected FieldSearchResult.ResultList resultList;

    /**
     * Gets the value of the listSession property.
     *
     * @return the list session
     * possible object is
     * {@link JAXBElement }{@code <}{@link ListSession }{@code >}
     */
    public JAXBElement<ListSession> getListSession() {
        return listSession;
    }

    /**
     * Sets the value of the listSession property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ListSession }{@code >}
     *     
     */
    public void setListSession(JAXBElement<ListSession> value) {
        this.listSession = ((JAXBElement<ListSession> ) value);
    }

    /**
     * Gets the value of the resultList property.
     *
     * @return the result list
     * possible object is
     * {@link FieldSearchResult.ResultList }
     */
    public FieldSearchResult.ResultList getResultList() {
        return resultList;
    }

    /**
     * Sets the value of the resultList property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldSearchResult.ResultList }
     *     
     */
    public void setResultList(FieldSearchResult.ResultList value) {
        this.resultList = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="objectFields" type="{http://www.fedora.info/definitions/1/0/types/}ObjectFields" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "objectFields"
    })
    public static class ResultList {

        /** The object fields. */
        protected List<ObjectFields> objectFields;

        /**
         * Gets the value of the objectFields property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the objectFields property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         * getObjectFields().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         *
         * @return the object fields
         * {@link ObjectFields }
         */
        public List<ObjectFields> getObjectFields() {
            if (objectFields == null) {
                objectFields = new ArrayList<ObjectFields>();
            }
            return this.objectFields;
        }

    }

}
