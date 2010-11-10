/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.presenter;

import java.util.List;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tile.TileGrid;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.view.ContainerRecord;
import cz.fi.muni.xkremser.editor.client.view.ModifyView;
import cz.fi.muni.xkremser.editor.client.view.ModifyView.MyUiHandlers;
import cz.fi.muni.xkremser.editor.client.view.PageRecord;
import cz.fi.muni.xkremser.editor.shared.event.DigitalObjectClosedEvent;
import cz.fi.muni.xkremser.editor.shared.event.DigitalObjectClosedEvent.DigitalObjectClosedHandler;
import cz.fi.muni.xkremser.editor.shared.event.DigitalObjectOpenedEvent;
import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDigitalObjectDetailAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDigitalObjectDetailResult;
import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PageDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class ModifyPresenter.
 */
public class ModifyPresenter extends Presenter<ModifyPresenter.MyView, ModifyPresenter.MyProxy> implements MyUiHandlers {

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * The Interface MyView.
	 */
	public interface MyView extends View, HasUiHandlers<MyUiHandlers> {

		/**
		 * Gets the name.
		 * 
		 * @return the name
		 */
		public HasValue<String> getName();

		public Record[] fromClipboard();

		public void toClipboard(Record[] data);

		public PopupPanel getPopupPanel();

		/**
		 * Adds the digital object.
		 * 
		 * @param tileGridVisible
		 *          the tile grid visible
		 * @param data
		 *          the data
		 * @param dispatcher
		 *          the dispatcher
		 */
		void addDigitalObject(boolean tileGridVisible, Record[] pageData, Record[] containerData, DublinCore dc, String uuid, DispatchAsync dispatcher);
	}

	/**
	 * The Interface MyProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.MODIFY)
	public interface MyProxy extends ProxyPlace<ModifyPresenter> {

	}

	private int done = 0;

	/** The dispatcher. */
	private final DispatchAsync dispatcher;

	/** The left presenter. */
	private final DigitalObjectMenuPresenter leftPresenter;

	/** The uuid. */
	private String uuid;

	/** The previous uuid. */
	private String previousUuid1;

	private String previousUuid2;

	/** The forced refresh. */
	private boolean forcedRefresh;

	private final PlaceManager placeManager;

	/**
	 * Instantiates a new modify presenter.
	 * 
	 * @param eventBus
	 *          the event bus
	 * @param view
	 *          the view
	 * @param proxy
	 *          the proxy
	 * @param leftPresenter
	 *          the left presenter
	 * @param dispatcher
	 *          the dispatcher
	 */
	@Inject
	public ModifyPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DigitalObjectMenuPresenter leftPresenter,
			final DispatchAsync dispatcher, final PlaceManager placeManager) {
		super(eventBus, view, proxy);
		this.leftPresenter = leftPresenter;
		this.dispatcher = dispatcher;
		this.placeManager = placeManager;
		getView().setUiHandlers(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
	 */
	@Override
	protected void onBind() {
		super.onBind();

		addRegisteredHandler(DigitalObjectClosedEvent.getType(), new DigitalObjectClosedHandler() {
			@Override
			public void onDigitalObjectClosed(DigitalObjectClosedEvent event) {
				String uuid = event.getUuid();
				if (uuid != null) {
					if (uuid.equals(previousUuid2)) {
						previousUuid2 = null;
					} else if (uuid.equals(previousUuid1)) {
						if (previousUuid2 == null) {
							previousUuid1 = null;
						} else { // move
							previousUuid1 = previousUuid2;
							previousUuid2 = null;
						}
					}
				}
			}
		});

	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.mvp.client.Presenter#prepareFromRequest(com.gwtplatform
	 * .mvp.client.proxy.PlaceRequest)
	 */
	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		uuid = request.getParameter(Constants.URL_PARAM_UUID, null);

		// SC.say(uuid);
		// if(anything wrong)
		// > {
		// > getProxy().manualRevealFailed();
		// > placeManager.revealErrorPlace( placeRequest.getNameToken() );
		// > return;
		// > }
		// > getProxy().manualReveal(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onUnbind()
	 */
	@Override
	protected void onUnbind() {
		super.onUnbind();
		// Add unbind functionality here for more complex presenters.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
	 */
	@Override
	protected void onReset() {
		super.onReset();

		// test
		// page cc25c992-c94c-11df-84b1-001b63bd97ba
		// monograph 0eaa6730-9068-11dd-97de-000d606f5dc6
		// internal part 1118b1bf-c94d-11df-84b1-001b63bd97ba
		// uuid = "1118b1bf-c94d-11df-84b1-001b63bd97ba";

		if (uuid != null && (forcedRefresh || (!uuid.equals(previousUuid1) && !uuid.equals(previousUuid2)))) {
			// final ModalWindow mw = new
			// ModalWindow((com.smartgwt.client.widgets.Canvas) getView().asWidget());
			// mw.setLoadingIcon("loadingAnimation.gif");
			// mw.show(true);
			dispatcher.execute(new GetDigitalObjectDetailAction(uuid), new DispatchCallback<GetDigitalObjectDetailResult>() {
				@Override
				public void callback(GetDigitalObjectDetailResult result) {
					AbstractDigitalObjectDetail detail = result.getDetail();
					Record[] pagesData = null;
					Record[] containerData = null;
					if (detail.hasPages()) {
						pagesData = new Record[detail.getPages().size()];
						List<PageDetail> pages = detail.getPages();
						for (int i = 0, total = pages.size(); i < total; i++) {
							pagesData[i] = new PageRecord(pages.get(i).getDc().getTitle().get(0), pages.get(i).getDc().getIdentifier().get(0), pages.get(i).getDc()
									.getIdentifier().get(0));
						}
					}

					if (detail.hasContainers()) {
						containerData = new Record[detail.getContainers().size()];
						List<? extends AbstractDigitalObjectDetail> containers = detail.getContainers();
						for (int i = 0, total = containers.size(); i < total; i++) {
							containerData[i] = new ContainerRecord(containers.get(i).getDc().getTitle().get(0), containers.get(i).getDc().getIdentifier().get(0),
									"icons/48/reset.png");
						}
					}

					getView().addDigitalObject(true, pagesData, containerData, detail.getDc(), uuid, dispatcher);
					DigitalObjectOpenedEvent.fire(ModifyPresenter.this, true, new RecentlyModifiedItem(uuid, detail.getDc().getTitle().get(0), "", detail.getModel()),
							result.getDetail().getRelated());
				}

				@Override
				public void callbackError(Throwable t) {
					super.callbackError(t);
				}
			});
			// mw.show(false);

			if (!uuid.equals(previousUuid1) && !uuid.equals(previousUuid2)) {
				previousUuid2 = previousUuid1;
				previousUuid1 = uuid;
			}
		}
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetLeftContent, leftPresenter);

		// if (uuid != null && (forcedRefresh || (uuid != previousUuid))) {
		// getView().addDigitalObject(false, null, dispatcher);
		// }

	}

	@Override
	public void onAddDigitalObject(final TileGrid tileGrid, final Menu menu) {
		MenuItem[] items = menu.getItems();
		if (!ModifyView.ID_EDIT.equals(items[0].getAttributeAsObject(ModifyView.ID_NAME))) {
			throw new IllegalStateException("Inconsistent gui.");
		}
		items[0].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				String uuidToEdit = tileGrid.getSelection()[0].getAttribute(Constants.ATTR_UUID);
				placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY).with(Constants.URL_PARAM_UUID, uuidToEdit));
			}
		});
		if (!ModifyView.ID_SEL_ALL.equals(items[2].getAttributeAsObject(ModifyView.ID_NAME))) {
			throw new IllegalStateException("Inconsistent gui.");
		}
		items[2].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				tileGrid.selectAllRecords();
			}
		});
		if (!ModifyView.ID_SEL_NONE.equals(items[3].getAttributeAsObject(ModifyView.ID_NAME))) {
			throw new IllegalStateException("Inconsistent gui.");
		}
		items[3].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				tileGrid.deselectAllRecords();
			}
		});
		if (!ModifyView.ID_SEL_INV.equals(items[4].getAttributeAsObject(ModifyView.ID_NAME))) {
			throw new IllegalStateException("Inconsistent gui.");
		}
		items[4].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Record[] selected = tileGrid.getSelection();
				tileGrid.selectAllRecords();
				tileGrid.deselectRecords(selected);
			}
		});
		if (!ModifyView.ID_COPY.equals(items[6].getAttributeAsObject(ModifyView.ID_NAME))) {
			throw new IllegalStateException("Inconsistent gui.");
		}
		items[6].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				getView().toClipboard(tileGrid.getSelection());
			}
		});
		if (!ModifyView.ID_PASTE.equals(items[7].getAttributeAsObject(ModifyView.ID_NAME))) {
			throw new IllegalStateException("Inconsistent gui.");
		}
		items[7].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				final Record[] data = getView().fromClipboard();
				final boolean progressbar = data.length > Constants.CLIPBOARD_MAX_WITHOUT_PROGRESSBAR;
				final Progressbar hBar1 = progressbar ? new Progressbar() : null;
				if (progressbar) {
					hBar1.setHeight(24);
					hBar1.setVertical(false);
					hBar1.setPercentDone(0);
					getView().getPopupPanel().setWidget(hBar1);
					getView().getPopupPanel().setVisible(true);
					getView().getPopupPanel().center();
					done = 0;
					Timer timer = new Timer() {
						@Override
						public void run() {
							hBar1.setPercentDone(((100 * (done + 1)) / data.length));
							tileGrid.addData(((PageRecord) data[done]).deepCopy());
							if (++done != data.length) {
								schedule(15);
							} else {
								getView().getPopupPanel().setVisible(false);
								getView().getPopupPanel().hide();
							}
						}
					};
					timer.schedule(40);
				} else {
					for (int i = 0; i < data.length; i++) {
						tileGrid.addData(((PageRecord) data[i]).deepCopy());
					}
				}
			}
		});
		if (!ModifyView.ID_DELETE.equals(items[8].getAttributeAsObject(ModifyView.ID_NAME))) {
			throw new IllegalStateException("Inconsistent gui.");
		}

		items[8].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				tileGrid.removeSelectedData();
			}
		});
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetMainContent, this);
	}

	@Override
	public void onAddDigitalObject(final String uuid, final ImgButton closeButton, final Menu menu) {
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DigitalObjectClosedEvent.fire(ModifyPresenter.this, uuid);
			}
		});
	}
}