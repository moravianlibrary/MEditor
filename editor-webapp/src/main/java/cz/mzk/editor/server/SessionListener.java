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

package cz.mzk.editor.server;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import org.springframework.security.core.context.SecurityContext;

import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.LogInOutDAOImpl;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.config.EditorConfiguration.ServerConstants;

public class SessionListener
        implements HttpSessionListener {

    private static final Logger LOGGER = Logger.getLogger(SessionListener.class);
    private static final Logger ACCESS_LOGGER = Logger.getLogger(ServerConstants.ACCESS_LOG_ID);
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Inject
    private static LogInOutDAOImpl logInOutDAO;

    @Inject
    private static UserDAO userDAO;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();

        SecurityContext secContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        EditorUserAuthentication authentication = null;
        if (secContext != null) authentication = (EditorUserAuthentication) secContext.getAuthentication();

        if (authentication != null) {

            try {
                Long usersId =
                        userDAO.getUsersId((String) authentication.getPrincipal(),
                                           authentication.getIdentityType());

                String name = userDAO.getName(usersId);
                ACCESS_LOGGER.info("LOG OUT: User " + name + " with "
                        + authentication.getIdentityType().toString() + " identifier "
                        + authentication.getPrincipal() + " at " + FORMATTER.format(new Date()));
                logInOutDAO.logInOut(usersId, false);
            } catch (DatabaseException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }

            if (secContext != null) secContext.setAuthentication(null);

        } else {
            LOGGER.debug("Session expired.");
        }
    }
}