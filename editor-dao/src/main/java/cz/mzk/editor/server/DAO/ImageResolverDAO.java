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

import cz.mzk.editor.shared.rpc.ImageItem;

// TODO: Auto-generated Javadoc
/**
 * The Interface ImageResolverDAO.
 */
public interface ImageResolverDAO {

    /**
     * Insert items.
     * 
     * @param toInsert
     *        the to insert
     * @throws DatabaseException
     *         the database exception
     */
    void insertItems(List<ImageItem> toInsert) throws DatabaseException;

    /**
     * Resolve items.
     * 
     * @param identifiers
     *        the identifiers
     * @return the array list
     * @throws DatabaseException
     *         the database exception
     */
    ArrayList<String> resolveItems(List<String> identifiers) throws DatabaseException;

    /**
     * Cache ageing process.
     * 
     * @param numberOfDays
     *        the number of days
     * @return the array list
     * @throws DatabaseException
     *         the database exception
     */
    ArrayList<String> cacheAgeingProcess(int numberOfDays) throws DatabaseException;

    /**
     * Gets the path to an original image.
     * 
     * @param imageFile
     *        The path to the new image file
     * @return OldJpgFsPath
     * @throws DatabaseException
     *         the database exception
     */
    String getOldJpgFsPath(String imageFile) throws DatabaseException;

    /**
     * Gets the new image file path.
     * 
     * @param identifier
     *        the identifier
     * @return the new image file path
     * @throws DatabaseException
     *         the database exception
     */
    String getNewImageFilePath(String identifier) throws DatabaseException;
}
