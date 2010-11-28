/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

import cz.fi.muni.xkremser.editor.client.gin.DefaultPlace;

// TODO: Auto-generated Javadoc
/**
 * The Class EditorPlaceManager.
 */
public class EditorPlaceManager extends PlaceManagerImpl {

	/** The default place request. */
	private final PlaceRequest defaultPlaceRequest;

	/**
	 * Instantiates a new editor place manager.
	 * 
	 * @param eventBus
	 *          the event bus
	 * @param tokenFormatter
	 *          the token formatter
	 * @param defaultNameToken
	 *          the default name token
	 */
	@Inject
	public EditorPlaceManager(final EventBus eventBus, final TokenFormatter tokenFormatter, @DefaultPlace String defaultNameToken) {
		super(eventBus, tokenFormatter);

		this.defaultPlaceRequest = new PlaceRequest(NameTokens.HOME);
		// this.defaultPlaceRequest = new PlaceRequest("home");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.proxy.PlaceManager#revealDefaultPlace()
	 */
	@Override
	public void revealDefaultPlace() {
		revealPlace(defaultPlaceRequest);
	}

	// @Override
	// public void revealErrorPlace(String invalidHistoryToken) {
	// super.revealErrorPlace(invalidHistoryToken);
	// }
}