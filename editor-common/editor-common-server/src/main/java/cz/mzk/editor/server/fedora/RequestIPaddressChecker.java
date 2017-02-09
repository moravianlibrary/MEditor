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

package cz.mzk.editor.server.fedora;

import cz.mzk.editor.server.config.EditorConfiguration;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class RequestIPaddressChecker.
 */
@Component
public class RequestIPaddressChecker
        implements IPaddressChecker {

    private static final Logger LOGGER = Logger.getLogger(RequestIPaddressChecker.class);

    /** The configuration. */
    @Inject
    private EditorConfiguration configuration;

    /** The provider. */
    private final HttpServletRequest httpServletRequest;

    /**
     * Instantiates a new request i paddress checker.
     * 
     * @param provider
     *        the provider
     */
    @Inject
    public RequestIPaddressChecker(HttpServletRequest httpServletRequest) {
        super();
        this.httpServletRequest = httpServletRequest;
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.server.fedora.IPaddressChecker#privateVisitor ()
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
     * @see cz.mzk.editor.server.fedora.IPaddressChecker#localHostVisitor ()
     */
    @Override
    public boolean localHostVisitor() {
        String[] patterns = configuration.getAdminAccessPatterns();
        return checkPatterns(patterns);
    }

}
