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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * @author Martin Rehanek (rehan@mzk.cz)
 */
class DocumentLoader {

    public static Document loadDocument(File inFile, boolean validate) throws FileNotFoundException,
            DocumentException {
        FileInputStream inputStream = new FileInputStream(inFile);
        return loadDocument(inputStream, validate);
    }

    public static Document loadDocument(InputStream in, boolean validate) throws DocumentException {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLReader parser = saxParser.getXMLReader();
            // Ignore the DTD declaration
            parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            parser.setFeature("http://xml.org/sax/features/validation", false);
            SAXReader reader = new SAXReader(parser);
            //            SAXReader reader = new SAXReader("org.apache.xerces.parsers.SAXParser");
            //            reader.setIncludeExternalDTDDeclarations(false);
            //            reader.setIncludeInternalDTDDeclarations(false);
            //
            //            reader.setFeature("http://xml.org/sax/features/validation", false);
            //            reader.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            return reader.read(in);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DocumentLoader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SAXException ex) {
            Logger.getLogger(DocumentLoader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Document loadDocumentIgnoreExternalDtd(InputStream in) throws DocumentException {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLReader parser = saxParser.getXMLReader();
            parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            parser.setFeature("http://xml.org/sax/features/validation", false);
            SAXReader reader = new SAXReader(parser);
            return reader.read(in);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DocumentLoader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SAXException ex) {
            Logger.getLogger(DocumentLoader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    static Document loadDocumentIgnoreExternalDtd(File file) throws FileNotFoundException, DocumentException {
        FileInputStream inputStream = new FileInputStream(file);
        return loadDocumentIgnoreExternalDtd(inputStream);
    }

    static Document loadDocument(String in, boolean validate) throws DocumentException {
        InputStream inStream = stringToInputStream(in);
        return loadDocument(inStream, validate);
    }

    private static InputStream stringToInputStream(String in) {
        try {
            return new ByteArrayInputStream(in.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DocumentLoader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
}
