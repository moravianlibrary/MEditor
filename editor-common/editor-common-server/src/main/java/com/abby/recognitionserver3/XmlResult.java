
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for XmlResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="XmlResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Priority" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}PriorityEnum"/>
 *         &lt;element name="InputFiles" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}ArrayOfInputFile" minOccurs="0"/>
 *         &lt;element name="JobDocuments" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}ArrayOfJobDocument" minOccurs="0"/>
 *         &lt;element name="PreprocessingParams" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}PreprocessingParams" minOccurs="0"/>
 *         &lt;element name="RecognitionParams" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}RecognitionParams" minOccurs="0"/>
 *         &lt;element name="ExportParams" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}ExportParams" minOccurs="0"/>
 *         &lt;element name="UserProperty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsFailed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Errors" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="Warnings" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="Statistics" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}Statistics" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XmlResult", propOrder = {
    "name",
    "priority",
    "inputFiles",
    "jobDocuments",
    "preprocessingParams",
    "recognitionParams",
    "exportParams",
    "userProperty",
    "isFailed",
    "errors",
    "warnings",
    "statistics"
})
public class XmlResult {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Priority", required = true)
    protected PriorityEnum priority;
    @XmlElement(name = "InputFiles")
    protected ArrayOfInputFile inputFiles;
    @XmlElement(name = "JobDocuments")
    protected ArrayOfJobDocument jobDocuments;
    @XmlElement(name = "PreprocessingParams")
    protected PreprocessingParams preprocessingParams;
    @XmlElement(name = "RecognitionParams")
    protected RecognitionParams recognitionParams;
    @XmlElement(name = "ExportParams")
    protected ExportParams exportParams;
    @XmlElement(name = "UserProperty")
    protected String userProperty;
    @XmlElement(name = "IsFailed")
    protected boolean isFailed;
    @XmlElement(name = "Errors")
    protected ArrayOfString errors;
    @XmlElement(name = "Warnings")
    protected ArrayOfString warnings;
    @XmlElement(name = "Statistics")
    protected Statistics statistics;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link PriorityEnum }
     *     
     */
    public PriorityEnum getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriorityEnum }
     *     
     */
    public void setPriority(PriorityEnum value) {
        this.priority = value;
    }

    /**
     * Gets the value of the inputFiles property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInputFile }
     *     
     */
    public ArrayOfInputFile getInputFiles() {
        return inputFiles;
    }

    /**
     * Sets the value of the inputFiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInputFile }
     *     
     */
    public void setInputFiles(ArrayOfInputFile value) {
        this.inputFiles = value;
    }

    /**
     * Gets the value of the jobDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfJobDocument }
     *     
     */
    public ArrayOfJobDocument getJobDocuments() {
        return jobDocuments;
    }

    /**
     * Sets the value of the jobDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfJobDocument }
     *     
     */
    public void setJobDocuments(ArrayOfJobDocument value) {
        this.jobDocuments = value;
    }

    /**
     * Gets the value of the preprocessingParams property.
     * 
     * @return
     *     possible object is
     *     {@link PreprocessingParams }
     *     
     */
    public PreprocessingParams getPreprocessingParams() {
        return preprocessingParams;
    }

    /**
     * Sets the value of the preprocessingParams property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreprocessingParams }
     *     
     */
    public void setPreprocessingParams(PreprocessingParams value) {
        this.preprocessingParams = value;
    }

    /**
     * Gets the value of the recognitionParams property.
     * 
     * @return
     *     possible object is
     *     {@link RecognitionParams }
     *     
     */
    public RecognitionParams getRecognitionParams() {
        return recognitionParams;
    }

    /**
     * Sets the value of the recognitionParams property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecognitionParams }
     *     
     */
    public void setRecognitionParams(RecognitionParams value) {
        this.recognitionParams = value;
    }

    /**
     * Gets the value of the exportParams property.
     * 
     * @return
     *     possible object is
     *     {@link ExportParams }
     *     
     */
    public ExportParams getExportParams() {
        return exportParams;
    }

    /**
     * Sets the value of the exportParams property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportParams }
     *     
     */
    public void setExportParams(ExportParams value) {
        this.exportParams = value;
    }

    /**
     * Gets the value of the userProperty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserProperty() {
        return userProperty;
    }

    /**
     * Sets the value of the userProperty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserProperty(String value) {
        this.userProperty = value;
    }

    /**
     * Gets the value of the isFailed property.
     * 
     */
    public boolean isIsFailed() {
        return isFailed;
    }

    /**
     * Sets the value of the isFailed property.
     * 
     */
    public void setIsFailed(boolean value) {
        this.isFailed = value;
    }

    /**
     * Gets the value of the errors property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getErrors() {
        return errors;
    }

    /**
     * Sets the value of the errors property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setErrors(ArrayOfString value) {
        this.errors = value;
    }

    /**
     * Gets the value of the warnings property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getWarnings() {
        return warnings;
    }

    /**
     * Sets the value of the warnings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setWarnings(ArrayOfString value) {
        this.warnings = value;
    }

    /**
     * Gets the value of the statistics property.
     * 
     * @return
     *     possible object is
     *     {@link Statistics }
     *     
     */
    public Statistics getStatistics() {
        return statistics;
    }

    /**
     * Sets the value of the statistics property.
     * 
     * @param value
     *     allowed object is
     *     {@link Statistics }
     *     
     */
    public void setStatistics(Statistics value) {
        this.statistics = value;
    }

}
