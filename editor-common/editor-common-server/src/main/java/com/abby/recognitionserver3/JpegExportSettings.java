
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for JpegExportSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JpegExportSettings">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}OutputFormatSettings">
 *       &lt;sequence>
 *         &lt;element name="Quality" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Resolution" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ColorMode" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}ImageColorModeEnum"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JpegExportSettings", propOrder = {
    "quality",
    "resolution",
    "colorMode"
})
public class JpegExportSettings
    extends OutputFormatSettings
{

    @XmlElement(name = "Quality")
    protected int quality;
    @XmlElement(name = "Resolution")
    protected int resolution;
    @XmlElement(name = "ColorMode", required = true)
    protected ImageColorModeEnum colorMode;

    /**
     * Gets the value of the quality property.
     * 
     */
    public int getQuality() {
        return quality;
    }

    /**
     * Sets the value of the quality property.
     * 
     */
    public void setQuality(int value) {
        this.quality = value;
    }

    /**
     * Gets the value of the resolution property.
     * 
     */
    public int getResolution() {
        return resolution;
    }

    /**
     * Sets the value of the resolution property.
     * 
     */
    public void setResolution(int value) {
        this.resolution = value;
    }

    /**
     * Gets the value of the colorMode property.
     * 
     * @return
     *     possible object is
     *     {@link ImageColorModeEnum }
     *     
     */
    public ImageColorModeEnum getColorMode() {
        return colorMode;
    }

    /**
     * Sets the value of the colorMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageColorModeEnum }
     *     
     */
    public void setColorMode(ImageColorModeEnum value) {
        this.colorMode = value;
    }

}
