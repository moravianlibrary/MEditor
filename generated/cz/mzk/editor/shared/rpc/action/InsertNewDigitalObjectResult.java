package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class InsertNewDigitalObjectResult implements Result { 

  private boolean ingestSuccess;
  private boolean reindexSuccess;
  private java.lang.String newPid;

  public InsertNewDigitalObjectResult(boolean ingestSuccess, boolean reindexSuccess, java.lang.String newPid) {
    this.ingestSuccess = ingestSuccess;
    this.reindexSuccess = reindexSuccess;
    this.newPid = newPid;
  }

  protected InsertNewDigitalObjectResult() {
    // Possibly for serialization.
  }

  public boolean isIngestSuccess() {
    return ingestSuccess;
  }

  public boolean isReindexSuccess() {
    return reindexSuccess;
  }

  public java.lang.String getNewPid() {
    return newPid;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    InsertNewDigitalObjectResult other = (InsertNewDigitalObjectResult) obj;
    if (ingestSuccess != other.ingestSuccess)
        return false;
    if (reindexSuccess != other.reindexSuccess)
        return false;
    if (newPid == null) {
      if (other.newPid != null)
        return false;
    } else if (!newPid.equals(other.newPid))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + new Boolean(ingestSuccess).hashCode();
    hashCode = (hashCode * 37) + new Boolean(reindexSuccess).hashCode();
    hashCode = (hashCode * 37) + (newPid == null ? 1 : newPid.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "InsertNewDigitalObjectResult["
                 + ingestSuccess
                 + ","
                 + reindexSuccess
                 + ","
                 + newPid
    + "]";
  }
}
