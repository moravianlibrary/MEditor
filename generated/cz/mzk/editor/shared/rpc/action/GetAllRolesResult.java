package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetAllRolesResult implements Result { 

  private java.util.ArrayList<java.lang.String> roles;

  public GetAllRolesResult(java.util.ArrayList<java.lang.String> roles) {
    this.roles = roles;
  }

  protected GetAllRolesResult() {
    // Possibly for serialization.
  }

  public java.util.ArrayList<java.lang.String> getRoles() {
    return roles;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetAllRolesResult other = (GetAllRolesResult) obj;
    if (roles == null) {
      if (other.roles != null)
        return false;
    } else if (!roles.equals(other.roles))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (roles == null ? 1 : roles.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetAllRolesResult["
                 + roles
    + "]";
  }
}
