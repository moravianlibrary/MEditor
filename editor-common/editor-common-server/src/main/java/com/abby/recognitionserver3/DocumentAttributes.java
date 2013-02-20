
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocumentAttributes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentAttributes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SkipManualIndexing" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DocumentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Items" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}ArrayOfDocumentAttribute" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentAttributes", propOrder = {
    "skipManualIndexing",
    "documentType",
    "items"
})
public class DocumentAttributes {

    @XmlElement(name = "SkipManualIndexing")
    protected boolean skipManualIndexing;
    @XmlElement(name = "DocumentType")
    protected String documentType;
    @XmlElement(name = "Items")
    protected ArrayOfDocumentAttribute items;

    /**
     * Gets the value of the skipManualIndexing property.
     * 
     */
    public boolean isSkipManualIndexing() {
        return skipManualIndexing;
    }

    /**
     * Sets the value of the skipManualIndexing property.
     * 
     */
    public void setSkipManualIndexing(boolean value) {
        this.skipManualIndexing = value;
    }

    /**
     * Gets the value of the documentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * Sets the value of the documentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentType(String value) {
        this.documentType = value;
    }

    /**
     * Gets the value of the items property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDocumentAttribute }
     *     
     */
    public ArrayOfDocumentAttribute getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDocumentAttribute }
     *     
     */
    public void setItems(ArrayOfDocumentAttribute value) {
        this.items = value;
    }

}
