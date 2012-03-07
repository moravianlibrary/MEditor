package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class FindAltoOcrFilesResult implements Result { 

  private java.util.List<java.lang.String> altoFileNames;
  private java.util.List<java.lang.String> ocrFileNames;

  public FindAltoOcrFilesResult(java.util.List<java.lang.String> altoFileNames, java.util.List<java.lang.String> ocrFileNames) {
    this.altoFileNames = altoFileNames;
    this.ocrFileNames = ocrFileNames;
  }

  protected FindAltoOcrFilesResult() {
    // Possibly for serialization.
  }

  public java.util.List<java.lang.String> getAltoFileNames() {
    return altoFileNames;
  }

  public java.util.List<java.lang.String> getOcrFileNames() {
    return ocrFileNames;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    FindAltoOcrFilesResult other = (FindAltoOcrFilesResult) obj;
    if (altoFileNames == null) {
      if (other.altoFileNames != null)
        return false;
    } else if (!altoFileNames.equals(other.altoFileNames))
      return false;
    if (ocrFileNames == null) {
      if (other.ocrFileNames != null)
        return false;
    } else if (!ocrFileNames.equals(other.ocrFileNames))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (altoFileNames == null ? 1 : altoFileNames.hashCode());
    hashCode = (hashCode * 37) + (ocrFileNames == null ? 1 : ocrFileNames.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "FindAltoOcrFilesResult["
                 + altoFileNames
                 + ","
                 + ocrFileNames
    + "]";
  }
}
