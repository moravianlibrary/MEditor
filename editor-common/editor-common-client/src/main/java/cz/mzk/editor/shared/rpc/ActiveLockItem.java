/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
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

/**
 * @author Matous Jobanek
 * @version Dec 4, 2012
 */
public class ActiveLockItem
        implements Serializable {

    private static final long serialVersionUID = 8354536419891447539L;

    private String objectName;

    private String uuid;

    private String date;

    private String description;

    private String model;

    public ActiveLockItem() {
    }

    /**
     * @param objectName
     * @param uuid
     * @param date
     * @param description
     * @param model
     */
    public ActiveLockItem(String objectName, String uuid, String date, String description, String model) {
        super();
        this.objectName = objectName;
        this.uuid = uuid;
        this.date = date;
        this.description = description;
        this.model = model;
    }

    /**
     * @return the objectName
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param objectName
     *        the objectName to set
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /**
     * @param uuid
     *        the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @param date
     *        the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets the description.
     * 
     * @param description
     *        the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param model
     *        the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ActiveLockItem [objectName=" + objectName + ", uuid=" + uuid + ", date=" + date
                + ", description=" + description + ", model=" + model + "]";
    }

}
