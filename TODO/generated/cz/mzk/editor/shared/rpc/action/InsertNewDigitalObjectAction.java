package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class InsertNewDigitalObjectAction implements Action<InsertNewDigitalObjectResult> { 

  private cz.mzk.editor.shared.rpc.NewDigitalObject object;
  private java.lang.String inputPath;

  public InsertNewDigitalObjectAction(cz.mzk.editor.shared.rpc.NewDigitalObject object, java.lang.String inputPath) {
    this.object = object;
    this.inputPath = inputPath;
  }

  protected InsertNewDigitalObjectAction() {
    // Possibly for serialization.
  }

  public cz.mzk.editor.shared.rpc.NewDigitalObject getObject() {
    return object;
  }

  public java.lang.String getInputPath() {
    return inputPath;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "InsertNewDigitalObject";
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
    InsertNewDigitalObjectAction other = (InsertNewDigitalObjectAction) obj;
    if (object == null) {
      if (other.object != null)
        return false;
    } else if (!object.equals(other.object))
      return false;
    if (inputPath == null) {
      if (other.inputPath != null)
        return false;
    } else if (!inputPath.equals(other.inputPath))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (object == null ? 1 : object.hashCode());
    hashCode = (hashCode * 37) + (inputPath == null ? 1 : inputPath.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "InsertNewDigitalObjectAction["
                 + object
                 + ","
                 + inputPath
    + "]";
  }
}
