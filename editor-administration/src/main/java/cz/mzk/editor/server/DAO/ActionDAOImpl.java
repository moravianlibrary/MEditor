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
import cz.mzk.editor.shared.rpc.EditorDate;
import cz.mzk.editor.shared.rpc.HistoryItem;

/**
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public class ActionDAOImpl
        extends AbstractDAO
        implements ActionDAO {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ActionDAOImpl.class);

    public static final String SELECT_USER_ACTIONS_TIMESTAMP = "SELECT timestamp FROM "
            + Constants.TABLE_ACTION + " WHERE editor_user_id = (?)";

    public static final String SELECT_CHILD_TABLES_OF_INTERVAL =
            "SELECT p.relname AS tableName FROM "
                    + Constants.TABLE_ACTION
                    + " a, pg_class p WHERE editor_user_id = (?) AND a.timestamp > (?) AND a.timestamp < (timestamp (?) + INTERVAL '1 day') AND a.tableoid = p.oid GROUP BY p.relname";

    public static final SimpleDateFormat FORMATTER_TO_DAY = new SimpleDateFormat("dd");
    public static final SimpleDateFormat FORMATTER_TO_MOUNTH = new SimpleDateFormat("MM");
    public static final SimpleDateFormat FORMATTER_TO_YEAR = new SimpleDateFormat("yyyy");

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

    public List<HistoryItem> getHistoryItems(Long editorUserId, EditorDate lowerLimit, EditorDate upperLimit)
            throws DatabaseException {

        List<String> tableNames = getTableNames(editorUserId, lowerLimit, upperLimit);

    }

    private List<String> getTableNames(Long editorUserId, EditorDate lowerLimit, EditorDate upperLimit)
            throws DatabaseException {

        PreparedStatement selectSt = null;
        List<String> tableNames = new ArrayList<String>();

        try {
            selectSt = getConnection().prepareStatement(SELECT_CHILD_TABLES_OF_INTERVAL);
            selectSt.setLong(1, editorUserId);
            selectSt.setString(2, getSqlTimestamp(lowerLimit));
            selectSt.setString(3, getSqlTimestamp(upperLimit));

            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                tableNames.add(rs.getString("tableName"));
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return tableNames;
    }

    private String getSqlTimestamp(EditorDate date) {
        return date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
    }
}
