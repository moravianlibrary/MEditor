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
import cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.presenter.HomePresenter;
import cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter;

@GinModules({ DispatchAsyncModule.class, EditorClientModule.class })
public interface EditorGinjector extends Ginjector {

	PlaceManager getPlaceManager();

	ProxyFailureHandler getProxyFailureHandler();

	EventBus getEventBus();

	/*
	 * PRESENTERS
	 * 
	 * Provider<> if you're using @ProxyStandard or AsyncProvider<> if you're
	 * using @ProxyCodeSplit
	 */
	Provider<AppPresenter> getAppPresenter();

	AsyncProvider<HomePresenter> getHomePresenter();

	AsyncProvider<ModifyPresenter> getModifyPresenter();

	// AsyncProvider<DigitalObjectMenuPresenter>
	// getDigitalObjectMenuPresenterPresenter();
	Provider<DigitalObjectMenuPresenter> getDigitalObjectMenuPresenterPresenter();
	// AsyncProvider<AboutUsPresenter> getAboutUsPresenter();

}