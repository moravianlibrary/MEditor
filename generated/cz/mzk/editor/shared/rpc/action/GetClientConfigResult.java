package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetClientConfigResult implements Result { 

  private java.util.HashMap<java.lang.String,java.lang.Object> config;

  public GetClientConfigResult(java.util.HashMap<java.lang.String,java.lang.Object> config) {
    this.config = config;
  }

  protected GetClientConfigResult() {
    // Possibly for serialization.
  }

  public java.util.HashMap<java.lang.String,java.lang.Object> getConfig() {
    return config;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetClientConfigResult other = (GetClientConfigResult) obj;
    if (config == null) {
      if (other.config != null)
        return false;
    } else if (!config.equals(other.config))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
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
