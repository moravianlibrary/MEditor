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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.server.EditorUserAuthentication;
import cz.mzk.editor.server.HttpCookies;
import cz.mzk.editor.server.SecurityUtils;

/**
 * @author Matous Jobanek
 * @version Nov 30, 2012
 */
public class LDAPAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter {

    /**
     * {@inheritDoc}
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
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

}
