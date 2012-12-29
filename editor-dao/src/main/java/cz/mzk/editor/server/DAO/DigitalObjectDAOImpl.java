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
import java.sql.SQLException;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;

/**
 * @author Matous Jobanek
 * @version Oct 22, 2012
 */
public class DigitalObjectDAOImpl
        extends AbstractActionDAO
        implements DigitalObjectDAO {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(DigitalObjectDAOImpl.class.getPackage().toString());

    //         ->  digital_object (uuid, model, name, description, input_queue_directory_path, state)

    //         ->  crud_digital_object_action (editor_user_id, timestamp, digital_object_uuid, type)

    /** The Constant DISABLE_DIGITAL_OBJECT_ITEM. */
    public static final String DISABLE_DIGITAL_OBJECT_ITEM = "UPDATE " + Constants.TABLE_DIGITAL_OBJECT
            + " SET state = 'false' WHERE uuid = (?)";

    /** The Constant UPDATE_TOP_DO_TIMESTAMP. */
    public static final String UPDATE_TOP_DO_TIMESTAMP =
            "UPDATE "
                    + Constants.TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT
                    + " SET timestamp = (CURRENT_TIMESTAMP) WHERE digital_object_uuid = (?) AND top_digital_object_uuid = (?) AND type='c'";

    /** The Constant UPDATE_TOP_DO_UUID_AND_STATE. */
    public static final String UPDATE_TOP_DO_UUID_AND_STATE =
            "UPDATE "
                    + Constants.TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT
                    + " SET top_digital_object_uuid = (?), state = 'true' WHERE top_digital_object_uuid = (?) AND digital_object_uuid = (?)";

    public static final String UPDATE_STATE = "UPDATE " + Constants.TABLE_DIGITAL_OBJECT
            + " SET state = (?) WHERE uuid = (?)";

    /** The dao utils. */
    @Inject
    private DAOUtils daoUtils;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteDigitalObject(String uuid, String model, String name, String topObjectUuid)
            throws DatabaseException {
        PreparedStatement deleteSt = null;
        boolean successful = false;

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }

        try {
            if (daoUtils.checkDigitalObject(uuid, model, name, null, null, true, true)) {
                deleteSt = getConnection().prepareStatement(DISABLE_DIGITAL_OBJECT_ITEM);
                deleteSt.setString(1, uuid);

                if (deleteSt.executeUpdate() == 1) {
                    LOGGER.debug("DB has been updated: The digital object: " + uuid + " has been disabled.");
                    if (insertCrudActionWithTopObject(getUserId(false),
                                                      Constants.TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT,
                                                      "digital_object_uuid",
                                                      uuid,
                                                      CRUD_ACTION_TYPES.DELETE,
                                                      topObjectUuid,
                                                      true)) {
                        getConnection().commit();
                        successful = true;
                        LOGGER.debug("DB has been updated by commit.");
                    } else {
                        getConnection().rollback();
                        LOGGER.debug("DB has not been updated -> rollback!");
                    }
                }
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
    public boolean insertNewDigitalObject(String uuid,
                                          String model,
                                          String name,
                                          String input_queue_directory_path,
                                          String top_digital_object_uuid,
                                          boolean state) throws DatabaseException {
        String pid =
                (uuid.startsWith(Constants.FEDORA_UUID_PREFIX)) ? uuid : Constants.FEDORA_UUID_PREFIX
                        .concat(uuid);
        String top_digital_object_pid =
                (uuid.startsWith(Constants.FEDORA_UUID_PREFIX)) ? top_digital_object_uuid
                        : Constants.FEDORA_UUID_PREFIX.concat(top_digital_object_uuid);
        boolean successful = false;

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }

        try {
            if (daoUtils.checkDigitalObject(pid, model, name, null, DAOUtilsImpl
                    .directoryPathToRightFormat(input_queue_directory_path), state, false))

                if (insertCrudActionWithTopObject(getUserId(false),
                                                  Constants.TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT,
                                                  "digital_object_uuid",
                                                  pid,
                                                  CRUD_ACTION_TYPES.CREATE,
                                                  top_digital_object_pid,
                                                  false)) {
                    getConnection().commit();
                    successful = true;
                    LOGGER.debug("DB has been updated by commit.");
                } else {
                    getConnection().rollback();
                    LOGGER.debug("DB has not been updated -> rollback!");
                }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return successful;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTopObjectTime(String uuid) throws DatabaseException {
        PreparedStatement updateSt = null;
        String pid =
                (uuid.startsWith(Constants.FEDORA_UUID_PREFIX)) ? uuid : Constants.FEDORA_UUID_PREFIX
                        .concat(uuid);
        try {
            updateSt = getConnection().prepareStatement(UPDATE_TOP_DO_TIMESTAMP);
            updateSt.setString(1, pid);
            updateSt.setString(2, pid);

            if (updateSt.executeUpdate() == 1) {
                LOGGER.debug("DB has been updated: The top digital object: " + uuid + " has been updated.");
            } else {
                LOGGER.error("DB has not been updated: " + updateSt);
            }

        } catch (SQLException e) {
            LOGGER.error("Query: " + updateSt, e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public void updateState(List<String> objects, boolean state) throws DatabaseException {
        PreparedStatement updateSt = null;

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }

        boolean successful = false;

        try {

            for (String uuid : objects) {
                String pid =
                        (uuid.startsWith(Constants.FEDORA_UUID_PREFIX)) ? uuid : Constants.FEDORA_UUID_PREFIX
                                .concat(uuid);

                updateSt = getConnection().prepareStatement(UPDATE_STATE);
                updateSt.setBoolean(1, state);
                updateSt.setString(2, pid);

                if (updateSt.executeUpdate() == 1) {
                    LOGGER.debug("DB has been updated: The digital object: " + pid + " has been updated.");
                    successful = true;
                } else {
                    LOGGER.error("DB has not been updated: " + updateSt);
                    successful = false;
                    break;
                }

            }

            if (successful) {
                getConnection().commit();
                LOGGER.debug("DB has been updated by commit.");
            } else {
                getConnection().rollback();
                LOGGER.debug("DB has not been updated -> rollback!");
            }

        } catch (SQLException e) {
            LOGGER.error("Query: " + updateSt, e);
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateTopObjectUuid(String oldUuid,
                                       String newUuid,
                                       List<String> lowerObj,
                                       String model,
                                       String name,
                                       String input_queue_directory_path) throws DatabaseException {

        boolean successful = false;
        if (insertNewDigitalObject(newUuid,
                                   model,
                                   name,
                                   DAOUtilsImpl.directoryPathToRightFormat(input_queue_directory_path),
                                   newUuid,
                                   true)) {

            try {
                getConnection().setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.warn("Unable to set autocommit off", e);
            }

            PreparedStatement updateSt = null;
            String oldPid =
                    (oldUuid.startsWith(Constants.FEDORA_UUID_PREFIX)) ? oldUuid
                            : Constants.FEDORA_UUID_PREFIX.concat(oldUuid);

            String newPid =
                    (newUuid.startsWith(Constants.FEDORA_UUID_PREFIX)) ? newUuid
                            : Constants.FEDORA_UUID_PREFIX.concat(newUuid);

            try {
                for (String lowerUuid : lowerObj) {
                    if (!(successful =
                            (getUpdateUuidStateSt(updateSt, oldPid, newPid, lowerUuid).executeUpdate() == 0))) {
                        break;
                    }
                }

                if (successful) {
                    LOGGER.debug("DB has been updated: The top digital object: " + oldUuid
                            + " has been updated to: " + newUuid);
                    getConnection().commit();
                } else {
                    LOGGER.error("DB ERROR!!! The top digital object: " + oldUuid
                            + " has not been updated to: " + newUuid);
                    getConnection().rollback();
                }

            } catch (SQLException e) {
                LOGGER.error("Query: " + updateSt, e);
            } finally {
                closeConnection();
            }
        }
        return successful;
    }

    /**
     * Gets the update uuid state st.
     * 
     * @param updateSt
     *        the update st
     * @param oldPid
     *        the old pid
     * @param newPid
     *        the new pid
     * @param lowerObjUuid
     *        the lower obj uuid
     * @return the update uuid state st
     * @throws SQLException
     *         the sQL exception
     * @throws DatabaseException
     *         the database exception
     */
    private PreparedStatement getUpdateUuidStateSt(PreparedStatement updateSt,
                                                   String oldPid,
                                                   String newPid,
                                                   String lowerObjUuid) throws SQLException,
            DatabaseException {

        String lowerObjPid =
                (lowerObjUuid.startsWith(Constants.FEDORA_UUID_PREFIX)) ? lowerObjUuid
                        : Constants.FEDORA_UUID_PREFIX.concat(lowerObjUuid);

        updateSt = getConnection().prepareStatement(UPDATE_TOP_DO_UUID_AND_STATE);
        updateSt.setString(1, newPid);
        updateSt.setString(2, oldPid);
        updateSt.setString(3, lowerObjPid);

        return updateSt;

    }

    public void insertDOCrudAction(String tableName,
                                   String fkNameCol,
                                   Object foreignKey,
                                   CRUD_ACTION_TYPES type) throws DatabaseException {
        try {
            insertCrudAction(tableName, fkNameCol, foreignKey, type, true);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
