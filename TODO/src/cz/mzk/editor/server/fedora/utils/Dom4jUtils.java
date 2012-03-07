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

package cz.mzk.editor.server.fedora.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.XPath;

import cz.mzk.editor.server.newObject.Namespaces;

/**
 * @author Martin Rehanek (rehan@mzk.cz)
 */
public class Dom4jUtils {

    public enum PrintType {

        COMPACT, PRETTY;
    }

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
