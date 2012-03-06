package cz.mzk.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class ConfigReceivedEvent extends GwtEvent<ConfigReceivedEvent.ConfigReceivedHandler> { 

  public interface HasConfigReceivedHandlers extends HasHandlers {
    HandlerRegistration addConfigReceivedHandler(ConfigReceivedHandler handler);
  }

  public interface ConfigReceivedHandler extends EventHandler {
    public void onConfigReceived(ConfigReceivedEvent event);
  }

  private static final Type<ConfigReceivedHandler> TYPE = new Type<ConfigReceivedHandler>();

  public static void fire(HasHandlers source, boolean statusOK) {
    source.fireEvent(new ConfigReceivedEvent(statusOK));
  }

  public static Type<ConfigReceivedHandler> getType() {
    return TYPE;
  }

  private boolean statusOK;

  public ConfigReceivedEvent(boolean statusOK) {
    this.statusOK = statusOK;
  }

  protected ConfigReceivedEvent() {
    // Possibly for serialization.
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
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    ConfigReceivedEvent other = (ConfigReceivedEvent) obj;
    if (statusOK != other.statusOK)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
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
