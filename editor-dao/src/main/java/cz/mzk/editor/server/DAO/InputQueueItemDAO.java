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

import java.util.ArrayList;
import java.util.List;

import cz.mzk.editor.shared.rpc.IngestInfo;
import cz.mzk.editor.shared.rpc.InputQueueItem;

// TODO: Auto-generated Javadoc
/**
 * The Interface InputQueueItemDAO.
 */
public interface InputQueueItemDAO {

    /**
     * Update items.
     * 
     * @param toUpdate
     *        the to update
     * @throws DatabaseException
     *         the database exception
     */
    void updateItems(List<InputQueueItem> toUpdate) throws DatabaseException;

    /**
     * Gets the items.
     * 
     * @param prefix
     *        the prefix
     * @return the items
     * @throws DatabaseException
     *         the database exception
     */
    ArrayList<InputQueueItem> getItems(String prefix) throws DatabaseException;

    /**
     * Updates the name of a digital object.
     * 
     * @param path
     *        The path to the digital object
     * @param name
     *        The name
     * @throws DatabaseException
     *         the database exception
     */

    public void updateName(String path, String name) throws DatabaseException;

    /**
     * Gets the ingest info.
     * 
     * @param path
     *        the path
     * @return the ingest info
     * @throws DatabaseException
     *         the database exception
     */
    List<IngestInfo> getIngestInfo(String path) throws DatabaseException;

    /**
     * Checks for been ingested.
     * 
     * @param path
     *        the path
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     */
    boolean hasBeenIngested(String path) throws DatabaseException;
}
