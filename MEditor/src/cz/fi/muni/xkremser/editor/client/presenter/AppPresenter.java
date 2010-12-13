/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.presenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

import cz.fi.muni.xkremser.editor.client.MEditor;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.view.AppView.MyUiHandlers;
import cz.fi.muni.xkremser.editor.shared.rpc.action.LogoutAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.LogoutResult;

// TODO: Auto-generated Javadoc
/**
 * The Class AppPresenter.
 */
public class AppPresenter extends Presenter<AppPresenter.MyView, AppPresenter.MyProxy> implements MyUiHandlers {

	/**
	 * Use this in leaf presenters, inside their {@link #revealInParent} method.
	 */
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new Type<RevealContentHandler<?>>();

	// @ContentSlot
	// public static final Type<RevealContentHandler<?>> TYPE_SetTopContent = new
	// Type<RevealContentHandler<?>>();

	/** The Constant TYPE_SetLeftContent. */
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetLeftContent = new Type<RevealContentHandler<?>>();

	/**
	 * The Interface MyProxy.
	 */
	@ProxyStandard
	public interface MyProxy extends Proxy<AppPresenter> {

	}

	/**
	 * The Interface MyView.
	 */
	public interface MyView extends View, HasUiHandlers<MyUiHandlers> {

	}

	/** The left presenter. */
	DigitalObjectMenuPresenter leftPresenter;
	private final DispatchAsync dispatcher;

	// private final HomePresenter homePresenter;
	// private final DigitalObjectMenuPresenter treePresenter;

	/**
	 * Instantiates a new app presenter.
	 * 
	 * @param eventBus
	 *          the event bus
	 * @param view
	 *          the view
	 * @param proxy
	 *          the proxy
	 * @param leftPresenter
	 *          the left presenter
	 */
	@Inject
	// public AppPresenter(final DispatchAsync dispatcher, final HomePresenter
	// homePresenter, final DigitalObjectMenuPresenter treePresenter,
	// final DigitalObjectMenuPresenter digitalObjectMenuPresenter, final
	// EditorClientConfiguration config) {
	public AppPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DigitalObjectMenuPresenter leftPresenter,
			final DispatchAsync dispatcher) {
		super(eventBus, view, proxy);
		this.dispatcher = dispatcher;
		this.leftPresenter = leftPresenter;
		getView().setUiHandlers(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
	 */
	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	@Override
	public void logout() {
		dispatcher.execute(new LogoutAction(), new DispatchCallback<LogoutResult>() {
			@Override
			public void callback(LogoutResult result) {
				MEditor.redirect("/login.html");
			}
		});
	}

}