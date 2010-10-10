package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class ScanInputQueueAction implements Action<ScanInputQueueResult> { 
  private java.lang.String id;
  private cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueue.TYPE type;

  protected ScanInputQueueAction() { }

  public ScanInputQueueAction(java.lang.String id, cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueue.TYPE type) { 
    this.id = id;
    this.type = type;
  }

  public java.lang.String getId() {
    return id;
  }

  public cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueue.TYPE getType() {
    return type;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "ScanInputQueue";
  }

  @Override
  public boolean isSecured() {
    return false;
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          ScanInputQueueAction o = (ScanInputQueueAction) other;
      return true
          && ((o.id == null && this.id == null) || (o.id != null && o.id.equals(this.id)))
          && ((o.type == null && this.type == null) || (o.type != null && o.type.equals(this.type)))
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + (id == null ? 1 : id.hashCode());
    hashCode = (hashCode * 37) + (type == null ? 1 : type.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "ScanInputQueueAction["
                 + id
                 + ","
                 + type
    + "]";
  }

}
