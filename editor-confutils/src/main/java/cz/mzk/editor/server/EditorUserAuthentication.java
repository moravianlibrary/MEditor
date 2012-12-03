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

package cz.mzk.editor.server;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;

/**
 * @author Matous Jobanek
 * @version Nov 14, 2012
 */
public class EditorUserAuthentication
        implements Authentication {

    private static final long serialVersionUID = -8271847980628620970L;

    private boolean authenticated;

    private final GrantedAuthority grantedAuthority;
    private final Authentication authentication;
    private final USER_IDENTITY_TYPES identityType;
    private boolean toAdd = false;
    private String displayName;

    /**
     * @param role
     * @param authentication
     */
    public EditorUserAuthentication(String role,
                                    Authentication authentication,
                                    USER_IDENTITY_TYPES identityType) {
        this.grantedAuthority = new SimpleGrantedAuthority(role);
        this.authentication = authentication;
        this.identityType = identityType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(grantedAuthority);
        return authorities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getCredentials() {
        return authentication.getCredentials();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getDetails() {
        return authentication.getDetails();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getPrincipal() {
        return authentication.getPrincipal();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;

    }

    /**
     * @return the identityType
     */
    public USER_IDENTITY_TYPES getIdentityType() {
        return identityType;
    }

    /**
     * @return the toAdd
     */
    public boolean isToAdd() {
        return toAdd;
    }

    /**
     * @param toAdd
     *        the toAdd to set
     */
    public void setToAdd(boolean toAdd) {
        this.toAdd = toAdd;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName
     *        the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
