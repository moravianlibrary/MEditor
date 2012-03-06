package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class PutRecentlyModifiedAction implements Action<PutRecentlyModifiedResult> { 

  private cz.mzk.editor.shared.rpc.RecentlyModifiedItem item;

  public PutRecentlyModifiedAction(cz.mzk.editor.shared.rpc.RecentlyModifiedItem item) {
    this.item = item;
  }

  protected PutRecentlyModifiedAction() {
    // Possibly for serialization.
  }

  public cz.mzk.editor.shared.rpc.RecentlyModifiedItem getItem() {
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
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    PutRecentlyModifiedAction other = (PutRecentlyModifiedAction) obj;
    if (item == null) {
      if (other.item != null)
        return false;
    } else if (!item.equals(other.item))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
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
