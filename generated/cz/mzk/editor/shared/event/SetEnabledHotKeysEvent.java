package cz.mzk.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class SetEnabledHotKeysEvent extends GwtEvent<SetEnabledHotKeysEvent.SetEnabledHotKeysHandler> { 

  public interface HasSetEnabledHotKeysHandlers extends HasHandlers {
    HandlerRegistration addSetEnabledHotKeysHandler(SetEnabledHotKeysHandler handler);
  }

  public interface SetEnabledHotKeysHandler extends EventHandler {
    public void onSetEnabledHotKeys(SetEnabledHotKeysEvent event);
  }

  private static final Type<SetEnabledHotKeysHandler> TYPE = new Type<SetEnabledHotKeysHandler>();

  public static void fire(HasHandlers source, boolean enable) {
    source.fireEvent(new SetEnabledHotKeysEvent(enable));
  }

  public static Type<SetEnabledHotKeysHandler> getType() {
    return TYPE;
  }

  private boolean enable;

  public SetEnabledHotKeysEvent(boolean enable) {
    this.enable = enable;
  }

  protected SetEnabledHotKeysEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<SetEnabledHotKeysHandler> getAssociatedType() {
    return TYPE;
  }

  public boolean isEnable() {
    return enable;
  }

  @Override
  protected void dispatch(SetEnabledHotKeysHandler handler) {
    handler.onSetEnabledHotKeys(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    SetEnabledHotKeysEvent other = (SetEnabledHotKeysEvent) obj;
    if (enable != other.enable)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + new Boolean(enable).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "SetEnabledHotKeysEvent["
                 + enable
    + "]";
  }
}
