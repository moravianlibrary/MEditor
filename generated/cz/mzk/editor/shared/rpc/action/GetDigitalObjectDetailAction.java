package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class GetDigitalObjectDetailAction implements Action<GetDigitalObjectDetailResult> { 

  private java.lang.String uuid;
  private cz.mzk.editor.shared.domain.DigitalObjectModel model;
  private java.lang.String storedFOXMLFilePath;

  public GetDigitalObjectDetailAction(java.lang.String uuid, cz.mzk.editor.shared.domain.DigitalObjectModel model, java.lang.String storedFOXMLFilePath) {
    this.uuid = uuid;
    this.model = model;
    this.storedFOXMLFilePath = storedFOXMLFilePath;
  }

  protected GetDigitalObjectDetailAction() {
    // Possibly for serialization.
  }

  public java.lang.String getUuid() {
    return uuid;
  }

  public cz.mzk.editor.shared.domain.DigitalObjectModel getModel() {
    return model;
  }

  public java.lang.String getStoredFOXMLFilePath() {
    return storedFOXMLFilePath;
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
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetDigitalObjectDetailAction other = (GetDigitalObjectDetailAction) obj;
    if (uuid == null) {
      if (other.uuid != null)
        return false;
    } else if (!uuid.equals(other.uuid))
      return false;
    if (model == null) {
      if (other.model != null)
        return false;
    } else if (!model.equals(other.model))
      return false;
    if (storedFOXMLFilePath == null) {
      if (other.storedFOXMLFilePath != null)
        return false;
    } else if (!storedFOXMLFilePath.equals(other.storedFOXMLFilePath))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (uuid == null ? 1 : uuid.hashCode());
    hashCode = (hashCode * 37) + (model == null ? 1 : model.hashCode());
    hashCode = (hashCode * 37) + (storedFOXMLFilePath == null ? 1 : storedFOXMLFilePath.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetDigitalObjectDetailAction["
                 + uuid
                 + ","
                 + model
                 + ","
                 + storedFOXMLFilePath
    + "]";
  }
}
