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
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;
import cz.mzk.editor.shared.rpc.TreeStructureBundle.TreeStructureInfo;
import cz.mzk.editor.shared.rpc.TreeStructureBundle.TreeStructureNode;

// TODO: Auto-generated Javadoc
/**
 * The Class TreeStructureDAOImpl.
 * 
 * @author Jiri Kremser
 * @version 25. 1. 2011
 */
public class TreeStructureDAOImpl
        extends AbstractDAO
        implements TreeStructureDAO {

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

    //    tree_structure_node (id,           tree_id, prop_id, prop_parent, prop_name, prop_picture_or_uuid, prop_model_id, prop_type, prop_date_or_int_part_name, prop_note_or_int_subtitle, prop_part_number_or_alto, prop_aditional_info_or_ocr, prop_exist)   ->
    //    tree_structure_node (id, tree_structure_id, prop_id, prop_parent, prop_name, prop_picture_or_uuid, prop_model_id, prop_type, prop_date_or_int_part_name, prop_note_or_int_subtitle, prop_part_number_or_alto, prop_aditional_info_or_ocr, prop_exist)

    /** The Constant SELECT_INFOS. */
    public static final String SELECT_INFOS =
            "SELECT eu.surname || ', ' || eu.name as full_name, ts.* FROM ((SELECT timestamp, editor_user_id, tree_structure_id FROM "
                    + Constants.TABLE_CRUD_TREE_STRUCTURE_ACTION
                    + " WHERE type='c') a INNER JOIN (SELECT * FROM "
                    + Constants.TABLE_TREE_STRUCTURE
                    + " WHERE state='true') t ON a.tree_structure_id = t.id) ts LEFT JOIN "
                    + Constants.TABLE_EDITOR_USER + " eu ON eu.id = ts.editor_user_id";

    /** The Constant SELECT_INFOS_WITH_CODE. */
    public static final String SELECT_INFOS_WITH_CODE = SELECT_INFOS + " WHERE ts.barcode = (?)";

    /** The Constant SELECT_INFOS_BY_USER. */
    public static final String SELECT_INFOS_BY_USER = SELECT_INFOS + " WHERE eu.id = (?)";

    /** The Constant SELECT_INFOS_BY_USER_AND_CODE. */
    public static final String SELECT_INFOS_BY_USER_AND_CODE = SELECT_INFOS_BY_USER + " AND ts.barcode = (?)";

    /** The Constant SELECT_NODES. */
    public static final String SELECT_NODES = "SELECT * FROM " + Constants.TABLE_TREE_STRUCTURE_NODE
            + " WHERE tree_structure_id = (?) ORDER BY id";

    /** The Constant DELETE_NODES. */
    public static final String DELETE_NODES = "DELETE FROM " + Constants.TABLE_TREE_STRUCTURE_NODE
            + " WHERE tree_structure_id = (?)";

    /** The Constant DISABLE_INFO. */
    public static final String DISABLE_INFO = "UPDATE " + Constants.TABLE_TREE_STRUCTURE
            + " SET state = 'false' WHERE id = (?)";

    /** The Constant INSERT_INFO. */
    public static final String INSERT_INFO =
            "INSERT INTO "
                    + Constants.TABLE_TREE_STRUCTURE
                    + " (barcode, description, name, model, state, input_queue_directory_path) VALUES ((?), (?), (?), (?), 'true', (?))";

    /** The Constant INFO_VALUE. */
    public static final String INFO_VALUE = "SELECT currval('" + Constants.SEQUENCE_TREE_STRUCTURE + "')";

    /** The Constant INSERT_NODE. */
    public static final String INSERT_NODE =
            "INSERT INTO "
                    + Constants.TABLE_TREE_STRUCTURE_NODE
                    + " (tree_structure_id, prop_id, prop_parent, prop_name, prop_picture_or_uuid, prop_model_id, prop_type, prop_date_or_int_part_name, prop_note_or_int_subtitle, prop_part_number_or_alto, prop_aditional_info_or_ocr, prop_exist) VALUES ((?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?))";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(TreeStructureDAOImpl.class);

    /** The dao utils. */
    @Inject
    private DAOUtils daoUtils;

    /**
     * The Enum DISCRIMINATOR.
     */
    private static enum DISCRIMINATOR {

        /** The ALL. */
        ALL,
        /** The AL l_ o f_ user. */
        ALL_OF_USER,
        /** The BARCOD e_ o f_ user. */
        BARCODE_OF_USER
    }

    /**
     * Gets the all saved structures of user.
     * 
     * @param userId
     *        the user id
     * @return the all saved structures of user
     * @throws DatabaseException
     *         the database exception {@inheritDoc}
     */
    @Override
    public ArrayList<TreeStructureInfo> getAllSavedStructuresOfUser(long userId) throws DatabaseException {
        return getSavedStructures(DISCRIMINATOR.ALL_OF_USER, userId, null);
    }

    /**
     * Gets the all saved structures.
     * 
     * @param code
     *        the code
     * @return the all saved structures
     * @throws DatabaseException
     *         the database exception {@inheritDoc}
     */
    @Override
    public ArrayList<TreeStructureInfo> getAllSavedStructures(String code) throws DatabaseException {
        return getSavedStructures(DISCRIMINATOR.ALL, -1, code);
    }

    /**
     * Gets the saved structures of user.
     * 
     * @param userId
     *        the user id
     * @param code
     *        the code
     * @return the saved structures of user
     * @throws DatabaseException
     *         the database exception {@inheritDoc}
     */
    @Override
    public ArrayList<TreeStructureInfo> getSavedStructuresOfUser(long userId, String code)
            throws DatabaseException {
        return getSavedStructures(DISCRIMINATOR.BARCODE_OF_USER, userId, code);
    }

    /**
     * Gets the saved structures.
     * 
     * @param what
     *        the what
     * @param userId
     *        the user id
     * @param code
     *        the code
     * @return the saved structures
     * @throws DatabaseException
     *         the database exception
     */
    private ArrayList<TreeStructureInfo> getSavedStructures(DISCRIMINATOR what, long userId, String code)
            throws DatabaseException {
        PreparedStatement selectSt = null;
        ArrayList<TreeStructureInfo> retList = new ArrayList<TreeStructureInfo>();
        try {
            switch (what) {
                case ALL:
                    if (code != null) {
                        selectSt = getConnection().prepareStatement(SELECT_INFOS_WITH_CODE);
                        selectSt.setString(1, code);
                    } else {
                        selectSt = getConnection().prepareStatement(SELECT_INFOS);
                    }
                    break;
                case ALL_OF_USER:
                    selectSt = getConnection().prepareStatement(SELECT_INFOS_BY_USER);
                    selectSt.setLong(1, userId);
                    break;
                case BARCODE_OF_USER:
                    selectSt = getConnection().prepareStatement(SELECT_INFOS_BY_USER_AND_CODE);
                    selectSt.setLong(1, userId);
                    selectSt.setString(2, code);
                    break;
                default:
                    throw new IllegalStateException(what.toString());
            }

        } catch (SQLException e) {
            LOGGER.error("Could not get select infos statement", e);
        }

        try {
            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = rs.getTimestamp("timestamp");
                if (date != null) {
                    retList.add(new TreeStructureInfo(rs.getLong("id"), formatter.format(date), rs
                            .getString("description"), rs.getString("barcode"), rs.getString("name"), rs
                            .getString("full_name"), rs.getString("input_queue_directory_path"), rs
                            .getString("model")));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return retList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeSavedStructure(long id) throws DatabaseException {
        PreparedStatement deleteSt = null, disableSt = null;
        boolean successful = false;
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }

        try {
            deleteSt = getConnection().prepareStatement(DELETE_NODES);
            deleteSt.setLong(1, id);
            deleteSt.executeUpdate();

            disableSt = getConnection().prepareStatement(DISABLE_INFO);
            disableSt.setLong(1, id);
            if (disableSt.executeUpdate() == 1) {
                LOGGER.debug("DB has been updated: The tree structure info: " + id + " has been disabled.");
                boolean success =
                        daoUtils.insertCrudAction(getUserId(),
                                                  Constants.TABLE_CRUD_TREE_STRUCTURE_ACTION,
                                                  "tree_structure_id",
                                                  id,
                                                  CRUD_ACTION_TYPES.DELETE,
                                                  false);
                if (success) {
                    getConnection().commit();
                    successful = true;
                    LOGGER.debug("DB has been updated by commit.");
                } else {
                    getConnection().rollback();
                    LOGGER.debug("DB has not been updated -> rollback!");
                }
            } else {
                LOGGER.error("DB has not been updated! " + deleteSt + "\n" + disableSt);
            }

        } catch (SQLException e) {
            LOGGER.error("Could not delete tree structure (info) with id " + id + "\n Query1: " + deleteSt
                    + "\n Query2: " + disableSt, e);
        } finally {
            closeConnection();
        }
        return successful;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<TreeStructureNode> loadStructure(long structureId) throws DatabaseException {
        PreparedStatement selectSt = null;
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }

        ArrayList<TreeStructureNode> retList = new ArrayList<TreeStructureNode>();
        try {
            selectSt = getConnection().prepareStatement(SELECT_NODES);
            selectSt.setLong(1, structureId);
        } catch (SQLException e) {
            LOGGER.error("Could not get select nodes statement", e);
        }

        try {
            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                retList.add(new TreeStructureNode(rs.getString("prop_id"),
                                                  rs.getString("prop_parent"),
                                                  rs.getString("prop_name"),
                                                  rs.getString("prop_picture_or_uuid"),
                                                  rs.getString("prop_model_id"),
                                                  rs.getString("prop_type"),
                                                  rs.getString("prop_date_or_int_part_name"),
                                                  rs.getString("prop_note_or_int_subtitle"),
                                                  rs.getString("prop_part_number_or_alto"),
                                                  rs.getString("prop_aditional_info_or_ocr"),
                                                  rs.getBoolean("prop_exist")));

            }
            if (retList.isEmpty()) {
                LOGGER.debug("DB has been updated: The tree structure: " + structureId + " has been read.");
                boolean success =
                        daoUtils.insertCrudAction(getUserId(),
                                                  Constants.TABLE_CRUD_TREE_STRUCTURE_ACTION,
                                                  "tree_structure_id",
                                                  structureId,
                                                  CRUD_ACTION_TYPES.READ,
                                                  false);
                if (success) {
                    getConnection().commit();
                    LOGGER.debug("DB has been updated by commit.");
                } else {
                    getConnection().rollback();
                    LOGGER.debug("DB has not been updated -> rollback!");
                }
            } else {
                LOGGER.error("DB has not been updated! " + selectSt);
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return retList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean saveStructure(long userId, TreeStructureInfo info, List<TreeStructureNode> structure)
            throws DatabaseException {
        if (info == null) throw new NullPointerException("info");
        if (structure == null) throw new NullPointerException("structure");
        boolean successful = false;
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }
        PreparedStatement insertInfoSt = null, insSt = null;

        try {
            if (daoUtils.checkInputQueue(info.getInputPath(), null, false)) {
                // TX start
                insertInfoSt = getConnection().prepareStatement(INSERT_INFO, Statement.RETURN_GENERATED_KEYS);
                insertInfoSt.setString(1, info.getBarcode());
                insertInfoSt.setString(2, info.getDescription() != null ? info.getDescription() : "");
                insertInfoSt.setString(3, info.getName());
                insertInfoSt.setString(4, info.getModel());
                insertInfoSt.setString(5, DAOUtilsImpl.directoryPathToRightFormat(info.getInputPath()));
                insertInfoSt.executeUpdate();

                ResultSet gk = insertInfoSt.getGeneratedKeys();
                Long key;
                if (gk.next()) {
                    key = gk.getLong(1);
                } else {
                    LOGGER.error("No key has been returned! " + insertInfoSt);
                    getConnection().rollback();
                    throw new DatabaseException("Unable to obtain new id from DB when executing query: "
                            + insertInfoSt);
                    //                
                    //                TODO: zkontrolovat vsude rollback pri nenavraceni id
                    //                
                }

                int total = 0;
                for (TreeStructureNode node : structure) {
                    insSt = getConnection().prepareStatement(INSERT_NODE);
                    insSt.setLong(1, key);
                    insSt.setString(2, node.getPropId());
                    insSt.setString(3, node.getPropParent());
                    insSt.setString(4, node.getPropName());
                    insSt.setString(5, node.getPropPictureOrUuid());
                    insSt.setString(6, node.getPropModelId());
                    insSt.setString(7, node.getPropType());
                    insSt.setString(8, node.getPropDateOrIntPartName());
                    insSt.setString(9, node.getPropNoteOrIntSubtitle());
                    insSt.setString(10, node.getPropPartNumberOrAlto());
                    insSt.setString(11, node.getPropAditionalInfoOrOcr());
                    insSt.setBoolean(12, node.isPropExist());
                    total += insSt.executeUpdate();
                }
                if (total != structure.size()) {
                    getConnection().rollback();
                    throw new DatabaseException("Unable to insert _ALL_ nodes: " + total
                            + " nodes were inserted of " + structure.size());
                } else {
                    LOGGER.debug("DB has been updated by commit.");
                    getConnection().commit();

                    successful =
                            daoUtils.insertCrudAction(userId,
                                                      Constants.TABLE_CRUD_TREE_STRUCTURE_ACTION,
                                                      "tree_structure_id",
                                                      key,
                                                      CRUD_ACTION_TYPES.CREATE,
                                                      false);
                    if (!successful) {
                        throw new DatabaseException("DB has not been updated: Unable to insert crud action!");
                    }
                }
            }
        } catch (SQLException e) {
            try {
                getConnection().rollback();
            } catch (SQLException e1) {
                LOGGER.error(e.getMessage());
                e1.printStackTrace();
            }
            LOGGER.error("Queries: \"" + insertInfoSt + "\" and \"" + insSt + "\"", e);
        } finally {
            closeConnection();
        }

        return successful;
    }
}
