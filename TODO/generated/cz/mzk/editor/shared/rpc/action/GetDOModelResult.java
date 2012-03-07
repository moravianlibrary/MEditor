package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetDOModelResult implements Result { 

  private cz.mzk.editor.shared.domain.DigitalObjectModel model;

  public GetDOModelResult(cz.mzk.editor.shared.domain.DigitalObjectModel model) {
    this.model = model;
  }

  protected GetDOModelResult() {
    // Possibly for serialization.
  }

  public cz.mzk.editor.shared.domain.DigitalObjectModel getModel() {
    return model;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetDOModelResult other = (GetDOModelResult) obj;
    if (model == null) {
      if (other.model != null)
        return false;
    } else if (!model.equals(other.model))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (model == null ? 1 : model.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetDOModelResult["
                 + model
    + "]";
  }
}
