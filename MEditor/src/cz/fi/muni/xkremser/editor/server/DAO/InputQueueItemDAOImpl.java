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

import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;
import cz.fi.muni.xkremser.editor.shared.rpc.InputQueueItem;

// TODO: Auto-generated Javadoc
/**
 * The Class InputQueueItemDAOImpl.
 */
public class InputQueueItemDAOImpl extends AbstractDAO implements InputQueueItemDAO {

	/** The Constant DELETE_ALL_ITEMS_STATEMENT. */
	public static final String DELETE_ALL_ITEMS_STATEMENT = "DELETE FROM " + Constants.TABLE_INPUT_QUEUE_NAME;

	/** The Constant SELECT_NUMBER_ITEMS_STATEMENT. */
	public static final String SELECT_NUMBER_ITEMS_STATEMENT = "SELECT count(id) FROM " + Constants.TABLE_INPUT_QUEUE_NAME;

	/** The Constant INSERT_ITEM_STATEMENT. */
	public static final String INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_INPUT_QUEUE_NAME + " (path, issn, name) VALUES ((?),(?),(?))";

	/** The Constant FIND_ITEMS_ON_TOP_LVL_STATEMENT. */
	public static final String FIND_ITEMS_ON_TOP_LVL_STATEMENT = "SELECT path, issn, name FROM " + Constants.TABLE_INPUT_QUEUE_NAME + " WHERE position('"
			+ File.separator + "' IN trim(leading ((?)) FROM path)) = 0";

	/** The Constant FIND_ITEMS_BY_PATH_STATEMENT. */
	public static final String FIND_ITEMS_BY_PATH_STATEMENT = FIND_ITEMS_ON_TOP_LVL_STATEMENT + " AND path LIKE ((?))";

	private static final Logger LOGGER = Logger.getLogger(InputQueueItemDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO#updateItems(java
	 * .util.List)
	 */
	@Override
	public void updateItems(List<InputQueueItem> toUpdate) throws DatabaseException {
		if (toUpdate == null)
			throw new NullPointerException("toUpdate");

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
				LOGGER.debug("DB has been updated. Queries: \"" + selectCount + "\" and \"" + deleteSt + "\".");
			} else {
				getConnection().rollback();
				LOGGER.error("DB has not been updated -> rollback! Queries: \"" + selectCount + "\" and \"" + deleteSt + "\".");
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
	 *          the item
	 * @return the item insert statement
	 * @throws DatabaseException
	 */
	private PreparedStatement getItemInsertStatement(InputQueueItem item) throws DatabaseException {
		PreparedStatement itemStmt = null;
		try {
			itemStmt = getConnection().prepareStatement(INSERT_ITEM_STATEMENT);
			itemStmt.setString(1, item.getPath());
			itemStmt.setString(2, item.getIssn());
			itemStmt.setString(3, item.getName());
		} catch (SQLException ex) {
			LOGGER.error("Could not get insert item statement " + itemStmt, ex);
		}
		return itemStmt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO#getItems(java.lang
	 * .String)
	 */
	@Override
	public ArrayList<InputQueueItem> getItems(String prefix) throws DatabaseException {
		boolean top = (prefix == null || "".equals(prefix));
		PreparedStatement findSt = null;
		ArrayList<InputQueueItem> retList = new ArrayList<InputQueueItem>();
		try {
			findSt = getConnection().prepareStatement(top ? FIND_ITEMS_ON_TOP_LVL_STATEMENT : FIND_ITEMS_BY_PATH_STATEMENT);
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
				retList.add(new InputQueueItem(rs.getString("path"), rs.getString("issn"), rs.getString("name")));
			}
		} catch (SQLException e) {
			LOGGER.error("Query: " + findSt, e);
		} finally {
			closeConnection();
		}
		return retList;
	}
}
