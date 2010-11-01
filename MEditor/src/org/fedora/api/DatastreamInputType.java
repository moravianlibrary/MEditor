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
 * <p>Java class for datastreamInputType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="datastreamInputType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="fedora:datastreamInputType"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "datastreamInputType")
@XmlEnum
public enum DatastreamInputType {

    /** The FEDOR a_ datastrea m_ inpu t_ type. */
    @XmlEnumValue("fedora:datastreamInputType")
    FEDORA_DATASTREAM_INPUT_TYPE("fedora:datastreamInputType");
    
    /** The value. */
    private final String value;

    /**
     * Instantiates a new datastream input type.
     *
     * @param v the v
     */
    DatastreamInputType(String v) {
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
     * @return the datastream input type
     */
    public static DatastreamInputType fromValue(String v) {
        for (DatastreamInputType c: DatastreamInputType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
