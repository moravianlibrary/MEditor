package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class PutUserInfoResult implements Result { 

  private java.lang.String id;
  private boolean found;

  public PutUserInfoResult(java.lang.String id, boolean found) {
    this.id = id;
    this.found = found;
  }

  protected PutUserInfoResult() {
    // Possibly for serialization.
  }

  public java.lang.String getId() {
    return id;
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
    PutUserInfoResult other = (PutUserInfoResult) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (found != other.found)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (id == null ? 1 : id.hashCode());
    hashCode = (hashCode * 37) + new Boolean(found).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "PutUserInfoResult["
                 + id
                 + ","
                 + found
    + "]";
  }
}
