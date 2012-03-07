package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class UnlockDigitalObjectResult implements Result { 

  private boolean successful;

  public UnlockDigitalObjectResult(boolean successful) {
    this.successful = successful;
  }

  protected UnlockDigitalObjectResult() {
    // Possibly for serialization.
  }

  public boolean isSuccessful() {
    return successful;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    UnlockDigitalObjectResult other = (UnlockDigitalObjectResult) obj;
    if (successful != other.successful)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + new Boolean(successful).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "UnlockDigitalObjectResult["
                 + successful
    + "]";
  }
}
