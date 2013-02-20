
package com.abby.recognitionserver3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="ProcessTicketResult" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}XmlResult" minOccurs="0"/>
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
    "processTicketResult"
})
@XmlRootElement(name = "ProcessTicketResponse")
public class ProcessTicketResponse {

    @XmlElement(name = "ProcessTicketResult")
    protected XmlResult processTicketResult;

    /**
     * Gets the value of the processTicketResult property.
     * 
     * @return
     *     possible object is
     *     {@link XmlResult }
     *     
     */
    public XmlResult getProcessTicketResult() {
        return processTicketResult;
    }

    /**
     * Sets the value of the processTicketResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlResult }
     *     
     */
    public void setProcessTicketResult(XmlResult value) {
        this.processTicketResult = value;
    }

}
