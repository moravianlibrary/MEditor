package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class CheckAvailabilityAction implements Action<CheckAvailabilityResult> { 

  private int serverId;

  public CheckAvailabilityAction(int serverId) {
    this.serverId = serverId;
  }

  protected CheckAvailabilityAction() {
    // Possibly for serialization.
  }

  public int getServerId() {
    return serverId;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "CheckAvailability";
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
    CheckAvailabilityAction other = (CheckAvailabilityAction) obj;
    if (serverId != other.serverId)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + new Integer(serverId).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "CheckAvailabilityAction["
                 + serverId
    + "]";
  }
}
