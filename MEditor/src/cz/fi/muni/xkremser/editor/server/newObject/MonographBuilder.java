/*
 * Metadata Editor
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

package cz.fi.muni.xkremser.editor.server.newObject;

import java.io.FileNotFoundException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.XPath;

import cz.fi.muni.xkremser.editor.client.util.Constants.DATASTREAM_CONTROLGROUP;
import cz.fi.muni.xkremser.editor.client.util.Constants.DATASTREAM_ID;

import cz.fi.muni.xkremser.editor.server.fedora.utils.Dom4jUtils;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;

/**
 * @author Jiri Kremser
 * @version 14.11.2011
 */
class MonographBuilder
        extends FoxmlBuilder {

    final Logger logger = Logger.getLogger(MonographBuilder.class.getName());
    private final XPath modsXpath = Dom4jUtils.createXPath("/mods:modsCollection/mods:mods");
    private final XPath locationXpath = Dom4jUtils
            .createXPath("/mods:modsCollection/mods:mods/mods:location");
    private final XPath shelfLocatorXpath = Dom4jUtils.createXPath("mods:shelfLocator");
    private final XPath copyInformationXpath = Dom4jUtils
            .createXPath("//mods:location/mods:holdingSimple/mods:copyInformation");
    private final XPath recordInfoXpath = Dom4jUtils.createXPath("//mods:recordInfo");

    /**
     * @throws FileNotFoundException
     *         final if some xsl template file cannot be found
     * @throws DocumentException
     *         final case of error in loading xsl template
     */
    public MonographBuilder(String label) {
        super(label);
    }

    private void updateDcDoc(Document dcDoc,
                             String pid,
                             String signature,
                             String sysno,
                             DigitalObjectModel model) {
        Element dcRootEl = dcDoc.getRootElement();
        Element idUuid = dcRootEl.addElement("dc:identifier");
        idUuid.addText(pid);
        if (signature != null) {
            Element idSignature = dcRootEl.addElement("dc:identifier");
            idSignature.addText("signature:" + signature);
        }
        if (sysno != null) {
            Element idSysno = dcRootEl.addElement("dc:identifier");
            idSysno.addText("sysno:" + sysno);
        }
        removeDcTypeElements(dcDoc);
        Element typeEl = dcRootEl.addElement("dc:type");
        typeEl.addText("model:" + model.toString());
        Element rightsEl = dcRootEl.addElement("dc:rights");
        rightsEl.addText("policy:" + Policy.PUBLIC.toString().toLowerCase());
        updateDcLanguages(dcDoc);
    }

    private void removeDcTypeElements(Document doc) {
        XPath typeXpath = Dom4jUtils.createXPath("/oai_dc:dc/dc:type");
        List<? extends Node> nodes = typeXpath.selectNodes(doc);
        for (Node node : nodes) {
            node.detach();
        }
    }

    private void updateModsLanguages(Document modsDoc) {
        XPath languageXpath = Dom4jUtils.createXPath("/mods:mods/mods:language/mods:languageTerm");
        updateLanguages(modsDoc, languageXpath);
    }

    private void updateDcLanguages(Document dcDoc) {
        XPath languageXpath = Dom4jUtils.createXPath("/oai_dc:dc/dc:language");
        updateLanguages(dcDoc, languageXpath);
    }

    private void updateLanguages(Document doc, XPath xpath) {
        List<? extends Node> nodes = xpath.selectNodes(doc);
        for (Node languageNode : nodes) {
            Element languageEl = (Element) languageNode;
            String originalLang = languageEl.getTextTrim();
            languageEl.clearContent();
            languageEl.addText(transformLanguage(originalLang));
        }
    }

    private String transformLanguage(String originalLang) {
        if ("d".equals(originalLang)) {
            return "ger";
        }
        return originalLang;
    }

    private void updateModsDoc(Document modsDoc, MarcDocument marc, String uuid) {
        //addPhysicalLocation(modsDoc, marc);
        addUdcOrDdc(modsDoc, marc);
        addTopic(modsDoc, marc);
        addSysno(modsDoc, marc);
        addLinks(modsDoc, marc, uuid);
        //addPublishment(modsDoc, marc);
        updateRecordInfo(modsDoc, uuid);
    }

    private void addPhysicalLocation(Document modsDoc, MarcDocument marc) {
        String location = marc.find040a();
        if (location != null) {
            Element locationEl = (Element) locationXpath.selectSingleNode(modsDoc);
            Element shelfLocatorEl = (Element) shelfLocatorXpath.selectSingleNode(locationEl);
            String shelfLocatorStr = shelfLocatorEl.getTextTrim();
            shelfLocatorEl.detach();
            Element physicalLocationEl =
                    locationEl.addElement(new QName("physicalLocation", Namespaces.mods));
            physicalLocationEl.addText(location);
            shelfLocatorEl = locationEl.addElement(new QName("shelfLocator", Namespaces.mods));
            shelfLocatorEl.addText(shelfLocatorStr);
        }
    }

    private void addUdcOrDdc(Document modsDoc, MarcDocument marc) {
        List<String> udcs = marc.find080a();
        for (String udc : udcs) {
            Element modsEl = (Element) modsXpath.selectSingleNode(modsDoc);
            Element classificationEl = modsEl.addElement(new QName("classification", Namespaces.mods));
            classificationEl.addAttribute("authority", "udc");
            classificationEl.addText(udc);
        }
    }

    private void addTopic(Document modsDoc, MarcDocument marc) {
        String topic = marc.find650a();
        if (topic != null) {
            Element modsEl = (Element) modsXpath.selectSingleNode(modsDoc);
            Element subjectEl = modsEl.addElement(new QName("subject", Namespaces.mods));
            Element topicEl = subjectEl.addElement(new QName("topic", Namespaces.mods));
            topicEl.addText(topic);
        }
    }

    private void addSysno(Document modsDoc, MarcDocument marc) {
        String sysno = marc.findSysno();
        if (sysno != null) {
            Element modsEl = (Element) modsXpath.selectSingleNode(modsDoc);
            Element identifierEl = modsEl.addElement(new QName("identifier", Namespaces.mods));
            identifierEl.addAttribute("type", "sysno");
            identifierEl.addText(sysno);
        }
    }

    private void addLinks(Document modsDoc, MarcDocument marc, String uuid) {
        Element copyInformation = (Element) copyInformationXpath.selectSingleNode(modsDoc);
        copyInformation.addElement(new QName("electronicLocator", Namespaces.mods));
        copyInformation.addText(alephLink(marc));

        Element location = (Element) locationXpath.selectSingleNode(modsDoc);
        Element url = location.addElement(new QName("url", Namespaces.mods));
        url.addText(krameriusLink(uuid));
    }

    private String alephLink(MarcDocument marc) {
        String sysno = marc.findSysno();
        return getAlephUrl() + "/F?func=direct&doc_number=" + sysno + "&local_base=MZK03&format=999";
    }

    private String krameriusLink(String uuid) {
        return getKrameriusUrl() + "/search/handle/uuid:" + uuid;
    }

    private void addPublishment(Document modsDoc, MarcDocument marc) {
        Element modsEl = (Element) modsXpath.selectSingleNode(modsDoc);
        Element originInfoEl = modsEl.addElement(new QName("originInfo", Namespaces.mods));
        originInfoEl.addAttribute("transliteration", "publisher");
        addPublishmentPlace(originInfoEl, marc);
        addPublisher(originInfoEl, marc);
        addPublishmentDate(originInfoEl, marc);
    }

    private void addPublishmentPlace(Element originInfoEl, MarcDocument marc) {
        String place = marc.find260aCorrected();
        if (place != null) {
            Element placeEl = originInfoEl.addElement(new QName("place", Namespaces.mods));
            Element placeTerm = placeEl.addElement(new QName("placeTerm", Namespaces.mods));
            placeTerm.addAttribute("type", "text");
            placeTerm.addText(place);
        }
    }

    private void addPublisher(Element originInfoEl, MarcDocument marc) {
        String name = marc.find260bCorrected();
        if (name != null) {
            Element publisher = originInfoEl.addElement(new QName("publisher", Namespaces.mods));
            publisher.addText(name);
        }
    }

    private void addPublishmentDate(Element originInfoEl, MarcDocument marc) {
        String dates = marc.find260c();
        if (dates != null) {
            Element dateIssued = originInfoEl.addElement(new QName("dateIssued", Namespaces.mods));
            dateIssued.addText(dates);
        }
    }

    private void updateRecordInfo(Document modsDoc, String uuid) {
        Element recordInfo = (Element) recordInfoXpath.selectSingleNode(modsDoc);
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        String nowStr = sdf.format(now);
        Element creationDate = recordInfo.addElement(new QName("recordCreationDate", Namespaces.mods));
        creationDate.addAttribute("encoding", "iso8601");
        creationDate.addText(nowStr);
        Element changeDate = recordInfo.addElement(new QName("recordChangeDate", Namespaces.mods));
        changeDate.addAttribute("encoding", "iso8601");
        changeDate.addText(nowStr);
        Element recordId = recordInfo.addElement(new QName("recordIdentifier", Namespaces.mods));
        recordId.addText("uuid:" + uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateMODSStream() {
        //        updateModsDoc(getModsXmlContent(), getMarcDocument(), getUuid());
        appendDatastream(DATASTREAM_CONTROLGROUP.X, DATASTREAM_ID.BIBLIO_MODS, getModsXmlContent()
                .getRootElement(), null, null);
    }

    @Override
    protected void decorateDCStream() {
        updateDcDoc(getDcXmlContent(), getPid(), getSignature(), getSysno(), getModel());
        appendDatastream(DATASTREAM_CONTROLGROUP.X,
                         DATASTREAM_ID.DC,
                         getDcXmlContent().getRootElement(),
                         null,
                         null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DigitalObjectModel getModel() {
        return DigitalObjectModel.MONOGRAPH;
    }
}
