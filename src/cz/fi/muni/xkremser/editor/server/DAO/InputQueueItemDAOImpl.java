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

package cz.fi.muni.xkremser.editor.server.DAO;

import java.io.File;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.util.ClientUtils;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

import cz.fi.muni.xkremser.editor.shared.rpc.InputQueueItem;

// TODO: Auto-generated Javadoc
/**
 * The Class InputQueueItemDAOImpl.
 */
public class InputQueueItemDAOImpl
        extends AbstractDAO
        implements InputQueueItemDAO {

    /** The Constant DELETE_ALL_ITEMS_STATEMENT. */
    public static final String DELETE_ALL_ITEMS_STATEMENT = "DELETE FROM " + Constants.TABLE_INPUT_QUEUE_ITEM;

    /** The Constant SELECT_NUMBER_ITEMS_STATEMENT. */
    public static final String SELECT_NUMBER_ITEMS_STATEMENT = "SELECT count(id) FROM "
            + Constants.TABLE_INPUT_QUEUE_ITEM;

    /** The Constant INSERT_ITEM_STATEMENT. */
    public static final String INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_INPUT_QUEUE_ITEM
            + " (path, barcode, ingested) VALUES ((?),(?),(?))";

    /** The Constant FIND_ITEMS_ON_TOP_LVL_STATEMENT. */
    public static final String FIND_ITEMS_ON_TOP_LVL_STATEMENT =
            "SELECT p.path, p.barcode, p.ingested, n.name FROM " + Constants.TABLE_INPUT_QUEUE_ITEM
                    + " p LEFT JOIN " + Constants.TABLE_INPUT_QUEUE_ITEM_NAME + " n ON(p.path=n.path) "
                    + "WHERE position('" + File.separator + "' IN trim(leading ((?)) FROM p.path)) = 0";

    /** The Constant FIND_ITEMS_ON_TOP_LVL_STATEMENT_ORDERED. */
    public static final String FIND_ITEMS_ON_TOP_LVL_STATEMENT_ORDERED = FIND_ITEMS_ON_TOP_LVL_STATEMENT
            + " ORDER BY p.path";

    /** The Constant FIND_ITEMS_BY_PATH_STATEMENT. */
    public static final String FIND_ITEMS_BY_PATH_STATEMENT = FIND_ITEMS_ON_TOP_LVL_STATEMENT
            + " AND p.path LIKE ((?)) ORDER BY p.path";

    public static final String UPDATE_INGEST_INFO = "UPDATE " + Constants.TABLE_INPUT_QUEUE_ITEM
            + " SET ingested = (?) WHERE path = (?)";

    public static final String SELECT_NAME_ITEM_ID = "SELECT id FROM "
            + Constants.TABLE_INPUT_QUEUE_ITEM_NAME + " WHERE path=(?)";

    public static final String INSERT_NAME = "INSERT INTO " + Constants.TABLE_INPUT_QUEUE_ITEM_NAME
            + " (name, path) VALUES ((?),(?))";

    public static final String UPDATE_NAME = "UPDATE " + Constants.TABLE_INPUT_QUEUE_ITEM_NAME
            + " SET name = (?) WHERE path = (?)";

    private static final Logger LOGGER = Logger.getLogger(InputQueueItemDAOImpl.class);

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO#updateItems(java
     * .util.List)
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
     */
    private PreparedStatement getItemInsertStatement(InputQueueItem item) throws DatabaseException {
        PreparedStatement itemStmt = null;
        try {
            itemStmt = getConnection().prepareStatement(INSERT_ITEM_STATEMENT);
            itemStmt.setString(1, item.getPath());
            itemStmt.setString(2, item.getBarcode());
            itemStmt.setBoolean(3, item.getIngestInfo());
        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + itemStmt, ex);
        }
        return itemStmt;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO#getItems(java
     * .lang .String)
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
        } catch (SQLException e) {
            LOGGER.error("Could not get find items statement " + findSt, e);
        }
        try {
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
    public void updateIngestInfo(boolean ingested, String path) throws DatabaseException {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }
        try {

            PreparedStatement updateSt = getConnection().prepareStatement(UPDATE_INGEST_INFO);
            updateSt.setBoolean(1, ingested);
            updateSt.setString(2, path);
            int updated = updateSt.executeUpdate();
            getConnection().commit();

            if (updated == 1) {
                LOGGER.debug("DB has been updated. Queries: \"" + updateSt + "\".");
            } else {
                LOGGER.error("DB has been updated, with unexpected count of updated lines: " + updated
                        + ". Queries: \"" + updateSt + "\".");
            }
            // TX end
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public void updateName(String path, String name) throws DatabaseException {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }
        int duplicate = selectName(path);
        try {

            PreparedStatement updateSt =
                    getConnection().prepareStatement((duplicate < 0) ? INSERT_NAME : UPDATE_NAME);
            updateSt.setString(1, ClientUtils.trimLabel(name, Constants.MAX_LABEL_LENGTH));
            updateSt.setString(2, path);
            int updated = updateSt.executeUpdate();
            getConnection().commit();

            if (updated == 1) {
                LOGGER.debug("DB has been updated. Queries: \"" + updateSt + "\".");
            } else {
                LOGGER.error("DB has been updated, with unexpected count of updated lines: " + updated
                        + ". Queries: \"" + updateSt + "\".");
            }
            // TX end
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            closeConnection();
        }
    }

    private int selectName(String path) throws DatabaseException {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }
        PreparedStatement selectSt = null;
        int id = Integer.MIN_VALUE;
        try {

            selectSt = getConnection().prepareStatement(SELECT_NAME_ITEM_ID);
            selectSt.setString(1, path);
            ResultSet rs = selectSt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return id;
    }
}
