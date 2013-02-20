
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PDFVersionEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PDFVersionEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PVN_Auto"/>
 *     &lt;enumeration value="PVN_Version13"/>
 *     &lt;enumeration value="PVN_Version14"/>
 *     &lt;enumeration value="PVN_Version15"/>
 *     &lt;enumeration value="PVN_Version16"/>
 *     &lt;enumeration value="PVN_Version17"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PDFVersionEnum")
@XmlEnum
public enum PDFVersionEnum {

    @XmlEnumValue("PVN_Auto")
    PVN_AUTO("PVN_Auto"),
    @XmlEnumValue("PVN_Version13")
    PVN_VERSION_13("PVN_Version13"),
    @XmlEnumValue("PVN_Version14")
    PVN_VERSION_14("PVN_Version14"),
    @XmlEnumValue("PVN_Version15")
    PVN_VERSION_15("PVN_Version15"),
    @XmlEnumValue("PVN_Version16")
    PVN_VERSION_16("PVN_Version16"),
    @XmlEnumValue("PVN_Version17")
    PVN_VERSION_17("PVN_Version17");
    private final String value;

    PDFVersionEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PDFVersionEnum fromValue(String v) {
        for (PDFVersionEnum c: PDFVersionEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
