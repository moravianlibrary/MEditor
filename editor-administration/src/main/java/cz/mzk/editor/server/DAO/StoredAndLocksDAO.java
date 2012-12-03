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

import java.util.ArrayList;
import java.util.List;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.rpc.StoredItem;
import cz.mzk.editor.shared.rpc.TreeStructureInfo;

/**
 * @author Matous Jobanek
 * @version Dec 3, 2012
 */
public interface StoredAndLocksDAO {

    public static final String SELECT_INFOS =
            "SELECT eu.surname || ', ' || eu.name as full_name, ts.* FROM ((SELECT timestamp, editor_user_id, tree_structure_id FROM "
                    + Constants.TABLE_CRUD_TREE_STRUCTURE_ACTION
                    + " WHERE type='c') a INNER JOIN (SELECT * FROM "
                    + Constants.TABLE_TREE_STRUCTURE
                    + " WHERE state='true') t ON a.tree_structure_id = t.id) ts LEFT JOIN "
                    + Constants.TABLE_EDITOR_USER + " eu ON eu.id = ts.editor_user_id";

    /** The Constant SELECT_INFOS_BY_USER. */
    public static final String SELECT_INFOS_BY_USER = StoredAndLocksDAO.SELECT_INFOS + " WHERE eu.id = (?)";

    /**
     * Gets the all stored items.
     * 
     * @param userId
     *        the user id
     * @return the all stored items
     * @throws DatabaseException
     *         the database exception
     */
    List<StoredItem> getAllStoredWorkingCopyItems(Long userId) throws DatabaseException;

    /**
     * Delete stored working copy item.
     * 
     * @param id
     *        the id
     * @return true, if successful
     * @throws DatabaseException
     */
    boolean deleteStoredWorkingCopyItem(Long id) throws DatabaseException;

    /**
     * Gets the all saved tree structures.
     * 
     * @param userId
     *        the user id
     * @return the all saved tree structures
     * @throws DatabaseException
     *         the database exception
     */
    ArrayList<TreeStructureInfo> getAllSavedTreeStructures(Long userId) throws DatabaseException;

    /**
     * Removes the saved structure.
     * 
     * @param id
     *        the id
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     */
    boolean removeSavedStructure(long id) throws DatabaseException;

}
