package cz.fi.muni.xkremser.editor.server.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.shared.rpc.OpenIDItem;
import cz.fi.muni.xkremser.editor.shared.rpc.RoleItem;
import cz.fi.muni.xkremser.editor.shared.rpc.UserInfoItem;

public class UserDAOImpl extends AbstractDAO implements UserDAO {

	/** The Constant SELECT_LAST_N_STATEMENT. */
	public static final String SELECT_USERS_STATEMENT = "SELECT id, name, surname, sex FROM " + Constants.TABLE_EDITOR_USER + " ORDER BY surname";

	public static final String SELECT_ROLES_STATEMENT = "SELECT id, name, description FROM " + Constants.TABLE_ROLE + " WHERE id IN ( SELECT id FROM "
			+ Constants.TABLE_USER_IN_ROLE + " WHERE user_id = (?) )";

	public static final String SELECT_IDENTITIES_STATEMENT = "SELECT id, identity FROM " + Constants.TABLE_OPEN_ID_IDENTITY + " WHERE user_id = (?)";

	public static final String DELETE_ALL_USER_ROLES = "DELETE FROM " + Constants.TABLE_USER_IN_ROLE + " WHERE user_id = (?)";

	public static final String DELETE_ALL_USER_IDENTITIES = "DELETE FROM " + Constants.TABLE_OPEN_ID_IDENTITY + " WHERE user_id = (?)";

	public static final String DELETE_USER = "DELETE FROM " + Constants.TABLE_EDITOR_USER + " WHERE id = (?)";

	/** The Constant INSERT_ITEM_STATEMENT. */
	public static final String INSERT_USER_STATEMENT = "INSERT INTO " + Constants.TABLE_EDITOR_USER + " (name, surname, sex) VALUES ((?),(?),(?))";

	/** The Constant UPDATE_ITEM_STATEMENT. */
	public static final String UPDATE_USER_STATEMENT = "UPDATE " + Constants.TABLE_EDITOR_USER + " SET name = (?), surname = (?) WHERE id = (?)";

	@Override
	public int isSupported(String identifier) {
		return 1;
	}

	@Override
	public void addUserIdentity(String identifier, String alternativeIdentifier) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<UserInfoItem> getUsers() {
		PreparedStatement selectSt = null;
		ArrayList<UserInfoItem> retList = new ArrayList<UserInfoItem>();
		try {
			selectSt = getConnection().prepareStatement(SELECT_USERS_STATEMENT);
		} catch (SQLException e) {
			logger.error("Could not get select users statement", e);
		}
		try {
			ResultSet rs = selectSt.executeQuery();
			while (rs.next()) {
				retList.add(new UserInfoItem(rs.getString("name"), rs.getString("surname"), rs.getBoolean("sex") ? "m" : "f", rs.getString("id")));
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			closeConnection();
		}
		return retList;
	}

	@Override
	public void removeUser(long id) {
		PreparedStatement deleteSt = null;
		try {
			deleteSt = getConnection().prepareStatement(DELETE_ALL_USER_IDENTITIES);
			deleteSt.setLong(1, id);
			deleteSt.executeUpdate();
			deleteSt = getConnection().prepareStatement(DELETE_ALL_USER_ROLES);
			deleteSt.setLong(1, id);
			deleteSt.executeUpdate();
			deleteSt = getConnection().prepareStatement(DELETE_USER);
			deleteSt.setLong(1, id);
			deleteSt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Could not delete user with id " + id, e);
		} finally {
			closeConnection();
		}
	}

	@Override
	public boolean addUser(UserInfoItem user) {
		if (user == null)
			throw new NullPointerException("user");
		if (user.getSurname() == null || "".equals(user.getSurname()))
			throw new NullPointerException("user.getSurname()");

		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			logger.warn("Unable to set autocommit off", e);
		}
		boolean found = true;
		try {
			// TX start
			int modified = 0;
			if (user.getId() != null) { // is allready in DB
				long id = Long.parseLong(user.getId());
				PreparedStatement updSt = getConnection().prepareStatement(UPDATE_USER_STATEMENT);
				updSt.setString(1, user.getName());
				updSt.setString(2, user.getSurname());
				updSt.setLong(3, id);
				modified = updSt.executeUpdate();
			} else {
				PreparedStatement insSt = getConnection().prepareStatement(INSERT_USER_STATEMENT);
				insSt.setString(1, user.getName());
				insSt.setString(2, user.getSurname());
				insSt.setBoolean(3, "m".equalsIgnoreCase(user.getSex()));
				modified = insSt.executeUpdate();
				found = false;
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

	@Override
	public ArrayList<RoleItem> getRoles(String id) {
		PreparedStatement selectSt = null;
		ArrayList<RoleItem> retList = new ArrayList<RoleItem>();
		try {
			selectSt = getConnection().prepareStatement(SELECT_ROLES_STATEMENT);
			selectSt.setLong(1, Long.parseLong(id));
		} catch (SQLException e) {
			logger.error("Could not get select users statement", e);
		}
		try {
			ResultSet rs = selectSt.executeQuery();
			while (rs.next()) {
				retList.add(new RoleItem(rs.getString("name"), rs.getString("description"), rs.getString("id")));
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			closeConnection();
		}
		return retList;
	}

	@Override
	public ArrayList<OpenIDItem> getIdentities(String id) {
		PreparedStatement selectSt = null;
		ArrayList<OpenIDItem> retList = new ArrayList<OpenIDItem>();
		try {
			selectSt = getConnection().prepareStatement(SELECT_IDENTITIES_STATEMENT);
			selectSt.setLong(1, Long.parseLong(id));
		} catch (SQLException e) {
			logger.error("Could not get select users statement", e);
		}
		try {
			ResultSet rs = selectSt.executeQuery();
			while (rs.next()) {
				retList.add(new OpenIDItem(rs.getString("identity"), rs.getString("id")));
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			closeConnection();
		}
		return retList;
	}

}
