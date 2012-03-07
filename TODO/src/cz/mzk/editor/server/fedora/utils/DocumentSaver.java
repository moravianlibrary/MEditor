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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cz.mzk.editor.server.fedora.utils.Dom4jUtils.PrintType;

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
