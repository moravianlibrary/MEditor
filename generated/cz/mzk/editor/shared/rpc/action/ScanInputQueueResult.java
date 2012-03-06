package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class ScanInputQueueResult implements Result { 

  private java.util.ArrayList<cz.mzk.editor.shared.rpc.InputQueueItem> items;
  private cz.mzk.editor.shared.rpc.ServerActionResult serverActionResult;

  public ScanInputQueueResult(java.util.ArrayList<cz.mzk.editor.shared.rpc.InputQueueItem> items, cz.mzk.editor.shared.rpc.ServerActionResult serverActionResult) {
    this.items = items;
    this.serverActionResult = serverActionResult;
  }

  protected ScanInputQueueResult() {
    // Possibly for serialization.
  }

  public java.util.ArrayList<cz.mzk.editor.shared.rpc.InputQueueItem> getItems() {
    return items;
  }

  public cz.mzk.editor.shared.rpc.ServerActionResult getServerActionResult() {
    return serverActionResult;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    ScanInputQueueResult other = (ScanInputQueueResult) obj;
    if (items == null) {
      if (other.items != null)
        return false;
    } else if (!items.equals(other.items))
      return false;
    if (serverActionResult == null) {
      if (other.serverActionResult != null)
        return false;
    } else if (!serverActionResult.equals(other.serverActionResult))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (items == null ? 1 : items.hashCode());
    hashCode = (hashCode * 37) + (serverActionResult == null ? 1 : serverActionResult.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "ScanInputQueueResult["
                 + items
                 + ","
                 + serverActionResult
    + "]";
  }
}
