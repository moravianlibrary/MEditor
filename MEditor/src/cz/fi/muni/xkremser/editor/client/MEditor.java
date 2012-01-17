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

package cz.fi.muni.xkremser.editor.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.DelayedBindRegistry;
import com.smartgwt.client.util.SC;

import cz.fi.muni.xkremser.editor.client.gin.EditorGinjector;

import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAndUpdateDBSchemaAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAndUpdateDBSchemaResult;

// TODO: Auto-generated Javadoc
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MEditor
        implements EntryPoint {

    /** The injector. */
    private final EditorGinjector injector = GWT.create(EditorGinjector.class);
    private DispatchAsync dispatcher;

    /*
     * (non-Javadoc)
     * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
     */
    @Override
    public void onModuleLoad() {
        DelayedBindRegistry.bind(injector);
        initializeDatabaseIfNeeded(injector.getDispatcher());
        injector.getPlaceManager().revealCurrentPlace();
        // remove progressbar
        RootPanel.getBodyElement().removeChild(RootPanel.get("loadingWrapper").getElement());
    }

    /**
     * Redirect.
     * 
     * @param url
     *        the url
     */
    public static native void redirect(String url)/*-{
		$wnd.location = url;
    }-*/;

    public static native void langRefresh(String locale)/*-{
		var pos = $wnd.location.search.indexOf('&locale=');
		var params = $wnd.location.search;
		if (pos == -1) {
			$wnd.location.search = params + '&locale=' + locale;
		} else {
			$wnd.location.search = params.substring(0, pos) + '&locale='
					+ locale + params.substring(pos + 13, params.length);
		}
    }-*/;

    public static void initializeDatabaseIfNeeded(DispatchAsync dispatcher) {
        dispatcher.execute(new CheckAndUpdateDBSchemaAction(),
                           new AsyncCallback<CheckAndUpdateDBSchemaResult>() {

                               @Override
                               public void onSuccess(CheckAndUpdateDBSchemaResult result) {
                                   if (result.isSuccess()) {
                                       SC.say("DB has been successfully updated to version "
                                               + result.getVersion());
                                   }
                               }

                               @Override
                               public void onFailure(Throwable caught) {
                                   SC.warn(caught.getMessage());
                               }
                           });
    }
}
