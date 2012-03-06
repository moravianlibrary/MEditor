package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class DownloadDigitalObjectDetailResult implements Result { 

  private java.lang.String[] stringsWithXml;

  public DownloadDigitalObjectDetailResult(java.lang.String[] stringsWithXml) {
    this.stringsWithXml = stringsWithXml;
  }

  protected DownloadDigitalObjectDetailResult() {
    // Possibly for serialization.
  }

  public java.lang.String[] getStringsWithXml() {
    return stringsWithXml;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    DownloadDigitalObjectDetailResult other = (DownloadDigitalObjectDetailResult) obj;
    if (stringsWithXml == null) {
      if (other.stringsWithXml != null)
        return false;
    } else if (!stringsWithXml.equals(other.stringsWithXml))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + java.util.Arrays.deepHashCode(stringsWithXml);
    return hashCode;
  }

  @Override
  public String toString() {
    return "DownloadDigitalObjectDetailResult["
                 + stringsWithXml
    + "]";
  }
}
