package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class GetRecentlyModifiedAction implements Action<GetRecentlyModifiedResult> { 
  private boolean forAllUsers;

  protected GetRecentlyModifiedAction() { }

  public GetRecentlyModifiedAction(boolean forAllUsers) { 
    this.forAllUsers = forAllUsers;
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
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          GetRecentlyModifiedAction o = (GetRecentlyModifiedAction) other;
      return true
          && o.forAllUsers == this.forAllUsers
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
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
