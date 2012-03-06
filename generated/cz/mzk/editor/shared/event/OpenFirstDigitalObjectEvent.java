package cz.mzk.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class OpenFirstDigitalObjectEvent extends GwtEvent<OpenFirstDigitalObjectEvent.OpenFirstDigitalObjectHandler> { 

  public interface HasOpenFirstDigitalObjectHandlers extends HasHandlers {
    HandlerRegistration addOpenFirstDigitalObjectHandler(OpenFirstDigitalObjectHandler handler);
  }

  public interface OpenFirstDigitalObjectHandler extends EventHandler {
    public void onOpenFirstDigitalObject(OpenFirstDigitalObjectEvent event);
  }

  private static final Type<OpenFirstDigitalObjectHandler> TYPE = new Type<OpenFirstDigitalObjectHandler>();

  public static void fire(HasHandlers source, java.lang.String uuid, cz.mzk.editor.shared.rpc.StoredItem storedItem) {
    source.fireEvent(new OpenFirstDigitalObjectEvent(uuid, storedItem));
  }

  public static Type<OpenFirstDigitalObjectHandler> getType() {
    return TYPE;
  }

  private java.lang.String uuid;
  private cz.mzk.editor.shared.rpc.StoredItem storedItem;

  public OpenFirstDigitalObjectEvent(java.lang.String uuid, cz.mzk.editor.shared.rpc.StoredItem storedItem) {
    this.uuid = uuid;
    this.storedItem = storedItem;
  }

  protected OpenFirstDigitalObjectEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<OpenFirstDigitalObjectHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getUuid() {
    return uuid;
  }

  public cz.mzk.editor.shared.rpc.StoredItem getStoredItem() {
    return storedItem;
  }

  @Override
  protected void dispatch(OpenFirstDigitalObjectHandler handler) {
    handler.onOpenFirstDigitalObject(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    OpenFirstDigitalObjectEvent other = (OpenFirstDigitalObjectEvent) obj;
    if (uuid == null) {
      if (other.uuid != null)
        return false;
    } else if (!uuid.equals(other.uuid))
      return false;
    if (storedItem == null) {
      if (other.storedItem != null)
        return false;
    } else if (!storedItem.equals(other.storedItem))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (uuid == null ? 1 : uuid.hashCode());
    hashCode = (hashCode * 37) + (storedItem == null ? 1 : storedItem.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "OpenFirstDigitalObjectEvent["
                 + uuid
                 + ","
                 + storedItem
    + "]";
  }
}
