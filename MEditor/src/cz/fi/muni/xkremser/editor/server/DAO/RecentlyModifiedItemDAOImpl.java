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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.KrameriusModel;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;

// TODO: Auto-generated Javadoc
/**
 * The Class RecentlyModifiedItemDAOImpl.
 */
public class RecentlyModifiedItemDAOImpl extends AbstractDAO implements RecentlyModifiedItemDAO {

	/** The Constant SELECT_LAST_N_STATEMENT. */
	public static final String SELECT_LAST_N_STATEMENT = "SELECT * FROM ( SELECT DISTINCT ON (uuid) uuid, name, description, model, modified FROM "
			+ Constants.TABLE_RECENTLY_MODIFIED_NAME + " LIMIT (?)) foo ORDER by modified DESC";

	/** The Constant SELECT_LAST_N_STATEMENT_FOR_USER. */
	public static final String SELECT_LAST_N_STATEMENT_FOR_USER = "SELECT uuid, name, description, model FROM " + Constants.TABLE_RECENTLY_MODIFIED_NAME
			+ " WHERE user_id IN (SELECT user_id FROM " + Constants.TABLE_OPEN_ID_IDENTITY + " WHERE identity = (?)) ORDER BY modified DESC LIMIT (?)";

	/** The Constant INSERT_ITEM_STATEMENT. */
	public static final String INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_RECENTLY_MODIFIED_NAME
			+ " (uuid, name, description, model, user_id, modified) VALUES ((?),(?),(?),(?),(SELECT user_id FROM " + Constants.TABLE_OPEN_ID_IDENTITY
			+ " WHERE identity = (?)),(CURRENT_TIMESTAMP))";

	/** The Constant FIND_ITEM_STATEMENT. */
	public static final String FIND_ITEM_STATEMENT = "SELECT id FROM " + Constants.TABLE_RECENTLY_MODIFIED_NAME
			+ " WHERE uuid = (?) AND user_id IN (SELECT user_id FROM " + Constants.TABLE_OPEN_ID_IDENTITY + " WHERE identity = (?))";

	/** The Constant INSERT_COMMON_DESCRIPTION_STATEMENT. */
	public static final String INSERT_COMMON_DESCRIPTION_STATEMENT = "INSERT INTO " + Constants.TABLE_DESCRIPTION + " (description, uuid) VALUES ((?),(?))";

	/** The Constant UPDATE_COMMON_DESCRIPTION_STATEMENT. */
	public static final String UPDATE_COMMON_DESCRIPTION_STATEMENT = "UPDATE " + Constants.TABLE_DESCRIPTION + " SET description = (?) WHERE uuid = (?)";

	/** The Constant SELECT_COMMON_DESCRIPTION_STATEMENT. */
	public static final String SELECT_COMMON_DESCRIPTION_STATEMENT = "SELECT description FROM " + Constants.TABLE_DESCRIPTION + " WHERE uuid = (?)";

	/** The Constant UPDATE_USER_DESCRIPTION_STATEMENT. */
	public static final String UPDATE_USER_DESCRIPTION_STATEMENT = "UPDATE " + Constants.TABLE_RECENTLY_MODIFIED_NAME
			+ " SET description = (?) WHERE uuid = (?) AND user_id IN (SELECT user_id FROM " + Constants.TABLE_OPEN_ID_IDENTITY + " WHERE identity = (?))";

	/** The Constant SELECT_USER_DESCRIPTION_STATEMENT. */
	public static final String SELECT_USER_DESCRIPTION_STATEMENT = "SELECT description FROM " + Constants.TABLE_RECENTLY_MODIFIED_NAME
			+ " WHERE uuid = (?) AND user_id IN (SELECT user_id FROM " + Constants.TABLE_OPEN_ID_IDENTITY + " WHERE identity = (?))";

	/** The Constant UPDATE_ITEM_STATEMENT. */
	public static final String UPDATE_ITEM_STATEMENT = "UPDATE " + Constants.TABLE_RECENTLY_MODIFIED_NAME + " SET modified = CURRENT_TIMESTAMP WHERE id = (?)";

	/** The conf. */
	@Inject
	private EditorConfiguration conf;

	/** The logger. */
	@Inject
	public Log logger = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.server.DAO.RecentlyModifiedItemDAO#put(cz.fi
	 * .muni.xkremser.editor.shared.rpc.RecentlyModifiedItem)
	 */
	@Override
	public boolean put(RecentlyModifiedItem toPut, String openID) {
		if (toPut == null)
			throw new NullPointerException("toPut");
		if (toPut.getUuid() == null || "".equals(toPut.getUuid()))
			throw new NullPointerException("toPut.getUuid()");

		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			logger.warn("Unable to set autocommit off", e);
		}
		boolean found = true;
		try {

			PreparedStatement findSt = getConnection().prepareStatement(FIND_ITEM_STATEMENT);
			findSt.setString(1, toPut.getUuid());
			findSt.setString(2, openID);
			ResultSet rs = findSt.executeQuery();
			found = rs.next();

			// TX start
			int modified = 0;
			if (found) { // is allready in DB
				int id = rs.getInt(1);
				PreparedStatement updSt = getConnection().prepareStatement(UPDATE_ITEM_STATEMENT);
				updSt.setInt(1, id);
				modified = updSt.executeUpdate();
			} else {
				PreparedStatement insSt = getConnection().prepareStatement(INSERT_ITEM_STATEMENT);
				insSt.setString(1, toPut.getUuid());
				insSt.setString(2, toPut.getName() == null ? "" : toPut.getName());
				insSt.setString(3, toPut.getDescription() == null ? "" : toPut.getDescription());
				insSt.setInt(4, toPut.getModel().ordinal()); // TODO: unknown model
				insSt.setString(5, openID);
				modified = insSt.executeUpdate();
			}
			if (modified == 1) {
				getConnection().commit();
				logger.debug("DB has been updated. -> commit");
			} else {
				getConnection().rollback();
				logger.debug("DB has not been updated. -> rollback");
				found = false;
			}
			// TX end

		} catch (SQLException e) {
			logger.error(e);
			found = false;
		} finally {
			closeConnection();
		}
		return found;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.server.DAO.RecentlyModifiedItemDAO#getItems(int,
	 * boolean)
	 */
	@Override
	public ArrayList<RecentlyModifiedItem> getItems(int nLatest, String openID) {
		PreparedStatement selectSt = null;
		ArrayList<RecentlyModifiedItem> retList = new ArrayList<RecentlyModifiedItem>();
		try {
			if (openID != null) {
				selectSt = getConnection().prepareStatement(SELECT_LAST_N_STATEMENT_FOR_USER);
				selectSt.setString(1, openID);
				selectSt.setInt(2, nLatest);
			} else {
				selectSt = getConnection().prepareStatement(SELECT_LAST_N_STATEMENT);
				selectSt.setInt(1, nLatest);
			}
		} catch (SQLException e) {
			logger.error("Could not get select items statement", e);
		}
		try {
			ResultSet rs = selectSt.executeQuery();
			while (rs.next()) {
				int modelId = rs.getInt("model");
				retList.add(new RecentlyModifiedItem(rs.getString("uuid"), rs.getString("name"), openID != null ? rs.getString("description") : "", KrameriusModel
						.values()[modelId]));
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			closeConnection();
		}
		return retList;
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.server.DAO.RecentlyModifiedItemDAO#putDescription(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean putDescription(String uuid, String description) {
		if (uuid == null)
			throw new NullPointerException("uuid");
		if (description == null)
			throw new NullPointerException("description");

		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			logger.warn("Unable to set autocommit off", e);
		}
		boolean found = true;
		try {

			PreparedStatement findSt = getConnection().prepareStatement(SELECT_COMMON_DESCRIPTION_STATEMENT);
			findSt.setString(1, uuid);
			ResultSet rs = findSt.executeQuery();
			found = rs.next();
			// TX start
			int modified = 0;
			PreparedStatement updSt = getConnection().prepareStatement(found ? UPDATE_COMMON_DESCRIPTION_STATEMENT : INSERT_COMMON_DESCRIPTION_STATEMENT);
			updSt.setString(1, description);
			updSt.setString(2, uuid);
			modified = updSt.executeUpdate();
			if (modified == 1) {
				getConnection().commit();
				logger.debug("DB has been updated. -> commit");
			} else {
				getConnection().rollback();
				logger.debug("DB has not been updated. -> rollback");
				found = false;
			}
			// TX end
		} catch (SQLException e) {
			logger.error(e);
			found = false;
		} finally {
			closeConnection();
		}
		return found;
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.server.DAO.RecentlyModifiedItemDAO#getDescription(java.lang.String)
	 */
	@Override
	public String getDescription(String uuid) {
		if (uuid == null)
			throw new NullPointerException("uuid");
		String description = null;
		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			logger.warn("Unable to set autocommit off", e);
		}
		try {
			PreparedStatement findSt = getConnection().prepareStatement(SELECT_COMMON_DESCRIPTION_STATEMENT);
			findSt.setString(1, uuid);
			ResultSet rs = findSt.executeQuery();

			while (rs.next()) {
				description = rs.getString("description");
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			closeConnection();
		}
		return description;
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.server.DAO.RecentlyModifiedItemDAO#putUserDescription(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean putUserDescription(String openID, String uuid, String description) {
		if (uuid == null)
			throw new NullPointerException("uuid");
		if (description == null)
			throw new NullPointerException("description");

		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			logger.warn("Unable to set autocommit off", e);
		}
		boolean found = true;
		try {
			// TX start
			int modified = 0;
			PreparedStatement updSt = getConnection().prepareStatement(UPDATE_USER_DESCRIPTION_STATEMENT);
			updSt.setString(1, description);
			updSt.setString(2, uuid);
			updSt.setString(3, openID);
			modified = updSt.executeUpdate();

			if (modified == 1) {
				getConnection().commit();
				logger.debug("DB has been updated. -> commit");
			} else {
				getConnection().rollback();
				logger.debug("DB has not been updated. -> rollback");
				found = false;
			}
			// TX end
		} catch (SQLException e) {
			logger.error(e);
			found = false;
		} finally {
			closeConnection();
		}
		return found;
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.server.DAO.RecentlyModifiedItemDAO#getUserDescription(java.lang.String, java.lang.String)
	 */
	@Override
	public String getUserDescription(String openID, String uuid) {
		if (uuid == null)
			throw new NullPointerException("uuid");
		String description = null;
		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			logger.warn("Unable to set autocommit off", e);
		}
		try {
			PreparedStatement findSt = getConnection().prepareStatement(SELECT_USER_DESCRIPTION_STATEMENT);
			findSt.setString(1, uuid);
			findSt.setString(2, openID);
			ResultSet rs = findSt.executeQuery();

			while (rs.next()) {
				description = rs.getString("description");
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			closeConnection();
		}
		return description;
	}
}
