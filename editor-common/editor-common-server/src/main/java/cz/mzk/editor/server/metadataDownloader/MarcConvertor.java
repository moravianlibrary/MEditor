package cz.mzk.editor.server.metadataDownloader;

import com.google.inject.Inject;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.fedora.utils.DCUtils;
import cz.mzk.editor.server.fedora.utils.Dom4jUtils;
import cz.mzk.editor.server.newObject.MarcDocument;
import cz.mzk.editor.shared.rpc.DublinCore;
import cz.mzk.editor.server.mods.ModsType;
import org.apache.log4j.Logger;
import org.dom4j.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;

/**
 * Created by rumanekm on 30.8.14.
 */
public class MarcConvertor {

    private static Document marc2dc;


    String MARC_TO_MODS_XSLT = File.separator + "xml" + File.separator + "MARC21slim2MODS3.xsl";
    String MARC_TO_DC_XSLT = File.separator + "xml" + File.separator + "MARC21slim2OAIDC.xsl";
    String MARC_UTILS = File.separator + "xml" + File.separator + "MARC21slimUtils.xsl";
    private final XPath dcXPath = Dom4jUtils.createXPath("//oai_dc:dc");
    public static final String WORKING_DIR = System.getProperty("user.home") + File.separator + ".meditor";


    public DublinCore marc2dublincore(MarcDocument marcDoc) throws FileNotFoundException, DocumentException {
        if (marc2dc == null) {
            marc2dc = Dom4jUtils.loadDocument(new File(WORKING_DIR + MARC_TO_DC_XSLT), true);
        }

        Document dcDoc = Dom4jUtils.transformDocument(marcDoc.getDocument(), marc2dc);
        Element dcElement = (Element) dcXPath.selectSingleNode(dcDoc);
        return DCUtils.getDC(DocumentHelper.createDocument(dcElement.createCopy()).asXML());
    }
}
