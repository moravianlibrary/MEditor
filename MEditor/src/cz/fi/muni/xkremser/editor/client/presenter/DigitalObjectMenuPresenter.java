/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
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

// TODO: Auto-generated Javadoc
/**
 * The Class DigitalObjectMenuPresenter.
 */
public class DigitalObjectMenuPresenter extends Presenter<DigitalObjectMenuPresenter.MyView, DigitalObjectMenuPresenter.MyProxy> implements MyUiHandlers {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while attempting to contact the server.";

	/**
	 * The Interface MyView.
	 */
	public interface MyView extends View/* , HasUiHandlers<MyUiHandlers> */{
		
		/**
		 * Gets the selected.
		 *
		 * @return the selected
		 */
		HasValue<String> getSelected();

		/**
		 * Expand node.
		 *
		 * @param id the id
		 */
		void expandNode(String id);

		/**
		 * Gets the refresh widget.
		 *
		 * @return the refresh widget
		 */
		HasClickHandlers getRefreshWidget();

		/**
		 * Show input queue.
		 *
		 * @param dispatcher the dispatcher
		 */
		void showInputQueue(DispatchAsync dispatcher);

		/**
		 * Gets the input tree.
		 *
		 * @return the input tree
		 */
		Refreshable getInputTree();

		// TODO: ListGrid -> na nejake rozhrani
		/**
		 * Gets the recently modified tree.
		 *
		 * @return the recently modified tree
		 */
		ListGrid getRecentlyModifiedTree();

		/**
		 * Sets the dS.
		 *
		 * @param dispatcher the new dS
		 */
		void setDS(DispatchAsync dispatcher);
	}

	/**
	 * The Interface MyProxy.
	 */
	@ProxyStandard
	public interface MyProxy extends Proxy<DigitalObjectMenuPresenter> {

	}

	/** The dispatcher. */
	private final DispatchAsync dispatcher;
	
	/** The input queue shown. */
	private boolean inputQueueShown = false;
	
	/** The place manager. */
	private final PlaceManager placeManager;

	// @Inject
	/** The config. */
	private final EditorClientConfiguration config;

	/**
	 * Instantiates a new digital object menu presenter.
	 *
	 * @param view the view
	 * @param eventBus the event bus
	 * @param proxy the proxy
	 * @param dispatcher the dispatcher
	 * @param config the config
	 * @param placeManager the place manager
	 */
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

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
	 */
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

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onUnbind()
	 */
	@Override
	protected void onUnbind() {
		super.onUnbind();
		getView().getRecentlyModifiedTree().setHoverCustomizer(null);
	}

	/**
	 * Checks if is input queue shown.
	 *
	 * @return true, if is input queue shown
	 */
	public boolean isInputQueueShown() {
		return inputQueueShown;
	}

	/**
	 * Sets the input queue shown.
	 *
	 * @param inputQueueShown the new input queue shown
	 */
	public void setInputQueueShown(boolean inputQueueShown) {
		this.inputQueueShown = inputQueueShown;
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.MyUiHandlers#onRefresh()
	 */
	@Override
	public void onRefresh() {
		dispatcher.execute(new ScanInputQueueAction(null, true), new DispatchCallback<ScanInputQueueResult>() {

			@Override
			public void callback(ScanInputQueueResult result) {
				getView().getInputTree().refreshTree();
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
	 */
	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetLeftContent, this);
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.MyUiHandlers#onShowInputQueue()
	 */
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

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.MyUiHandlers#onAddDigitalObject(cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem)
	 */
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

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.MyUiHandlers#revealModifiedItem(java.lang.String)
	 */
	@Override
	public void revealModifiedItem(String uuid) {
		placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY).with(Constants.URL_PARAM_UUID, uuid));
	}
}