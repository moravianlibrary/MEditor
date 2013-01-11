package cz.mzk.editor.server.newObject;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.server.fedora.utils.FoxmlUtils;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.rpc.NewDigitalObject;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

/**
 * @author: Martin Rumanek
 * @version: 19.11.12
 */
public class SoundUnitBuilder extends FoxmlBuilder {

    private final DigitalObjectModel model;


    public SoundUnitBuilder(NewDigitalObject object) {
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

        Element genre = mods.addElement(new QName("genre", modsNs));
        genre.addAttribute("type", "model");
        genre.addText("side");

        Element relatedItem = mods.addElement(new QName("relatedItem", modsNs));
        Element relatedTitleInfo = relatedItem.addElement(new QName("titleinfo", modsNs));

        if (isNotNullOrEmpty(getNoteOrIntSubtitle())) {
            relatedTitleInfo.addElement(new QName("partNumber", modsNs)).addText(getNoteOrIntSubtitle());
        }

        if (isNotNullOrEmpty(getAditionalInfo())) {
            relatedTitleInfo.addElement(new QName("partName", modsNs)).addText(getPartNumber());
        }

        addIdentifierUuid(mods, getUuid());
        addRootRecordInfo(mods.addElement(new QName("recordInfo", modsNs)));

        appendDatastream(Constants.DATASTREAM_CONTROLGROUP.X, Constants.DATASTREAM_ID.BIBLIO_MODS, modsCollection, null, null);
    }

    @Override
    protected void createOtherStreams() {
    }

    @Override
    protected DigitalObjectModel getModel() {
        return model;
    }
}
