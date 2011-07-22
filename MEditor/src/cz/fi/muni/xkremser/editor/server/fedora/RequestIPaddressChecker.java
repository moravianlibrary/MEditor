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

package cz.fi.muni.xkremser.editor.server.fedora;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class RequestIPaddressChecker.
 */
public class RequestIPaddressChecker
        implements IPaddressChecker {

    private static final Logger LOGGER = Logger.getLogger(RequestIPaddressChecker.class);

    /** The configuration. */
    @Inject
    private EditorConfiguration configuration;

    /** The provider. */
    private final Provider<HttpServletRequest> provider;

    /**
     * Instantiates a new request i paddress checker.
     * 
     * @param provider
     *        the provider
     */
    @Inject
    public RequestIPaddressChecker(Provider<HttpServletRequest> provider) {
        super();
        this.provider = provider;
        LOGGER.debug("provider is '" + provider + "'");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.IPaddressChecker#privateVisitor
     * ()
     */
    @Override
    public boolean privateVisitor() {
        String[] patterns = configuration.getUserAccessPatterns();
        return checkPatterns(patterns);
    }

    /**
     * Check patterns.
     * 
     * @param patterns
     *        the patterns
     * @return true, if successful
     */
    private boolean checkPatterns(String[] patterns) {
        HttpServletRequest httpServletRequest = this.provider.get();
        String remoteAddr = httpServletRequest.getRemoteAddr();
        if (patterns != null) {
            for (String regex : patterns) {
                if (remoteAddr.matches(regex)) return true;
            }
        }
        LOGGER.info("Remote address is == " + remoteAddr);
        return false;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.IPaddressChecker#localHostVisitor
     * ()
     */
    @Override
    public boolean localHostVisitor() {
        String[] patterns = configuration.getAdminAccessPatterns();
        return checkPatterns(patterns);
    }

}
