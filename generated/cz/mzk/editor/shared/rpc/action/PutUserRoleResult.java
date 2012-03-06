package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class PutUserRoleResult implements Result { 

  private java.lang.String id;
  private boolean found;
  private java.lang.String description;

  public PutUserRoleResult(java.lang.String id, boolean found, java.lang.String description) {
    this.id = id;
    this.found = found;
    this.description = description;
  }

  protected PutUserRoleResult() {
    // Possibly for serialization.
  }

  public java.lang.String getId() {
    return id;
  }

  public boolean isFound() {
    return found;
  }

  public java.lang.String getDescription() {
    return description;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    PutUserRoleResult other = (PutUserRoleResult) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (found != other.found)
        return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (id == null ? 1 : id.hashCode());
    hashCode = (hashCode * 37) + new Boolean(found).hashCode();
    hashCode = (hashCode * 37) + (description == null ? 1 : description.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "PutUserRoleResult["
                 + id
                 + ","
                 + found
                 + ","
                 + description
    + "]";
  }
}
