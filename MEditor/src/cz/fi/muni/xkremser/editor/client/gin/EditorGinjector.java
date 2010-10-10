package cz.fi.muni.xkremser.editor.client.gin;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;

import cz.fi.muni.xkremser.editor.client.presenter.AppPresenter;
import cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.presenter.HomePresenter;

@GinModules({ /* StandardDispatchModule.class, */EditorClientModule.class })
public interface EditorGinjector extends Ginjector {

	AppPresenter getAppPresenter();

	PlaceManager getPlaceManager();

	ProxyFailureHandler getProxyFailureHandler();

	/*
	 * PRESENTERS
	 * 
	 * Provider<> if you're using @ProxyStandard or AsyncProvider<> if you're
	 * using @ProxyCodeSplit
	 */

	Provider<AppPresenter> getAppPagePresenter();

	AsyncProvider<HomePresenter> getHomePresenter();

	AsyncProvider<DigitalObjectMenuPresenter> getDigitalObjectMenuPresenterPresenter();
	// AsyncProvider<AboutUsPresenter> getAboutUsPresenter();

}