
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for JobDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JobDocument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OutputDocuments" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}ArrayOfOutputDocument" minOccurs="0"/>
 *         &lt;element name="Errors" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="Warnings" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="Statistics" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}Statistics" minOccurs="0"/>
 *         &lt;element name="Attributes" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}DocumentAttributes" minOccurs="0"/>
 *         &lt;element name="CustomText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BarcodeText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JobDocument", propOrder = {
    "outputDocuments",
    "errors",
    "warnings",
    "statistics",
    "attributes",
    "customText",
    "barcodeText"
})
public class JobDocument {

    @XmlElement(name = "OutputDocuments")
    protected ArrayOfOutputDocument outputDocuments;
    @XmlElement(name = "Errors")
    protected ArrayOfString errors;
    @XmlElement(name = "Warnings")
    protected ArrayOfString warnings;
    @XmlElement(name = "Statistics")
    protected Statistics statistics;
    @XmlElement(name = "Attributes")
    protected DocumentAttributes attributes;
    @XmlElement(name = "CustomText")
    protected String customText;
    @XmlElement(name = "BarcodeText")
    protected String barcodeText;

    /**
     * Gets the value of the outputDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOutputDocument }
     *     
     */
    public ArrayOfOutputDocument getOutputDocuments() {
        return outputDocuments;
    }

    /**
     * Sets the value of the outputDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOutputDocument }
     *     
     */
    public void setOutputDocuments(ArrayOfOutputDocument value) {
        this.outputDocuments = value;
    }

    /**
     * Gets the value of the errors property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getErrors() {
        return errors;
    }

    /**
     * Sets the value of the errors property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setErrors(ArrayOfString value) {
        this.errors = value;
    }

    /**
     * Gets the value of the warnings property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getWarnings() {
        return warnings;
    }

    /**
     * Sets the value of the warnings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setWarnings(ArrayOfString value) {
        this.warnings = value;
    }

    /**
     * Gets the value of the statistics property.
     * 
     * @return
     *     possible object is
     *     {@link Statistics }
     *     
     */
    public Statistics getStatistics() {
        return statistics;
    }

    /**
     * Sets the value of the statistics property.
     * 
     * @param value
     *     allowed object is
     *     {@link Statistics }
     *     
     */
    public void setStatistics(Statistics value) {
        this.statistics = value;
    }

    /**
     * Gets the value of the attributes property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentAttributes }
     *     
     */
    public DocumentAttributes getAttributes() {
        return attributes;
    }

    /**
     * Sets the value of the attributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentAttributes }
     *     
     */
    public void setAttributes(DocumentAttributes value) {
        this.attributes = value;
    }

    /**
     * Gets the value of the customText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomText() {
        return customText;
    }

    /**
     * Sets the value of the customText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomText(String value) {
        this.customText = value;
    }

    /**
     * Gets the value of the barcodeText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBarcodeText() {
        return barcodeText;
    }

    /**
     * Sets the value of the barcodeText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBarcodeText(String value) {
        this.barcodeText = value;
    }

}
