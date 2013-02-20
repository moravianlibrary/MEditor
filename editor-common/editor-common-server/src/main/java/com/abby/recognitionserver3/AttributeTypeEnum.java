
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttributeTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AttributeTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AT_Boolean"/>
 *     &lt;enumeration value="AT_Enumeration"/>
 *     &lt;enumeration value="AT_SingleLine"/>
 *     &lt;enumeration value="AT_MultipleLines"/>
 *     &lt;enumeration value="AT_RegularExpression"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AttributeTypeEnum")
@XmlEnum
public enum AttributeTypeEnum {

    @XmlEnumValue("AT_Boolean")
    AT_BOOLEAN("AT_Boolean"),
    @XmlEnumValue("AT_Enumeration")
    AT_ENUMERATION("AT_Enumeration"),
    @XmlEnumValue("AT_SingleLine")
    AT_SINGLE_LINE("AT_SingleLine"),
    @XmlEnumValue("AT_MultipleLines")
    AT_MULTIPLE_LINES("AT_MultipleLines"),
    @XmlEnumValue("AT_RegularExpression")
    AT_REGULAR_EXPRESSION("AT_RegularExpression");
    private final String value;

    AttributeTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AttributeTypeEnum fromValue(String v) {
        for (AttributeTypeEnum c: AttributeTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
