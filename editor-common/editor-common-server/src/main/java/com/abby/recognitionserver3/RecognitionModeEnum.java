
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RecognitionModeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RecognitionModeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RM_FullPage"/>
 *     &lt;enumeration value="RM_BarcodesOnly"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RecognitionModeEnum")
@XmlEnum
public enum RecognitionModeEnum {

    @XmlEnumValue("RM_FullPage")
    RM_FULL_PAGE("RM_FullPage"),
    @XmlEnumValue("RM_BarcodesOnly")
    RM_BARCODES_ONLY("RM_BarcodesOnly");
    private final String value;

    RecognitionModeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RecognitionModeEnum fromValue(String v) {
        for (RecognitionModeEnum c: RecognitionModeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
