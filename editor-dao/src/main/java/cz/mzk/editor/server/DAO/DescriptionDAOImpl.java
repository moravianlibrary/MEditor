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

import javax.inject.Inject;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;

/**
 * @author Matous Jobanek
 * @version Oct 12, 2012
 */
public class DescriptionDAOImpl
        extends AbstractDAO
        implements DescriptionDAO {

    private static final Logger LOGGER = Logger.getLogger(DescriptionDAO.class);

    /** The Constant DESCRIPTION_SELECT_DESC_STATEMENT. */
    public static final String DESCRIPTION_SELECT_DESC_STATEMENT = "SELECT description FROM "
            + Constants.TABLE_DESCRIPTION + " WHERE digital_object_uuid=(?) AND editor_user_id=(?)";

    /** The Constant DESCRIPTION_UPDATE_DESC_STATEMENT. */
    public static final String DESCRIPTION_UPDATE_DESC_STATEMENT = "UPDATE " + Constants.TABLE_DESCRIPTION
            + " SET description=(?) WHERE digital_object_uuid=(?) AND editor_user_id=(?)";

    /** The Constant SELECT_COMMON_DESCRIPTION_STATEMENT. */
    public static final String SELECT_COMMON_DESCRIPTION_STATEMENT = "SELECT description FROM "
            + Constants.TABLE_DIGITAL_OBJECT + " WHERE uuid = (?)";

    /** The dao utils. */
    @Inject
    private DAOUtils daoUtils;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCommonDescription(String uuid) throws DatabaseException {
        if (uuid == null) throw new NullPointerException("uuid");
        String description = null;
        PreparedStatement findSt = null;
        try {
            findSt = getConnection().prepareStatement(SELECT_COMMON_DESCRIPTION_STATEMENT);
            findSt.setString(1, uuid);
            ResultSet rs = findSt.executeQuery();

            while (rs.next()) {
                description = rs.getString("description");
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + findSt, e);
        } finally {
            closeConnection();
        }
        return description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean putCommonDescription(String uuid, String description, Long user_id)
            throws DatabaseException {
        if (uuid == null) throw new NullPointerException("uuid");
        if (description == null) throw new NullPointerException("description");

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }
        try {

            boolean successful = daoUtils.checkDigitalObject(uuid, null, null, description, null, false);
            successful =
                    daoUtils.insertCrudAction(user_id,
                                              Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION,
                                              "digital_object_uuid",
                                              uuid,
                                              CRUD_ACTION_TYPES.UPDATE,
                                              false);

            if (successful) {
                getConnection().commit();
                LOGGER.debug("DB has been updated by commit.");
            } else {
                getConnection().rollback();
                LOGGER.error("DB has not been updated -> rollback!");
            }
            // TX end
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            closeConnection();
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserDescription(String digital_object_uuid) throws DatabaseException {

        PreparedStatement selSt = null;
        String desc = null;
        try {
            selSt = getConnection().prepareStatement(DESCRIPTION_SELECT_DESC_STATEMENT);
            selSt.setString(1, digital_object_uuid);
            selSt.setLong(2, getUserId());
            ResultSet rs = selSt.executeQuery();
            if (rs.next()) {
                desc = rs.getString("description");
                desc = (desc == null) ? "" : desc;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get select statement " + selSt, e);
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return desc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkUserDescription(String digital_object_uuid, String description)
            throws DatabaseException {

        String desc = getUserDescription(digital_object_uuid);
        boolean successful = false;
        PreparedStatement updateSt = null;

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }

        Long editor_user_id = getUserId();
        try {
            if (desc == null) {
                successful = daoUtils.insertDescription(editor_user_id, digital_object_uuid, description);
                if (successful)
                    daoUtils.insertCrudAction(editor_user_id,
                                              Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION,
                                              "digital_object_uuid",
                                              digital_object_uuid,
                                              CRUD_ACTION_TYPES.UPDATE,
                                              false);
            } else if (!desc.equals(description)) {

                updateSt = getConnection().prepareStatement(DESCRIPTION_UPDATE_DESC_STATEMENT);
                updateSt.setString(1, description);
                updateSt.setString(2, digital_object_uuid);
                updateSt.setLong(3, editor_user_id);
                int updated = updateSt.executeUpdate();

                if (updated == 1) {
                    LOGGER.debug("DB has been updated: The user's: " + editor_user_id
                            + " description of object: " + digital_object_uuid + " has been updated.");

                    daoUtils.insertCrudAction(editor_user_id,
                                              Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION,
                                              "digital_object_uuid",
                                              digital_object_uuid,
                                              CRUD_ACTION_TYPES.UPDATE,
                                              false);
                    successful = true;
                } else {
                    LOGGER.error("DB has not been updated! " + updateSt);
                }
            }
            if (successful) {
                getConnection().commit();
                LOGGER.debug("DB has been updated by commit.");
            } else {
                getConnection().rollback();
                LOGGER.error("DB has not been updated -> rollback!");
            }
        } catch (SQLException ex) {
            LOGGER.error("Could not get update item statement " + updateSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }

        return successful;
    }
}
