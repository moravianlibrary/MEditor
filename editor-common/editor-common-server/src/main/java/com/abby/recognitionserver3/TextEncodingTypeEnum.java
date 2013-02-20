
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TextEncodingTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TextEncodingTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="TET_Simple"/>
 *     &lt;enumeration value="TET_UTF8"/>
 *     &lt;enumeration value="TET_UTF16"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TextEncodingTypeEnum")
@XmlEnum
public enum TextEncodingTypeEnum {

    @XmlEnumValue("TET_Simple")
    TET_SIMPLE("TET_Simple"),
    @XmlEnumValue("TET_UTF8")
    TET_UTF_8("TET_UTF8"),
    @XmlEnumValue("TET_UTF16")
    TET_UTF_16("TET_UTF16");
    private final String value;

    TextEncodingTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TextEncodingTypeEnum fromValue(String v) {
        for (TextEncodingTypeEnum c: TextEncodingTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
