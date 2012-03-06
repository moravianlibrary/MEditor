package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class ScanInputQueueAction implements Action<ScanInputQueueResult> { 
  private java.lang.String id;
  private boolean refresh;

  protected ScanInputQueueAction() { }

  public ScanInputQueueAction(java.lang.String id, boolean refresh) { 
    this.id = id;
    this.refresh = refresh;
  }

  public java.lang.String getId() {
    return id;
  }

  public boolean isRefresh() {
    return refresh;
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
          && o.refresh == this.refresh
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + (id == null ? 1 : id.hashCode());
    hashCode = (hashCode * 37) + new Boolean(refresh).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "ScanInputQueueAction["
                 + id
                 + ","
                 + refresh
    + "]";
  }

}
