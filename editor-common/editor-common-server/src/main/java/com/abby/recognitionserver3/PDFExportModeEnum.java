
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PDFExportModeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PDFExportModeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PEM_TextOnly"/>
 *     &lt;enumeration value="PEM_TextOnImage"/>
 *     &lt;enumeration value="PEM_ImageOnText"/>
 *     &lt;enumeration value="PEM_ImageOnly"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PDFExportModeEnum")
@XmlEnum
public enum PDFExportModeEnum {

    @XmlEnumValue("PEM_TextOnly")
    PEM_TEXT_ONLY("PEM_TextOnly"),
    @XmlEnumValue("PEM_TextOnImage")
    PEM_TEXT_ON_IMAGE("PEM_TextOnImage"),
    @XmlEnumValue("PEM_ImageOnText")
    PEM_IMAGE_ON_TEXT("PEM_ImageOnText"),
    @XmlEnumValue("PEM_ImageOnly")
    PEM_IMAGE_ONLY("PEM_ImageOnly");
    private final String value;

    PDFExportModeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PDFExportModeEnum fromValue(String v) {
        for (PDFExportModeEnum c: PDFExportModeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
