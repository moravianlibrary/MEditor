package cz.mzk.editor.server.newObject;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.rpc.NewDigitalObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author: Martin Rumanek
 * @version: 17.11.12
 */
public class SoundRecordingBuilder extends FoxmlBuilder {

    final Logger logger = Logger.getLogger(MonographBuilder.class.getName());

    private final DigitalObjectModel model;

    public SoundRecordingBuilder(NewDigitalObject object) {
        super(object);
        this.model = object.getModel();
    }

    @Override
    protected void decorateMODSStream() {
        if (getBundle() != null && getBundle().getMarc() != null) {
            //getBundle().getMarc().
            //updateModsDoc(getModsXmlContent(), getBundle().getMarc(), getUuid());
        }

    }

//    @Override
//    protected void decorateDCStream() {
//        updateDcDoc(getDcXmlContent(), getPid(), getSignature(), getSysno(), getModel());
//        /*appendDatastream(Constants.DATASTREAM_CONTROLGROUP.X,
//                Constants.DATASTREAM_ID.DC,
//                getDcXmlContent().getRootElement(),
//                null,
//                null);*/
//    }

    @SuppressWarnings("unchecked")
    private void updateDcDoc(Document dcDoc,
                             String pid,
                             String signature,
                             String sysno,
                             DigitalObjectModel model) {
        Element dcRootEl = dcDoc.getRootElement();


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
