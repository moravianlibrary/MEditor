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

package cz.mzk.editor.server.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import javax.activation.UnsupportedDataTypeException;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;
import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.shared.rpc.RoleItem;
import cz.mzk.editor.shared.rpc.UserIdentity;
import cz.mzk.editor.shared.rpc.UserInfoItem;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDAOImpl.
 */
public class UserDAOImpl
        extends AbstractDAO
        implements UserDAO {

    //    editor_user (id, name, surname, sex) -> editor_user (id, name, surname, state)
    //                                                             name, surname, 'true'
    //    role (name, description) 
    //    users_role (editor_user_id, role_name)
    //    open_id_identity (id, user_id, identity) -> open_id_identity (editor_user_id, identity)
    //                                                                         user_id, identity
    //    user_edit (id, editor_user_id, "timestamp", edited_editor_user_id, description, type)

    /** The Constant SELECT_USERS_STATEMENT. */
    public static final String SELECT_USERS_STATEMENT = "SELECT id, name, surname FROM "
            + Constants.TABLE_EDITOR_USER + " ORDER BY surname";

    /** The Constant DISABLE_USER. */
    public static final String DISABLE_USER = "UPDATE " + Constants.TABLE_EDITOR_USER
            + " SET state='false' WHERE id = (?)";

    /** The Constant INSERT_ITEM_STATEMENT. */
    public static final String INSERT_USER_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_EDITOR_USER
            + " (name, surname, state) VALUES ((?),(?),'true')";

    /** The Constant INSERT_EDIT_USER_ACTION_STATEMENT. */
    public static final String INSERT_EDIT_USER_ACTION_STATEMENT =
            "INSERT INTO "
                    + Constants.TABLE_EDITOR_USER
                    + " (editor_user_id, timestamp, edited_editor_user_id, description, type) VALUES ((?),(CURRENT_TIMESTAMP),(?),(?),(?))";

    /** The Constant UPDATE_ITEM_STATEMENT. */
    public static final String UPDATE_USER_STATEMENT = "UPDATE " + Constants.TABLE_EDITOR_USER
            + " SET name = (?), surname = (?) WHERE id = (?)";

    /** The Constant SELECT_ROLES_OF_USER_STATEMENT2. */
    public static final String SELECT_ROLES_OF_USER_STATEMENT = "SELECT name, description FROM "
            + Constants.TABLE_ROLE + " WHERE name IN ( SELECT role_name FROM " + Constants.TABLE_USERS_ROLE
            + " WHERE editor_user_id = (?) )";

    /** The Constant INSERT_USERS_ROLE_ITEM_STATEMENT. */
    public static final String INSERT_USERS_ROLE_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_USERS_ROLE
            + " (editor_user_id, role_name) VALUES ((?),(?))";

    /** The Constant DELETE_USERS_ROLE_ITEM_STATEMENT. */
    public static final String DELETE_USERS_ROLE_ITEM_STATEMENT = "DELETE FROM " + Constants.TABLE_USERS_ROLE
            + " WHERE editor_user_id=(?) AND role_name=(?)";

    /** The Constant SELECT_ROLE_ITEMS_STATEMENT. */
    public static final String SELECT_ROLE_ITEMS_STATEMENT = "SELECT name, description FROM "
            + Constants.TABLE_ROLE + " ORDER BY name";

    /** The Constant SELECT_USER_NAME. */
    public static final String SELECT_USER_NAME = "SELECT name, surname FROM " + Constants.TABLE_EDITOR_USER
            + " WHERE id=(?)";

    /** The Constant SELECT_USER_ID_BY_OPENID. */
    public static final String SELECT_USER_ID_BY_OPENID = "SELECT id FROM " + Constants.TABLE_EDITOR_USER
            + " WHERE id IN (SELECT user_id FROM " + Constants.TABLE_OPEN_ID_IDENTITY
            + " WHERE identity = (?))";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RequestDAOImpl.class);

    /** The dao utils. */
    @SuppressWarnings("unused")
    @Inject
    private DAOUtils daoUtils;

    @Inject
    private LockDAO lockDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public int isSupported(String identifier) throws DatabaseException {

        long userId = getUsersId(identifier);

        if (userId == -1) userId = getUsersIdOld(identifier);

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

    /**
     * {@inheritDoc}
     */
    @Override
    public long getUsersId(String identifier) throws DatabaseException {

        return getUsersId(identifier, USER_IDENTITY_TYPES.OPEN_ID);
    }

    /**
     * Gets the users id old.
     * 
     * @param identifier
     *        the identifier
     * @return the users id old
     * @throws DatabaseException
     *         the database exception
     */
    private long getUsersIdOld(String identifier) throws DatabaseException {
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
        return userId;
    }

    /**
     * {@inheritDoc}
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
                retList.add(new UserInfoItem(rs.getString("name"), rs.getString("surname"), rs
                        .getString("id")));
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return retList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean disableUser(long userId) throws DatabaseException {
        PreparedStatement updateSt = null;
        boolean successful = false;

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }

        try {
            updateSt = getConnection().prepareStatement(DISABLE_USER);
            updateSt.setLong(1, userId);
            int updated = updateSt.executeUpdate();

            if (updated == 1) {
                LOGGER.debug("DB has been updated: The user: " + userId + " has been disabled.");

                //                lockDAO.un

                boolean crudSucc =
                        insertEditUserActionItem(getUserId(),
                                                 userId,
                                                 "The role has been disabled.",
                                                 CRUD_ACTION_TYPES.DELETE,
                                                 false);
                if (crudSucc) {
                    getConnection().commit();
                    successful = true;
                    LOGGER.debug("DB has been updated by commit.");
                } else {
                    getConnection().rollback();
                    LOGGER.debug("DB has not been updated -> rollback!");
                }

            } else {
                LOGGER.error("DB has not been updated! " + updateSt);
            }

        } catch (SQLException e) {
            LOGGER.error("Could not delete user with id " + userId + ". Query: " + updateSt, e);
        } finally {
            closeConnection();
        }
        return successful;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertUpdatetUser(UserInfoItem user) throws DatabaseException {
        if (user == null) throw new NullPointerException("user");
        if (user.getSurname() == null || "".equals(user.getSurname()))
            throw new NullPointerException("user.getSurname()");

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }

        PreparedStatement updateSt = null;
        boolean successful = false;
        Long id = null;
        try {
            if (user.getId() == null) {
                updateSt =
                        getConnection().prepareStatement(INSERT_USER_ITEM_STATEMENT,
                                                         Statement.RETURN_GENERATED_KEYS);
                updateSt.setString(1, user.getName());
                updateSt.setString(2, user.getSurname());

            } else {
                updateSt = getConnection().prepareStatement(UPDATE_USER_STATEMENT);
                id = Long.parseLong(user.getId());
                updateSt.setString(1, user.getName());
                updateSt.setString(2, user.getSurname());
                updateSt.setLong(3, id);
            }

            if (updateSt.executeUpdate() == 1) {
                LOGGER.debug("DB has been updated: The editor user: " + user.getName() + " "
                        + user.getSurname() + " has been inserted.");
                ResultSet gk = updateSt.getGeneratedKeys();
                if (gk.next()) {
                    id = gk.getLong(1);
                } else {
                    LOGGER.error("No key has been returned! " + updateSt);
                }
            } else {
                LOGGER.error("DB has not been updated! " + updateSt);
            }

            if (id != null) {
                boolean crudSucc =
                        insertEditUserActionItem(getUserId(),
                                                 id,
                                                 user.getId() == null ? "New user has been added"
                                                         : "User has been updated",
                                                 user.getId() == null ? CRUD_ACTION_TYPES.CREATE
                                                         : CRUD_ACTION_TYPES.UPDATE,
                                                 false);

                if (crudSucc) {
                    successful = true;
                    getConnection().commit();
                    LOGGER.debug("DB has been updated by commit.");
                } else {
                    getConnection().rollback();
                    LOGGER.debug("DB has not been updated. -> rollback!");
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Could not get insert item statement " + updateSt, e);
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return successful;
    }

    /**
     * Insert edit user action item.
     * 
     * @param user_id
     *        the user_id
     * @param edited_user_id
     *        the edited_user_id
     * @param description
     *        the description
     * @param type
     *        the type
     * @param closeCon
     *        the close con
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     */
    private boolean insertEditUserActionItem(Long user_id,
                                             Long edited_user_id,
                                             String description,
                                             CRUD_ACTION_TYPES type,
                                             boolean closeCon) throws DatabaseException {
        PreparedStatement insSt = null;
        boolean successful = false;
        try {
            insSt = getConnection().prepareStatement(INSERT_EDIT_USER_ACTION_STATEMENT);
            insSt.setLong(1, user_id);
            insSt.setLong(2, edited_user_id);
            insSt.setString(3, description);
            insSt.setString(4, type.toString());

            if (insSt.executeUpdate() == 1) {
                successful = true;
                LOGGER.debug("DB has been updated by commit.: The edit_user action: " + type.toString()
                        + " of user: " + user_id + " has been inserted.");
            } else {
                LOGGER.debug("DB has not been updated!");
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get insert item statement " + insSt, e);
        } finally {
            if (closeCon) closeConnection();
        }
        return successful;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<RoleItem> getRolesOfUser(long userId) throws DatabaseException {
        PreparedStatement selectSt = null;
        ArrayList<RoleItem> retList = new ArrayList<RoleItem>();
        try {
            selectSt = getConnection().prepareStatement(SELECT_ROLES_OF_USER_STATEMENT);
            selectSt.setLong(1, userId);
        } catch (SQLException e) {
            LOGGER.error("Could not get select statement", e);
        }
        try {
            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                retList.add(new RoleItem(userId, rs.getString("name"), rs.getString("description")));
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return retList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<UserIdentity> getIdentities(String id, USER_IDENTITY_TYPES type)
            throws DatabaseException, UnsupportedDataTypeException {
        PreparedStatement selectSt = null;
        ArrayList<UserIdentity> retList = new ArrayList<UserIdentity>();
        long userId = Long.parseLong(id);
        try {

            StringBuffer sql = new StringBuffer("SELECT identity FROM ");
            switch (type) {
                case OPEN_ID:
                    sql.append(Constants.TABLE_OPEN_ID_IDENTITY);
                    break;
                case SHIBBOLETH:
                    sql.append(Constants.TABLE_SHIBBOLETH_IDENTITY);
                    break;
                case LDAP:
                    sql.append(Constants.TABLE_LDAP_IDENTITY);
                    break;
                default:
                    throw new UnsupportedDataTypeException(type.toString());
            }
            sql.append(" WHERE user_id = (?)");
            selectSt = getConnection().prepareStatement(sql.toString());
            selectSt.setLong(1, userId);
        } catch (SQLException e) {
            LOGGER.error("Could not get select statement", e);
        }
        try {
            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                retList.add(new UserIdentity(rs.getString("identity"), type, userId));
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return retList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addRemoveUserIdentity(UserIdentity userIdentity, boolean add) throws DatabaseException,
            UnsupportedDataTypeException {
        if (userIdentity == null) throw new NullPointerException("identity");
        if (userIdentity.getIdentity() == null || "".equals(userIdentity.getIdentity())
                || userIdentity.getUserId() == null) throw new NullPointerException();

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }
        boolean success = false;
        PreparedStatement updateSt = null;
        try {
            StringBuffer sql = new StringBuffer(add ? "INSERT INTO " : "DELETE FROM ");
            switch (userIdentity.getType()) {
                case OPEN_ID:
                    sql.append(Constants.TABLE_OPEN_ID_IDENTITY);
                    break;
                case SHIBBOLETH:
                    sql.append(Constants.TABLE_SHIBBOLETH_IDENTITY);
                    break;
                case LDAP:
                    sql.append(Constants.TABLE_LDAP_IDENTITY);
                    break;
                default:
                    throw new UnsupportedDataTypeException(userIdentity.getType().toString());
            }
            if (add) {
                sql.append(" (editor_user_id, identity) VALUES ((?),(?))");
            } else {
                sql.append(" WHERE editor_user_id = (?) AND identity = (?)");
            }
            updateSt = getConnection().prepareStatement(sql.toString());

            updateSt.setLong(1, userIdentity.getUserId());
            updateSt.setString(2, userIdentity.getIdentity());

            if (updateSt.executeUpdate() == 1) {
                LOGGER.debug("DB has been updated: The " + userIdentity.getType().toString() + " identity "
                        + userIdentity.getIdentity() + " has been " + (add ? "added to" : "removed from")
                        + " the user: " + userIdentity.getUserId());

                boolean crudSucc =
                        insertEditUserActionItem(getUserId(),
                                                 userIdentity.getUserId(),
                                                 "An identity has been " + (add ? "added." : "removed."),
                                                 CRUD_ACTION_TYPES.UPDATE,
                                                 true);

                if (crudSucc) {
                    getConnection().commit();
                    success = true;
                    LOGGER.debug("DB has been updated by commit.");
                } else {
                    getConnection().rollback();
                    LOGGER.debug("DB has not been updated -> rollback!");
                }

            } else {
                LOGGER.error("DB has not been updated! " + updateSt);
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return success;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addRemoveRoleItem(RoleItem roleItem, boolean add) throws DatabaseException,
            UnsupportedDataTypeException {

        if (roleItem == null) throw new NullPointerException("role");
        if (roleItem.getName() == null || "".equals(roleItem.getName()) || roleItem.getUserId() == null)
            throw new NullPointerException();
        if (hasRole(roleItem.getName(), roleItem.getUserId())) {
            return true;
        }

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }

        boolean success = false;
        PreparedStatement updateSt = null;
        try {

            updateSt =
                    getConnection().prepareStatement(add ? INSERT_USERS_ROLE_ITEM_STATEMENT
                            : DELETE_USERS_ROLE_ITEM_STATEMENT);
            updateSt.setLong(1, roleItem.getUserId());
            updateSt.setString(2, roleItem.getName());

            if (updateSt.executeUpdate() == 1) {
                LOGGER.debug("DB has been updated: The role " + roleItem.getName() + " has been "
                        + (add ? "added to" : "removed from") + " the user: " + roleItem.getUserId());

                boolean crudSucc =
                        insertEditUserActionItem(getUserId(), roleItem.getUserId(), "The role has been "
                                + (add ? "added." : "removed."), CRUD_ACTION_TYPES.UPDATE, true);

                if (crudSucc) {
                    getConnection().commit();
                    success = true;
                    LOGGER.debug("DB has been updated by commit.");
                } else {
                    getConnection().rollback();
                    LOGGER.debug("DB has not been updated -> rollback!");
                }

            } else {
                LOGGER.error("DB has not been updated! " + updateSt);
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return success;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasRole(String role, long userId) throws DatabaseException {
        ArrayList<RoleItem> roles = getRolesOfUser(userId);
        if (role == null) return false;
        for (RoleItem candidateRole : roles) {
            if (candidateRole != null && candidateRole.getName() != null
                    && candidateRole.getName().equals(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<RoleItem> getRoles() throws DatabaseException {
        PreparedStatement selectSt = null;
        ArrayList<RoleItem> retList = new ArrayList<RoleItem>();
        try {
            selectSt = getConnection().prepareStatement(SELECT_ROLE_ITEMS_STATEMENT);
        } catch (SQLException e) {
            LOGGER.error("Could not get select roles statement", e);
        }
        try {
            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                retList.add(new RoleItem(null, rs.getString("name"), rs.getString("description")));
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return retList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName(Long key) throws DatabaseException {
        PreparedStatement selectSt = null;
        String name = "unknown";
        try {

            selectSt = getConnection().prepareStatement(SELECT_USER_NAME);
            selectSt.setLong(1, Long.valueOf(key));

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

}
