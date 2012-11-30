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

import javax.inject.Inject;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;
import cz.mzk.editor.client.util.Constants.DEFAULT_SYSTEM_USERS;
import cz.mzk.editor.client.util.Constants.REQUESTS_TO_ADMIN_TYPES;
import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.shared.rpc.RequestItem;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDAOImpl.
 */
public class RequestDAOImpl
        extends AbstractDAO
        implements RequestDAO {

    /** The Constant SELECT_IDENTITIES_STATEMENT. */
    public static final String SELECT_REQUESTS_STATEMENT =
            "SELECT r.type, r.id, object, description, editor_user_id, surname || ' ' || name as fullUserName, timestamp FROM ("
                    + Constants.TABLE_REQUEST_TO_ADMIN
                    + " r INNER JOIN (SELECT * FROM ("
                    + Constants.TABLE_CRUD_REQUEST_TO_ADMIN_ACTION
                    + " cra INNER JOIN "
                    + Constants.TABLE_EDITOR_USER
                    + " eu ON cra.editor_user_id = eu.id) WHERE type = 'c') a ON r.id = a.request_to_admin_id) WHERE solved = 'false' AND (admin_editor_user_id = '"
                    + Constants.DEFAULT_SYSTEM_USERS.NON_EXISTENT.getUserId()
                    + "' OR admin_editor_user_id = (?))";

    public static final String SELECT_IDENTITY_STATEMENT = "SELECT * FROM "
            + Constants.TABLE_REQUEST_TO_ADMIN + " WHERE solved = 'false' AND object = (?)";

    public static final String INSERT_REQUEST_STATEMENT = "INSERT INTO " + Constants.TABLE_REQUEST_TO_ADMIN
            + " (admin_editor_user_id, type, object, description, solved) VALUES ((?),(?),(?),(?),'false')";

    /** The Constant SOLVE_REQUEST. */
    public static final String SOLVE_REQUEST = "UPDATE " + Constants.TABLE_REQUEST_TO_ADMIN
            + " SET solved = 'true' WHERE id = (?)";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RequestDAOImpl.class);

    /** The dao utils. */
    @Inject
    private DAOUtils daoUtils;

    /**
     * {@inheritDoc}
     */
    @Override
    public void solveRequest(long id) throws DatabaseException {
        PreparedStatement solveSt = null;
        int updated = 0;

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }

        try {
            solveSt = getConnection().prepareStatement(SOLVE_REQUEST);
            solveSt.setLong(1, id);
            updated = solveSt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Could not solve request with id " + id + " Query: " + solveSt, e);
        } finally {
            closeConnection();
        }
        if (updated > 0) {
            try {
                if (daoUtils.insertCrudAction(Constants.TABLE_CRUD_REQUEST_TO_ADMIN_ACTION,
                                              "request_to_admin_id",
                                              id,
                                              CRUD_ACTION_TYPES.UPDATE,
                                              true)) {
                    getConnection().commit();
                    LOGGER.debug("DB has been updated by commit.");
                } else {
                    getConnection().rollback();
                    LOGGER.debug("DB has not been updated -> rollback!");
                }

            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }

    }

    @Override
    public int addNewIdentifierRequest(String name, String identifier, USER_IDENTITY_TYPES idType)
            throws DatabaseException {
        if (name == null) throw new NullPointerException("name");
        if (identifier == null || "".equals(identifier)) throw new NullPointerException("identifier");

        int toReturn = -1;

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }
        PreparedStatement findSt = null, insSt = null;
        try {

            StringBuffer sqlSb = new StringBuffer(SELECT_IDENTITY_STATEMENT);
            if (idType == USER_IDENTITY_TYPES.OPEN_ID) sqlSb.append(" OR object = (?)");
            findSt = getConnection().prepareStatement(sqlSb.toString());

            findSt.setString(1, getIdentifierRequest(idType, identifier));
            if (idType == USER_IDENTITY_TYPES.OPEN_ID) findSt.setString(2, identifier);

            ResultSet rs = findSt.executeQuery();
            if (!rs.next()) {
                insSt =
                        getConnection().prepareStatement(INSERT_REQUEST_STATEMENT,
                                                         Statement.RETURN_GENERATED_KEYS);
                insSt.setLong(1, DEFAULT_SYSTEM_USERS.NON_EXISTENT.getUserId());
                insSt.setString(2, REQUESTS_TO_ADMIN_TYPES.ADDING_NEW_ACOUNT.getValue());
                insSt.setString(3, getIdentifierRequest(idType, identifier));
                insSt.setString(4, name);

                if (insSt.executeUpdate() == 1) {
                    LOGGER.debug("DB has been updated: The request of " + name + " with " + idType
                            + " identifier: " + identifier + " has been inserted.");
                    ResultSet gk = insSt.getGeneratedKeys();
                    if (gk.next()) {
                        if (daoUtils.insertCrudAction(DEFAULT_SYSTEM_USERS.NON_EXISTENT.getUserId(),
                                                      Constants.TABLE_CRUD_REQUEST_TO_ADMIN_ACTION,
                                                      "request_to_admin_id",
                                                      gk.getLong(1),
                                                      CRUD_ACTION_TYPES.CREATE,
                                                      false)) {
                            toReturn = 1;
                            getConnection().commit();
                            LOGGER.debug("DB has been updated by commit.");
                        } else {
                            getConnection().rollback();
                            LOGGER.debug("DB has not been updated -> rollback!");
                        }
                    } else {
                        LOGGER.error("No key has been returned! " + insSt);
                    }

                } else {
                    LOGGER.error("DB has not been updated! " + insSt);
                }
            } else {
                toReturn = 0;
            }

            // TX end
        } catch (SQLException e) {
            LOGGER.error("Queries: \"" + findSt + "\" and \"" + insSt + "\"", e);
        } finally {
            closeConnection();
        }
        return toReturn;
    }

    private String getIdentifierRequest(USER_IDENTITY_TYPES idType, String identifier) {
        return idType.toString() + ": " + identifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<RequestItem> getAllRequests() throws DatabaseException {
        PreparedStatement selectSt = null;
        ArrayList<RequestItem> retList = new ArrayList<RequestItem>();
        try {
            selectSt = getConnection().prepareStatement(SELECT_REQUESTS_STATEMENT);
            selectSt.setLong(1, getUserId(false));
        } catch (SQLException e) {
            LOGGER.error("Could not get select roles statement", e);
        }
        try {
            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                long user = rs.getLong("editor_user_id");
                String userName = rs.getString("fullUserName");
                String description = rs.getString("description");
                if (DEFAULT_SYSTEM_USERS.NON_EXISTENT.getUserId().equals(user)) {
                    userName = description;
                    //                    TODO
                    description = "";
                }

                retList.add(new RequestItem(rs.getLong("id"),
                                            userName,
                                            user,
                                            rs.getString("object"),
                                            FORMATTER_TO_SECONDS.format(rs.getTimestamp("timestamp")),
                                            description,
                                            rs.getString("type")));
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return retList;
    }
}
