/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */

package org.fedora.api;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


// TODO: Auto-generated Javadoc
/**
 * <p>Java class for defaultInputType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="defaultInputType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="fedora:defaultInputType"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "defaultInputType")
@XmlEnum
public enum DefaultInputType {

    /** The FEDOR a_ defaul t_ inpu t_ type. */
    @XmlEnumValue("fedora:defaultInputType")
    FEDORA_DEFAULT_INPUT_TYPE("fedora:defaultInputType");
    
    /** The value. */
    private final String value;

    /**
     * Instantiates a new default input type.
     *
     * @param v the v
     */
    DefaultInputType(String v) {
        value = v;
    }

    /**
     * Value.
     *
     * @return the string
     */
    public String value() {
        return value;
    }

    /**
     * From value.
     *
     * @param v the v
     * @return the default input type
     */
    public static DefaultInputType fromValue(String v) {
        for (DefaultInputType c: DefaultInputType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
