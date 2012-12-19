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

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.server.EditorUserAuthentication;
import cz.mzk.editor.server.HttpCookies;
import cz.mzk.editor.server.SecurityUtils;
import cz.mzk.editor.server.URLS;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.config.EditorConfigurationImpl;

/**
 * @author Matous Jobanek
 * @version Nov 30, 2012
 */
public class LDAPAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter {

    @Inject
    private static EditorConfiguration configuration;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(LDAPAuthenticationFilter.class.getPackage()
            .toString());

    /**
     * {@inheritDoc}
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        if (!configuration.getIdentityTypes().contains(USER_IDENTITY_TYPES.LDAP)) {
            LOGGER.warn("The LDAP authentication is not allowed in the "
                    + EditorConfigurationImpl.DEFAULT_CONF_LOCATION + " file.");
            return null;
        }

        EditorUserAuthentication attemptAuthentication =
                (EditorUserAuthentication) super.attemptAuthentication(request, response);

        if (!attemptAuthentication.isAuthenticated() && attemptAuthentication.isToAdd()) {
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpCookies.UNKNOWN_ID_KEY, attemptAuthentication.getPrincipal());
            session.setAttribute(HttpCookies.NAME_KEY, attemptAuthentication.getDisplayName());
            session.setAttribute(HttpCookies.IDENTITY_TYPE, USER_IDENTITY_TYPES.LDAP);
            throw new UsernameNotFoundException("");
        }
        return attemptAuthentication;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException,
            ServletException {
        HttpSession session = request.getSession(true);
        Object ldapId = session.getAttribute(HttpCookies.UNKNOWN_ID_KEY);

        if (ldapId != null) {
            SecurityUtils.redirectToRegisterPage(request, response);
        } else {
            super.unsuccessfulAuthentication(request, response, failed);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        if (!configuration.isLocalhost()) {
            super.successfulAuthentication(request, response, chain, authResult);
        } else {
            SecurityContextHolder.getContext().setAuthentication(authResult);
            response.sendRedirect(response.encodeRedirectURL(URLS.MAIN_PAGE + "?gwt.codesvr=127.0.0.1:9997"));
        }

    }
}
