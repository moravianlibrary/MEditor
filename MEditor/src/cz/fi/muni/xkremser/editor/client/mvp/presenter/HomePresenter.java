package cz.fi.muni.xkremser.editor.client.mvp.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.shared.rpc.ScanInputQueue;
import cz.fi.muni.xkremser.editor.shared.rpc.ScanInputQueueResult;

public class HomePresenter extends WidgetPresenter<HomePresenter.Display> {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	public interface Display extends WidgetDisplay {
		public HasValue<String> getName();

		public HasClickHandlers getSend();
	}

	private final DispatchAsync dispatcher;
	private final DigitalObjectMenuPresenter treePresenter;

	// private final DigitalObjectMenuPresenter treePresenter;

	// FUDGE FACTOR! Although this is not used, having GIN pass the object
	// to this class will force its instantiation and therefore will make the
	// response presenter listen for events (via bind()). This is not a very
	// good way to
	// achieve this, but I wanted to put something together quickly - sorry!
	// private final GreetingResponsePresenter greetingResponsePresenter;
	// /*, final DigitalObjectMenuPresenter treePresenter*/

	@Inject
	public HomePresenter(final Display display, final EventBus eventBus, final DispatchAsync dispatcher, final DigitalObjectMenuPresenter treePresenter) {
		super(display, eventBus);
		this.dispatcher = dispatcher;
		this.treePresenter = treePresenter;

		bind();
	}

	/**
	 * Try to send the greeting message
	 */
	private void doSend() {
		Log.info("Calling doSend");

		// dispatcher.execute(new SendGreeting(display.getName().getValue()), new
		// DisplayCallback<SendGreetingResult>(display) {
		//
		// @Override
		// protected void handleFailure(final Throwable cause) {
		// Log.error("Handle Failure:", cause);
		//
		// Window.alert(SERVER_ERROR);
		// }
		//
		// @Override
		// protected void handleSuccess(final SendGreetingResult result) {
		// // take the result from the server and notify client
		// // interested components
		// eventBus.fireEvent(new GreetingSentEvent(result.getName(),
		// result.getMessage()));
		// }
		//
		// });

		// delete
		dispatcher.execute(new ScanInputQueue(Constants.DOC_TYPE.MONOGRAPHS), new AsyncCallback<ScanInputQueueResult>() {
			@Override
			public void onFailure(final Throwable cause) {
				Log.error("Handle Failure:", cause);

				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(final ScanInputQueueResult result) {
				// take the result from the server and notify client
				// interested

			}

		});
	}

	@Override
	protected void onBind() {
		// 'display' is a final global field containing the Display passed into
		// the constructor.
		display.getSend().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				doSend();
			}
		});
	}

	@Override
	protected void onUnbind() {
		// Add unbind functionality here for more complex presenters.
	}

	@Override
	public void revealDisplay() {
		// Nothing to do. This is more useful in UI which may be buried
		// in a tab bar, tree, etc.
	}

	@Override
	protected void onRevealDisplay() {
		// TODO Auto-generated method stub

	}

	// @Override
	// protected void onPlaceRequest(final PlaceRequest request) {
	// // Grab the 'name' from the request and put it into the 'name' field.
	// // This allows a tag of '#Greeting;name=Foo' to populate the name
	// // field.
	// final String name = request.getParameter("name", null);
	// final String doi = request.getParameter(Constants.URL_PARAM_DOI, null);
	// final String action = request.getParameter(Constants.URL_PARAM_ACTION,
	// null);
	//
	// if (doi != null) {
	// Window.alert(doi);
	// }
	// if (action != null) {
	// Window.alert(action);
	// }
	// if (name != null) {
	// display.getName().setValue(name);
	// }
	// }
}