package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class PutDigitalObjectDetailAction implements Action<PutDigitalObjectDetailResult> { 

  private cz.mzk.editor.shared.rpc.DigitalObjectDetail detail;
  private boolean versioning;

  public PutDigitalObjectDetailAction(cz.mzk.editor.shared.rpc.DigitalObjectDetail detail, boolean versioning) {
    this.detail = detail;
    this.versioning = versioning;
  }

  protected PutDigitalObjectDetailAction() {
    // Possibly for serialization.
  }

  public cz.mzk.editor.shared.rpc.DigitalObjectDetail getDetail() {
    return detail;
  }

  public boolean isVersioning() {
    return versioning;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "PutDigitalObjectDetail";
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
    PutDigitalObjectDetailAction other = (PutDigitalObjectDetailAction) obj;
    if (detail == null) {
      if (other.detail != null)
        return false;
    } else if (!detail.equals(other.detail))
      return false;
    if (versioning != other.versioning)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (detail == null ? 1 : detail.hashCode());
    hashCode = (hashCode * 37) + new Boolean(versioning).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "PutDigitalObjectDetailAction["
                 + detail
                 + ","
                 + versioning
    + "]";
  }
}
