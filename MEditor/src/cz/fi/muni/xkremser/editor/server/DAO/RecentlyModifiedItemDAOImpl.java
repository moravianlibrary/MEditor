package cz.fi.muni.xkremser.editor.server.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;

public class RecentlyModifiedItemDAOImpl extends AbstractDAO implements RecentlyModifiedItemDAO {

	public static final String SELECT_LAST_N_STATEMENT = "SELECT uuid, name, description, model FROM " + Constants.TABLE_RECENTLY_MODIFIED_NAME
			+ " ORDER BY modified DESC LIMIT (?)";
	public static final String INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_RECENTLY_MODIFIED_NAME
			+ " (uuid, name, description, model, modified) VALUES ((?),(?),(?),(?),(CURRENT_TIMESTAMP))";

	public static final String FIND_ITEM_STATEMENT = "SELECT id FROM " + Constants.TABLE_INPUT_QUEUE_NAME + " WHERE uuid = (?)";
	public static final String UPDATE_ITEM_STATEMENT = "UPDATE " + Constants.TABLE_INPUT_QUEUE_NAME + "SET modified = CURRENT_TIMESTAMP WHERE id = (?)";

	@Inject
	private EditorConfiguration conf;

	@Inject
	public Log logger = null;

	@Override
	public boolean put(RecentlyModifiedItem toPut) {
		if (toPut == null)
			throw new NullPointerException("toPut");
		if (toPut.getUuid() == null || "".equals(toPut.getUuid()))
			throw new NullPointerException("toPut.getUuid()");

		boolean returnVal = true;
		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			logger.warn("Unable to set autocommit off", e);
		}
		try {

			PreparedStatement findSt = getConnection().prepareStatement(FIND_ITEM_STATEMENT);
			// PreparedStatement selectCount =
			// getConnection().prepareStatement(SELECT_NUMBER_ITEMS_STATEMENT);

			ResultSet rs = findSt.executeQuery();
			rs.next();
			int id = rs.getInt(1); // TODO: testnout!!

			// TX start
			int modified = 0;
			if (id != -1) { // is allready in DB
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
				returnVal = false;
			}
			// TX end

		} catch (SQLException e) {
			logger.error(e);
			returnVal = false;
		} finally {
			closeConnection();
		}
		return returnVal;
	}

	@Override
	public ArrayList<RecentlyModifiedItem> getItems(int nLatest) {
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
				retList.add(new RecentlyModifiedItem(rs.getString("uuid"), rs.getString("name"), rs.getString("description"),
						Constants.KrameriusModel.values()[modelId]));
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			closeConnection();
		}
		return retList;
	}
}
