package cz.fi.muni.xkremser.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.gwtplatform.mvp.client.HasEventBus;
import com.google.gwt.event.shared.HasHandlers;

public class DigitalObjectOpenedEvent extends GwtEvent<DigitalObjectOpenedEvent.DigitalObjectOpenedHandler> { 

  public interface HasDigitalObjectOpenedHandlers extends HasHandlers {
    HandlerRegistration addDigitalObjectOpenedHandler(DigitalObjectOpenedHandler handler);
  }

  public interface DigitalObjectOpenedHandler extends EventHandler {
    public void onDigitalObjectOpened(DigitalObjectOpenedEvent event);
  }

  private static final Type<DigitalObjectOpenedHandler> TYPE = new Type<DigitalObjectOpenedHandler>();

  public static void fire(HasEventBus source, boolean statusOK, cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem item, java.util.List<? extends java.util.List<java.lang.String>> related) {
    source.fireEvent(new DigitalObjectOpenedEvent(statusOK, item, related));
  }

  public static Type<DigitalObjectOpenedHandler> getType() {
    return TYPE;
  }

  private boolean statusOK;
  private final cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem item;
  private final java.util.List<? extends java.util.List<java.lang.String>> related;

  public DigitalObjectOpenedEvent(boolean statusOK, cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem item, java.util.List<? extends java.util.List<java.lang.String>> related) {
    this.statusOK = statusOK;
    this.item = item;
    this.related = related;
  }

  @Override
  public Type<DigitalObjectOpenedHandler> getAssociatedType() {
    return TYPE;
  }

  public boolean isStatusOK() {
    return statusOK;
  }

  public cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem getItem() {
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
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          DigitalObjectOpenedEvent o = (DigitalObjectOpenedEvent) other;
      return true
          && o.statusOK == this.statusOK
          && ((o.item == null && this.item == null) || (o.item != null && o.item.equals(this.item)))
          && ((o.related == null && this.related == null) || (o.related != null && o.related.equals(this.related)))
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
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
