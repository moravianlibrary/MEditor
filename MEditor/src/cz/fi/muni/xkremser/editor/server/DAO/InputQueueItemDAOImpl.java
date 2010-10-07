package cz.fi.muni.xkremser.editor.server.DAO;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
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

public class InputQueueItemDAOImpl implements InputQueueItemDAO {

	public Connection conn = null;

	public static final String DELETE_ALL_ITEMS_STATEMENT = "DELETE FROM " + Constants.TABLE_INPUT_QUEUE_NAME;
	public static final String INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_INPUT_QUEUE_NAME + " (path, issn, name) VALUES ((?),(?),(?))";
	public static final String FIND_ITEMS_ON_TOP_LVL_STATEMENT = "SELECT path, issn, name FROM " + Constants.TABLE_INPUT_QUEUE_NAME + " WHERE position('"
			+ File.separator + "' IN trim(leading ((?)) FROM path)) = 0";
	public static final String FIND_ITEMS_BY_PATH_STATEMENT = FIND_ITEMS_ON_TOP_LVL_STATEMENT + " AND path LIKE ((?))";

	@Inject
	private EditorConfiguration conf;

	@Inject
	public Log logger = null;

	private void initConnection() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			logger.error("Could not find the driver", ex);
		}
		String host = conf.getDBHost();
		String port = conf.getDBPort();
		String login = conf.getDBLogin();
		String password = conf.getDBPassword();
		String name = conf.getDBName();
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + name, login, password);

		} catch (SQLException ex) {
			logger.error("Unable to connect to database at 'jdbc:postgresql://" + host + ":" + port + "/" + name + "'", ex);
		}

	}

	@Override
	public void updateItems(List<InputQueueItem> toUpdate) {
		if (toUpdate == null)
			throw new NullPointerException("toUpdate");
		if (conn == null) {
			initConnection();
		}
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			logger.warn("Unable to set autocommit off", e);
		}
		try {

			PreparedStatement deleteSt = conn.prepareStatement(DELETE_ALL_ITEMS_STATEMENT);
			// TX start
			int ret = deleteSt.executeUpdate();
			for (InputQueueItem item : toUpdate) {
				ret += getItemInsertStatement(item).executeUpdate();
			}
			if (ret == toUpdate.size()) {
				conn.commit();
			} else {
				conn.rollback();
			}
			// TX end
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				logger.error("Connection was not closed", ex);
			}
			conn = null;
		}

	}

	private PreparedStatement getItemInsertStatement(InputQueueItem item) {
		PreparedStatement itemStmt = null;
		try {
			itemStmt = conn.prepareStatement(INSERT_ITEM_STATEMENT);
			itemStmt.setString(1, item.getPath());
			itemStmt.setString(2, item.getIssn());
			itemStmt.setString(3, item.getName());
		} catch (SQLException ex) {
			logger.error("Could not get insert item statement", ex);
		}
		return itemStmt;
	}

	@Override
	public ArrayList<InputQueueItem> getItems(String prefix) {
		if (conn == null) {
			initConnection();
		}
		boolean top = (prefix == null || "".equals(prefix));
		PreparedStatement findSt = null;
		ArrayList<InputQueueItem> retList = new ArrayList<InputQueueItem>();
		try {
			findSt = conn.prepareStatement(top ? FIND_ITEMS_ON_TOP_LVL_STATEMENT : FIND_ITEMS_BY_PATH_STATEMENT);
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
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				logger.error("Connection was not closed", ex);
			}
			conn = null;
		}
		return retList;
	}
}
