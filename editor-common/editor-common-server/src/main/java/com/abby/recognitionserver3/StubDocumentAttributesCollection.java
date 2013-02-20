
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StubDocumentAttributesCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StubDocumentAttributesCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Bool" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}BooleanDocumentAttribute" minOccurs="0"/>
 *         &lt;element name="SingleLine" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}SingleLineDocumentAttribute" minOccurs="0"/>
 *         &lt;element name="RegularExpression" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}RegularExpressionDocumentAttribute" minOccurs="0"/>
 *         &lt;element name="Enumeration" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}EnumerationDocumentAttribute" minOccurs="0"/>
 *         &lt;element name="MultipleLines" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}MultipleLinesDocumentAttribute" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StubDocumentAttributesCollection", propOrder = {
    "bool",
    "singleLine",
    "regularExpression",
    "enumeration",
    "multipleLines"
})
public class StubDocumentAttributesCollection {

    @XmlElement(name = "Bool")
    protected BooleanDocumentAttribute bool;
    @XmlElement(name = "SingleLine")
    protected SingleLineDocumentAttribute singleLine;
    @XmlElement(name = "RegularExpression")
    protected RegularExpressionDocumentAttribute regularExpression;
    @XmlElement(name = "Enumeration")
    protected EnumerationDocumentAttribute enumeration;
    @XmlElement(name = "MultipleLines")
    protected MultipleLinesDocumentAttribute multipleLines;

    /**
     * Gets the value of the bool property.
     * 
     * @return
     *     possible object is
     *     {@link BooleanDocumentAttribute }
     *     
     */
    public BooleanDocumentAttribute getBool() {
        return bool;
    }

    /**
     * Sets the value of the bool property.
     * 
     * @param value
     *     allowed object is
     *     {@link BooleanDocumentAttribute }
     *     
     */
    public void setBool(BooleanDocumentAttribute value) {
        this.bool = value;
    }

    /**
     * Gets the value of the singleLine property.
     * 
     * @return
     *     possible object is
     *     {@link SingleLineDocumentAttribute }
     *     
     */
    public SingleLineDocumentAttribute getSingleLine() {
        return singleLine;
    }

    /**
     * Sets the value of the singleLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link SingleLineDocumentAttribute }
     *     
     */
    public void setSingleLine(SingleLineDocumentAttribute value) {
        this.singleLine = value;
    }

    /**
     * Gets the value of the regularExpression property.
     * 
     * @return
     *     possible object is
     *     {@link RegularExpressionDocumentAttribute }
     *     
     */
    public RegularExpressionDocumentAttribute getRegularExpression() {
        return regularExpression;
    }

    /**
     * Sets the value of the regularExpression property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegularExpressionDocumentAttribute }
     *     
     */
    public void setRegularExpression(RegularExpressionDocumentAttribute value) {
        this.regularExpression = value;
    }

    /**
     * Gets the value of the enumeration property.
     * 
     * @return
     *     possible object is
     *     {@link EnumerationDocumentAttribute }
     *     
     */
    public EnumerationDocumentAttribute getEnumeration() {
        return enumeration;
    }

    /**
     * Sets the value of the enumeration property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumerationDocumentAttribute }
     *     
     */
    public void setEnumeration(EnumerationDocumentAttribute value) {
        this.enumeration = value;
    }

    /**
     * Gets the value of the multipleLines property.
     * 
     * @return
     *     possible object is
     *     {@link MultipleLinesDocumentAttribute }
     *     
     */
    public MultipleLinesDocumentAttribute getMultipleLines() {
        return multipleLines;
    }

    /**
     * Sets the value of the multipleLines property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultipleLinesDocumentAttribute }
     *     
     */
    public void setMultipleLines(MultipleLinesDocumentAttribute value) {
        this.multipleLines = value;
    }

}
