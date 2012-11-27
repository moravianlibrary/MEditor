package cz.mzk.editor.server.newObject;

import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.rpc.NewDigitalObject;

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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void createOtherStreams() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected DigitalObjectModel getModel() {
        return model;
    }
}
