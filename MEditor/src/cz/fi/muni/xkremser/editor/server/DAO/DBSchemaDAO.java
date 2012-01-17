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

import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

/**
 * @author Jiri Kremser
 * @version 17. 1. 2011
 */
public interface DBSchemaDAO {

    /**
     * Check whether the DB is available for connecting
     * 
     * @return true if it is possible to connect to DB
     */
    boolean canConnect();

    /**
     * Check whether the DB has the same version as the specified parameter
     * 
     * @param version
     *        the version of DB
     * @return true if the version of the current deployed DB is the same as
     *         param version
     * @throws DatabaseException
     */
    boolean checkVersion(int version) throws DatabaseException;

    /**
     * Updates the schema and writes the new version number into DB
     * 
     * @param version
     *        version to which should be updated
     * @throws DatabaseException
     */
    void updateSchema(int version) throws DatabaseException;

}
