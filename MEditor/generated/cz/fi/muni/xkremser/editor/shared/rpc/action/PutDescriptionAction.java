package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class PutDescriptionAction implements Action<PutDescriptionResult> { 
  private java.lang.String uuid;
  private java.lang.String description;

  protected PutDescriptionAction() { }

  public PutDescriptionAction(java.lang.String uuid, java.lang.String description) { 
    this.uuid = uuid;
    this.description = description;
  }

  public java.lang.String getUuid() {
    return uuid;
  }

  public java.lang.String getDescription() {
    return description;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "PutDescription";
  }

  @Override
  public boolean isSecured() {
    return false;
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          PutDescriptionAction o = (PutDescriptionAction) other;
      return true
          && ((o.uuid == null && this.uuid == null) || (o.uuid != null && o.uuid.equals(this.uuid)))
          && ((o.description == null && this.description == null) || (o.description != null && o.description.equals(this.description)))
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + (uuid == null ? 1 : uuid.hashCode());
    hashCode = (hashCode * 37) + (description == null ? 1 : description.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "PutDescriptionAction["
                 + uuid
                 + ","
                 + description
    + "]";
  }

}
