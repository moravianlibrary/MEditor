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
public class InputQueueItem
        implements IsSerializable {

    /** The path. */
    private String path;

    /** The name. */
    private String name;

    /** The issn. */
    private String issn;

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
     *        the path
     * @param issn
     *        the issn
     * @param name
     *        the name
     */
    public InputQueueItem(String path, String issn, String name) {
        super();
        this.path = path;
        this.name = name;
        this.issn = issn;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *        the new name
     */
    public void setName(String name) {
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
     * Gets the issn.
     * 
     * @return the issn
     */
    public String getIssn() {
        return issn;
    }

    /**
     * Sets the issn.
     * 
     * @param issn
     *        the new issn
     */
    public void setIssn(String issn) {
        this.issn = issn;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "(" + this.path + ", " + this.name + ", " + this.issn + ")";
    }

}
