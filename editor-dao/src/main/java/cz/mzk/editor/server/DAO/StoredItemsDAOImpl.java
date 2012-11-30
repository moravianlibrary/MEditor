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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.rpc.StoredItem;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class StoredItemsDAOImpl
        extends AbstractDAO
        implements StoredItemsDAO {

    //    stored_files (id, user_id, uuid, model, description, stored, file_name) ->
    //    
    //        ->  saved_edited_object (digital_object_uuid, file_name, description, state)
    //                                                uuid, file_name, description, 'true'
    //
    //        ->  crud_saved_edited_object_action (editor_user_id, timestamp, saved_edited_object_id, type)
    //                                                    user_id,    stored,                     id,  'c'
    //    
    //        ->  digital_object (uuid, model, name, description, input_queue_directory_path)
    //  

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(StoredItemsDAOImpl.class.getPackage().toString());

    /** The Constant FORMATTER. */
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /** The Constant SELECT_STORED_ITEMS_BY_USER. */
    private static final String SELECT_STORED_ITEMS_BY_USER =
            "SELECT so.id, so.file_name, so.description, a.timestamp, so.digital_object_uuid, so.model FROM ((SELECT saved_edited_object_id, timestamp FROM "
                    + Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION
                    + " WHERE editor_user_id = (?)) a INNER JOIN ((SELECT uuid, model FROM "
                    + Constants.TABLE_DIGITAL_OBJECT
                    + ") o INNER JOIN (SELECT digital_object_uuid, file_name, description, id FROM "
                    + Constants.TABLE_SAVED_EDITED_OBJECT
                    + " WHERE state = 'true') s ON o.uuid = s.digital_object_uuid) so ON a.saved_edited_object_id = so.id) ORDER BY a.timestamp";

    /** The Constant UPDATE_STORED_ITEM. */
    private static final String UPDATE_STORED_ITEM = "UPDATE " + Constants.TABLE_SAVED_EDITED_OBJECT
            + " SET digital_object_uuid=(?), description=(?) WHERE id = (?)";

    /** The Constant SELECT_STORED_ITEM_WITH_SAME_NAME. */
    private static final String SELECT_STORED_ITEM_WITH_SAME_NAME =
            "SELECT o.id FROM ((SELECT saved_edited_object_id FROM "
                    + Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION
                    + " WHERE editor_user_id = (?)) a INNER JOIN (SELECT id FROM "
                    + Constants.TABLE_SAVED_EDITED_OBJECT
                    + " WHERE state = 'true' AND file_name = (?)) o ON a.saved_edited_object_id = o.id)";

    /** The Constant DISABLE_STORED_ITEM. */
    private static final String DISABLE_STORED_ITEM = "UPDATE " + Constants.TABLE_SAVED_EDITED_OBJECT
            + " SET state = 'false' WHERE id = (?)";

    /** The Constant INSERT_STORED_ITEM. */
    private static final String INSERT_STORED_ITEM = "INSERT INTO " + Constants.TABLE_SAVED_EDITED_OBJECT
            + " (digital_object_uuid, description, file_name, state) VALUES ((?),(?),(?),'true') ";

    /** The dao utils. */
    @Inject
    private DAOUtils daoUtils;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StoredItem> getStoredItems(long userId) throws DatabaseException {

        PreparedStatement selectSt = null;
        List<StoredItem> storedItems = new ArrayList<StoredItem>();

        try {
            selectSt = getConnection().prepareStatement(SELECT_STORED_ITEMS_BY_USER);
            selectSt.setLong(1, userId);

            ResultSet rs = selectSt.executeQuery();

            while (rs.next()) {
                String fileName = rs.getString("file_name");
                String uuid = rs.getString("digital_object_uuid");
                DigitalObjectModel model = DigitalObjectModel.parseString(rs.getString("model"));
                String description = rs.getString("description");
                java.util.Date date = rs.getDate("timestamp");
                String storedDate = FORMATTER.format(date);
                long id = rs.getLong("id");

                StoredItem storedItem = new StoredItem(id, fileName, uuid, model, description, storedDate);
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
    public boolean checkStoredDigitalObject(long userId, StoredItem storedItem) throws DatabaseException {
        PreparedStatement updateSt = null;
        boolean successful = false;

        Long id = selectDuplicate(userId, storedItem.getFileName());

        try {

            boolean update = id > 0;

            if (update) {
                updateSt = getConnection().prepareStatement(UPDATE_STORED_ITEM);
            } else {
                updateSt =
                        getConnection().prepareStatement(INSERT_STORED_ITEM, Statement.RETURN_GENERATED_KEYS);
            }

            updateSt.setString(1, storedItem.getUuid());
            updateSt.setString(2, storedItem.getDescription());
            if (id >= 0) {
                updateSt.setLong(3, id);
            } else {
                updateSt.setString(3, storedItem.getFileName());
            }

            if (updateSt.executeUpdate() > 0) {
                LOGGER.debug("DB has been updated: The file: " + storedItem.getFileName()
                        + " has been inserted.");
                if (!update) {
                    ResultSet gk = updateSt.getGeneratedKeys();
                    if (gk.next()) {
                        id = gk.getLong(1);
                    } else {
                        throw new DatabaseException("No key has been returned! " + updateSt);
                    }
                }

                successful =
                        daoUtils.insertCrudAction(userId,
                                                  Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION,
                                                  "saved_edited_object_id",
                                                  id,
                                                  update ? CRUD_ACTION_TYPES.UPDATE
                                                          : CRUD_ACTION_TYPES.CREATE,
                                                  true);
            } else {
                LOGGER.error("DB has not been updated! " + updateSt);
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + updateSt, e);
        } finally {
            closeConnection();
        }
        return successful;
    }

    /**
     * Select duplicate.
     * 
     * @param userId
     *        the user id
     * @param fileName
     *        the file name
     * @return the long
     * @throws DatabaseException
     *         the database exception
     */
    private Long selectDuplicate(long userId, String fileName) throws DatabaseException {
        PreparedStatement selectSt = null;
        Long id = Long.MIN_VALUE;

        try {
            selectSt = getConnection().prepareStatement(SELECT_STORED_ITEM_WITH_SAME_NAME);
            selectSt.setLong(1, userId);
            selectSt.setString(2, fileName);

            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                id = rs.getLong("id");
            }

        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteItem(Long id) throws DatabaseException {
        PreparedStatement deleteSt = null;
        boolean successful = false;
        try {
            deleteSt = getConnection().prepareStatement(DISABLE_STORED_ITEM);
            deleteSt.setLong(1, id);

            if (deleteSt.executeUpdate() == 1) {
                LOGGER.debug("DB has been updated: The edited stored object: " + id + " has been disabled.");
                successful =
                        daoUtils.insertCrudAction(getUserId(false),
                                                  Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION,
                                                  "saved_edited_object_id",
                                                  id,
                                                  CRUD_ACTION_TYPES.DELETE,
                                                  true);
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + deleteSt, e);
        } finally {
            closeConnection();
        }
        return successful;
    }
}
