package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class CheckAvailabilityResult implements Result { 
  private boolean availability;
  private java.lang.String url;

  protected CheckAvailabilityResult() { }

  public CheckAvailabilityResult(boolean availability, java.lang.String url) { 
    this.availability = availability;
    this.url = url;
  }

  public boolean isAvailability() {
    return availability;
  }

  public java.lang.String getUrl() {
    return url;
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          CheckAvailabilityResult o = (CheckAvailabilityResult) other;
      return true
          && o.availability == this.availability
          && ((o.url == null && this.url == null) || (o.url != null && o.url.equals(this.url)))
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + new Boolean(availability).hashCode();
    hashCode = (hashCode * 37) + (url == null ? 1 : url.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "CheckAvailabilityResult["
                 + availability
                 + ","
                 + url
    + "]";
  }

}
