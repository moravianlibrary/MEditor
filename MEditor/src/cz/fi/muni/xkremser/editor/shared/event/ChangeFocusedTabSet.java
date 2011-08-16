
package cz.fi.muni.xkremser.editor.shared.event;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class ChangeFocusedTabSet {

    @Order(1)
    String focusedUuid;
}
