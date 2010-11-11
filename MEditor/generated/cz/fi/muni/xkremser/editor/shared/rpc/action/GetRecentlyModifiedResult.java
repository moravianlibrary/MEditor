package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetRecentlyModifiedResult implements Result { 
  private java.util.ArrayList<cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem> items;

  protected GetRecentlyModifiedResult() { }

  public GetRecentlyModifiedResult(java.util.ArrayList<cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem> items) { 
    this.items = items;
  }

  public java.util.ArrayList<cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          GetRecentlyModifiedResult o = (GetRecentlyModifiedResult) other;
      return true
          && ((o.items == null && this.items == null) || (o.items != null && o.items.equals(this.items)))
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + (items == null ? 1 : items.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetRecentlyModifiedResult["
                 + items
    + "]";
  }

}
