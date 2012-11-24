/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
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
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;

/**
 * @author Matous Jobanek
 * @version Oct 23, 2012
 */
public class LogInOutDAOImpl
        extends AbstractDAO
        implements LogInOutDAO {

    private static final Logger LOGGER = Logger.getLogger(LogInOutDAOImpl.class);

    //  log_in_out (id, editor_user_id, "timestamp", successful, type)

    /** The Constant INSERT_LOG_IN_ACTION. */
    public static final String INSERT_LOG_IN_ACTION = "INSERT INTO " + Constants.TABLE_LOG_IN_OUT
            + " (editor_user_id, timestamp, type) VALUES ((?), (CURRENT_TIMESTAMP), (?))";

    /**
     * {@inheritDoc}
     */
    public void logInOut(Long userId, boolean logIn) throws DatabaseException {
        PreparedStatement insSt = null;

        try {
            insSt = getConnection().prepareStatement(INSERT_LOG_IN_ACTION);
            insSt.setLong(1, userId);
            insSt.setBoolean(2, logIn);

            if (insSt.executeUpdate() == 1) {
                LOGGER.debug("DB has been updated: The user " + userId + " item has been loged.");
            } else {
                LOGGER.error("DB has not been updated! " + insSt);
            }

        } catch (SQLException e) {
            LOGGER.error("Could not get insert item statement " + insSt, e);
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void logInOut(boolean logIn) throws DatabaseException {
        logInOut(getUserId(), logIn);
    }
}
