package cz.mzk.editor.client.view.window;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import cz.mzk.editor.client.LangConstants;

/**
 * @author: Martin Rumanek
 * @version: 18.2.13
 */
public class AddOcrWindow extends UniversalWindow {

    private final LangConstants lang;

    public AddOcrWindow(LangConstants lang, DispatchAsync dispatcher, EventBus eventBus) {
        super(600, 400, lang.addOcr(), eventBus, 60);
        this.lang = lang;

    }
}
