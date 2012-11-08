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

    private static final String USER_ID_AND_INTERVAL_CONSTRAINTS =
            "editor_user_id = (?) AND a.timestamp > '%lowerTimestamp' AND a.timestamp < (timestamp '%s' + INTERVAL '1 day')";

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

    public static final SimpleDateFormat FORMATTER_TO_DAY = new SimpleDateFormat("dd");
    public static final SimpleDateFormat FORMATTER_TO_MOUNTH = new SimpleDateFormat("MM");
    public static final SimpleDateFormat FORMATTER_TO_YEAR = new SimpleDateFormat("yyyy");

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
                                EditorDate lowerLimit,
                                EditorDate upperLimit,
                                String sql,
                                List<HistoryItem> historyItems)
                throws DatabaseException {

            try {
                ResultSet rs = getAnyActionSelSt(editorUserId, lowerLimit, upperLimit, sql).executeQuery();

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
    public List<EditorDate> getHistoryDays(Long userId) throws DatabaseException {
        List<EditorDate> days = new ArrayList<EditorDate>();
        PreparedStatement selSt = null;

        long editorUserId = (userId == null) ? getUserId() : userId;

        try {
            selSt = getConnection().prepareStatement(SELECT_USER_ACTIONS_TIMESTAMP);
            selSt.setLong(1, editorUserId);

            ResultSet rs = selSt.executeQuery();

            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("timestamp");

                EditorDate date =
                        new EditorDate(Integer.parseInt(FORMATTER_TO_DAY.format(timestamp)),
                                       Integer.parseInt(FORMATTER_TO_MOUNTH.format(timestamp)),
                                       Integer.parseInt(FORMATTER_TO_YEAR.format(timestamp)));
                if (!days.contains(date)) days.add(date);
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return days;
    }

    @Override
    public List<HistoryItem> getHistoryItems(Long editorUserId, EditorDate lowerLimit, EditorDate upperLimit)
            throws DatabaseException {

        List<String> tableNames = getTableNames(editorUserId, lowerLimit, upperLimit);
        List<HistoryItem> historyItems = new ArrayList<HistoryItem>();

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
        return historyItems;
    }

    private void handleDefaultAction(Long editorUserId,
                                     EditorDate lowerLimit,
                                     EditorDate upperLimit,
                                     List<HistoryItem> historyItems) throws DatabaseException {

        new ActionDAOHandler(editorUserId, lowerLimit, upperLimit, SELECT_DEFAULT_ACTION, historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       formatTimestampToSeconds(rs.getTimestamp("timestamp")),
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

        new ActionDAOHandler(editorUserId, lowerLimit, upperLimit, SELECT_LONG_PROCESS_ACTION, historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       formatTimestampToSeconds(rs.getTimestamp("timestamp")),
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

        new ActionDAOHandler(editorUserId, lowerLimit, upperLimit, SELECT_CRUD_REQUEST_ACTION, historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       formatTimestampToSeconds(rs.getTimestamp("timestamp")),
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
                             lowerLimit,
                             upperLimit,
                             SELECT_CRUD_TREE_STRUC_ACTION,
                             historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       formatTimestampToSeconds(rs.getTimestamp("timestamp")),
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
                             lowerLimit,
                             upperLimit,
                             SELECT_CRUD_SAVED_EDIT_OBJ_ACTION,
                             historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       formatTimestampToSeconds(rs.getTimestamp("timestamp")),
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

        new ActionDAOHandler(editorUserId, lowerLimit, upperLimit, SELECT_CRUD_LOCK_ACTION, historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       formatTimestampToSeconds(rs.getTimestamp("timestamp")),
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
                             lowerLimit,
                             upperLimit,
                             SELECT_CRUD_DO_WITH_TOP_OBJ_ACTION,
                             historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       formatTimestampToSeconds(rs.getTimestamp("timestamp")),
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
                             lowerLimit,
                             upperLimit,
                             SELECT_CRUD_DIGITAL_OBJECT_ACTION,
                             historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       formatTimestampToSeconds(rs.getTimestamp("timestamp")),
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

        new ActionDAOHandler(editorUserId, lowerLimit, upperLimit, SELECT_CONVERSION_ACTION, historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       formatTimestampToSeconds(rs.getTimestamp("timestamp")),
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

        new ActionDAOHandler(editorUserId, lowerLimit, upperLimit, SELECT_LOG_IN_OUT_ACTION, historyItems) {

            @Override
            protected HistoryItem getHistoryItemFromResultSet(ResultSet rs) throws SQLException {
                return new HistoryItem(rs.getLong("id"),
                                       formatTimestampToSeconds(rs.getTimestamp("timestamp")),
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
                    getAnyActionSelSt(editorUserId, lowerLimit, upperLimit, SELECT_CHILD_TABLES_OF_INTERVAL)
                            .executeQuery();
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
            selectSt.setLong(1, editorUserId);
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
