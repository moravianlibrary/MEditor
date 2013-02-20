
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CodePageEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CodePageEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CP_Null"/>
 *     &lt;enumeration value="CP_Latin"/>
 *     &lt;enumeration value="CP_Cyrillic"/>
 *     &lt;enumeration value="CP_EasternEuropean"/>
 *     &lt;enumeration value="CP_Baltic"/>
 *     &lt;enumeration value="CP_Turkish"/>
 *     &lt;enumeration value="CP_US_MSDOS"/>
 *     &lt;enumeration value="CP_LatinI_MSDOS"/>
 *     &lt;enumeration value="CP_Russian_MSDOS"/>
 *     &lt;enumeration value="CP_Baltic_MSDOS"/>
 *     &lt;enumeration value="CP_Turkish_IBM"/>
 *     &lt;enumeration value="CP_Slavic_MSDOS"/>
 *     &lt;enumeration value="CP_Greek"/>
 *     &lt;enumeration value="CP_Greek_737"/>
 *     &lt;enumeration value="CP_Greek_869"/>
 *     &lt;enumeration value="CP_Latin_ISO"/>
 *     &lt;enumeration value="CP_EasternEuropean_ISO"/>
 *     &lt;enumeration value="CP_Turkish_ISO"/>
 *     &lt;enumeration value="CP_Baltic_ISO"/>
 *     &lt;enumeration value="CP_Cyrillic_ISO"/>
 *     &lt;enumeration value="CP_Greek_ISO"/>
 *     &lt;enumeration value="CP_KOI8"/>
 *     &lt;enumeration value="CP_Tatar"/>
 *     &lt;enumeration value="CP_Tatar_MSDOS"/>
 *     &lt;enumeration value="CP_Roman_Macintosh"/>
 *     &lt;enumeration value="CP_Greek_Macintosh"/>
 *     &lt;enumeration value="CP_Cyrillic_Macintosh"/>
 *     &lt;enumeration value="CP_Ukrainian_Macintosh"/>
 *     &lt;enumeration value="CP_Latin2_Macintosh"/>
 *     &lt;enumeration value="CP_Icelandic_Macintosh"/>
 *     &lt;enumeration value="CP_Turkish_Macintosh"/>
 *     &lt;enumeration value="CP_Croatian_Macintosh"/>
 *     &lt;enumeration value="CP_Armenian"/>
 *     &lt;enumeration value="CP_Armenian_MSDOS"/>
 *     &lt;enumeration value="CP_Armenian_Macintosh"/>
 *     &lt;enumeration value="CP_Latin5_ISO"/>
 *     &lt;enumeration value="CP_Cyrillic_MSDOS"/>
 *     &lt;enumeration value="CP_Bashkir"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CodePageEnum")
@XmlEnum
public enum CodePageEnum {

    @XmlEnumValue("CP_Null")
    CP_NULL("CP_Null"),
    @XmlEnumValue("CP_Latin")
    CP_LATIN("CP_Latin"),
    @XmlEnumValue("CP_Cyrillic")
    CP_CYRILLIC("CP_Cyrillic"),
    @XmlEnumValue("CP_EasternEuropean")
    CP_EASTERN_EUROPEAN("CP_EasternEuropean"),
    @XmlEnumValue("CP_Baltic")
    CP_BALTIC("CP_Baltic"),
    @XmlEnumValue("CP_Turkish")
    CP_TURKISH("CP_Turkish"),
    CP_US_MSDOS("CP_US_MSDOS"),
    @XmlEnumValue("CP_LatinI_MSDOS")
    CP_LATIN_I_MSDOS("CP_LatinI_MSDOS"),
    @XmlEnumValue("CP_Russian_MSDOS")
    CP_RUSSIAN_MSDOS("CP_Russian_MSDOS"),
    @XmlEnumValue("CP_Baltic_MSDOS")
    CP_BALTIC_MSDOS("CP_Baltic_MSDOS"),
    @XmlEnumValue("CP_Turkish_IBM")
    CP_TURKISH_IBM("CP_Turkish_IBM"),
    @XmlEnumValue("CP_Slavic_MSDOS")
    CP_SLAVIC_MSDOS("CP_Slavic_MSDOS"),
    @XmlEnumValue("CP_Greek")
    CP_GREEK("CP_Greek"),
    @XmlEnumValue("CP_Greek_737")
    CP_GREEK_737("CP_Greek_737"),
    @XmlEnumValue("CP_Greek_869")
    CP_GREEK_869("CP_Greek_869"),
    @XmlEnumValue("CP_Latin_ISO")
    CP_LATIN_ISO("CP_Latin_ISO"),
    @XmlEnumValue("CP_EasternEuropean_ISO")
    CP_EASTERN_EUROPEAN_ISO("CP_EasternEuropean_ISO"),
    @XmlEnumValue("CP_Turkish_ISO")
    CP_TURKISH_ISO("CP_Turkish_ISO"),
    @XmlEnumValue("CP_Baltic_ISO")
    CP_BALTIC_ISO("CP_Baltic_ISO"),
    @XmlEnumValue("CP_Cyrillic_ISO")
    CP_CYRILLIC_ISO("CP_Cyrillic_ISO"),
    @XmlEnumValue("CP_Greek_ISO")
    CP_GREEK_ISO("CP_Greek_ISO"),
    @XmlEnumValue("CP_KOI8")
    CP_KOI_8("CP_KOI8"),
    @XmlEnumValue("CP_Tatar")
    CP_TATAR("CP_Tatar"),
    @XmlEnumValue("CP_Tatar_MSDOS")
    CP_TATAR_MSDOS("CP_Tatar_MSDOS"),
    @XmlEnumValue("CP_Roman_Macintosh")
    CP_ROMAN_MACINTOSH("CP_Roman_Macintosh"),
    @XmlEnumValue("CP_Greek_Macintosh")
    CP_GREEK_MACINTOSH("CP_Greek_Macintosh"),
    @XmlEnumValue("CP_Cyrillic_Macintosh")
    CP_CYRILLIC_MACINTOSH("CP_Cyrillic_Macintosh"),
    @XmlEnumValue("CP_Ukrainian_Macintosh")
    CP_UKRAINIAN_MACINTOSH("CP_Ukrainian_Macintosh"),
    @XmlEnumValue("CP_Latin2_Macintosh")
    CP_LATIN_2_MACINTOSH("CP_Latin2_Macintosh"),
    @XmlEnumValue("CP_Icelandic_Macintosh")
    CP_ICELANDIC_MACINTOSH("CP_Icelandic_Macintosh"),
    @XmlEnumValue("CP_Turkish_Macintosh")
    CP_TURKISH_MACINTOSH("CP_Turkish_Macintosh"),
    @XmlEnumValue("CP_Croatian_Macintosh")
    CP_CROATIAN_MACINTOSH("CP_Croatian_Macintosh"),
    @XmlEnumValue("CP_Armenian")
    CP_ARMENIAN("CP_Armenian"),
    @XmlEnumValue("CP_Armenian_MSDOS")
    CP_ARMENIAN_MSDOS("CP_Armenian_MSDOS"),
    @XmlEnumValue("CP_Armenian_Macintosh")
    CP_ARMENIAN_MACINTOSH("CP_Armenian_Macintosh"),
    @XmlEnumValue("CP_Latin5_ISO")
    CP_LATIN_5_ISO("CP_Latin5_ISO"),
    @XmlEnumValue("CP_Cyrillic_MSDOS")
    CP_CYRILLIC_MSDOS("CP_Cyrillic_MSDOS"),
    @XmlEnumValue("CP_Bashkir")
    CP_BASHKIR("CP_Bashkir");
    private final String value;

    CodePageEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CodePageEnum fromValue(String v) {
        for (CodePageEnum c: CodePageEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
