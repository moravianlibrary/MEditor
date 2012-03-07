package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class GetFullImgMetadataResult implements Result { 

  private int height;
  private int width;

  public GetFullImgMetadataResult(int height, int width) {
    this.height = height;
    this.width = width;
  }

  protected GetFullImgMetadataResult() {
    // Possibly for serialization.
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetFullImgMetadataResult other = (GetFullImgMetadataResult) obj;
    if (height != other.height)
        return false;
    if (width != other.width)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + new Integer(height).hashCode();
    hashCode = (hashCode * 37) + new Integer(width).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetFullImgMetadataResult["
                 + height
                 + ","
                 + width
    + "]";
  }
}
