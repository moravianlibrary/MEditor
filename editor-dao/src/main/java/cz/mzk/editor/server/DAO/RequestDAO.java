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

package cz.mzk.editor.server.DAO;

import java.util.ArrayList;

import cz.mzk.editor.shared.rpc.RequestItem;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserDAO.
 */
public interface RequestDAO {

    /**
     * Adds the open id request.
     * 
     * @param name
     *        the name
     * @param openID
     *        the open id
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     */
    boolean addOpenIDRequest(String name, String openID) throws DatabaseException;

    /**
     * Removes the open id request.
     * 
     * @param id
     *        the id
     * @throws DatabaseException
     *         the database exception
     */
    void removeOpenIDRequest(long id) throws DatabaseException;

    /**
     * Gets the all open id requests.
     * 
     * @return the all open id requests
     * @throws DatabaseException
     *         the database exception
     */
    ArrayList<RequestItem> getAllOpenIDRequests() throws DatabaseException;

}
