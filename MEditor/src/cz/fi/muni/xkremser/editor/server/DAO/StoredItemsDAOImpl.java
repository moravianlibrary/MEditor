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

package cz.fi.muni.xkremser.editor.server.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

import cz.fi.muni.xkremser.editor.shared.rpc.StoredItem;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class StoredItemsDAOImpl
        extends AbstractDAO
        implements StoredItemsDAO {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(StoredItemsDAOImpl.class.getPackage().toString());
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private static final String SELECT_STORED_ITEM =
            "SELECT file_name, uuid, name, description, stored FROM " + Constants.TABLE_STORED_FILES
                    + " WHERE user_id=(?)";

    //    private static final String INSERT_STORED_ITEM =
    //            "INSERT INTO "
    //                    + Constants.TABLE_STORED_FILES
    //                    + " (user_id, file_name, uuid, name, description, stored) VALUES ((?)(?)(?)(?)(?)(CURRENT_TIMESTAMP)) ";

    /**
     * {@inheritDoc}
     */

    @Override
    public List<StoredItem> getStoredItems(long userId) throws DatabaseException {

        PreparedStatement selectSt = null;
        List<StoredItem> storedItems = new ArrayList<StoredItem>();

        try {
            selectSt = getConnection().prepareStatement(SELECT_STORED_ITEM);
            selectSt.setLong(1, userId);

            ResultSet rs = selectSt.executeQuery();

            while (rs.next()) {
                String fileName = rs.getString("file_name");
                String uuid = rs.getString("uuid");
                String name = rs.getString("name");
                String description = rs.getString("description");
                java.util.Date date = rs.getDate("stored");
                String storedDate = FORMATTER.format(date);

                StoredItem storedItem = new StoredItem(fileName, uuid, name, description, storedDate);
                storedItems.add(storedItem);
            }

        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }

        return storedItems;
    }

    //    /**
    //     * {@inheritDoc}
    //     */
    //
    //    @Override
    //    public boolean storeDigitalObject(String userId, StoredItems storedItem, String foxml)
    //            throws DatabaseException {
    //                PreparedStatement updateSt = null;
    //        
    //                try {
    //                    updateSt = getConnection().prepareStatement(INSERT_STORED_ITEM);
    //                    updateSt.setString(1, userId);
    //                    updateSt.setString(2, storedItem.);
    //                    
    //                    
    //                } catch (SQLException e) {
    //                    // TODO Auto-generated method stub
    //                    e.printStackTrace();
    //        
    //                }
    //        return false;
    //    }
}
