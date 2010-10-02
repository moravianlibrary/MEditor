package cz.fi.muni.xkremser.editor.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

import cz.fi.muni.xkremser.editor.client.gin.EditorGinjector;
import cz.fi.muni.xkremser.editor.client.mvp.AppPresenter;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MEditor implements EntryPoint {
	private final EditorGinjector injector = GWT.create(EditorGinjector.class);

	@Override
	public void onModuleLoad() {
		final AppPresenter appPresenter = injector.getAppPresenter();

		appPresenter.go(RootPanel.get());

		injector.getPlaceManager().fireCurrentPlace();
	}
}
