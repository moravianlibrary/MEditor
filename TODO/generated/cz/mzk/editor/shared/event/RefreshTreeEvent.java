package cz.mzk.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class RefreshTreeEvent extends GwtEvent<RefreshTreeEvent.RefreshTreeHandler> { 

  public interface HasRefreshTreeHandlers extends HasHandlers {
    HandlerRegistration addRefreshTreeHandler(RefreshTreeHandler handler);
  }

  public interface RefreshTreeHandler extends EventHandler {
    public void onRefreshTree(RefreshTreeEvent event);
  }

  private static final Type<RefreshTreeHandler> TYPE = new Type<RefreshTreeHandler>();

  public static void fire(HasHandlers source, cz.mzk.editor.client.util.Constants.NAME_OF_TREE tree) {
    source.fireEvent(new RefreshTreeEvent(tree));
  }

  public static Type<RefreshTreeHandler> getType() {
    return TYPE;
  }

  private cz.mzk.editor.client.util.Constants.NAME_OF_TREE tree;

  public RefreshTreeEvent(cz.mzk.editor.client.util.Constants.NAME_OF_TREE tree) {
    this.tree = tree;
  }

  protected RefreshTreeEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<RefreshTreeHandler> getAssociatedType() {
    return TYPE;
  }

  public cz.mzk.editor.client.util.Constants.NAME_OF_TREE getTree() {
    return tree;
  }

  @Override
  protected void dispatch(RefreshTreeHandler handler) {
    handler.onRefreshTree(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    RefreshTreeEvent other = (RefreshTreeEvent) obj;
    if (tree == null) {
      if (other.tree != null)
        return false;
    } else if (!tree.equals(other.tree))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (tree == null ? 1 : tree.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "RefreshTreeEvent["
                 + tree
    + "]";
  }
}
