package cz.fi.muni.xkremser.editor.client.places;

import net.customware.gwt.presenter.client.gin.ProvidedPresenterPlace;

import com.google.inject.Inject;
import com.google.inject.Provider;

import cz.fi.muni.xkremser.editor.client.mvp.presenter.GreetingResponsePresenter;

public class ListTeamsPlace extends ProvidedPresenterPlace<GreetingResponsePresenter> {

	public static final String NAME = "ListTeams";

	@Inject
	public ListTeamsPlace(Provider<GreetingResponsePresenter> presenter) {
		super(presenter);
	}

	@Override
	public String getName() {
		return NAME;
	}

}
