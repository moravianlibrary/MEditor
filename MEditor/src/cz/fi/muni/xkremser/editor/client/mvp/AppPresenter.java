package cz.fi.muni.xkremser.editor.client.mvp;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

import cz.fi.muni.xkremser.editor.client.mvp.presenter.DigitalObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.mvp.presenter.HomePresenter;

public class AppPresenter {
	private HasWidgets treeContainer;
	private HasWidgets mainContainer;
	private final HomePresenter homePresenter;
	private final DigitalObjectMenuPresenter digitalObjectMenuPresenter;

	@Inject
	public AppPresenter(final DispatchAsync dispatcher, final HomePresenter homePresenter, final DigitalObjectMenuPresenter treePresenter,
			final DigitalObjectMenuPresenter digitalObjectMenuPresenter) {
		this.homePresenter = homePresenter;
		this.digitalObjectMenuPresenter = digitalObjectMenuPresenter;
		bind();
	}

	private void bind() {
		// eventBus.addHandler(AddContactEvent.TYPE,
		// new AddContactEventHandler() {
		// public void onAddContact(AddContactEvent event) {
		// doAddNewContact();
		// }
		// });
		//
		// eventBus.addHandler(EditContactEvent.TYPE,
		// new EditContactEventHandler() {
		// public void onEditContact(EditContactEvent event) {
		// doEditContact(event.getId());
		// }
		// });
		//
		// eventBus.addHandler(EditContactCancelledEvent.TYPE,
		// new EditContactCancelledEventHandler() {
		// public void onEditContactCancelled(EditContactCancelledEvent event) {
		// doEditContactCancelled();
		// }
		// });
		//
		// eventBus.addHandler(ContactUpdatedEvent.TYPE,
		// new ContactUpdatedEventHandler() {
		// public void onContactUpdated(ContactUpdatedEvent event) {
		// doContactUpdated();
		// }
		// });
	}

	private void showMain() {
		mainContainer.clear();
		treeContainer.clear();
		mainContainer.add(homePresenter.getDisplay().asWidget());
		treeContainer.add(digitalObjectMenuPresenter.getDisplay().asWidget());
	}

	public void go(final HasWidgets menuContainer, final HasWidgets mainContainer) {
		this.treeContainer = menuContainer;
		this.mainContainer = mainContainer;

		showMain();
	}

}