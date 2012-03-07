package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class StoredItemsAction implements Action<StoredItemsResult> { 

  private cz.mzk.editor.shared.rpc.DigitalObjectDetail detail;
  private cz.mzk.editor.shared.rpc.StoredItem storedItem;
  private cz.mzk.editor.client.util.Constants.VERB verb;

  public StoredItemsAction(cz.mzk.editor.shared.rpc.DigitalObjectDetail detail, cz.mzk.editor.shared.rpc.StoredItem storedItem, cz.mzk.editor.client.util.Constants.VERB verb) {
    this.detail = detail;
    this.storedItem = storedItem;
    this.verb = verb;
  }

  protected StoredItemsAction() {
    // Possibly for serialization.
  }

  public cz.mzk.editor.shared.rpc.DigitalObjectDetail getDetail() {
    return detail;
  }

  public cz.mzk.editor.shared.rpc.StoredItem getStoredItem() {
    return storedItem;
  }

  public cz.mzk.editor.client.util.Constants.VERB getVerb() {
    return verb;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "StoredItems";
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
    StoredItemsAction other = (StoredItemsAction) obj;
    if (detail == null) {
      if (other.detail != null)
        return false;
    } else if (!detail.equals(other.detail))
      return false;
    if (storedItem == null) {
      if (other.storedItem != null)
        return false;
    } else if (!storedItem.equals(other.storedItem))
      return false;
    if (verb == null) {
      if (other.verb != null)
        return false;
    } else if (!verb.equals(other.verb))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (detail == null ? 1 : detail.hashCode());
    hashCode = (hashCode * 37) + (storedItem == null ? 1 : storedItem.hashCode());
    hashCode = (hashCode * 37) + (verb == null ? 1 : verb.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "StoredItemsAction["
                 + detail
                 + ","
                 + storedItem
                 + ","
                 + verb
    + "]";
  }
}
