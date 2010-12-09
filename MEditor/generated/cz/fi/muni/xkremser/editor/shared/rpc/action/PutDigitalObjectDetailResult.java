package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class PutDigitalObjectDetailResult implements Result { 

  public PutDigitalObjectDetailResult() { 
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          PutDigitalObjectDetailResult o = (PutDigitalObjectDetailResult) other;
      return true
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "PutDigitalObjectDetailResult["
    + "]";
  }

}
