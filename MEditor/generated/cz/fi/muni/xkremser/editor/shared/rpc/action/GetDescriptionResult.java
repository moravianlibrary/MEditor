package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetDescriptionResult implements Result { 
  private java.lang.String description;

  protected GetDescriptionResult() { }

  public GetDescriptionResult(java.lang.String description) { 
    this.description = description;
  }

  public java.lang.String getDescription() {
    return description;
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          GetDescriptionResult o = (GetDescriptionResult) other;
      return true
          && ((o.description == null && this.description == null) || (o.description != null && o.description.equals(this.description)))
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + (description == null ? 1 : description.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetDescriptionResult["
                 + description
    + "]";
  }

}
