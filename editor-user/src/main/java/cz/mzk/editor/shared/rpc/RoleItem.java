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

import java.util.List;

import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;

// TODO: Auto-generated Javadoc
/**
 * The Class RecentlyModifiedItem.
 */
public class RoleItem
        implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1940732012984034268L;

    /** The user id. */
    private Long userId;

    /** The name. */
    private String name;

    /** The description. */
    private String description;

    /** The rights. */
    private List<EDITOR_RIGHTS> rights;

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
     * Instantiates a new role item.
     * 
     * @param name
     *        the name
     * @param description
     *        the description
     * @param rights
     *        the rights
     */
    public RoleItem(String name, String description, List<EDITOR_RIGHTS> rights) {
        super();
        this.name = name;
        this.description = description;
        this.rights = rights;
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
     * @param rights
     *        the rights
     */
    public RoleItem(Long userId, String name, String description, List<EDITOR_RIGHTS> rights) {
        super();
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.rights = rights;
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
     * Gets the user id.
     * 
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     * 
     * @param userId
     *        the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the rights
     */
    public List<EDITOR_RIGHTS> getRights() {
        return rights;
    }

    /**
     * @param rights
     *        the rights to set
     */
    public void setRights(List<EDITOR_RIGHTS> rights) {
        this.rights = rights;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "RoleItem [userId=" + userId + ", name=" + name + ", description=" + description + ", rights="
                + rights + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        return true;
    }

}