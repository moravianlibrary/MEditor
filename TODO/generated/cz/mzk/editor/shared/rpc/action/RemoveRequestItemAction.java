package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class RemoveRequestItemAction implements Action<RemoveRequestItemResult> { 

  private java.lang.Long id;

  public RemoveRequestItemAction(java.lang.Long id) {
    this.id = id;
  }

  protected RemoveRequestItemAction() {
    // Possibly for serialization.
  }

  public java.lang.Long getId() {
    return id;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "RemoveRequestItem";
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
    RemoveRequestItemAction other = (RemoveRequestItemAction) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (id == null ? 1 : id.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "RemoveRequestItemAction["
                 + id
    + "]";
  }
}
