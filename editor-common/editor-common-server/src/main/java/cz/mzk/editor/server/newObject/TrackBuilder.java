package cz.mzk.editor.server.newObject;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.server.fedora.utils.DCUtils;
import cz.mzk.editor.server.fedora.utils.Dom4jUtils;
import cz.mzk.editor.server.fedora.utils.FoxmlUtils;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.domain.FedoraNamespaces;
import cz.mzk.editor.shared.rpc.NewDigitalObject;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.XPath;

import java.util.List;

/**
 * @author: Martin Rumanek
 * @version: 19.11.12
 */
public class TrackBuilder extends FoxmlBuilder {

    private final DigitalObjectModel model;
    private boolean wavProvided = false;

    public TrackBuilder(NewDigitalObject object) {
        super(object);
        this.model = object.getModel();
    }

    @Override
    protected void decorateMODSStream() {
        Element modsCollection = FoxmlUtils.createModsCollectionEl();
        Namespace modsNs = Namespaces.mods;
        Element mods = modsCollection.addElement(new QName("mods", modsNs));
        mods.addAttribute("version", "3.4");
        mods.addAttribute("ID", getAditionalInfo());
        Element idUrn = mods.addElement(new QName("identifier", modsNs));
        idUrn.addAttribute("type", "uuid");
        idUrn.addText(getUuid());

        Element titleInfo = mods.addElement(new QName("titleInfo", modsNs));
        addRootTitle(titleInfo.addElement(new QName("title", modsNs)));

        if (isNotNullOrEmpty(getPartNumber())) {
            Element partNumber = titleInfo.addElement(new QName("partNumber", modsNs));
            partNumber.addText(getPartNumber());
        }
        if (isNotNullOrEmpty(getName())) {
            Element partName = titleInfo.addElement(new QName("partName", modsNs));
            partName.addText(getName());
        }

        addRootLanguage(mods);

        if (isNotNullOrEmpty(getTypeOfResource())) {
            Element typeOfResourceEl = mods.addElement(new QName("typeOfResource", modsNs));
            typeOfResourceEl.addText(getTypeOfResource());
        }

        Element genre = mods.addElement(new QName("genre", modsNs));
        genre.addAttribute("type", "model");
        genre.addText("track");
        addIdentifierUuid(mods, getUuid());
        addRootRecordInfo(mods);


        appendDatastream(Constants.DATASTREAM_CONTROLGROUP.X, Constants.DATASTREAM_ID.BIBLIO_MODS, modsCollection, null, null);
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

        Element typeEl = dcRootEl.addElement("dc:type");
        typeEl.addText("model:" + model.getValue());
        Element rightsEl = dcRootEl.addElement("dc:rights");
        rightsEl.addText("policy:" + getPolicy().toString().toLowerCase());
    }

    @Override
    protected void createOtherStreams() {
        appendDatastream(Constants.DATASTREAM_CONTROLGROUP.E, Constants.DATASTREAM_ID.WAV,
                null, "URL", getImageUrl() +".wav");
        appendDatastream(Constants.DATASTREAM_CONTROLGROUP.E, Constants.DATASTREAM_ID.MP3,
                null, "URL", getImageUrl() + ".mp3");
        appendDatastream(Constants.DATASTREAM_CONTROLGROUP.E, Constants.DATASTREAM_ID.OGG,
                null, "URL", getImageUrl() + ".ogg");
    }

    public void wavProvided(boolean wavProvided) {
        this.wavProvided = wavProvided;
    }

    @Override
    protected DigitalObjectModel getModel() {
        return model;
    }
}
