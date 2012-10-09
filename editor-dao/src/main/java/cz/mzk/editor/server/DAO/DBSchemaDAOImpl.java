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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;

/**
 * @author Jiri Kremser
 * @version 17. 1. 2011
 */
public class DBSchemaDAOImpl
        extends AbstractDAO
        implements DBSchemaDAO {

    //    public static final String SELECT_OLD_VERSION = "SELECT * FROM " + Constants.TABLE_VERSION_NAME
    //            + " WHERE id = 1";

    public static final String SELECT_ALL = "SELECT * FROM ";

    public static final String SELECT_VERSION = SELECT_ALL + Constants.TABLE_VERSION_NAME;

    public static final String UPDATE_VERSION = "UPDATE " + Constants.TABLE_VERSION_NAME
            + " SET version = (?)";

    private static final Logger LOGGER = Logger.getLogger(DBSchemaDAOImpl.class);

    //    
    //newTables    
    //

    public static final String INSERT_ITEM_STATEMENT_ACTION = "INSERT INTO " + Constants.TABLE_ACTION
            + " (editor_user_id, timestamp, successful) VALUES ((?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_ACTION_WITH_TOP_OBJECT =
            "INSERT INTO "
                    + Constants.TABLE_ACTION_WITH_TOP_OBJECT
                    + " (id, editor_user_id, timestamp, successful, top_digital_object_uuid) VALUES ((?),(?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_CONVERSION = "INSERT INTO " + Constants.TABLE_CONVERSION
            + " (editor_user_id, timestamp, successful, input_queue_directory_path) VALUES ((?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_CRUD_DIGITAL_OBJECT_ACTION =
            "INSERT INTO "
                    + Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION
                    + " (editor_user_id, timestamp, successful, digital_object_uuid, type) VALUES ((?),(?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_CRUD_LOCK_ACTION = "INSERT INTO "
            + Constants.TABLE_CRUD_LOCK_ACTION
            + " (editor_user_id, timestamp, successful, lock_id, type) VALUES ((?),(?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_CRUD_REQUEST_TO_ADMIN_ACTION =
            "INSERT INTO "
                    + Constants.TABLE_CRUD_REQUEST_TO_ADMIN_ACTION
                    + " (editor_user_id, timestamp, successful, request_to_admin_id, type) VALUES ((?),(?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_CRUD_SAVED_EDITED_OBJECT_ACTION =
            "INSERT INTO "
                    + Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION
                    + " (editor_user_id, timestamp, successful, saved_edited_object_id, type) VALUES ((?),(?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_CRUD_TREE_STRUCTURE_ACTION =
            "INSERT INTO "
                    + Constants.TABLE_CRUD_TREE_STRUCTURE_ACTION
                    + " (editor_user_id, timestamp, successful, tree_structure_id, type) VALUES ((?),(?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_DESCRIPTION = "INSERT INTO "
            + Constants.TABLE_DESCRIPTION
            + " (editor_user_id, digital_object_uuid, description) VALUES ((?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_DIGITAL_OBJECT = "INSERT INTO "
            + Constants.TABLE_DIGITAL_OBJECT
            + " (uuid, model, name, description, input_queue_directory_path) VALUES ((?),(?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_EDITOR_RIGHT = "INSERT INTO "
            + Constants.TABLE_EDITOR_RIGHT + " (name, description) VALUES ((?),(?))";

    public static final String INSERT_ITEM_STATEMENT_EDITOR_USER = "INSERT INTO "
            + Constants.TABLE_EDITOR_USER + " (id, name, surname, state) VALUES ((?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_IMAGE = "INSERT INTO " + Constants.TABLE_IMAGE
            + " (identifier, shown, old_fs_path, imagefile) VALUES ((?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_INPUT_QUEUE = "INSERT INTO "
            + Constants.TABLE_INPUT_QUEUE + " (path, barcode, ingested) VALUES ((?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_INPUT_QUEUE_ITEM = "INSERT INTO "
            + Constants.TABLE_INPUT_QUEUE_ITEM + " (directory_path, name) VALUES ((?),(?))";

    public static final String INSERT_ITEM_STATEMENT_LDAP_IDENTITY = "INSERT INTO "
            + Constants.TABLE_LDAP_IDENTITY + " (editor_user_id, identity) VALUES ((?),(?))";

    public static final String INSERT_ITEM_STATEMENT_LOCK = "INSERT INTO " + Constants.TABLE_LOCK
            + " (digital_object_uuid, description, state) VALUES ((?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_LOG_IN_OUT = "INSERT INTO " + Constants.TABLE_LOG_IN_OUT
            + " (editor_user_id, timestamp, successful, type) VALUES ((?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_LONG_RUNNING_PROCESS = "INSERT INTO "
            + Constants.TABLE_LONG_RUNNING_PROCESS
            + " (editor_user_id, timestamp, successful, name, finished) VALUES ((?),(?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_OPEN_ID_IDENTITY = "INSERT INTO "
            + Constants.TABLE_OPEN_ID_IDENTITY + " (editor_user_id, identity) VALUES ((?),(?))";

    public static final String INSERT_ITEM_STATEMENT_REQUEST_TO_ADMIN = "INSERT INTO "
            + Constants.TABLE_REQUEST_TO_ADMIN
            + " (admin_editor_user_id, type, object, description, solved) VALUES ((?),(?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_RIGHT_IN_ROLE = "INSERT INTO "
            + Constants.TABLE_RIGHT_IN_ROLE + " (editor_right_name, role_name) VALUES ((?),(?))";

    public static final String INSERT_ITEM_STATEMENT_ROLE = "INSERT INTO " + Constants.TABLE_ROLE
            + " (name, description) VALUES ((?),(?))";

    public static final String INSERT_ITEM_STATEMENT_SAVED_EDITED_OBJECT = "INSERT INTO "
            + Constants.TABLE_SAVED_EDITED_OBJECT
            + " (digital_object_uuid, file_name, description, state) VALUES ((?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_SHIBBOLETH_IDENTITY = "INSERT INTO "
            + Constants.TABLE_SHIBBOLETH_IDENTITY + " (editor_user_id, identity) VALUES ((?),(?))";

    public static final String INSERT_ITEM_STATEMENT_TREE_STRUCTURE =
            "INSERT INTO "
                    + Constants.TABLE_TREE_STRUCTURE
                    + " (barcode, description, name, model, state, input_queue_directory_path) VALUES ((?),(?),(?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_TREE_STRUCTURE_NODE =
            "INSERT INTO "
                    + Constants.TABLE_TREE_STRUCTURE_NODE
                    + " (tree_structure_id, prop_id, prop_parent, prop_name, prop_picture_or_uuid, prop_model_id, prop_type, prop_date_or_int_part_name, prop_note_or_int_subtitle, prop_part_number_or_alto, prop_aditional_info_or_ocr, prop_exist) VALUES ((?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_USER_EDIT =
            "INSERT INTO "
                    + Constants.TABLE_USER_EDIT
                    + " (editor_user_id, timestamp, successful, edited_editor_user_id, description, type) VALUES ((?),(?),(?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_USERS_RIGHT = "INSERT INTO "
            + Constants.TABLE_USERS_RIGHT + " (editor_user_id, editor_right_name) VALUES ((?),(?))";

    public static final String INSERT_ITEM_STATEMENT_USERS_ROLE = "INSERT INTO " + Constants.TABLE_USERS_ROLE
            + " (editor_user_id, role_name) VALUES ((?),(?))";

    public static final String INSERT_ITEM_STATEMENT_VERSION = "INSERT INTO " + Constants.TABLE_VERSION
            + " (version) VALUES ((?))";

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canConnect() {
        try {
            getConnection();
        } catch (DatabaseException e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int checkVersion(int version) throws DatabaseException {
        PreparedStatement statement = null;
        int versionDb = -1;
        try {
            statement = getConnection().prepareStatement(SELECT_VERSION);
            ResultSet rs = statement.executeQuery();
            int columnCount = statement.getMetaData().getColumnCount();
            if (columnCount != 1) {
                return -1;
            }
            while (rs.next()) {
                versionDb = rs.getInt("version");
            }
            return (versionDb >= version) ? 1 : 0;
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DatabaseException("unable to obtain the version of the DB", e);
        } catch (NumberFormatException nfe) {
            LOGGER.error(nfe);
        } finally {
            closeConnection();
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateSchema(int version, String pathPrefix) throws DatabaseException {
        File dbSchema = new File(pathPrefix + File.separator + Constants.SCHEMA_PATH);
        if (!dbSchema.exists()) {
            throw new DatabaseException("Unable to find the file with DB schema "
                    + dbSchema.getAbsolutePath());
        }
        if (!dbSchema.canRead()) {
            throw new DatabaseException("Unable to read from the file with DB schema "
                    + dbSchema.getAbsolutePath());
        }
        try {
            ScriptRunner runner = new ScriptRunner(getConnection(), false, true);
            runner.setLogger(LOGGER);
            Reader reader = null;
            try {
                reader = new FileReader(dbSchema);
                runner.runScript(reader);
            } catch (FileNotFoundException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
                throw new DatabaseException("Unable to find the file with DB schema "
                        + dbSchema.getAbsolutePath(), e);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
                throw new DatabaseException("Unable to read from file with DB schema "
                        + dbSchema.getAbsolutePath(), e);
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
                throw new DatabaseException("Unable to run SQL command: ", e);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                        e.printStackTrace();
                        reader = null;
                    }
                }
            }
            Writer writer = null;
            try {
                writer =
                        new BufferedWriter(new FileWriter(pathPrefix + File.separator
                                + Constants.SCHEMA_VERSION_PATH));
                writer.write(String.valueOf(version));
                writer.flush();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
                throw new DatabaseException("Unable to write to file with DB schema version "
                        + dbSchema.getAbsolutePath(), e);
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                        e.printStackTrace();
                        writer = null;
                    }
                }
            }
        } finally {
            closeConnection();
        }
        updateVersionInDb(version);
    }

    private void updateVersionInDb(int version) throws DatabaseException {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement(UPDATE_VERSION);
            statement.setInt(1, version);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DatabaseException("unable to update version number of the DB", e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public Map<Long, String[]> getAllDataFromTable(String tableName) {
        PreparedStatement selStatement = null;
        Map<Long, String[]> rows = null;
        try {
            selStatement = getConnection().prepareStatement(SELECT_ALL + tableName);
            ResultSet rs = selStatement.executeQuery();

            int columnCount = selStatement.getMetaData().getColumnCount();
            long row = 0;
            String[] columns = new String[columnCount];
            rows = new HashMap<Long, String[]>();

            for (int i = 0; i < columnCount; i++) {
                columns[i] = selStatement.getMetaData().getColumnClassName(i + 1);
            }
            rows.put(row++, columns);

            while (rs.next()) {
                columns = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    columns[i] = rs.getString(i + 1);
                }
                rows.put(row++, columns);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (DatabaseException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return rows;
    }

    /**
     * {@inheritDoc}
     */
    //    description (id, uuid, description)  ->  digital_object (uuid, model, name, description, input_queue_directory_path);
    //                                                             uuid, ?????, ????, description,             'null'
    @Override
    public void transformAndPutDescription(Map<Long, String[]> oldData) {

        for (long i = 1; i < oldData.size(); i++) {

        }

    }
}
