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

package cz.mzk.editor.server.janrain;

import java.io.IOException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;

import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.server.EditorUserAuthentication;
import cz.mzk.editor.server.HttpCookies;
import cz.mzk.editor.server.SecurityUtils;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.config.EditorConfigurationImpl;

/**
 * @author Matous Jobanek
 * @version Nov 22, 2012
 */
public class JanrainAuthenticationFilter
        extends AbstractAuthenticationProcessingFilter {

    @Inject
    private static EditorConfiguration configuration;

    /**
     * @param defaultFilterProcessesUrl
     */
    protected JanrainAuthenticationFilter() {
        super("/janrain_spring_security_check");
    }

    public static final String DEFAULT_CLAIMED_IDENTITY_FIELD = "token";

    private RpxConsumer consumer;
    private Set<String> returnToUrlParameters = Collections.emptySet();
    private String rpxBaseUrl = "https://rpxnow.com";
    private String rpxApiKey = null;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(JanrainAuthenticationFilter.class.getPackage()
            .toString());

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();

        if (consumer == null) {
            consumer = new RpxConsumerImpl();
        }

        if (returnToUrlParameters.isEmpty() && getRememberMeServices() instanceof AbstractRememberMeServices) {
            returnToUrlParameters = new HashSet<String>();
            returnToUrlParameters.add(((AbstractRememberMeServices) getRememberMeServices()).getParameter());
        }

    }

    public void setConsumer(RpxConsumer consumer) {
        this.consumer = consumer;
    }

    public void setRpxApiKey(String rpxApiKey) {
        this.rpxApiKey = rpxApiKey;
    }

    public void setRpxBaseUrl(String rpxBaseUrl) {
        this.rpxBaseUrl = rpxBaseUrl;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        if (!configuration.getIdentityTypes().contains(USER_IDENTITY_TYPES.OPEN_ID)) {
            LOGGER.warn("The OpenID authentication is not allowed in the "
                    + EditorConfigurationImpl.DEFAULT_CONF_LOCATION + " file.");
            return null;
        }

        OpenIDAuthenticationToken token;

        this.rpxApiKey = configuration.getOpenIDApiKey();
        this.rpxBaseUrl = configuration.getOpenIDApiURL();

        token = consumer.consume(rpxBaseUrl, rpxApiKey, request);

        token.setDetails(authenticationDetailsSource.buildDetails(request));

        // delegate to the authentication provider
        EditorUserAuthentication authentication =
                (EditorUserAuthentication) this.getAuthenticationManager().authenticate(token);
        if (authentication != null) {
            if (authentication.isAuthenticated()) {
                setLastUsername(token.getIdentityUrl(), request);
            } else if (authentication.isToAdd()) {
                HttpSession session = request.getSession(true);
                session.setAttribute(HttpCookies.UNKNOWN_ID_KEY, token.getIdentityUrl());
                session.setAttribute(HttpCookies.NAME_KEY, token.getAttributes().get(1).getValues().get(0));
                session.setAttribute(HttpCookies.IDENTITY_TYPE, USER_IDENTITY_TYPES.OPEN_ID);
                throw new UsernameNotFoundException("");
            }
        }
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException,
            ServletException {
        HttpSession session = request.getSession(true);
        Object openId = session.getAttribute(HttpCookies.UNKNOWN_ID_KEY);
        if (openId != null) {
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

        super.successfulAuthentication(request, response, chain, authResult);
        if (configuration.isLocalhost()) SecurityUtils.redirectToHostDebugMode(request, response);

    }

}
