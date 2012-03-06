package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class ScanFolderAction implements Action<ScanFolderResult> { 

  private java.lang.String model;
  private java.lang.String code;
  private java.lang.String name;

  public ScanFolderAction(java.lang.String model, java.lang.String code, java.lang.String name) {
    this.model = model;
    this.code = code;
    this.name = name;
  }

  protected ScanFolderAction() {
    // Possibly for serialization.
  }

  public java.lang.String getModel() {
    return model;
  }

  public java.lang.String getCode() {
    return code;
  }

  public java.lang.String getName() {
    return name;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "ScanFolder";
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
    ScanFolderAction other = (ScanFolderAction) obj;
    if (model == null) {
      if (other.model != null)
        return false;
    } else if (!model.equals(other.model))
      return false;
    if (code == null) {
      if (other.code != null)
        return false;
    } else if (!code.equals(other.code))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (model == null ? 1 : model.hashCode());
    hashCode = (hashCode * 37) + (code == null ? 1 : code.hashCode());
    hashCode = (hashCode * 37) + (name == null ? 1 : name.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "ScanFolderAction["
                 + model
                 + ","
                 + code
                 + ","
                 + name
    + "]";
  }
}
