package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class CheckAvailabilityAction implements Action<CheckAvailabilityResult> { 
  private int serverId;

  protected CheckAvailabilityAction() { }

  public CheckAvailabilityAction(int serverId) { 
    this.serverId = serverId;
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
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          CheckAvailabilityAction o = (CheckAvailabilityAction) other;
      return true
          && o.serverId == this.serverId
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
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
