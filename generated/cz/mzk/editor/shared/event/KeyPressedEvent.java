package cz.mzk.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class KeyPressedEvent extends GwtEvent<KeyPressedEvent.KeyPressedHandler> { 

  public interface HasKeyPressedHandlers extends HasHandlers {
    HandlerRegistration addKeyPressedHandler(KeyPressedHandler handler);
  }

  public interface KeyPressedHandler extends EventHandler {
    public void onKeyPressed(KeyPressedEvent event);
  }

  private static final Type<KeyPressedHandler> TYPE = new Type<KeyPressedHandler>();

  public static void fire(HasHandlers source, int code) {
    source.fireEvent(new KeyPressedEvent(code));
  }

  public static Type<KeyPressedHandler> getType() {
    return TYPE;
  }

  private int code;

  public KeyPressedEvent(int code) {
    this.code = code;
  }

  protected KeyPressedEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<KeyPressedHandler> getAssociatedType() {
    return TYPE;
  }

  public int getCode() {
    return code;
  }

  @Override
  protected void dispatch(KeyPressedHandler handler) {
    handler.onKeyPressed(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    KeyPressedEvent other = (KeyPressedEvent) obj;
    if (code != other.code)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + new Integer(code).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "KeyPressedEvent["
                 + code
    + "]";
  }
}
