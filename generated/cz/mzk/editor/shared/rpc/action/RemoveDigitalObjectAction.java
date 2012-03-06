package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class RemoveDigitalObjectAction implements Action<RemoveDigitalObjectResult> { 

  private java.lang.String uuid;
  private java.util.List<java.lang.String> uuidNotToRemove;

  public RemoveDigitalObjectAction(java.lang.String uuid, java.util.List<java.lang.String> uuidNotToRemove) {
    this.uuid = uuid;
    this.uuidNotToRemove = uuidNotToRemove;
  }

  protected RemoveDigitalObjectAction() {
    // Possibly for serialization.
  }

  public java.lang.String getUuid() {
    return uuid;
  }

  public java.util.List<java.lang.String> getUuidNotToRemove() {
    return uuidNotToRemove;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "RemoveDigitalObject";
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
    RemoveDigitalObjectAction other = (RemoveDigitalObjectAction) obj;
    if (uuid == null) {
      if (other.uuid != null)
        return false;
    } else if (!uuid.equals(other.uuid))
      return false;
    if (uuidNotToRemove == null) {
      if (other.uuidNotToRemove != null)
        return false;
    } else if (!uuidNotToRemove.equals(other.uuidNotToRemove))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (uuid == null ? 1 : uuid.hashCode());
    hashCode = (hashCode * 37) + (uuidNotToRemove == null ? 1 : uuidNotToRemove.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "RemoveDigitalObjectAction["
                 + uuid
                 + ","
                 + uuidNotToRemove
    + "]";
  }
}
