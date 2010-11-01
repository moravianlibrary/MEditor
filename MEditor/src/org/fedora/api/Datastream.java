/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */

package org.fedora.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


// TODO: Auto-generated Javadoc
/**
 * content stream of a digital object
 * 
 * <p>Java class for Datastream complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Datastream">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="controlGroup" type="{http://www.fedora.info/definitions/1/0/types/}DatastreamControlGroup"/>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="versionID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="altIDs" type="{http://www.fedora.info/definitions/1/0/types/}ArrayOfString"/>
 *         &lt;element name="label" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="versionable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="MIMEType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="formatURI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="createDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="checksumType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="checksum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Datastream", propOrder = {
    "controlGroup",
    "id",
    "versionID",
    "altIDs",
    "label",
    "versionable",
    "mimeType",
    "formatURI",
    "createDate",
    "size",
    "state",
    "location",
    "checksumType",
    "checksum"
})
public class Datastream {

    /** The control group. */
    @XmlElement(required = true)
    protected DatastreamControlGroup controlGroup;
    
    /** The id. */
    @XmlElement(name = "ID", required = true)
    protected String id;
    
    /** The version id. */
    @XmlElement(required = true)
    protected String versionID;
    
    /** The alt i ds. */
    @XmlElement(required = true, nillable = true)
    protected ArrayOfString altIDs;
    
    /** The label. */
    @XmlElement(required = true, nillable = true)
    protected String label;
    
    /** The versionable. */
    protected boolean versionable;
    
    /** The mime type. */
    @XmlElement(name = "MIMEType", required = true, nillable = true)
    protected String mimeType;
    
    /** The format uri. */
    @XmlElement(required = true, nillable = true)
    protected String formatURI;
    
    /** The create date. */
    @XmlElement(required = true)
    protected String createDate;
    
    /** The size. */
    protected long size;
    
    /** The state. */
    @XmlElement(required = true)
    protected String state;
    
    /** The location. */
    @XmlElement(required = true, nillable = true)
    protected String location;
    
    /** The checksum type. */
    @XmlElement(required = true, nillable = true)
    protected String checksumType;
    
    /** The checksum. */
    @XmlElement(required = true, nillable = true)
    protected String checksum;

    /**
     * Gets the value of the controlGroup property.
     *
     * @return the control group
     * possible object is
     * {@link DatastreamControlGroup }
     */
    public DatastreamControlGroup getControlGroup() {
        return controlGroup;
    }

    /**
     * Sets the value of the controlGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatastreamControlGroup }
     *     
     */
    public void setControlGroup(DatastreamControlGroup value) {
        this.controlGroup = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return the iD
     * possible object is
     * {@link String }
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the versionID property.
     *
     * @return the version id
     * possible object is
     * {@link String }
     */
    public String getVersionID() {
        return versionID;
    }

    /**
     * Sets the value of the versionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersionID(String value) {
        this.versionID = value;
    }

    /**
     * Gets the value of the altIDs property.
     *
     * @return the alt i ds
     * possible object is
     * {@link ArrayOfString }
     */
    public ArrayOfString getAltIDs() {
        return altIDs;
    }

    /**
     * Sets the value of the altIDs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setAltIDs(ArrayOfString value) {
        this.altIDs = value;
    }

    /**
     * Gets the value of the label property.
     *
     * @return the label
     * possible object is
     * {@link String }
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Gets the value of the versionable property.
     *
     * @return true, if is versionable
     */
    public boolean isVersionable() {
        return versionable;
    }

    /**
     * Sets the value of the versionable property.
     *
     * @param value the new versionable
     */
    public void setVersionable(boolean value) {
        this.versionable = value;
    }

    /**
     * Gets the value of the mimeType property.
     *
     * @return the mIME type
     * possible object is
     * {@link String }
     */
    public String getMIMEType() {
        return mimeType;
    }

    /**
     * Sets the value of the mimeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMIMEType(String value) {
        this.mimeType = value;
    }

    /**
     * Gets the value of the formatURI property.
     *
     * @return the format uri
     * possible object is
     * {@link String }
     */
    public String getFormatURI() {
        return formatURI;
    }

    /**
     * Sets the value of the formatURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormatURI(String value) {
        this.formatURI = value;
    }

    /**
     * Gets the value of the createDate property.
     *
     * @return the creates the date
     * possible object is
     * {@link String }
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Sets the value of the createDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateDate(String value) {
        this.createDate = value;
    }

    /**
     * Gets the value of the size property.
     *
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     *
     * @param value the new size
     */
    public void setSize(long value) {
        this.size = value;
    }

    /**
     * Gets the value of the state property.
     *
     * @return the state
     * possible object is
     * {@link String }
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the location property.
     *
     * @return the location
     * possible object is
     * {@link String }
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the checksumType property.
     *
     * @return the checksum type
     * possible object is
     * {@link String }
     */
    public String getChecksumType() {
        return checksumType;
    }

    /**
     * Sets the value of the checksumType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChecksumType(String value) {
        this.checksumType = value;
    }

    /**
     * Gets the value of the checksum property.
     *
     * @return the checksum
     * possible object is
     * {@link String }
     */
    public String getChecksum() {
        return checksum;
    }

    /**
     * Sets the value of the checksum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChecksum(String value) {
        this.checksum = value;
    }

}
