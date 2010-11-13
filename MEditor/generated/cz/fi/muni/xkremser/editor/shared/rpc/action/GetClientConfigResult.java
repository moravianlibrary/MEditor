package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetClientConfigResult implements Result { 
  private java.util.HashMap<java.lang.String,java.lang.Object> config;

  protected GetClientConfigResult() { }

  public GetClientConfigResult(java.util.HashMap<java.lang.String,java.lang.Object> config) { 
    this.config = config;
  }

  public java.util.HashMap<java.lang.String,java.lang.Object> getConfig() {
    return config;
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          GetClientConfigResult o = (GetClientConfigResult) other;
      return true
          && ((o.config == null && this.config == null) || (o.config != null && o.config.equals(this.config)))
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + (config == null ? 1 : config.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetClientConfigResult["
                 + config
    + "]";
  }

}
