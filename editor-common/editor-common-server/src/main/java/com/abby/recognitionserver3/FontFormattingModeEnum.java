
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FontFormattingModeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FontFormattingModeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FFM_Plain"/>
 *     &lt;enumeration value="FFM_Restricted"/>
 *     &lt;enumeration value="FFM_Full"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FontFormattingModeEnum")
@XmlEnum
public enum FontFormattingModeEnum {

    @XmlEnumValue("FFM_Plain")
    FFM_PLAIN("FFM_Plain"),
    @XmlEnumValue("FFM_Restricted")
    FFM_RESTRICTED("FFM_Restricted"),
    @XmlEnumValue("FFM_Full")
    FFM_FULL("FFM_Full");
    private final String value;

    FontFormattingModeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FontFormattingModeEnum fromValue(String v) {
        for (FontFormattingModeEnum c: FontFormattingModeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
