/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.xkremser.editor.server.fedora.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cz.fi.muni.xkremser.editor.server.fedora.utils.Dom4jUtils.PrintType;

/**
 * @author Martin Rehanek (rehan@mzk.cz)
 */
class DocumentSaver {

    private static final Logger logger = Logger.getLogger(DocumentSaver.class.getName());

    public static boolean saveDocument(Document document, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            return writeDocument(document, fos, PrintType.PRETTY);
        } catch (FileNotFoundException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    public static boolean writeDocument(Document document, OutputStream out, PrintType printType) {
        OutputFormat format = chooseOutputFormat(printType);
        try {
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            writer.flush();
            return true;
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private static OutputFormat chooseOutputFormat(PrintType printType) {
        switch (printType) {
            case COMPACT:
                return OutputFormat.createCompactFormat();
            case PRETTY:
                return OutputFormat.createPrettyPrint();
            default:
                return OutputFormat.createCompactFormat();
        }
    }
}
