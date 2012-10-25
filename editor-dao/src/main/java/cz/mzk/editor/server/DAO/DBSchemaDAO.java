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

    /**
     * Gets the all data from table.
     * 
     * @param tableName
     *        the table name
     * @return the all data from table
     * @throws ClassNotFoundException
     *         the class not found exception
     */
    Map<Long, Object[]> getAllDataFromTable(String tableName) throws ClassNotFoundException;

    /**
     * Transform and put description.
     * 
     * @param map
     *        the map
     * @throws DatabaseException
     *         the database exception
     */
    void transformAndPutDescription(Map<Long, Object[]> map) throws DatabaseException;

    /**
     * Transform and put editor user.
     * 
     * @param map
     *        the map
     * @return the map
     * @throws DatabaseException
     *         the database exception
     */
    Map<Long, Long> transformAndPutEditorUser(Map<Long, Object[]> map) throws DatabaseException;

    /**
     * Transform and put image.
     * 
     * @param map
     *        the map
     * @throws DatabaseException
     *         the database exception
     */
    void transformAndPutImage(Map<Long, Object[]> map) throws DatabaseException;

    /**
     * Transform and put input queue item.
     * 
     * @param map
     *        the map
     * @throws DatabaseException
     *         the database exception
     */
    void transformAndPutInputQueueItem(Map<Long, Object[]> map) throws DatabaseException;

    /**
     * Transform and put input queue.
     * 
     * @param map
     *        the map
     * @throws DatabaseException
     *         the database exception
     */
    void transformAndPutInputQueue(Map<Long, Object[]> map) throws DatabaseException;

    /**
     * Transform and put open id identity.
     * 
     * @param map
     *        the map
     * @param editorUserIdMapping
     *        the editor user id mapping
     * @throws NumberFormatException
     *         the number format exception
     * @throws DatabaseException
     *         the database exception
     */
    void transformAndPutOpenIdIdentity(Map<Long, Object[]> map, Map<Long, Long> editorUserIdMapping)
            throws NumberFormatException, DatabaseException;

    /**
     * Transform and recently modified.
     * 
     * @param map
     *        the map
     * @param editorUserIdMapping
     *        the editor user id mapping
     * @throws NumberFormatException
     *         the number format exception
     * @throws DatabaseException
     *         the database exception
     */
    void transformAndRecentlyModified(Map<Long, Object[]> map, Map<Long, Long> editorUserIdMapping)
            throws NumberFormatException, DatabaseException;

    /**
     * Transform and put request for adding.
     * 
     * @param map
     *        the map
     * @throws DatabaseException
     *         the database exception
     */
    void transformAndPutRequestForAdding(Map<Long, Object[]> map) throws DatabaseException;

    /**
     * Transform and put stored files.
     * 
     * @param map
     *        the map
     * @param editorUserIdMapping
     *        the editor user id mapping
     * @throws DatabaseException
     *         the database exception
     */
    void transformAndPutStoredFiles(Map<Long, Object[]> map, Map<Long, Long> editorUserIdMapping)
            throws DatabaseException;

    /**
     * Transform and put tree structure.
     * 
     * @param map
     *        the map
     * @param editorUserIdMapping
     *        the editor user id mapping
     * @return the map
     * @throws DatabaseException
     *         the database exception
     */
    Map<Long, Long> transformAndPutTreeStructure(Map<Long, Object[]> map, Map<Long, Long> editorUserIdMapping)
            throws DatabaseException;

    /**
     * Transform and put tree struc node.
     * 
     * @param map
     *        the map
     * @param treeStrucIdMapping
     *        the tree struc id mapping
     * @throws NumberFormatException
     *         the number format exception
     * @throws DatabaseException
     *         the database exception
     */
    void transformAndPutTreeStrucNode(Map<Long, Object[]> map, Map<Long, Long> treeStrucIdMapping)
            throws NumberFormatException, DatabaseException;

}
