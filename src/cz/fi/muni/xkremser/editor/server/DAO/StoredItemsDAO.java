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

package cz.fi.muni.xkremser.editor.server.DAO;

import java.util.List;

import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

import cz.fi.muni.xkremser.editor.shared.rpc.StoredItem;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public interface StoredItemsDAO {

    /**
     * @param userId
     * @return The list of the stored items
     * @throws DatabaseException
     */

    List<StoredItem> getStoredItems(long userId) throws DatabaseException;

    /**
     * @param userId
     * @param storedItem
     * @return whether the operation was successful
     * @throws DatabaseException
     */

    boolean storeDigitalObject(long userId, StoredItem storedItem) throws DatabaseException;

    /**
     * @param fileName
     * @return whether the operation was successful
     * @throws DatabaseException
     */

    public boolean deleteItem(String fileName) throws DatabaseException;

}
