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
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

import cz.fi.muni.xkremser.editor.client.gin.EditorGinjector;

// TODO: Auto-generated Javadoc
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MEditor implements EntryPoint {

	/** The injector. */
	private final EditorGinjector injector = GWT.create(EditorGinjector.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	@Override
	public void onModuleLoad() {

		// IButton adminButton = new IButton("Admin Console");
		// adminButton.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// com.smartgwtee.tools.client.SCEE.openDataSourceConsole();
		// }
		// });
		// adminButton.draw();
		// FileLoader.cacheImgs(skinImgDir, baseImageURLs)

		DelayedBindRegistry.bind(injector);

		// UserAuthenticationAsync auth = (UserAuthenticationAsync)
		// GWT.create(UserAuthentication.class);
		// ServiceDefTarget endpoint = (ServiceDefTarget) auth;
		// endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "auth");
		//
		// auth.login("", new AsyncCallback<LoginResult>() {
		//
		// @Override
		// public void onSuccess(LoginResult result) {
		// redirect(result.getUrl());
		// }
		//
		// @Override
		// public void onFailure(Throwable caught) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		injector.getPlaceManager().revealCurrentPlace();
		RootPanel.getBodyElement().removeChild(RootPanel.get("loadingWrapper").getElement());
	}

	/**
	 * Redirect.
	 * 
	 * @param url
	 *          the url
	 */
	public static native void redirect(String url)/*-{
		$wnd.location = url;
	}-*/;

	public static native void langRefresh(String locale)/*-{
		var pos = $wnd.location.search.indexOf('&locale=');
		var params = $wnd.location.search;
		if (pos == -1) {
			$wnd.location.search = params + '&locale=' + locale;
			$wnd.alert('@1@' + params);
		} else {
			$wnd.location.search = params.substring(0, pos) + '&locale='
					+ locale + params.substring(pos + 13, params.length);
			$wnd.alert('@2@' + params);
		}
	}-*/;
}
