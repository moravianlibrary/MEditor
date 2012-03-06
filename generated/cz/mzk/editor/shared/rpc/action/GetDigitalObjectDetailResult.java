package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetDigitalObjectDetailResult implements Result { 

  private cz.mzk.editor.shared.rpc.DigitalObjectDetail detail;
  private java.lang.String description;
  private java.util.Date modified;

  public GetDigitalObjectDetailResult(cz.mzk.editor.shared.rpc.DigitalObjectDetail detail, java.lang.String description, java.util.Date modified) {
    this.detail = detail;
    this.description = description;
    this.modified = modified;
  }

  protected GetDigitalObjectDetailResult() {
    // Possibly for serialization.
  }

  public cz.mzk.editor.shared.rpc.DigitalObjectDetail getDetail() {
    return detail;
  }

  public java.lang.String getDescription() {
    return description;
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
    GetDigitalObjectDetailResult other = (GetDigitalObjectDetailResult) obj;
    if (detail == null) {
      if (other.detail != null)
        return false;
    } else if (!detail.equals(other.detail))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
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
    hashCode = (hashCode * 37) + (detail == null ? 1 : detail.hashCode());
    hashCode = (hashCode * 37) + (description == null ? 1 : description.hashCode());
    hashCode = (hashCode * 37) + (modified == null ? 1 : modified.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetDigitalObjectDetailResult["
                 + detail
                 + ","
                 + description
                 + ","
                 + modified
    + "]";
  }
}
