package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetDigitalObjectDetailResult implements Result { 
  private cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail detail;

  protected GetDigitalObjectDetailResult() { }

  public GetDigitalObjectDetailResult(cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail detail) { 
    this.detail = detail;
  }

  public cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail getDetail() {
    return detail;
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          GetDigitalObjectDetailResult o = (GetDigitalObjectDetailResult) other;
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
    return "GetDigitalObjectDetailResult["
                 + detail
    + "]";
  }

}
