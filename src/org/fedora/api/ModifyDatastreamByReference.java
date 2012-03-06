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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * Java class for anonymous complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dsID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="altIDs" type="{http://www.fedora.info/definitions/1/0/types/}ArrayOfString"/>
 *         &lt;element name="dsLabel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MIMEType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="formatURI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dsLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="checksumType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="checksum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="logMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="force" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"pid", "dsID", "altIDs", "dsLabel", "mimeType", "formatURI", "dsLocation",
        "checksumType", "checksum", "logMessage", "force"})
@XmlRootElement(name = "modifyDatastreamByReference")
public class ModifyDatastreamByReference {

    /** The pid. */
    @XmlElement(required = true)
    protected String pid;

    /** The ds id. */
    @XmlElement(required = true)
    protected String dsID;

    /** The alt i ds. */
    @XmlElement(required = true)
    protected ArrayOfString altIDs;

    /** The ds label. */
    @XmlElement(required = true)
    protected String dsLabel;

    /** The mime type. */
    @XmlElement(name = "MIMEType", required = true)
    protected String mimeType;

    /** The format uri. */
    @XmlElement(required = true)
    protected String formatURI;

    /** The ds location. */
    @XmlElement(required = true)
    protected String dsLocation;

    /** The checksum type. */
    @XmlElement(required = true)
    protected String checksumType;

    /** The checksum. */
    @XmlElement(required = true)
    protected String checksum;

    /** The log message. */
    @XmlElement(required = true)
    protected String logMessage;

    /** The force. */
    protected boolean force;

    /**
     * Gets the value of the pid property.
     * 
     * @return the pid possible object is {@link String }
     */
    public String getPid() {
        return pid;
    }

    /**
     * Sets the value of the pid property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setPid(String value) {
        this.pid = value;
    }

    /**
     * Gets the value of the dsID property.
     * 
     * @return the ds id possible object is {@link String }
     */
    public String getDsID() {
        return dsID;
    }

    /**
     * Sets the value of the dsID property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setDsID(String value) {
        this.dsID = value;
    }

    /**
     * Gets the value of the altIDs property.
     * 
     * @return the alt i ds possible object is {@link ArrayOfString }
     */
    public ArrayOfString getAltIDs() {
        return altIDs;
    }

    /**
     * Sets the value of the altIDs property.
     * 
     * @param value
     *        allowed object is {@link ArrayOfString }
     */
    public void setAltIDs(ArrayOfString value) {
        this.altIDs = value;
    }

    /**
     * Gets the value of the dsLabel property.
     * 
     * @return the ds label possible object is {@link String }
     */
    public String getDsLabel() {
        return dsLabel;
    }

    /**
     * Sets the value of the dsLabel property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setDsLabel(String value) {
        this.dsLabel = value;
    }

    /**
     * Gets the value of the mimeType property.
     * 
     * @return the mIME type possible object is {@link String }
     */
    public String getMIMEType() {
        return mimeType;
    }

    /**
     * Sets the value of the mimeType property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setMIMEType(String value) {
        this.mimeType = value;
    }

    /**
     * Gets the value of the formatURI property.
     * 
     * @return the format uri possible object is {@link String }
     */
    public String getFormatURI() {
        return formatURI;
    }

    /**
     * Sets the value of the formatURI property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setFormatURI(String value) {
        this.formatURI = value;
    }

    /**
     * Gets the value of the dsLocation property.
     * 
     * @return the ds location possible object is {@link String }
     */
    public String getDsLocation() {
        return dsLocation;
    }

    /**
     * Sets the value of the dsLocation property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setDsLocation(String value) {
        this.dsLocation = value;
    }

    /**
     * Gets the value of the checksumType property.
     * 
     * @return the checksum type possible object is {@link String }
     */
    public String getChecksumType() {
        return checksumType;
    }

    /**
     * Sets the value of the checksumType property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setChecksumType(String value) {
        this.checksumType = value;
    }

    /**
     * Gets the value of the checksum property.
     * 
     * @return the checksum possible object is {@link String }
     */
    public String getChecksum() {
        return checksum;
    }

    /**
     * Sets the value of the checksum property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setChecksum(String value) {
        this.checksum = value;
    }

    /**
     * Gets the value of the logMessage property.
     * 
     * @return the log message possible object is {@link String }
     */
    public String getLogMessage() {
        return logMessage;
    }

    /**
     * Sets the value of the logMessage property.
     * 
     * @param value
     *        allowed object is {@link String }
     */
    public void setLogMessage(String value) {
        this.logMessage = value;
    }

    /**
     * Gets the value of the force property.
     * 
     * @return true, if is force
     */
    public boolean isForce() {
        return force;
    }

    /**
     * Sets the value of the force property.
     * 
     * @param value
     *        the new force
     */
    public void setForce(boolean value) {
        this.force = value;
    }

}
