
package cz.fi.muni.xkremser.editor.shared.event;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

import cz.fi.muni.xkremser.editor.client.util.Constants;

@GenEvent
@SuppressWarnings("unused")
public class RefreshTree {

    @Order(1)
    private Constants.NAME_OF_TREE tree;

}
