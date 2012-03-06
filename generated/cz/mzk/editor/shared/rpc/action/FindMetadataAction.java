package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class FindMetadataAction implements Action<FindMetadataResult> { 

  private cz.mzk.editor.client.util.Constants.SEARCH_FIELD searchType;
  private java.lang.String id;
  private boolean oai;
  private java.lang.String oaiQuery;

  public FindMetadataAction(cz.mzk.editor.client.util.Constants.SEARCH_FIELD searchType, java.lang.String id, boolean oai, java.lang.String oaiQuery) {
    this.searchType = searchType;
    this.id = id;
    this.oai = oai;
    this.oaiQuery = oaiQuery;
  }

  protected FindMetadataAction() {
    // Possibly for serialization.
  }

  public cz.mzk.editor.client.util.Constants.SEARCH_FIELD getSearchType() {
    return searchType;
  }

  public java.lang.String getId() {
    return id;
  }

  public boolean isOai() {
    return oai;
  }

  public java.lang.String getOaiQuery() {
    return oaiQuery;
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "FindMetadata";
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
    FindMetadataAction other = (FindMetadataAction) obj;
    if (searchType == null) {
      if (other.searchType != null)
        return false;
    } else if (!searchType.equals(other.searchType))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (oai != other.oai)
        return false;
    if (oaiQuery == null) {
      if (other.oaiQuery != null)
        return false;
    } else if (!oaiQuery.equals(other.oaiQuery))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (searchType == null ? 1 : searchType.hashCode());
    hashCode = (hashCode * 37) + (id == null ? 1 : id.hashCode());
    hashCode = (hashCode * 37) + new Boolean(oai).hashCode();
    hashCode = (hashCode * 37) + (oaiQuery == null ? 1 : oaiQuery.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "FindMetadataAction["
                 + searchType
                 + ","
                 + id
                 + ","
                 + oai
                 + ","
                 + oaiQuery
    + "]";
  }
}
