package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetDescriptionResult implements Result { 

  private java.lang.String description;
  private java.lang.String userDescription;
  private java.util.Date modified;

  public GetDescriptionResult(java.lang.String description, java.lang.String userDescription, java.util.Date modified) {
    this.description = description;
    this.userDescription = userDescription;
    this.modified = modified;
  }

  protected GetDescriptionResult() {
    // Possibly for serialization.
  }

  public java.lang.String getDescription() {
    return description;
  }

  public java.lang.String getUserDescription() {
    return userDescription;
  }

  public java.util.Date getModified() {
    return modified;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetDescriptionResult other = (GetDescriptionResult) obj;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (userDescription == null) {
      if (other.userDescription != null)
        return false;
    } else if (!userDescription.equals(other.userDescription))
      return false;
    if (modified == null) {
      if (other.modified != null)
        return false;
    } else if (!modified.equals(other.modified))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (description == null ? 1 : description.hashCode());
    hashCode = (hashCode * 37) + (userDescription == null ? 1 : userDescription.hashCode());
    hashCode = (hashCode * 37) + (modified == null ? 1 : modified.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetDescriptionResult["
                 + description
                 + ","
                 + userDescription
                 + ","
                 + modified
    + "]";
  }
}
