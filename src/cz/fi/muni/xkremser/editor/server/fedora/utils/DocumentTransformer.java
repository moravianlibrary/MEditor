/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.xkremser.editor.server.fedora.utils;

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
