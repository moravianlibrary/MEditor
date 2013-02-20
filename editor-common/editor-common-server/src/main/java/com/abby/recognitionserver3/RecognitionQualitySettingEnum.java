
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RecognitionQualitySettingEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RecognitionQualitySettingEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RQS_Thorough"/>
 *     &lt;enumeration value="RQS_Balanced"/>
 *     &lt;enumeration value="RQS_Fast"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RecognitionQualitySettingEnum")
@XmlEnum
public enum RecognitionQualitySettingEnum {

    @XmlEnumValue("RQS_Thorough")
    RQS_THOROUGH("RQS_Thorough"),
    @XmlEnumValue("RQS_Balanced")
    RQS_BALANCED("RQS_Balanced"),
    @XmlEnumValue("RQS_Fast")
    RQS_FAST("RQS_Fast");
    private final String value;

    RecognitionQualitySettingEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RecognitionQualitySettingEnum fromValue(String v) {
        for (RecognitionQualitySettingEnum c: RecognitionQualitySettingEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
