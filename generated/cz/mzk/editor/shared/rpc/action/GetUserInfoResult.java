package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetUserInfoResult implements Result { 

  private java.util.ArrayList<cz.mzk.editor.shared.rpc.UserInfoItem> items;

  public GetUserInfoResult(java.util.ArrayList<cz.mzk.editor.shared.rpc.UserInfoItem> items) {
    this.items = items;
  }

  protected GetUserInfoResult() {
    // Possibly for serialization.
  }

  public java.util.ArrayList<cz.mzk.editor.shared.rpc.UserInfoItem> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetUserInfoResult other = (GetUserInfoResult) obj;
    if (items == null) {
      if (other.items != null)
        return false;
    } else if (!items.equals(other.items))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (items == null ? 1 : items.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetUserInfoResult["
                 + items
    + "]";
  }
}
