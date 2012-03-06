package cz.mzk.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class SaveStructureEvent extends GwtEvent<SaveStructureEvent.SaveStructureHandler> { 

  public interface HasSaveStructureHandlers extends HasHandlers {
    HandlerRegistration addSaveStructureHandler(SaveStructureHandler handler);
  }

  public interface SaveStructureHandler extends EventHandler {
    public void onSaveStructure(SaveStructureEvent event);
  }

  private static final Type<SaveStructureHandler> TYPE = new Type<SaveStructureHandler>();

  public static void fire(HasHandlers source) {
    source.fireEvent(new SaveStructureEvent());
  }

  public static Type<SaveStructureHandler> getType() {
    return TYPE;
  }


  public SaveStructureEvent() {
  }

  @Override
  public Type<SaveStructureHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(SaveStructureHandler handler) {
    handler.onSaveStructure(this);
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "SaveStructureEvent["
    + "]";
  }
}
