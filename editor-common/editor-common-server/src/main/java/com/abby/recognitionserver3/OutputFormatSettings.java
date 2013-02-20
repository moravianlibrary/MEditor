
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OutputFormatSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OutputFormatSettings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FileFormat" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}OutputFileFormatEnum"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutputFormatSettings", propOrder = {
    "fileFormat"
})
@XmlSeeAlso({
    RTFExportSettings.class,
    PDFExportSettings.class,
    TextExportSettings.class,
    HTMLExportSettings.class,
    XLExportSettings.class,
    AltoExportSettings.class,
    XLSXExportSettings.class,
    XMLExportSettings.class,
    TiffExportSettings.class,
    PDFAExportSettings.class,
    DOCXExportSettings.class,
    MSWordExportSettings.class,
    JpegExportSettings.class,
    Jpeg2KExportSettings.class,
    NoConversionExportSettings.class,
    CSVExportSettings.class,
    EpubExportSettings.class,
    InternalFormatSettings.class,
    JBig2ExportSettings.class
})
public abstract class OutputFormatSettings {

    @XmlElement(name = "FileFormat", required = true)
    protected OutputFileFormatEnum fileFormat;

    /**
     * Gets the value of the fileFormat property.
     * 
     * @return
     *     possible object is
     *     {@link OutputFileFormatEnum }
     *     
     */
    public OutputFileFormatEnum getFileFormat() {
        return fileFormat;
    }

    /**
     * Sets the value of the fileFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputFileFormatEnum }
     *     
     */
    public void setFileFormat(OutputFileFormatEnum value) {
        this.fileFormat = value;
    }

}
