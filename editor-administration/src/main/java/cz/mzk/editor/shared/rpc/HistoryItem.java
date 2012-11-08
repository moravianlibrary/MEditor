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

import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;

/**
 * @author Matous Jobanek
 * @version Oct 31, 2012
 */
public class HistoryItem
        implements Serializable, Comparable<HistoryItem> {

    private static final long serialVersionUID = 8547925472051690544L;

    private Long id;
    private String timestamp;
    private CRUD_ACTION_TYPES action;
    private String tableName;
    private String object;
    private boolean moreInformation;

    /**
     * Instantiates a new history item.
     */
    public HistoryItem() {
    }

    /**
     * @param id
     * @param timestamp
     * @param action
     * @param tableName
     * @param object
     * @param moreInformation
     */
    public HistoryItem(Long id,
                       String timestamp,
                       CRUD_ACTION_TYPES action,
                       String tableName,
                       String object,
                       boolean moreInformation) {
        super();
        this.id = id;
        this.timestamp = timestamp;
        this.action = action;
        this.tableName = tableName;
        this.object = object;
        this.moreInformation = moreInformation;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @return the action
     */
    public CRUD_ACTION_TYPES getAction() {
        return action;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @return the object
     */
    public String getObject() {
        return object;
    }

    /**
     * @return the moreInformation
     */
    public boolean isMoreInformation() {
        return moreInformation;
    }

    /**
     * @param id
     *        the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param timestamp
     *        the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @param action
     *        the action to set
     */
    public void setAction(CRUD_ACTION_TYPES action) {
        this.action = action;
    }

    /**
     * @param tableName
     *        the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @param object
     *        the object to set
     */
    public void setObject(String object) {
        this.object = object;
    }

    /**
     * @param moreInformation
     *        the moreInformation to set
     */
    public void setMoreInformation(boolean moreInformation) {
        this.moreInformation = moreInformation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((action == null) ? 0 : action.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + (moreInformation ? 1231 : 1237);
        result = prime * result + ((object == null) ? 0 : object.hashCode());
        result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
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
        HistoryItem other = (HistoryItem) obj;
        if (action != other.action) return false;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        if (moreInformation != other.moreInformation) return false;
        if (object == null) {
            if (other.object != null) return false;
        } else if (!object.equals(other.object)) return false;
        if (tableName == null) {
            if (other.tableName != null) return false;
        } else if (!tableName.equals(other.tableName)) return false;
        if (timestamp == null) {
            if (other.timestamp != null) return false;
        } else if (!timestamp.equals(other.timestamp)) return false;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(HistoryItem hItem) {
        return this.getTimestamp().compareTo(hItem.getTimestamp());
    }

}