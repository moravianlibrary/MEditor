package cz.mzk.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class DigitalObjectClosedEvent extends GwtEvent<DigitalObjectClosedEvent.DigitalObjectClosedHandler> { 

  public interface HasDigitalObjectClosedHandlers extends HasHandlers {
    HandlerRegistration addDigitalObjectClosedHandler(DigitalObjectClosedHandler handler);
  }

  public interface DigitalObjectClosedHandler extends EventHandler {
    public void onDigitalObjectClosed(DigitalObjectClosedEvent event);
  }

  private static final Type<DigitalObjectClosedHandler> TYPE = new Type<DigitalObjectClosedHandler>();

  public static void fire(HasHandlers source, java.lang.String uuid) {
    source.fireEvent(new DigitalObjectClosedEvent(uuid));
  }

  public static Type<DigitalObjectClosedHandler> getType() {
    return TYPE;
  }

  private java.lang.String uuid;

  public DigitalObjectClosedEvent(java.lang.String uuid) {
    this.uuid = uuid;
  }

  protected DigitalObjectClosedEvent() {
    // Possibly for serialization.
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
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    DigitalObjectClosedEvent other = (DigitalObjectClosedEvent) obj;
    if (uuid == null) {
      if (other.uuid != null)
        return false;
    } else if (!uuid.equals(other.uuid))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
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
