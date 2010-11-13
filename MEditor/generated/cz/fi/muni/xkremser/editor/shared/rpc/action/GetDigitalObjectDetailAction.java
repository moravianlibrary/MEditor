package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class GetDigitalObjectDetailAction implements Action<GetDigitalObjectDetailResult> { 
  private java.lang.String uuid;

  protected GetDigitalObjectDetailAction() { }

  public GetDigitalObjectDetailAction(java.lang.String uuid) { 
    this.uuid = uuid;
  }

  public java.lang.String getUuid() {
    return uuid;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "GetDigitalObjectDetail";
  }

  @Override
  public boolean isSecured() {
    return false;
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          GetDigitalObjectDetailAction o = (GetDigitalObjectDetailAction) other;
      return true
          && ((o.uuid == null && this.uuid == null) || (o.uuid != null && o.uuid.equals(this.uuid)))
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + (uuid == null ? 1 : uuid.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetDigitalObjectDetailAction["
                 + uuid
    + "]";
  }

}
