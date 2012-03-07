package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class PutUserInfoAction implements Action<PutUserInfoResult> { 

  private cz.mzk.editor.shared.rpc.UserInfoItem user;

  public PutUserInfoAction(cz.mzk.editor.shared.rpc.UserInfoItem user) {
    this.user = user;
  }

  protected PutUserInfoAction() {
    // Possibly for serialization.
  }

  public cz.mzk.editor.shared.rpc.UserInfoItem getUser() {
    return user;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "PutUserInfo";
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
    PutUserInfoAction other = (PutUserInfoAction) obj;
    if (user == null) {
      if (other.user != null)
        return false;
    } else if (!user.equals(other.user))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (user == null ? 1 : user.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "PutUserInfoAction["
                 + user
    + "]";
  }
}
