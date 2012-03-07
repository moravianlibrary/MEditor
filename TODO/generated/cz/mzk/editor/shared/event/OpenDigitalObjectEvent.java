package cz.mzk.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class OpenDigitalObjectEvent extends GwtEvent<OpenDigitalObjectEvent.OpenDigitalObjectHandler> { 

  public interface HasOpenDigitalObjectHandlers extends HasHandlers {
    HandlerRegistration addOpenDigitalObjectHandler(OpenDigitalObjectHandler handler);
  }

  public interface OpenDigitalObjectHandler extends EventHandler {
    public void onOpenDigitalObject(OpenDigitalObjectEvent event);
  }

  private static final Type<OpenDigitalObjectHandler> TYPE = new Type<OpenDigitalObjectHandler>();

  public static void fire(HasHandlers source, java.lang.String uuid, cz.mzk.editor.shared.rpc.StoredItem storedItem) {
    source.fireEvent(new OpenDigitalObjectEvent(uuid, storedItem));
  }

  public static Type<OpenDigitalObjectHandler> getType() {
    return TYPE;
  }

  private java.lang.String uuid;
  private cz.mzk.editor.shared.rpc.StoredItem storedItem;

  public OpenDigitalObjectEvent(java.lang.String uuid, cz.mzk.editor.shared.rpc.StoredItem storedItem) {
    this.uuid = uuid;
    this.storedItem = storedItem;
  }

  protected OpenDigitalObjectEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<OpenDigitalObjectHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getUuid() {
    return uuid;
  }

  public cz.mzk.editor.shared.rpc.StoredItem getStoredItem() {
    return storedItem;
  }

  @Override
  protected void dispatch(OpenDigitalObjectHandler handler) {
    handler.onOpenDigitalObject(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    OpenDigitalObjectEvent other = (OpenDigitalObjectEvent) obj;
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
    return "OpenDigitalObjectEvent["
                 + uuid
                 + ","
                 + storedItem
    + "]";
  }
}
