package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class FindMetadataResult implements Result { 

  private java.util.ArrayList<cz.mzk.editor.shared.rpc.MetadataBundle> bundle;

  public FindMetadataResult(java.util.ArrayList<cz.mzk.editor.shared.rpc.MetadataBundle> bundle) {
    this.bundle = bundle;
  }

  protected FindMetadataResult() {
    // Possibly for serialization.
  }

  public java.util.ArrayList<cz.mzk.editor.shared.rpc.MetadataBundle> getBundle() {
    return bundle;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    FindMetadataResult other = (FindMetadataResult) obj;
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
    hashCode = (hashCode * 37) + (bundle == null ? 1 : bundle.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "FindMetadataResult["
                 + bundle
    + "]";
  }
}
