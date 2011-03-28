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

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;
import cz.fi.muni.xkremser.editor.shared.rpc.OpenIDItem;
import cz.fi.muni.xkremser.editor.shared.rpc.RoleItem;
import cz.fi.muni.xkremser.editor.shared.rpc.UserInfoItem;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDAOImpl.
 */
public class UserDAOImpl extends AbstractDAO implements UserDAO {

	/** The Constant SELECT_LAST_N_STATEMENT. */
	public static final String SELECT_USERS_STATEMENT = "SELECT id, name, surname, sex FROM " + Constants.TABLE_EDITOR_USER + " ORDER BY surname";

	/** The Constant SELECT_ROLES_STATEMENT. */
	public static final String SELECT_ROLES_STATEMENT = "SELECT name FROM " + Constants.TABLE_ROLE + " ORDER BY name";

	/** The Constant SELECT_ROLE_BY_IDENTITY_STATEMENT. */
	public static final String SELECT_ROLE_BY_IDENTITY_STATEMENT = "SELECT id FROM " + Constants.TABLE_USER_IN_ROLE + " WHERE user_id IN (SELECT user_id FROM "
			+ Constants.TABLE_OPEN_ID_IDENTITY + " WHERE identity = (?)) AND role_id IN (SELECT id FROM " + Constants.TABLE_ROLE + " WHERE name = (?))";

	/** The Constant SELECT_ROLES_OF_USER_STATEMENT2. */
	public static final String SELECT_ROLES_OF_USER_STATEMENT2 = "SELECT id, name, description FROM " + Constants.TABLE_ROLE
			+ " WHERE id IN ( SELECT role_id FROM " + Constants.TABLE_USER_IN_ROLE + " WHERE user_id = (?) )";

	/** The Constant SELECT_ROLES_OF_USER_STATEMENT. */
	public static final String SELECT_ROLES_OF_USER_STATEMENT = "SELECT (SELECT id FROM " + Constants.TABLE_USER_IN_ROLE
			+ " WHERE user_id = (?) AND role_id = role.id) AS id, name, description FROM role WHERE id IN ( SELECT role_id FROM user_in_role WHERE user_id = (?))";

	/** The Constant SELECT_IDENTITIES_STATEMENT. */
	public static final String SELECT_IDENTITIES_STATEMENT = "SELECT id, identity FROM " + Constants.TABLE_OPEN_ID_IDENTITY + " WHERE user_id = (?)";

	/** The Constant DELETE_ALL_USER_ROLES. */
	public static final String DELETE_ALL_USER_ROLES = "DELETE FROM " + Constants.TABLE_USER_IN_ROLE + " WHERE user_id = (?)";

	/** The Constant DELETE_ALL_USER_IDENTITIES. */
	public static final String DELETE_ALL_USER_IDENTITIES = "DELETE FROM " + Constants.TABLE_OPEN_ID_IDENTITY + " WHERE user_id = (?)";

	/** The Constant DELETE_USER. */
	public static final String DELETE_USER = "DELETE FROM " + Constants.TABLE_EDITOR_USER + " WHERE id = (?)";

	/** The Constant DELETE_IDENTITY. */
	public static final String DELETE_IDENTITY = "DELETE FROM " + Constants.TABLE_OPEN_ID_IDENTITY + " WHERE id = (?)";

	/** The Constant DELETE_USER_IN_ROLE. */
	public static final String DELETE_USER_IN_ROLE = "DELETE FROM " + Constants.TABLE_USER_IN_ROLE + " WHERE id = (?)";

	/** The Constant SELECT_ROLE_DESCRIPTION. */
	public static final String SELECT_ROLE_DESCRIPTION = "SELECT description FROM " + Constants.TABLE_ROLE + " WHERE name = (?)";

	/** The Constant SELECT_NAME_BY_OPENID. */
	public static final String SELECT_NAME_BY_OPENID = "SELECT name, surname FROM " + Constants.TABLE_EDITOR_USER + " WHERE id IN (SELECT user_id FROM "
			+ Constants.TABLE_OPEN_ID_IDENTITY + " WHERE identity = (?))";

	/** The Constant SELECT_USER_ID_BY_OPENID. */
	public static final String SELECT_USER_ID_BY_OPENID = "SELECT id FROM " + Constants.TABLE_EDITOR_USER + " WHERE id IN (SELECT user_id FROM "
			+ Constants.TABLE_OPEN_ID_IDENTITY + " WHERE identity = (?))";

	/** The Constant INSERT_ITEM_STATEMENT. */
	public static final String INSERT_USER_STATEMENT = "INSERT INTO " + Constants.TABLE_EDITOR_USER + " (name, surname, sex) VALUES ((?),(?),(?))";

	/** The Constant INSERT_IDENTITY_STATEMENT. */
	public static final String INSERT_IDENTITY_STATEMENT = "INSERT INTO " + Constants.TABLE_OPEN_ID_IDENTITY + " (user_id, identity) VALUES ((?),(?))";

	/** The Constant INSERT_USER_IN_ROLE_STATEMENT. */
	public static final String INSERT_USER_IN_ROLE_STATEMENT = "INSERT INTO " + Constants.TABLE_USER_IN_ROLE
			+ " (user_id, role_id, date) VALUES ((?),(SELECT id FROM " + Constants.TABLE_ROLE + " WHERE name = (?)),(CURRENT_TIMESTAMP))";

	/** The Constant USER_CURR_VALUE. */
	public static final String USER_CURR_VALUE = "SELECT currval('" + Constants.SEQUENCE_EDITOR_USER + "')";

	/** The Constant USER_IDENTITY_VALUE. */
	public static final String USER_IDENTITY_VALUE = "SELECT currval('" + Constants.SEQUENCE_OPEN_ID_IDENTITY + "')";

	/** The Constant USER_ROLE_VALUE. */
	public static final String USER_ROLE_VALUE = "SELECT currval('" + Constants.SEQUENCE_ROLE + "')";

	/** The Constant UPDATE_ITEM_STATEMENT. */
	public static final String UPDATE_USER_STATEMENT = "UPDATE " + Constants.TABLE_EDITOR_USER + " SET name = (?), surname = (?) WHERE id = (?)";

	private static final Logger LOGGER = Logger.getLogger(RequestDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.server.DAO.UserDAO#isSupported(java.lang.String)
	 */
	@Override
	public int isSupported(String identifier) throws DatabaseException {

		PreparedStatement selectSt = null;
		long userId = -1;
		try {
			selectSt = getConnection().prepareStatement(SELECT_USER_ID_BY_OPENID);
			selectSt.setString(1, identifier);
		} catch (SQLException e) {
			LOGGER.error("Could not get select statement", e);
		}
		try {
			ResultSet rs = selectSt.executeQuery();
			while (rs.next()) {
				userId = rs.getLong("id");
			}
		} catch (SQLException e) {
			LOGGER.error("Query: " + selectSt, e);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.server.DAO.UserDAO#getUsers()
	 */
	@Override
	public ArrayList<UserInfoItem> getUsers() throws DatabaseException {
		PreparedStatement selectSt = null;
		ArrayList<UserInfoItem> retList = new ArrayList<UserInfoItem>();
		try {
			selectSt = getConnection().prepareStatement(SELECT_USERS_STATEMENT);
		} catch (SQLException e) {
			LOGGER.error("Could not get select users statement", e);
		}
		try {
			ResultSet rs = selectSt.executeQuery();
			while (rs.next()) {
				retList.add(new UserInfoItem(rs.getString("name"), rs.getString("surname"), rs.getBoolean("sex") ? "m" : "f", rs.getString("id")));
			}
		} catch (SQLException e) {
			LOGGER.error("Query: " + selectSt, e);
		} finally {
			closeConnection();
		}
		return retList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.server.DAO.UserDAO#removeUser(long)
	 */
	@Override
	public void removeUser(long id) throws DatabaseException {
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
			LOGGER.error("Could not delete user with id " + id + ". Query: " + deleteSt, e);
		} finally {
			closeConnection();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.server.DAO.UserDAO#addUser(cz.fi.muni.xkremser
	 * .editor.shared.rpc.UserInfoItem)
	 */
	@Override
	public String addUser(UserInfoItem user) throws DatabaseException {
		if (user == null)
			throw new NullPointerException("user");
		if (user.getSurname() == null || "".equals(user.getSurname()))
			throw new NullPointerException("user.getSurname()");

		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			LOGGER.warn("Unable to set autocommit off", e);
		}
		String retID = "exist";
		PreparedStatement insSt = null, updSt = null, seqSt = null;
		try {
			// TX start
			int modified = 0;
			if (user.getId() != null) { // is allready in DB
				long id = Long.parseLong(user.getId());
				updSt = getConnection().prepareStatement(UPDATE_USER_STATEMENT);
				updSt.setString(1, user.getName());
				updSt.setString(2, user.getSurname());
				updSt.setLong(3, id);
				modified = updSt.executeUpdate();
			} else {
				insSt = getConnection().prepareStatement(INSERT_USER_STATEMENT);
				insSt.setString(1, user.getName());
				insSt.setString(2, user.getSurname());
				insSt.setBoolean(3, "m".equalsIgnoreCase(user.getSex()));
				modified = insSt.executeUpdate();
				seqSt = getConnection().prepareStatement(USER_CURR_VALUE);
				ResultSet rs = seqSt.executeQuery();
				while (rs.next()) {
					retID = rs.getString(1);
				}
			}
			if (modified == 1) {
				getConnection().commit();
				LOGGER.debug("DB has been updated. Queries: \"" + seqSt + "\" and \"" + (user.getId() != null ? updSt : insSt) + "\"");
			} else {
				getConnection().rollback();
				LOGGER.debug("DB has not been updated. -> rollback! Queries: \"" + seqSt + "\" and \"" + (user.getId() != null ? updSt : insSt) + "\"");
				retID = "error";
			}
			// TX end

		} catch (SQLException e) {
			LOGGER.error(e);
			retID = "error";
		} finally {
			closeConnection();
		}
		return retID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.server.DAO.UserDAO#getRolesOfUser(long)
	 */
	@Override
	public ArrayList<RoleItem> getRolesOfUser(long userId) throws DatabaseException {
		PreparedStatement selectSt = null;
		ArrayList<RoleItem> retList = new ArrayList<RoleItem>();
		try {
			selectSt = getConnection().prepareStatement(SELECT_ROLES_OF_USER_STATEMENT);
			selectSt.setLong(1, userId);
			selectSt.setLong(2, userId);
		} catch (SQLException e) {
			LOGGER.error("Could not get select statement", e);
		}
		try {
			ResultSet rs = selectSt.executeQuery();
			while (rs.next()) {
				retList.add(new RoleItem(rs.getString("name"), rs.getString("description"), rs.getString("id")));
			}
		} catch (SQLException e) {
			LOGGER.error("Query: " + selectSt, e);
		} finally {
			closeConnection();
		}
		return retList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.server.DAO.UserDAO#getIdentities(java.lang.String
	 * )
	 */
	@Override
	public ArrayList<OpenIDItem> getIdentities(String id) throws DatabaseException {
		PreparedStatement selectSt = null;
		ArrayList<OpenIDItem> retList = new ArrayList<OpenIDItem>();
		try {
			selectSt = getConnection().prepareStatement(SELECT_IDENTITIES_STATEMENT);
			selectSt.setLong(1, Long.parseLong(id));
		} catch (SQLException e) {
			LOGGER.error("Could not get select statement", e);
		}
		try {
			ResultSet rs = selectSt.executeQuery();
			while (rs.next()) {
				retList.add(new OpenIDItem(rs.getString("identity"), rs.getString("id")));
			}
		} catch (SQLException e) {
			LOGGER.error("Query: " + selectSt, e);
		} finally {
			closeConnection();
		}
		return retList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.server.DAO.UserDAO#addUserIdentity(cz.fi.muni
	 * .xkremser.editor.shared.rpc.OpenIDItem, long)
	 */
	@Override
	public String addUserIdentity(OpenIDItem identity, long userId) throws DatabaseException {
		if (identity == null)
			throw new NullPointerException("identity");
		if (identity.getIdentity() == null || "".equals(identity.getIdentity()))
			throw new NullPointerException("identity.getIdentity()");

		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			LOGGER.warn("Unable to set autocommit off", e);
		}
		String retID = "exist";
		PreparedStatement insSt = null, seqSt = null;
		try {
			// TX start
			int modified = 0;
			insSt = getConnection().prepareStatement(INSERT_IDENTITY_STATEMENT);
			insSt.setLong(1, userId);
			insSt.setString(2, identity.getIdentity());
			modified = insSt.executeUpdate();
			seqSt = getConnection().prepareStatement(USER_IDENTITY_VALUE);
			ResultSet rs = seqSt.executeQuery();
			while (rs.next()) {
				retID = rs.getString(1);
			}
			if (modified == 1) {
				getConnection().commit();
				LOGGER.debug("DB has been updated. Queries: \"" + seqSt + "\" and \"" + insSt + "\"");
			} else {
				getConnection().rollback();
				LOGGER.debug("DB has not been updated -> rollback! Queries: \"" + seqSt + "\" and \"" + insSt + "\"");
				retID = "error";
			}
			// TX end

		} catch (SQLException e) {
			LOGGER.error(e);
			retID = "error";
		} finally {
			closeConnection();
		}
		return retID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.server.DAO.UserDAO#removeUserIdentity(long)
	 */
	@Override
	public void removeUserIdentity(long id) throws DatabaseException {
		PreparedStatement deleteSt = null;
		try {
			deleteSt = getConnection().prepareStatement(DELETE_IDENTITY);
			deleteSt.setLong(1, id);
			deleteSt.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Could not delete openID identity with id " + id + ". Query: " + deleteSt, e);
		} finally {
			closeConnection();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.server.DAO.UserDAO#addUserRole(cz.fi.muni.xkremser
	 * .editor.shared.rpc.RoleItem, long)
	 */
	@Override
	public RoleItem addUserRole(RoleItem role, long userId) throws DatabaseException {

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
			LOGGER.warn("Unable to set autocommit off", e);
		}
		String retID = "exist";
		String roleDesc = "";
		PreparedStatement seqSt = null, roleDescSt = null;
		try {
			// TX start
			int modified = 0;
			PreparedStatement insSt = getConnection().prepareStatement(INSERT_USER_IN_ROLE_STATEMENT);
			insSt.setLong(1, userId);
			insSt.setString(2, role.getName());
			modified = insSt.executeUpdate();
			seqSt = getConnection().prepareStatement(USER_ROLE_VALUE);
			ResultSet rs = seqSt.executeQuery();
			while (rs.next()) {
				retID = rs.getString(1);
			}
			roleDescSt = getConnection().prepareStatement(SELECT_ROLE_DESCRIPTION);
			roleDescSt.setString(1, role.getName());
			ResultSet rs2 = roleDescSt.executeQuery();
			while (rs2.next()) {
				roleDesc = rs2.getString(1);
			}
			if (modified == 1) {
				getConnection().commit();
				LOGGER.debug("DB has been updated. Queries: \"" + seqSt + "\" and \"" + roleDescSt + "\"");
			} else {
				getConnection().rollback();
				LOGGER.error("DB has not been updated -> rollback! Queries: \"" + seqSt + "\" and \"" + roleDescSt + "\"");
				retID = "error";
			}
			// TX end

		} catch (SQLException e) {
			LOGGER.error(e);
			retID = "error";
		} finally {
			closeConnection();
		}
		defaultRole.setId(retID);
		defaultRole.setDescription(roleDesc);
		return defaultRole;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.server.DAO.UserDAO#removeUserRole(long)
	 */
	@Override
	public void removeUserRole(long id) throws DatabaseException {
		PreparedStatement deleteSt = null;
		try {
			deleteSt = getConnection().prepareStatement(DELETE_USER_IN_ROLE);
			deleteSt.setLong(1, id);
			deleteSt.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Could not delete user_in_role item with id " + id + ". Query: " + deleteSt, e);
		} finally {
			closeConnection();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.server.DAO.UserDAO#hasRole(java.lang.String,
	 * long)
	 */
	@Override
	public boolean hasRole(String role, long userId) throws DatabaseException {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.server.DAO.UserDAO#getRoles()
	 */
	@Override
	public ArrayList<String> getRoles() throws DatabaseException {
		PreparedStatement selectSt = null;
		ArrayList<String> retList = new ArrayList<String>();
		try {
			selectSt = getConnection().prepareStatement(SELECT_ROLES_STATEMENT);
		} catch (SQLException e) {
			LOGGER.error("Could not get select roles statement", e);
		}
		try {
			ResultSet rs = selectSt.executeQuery();
			while (rs.next()) {
				retList.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			LOGGER.error("Query: " + selectSt, e);
		} finally {
			closeConnection();
		}
		return retList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.server.DAO.UserDAO#getName(java.lang.String)
	 */
	@Override
	public String getName(String openID) throws DatabaseException {
		PreparedStatement selectSt = null;
		String name = "unknown";
		try {
			selectSt = getConnection().prepareStatement(SELECT_NAME_BY_OPENID);
			selectSt.setString(1, openID);
		} catch (SQLException e) {
			LOGGER.error("Could not get select statement", e);
		}
		try {
			ResultSet rs = selectSt.executeQuery();
			while (rs.next()) {
				name = rs.getString("name") + " " + rs.getString("surname");
			}
		} catch (SQLException e) {
			LOGGER.error("Query: " + selectSt, e);
		} finally {
			closeConnection();
		}
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.server.DAO.UserDAO#openIDhasRole(java.lang.String
	 * , java.lang.String)
	 */
	@Override
	public boolean openIDhasRole(String role, String identifier) throws DatabaseException {
		PreparedStatement selectSt = null;
		boolean ret = false;
		try {
			selectSt = getConnection().prepareStatement(SELECT_ROLE_BY_IDENTITY_STATEMENT);
			selectSt.setString(1, identifier);
			selectSt.setString(2, role);
		} catch (SQLException e) {
			LOGGER.error("Could not get select roles statement", e);
		}
		try {
			ResultSet rs = selectSt.executeQuery();
			while (rs.next()) {
				ret = true;
			}
		} catch (SQLException e) {
			LOGGER.error("Query: " + selectSt, e);
		} finally {
			closeConnection();
		}
		return ret;
	}
}
