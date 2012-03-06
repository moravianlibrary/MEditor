package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetRelationshipsResult implements Result { 

  private cz.mzk.editor.shared.rpc.DigitalObjectRelationships digObjRel;
  private java.util.List<java.lang.String> sharedPages;
  private java.util.List<java.lang.String> uuidNotToRemove;

  public GetRelationshipsResult(cz.mzk.editor.shared.rpc.DigitalObjectRelationships digObjRel, java.util.List<java.lang.String> sharedPages, java.util.List<java.lang.String> uuidNotToRemove) {
    this.digObjRel = digObjRel;
    this.sharedPages = sharedPages;
    this.uuidNotToRemove = uuidNotToRemove;
  }

  protected GetRelationshipsResult() {
    // Possibly for serialization.
  }

  public cz.mzk.editor.shared.rpc.DigitalObjectRelationships getDigObjRel() {
    return digObjRel;
  }

  public java.util.List<java.lang.String> getSharedPages() {
    return sharedPages;
  }

  public java.util.List<java.lang.String> getUuidNotToRemove() {
    return uuidNotToRemove;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetRelationshipsResult other = (GetRelationshipsResult) obj;
    if (digObjRel == null) {
      if (other.digObjRel != null)
        return false;
    } else if (!digObjRel.equals(other.digObjRel))
      return false;
    if (sharedPages == null) {
      if (other.sharedPages != null)
        return false;
    } else if (!sharedPages.equals(other.sharedPages))
      return false;
    if (uuidNotToRemove == null) {
      if (other.uuidNotToRemove != null)
        return false;
    } else if (!uuidNotToRemove.equals(other.uuidNotToRemove))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (digObjRel == null ? 1 : digObjRel.hashCode());
    hashCode = (hashCode * 37) + (sharedPages == null ? 1 : sharedPages.hashCode());
    hashCode = (hashCode * 37) + (uuidNotToRemove == null ? 1 : uuidNotToRemove.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetRelationshipsResult["
                 + digObjRel
                 + ","
                 + sharedPages
                 + ","
                 + uuidNotToRemove
    + "]";
  }
}
