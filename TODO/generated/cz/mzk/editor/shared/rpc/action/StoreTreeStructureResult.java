package cz.mzk.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.Result;

public class StoreTreeStructureResult implements Result { 

  private java.util.ArrayList<cz.mzk.editor.shared.rpc.TreeStructureBundle.TreeStructureInfo> infos;
  private java.util.ArrayList<cz.mzk.editor.shared.rpc.TreeStructureBundle.TreeStructureNode> nodes;

  public StoreTreeStructureResult(java.util.ArrayList<cz.mzk.editor.shared.rpc.TreeStructureBundle.TreeStructureInfo> infos, java.util.ArrayList<cz.mzk.editor.shared.rpc.TreeStructureBundle.TreeStructureNode> nodes) {
    this.infos = infos;
    this.nodes = nodes;
  }

  protected StoreTreeStructureResult() {
    // Possibly for serialization.
  }

  public java.util.ArrayList<cz.mzk.editor.shared.rpc.TreeStructureBundle.TreeStructureInfo> getInfos() {
    return infos;
  }

  public java.util.ArrayList<cz.mzk.editor.shared.rpc.TreeStructureBundle.TreeStructureNode> getNodes() {
    return nodes;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    StoreTreeStructureResult other = (StoreTreeStructureResult) obj;
    if (infos == null) {
      if (other.infos != null)
        return false;
    } else if (!infos.equals(other.infos))
      return false;
    if (nodes == null) {
      if (other.nodes != null)
        return false;
    } else if (!nodes.equals(other.nodes))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (infos == null ? 1 : infos.hashCode());
    hashCode = (hashCode * 37) + (nodes == null ? 1 : nodes.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "StoreTreeStructureResult["
                 + infos
                 + ","
                 + nodes
    + "]";
  }
}
