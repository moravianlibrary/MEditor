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
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.rpc.EditorDate;
import cz.mzk.editor.shared.rpc.HistoryItem;
import cz.mzk.editor.shared.rpc.HistoryItemInfo;

/**
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public class ActionDAOImpl
        extends AbstractDAO
        implements ActionDAO {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ActionDAOImpl.class.getPackage().toString());

    public static final String SELECT_USER_ACTIONS_TIMESTAMP = "SELECT timestamp FROM "
            + Constants.TABLE_ACTION + " WHERE editor_user_id = (?)";

    private static final String INTERVAL_CONSTRAINTS =
            "a.timestamp > '%lowerTimestamp' AND a.timestamp < (timestamp '%s' + INTERVAL '1 day')";

    private static final String USER_ID_AND_INTERVAL_CONSTRAINTS = "editor_user_id = (?) AND "
            + INTERVAL_CONSTRAINTS;

    public static final String SELECT_CHILD_TABLES_OF_INTERVAL = "SELECT p.relname AS tableName FROM "
            + Constants.TABLE_ACTION + " a, pg_class p WHERE " + USER_ID_AND_INTERVAL_CONSTRAINTS
            + " AND a.tableoid = p.oid GROUP BY p.relname";

    public static final String SELECT_LOG_IN_OUT_ACTION = "SELECT * FROM " + Constants.TABLE_LOG_IN_OUT
            + " a WHERE " + USER_ID_AND_INTERVAL_CONSTRAINTS;

    public static final String SELECT_CONVERSION_ACTION = "SELECT * FROM " + Constants.TABLE_CONVERSION
            + " a WHERE " + USER_ID_AND_INTERVAL_CONSTRAINTS;

    public static final String SELECT_LONG_PROCESS_ACTION = "SELECT * FROM "
            + Constants.TABLE_LONG_RUNNING_PROCESS + " a WHERE " + USER_ID_AND_INTERVAL_CONSTRAINTS;

    public static final String SELECT_DEFAULT_ACTION = "SELECT * FROM " + Constants.TABLE_ACTION
            + " a WHERE " + USER_ID_AND_INTERVAL_CONSTRAINTS;

    public static final String SELECT_CRUD_LOCK_ACTION =
            "SELECT * FROM (SELECT ac.id, ac.timestamp, ac.type, l.digital_object_uuid, ac.editor_user_id FROM "
                    + Constants.TABLE_CRUD_LOCK_ACTION + " ac INNER JOIN " + Constants.TABLE_LOCK
                    + " l ON ac.lock_id = l.id) a WHERE " + USER_ID_AND_INTERVAL_CONSTRAINTS;

    public static final String SELECT_CRUD_SAVED_EDIT_OBJ_ACTION =
            "SELECT * FROM (SELECT ac.id, ac.timestamp, ac.type, o.digital_object_uuid, ac.editor_user_id FROM "
                    + Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION + " ac INNER JOIN "
                    + Constants.TABLE_SAVED_EDITED_OBJECT + " o ON ac.saved_edited_id = o.id) a WHERE "
                    + USER_ID_AND_INTERVAL_CONSTRAINTS;

    public static final String SELECT_CRUD_TREE_STRUC_ACTION =
            "SELECT * FROM (SELECT ac.id, ac.timestamp, ac.type, s.name, ac.editor_user_id, s.input_queue_directory_path FROM "
                    + Constants.TABLE_CRUD_TREE_STRUCTURE_ACTION
                    + " ac INNER JOIN "
                    + Constants.TABLE_TREE_STRUCTURE
                    + " s ON ac.tree_structure_id = s.id) a WHERE "
                    + USER_ID_AND_INTERVAL_CONSTRAINTS;

    public static final String SELECT_CRUD_REQUEST_ACTION =
            "SELECT * FROM (SELECT ac.id, ac.timestamp, ac.type, r.type AS reqType, ac.editor_user_id FROM "
                    + Constants.TABLE_CRUD_REQUEST_TO_ADMIN_ACTION + " ac INNER JOIN "
                    + Constants.TABLE_REQUEST_TO_ADMIN + " r ON ac.request_to_admin_id = r.id) a WHERE "
                    + USER_ID_AND_INTERVAL_CONSTRAINTS;

    public static final String SELECT_CRUD_DIGITAL_OBJECT_ACTION =
            "SELECT ac.*, uuid, name FROM (SELECT * FROM ONLY " + Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION
                    + " a WHERE " + USER_ID_AND_INTERVAL_CONSTRAINTS + ") ac INNER JOIN "
                    + Constants.TABLE_DIGITAL_OBJECT + " ON ac.digital_object_uuid = uuid";

    public static final String SELECT_CRUD_DO_WITH_TOP_OBJ_ACTION =

    "SELECT ac.*, uuid, name FROM (SELECT * FROM ONLY " + Constants.TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT
            + " a WHERE digital_object_uuid = top_digital_object_uuid AND "
            + USER_ID_AND_INTERVAL_CONSTRAINTS + ") ac INNER JOIN " + Constants.TABLE_DIGITAL_OBJECT
            + " ON ac.top_digital_object_uuid = uuid";

    public static final String SELECT_ALL_CHILDREN_OF_TOP = "SELECT name, model, uuid FROM "
            + Constants.TABLE_DIGITAL_OBJECT + " WHERE uuid IN (" + "SELECT digital_object_uuid FROM "
            + Constants.TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT
            + " WHERE top_digital_object_uuid = (?) AND top_digital_object_uuid != digital_object_uuid)";

    public static final String SELECT_ALL_DIGITAL_OBJECT_INFO_ITEM = "SELECT * FROM "
            + Constants.TABLE_DIGITAL_OBJECT + " WHERE uuid = (SELECT digital_object_uuid FROM "
            + Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION + " WHERE id = (?))";

    public static final String SELECT_ALL_LOCK_INFO_ITEM =
            "SELECT l.*, o.uuid, o.model, o.name FROM ((SELECT * FROM " + Constants.TABLE_LOCK
                    + " WHERE id = (SELECT lock_id FROM " + Constants.TABLE_CRUD_LOCK_ACTION
                    + " WHERE id = (?))) l INNER JOIN " + Constants.TABLE_DIGITAL_OBJECT
                    + " o ON o.uuid = l.digital_object_uuid)";

    public static final String SELECT_ALL_SAVED_EDITED_INFO_ITEM =
            "SELECT s.*, o.uuid, o.model, o.name FROM ((SELECT * FROM " + Constants.TABLE_SAVED_EDITED_OBJECT
                    + " WHERE id = (SELECT saved_edited_object_id FROM "
                    + Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION + " WHERE id = (?))) s INNER JOIN "
                    + Constants.TABLE_DIGITAL_OBJECT + " o ON o.uuid = s.digital_object_uuid)";

    public static final String SELECT_ALL_TREE_STRUC_INFO_ITEM = "SELECT * FROM "
            + Constants.TABLE_TREE_STRUCTURE + " WHERE id = (SELECT tree_structure_id FROM "
            + Constants.TABLE_CRUD_TREE_STRUCTURE_ACTION + " WHERE id = (?))";

    public static final String SELECT_DO_LOCK_DAYS = "SELECT timestamp FROM "
            + Constants.TABLE_CRUD_LOCK_ACTION + " a WHERE a.lock_id = (SELECT id FROM "
            + Constants.TABLE_LOCK + " l WHERE digital_object_uuid = (?))";

    public static final String SELECT_DO_EDIT_SAVED_DAYS = "SELECT timestamp FROM "
            + Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION
            + " a WHERE a.saved_edited_object_id = (SELECT id FROM " + Constants.TABLE_SAVED_EDITED_OBJECT
            + " s WHERE digital_object_uuid = (?))";

    public static final String SELECT_DO_DAYS = "SELECT timestamp FROM "
            + Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION + " WHERE digital_object_uuid = (?)";

    //    

    public static final String SELECT_DO_LOCK_ACTION =
            "SELECT au.id, au.name, au.surname, au.timestamp, au.type FROM ((SELECT * FROM "
                    + Constants.TABLE_CRUD_LOCK_ACTION + " a WHERE a.lock_id = (SELECT id FROM "
                    + Constants.TABLE_LOCK + " l WHERE digital_object_uuid = (?)) AND "
                    + INTERVAL_CONSTRAINTS + ") al LEFT JOIN (SELECT id AS user_id, name, surname FROM "
                    + Constants.TABLE_EDITOR_USER + ") u ON al.editor_user_id = u.user_id) au";

    public static final String SELECT_DO_EDIT_SAVED_ACTION =
            "SELECT au.id, au.name, au.surname, au.timestamp, au.type FROM ((SELECT * FROM "
                    + Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION
                    + " a WHERE a.saved_edited_object_id = (SELECT id FROM "
                    + Constants.TABLE_SAVED_EDITED_OBJECT + " s WHERE digital_object_uuid = (?)) AND "
                    + INTERVAL_CONSTRAINTS + ") aso LEFT JOIN (SELECT id AS user_id, name, surname FROM "
                    + Constants.TABLE_EDITOR_USER + ") u ON aso.editor_user_id = u.user_id) au";

    public static final String SELECT_DO_ACTION =
            "SELECT  uo.id, uo.name, uo.surname, uo.timestamp, uo.type, uo.tableName FROM ((SELECT * FROM (SELECT t.id, t.editor_user_id, t.timestamp, t.type, t.digital_object_uuid, p.relname AS tableName FROM "
                    + Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION
                    + " t INNER JOIN pg_class p ON t.tableoid = p.oid) a WHERE a.digital_object_uuid = (?) AND "
                    + INTERVAL_CONSTRAINTS
                    + ") o LEFT JOIN (SELECT id AS user_id, name, surname FROM "
                    + Constants.TABLE_EDITOR_USER + ") u ON o.editor_user_id = u.user_id) uo";

    public static final SimpleDateFormat FORMATTER_TO_DAY = new SimpleDateFormat("dd");
    public static final SimpleDateFormat FORMATTER_TO_MOUNTH = new SimpleDateFormat("MM");
    public static final SimpleDateFormat FORMATTER_TO_YEAR = new SimpleDateFormat("yyyy");
    public static final SimpleDateFormat FORMATTER_TO_HOUR = new SimpleDateFormat("hh");
    public static final SimpleDateFormat FORMATTER_TO_MINUTE = new SimpleDateFormat("mm");
    public static final SimpleDateFormat FORMATTER_TO_SECOND = new SimpleDateFormat("ss");

    private abstract class ActionDAOHandler
            extends AbstractDAO {

        /**
         * Instantiates a new action dao handler.
         * 
         * @param editorUserId
         *        the editor user id
         * @param lowerLimit
         *        the lower limit
         * @param upperLimit
         *        the upper limit
         * @param sql
         *        the sql
         * @param historyItems
         *        the history items
         * @throws DatabaseException
         *         the database exception
         */
        public ActionDAOHandler(Long editorUserId,
                                String uuid,
                                EditorDate lowerLimit,
                                EditorDate upperLimit,
                                String sql,
                                List<HistoryItem> historyItems)
                throws DatabaseException {

            try {
                ResultSet rs =
                        getAnyActionSelSt(editorUserId, uuid, lowerLimit, upperLimit, sql).executeQuery();

                while (rs.next()) {
                    historyItems.add(getHistoryItemFromResultSet(rs));
                }

            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            } finally {
                closeConnection();
            }
        }

        /**
         * @return
         */
        protected abstract HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException;

    }

    @Override
    public List<EditorDate> getHistoryDays(Long userId, String uuid) throws DatabaseException {
        List<EditorDate> days = new ArrayList<EditorDate>();

        if (userId == null && uuid == null) {
            userId = getUserId();
        }

        if (userId != null) {
            PreparedStatement selSt = null;

            long editorUserId = (userId == null) ? getUserId() : userId;

            try {
                selSt = getConnection().prepareStatement(SELECT_USER_ACTIONS_TIMESTAMP);
                selSt.setLong(1, editorUserId);

                ResultSet rs = selSt.executeQuery();

                while (rs.next()) {
                    Timestamp timestamp = rs.getTimestamp("timestamp");
                    EditorDate date = getEditorDate(timestamp, true);
                    if (!days.contains(date)) days.add(date);
                }

            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            } finally {
                closeConnection();
            }

        } else if (uuid != null) {
            handleDigitalObjectDays(uuid, days);
        }

        return days;
    }

    /**
     * @param uuid
     * @param days
     * @throws DatabaseException
     */
    private void handleDigitalObjectDays(String uuid, List<EditorDate> days) throws DatabaseException {
        PreparedStatement selectSt = null;

        //        TODO

        try {
            selectSt = getConnection().prepareStatement(SELECT_DO_DAYS);
            selectSt.setString(1, uuid);

            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                days.add(getEditorDate(rs.getTimestamp("timestamp"), true));
            }

            selectSt = getConnection().prepareStatement(SELECT_DO_EDIT_SAVED_DAYS);
            selectSt.setString(1, uuid);

            rs = selectSt.executeQuery();
            while (rs.next()) {
                days.add(getEditorDate(rs.getTimestamp("timestamp"), true));
            }

            selectSt = getConnection().prepareStatement(SELECT_DO_LOCK_DAYS);
            selectSt.setString(1, uuid);

            rs = selectSt.executeQuery();
            while (rs.next()) {
                days.add(getEditorDate(rs.getTimestamp("timestamp"), true));
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private EditorDate getEditorDate(Timestamp timestamp, boolean onlyDay) {
        EditorDate date =
                new EditorDate(Integer.parseInt(FORMATTER_TO_DAY.format(timestamp)),
                               Integer.parseInt(FORMATTER_TO_MOUNTH.format(timestamp)),
                               Integer.parseInt(FORMATTER_TO_YEAR.format(timestamp)));
        if (!onlyDay) {
            date.setHour(Integer.parseInt(FORMATTER_TO_HOUR.format(timestamp)));
            date.setMinute(Integer.parseInt(FORMATTER_TO_MINUTE.format(timestamp)));
            date.setSecond(Integer.parseInt(FORMATTER_TO_SECOND.format(timestamp)));
        }
        return date;
    }

    @Override
    public List<HistoryItem> getHistoryItems(Long editorUserId,
                                             String uuid,
                                             EditorDate lowerLimit,
                                             EditorDate upperLimit) throws DatabaseException {

        List<HistoryItem> historyItems = new ArrayList<HistoryItem>();

        if (editorUserId != null) {
            List<String> tableNames = getTableNames(editorUserId, lowerLimit, upperLimit);
            for (String tableName : tableNames) {

                if (tableName.equals(Constants.TABLE_LOG_IN_OUT)) {
                    handleLogInOutAction(editorUserId, lowerLimit, upperLimit, historyItems);

                } else if (tableName.equals(Constants.TABLE_CONVERSION)) {
                    handleConversionAction(editorUserId, lowerLimit, upperLimit, historyItems);

                } else if (tableName.equals(Constants.TABLE_CRUD_LOCK_ACTION)) {
                    handleCrudLockAction(editorUserId, lowerLimit, upperLimit, historyItems);

                } else if (tableName.equals(Constants.TABLE_CRUD_TREE_STRUCTURE_ACTION)) {
                    handleCrudTreeStrucAction(editorUserId, lowerLimit, upperLimit, historyItems);

                } else if (tableName.equals(Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION)) {
                    handleCrudDigitalObjectAction(editorUserId, lowerLimit, upperLimit, historyItems);

                } else if (tableName.equals(Constants.TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT)) {
                    handleCrudDOWithTopObjAction(editorUserId, lowerLimit, upperLimit, historyItems);

                } else if (tableName.equals(Constants.TABLE_CRUD_REQUEST_TO_ADMIN_ACTION)) {
                    handleCrudRequestAction(editorUserId, lowerLimit, upperLimit, historyItems);

                } else if (tableName.equals(Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION)) {
                    handleCrudSavedEditObjAction(editorUserId, lowerLimit, upperLimit, historyItems);

                } else if (tableName.equals(Constants.TABLE_LONG_RUNNING_PROCESS)) {
                    handleLongProcessAction(editorUserId, lowerLimit, upperLimit, historyItems);

                } else {
                    handleDefaultAction(editorUserId, lowerLimit, upperLimit, historyItems);
                }
            }
        } else if (uuid != null) {

            handleDigitalObjectAction(uuid, lowerLimit, upperLimit, historyItems);

        }
        return historyItems;
    }

    /**
     * @param uuid
     * @param historyItems
     * @throws DatabaseException
     */
    private void handleDigitalObjectAction(String uuid,
                                           EditorDate lowerLimit,
                                           EditorDate upperLimit,
                                           List<HistoryItem> historyItems) throws DatabaseException {

        new ActionDAOHandler(null, uuid, lowerLimit, upperLimit, SELECT_DO_ACTION, historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                //        TODO
                return new HistoryItem(rs.getLong("id"),
                                       getEditorDate(rs.getTimestamp("timestamp"), false),
                                       CRUD_ACTION_TYPES.parseString(rs.getString("type")),
                                       rs.getString("tableName"),
                                       rs.getString("surname") + " " + rs.getString("name"),
                                       true);
            }
        };
        new ActionDAOHandler(null, uuid, lowerLimit, upperLimit, SELECT_DO_EDIT_SAVED_ACTION, historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                //        TODO
                return new HistoryItem(rs.getLong("id"),
                                       getEditorDate(rs.getTimestamp("timestamp"), false),
                                       CRUD_ACTION_TYPES.parseString(rs.getString("type")),
                                       Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION,
                                       rs.getString("surname") + " " + rs.getString("name"),
                                       true);
            }
        };
        new ActionDAOHandler(null, uuid, lowerLimit, upperLimit, SELECT_DO_LOCK_ACTION, historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                //        TODO
                return new HistoryItem(rs.getLong("id"),
                                       getEditorDate(rs.getTimestamp("timestamp"), false),
                                       CRUD_ACTION_TYPES.parseString(rs.getString("type")),
                                       Constants.TABLE_CRUD_LOCK_ACTION,
                                       rs.getString("surname") + " " + rs.getString("name"),
                                       true);
            }
        };

    }

    private void handleDefaultAction(Long editorUserId,
                                     EditorDate lowerLimit,
                                     EditorDate upperLimit,
                                     List<HistoryItem> historyItems) throws DatabaseException {

        new ActionDAOHandler(editorUserId, null, lowerLimit, upperLimit, SELECT_DEFAULT_ACTION, historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       getEditorDate(rs.getTimestamp("timestamp"), false),
                                       null,
                                       Constants.TABLE_ACTION,
                                       null,
                                       false);
            }
        };
    }

    private void handleLongProcessAction(Long editorUserId,
                                         EditorDate lowerLimit,
                                         EditorDate upperLimit,
                                         List<HistoryItem> historyItems) throws DatabaseException {

        new ActionDAOHandler(editorUserId,
                             null,
                             lowerLimit,
                             upperLimit,
                             SELECT_LONG_PROCESS_ACTION,
                             historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       getEditorDate(rs.getTimestamp("timestamp"), false),
                                       CRUD_ACTION_TYPES.CREATE,
                                       Constants.TABLE_LONG_RUNNING_PROCESS,
                                       rs.getTimestamp("finished").toString(),
                                       false);
            }
        };
    }

    private void handleCrudRequestAction(Long editorUserId,
                                         EditorDate lowerLimit,
                                         EditorDate upperLimit,
                                         List<HistoryItem> historyItems) throws DatabaseException {

        new ActionDAOHandler(editorUserId,
                             null,
                             lowerLimit,
                             upperLimit,
                             SELECT_CRUD_REQUEST_ACTION,
                             historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       getEditorDate(rs.getTimestamp("timestamp"), false),
                                       CRUD_ACTION_TYPES.parseString(rs.getString("type")),
                                       Constants.TABLE_CRUD_REQUEST_TO_ADMIN_ACTION,
                                       rs.getString("reqType"),
                                       false);
            }
        };
    }

    private void handleCrudTreeStrucAction(Long editorUserId,
                                           EditorDate lowerLimit,
                                           EditorDate upperLimit,
                                           List<HistoryItem> historyItems) throws DatabaseException {

        new ActionDAOHandler(editorUserId,
                             null,
                             lowerLimit,
                             upperLimit,
                             SELECT_CRUD_TREE_STRUC_ACTION,
                             historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       getEditorDate(rs.getTimestamp("timestamp"), false),
                                       CRUD_ACTION_TYPES.parseString(rs.getString("type")),
                                       Constants.TABLE_CRUD_TREE_STRUCTURE_ACTION,
                                       rs.getString("name") + " ["
                                               + rs.getString("input_queue_directory_path") + "]",
                                       true);
            }
        };
    }

    private void handleCrudSavedEditObjAction(Long editorUserId,
                                              EditorDate lowerLimit,
                                              EditorDate upperLimit,
                                              List<HistoryItem> historyItems) throws DatabaseException {

        new ActionDAOHandler(editorUserId,
                             null,
                             lowerLimit,
                             upperLimit,
                             SELECT_CRUD_SAVED_EDIT_OBJ_ACTION,
                             historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       getEditorDate(rs.getTimestamp("timestamp"), false),
                                       CRUD_ACTION_TYPES.parseString(rs.getString("type")),
                                       Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION,
                                       rs.getString("digital_object_uuid"),
                                       true);
            }
        };
    }

    private void handleCrudLockAction(Long editorUserId,
                                      EditorDate lowerLimit,
                                      EditorDate upperLimit,
                                      List<HistoryItem> historyItems) throws DatabaseException {

        new ActionDAOHandler(editorUserId,
                             null,
                             lowerLimit,
                             upperLimit,
                             SELECT_CRUD_LOCK_ACTION,
                             historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       getEditorDate(rs.getTimestamp("timestamp"), false),
                                       CRUD_ACTION_TYPES.parseString(rs.getString("type")),
                                       Constants.TABLE_CRUD_LOCK_ACTION,
                                       rs.getString("digital_object_uuid"),
                                       true);
            }
        };
    }

    private void handleCrudDOWithTopObjAction(Long editorUserId,
                                              EditorDate lowerLimit,
                                              EditorDate upperLimit,
                                              List<HistoryItem> historyItems) throws DatabaseException {

        new ActionDAOHandler(editorUserId,
                             null,
                             lowerLimit,
                             upperLimit,
                             SELECT_CRUD_DO_WITH_TOP_OBJ_ACTION,
                             historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       getEditorDate(rs.getTimestamp("timestamp"), false),
                                       CRUD_ACTION_TYPES.parseString(rs.getString("type")),
                                       Constants.TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT,
                                       rs.getString("name") + " [PID: " + rs.getString("digital_object_uuid")
                                               + "]",
                                       true);
            }
        };
    }

    private void handleCrudDigitalObjectAction(Long editorUserId,
                                               EditorDate lowerLimit,
                                               EditorDate upperLimit,
                                               List<HistoryItem> historyItems) throws DatabaseException {

        new ActionDAOHandler(editorUserId,
                             null,
                             lowerLimit,
                             upperLimit,
                             SELECT_CRUD_DIGITAL_OBJECT_ACTION,
                             historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       getEditorDate(rs.getTimestamp("timestamp"), false),
                                       CRUD_ACTION_TYPES.parseString(rs.getString("type")),
                                       Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION,
                                       rs.getString("name") + " [PID: " + rs.getString("digital_object_uuid")
                                               + "]",
                                       true);
            }
        };
    }

    private void handleConversionAction(Long editorUserId,
                                        EditorDate lowerLimit,
                                        EditorDate upperLimit,
                                        List<HistoryItem> historyItems) throws DatabaseException {

        new ActionDAOHandler(editorUserId,
                             null,
                             lowerLimit,
                             upperLimit,
                             SELECT_CONVERSION_ACTION,
                             historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       getEditorDate(rs.getTimestamp("timestamp"), false),
                                       CRUD_ACTION_TYPES.CREATE,
                                       Constants.TABLE_CONVERSION,
                                       rs.getString("input_queue_directory_path"),
                                       false);
            }
        };
    }

    private void handleLogInOutAction(Long editorUserId,
                                      EditorDate lowerLimit,
                                      EditorDate upperLimit,
                                      List<HistoryItem> historyItems) throws DatabaseException {

        new ActionDAOHandler(editorUserId,
                             null,
                             lowerLimit,
                             upperLimit,
                             SELECT_LOG_IN_OUT_ACTION,
                             historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       getEditorDate(rs.getTimestamp("timestamp"), false),
                                       (rs.getBoolean("type")) ? CRUD_ACTION_TYPES.CREATE
                                               : CRUD_ACTION_TYPES.DELETE,
                                       Constants.TABLE_LOG_IN_OUT,
                                       null,
                                       false);
            }
        };
    }

    private List<String> getTableNames(Long editorUserId, EditorDate lowerLimit, EditorDate upperLimit)
            throws DatabaseException {

        List<String> tableNames = new ArrayList<String>();

        try {
            ResultSet rs =
                    getAnyActionSelSt(editorUserId,
                                      null,
                                      lowerLimit,
                                      upperLimit,
                                      SELECT_CHILD_TABLES_OF_INTERVAL).executeQuery();
            while (rs.next()) {
                tableNames.add(rs.getString("tableName"));
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return tableNames;
    }

    private PreparedStatement getAnyActionSelSt(Long editorUserId,
                                                String uuid,
                                                EditorDate lowerLimit,
                                                EditorDate upperLimit,
                                                String sql) throws DatabaseException {
        PreparedStatement selectSt = null;

        try {
            selectSt =
                    getConnection()
                            .prepareStatement(String.format(sql.replace("%lowerTimestamp",
                                                                        getStringTimestamp(lowerLimit)),
                                                            getStringTimestamp(upperLimit)));
            if (editorUserId != null) {
                selectSt.setLong(1, editorUserId);
            } else {
                selectSt.setString(1, uuid);
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get any action select statement: " + selectSt + ": " + e.getMessage());
            e.printStackTrace();
        }
        return selectSt;
    }

    private String getStringTimestamp(EditorDate date) {
        return date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
    }

    @Override
    public HistoryItemInfo getHistoryItemInfo(Long id, String tableName) throws DatabaseException {

        if (tableName.equals(Constants.TABLE_CRUD_LOCK_ACTION)) {
            return getAllLockInfo(id);

        } else if (tableName.equals(Constants.TABLE_CRUD_TREE_STRUCTURE_ACTION)) {
            return getAllTreeStrucInfo(id);

        } else if (tableName.equals(Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION)) {
            return getAllDigitalObjectInfo(id);

        } else if (tableName.equals(Constants.TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT)) {
            return getAllTopDOInfo(id);

        } else if (tableName.equals(Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION)) {
            return getAllSavedEditedInfo(id);

        } else {
            return null;
        }
    }

    private HistoryItemInfo getAllTopDOInfo(Long id) throws DatabaseException {
        PreparedStatement selectSt = null;
        HistoryItemInfo historyItemInfo = getAllDigitalObjectInfo(id);
        historyItemInfo.setChildren(new ArrayList<HistoryItemInfo>());

        try {
            selectSt = getConnection().prepareStatement(SELECT_ALL_CHILDREN_OF_TOP);
            selectSt.setString(1, historyItemInfo.getUuid());
            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                HistoryItemInfo child = new HistoryItemInfo();
                child.setUuid(rs.getString("uuid"));
                child.setName(rs.getString("name"));
                child.setModel(DigitalObjectModel.parseString(rs.getString("model")));
                historyItemInfo.getChildren().add(child);
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get any action select statement: " + selectSt + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return historyItemInfo;
    }

    private HistoryItemInfo getAllDigitalObjectInfo(Long id) throws DatabaseException {
        PreparedStatement selectSt = null;
        HistoryItemInfo historyItemInfo = null;

        try {
            selectSt = getConnection().prepareStatement(SELECT_ALL_DIGITAL_OBJECT_INFO_ITEM);
            selectSt.setLong(1, id);
            ResultSet rs = selectSt.executeQuery();
            if (rs.next()) {
                historyItemInfo = new HistoryItemInfo();
                historyItemInfo.setUuid(rs.getString("uuid"));
                historyItemInfo.setDescription(rs.getString("description"));
                historyItemInfo.setState(rs.getBoolean("state"));
                historyItemInfo.setModel(DigitalObjectModel.parseString(rs.getString("model")));
                historyItemInfo.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get any action select statement: " + selectSt + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return historyItemInfo;
    }

    private HistoryItemInfo getAllTreeStrucInfo(Long id) throws DatabaseException {
        PreparedStatement selectSt = null;
        HistoryItemInfo historyItemInfo = null;

        try {
            selectSt = getConnection().prepareStatement(SELECT_ALL_TREE_STRUC_INFO_ITEM);
            selectSt.setLong(1, id);
            ResultSet rs = selectSt.executeQuery();
            if (rs.next()) {
                historyItemInfo = new HistoryItemInfo();
                historyItemInfo.setBarcode(rs.getString("barcode"));
                historyItemInfo.setDescription(rs.getString("description"));
                historyItemInfo.setState(rs.getBoolean("state"));
                historyItemInfo.setModel(DigitalObjectModel.parseString(rs.getString("model")));
                historyItemInfo.setName(rs.getString("name"));
                historyItemInfo.setPath(rs.getString("input_queue_directory_path"));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get any action select statement: " + selectSt + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return historyItemInfo;
    }

    private HistoryItemInfo getAllSavedEditedInfo(Long id) throws DatabaseException {
        PreparedStatement selectSt = null;
        HistoryItemInfo historyItemInfo = null;

        try {
            selectSt = getConnection().prepareStatement(SELECT_ALL_SAVED_EDITED_INFO_ITEM);
            selectSt.setLong(1, id);
            ResultSet rs = selectSt.executeQuery();
            if (rs.next()) {
                historyItemInfo = new HistoryItemInfo();
                historyItemInfo.setUuid(rs.getString("uuid"));
                historyItemInfo.setDescription(rs.getString("description"));
                historyItemInfo.setState(rs.getBoolean("state"));
                historyItemInfo.setModel(DigitalObjectModel.parseString(rs.getString("model")));
                historyItemInfo.setPath(rs.getString("file_name"));
                historyItemInfo.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get any action select statement: " + selectSt + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return historyItemInfo;
    }

    private HistoryItemInfo getAllLockInfo(Long id) throws DatabaseException {
        PreparedStatement selectSt = null;
        HistoryItemInfo historyItemInfo = null;

        try {
            selectSt = getConnection().prepareStatement(SELECT_ALL_LOCK_INFO_ITEM);
            selectSt.setLong(1, id);
            ResultSet rs = selectSt.executeQuery();
            if (rs.next()) {
                historyItemInfo = new HistoryItemInfo();
                historyItemInfo.setUuid(rs.getString("uuid"));
                historyItemInfo.setDescription(rs.getString("description"));
                historyItemInfo.setState(rs.getBoolean("state"));
                historyItemInfo.setModel(DigitalObjectModel.parseString(rs.getString("model")));
                historyItemInfo.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get any action select statement: " + selectSt + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return historyItemInfo;
    }
}
