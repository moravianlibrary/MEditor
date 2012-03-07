package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class LogoutResult implements Result { 

  private java.lang.String url;

  public LogoutResult(java.lang.String url) {
    this.url = url;
  }

  protected LogoutResult() {
    // Possibly for serialization.
  }

  public java.lang.String getUrl() {
    return url;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    LogoutResult other = (LogoutResult) obj;
    if (url == null) {
      if (other.url != null)
        return false;
    } else if (!url.equals(other.url))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (url == null ? 1 : url.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "LogoutResult["
                 + url
    + "]";
  }
}
