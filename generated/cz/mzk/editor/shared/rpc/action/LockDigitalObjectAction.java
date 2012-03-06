package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class LockDigitalObjectAction implements Action<LockDigitalObjectResult> { 

  private java.lang.String uuid;
  private java.lang.String description;

  public LockDigitalObjectAction(java.lang.String uuid, java.lang.String description) {
    this.uuid = uuid;
    this.description = description;
  }

  protected LockDigitalObjectAction() {
    // Possibly for serialization.
  }

  public java.lang.String getUuid() {
    return uuid;
  }

  public java.lang.String getDescription() {
    return description;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "LockDigitalObject";
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
    LockDigitalObjectAction other = (LockDigitalObjectAction) obj;
    if (uuid == null) {
      if (other.uuid != null)
        return false;
    } else if (!uuid.equals(other.uuid))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (uuid == null ? 1 : uuid.hashCode());
    hashCode = (hashCode * 37) + (description == null ? 1 : description.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "LockDigitalObjectAction["
                 + uuid
                 + ","
                 + description
    + "]";
  }
}
