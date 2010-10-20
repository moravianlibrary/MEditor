package cz.fi.muni.xkremser.editor.client;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

import cz.fi.muni.xkremser.editor.client.gin.DefaultPlace;

public class EditorPlaceManager extends PlaceManagerImpl {
	private final PlaceRequest defaultPlaceRequest;

	@Inject
	public EditorPlaceManager(final EventBus eventBus, final TokenFormatter tokenFormatter, @DefaultPlace String defaultNameToken) {
		super(eventBus, tokenFormatter);

		// this.defaultPlaceRequest = new PlaceRequest("modify");
		this.defaultPlaceRequest = new PlaceRequest("home");
	}

	@Override
	public void revealDefaultPlace() {
		revealPlace(defaultPlaceRequest);
	}

	// @Override
	// public void revealErrorPlace(String invalidHistoryToken) {
	// super.revealErrorPlace(invalidHistoryToken);
	// }
}