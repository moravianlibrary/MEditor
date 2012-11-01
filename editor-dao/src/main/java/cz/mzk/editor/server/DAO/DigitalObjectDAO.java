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

/**
 * @author Matous Jobanek
 * @version Oct 22, 2012
 */
public interface DigitalObjectDAO {

    /**
     * Delete digital object.
     * 
     * @param uuid
     *        the uuid
     * @param model
     *        the model
     * @param name
     *        the name
     * @param topObjectUuid
     *        the top object uuid
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     */
    boolean deleteDigitalObject(String uuid, String model, String name, String topObjectUuid)
            throws DatabaseException;

    /**
     * Insert new digital object.
     * 
     * @param uuid
     *        the uuid
     * @param model
     *        the model
     * @param name
     *        the name
     * @param input_queue_directory_path
     *        the input_queue_directory_path
     * @param top_digital_object_uuid
     *        the top_digital_object_uuid
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     */
    boolean insertNewDigitalObject(String uuid,
                                   String model,
                                   String name,
                                   String input_queue_directory_path,
                                   String top_digital_object_uuid) throws DatabaseException;

    /**
     * Update top object time.
     * 
     * @param uuid
     *        the uuid
     * @throws DatabaseException
     *         the database exception
     */
    void updateTopObjectTime(String uuid) throws DatabaseException;

    /**
     * Update top object uuid.
     * 
     * @param oldUuid
     *        the old uuid
     * @param newUuid
     *        the new uuid
     * @param lowerObj
     *        the lower obj
     * @param model
     *        the model
     * @param name
     *        the name
     * @param input_queue_directory_path
     *        the input_queue_directory_path
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     */
    boolean updateTopObjectUuid(String oldUuid,
                                String newUuid,
                                List<String> lowerObj,
                                String model,
                                String name,
                                String input_queue_directory_path) throws DatabaseException;
}
