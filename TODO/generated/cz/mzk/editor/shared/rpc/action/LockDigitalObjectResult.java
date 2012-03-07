package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class LockDigitalObjectResult implements Result { 

  private cz.mzk.editor.shared.rpc.LockInfo lockInfo;

  public LockDigitalObjectResult(cz.mzk.editor.shared.rpc.LockInfo lockInfo) {
    this.lockInfo = lockInfo;
  }

  protected LockDigitalObjectResult() {
    // Possibly for serialization.
  }

  public cz.mzk.editor.shared.rpc.LockInfo getLockInfo() {
    return lockInfo;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    LockDigitalObjectResult other = (LockDigitalObjectResult) obj;
    if (lockInfo == null) {
      if (other.lockInfo != null)
        return false;
    } else if (!lockInfo.equals(other.lockInfo))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (lockInfo == null ? 1 : lockInfo.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "LockDigitalObjectResult["
                 + lockInfo
    + "]";
  }
}
