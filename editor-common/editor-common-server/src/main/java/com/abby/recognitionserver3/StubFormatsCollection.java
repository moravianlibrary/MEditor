
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StubFormatsCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StubFormatsCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Html" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}HTMLExportSettings" minOccurs="0"/>
 *         &lt;element name="Internal" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}InternalFormatSettings" minOccurs="0"/>
 *         &lt;element name="NoConv" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}NoConversionExportSettings" minOccurs="0"/>
 *         &lt;element name="Pdfa" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}PDFAExportSettings" minOccurs="0"/>
 *         &lt;element name="Pdf" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}PDFExportSettings" minOccurs="0"/>
 *         &lt;element name="Text" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}TextExportSettings" minOccurs="0"/>
 *         &lt;element name="Word" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}MSWordExportSettings" minOccurs="0"/>
 *         &lt;element name="Xl" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}XLExportSettings" minOccurs="0"/>
 *         &lt;element name="Rtf" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}RTFExportSettings" minOccurs="0"/>
 *         &lt;element name="Xml" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}XMLExportSettings" minOccurs="0"/>
 *         &lt;element name="Csv" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}CSVExportSettings" minOccurs="0"/>
 *         &lt;element name="Tiff" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}TiffExportSettings" minOccurs="0"/>
 *         &lt;element name="Jpeg" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}JpegExportSettings" minOccurs="0"/>
 *         &lt;element name="Jpeg2k" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}Jpeg2kExportSettings" minOccurs="0"/>
 *         &lt;element name="JBig2" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}JBig2ExportSettings" minOccurs="0"/>
 *         &lt;element name="Docx" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}DOCXExportSettings" minOccurs="0"/>
 *         &lt;element name="Xlsx" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}XLSXExportSettings" minOccurs="0"/>
 *         &lt;element name="Alto" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}AltoExportSettings" minOccurs="0"/>
 *         &lt;element name="Epub" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}EpubExportSettings" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StubFormatsCollection", propOrder = {
    "html",
    "internal",
    "noConv",
    "pdfa",
    "pdf",
    "text",
    "word",
    "xl",
    "rtf",
    "xml",
    "csv",
    "tiff",
    "jpeg",
    "jpeg2K",
    "jBig2",
    "docx",
    "xlsx",
    "alto",
    "epub"
})
public class StubFormatsCollection {

    @XmlElement(name = "Html")
    protected HTMLExportSettings html;
    @XmlElement(name = "Internal")
    protected InternalFormatSettings internal;
    @XmlElement(name = "NoConv")
    protected NoConversionExportSettings noConv;
    @XmlElement(name = "Pdfa")
    protected PDFAExportSettings pdfa;
    @XmlElement(name = "Pdf")
    protected PDFExportSettings pdf;
    @XmlElement(name = "Text")
    protected TextExportSettings text;
    @XmlElement(name = "Word")
    protected MSWordExportSettings word;
    @XmlElement(name = "Xl")
    protected XLExportSettings xl;
    @XmlElement(name = "Rtf")
    protected RTFExportSettings rtf;
    @XmlElement(name = "Xml")
    protected XMLExportSettings xml;
    @XmlElement(name = "Csv")
    protected CSVExportSettings csv;
    @XmlElement(name = "Tiff")
    protected TiffExportSettings tiff;
    @XmlElement(name = "Jpeg")
    protected JpegExportSettings jpeg;
    @XmlElement(name = "Jpeg2k")
    protected Jpeg2KExportSettings jpeg2K;
    @XmlElement(name = "JBig2")
    protected JBig2ExportSettings jBig2;
    @XmlElement(name = "Docx")
    protected DOCXExportSettings docx;
    @XmlElement(name = "Xlsx")
    protected XLSXExportSettings xlsx;
    @XmlElement(name = "Alto")
    protected AltoExportSettings alto;
    @XmlElement(name = "Epub")
    protected EpubExportSettings epub;

    /**
     * Gets the value of the html property.
     * 
     * @return
     *     possible object is
     *     {@link HTMLExportSettings }
     *     
     */
    public HTMLExportSettings getHtml() {
        return html;
    }

    /**
     * Sets the value of the html property.
     * 
     * @param value
     *     allowed object is
     *     {@link HTMLExportSettings }
     *     
     */
    public void setHtml(HTMLExportSettings value) {
        this.html = value;
    }

    /**
     * Gets the value of the internal property.
     * 
     * @return
     *     possible object is
     *     {@link InternalFormatSettings }
     *     
     */
    public InternalFormatSettings getInternal() {
        return internal;
    }

    /**
     * Sets the value of the internal property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternalFormatSettings }
     *     
     */
    public void setInternal(InternalFormatSettings value) {
        this.internal = value;
    }

    /**
     * Gets the value of the noConv property.
     * 
     * @return
     *     possible object is
     *     {@link NoConversionExportSettings }
     *     
     */
    public NoConversionExportSettings getNoConv() {
        return noConv;
    }

    /**
     * Sets the value of the noConv property.
     * 
     * @param value
     *     allowed object is
     *     {@link NoConversionExportSettings }
     *     
     */
    public void setNoConv(NoConversionExportSettings value) {
        this.noConv = value;
    }

    /**
     * Gets the value of the pdfa property.
     * 
     * @return
     *     possible object is
     *     {@link PDFAExportSettings }
     *     
     */
    public PDFAExportSettings getPdfa() {
        return pdfa;
    }

    /**
     * Sets the value of the pdfa property.
     * 
     * @param value
     *     allowed object is
     *     {@link PDFAExportSettings }
     *     
     */
    public void setPdfa(PDFAExportSettings value) {
        this.pdfa = value;
    }

    /**
     * Gets the value of the pdf property.
     * 
     * @return
     *     possible object is
     *     {@link PDFExportSettings }
     *     
     */
    public PDFExportSettings getPdf() {
        return pdf;
    }

    /**
     * Sets the value of the pdf property.
     * 
     * @param value
     *     allowed object is
     *     {@link PDFExportSettings }
     *     
     */
    public void setPdf(PDFExportSettings value) {
        this.pdf = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link TextExportSettings }
     *     
     */
    public TextExportSettings getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextExportSettings }
     *     
     */
    public void setText(TextExportSettings value) {
        this.text = value;
    }

    /**
     * Gets the value of the word property.
     * 
     * @return
     *     possible object is
     *     {@link MSWordExportSettings }
     *     
     */
    public MSWordExportSettings getWord() {
        return word;
    }

    /**
     * Sets the value of the word property.
     * 
     * @param value
     *     allowed object is
     *     {@link MSWordExportSettings }
     *     
     */
    public void setWord(MSWordExportSettings value) {
        this.word = value;
    }

    /**
     * Gets the value of the xl property.
     * 
     * @return
     *     possible object is
     *     {@link XLExportSettings }
     *     
     */
    public XLExportSettings getXl() {
        return xl;
    }

    /**
     * Sets the value of the xl property.
     * 
     * @param value
     *     allowed object is
     *     {@link XLExportSettings }
     *     
     */
    public void setXl(XLExportSettings value) {
        this.xl = value;
    }

    /**
     * Gets the value of the rtf property.
     * 
     * @return
     *     possible object is
     *     {@link RTFExportSettings }
     *     
     */
    public RTFExportSettings getRtf() {
        return rtf;
    }

    /**
     * Sets the value of the rtf property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTFExportSettings }
     *     
     */
    public void setRtf(RTFExportSettings value) {
        this.rtf = value;
    }

    /**
     * Gets the value of the xml property.
     * 
     * @return
     *     possible object is
     *     {@link XMLExportSettings }
     *     
     */
    public XMLExportSettings getXml() {
        return xml;
    }

    /**
     * Sets the value of the xml property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLExportSettings }
     *     
     */
    public void setXml(XMLExportSettings value) {
        this.xml = value;
    }

    /**
     * Gets the value of the csv property.
     * 
     * @return
     *     possible object is
     *     {@link CSVExportSettings }
     *     
     */
    public CSVExportSettings getCsv() {
        return csv;
    }

    /**
     * Sets the value of the csv property.
     * 
     * @param value
     *     allowed object is
     *     {@link CSVExportSettings }
     *     
     */
    public void setCsv(CSVExportSettings value) {
        this.csv = value;
    }

    /**
     * Gets the value of the tiff property.
     * 
     * @return
     *     possible object is
     *     {@link TiffExportSettings }
     *     
     */
    public TiffExportSettings getTiff() {
        return tiff;
    }

    /**
     * Sets the value of the tiff property.
     * 
     * @param value
     *     allowed object is
     *     {@link TiffExportSettings }
     *     
     */
    public void setTiff(TiffExportSettings value) {
        this.tiff = value;
    }

    /**
     * Gets the value of the jpeg property.
     * 
     * @return
     *     possible object is
     *     {@link JpegExportSettings }
     *     
     */
    public JpegExportSettings getJpeg() {
        return jpeg;
    }

    /**
     * Sets the value of the jpeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link JpegExportSettings }
     *     
     */
    public void setJpeg(JpegExportSettings value) {
        this.jpeg = value;
    }

    /**
     * Gets the value of the jpeg2K property.
     * 
     * @return
     *     possible object is
     *     {@link Jpeg2KExportSettings }
     *     
     */
    public Jpeg2KExportSettings getJpeg2K() {
        return jpeg2K;
    }

    /**
     * Sets the value of the jpeg2K property.
     * 
     * @param value
     *     allowed object is
     *     {@link Jpeg2KExportSettings }
     *     
     */
    public void setJpeg2K(Jpeg2KExportSettings value) {
        this.jpeg2K = value;
    }

    /**
     * Gets the value of the jBig2 property.
     * 
     * @return
     *     possible object is
     *     {@link JBig2ExportSettings }
     *     
     */
    public JBig2ExportSettings getJBig2() {
        return jBig2;
    }

    /**
     * Sets the value of the jBig2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JBig2ExportSettings }
     *     
     */
    public void setJBig2(JBig2ExportSettings value) {
        this.jBig2 = value;
    }

    /**
     * Gets the value of the docx property.
     * 
     * @return
     *     possible object is
     *     {@link DOCXExportSettings }
     *     
     */
    public DOCXExportSettings getDocx() {
        return docx;
    }

    /**
     * Sets the value of the docx property.
     * 
     * @param value
     *     allowed object is
     *     {@link DOCXExportSettings }
     *     
     */
    public void setDocx(DOCXExportSettings value) {
        this.docx = value;
    }

    /**
     * Gets the value of the xlsx property.
     * 
     * @return
     *     possible object is
     *     {@link XLSXExportSettings }
     *     
     */
    public XLSXExportSettings getXlsx() {
        return xlsx;
    }

    /**
     * Sets the value of the xlsx property.
     * 
     * @param value
     *     allowed object is
     *     {@link XLSXExportSettings }
     *     
     */
    public void setXlsx(XLSXExportSettings value) {
        this.xlsx = value;
    }

    /**
     * Gets the value of the alto property.
     * 
     * @return
     *     possible object is
     *     {@link AltoExportSettings }
     *     
     */
    public AltoExportSettings getAlto() {
        return alto;
    }

    /**
     * Sets the value of the alto property.
     * 
     * @param value
     *     allowed object is
     *     {@link AltoExportSettings }
     *     
     */
    public void setAlto(AltoExportSettings value) {
        this.alto = value;
    }

    /**
     * Gets the value of the epub property.
     * 
     * @return
     *     possible object is
     *     {@link EpubExportSettings }
     *     
     */
    public EpubExportSettings getEpub() {
        return epub;
    }

    /**
     * Sets the value of the epub property.
     * 
     * @param value
     *     allowed object is
     *     {@link EpubExportSettings }
     *     
     */
    public void setEpub(EpubExportSettings value) {
        this.epub = value;
    }

}
