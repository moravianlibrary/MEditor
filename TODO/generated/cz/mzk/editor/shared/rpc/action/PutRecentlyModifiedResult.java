package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class PutRecentlyModifiedResult implements Result { 

  private boolean found;

  public PutRecentlyModifiedResult(boolean found) {
    this.found = found;
  }

  protected PutRecentlyModifiedResult() {
    // Possibly for serialization.
  }

  public boolean isFound() {
    return found;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    PutRecentlyModifiedResult other = (PutRecentlyModifiedResult) obj;
    if (found != other.found)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + new Boolean(found).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "PutRecentlyModifiedResult["
                 + found
    + "]";
  }
}
