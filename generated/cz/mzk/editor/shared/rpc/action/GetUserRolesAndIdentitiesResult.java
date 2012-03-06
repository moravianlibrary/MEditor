package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetUserRolesAndIdentitiesResult implements Result { 

  private java.util.ArrayList<cz.mzk.editor.shared.rpc.RoleItem> roles;
  private java.util.ArrayList<cz.mzk.editor.shared.rpc.OpenIDItem> identities;

  public GetUserRolesAndIdentitiesResult(java.util.ArrayList<cz.mzk.editor.shared.rpc.RoleItem> roles, java.util.ArrayList<cz.mzk.editor.shared.rpc.OpenIDItem> identities) {
    this.roles = roles;
    this.identities = identities;
  }

  protected GetUserRolesAndIdentitiesResult() {
    // Possibly for serialization.
  }

  public java.util.ArrayList<cz.mzk.editor.shared.rpc.RoleItem> getRoles() {
    return roles;
  }

  public java.util.ArrayList<cz.mzk.editor.shared.rpc.OpenIDItem> getIdentities() {
    return identities;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetUserRolesAndIdentitiesResult other = (GetUserRolesAndIdentitiesResult) obj;
    if (roles == null) {
      if (other.roles != null)
        return false;
    } else if (!roles.equals(other.roles))
      return false;
    if (identities == null) {
      if (other.identities != null)
        return false;
    } else if (!identities.equals(other.identities))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (roles == null ? 1 : roles.hashCode());
    hashCode = (hashCode * 37) + (identities == null ? 1 : identities.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetUserRolesAndIdentitiesResult["
                 + roles
                 + ","
                 + identities
    + "]";
  }
}
