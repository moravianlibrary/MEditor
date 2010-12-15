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

	public static final String SELECT_ROLES_STATEMENT = "SELECT name FROM " + Constants.TABLE_ROLE + " ORDER BY name";

	public static final String SELECT_ROLES_OF_USER_STATEMENT = "SELECT id, name, description FROM " + Constants.TABLE_ROLE + " WHERE id IN ( SELECT id FROM "
			+ Constants.TABLE_USER_IN_ROLE + " WHERE user_id = (?) )";

	public static final String SELECT_IDENTITIES_STATEMENT = "SELECT id, identity FROM " + Constants.TABLE_OPEN_ID_IDENTITY + " WHERE user_id = (?)";

	public static final String DELETE_ALL_USER_ROLES = "DELETE FROM " + Constants.TABLE_USER_IN_ROLE + " WHERE user_id = (?)";

	public static final String DELETE_ALL_USER_IDENTITIES = "DELETE FROM " + Constants.TABLE_OPEN_ID_IDENTITY + " WHERE user_id = (?)";

	public static final String DELETE_USER = "DELETE FROM " + Constants.TABLE_EDITOR_USER + " WHERE id = (?)";

	public static final String DELETE_IDENTITY = "DELETE FROM " + Constants.TABLE_OPEN_ID_IDENTITY + " WHERE id = (?)";

	public static final String DELETE_USER_IN_ROLE = "DELETE FROM " + Constants.TABLE_USER_IN_ROLE + " WHERE id = (?)";

	public static final String SELECT_ROLE_DESCRIPTION = "SELECT description FROM " + Constants.TABLE_ROLE + " WHERE name = (?)";

	public static final String SELECT_NAME_BY_OPENID = "SELECT name, surname FROM " + Constants.TABLE_EDITOR_USER + " WHERE id IN (SELECT user_id FROM "
			+ Constants.TABLE_OPEN_ID_IDENTITY + " WHERE identity = (?))";

	public static final String SELECT_USER_ID_BY_OPENID = "SELECT id FROM " + Constants.TABLE_EDITOR_USER + " WHERE id IN (SELECT user_id FROM "
			+ Constants.TABLE_OPEN_ID_IDENTITY + " WHERE identity = (?))";

	/** The Constant INSERT_ITEM_STATEMENT. */
	public static final String INSERT_USER_STATEMENT = "INSERT INTO " + Constants.TABLE_EDITOR_USER + " (name, surname, sex) VALUES ((?),(?),(?))";

	public static final String INSERT_IDENTITY_STATEMENT = "INSERT INTO " + Constants.TABLE_OPEN_ID_IDENTITY + " (user_id, identity) VALUES ((?),(?))";

	public static final String INSERT_USER_IN_ROLE_STATEMENT = "INSERT INTO " + Constants.TABLE_USER_IN_ROLE
			+ " (user_id, role_id, date) VALUES ((?),(SELECT id FROM " + Constants.TABLE_ROLE + " WHERE name = (?)),(CURRENT_TIMESTAMP))";

	public static final String USER_CURR_VALUE = "SELECT currval('" + Constants.SEQUENCE_EDITOR_USER + "')";

	public static final String USER_IDENTITY_VALUE = "SELECT currval('" + Constants.SEQUENCE_OPEN_ID_IDENTITY + "')";

	public static final String USER_ROLE_VALUE = "SELECT currval('" + Constants.SEQUENCE_ROLE + "')";

	/** The Constant UPDATE_ITEM_STATEMENT. */
	public static final String UPDATE_USER_STATEMENT = "UPDATE " + Constants.TABLE_EDITOR_USER + " SET name = (?), surname = (?) WHERE id = (?)";

	@Override
	public int isSupported(String identifier) {

		PreparedStatement selectSt = null;
		long userId = -1;
		try {
			selectSt = getConnection().prepareStatement(SELECT_USER_ID_BY_OPENID);
			selectSt.setString(1, identifier);
		} catch (SQLException e) {
			logger.error("Could not get select statement", e);
		}
		try {
			ResultSet rs = selectSt.executeQuery();
			while (rs.next()) {
				userId = rs.getLong("id");
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			closeConnection();
		}
		if (userId != -1) {
			if (hasRole(UserDAO.ADMIN_STRING, userId)) {
				return UserDAO.ADMIN;
			} else {
				return UserDAO.USER;
			}
		} else {
			return UserDAO.NOT_PRESENT;
		}
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
	public String addUser(UserInfoItem user) {
		if (user == null)
			throw new NullPointerException("user");
		if (user.getSurname() == null || "".equals(user.getSurname()))
			throw new NullPointerException("user.getSurname()");

		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			logger.warn("Unable to set autocommit off", e);
		}
		String retID = "exist";
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
				PreparedStatement seqSt = getConnection().prepareStatement(USER_CURR_VALUE);
				ResultSet rs = seqSt.executeQuery();
				while (rs.next()) {
					retID = rs.getString(1);
				}
			}
			if (modified == 1) {
				getConnection().commit();
				logger.debug("DB has been updated. -> commit");
			} else {
				getConnection().rollback();
				logger.debug("DB has not been updated. -> rollback");
				retID = "error";
			}
			// TX end

		} catch (SQLException e) {
			logger.error(e);
			retID = "error";
		} finally {
			closeConnection();
		}
		return retID;
	}

	@Override
	public ArrayList<RoleItem> getRolesOfUser(long userId) {
		PreparedStatement selectSt = null;
		ArrayList<RoleItem> retList = new ArrayList<RoleItem>();
		try {
			selectSt = getConnection().prepareStatement(SELECT_ROLES_OF_USER_STATEMENT);
			selectSt.setLong(1, userId);
		} catch (SQLException e) {
			logger.error("Could not get select statement", e);
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
			logger.error("Could not get select statement", e);
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

	@Override
	public String addUserIdentity(OpenIDItem identity, long userId) {
		if (identity == null)
			throw new NullPointerException("identity");
		if (identity.getIdentity() == null || "".equals(identity.getIdentity()))
			throw new NullPointerException("identity.getIdentity()");

		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			logger.warn("Unable to set autocommit off", e);
		}
		String retID = "exist";
		try {
			// TX start
			int modified = 0;
			PreparedStatement insSt = getConnection().prepareStatement(INSERT_IDENTITY_STATEMENT);
			insSt.setLong(1, userId);
			insSt.setString(2, identity.getIdentity());
			modified = insSt.executeUpdate();
			PreparedStatement seqSt = getConnection().prepareStatement(USER_IDENTITY_VALUE);
			ResultSet rs = seqSt.executeQuery();
			while (rs.next()) {
				retID = rs.getString(1);
			}
			if (modified == 1) {
				getConnection().commit();
				logger.debug("DB has been updated. -> commit");
			} else {
				getConnection().rollback();
				logger.debug("DB has not been updated. -> rollback");
				retID = "error";
			}
			// TX end

		} catch (SQLException e) {
			logger.error(e);
			retID = "error";
		} finally {
			closeConnection();
		}
		return retID;
	}

	@Override
	public void removeUserIdentity(long id) {
		PreparedStatement deleteSt = null;
		try {
			deleteSt = getConnection().prepareStatement(DELETE_IDENTITY);
			deleteSt.setLong(1, id);
			deleteSt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Could not delete openID identity with id " + id, e);
		} finally {
			closeConnection();
		}
	}

	@Override
	public RoleItem addUserRole(RoleItem role, long userId) {

		if (role == null)
			throw new NullPointerException("role");
		if (role.getName() == null || "".equals(role.getName()))
			throw new NullPointerException("role.getName()");
		if (hasRole(role.getName(), userId)) {
			return new RoleItem(role.getName(), "", "exist");
		}
		RoleItem defaultRole = new RoleItem(role.getName(), "", "exist");
		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			logger.warn("Unable to set autocommit off", e);
		}
		String retID = "exist";
		String roleDesc = "";
		try {
			// TX start
			int modified = 0;
			PreparedStatement insSt = getConnection().prepareStatement(INSERT_USER_IN_ROLE_STATEMENT);
			insSt.setLong(1, userId);
			insSt.setString(2, role.getName());
			modified = insSt.executeUpdate();
			PreparedStatement seqSt = getConnection().prepareStatement(USER_ROLE_VALUE);
			ResultSet rs = seqSt.executeQuery();
			while (rs.next()) {
				retID = rs.getString(1);
			}
			PreparedStatement roleDescSt = getConnection().prepareStatement(SELECT_ROLE_DESCRIPTION);
			roleDescSt.setString(1, role.getName());
			ResultSet rs2 = roleDescSt.executeQuery();
			while (rs2.next()) {
				roleDesc = rs2.getString(1);
			}
			if (modified == 1) {
				getConnection().commit();
				logger.debug("DB has been updated. -> commit");
			} else {
				getConnection().rollback();
				logger.debug("DB has not been updated. -> rollback");
				retID = "error";
			}
			// TX end

		} catch (SQLException e) {
			logger.error(e);
			retID = "error";
		} finally {
			closeConnection();
		}
		defaultRole.setId(retID);
		defaultRole.setDescription(roleDesc);
		return defaultRole;
	}

	@Override
	public void removeUserRole(long id) {
		PreparedStatement deleteSt = null;
		try {
			deleteSt = getConnection().prepareStatement(DELETE_USER_IN_ROLE);
			deleteSt.setLong(1, id);
			deleteSt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Could not delete user_in_role item with id " + id, e);
		} finally {
			closeConnection();
		}
	}

	@Override
	public boolean hasRole(String role, long userId) {
		ArrayList<RoleItem> roles = getRolesOfUser(userId);
		if (role == null)
			return false;
		for (RoleItem candidateRole : roles) {
			if (candidateRole != null && candidateRole.getName() != null && candidateRole.getName().equals(role)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ArrayList<String> getRoles() {
		PreparedStatement selectSt = null;
		ArrayList<String> retList = new ArrayList<String>();
		try {
			selectSt = getConnection().prepareStatement(SELECT_ROLES_STATEMENT);
		} catch (SQLException e) {
			logger.error("Could not get select roles statement", e);
		}
		try {
			ResultSet rs = selectSt.executeQuery();
			while (rs.next()) {
				retList.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			closeConnection();
		}
		return retList;
	}

	@Override
	public String getName(String openID) {
		PreparedStatement selectSt = null;
		String name = "unknown";
		try {
			selectSt = getConnection().prepareStatement(SELECT_NAME_BY_OPENID);
			selectSt.setString(1, openID);
		} catch (SQLException e) {
			logger.error("Could not get select statement", e);
		}
		try {
			ResultSet rs = selectSt.executeQuery();
			while (rs.next()) {
				name = rs.getString("name") + " " + rs.getString("surname");
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			closeConnection();
		}
		return name;
	}
}
