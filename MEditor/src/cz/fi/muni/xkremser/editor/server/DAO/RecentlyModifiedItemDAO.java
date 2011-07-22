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

package cz.fi.muni.xkremser.editor.server.DAO;

import java.util.ArrayList;

import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;

// TODO: Auto-generated Javadoc
/**
 * The Interface RecentlyModifiedItemDAO.
 */
public interface RecentlyModifiedItemDAO {

    /**
     * Put.
     * 
     * @param toPut
     *        the to put
     * @param openID
     *        the open id
     * @return true, if successful
     */
    boolean put(RecentlyModifiedItem toPut, String openID) throws DatabaseException;

    /**
     * Put description.
     * 
     * @param uuid
     *        the uuid
     * @param description
     *        the description
     * @return true, if successful
     */
    boolean putDescription(String uuid, String description) throws DatabaseException;

    /**
     * Gets the description.
     * 
     * @param uuid
     *        the uuid
     * @return the description
     */
    String getDescription(String uuid) throws DatabaseException;

    /**
     * Put user description.
     * 
     * @param openID
     *        the open id
     * @param uuid
     *        the uuid
     * @param description
     *        the description
     * @return true, if successful
     */
    boolean putUserDescription(String openID, String uuid, String description) throws DatabaseException;

    /**
     * Gets the user description.
     * 
     * @param openID
     *        the open id
     * @param uuid
     *        the uuid
     * @return the user description
     */
    String getUserDescription(String openID, String uuid) throws DatabaseException;

    /**
     * Gets the items.
     * 
     * @param nLatest
     *        the n latest
     * @param openID
     *        the open id
     * @return the items
     */
    ArrayList<RecentlyModifiedItem> getItems(int nLatest, String openID) throws DatabaseException;
}
