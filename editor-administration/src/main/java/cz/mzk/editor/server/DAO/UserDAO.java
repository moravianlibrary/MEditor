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

import java.util.ArrayList;

import javax.activation.UnsupportedDataTypeException;

import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.shared.rpc.RoleItem;
import cz.mzk.editor.shared.rpc.UserIdentity;
import cz.mzk.editor.shared.rpc.UserInfoItem;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserDAO.
 */
public interface UserDAO {

    /** The Constant UNKNOWN. */
    public static final int UNKNOWN = -1;

    /** The Constant NOT_PRESENT. */
    public static final int NOT_PRESENT = 0;

    /** The Constant USER. */
    public static final int USER = 1;

    /** The Constant ADMIN. */
    public static final int ADMIN = 2;

    /** The Constant ADMIN_STRING. */
    public static final String ADMIN_STRING = "admin";

    /** The Constant EDIT_USERS_STRING. */
    public static final String EDIT_USERS_STRING = "edit_users";

    /** The Constant CAN_PUBLISH_STRING. */
    public static final String CAN_PUBLISH_STRING = "can_publish";

    /**
     * Checks if is supported.
     * 
     * @param identifier
     *        the identifier
     * @return the int
     * @throws DatabaseException
     *         the database exception
     * @throws UnsupportedDataTypeException
     */
    int isSupported(String identifier) throws DatabaseException;

    /**
     * Gets the users id.
     * 
     * @param identifier
     *        the identifier
     * @param identityType
     *        the identity type
     * @return the users id
     * @throws DatabaseException
     *         the database exception
     */
    long getUsersId(String identifier, USER_IDENTITY_TYPES identityType) throws DatabaseException;

    long getUsersId(String identifier) throws DatabaseException;

    /**
     * Checks for role.
     * 
     * @param role
     *        the role
     * @param userId
     *        the user id
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     */
    boolean hasRole(String role, long userId) throws DatabaseException;

    /**
     * Gets the name.
     * 
     * @param key
     *        the key
     * @return the name
     * @throws DatabaseException
     *         the database exception
     */
    String getName(Long key) throws DatabaseException;

    /**
     * Adds the remove user identity.
     * 
     * @param userIdentity
     *        the user identity
     * @param add
     *        the add
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     * @throws UnsupportedDataTypeException
     */
    boolean addRemoveUserIdentity(UserIdentity userIdentity, boolean add) throws DatabaseException,
            UnsupportedDataTypeException;

    /**
     * Adds the remove role item.
     * 
     * @param roleItem
     *        the role item
     * @param add
     *        the add
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     * @throws UnsupportedDataTypeException
     */
    boolean addRemoveRoleItem(RoleItem roleItem, boolean add) throws DatabaseException,
            UnsupportedDataTypeException;

    /**
     * Gets the roles of user.
     * 
     * @param id
     *        the id
     * @return the roles of user
     * @throws DatabaseException
     *         the database exception
     */
    ArrayList<RoleItem> getRolesOfUser(long id) throws DatabaseException;

    /**
     * Gets the roles.
     * 
     * @return the roles
     * @throws DatabaseException
     *         the database exception
     */
    ArrayList<RoleItem> getRoles() throws DatabaseException;

    /**
     * Gets the identities.
     * 
     * @param id
     *        the id
     * @param type
     *        the type
     * @return the identities
     * @throws DatabaseException
     *         the database exception
     * @throws UnsupportedDataTypeException
     */
    ArrayList<UserIdentity> getIdentities(String userId, USER_IDENTITY_TYPES type) throws DatabaseException,
            UnsupportedDataTypeException;

    /**
     * Removes the user.
     * 
     * @param id
     *        the id
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     * @throws UnsupportedDataTypeException
     */
    boolean disableUser(long userId) throws DatabaseException;

    /**
     * Inser updatet user.
     * 
     * @param user
     *        the user
     * @return true, if successful
     * @throws DatabaseException
     *         the database exception
     * @throws UnsupportedDataTypeException
     */
    boolean insertUpdatetUser(UserInfoItem user) throws DatabaseException;

    /**
     * Gets the users.
     * 
     * @return the users
     * @throws DatabaseException
     *         the database exception
     */
    ArrayList<UserInfoItem> getUsers() throws DatabaseException;

}
