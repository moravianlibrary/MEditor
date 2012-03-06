package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class DownloadDigitalObjectDetailAction implements Action<DownloadDigitalObjectDetailResult> { 

  private cz.mzk.editor.shared.rpc.DigitalObjectDetail detail;

  public DownloadDigitalObjectDetailAction(cz.mzk.editor.shared.rpc.DigitalObjectDetail detail) {
    this.detail = detail;
  }

  protected DownloadDigitalObjectDetailAction() {
    // Possibly for serialization.
  }

  public cz.mzk.editor.shared.rpc.DigitalObjectDetail getDetail() {
    return detail;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "DownloadDigitalObjectDetail";
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
    DownloadDigitalObjectDetailAction other = (DownloadDigitalObjectDetailAction) obj;
    if (detail == null) {
      if (other.detail != null)
        return false;
    } else if (!detail.equals(other.detail))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (detail == null ? 1 : detail.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "DownloadDigitalObjectDetailAction["
                 + detail
    + "]";
  }
}
