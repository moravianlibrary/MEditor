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

import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;

/**
 * @author Matous Jobanek
 * @version Nov 15, 2012
 */
public interface SecurityUserDAO {

    /**
     * Gets the user id.
     * 
     * @param identifier
     *        the identifier
     * @param type
     *        the type
     * @return the user id
     * @throws DatabaseException
     *         the database exception
     */
    Long getUserId(String identifier, USER_IDENTITY_TYPES type) throws DatabaseException;

    /**
     * Gets the name.
     * 
     * @param userId
     *        the user id
     * @return the name
     * @throws DatabaseException
     *         the database exception
     */
    String getName(Long userId) throws DatabaseException;
}
