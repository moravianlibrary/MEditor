package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class PutDigitalObjectDetailAction implements Action<PutDigitalObjectDetailResult> { 
  private cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail detail;

  protected PutDigitalObjectDetailAction() { }

  public PutDigitalObjectDetailAction(cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail detail) { 
    this.detail = detail;
  }

  public cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail getDetail() {
    return detail;
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
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          PutDigitalObjectDetailAction o = (PutDigitalObjectDetailAction) other;
      return true
          && ((o.detail == null && this.detail == null) || (o.detail != null && o.detail.equals(this.detail)))
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + (detail == null ? 1 : detail.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "PutDigitalObjectDetailAction["
                 + detail
    + "]";
  }

}
