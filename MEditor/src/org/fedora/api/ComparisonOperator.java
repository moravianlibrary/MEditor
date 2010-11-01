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
 * <p>Java class for ComparisonOperator.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ComparisonOperator">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="has"/>
 *     &lt;enumeration value="eq"/>
 *     &lt;enumeration value="lt"/>
 *     &lt;enumeration value="le"/>
 *     &lt;enumeration value="gt"/>
 *     &lt;enumeration value="ge"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ComparisonOperator")
@XmlEnum
public enum ComparisonOperator {

    /** The HAS. */
    @XmlEnumValue("has")
    HAS("has"),
    
    /** The EQ. */
    @XmlEnumValue("eq")
    EQ("eq"),
    
    /** The LT. */
    @XmlEnumValue("lt")
    LT("lt"),
    
    /** The LE. */
    @XmlEnumValue("le")
    LE("le"),
    
    /** The GT. */
    @XmlEnumValue("gt")
    GT("gt"),
    
    /** The GE. */
    @XmlEnumValue("ge")
    GE("ge");
    
    /** The value. */
    private final String value;

    /**
     * Instantiates a new comparison operator.
     *
     * @param v the v
     */
    ComparisonOperator(String v) {
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
     * @return the comparison operator
     */
    public static ComparisonOperator fromValue(String v) {
        for (ComparisonOperator c: ComparisonOperator.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
