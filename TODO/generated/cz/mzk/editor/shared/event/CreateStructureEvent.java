package cz.mzk.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class CreateStructureEvent extends GwtEvent<CreateStructureEvent.CreateStructureHandler> { 

  public interface HasCreateStructureHandlers extends HasHandlers {
    HandlerRegistration addCreateStructureHandler(CreateStructureHandler handler);
  }

  public interface CreateStructureHandler extends EventHandler {
    public void onCreateStructure(CreateStructureEvent event);
  }

  private static final Type<CreateStructureHandler> TYPE = new Type<CreateStructureHandler>();

  public static void fire(HasHandlers source, java.lang.String model, java.lang.String code, java.lang.String inputPath, cz.mzk.editor.shared.rpc.MetadataBundle bundle) {
    source.fireEvent(new CreateStructureEvent(model, code, inputPath, bundle));
  }

  public static void fire(HasHandlers source, java.lang.String model, java.lang.String code, java.lang.String inputPath) {
    source.fireEvent(new CreateStructureEvent(model, code, inputPath));
  }

  public static Type<CreateStructureHandler> getType() {
    return TYPE;
  }

  private java.lang.String model;
  private java.lang.String code;
  private java.lang.String inputPath;
  private cz.mzk.editor.shared.rpc.MetadataBundle bundle;

  public CreateStructureEvent(java.lang.String model, java.lang.String code, java.lang.String inputPath, cz.mzk.editor.shared.rpc.MetadataBundle bundle) {
    this.model = model;
    this.code = code;
    this.inputPath = inputPath;
    this.bundle = bundle;
  }

  public CreateStructureEvent(java.lang.String model, java.lang.String code, java.lang.String inputPath) {
    this.model = model;
    this.code = code;
    this.inputPath = inputPath;
  }

  protected CreateStructureEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<CreateStructureHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getModel() {
    return model;
  }

  public java.lang.String getCode() {
    return code;
  }

  public java.lang.String getInputPath() {
    return inputPath;
  }

  public cz.mzk.editor.shared.rpc.MetadataBundle getBundle() {
    return bundle;
  }

  @Override
  protected void dispatch(CreateStructureHandler handler) {
    handler.onCreateStructure(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    CreateStructureEvent other = (CreateStructureEvent) obj;
    if (model == null) {
      if (other.model != null)
        return false;
    } else if (!model.equals(other.model))
      return false;
    if (code == null) {
      if (other.code != null)
        return false;
    } else if (!code.equals(other.code))
      return false;
    if (inputPath == null) {
      if (other.inputPath != null)
        return false;
    } else if (!inputPath.equals(other.inputPath))
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
    hashCode = (hashCode * 37) + (model == null ? 1 : model.hashCode());
    hashCode = (hashCode * 37) + (code == null ? 1 : code.hashCode());
    hashCode = (hashCode * 37) + (inputPath == null ? 1 : inputPath.hashCode());
    hashCode = (hashCode * 37) + (bundle == null ? 1 : bundle.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "CreateStructureEvent["
                 + model
                 + ","
                 + code
                 + ","
                 + inputPath
                 + ","
                 + bundle
    + "]";
  }
}
