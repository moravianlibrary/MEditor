package cz.fi.muni.xkremser.editor.client.presenter;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;

import cz.fi.muni.xkremser.editor.client.ClientUtils;
import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.MyUiHandlers;
import cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.Refreshable;
import cz.fi.muni.xkremser.editor.client.view.RecentlyModifiedRecord;
import cz.fi.muni.xkremser.editor.shared.event.ConfigReceivedEvent;
import cz.fi.muni.xkremser.editor.shared.event.ConfigReceivedEvent.ConfigReceivedHandler;
import cz.fi.muni.xkremser.editor.shared.event.DigitalObjectOpenedEvent;
import cz.fi.muni.xkremser.editor.shared.event.DigitalObjectOpenedEvent.DigitalObjectOpenedHandler;
import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;
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

		// TODO: ListGrid -> na nejake rozhrani
		ListGrid getRecentlyModifiedTree();

		void setDS(DispatchAsync dispatcher);
	}

	@ProxyStandard
	public interface MyProxy extends Proxy<DigitalObjectMenuPresenter> {

	}

	private final DispatchAsync dispatcher;
	private boolean inputQueueShown = false;
	private final PlaceManager placeManager;

	// @Inject
	private final EditorClientConfiguration config;

	@Inject
	public DigitalObjectMenuPresenter(final MyView view, final EventBus eventBus, final MyProxy proxy, final DispatchAsync dispatcher,
			final EditorClientConfiguration config, PlaceManager placeManager) {
		super(eventBus, view, proxy);
		this.dispatcher = dispatcher;
		this.config = config;
		this.placeManager = placeManager;
		// getView().setUiHandlers(this);
		// this.config = config;
		bind();
	}

	@Override
	protected void onBind() {
		super.onBind();

		getView().setDS(dispatcher);
		getView().getRecentlyModifiedTree().setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return record.getAttribute(Constants.ATTR_DESC);
			}
		});
		getView().getRecentlyModifiedTree().addCellClickHandler(new CellClickHandler() {
			@Override
			public void onCellClick(CellClickEvent event) {
				revealModifiedItem(event.getRecord().getAttribute(Constants.ATTR_UUID));
			}
		});
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

		addRegisteredHandler(DigitalObjectOpenedEvent.getType(), new DigitalObjectOpenedHandler() {
			@Override
			public void onDigitalObjectOpened(DigitalObjectOpenedEvent event) {
				if (event.isStatusOK()) {
					onAddDigitalObject(event.getItem());
				}
			}
		});
	}

	@Override
	protected void onUnbind() {
		super.onUnbind();
		getView().getRecentlyModifiedTree().setHoverCustomizer(null);
	}

	public boolean isInputQueueShown() {
		return inputQueueShown;
	}

	public void setInputQueueShown(boolean inputQueueShown) {
		this.inputQueueShown = inputQueueShown;
	}

	@Override
	public void onRefresh() {
		dispatcher.execute(new ScanInputQueueAction(null, true), new DispatchCallback<ScanInputQueueResult>() {

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

	@Override
	public void onAddDigitalObject(final RecentlyModifiedItem item) {
		Timer timer = new Timer() {
			@Override
			public void run() {
				RecentlyModifiedRecord record = ClientUtils.toRecord(item);
				if (getView().getRecentlyModifiedTree().getDataAsRecordList().contains(record)) {
					getView().getRecentlyModifiedTree().updateData(record);
				} else {
					getView().getRecentlyModifiedTree().addData(record);
				}
			}
		};
		timer.schedule(500);

	}

	@Override
	public void revealModifiedItem(String uuid) {
		placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY).with(Constants.URL_PARAM_UUID, uuid));
	}
}