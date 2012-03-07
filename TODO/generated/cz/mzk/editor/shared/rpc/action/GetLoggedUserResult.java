package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetLoggedUserResult implements Result { 

  private java.lang.String name;
  private boolean editUsers;

  public GetLoggedUserResult(java.lang.String name, boolean editUsers) {
    this.name = name;
    this.editUsers = editUsers;
  }

  protected GetLoggedUserResult() {
    // Possibly for serialization.
  }

  public java.lang.String getName() {
    return name;
  }

  public boolean isEditUsers() {
    return editUsers;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetLoggedUserResult other = (GetLoggedUserResult) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (editUsers != other.editUsers)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (name == null ? 1 : name.hashCode());
    hashCode = (hashCode * 37) + new Boolean(editUsers).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetLoggedUserResult["
                 + name
                 + ","
                 + editUsers
    + "]";
  }
}
