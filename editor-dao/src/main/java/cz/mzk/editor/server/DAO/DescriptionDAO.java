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

/**
 * @author Matous Jobanek
 * @version Oct 12, 2012
 */
public interface DescriptionDAO {

    /**
     * Gets the common description.
     * 
     * @param uuid
     *        the uuid
     * @return the common description
     * @throws DatabaseException
     *         the database exception
     */
    String getCommonDescription(String uuid) throws DatabaseException;

    /**
     * Put common description.
     * 
     * @param uuid
     *        the uuid
     * @param description
     *        the description
     * @param user_id
     *        the user_id
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     */
    boolean putCommonDescription(String uuid, String description, Long user_id) throws DatabaseException;

    /**
     * Gets the user description.
     * 
     * @param digital_object_uuid
     *        the digital_object_uuid
     * @param editor_user_id
     *        the editor_user_id
     * @return the user description
     * @throws DatabaseException
     *         the database exception
     */
    String getUserDescription(String digital_object_uuid, Long editor_user_id) throws DatabaseException;

    /**
     * Check user description.
     * 
     * @param digital_object_uuid
     *        the digital_object_uuid
     * @param editor_user_id
     *        the editor_user_id
     * @param description
     *        the description
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     */
    boolean checkUserDescription(String digital_object_uuid, Long editor_user_id, String description)
            throws DatabaseException;
}
