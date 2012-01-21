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

package cz.fi.muni.xkremser.editor.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.xml.sax.SAXException;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.utils.BiblioModsUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.DCUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.Dom4jUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.RESTHelper;
import cz.fi.muni.xkremser.editor.server.fedora.utils.XMLUtils;
import cz.fi.muni.xkremser.editor.server.mods.ModsCollection;
import cz.fi.muni.xkremser.editor.server.mods.ModsType;
import cz.fi.muni.xkremser.editor.server.newObject.MarcDocument;
import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;
import cz.fi.muni.xkremser.editor.shared.rpc.MarcSpecificMetadata;
import cz.fi.muni.xkremser.editor.shared.rpc.MetadataBundle;

/**
 * @author Jiri Kremser
 * @version 12.11.2011
 */
public class OAIPMHClientImpl
        implements OAIPMHClient {

    private static final Logger LOGGER = Logger.getLogger(OAIPMHClientImpl.class);
    private final XPath dcXPath = Dom4jUtils.createXPath("//oai_dc:dc");

    @Inject
    private EditorConfiguration config;

    private Document marc2mods;

    private Document marc2dc;

    @Override
    public ArrayList<MetadataBundle> search(String url) {
        ArrayList<MetadataBundle> retList = new ArrayList<MetadataBundle>();
        try {
            if (!url.startsWith("http")) {
                url = "http://" + url;
            }
            if (marc2mods == null) {
                if (!new File(config.getEditorHome() + MARC_TO_MODS_XSLT).exists()) {
                    LOGGER.error("File " + config.getEditorHome() + MARC_TO_MODS_XSLT + " does not exist.");
                    return retList;
                }
                marc2mods =
                        Dom4jUtils.loadDocument(new File(config.getEditorHome() + MARC_TO_MODS_XSLT), true);
            }
            InputStream marcStream = RESTHelper.get(url + MARC_METADATA_PREFIX, null, null, false);

            if (marc2dc == null) {
                if (!new File(config.getEditorHome() + MARC_TO_DC_XSLT).exists()) {
                    LOGGER.error("File " + config.getEditorHome() + MARC_TO_DC_XSLT + " does not exist.");
                    return retList;
                }
                marc2dc = Dom4jUtils.loadDocument(new File(config.getEditorHome() + MARC_TO_DC_XSLT), true);
            }

            //            StringWriter writer = new StringWriter();
            //            IOUtils.copy(marcStream, writer, "UTF-8");
            //            String theString = writer.toString();
            //            System.out.println(theString);

            Document marcDoc = Dom4jUtils.loadDocument(marcStream, true);
            if (marcDoc.asXML().contains("idDoesNotExist")
                    || marcDoc.asXML().contains("cannotDisseminateFormat")) {
                return retList;
            }

            MarcDocument mrc = new MarcDocument(marcDoc);
            MarcSpecificMetadata marcSpecific =
                    new MarcSpecificMetadata(mrc.findSysno(),
                                             mrc.findBase(),
                                             mrc.find040a(),
                                             mrc.find080a(),
                                             mrc.find650a(),
                                             mrc.find260aCorrected(),
                                             mrc.find260bCorrected(),
                                             mrc.find260c());
            InputStream dcStream = null;
            boolean dcSuccess = true;
            try {
                dcStream = RESTHelper.get(url + OAI_METADATA_PREFIX, null, null, false);
            } catch (IOException e) {
                LOGGER.warn(e.getMessage());
                dcSuccess = false;
            }
            Document dcDoc = Dom4jUtils.transformDocument(marcDoc, marc2dc);
            //            Document dcDoc = Dom4jUtils.loadDocument(dcStream, true);
            Document modsDoc = Dom4jUtils.transformDocument(marcDoc, marc2mods);

            //            System.out.println("\n\n\n*****\n\n\n");
            //            Dom4jUtils.writeDocument(marcDoc, System.out, PrintType.PRETTY);
            //            System.out.println("\n\n\n*****\n\n\n");
            //            Dom4jUtils.writeDocument(dcDoc, System.out, PrintType.PRETTY);
            //            System.out.println("\n\n\n*****\n\n\n");
            //            Dom4jUtils.writeDocument(modsDoc, System.out, PrintType.PRETTY);
            //            System.out.println("\n\n\n*****\n\n\n");

            Element dcElement = (Element) dcXPath.selectSingleNode(dcDoc);
            final DublinCore dc =
                    DCUtils.getDC(DocumentHelper.createDocument(dcElement.createCopy()).asXML());
            if (dc != null && dc.getTitle() != null && dc.getTitle().size() != 0
                    && dc.getTitle().get(0) != null && "".equals(dc.getTitle().get(0).trim())) {
                dc.addTitle("no title");
            }
            ModsType mods = BiblioModsUtils.getMods(XMLUtils.parseDocument(modsDoc.asXML(), true));
            final ModsCollection modsC = new ModsCollection();
            modsC.setMods(Arrays.asList(mods));
            MetadataBundle bundle = new MetadataBundle(dc, BiblioModsUtils.toModsClient(modsC), marcSpecific);
            retList.add(bundle);
            return retList;
        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage());
        } catch (SAXException e) {
            LOGGER.error(e.getMessage());
        } catch (DocumentException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return retList;
    }
}
