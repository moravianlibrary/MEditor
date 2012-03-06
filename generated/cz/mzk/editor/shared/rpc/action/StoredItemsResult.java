package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class StoredItemsResult implements Result { 

  private java.util.List<cz.mzk.editor.shared.rpc.StoredItem> storedItems;

  public StoredItemsResult(java.util.List<cz.mzk.editor.shared.rpc.StoredItem> storedItems) {
    this.storedItems = storedItems;
  }

  protected StoredItemsResult() {
    // Possibly for serialization.
  }

  public java.util.List<cz.mzk.editor.shared.rpc.StoredItem> getStoredItems() {
    return storedItems;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    StoredItemsResult other = (StoredItemsResult) obj;
    if (storedItems == null) {
      if (other.storedItems != null)
        return false;
    } else if (!storedItems.equals(other.storedItems))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (storedItems == null ? 1 : storedItems.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "StoredItemsResult["
                 + storedItems
    + "]";
  }
}
