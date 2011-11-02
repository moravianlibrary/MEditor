/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.xkremser.editor.server.fedora.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.XPath;

import cz.fi.muni.xkremser.editor.server.newObject.Namespaces;

/**
 * @author Martin Rehanek (rehan@mzk.cz)
 */
public class Dom4jUtils {

    public enum PrintType {

        COMPACT, PRETTY;
    }

    private static final Logger logger = Logger.getLogger(Dom4jUtils.class.getName());

    /**
     * Writes dom4j Document into the file
     * 
     * @param document
     * @param file
     */
    public static boolean saveDocument(Document document, File file) {
        return DocumentSaver.saveDocument(document, file);
    }

    public static boolean writeDocument(Document doc, OutputStream out, PrintType printType) {
        return DocumentSaver.writeDocument(doc, out, printType);
    }

    public static Document loadDocument(File inFile, boolean validate) throws FileNotFoundException,
            DocumentException {
        return DocumentLoader.loadDocument(inFile, validate);
    }

    public static Document loadDocument(InputStream in, boolean validate) throws DocumentException {
        return DocumentLoader.loadDocument(in, validate);
    }

    public static Document loadDocument(String in, boolean validate) throws DocumentException {
        return DocumentLoader.loadDocument(in, validate);
    }

    public static Document loadDocumentIgnoreExternalDtd(InputStream in) throws DocumentException {
        return DocumentLoader.loadDocumentIgnoreExternalDtd(in);
    }

    public static Document loadDocumentIgnoreExternalDtd(File file) throws FileNotFoundException,
            DocumentException {
        return DocumentLoader.loadDocumentIgnoreExternalDtd(file);
    }

    /**
     * @param sourceDocument
     * @param xslt
     * @return
     */
    public static Document transformDocument(Document sourceDocument, Document xslt) {
        return DocumentTransformer.transformDocument(sourceDocument, xslt);
    }

    public static boolean isValid(Document document, File schemaFile) {
        return DocumentValidator.isValid(document, schemaFile);
    }

    public static boolean isValid(Document document, String schemaUrl) {
        return DocumentValidator.isValid(document, schemaUrl);
    }

    public static boolean isValid(File file) {
        return DocumentValidator.isValid(file);
    }

    /**
     * Validation according to schema in url
     * 
     * @param documentFile
     * @param schemaUrl
     */
    public static boolean isValid(File documentFile, String schemaUrl) {
        return DocumentValidator.isValid(documentFile, schemaUrl);
    }

    /**
     * Validation according to schema in url
     * 
     * @param documentFile
     * @param schemaUrl
     */
    public static boolean isValid(File documentFile, File schemaFile) {
        return DocumentValidator.isValid(documentFile, schemaFile);
    }

    /**
     * Creates Xpath with prefixes from class Namespaces
     * 
     * @param expression
     * @return
     */
    public static XPath createXPath(String expression) {
        XPath result = DocumentHelper.createXPath(expression);
        result.setNamespaceURIs(Namespaces.getPrefixUriMap());
        return result;
    }
}
