/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.fi.muni.xkremser.editor.shared.rpc;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDigitalObjectDetail.
 */
public class DigitalObjectDetail
        implements IsSerializable {

    /** The foxml. */
    private String foxmlString;

    /** The ocr. */
    private String ocr;

    /** The tei. */
    private String tei;

    /** The items. */
    private List<DigitalObjectDetail> items;

    private List<List<DigitalObjectDetail>> allItems;

    /** The related. */
    private ArrayList<ArrayList<String>> related;

    /** The uuid. */
    private String uuid;

    /** The dc changed. */
    private boolean dcChanged;

    /** The mods changed. */
    private boolean modsChanged;

    /** The ocr changed. */
    private boolean ocrChanged;

    /** Whether there was any ocr before the change */
    private boolean thereWasAnyOcr;

    private boolean itemsChanged;

    private String label;

    private boolean labelChanged;

    private DublinCore dc;

    private String firstPageURL;

    private ModsCollectionClient mods;

    private DigitalObjectModel model;

    private LockInfo lockInfo;

    public boolean thereWasAnyOcr() {
        return thereWasAnyOcr;
    }

    public void setThereWasAnyOcr(boolean thereWasAnyOcr) {
        this.thereWasAnyOcr = thereWasAnyOcr;
    }

    public DigitalObjectDetail() {

    }

    /**
     * Instantiates a new abstract digital object detail.
     * 
     * @param related
     *        the related
     */
    public DigitalObjectDetail(DigitalObjectModel model, ArrayList<ArrayList<String>> related) {
        this.model = model;
        this.related = related;
    }

    /**
     * Gets the model.
     * 
     * @return the model
     */
    public DigitalObjectModel getModel() {
        return model;
    }

    /**
     * Gets the related.
     * 
     * @return the related
     */
    public ArrayList<ArrayList<String>> getRelated() {
        return related;
    }

    /**
     * Sets the dc.
     * 
     * @param dc
     *        the new dc
     */
    public void setDc(DublinCore dc) {
        this.dc = dc;
    }

    /**
     * Gets the dc.
     * 
     * @return the dc
     */
    public DublinCore getDc() {
        return dc;
    }

    /**
     * Sets the mods.
     * 
     * @param mods
     *        the new mods
     */
    public void setMods(ModsCollectionClient mods) {
        this.mods = mods;
    }

    /**
     * Gets the mods.
     * 
     * @return the mods
     */
    public ModsCollectionClient getMods() {
        return mods;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "uuid" + getUuid() + " model: " + getModel() + "\nItems: " + getItems();
    }

    /**
     * Gets the foxmlString.
     * 
     * @return the foxmlString
     */
    public String getFoxmlString() {
        return foxmlString;
    }

    /**
     * Sets the foxmlString.
     * 
     * @param foxmlString
     *        the new foxmlString
     */
    public void setFoxmlString(String foxmlString) {
        this.foxmlString = foxmlString;
    }

    /**
     * Gets the label.
     * 
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label.
     * 
     * @param foxml
     *        the new label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Checks if is label changed.
     * 
     * @return true, if is label changed
     */
    public boolean isLabelChanged() {
        return labelChanged;
    }

    /**
     * Sets the label changed.
     * 
     * @param labelChnged
     *        the new label changed
     */
    public void setLabelChanged(boolean labelChanged) {
        this.labelChanged = labelChanged;
    }

    /**
     * Gets the ocr.
     * 
     * @return the ocr
     */
    public String getOcr() {
        return ocr;
    }

    /**
     * Sets the ocr.
     * 
     * @param ocr
     *        the new ocr
     */
    public void setOcr(String ocr) {
        this.ocr = ocr;
    }

    /**
     * Sets the related.
     * 
     * @param related
     *        the new related
     */
    public void setRelated(ArrayList<ArrayList<String>> related) {
        this.related = related;
    }

    /**
     * Gets the uuid.
     * 
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the uuid.
     * 
     * @param uuid
     *        the new uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Checks if is dc changed.
     * 
     * @return true, if is dc changed
     */
    public boolean isDcChanged() {
        return dcChanged;
    }

    /**
     * Sets the dc changed.
     * 
     * @param dcChanged
     *        the new dc changed
     */
    public void setDcChanged(boolean dcChanged) {
        this.dcChanged = dcChanged;
    }

    /**
     * Checks if is mods changed.
     * 
     * @return true, if is mods changed
     */
    public boolean isModsChanged() {
        return modsChanged;
    }

    /**
     * Sets the mods changed.
     * 
     * @param modsChanged
     *        the new mods changed
     */
    public void setModsChanged(boolean modsChanged) {
        this.modsChanged = modsChanged;
    }

    /**
     * Checks if is ocr changed.
     * 
     * @return true, if is ocr changed
     */
    public boolean isOcrChanged() {
        return ocrChanged;
    }

    /**
     * Sets the ocr changed.
     * 
     * @param ocrChanged
     *        the new ocr changed
     */
    public void setOcrChanged(boolean ocrChanged) {
        this.ocrChanged = ocrChanged;
    }

    public String getTei() {
        return tei;
    }

    public void setTei(String tei) {
        this.tei = tei;
    }

    public List<DigitalObjectDetail> getItems() {
        return items;
    }

    public void setItems(List<DigitalObjectDetail> items) {
        this.items = items;
    }

    public boolean getItemsChanged() {
        return itemsChanged;
    }

    public void setItemsChanged(boolean itemsChanged) {
        this.itemsChanged = itemsChanged;
    }

    public void setModel(DigitalObjectModel model) {
        this.model = model;
    }

    public List<List<DigitalObjectDetail>> getAllItems() {
        return allItems;
    }

    public void setAllItems(List<List<DigitalObjectDetail>> allItems) {
        this.allItems = allItems;
    }

    public String getFirstPageURL() {
        return firstPageURL;
    }

    public void setFirstPageURL(String firstPageURL) {
        this.firstPageURL = firstPageURL;
    }

    /**
     * @return the lockInfo
     */

    public LockInfo getLockInfo() {
        return lockInfo;
    }

    /**
     * @param lockInfo
     *        the lockInfo to set
     */

    public void setLockInfo(LockInfo lockInfo) {
        this.lockInfo = lockInfo;
    }

}