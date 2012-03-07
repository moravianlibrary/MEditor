package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetAllRequestItemsResult implements Result { 

  private java.util.ArrayList<cz.mzk.editor.common.RequestItem> items;

  public GetAllRequestItemsResult(java.util.ArrayList<cz.mzk.editor.common.RequestItem> items) {
    this.items = items;
  }

  protected GetAllRequestItemsResult() {
    // Possibly for serialization.
  }

  public java.util.ArrayList<cz.mzk.editor.common.RequestItem> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetAllRequestItemsResult other = (GetAllRequestItemsResult) obj;
    if (items == null) {
      if (other.items != null)
        return false;
    } else if (!items.equals(other.items))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (items == null ? 1 : items.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetAllRequestItemsResult["
                 + items
    + "]";
  }
}
