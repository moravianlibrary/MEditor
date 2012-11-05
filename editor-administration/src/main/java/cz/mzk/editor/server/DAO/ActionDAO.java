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

import cz.mzk.editor.shared.rpc.EditorDate;
import cz.mzk.editor.shared.rpc.HistoryItem;

/**
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public interface ActionDAO {

    /**
     * Gets the history days.
     * 
     * @param userId
     *        the user id
     * @return the history days
     * @throws DatabaseException
     *         the database exception
     */
    public List<EditorDate> getHistoryDays(Long userId) throws DatabaseException;

    /**
     * Gets the history items.
     * 
     * @param editorUserId
     *        the editor user id
     * @param lowerLimit
     *        the lower limit
     * @param upperLimit
     *        the upper limit
     * @return the history items
     * @throws DatabaseException
     *         the database exception
     */
    List<HistoryItem> getHistoryItems(Long editorUserId, EditorDate lowerLimit, EditorDate upperLimit)
            throws DatabaseException;

}
