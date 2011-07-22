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

import java.util.ArrayList;

import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

import cz.fi.muni.xkremser.editor.shared.rpc.OpenIDItem;
import cz.fi.muni.xkremser.editor.shared.rpc.RoleItem;
import cz.fi.muni.xkremser.editor.shared.rpc.UserInfoItem;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserDAO.
 */
public interface UserDAO {

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
     */
    int isSupported(String identifier) throws DatabaseException;

    /**
     * Checks for role.
     * 
     * @param role
     *        the role
     * @param userId
     *        the user id
     * @return true, if successful
     */
    boolean hasRole(String role, long userId) throws DatabaseException;

    /**
     * Open i dhas role.
     * 
     * @param role
     *        the role
     * @param identifier
     *        the identifier
     * @return true, if successful
     */
    boolean openIDhasRole(String role, String identifier) throws DatabaseException;

    /**
     * Gets the name.
     * 
     * @param openID
     *        the open id
     * @return the name
     */
    String getName(String openID) throws DatabaseException;

    /**
     * Adds the user identity.
     * 
     * @param identity
     *        the identity
     * @param userId
     *        the user id
     * @return the string
     */
    String addUserIdentity(OpenIDItem identity, long userId) throws DatabaseException;

    /**
     * Removes the user identity.
     * 
     * @param id
     *        the id
     */
    void removeUserIdentity(long id) throws DatabaseException;

    /**
     * Adds the user role.
     * 
     * @param role
     *        the role
     * @param userId
     *        the user id
     * @return the role item
     */
    RoleItem addUserRole(RoleItem role, long userId) throws DatabaseException;

    /**
     * Removes the user role.
     * 
     * @param id
     *        the id
     */
    void removeUserRole(long id) throws DatabaseException;

    /**
     * Gets the roles of user.
     * 
     * @param id
     *        the id
     * @return the roles of user
     */
    ArrayList<RoleItem> getRolesOfUser(long id) throws DatabaseException;

    /**
     * Gets the roles.
     * 
     * @return the roles
     */
    ArrayList<String> getRoles() throws DatabaseException;

    /**
     * Gets the identities.
     * 
     * @param id
     *        the id
     * @return the identities
     */
    ArrayList<OpenIDItem> getIdentities(String id) throws DatabaseException;

    /**
     * Removes the user.
     * 
     * @param id
     *        the id
     */
    void removeUser(long id) throws DatabaseException;

    /**
     * Adds the user.
     * 
     * @param user
     *        the user
     * @return the string
     */
    String addUser(UserInfoItem user) throws DatabaseException;

    /**
     * Gets the users.
     * 
     * @return the users
     */
    ArrayList<UserInfoItem> getUsers() throws DatabaseException;

}
