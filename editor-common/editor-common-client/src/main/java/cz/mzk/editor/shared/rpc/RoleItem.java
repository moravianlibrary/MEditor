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
 * The Class RecentlyModifiedItem.
 */
public class RoleItem
        implements Serializable {

    private static final long serialVersionUID = 1940732012984034268L;

    /** The user id. */
    private Long userId;

    /** The name. */
    private String name;

    /** The description. */
    private String description;

    // @SuppressWarnings("unused")
    /**
     * Instantiates a new recently modified item.
     */
    public RoleItem() {

    }

    /**
     * Instantiates a new role item.
     * 
     * @param userId
     *        the user id
     * @param name
     *        the name
     * @param description
     *        the description
     */
    public RoleItem(Long userId, String name, String description) {
        super();
        this.userId = userId;
        this.name = name;
        this.description = description;
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
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
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
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *        the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RoleItem other = (RoleItem) obj;
        if (description == null) {
            if (other.description != null) return false;
        } else if (!description.equals(other.description)) return false;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        if (userId == null) {
            if (other.userId != null) return false;
        } else if (!userId.equals(other.userId)) return false;
        return true;
    }

}