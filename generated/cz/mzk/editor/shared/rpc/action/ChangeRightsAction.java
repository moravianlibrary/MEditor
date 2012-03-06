package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class ChangeRightsAction implements Action<ChangeRightsResult> { 

  private java.lang.String parentUuid;
  private java.lang.String right;
  private boolean forChildren;

  public ChangeRightsAction(java.lang.String parentUuid, java.lang.String right, boolean forChildren) {
    this.parentUuid = parentUuid;
    this.right = right;
    this.forChildren = forChildren;
  }

  protected ChangeRightsAction() {
    // Possibly for serialization.
  }

  public java.lang.String getParentUuid() {
    return parentUuid;
  }

  public java.lang.String getRight() {
    return right;
  }

  public boolean isForChildren() {
    return forChildren;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "ChangeRights";
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
    ChangeRightsAction other = (ChangeRightsAction) obj;
    if (parentUuid == null) {
      if (other.parentUuid != null)
        return false;
    } else if (!parentUuid.equals(other.parentUuid))
      return false;
    if (right == null) {
      if (other.right != null)
        return false;
    } else if (!right.equals(other.right))
      return false;
    if (forChildren != other.forChildren)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (parentUuid == null ? 1 : parentUuid.hashCode());
    hashCode = (hashCode * 37) + (right == null ? 1 : right.hashCode());
    hashCode = (hashCode * 37) + new Boolean(forChildren).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "ChangeRightsAction["
                 + parentUuid
                 + ","
                 + right
                 + ","
                 + forChildren
    + "]";
  }
}
