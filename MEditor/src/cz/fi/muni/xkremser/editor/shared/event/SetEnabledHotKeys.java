
package cz.fi.muni.xkremser.editor.shared.event;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
@SuppressWarnings("unused")
public class SetEnabledHotKeys {

    @Order(1)
    private boolean enable;
}
