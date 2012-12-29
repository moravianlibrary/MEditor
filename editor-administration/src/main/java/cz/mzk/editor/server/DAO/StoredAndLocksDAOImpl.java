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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.rpc.ActiveLockItem;
import cz.mzk.editor.shared.rpc.StoredItem;
import cz.mzk.editor.shared.rpc.TreeStructureInfo;

/**
 * @author Matous Jobanek
 * @version Dec 3, 2012
 */
public class StoredAndLocksDAOImpl
        extends AbstractActionDAO
        implements StoredAndLocksDAO {

    /** The logger. */
    private static final Logger LOGGER = Logger
            .getLogger(StoredAndLocksDAOImpl.class.getPackage().toString());

    /** The Constant SELECT_STORED_ITEMS_BY_USER. */
    private static final String SELECT_STORED_WORKING_COPY_ITEMS_BY_USER =
            "SELECT so.id, so.file_name, so.description, a.timestamp, so.digital_object_uuid, so.model FROM ((SELECT saved_edited_object_id, timestamp FROM "
                    + Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION
                    + " WHERE editor_user_id = (?)) a INNER JOIN ((SELECT uuid, model FROM "
                    + Constants.TABLE_DIGITAL_OBJECT
                    + ") o INNER JOIN (SELECT digital_object_uuid, file_name, description, id FROM "
                    + Constants.TABLE_SAVED_EDITED_OBJECT
                    + " WHERE state = 'true') s ON o.uuid = s.digital_object_uuid) so ON a.saved_edited_object_id = so.id) ORDER BY a.timestamp";

    /** The Constant DISABLE_STORED_ITEM. */
    private static final String DISABLE_STORED_ITEM = "UPDATE " + Constants.TABLE_SAVED_EDITED_OBJECT
            + " SET state = 'false' WHERE id = (?)";

    /** The Constant DELETE_NODES. */
    public static final String DELETE_NODES = "DELETE FROM " + Constants.TABLE_TREE_STRUCTURE_NODE
            + " WHERE tree_structure_id = (?)";

    /** The Constant DISABLE_INFO. */
    public static final String DISABLE_INFO = "UPDATE " + Constants.TABLE_TREE_STRUCTURE
            + " SET state = 'false' WHERE id = (?)";

    /** The Constant DISABLE_DO_LOCK_BY_UUID. */
    public static final String DISABLE_DO_LOCK_BY_UUID =
            "UPDATE lock SET state = 'false' WHERE digital_object_uuid = (?)";

    /** The Constant SELECT_ID_OF_LOCK. */
    private static final String SELECT_ID_OF_LOCK =
            "SELECT l.id, MAX(timestamp) FROM ((SELECT lock_id, timestamp FROM "
                    + Constants.TABLE_CRUD_LOCK_ACTION
                    + " WHERE (type = 'c' OR type = 'u') AND editor_user_id = (?)) a INNER JOIN (SELECT id FROM "
                    + Constants.TABLE_LOCK
                    + "  WHERE digital_object_uuid = (?) AND state = 'true') l ON a.lock_id=l.id) "
                    + "GROUP BY l.id, timestamp ORDER BY timestamp DESC LIMIT '1'";

    private static final String SELECT_ALL_ACTIVE_LOCK_ITEMS =
            "SELECT name, uuid, timestamp, al.description, model FROM "
                    + "((SELECT lock_id, timestamp, digital_object_uuid, description FROM "
                    + "((SELECT lock_id, MAX(timestamp) AS timestamp FROM "
                    + Constants.TABLE_CRUD_LOCK_ACTION
                    + " WHERE editor_user_id = (?) AND (type = 'c' OR type = 'u') GROUP BY lock_id) a INNER JOIN "
                    + Constants.TABLE_LOCK + " l ON l.id = a.lock_id) WHERE state = true) al INNER JOIN "
                    + Constants.TABLE_DIGITAL_OBJECT + " o ON al.digital_object_uuid = o.uuid)";

    /** The dao utils. */
    @Inject
    private DAOUtils daoUtils;

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     */
    @Override
    public List<StoredItem> getAllStoredWorkingCopyItems(Long userId) throws DatabaseException {
        PreparedStatement selectSt = null;
        List<StoredItem> storedItems = new ArrayList<StoredItem>();

        try {
            Long editorUserId = userId;
            if (editorUserId == null) {
                editorUserId = getUserId(false);
            }

            selectSt = getConnection().prepareStatement(SELECT_STORED_WORKING_COPY_ITEMS_BY_USER);
            selectSt.setLong(1, editorUserId);

            ResultSet rs = selectSt.executeQuery();

            while (rs.next()) {
                String fileName = rs.getString("file_name");
                String uuid = rs.getString("digital_object_uuid");
                DigitalObjectModel model = DigitalObjectModel.parseString(rs.getString("model"));
                String description = rs.getString("description");
                java.util.Date date = rs.getDate("timestamp");
                String storedDate = FORMATTER_TO_SECONDS.format(date);
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
     * 
     * @throws DatabaseException
     */
    @Override
    public boolean deleteStoredWorkingCopyItem(Long id) throws DatabaseException {
        PreparedStatement deleteSt = null;
        boolean successful = false;

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }

        try {
            deleteSt = getConnection().prepareStatement(DISABLE_STORED_ITEM);
            deleteSt.setLong(1, id);

            if (deleteSt.executeUpdate() == 1) {
                LOGGER.debug("DB has been updated: The edited stored object: " + id + " has been disabled.");
                if (insertCrudAction(getUserId(false),
                                     Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION,
                                     "saved_edited_object_id",
                                     id,
                                     CRUD_ACTION_TYPES.DELETE,
                                     true)) {
                    getConnection().commit();
                    successful = true;
                    LOGGER.debug("DB has been updated by commit.");
                } else {
                    getConnection().rollback();
                    LOGGER.debug("DB has not been updated -> rollback!");
                }
            } else {
                LOGGER.warn("DB has NOT been updated: The edited stored object: " + id
                        + " was trying to be disabled.");
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + deleteSt, e);
        } finally {
            closeConnection();
        }
        return successful;
    }

    @Override
    public ArrayList<TreeStructureInfo> getAllSavedTreeStructures(Long userId) throws DatabaseException {
        PreparedStatement selectSt = null;
        ArrayList<TreeStructureInfo> retList = new ArrayList<TreeStructureInfo>();
        try {
            Long editorUserId = userId;
            if (editorUserId == null) {
                editorUserId = getUserId(false);
            }

            selectSt = getConnection().prepareStatement(SELECT_INFOS_BY_USER);
            selectSt.setLong(1, editorUserId);

        } catch (SQLException e) {
            LOGGER.error("Could not get select infos statement", e);
        }

        try {

            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                Date date = rs.getTimestamp("timestamp");
                if (date != null) {
                    retList.add(new TreeStructureInfo(rs.getLong("id"), FORMATTER_TO_SECONDS.format(date), rs
                            .getString("description"), rs.getString("barcode"), rs.getString("name"), rs
                            .getString("full_name"), rs.getString("input_queue_directory_path"), rs
                            .getString("model")));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return retList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeSavedStructure(long id) throws DatabaseException {
        PreparedStatement deleteSt = null, disableSt = null;
        boolean successful = false;
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }

        try {
            deleteSt = getConnection().prepareStatement(DELETE_NODES);
            deleteSt.setLong(1, id);
            deleteSt.executeUpdate();

            disableSt = getConnection().prepareStatement(DISABLE_INFO);
            disableSt.setLong(1, id);
            if (disableSt.executeUpdate() == 1) {
                LOGGER.debug("DB has been updated: The tree structure info: " + id + " has been disabled.");
                boolean success =
                        insertCrudAction(getUserId(false),
                                         Constants.TABLE_CRUD_TREE_STRUCTURE_ACTION,
                                         "tree_structure_id",
                                         id,
                                         CRUD_ACTION_TYPES.DELETE,
                                         false);
                if (success) {
                    getConnection().commit();
                    successful = true;
                    LOGGER.debug("DB has been updated by commit.");
                } else {
                    getConnection().rollback();
                    LOGGER.debug("DB has not been updated -> rollback!");
                }
            } else {
                LOGGER.error("DB has not been updated! " + deleteSt + "\n" + disableSt);
            }

        } catch (SQLException e) {
            LOGGER.error("Could not delete tree structure (info) with id " + id + "\n Query1: " + deleteSt
                    + "\n Query2: " + disableSt, e);
        } finally {
            closeConnection();
        }
        return successful;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean unlockDigitalObject(String uuid) throws DatabaseException {
        PreparedStatement deleteSt = null;
        boolean successful = false;
        Long id = getLockId(uuid);
        if (id != null) {
            try {
                getConnection().setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.warn("Unable to set autocommit off", e);
            }
            try {
                deleteSt = getConnection().prepareStatement(DISABLE_DO_LOCK_BY_UUID);
                deleteSt.setString(1, uuid);

                if (deleteSt.executeUpdate() > 0) {
                    successful =
                            insertCrudAction(getUserId(false),
                                             Constants.TABLE_CRUD_LOCK_ACTION,
                                             "lock_id",
                                             id,
                                             CRUD_ACTION_TYPES.DELETE,
                                             false);
                }

                if (successful) {
                    getConnection().commit();
                    LOGGER.debug("DB has been updated by commit.");
                } else {
                    getConnection().rollback();
                    LOGGER.error("DB has not been updated -> rollback!");
                }

            } catch (SQLException e) {
                LOGGER.error("Query: " + deleteSt, e);
            } finally {
                closeConnection();
            }
        } else {
            LOGGER.error("No key has been returned! " + deleteSt);
        }

        return successful;
    }

    @Override
    public Long getLockId(String uuid) throws DatabaseException {
        PreparedStatement selectSt = null;
        Long id = null;
        try {
            selectSt = getConnection().prepareStatement(SELECT_ID_OF_LOCK);
            selectSt.setLong(1, getUserId(false));
            selectSt.setString(2, uuid);
        } catch (SQLException e) {
            LOGGER.error("Could not get select statement", e);
        }

        try {
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

    @Override
    public List<ActiveLockItem> getAllActiveLocks(Long userId) throws DatabaseException {
        ArrayList<ActiveLockItem> items = new ArrayList<ActiveLockItem>();
        PreparedStatement selSt = null;
        try {
            Long editorUserId = userId;
            if (editorUserId == null) {
                editorUserId = getUserId(false);
            }

            selSt = getConnection().prepareStatement(SELECT_ALL_ACTIVE_LOCK_ITEMS);
            selSt.setLong(1, editorUserId);

            ResultSet rs = selSt.executeQuery();

            while (rs.next()) {
                items.add(new ActiveLockItem(rs.getString("name"), rs.getString("uuid"), FORMATTER_TO_SECONDS
                        .format(rs.getTimestamp("timestamp")), rs.getString("description"), rs
                        .getString("model")));
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + ", Query: " + selSt);
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return items;
    }
}
