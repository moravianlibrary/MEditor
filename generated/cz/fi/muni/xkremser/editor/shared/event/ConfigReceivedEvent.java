package cz.fi.muni.xkremser.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.gwtplatform.mvp.client.HasEventBus;
import com.google.gwt.event.shared.HasHandlers;

public class ConfigReceivedEvent extends GwtEvent<ConfigReceivedEvent.ConfigReceivedHandler> { 

  public interface HasConfigReceivedHandlers extends HasHandlers {
    HandlerRegistration addConfigReceivedHandler(ConfigReceivedHandler handler);
  }

  public interface ConfigReceivedHandler extends EventHandler {
    public void onConfigReceived(ConfigReceivedEvent event);
  }

  private static final Type<ConfigReceivedHandler> TYPE = new Type<ConfigReceivedHandler>();

  public static void fire(HasEventBus source, boolean statusOK) {
    source.fireEvent(new ConfigReceivedEvent(statusOK));
  }

  public static Type<ConfigReceivedHandler> getType() {
    return TYPE;
  }

  private boolean statusOK;

  public ConfigReceivedEvent(boolean statusOK) {
    this.statusOK = statusOK;
  }

  @Override
  public Type<ConfigReceivedHandler> getAssociatedType() {
    return TYPE;
  }

  public boolean isStatusOK() {
    return statusOK;
  }

  @Override
  protected void dispatch(ConfigReceivedHandler handler) {
    handler.onConfigReceived(this);
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          ConfigReceivedEvent o = (ConfigReceivedEvent) other;
      return true
          && o.statusOK == this.statusOK
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + new Boolean(statusOK).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "ConfigReceivedEvent["
                 + statusOK
    + "]";
  }

}
