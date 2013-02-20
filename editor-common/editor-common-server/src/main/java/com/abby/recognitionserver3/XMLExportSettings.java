
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for XMLExportSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="XMLExportSettings">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}OutputFormatSettings">
 *       &lt;sequence>
 *         &lt;element name="WriteCharAttributes" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="WriteExtendedCharAttributes" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="WriteCharactersFormatting" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="WriteNonDeskewedCoordinates" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PagesPerFile" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMLExportSettings", propOrder = {
    "writeCharAttributes",
    "writeExtendedCharAttributes",
    "writeCharactersFormatting",
    "writeNonDeskewedCoordinates",
    "pagesPerFile"
})
public class XMLExportSettings
    extends OutputFormatSettings
{

    @XmlElement(name = "WriteCharAttributes")
    protected boolean writeCharAttributes;
    @XmlElement(name = "WriteExtendedCharAttributes")
    protected boolean writeExtendedCharAttributes;
    @XmlElement(name = "WriteCharactersFormatting")
    protected boolean writeCharactersFormatting;
    @XmlElement(name = "WriteNonDeskewedCoordinates")
    protected boolean writeNonDeskewedCoordinates;
    @XmlElement(name = "PagesPerFile")
    protected int pagesPerFile;

    /**
     * Gets the value of the writeCharAttributes property.
     * 
     */
    public boolean isWriteCharAttributes() {
        return writeCharAttributes;
    }

    /**
     * Sets the value of the writeCharAttributes property.
     * 
     */
    public void setWriteCharAttributes(boolean value) {
        this.writeCharAttributes = value;
    }

    /**
     * Gets the value of the writeExtendedCharAttributes property.
     * 
     */
    public boolean isWriteExtendedCharAttributes() {
        return writeExtendedCharAttributes;
    }

    /**
     * Sets the value of the writeExtendedCharAttributes property.
     * 
     */
    public void setWriteExtendedCharAttributes(boolean value) {
        this.writeExtendedCharAttributes = value;
    }

    /**
     * Gets the value of the writeCharactersFormatting property.
     * 
     */
    public boolean isWriteCharactersFormatting() {
        return writeCharactersFormatting;
    }

    /**
     * Sets the value of the writeCharactersFormatting property.
     * 
     */
    public void setWriteCharactersFormatting(boolean value) {
        this.writeCharactersFormatting = value;
    }

    /**
     * Gets the value of the writeNonDeskewedCoordinates property.
     * 
     */
    public boolean isWriteNonDeskewedCoordinates() {
        return writeNonDeskewedCoordinates;
    }

    /**
     * Sets the value of the writeNonDeskewedCoordinates property.
     * 
     */
    public void setWriteNonDeskewedCoordinates(boolean value) {
        this.writeNonDeskewedCoordinates = value;
    }

    /**
     * Gets the value of the pagesPerFile property.
     * 
     */
    public int getPagesPerFile() {
        return pagesPerFile;
    }

    /**
     * Sets the value of the pagesPerFile property.
     * 
     */
    public void setPagesPerFile(int value) {
        this.pagesPerFile = value;
    }

}
