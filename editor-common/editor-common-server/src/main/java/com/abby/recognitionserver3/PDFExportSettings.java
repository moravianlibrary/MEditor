
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PDFExportSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PDFExportSettings">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}OutputFormatSettings">
 *       &lt;sequence>
 *         &lt;element name="PDFExportMode" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}PDFExportModeEnum"/>
 *         &lt;element name="PictureResolution" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Quality" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="WriteTaggedPdf" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ReplaceUncertainWordsWithImages" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsEncryptionRequested" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="UseImprovedCompression" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DocumentInfo" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}ArrayOfDocumentInfoItem" minOccurs="0"/>
 *         &lt;element name="EncryptionInfo" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}PDFEncryptionInfo" minOccurs="0"/>
 *         &lt;element name="HeaderAndFooter" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}HeaderAndFooterSettings" minOccurs="0"/>
 *         &lt;element name="PictureFormat" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}ExportPictureFormatEnum"/>
 *         &lt;element name="PaperWidth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PaperHeight" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="UseOriginalPaperSize" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="OverwriteMetadata" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PdfVersion" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}PDFVersionEnum"/>
 *         &lt;element name="KeepOriginalHeadersFooters" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="WriteAnnotations" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Scenario" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}PdfExportScenarioEnum"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PDFExportSettings", propOrder = {
    "pdfExportMode",
    "pictureResolution",
    "quality",
    "writeTaggedPdf",
    "replaceUncertainWordsWithImages",
    "isEncryptionRequested",
    "useImprovedCompression",
    "documentInfo",
    "encryptionInfo",
    "headerAndFooter",
    "pictureFormat",
    "paperWidth",
    "paperHeight",
    "useOriginalPaperSize",
    "overwriteMetadata",
    "pdfVersion",
    "keepOriginalHeadersFooters",
    "writeAnnotations",
    "scenario"
})
public class PDFExportSettings
    extends OutputFormatSettings
{

    @XmlElement(name = "PDFExportMode", required = true)
    protected PDFExportModeEnum pdfExportMode;
    @XmlElement(name = "PictureResolution")
    protected int pictureResolution;
    @XmlElement(name = "Quality")
    protected int quality;
    @XmlElement(name = "WriteTaggedPdf")
    protected boolean writeTaggedPdf;
    @XmlElement(name = "ReplaceUncertainWordsWithImages")
    protected boolean replaceUncertainWordsWithImages;
    @XmlElement(name = "IsEncryptionRequested")
    protected boolean isEncryptionRequested;
    @XmlElement(name = "UseImprovedCompression")
    protected boolean useImprovedCompression;
    @XmlElement(name = "DocumentInfo")
    protected ArrayOfDocumentInfoItem documentInfo;
    @XmlElement(name = "EncryptionInfo")
    protected PDFEncryptionInfo encryptionInfo;
    @XmlElement(name = "HeaderAndFooter")
    protected HeaderAndFooterSettings headerAndFooter;
    @XmlElement(name = "PictureFormat", required = true)
    protected ExportPictureFormatEnum pictureFormat;
    @XmlElement(name = "PaperWidth")
    protected int paperWidth;
    @XmlElement(name = "PaperHeight")
    protected int paperHeight;
    @XmlElement(name = "UseOriginalPaperSize")
    protected boolean useOriginalPaperSize;
    @XmlElement(name = "OverwriteMetadata")
    protected boolean overwriteMetadata;
    @XmlElement(name = "PdfVersion", required = true)
    protected PDFVersionEnum pdfVersion;
    @XmlElement(name = "KeepOriginalHeadersFooters")
    protected boolean keepOriginalHeadersFooters;
    @XmlElement(name = "WriteAnnotations")
    protected boolean writeAnnotations;
    @XmlElement(name = "Scenario", required = true)
    protected PdfExportScenarioEnum scenario;

    /**
     * Gets the value of the pdfExportMode property.
     * 
     * @return
     *     possible object is
     *     {@link PDFExportModeEnum }
     *     
     */
    public PDFExportModeEnum getPDFExportMode() {
        return pdfExportMode;
    }

    /**
     * Sets the value of the pdfExportMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link PDFExportModeEnum }
     *     
     */
    public void setPDFExportMode(PDFExportModeEnum value) {
        this.pdfExportMode = value;
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

    /**
     * Gets the value of the writeTaggedPdf property.
     * 
     */
    public boolean isWriteTaggedPdf() {
        return writeTaggedPdf;
    }

    /**
     * Sets the value of the writeTaggedPdf property.
     * 
     */
    public void setWriteTaggedPdf(boolean value) {
        this.writeTaggedPdf = value;
    }

    /**
     * Gets the value of the replaceUncertainWordsWithImages property.
     * 
     */
    public boolean isReplaceUncertainWordsWithImages() {
        return replaceUncertainWordsWithImages;
    }

    /**
     * Sets the value of the replaceUncertainWordsWithImages property.
     * 
     */
    public void setReplaceUncertainWordsWithImages(boolean value) {
        this.replaceUncertainWordsWithImages = value;
    }

    /**
     * Gets the value of the isEncryptionRequested property.
     * 
     */
    public boolean isIsEncryptionRequested() {
        return isEncryptionRequested;
    }

    /**
     * Sets the value of the isEncryptionRequested property.
     * 
     */
    public void setIsEncryptionRequested(boolean value) {
        this.isEncryptionRequested = value;
    }

    /**
     * Gets the value of the useImprovedCompression property.
     * 
     */
    public boolean isUseImprovedCompression() {
        return useImprovedCompression;
    }

    /**
     * Sets the value of the useImprovedCompression property.
     * 
     */
    public void setUseImprovedCompression(boolean value) {
        this.useImprovedCompression = value;
    }

    /**
     * Gets the value of the documentInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDocumentInfoItem }
     *     
     */
    public ArrayOfDocumentInfoItem getDocumentInfo() {
        return documentInfo;
    }

    /**
     * Sets the value of the documentInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDocumentInfoItem }
     *     
     */
    public void setDocumentInfo(ArrayOfDocumentInfoItem value) {
        this.documentInfo = value;
    }

    /**
     * Gets the value of the encryptionInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PDFEncryptionInfo }
     *     
     */
    public PDFEncryptionInfo getEncryptionInfo() {
        return encryptionInfo;
    }

    /**
     * Sets the value of the encryptionInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PDFEncryptionInfo }
     *     
     */
    public void setEncryptionInfo(PDFEncryptionInfo value) {
        this.encryptionInfo = value;
    }

    /**
     * Gets the value of the headerAndFooter property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderAndFooterSettings }
     *     
     */
    public HeaderAndFooterSettings getHeaderAndFooter() {
        return headerAndFooter;
    }

    /**
     * Sets the value of the headerAndFooter property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderAndFooterSettings }
     *     
     */
    public void setHeaderAndFooter(HeaderAndFooterSettings value) {
        this.headerAndFooter = value;
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
     * Gets the value of the paperWidth property.
     * 
     */
    public int getPaperWidth() {
        return paperWidth;
    }

    /**
     * Sets the value of the paperWidth property.
     * 
     */
    public void setPaperWidth(int value) {
        this.paperWidth = value;
    }

    /**
     * Gets the value of the paperHeight property.
     * 
     */
    public int getPaperHeight() {
        return paperHeight;
    }

    /**
     * Sets the value of the paperHeight property.
     * 
     */
    public void setPaperHeight(int value) {
        this.paperHeight = value;
    }

    /**
     * Gets the value of the useOriginalPaperSize property.
     * 
     */
    public boolean isUseOriginalPaperSize() {
        return useOriginalPaperSize;
    }

    /**
     * Sets the value of the useOriginalPaperSize property.
     * 
     */
    public void setUseOriginalPaperSize(boolean value) {
        this.useOriginalPaperSize = value;
    }

    /**
     * Gets the value of the overwriteMetadata property.
     * 
     */
    public boolean isOverwriteMetadata() {
        return overwriteMetadata;
    }

    /**
     * Sets the value of the overwriteMetadata property.
     * 
     */
    public void setOverwriteMetadata(boolean value) {
        this.overwriteMetadata = value;
    }

    /**
     * Gets the value of the pdfVersion property.
     * 
     * @return
     *     possible object is
     *     {@link PDFVersionEnum }
     *     
     */
    public PDFVersionEnum getPdfVersion() {
        return pdfVersion;
    }

    /**
     * Sets the value of the pdfVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link PDFVersionEnum }
     *     
     */
    public void setPdfVersion(PDFVersionEnum value) {
        this.pdfVersion = value;
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

    /**
     * Gets the value of the writeAnnotations property.
     * 
     */
    public boolean isWriteAnnotations() {
        return writeAnnotations;
    }

    /**
     * Sets the value of the writeAnnotations property.
     * 
     */
    public void setWriteAnnotations(boolean value) {
        this.writeAnnotations = value;
    }

    /**
     * Gets the value of the scenario property.
     * 
     * @return
     *     possible object is
     *     {@link PdfExportScenarioEnum }
     *     
     */
    public PdfExportScenarioEnum getScenario() {
        return scenario;
    }

    /**
     * Sets the value of the scenario property.
     * 
     * @param value
     *     allowed object is
     *     {@link PdfExportScenarioEnum }
     *     
     */
    public void setScenario(PdfExportScenarioEnum value) {
        this.scenario = value;
    }

}
