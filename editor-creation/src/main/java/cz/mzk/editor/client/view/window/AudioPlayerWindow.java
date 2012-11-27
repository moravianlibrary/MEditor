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

    public AudioPlayerWindow(EventBus eventBus, String uuid, String modelid) {
        super(200, 400, "Audio player", eventBus, 50);
        setLayoutAlign(Alignment.CENTER);
        Label label = new Label(uuid);

        this.setWidth(400);
        this.setHeight(300);
        this.setIsModal(true);
        this.setShowModalMask(true);
        this.centerInPage();
        HTMLPane audioPane = new HTMLPane();
        audioPane.setPadding(15);
        audioPane.setContents("<audio controls>"
                + sourceBuilder(uuid, Constants.AUDIO_MIMETYPES.OGG_MIMETYPE)
                + sourceBuilder(uuid, Constants.AUDIO_MIMETYPES.MP3_MIMETYPE)
                + sourceBuilder(uuid, Constants.AUDIO_MIMETYPES.WAV_MIMETYPE)
                + "</audio>"
        );


        this.addItem(audioPane);
        this.addItem(label);
        this.show();

    }

    private String sourceBuilder(String uuid, Constants.AUDIO_MIMETYPES mimetype) {
        StringBuilder sb = new StringBuilder("<source src=\"");
        sb.append("http://localhost/").append("audio/").append(uuid)
                .append(mimetype.getExtension()).append("\" type=\"")
                .append(mimetype.getMimeType()).append("\"></source>");
        return sb.toString();
    }
}
