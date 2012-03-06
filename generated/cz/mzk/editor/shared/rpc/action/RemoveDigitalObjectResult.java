package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class RemoveDigitalObjectResult implements Result { 

  private java.lang.String errorMessage;
  private java.util.List<java.lang.String> removed;

  public RemoveDigitalObjectResult(java.lang.String errorMessage, java.util.List<java.lang.String> removed) {
    this.errorMessage = errorMessage;
    this.removed = removed;
  }

  protected RemoveDigitalObjectResult() {
    // Possibly for serialization.
  }

  public java.lang.String getErrorMessage() {
    return errorMessage;
  }

  public java.util.List<java.lang.String> getRemoved() {
    return removed;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    RemoveDigitalObjectResult other = (RemoveDigitalObjectResult) obj;
    if (errorMessage == null) {
      if (other.errorMessage != null)
        return false;
    } else if (!errorMessage.equals(other.errorMessage))
      return false;
    if (removed == null) {
      if (other.removed != null)
        return false;
    } else if (!removed.equals(other.removed))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (errorMessage == null ? 1 : errorMessage.hashCode());
    hashCode = (hashCode * 37) + (removed == null ? 1 : removed.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "RemoveDigitalObjectResult["
                 + errorMessage
                 + ","
                 + removed
    + "]";
  }
}
