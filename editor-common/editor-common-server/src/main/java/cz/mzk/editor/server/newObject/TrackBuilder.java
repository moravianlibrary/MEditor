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

    public TrackBuilder(NewDigitalObject object) {
        super(object);
        this.model = object.getModel();
    }

    @Override
    protected void decorateMODSStream() {
        Element modsCollection = FoxmlUtils.createModsCollectionEl();
        Namespace modsNs = Namespaces.mods;
        Element mods = modsCollection.addElement(new QName("mods", modsNs));
        mods.addAttribute("version", "3.3");
        mods.addAttribute("ID", "MODS_VOLUME_0001");
        Element idUrn = mods.addElement(new QName("identifier", modsNs));
        idUrn.addAttribute("type", "urn");
        idUrn.addText(getUuid());
        Element titleInfo = mods.addElement(new QName("titleInfo", modsNs));
        Element title = titleInfo.addElement(new QName("title", modsNs));
        title.addText(getLabel());

        if (isNotNullOrEmpty(getPartNumber())) {
            Element partNumber = titleInfo.addElement(new QName("partNumber", modsNs));
            partNumber.addText(getPartNumber());
        }

        Element typeOfResource = mods.addElement(new QName("typeOfResource", modsNs));
        typeOfResource.addText("sound recording");

        Element genre = typeOfResource.addElement(new QName("genre", modsNs));
        genre.addAttribute("type", "track");

        addRootLanguage(mods);

        Element physicalDescription = addRootPhysicalDescriptionForm(mods);






        appendDatastream(Constants.DATASTREAM_CONTROLGROUP.X, Constants.DATASTREAM_ID.BIBLIO_MODS, modsCollection, null, null);
    }

    @Override
    protected void decorateDCStream() {
        Element rootElement = DocumentHelper.createElement(new QName("dc", Namespaces.oai_dc));
        rootElement.add(Namespaces.dc);
        rootElement.add(Namespaces.xsi);
        Element title = rootElement.addElement(new QName("title", Namespaces.dc));
        title.addText(getLabel());
        Element identifier = rootElement.addElement(new QName("identifier", Namespaces.dc));
        identifier.setText(getPid());
        Element type = rootElement.addElement(new QName("type", Namespaces.dc));
        type.addText("model:" + model.getValue());
        Element rights = rootElement.addElement(new QName("rights", Namespaces.dc));
        rights.addText("policy:" + getPolicy().toString().toLowerCase());
        appendDatastream(Constants.DATASTREAM_CONTROLGROUP.X,
                Constants.DATASTREAM_ID.DC,
                getDcXmlContent().getRootElement(),
                null,
                null);
    }

    @Override
    protected void createOtherStreams() {
        appendDatastream(Constants.DATASTREAM_CONTROLGROUP.E, Constants.DATASTREAM_ID.WAV,
                null, "URL", "http://iris.mzk.cz/cache/audio/files/"+ this.getUuid() +".wav");
        appendDatastream(Constants.DATASTREAM_CONTROLGROUP.E, Constants.DATASTREAM_ID.MP3,
                null, "URL", "http://iris.mzk.cz/cache/audio/files/" + this.getUuid() + ".mp3");
        appendDatastream(Constants.DATASTREAM_CONTROLGROUP.E, Constants.DATASTREAM_ID.OGG,
                null, "URL", "http://iris.mzk.cz/cache/audio/files/" + this.getUuid() + ".ogg");



    }

    @Override
    protected DigitalObjectModel getModel() {
        return model;
    }
}
