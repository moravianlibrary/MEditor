package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class CheckAndUpdateDBSchemaResult implements Result { 

  private boolean success;
  private java.lang.String version;

  public CheckAndUpdateDBSchemaResult(boolean success, java.lang.String version) {
    this.success = success;
    this.version = version;
  }

  protected CheckAndUpdateDBSchemaResult() {
    // Possibly for serialization.
  }

  public boolean isSuccess() {
    return success;
  }

  public java.lang.String getVersion() {
    return version;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    CheckAndUpdateDBSchemaResult other = (CheckAndUpdateDBSchemaResult) obj;
    if (success != other.success)
        return false;
    if (version == null) {
      if (other.version != null)
        return false;
    } else if (!version.equals(other.version))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + new Boolean(success).hashCode();
    hashCode = (hashCode * 37) + (version == null ? 1 : version.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "CheckAndUpdateDBSchemaResult["
                 + success
                 + ","
                 + version
    + "]";
  }
}
