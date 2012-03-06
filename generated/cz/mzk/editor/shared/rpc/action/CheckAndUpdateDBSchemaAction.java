package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Action;

public class CheckAndUpdateDBSchemaAction implements Action<CheckAndUpdateDBSchemaResult> { 


  public CheckAndUpdateDBSchemaAction() {
  }

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "CheckAndUpdateDBSchema";
  }

  @Override
  public boolean isSecured() {
    return false;
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "CheckAndUpdateDBSchemaAction["
    + "]";
  }
}
