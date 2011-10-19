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

package cz.fi.muni.xkremser.editor.server.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import com.google.inject.Inject;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

/**
 * @author Jiri Kremser
 * @version $Id$
 */

public class LocksDAOImpl
        extends AbstractDAO
        implements LocksDAO {

    /** The recently modified dao. */
    @Inject
    private UserDAO userDAO;

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(LocksDAOImpl.class.getPackage().toString());
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private static final String SELECT_LOCK_USER_ID = "SELECT user_id FROM  " + Constants.TABLE_LOCK
            + " WHERE uuid = (?)";

    private static final String INSERT_NEW_DIGITAL_OBJECTS_LOCK = "INSERT INTO " + Constants.TABLE_LOCK
            + " (uuid, description, modified, user_id) VALUES ((?),(?),(CURRENT_TIMESTAMP),(?))";

    private static final String UPDATE_DIGITAL_OBJECTS_TIMESTAMP_DESCRIPTION = "UPDATE "
            + Constants.TABLE_LOCK
            + " SET description = (?), modified = (CURRENT_TIMESTAMP) WHERE uuid = (?)";

    private static final String SELECT_LOCK_DESCRIPTION = "SELECT description FROM  " + Constants.TABLE_LOCK
            + " WHERE uuid = (?)";

    private static final String DELETE_OLD_DIGITAL_OBJECT = "DELETE FROM " + Constants.TABLE_LOCK
            + " WHERE modified < (NOW() - INTERVAL '1 week')";

    private static final String DELETE_DIGITAL_OBJETCS_LOCK_BY_UUID = "DELETE FROM " + Constants.TABLE_LOCK
            + " WHERE uuid = (?)";

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     */

    @Override
    public boolean lockDigitalObject(String uuid, Long id, String description) throws DatabaseException {

        PreparedStatement updateSt = null;
        boolean successful = false;

        try {
            if (id != null) {
                updateSt = getConnection().prepareStatement(INSERT_NEW_DIGITAL_OBJECTS_LOCK);
                updateSt.setString(1, uuid);
                updateSt.setString(2, description);
                updateSt.setLong(3, id);
            } else {
                updateSt = getConnection().prepareStatement(UPDATE_DIGITAL_OBJECTS_TIMESTAMP_DESCRIPTION);
                updateSt.setString(1, description);
                updateSt.setString(2, uuid);
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get insert statement", e);
        }

        int modified = 0;
        try {
            modified = updateSt.executeUpdate();
            if (modified == 1) {
                getConnection().commit();
                LOGGER.debug("DB has been updated. Queries: \"" + updateSt + "\"");
                successful = true;
            } else {
                getConnection().rollback();
                LOGGER.error("DB has not been updated -> rollback! Queries: \"" + updateSt + "\"");
                successful = false;
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
        PreparedStatement statement = null;
        long lockOwnersId = 0;

        try {
            statement = getConnection().prepareStatement(DELETE_OLD_DIGITAL_OBJECT);
            int modified = statement.executeUpdate();
            if (modified > 0) {
                LOGGER.debug(modified + " digital objects have been unlock at "
                        + FORMATTER.format(new java.util.Date())
                        + " because the lock was older than one week");
            }
            statement = getConnection().prepareStatement(SELECT_LOCK_USER_ID);
            statement.setString(1, uuid);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                lockOwnersId = rs.getInt("user_id");
            }

        } catch (SQLException e) {
            LOGGER.error("Could not get select statement: " + statement, e);
        } finally {
            closeConnection();
        }
        return lockOwnersId;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     */

    @Override
    public boolean unlockDigitalObject(String uuid) throws DatabaseException {

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
}
