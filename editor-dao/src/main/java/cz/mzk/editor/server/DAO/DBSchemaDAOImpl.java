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
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;
import cz.mzk.editor.client.util.Constants.REQUESTS_TO_ADMIN_TYPES;
import cz.mzk.editor.shared.domain.DigitalObjectModel;

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

    public static final String ACTION_INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_ACTION
            + " (editor_user_id, timestamp, successful) VALUES ((?),(?),(?))";

    public static final String ACTION_WITH_TOP_OBJECT_INSERT_ITEM_STATEMENT =
            "INSERT INTO "
                    + Constants.TABLE_ACTION_WITH_TOP_OBJECT
                    + " (id, editor_user_id, timestamp, successful, top_digital_object_uuid) VALUES ((?),(?),(?),(?),(?))";

    public static final String INSERT_ITEM_STATEMENT_CONVERSION = "INSERT INTO " + Constants.TABLE_CONVERSION
            + " (editor_user_id, timestamp, successful, input_queue_directory_path) VALUES ((?),(?),(?),(?))";

    public static final String CRUD_DIGITAL_OBJECT_ACTION_INSERT_ITEM_STATEMENT =
            "INSERT INTO "
                    + Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION
                    + " (editor_user_id, timestamp, successful, digital_object_uuid, type) VALUES ((?),(?),(?),(?),(?))";

    public static final String CRUD_LOCK_ACTION_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_CRUD_LOCK_ACTION
            + " (editor_user_id, timestamp, successful, lock_id, type) VALUES ((?),(?),(?),(?),(?))";

    public static final String CRUD_REQUEST_TO_ADMIN_ACTION_INSERT_ITEM_STATEMENT =
            "INSERT INTO "
                    + Constants.TABLE_CRUD_REQUEST_TO_ADMIN_ACTION
                    + " (editor_user_id, timestamp, successful, request_to_admin_id, type) VALUES ((?),(?),(?),(?),(?))";

    public static final String CRUD_SAVED_EDITED_OBJECT_ACTION_INSERT_ITEM_STATEMENT =
            "INSERT INTO "
                    + Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION
                    + " (editor_user_id, timestamp, successful, saved_edited_object_id, type) VALUES ((?),(?),(?),(?),(?))";

    public static final String CRUD_TREE_STRUCTURE_ACTION_INSERT_ITEM_STATEMENT =
            "INSERT INTO "
                    + Constants.TABLE_CRUD_TREE_STRUCTURE_ACTION
                    + " (editor_user_id, timestamp, successful, tree_structure_id, type) VALUES ((?),(?),(?),(?),(?))";

    public static final String DESCRIPTION_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_DESCRIPTION
            + " (editor_user_id, digital_object_uuid, description) VALUES ((?),(?),(?))";

    public static final String DIGITAL_OBJECT_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_DIGITAL_OBJECT
            + " (uuid, model, name, description, input_queue_directory_path) VALUES ((?),(?),(?),(?),(?))";

    public static final String DIGITAL_OBJECT_SELECT_ITEM_STATEMENT = "SELECT * FROM "
            + Constants.TABLE_DIGITAL_OBJECT + " WHERE uuid=(?)";

    public static final String DIGITAL_OBJECT_UPDATE_ITEM_STATEMENT = "UPDATE "
            + Constants.TABLE_DIGITAL_OBJECT
            + " SET model=(?), name=(?), description=(?), input_queue_directory_path=(?) WHERE uuid=(?)";

    public static final String EDITOR_RIGHT_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_EDITOR_RIGHT + " (name, description) VALUES ((?),(?))";

    public static final String EDITOR_USER_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_EDITOR_USER + " (name, surname, state) VALUES ((?),(?),(?))";

    public static final String IMAGE_INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_IMAGE
            + " (identifier, shown, old_fs_path, imagefile) VALUES ((?),(?),(?),(?))";

    public static final String INPUT_QUEUE_ITEM_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_INPUT_QUEUE_ITEM + " (path, barcode, ingested) VALUES ((?),(?),(?))";

    public static final String INPUT_QUEUE_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_INPUT_QUEUE + " (directory_path, name) VALUES ((?),(?))";

    public static final String LDAP_IDENTITY_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_LDAP_IDENTITY + " (editor_user_id, identity) VALUES ((?),(?))";

    public static final String LOCK_INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_LOCK
            + " (digital_object_uuid, description, state) VALUES ((?),(?),(?))";

    public static final String LOG_IN_OUT_INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_LOG_IN_OUT
            + " (editor_user_id, timestamp, successful, type) VALUES ((?),(?),(?),(?))";

    public static final String LONG_RUNNING_PROCESS_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_LONG_RUNNING_PROCESS
            + " (editor_user_id, timestamp, successful, name, finished) VALUES ((?),(?),(?),(?),(?))";

    public static final String OPEN_ID_IDENTITY_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_OPEN_ID_IDENTITY + " (editor_user_id, identity) VALUES ((?),(?))";

    public static final String REQUEST_TO_ADMIN_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_REQUEST_TO_ADMIN
            + " (admin_editor_user_id, type, object, description, solved) VALUES ((?),(?),(?),(?),(?))";

    public static final String RIGHT_IN_ROLE_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_RIGHT_IN_ROLE + " (editor_right_name, role_name) VALUES ((?),(?))";

    public static final String ROLE_INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_ROLE
            + " (name, description) VALUES ((?),(?))";

    public static final String SAVED_EDITED_OBJECT_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_SAVED_EDITED_OBJECT
            + " (digital_object_uuid, file_name, description, state) VALUES ((?),(?),(?),(?))";

    public static final String SHIBBOLETH_IDENTITY_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_SHIBBOLETH_IDENTITY + " (editor_user_id, identity) VALUES ((?),(?))";

    public static final String TREE_STRUCTURE_INSERT_ITEM_STATEMENT =
            "INSERT INTO "
                    + Constants.TABLE_TREE_STRUCTURE
                    + " (barcode, description, name, model, state, input_queue_directory_path) VALUES ((?),(?),(?),(?),(?),(?))";

    public static final String TREE_STRUCTURE_NODE_INSERT_ITEM_STATEMENT =
            "INSERT INTO "
                    + Constants.TABLE_TREE_STRUCTURE_NODE
                    + " (tree_structure_id, prop_id, prop_parent, prop_name, prop_picture_or_uuid, prop_model_id, prop_type, prop_date_or_int_part_name, prop_note_or_int_subtitle, prop_part_number_or_alto, prop_aditional_info_or_ocr, prop_exist) VALUES ((?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?))";

    public static final String USER_EDIT_INSERT_ITEM_STATEMENT =
            "INSERT INTO "
                    + Constants.TABLE_USER_EDIT
                    + " (editor_user_id, timestamp, successful, edited_editor_user_id, description, type) VALUES ((?),(?),(?),(?),(?),(?))";

    public static final String USERS_RIGHT_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_USERS_RIGHT + " (editor_user_id, editor_right_name) VALUES ((?),(?))";

    public static final String USERS_ROLE_INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_USERS_ROLE
            + " (editor_user_id, role_name) VALUES ((?),(?))";

    public static final String VERSION_INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_VERSION
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
            if (rs.next()) {
                versionDb = rs.getInt("version");
            } else {
                statement = getConnection().prepareStatement(VERSION_INSERT_ITEM_STATEMENT);
                statement.setInt(1, version);

                if (statement.executeUpdate() == 1) {
                    LOGGER.debug("DB has been updated: The version: " + version + " has been inserted.");
                } else {
                    LOGGER.error("DB has not been updated! " + statement);
                }
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
    public Map<Long, Object[]> getAllDataFromTable(String tableName) throws ClassNotFoundException {
        PreparedStatement selStatement = null;
        Map<Long, Object[]> rows = null;
        try {
            selStatement = getConnection().prepareStatement(SELECT_ALL + tableName);
            ResultSet rs = selStatement.executeQuery();

            int columnCount = selStatement.getMetaData().getColumnCount();
            long row = 0;
            String[] types = new String[columnCount];
            rows = new HashMap<Long, Object[]>();

            for (int i = 0; i < columnCount; i++) {
                types[i] = selStatement.getMetaData().getColumnClassName(i + 1);
            }
            rows.put(row++, types);
            Object[] columns;
            while (rs.next()) {
                columns = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    columns[i] = Class.forName(types[i]).cast((rs.getObject(i + 1)));
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

    public boolean checkDigitalObject(String uuid,
                                      String model,
                                      String name,
                                      String description,
                                      String input_queue_directory_path) throws DatabaseException {

        PreparedStatement selSt = null;
        try {
            boolean thereIs = false;
            selSt = getConnection().prepareStatement(DIGITAL_OBJECT_SELECT_ITEM_STATEMENT);
            selSt.setString(1, uuid);
            ResultSet rs = selSt.executeQuery();

            while (rs.next()) {
                thereIs = true;
                boolean changed = false;
                String chaModel = rs.getString("model");

                if (model != null && !model.equals(chaModel)) {
                    chaModel = model;
                    changed = true;
                }
                String chaName = rs.getString("name");
                if (name != null && !name.equals(chaName)) {
                    chaName = name;
                    changed = true;
                }
                String chaDescription = rs.getString("description");
                if (description != null && !description.equals(chaDescription)) {
                    chaDescription = description;
                    changed = true;
                }
                String chaInputPath = rs.getString("input_queue_directory_path");
                if (input_queue_directory_path != null && !input_queue_directory_path.equals(chaInputPath)) {
                    chaInputPath = input_queue_directory_path;
                    changed = true;
                }
                if (changed) {
                    updateDigitalObject(uuid, chaModel, chaName, chaDescription, chaInputPath);
                }
            }

            if (!thereIs) {
                insertDigitalObject(uuid, model, name, description, input_queue_directory_path);
            }

        } catch (SQLException e) {
            LOGGER.error("Could not get select statement " + selSt, e);
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return true;
    }

    public void updateDigitalObject(String uuid,
                                    String model,
                                    String name,
                                    String description,
                                    String input_queue_directory_path) throws DatabaseException {
        PreparedStatement updateSt = null;
        try {
            updateSt = getConnection().prepareStatement(DIGITAL_OBJECT_UPDATE_ITEM_STATEMENT);
            updateSt.setString(1, model);
            updateSt.setString(2, name);
            updateSt.setString(3, description);
            updateSt.setString(4, input_queue_directory_path);
            updateSt.setString(5, uuid);
            int updated = updateSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The digital object: " + uuid + " has been updated.");
            } else {
                LOGGER.error("DB has not been updated! " + updateSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + updateSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void insertDigitalObject(String uuid,
                                    String model,
                                    String name,
                                    String description,
                                    String input_queue_directory_path) throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt = getConnection().prepareStatement(DIGITAL_OBJECT_INSERT_ITEM_STATEMENT);
            insertSt.setString(1, uuid);
            insertSt.setString(2, model);
            insertSt.setString(3, name);
            insertSt.setString(4, description);
            insertSt.setString(5, input_queue_directory_path);
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The digital object: " + uuid + " has been inserted.");
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void insertCrudDigitalObjectAction(Long editor_user_id,
                                              Timestamp timestamp,
                                              boolean successful,
                                              String digital_object_uuid,
                                              CRUD_ACTION_TYPES type) throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt = getConnection().prepareStatement(CRUD_DIGITAL_OBJECT_ACTION_INSERT_ITEM_STATEMENT);
            insertSt.setLong(1, editor_user_id);
            insertSt.setTimestamp(2, timestamp);
            insertSt.setBoolean(3, successful);
            insertSt.setString(4, digital_object_uuid);
            insertSt.setString(5, type.getValue());
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The CRUD action: " + type.toString() + " digital object: "
                        + digital_object_uuid + " has been inserted.");
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void insertEditorUser(String name, String surname, boolean state) throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt = getConnection().prepareStatement(EDITOR_USER_INSERT_ITEM_STATEMENT);
            insertSt.setString(1, name);
            insertSt.setString(2, surname);
            insertSt.setBoolean(3, state);
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The editor user: " + name + " " + surname
                        + " has been inserted.");
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void insertImage(String identifier, java.sql.Timestamp shown, String old_fs_path, String imagefile)
            throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt = getConnection().prepareStatement(IMAGE_INSERT_ITEM_STATEMENT);
            insertSt.setString(1, identifier);
            insertSt.setTimestamp(2, shown);
            insertSt.setString(3, old_fs_path);
            insertSt.setString(4, imagefile);
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The image: " + identifier + " has been inserted.");
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void insertInputQueueItem(String path, String barcode, Boolean ingested) throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt = getConnection().prepareStatement(INPUT_QUEUE_ITEM_INSERT_ITEM_STATEMENT);
            insertSt.setString(1, path);
            insertSt.setString(2, barcode);
            insertSt.setBoolean(3, ingested);
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The input queue item: " + path + " has been inserted.");
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void insertInputQueue(String directory_path, String name) throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt = getConnection().prepareStatement(INPUT_QUEUE_INSERT_ITEM_STATEMENT);
            insertSt.setString(1, directory_path);
            insertSt.setString(2, name);
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The input queue: " + directory_path
                        + " has been inserted.");
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void insertOpenIdIdentity(long editor_user_id, String identity) throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt = getConnection().prepareStatement(OPEN_ID_IDENTITY_INSERT_ITEM_STATEMENT);
            insertSt.setLong(1, editor_user_id);
            insertSt.setString(2, identity);
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The openId identity: " + identity + " has been inserted.");
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void insertDescription(long editor_user_id, String digital_object_uuid, String description)
            throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt = getConnection().prepareStatement(DESCRIPTION_INSERT_ITEM_STATEMENT);
            insertSt.setLong(1, editor_user_id);
            insertSt.setString(2, digital_object_uuid);
            insertSt.setString(3, description);
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: A description has been inserted.");
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public Long insertRequestToAdmin(long admin_editor_user_id,
                                     REQUESTS_TO_ADMIN_TYPES type,
                                     String object,
                                     String description,
                                     boolean solved) throws DatabaseException {
        PreparedStatement insertSt = null;
        Long id = null;
        try {
            insertSt =
                    getConnection().prepareStatement(REQUEST_TO_ADMIN_INSERT_ITEM_STATEMENT,
                                                     Statement.RETURN_GENERATED_KEYS);
            insertSt.setLong(1, admin_editor_user_id);
            insertSt.setString(2, type.getValue());
            insertSt.setString(3, object);
            insertSt.setString(4, description);
            insertSt.setBoolean(5, solved);
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The request to admin: " + type.toString()
                        + " has been inserted.");
                ResultSet gk = insertSt.getGeneratedKeys();
                if (gk.next()) {
                    id = Long.parseLong(Integer.toString(gk.getInt(1)));
                } else {
                    LOGGER.error("No key has been returned! " + insertSt);
                }
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return id;
    }

    public Long insertSavedEditedObject(String digital_object_uuid,
                                        String file_name,
                                        String description,
                                        boolean state) throws DatabaseException {
        PreparedStatement insertSt = null;
        Long id = null;
        try {
            insertSt =
                    getConnection().prepareStatement(SAVED_EDITED_OBJECT_INSERT_ITEM_STATEMENT,
                                                     Statement.RETURN_GENERATED_KEYS);
            insertSt.setString(1, digital_object_uuid);
            insertSt.setString(2, file_name);
            insertSt.setString(3, description);
            insertSt.setBoolean(4, state);
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The saved edited object: " + digital_object_uuid
                        + " has been inserted.");
                ResultSet gk = insertSt.getGeneratedKeys();
                if (gk.next()) {
                    id = Long.parseLong(Integer.toString(gk.getInt(1)));
                } else {
                    LOGGER.error("No key has been returned! " + insertSt);
                }
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return id;
    }

    public void insertCrudSavedEditedObjectAction(Long editor_user_id,
                                                  Timestamp timestamp,
                                                  boolean successful,
                                                  Long saved_edited_object_id,
                                                  CRUD_ACTION_TYPES type) throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt =
                    getConnection().prepareStatement(CRUD_SAVED_EDITED_OBJECT_ACTION_INSERT_ITEM_STATEMENT);
            insertSt.setLong(1, editor_user_id);
            insertSt.setTimestamp(2, timestamp);
            insertSt.setBoolean(3, successful);
            insertSt.setLong(4, saved_edited_object_id);
            insertSt.setString(5, type.getValue());
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated:  The CRUD action: " + type.toString()
                        + " of saved edited object: " + saved_edited_object_id + " has beeen inserted.");
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void insertCrudRequestToAdminAction(long editor_user_id,
                                               Timestamp timestamp,
                                               boolean successful,
                                               long request_to_admin_id,
                                               CRUD_ACTION_TYPES type) throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt = getConnection().prepareStatement(CRUD_REQUEST_TO_ADMIN_ACTION_INSERT_ITEM_STATEMENT);
            insertSt.setLong(1, editor_user_id);
            insertSt.setTimestamp(2, timestamp);
            insertSt.setBoolean(3, successful);
            insertSt.setLong(4, request_to_admin_id);
            insertSt.setString(5, type.getValue());
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated:  The CRUD action: " + type.toString()
                        + " of request to admin: " + request_to_admin_id + " has beeen inserted.");
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public Long insertTreeStructure(String barcode,
                                    String description,
                                    String name,
                                    String model,
                                    boolean state,
                                    String input_queue_directory_path) throws DatabaseException {
        PreparedStatement insertSt = null;
        Long id = null;
        try {
            insertSt =
                    getConnection().prepareStatement(TREE_STRUCTURE_INSERT_ITEM_STATEMENT,
                                                     Statement.RETURN_GENERATED_KEYS);
            insertSt.setString(1, barcode);
            insertSt.setString(2, description);
            insertSt.setString(3, name);
            insertSt.setString(4, model);
            insertSt.setBoolean(5, state);
            insertSt.setString(6, input_queue_directory_path);
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The tree structure: " + input_queue_directory_path
                        + " has been inserted.");
                ResultSet gk = insertSt.getGeneratedKeys();
                if (gk.next()) {
                    id = Long.parseLong(Integer.toString(gk.getInt(1)));
                } else {
                    LOGGER.error("No key has been returned! " + insertSt);
                }
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return id;
    }

    public void insertCrudTreeStructureAction(long editor_user_id,
                                              Timestamp timestamp,
                                              boolean successful,
                                              long tree_structure_id,
                                              CRUD_ACTION_TYPES type) throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt = getConnection().prepareStatement(CRUD_TREE_STRUCTURE_ACTION_INSERT_ITEM_STATEMENT);
            insertSt.setLong(1, editor_user_id);
            insertSt.setTimestamp(2, timestamp);
            insertSt.setBoolean(3, successful);
            insertSt.setLong(4, tree_structure_id);
            insertSt.setString(5, type.getValue());
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated:  The CRUD action: " + type.toString()
                        + " of tree structure: " + tree_structure_id + " has beeen inserted.");
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void insertTreeStructureNode(long tree_structure_id,
                                        String prop_id,
                                        String prop_parent,
                                        String prop_name,
                                        String prop_picture_or_uuid,
                                        String prop_model_id,
                                        String prop_type,
                                        String prop_date_or_int_part_name,
                                        String prop_note_or_int_subtitle,
                                        String prop_part_number_or_alto,
                                        String prop_aditional_info_or_ocr,
                                        boolean prop_exist) throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt = getConnection().prepareStatement(TREE_STRUCTURE_NODE_INSERT_ITEM_STATEMENT);
            insertSt.setLong(1, tree_structure_id);
            insertSt.setString(2, prop_id);
            insertSt.setString(3, prop_parent);
            insertSt.setString(4, prop_name);
            insertSt.setString(5, prop_picture_or_uuid);
            insertSt.setString(6, prop_model_id);
            insertSt.setString(7, prop_type);
            insertSt.setString(8, prop_date_or_int_part_name);
            insertSt.setString(9, prop_note_or_int_subtitle);
            insertSt.setString(10, prop_part_number_or_alto);
            insertSt.setString(11, prop_aditional_info_or_ocr);
            insertSt.setBoolean(12, prop_exist);
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated:  The tree structure node: " + prop_name
                        + " of tree structure: " + tree_structure_id + " has beeen inserted.");
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     */
    //    description (id, uuid, description) -> digital_object (uuid, model, name, description, input_queue_directory_path);
    //                                                           uuid, ?????, ????, description,             'null'
    @Override
    public void transformAndPutDescription(Map<Long, Object[]> oldData) throws DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] desc = oldData.get(i);
            String uuid = (String) desc[1];
            checkDigitalObject((uuid).startsWith("uuid:") ? uuid : "uuid:" + uuid,
                               "?",
                               null,
                               (String) desc[2],
                               null);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     */
    //    editor_user (id, name, surname, sex) -> editor_user (id, name, surname, state)
    //                                                             name, surname, 'true'
    @Override
    public Map<Long, Long> transformAndPutEditorUser(Map<Long, Object[]> oldData) throws DatabaseException {

        Map<Long, Long> editorUserIdMapping = new HashMap<Long, Long>(oldData.size());

        insertEditorUser(Constants.NON_EXISTENT, Constants.NON_EXISTENT, true);

        for (long i = 1; i < oldData.size(); i++) {
            Object[] user = oldData.get(i);
            insertEditorUser((String) user[1], (String) user[2], true);
            editorUserIdMapping.put(Long.parseLong(user[0].toString()), i);
        }
        return editorUserIdMapping;
    }

    //    Class.forName(types[i]).cast();

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     */
    //  image (id, identifier, shown, old_fs_path, imagefile) -> image (identifier, shown, old_fs_path, imagefile)
    //                                                                  identifier, shown, old_fs_path, imagefile
    @Override
    public void transformAndPutImage(Map<Long, Object[]> oldData) throws DatabaseException {

        String[] types = (String[]) oldData.get(1);
        for (long i = 1; i < oldData.size(); i++) {
            Object[] image = oldData.get(i);
            insertImage((String) image[1], (Timestamp) image[2], (String) image[3], (String) image[4]);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     */
    //    input_queue_item (id, path, barcode, ingested) -> input_queue_item (path, barcode, ingested)
    //                                                                        path, barcode, ingested
    @Override
    public void transformAndPutInputQueueItem(Map<Long, Object[]> oldData) throws DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] queueItem = oldData.get(i);
            insertInputQueueItem((String) queueItem[1], (String) queueItem[2], (Boolean) queueItem[3]);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     */
    //    input_queue_item_name (id, path, name) -> input_queue (directory_path, name)
    //                                                                     path, name
    @Override
    public void transformAndPutInputQueue(Map<Long, Object[]> oldData) throws DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] inputQueue = oldData.get(i);
            insertInputQueue((String) inputQueue[1], (String) inputQueue[2]);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     * @throws NumberFormatException
     */
    //    open_id_identity (id, user_id, identity) -> open_id_identity (editor_user_id, identity)
    //                                                                         user_id, identity
    @Override
    public void transformAndPutOpenIdIdentity(Map<Long, Object[]> oldData, Map<Long, Long> editorUserIdMapping)
            throws NumberFormatException, DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] openId = oldData.get(i);
            insertOpenIdIdentity(editorUserIdMapping.get(Long.parseLong(openId[1].toString())),
                                 (String) openId[2]);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     * @throws NumberFormatException
     */
    //     recently_modified_item (id, uuid, name, description, modified, model, user_id)  ->
    //            
    //         ->  digital_object (uuid,     model, name, description, input_queue_directory_path)
    //                             uuid, ?ordinal?, name,  'null',             'null'
    //        
    //         ->  crud_digital_object_action (editor_user_id, "timestamp", successful, digital_object_uuid, type)
    //                                                user_id,    modified,     'true',                uuid,  'c'
    //        
    //         ->  description (editor_user_id, digital_object_uuid, description)
    //                                 user_id,            uuid, description
    @Override
    public void transformAndRecentlyModified(Map<Long, Object[]> oldData, Map<Long, Long> editorUserIdMapping)
            throws NumberFormatException, DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] recModItem = oldData.get(i);

            checkDigitalObject((String) recModItem[1],
                               DigitalObjectModel.getModel(Integer.parseInt(recModItem[5].toString()))
                                       .getValue(),
                               (String) recModItem[2],
                               null,
                               null);

            Long userId = editorUserIdMapping.get(Long.parseLong(recModItem[6].toString()));
            insertCrudDigitalObjectAction(userId,
                                          (Timestamp) recModItem[4],
                                          true,
                                          (String) recModItem[1],
                                          CRUD_ACTION_TYPES.READ);

            insertDescription(userId, (String) recModItem[1], (String) recModItem[3]);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     */
    //    request_for_adding (id, name, identity, modified)   ->
    //    
    //        ->  request_to_admin (admin_editor_user_id,  type,   object, description,  solved)
    //                                            'null', 'r4a', identity,        name, 'false'
    //
    //        ->  crud_request_to_admin_action (editor_user_id, "timestamp", successful, request_to_admin_id, type)
    //                                          'non-existent',    modified,       true,                  id,  'c'
    @Override
    public void transformAndPutRequestForAdding(Map<Long, Object[]> oldData) throws DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] r4a = oldData.get(i);
            Long requestId =
                    insertRequestToAdmin(1,
                                         REQUESTS_TO_ADMIN_TYPES.ADDING_NEW_ACOUNT,
                                         (String) r4a[2],
                                         (String) r4a[1],
                                         false);

            insertCrudRequestToAdminAction(1, (Timestamp) r4a[3], true, requestId, CRUD_ACTION_TYPES.CREATE);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     */
    //    stored_files (id, user_id, uuid, model, description, stored, file_name) ->
    //    
    //        ->  saved_edited_object (digital_object_uuid, file_name, description, state)
    //                                                uuid, file_name, description, 'true'
    //
    //        ->  crud_saved_edited_object_action (editor_user_id, "timestamp", successful, saved_edited_object_id, type)
    //                                                    user_id,      stored,     'true',                     id,  'c'
    //    
    //        ->  digital_object (uuid, model, name, description, input_queue_directory_path)
    //                            uuid, model, ????, ???????????,             'null'
    @Override
    public void transformAndPutStoredFiles(Map<Long, Object[]> oldData, Map<Long, Long> editorUserIdMapping)
            throws DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] storedFile = oldData.get(i);
            checkDigitalObject((String) storedFile[2],
                               DigitalObjectModel.getModel(Integer.parseInt(storedFile[3].toString()))
                                       .getValue(),
                               null,
                               null,
                               null);
            Long savedId =
                    insertSavedEditedObject((String) storedFile[2],
                                            (String) storedFile[6],
                                            (String) storedFile[4],
                                            true);
            insertCrudSavedEditedObjectAction(editorUserIdMapping.get(Long.parseLong(storedFile[1].toString())),
                                              (Timestamp) storedFile[5],
                                              true,
                                              savedId,
                                              CRUD_ACTION_TYPES.CREATE);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     */
    //    tree_structure (id, user_id, created, barcode, description, name, input_path, model)    ->
    //    
    //        ->  tree_structure (barcode, description, name, model,  state, input_queue_directory_path)
    //                            barcode, description, name, model, 'true',                 input_path
    //
    //        ->  crud_tree_structure_action (editor_user_id, timestamp, successful, tree_structure_id, type)
    //                                               user_id,   created,     'true',                id,  'c'
    //
    //        ->  input_queue (directory_path, name)
    //                             input_path, ????
    @Override
    public Map<Long, Long> transformAndPutTreeStructure(Map<Long, Object[]> oldData,
                                                        Map<Long, Long> editorUserIdMapping)
            throws DatabaseException {

        Map<Long, Long> treeStrucIdMapping = new HashMap<Long, Long>(oldData.size());

        for (long i = 1; i < oldData.size(); i++) {
            Object[] treeStruc = oldData.get(i);

            insertInputQueue((String) treeStruc[6], null);

            Long treeStrucId =
                    insertTreeStructure((String) treeStruc[3],
                                        (String) treeStruc[4],
                                        (String) treeStruc[5],
                                        (String) treeStruc[7],
                                        true,
                                        (String) treeStruc[6]);

            insertCrudTreeStructureAction(editorUserIdMapping.get(Long.parseLong(treeStruc[1].toString())),
                                          (Timestamp) treeStruc[2],
                                          true,
                                          treeStrucId,
                                          CRUD_ACTION_TYPES.CREATE);

            treeStrucIdMapping.put(Long.parseLong(treeStruc[0].toString()), treeStrucId);
        }

        return treeStrucIdMapping;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     * @throws NumberFormatException
     */
    //    tree_structure_node (id,           tree_id, prop_id, prop_parent, prop_name, prop_picture_or_uuid, prop_model_id, prop_type, prop_date_or_int_part_name, prop_note_or_int_subtitle, prop_part_number_or_alto, prop_aditional_info_or_ocr, prop_exist)   ->
    //    tree_structure_node (id, tree_structure_id, prop_id, prop_parent, prop_name, prop_picture_or_uuid, prop_model_id, prop_type, prop_date_or_int_part_name, prop_note_or_int_subtitle, prop_part_number_or_alto, prop_aditional_info_or_ocr, prop_exist)
    @Override
    public void transformAndPutTreeStrucNode(Map<Long, Object[]> oldData, Map<Long, Long> treeStrucIdMapping)
            throws NumberFormatException, DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] treeNode = oldData.get(i);
            insertTreeStructureNode(treeStrucIdMapping.get(Long.parseLong(treeNode[1].toString())),
                                    (String) treeNode[2],
                                    (String) treeNode[3],
                                    (String) treeNode[4],
                                    (String) treeNode[5],
                                    (String) treeNode[6],
                                    (String) treeNode[7],
                                    (String) treeNode[8],
                                    (String) treeNode[9],
                                    (String) treeNode[10],
                                    (String) treeNode[11],
                                    (Boolean) treeNode[12]);
        }
    }
}
