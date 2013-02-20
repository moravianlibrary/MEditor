
package com.abby.recognitionserver3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfInputFile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfInputFile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InputFile" type="{http://www.abbyy.com/RecognitionServer3_xml/RecognitionServer3.xml}InputFile" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfInputFile", propOrder = {
    "inputFile"
})
public class ArrayOfInputFile {

    @XmlElement(name = "InputFile", nillable = true)
    protected List<InputFile> inputFile;

    /**
     * Gets the value of the inputFile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inputFile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInputFile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InputFile }
     * 
     * 
     */
    public List<InputFile> getInputFile() {
        if (inputFile == null) {
            inputFile = new ArrayList<InputFile>();
        }
        return this.inputFile;
    }

}
