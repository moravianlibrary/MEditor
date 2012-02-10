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

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class InputQueueItem.
 */
public class InputQueueItem
        implements Serializable {

    private static final long serialVersionUID = -5593526588672194451L;

    /** The path. */
    private String path;

    /** The barcode. */
    private String barcode;

    /** The ingest info */
    private boolean ingestInfo = false;

    private String name;

    /**
     * Instantiates a new input queue item.
     */
    @SuppressWarnings("unused")
    private InputQueueItem() {

    }

    /**
     * Instantiates a new input queue item.
     * 
     * @param path
     * @param barcode
     * @param ingestInfo
     * @param name
     */

    public InputQueueItem(String path, String barcode, boolean ingestInfo, String name) {
        super();
        this.path = path;
        this.barcode = barcode;
        this.ingestInfo = ingestInfo;
        this.name = name;
    }

    /**
     * Gets the path.
     * 
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the path.
     * 
     * @param path
     *        the new path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Gets the barcode.
     * 
     * @return the barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Sets the barcode.
     * 
     * @param barcode
     *        the new barcode
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "(" + this.path + ", " + this.barcode + ", " + this.ingestInfo + ")";
    }

    /**
     * @return the ingestInfo
     */

    public boolean getIngestInfo() {
        return ingestInfo;
    }

    /**
     * @param ingestInfo
     *        the ingestInfo to set
     */

    public void setIngestInfo(boolean ingestInfo) {
        this.ingestInfo = ingestInfo;
    }

    /**
     * @return the name
     */

    public String getName() {
        return name;
    }

    /**
     * @param name
     *        the name to set
     */

    public void setName(String name) {
        this.name = name;
    }
}
