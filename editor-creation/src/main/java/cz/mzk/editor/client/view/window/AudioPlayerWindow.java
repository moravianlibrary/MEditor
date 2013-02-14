package cz.mzk.editor.client.view.window;

import com.google.web.bindery.event.shared.EventBus;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.HTMLPane;
import cz.mzk.editor.client.util.Constants;

/**
 * @author: Martin Rumanek
 * @version: 29.10.12
 */
public class AudioPlayerWindow extends UniversalWindow {

    private String hostname;

    public AudioPlayerWindow(EventBus eventBus, String uuid, String modelid, String hostname) {
        super(200, 400, "Audio player", eventBus, 50);
        this.hostname = hostname;
        setLayoutAlign(Alignment.CENTER);
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
        this.show();

    }

    private String sourceBuilder(String uuid, Constants.AUDIO_MIMETYPES mimetype) {
        StringBuilder sb = new StringBuilder("<source src=\"");
        sb.append(hostname).append("/").append("audio").append("/").append(uuid)
                .append(mimetype.getExtension()).append("\" type=\"")
                .append(mimetype.getMimeType()).append("\"></source>");
        return sb.toString();
    }
}
