
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PdfExportScenarioEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PdfExportScenarioEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PES_MaxQuality"/>
 *     &lt;enumeration value="PES_MinSize"/>
 *     &lt;enumeration value="PES_MaxSpeed"/>
 *     &lt;enumeration value="PES_Balanced"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PdfExportScenarioEnum")
@XmlEnum
public enum PdfExportScenarioEnum {

    @XmlEnumValue("PES_MaxQuality")
    PES_MAX_QUALITY("PES_MaxQuality"),
    @XmlEnumValue("PES_MinSize")
    PES_MIN_SIZE("PES_MinSize"),
    @XmlEnumValue("PES_MaxSpeed")
    PES_MAX_SPEED("PES_MaxSpeed"),
    @XmlEnumValue("PES_Balanced")
    PES_BALANCED("PES_Balanced");
    private final String value;

    PdfExportScenarioEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PdfExportScenarioEnum fromValue(String v) {
        for (PdfExportScenarioEnum c: PdfExportScenarioEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
