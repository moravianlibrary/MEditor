package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetLockInformationResult implements Result { 

  private cz.mzk.editor.shared.rpc.LockInfo lockInfo;

  public GetLockInformationResult(cz.mzk.editor.shared.rpc.LockInfo lockInfo) {
    this.lockInfo = lockInfo;
  }

  protected GetLockInformationResult() {
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
    GetLockInformationResult other = (GetLockInformationResult) obj;
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
    return "GetLockInformationResult["
                 + lockInfo
    + "]";
  }
}
