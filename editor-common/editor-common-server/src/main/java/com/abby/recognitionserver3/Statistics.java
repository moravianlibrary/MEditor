
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Statistics complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Statistics">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PagesArea" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TotalCharacters" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="UncertainCharacters" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Statistics", propOrder = {
    "pagesArea",
    "totalCharacters",
    "uncertainCharacters"
})
public class Statistics {

    @XmlElement(name = "PagesArea")
    protected int pagesArea;
    @XmlElement(name = "TotalCharacters")
    protected int totalCharacters;
    @XmlElement(name = "UncertainCharacters")
    protected int uncertainCharacters;

    /**
     * Gets the value of the pagesArea property.
     * 
     */
    public int getPagesArea() {
        return pagesArea;
    }

    /**
     * Sets the value of the pagesArea property.
     * 
     */
    public void setPagesArea(int value) {
        this.pagesArea = value;
    }

    /**
     * Gets the value of the totalCharacters property.
     * 
     */
    public int getTotalCharacters() {
        return totalCharacters;
    }

    /**
     * Sets the value of the totalCharacters property.
     * 
     */
    public void setTotalCharacters(int value) {
        this.totalCharacters = value;
    }

    /**
     * Gets the value of the uncertainCharacters property.
     * 
     */
    public int getUncertainCharacters() {
        return uncertainCharacters;
    }

    /**
     * Sets the value of the uncertainCharacters property.
     * 
     */
    public void setUncertainCharacters(int value) {
        this.uncertainCharacters = value;
    }

}
