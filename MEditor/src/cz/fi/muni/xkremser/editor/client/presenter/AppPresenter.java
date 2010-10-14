package cz.fi.muni.xkremser.editor.client.presenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class AppPresenter extends Presenter<AppPresenter.MyView, AppPresenter.MyProxy> {

	/**
	 * Use this in leaf presenters, inside their {@link #revealInParent} method.
	 */
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new Type<RevealContentHandler<?>>();

	// @ContentSlot
	// public static final Type<RevealContentHandler<?>> TYPE_SetTopContent = new
	// Type<RevealContentHandler<?>>();

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetLeftContent = new Type<RevealContentHandler<?>>();

	@ProxyStandard
	public interface MyProxy extends Proxy<AppPresenter> {

	}

	public interface MyView extends View {

	}

	DigitalObjectMenuPresenter leftPresenter;

	// private final HomePresenter homePresenter;
	// private final DigitalObjectMenuPresenter treePresenter;

	@Inject
	// public AppPresenter(final DispatchAsync dispatcher, final HomePresenter
	// homePresenter, final DigitalObjectMenuPresenter treePresenter,
	// final DigitalObjectMenuPresenter digitalObjectMenuPresenter, final
	// EditorClientConfiguration config) {
	public AppPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DigitalObjectMenuPresenter leftPresenter) {
		// this.homePresenter = homePresenter;
		// this.treePresenter = treePresenter;
		// this.config = config;
		super(eventBus, view, proxy);
		this.leftPresenter = leftPresenter;
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

}