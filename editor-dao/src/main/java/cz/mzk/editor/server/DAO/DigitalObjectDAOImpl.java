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

import javax.inject.Inject;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;

/**
 * @author Matous Jobanek
 * @version Oct 22, 2012
 */
public class DigitalObjectDAOImpl
        extends AbstractDAO
        implements DigitalObjectDAO {

    private static final Logger LOGGER = Logger.getLogger(DigitalObjectDAOImpl.class.getPackage().toString());

    //         ->  digital_object (uuid, model, name, description, input_queue_directory_path, state)

    //         ->  crud_digital_object_action (editor_user_id, timestamp, digital_object_uuid, type)

    public static final String DISABLE_DIGITAL_OBJECT_ITEM = "UPDATE " + Constants.TABLE_DIGITAL_OBJECT
            + " SET state = 'false' WHERE uuid = (?)";

    @Inject
    private DAOUtils daoUtils;

    @Override
    public boolean deleteDigitalObject(String uuid, String model, String name, String topObjectUuid)
            throws DatabaseException {
        PreparedStatement deleteSt = null;
        boolean successful = false;

        try {
            if (daoUtils.checkDigitalObject(uuid, model, name, null, null, true)) {
                deleteSt = getConnection().prepareStatement(DISABLE_DIGITAL_OBJECT_ITEM);
                deleteSt.setString(1, uuid);

                if (deleteSt.executeUpdate() == 1) {
                    LOGGER.debug("DB has been updated: The digital object: " + uuid + " has been disabled.");
                    successful =
                            daoUtils.insertCrudActionWithTopObject(getUserId(),
                                                                   Constants.TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT,
                                                                   "digital_object_uuid",
                                                                   uuid,
                                                                   CRUD_ACTION_TYPES.DELETE,
                                                                   topObjectUuid,
                                                                   true);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + deleteSt, e);
        } finally {
            closeConnection();
        }
        return successful;
    }
}
