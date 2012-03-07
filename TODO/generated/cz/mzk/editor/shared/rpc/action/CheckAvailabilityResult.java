package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class CheckAvailabilityResult implements Result { 

  private boolean availability;
  private java.lang.String url;

  public CheckAvailabilityResult(boolean availability, java.lang.String url) {
    this.availability = availability;
    this.url = url;
  }

  protected CheckAvailabilityResult() {
    // Possibly for serialization.
  }

  public boolean isAvailability() {
    return availability;
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
    CheckAvailabilityResult other = (CheckAvailabilityResult) obj;
    if (availability != other.availability)
        return false;
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
