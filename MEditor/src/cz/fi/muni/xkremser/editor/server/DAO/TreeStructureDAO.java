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

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

import cz.fi.muni.xkremser.editor.shared.rpc.TreeStructureBundle.TreeStructureInfo;
import cz.fi.muni.xkremser.editor.shared.rpc.TreeStructureBundle.TreeStructureNode;

/**
 * @author Jiri Kremser
 * @version 25. 1. 2011
 */
public interface TreeStructureDAO {

    /**
     * Returns saved structure of user
     * 
     * @param userId
     *        id of the user
     * @return list of TreeStructureInfo instances belonging to specified user
     * @throws DatabaseException
     */
    ArrayList<TreeStructureInfo> getSavedStructuresOfUser(long userId) throws DatabaseException;

    /**
     * Deletes the structure from DB
     * 
     * @param id
     *        id of the record
     * @throws DatabaseException
     */
    void removeSavedStructure(long id) throws DatabaseException;

    /**
     * Returns the list of TreeStructureNode representing the unfinished work in
     * editor
     * 
     * @param structureId
     *        id of a stored structure
     * @throws DatabaseException
     */
    ArrayList<TreeStructureNode> loadStructure(long structureId) throws DatabaseException;

    /**
     * Saves the unfinished work into DB
     * 
     * @param userId
     *        user's id
     * @param info
     *        Information about the tree such as description
     * @param structure
     *        list with TreeStructureNode instances
     * @throws DatabaseException
     */
    void saveStructure(long userId, TreeStructureInfo info, List<TreeStructureNode> structure)
            throws DatabaseException;

}
