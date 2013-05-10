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

package cz.mzk.editor.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.DelayedBindRegistry;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.smartgwt.client.util.SC;

import cz.mzk.editor.client.gin.EditorGinjector;
import cz.mzk.editor.client.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MEditor
        implements EntryPoint {

    /** The injector. */
    private final EditorGinjector injector = GWT.create(EditorGinjector.class);

    /*
     * (non-Javadoc)
     * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
     */
    @Override
    public void onModuleLoad() {
        DelayedBindRegistry.bind(injector);

        if (Location.getQueryString().startsWith("?pids=uuid:")
                && (Location.getPath() == null || "/meditor/".equals(Location.getPath()))) {

            injector.getPlaceManager()
                    .revealPlace(new PlaceRequest(NameTokens.MODIFY).with(Constants.URL_PARAM_UUID, Location
                            .getQueryString().substring(Location.getQueryString().indexOf("uuid:"))));
        } else {
            injector.getPlaceManager().revealCurrentPlace();
        }
        RootPanel.getBodyElement().removeChild(RootPanel.get("loadingWrapper")
                .getElement());
    }

}
