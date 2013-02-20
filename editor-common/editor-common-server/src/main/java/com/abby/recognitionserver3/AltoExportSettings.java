
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AltoExportSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AltoExportSettings">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}OutputFormatSettings">
 *       &lt;sequence>
 *         &lt;element name="TextCoordinatesParticularity" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}TextCoordinatesParticularityEnum"/>
 *         &lt;element name="FontFormattingMode" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}FontFormattingModeEnum"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AltoExportSettings", propOrder = {
    "textCoordinatesParticularity",
    "fontFormattingMode"
})
public class AltoExportSettings
    extends OutputFormatSettings
{

    @XmlElement(name = "TextCoordinatesParticularity", required = true)
    protected TextCoordinatesParticularityEnum textCoordinatesParticularity;
    @XmlElement(name = "FontFormattingMode", required = true)
    protected FontFormattingModeEnum fontFormattingMode;

    /**
     * Gets the value of the textCoordinatesParticularity property.
     * 
     * @return
     *     possible object is
     *     {@link TextCoordinatesParticularityEnum }
     *     
     */
    public TextCoordinatesParticularityEnum getTextCoordinatesParticularity() {
        return textCoordinatesParticularity;
    }

    /**
     * Sets the value of the textCoordinatesParticularity property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextCoordinatesParticularityEnum }
     *     
     */
    public void setTextCoordinatesParticularity(TextCoordinatesParticularityEnum value) {
        this.textCoordinatesParticularity = value;
    }

    /**
     * Gets the value of the fontFormattingMode property.
     * 
     * @return
     *     possible object is
     *     {@link FontFormattingModeEnum }
     *     
     */
    public FontFormattingModeEnum getFontFormattingMode() {
        return fontFormattingMode;
    }

    /**
     * Sets the value of the fontFormattingMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link FontFormattingModeEnum }
     *     
     */
    public void setFontFormattingMode(FontFormattingModeEnum value) {
        this.fontFormattingMode = value;
    }

}
