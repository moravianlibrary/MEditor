
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HeaderAndFooterSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HeaderAndFooterSettings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LeftHeader" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CentralHeader" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RightHeader" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LeftFooter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CentralFooter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RightFooter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FontName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FontSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IsBold" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsItalic" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsUnderlined" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="TextColor" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TopMargin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="BottomMargin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="LeftMargin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="RightMargin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="IsInInches" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="StartingNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NumberOfDigits" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderAndFooterSettings", propOrder = {
    "leftHeader",
    "centralHeader",
    "rightHeader",
    "leftFooter",
    "centralFooter",
    "rightFooter",
    "fontName",
    "fontSize",
    "isBold",
    "isItalic",
    "isUnderlined",
    "textColor",
    "topMargin",
    "bottomMargin",
    "leftMargin",
    "rightMargin",
    "isInInches",
    "startingNumber",
    "numberOfDigits"
})
public class HeaderAndFooterSettings {

    @XmlElement(name = "LeftHeader")
    protected String leftHeader;
    @XmlElement(name = "CentralHeader")
    protected String centralHeader;
    @XmlElement(name = "RightHeader")
    protected String rightHeader;
    @XmlElement(name = "LeftFooter")
    protected String leftFooter;
    @XmlElement(name = "CentralFooter")
    protected String centralFooter;
    @XmlElement(name = "RightFooter")
    protected String rightFooter;
    @XmlElement(name = "FontName")
    protected String fontName;
    @XmlElement(name = "FontSize")
    protected int fontSize;
    @XmlElement(name = "IsBold")
    protected boolean isBold;
    @XmlElement(name = "IsItalic")
    protected boolean isItalic;
    @XmlElement(name = "IsUnderlined")
    protected boolean isUnderlined;
    @XmlElement(name = "TextColor")
    protected int textColor;
    @XmlElement(name = "TopMargin")
    protected double topMargin;
    @XmlElement(name = "BottomMargin")
    protected double bottomMargin;
    @XmlElement(name = "LeftMargin")
    protected double leftMargin;
    @XmlElement(name = "RightMargin")
    protected double rightMargin;
    @XmlElement(name = "IsInInches")
    protected boolean isInInches;
    @XmlElement(name = "StartingNumber")
    protected int startingNumber;
    @XmlElement(name = "NumberOfDigits")
    protected int numberOfDigits;

    /**
     * Gets the value of the leftHeader property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeftHeader() {
        return leftHeader;
    }

    /**
     * Sets the value of the leftHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeftHeader(String value) {
        this.leftHeader = value;
    }

    /**
     * Gets the value of the centralHeader property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCentralHeader() {
        return centralHeader;
    }

    /**
     * Sets the value of the centralHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCentralHeader(String value) {
        this.centralHeader = value;
    }

    /**
     * Gets the value of the rightHeader property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRightHeader() {
        return rightHeader;
    }

    /**
     * Sets the value of the rightHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRightHeader(String value) {
        this.rightHeader = value;
    }

    /**
     * Gets the value of the leftFooter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeftFooter() {
        return leftFooter;
    }

    /**
     * Sets the value of the leftFooter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeftFooter(String value) {
        this.leftFooter = value;
    }

    /**
     * Gets the value of the centralFooter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCentralFooter() {
        return centralFooter;
    }

    /**
     * Sets the value of the centralFooter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCentralFooter(String value) {
        this.centralFooter = value;
    }

    /**
     * Gets the value of the rightFooter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRightFooter() {
        return rightFooter;
    }

    /**
     * Sets the value of the rightFooter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRightFooter(String value) {
        this.rightFooter = value;
    }

    /**
     * Gets the value of the fontName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFontName() {
        return fontName;
    }

    /**
     * Sets the value of the fontName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFontName(String value) {
        this.fontName = value;
    }

    /**
     * Gets the value of the fontSize property.
     * 
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * Sets the value of the fontSize property.
     * 
     */
    public void setFontSize(int value) {
        this.fontSize = value;
    }

    /**
     * Gets the value of the isBold property.
     * 
     */
    public boolean isIsBold() {
        return isBold;
    }

    /**
     * Sets the value of the isBold property.
     * 
     */
    public void setIsBold(boolean value) {
        this.isBold = value;
    }

    /**
     * Gets the value of the isItalic property.
     * 
     */
    public boolean isIsItalic() {
        return isItalic;
    }

    /**
     * Sets the value of the isItalic property.
     * 
     */
    public void setIsItalic(boolean value) {
        this.isItalic = value;
    }

    /**
     * Gets the value of the isUnderlined property.
     * 
     */
    public boolean isIsUnderlined() {
        return isUnderlined;
    }

    /**
     * Sets the value of the isUnderlined property.
     * 
     */
    public void setIsUnderlined(boolean value) {
        this.isUnderlined = value;
    }

    /**
     * Gets the value of the textColor property.
     * 
     */
    public int getTextColor() {
        return textColor;
    }

    /**
     * Sets the value of the textColor property.
     * 
     */
    public void setTextColor(int value) {
        this.textColor = value;
    }

    /**
     * Gets the value of the topMargin property.
     * 
     */
    public double getTopMargin() {
        return topMargin;
    }

    /**
     * Sets the value of the topMargin property.
     * 
     */
    public void setTopMargin(double value) {
        this.topMargin = value;
    }

    /**
     * Gets the value of the bottomMargin property.
     * 
     */
    public double getBottomMargin() {
        return bottomMargin;
    }

    /**
     * Sets the value of the bottomMargin property.
     * 
     */
    public void setBottomMargin(double value) {
        this.bottomMargin = value;
    }

    /**
     * Gets the value of the leftMargin property.
     * 
     */
    public double getLeftMargin() {
        return leftMargin;
    }

    /**
     * Sets the value of the leftMargin property.
     * 
     */
    public void setLeftMargin(double value) {
        this.leftMargin = value;
    }

    /**
     * Gets the value of the rightMargin property.
     * 
     */
    public double getRightMargin() {
        return rightMargin;
    }

    /**
     * Sets the value of the rightMargin property.
     * 
     */
    public void setRightMargin(double value) {
        this.rightMargin = value;
    }

    /**
     * Gets the value of the isInInches property.
     * 
     */
    public boolean isIsInInches() {
        return isInInches;
    }

    /**
     * Sets the value of the isInInches property.
     * 
     */
    public void setIsInInches(boolean value) {
        this.isInInches = value;
    }

    /**
     * Gets the value of the startingNumber property.
     * 
     */
    public int getStartingNumber() {
        return startingNumber;
    }

    /**
     * Sets the value of the startingNumber property.
     * 
     */
    public void setStartingNumber(int value) {
        this.startingNumber = value;
    }

    /**
     * Gets the value of the numberOfDigits property.
     * 
     */
    public int getNumberOfDigits() {
        return numberOfDigits;
    }

    /**
     * Sets the value of the numberOfDigits property.
     * 
     */
    public void setNumberOfDigits(int value) {
        this.numberOfDigits = value;
    }

}
