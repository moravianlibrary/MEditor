
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

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.server.EditorUserAuthentication;
import cz.mzk.editor.server.DAO.LogInOutDAO;
import cz.mzk.editor.server.DAO.SecurityUserDAO;
import cz.mzk.editor.server.config.EditorConfiguration.ServerConstants;

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

/**
 * @author Matous Jobanek
 * @version Nov 14, 2012
 */
public class LDAPAuthenticationProvider
        implements AuthenticationProvider {

    /** The Constant LOGGER. */
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(LDAPAuthenticationProvider.class.getPackage()
            .toString());

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

    private Properties ldapProperties = null;

    private LDAPSearch ldapSearch = null;

    /**
     * {@inheritDoc}
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        Long userId = verify(username, password);

        if (userId < 0) {
            SecurityContextHolder.clearContext();
            throw new BadCredentialsException(Constants.INVALID_LOGIN_OR_PASSWORD);
        } else if (userId == 0) {
            EditorUserAuthentication customAuthentication =
                    new EditorUserAuthentication("ROLE_USER", authentication, USER_IDENTITY_TYPES.LDAP);
            customAuthentication.setAuthenticated(false);
            customAuthentication.setToAdd(true);
            customAuthentication.setDisplayName(getLdapName(username));
            return customAuthentication;
        } else {

            Authentication customAuthentication =
                    new EditorUserAuthentication("ROLE_USER", authentication, USER_IDENTITY_TYPES.LDAP);
            customAuthentication.setAuthenticated(true);
            return customAuthentication;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private void setLdapSearchAndProp() throws Exception {
        ldapProperties = new Properties();
        ldapProperties.load(new FileInputStream(new File(System.getProperty("user.home") + File.separator
                + ".meditor/ldap.properties")));

        ldapSearch = new LDAPSearchImpl(ldapProperties);
    }

    /**
     * Verify.
     * 
     * @param login
     *        the login
     * @param password
     *        the password
     * @return the int
     */
    public Long verify(String login, String password) {

        try {

            if (ldapSearch == null || ldapProperties == null) {
                setLdapSearchAndProp();
            }

            String loginAttribute = ldapProperties.getProperty("loginAttribute", "sAMAccountName");
            String query = loginAttribute + "=" + login;

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

    private String getLdapName(String login) {
        String loginAttribute = ldapProperties.getProperty("loginAttribute", "sAMAccountName");
        List<Map<String, List<Object>>> objects = ldapSearch.search(loginAttribute + "=" + login);
        String dispalyNameAttribute = ldapProperties.getProperty("dispalyNameAttribute", "displayName");
        if (objects != null && !objects.isEmpty() && objects.get(0).get(dispalyNameAttribute) != null
                && !objects.get(0).get(dispalyNameAttribute).isEmpty()
                && objects.get(0).get(dispalyNameAttribute).get(0) != null) {
            return objects.get(0).get(dispalyNameAttribute).get(0).toString();
        }
        return "";
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
