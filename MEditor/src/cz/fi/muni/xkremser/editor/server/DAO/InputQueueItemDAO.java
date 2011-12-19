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

import cz.fi.muni.xkremser.editor.shared.rpc.InputQueueItem;

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
     */
    void updateItems(List<InputQueueItem> toUpdate) throws DatabaseException;

    /**
     * Gets the items.
     * 
     * @param prefix
     *        the prefix
     * @return the items
     */
    ArrayList<InputQueueItem> getItems(String prefix) throws DatabaseException;

    /**
     * Updates the info about a possible ingest
     * 
     * @param ingested
     *        the info about a possible ingest
     * @param path
     *        the path to the (non)ingested images
     */
    void updateIngestInfo(boolean ingested, String path) throws DatabaseException;
}
