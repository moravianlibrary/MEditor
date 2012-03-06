/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.xkremser.editor.server.fedora.utils;

import java.io.File;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import org.xml.sax.SAXException;

/**
 * @author Martin Rehanek (rehan@mzk.cz)
 */
public class DocumentValidator {

    private static final Logger logger = Logger.getLogger(DocumentValidator.class.getName());
    private static final File tmpDir = new File("/tmp");

    public static boolean isValid(Document document, File schemaFile) {
        File tmpFile = saveToTmpFile(document);
        return isValid(tmpFile, schemaFile);
    }

    public static boolean isValid(Document document, String schemaUrl) {
        File tmpFile = saveToTmpFile(document);
        return isValid(tmpFile, schemaUrl);
    }

    private static File saveToTmpFile(Document document) {
        File tmpFile = new File(tmpDir.getAbsolutePath().concat(File.separator).concat("dom4jutils.xml"));
        DocumentSaver.saveDocument(document, tmpFile);
        return tmpFile;
    }

    /**
     * Validation according to schema in url
     * 
     * @param documentFile
     * @param schemaUrl
     * @return
     */
    public static boolean isValid(File documentFile, String schemaUrl) {
        try {
            SAXReader reader = new SAXReader(true);
            // set the validation feature to true to report validation errors
            reader.setFeature("http://xml.org/sax/features/validation", true);
            // set the validation/schema feature to true to report validation errors against a schema
            reader.setFeature("http://apache.org/xml/features/validation/schema", true);
            // set the validation/schema-full-checking feature to true to enable full schema, grammar-constraint checking
            reader.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
            //reader.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", schemaUrl);
            reader.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation", schemaUrl);
            readDocument(reader, documentFile);
            return true;
        } catch (DocumentException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            return false;
        } catch (SAXException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Validation according to schema in url
     * 
     * @param documentFile
     * @param schemaUrl
     * @return
     */
    public static boolean isValid(File documentFile, File schemaFile) {
        try {
            SAXReader reader = new SAXReader(true);
            reader.setFeature("http://apache.org/xml/features/validation/schema", true);
            // set the validation feature to true to report validation errors
            reader.setFeature("http://xml.org/sax/features/validation", true);
            // set the validation/schema feature to true to report validation errors against a schema
            reader.setFeature("http://apache.org/xml/features/validation/schema", true);
            // set the validation/schema-full-checking feature to true to enable full schema, grammar-constraint checking
            reader.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
            reader.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",
                               schemaFile.getAbsolutePath());
            readDocument(reader, documentFile);
            return true;
        } catch (DocumentException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            return false;
        } catch (SAXException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean isValid(File file) {
        try {
            SAXReader reader = new SAXReader(true);
            reader.setFeature("http://apache.org/xml/features/validation/schema", true);
            // set the validation feature to true to report validation errors
            reader.setFeature("http://xml.org/sax/features/validation", true);
            // set the validation/schema feature to true to report validation errors against a schema
            reader.setFeature("http://apache.org/xml/features/validation/schema", true);
            // set the validation/schema-full-checking feature to true to enable full schema, grammar-constraint checking
            reader.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
            readDocument(reader, file);
            return true;
        } catch (DocumentException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            return false;
        } catch (SAXException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private static Document readDocument(SAXReader reader, File documentFile) throws DocumentException {
        return reader.read(documentFile);
    }
}
