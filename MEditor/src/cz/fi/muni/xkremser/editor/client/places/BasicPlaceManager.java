package cz.fi.muni.xkremser.editor.client.places;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.DefaultPlaceManager;
import net.customware.gwt.presenter.client.place.TokenFormatter;

import com.google.inject.Inject;

public class BasicPlaceManager extends DefaultPlaceManager {

	@Inject
	public BasicPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter, ShowPersonPlace spp, ListTeamsPlace ltp) {
		super(eventBus, tokenFormatter, spp, ltp);
	}

}