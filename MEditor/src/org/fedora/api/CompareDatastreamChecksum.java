/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */

package org.fedora.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


// TODO: Auto-generated Javadoc
/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dsID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="versionDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pid",
    "dsID",
    "versionDate"
})
@XmlRootElement(name = "compareDatastreamChecksum")
public class CompareDatastreamChecksum {

    /** The pid. */
    @XmlElement(required = true)
    protected String pid;
    
    /** The ds id. */
    @XmlElement(required = true)
    protected String dsID;
    
    /** The version date. */
    @XmlElement(required = true, nillable = true)
    protected String versionDate;

    /**
     * Gets the value of the pid property.
     *
     * @return the pid
     * possible object is
     * {@link String }
     */
    public String getPid() {
        return pid;
    }

    /**
     * Sets the value of the pid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPid(String value) {
        this.pid = value;
    }

    /**
     * Gets the value of the dsID property.
     *
     * @return the ds id
     * possible object is
     * {@link String }
     */
    public String getDsID() {
        return dsID;
    }

    /**
     * Sets the value of the dsID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsID(String value) {
        this.dsID = value;
    }

    /**
     * Gets the value of the versionDate property.
     *
     * @return the version date
     * possible object is
     * {@link String }
     */
    public String getVersionDate() {
        return versionDate;
    }

    /**
     * Sets the value of the versionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersionDate(String value) {
        this.versionDate = value;
    }

}