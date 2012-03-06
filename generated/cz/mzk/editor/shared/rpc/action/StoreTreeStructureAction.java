package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class StoreTreeStructureAction implements Action<StoreTreeStructureResult> { 

  private cz.mzk.editor.client.util.Constants.VERB verb;
  private java.lang.String id;
  private boolean all;
  private cz.mzk.editor.shared.rpc.TreeStructureBundle bundle;

  public StoreTreeStructureAction(cz.mzk.editor.client.util.Constants.VERB verb, java.lang.String id, boolean all, cz.mzk.editor.shared.rpc.TreeStructureBundle bundle) {
    this.verb = verb;
    this.id = id;
    this.all = all;
    this.bundle = bundle;
  }

  protected StoreTreeStructureAction() {
    // Possibly for serialization.
  }

  public cz.mzk.editor.client.util.Constants.VERB getVerb() {
    return verb;
  }

  public java.lang.String getId() {
    return id;
  }

  public boolean isAll() {
    return all;
  }

  public cz.mzk.editor.shared.rpc.TreeStructureBundle getBundle() {
    return bundle;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "StoreTreeStructure";
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
    StoreTreeStructureAction other = (StoreTreeStructureAction) obj;
    if (verb == null) {
      if (other.verb != null)
        return false;
    } else if (!verb.equals(other.verb))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (all != other.all)
        return false;
    if (bundle == null) {
      if (other.bundle != null)
        return false;
    } else if (!bundle.equals(other.bundle))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (verb == null ? 1 : verb.hashCode());
    hashCode = (hashCode * 37) + (id == null ? 1 : id.hashCode());
    hashCode = (hashCode * 37) + new Boolean(all).hashCode();
    hashCode = (hashCode * 37) + (bundle == null ? 1 : bundle.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "StoreTreeStructureAction["
                 + verb
                 + ","
                 + id
                 + ","
                 + all
                 + ","
                 + bundle
    + "]";
  }
}
