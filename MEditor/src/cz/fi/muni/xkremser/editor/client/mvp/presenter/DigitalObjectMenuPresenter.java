package cz.fi.muni.xkremser.editor.client.mvp.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;
import com.smartgwt.client.widgets.tree.events.FolderOpenedEvent;
import com.smartgwt.client.widgets.tree.events.FolderOpenedHandler;
import com.smartgwt.client.widgets.tree.events.HasFolderOpenedHandlers;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.shared.rpc.ScanInputQueue;
import cz.fi.muni.xkremser.editor.shared.rpc.ScanInputQueueResult;

public class DigitalObjectMenuPresenter extends WidgetPresenter<DigitalObjectMenuPresenter.Display> {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while attempting to contact the server.";

	public interface Display extends WidgetDisplay {
		public HasValue<String> getSelected();

		public void expandNode(String id);

		public void showInputQueue();

		public HasFolderOpenedHandlers getInputTree();

		public void refreshInputQueue(ScanInputQueueResult result);
	}

	private final DispatchAsync dispatcher;
	private boolean inputQueueShown = false;

	// FUDGE FACTOR! Although this is not used, having GIN pass the object
	// to this class will force its instantiation and therefore will make
	// the
	// response presenter listen for events (via bind()). This is not a
	// very
	// good way to
	// achieve this, but I wanted to put something together quickly -
	// sorry!
	@Inject
	public DigitalObjectMenuPresenter(final Display display, final EventBus eventBus, final DispatchAsync dispatcher) {
		super(display, eventBus);
		this.dispatcher = dispatcher;
		bind();
	}

	private void getData(String id) {
		Log.info("Calling getData(id='" + id == null ? "root" : id + "')");

		// scan input queue
		dispatcher.execute(new ScanInputQueue(id), new AsyncCallback<ScanInputQueueResult>() {
			@Override
			public void onFailure(final Throwable cause) {
				Log.error("Method getData failed: ", cause);
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(final ScanInputQueueResult result) {
				if (result != null) {
					if (!isInputQueueShown()) { // input_queue is not set
						getDisplay().showInputQueue();
						addFolderOpenedHandler();
					}
					setInputQueueShown(true);
					getDisplay().refreshInputQueue(result);
				} else {
					setInputQueueShown(false);
				}
			}

		});

		// get recently modified
		// dispatcher.execute(new ScanInputQueue(path), new
		// AsyncCallback<ScanInputQueueResult>() {
		// @Override
		// public void onFailure(final Throwable cause) {
		// Log.error("Method getData failed: ", cause);
		// Window.alert(SERVER_ERROR);
		// }
		//
		// @Override
		// public void onSuccess(final ScanInputQueueResult result) {
		// if (result == null) { // input_queue is not set
		// // hide input queue tree
		// } else {
		// // fill input queue tree
		// }
		// }
		//
		// });
	}

	private void getData() {
		getData(null);
	}

	@Override
	protected void onBind() {
		getData();

		// 'display' is a final global field containing the Display passed into
		// the constructor.
		// display.getMenu().addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(final ClickEvent event) {
		// // doSend();
		// Log.error("pipina");
		// }
		// });
	}

	private void addFolderOpenedHandler() {
		getDisplay().getInputTree().addFolderOpenedHandler(new FolderOpenedHandler() {

			@Override
			public void onFolderOpened(FolderOpenedEvent event) {
				getData(event.getNode().getAttribute(Constants.ATTR_ID));

			}
		});
	}

	@Override
	protected void onUnbind() {
		// Add unbind functionality here for more complex presenters.
	}

	@Override
	public void revealDisplay() {
		// Window.alert("blib");
	}

	@Override
	protected void onRevealDisplay() {
		Window.alert("blib");
	}

	public boolean isInputQueueShown() {
		return inputQueueShown;
	}

	public void setInputQueueShown(boolean inputQueueShown) {
		this.inputQueueShown = inputQueueShown;
	}

}