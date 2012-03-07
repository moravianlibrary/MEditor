package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class GetDescriptionAction implements Action<GetDescriptionResult> { 

  private java.lang.String uuid;

  public GetDescriptionAction(java.lang.String uuid) {
    this.uuid = uuid;
  }

  protected GetDescriptionAction() {
    // Possibly for serialization.
  }

  public java.lang.String getUuid() {
    return uuid;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "GetDescription";
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
    GetDescriptionAction other = (GetDescriptionAction) obj;
    if (uuid == null) {
      if (other.uuid != null)
        return false;
    } else if (!uuid.equals(other.uuid))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (uuid == null ? 1 : uuid.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetDescriptionAction["
                 + uuid
    + "]";
  }
}
