
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TextExportSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TextExportSettings">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}OutputFormatSettings">
 *       &lt;sequence>
 *         &lt;element name="CodePage" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}CodePageEnum"/>
 *         &lt;element name="InsertEmptyLineBetweenParagraphs" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ExportParagraphsAsOneLine" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="UsePageBreaks" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="EncodingType" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}TextEncodingTypeEnum"/>
 *         &lt;element name="KeepOriginalHeadersFooters" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TextExportSettings", propOrder = {
    "codePage",
    "insertEmptyLineBetweenParagraphs",
    "exportParagraphsAsOneLine",
    "usePageBreaks",
    "encodingType",
    "keepOriginalHeadersFooters"
})
public class TextExportSettings
    extends OutputFormatSettings
{

    @XmlElement(name = "CodePage", required = true)
    protected CodePageEnum codePage;
    @XmlElement(name = "InsertEmptyLineBetweenParagraphs")
    protected boolean insertEmptyLineBetweenParagraphs;
    @XmlElement(name = "ExportParagraphsAsOneLine")
    protected boolean exportParagraphsAsOneLine;
    @XmlElement(name = "UsePageBreaks")
    protected boolean usePageBreaks;
    @XmlElement(name = "EncodingType", required = true)
    protected TextEncodingTypeEnum encodingType;
    @XmlElement(name = "KeepOriginalHeadersFooters")
    protected boolean keepOriginalHeadersFooters;

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
     * Gets the value of the insertEmptyLineBetweenParagraphs property.
     * 
     */
    public boolean isInsertEmptyLineBetweenParagraphs() {
        return insertEmptyLineBetweenParagraphs;
    }

    /**
     * Sets the value of the insertEmptyLineBetweenParagraphs property.
     * 
     */
    public void setInsertEmptyLineBetweenParagraphs(boolean value) {
        this.insertEmptyLineBetweenParagraphs = value;
    }

    /**
     * Gets the value of the exportParagraphsAsOneLine property.
     * 
     */
    public boolean isExportParagraphsAsOneLine() {
        return exportParagraphsAsOneLine;
    }

    /**
     * Sets the value of the exportParagraphsAsOneLine property.
     * 
     */
    public void setExportParagraphsAsOneLine(boolean value) {
        this.exportParagraphsAsOneLine = value;
    }

    /**
     * Gets the value of the usePageBreaks property.
     * 
     */
    public boolean isUsePageBreaks() {
        return usePageBreaks;
    }

    /**
     * Sets the value of the usePageBreaks property.
     * 
     */
    public void setUsePageBreaks(boolean value) {
        this.usePageBreaks = value;
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

    /**
     * Gets the value of the keepOriginalHeadersFooters property.
     * 
     */
    public boolean isKeepOriginalHeadersFooters() {
        return keepOriginalHeadersFooters;
    }

    /**
     * Sets the value of the keepOriginalHeadersFooters property.
     * 
     */
    public void setKeepOriginalHeadersFooters(boolean value) {
        this.keepOriginalHeadersFooters = value;
    }

}
