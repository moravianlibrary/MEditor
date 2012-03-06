package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class GetRecentlyModifiedAction implements Action<GetRecentlyModifiedResult> { 

  boolean forAllUsers;

  public GetRecentlyModifiedAction(boolean forAllUsers) {
    this.forAllUsers = forAllUsers;
  }

  protected GetRecentlyModifiedAction() {
    // Possibly for serialization.
  }

  public boolean isForAllUsers() {
    return forAllUsers;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "GetRecentlyModified";
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
    GetRecentlyModifiedAction other = (GetRecentlyModifiedAction) obj;
    if (forAllUsers != other.forAllUsers)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + new Boolean(forAllUsers).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetRecentlyModifiedAction["
                 + forAllUsers
    + "]";
  }
}
