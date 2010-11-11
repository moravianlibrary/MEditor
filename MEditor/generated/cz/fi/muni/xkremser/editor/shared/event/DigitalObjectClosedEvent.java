package cz.fi.muni.xkremser.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.gwtplatform.mvp.client.HasEventBus;
import com.google.gwt.event.shared.HasHandlers;

public class DigitalObjectClosedEvent extends GwtEvent<DigitalObjectClosedEvent.DigitalObjectClosedHandler> { 

  public interface HasDigitalObjectClosedHandlers extends HasHandlers {
    HandlerRegistration addDigitalObjectClosedHandler(DigitalObjectClosedHandler handler);
  }

  public interface DigitalObjectClosedHandler extends EventHandler {
    public void onDigitalObjectClosed(DigitalObjectClosedEvent event);
  }

  private static final Type<DigitalObjectClosedHandler> TYPE = new Type<DigitalObjectClosedHandler>();

  public static void fire(HasEventBus source, java.lang.String uuid) {
    source.fireEvent(new DigitalObjectClosedEvent(uuid));
  }

  public static Type<DigitalObjectClosedHandler> getType() {
    return TYPE;
  }

  private final java.lang.String uuid;

  public DigitalObjectClosedEvent(java.lang.String uuid) {
    this.uuid = uuid;
  }

  @Override
  public Type<DigitalObjectClosedHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getUuid() {
    return uuid;
  }

  @Override
  protected void dispatch(DigitalObjectClosedHandler handler) {
    handler.onDigitalObjectClosed(this);
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          DigitalObjectClosedEvent o = (DigitalObjectClosedEvent) other;
      return true
          && ((o.uuid == null && this.uuid == null) || (o.uuid != null && o.uuid.equals(this.uuid)))
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + (uuid == null ? 1 : uuid.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "DigitalObjectClosedEvent["
                 + uuid
    + "]";
  }

}
