
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RTFSynthesisModeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RTFSynthesisModeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RSM_PlainText"/>
 *     &lt;enumeration value="RSM_FormattedText"/>
 *     &lt;enumeration value="RSM_EditableCopy"/>
 *     &lt;enumeration value="RSM_ExactCopy"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RTFSynthesisModeEnum")
@XmlEnum
public enum RTFSynthesisModeEnum {

    @XmlEnumValue("RSM_PlainText")
    RSM_PLAIN_TEXT("RSM_PlainText"),
    @XmlEnumValue("RSM_FormattedText")
    RSM_FORMATTED_TEXT("RSM_FormattedText"),
    @XmlEnumValue("RSM_EditableCopy")
    RSM_EDITABLE_COPY("RSM_EditableCopy"),
    @XmlEnumValue("RSM_ExactCopy")
    RSM_EXACT_COPY("RSM_ExactCopy");
    private final String value;

    RTFSynthesisModeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RTFSynthesisModeEnum fromValue(String v) {
        for (RTFSynthesisModeEnum c: RTFSynthesisModeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
