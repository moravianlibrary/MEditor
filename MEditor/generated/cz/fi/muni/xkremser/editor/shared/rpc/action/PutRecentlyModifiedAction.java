package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class PutRecentlyModifiedAction implements Action<PutRecentlyModifiedResult> { 
  private cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem item;

  protected PutRecentlyModifiedAction() { }

  public PutRecentlyModifiedAction(cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem item) { 
    this.item = item;
  }

  public cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem getItem() {
    return item;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "PutRecentlyModified";
  }

  @Override
  public boolean isSecured() {
    return false;
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          PutRecentlyModifiedAction o = (PutRecentlyModifiedAction) other;
      return true
          && ((o.item == null && this.item == null) || (o.item != null && o.item.equals(this.item)))
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + (item == null ? 1 : item.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "PutRecentlyModifiedAction["
                 + item
    + "]";
  }

}
