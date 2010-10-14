package cz.fi.muni.xkremser.editor.client.presenter;

import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;

import cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.MyUiHandlers;
import cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.Refreshable;
import cz.fi.muni.xkremser.editor.shared.event.ConfigReceivedEvent;
import cz.fi.muni.xkremser.editor.shared.event.ConfigReceivedEvent.ConfigReceivedHandler;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueue;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueResult;

public class DigitalObjectMenuPresenter extends Presenter<DigitalObjectMenuPresenter.MyView, DigitalObjectMenuPresenter.MyProxy> implements MyUiHandlers {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while attempting to contact the server.";

	public interface MyView extends View/* , HasUiHandlers<MyUiHandlers> */{
		HasValue<String> getSelected();

		void expandNode(String id);

		HasClickHandlers getRefreshWidget();

		void showInputQueue(DispatchAsync dispatcher);

		Refreshable getInputTree();
	}

	@ProxyStandard
	public interface MyProxy extends Proxy<DigitalObjectMenuPresenter> {

	}

	private final DispatchAsync dispatcher;
	private boolean inputQueueShown = false;

	// @Inject
	private final EditorClientConfiguration config;

	@Inject
	public DigitalObjectMenuPresenter(final MyView view, final EventBus eventBus, final MyProxy proxy, final DispatchAsync dispatcher,
			final EditorClientConfiguration config) {
		super(eventBus, view, proxy);
		this.dispatcher = dispatcher;
		this.config = config;
		// getView().setUiHandlers(this);
		// this.config = config;
		bind();
	}

	@Override
	protected void onBind() {
		super.onBind();
		Log.info("tady to chci pouzit" + new Date().toString());
		Log.info(String.valueOf(System.currentTimeMillis()));

		addRegisteredHandler(ConfigReceivedEvent.getType(), new ConfigReceivedHandler() {
			@Override
			public void onConfigReceived(ConfigReceivedEvent event) {
				if (event.isStatusOK()) {
					setInputQueueShown(config.getShowInputQueue());
				} else {
					setInputQueueShown(EditorClientConfiguration.Constants.GUI_SHOW_INPUT_QUEUE_DEFAULT);
				}
				if (isInputQueueShown()) {
					onShowInputQueue();
				}
			}
		});
	}

	@Override
	protected void onUnbind() {
		super.onUnbind();
	}

	public boolean isInputQueueShown() {
		return inputQueueShown;
	}

	public void setInputQueueShown(boolean inputQueueShown) {
		this.inputQueueShown = inputQueueShown;
	}

	@Override
	public void onRefresh() {
		dispatcher.execute(new ScanInputQueueAction(null, ScanInputQueue.TYPE.DB_UPDATE), new DispatchCallback<ScanInputQueueResult>() {

			@Override
			public void callback(ScanInputQueueResult result) {
				getView().getInputTree().refreshTree();
			}
		});
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetLeftContent, this);
	}

	@Override
	public void onShowInputQueue() {
		getView().showInputQueue(dispatcher);
		registerHandler(getView().getRefreshWidget().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onRefresh();
			}
		}));
	}

}