/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
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
