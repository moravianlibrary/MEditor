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

import cz.fi.muni.xkremser.editor.shared.rpc.ImageItem;

// TODO: Auto-generated Javadoc
/**
 * The Interface ImageResolverDAO.
 */
public interface ImageResolverDAO {

    void insertItems(List<ImageItem> toInsert) throws DatabaseException;

    ArrayList<String> resolveItems(List<String> identifiers) throws DatabaseException;

    String resolveItem(String identifier) throws DatabaseException;

    ArrayList<String> cacheAgeingProcess(int numberOfDays) throws DatabaseException;

    /**
     * Gets the path to an original image.
     * 
     * @param imageFile
     *          The path to the new image file
     *          
     * @return OldJpgFsPath
     */
    String getOldJpgFsPath(String imageFile) throws DatabaseException;
}
