
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for XLSXExportSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="XLSXExportSettings">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}OutputFormatSettings">
 *       &lt;sequence>
 *         &lt;element name="ConvertNumericValuesToNumbers" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IgnoreTextOutsideTables" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XLSXExportSettings", propOrder = {
    "convertNumericValuesToNumbers",
    "ignoreTextOutsideTables"
})
public class XLSXExportSettings
    extends OutputFormatSettings
{

    @XmlElement(name = "ConvertNumericValuesToNumbers")
    protected boolean convertNumericValuesToNumbers;
    @XmlElement(name = "IgnoreTextOutsideTables")
    protected boolean ignoreTextOutsideTables;

    /**
     * Gets the value of the convertNumericValuesToNumbers property.
     * 
     */
    public boolean isConvertNumericValuesToNumbers() {
        return convertNumericValuesToNumbers;
    }

    /**
     * Sets the value of the convertNumericValuesToNumbers property.
     * 
     */
    public void setConvertNumericValuesToNumbers(boolean value) {
        this.convertNumericValuesToNumbers = value;
    }

    /**
     * Gets the value of the ignoreTextOutsideTables property.
     * 
     */
    public boolean isIgnoreTextOutsideTables() {
        return ignoreTextOutsideTables;
    }

    /**
     * Sets the value of the ignoreTextOutsideTables property.
     * 
     */
    public void setIgnoreTextOutsideTables(boolean value) {
        this.ignoreTextOutsideTables = value;
    }

}
