/**
 * Metadata Editor
 * @author Jiri Kremser
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
	public static final String SELECT_LAST_N_STATEMENT = "SELECT uuid, name, description, model FROM " + Constants.TABLE_RECENTLY_MODIFIED_NAME
			+ " ORDER BY modified DESC LIMIT (?)";

	/** The Constant INSERT_ITEM_STATEMENT. */
	public static final String INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_RECENTLY_MODIFIED_NAME
			+ " (uuid, name, description, model, modified) VALUES ((?),(?),(?),(?),(CURRENT_TIMESTAMP))";

	public static final String UPDATE_DESCRIPTION_ITEM_STATEMENT = "UPDATE " + Constants.TABLE_RECENTLY_MODIFIED_NAME + " SET description = (?) WHERE uuid = (?)";

	/** The Constant FIND_ITEM_STATEMENT. */
	public static final String FIND_ITEM_STATEMENT = "SELECT id FROM " + Constants.TABLE_RECENTLY_MODIFIED_NAME + " WHERE uuid = (?)";

	public static final String SELECT_DESCRIPTION_STATEMENT = "SELECT description FROM " + Constants.TABLE_RECENTLY_MODIFIED_NAME + " WHERE uuid = (?)";

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
	public boolean put(RecentlyModifiedItem toPut) {
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
			// PreparedStatement selectCount =
			// getConnection().prepareStatement(SELECT_NUMBER_ITEMS_STATEMENT);

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
	public ArrayList<RecentlyModifiedItem> getItems(int nLatest, boolean isForAll) {
		PreparedStatement selectSt = null;
		ArrayList<RecentlyModifiedItem> retList = new ArrayList<RecentlyModifiedItem>();
		try {
			selectSt = getConnection().prepareStatement(SELECT_LAST_N_STATEMENT);
		} catch (SQLException e) {
			logger.error("Could not get select items statement", e);
		}
		try {
			selectSt.setInt(1, nLatest);
			ResultSet rs = selectSt.executeQuery();
			while (rs.next()) {
				int modelId = rs.getInt("model");
				retList.add(new RecentlyModifiedItem(rs.getString("uuid"), rs.getString("name"), rs.getString("description"), KrameriusModel.values()[modelId]));
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			closeConnection();
		}
		return retList;
	}

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
			// TX start
			int modified = 0;
			PreparedStatement updSt = getConnection().prepareStatement(UPDATE_DESCRIPTION_ITEM_STATEMENT);
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
			PreparedStatement findSt = getConnection().prepareStatement(SELECT_DESCRIPTION_STATEMENT);
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
}
