
package cz.fi.muni.xkremser.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.smartgwt.client.event.Cancellable;

public class EscKeyPressedEvent
        extends GwtEvent<EscKeyPressedEvent.EscKeyPressedHandler>
        implements Cancellable {

    private boolean cancel = false;

    public interface HasEscKeyPressedHandlers
            extends HasHandlers {

        HandlerRegistration addEscKeyPressedHandler(EscKeyPressedHandler handler);

    }

    public interface EscKeyPressedHandler
            extends EventHandler {

        public void onEscKeyPressed(EscKeyPressedEvent event);
    }

    private static final Type<EscKeyPressedHandler> TYPE = new Type<EscKeyPressedHandler>();

    public static void fire(HasHandlers source) {
        source.fireEvent(new EscKeyPressedEvent());
    }

    public static Type<EscKeyPressedHandler> getType() {
        return TYPE;
    }

    public EscKeyPressedEvent() {
    }

    @Override
    public Type<EscKeyPressedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EscKeyPressedHandler handler) {
        handler.onEscKeyPressed(this);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "EscKeyPressedEvent[" + "]";
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void cancel() {
        cancel = true;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isCancelled() {
        return cancel;
    }

}
