package cz.fi.muni.xkremser.editor.client.mvp;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.mvp.presenter.DigitalObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.mvp.presenter.HomePresenter;

public class AppPresenter {
	private HasWidgets mainContainer;
	private final HomePresenter homePresenter;
	private final DigitalObjectMenuPresenter treePresenter;

	@Inject
	public AppPresenter(final DispatchAsync dispatcher, final HomePresenter homePresenter, final DigitalObjectMenuPresenter treePresenter,
			final DigitalObjectMenuPresenter digitalObjectMenuPresenter) {
		this.homePresenter = homePresenter;
		this.treePresenter = treePresenter;
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

		// layouting

		VLayout main = new VLayout();
		// main.setLayoutMargin(5);
		main.setWidth100();
		main.setHeight100();
		HLayout top = new HLayout();
		top.setWidth100();
		top.setHeight(100);
		top.setBorder("1px solid");
		main.addMember(top);

		HLayout underTop = new HLayout();
		underTop.setWidth100();
		underTop.setHeight100();
		// underTop.setShowResizeBar(true);
		main.addMember(underTop);

		underTop.addMember(treePresenter.getDisplay().asWidget());
		underTop.addMember(homePresenter.getDisplay().asWidget());

		// main.draw();
		mainContainer.add(main);

		// sideNav.addLeafClickHandler(new LeafClickHandler() {
		// @Override
		// public void onLeafClick(LeafClickEvent event) {
		// TreeNode node = event.getLeaf();
		// // showSample(node);
		// }
		// });
		// main.addMember(hLayout);
		// main.draw();

		// mainContainer.add(homePresenter.getDisplay().asWidget());

	}

	public void go(final HasWidgets mainContainer) {
		this.mainContainer = mainContainer;

		showMain();
	}

}