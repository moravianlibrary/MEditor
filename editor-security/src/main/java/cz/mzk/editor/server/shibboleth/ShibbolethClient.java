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

package cz.mzk.editor.server.shibboleth;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.LogInOutDAO;
import cz.mzk.editor.server.DAO.SecurityUserDAO;
import cz.mzk.editor.server.config.EditorConfiguration.ServerConstants;

/**
 * @author Matous Jobanek
 * @version Nov 22, 2012
 */
public class ShibbolethClient {

    /** The Constant ACCESS_LOGGER. */
    private static final Logger ACCESS_LOGGER = Logger.getLogger(ServerConstants.ACCESS_LOG_ID);

    /** The Constant FORMATTER. */
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /** The log in out dao. */
    @Inject
    private static LogInOutDAO logInOutDAO;

    /** The security user dao. */
    @Inject
    private static SecurityUserDAO securityUserDAO;

    public static Long getUserId(String shibboleth) {
	Long userId = -1L;
	try {
	    userId = securityUserDAO.getUserId(shibboleth, USER_IDENTITY_TYPES.SHIBBOLETH, true);
	} catch (DatabaseException e) {
	    throw new AuthenticationServiceException(Constants.CANNOT_CONNECT_TO_DB);
	} catch (SQLException e) {
	    throw new InternalAuthenticationServiceException(Constants.DB_ERROR, e);
	}
	if (userId > 0) {
	    ACCESS_LOGGER.info("LOG IN: [" + FORMATTER.format(new Date()) + "] Shibboleth User " + shibboleth);
	    try {
		logInOutDAO.logInOut(userId, true);
	    } catch (DatabaseException e) {
		throw new AuthenticationServiceException(Constants.CANNOT_CONNECT_TO_DB);
	    }
	    return userId;
	} else {
	    return 0L;
	}
    }

}
