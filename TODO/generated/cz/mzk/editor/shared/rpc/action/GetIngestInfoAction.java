package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class GetIngestInfoAction implements Action<GetIngestInfoResult> { 

  private java.lang.String path;

  public GetIngestInfoAction(java.lang.String path) {
    this.path = path;
  }

  protected GetIngestInfoAction() {
    // Possibly for serialization.
  }

  public java.lang.String getPath() {
    return path;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "GetIngestInfo";
  }

  @Override
  public boolean isSecured() {
    return false;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetIngestInfoAction other = (GetIngestInfoAction) obj;
    if (path == null) {
      if (other.path != null)
        return false;
    } else if (!path.equals(other.path))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (path == null ? 1 : path.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetIngestInfoAction["
                 + path
    + "]";
  }
}
