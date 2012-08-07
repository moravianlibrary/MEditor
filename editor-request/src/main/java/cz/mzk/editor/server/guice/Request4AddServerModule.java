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

package cz.mzk.editor.server.guice;

import javax.inject.Inject;

import com.google.inject.Scopes;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.config.EditorConfigurationImpl;

// TODO: Auto-generated Javadoc
/**
 * Module which binds the handlers and configurations.
 */
public class Request4AddServerModule
        extends HandlerModule {

    /** The conf. */
    @Inject
    private EditorConfiguration conf;

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.guice.HandlerModule#configureHandlers()
     */
    @Override
    protected void configureHandlers() {

        if (conf == null)
            bind(EditorConfiguration.class).to(EditorConfigurationImpl.class).in(Scopes.SINGLETON);

    }
}