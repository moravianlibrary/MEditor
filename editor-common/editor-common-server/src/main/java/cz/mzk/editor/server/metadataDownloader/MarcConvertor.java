package cz.mzk.editor.server.metadataDownloader;

import cz.mzk.editor.server.fedora.utils.BiblioModsUtils;
import cz.mzk.editor.server.fedora.utils.DCUtils;
import cz.mzk.editor.server.fedora.utils.Dom4jUtils;
import cz.mzk.editor.server.newObject.MarcDocument;
import cz.mzk.editor.server.util.XMLUtils;
import cz.mzk.editor.shared.rpc.DublinCore;
import cz.mzk.editor.server.mods.ModsType;
import org.dom4j.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by rumanekm on 30.8.14.
 */
public class MarcConvertor {

    private static Document marc2dcXsl;
    private static Document marc2modsXsl;


    String MARC_TO_MODS_XSLT = File.separator + "xml" + File.separator + "MARC21slim2MODS3.xsl";
    String MARC_TO_DC_XSLT = File.separator + "xml" + File.separator + "MARC21slim2OAIDC.xsl";
    String MARC_UTILS = File.separator + "xml" + File.separator + "MARC21slimUtils.xsl";
    private final XPath dcXPath = Dom4jUtils.createXPath("//oai_dc:dc");
    public static final String WORKING_DIR = System.getProperty("user.home") + File.separator + ".meditor";


    public DublinCore marc2dublincore(MarcDocument marcDocument) throws FileNotFoundException, DocumentException {
        if (marc2dcXsl == null) {
            marc2dcXsl = Dom4jUtils.loadDocument(new File(WORKING_DIR + MARC_TO_DC_XSLT), true);
        }

        Document dcDoc = Dom4jUtils.transformDocument(marcDocument.getDocument(), marc2dcXsl);
        Element dcElement = (Element) dcXPath.selectSingleNode(dcDoc);

        return DCUtils.getDC(DocumentHelper.createDocument(dcElement.createCopy()).asXML());
    }

    public ModsType marc2mods(MarcDocument marcDocument) throws IOException, DocumentException, SAXException, ParserConfigurationException {
        if (marc2modsXsl == null) {
            marc2modsXsl = Dom4jUtils.loadDocument(new File(WORKING_DIR + MARC_TO_MODS_XSLT), true);
        }

        Document modsDoc = Dom4jUtils.transformDocument(marcDocument.getDocument(), marc2modsXsl);
        ModsType mods = BiblioModsUtils.getMods(XMLUtils.parseDocument(modsDoc.asXML(), true));

        return mods;
    }
}
