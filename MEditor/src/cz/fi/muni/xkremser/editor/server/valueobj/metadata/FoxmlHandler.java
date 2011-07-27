
package cz.fi.muni.xkremser.editor.server.valueobj.metadata;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.ParserConfigurationException;

import com.uwyn.jhighlight.renderer.XhtmlRendererFactory;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;

import org.xml.sax.SAXException;

import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FoxmlUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.XMLUtils;


public class FoxmlHandler {

    private static final Logger LOGGER = Logger.getLogger(FoxmlHandler.class);

    /**
     * Handle foxml.
     * 
     * @param uuid
     *        the uuid
     * @param onlyTitleAndUuid
     *        the only title and uuid
     * @return the Foxml
     */
    public static Foxml handleFoxml(String uuid, FedoraAccess fedoraAccess) {
        Foxml foxml = new Foxml();

        String stringFoxml = fedoraAccess.getFOXML(uuid);
        InputStream is = getInputStreamFromString(stringFoxml, uuid);

        try {
            foxml.setFoxml(handleFOXMLString(stringFoxml, uuid));
            Document foxmlDocument = getFoxmlDocument(is);
            foxml.setIdentifier(uuid);
            foxml.setLabel(FoxmlUtils.getLabel(foxmlDocument));

        } catch (IOException e) {
            LOGGER.error("Unable to get Foxml metadata for " + uuid + "[" + e.getMessage() + "]", e);
        }
        return foxml;
    }

    private static Document getFoxmlDocument(InputStream docStream) throws IOException {
        try {
            return XMLUtils.parseDocument(docStream, true);
        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        } catch (SAXException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        } finally {
            docStream.close();
        }
    }

    /**
     * Handle foxml.
     * 
     * @param uuid
     *        the uuid
     * @return the string
     */
    private static String handleFOXMLString(String returnString, String uuid) {
        String returnedString = returnString;
        String highlighted = null;
        try {
            highlighted =
                    XhtmlRendererFactory.getRenderer("xml").highlight("foxml",
                                                                      returnedString,
                                                                      "Windows-1250",
                                                                      true);
        } catch (IOException e) {
            LOGGER.error("Unable to get FOXML representation for " + uuid + "[" + e.getMessage() + "]", e);
            return returnedString;
        }
        return highlighted.substring(highlighted.indexOf('\n'));
    }

    private static InputStream getInputStreamFromString(String stringFoxml, String uuid) {
        try {
            return new ByteArrayInputStream(stringFoxml.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Unable to get InputStream of Foxml from String format for " + uuid + "["
                                 + e.getMessage() + "]",
                         e);
        }
        return null;
    }
}
