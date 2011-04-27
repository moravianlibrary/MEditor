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
package cz.fi.muni.xkremser.editor.client.gin;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;

import cz.fi.muni.xkremser.editor.client.presenter.AppPresenter;
import cz.fi.muni.xkremser.editor.client.presenter.CreatePresenter;
import cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.presenter.FindMetadataPresenter;
import cz.fi.muni.xkremser.editor.client.presenter.HomePresenter;
import cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter;
import cz.fi.muni.xkremser.editor.client.presenter.UserPresenter;

// TODO: Auto-generated Javadoc
/**
 * The Interface EditorGinjector.
 */
@GinModules({ DispatchAsyncModule.class, EditorClientModule.class })
public interface EditorGinjector extends Ginjector {

	/**
	 * Gets the place manager.
	 * 
	 * @return the place manager
	 */
	PlaceManager getPlaceManager();

	/**
	 * Gets the proxy failure handler.
	 * 
	 * @return the proxy failure handler
	 */
	ProxyFailureHandler getProxyFailureHandler();

	/**
	 * Gets the event bus.
	 * 
	 * @return the event bus
	 */
	EventBus getEventBus();

	/*
	 * PRESENTERS
	 * 
	 * Provider<> if you're using @ProxyStandard or AsyncProvider<> if you're
	 * using @ProxyCodeSplit
	 */
	/**
	 * Gets the app presenter.
	 * 
	 * @return the app presenter
	 */
	Provider<AppPresenter> getAppPresenter();

	/**
	 * Gets the home presenter.
	 * 
	 * @return the home presenter
	 */
	AsyncProvider<HomePresenter> getHomePresenter();

	/**
	 * Gets the user presenter.
	 * 
	 * @return the user presenter
	 */
	AsyncProvider<UserPresenter> getUserPresenter();

	/**
	 * Gets the modify presenter.
	 * 
	 * @return the modify presenter
	 */
	AsyncProvider<ModifyPresenter> getModifyPresenter();

	AsyncProvider<FindMetadataPresenter> getFindMetadataPresenterPresenter();

	AsyncProvider<CreatePresenter> getCreatePresenterPresenter();

	/**
	 * Gets the digital object menu presenter presenter.
	 * 
	 * @return the digital object menu presenter presenter
	 */
	Provider<DigitalObjectMenuPresenter> getDigitalObjectMenuPresenterPresenter();
	// AsyncProvider<AboutUsPresenter> getAboutUsPresenter();

}