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
import javax.xml.bind.annotation.XmlType;


// TODO: Auto-generated Javadoc
/**
 * <p>Java class for RepositoryInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RepositoryInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="repositoryName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="repositoryVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="repositoryBaseURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="repositoryPIDNamespace" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="defaultExportFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OAINamespace" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="adminEmailList" type="{http://www.fedora.info/definitions/1/0/types/}ArrayOfString"/>
 *         &lt;element name="samplePID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sampleOAIIdentifier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sampleSearchURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sampleAccessURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sampleOAIURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="retainPIDs" type="{http://www.fedora.info/definitions/1/0/types/}ArrayOfString"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RepositoryInfo", propOrder = {
    "repositoryName",
    "repositoryVersion",
    "repositoryBaseURL",
    "repositoryPIDNamespace",
    "defaultExportFormat",
    "oaiNamespace",
    "adminEmailList",
    "samplePID",
    "sampleOAIIdentifier",
    "sampleSearchURL",
    "sampleAccessURL",
    "sampleOAIURL",
    "retainPIDs"
})
public class RepositoryInfo {

    /** The repository name. */
    @XmlElement(required = true, nillable = true)
    protected String repositoryName;
    
    /** The repository version. */
    @XmlElement(required = true, nillable = true)
    protected String repositoryVersion;
    
    /** The repository base url. */
    @XmlElement(required = true, nillable = true)
    protected String repositoryBaseURL;
    
    /** The repository pid namespace. */
    @XmlElement(required = true, nillable = true)
    protected String repositoryPIDNamespace;
    
    /** The default export format. */
    @XmlElement(required = true, nillable = true)
    protected String defaultExportFormat;
    
    /** The oai namespace. */
    @XmlElement(name = "OAINamespace", required = true, nillable = true)
    protected String oaiNamespace;
    
    /** The admin email list. */
    @XmlElement(required = true, nillable = true)
    protected ArrayOfString adminEmailList;
    
    /** The sample pid. */
    @XmlElement(required = true, nillable = true)
    protected String samplePID;
    
    /** The sample oai identifier. */
    @XmlElement(required = true, nillable = true)
    protected String sampleOAIIdentifier;
    
    /** The sample search url. */
    @XmlElement(required = true, nillable = true)
    protected String sampleSearchURL;
    
    /** The sample access url. */
    @XmlElement(required = true, nillable = true)
    protected String sampleAccessURL;
    
    /** The sample oaiurl. */
    @XmlElement(required = true, nillable = true)
    protected String sampleOAIURL;
    
    /** The retain pi ds. */
    @XmlElement(required = true, nillable = true)
    protected ArrayOfString retainPIDs;

    /**
     * Gets the value of the repositoryName property.
     *
     * @return the repository name
     * possible object is
     * {@link String }
     */
    public String getRepositoryName() {
        return repositoryName;
    }

    /**
     * Sets the value of the repositoryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepositoryName(String value) {
        this.repositoryName = value;
    }

    /**
     * Gets the value of the repositoryVersion property.
     *
     * @return the repository version
     * possible object is
     * {@link String }
     */
    public String getRepositoryVersion() {
        return repositoryVersion;
    }

    /**
     * Sets the value of the repositoryVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepositoryVersion(String value) {
        this.repositoryVersion = value;
    }

    /**
     * Gets the value of the repositoryBaseURL property.
     *
     * @return the repository base url
     * possible object is
     * {@link String }
     */
    public String getRepositoryBaseURL() {
        return repositoryBaseURL;
    }

    /**
     * Sets the value of the repositoryBaseURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepositoryBaseURL(String value) {
        this.repositoryBaseURL = value;
    }

    /**
     * Gets the value of the repositoryPIDNamespace property.
     *
     * @return the repository pid namespace
     * possible object is
     * {@link String }
     */
    public String getRepositoryPIDNamespace() {
        return repositoryPIDNamespace;
    }

    /**
     * Sets the value of the repositoryPIDNamespace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepositoryPIDNamespace(String value) {
        this.repositoryPIDNamespace = value;
    }

    /**
     * Gets the value of the defaultExportFormat property.
     *
     * @return the default export format
     * possible object is
     * {@link String }
     */
    public String getDefaultExportFormat() {
        return defaultExportFormat;
    }

    /**
     * Sets the value of the defaultExportFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultExportFormat(String value) {
        this.defaultExportFormat = value;
    }

    /**
     * Gets the value of the oaiNamespace property.
     *
     * @return the oAI namespace
     * possible object is
     * {@link String }
     */
    public String getOAINamespace() {
        return oaiNamespace;
    }

    /**
     * Sets the value of the oaiNamespace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOAINamespace(String value) {
        this.oaiNamespace = value;
    }

    /**
     * Gets the value of the adminEmailList property.
     *
     * @return the admin email list
     * possible object is
     * {@link ArrayOfString }
     */
    public ArrayOfString getAdminEmailList() {
        return adminEmailList;
    }

    /**
     * Sets the value of the adminEmailList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setAdminEmailList(ArrayOfString value) {
        this.adminEmailList = value;
    }

    /**
     * Gets the value of the samplePID property.
     *
     * @return the sample pid
     * possible object is
     * {@link String }
     */
    public String getSamplePID() {
        return samplePID;
    }

    /**
     * Sets the value of the samplePID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSamplePID(String value) {
        this.samplePID = value;
    }

    /**
     * Gets the value of the sampleOAIIdentifier property.
     *
     * @return the sample oai identifier
     * possible object is
     * {@link String }
     */
    public String getSampleOAIIdentifier() {
        return sampleOAIIdentifier;
    }

    /**
     * Sets the value of the sampleOAIIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleOAIIdentifier(String value) {
        this.sampleOAIIdentifier = value;
    }

    /**
     * Gets the value of the sampleSearchURL property.
     *
     * @return the sample search url
     * possible object is
     * {@link String }
     */
    public String getSampleSearchURL() {
        return sampleSearchURL;
    }

    /**
     * Sets the value of the sampleSearchURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleSearchURL(String value) {
        this.sampleSearchURL = value;
    }

    /**
     * Gets the value of the sampleAccessURL property.
     *
     * @return the sample access url
     * possible object is
     * {@link String }
     */
    public String getSampleAccessURL() {
        return sampleAccessURL;
    }

    /**
     * Sets the value of the sampleAccessURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleAccessURL(String value) {
        this.sampleAccessURL = value;
    }

    /**
     * Gets the value of the sampleOAIURL property.
     *
     * @return the sample oaiurl
     * possible object is
     * {@link String }
     */
    public String getSampleOAIURL() {
        return sampleOAIURL;
    }

    /**
     * Sets the value of the sampleOAIURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleOAIURL(String value) {
        this.sampleOAIURL = value;
    }

    /**
     * Gets the value of the retainPIDs property.
     *
     * @return the retain pi ds
     * possible object is
     * {@link ArrayOfString }
     */
    public ArrayOfString getRetainPIDs() {
        return retainPIDs;
    }

    /**
     * Sets the value of the retainPIDs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setRetainPIDs(ArrayOfString value) {
        this.retainPIDs = value;
    }

}
