/*
 * Metadata Editor
 * @author Matous Jobanek
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
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
import cz.mzk.editor.client.presenter.AdminHomePresenter;
import cz.mzk.editor.client.presenter.AdminMenuPresenter;
import cz.mzk.editor.client.presenter.AdminPresenter;
import cz.mzk.editor.client.presenter.HistoryPresenter;
import cz.mzk.editor.client.presenter.MyAcountPresenter;
import cz.mzk.editor.client.presenter.StatisticsPresenter;
import cz.mzk.editor.client.presenter.StoredAndLocksPresenter;
import cz.mzk.editor.client.presenter.UserPresenter;
import cz.mzk.editor.client.view.AdminHomeView;
import cz.mzk.editor.client.view.AdminMenuView;
import cz.mzk.editor.client.view.AdminView;
import cz.mzk.editor.client.view.HistoryView;
import cz.mzk.editor.client.view.MyAcountView;
import cz.mzk.editor.client.view.StatisticsView;
import cz.mzk.editor.client.view.StoredAndLocksView;
import cz.mzk.editor.client.view.UserView;

// TODO: Auto-generated Javadoc
/**
 * The Class EditorClientModule.
 */
public class AdminClientModule
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

        // ServerConstants
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.ADMIN_HOME);

        //i18n
        bind(LangConstants.class).toProvider(LangProvider.class).in(Singleton.class);

        // Presenters
        bindPresenter(AdminPresenter.class,
                      AdminPresenter.MyView.class,
                      AdminView.class,
                      AdminPresenter.MyProxy.class);
        bindPresenter(AdminHomePresenter.class,
                      AdminHomePresenter.MyView.class,
                      AdminHomeView.class,
                      AdminHomePresenter.MyProxy.class);
        bindPresenter(AdminMenuPresenter.class,
                      AdminMenuPresenter.MyView.class,
                      AdminMenuView.class,
                      AdminMenuPresenter.MyProxy.class);
        bindPresenter(HistoryPresenter.class,
                      HistoryPresenter.MyView.class,
                      HistoryView.class,
                      HistoryPresenter.MyProxy.class);
        bindPresenter(UserPresenter.class,
                      UserPresenter.MyView.class,
                      UserView.class,
                      UserPresenter.MyProxy.class);
        bindPresenter(StoredAndLocksPresenter.class,
                      StoredAndLocksPresenter.MyView.class,
                      StoredAndLocksView.class,
                      StoredAndLocksPresenter.MyProxy.class);
        bindPresenter(MyAcountPresenter.class,
                      MyAcountPresenter.MyView.class,
                      MyAcountView.class,
                      MyAcountPresenter.MyProxy.class);
        bindPresenter(StatisticsPresenter.class,
                      StatisticsPresenter.MyView.class,
                      StatisticsView.class,
                      StatisticsPresenter.MyProxy.class);

        bind(CachingDispatchAsync.class);
        bind(EditorClientConfiguration.class).to(EditorClientConfigurationImpl.class);

        requestStaticInjection(DispatchCallback.class);

    }
}