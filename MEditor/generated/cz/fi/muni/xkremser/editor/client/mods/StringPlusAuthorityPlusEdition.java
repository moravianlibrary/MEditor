//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.11 at 08:50:20 PM CET 
//


package cz.fi.muni.xkremser.editor.client.mods;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for stringPlusAuthorityPlusEdition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="stringPlusAuthorityPlusEdition">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.loc.gov/mods/v3>stringPlusAuthority">
 *       &lt;attribute name="edition" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "stringPlusAuthorityPlusEdition")
@XmlSeeAlso({
    ClassificationType.class
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-11-11T08:50:20+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
public class StringPlusAuthorityPlusEdition
    extends StringPlusAuthority
{

    @XmlAttribute
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-11-11T08:50:20+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected String edition;

    /**
     * Gets the value of the edition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-11-11T08:50:20+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public String getEdition() {
        return edition;
    }

    /**
     * Sets the value of the edition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-11-11T08:50:20+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setEdition(String value) {
        this.edition = value;
    }

}