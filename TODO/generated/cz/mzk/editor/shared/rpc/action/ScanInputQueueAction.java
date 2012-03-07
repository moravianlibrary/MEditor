package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class ScanInputQueueAction implements Action<ScanInputQueueResult> { 

  private java.lang.String id;
  private boolean refresh;

  public ScanInputQueueAction(java.lang.String id, boolean refresh) {
    this.id = id;
    this.refresh = refresh;
  }

  protected ScanInputQueueAction() {
    // Possibly for serialization.
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
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    ScanInputQueueAction other = (ScanInputQueueAction) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (refresh != other.refresh)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
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
