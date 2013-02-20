
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EpubExportSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EpubExportSettings">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}OutputFormatSettings">
 *       &lt;sequence>
 *         &lt;element name="ThreatFirstPageAsCover" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="FontFormattingMode" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}FontFormattingModeEnum"/>
 *         &lt;element name="KeepPictures" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PictureFormat" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}ExportPictureFormatEnum"/>
 *         &lt;element name="PictureResolution" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Quality" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EpubExportSettings", propOrder = {
    "threatFirstPageAsCover",
    "fontFormattingMode",
    "keepPictures",
    "pictureFormat",
    "pictureResolution",
    "quality"
})
public class EpubExportSettings
    extends OutputFormatSettings
{

    @XmlElement(name = "ThreatFirstPageAsCover")
    protected boolean threatFirstPageAsCover;
    @XmlElement(name = "FontFormattingMode", required = true)
    protected FontFormattingModeEnum fontFormattingMode;
    @XmlElement(name = "KeepPictures")
    protected boolean keepPictures;
    @XmlElement(name = "PictureFormat", required = true)
    protected ExportPictureFormatEnum pictureFormat;
    @XmlElement(name = "PictureResolution")
    protected int pictureResolution;
    @XmlElement(name = "Quality")
    protected int quality;

    /**
     * Gets the value of the threatFirstPageAsCover property.
     * 
     */
    public boolean isThreatFirstPageAsCover() {
        return threatFirstPageAsCover;
    }

    /**
     * Sets the value of the threatFirstPageAsCover property.
     * 
     */
    public void setThreatFirstPageAsCover(boolean value) {
        this.threatFirstPageAsCover = value;
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

    /**
     * Gets the value of the keepPictures property.
     * 
     */
    public boolean isKeepPictures() {
        return keepPictures;
    }

    /**
     * Sets the value of the keepPictures property.
     * 
     */
    public void setKeepPictures(boolean value) {
        this.keepPictures = value;
    }

    /**
     * Gets the value of the pictureFormat property.
     * 
     * @return
     *     possible object is
     *     {@link ExportPictureFormatEnum }
     *     
     */
    public ExportPictureFormatEnum getPictureFormat() {
        return pictureFormat;
    }

    /**
     * Sets the value of the pictureFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportPictureFormatEnum }
     *     
     */
    public void setPictureFormat(ExportPictureFormatEnum value) {
        this.pictureFormat = value;
    }

    /**
     * Gets the value of the pictureResolution property.
     * 
     */
    public int getPictureResolution() {
        return pictureResolution;
    }

    /**
     * Sets the value of the pictureResolution property.
     * 
     */
    public void setPictureResolution(int value) {
        this.pictureResolution = value;
    }

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

}
