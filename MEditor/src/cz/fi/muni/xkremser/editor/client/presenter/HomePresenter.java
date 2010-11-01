/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueResult;

// TODO: Auto-generated Javadoc
/**
 * The Class HomePresenter.
 */
public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy> {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * The Interface MyView.
	 */
	public interface MyView extends View {
		
		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public HasValue<String> getName();

		/**
		 * Gets the send.
		 *
		 * @return the send
		 */
		public HasClickHandlers getSend();
	}

	/**
	 * The Interface MyProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.HOME)
	public interface MyProxy extends ProxyPlace<HomePresenter> {

	}

	/** The dispatcher. */
	private DispatchAsync dispatcher;
	
	/** The left presenter. */
	private final DigitalObjectMenuPresenter leftPresenter;

	/**
	 * Instantiates a new home presenter.
	 *
	 * @param eventBus the event bus
	 * @param view the view
	 * @param proxy the proxy
	 * @param leftPresenter the left presenter
	 */
	@Inject
	// public HomePresenter(final MyView display, final EventBus eventBus, final
	// DispatchAsync dispatcher, final DigitalObjectMenuPresenter treePresenter) {
	public HomePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DigitalObjectMenuPresenter leftPresenter) {
		super(eventBus, view, proxy);
		this.leftPresenter = leftPresenter;

	}

	/**
	 * Try to send the greeting message.
	 */
	private void doSend() {
		Log.info("Calling doSend");

		// delete
		dispatcher.execute(new ScanInputQueueAction("", false), new DispatchCallback<ScanInputQueueResult>() {
			@Override
			public void callbackError(final Throwable cause) {
				Log.error("Handle Failure:", cause);

				Window.alert(SERVER_ERROR);
			}

			@Override
			public void callback(final ScanInputQueueResult result) {
				// take the result from the server and notify client
				// interested

			}

		});
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
	 */
	@Override
	protected void onBind() {
		// If you add a handler, register it by calling registerHandler() so that it
		// is automatically removed later.

		super.onBind();
		getView().getSend().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				doSend();
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onUnbind()
	 */
	@Override
	protected void onUnbind() {
		// Add unbind functionality here for more complex presenters.
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
	 */
	@Override
	protected void onReset() {
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetLeftContent, leftPresenter);
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
	 */
	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetMainContent, this);
	}

}