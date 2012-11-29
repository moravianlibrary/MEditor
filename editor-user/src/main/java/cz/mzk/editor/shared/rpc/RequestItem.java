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
 * The Class RequestItem.
 */
public class RequestItem
        implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6447185495167665282L;

    /** The id. */
    private Long id;

    /** The user name. */
    private String userName;

    /** The user id. */
    private Long userId;

    /** The object. */
    private String object;

    /** The timestamp. */
    private String timestamp;

    /** The description. */
    private String description;

    /** The type. */
    private String type;

    /**
     * Instantiates a new request item.
     */
    public RequestItem() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new request item.
     * 
     * @param id
     *        the id
     * @param userName
     *        the user name
     * @param userId
     *        the user id
     * @param object
     *        the object
     * @param timestamp
     *        the timestamp
     * @param description
     *        the description
     * @param type
     *        the type
     */
    public RequestItem(Long id,
                       String userName,
                       Long userId,
                       String object,
                       String timestamp,
                       String description,
                       String type) {
        super();
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.object = object;
        this.timestamp = timestamp;
        this.description = description;
        this.type = type;
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the user name.
     * 
     * @return the userName
     */
    public String getUserName() {
        return userName;
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
     * Gets the object.
     * 
     * @return the object
     */
    public String getObject() {
        return object;
    }

    /**
     * Gets the timestamp.
     * 
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
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
     * Gets the type.
     * 
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *        the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the user name.
     * 
     * @param userName
     *        the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * Sets the object.
     * 
     * @param object
     *        the object to set
     */
    public void setObject(String object) {
        this.object = object;
    }

    /**
     * Sets the timestamp.
     * 
     * @param timestamp
     *        the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Sets the description.
     * 
     * @param description
     *        the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the type.
     * 
     * @param type
     *        the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "RequestItem [id=" + id + ", userName=" + userName + ", userId=" + userId + ", object="
                + object + ", timestamp=" + timestamp + ", description=" + description + ", type=" + type
                + "]";
    }

}
