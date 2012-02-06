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

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
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
            "SELECT file_name, uuid, model, description, stored FROM " + Constants.TABLE_STORED_FILES
                    + " WHERE user_id=(?)";

    private static final String UPDATE_STORED_ITEM = "UPDATE " + Constants.TABLE_STORED_FILES
            + " SET uuid=(?), model=(?), description=(?), stored=CURRENT_TIMESTAMP WHERE id=(?)";

    private static final String SELECT_STORED_ITEM_WITH_SAME_NAME = "SELECT id FROM "
            + Constants.TABLE_STORED_FILES + " WHERE file_name=(?)";

    private static final String DELETE_STORED_ITEM = "DELETE FROM " + Constants.TABLE_STORED_FILES
            + " WHERE file_name=(?)";

    private static final String INSERT_STORED_ITEM =
            "INSERT INTO "
                    + Constants.TABLE_STORED_FILES
                    + " (uuid, model, description, stored, file_name, user_id) VALUES ((?),(?),(?),(CURRENT_TIMESTAMP),(?),(?)) ";

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
                DigitalObjectModel model = DigitalObjectModel.getModel(rs.getInt("model"));
                String description = rs.getString("description");
                java.util.Date date = rs.getDate("stored");
                String storedDate = FORMATTER.format(date);

                StoredItem storedItem = new StoredItem(fileName, uuid, model, description, storedDate);
                storedItems.add(storedItem);
            }

        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }

        return storedItems;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean storeDigitalObject(long userId, StoredItem storedItem) throws DatabaseException {
        PreparedStatement updateSt = null;
        boolean successful = false;

        int duplicateId = selectDuplicate(userId, storedItem.getFileName());

        try {

            if (duplicateId >= 0) {
                updateSt = getConnection().prepareStatement(UPDATE_STORED_ITEM);
            } else {
                updateSt = getConnection().prepareStatement(INSERT_STORED_ITEM);
            }

            updateSt.setString(1, storedItem.getUuid());
            updateSt.setInt(2, storedItem.getModel().ordinal());
            updateSt.setString(3, storedItem.getDescription());
            if (duplicateId >= 0) {
                updateSt.setInt(4, duplicateId);
            } else {
                updateSt.setString(4, storedItem.getFileName());
                updateSt.setLong(5, userId);
            }

            successful = updateSt.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.error("Query: " + updateSt, e);
            successful = false;
        } finally {
            closeConnection();
        }
        return successful;
    }

    private int selectDuplicate(long userId, String fileName) throws DatabaseException {
        PreparedStatement selectSt = null;
        int id = Integer.MIN_VALUE;

        try {
            selectSt = getConnection().prepareStatement(SELECT_STORED_ITEM_WITH_SAME_NAME);
            selectSt.setString(1, fileName);

            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return id;
    }

    @Override
    public boolean deleteItem(String fileName) throws DatabaseException {
        PreparedStatement deleteSt = null;
        boolean successful = true;
        try {
            deleteSt = getConnection().prepareStatement(DELETE_STORED_ITEM);
            deleteSt.setString(1, fileName);

            deleteSt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Query: " + deleteSt, e);
            successful = false;
        } finally {
            closeConnection();
        }
        return successful;
    }
}
