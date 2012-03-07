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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import org.dom4j.Document;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;

/**
 * @author Martin Rehanek (rehan@mzk.cz)
 */
class DocumentTransformer {

    private static final Logger logger = Logger.getLogger(DocumentTransformer.class.getName());

    public static Document transformDocument(Document sourceDocument, Document xslt) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer(new DocumentSource(xslt));
        } catch (TransformerConfigurationException ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }
        Document finalDoc = null;
        if (null != transformer) {
            DocumentSource source = new DocumentSource(sourceDocument);
            DocumentResult result = new DocumentResult();
            try {
                transformer.transform(source, result);
            } catch (TransformerException ex) {
                logger.log(Level.SEVERE, null, ex);
                return null;
            }
            finalDoc = result.getDocument();
        }
        return finalDoc;
    }
}
