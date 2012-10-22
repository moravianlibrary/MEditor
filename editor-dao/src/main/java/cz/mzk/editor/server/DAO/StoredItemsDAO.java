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

package cz.mzk.editor.server.DAO;

import java.util.List;

import cz.mzk.editor.shared.rpc.StoredItem;

// TODO: Auto-generated Javadoc
/**
 * The Interface StoredItemsDAO.
 * 
 * @author Matous Jobanek
 * @version $Id$
 */

public interface StoredItemsDAO {

    /**
     * Gets the stored items.
     * 
     * @param userId
     *        the user id
     * @return the stored items
     * @throws DatabaseException
     *         the database exception
     */
    List<StoredItem> getStoredItems(long userId) throws DatabaseException;

    /**
     * Check stored digital object.
     * 
     * @param userId
     *        the user id
     * @param storedItem
     *        the stored item
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     */
    boolean checkStoredDigitalObject(long userId, StoredItem storedItem) throws DatabaseException;

    /**
     * Delete item.
     * 
     * @param id
     *        the id
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     */
    public boolean deleteItem(Long id) throws DatabaseException;

}
