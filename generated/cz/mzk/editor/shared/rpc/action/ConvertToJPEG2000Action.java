package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class ConvertToJPEG2000Action implements Action<ConvertToJPEG2000Result> { 

  private cz.mzk.editor.shared.rpc.ImageItem item;

  public ConvertToJPEG2000Action(cz.mzk.editor.shared.rpc.ImageItem item) {
    this.item = item;
  }

  protected ConvertToJPEG2000Action() {
    // Possibly for serialization.
  }

  public cz.mzk.editor.shared.rpc.ImageItem getItem() {
    return item;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "ConvertToJPEG2000";
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
    ConvertToJPEG2000Action other = (ConvertToJPEG2000Action) obj;
    if (item == null) {
      if (other.item != null)
        return false;
    } else if (!item.equals(other.item))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (item == null ? 1 : item.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "ConvertToJPEG2000Action["
                 + item
    + "]";
  }
}
