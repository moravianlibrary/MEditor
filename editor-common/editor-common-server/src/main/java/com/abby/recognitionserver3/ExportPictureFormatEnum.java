
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExportPictureFormatEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ExportPictureFormatEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="EPF_Automatic"/>
 *     &lt;enumeration value="EPF_Ccitt4"/>
 *     &lt;enumeration value="EPF_JpegColor"/>
 *     &lt;enumeration value="EPF_JpegGray"/>
 *     &lt;enumeration value="EPF_LzwColor"/>
 *     &lt;enumeration value="EPF_LzwGray"/>
 *     &lt;enumeration value="EPF_ZipColor"/>
 *     &lt;enumeration value="EPF_ZipGray"/>
 *     &lt;enumeration value="EPF_J2KColor"/>
 *     &lt;enumeration value="EPF_J2KGray"/>
 *     &lt;enumeration value="EPF_JBIG2"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ExportPictureFormatEnum")
@XmlEnum
public enum ExportPictureFormatEnum {

    @XmlEnumValue("EPF_Automatic")
    EPF_AUTOMATIC("EPF_Automatic"),
    @XmlEnumValue("EPF_Ccitt4")
    EPF_CCITT_4("EPF_Ccitt4"),
    @XmlEnumValue("EPF_JpegColor")
    EPF_JPEG_COLOR("EPF_JpegColor"),
    @XmlEnumValue("EPF_JpegGray")
    EPF_JPEG_GRAY("EPF_JpegGray"),
    @XmlEnumValue("EPF_LzwColor")
    EPF_LZW_COLOR("EPF_LzwColor"),
    @XmlEnumValue("EPF_LzwGray")
    EPF_LZW_GRAY("EPF_LzwGray"),
    @XmlEnumValue("EPF_ZipColor")
    EPF_ZIP_COLOR("EPF_ZipColor"),
    @XmlEnumValue("EPF_ZipGray")
    EPF_ZIP_GRAY("EPF_ZipGray"),
    @XmlEnumValue("EPF_J2KColor")
    EPF_J_2_K_COLOR("EPF_J2KColor"),
    @XmlEnumValue("EPF_J2KGray")
    EPF_J_2_K_GRAY("EPF_J2KGray"),
    @XmlEnumValue("EPF_JBIG2")
    EPF_JBIG_2("EPF_JBIG2");
    private final String value;

    ExportPictureFormatEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ExportPictureFormatEnum fromValue(String v) {
        for (ExportPictureFormatEnum c: ExportPictureFormatEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
