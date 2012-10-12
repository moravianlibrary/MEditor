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

/**
 * @author Jiri Kremser
 * @version $Id$
 */

public class LockDAOImpl
        extends AbstractDAO
        implements LockDAO {

    //    lock (id, uuid, description, modified, user_id) ->
    //    
    //          lock (id, digital_object_uuid, description, state)
    //                                   uuid, description, 

    //          crud_lock_action (id, editor_user_id, timestamp, successful, lock_id, type)
    //                                       user_id,  modified,  

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(LockDAOImpl.class.getPackage().toString());
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private static final String DURATION_LOCK = "1 week";

    //    private static final String SELECT_LOCK_USER_ID = "SELECT user_id FROM  " + Constants.TABLE_LOCK
    //            + " WHERE uuid = (?)";
    private static final String SELECT_LOCK_USER_ID = "SELECT user_id FROM  " + Constants.TABLE_LOCK
            + " WHERE digital_object_uuid = (?) AND state = 'true'";

    //    private static final String INSERT_NEW_DIGITAL_OBJECTS_LOCK = "INSERT INTO " + Constants.TABLE_LOCK
    //            + " (uuid, description, modified, user_id) VALUES ((?),(?),(CURRENT_TIMESTAMP),(?))";

    private static final String UPDATE_DIGITAL_OBJECTS_TIMESTAMP_DESCRIPTION = "UPDATE "
            + Constants.TABLE_LOCK
            + " SET description = (?), modified = (CURRENT_TIMESTAMP) WHERE digital_object_uuid = (?)";

    private static final String SELECT_LOCK_DESCRIPTION = "SELECT description FROM  " + Constants.TABLE_LOCK
            + " WHERE digital_object_uuid = (?)";

    //    private static final String DELETE_OLD_DIGITAL_OBJECT = "DELETE FROM " + Constants.TABLE_LOCK
    //            + " WHERE modified < (NOW() - INTERVAL '" + DURATION_LOCK + "')";
    private static final String UNLOCK_OLD_DIGITAL_OBJECT = "UPDATE " + Constants.TABLE_LOCK
            + " SET state = 'true' WHERE modified < (NOW() - INTERVAL '" + DURATION_LOCK + "')";

    private static final String MAX_U_C_TIME = "SELECT MAX(" + Constants.TABLE_CRUD_LOCK_ACTION
            + ".timestamp) FROM " + "";

    private static final String SELECT_ACTIVE_LOCKS_ID_BY_UUID = "SELECT id FROM " + Constants.TABLE_LOCK
            + " WHERE uuid=(?) AND state = 'true'";

    //LEFT JOIN Orders
    //ON Persons.P_Id=Orders.P_Id
    //ORDER BY Persons.LastName"

    private static final String DELETE_DIGITAL_OBJETCS_LOCK_BY_UUID = "DELETE FROM " + Constants.TABLE_LOCK
            + " WHERE uuid = (?)";

    private static final String SELECT_TIME_TO_EXPIRATION_LOCK = "SELECT ((modified + INTERVAL '"
            + DURATION_LOCK + "') - (CURRENT_TIMESTAMP)) AS timeToExpLock FROM " + Constants.TABLE_LOCK
            + " WHERE uuid = (?)";

    @Inject
    private DAOUtils daoUtils;

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     */

    @Override
    public boolean lockDigitalObject(String uuid, Long user_id, String description, boolean insert)
            throws DatabaseException {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }
        PreparedStatement updateSt = null;
        boolean successful = false;

        try {
            if (insert) {
                updateSt =
                        getConnection().prepareStatement(DAOUtils.LOCK_INSERT_ITEM_STATEMENT,
                                                         Statement.RETURN_GENERATED_KEYS);
                updateSt.setString(1, uuid);
                updateSt.setString(2, description != null ? description : "");
                updateSt.setBoolean(3, true);
            } else {
                updateSt =
                        getConnection().prepareStatement(UPDATE_DIGITAL_OBJECTS_TIMESTAMP_DESCRIPTION,
                                                         Statement.RETURN_GENERATED_KEYS);
                updateSt.setString(1, description != null ? description : "");
                updateSt.setString(2, uuid);
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get statement", e);
        }

        int modified = 0;
        try {
            modified = updateSt.executeUpdate();

            if (modified == 1) {
                LOGGER.debug("DB has been updated: The lock of the object: " + uuid + " has been "
                        + ((insert) ? "insertes." : "updated."));

                ResultSet gk = updateSt.getGeneratedKeys();
                if (gk.next()) {
                    long id = Long.parseLong(Integer.toString(gk.getInt(1)));
                    if (daoUtils.insertCrudAction(user_id,
                                                  Constants.TABLE_LOCK,
                                                  "",
                                                  id,
                                                  insert ? CRUD_ACTION_TYPES.CREATE
                                                          : CRUD_ACTION_TYPES.UPDATE,
                                                  false)) {
                        getConnection().commit();
                        successful = true;
                    }
                } else {
                    LOGGER.error("No key has been returned! " + updateSt);
                }
            }
            if (!successful) {
                getConnection().rollback();
                LOGGER.error("DB has not been updated -> rollback! Queries: \"" + updateSt + "\"");
            }

        } catch (SQLException e) {
            LOGGER.error("Query: " + updateSt, e);
        } finally {
            closeConnection();
        }
        return successful;
    }

    @Override
    public long getLockOwnersID(String uuid) throws DatabaseException {
        return 0;
        //        PreparedStatement statement = null;
        //        long lockOwnersId = 0;
        //
        //        try {
        //            statement = getConnection().prepareStatement(DELETE_OLD_DIGITAL_OBJECT);
        //            int modified = statement.executeUpdate();
        //            if (modified > 0) {
        //                LOGGER.debug(modified + " digital objects have been unlock at "
        //                        + FORMATTER.format(new java.util.Date())
        //                        + " because the lock was older than one week");
        //            }
        //        } catch (SQLException e) {
        //            LOGGER.error("Could not get select statement: " + statement, e);
        //        } finally {
        //            closeConnection();
        //        }
        //        try {
        //            statement = getConnection().prepareStatement(SELECT_LOCK_USER_ID);
        //            statement.setString(1, uuid);
        //            ResultSet rs = statement.executeQuery();
        //            while (rs.next()) {
        //                lockOwnersId = rs.getInt("user_id");
        //            }
        //
        //        } catch (SQLException e) {
        //            LOGGER.error("Could not get select statement: " + statement, e);
        //        } finally {
        //            closeConnection();
        //        }
        //        return lockOwnersId;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     */

    @Override
    public boolean unlockDigitalObject(String uuid) throws DatabaseException {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }
        PreparedStatement deleteSt = null;
        boolean successful = false;
        try {
            deleteSt = getConnection().prepareStatement(DELETE_DIGITAL_OBJETCS_LOCK_BY_UUID);
            deleteSt.setString(1, uuid);
        } catch (SQLException e) {
            LOGGER.error("Could not get delete statement", e);
        }

        int modified;
        try {
            modified = deleteSt.executeUpdate();

            if (modified == 1) {
                getConnection().commit();
                LOGGER.debug("DB has been updated. Queries: \"" + deleteSt + "\"");
                successful = true;
            } else {
                getConnection().rollback();
                LOGGER.error("DB has not been updated -> rollback! Queries: \"" + deleteSt + "\"");
                successful = false;
            }

        } catch (SQLException e) {
            LOGGER.error("Query: " + deleteSt, e);
        } finally {
            closeConnection();
        }

        return successful;
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
