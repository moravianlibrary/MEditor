package cz.mzk.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class DigitalObjectOpenedEvent extends GwtEvent<DigitalObjectOpenedEvent.DigitalObjectOpenedHandler> { 

  public interface HasDigitalObjectOpenedHandlers extends HasHandlers {
    HandlerRegistration addDigitalObjectOpenedHandler(DigitalObjectOpenedHandler handler);
  }

  public interface DigitalObjectOpenedHandler extends EventHandler {
    public void onDigitalObjectOpened(DigitalObjectOpenedEvent event);
  }

  private static final Type<DigitalObjectOpenedHandler> TYPE = new Type<DigitalObjectOpenedHandler>();

  public static void fire(HasHandlers source, boolean statusOK, cz.mzk.editor.shared.rpc.RecentlyModifiedItem item, java.util.List<? extends java.util.List<java.lang.String>> related) {
    source.fireEvent(new DigitalObjectOpenedEvent(statusOK, item, related));
  }

  public static Type<DigitalObjectOpenedHandler> getType() {
    return TYPE;
  }

  private boolean statusOK;
  private cz.mzk.editor.shared.rpc.RecentlyModifiedItem item;
  private java.util.List<? extends java.util.List<java.lang.String>> related;

  public DigitalObjectOpenedEvent(boolean statusOK, cz.mzk.editor.shared.rpc.RecentlyModifiedItem item, java.util.List<? extends java.util.List<java.lang.String>> related) {
    this.statusOK = statusOK;
    this.item = item;
    this.related = related;
  }

  protected DigitalObjectOpenedEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<DigitalObjectOpenedHandler> getAssociatedType() {
    return TYPE;
  }

  public boolean isStatusOK() {
    return statusOK;
  }

  public cz.mzk.editor.shared.rpc.RecentlyModifiedItem getItem() {
    return item;
  }

  public java.util.List<? extends java.util.List<java.lang.String>> getRelated() {
    return related;
  }

  @Override
  protected void dispatch(DigitalObjectOpenedHandler handler) {
    handler.onDigitalObjectOpened(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    DigitalObjectOpenedEvent other = (DigitalObjectOpenedEvent) obj;
    if (statusOK != other.statusOK)
        return false;
    if (item == null) {
      if (other.item != null)
        return false;
    } else if (!item.equals(other.item))
      return false;
    if (related == null) {
      if (other.related != null)
        return false;
    } else if (!related.equals(other.related))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + new Boolean(statusOK).hashCode();
    hashCode = (hashCode * 37) + (item == null ? 1 : item.hashCode());
    hashCode = (hashCode * 37) + (related == null ? 1 : related.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "DigitalObjectOpenedEvent["
                 + statusOK
                 + ","
                 + item
                 + ","
                 + related
    + "]";
  }
}
