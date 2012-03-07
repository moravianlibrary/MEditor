package cz.mzk.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class LoadStructureEvent extends GwtEvent<LoadStructureEvent.LoadStructureHandler> { 

  public interface HasLoadStructureHandlers extends HasHandlers {
    HandlerRegistration addLoadStructureHandler(LoadStructureHandler handler);
  }

  public interface LoadStructureHandler extends EventHandler {
    public void onLoadStructure(LoadStructureEvent event);
  }

  private static final Type<LoadStructureHandler> TYPE = new Type<LoadStructureHandler>();

  public static void fire(HasHandlers source, com.smartgwt.client.widgets.tree.TreeNode[] tree, com.smartgwt.client.data.Record[] pages) {
    source.fireEvent(new LoadStructureEvent(tree, pages));
  }

  public static Type<LoadStructureHandler> getType() {
    return TYPE;
  }

  private com.smartgwt.client.widgets.tree.TreeNode[] tree;
  private com.smartgwt.client.data.Record[] pages;

  public LoadStructureEvent(com.smartgwt.client.widgets.tree.TreeNode[] tree, com.smartgwt.client.data.Record[] pages) {
    this.tree = tree;
    this.pages = pages;
  }

  protected LoadStructureEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<LoadStructureHandler> getAssociatedType() {
    return TYPE;
  }

  public com.smartgwt.client.widgets.tree.TreeNode[] getTree() {
    return tree;
  }

  public com.smartgwt.client.data.Record[] getPages() {
    return pages;
  }

  @Override
  protected void dispatch(LoadStructureHandler handler) {
    handler.onLoadStructure(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    LoadStructureEvent other = (LoadStructureEvent) obj;
    if (tree == null) {
      if (other.tree != null)
        return false;
    } else if (!tree.equals(other.tree))
      return false;
    if (pages == null) {
      if (other.pages != null)
        return false;
    } else if (!pages.equals(other.pages))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + java.util.Arrays.deepHashCode(tree);
    hashCode = (hashCode * 37) + java.util.Arrays.deepHashCode(pages);
    return hashCode;
  }

  @Override
  public String toString() {
    return "LoadStructureEvent["
                 + tree
                 + ","
                 + pages
    + "]";
  }
}
