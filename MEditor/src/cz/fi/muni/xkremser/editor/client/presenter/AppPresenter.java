package cz.fi.muni.xkremser.editor.client.presenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AppPresenter extends Presenter<AppPresenter.MyView, AppPresenter.MyProxy> {

	/**
	 * Use this in leaf presenters, inside their {@link #revealInParent} method.
	 */
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new Type<RevealContentHandler<?>>();

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetTopContent = new Type<RevealContentHandler<?>>();

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetLeftContent = new Type<RevealContentHandler<?>>();

	@ProxyStandard
	public interface MyProxy extends Proxy<AppPresenter> {

	}

	public interface MyView extends View {

	}

	private HasWidgets mainContainer;

	// private final HomePresenter homePresenter;
	// private final DigitalObjectMenuPresenter treePresenter;
	// private final EditorClientConfiguration config;

	@Inject
	// public AppPresenter(final DispatchAsync dispatcher, final HomePresenter
	// homePresenter, final DigitalObjectMenuPresenter treePresenter,
	// final DigitalObjectMenuPresenter digitalObjectMenuPresenter, final
	// EditorClientConfiguration config) {
	public AppPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy) {
		// this.homePresenter = homePresenter;
		// this.treePresenter = treePresenter;
		// this.config = config;
		super(eventBus, view, proxy);
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
		top.setHeight(90);
		top.setBorder("1px solid");
		main.addMember(top);

		HLayout underTop = new HLayout();
		underTop.setWidth100();
		underTop.setHeight100();
		// underTop.setShowResizeBar(true);
		main.addMember(underTop);

		// underTop.addMember(treePresenter.getWidget().asWidget());
		// underTop.addMember(homePresenter.getDisplay().asWidget());

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

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

}