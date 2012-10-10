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

import java.util.Map;

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
     * @return 1 if the version of the current deployed DB is the same as param
     *         version, 0 if the version of the current deployed DB is not same
     *         as param version, -1 if the version of the current deployed DB is
     *         the old version (there is no action table and so on)
     * @throws DatabaseException
     */
    int checkVersion(int version) throws DatabaseException;

    /**
     * Updates the schema and writes the new version number into DB
     * 
     * @param version
     *        version to which should be updated
     * @param pathPrefix
     *        the absolute path to the
     *        $TOMCAT_HOME/webapps/meditor/WEB-INF/classes
     * @throws DatabaseException
     */
    void updateSchema(int version, String pathPrefix) throws DatabaseException;

    Map<Long, Object[]> getAllDataFromTable(String tableName) throws ClassNotFoundException;

    /**
     * @param map
     * @throws DatabaseException
     */
    void transformAndPutDescription(Map<Long, Object[]> map) throws DatabaseException;

    /**
     * @param map
     * @return
     * @throws DatabaseException
     */
    Map<Long, Long> transformAndPutEditorUser(Map<Long, Object[]> map) throws DatabaseException;

    /**
     * @param map
     * @throws DatabaseException
     */
    void transformAndPutImage(Map<Long, Object[]> map) throws DatabaseException;

    /**
     * @param map
     * @throws DatabaseException
     */
    void transformAndPutInputQueueItem(Map<Long, Object[]> map) throws DatabaseException;

    /**
     * @param map
     * @throws DatabaseException
     */
    void transformAndPutInputQueue(Map<Long, Object[]> map) throws DatabaseException;

    /**
     * @param map
     * @param editorUserIdMapping
     * @throws DatabaseException
     * @throws NumberFormatException
     */
    void transformAndPutOpenIdIdentity(Map<Long, Object[]> map, Map<Long, Long> editorUserIdMapping)
            throws NumberFormatException, DatabaseException;

    /**
     * @param map
     * @param editorUserIdMapping
     * @throws DatabaseException
     * @throws NumberFormatException
     */
    void transformAndRecentlyModified(Map<Long, Object[]> map, Map<Long, Long> editorUserIdMapping)
            throws NumberFormatException, DatabaseException;

    /**
     * @param map
     * @throws DatabaseException
     */
    void transformAndPutRequestForAdding(Map<Long, Object[]> map) throws DatabaseException;

    /**
     * @param map
     * @param editorUserIdMapping
     * @throws DatabaseException
     */
    void transformAndPutStoredFiles(Map<Long, Object[]> map, Map<Long, Long> editorUserIdMapping)
            throws DatabaseException;

    /**
     * @param map
     * @param editorUserIdMapping
     * @return
     * @throws DatabaseException
     */
    Map<Long, Long> transformAndPutTreeStructure(Map<Long, Object[]> map, Map<Long, Long> editorUserIdMapping)
            throws DatabaseException;

    /**
     * @param map
     * @param treeStrucIdMapping
     * @throws DatabaseException
     * @throws NumberFormatException
     */
    void transformAndPutTreeStrucNode(Map<Long, Object[]> map, Map<Long, Long> treeStrucIdMapping)
            throws NumberFormatException, DatabaseException;

}
