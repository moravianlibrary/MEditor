package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class ScanFolderResult implements Result { 

  private java.util.ArrayList<cz.mzk.editor.shared.rpc.ImageItem> items;
  private java.util.ArrayList<cz.mzk.editor.shared.rpc.ImageItem> toAdd;
  private cz.mzk.editor.shared.rpc.ServerActionResult serverActionResult;

  public ScanFolderResult(java.util.ArrayList<cz.mzk.editor.shared.rpc.ImageItem> items, java.util.ArrayList<cz.mzk.editor.shared.rpc.ImageItem> toAdd, cz.mzk.editor.shared.rpc.ServerActionResult serverActionResult) {
    this.items = items;
    this.toAdd = toAdd;
    this.serverActionResult = serverActionResult;
  }

  protected ScanFolderResult() {
    // Possibly for serialization.
  }

  public java.util.ArrayList<cz.mzk.editor.shared.rpc.ImageItem> getItems() {
    return items;
  }

  public java.util.ArrayList<cz.mzk.editor.shared.rpc.ImageItem> getToAdd() {
    return toAdd;
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
    ScanFolderResult other = (ScanFolderResult) obj;
    if (items == null) {
      if (other.items != null)
        return false;
    } else if (!items.equals(other.items))
      return false;
    if (toAdd == null) {
      if (other.toAdd != null)
        return false;
    } else if (!toAdd.equals(other.toAdd))
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
    hashCode = (hashCode * 37) + (toAdd == null ? 1 : toAdd.hashCode());
    hashCode = (hashCode * 37) + (serverActionResult == null ? 1 : serverActionResult.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "ScanFolderResult["
                 + items
                 + ","
                 + toAdd
                 + ","
                 + serverActionResult
    + "]";
  }
}
