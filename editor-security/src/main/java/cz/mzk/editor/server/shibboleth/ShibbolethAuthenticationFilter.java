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

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.config.EditorConfigurationImpl;

/**
 * @author Matous Jobanek
 * @version Nov 22, 2012
 */
public class ShibbolethAuthenticationFilter
        extends AbstractAuthenticationProcessingFilter {

    /** The configuration. */
    @Inject
    private static EditorConfiguration configuration;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ShibbolethAuthenticationFilter.class.getPackage()
            .toString());

    /**
     * @param defaultFilterProcessesUrl
     */
    protected ShibbolethAuthenticationFilter() {
        super("/shibboleth_spring_security_check");
    }

    @SuppressWarnings("unused")
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        if (!configuration.getIdentityTypes().contains(USER_IDENTITY_TYPES.SHIBBOLETH)) {
            LOGGER.warn("The LDAP authentication is not allowed in the "
                    + EditorConfigurationImpl.DEFAULT_CONF_LOCATION + " file.");
            return null;
        }

        RememberMeAuthenticationToken token;

        String shibbolethIdentifier = request.getHeader(configuration.getShibbolethUid());

        token =
                new RememberMeAuthenticationToken(ShibbolethAuthenticationFilter.class.getPackage()
                        .toString(), shibbolethIdentifier, new ArrayList<GrantedAuthority>());

        token.setDetails(authenticationDetailsSource.buildDetails(request));

        // delegate to the authentication provider
        Authentication authentication = this.getAuthenticationManager().authenticate(token);

        if (authentication.isAuthenticated()) {
            setLastUsername(token.getName(), request);
        }

        UsernamePasswordAuthenticationFilter f;
        return authentication;
    }

    @SuppressWarnings("deprecation")
    private void setLastUsername(String username, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null || getAllowSessionCreation()) {
            request.getSession()
                    .setAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY,
                                  username);
        }
    }

}
