/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.DAO;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
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

	/** The conf. */
	@Inject
	private EditorConfiguration conf;

	/** The logger. */
	@Inject
	public Log logger = null;

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO#updateItems(java.util.List)
	 */
	@Override
	public void updateItems(List<InputQueueItem> toUpdate) {
		if (toUpdate == null)
			throw new NullPointerException("toUpdate");

		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			logger.warn("Unable to set autocommit off", e);
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
				logger.debug("DB has been updated. -> commit");
			} else {
				getConnection().rollback();
				logger.debug("DB has not been updated. -> rollback");
			}
			// TX end
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			closeConnection();
		}

	}

	/**
	 * Gets the item insert statement.
	 *
	 * @param item the item
	 * @return the item insert statement
	 */
	private PreparedStatement getItemInsertStatement(InputQueueItem item) {
		PreparedStatement itemStmt = null;
		try {
			itemStmt = getConnection().prepareStatement(INSERT_ITEM_STATEMENT);
			itemStmt.setString(1, item.getPath());
			itemStmt.setString(2, item.getIssn());
			itemStmt.setString(3, item.getName());
		} catch (SQLException ex) {
			logger.error("Could not get insert item statement", ex);
		}
		return itemStmt;
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO#getItems(java.lang.String)
	 */
	@Override
	public ArrayList<InputQueueItem> getItems(String prefix) {
		boolean top = (prefix == null || "".equals(prefix));
		PreparedStatement findSt = null;
		ArrayList<InputQueueItem> retList = new ArrayList<InputQueueItem>();
		try {
			findSt = getConnection().prepareStatement(top ? FIND_ITEMS_ON_TOP_LVL_STATEMENT : FIND_ITEMS_BY_PATH_STATEMENT);
		} catch (SQLException e) {
			logger.error("Could not get find items statement", e);
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
			logger.error(e);
		} finally {
			closeConnection();
		}
		return retList;
	}
}
