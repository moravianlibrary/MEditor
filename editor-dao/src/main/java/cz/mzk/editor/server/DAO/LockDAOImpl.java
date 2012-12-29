/*
 * Metadata Editor
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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;
import cz.mzk.editor.client.util.Constants.DEFAULT_SYSTEM_USERS;

/**
 * @author Jiri Kremser
 * @version $Id$
 */

public class LockDAOImpl
        extends AbstractActionDAO
        implements LockDAO {

    //    lock (id, uuid, description, modified, user_id) ->
    //    
    //          lock (id, digital_object_uuid, description, state)
    //                                   uuid, description, 

    //          crud_lock_action (id, editor_user_id, timestamp, successful, lock_id, type)
    //                                       user_id,  modified,  

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(LockDAOImpl.class.getPackage().toString());

    /** The Constant FORMATTER. */
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /** The Constant DURATION_LOCK. */
    private static final String DURATION_LOCK = "1 week";

    /** The Constant SELECT_USER_ID_OF_LOCK. */
    private static final String SELECT_USER_ID_OF_LOCK =
            "SELECT editor_user_id, MAX(timestamp) FROM ((SELECT editor_user_id, lock_id, timestamp FROM   "
                    + Constants.TABLE_CRUD_LOCK_ACTION
                    + " WHERE type='c' OR type='u') a INNER JOIN (SELECT id FROM "
                    + Constants.TABLE_LOCK
                    + "  WHERE digital_object_uuid=(?) AND state='true') l ON a.lock_id=l.id) GROUP BY editor_user_id, timestamp ORDER BY timestamp DESC LIMIT '1'";

    /** The Constant SELECT_OLD_DO_LOCKS. */
    private static final String SELECT_OLD_DO_LOCKS = "SELECT l.id FROM ((SELECT lock_id FROM "
            + Constants.TABLE_CRUD_LOCK_ACTION
            + " WHERE (type='c' OR type='u') AND timestamp < (NOW() - INTERVAL '" + DURATION_LOCK
            + "')) a INNER JOIN (SELECT digital_object_uuid, id FROM " + Constants.TABLE_LOCK
            + "lock WHERE state='true') l ON a.lock_id=l.id)";

    /** The Constant DISABLE_DO_LOCK_BY_ID. */
    private static final String DISABLE_DO_LOCK_BY_ID = "UPDATE lock SET state = 'false' WHERE id = (?)";

    /** The Constant UPDATE_DIGITAL_OBJECTS_TIMESTAMP_DESCRIPTION. */
    private static final String UPDATE_DIGITAL_OBJECTS_TIMESTAMP_DESCRIPTION = "UPDATE "
            + Constants.TABLE_LOCK + " SET description = (?) WHERE id = (?)";

    /** The Constant SELECT_LOCK_DESCRIPTION. */
    private static final String SELECT_LOCK_DESCRIPTION = "SELECT description FROM  " + Constants.TABLE_LOCK
            + " WHERE digital_object_uuid = (?)";

    /** The Constant SELECT_TIME_TO_EXPIRATION_LOCK. */
    private static final String SELECT_TIME_TO_EXPIRATION_LOCK =
            "SELECT ((MAX(al.timestamp) + INTERVAL '"
                    + DURATION_LOCK
                    + "')  - (CURRENT_TIMESTAMP)) AS timeToExpLock FROM ((SELECT lock_id, timestamp FROM "
                    + Constants.TABLE_CRUD_LOCK_ACTION
                    + " WHERE type='c' OR type='u') a INNER JOIN (SELECT id FROM "
                    + Constants.TABLE_LOCK
                    + " WHERE digital_object_uuid=(?) AND state='true') l ON a.lock_id=l.id) al GROUP BY al.timestamp ORDER BY al.timestamp DESC LIMIT '1'";

    /** The dao utils. */
    @Inject
    private DAOUtils daoUtils;

    /** The stored and locks dao. */
    @Inject
    private StoredAndLocksDAO storedAndLocksDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean lockDigitalObject(String uuid, String description, boolean insert)
            throws DatabaseException {

        PreparedStatement updateSt = null;
        boolean successful = false;
        Long lockId = null;

        try {
            daoUtils.checkDigitalObject(uuid, null, null, null, null, true, true);
        } catch (SQLException e1) {
            LOGGER.error(e1.getMessage());
            e1.printStackTrace();
        }

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }

        try {
            if (insert) {
                updateSt =
                        getConnection().prepareStatement(DAOUtils.LOCK_INSERT_ITEM_STATEMENT,
                                                         Statement.RETURN_GENERATED_KEYS);
                updateSt.setString(1, uuid);
                updateSt.setString(2, description != null ? description : "");
                updateSt.setBoolean(3, true);
            } else {
                lockId = storedAndLocksDAO.getLockId(uuid);
                updateSt = getConnection().prepareStatement(UPDATE_DIGITAL_OBJECTS_TIMESTAMP_DESCRIPTION);
                updateSt.setString(1, description != null ? description : "");
                updateSt.setLong(2, lockId);
            }

            if ((insert || lockId != null) && updateSt.executeUpdate() == 1) {
                LOGGER.debug("DB has been updated: The lock of the object: " + uuid + " has been "
                        + ((insert) ? "insertes." : "updated."));
                if (insert) {
                    ResultSet gk = updateSt.getGeneratedKeys();
                    if (gk.next()) {
                        lockId = gk.getLong(1);
                    }
                }
                if (lockId != null) {
                    successful =
                            (insertCrudAction(getUserId(false),
                                              Constants.TABLE_CRUD_LOCK_ACTION,
                                              "lock_id",
                                              lockId,
                                              insert ? CRUD_ACTION_TYPES.CREATE : CRUD_ACTION_TYPES.UPDATE,
                                              false));

                } else {
                    LOGGER.error("No key has been returned! " + updateSt);
                }

                if (successful) {
                    getConnection().commit();
                    LOGGER.debug("DB has been updated by commit.");
                } else {
                    getConnection().rollback();
                    LOGGER.debug("DB has not been updated -> rollback!");
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Could not get statement: " + updateSt, e);
        } finally {
            closeConnection();
        }

        return successful;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getLockOwnersID(String uuid) throws DatabaseException {
        PreparedStatement statement = null;
        long lockOwnersId = 0;
        disableOldLocks();

        try {
            statement = getConnection().prepareStatement(SELECT_USER_ID_OF_LOCK);
            statement.setString(1, uuid);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                lockOwnersId = rs.getLong("editor_user_id");
            }

        } catch (SQLException e) {
            LOGGER.error("Could not get select statement: " + statement, e);
        } finally {
            closeConnection();
        }
        return lockOwnersId;
    }

    /**
     * Disable old locks.
     * 
     * @throws DatabaseException
     *         the database exception
     */
    private void disableOldLocks() throws DatabaseException {

        PreparedStatement selSt = null, disSt = null;
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }

        try {
            selSt = getConnection().prepareStatement(SELECT_OLD_DO_LOCKS);
            ResultSet rs = selSt.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("id");
                disSt = getConnection().prepareStatement(DISABLE_DO_LOCK_BY_ID);
                disSt.setLong(1, id);
                if (disSt.executeUpdate() == 1) {
                    LOGGER.debug(id + " digital object have been unlocked at "
                            + FORMATTER.format(new java.util.Date()) + " because the lock was older than "
                            + DURATION_LOCK);

                    if (insertCrudAction(DEFAULT_SYSTEM_USERS.TIME.getUserId(),
                                         Constants.TABLE_CRUD_LOCK_ACTION,
                                         "lock_id",
                                         id,
                                         CRUD_ACTION_TYPES.DELETE,
                                         false)) {
                        getConnection().commit();
                        LOGGER.debug("DB has been updated by commit.");
                    } else {
                        getConnection().rollback();
                        LOGGER.debug("DB has not been updated -> rollback!");
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get select statement: " + selSt, e);
        } finally {
            closeConnection();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription(String uuid) throws DatabaseException {
        PreparedStatement selectSt = null;
        String description = null;

        try {
            selectSt = getConnection().prepareStatement(SELECT_LOCK_DESCRIPTION);
            selectSt.setString(1, uuid);
        } catch (SQLException e) {
            LOGGER.error("Could not get select statement", e);
        }

        try {
            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                description = rs.getString("description");
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getTimeToExpirationLock(String uuid) throws DatabaseException {
        PreparedStatement selectSt = null;
        String timeToExpirationLock = null;

        try {
            selectSt = getConnection().prepareStatement(SELECT_TIME_TO_EXPIRATION_LOCK);
            selectSt.setString(1, uuid);
        } catch (SQLException e) {
            LOGGER.error("Could not get select statement", e);
        }

        try {
            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                timeToExpirationLock = rs.getString("timeToExpLock");
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }

        String[] parsedExpTime = new String[3];

        if (timeToExpirationLock.contains(" day")) {
            String[] splitedByDays =
                    timeToExpirationLock.split(timeToExpirationLock.contains(" days ") ? " days " : " day ");
            parsedExpTime[0] = splitedByDays[0];
            parsedExpTime[1] = splitedByDays[1];
        } else {
            parsedExpTime[0] = "0";
            parsedExpTime[1] = timeToExpirationLock;
        }
        String[] splitedByColon = parsedExpTime[1].split(":");
        parsedExpTime[1] = splitedByColon[0];
        parsedExpTime[2] = splitedByColon[1];

        return parsedExpTime;
    }
}
