
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PDFEncryptionInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PDFEncryptionInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OwnerPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EncryptionLevel" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}PDFEncryptionLevelEnum"/>
 *         &lt;element name="AllowPrinting" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AllowPrintingExt" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AllowModifyingContent" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AllowExtractingTextAndGraphics" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AllowAddingTextAnnotations" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AllowFillingFormFields" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AllowExtractingTextAndGraphicsExt" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AllowDocumentAssembling" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PDFEncryptionInfo", propOrder = {
    "userPassword",
    "ownerPassword",
    "encryptionLevel",
    "allowPrinting",
    "allowPrintingExt",
    "allowModifyingContent",
    "allowExtractingTextAndGraphics",
    "allowAddingTextAnnotations",
    "allowFillingFormFields",
    "allowExtractingTextAndGraphicsExt",
    "allowDocumentAssembling"
})
public class PDFEncryptionInfo {

    @XmlElement(name = "UserPassword")
    protected String userPassword;
    @XmlElement(name = "OwnerPassword")
    protected String ownerPassword;
    @XmlElement(name = "EncryptionLevel", required = true)
    protected PDFEncryptionLevelEnum encryptionLevel;
    @XmlElement(name = "AllowPrinting")
    protected boolean allowPrinting;
    @XmlElement(name = "AllowPrintingExt")
    protected boolean allowPrintingExt;
    @XmlElement(name = "AllowModifyingContent")
    protected boolean allowModifyingContent;
    @XmlElement(name = "AllowExtractingTextAndGraphics")
    protected boolean allowExtractingTextAndGraphics;
    @XmlElement(name = "AllowAddingTextAnnotations")
    protected boolean allowAddingTextAnnotations;
    @XmlElement(name = "AllowFillingFormFields")
    protected boolean allowFillingFormFields;
    @XmlElement(name = "AllowExtractingTextAndGraphicsExt")
    protected boolean allowExtractingTextAndGraphicsExt;
    @XmlElement(name = "AllowDocumentAssembling")
    protected boolean allowDocumentAssembling;

    /**
     * Gets the value of the userPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Sets the value of the userPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserPassword(String value) {
        this.userPassword = value;
    }

    /**
     * Gets the value of the ownerPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnerPassword() {
        return ownerPassword;
    }

    /**
     * Sets the value of the ownerPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnerPassword(String value) {
        this.ownerPassword = value;
    }

    /**
     * Gets the value of the encryptionLevel property.
     * 
     * @return
     *     possible object is
     *     {@link PDFEncryptionLevelEnum }
     *     
     */
    public PDFEncryptionLevelEnum getEncryptionLevel() {
        return encryptionLevel;
    }

    /**
     * Sets the value of the encryptionLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link PDFEncryptionLevelEnum }
     *     
     */
    public void setEncryptionLevel(PDFEncryptionLevelEnum value) {
        this.encryptionLevel = value;
    }

    /**
     * Gets the value of the allowPrinting property.
     * 
     */
    public boolean isAllowPrinting() {
        return allowPrinting;
    }

    /**
     * Sets the value of the allowPrinting property.
     * 
     */
    public void setAllowPrinting(boolean value) {
        this.allowPrinting = value;
    }

    /**
     * Gets the value of the allowPrintingExt property.
     * 
     */
    public boolean isAllowPrintingExt() {
        return allowPrintingExt;
    }

    /**
     * Sets the value of the allowPrintingExt property.
     * 
     */
    public void setAllowPrintingExt(boolean value) {
        this.allowPrintingExt = value;
    }

    /**
     * Gets the value of the allowModifyingContent property.
     * 
     */
    public boolean isAllowModifyingContent() {
        return allowModifyingContent;
    }

    /**
     * Sets the value of the allowModifyingContent property.
     * 
     */
    public void setAllowModifyingContent(boolean value) {
        this.allowModifyingContent = value;
    }

    /**
     * Gets the value of the allowExtractingTextAndGraphics property.
     * 
     */
    public boolean isAllowExtractingTextAndGraphics() {
        return allowExtractingTextAndGraphics;
    }

    /**
     * Sets the value of the allowExtractingTextAndGraphics property.
     * 
     */
    public void setAllowExtractingTextAndGraphics(boolean value) {
        this.allowExtractingTextAndGraphics = value;
    }

    /**
     * Gets the value of the allowAddingTextAnnotations property.
     * 
     */
    public boolean isAllowAddingTextAnnotations() {
        return allowAddingTextAnnotations;
    }

    /**
     * Sets the value of the allowAddingTextAnnotations property.
     * 
     */
    public void setAllowAddingTextAnnotations(boolean value) {
        this.allowAddingTextAnnotations = value;
    }

    /**
     * Gets the value of the allowFillingFormFields property.
     * 
     */
    public boolean isAllowFillingFormFields() {
        return allowFillingFormFields;
    }

    /**
     * Sets the value of the allowFillingFormFields property.
     * 
     */
    public void setAllowFillingFormFields(boolean value) {
        this.allowFillingFormFields = value;
    }

    /**
     * Gets the value of the allowExtractingTextAndGraphicsExt property.
     * 
     */
    public boolean isAllowExtractingTextAndGraphicsExt() {
        return allowExtractingTextAndGraphicsExt;
    }

    /**
     * Sets the value of the allowExtractingTextAndGraphicsExt property.
     * 
     */
    public void setAllowExtractingTextAndGraphicsExt(boolean value) {
        this.allowExtractingTextAndGraphicsExt = value;
    }

    /**
     * Gets the value of the allowDocumentAssembling property.
     * 
     */
    public boolean isAllowDocumentAssembling() {
        return allowDocumentAssembling;
    }

    /**
     * Sets the value of the allowDocumentAssembling property.
     * 
     */
    public void setAllowDocumentAssembling(boolean value) {
        this.allowDocumentAssembling = value;
    }

}
