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

import cz.mzk.editor.client.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class RecentlyModifiedItem.
 */
public class UserIdentity
        implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8562338344074874962L;

    /** The identities. */
    private List<String> identities;

    /** The type. */
    private Constants.USER_IDENTITY_TYPES type;

    /** The user id. */
    private Long userId;

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
    public UserIdentity(List<String> identities, Constants.USER_IDENTITY_TYPES type, Long userId) {
        super();
        this.identities = identities;
        this.type = type;
        this.userId = userId;
    }

    /**
     * @return the identities
     */
    public List<String> getIdentities() {
        return identities;
    }

    /**
     * @param identities
     *        the identities to set
     */
    public void setIdentities(List<String> identities) {
        this.identities = identities;
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
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((identities == null) ? 0 : identities.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        UserIdentity other = (UserIdentity) obj;
        if (identities == null) {
            if (other.identities != null) return false;
        } else if (!identities.equals(other.identities)) return false;
        if (type != other.type) return false;
        if (userId == null) {
            if (other.userId != null) return false;
        } else if (!userId.equals(other.userId)) return false;
        return true;
    }

}