
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HTMLSynthesisModeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HTMLSynthesisModeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="HSM_PlainText"/>
 *     &lt;enumeration value="HSM_FormattedStream"/>
 *     &lt;enumeration value="HSM_PageLayout"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HTMLSynthesisModeEnum")
@XmlEnum
public enum HTMLSynthesisModeEnum {

    @XmlEnumValue("HSM_PlainText")
    HSM_PLAIN_TEXT("HSM_PlainText"),
    @XmlEnumValue("HSM_FormattedStream")
    HSM_FORMATTED_STREAM("HSM_FormattedStream"),
    @XmlEnumValue("HSM_PageLayout")
    HSM_PAGE_LAYOUT("HSM_PageLayout");
    private final String value;

    HTMLSynthesisModeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HTMLSynthesisModeEnum fromValue(String v) {
        for (HTMLSynthesisModeEnum c: HTMLSynthesisModeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
