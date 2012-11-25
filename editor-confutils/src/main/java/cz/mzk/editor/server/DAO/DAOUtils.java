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

import java.sql.SQLException;
import java.sql.Timestamp;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;
import cz.mzk.editor.client.util.Constants.REQUESTS_TO_ADMIN_TYPES;

/**
 * @author Matous Jobanek
 * @version Oct 11, 2012
 */
public interface DAOUtils {

    /** The Constant INSERT_ITEM_STATEMENT_CONVERSION. */
    public static final String CONVERSION_INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_CONVERSION
            + " (editor_user_id, timestamp, input_queue_directory_path) VALUES ((?),(?),(?))";

    /** The Constant CRUD_DIGITAL_OBJECT_ACTION_INSERT_ITEM_STATEMENT. */
    public static final String CRUD_DIGITAL_OBJECT_ACTION_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION
            + " (editor_user_id, timestamp, digital_object_uuid, type) VALUES ((?),(?),(?),(?))";

    /** The Constant CRUD_LOCK_ACTION_INSERT_ITEM_STATEMENT. */
    public static final String CRUD_LOCK_ACTION_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_CRUD_LOCK_ACTION
            + " (editor_user_id, timestamp, lock_id, type) VALUES ((?),(?),(?),(?))";

    /** The Constant CRUD_REQUEST_TO_ADMIN_ACTION_INSERT_ITEM_STATEMENT. */
    public static final String CRUD_REQUEST_TO_ADMIN_ACTION_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_CRUD_REQUEST_TO_ADMIN_ACTION
            + " (editor_user_id, timestamp, request_to_admin_id, type) VALUES ((?),(?),(?),(?))";

    /** The Constant CRUD_SAVED_EDITED_OBJECT_ACTION_INSERT_ITEM_STATEMENT. */
    public static final String CRUD_SAVED_EDITED_OBJECT_ACTION_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION
            + " (editor_user_id, timestamp, saved_edited_object_id, type) VALUES ((?),(?),(?),(?))";

    /** The Constant CRUD_TREE_STRUCTURE_ACTION_INSERT_ITEM_STATEMENT. */
    public static final String CRUD_TREE_STRUCTURE_ACTION_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_CRUD_TREE_STRUCTURE_ACTION
            + " (editor_user_id, timestamp, tree_structure_id, type) VALUES ((?),(?),(?),(?))";

    /** The Constant DESCRIPTION_INSERT_ITEM_STATEMENT. */
    public static final String DESCRIPTION_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_DESCRIPTION
            + " (editor_user_id, digital_object_uuid, description) VALUES ((?),(?),(?))";

    /** The Constant DIGITAL_OBJECT_INSERT_ITEM_STATEMENT. */
    public static final String DIGITAL_OBJECT_INSERT_ITEM_STATEMENT =
            "INSERT INTO "
                    + Constants.TABLE_DIGITAL_OBJECT
                    + " (uuid, model, name, description, input_queue_directory_path, state) VALUES ((?),(?),(?),(?),(?), 'true')";

    /** The Constant DIGITAL_OBJECT_SELECT_ITEM_STATEMENT. */
    public static final String DIGITAL_OBJECT_SELECT_ITEM_STATEMENT = "SELECT * FROM "
            + Constants.TABLE_DIGITAL_OBJECT + " WHERE uuid=(?)";

    /** The Constant DIGITAL_OBJECT_UPDATE_ITEM_STATEMENT. */
    public static final String DIGITAL_OBJECT_UPDATE_ITEM_STATEMENT =
            "UPDATE "
                    + Constants.TABLE_DIGITAL_OBJECT
                    + " SET model=(?), name=(?), description=(?), input_queue_directory_path=(?), state = 'true' WHERE uuid=(?)";

    /** The Constant EDITOR_RIGHT_INSERT_ITEM_STATEMENT. */
    public static final String EDITOR_RIGHT_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_EDITOR_RIGHT + " (name, description) VALUES ((?),(?))";

    /** The Constant EDITOR_USER_INSERT_ITEM_STATEMENT. */
    public static final String EDITOR_USER_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_EDITOR_USER + " (name, surname, state) VALUES ((?),(?),(?))";

    /** The Constant IMAGE_INSERT_ITEM_STATEMENT. */
    public static final String IMAGE_INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_IMAGE
            + " (identifier, shown, old_fs_path, imagefile) VALUES ((?),(?),(?),(?))";

    /** The Constant INPUT_QUEUE_ITEM_INSERT_ITEM_STATEMENT. */
    public static final String INPUT_QUEUE_ITEM_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_INPUT_QUEUE_ITEM + " (path, barcode, ingested) VALUES ((?),(?),(?))";

    /** The Constant INPUT_QUEUE_INSERT_ITEM_STATEMENT. */
    public static final String INPUT_QUEUE_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_INPUT_QUEUE + " (directory_path, name) VALUES ((?),(?))";

    /** The Constant INPUT_QUEUE_SELECT_NAME_STATEMENT. */
    public static final String INPUT_QUEUE_SELECT_NAME_STATEMENT = "SELECT name FROM "
            + Constants.TABLE_INPUT_QUEUE + " WHERE directory_path=(?)";

    /** The Constant INPUT_QUEUE_UPDATE_ITEM_STATEMENT. */
    public static final String INPUT_QUEUE_UPDATE_ITEM_STATEMENT = "UPDATE " + Constants.TABLE_INPUT_QUEUE
            + " SET name=(?) WHERE directory_path=(?)";

    /** The Constant LDAP_IDENTITY_INSERT_ITEM_STATEMENT. */
    public static final String LDAP_IDENTITY_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_LDAP_IDENTITY + " (editor_user_id, identity) VALUES ((?),(?))";

    /** The Constant LOCK_INSERT_ITEM_STATEMENT. */
    public static final String LOCK_INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_LOCK
            + " (digital_object_uuid, description, state) VALUES ((?),(?),(?))";

    /** The Constant LOG_IN_OUT_INSERT_ITEM_STATEMENT. */
    public static final String LOG_IN_OUT_INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_LOG_IN_OUT
            + " (editor_user_id, timestamp, type) VALUES ((?),(?),(?))";

    /** The Constant LONG_RUNNING_PROCESS_INSERT_ITEM_STATEMENT. */
    public static final String LONG_RUNNING_PROCESS_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_LONG_RUNNING_PROCESS
            + " (editor_user_id, timestamp, name, finished) VALUES ((?),(?),(?),(?))";

    /** The Constant OPEN_ID_IDENTITY_INSERT_ITEM_STATEMENT. */
    public static final String OPEN_ID_IDENTITY_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_OPEN_ID_IDENTITY + " (editor_user_id, identity) VALUES ((?),(?))";

    /** The Constant REQUEST_TO_ADMIN_INSERT_ITEM_STATEMENT. */
    public static final String REQUEST_TO_ADMIN_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_REQUEST_TO_ADMIN
            + " (admin_editor_user_id, type, object, description, solved) VALUES ((?),(?),(?),(?),(?))";

    /** The Constant RIGHT_IN_ROLE_INSERT_ITEM_STATEMENT. */
    public static final String RIGHT_IN_ROLE_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_RIGHT_IN_ROLE + " (editor_right_name, role_name) VALUES ((?),(?))";

    /** The Constant ROLE_INSERT_ITEM_STATEMENT. */
    public static final String ROLE_INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_ROLE
            + " (name, description) VALUES ((?),(?))";

    /** The Constant SAVED_EDITED_OBJECT_INSERT_ITEM_STATEMENT. */
    public static final String SAVED_EDITED_OBJECT_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_SAVED_EDITED_OBJECT
            + " (digital_object_uuid, file_name, description, state) VALUES ((?),(?),(?),(?))";

    /** The Constant SHIBBOLETH_IDENTITY_INSERT_ITEM_STATEMENT. */
    public static final String SHIBBOLETH_IDENTITY_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_SHIBBOLETH_IDENTITY + " (editor_user_id, identity) VALUES ((?),(?))";

    /** The Constant TREE_STRUCTURE_INSERT_ITEM_STATEMENT. */
    public static final String TREE_STRUCTURE_INSERT_ITEM_STATEMENT =
            "INSERT INTO "
                    + Constants.TABLE_TREE_STRUCTURE
                    + " (barcode, description, name, model, state, input_queue_directory_path) VALUES ((?),(?),(?),(?),(?),(?))";

    /** The Constant TREE_STRUCTURE_NODE_INSERT_ITEM_STATEMENT. */
    public static final String TREE_STRUCTURE_NODE_INSERT_ITEM_STATEMENT =
            "INSERT INTO "
                    + Constants.TABLE_TREE_STRUCTURE_NODE
                    + " (tree_structure_id, prop_id, prop_parent, prop_name, prop_picture_or_uuid, prop_model_id, prop_type, prop_date_or_int_part_name, prop_note_or_int_subtitle, prop_part_number_or_alto, prop_aditional_info_or_ocr, prop_exist) VALUES ((?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?))";

    /** The Constant USER_EDIT_INSERT_ITEM_STATEMENT. */
    public static final String USER_EDIT_INSERT_ITEM_STATEMENT =
            "INSERT INTO "
                    + Constants.TABLE_USER_EDIT
                    + " (editor_user_id, timestamp, edited_editor_user_id, description, type) VALUES ((?),(?),(?),(?),(?))";

    /** The Constant USERS_RIGHT_INSERT_ITEM_STATEMENT. */
    public static final String USERS_RIGHT_INSERT_ITEM_STATEMENT = "INSERT INTO "
            + Constants.TABLE_USERS_RIGHT + " (editor_user_id, editor_right_name) VALUES ((?),(?))";

    /** The Constant USERS_ROLE_INSERT_ITEM_STATEMENT. */
    public static final String USERS_ROLE_INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_USERS_ROLE
            + " (editor_user_id, role_name) VALUES ((?),(?))";

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
    public boolean insertCrudAction(String tableName,
                                    String fkNameCol,
                                    Object foreignKey,
                                    CRUD_ACTION_TYPES type,
                                    boolean closeCon) throws DatabaseException, SQLException;

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
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     * @throws SQLException
     *         the sQL exception
     */
    public boolean insertCrudActionWithTopObject(String tableName,
                                                 String fkNameCol,
                                                 Object foreignKey,
                                                 CRUD_ACTION_TYPES type,
                                                 String top_digital_object_uuid,
                                                 boolean closeCon) throws DatabaseException, SQLException;

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
     */
    boolean insertCrudAction(Long editor_user_id,
                             String tableName,
                             String fkNameCol,
                             Object foreignKey,
                             CRUD_ACTION_TYPES type,
                             boolean closeCon) throws DatabaseException, SQLException;

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
     */
    boolean insertCrudActionWithTopObject(Long editor_user_id,
                                          String tableName,
                                          String fkNameCol,
                                          Object foreignKey,
                                          CRUD_ACTION_TYPES type,
                                          String top_digital_object_uuid,
                                          boolean closeCon) throws DatabaseException, SQLException;

    /**
     * Check digital object.
     * 
     * @param uuid
     *        the uuid
     * @param model
     *        the model
     * @param name
     *        the name
     * @param description
     *        the description
     * @param input_queue_directory_path
     *        the input_queue_directory_path
     * @param closeCon
     *        the close con
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     * @throws SQLException
     */
    boolean checkDigitalObject(String uuid,
                               String model,
                               String name,
                               String description,
                               String input_queue_directory_path,
                               boolean closeCon) throws DatabaseException, SQLException;

    /**
     * Update digital object.
     * 
     * @param uuid
     *        the uuid
     * @param model
     *        the model
     * @param name
     *        the name
     * @param description
     *        the description
     * @param input_queue_directory_path
     *        the input_queue_directory_path
     * @param closeCon
     *        the close con
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     * @throws SQLException
     */
    boolean updateDigitalObject(String uuid,
                                String model,
                                String name,
                                String description,
                                String input_queue_directory_path,
                                boolean closeCon) throws DatabaseException, SQLException;

    /**
     * Insert digital object.
     * 
     * @param uuid
     *        the uuid
     * @param model
     *        the model
     * @param name
     *        the name
     * @param description
     *        the description
     * @param input_queue_directory_path
     *        the input_queue_directory_path
     * @param closeCon
     *        the close con
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     * @throws SQLException
     */
    boolean insertDigitalObject(String uuid,
                                String model,
                                String name,
                                String description,
                                String input_queue_directory_path,
                                boolean closeCon) throws DatabaseException, SQLException;

    /**
     * Insert crud digital object action.
     * 
     * @param editor_user_id
     *        the editor_user_id
     * @param timestamp
     *        the timestamp
     * @param digital_object_uuid
     *        the digital_object_uuid
     * @param type
     *        the type
     * @throws DatabaseException
     *         the database exception
     */
    void insertCrudDigitalObjectAction(Long editor_user_id,
                                       Timestamp timestamp,
                                       String digital_object_uuid,
                                       CRUD_ACTION_TYPES type) throws DatabaseException;

    /**
     * Insert editor user.
     * 
     * @param name
     *        the name
     * @param surname
     *        the surname
     * @param state
     *        the state
     * @return the long
     * @throws DatabaseException
     *         the database exception
     */
    Long insertEditorUser(String name, String surname, boolean state) throws DatabaseException;

    /**
     * Insert image.
     * 
     * @param identifier
     *        the identifier
     * @param shown
     *        the shown
     * @param old_fs_path
     *        the old_fs_path
     * @param imagefile
     *        the imagefile
     * @throws DatabaseException
     *         the database exception
     */
    void insertImage(String identifier, java.sql.Timestamp shown, String old_fs_path, String imagefile)
            throws DatabaseException;

    /**
     * Insert input queue item.
     * 
     * @param path
     *        the path
     * @param barcode
     *        the barcode
     * @param ingested
     *        the ingested
     * @throws DatabaseException
     *         the database exception
     */
    void insertInputQueueItem(String path, String barcode, Boolean ingested) throws DatabaseException;

    /**
     * Check input queue.
     * 
     * @param directory_path
     *        the directory_path
     * @param name
     *        the name
     * @param closeCon
     *        the close con
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     * @throws SQLException
     */
    boolean checkInputQueue(String directory_path, String name, boolean closeCon) throws DatabaseException,
            SQLException;

    /**
     * Update input queue.
     * 
     * @param directory_path
     *        the directory_path
     * @param name
     *        the name
     * @param closeCon
     *        the close con
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     * @throws SQLException
     */
    boolean updateInputQueue(String directory_path, String name, boolean closeCon) throws DatabaseException,
            SQLException;

    /**
     * Insert input queue.
     * 
     * @param directory_path
     *        the directory_path
     * @param name
     *        the name
     * @param closeCon
     *        the close con
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     * @throws SQLException
     */
    boolean insertInputQueue(String directory_path, String name, boolean closeCon) throws DatabaseException,
            SQLException;

    /**
     * Insert open id identity.
     * 
     * @param editor_user_id
     *        the editor_user_id
     * @param identity
     *        the identity
     * @throws DatabaseException
     *         the database exception
     */
    void insertOpenIdIdentity(long editor_user_id, String identity) throws DatabaseException;

    /**
     * Insert description.
     * 
     * @param editor_user_id
     *        the editor_user_id
     * @param digital_object_uuid
     *        the digital_object_uuid
     * @param description
     *        the description
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     */
    boolean insertDescription(long editor_user_id, String digital_object_uuid, String description)
            throws DatabaseException;

    /**
     * Insert request to admin.
     * 
     * @param admin_editor_user_id
     *        the admin_editor_user_id
     * @param type
     *        the type
     * @param object
     *        the object
     * @param description
     *        the description
     * @param solved
     *        the solved
     * @return the long
     * @throws DatabaseException
     *         the database exception
     */
    Long insertRequestToAdmin(long admin_editor_user_id,
                              REQUESTS_TO_ADMIN_TYPES type,
                              String object,
                              String description,
                              boolean solved) throws DatabaseException;

    /**
     * Insert saved edited object.
     * 
     * @param digital_object_uuid
     *        the digital_object_uuid
     * @param file_name
     *        the file_name
     * @param description
     *        the description
     * @param state
     *        the state
     * @return the long
     * @throws DatabaseException
     *         the database exception
     */
    Long insertSavedEditedObject(String digital_object_uuid,
                                 String file_name,
                                 String description,
                                 boolean state) throws DatabaseException;

    /**
     * Insert crud saved edited object action.
     * 
     * @param editor_user_id
     *        the editor_user_id
     * @param timestamp
     *        the timestamp
     * @param saved_edited_object_id
     *        the saved_edited_object_id
     * @param type
     *        the type
     * @throws DatabaseException
     *         the database exception
     */
    void insertCrudSavedEditedObjectAction(Long editor_user_id,
                                           Timestamp timestamp,
                                           Long saved_edited_object_id,
                                           CRUD_ACTION_TYPES type) throws DatabaseException;

    /**
     * Insert crud request to admin action.
     * 
     * @param editor_user_id
     *        the editor_user_id
     * @param timestamp
     *        the timestamp
     * @param request_to_admin_id
     *        the request_to_admin_id
     * @param type
     *        the type
     * @throws DatabaseException
     *         the database exception
     */
    void insertCrudRequestToAdminAction(long editor_user_id,
                                        Timestamp timestamp,
                                        long request_to_admin_id,
                                        CRUD_ACTION_TYPES type) throws DatabaseException;

    /**
     * Insert tree structure.
     * 
     * @param barcode
     *        the barcode
     * @param description
     *        the description
     * @param name
     *        the name
     * @param model
     *        the model
     * @param state
     *        the state
     * @param input_queue_directory_path
     *        the input_queue_directory_path
     * @return the long
     * @throws DatabaseException
     *         the database exception
     */
    Long insertTreeStructure(String barcode,
                             String description,
                             String name,
                             String model,
                             boolean state,
                             String input_queue_directory_path) throws DatabaseException;

    /**
     * Insert crud tree structure action.
     * 
     * @param editor_user_id
     *        the editor_user_id
     * @param timestamp
     *        the timestamp
     * @param tree_structure_id
     *        the tree_structure_id
     * @param type
     *        the type
     * @throws DatabaseException
     *         the database exception
     */
    void insertCrudTreeStructureAction(long editor_user_id,
                                       Timestamp timestamp,
                                       long tree_structure_id,
                                       CRUD_ACTION_TYPES type) throws DatabaseException;

    /**
     * Insert tree structure node.
     * 
     * @param tree_structure_id
     *        the tree_structure_id
     * @param prop_id
     *        the prop_id
     * @param prop_parent
     *        the prop_parent
     * @param prop_name
     *        the prop_name
     * @param prop_picture_or_uuid
     *        the prop_picture_or_uuid
     * @param prop_model_id
     *        the prop_model_id
     * @param prop_type
     *        the prop_type
     * @param prop_date_or_int_part_name
     *        the prop_date_or_int_part_name
     * @param prop_note_or_int_subtitle
     *        the prop_note_or_int_subtitle
     * @param prop_part_number_or_alto
     *        the prop_part_number_or_alto
     * @param prop_aditional_info_or_ocr
     *        the prop_aditional_info_or_ocr
     * @param prop_exist
     *        the prop_exist
     * @throws DatabaseException
     *         the database exception
     */
    void insertTreeStructureNode(long tree_structure_id,
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
                                 boolean prop_exist) throws DatabaseException;

    /**
     * Gets the user id.
     * 
     * @return the user id
     * @throws DatabaseException
     *         the database exception
     */
    Long getUserId() throws DatabaseException;

    /**
     * Gets the name.
     * 
     * @param key
     *        the key
     * @return the name
     * @throws DatabaseException
     *         the database exception
     */
    String getName(Long key) throws DatabaseException;
}
