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

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class InputQueueItem.
 */
public class ImageItem
        implements IsSerializable {

    /** The path. */
    private String identifier;

    /** The name. */
    private String fsPath;

    /**
     * Instantiates a new input queue item.
     */
    @SuppressWarnings("unused")
    private ImageItem() {

    }

    /**
     * Instantiates a new input queue item.
     * 
     * @param path
     *        the path
     * @param issn
     *        the issn
     * @param name
     *        the name
     */
    public ImageItem(String identifier, String fsPath) {
        super();
        this.identifier = identifier;
        this.fsPath = fsPath;
    }

    /**
     * Gets the identifier.
     * 
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier.
     * 
     * @param identifier
     *        the new identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets the fsPath.
     * 
     * @return the fsPath
     */
    public String getFsPath() {
        return fsPath;
    }

    /**
     * Sets the path.
     * 
     * @param path
     *        the new path
     */
    public void setFsPath(String fsPath) {
        this.fsPath = fsPath;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "(" + this.identifier + ", " + this.fsPath + ")";
    }

}
