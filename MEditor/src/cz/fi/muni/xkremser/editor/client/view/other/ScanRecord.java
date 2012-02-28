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

package cz.fi.muni.xkremser.editor.client.view.other;

import com.smartgwt.client.widgets.tile.TileRecord;

import cz.fi.muni.xkremser.editor.client.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class PageRecord.
 */
public class ScanRecord
        extends TileRecord {

    /**
     * Instantiates a new page record.
     */
    public ScanRecord() {
    }

    /**
     * Instantiates a new page record.
     * 
     * @param scanIndex
     *        the scanIndex
     * @param name
     *        the name
     * @param issn
     *        the issn
     * @param picture
     *        the picture
     * @param path
     *        the path
     */
    public ScanRecord(int scanIndex,
                      String name,
                      String model,
                      String barcode,
                      String picture,
                      String path,
                      String pageType) {
        setScanIndex(scanIndex);
        setName(name);
        setModel(model);
        setBarcode(barcode);
        setPicture(picture);
        setPath(path);
        setPageType(pageType);
    }

    /**
     * Set the scanIndex.
     * 
     * @param scanIndex
     *        the scanIndex
     */
    public void setScanIndex(int scanIndex) {
        setAttribute(Constants.ATTR_SCAN_INDEX, scanIndex);
    }

    /**
     * Return the scanIndex.
     * 
     * @return the scanIndex
     */
    public int getScanIndex() {
        return getAttributeAsInt(Constants.ATTR_SCAN_INDEX);
    }

    /**
     * Set the name.
     * 
     * @param name
     *        the name
     */
    public void setName(String name) {
        setAttribute(Constants.ATTR_NAME, name);
    }

    /**
     * Return the name.
     * 
     * @return the name
     */
    public String getName() {
        return getAttribute(Constants.ATTR_NAME);
    }

    /**
     * Set the model.
     * 
     * @param model
     *        the model
     */
    public void setModel(String model) {
        setAttribute(Constants.ATTR_MODEL, model);
    }

    /**
     * Return the model.
     * 
     * @return the model
     */
    public String getModel() {
        return getAttribute(Constants.ATTR_MODEL);
    }

    /**
     * Set the barcode.
     * 
     * @param barcode
     *        the barcode
     */
    public void setBarcode(String barcode) {
        setAttribute(Constants.ATTR_BARCODE, barcode);
    }

    /**
     * Return the barcode.
     * 
     * @return the barcode
     */
    public String getBarcode() {
        return getAttribute(Constants.ATTR_BARCODE);
    }

    /**
     * Set the picture.
     * 
     * @param picture
     *        the picture
     */
    public void setPicture(String picture) {
        setAttribute(Constants.ATTR_PICTURE, picture);
    }

    /**
     * Return the picture.
     * 
     * @return the picture
     */
    public String getPicture() {
        return getAttribute(Constants.ATTR_PICTURE);
    }

    /**
     * Set the path.
     * 
     * @param path
     *        the path
     */
    public void setPath(String path) {
        setAttribute(Constants.ATTR_PATH, path);
    }

    /**
     * Return the path.
     * 
     * @return the path
     */
    public String getPath() {
        return getAttribute(Constants.ATTR_PATH);
    }

    /**
     * Set the pageType.
     * 
     * @param pageType
     *        the pageType
     */
    public void setPageType(String pageType) {
        setAttribute(Constants.ATTR_PAGE_TYPE, pageType);
    }

    /**
     * Return the pageType.
     * 
     * @return the pageType
     */
    public String getPageType() {
        return getAttribute(Constants.ATTR_PAGE_TYPE);
    }

    /**
     * Deep copy.
     * 
     * @return the page record
     */
    public ScanRecord deepCopy() {
        return new ScanRecord(getScanIndex(),
                              getName(),
                              getModel(),
                              getBarcode(),
                              getPicture(),
                              getPath(),
                              getPageType());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String toString() {
        return "ScanRecord [getScanIndex()=" + getScanIndex() + ", getName()=" + getName() + ", getModel()="
                + getModel() + ", getBarcode()=" + getBarcode() + ", getPicture()=" + getPicture()
                + ", getPath()=" + getPath() + ", getPageType()=" + getPageType() + "]";
    }

}
