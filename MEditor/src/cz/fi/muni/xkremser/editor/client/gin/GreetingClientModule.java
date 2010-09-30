package cz.fi.muni.xkremser.editor.client.gin;

import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.gin.AbstractPresenterModule;
import net.customware.gwt.presenter.client.place.PlaceManager;

import com.google.inject.Singleton;

import cz.fi.muni.xkremser.editor.client.CachingDispatchAsync;
import cz.fi.muni.xkremser.editor.client.mvp.AppPresenter;
import cz.fi.muni.xkremser.editor.client.mvp.DigitalObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.mvp.DigitalObjectMenuView;
import cz.fi.muni.xkremser.editor.client.mvp.GreetingPresenter;
import cz.fi.muni.xkremser.editor.client.mvp.GreetingResponsePresenter;
import cz.fi.muni.xkremser.editor.client.mvp.GreetingResponseView;
import cz.fi.muni.xkremser.editor.client.mvp.GreetingView;

public class GreetingClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);
		bind(PlaceManager.class).in(Singleton.class);

		bindPresenter(GreetingPresenter.class, GreetingPresenter.Display.class, GreetingView.class);
		bindPresenter(DigitalObjectMenuPresenter.class, DigitalObjectMenuPresenter.Display.class, DigitalObjectMenuView.class);
		bindPresenter(GreetingResponsePresenter.class, GreetingResponsePresenter.Display.class, GreetingResponseView.class);

		bind(AppPresenter.class).in(Singleton.class);
		bind(CachingDispatchAsync.class);
	}
}