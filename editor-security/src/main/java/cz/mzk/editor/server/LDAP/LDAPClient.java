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

package cz.mzk.editor.server.LDAP;

import java.io.File;
import java.io.FileInputStream;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.server.DAO.LogInOutDAO;
import cz.mzk.editor.server.DAO.SecurityUserDAO;
import cz.mzk.editor.server.config.EditorConfiguration.ServerConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class LDAPClient.
 * 
 * @author Matous Jobanek
 * @author xrosecky
 * @version Nov 14, 2012
 */
public class LDAPClient {

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

    /**
     * Verify.
     * 
     * @param login
     *        the login
     * @param password
     *        the password
     * @return the int
     */
    public static Long verify(String login, String password) {

        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File(System.getProperty("user.home") + File.separator
                    + ".meditor/ldap.properties")));

            LDAPSearchImpl ldapSearch = new LDAPSearchImpl(properties);

            String loginAttribute = properties.getProperty("loginAttribute", "sAMAccountName");
            String query = loginAttribute + "=" + login;

            //            List<Map<String, List<Object>>> objects = ldapSearch.search(query);
            //            String uniqueIdAttribute = properties.getProperty("uniqueIdentifier", loginAttribute);
            //            String fullyQualName = objects.get(0).get(uniqueIdAttribute).get(0).toString();
            //            boolean isAuth = ldapSearch.auth(password, uniqueIdAttribute + "=" + fullyQualName);

            boolean isAuth = ldapSearch.auth(password, query);

            if (isAuth) {
                Long userId = securityUserDAO.getUserId(login, USER_IDENTITY_TYPES.LDAP, true);
                if (userId > 0) {
                    ACCESS_LOGGER.info("LOG IN: [" + FORMATTER.format(new Date()) + "] LDAP User " + login);
                    logInOutDAO.logInOut(userId, true);
                    return userId;
                } else {
                    return new Long(0);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new Long(-1);
    }

    /**
     * Prints the all attributes.
     * 
     * @param objects
     *        the objects
     */
    @SuppressWarnings("unused")
    private void printAllAttributes(List<Map<String, List<Object>>> objects) {
        for (Map<String, List<Object>> object : objects) {
            System.out.println("============================================");
            for (Map.Entry<String, List<Object>> entry : object.entrySet()) {
                System.out.print(entry.getKey() + " : ");
                String sep = "";
                for (Object obj : entry.getValue()) {
                    System.out.print(sep + obj);
                    sep = ", ";
                }
                System.out.println();
            }
            System.out.println("============================================");
        }
    }
}
