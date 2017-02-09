package cz.mzk.editor.server.newObject;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.server.fedora.utils.Dom4jUtils;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.domain.FedoraNamespaces;
import cz.mzk.editor.shared.rpc.MarcSpecificMetadata;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.XPath;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author: Martin Rumanek
 * @version: 17.11.12
 */
@Component
public class SoundRecordingBuilder extends FoxmlBuilder {
    private final XPath locationXpath = Dom4jUtils
            .createXPath("/mods:modsCollection/mods:mods/mods:location");

    final Logger logger = Logger.getLogger(MonographBuilder.class.getName());
    private final XPath modsXpath = Dom4jUtils.createXPath("/mods:modsCollection/mods:mods");
    private final XPath recordInfoXpath = Dom4jUtils.createXPath("//mods:recordInfo");

    private final DigitalObjectModel model = DigitalObjectModel.SOUNDRECORDING;

    @Override
    protected void decorateMODSStream() {
        if (getBundle() != null && getBundle().getMarc() != null) {
            updateModsDoc(getModsXmlContent(), getBundle().getMarc(), getUuid());
        }
        appendDatastream(Constants.DATASTREAM_CONTROLGROUP.X, Constants.DATASTREAM_ID.BIBLIO_MODS, getModsXmlContent()
                .getRootElement(), null, null);
    }

    private void updateModsDoc(Document modsDoc, MarcSpecificMetadata marc, String uuid) {
        addRootUdcOrDdc((Element) modsXpath.selectSingleNode(modsDoc));
        addRootTopic((Element) modsXpath.selectSingleNode(modsDoc));
        addSysno(modsDoc, marc);
        updateRecordInfo(modsDoc);
        addIdentifierUuid((Element) modsXpath.selectSingleNode(modsDoc), uuid);
        Element locationEl = (Element) locationXpath.selectSingleNode(modsDoc);
        addRootPhysicalLocation(locationEl != null ? locationEl
                : ((Element) modsXpath.selectSingleNode(modsDoc))
                .addElement(new QName("location", Namespaces.mods)),
                true);

    }

    private void updateRecordInfo(Document modsDoc) {
        Element recordInfo = (Element) recordInfoXpath.selectSingleNode(modsDoc);
        if (recordInfo == null) {
            recordInfo = modsDoc.getRootElement().addElement(new QName("recordInfo", Namespaces.mods));
        }
        addRootRecordInfo(recordInfo);
    }

    private void addSysno(Document modsDoc, MarcSpecificMetadata marc) {
        String sysno = marc.getSysno();
        if (sysno != null) {
            Element modsEl = (Element) modsXpath.selectSingleNode(modsDoc);
            Element identifierEl = modsEl.addElement(new QName("identifier", Namespaces.mods));
            identifierEl.addAttribute("type", "sysno");
            identifierEl.addText(sysno);
        }
    }




    @Override
    protected void decorateDCStream() {
        updateDcDoc(getDcXmlContent(), getPid(), getSignature(), getSysno(), getModel());
        appendDatastream(Constants.DATASTREAM_CONTROLGROUP.X,
                Constants.DATASTREAM_ID.DC,
                getDcXmlContent().getRootElement(),
                null,
                null);
    }

    @SuppressWarnings("unchecked")
    private void updateDcDoc(Document dcDoc,
                             String pid,
                             String signature,
                             String sysno,
                             DigitalObjectModel model) {
        Element dcRootEl = dcDoc.getRootElement();
        Attribute schemaLoc = dcRootEl.attribute("schemaLocation");
        dcRootEl.remove(schemaLoc);
        Namespace xsi = DocumentHelper.createNamespace("xsi2", FedoraNamespaces.SCHEMA_NAMESPACE_URI);
        dcRootEl.add(xsi);
        dcRootEl.addAttribute(new QName("schemaLocation", xsi),
                "http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd");

        XPath typeXpath = Dom4jUtils.createXPath("/oai_dc:dc/dc:identifier");
        List<? extends Node> nodes = typeXpath.selectNodes(dcDoc);
        for (Node node : nodes) {
            node.detach();
        }
        Element idUuid = dcRootEl.addElement("dc:identifier");
        idUuid.addText(pid);

        for (Node node : nodes) {
            if (node.getText() != null && !"".equals(node.getText().trim())
                    && !node.getText().contains(Constants.FEDORA_UUID_PREFIX)) {
                Element temp = dcRootEl.addElement("dc:identifier");
                temp.addText(node.getText());
            }
        }

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
        typeEl.addText("model:" + model.getValue());
        Element rightsEl = dcRootEl.addElement("dc:rights");
        rightsEl.addText("policy:" + getPolicy().toString().toLowerCase());
        updateDcLanguages(dcDoc);
    }

    @SuppressWarnings("unchecked")
    private void removeDcTypeElements(Document doc) {
        XPath typeXpath = Dom4jUtils.createXPath("/oai_dc:dc/dc:type");
        List<? extends Node> nodes = typeXpath.selectNodes(doc);
        for (Node node : nodes) {
            node.detach();
        }
    }

    private void updateDcLanguages(Document dcDoc) {
        XPath languageXpath = Dom4jUtils.createXPath("/oai_dc:dc/dc:language");
        updateLanguages(dcDoc, languageXpath);
    }

    @Override
    protected void createOtherStreams() {
    }

    @Override
    protected DigitalObjectModel getModel() {
        return model;
    }

    @SuppressWarnings("unchecked")
    private void updateLanguages(Document doc, XPath xpath) {
        List<? extends Node> nodes = xpath.selectNodes(doc);
        for (Node languageNode : nodes) {
            Element languageEl = (Element) languageNode;
            String originalLang = languageEl.getTextTrim();
            languageEl.clearContent();
            languageEl.addText(transformLanguage(originalLang));
        }
    }
}
