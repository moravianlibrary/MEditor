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

package cz.fi.muni.xkremser.editor.client.view;

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
     * @param name
     *        the name
     * @param barcode
     *        the barcode
     * @param picture
     *        the picture
     */
    public ScanRecord(String name, String model, String barcode, String picture) {
        this(name, model, barcode, picture, null);
    }

    /**
     * Instantiates a new page record.
     * 
     * @param name
     *        the name
     * @param issn
     *        the issn
     * @param picture
     *        the picture
     * @param description
     *        the description
     */
    public ScanRecord(String name, String model, String barcode, String picture, String description) {
        setName(name);
        setModel(model);
        setBarcode(barcode);
        setPicture(picture);
        setDescription(description);
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
     * Set the description.
     * 
     * @param description
     *        the description
     */
    public void setDescription(String description) {
        setAttribute(Constants.ATTR_DESC, description);
    }

    /**
     * Return the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return getAttribute(Constants.ATTR_DESC);
    }

    /**
     * Deep copy.
     * 
     * @return the page record
     */
    public ScanRecord deepCopy() {
        return new ScanRecord(getName(), getBarcode(), getPicture(), getDescription());
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PageRecord [getName()=" + getName() + ", getModel()=" + getModel() + ", getBarcode()="
                + getBarcode() + ", getPicture()=" + getPicture() + ", getDescription()=" + getDescription()
                + "]";
    }

}
