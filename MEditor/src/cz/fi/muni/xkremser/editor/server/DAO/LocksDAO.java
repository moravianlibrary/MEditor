/*
 * Metadata Editor
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

import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

/**
 * @author Jiri Kremser
 * @version $Id$
 */

public interface LocksDAO {

    /**
     * Tries to lock a digital object with the given
     * <code>uuid<code>, if fails returns the id of owner of lock, if is successful returns <code>0<code>
     * 
     * @param uuid
     *        the uuid of digital object
     * @param id
     *        the id of the user
     * @param description
     *        the lock-description
     * @return whether locking was successful
     * @throws DatabaseException
     */
    boolean lockDigitalObject(String uuid, Long id, String description) throws DatabaseException;

    boolean unlockDigitalObjectWithUuid(String uuid) throws DatabaseException;

    /**
     * Gets the id of owner of lock
     * 
     * @param uuid
     *        the uuid of digital object
     * @return id the id of owner of lock, <code>id == 0<code> when there is no
     *         lock (no owner) and the locking was successful, <code> id &lt 0
     *         <code> when there has occurred any other problem and the DB has
     *         not been updated
     * @throws DatabaseException
     */
    long getLockOwnersID(String uuid) throws DatabaseException;

    String getDescription(String uuid) throws DatabaseException;

    //    boolean unlockDigitalObjectsWithOpenId(String openId) throws DatabaseException;

}
