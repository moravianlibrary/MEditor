package cz.fi.muni.xkremser.editor.client.mvp;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

public class AppPresenter {
	private HasWidgets container;
	private final HomePresenter greetingPresenter;
	private final DigitalObjectMenuPresenter digitalObjectMenuPresenter;

	@Inject
	public AppPresenter(final DispatchAsync dispatcher, final HomePresenter greetingPresenter, final DigitalObjectMenuPresenter digitalObjectMenuPresenter) {
		this.greetingPresenter = greetingPresenter;
		this.digitalObjectMenuPresenter = digitalObjectMenuPresenter;
	}

	private void showMain() {
		container.clear();
		container.add(greetingPresenter.getDisplay().asWidget());
		// container.add(digitalObjectMenuPresenter.getDisplay().asWidget());
	}

	public void go(final HasWidgets container) {
		this.container = container;

		showMain();
	}

}