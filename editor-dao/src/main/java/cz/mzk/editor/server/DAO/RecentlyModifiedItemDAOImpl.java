/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
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

import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.rpc.RecentlyModifiedItem;

// TODO: Auto-generated Javadoc
/**
 * The Class RecentlyModifiedItemDAOImpl.
 */
public class RecentlyModifiedItemDAOImpl
        extends AbstractDAO
        implements RecentlyModifiedItemDAO {

    //     recently_modified_item (id, uuid, name, description, modified, model, user_id)  ->
    //            

    //         ->  digital_object (uuid,     model, name, description, input_queue_directory_path, state)
    //                             uuid, ?ordinal?, name,  'null',             'null'               true
    //        
    //         ->  crud_digital_object_action (editor_user_id, timestamp, digital_object_uuid, type)
    //                                                user_id,  modified,                uuid,  'c'
    //        
    //         ->  description (editor_user_id, digital_object_uuid, description)
    //                                 user_id,                uuid, description

    /** The Constant SELECT_LAST_N_STATEMENT. */
    public static final String SELECT_LAST_N_STATEMENT =
            "SELECT o.uuid, o.name, o.description, o.model, MAX(a.timestamp) AS modified FROM (SELECT timestamp, digital_object_uuid FROM "
                    + Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION
                    + " WHERE type='"
                    + Constants.CRUD_ACTION_TYPES.READ.getValue()
                    + "') a INNER JOIN (SELECT name, uuid, description, model FROM "
                    + Constants.TABLE_DIGITAL_OBJECT
                    + " WHERE state = 'true') o ON a.digital_object_uuid=o.uuid GROUP BY o.uuid, o.name, o.model, o.description ORDER by modified DESC LIMIT (?)";

    /** The Constant SELECT_LAST_N_STATEMENT_FOR_USER. */
    public static final String SELECT_LAST_N_STATEMENT_FOR_USER =
            "SELECT o.uuid, o.name, d.description, o.model, MAX(o.timestamp) AS modified FROM (SELECT uuid, name, model, a.timestamp FROM (SELECT timestamp, digital_object_uuid FROM "
                    + Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION
                    + " WHERE type='"
                    + Constants.CRUD_ACTION_TYPES.READ.getValue()
                    + "' AND editor_user_id = (?)) a INNER JOIN (SELECT name, model, uuid, description FROM "
                    + Constants.TABLE_DIGITAL_OBJECT
                    + " WHERE state = 'true') o ON a.digital_object_uuid=o.uuid) o LEFT JOIN (SELECT * FROM "
                    + Constants.TABLE_DESCRIPTION
                    + " WHERE editor_user_id = (?)) d ON d.digital_object_uuid = o.uuid GROUP BY o.uuid, d.description, name, model ORDER by modified DESC LIMIT (?)";

    /** The Constant SELECT_COMMON_DESCRIPTION_STATEMENT. */
    public static final String SELECT_COMMON_DESCRIPTION_STATEMENT = "SELECT description FROM "
            + Constants.TABLE_DIGITAL_OBJECT + " WHERE uuid = (?)";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RecentlyModifiedItemDAOImpl.class);

    /** The dao utils. */
    @Inject
    private DAOUtils daoUtils;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean put(RecentlyModifiedItem toPut) throws DatabaseException {
        if (toPut == null) throw new NullPointerException("toPut");
        if (toPut.getUuid() == null || "".equals(toPut.getUuid()))
            throw new NullPointerException("toPut.getUuid()");

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }
        boolean successful = false;

        try {
            successful =
                    daoUtils.checkDigitalObject(toPut.getUuid(),
                                                toPut.getModel().getValue(),
                                                toPut.getName(),
                                                null,
                                                null,
                                                false);

            if (successful)
                successful =
                        daoUtils.insertCrudAction(getUserId(false),
                                                  Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION,
                                                  "digital_object_uuid",
                                                  toPut.getUuid(),
                                                  CRUD_ACTION_TYPES.READ,
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
    public ArrayList<RecentlyModifiedItem> getItems(int nLatest, Long user_id) throws DatabaseException {
        PreparedStatement selectSt = null;
        ArrayList<RecentlyModifiedItem> retList = new ArrayList<RecentlyModifiedItem>();
        try {
            if (user_id != null) {
                selectSt = getConnection().prepareStatement(SELECT_LAST_N_STATEMENT_FOR_USER);
                selectSt.setLong(1, user_id);
                selectSt.setLong(2, user_id);
                selectSt.setInt(3, nLatest);
            } else {
                selectSt = getConnection().prepareStatement(SELECT_LAST_N_STATEMENT);
                selectSt.setInt(1, nLatest);
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get select items statement", e);
        }
        try {
            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                String modelId = rs.getString("model");
                retList.add(new RecentlyModifiedItem(rs.getString("uuid"),
                                                     rs.getString("name"),
                                                     user_id != null ? rs.getString("description") : "",
                                                     DigitalObjectModel.parseString(modelId),
                                                     rs.getDate("modified")));
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return retList;
    }

}
