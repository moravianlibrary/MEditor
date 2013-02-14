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

package cz.mzk.editor.shared.rpc;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ImageItem.
 */
public class ImageItem
        implements Serializable {

    private static final long serialVersionUID = -3272785315302745954L;

    /** The identifier. */
    private String identifier;

    /** The jpeg2000FsPath. */
    private String jpeg2000FsPath; //TODO-MR: possible refactor. Audio fspath can be in this value :-(

    /** Two digit length of Audio file */
    private String length;

    /** The jpgFsPath. */
    private String jpgFsPath;

    /** Name of object on filesystem */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    /** Mime Type of File */
    private String mimeType = null;


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
     *        the identifier
     * @param issn
     *        the jpeg2000FsPath
     * @param jpgFsPath
     *        the jpgFsPath
     */
    public ImageItem(String identifier, String jpeg2000FsPath, String jpgFsPath) {
        super();
        this.identifier = identifier;
        this.jpeg2000FsPath = jpeg2000FsPath;
        this.jpgFsPath = jpgFsPath;
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
     * Gets the jpeg2000FsPath.
     * 
     * @return the jpeg2000FsPath
     */
    public String getJpeg2000FsPath() {
        return jpeg2000FsPath;
    }

    /**
     * Sets the jpeg2000FsPath.
     * 
     * @param jpeg2000FsPath
     *        the new jpeg2000FsPath
     */
    public void setJpeg2000FsPath(String jpeg2000FsPath) {
        this.jpeg2000FsPath = jpeg2000FsPath;
    }

    /**
     * Gets the jpgFsPath.
     * 
     * @return the jpgFsPath
     */
    public String getJpgFsPath() {
        return jpgFsPath;
    }

    /**
     * Sets the jpgFsPath.
     * 
     * @param jpgFsPath
     *        the new jpgFsPath
     */
    public void setJpgFsPath(String jpgFsPath) {
        this.jpgFsPath = jpgFsPath;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "(" + this.identifier + ", " + this.jpeg2000FsPath + ", " + this.jpgFsPath + ")";
    }

}
