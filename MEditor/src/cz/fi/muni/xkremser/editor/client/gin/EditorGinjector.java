/**
 * Metadata Editor
 * @author Jiri Kremser
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
import cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter;
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

	AsyncProvider<UserPresenter> getUserPresenter();

	/**
	 * Gets the modify presenter.
	 * 
	 * @return the modify presenter
	 */
	AsyncProvider<ModifyPresenter> getModifyPresenter();

	// AsyncProvider<DigitalObjectMenuPresenter>
	// getDigitalObjectMenuPresenterPresenter();
	/**
	 * Gets the digital object menu presenter presenter.
	 * 
	 * @return the digital object menu presenter presenter
	 */
	Provider<DigitalObjectMenuPresenter> getDigitalObjectMenuPresenterPresenter();
	// AsyncProvider<AboutUsPresenter> getAboutUsPresenter();

}