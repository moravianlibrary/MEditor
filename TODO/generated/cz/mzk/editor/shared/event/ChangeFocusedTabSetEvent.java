package cz.mzk.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class ChangeFocusedTabSetEvent extends GwtEvent<ChangeFocusedTabSetEvent.ChangeFocusedTabSetHandler> { 

  public interface HasChangeFocusedTabSetHandlers extends HasHandlers {
    HandlerRegistration addChangeFocusedTabSetHandler(ChangeFocusedTabSetHandler handler);
  }

  public interface ChangeFocusedTabSetHandler extends EventHandler {
    public void onChangeFocusedTabSet(ChangeFocusedTabSetEvent event);
  }

  private static final Type<ChangeFocusedTabSetHandler> TYPE = new Type<ChangeFocusedTabSetHandler>();

  public static void fire(HasHandlers source, java.lang.String focusedUuid) {
    source.fireEvent(new ChangeFocusedTabSetEvent(focusedUuid));
  }

  public static Type<ChangeFocusedTabSetHandler> getType() {
    return TYPE;
  }

  private java.lang.String focusedUuid;

  public ChangeFocusedTabSetEvent(java.lang.String focusedUuid) {
    this.focusedUuid = focusedUuid;
  }

  protected ChangeFocusedTabSetEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<ChangeFocusedTabSetHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getFocusedUuid() {
    return focusedUuid;
  }

  @Override
  protected void dispatch(ChangeFocusedTabSetHandler handler) {
    handler.onChangeFocusedTabSet(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    ChangeFocusedTabSetEvent other = (ChangeFocusedTabSetEvent) obj;
    if (focusedUuid == null) {
      if (other.focusedUuid != null)
        return false;
    } else if (!focusedUuid.equals(other.focusedUuid))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (focusedUuid == null ? 1 : focusedUuid.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "ChangeFocusedTabSetEvent["
                 + focusedUuid
    + "]";
  }
}
