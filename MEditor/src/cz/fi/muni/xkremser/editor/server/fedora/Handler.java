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

import java.io.IOException;

import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class Handler.
 */
public class Handler
        extends URLStreamHandler {

    /** The fedora access. */
    private final FedoraAccess fedoraAccess;

    /**
     * Instantiates a new handler.
     * 
     * @param fedoraAccess
     *        the fedora access
     */
    @Inject
    public Handler(FedoraAccess fedoraAccess) {
        super();
        this.fedoraAccess = fedoraAccess;
    }

    /*
     * (non-Javadoc)
     * @see java.net.URLStreamHandler#openConnection(java.net.URL)
     */
    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        return new FedoraURLConnection(u, fedoraAccess);
    }

    /*
     * (non-Javadoc)
     * @see java.net.URLStreamHandler#openConnection(java.net.URL,
     * java.net.Proxy)
     */
    @Override
    protected URLConnection openConnection(URL u, Proxy p) throws IOException {
        // TODO Auto-generated method stub
        return super.openConnection(u, p);
    }

}
