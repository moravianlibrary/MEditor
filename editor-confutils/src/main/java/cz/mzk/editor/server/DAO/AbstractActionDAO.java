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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractActionDAO.
 * 
 * @author Matous Jobanek
 * @version Dec 29, 2012
 */
public class AbstractActionDAO
        extends AbstractDAO {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getPackage().toString());

    /**
     * Insert crud action.
     * 
     * @param tableName
     *        the table name
     * @param fkNameCol
     *        the fk name col
     * @param foreignKey
     *        the foreign key
     * @param type
     *        the type
     * @param closeCon
     *        the close con
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     * @throws SQLException
     *         the sQL exception
     */
    protected boolean insertCrudAction(String tableName,
                                       String fkNameCol,
                                       Object foreignKey,
                                       CRUD_ACTION_TYPES type,
                                       boolean closeCon) throws DatabaseException, SQLException {
        return insertAnyCrudAction(getUserId(false), tableName, fkNameCol, foreignKey, type, null, closeCon);
    }

    /**
     * Insert crud action with top object.
     * 
     * @param tableName
     *        the table name
     * @param fkNameCol
     *        the fk name col
     * @param foreignKey
     *        the foreign key
     * @param type
     *        the type
     * @param top_digital_object_uuid
     *        the top_digital_object_uuid
     * @param closeCon
     *        the close con
     * @param con
     *        the con
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     * @throws SQLException
     *         the sQL exception
     */
    protected boolean insertCrudActionWithTopObject(String tableName,
                                                    String fkNameCol,
                                                    Object foreignKey,
                                                    CRUD_ACTION_TYPES type,
                                                    String top_digital_object_uuid,
                                                    boolean closeCon,
                                                    Connection con) throws DatabaseException, SQLException {
        return insertAnyCrudAction(getUserId(false),
                                   tableName,
                                   fkNameCol,
                                   foreignKey,
                                   type,
                                   top_digital_object_uuid,
                                   closeCon);
    }

    /**
     * Insert crud action.
     * 
     * @param editor_user_id
     *        the editor_user_id
     * @param tableName
     *        the table name
     * @param fkNameCol
     *        the fk name col
     * @param foreignKey
     *        the foreign key
     * @param type
     *        the type
     * @param closeCon
     *        the close con
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     * @throws SQLException
     *         the sQL exception
     */
    protected boolean insertCrudAction(Long editor_user_id,
                                       String tableName,
                                       String fkNameCol,
                                       Object foreignKey,
                                       CRUD_ACTION_TYPES type,
                                       boolean closeCon) throws DatabaseException, SQLException {
        return insertAnyCrudAction(editor_user_id, tableName, fkNameCol, foreignKey, type, null, closeCon);
    }

    /**
     * Insert crud action with top object.
     * 
     * @param editor_user_id
     *        the editor_user_id
     * @param tableName
     *        the table name
     * @param fkNameCol
     *        the fk name col
     * @param foreignKey
     *        the foreign key
     * @param type
     *        the type
     * @param top_digital_object_uuid
     *        the top_digital_object_uuid
     * @param closeCon
     *        the close con
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     * @throws SQLException
     *         the sQL exception
     */
    protected boolean insertCrudActionWithTopObject(Long editor_user_id,
                                                    String tableName,
                                                    String fkNameCol,
                                                    Object foreignKey,
                                                    CRUD_ACTION_TYPES type,
                                                    String top_digital_object_uuid,
                                                    boolean closeCon) throws DatabaseException, SQLException {
        return insertAnyCrudAction(editor_user_id,
                                   tableName,
                                   fkNameCol,
                                   foreignKey,
                                   type,
                                   top_digital_object_uuid,
                                   closeCon);
    }

    /**
     * Insert any crud action.
     * 
     * @param editor_user_id
     *        the editor_user_id
     * @param tableName
     *        the table name
     * @param fkNameCol
     *        the fk name col
     * @param foreignKey
     *        the foreign key
     * @param type
     *        the type
     * @param top_digital_object_uuid
     *        the top_digital_object_uuid
     * @param closeCon
     *        the close con
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     * @throws SQLException
     *         the sQL exception
     */
    private boolean insertAnyCrudAction(Long editor_user_id,
                                        String tableName,
                                        String fkNameCol,
                                        Object foreignKey,
                                        CRUD_ACTION_TYPES type,
                                        String top_digital_object_uuid,
                                        boolean closeCon) throws DatabaseException, SQLException {

        PreparedStatement insertSt = null;
        boolean successful = false;
        String sql =
                "INSERT INTO " + tableName + " (editor_user_id, timestamp, " + fkNameCol + ", type"
                        + (top_digital_object_uuid == null ? "" : ", top_digital_object_uuid")
                        + ") VALUES ((?),(CURRENT_TIMESTAMP),(?),(?)"
                        + (top_digital_object_uuid == null ? "" : ",(?)") + ")";

        try {
            insertSt = getConnection().prepareStatement(sql);
            insertSt.setLong(1, editor_user_id);
            insertSt.setObject(2, foreignKey);
            insertSt.setString(3, type.getValue());
            if (top_digital_object_uuid != null) insertSt.setString(4, top_digital_object_uuid);

            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The " + tableName + " item has been inserted.");
                successful = true;
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            if (closeCon) {
                ex.printStackTrace();
            } else {
                throw new SQLException(ex);
            }
        } finally {
            if (closeCon) closeConnection();
        }

        return successful;
    }
}
