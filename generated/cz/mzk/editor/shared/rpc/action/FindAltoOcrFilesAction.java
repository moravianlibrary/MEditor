package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class FindAltoOcrFilesAction implements Action<FindAltoOcrFilesResult> { 

  private java.lang.String path;

  public FindAltoOcrFilesAction(java.lang.String path) {
    this.path = path;
  }

  protected FindAltoOcrFilesAction() {
    // Possibly for serialization.
  }

  public java.lang.String getPath() {
    return path;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "FindAltoOcrFiles";
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
    FindAltoOcrFilesAction other = (FindAltoOcrFilesAction) obj;
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
    return "FindAltoOcrFilesAction["
                 + path
    + "]";
  }
}
