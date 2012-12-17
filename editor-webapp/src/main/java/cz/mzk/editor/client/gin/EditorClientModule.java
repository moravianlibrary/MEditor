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

package cz.mzk.editor.client.gin;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

import cz.mzk.editor.client.EditorPlaceManager;
import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.client.config.EditorClientConfiguration;
import cz.mzk.editor.client.config.EditorClientConfigurationImpl;
import cz.mzk.editor.client.dispatcher.CachingDispatchAsync;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.presenter.AppPresenter;
import cz.mzk.editor.client.presenter.CreateObjectMenuPresenter;
import cz.mzk.editor.client.presenter.CreateStructurePresenter;
import cz.mzk.editor.client.presenter.DigitalObjectMenuPresenter;
import cz.mzk.editor.client.presenter.FindMetadataPresenter;
import cz.mzk.editor.client.presenter.HomePresenter;
import cz.mzk.editor.client.presenter.ModifyPresenter;
import cz.mzk.editor.client.view.AppView;
import cz.mzk.editor.client.view.CreateObjectMenuView;
import cz.mzk.editor.client.view.CreateStructureView;
import cz.mzk.editor.client.view.DigitalObjectMenuView;
import cz.mzk.editor.client.view.FindMetadataView;
import cz.mzk.editor.client.view.HomeView;
import cz.mzk.editor.client.view.ModifyView;
import cz.mzk.editor.client.view.other.DCTab;

// TODO: Auto-generated Javadoc
/**
 * The Class EditorClientModule.
 */
public class EditorClientModule
        extends AbstractPresenterModule {

    /*
     * (non-Javadoc)
     * @see com.google.gwt.inject.client.AbstractGinModule#configure()
     */
    @Override
    protected void configure() {
        // Singletons
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
        bind(PlaceManager.class).to(EditorPlaceManager.class).in(Singleton.class);
        bind(RootPresenter.class).asEagerSingleton();
        //        bind(ProxyFailureHandler.class).to(DefaultProxyFailureHandler.class).in(Singleton.class);

        // ServerConstants
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.MEDIT_HOME);

        //i18n
        bind(LangConstants.class).toProvider(LangProvider.class).in(Singleton.class);

        // Presenters
        bindPresenter(AppPresenter.class,
                      AppPresenter.MyView.class,
                      AppView.class,
                      AppPresenter.MyProxy.class);
        bindPresenter(HomePresenter.class,
                      HomePresenter.MyView.class,
                      HomeView.class,
                      HomePresenter.MyProxy.class);
        bindPresenter(ModifyPresenter.class,
                      ModifyPresenter.MyView.class,
                      ModifyView.class,
                      ModifyPresenter.MyProxy.class);
        bindPresenter(CreateStructurePresenter.class,
                      CreateStructurePresenter.MyView.class,
                      CreateStructureView.class,
                      CreateStructurePresenter.MyProxy.class);
        bindPresenter(FindMetadataPresenter.class,
                      FindMetadataPresenter.MyView.class,
                      FindMetadataView.class,
                      FindMetadataPresenter.MyProxy.class);
        bindPresenter(DigitalObjectMenuPresenter.class,
                      DigitalObjectMenuPresenter.MyView.class,
                      DigitalObjectMenuView.class,
                      DigitalObjectMenuPresenter.MyProxy.class);
        bindPresenter(CreateObjectMenuPresenter.class,
                      CreateObjectMenuPresenter.MyView.class,
                      CreateObjectMenuView.class,
                      CreateObjectMenuPresenter.MyProxy.class);

        bind(CachingDispatchAsync.class);
        bind(EditorClientConfiguration.class).to(EditorClientConfigurationImpl.class);

        requestStaticInjection(DCTab.class);
        requestStaticInjection(DispatchCallback.class);

    }
}