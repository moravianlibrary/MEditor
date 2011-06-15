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

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.DefaultProxyFailureHandler;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

import cz.fi.muni.xkremser.editor.client.EditorPlaceManager;
import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration;
import cz.fi.muni.xkremser.editor.client.config.EditorClientConfigurationImpl;
import cz.fi.muni.xkremser.editor.client.dispatcher.CachingDispatchAsync;
import cz.fi.muni.xkremser.editor.client.presenter.AppPresenter;
import cz.fi.muni.xkremser.editor.client.presenter.CreatePresenter;
import cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.presenter.FindMetadataPresenter;
import cz.fi.muni.xkremser.editor.client.presenter.HomePresenter;
import cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter;
import cz.fi.muni.xkremser.editor.client.presenter.UserPresenter;
import cz.fi.muni.xkremser.editor.client.view.AppView;
import cz.fi.muni.xkremser.editor.client.view.CreateView;
import cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView;
import cz.fi.muni.xkremser.editor.client.view.FindMetadataView;
import cz.fi.muni.xkremser.editor.client.view.HomeView;
import cz.fi.muni.xkremser.editor.client.view.ModifyView;
import cz.fi.muni.xkremser.editor.client.view.UserView;
import cz.fi.muni.xkremser.editor.client.view.tab.DCTab;
import cz.fi.muni.xkremser.editor.client.view.tab.TabUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class EditorClientModule.
 */
public class EditorClientModule extends AbstractPresenterModule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.inject.client.AbstractGinModule#configure()
	 */
	@Override
	protected void configure() {
		// Singletons
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
		bind(PlaceManager.class).to(EditorPlaceManager.class).in(Singleton.class);
		bind(RootPresenter.class).asEagerSingleton();
		bind(ProxyFailureHandler.class).to(DefaultProxyFailureHandler.class).in(Singleton.class);

		// ServerConstants
		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.HOME);
		bind(LangConstants.class).toProvider(LangProvider.class).in(Singleton.class);

		bindPresenter(AppPresenter.class, AppPresenter.MyView.class, AppView.class, AppPresenter.MyProxy.class);
		bindPresenter(HomePresenter.class, HomePresenter.MyView.class, HomeView.class, HomePresenter.MyProxy.class);
		bindPresenter(UserPresenter.class, UserPresenter.MyView.class, UserView.class, UserPresenter.MyProxy.class);
		bindPresenter(ModifyPresenter.class, ModifyPresenter.MyView.class, ModifyView.class, ModifyPresenter.MyProxy.class);
		bindPresenter(CreatePresenter.class, CreatePresenter.MyView.class, CreateView.class, CreatePresenter.MyProxy.class);
		bindPresenter(FindMetadataPresenter.class, FindMetadataPresenter.MyView.class, FindMetadataView.class, FindMetadataPresenter.MyProxy.class);
		bindPresenter(DigitalObjectMenuPresenter.class, DigitalObjectMenuPresenter.MyView.class, DigitalObjectMenuView.class,
				DigitalObjectMenuPresenter.MyProxy.class);

		bind(CachingDispatchAsync.class);
		bind(EditorClientConfiguration.class).to(EditorClientConfigurationImpl.class);

		requestStaticInjection(TabUtils.class);
		requestStaticInjection(DCTab.class);

	}
}