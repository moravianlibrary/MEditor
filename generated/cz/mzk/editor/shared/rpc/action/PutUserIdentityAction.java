package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class PutUserIdentityAction implements Action<PutUserIdentityResult> { 

  private cz.mzk.editor.shared.rpc.OpenIDItem identity;
  private java.lang.String userId;

  public PutUserIdentityAction(cz.mzk.editor.shared.rpc.OpenIDItem identity, java.lang.String userId) {
    this.identity = identity;
    this.userId = userId;
  }

  protected PutUserIdentityAction() {
    // Possibly for serialization.
  }

  public cz.mzk.editor.shared.rpc.OpenIDItem getIdentity() {
    return identity;
  }

  public java.lang.String getUserId() {
    return userId;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "PutUserIdentity";
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
    PutUserIdentityAction other = (PutUserIdentityAction) obj;
    if (identity == null) {
      if (other.identity != null)
        return false;
    } else if (!identity.equals(other.identity))
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
    hashCode = (hashCode * 37) + (identity == null ? 1 : identity.hashCode());
    hashCode = (hashCode * 37) + (userId == null ? 1 : userId.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "PutUserIdentityAction["
                 + identity
                 + ","
                 + userId
    + "]";
  }
}
