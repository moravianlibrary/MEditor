package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetIngestInfoResult implements Result { 

  private java.util.List<cz.mzk.editor.shared.rpc.IngestInfo> ingestInfo;

  public GetIngestInfoResult(java.util.List<cz.mzk.editor.shared.rpc.IngestInfo> ingestInfo) {
    this.ingestInfo = ingestInfo;
  }

  protected GetIngestInfoResult() {
    // Possibly for serialization.
  }

  public java.util.List<cz.mzk.editor.shared.rpc.IngestInfo> getIngestInfo() {
    return ingestInfo;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetIngestInfoResult other = (GetIngestInfoResult) obj;
    if (ingestInfo == null) {
      if (other.ingestInfo != null)
        return false;
    } else if (!ingestInfo.equals(other.ingestInfo))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (ingestInfo == null ? 1 : ingestInfo.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetIngestInfoResult["
                 + ingestInfo
    + "]";
  }
}
