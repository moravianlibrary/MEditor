package cz.fi.muni.xkremser.editor.client.gin;

import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.gin.AbstractPresenterModule;
import net.customware.gwt.presenter.client.place.ParameterTokenFormatter;
import net.customware.gwt.presenter.client.place.PlaceManager;
import net.customware.gwt.presenter.client.place.TokenFormatter;

import com.google.inject.Singleton;

import cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration;
import cz.fi.muni.xkremser.editor.client.config.EditorClientConfigurationImpl;
import cz.fi.muni.xkremser.editor.client.dispatcher.CachingDispatchAsync;
import cz.fi.muni.xkremser.editor.client.mvp.AppPresenter;
import cz.fi.muni.xkremser.editor.client.mvp.presenter.DigitalObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.mvp.presenter.GreetingResponsePresenter;
import cz.fi.muni.xkremser.editor.client.mvp.presenter.HomePresenter;
import cz.fi.muni.xkremser.editor.client.mvp.view.DigitalObjectMenuView;
import cz.fi.muni.xkremser.editor.client.mvp.view.GreetingResponseView;
import cz.fi.muni.xkremser.editor.client.mvp.view.HomeView;
import cz.fi.muni.xkremser.editor.client.place.BasicPlaceManager;

public class EditorClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);

		bindPresenter(HomePresenter.class, HomePresenter.Display.class, HomeView.class);
		bindPresenter(DigitalObjectMenuPresenter.class, DigitalObjectMenuPresenter.Display.class, DigitalObjectMenuView.class);
		bindPresenter(GreetingResponsePresenter.class, GreetingResponsePresenter.Display.class, GreetingResponseView.class);

		bind(AppPresenter.class).in(Singleton.class);
		bind(CachingDispatchAsync.class);

		bind(TokenFormatter.class).to(ParameterTokenFormatter.class);
		bind(PlaceManager.class).to(BasicPlaceManager.class).asEagerSingleton();

		bind(EditorClientConfiguration.class).to(EditorClientConfigurationImpl.class);

	}
}