
package cz.fi.muni.xkremser.editor.client.view.other;

import java.util.List;
import java.util.Map;

import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tile.TileGrid;

import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;

public class EditorTabSet
        extends TabSet {

    private TextAreaItem ocrContent;

    private DCTab dcTab;

    private Tab modsTab;

    private InfoTab infoTab;

    private DublinCore dc;

    private ModsCollectionClient modsCollection;

    private List<Tab> itemTab;

    private Map<DigitalObjectModel, TileGrid> itemGrid;

    private String uuid;

    private TileGrid tileGrid;

    private String lockOwner;

    private String lockDescription;

    public String getLockOwner() {
        return lockOwner;
    }

    public void setLockOwner(String lockOwner) {
        this.lockOwner = lockOwner;
    }

    public String getLockDescription() {
        return lockDescription;
    }

    public void setLockDescription(String lockDescription) {
        this.lockDescription = lockDescription;
    }

    public TileGrid getTileGrid() {
        return tileGrid;
    }

    public void setTileGrid(TileGrid tileGrid) {
        this.tileGrid = tileGrid;
    }

    public TextAreaItem getOcrContent() {
        return ocrContent;
    }

    public void setOcrContent(TextAreaItem ocrContent) {
        this.ocrContent = ocrContent;
    }

    public DCTab getDcTab() {
        return dcTab;
    }

    public void setDcTab(DCTab dcTab) {
        this.dcTab = dcTab;
    }

    public Tab getModsTab() {
        return modsTab;
    }

    public void setModsTab(Tab modsTab) {
        this.modsTab = modsTab;
    }

    public InfoTab getInfoTab() {
        return infoTab;
    }

    public void setInfoTab(InfoTab infoTab) {
        this.infoTab = infoTab;
    }

    public DublinCore getDc() {
        return dc;
    }

    public void setDc(DublinCore dc) {
        this.dc = dc;
    }

    public ModsCollectionClient getModsCollection() {
        return modsCollection;
    }

    public void setModsCollection(ModsCollectionClient modsCollection) {
        this.modsCollection = modsCollection;
    }

    public List<Tab> getItemTab() {
        return itemTab;
    }

    public void setItemTab(List<Tab> itemTab) {
        this.itemTab = itemTab;
    }

    public Map<DigitalObjectModel, TileGrid> getItemGrid() {
        return itemGrid;
    }

    public void setItemGrid(Map<DigitalObjectModel, TileGrid> itemGrid) {
        this.itemGrid = itemGrid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
