
package cz.mzk.editor.server.shibboleth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.server.EditorUserAuthentication;
import cz.mzk.editor.server.URLS;

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
public class ShibbolethAuthenticationProvider
        implements AuthenticationProvider {

    /**
     * {@inheritDoc}
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String openId = (String) authentication.getPrincipal();
        Long userId = new Long(-1);

        if (openId != null) userId = ShibbolethClient.getUserId(openId);

        if (userId < 0) {
            throw new BadCredentialsException("Invalid login or password");
        } else if (userId == 0) {
            throw new UsernameNotFoundException("Username not  found! Please register <a href=\""
                    + (URLS.LOCALHOST() ? "info.html?gwt.codesvr=127.0.0.1:9997" : URLS.INFO_PAGE)
                    + "\">here</a>");
        } else {

            Authentication customAuthentication =
                    new EditorUserAuthentication("ROLE_USER", authentication, USER_IDENTITY_TYPES.OPEN_ID);
            customAuthentication.setAuthenticated(true);
            return customAuthentication;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean supports(Class<?> authentication) {
        return RememberMeAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
