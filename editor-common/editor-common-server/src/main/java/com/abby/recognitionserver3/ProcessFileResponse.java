
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
 *         &lt;element name="ProcessFileResult" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}XmlResult" minOccurs="0"/>
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
    "processFileResult"
})
@XmlRootElement(name = "ProcessFileResponse")
public class ProcessFileResponse {

    @XmlElement(name = "ProcessFileResult")
    protected XmlResult processFileResult;

    /**
     * Gets the value of the processFileResult property.
     * 
     * @return
     *     possible object is
     *     {@link XmlResult }
     *     
     */
    public XmlResult getProcessFileResult() {
        return processFileResult;
    }

    /**
     * Sets the value of the processFileResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlResult }
     *     
     */
    public void setProcessFileResult(XmlResult value) {
        this.processFileResult = value;
    }

}
