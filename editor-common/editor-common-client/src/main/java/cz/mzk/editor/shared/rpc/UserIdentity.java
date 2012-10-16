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

import cz.mzk.editor.client.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class RecentlyModifiedItem.
 */
public class UserIdentity
        implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8562338344074874962L;

    /** The identity. */
    private String identity;

    /** The type. */
    private Constants.USER_IDENTITY_TYPES type;

    /** The user id. */
    private Long userId;

    // @SuppressWarnings("unused")
    /**
     * Instantiates a new recently modified item.
     */
    public UserIdentity() {

    }

    /**
     * Instantiates a new user identity.
     * 
     * @param identity
     *        the identity
     * @param type
     *        the type
     * @param userId
     *        the user id
     */
    public UserIdentity(String identity, Constants.USER_IDENTITY_TYPES type, Long userId) {
        super();
        this.identity = identity;
        this.type = type;
        this.userId = userId;
    }

    /**
     * Gets the identity.
     * 
     * @return the identity
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * Sets the identity.
     * 
     * @param identity
     *        the new identity
     */
    public void setIdentity(String identity) {
        this.identity = identity;
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public Constants.USER_IDENTITY_TYPES getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type
     *        the type to set
     */
    public void setType(Constants.USER_IDENTITY_TYPES type) {
        this.type = type;
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
     * Hash code.
     * 
     * @return the int {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((identity == null) ? 0 : identity.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /**
     * Equals.
     * 
     * @param obj
     *        the obj
     * @return true, if successful {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        UserIdentity other = (UserIdentity) obj;
        if (identity == null) {
            if (other.identity != null) return false;
        } else if (!identity.equals(other.identity)) return false;
        if (type != other.type) return false;
        return true;
    }

}