
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PreprocessingParams complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PreprocessingParams">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Deskew" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="RemoveTexture" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CorrectResolution" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SplitDualPages" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ConvertToBWFormat" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="RotationType" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}RotationTypeEnum"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PreprocessingParams", propOrder = {
    "deskew",
    "removeTexture",
    "correctResolution",
    "splitDualPages",
    "convertToBWFormat",
    "rotationType"
})
public class PreprocessingParams {

    @XmlElement(name = "Deskew")
    protected boolean deskew;
    @XmlElement(name = "RemoveTexture")
    protected boolean removeTexture;
    @XmlElement(name = "CorrectResolution")
    protected boolean correctResolution;
    @XmlElement(name = "SplitDualPages")
    protected boolean splitDualPages;
    @XmlElement(name = "ConvertToBWFormat")
    protected boolean convertToBWFormat;
    @XmlElement(name = "RotationType", required = true)
    protected RotationTypeEnum rotationType;

    /**
     * Gets the value of the deskew property.
     * 
     */
    public boolean isDeskew() {
        return deskew;
    }

    /**
     * Sets the value of the deskew property.
     * 
     */
    public void setDeskew(boolean value) {
        this.deskew = value;
    }

    /**
     * Gets the value of the removeTexture property.
     * 
     */
    public boolean isRemoveTexture() {
        return removeTexture;
    }

    /**
     * Sets the value of the removeTexture property.
     * 
     */
    public void setRemoveTexture(boolean value) {
        this.removeTexture = value;
    }

    /**
     * Gets the value of the correctResolution property.
     * 
     */
    public boolean isCorrectResolution() {
        return correctResolution;
    }

    /**
     * Sets the value of the correctResolution property.
     * 
     */
    public void setCorrectResolution(boolean value) {
        this.correctResolution = value;
    }

    /**
     * Gets the value of the splitDualPages property.
     * 
     */
    public boolean isSplitDualPages() {
        return splitDualPages;
    }

    /**
     * Sets the value of the splitDualPages property.
     * 
     */
    public void setSplitDualPages(boolean value) {
        this.splitDualPages = value;
    }

    /**
     * Gets the value of the convertToBWFormat property.
     * 
     */
    public boolean isConvertToBWFormat() {
        return convertToBWFormat;
    }

    /**
     * Sets the value of the convertToBWFormat property.
     * 
     */
    public void setConvertToBWFormat(boolean value) {
        this.convertToBWFormat = value;
    }

    /**
     * Gets the value of the rotationType property.
     * 
     * @return
     *     possible object is
     *     {@link RotationTypeEnum }
     *     
     */
    public RotationTypeEnum getRotationType() {
        return rotationType;
    }

    /**
     * Sets the value of the rotationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RotationTypeEnum }
     *     
     */
    public void setRotationType(RotationTypeEnum value) {
        this.rotationType = value;
    }

}
