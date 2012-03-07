package cz.mzk.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class ChangeMenuWidthEvent extends GwtEvent<ChangeMenuWidthEvent.ChangeMenuWidthHandler> { 

  public interface HasChangeMenuWidthHandlers extends HasHandlers {
    HandlerRegistration addChangeMenuWidthHandler(ChangeMenuWidthHandler handler);
  }

  public interface ChangeMenuWidthHandler extends EventHandler {
    public void onChangeMenuWidth(ChangeMenuWidthEvent event);
  }

  private static final Type<ChangeMenuWidthHandler> TYPE = new Type<ChangeMenuWidthHandler>();

  public static void fire(HasHandlers source, java.lang.String width) {
    source.fireEvent(new ChangeMenuWidthEvent(width));
  }

  public static Type<ChangeMenuWidthHandler> getType() {
    return TYPE;
  }

  private java.lang.String width;

  public ChangeMenuWidthEvent(java.lang.String width) {
    this.width = width;
  }

  protected ChangeMenuWidthEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<ChangeMenuWidthHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getWidth() {
    return width;
  }

  @Override
  protected void dispatch(ChangeMenuWidthHandler handler) {
    handler.onChangeMenuWidth(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    ChangeMenuWidthEvent other = (ChangeMenuWidthEvent) obj;
    if (width == null) {
      if (other.width != null)
        return false;
    } else if (!width.equals(other.width))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (width == null ? 1 : width.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "ChangeMenuWidthEvent["
                 + width
    + "]";
  }
}
