package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class PutDigitalObjectDetailResult implements Result { 

  private boolean saved;

  public PutDigitalObjectDetailResult(boolean saved) {
    this.saved = saved;
  }

  protected PutDigitalObjectDetailResult() {
    // Possibly for serialization.
  }

  public boolean isSaved() {
    return saved;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    PutDigitalObjectDetailResult other = (PutDigitalObjectDetailResult) obj;
    if (saved != other.saved)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + new Boolean(saved).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "PutDigitalObjectDetailResult["
                 + saved
    + "]";
  }
}
