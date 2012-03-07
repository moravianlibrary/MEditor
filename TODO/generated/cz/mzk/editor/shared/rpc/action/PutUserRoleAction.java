package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class PutUserRoleAction implements Action<PutUserRoleResult> { 

  private cz.mzk.editor.shared.rpc.RoleItem role;
  private java.lang.String userId;

  public PutUserRoleAction(cz.mzk.editor.shared.rpc.RoleItem role, java.lang.String userId) {
    this.role = role;
    this.userId = userId;
  }

  protected PutUserRoleAction() {
    // Possibly for serialization.
  }

  public cz.mzk.editor.shared.rpc.RoleItem getRole() {
    return role;
  }

  public java.lang.String getUserId() {
    return userId;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "PutUserRole";
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
    PutUserRoleAction other = (PutUserRoleAction) obj;
    if (role == null) {
      if (other.role != null)
        return false;
    } else if (!role.equals(other.role))
      return false;
    if (userId == null) {
      if (other.userId != null)
        return false;
    } else if (!userId.equals(other.userId))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (role == null ? 1 : role.hashCode());
    hashCode = (hashCode * 37) + (userId == null ? 1 : userId.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "PutUserRoleAction["
                 + role
                 + ","
                 + userId
    + "]";
  }
}
