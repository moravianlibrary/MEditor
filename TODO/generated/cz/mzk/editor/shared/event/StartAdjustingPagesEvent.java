package cz.mzk.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class StartAdjustingPagesEvent extends GwtEvent<StartAdjustingPagesEvent.StartAdjustingPagesHandler> { 

  public interface HasStartAdjustingPagesHandlers extends HasHandlers {
    HandlerRegistration addStartAdjustingPagesHandler(StartAdjustingPagesHandler handler);
  }

  public interface StartAdjustingPagesHandler extends EventHandler {
    public void onStartAdjustingPages(StartAdjustingPagesEvent event);
  }

  private static final Type<StartAdjustingPagesHandler> TYPE = new Type<StartAdjustingPagesHandler>();

  public static void fire(HasHandlers source, java.lang.String path, cz.mzk.editor.shared.rpc.DublinCore dc) {
    source.fireEvent(new StartAdjustingPagesEvent(path, dc));
  }

  public static void fire(HasHandlers source, java.lang.String path) {
    source.fireEvent(new StartAdjustingPagesEvent(path));
  }

  public static Type<StartAdjustingPagesHandler> getType() {
    return TYPE;
  }

  private java.lang.String path;
  private cz.mzk.editor.shared.rpc.DublinCore dc;

  public StartAdjustingPagesEvent(java.lang.String path, cz.mzk.editor.shared.rpc.DublinCore dc) {
    this.path = path;
    this.dc = dc;
  }

  public StartAdjustingPagesEvent(java.lang.String path) {
    this.path = path;
  }

  protected StartAdjustingPagesEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<StartAdjustingPagesHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getPath() {
    return path;
  }

  public cz.mzk.editor.shared.rpc.DublinCore getDc() {
    return dc;
  }

  @Override
  protected void dispatch(StartAdjustingPagesHandler handler) {
    handler.onStartAdjustingPages(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    StartAdjustingPagesEvent other = (StartAdjustingPagesEvent) obj;
    if (path == null) {
      if (other.path != null)
        return false;
    } else if (!path.equals(other.path))
      return false;
    if (dc == null) {
      if (other.dc != null)
        return false;
    } else if (!dc.equals(other.dc))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (path == null ? 1 : path.hashCode());
    hashCode = (hashCode * 37) + (dc == null ? 1 : dc.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "StartAdjustingPagesEvent["
                 + path
                 + ","
                 + dc
    + "]";
  }
}
