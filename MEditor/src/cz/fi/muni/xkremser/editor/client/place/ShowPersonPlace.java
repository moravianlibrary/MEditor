package cz.fi.muni.xkremser.editor.client.place;

import net.customware.gwt.presenter.client.gin.ProvidedPresenterPlace;
import net.customware.gwt.presenter.client.place.PlaceRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;

import cz.fi.muni.xkremser.editor.client.mvp.presenter.HomePresenter;

public class ShowPersonPlace extends ProvidedPresenterPlace<HomePresenter> {

	public static final String NAME = "ShowPerson";
	public static final String PARAM_ID = "id";

	@Inject
	public ShowPersonPlace(Provider<HomePresenter> presenter) {
		super(presenter);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected void preparePresenter(PlaceRequest request, HomePresenter presenter) {

		String id = request.getParameter(PARAM_ID, null);
		// presenter.setId(id);
	}

	@Override
	protected PlaceRequest prepareRequest(PlaceRequest request, HomePresenter presenter) {

		// if (presenter.getId() != null) {
		// request = request.with(PARAM_ID, presenter.getId());
		// }

		return request;
	}

}