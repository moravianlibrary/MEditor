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
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;
import cz.mzk.editor.client.util.Constants.DEFAULT_SYSTEM_USERS;
import cz.mzk.editor.client.util.Constants.REQUESTS_TO_ADMIN_TYPES;
import cz.mzk.editor.server.util.ScriptRunner;
import cz.mzk.editor.shared.domain.DigitalObjectModel;

/**
 * @author Jiri Kremser
 * @version 17. 1. 2011
 */
public class DBSchemaDAOImpl
        extends AbstractDAO
        implements DBSchemaDAO {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL = "SELECT * FROM ";

    /** The Constant SELECT_VERSION. */
    public static final String SELECT_VERSION = SELECT_ALL + Constants.TABLE_VERSION;

    /** The Constant UPDATE_VERSION. */
    public static final String UPDATE_VERSION = "UPDATE " + Constants.TABLE_VERSION + " SET version = (?)";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(DBSchemaDAOImpl.class);

    /** The Constant VERSION_INSERT_ITEM_STATEMENT. */
    public static final String VERSION_INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_VERSION
            + " (version) VALUES ((?))";

    @Inject
    private DAOUtils daoUtils;

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

    /**
     * Update version in db.
     * 
     * @param version
     *        the version
     * @throws DatabaseException
     *         the database exception
     */
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

    /**
     * {@inheritDoc}
     */
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
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (DatabaseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return rows;
    }

    //    description (id, uuid, description) -> digital_object (uuid, model, name, description, input_queue_directory_path);
    //                                                           uuid, ?????, ????, description,             'null'
    /**
     * {@inheritDoc}
     */
    @Override
    public void transformAndPutDescription(Map<Long, Object[]> oldData) throws DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] desc = oldData.get(i);
            String uuid = (String) desc[1];
            try {
                daoUtils.checkDigitalObject((uuid).startsWith("uuid:") ? uuid : "uuid:" + uuid,
                                            "?",
                                            null,
                                            (String) desc[2],
                                            null,
                                            true);
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //    editor_user (id, name, surname, sex) -> editor_user (id, name, surname, state)
    //                                                             name, surname, 'true'
    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, Long> transformAndPutEditorUser(Map<Long, Object[]> oldData) throws DatabaseException {

        Map<Long, Long> editorUserIdMapping = new HashMap<Long, Long>(oldData.size());

        for (DEFAULT_SYSTEM_USERS defSysUser : DEFAULT_SYSTEM_USERS.values()) {
            daoUtils.insertEditorUser("", defSysUser.getUserName(), true);
        }

        for (long i = 1; i < oldData.size(); i++) {
            Object[] user = oldData.get(i);
            Long userId = daoUtils.insertEditorUser((String) user[1], (String) user[2], true);
            editorUserIdMapping.put(Long.parseLong(user[0].toString()), userId);
        }
        return editorUserIdMapping;
    }

    //  image (id, identifier, shown, old_fs_path, imagefile) -> image (identifier, shown, old_fs_path, imagefile)
    //                                                                  identifier, shown, old_fs_path, imagefile
    /**
     * {@inheritDoc}
     */
    @Override
    public void transformAndPutImage(Map<Long, Object[]> oldData) throws DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] image = oldData.get(i);
            daoUtils.insertImage((String) image[1],
                                 (Timestamp) image[2],
                                 (String) image[3],
                                 (String) image[4]);
        }
    }

    //    input_queue_item (id, path, barcode, ingested) -> input_queue_item (path, barcode, ingested)
    //                                                                        path, barcode, ingested
    /**
     * {@inheritDoc}
     */
    @Override
    public void transformAndPutInputQueueItem(Map<Long, Object[]> oldData) throws DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] queueItem = oldData.get(i);
            daoUtils.insertInputQueueItem((String) queueItem[1],
                                          (String) queueItem[2],
                                          (Boolean) queueItem[3]);
        }
    }

    //    input_queue_item_name (id, path, name) -> input_queue (directory_path, name)
    //                                                                     path, name
    /**
     * {@inheritDoc}
     */
    @Override
    public void transformAndPutInputQueue(Map<Long, Object[]> oldData) throws DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] inputQueue = oldData.get(i);
            try {
                daoUtils.insertInputQueue((String) inputQueue[1], (String) inputQueue[2], true);
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //    open_id_identity (id, user_id, identity) -> open_id_identity (editor_user_id, identity)
    //                                                                         user_id, identity
    /**
     * {@inheritDoc}
     */
    @Override
    public void transformAndPutOpenIdIdentity(Map<Long, Object[]> oldData, Map<Long, Long> editorUserIdMapping)
            throws NumberFormatException, DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] openId = oldData.get(i);
            daoUtils.insertOpenIdIdentity(editorUserIdMapping.get(Long.parseLong(openId[1].toString())),
                                          (String) openId[2]);
        }
    }

    //     recently_modified_item (id, uuid, name, description, modified, model, user_id)  ->
    //            
    //         ->  digital_object (uuid,     model, name, description, input_queue_directory_path)
    //                             uuid, ?ordinal?, name,  'null',             'null'
    //        
    //         ->  crud_digital_object_action (editor_user_id, timestamp, digital_object_uuid, type)
    //                                                user_id,  modified,                uuid,  'c'
    //        
    //         ->  description (editor_user_id, digital_object_uuid, description)
    //                                 user_id,            uuid, description
    /**
     * {@inheritDoc}
     */
    @Override
    public void transformAndRecentlyModified(Map<Long, Object[]> oldData, Map<Long, Long> editorUserIdMapping)
            throws NumberFormatException, DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] recModItem = oldData.get(i);

            try {
                daoUtils.checkDigitalObject((String) recModItem[1],
                                            DigitalObjectModel.getModel(Integer.parseInt(recModItem[5]
                                                    .toString())).getValue(),
                                            (String) recModItem[2],
                                            null,
                                            null,
                                            true);

                Long userId = editorUserIdMapping.get(Long.parseLong(recModItem[6].toString()));
                daoUtils.insertCrudDigitalObjectAction(userId,
                                                       (Timestamp) recModItem[4],
                                                       (String) recModItem[1],
                                                       CRUD_ACTION_TYPES.READ);

                String desc = (String) recModItem[3];
                if (desc != null && !"".equals(desc))
                    daoUtils.insertDescription(userId, (String) recModItem[1], desc);
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //    request_for_adding (id, name, identity, modified)   ->
    //    
    //        ->  request_to_admin (admin_editor_user_id,  type,   object, description,  solved)
    //                                            'null', 'r4a', identity,        name, 'false'
    //
    //        ->  crud_request_to_admin_action (editor_user_id, "timestamp", request_to_admin_id, type)
    //                                          'non-existent',    modified,                  id,  'c'
    /**
     * {@inheritDoc}
     */
    @Override
    public void transformAndPutRequestForAdding(Map<Long, Object[]> oldData) throws DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] r4a = oldData.get(i);
            Long requestId =
                    daoUtils.insertRequestToAdmin(DEFAULT_SYSTEM_USERS.NON_EXISTENT.getUserId(),
                                                  REQUESTS_TO_ADMIN_TYPES.ADDING_NEW_ACOUNT,
                                                  (String) r4a[2],
                                                  (String) r4a[1],
                                                  false);

            daoUtils.insertCrudRequestToAdminAction(DEFAULT_SYSTEM_USERS.NON_EXISTENT.getUserId(),
                                                    (Timestamp) r4a[3],
                                                    requestId,
                                                    CRUD_ACTION_TYPES.CREATE);
        }
    }

    //    stored_files (id, user_id, uuid, model, description, stored, file_name) ->
    //    
    //        ->  saved_edited_object (digital_object_uuid, file_name, description, state)
    //                                                uuid, file_name, description, 'true'
    //
    //        ->  crud_saved_edited_object_action (editor_user_id, timestamp, saved_edited_object_id, type)
    //                                                    user_id,    stored,                     id,  'c'
    //    
    //        ->  digital_object (uuid, model, name, description, input_queue_directory_path)
    //                            uuid, model, ????, ???????????,             'null'
    /**
     * {@inheritDoc}
     */
    @Override
    public void transformAndPutStoredFiles(Map<Long, Object[]> oldData, Map<Long, Long> editorUserIdMapping)
            throws DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] storedFile = oldData.get(i);
            try {
                daoUtils.checkDigitalObject((String) storedFile[2],
                                            DigitalObjectModel.getModel(Integer.parseInt(storedFile[3]
                                                    .toString())).getValue(),
                                            null,
                                            null,
                                            null,
                                            true);

                Long savedId =
                        daoUtils.insertSavedEditedObject((String) storedFile[2],
                                                         (String) storedFile[6],
                                                         (String) storedFile[4],
                                                         true);
                daoUtils.insertCrudSavedEditedObjectAction(editorUserIdMapping.get(Long
                                                                   .parseLong(storedFile[1].toString())),
                                                           (Timestamp) storedFile[5],
                                                           savedId,
                                                           CRUD_ACTION_TYPES.CREATE);
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //    tree_structure (id, user_id, created, barcode, description, name, input_path, model)    ->
    //    
    //        ->  tree_structure (barcode, description, name, model,  state, input_queue_directory_path)
    //                            barcode, description, name, model, 'true',                 input_path
    //
    //        ->  crud_tree_structure_action (editor_user_id, timestamp, tree_structure_id, type)
    //                                               user_id,   created,                id,  'c'
    //
    //        ->  input_queue (directory_path, name)
    //                             input_path, ????
    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, Long> transformAndPutTreeStructure(Map<Long, Object[]> oldData,
                                                        Map<Long, Long> editorUserIdMapping)
            throws DatabaseException {

        Map<Long, Long> treeStrucIdMapping = new HashMap<Long, Long>(oldData.size());

        for (long i = 1; i < oldData.size(); i++) {
            Object[] treeStruc = oldData.get(i);

            if (treeStruc[3] != null && !"".equals(treeStruc[3])) {

                try {
                    daoUtils.checkInputQueue((String) treeStruc[6], null, true);

                    Long treeStrucId =
                            daoUtils.insertTreeStructure((String) treeStruc[3],
                                                         (String) treeStruc[4],
                                                         (String) treeStruc[5],
                                                         (String) treeStruc[7],
                                                         true,
                                                         (String) treeStruc[6]);

                    daoUtils.insertCrudTreeStructureAction(editorUserIdMapping.get(Long
                                                                   .parseLong(treeStruc[1].toString())),
                                                           (Timestamp) treeStruc[2],
                                                           treeStrucId,
                                                           CRUD_ACTION_TYPES.CREATE);

                    treeStrucIdMapping.put(Long.parseLong(treeStruc[0].toString()), treeStrucId);
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        return treeStrucIdMapping;
    }

    //    tree_structure_node (id,           tree_id, prop_id, prop_parent, prop_name, prop_picture_or_uuid, prop_model_id, prop_type, prop_date_or_int_part_name, prop_note_or_int_subtitle, prop_part_number_or_alto, prop_aditional_info_or_ocr, prop_exist)   ->
    //    tree_structure_node (id, tree_structure_id, prop_id, prop_parent, prop_name, prop_picture_or_uuid, prop_model_id, prop_type, prop_date_or_int_part_name, prop_note_or_int_subtitle, prop_part_number_or_alto, prop_aditional_info_or_ocr, prop_exist)
    /**
     * {@inheritDoc}
     */
    @Override
    public void transformAndPutTreeStrucNode(Map<Long, Object[]> oldData, Map<Long, Long> treeStrucIdMapping)
            throws NumberFormatException, DatabaseException {

        for (long i = 1; i < oldData.size(); i++) {
            Object[] treeNode = oldData.get(i);
            Long treeStrucId = treeStrucIdMapping.get(Long.parseLong(treeNode[1].toString()));
            if (treeStrucId != null) {
                daoUtils.insertTreeStructureNode(treeStrucId,
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
}
