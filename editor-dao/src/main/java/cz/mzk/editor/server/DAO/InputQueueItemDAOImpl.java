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

import java.io.File;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.rpc.IngestInfo;
import cz.mzk.editor.shared.rpc.InputQueueItem;

// TODO: Auto-generated Javadoc
/**
 * The Class InputQueueItemDAOImpl.
 */
public class InputQueueItemDAOImpl
        extends AbstractDAO
        implements InputQueueItemDAO {

    //    input_queue_item (id, path, barcode, ingested) -> input_queue_item (path, barcode, ingested)
    //                                                                        path, barcode, ingested

    //    input_queue_item_name (id, path, name) -> input_queue (directory_path, name)
    //                                                                     path, name

    /** The Constant DELETE_ALL_ITEMS_STATEMENT. */
    public static final String DELETE_ALL_ITEMS_STATEMENT = "DELETE FROM " + Constants.TABLE_INPUT_QUEUE_ITEM;

    /** The Constant SELECT_NUMBER_ITEMS_STATEMENT. */
    public static final String SELECT_NUMBER_ITEMS_STATEMENT = "SELECT count(path) FROM "
            + Constants.TABLE_INPUT_QUEUE_ITEM;

    /** The Constant FIND_ITEMS_ON_TOP_LVL_STATEMENT. */
    public static final String FIND_ITEMS_ON_TOP_LVL_STATEMENT =
            "SELECT p.path, p.barcode, p.ingested, n.name FROM " + Constants.TABLE_INPUT_QUEUE_ITEM
                    + " p LEFT JOIN " + Constants.TABLE_INPUT_QUEUE + " n ON(p.path=n.directory_path) "
                    + "WHERE position('" + File.separator + "' IN trim(leading ((?)) FROM p.path)) = 0";

    /** The Constant FIND_ITEMS_ON_TOP_LVL_STATEMENT_ORDERED. */
    public static final String FIND_ITEMS_ON_TOP_LVL_STATEMENT_ORDERED = FIND_ITEMS_ON_TOP_LVL_STATEMENT
            + " ORDER BY p.path";

    /** The Constant FIND_ITEMS_BY_PATH_STATEMENT. */
    public static final String FIND_ITEMS_BY_PATH_STATEMENT = FIND_ITEMS_ON_TOP_LVL_STATEMENT
            + " AND p.path LIKE ((?)) ORDER BY p.path";

    /** The Constant INSERT_NAME. */
    //    public static final String INSERT_NAME = "INSERT INTO " + Constants.TABLE_INPUT_QUEUE
    //            + " (name, directory_path) VALUES ((?),(?))";
    //
    //    /** The Constant UPDATE_NAME. */
    //    public static final String UPDATE_NAME = "UPDATE " + Constants.TABLE_INPUT_QUEUE
    //            + " SET name = (?) WHERE directory_path = (?)";

    /** The Constant SELECT_INGEST_INFO. */
    public static final String SELECT_INGEST_INFO =
            "SELECT eu.surname || ', ' || eu.name as full_name, io.top_digital_object_uuid, io.timestamp FROM ((SELECT top_digital_object_uuid, timestamp, editor_user_id FROM "
                    + Constants.TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT
                    + " WHERE type = 'c' AND top_digital_object_uuid = digital_object_uuid) a INNER JOIN (SELECT uuid, input_queue_directory_path FROM "
                    + Constants.TABLE_DIGITAL_OBJECT
                    + " WHERE input_queue_directory_path = (?)) o ON a.top_digital_object_uuid = o.uuid) io LEFT JOIN "
                    + Constants.TABLE_EDITOR_USER + " eu ON io.editor_user_id = eu.id";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(InputQueueItemDAOImpl.class);

    /** The dao utils. */
    @Inject
    private DAOUtils daoUtils;

    /**
     * Update items.
     * 
     * @param toUpdate
     *        the to update
     * @throws DatabaseException
     *         the database exception {@inheritDoc}
     */
    @Override
    public void updateItems(List<InputQueueItem> toUpdate) throws DatabaseException {
        if (toUpdate == null) throw new NullPointerException("toUpdate");

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }
        try {

            PreparedStatement deleteSt = getConnection().prepareStatement(DELETE_ALL_ITEMS_STATEMENT);
            PreparedStatement selectCount = getConnection().prepareStatement(SELECT_NUMBER_ITEMS_STATEMENT);

            ResultSet rs = selectCount.executeQuery();
            rs.next();
            int totalBefore = rs.getInt(1);
            // TX start
            int deleted = deleteSt.executeUpdate();
            int updated = 0;
            for (InputQueueItem item : toUpdate) {
                updated += getItemInsertStatement(item).executeUpdate();
            }
            if (totalBefore == deleted && updated == toUpdate.size()) {
                getConnection().commit();
                LOGGER.debug("DB has been updated. Queries: \"" + selectCount + "\" and \"" + deleteSt
                        + "\".");
            } else {
                getConnection().rollback();
                LOGGER.error("DB has not been updated -> rollback! Queries: \"" + selectCount + "\" and \""
                        + deleteSt + "\".");
            }
            // TX end
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            closeConnection();
        }

    }

    /**
     * Gets the item insert statement.
     * 
     * @param item
     *        the item
     * @return the item insert statement
     * @throws DatabaseException
     *         the database exception
     */
    private PreparedStatement getItemInsertStatement(InputQueueItem item) throws DatabaseException {
        PreparedStatement itemStmt = null;
        try {
            itemStmt = getConnection().prepareStatement(DAOUtils.INPUT_QUEUE_ITEM_INSERT_ITEM_STATEMENT);
            itemStmt.setString(1, item.getPath());
            itemStmt.setString(2, item.getBarcode());
            itemStmt.setBoolean(3, item.getIngestInfo());
        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + itemStmt, ex);
        }
        return itemStmt;
    }

    /**
     * Gets the items.
     * 
     * @param prefix
     *        the prefix
     * @return the items
     * @throws DatabaseException
     *         the database exception {@inheritDoc}
     */
    @Override
    public ArrayList<InputQueueItem> getItems(String prefix) throws DatabaseException {
        boolean top = (prefix == null || "".equals(prefix));
        PreparedStatement findSt = null;
        ArrayList<InputQueueItem> retList = new ArrayList<InputQueueItem>();
        try {
            findSt =
                    getConnection().prepareStatement(top ? FIND_ITEMS_ON_TOP_LVL_STATEMENT_ORDERED
                            : FIND_ITEMS_BY_PATH_STATEMENT);

            findSt.setString(1, prefix + '/');
            if (!top) {
                findSt.setString(2, '%' + prefix + "/%");
            }

            ResultSet rs = findSt.executeQuery();
            while (rs.next()) {
                retList.add(new InputQueueItem(rs.getString("path"), rs.getString("barcode"), rs
                        .getBoolean("ingested"), rs.getString("name")));
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + findSt, e);
        } finally {
            closeConnection();
        }
        return retList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasBeenIngested(String path) throws DatabaseException {
        List<IngestInfo> ingestInfo = getIngestInfo(path);
        return ingestInfo != null && !ingestInfo.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateName(String path, String name) throws DatabaseException {

        try {
            daoUtils.checkInputQueue(path, name, true);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IngestInfo> getIngestInfo(String path) throws DatabaseException {
        PreparedStatement selectSt = null;

        List<String> pid = new ArrayList<String>();
        List<String> username = new ArrayList<String>();
        List<String> time = new ArrayList<String>();
        int count = 0;

        try {
            selectSt = getConnection().prepareStatement(SELECT_INGEST_INFO);
            selectSt.setString(1, DAOUtilsImpl.directoryPathToRightFormat(path));
            ResultSet rs = selectSt.executeQuery();

            while (rs.next()) {
                pid.add(rs.getString("top_digital_object_uuid"));
                time.add(rs.getString("timestamp"));
                username.add(rs.getString("full_name"));
                count++;
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        List<IngestInfo> ingestInfo = new ArrayList<IngestInfo>(count);
        if (count > 0) ingestInfo.add(new IngestInfo(path, pid, username, time));
        return ingestInfo;
    }
}
