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
import java.sql.Statement;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;
import cz.mzk.editor.client.util.Constants.REQUESTS_TO_ADMIN_TYPES;

/**
 * @author Matous Jobanek
 * @version Oct 11, 2012
 */
public class DAOUtilsImpl
        extends AbstractDAO
        implements DAOUtils {

    private static final Logger LOGGER = Logger.getLogger(DAOUtilsImpl.class);

    public static final String ACTION_UPDATE_SUCCESS_STATEMENT = "UPDATE " + Constants.TABLE_ACTION
            + " (editor_user_id, timestamp, successful) VALUES ((?),(?),(?))";

    /** The Constant SELECT_USER_NAME. */
    public static final String SELECT_USER_NAME = "SELECT name, surname FROM " + Constants.TABLE_EDITOR_USER
            + " WHERE id=(?)";

    /**
     * {@inheritDoc}
     * 
     * @throws SQLException
     */
    @Override
    public boolean insertCrudAction(String tableName,
                                    String fkNameCol,
                                    Object foreignKey,
                                    CRUD_ACTION_TYPES type,
                                    boolean closeCon) throws DatabaseException, SQLException {
        return insertAnyCrudAction(getUserId(), tableName, fkNameCol, foreignKey, type, null, closeCon);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws SQLException
     */
    @Override
    public boolean insertCrudActionWithTopObject(String tableName,
                                                 String fkNameCol,
                                                 Object foreignKey,
                                                 CRUD_ACTION_TYPES type,
                                                 String top_digital_object_uuid,
                                                 boolean closeCon) throws DatabaseException, SQLException {
        return insertAnyCrudAction(getUserId(),
                                   tableName,
                                   fkNameCol,
                                   foreignKey,
                                   type,
                                   top_digital_object_uuid,
                                   closeCon);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws SQLException
     */
    @Override
    public boolean insertCrudAction(Long editor_user_id,
                                    String tableName,
                                    String fkNameCol,
                                    Object foreignKey,
                                    CRUD_ACTION_TYPES type,
                                    boolean closeCon) throws DatabaseException, SQLException {
        return insertAnyCrudAction(editor_user_id, tableName, fkNameCol, foreignKey, type, null, closeCon);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws SQLException
     */
    @Override
    public boolean insertCrudActionWithTopObject(Long editor_user_id,
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
            insertSt.setLong(1, getUserId());
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

    /**
     * {@inheritDoc}
     * 
     * @throws SQLException
     */
    @Override
    public boolean checkDigitalObject(String uuid,
                                      String model,
                                      String name,
                                      String description,
                                      String input_queue_dir_path,
                                      boolean closeCon) throws DatabaseException, SQLException {

        PreparedStatement selSt = null;
        boolean successful = false;
        String input_queue_directory_path = directoryPathToRightFormat(input_queue_dir_path);
        try {
            selSt = getConnection().prepareStatement(DIGITAL_OBJECT_SELECT_ITEM_STATEMENT);
            selSt.setString(1, uuid);
            ResultSet rs = selSt.executeQuery();

            if (rs.next()) {
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

                if (!rs.getBoolean("state")) {
                    changed = true;
                }

                if (changed) {

                    successful =
                            updateDigitalObject(uuid,
                                                chaModel,
                                                chaName,
                                                chaDescription,
                                                chaInputPath,
                                                closeCon);
                } else {
                    successful = true;
                }
            } else {
                successful =
                        insertDigitalObject(uuid,
                                            model,
                                            name,
                                            description,
                                            input_queue_directory_path,
                                            closeCon);
            }

        } catch (SQLException e) {
            LOGGER.error("Could not get select statement " + selSt, e);
            if (closeCon) {
                e.printStackTrace();
            } else {
                throw new SQLException(e);
            }
        } finally {
            if (closeCon) closeConnection();
        }

        return successful;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws SQLException
     */
    @Override
    public boolean updateDigitalObject(String uuid,
                                       String model,
                                       String name,
                                       String description,
                                       String input_queue_directory_path,
                                       boolean closeCon) throws DatabaseException, SQLException {
        PreparedStatement updateSt = null;
        boolean successful = false;
        try {
            if (input_queue_directory_path != null) {
                if (!checkInputQueue(input_queue_directory_path, name, true)) {
                    throw new DatabaseException("Directory path: " + input_queue_directory_path
                            + " has not been added.");
                }
            }

            updateSt = getConnection().prepareStatement(DIGITAL_OBJECT_UPDATE_ITEM_STATEMENT);
            updateSt.setString(1, model);
            updateSt.setString(2, name);
            updateSt.setString(3, description);
            updateSt.setString(4, directoryPathToRightFormat(input_queue_directory_path));
            updateSt.setString(5, uuid);
            int updated = updateSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The digital object: " + uuid + " has been updated.");
                successful = true;
            } else {
                LOGGER.error("DB has not been updated! " + updateSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get update item statement " + updateSt, ex);
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

    /**
     * {@inheritDoc}
     * 
     * @throws SQLException
     */
    @Override
    public boolean insertDigitalObject(String uuid,
                                       String model,
                                       String name,
                                       String description,
                                       String input_queue_directory_path,
                                       boolean closeCon) throws DatabaseException, SQLException {
        PreparedStatement insertSt = null;
        boolean successful = false;
        try {
            insertSt = getConnection().prepareStatement(DIGITAL_OBJECT_INSERT_ITEM_STATEMENT);
            insertSt.setString(1, uuid);
            insertSt.setString(2, model);
            insertSt.setString(3, name);
            insertSt.setString(4, description);
            insertSt.setString(5, directoryPathToRightFormat(input_queue_directory_path));
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The digital object: " + uuid + " has been inserted.");
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertCrudDigitalObjectAction(Long editor_user_id,
                                              Timestamp timestamp,
                                              String digital_object_uuid,
                                              CRUD_ACTION_TYPES type) throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt = getConnection().prepareStatement(CRUD_DIGITAL_OBJECT_ACTION_INSERT_ITEM_STATEMENT);
            insertSt.setLong(1, editor_user_id);
            insertSt.setTimestamp(2, timestamp);
            insertSt.setString(3, digital_object_uuid);
            insertSt.setString(4, type.getValue());
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Long insertEditorUser(String name, String surname, boolean state) throws DatabaseException {
        PreparedStatement insertSt = null;
        Long id = null;
        try {
            insertSt =
                    getConnection().prepareStatement(EDITOR_USER_INSERT_ITEM_STATEMENT,
                                                     Statement.RETURN_GENERATED_KEYS);
            insertSt.setString(1, name);
            insertSt.setString(2, surname);
            insertSt.setBoolean(3, state);
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The editor user: " + name + " " + surname
                        + " has been inserted.");
                ResultSet gk = insertSt.getGeneratedKeys();
                if (gk.next()) {
                    id = gk.getLong(1);
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

    /**
     * {@inheritDoc}
     */
    @Override
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

    /**
     * {@inheritDoc}
     */
    @Override
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

    /**
     * {@inheritDoc}
     * 
     * @throws SQLException
     */
    @Override
    public boolean checkInputQueue(String directory_path, String name, boolean closeCon)
            throws DatabaseException, SQLException {
        PreparedStatement selSt = null;
        boolean successful = false;
        String dirPath = directoryPathToRightFormat(directory_path);

        try {
            selSt = getConnection().prepareStatement(INPUT_QUEUE_SELECT_NAME_STATEMENT);
            selSt.setString(1, dirPath);
            ResultSet rs = selSt.executeQuery();

            if (rs.next()) {
                if (name != null && !name.equals(rs.getString("name"))) {
                    successful = updateInputQueue(dirPath, name, closeCon);
                } else {
                    successful = true;
                }

            } else {
                successful = insertInputQueue(dirPath, name != null ? name : "", closeCon);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get select item statement " + selSt, ex);
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

    /**
     * {@inheritDoc}
     * 
     * @throws SQLException
     */
    @Override
    public boolean updateInputQueue(String directory_path, String name, boolean closeCon)
            throws DatabaseException, SQLException {
        PreparedStatement updateSt = null;
        boolean successful = false;
        try {
            updateSt = getConnection().prepareStatement(INPUT_QUEUE_UPDATE_ITEM_STATEMENT);
            updateSt.setString(1, name);
            updateSt.setString(2, directoryPathToRightFormat(directory_path));
            int updated = updateSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The name of input queue: " + directory_path
                        + " has been changed to: " + name + ".");
                successful = true;
            } else {
                LOGGER.error("DB has not been updated! " + updateSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get update item statement " + updateSt, ex);
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

    /**
     * {@inheritDoc}
     * 
     * @throws SQLException
     */
    @Override
    public boolean insertInputQueue(String directory_path, String name, boolean closeCon)
            throws DatabaseException, SQLException {
        PreparedStatement insertSt = null;
        boolean successful = false;
        try {
            insertSt = getConnection().prepareStatement(INPUT_QUEUE_INSERT_ITEM_STATEMENT);
            insertSt.setString(1, directoryPathToRightFormat(directory_path));
            insertSt.setString(2, name);
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The input queue: " + directory_path
                        + " has been inserted.");
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

    /**
     * {@inheritDoc}
     */
    @Override
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertDescription(long editor_user_id, String digital_object_uuid, String description)
            throws DatabaseException {
        PreparedStatement insertSt = null;
        boolean successful = false;
        try {
            insertSt = getConnection().prepareStatement(DESCRIPTION_INSERT_ITEM_STATEMENT);
            insertSt.setLong(1, editor_user_id);
            insertSt.setString(2, digital_object_uuid);
            insertSt.setString(3, description);
            int updated = insertSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: A description has been inserted.");
                successful = true;
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }

        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + insertSt, ex);
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return successful;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
                    id = gk.getLong(1);
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

    /**
     * {@inheritDoc}
     */
    @Override
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
                    id = gk.getLong(1);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertCrudSavedEditedObjectAction(Long editor_user_id,
                                                  Timestamp timestamp,
                                                  Long saved_edited_object_id,
                                                  CRUD_ACTION_TYPES type) throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt =
                    getConnection().prepareStatement(CRUD_SAVED_EDITED_OBJECT_ACTION_INSERT_ITEM_STATEMENT);
            insertSt.setLong(1, editor_user_id);
            insertSt.setTimestamp(2, timestamp);
            insertSt.setLong(3, saved_edited_object_id);
            insertSt.setString(4, type.getValue());
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertCrudRequestToAdminAction(long editor_user_id,
                                               Timestamp timestamp,
                                               long request_to_admin_id,
                                               CRUD_ACTION_TYPES type) throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt = getConnection().prepareStatement(CRUD_REQUEST_TO_ADMIN_ACTION_INSERT_ITEM_STATEMENT);
            insertSt.setLong(1, editor_user_id);
            insertSt.setTimestamp(2, timestamp);
            insertSt.setLong(3, request_to_admin_id);
            insertSt.setString(4, type.getValue());
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Long insertTreeStructure(String barcode,
                                    String description,
                                    String name,
                                    String model,
                                    boolean state,
                                    String input_queue_dir_path) throws DatabaseException {
        PreparedStatement insertSt = null;
        Long id = null;
        String input_queue_directory_path = directoryPathToRightFormat(input_queue_dir_path);
        try {
            if (input_queue_directory_path != null) {
                if (!checkInputQueue(input_queue_directory_path, null, true)) {
                    throw new DatabaseException("Directory path: " + input_queue_directory_path
                            + " has not been added.");
                }
            }

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
                LOGGER.debug("DB has been updated: The tree structure: "
                        + directoryPathToRightFormat(input_queue_directory_path) + " has been inserted.");
                ResultSet gk = insertSt.getGeneratedKeys();
                if (gk.next()) {
                    id = gk.getLong(1);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertCrudTreeStructureAction(long editor_user_id,
                                              Timestamp timestamp,
                                              long tree_structure_id,
                                              CRUD_ACTION_TYPES type) throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt = getConnection().prepareStatement(CRUD_TREE_STRUCTURE_ACTION_INSERT_ITEM_STATEMENT);
            insertSt.setLong(1, editor_user_id);
            insertSt.setTimestamp(2, timestamp);
            insertSt.setLong(3, tree_structure_id);
            insertSt.setString(4, type.getValue());
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

    /**
     * {@inheritDoc}
     */
    @Override
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

    public static String directoryPathToRightFormat(String path) {
        if (path != null) {
            String dirPath = (path.startsWith("/")) ? path : "/".concat(path);
            return dirPath.endsWith("/") ? dirPath.substring(0, dirPath.length() - 1) : dirPath;
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getUserId() throws DatabaseException {
        return super.getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName(Long key) throws DatabaseException {
        PreparedStatement selectSt = null;
        String name = "unknown";
        try {

            selectSt = getConnection().prepareStatement(SELECT_USER_NAME);
            selectSt.setLong(1, Long.valueOf(key));

        } catch (SQLException e) {
            LOGGER.error("Could not get select statement", e);
        }
        try {
            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                name = rs.getString("name") + " " + rs.getString("surname");
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return name;
    }
}
