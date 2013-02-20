
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HTMLExportSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HTMLExportSettings">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}OutputFormatSettings">
 *       &lt;sequence>
 *         &lt;element name="CodePage" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}CodePageEnum"/>
 *         &lt;element name="HTMLSynthesisMode" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}HTMLSynthesisModeEnum"/>
 *         &lt;element name="AllowCss" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="WritePictures" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="EncodingType" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}TextEncodingTypeEnum"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HTMLExportSettings", propOrder = {
    "codePage",
    "htmlSynthesisMode",
    "allowCss",
    "writePictures",
    "encodingType"
})
public class HTMLExportSettings
    extends OutputFormatSettings
{

    @XmlElement(name = "CodePage", required = true)
    protected CodePageEnum codePage;
    @XmlElement(name = "HTMLSynthesisMode", required = true)
    protected HTMLSynthesisModeEnum htmlSynthesisMode;
    @XmlElement(name = "AllowCss")
    protected boolean allowCss;
    @XmlElement(name = "WritePictures")
    protected boolean writePictures;
    @XmlElement(name = "EncodingType", required = true)
    protected TextEncodingTypeEnum encodingType;

    /**
     * Gets the value of the codePage property.
     * 
     * @return
     *     possible object is
     *     {@link CodePageEnum }
     *     
     */
    public CodePageEnum getCodePage() {
        return codePage;
    }

    /**
     * Sets the value of the codePage property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodePageEnum }
     *     
     */
    public void setCodePage(CodePageEnum value) {
        this.codePage = value;
    }

    /**
     * Gets the value of the htmlSynthesisMode property.
     * 
     * @return
     *     possible object is
     *     {@link HTMLSynthesisModeEnum }
     *     
     */
    public HTMLSynthesisModeEnum getHTMLSynthesisMode() {
        return htmlSynthesisMode;
    }

    /**
     * Sets the value of the htmlSynthesisMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link HTMLSynthesisModeEnum }
     *     
     */
    public void setHTMLSynthesisMode(HTMLSynthesisModeEnum value) {
        this.htmlSynthesisMode = value;
    }

    /**
     * Gets the value of the allowCss property.
     * 
     */
    public boolean isAllowCss() {
        return allowCss;
    }

    /**
     * Sets the value of the allowCss property.
     * 
     */
    public void setAllowCss(boolean value) {
        this.allowCss = value;
    }

    /**
     * Gets the value of the writePictures property.
     * 
     */
    public boolean isWritePictures() {
        return writePictures;
    }

    /**
     * Sets the value of the writePictures property.
     * 
     */
    public void setWritePictures(boolean value) {
        this.writePictures = value;
    }

    /**
     * Gets the value of the encodingType property.
     * 
     * @return
     *     possible object is
     *     {@link TextEncodingTypeEnum }
     *     
     */
    public TextEncodingTypeEnum getEncodingType() {
        return encodingType;
    }

    /**
     * Sets the value of the encodingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextEncodingTypeEnum }
     *     
     */
    public void setEncodingType(TextEncodingTypeEnum value) {
        this.encodingType = value;
    }

}
