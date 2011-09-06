
package cz.fi.muni.xkremser.editor.client.gwtrpcds;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.UiHandlers;

public abstract class AbstractGwtRPCDSWithUiHandlers<C extends UiHandlers>
        extends AbstractGwtRPCDS
        implements HasUiHandlers<C> {

    private C uiHandlers;

    protected C getUiHandlers() {
        return uiHandlers;
    }

    @Override
    public void setUiHandlers(C uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

}
