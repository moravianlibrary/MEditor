package cz.mzk.editor.client.view.window;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.google.gwt.user.client.Window.Location;
import cz.mzk.editor.client.util.Constants;

/**
 * @author: Martin Rumanek
 * @version: 29.10.12
 */
public class AudioPlayerWindow extends UniversalWindow {

    public AudioPlayerWindow(EventBus eventBus, String mimeType, String uuid, String modelid) {
        super(200, 400, "Audio player", eventBus, 50);
        setLayoutAlign(Alignment.CENTER);

        this.setWidth(400);
        this.setHeight(300);
        this.setIsModal(true);
        this.setShowModalMask(true);
        this.centerInPage();
        HTMLPane audioPane = new HTMLPane();
        audioPane.setPadding(15);
        Constants.AUDIO_MIMETYPES mim = Constants.AUDIO_MIMETYPES.findByMimetype(mimeType);
        SC.say(Location.getPath() + "audio/"
                + uuid + mim.getExtension());
        audioPane.setContents("<audio controls>"
                + "<source src=\"" + Location.getPath() + "audio/"
                + uuid + mim.getExtension() + "\" type=\"" +
                mim.getMimeType() + "\">" + "</source></audio>"
        );


        this.addItem(audioPane);

        this.show();

    }
}
