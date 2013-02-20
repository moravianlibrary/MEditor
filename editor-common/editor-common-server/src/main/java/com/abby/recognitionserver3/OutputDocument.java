
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OutputDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OutputDocument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Files" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}ArrayOfFileContainer" minOccurs="0"/>
 *         &lt;element name="FormatSettings" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}OutputFormatSettings" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutputDocument", propOrder = {
    "files",
    "formatSettings"
})
public class OutputDocument {

    @XmlElement(name = "Files")
    protected ArrayOfFileContainer files;
    @XmlElement(name = "FormatSettings")
    protected OutputFormatSettings formatSettings;

    /**
     * Gets the value of the files property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFileContainer }
     *     
     */
    public ArrayOfFileContainer getFiles() {
        return files;
    }

    /**
     * Sets the value of the files property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFileContainer }
     *     
     */
    public void setFiles(ArrayOfFileContainer value) {
        this.files = value;
    }

    /**
     * Gets the value of the formatSettings property.
     * 
     * @return
     *     possible object is
     *     {@link OutputFormatSettings }
     *     
     */
    public OutputFormatSettings getFormatSettings() {
        return formatSettings;
    }

    /**
     * Sets the value of the formatSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputFormatSettings }
     *     
     */
    public void setFormatSettings(OutputFormatSettings value) {
        this.formatSettings = value;
    }

}
