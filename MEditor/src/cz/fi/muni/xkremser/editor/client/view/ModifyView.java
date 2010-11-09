/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.view;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.TabBarControls;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.IMenuButton;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.viewer.DetailFormatter;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter.MyView;
import cz.fi.muni.xkremser.editor.client.view.ModifyView.MyUiHandlers;
import cz.fi.muni.xkremser.editor.client.view.tab.DCTab;
import cz.fi.muni.xkremser.editor.client.view.tab.ModsTab;
import cz.fi.muni.xkremser.editor.shared.valueobj.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class ModifyView.
 */
public class ModifyView extends ViewWithUiHandlers<MyUiHandlers> implements MyView {

	public interface MyUiHandlers extends UiHandlers {

		void onAddDigitalObject(final TileGrid tileGrid, final Menu menu);

	}

	private static final String ID_DC = "dc";
	private static final String ID_MODS = "mods";
	private static final String ID_TAB = "tab";

	public static final String ID_NAME = "name";
	public static final String ID_EDIT = "edit";
	public static final String ID_SEPARATOR = "separator";
	public static final String ID_SEL_ALL = "all";
	public static final String ID_SEL_NONE = "none";
	public static final String ID_SEL_INV = "invert";
	public static final String ID_COPY = "copy";
	public static final String ID_PASTE = "paste";
	public static final String ID_DELETE = "delete";

	private Record[] clipboard;

	@Inject
	private EventBus eventBus;

	/** The tile grid. */
	// private TileGrid tileGrid;

	/** The layout. */
	private final VLayout layout;

	/** The images layout. */
	private VLayout imagesLayout;

	/** The top tab set1. */
	private TabSet topTabSet1;

	/** The top tab set2. */
	private TabSet topTabSet2;
	// private VLayout imagesLayout1;
	// private VLayout imagesLayout2;
	/** The image popup. */
	private PopupPanel imagePopup;

	/** The first. */
	private boolean first = true;

	// private boolean first = true;

	// private final GlassPanel glassPanel;

	/**
	 * Instantiates a new modify view.
	 */
	public ModifyView() {
		layout = new VLayout();
		layout.setOverflow(Overflow.AUTO);
		layout.setLeaveScrollbarGap(true);
		imagePopup = new PopupPanel(true);
		imagePopup.setGlassEnabled(true);
		imagePopup.setAnimationEnabled(true);
		// layout.setCanDragResize(true);

		// HLayout buttons = new HLayout();
		// buttons.setMembersMargin(15);

		// IButton blueButton = new IButton("Select Blue");
		// blueButton.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// topTabSet.selectTab(0);
		// }
		// });

		// buttons.addMember(blueButton);

		// layout.addMember(buttons);

		// IButton print = new IButton("print");
		// print.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// print();
		// }
		// });
		// imagesLayout.addMember(print);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter.MyView#getName
	 * ()
	 */
	@Override
	public HasValue<String> getName() {
		return null;
	}

	@Override
	public Record[] fromClipboard() {
		return this.clipboard;
	}

	@Override
	public void toClipboard(Record[] data) {
		this.clipboard = data;
	}

	/**
	 * Prints the.
	 */
	public void print() {
		// Record[] data = tileGrid.getData();
		// for (Record rec : data) {
		// System.out.println(rec.getAttribute(Constants.ATTR_NAME));
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter.MyView#
	 * addDigitalObject(boolean, com.smartgwt.client.data.Record[],
	 * com.gwtplatform.dispatch.client.DispatchAsync)
	 */
	@Override
	public void addDigitalObject(final boolean tileGridVisible, final Record[] data, final DublinCore dc, final String uuid, final DispatchAsync dispatcher) {
		// final ModalWindow modal = new ModalWindow(layout);
		// modal.setLoadingIcon("loadingAnimation.gif");
		// modal.show("Loading digital object data...", true);

		imagesLayout = new VLayout();

		final TabSet topTabSet = new TabSet();
		// topTabSet.setID(uuid);
		topTabSet.setTabBarPosition(Side.TOP);
		topTabSet.setWidth100();
		topTabSet.setHeight100();

		Menu menu = new Menu();
		menu.setShowShadow(true);
		menu.setShadowDepth(10);

		MenuItem newItem = new MenuItem("New", "icons/16/document_plain_new.png", "Ctrl+N");
		MenuItem openItem = new MenuItem("Open", "icons/16/folder_out.png", "Ctrl+O");
		MenuItem saveItem = new MenuItem("Save", "icons/16/disk_blue.png", "Ctrl+S");
		MenuItem downloadItem = new MenuItem("Download", "icons/16/download.png");
		MenuItem removeItem = new MenuItem("Remove", "icons/16/close.png");
		MenuItem refreshItem = new MenuItem("Refresh", "icons/16/refresh.png");
		MenuItem publishItem = new MenuItem("Publish", "icons/16/add.png");
		menu.setItems(newItem, openItem, saveItem, refreshItem, downloadItem, removeItem, publishItem);
		IMenuButton menuButton = new IMenuButton("Menu", menu);
		menuButton.setWidth(60);
		menuButton.setHeight(16);

		final ImgButton closeButton = new ImgButton();
		closeButton.setSrc("[SKIN]headerIcons/close.png");
		closeButton.setSize(16);
		// closeButton.setShowFocused(false);
		closeButton.setShowRollOver(true);
		closeButton.setCanHover(true);
		closeButton.setShowDownIcon(false);
		closeButton.setShowDown(false);
		closeButton.addHoverHandler(new HoverHandler() {
			@Override
			public void onHover(HoverEvent event) {
				closeButton.setPrompt("Close this digital object.");
			}
		});

		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// DigitalObjectClosedEvent.fire(ModifyView.this, uuid);
				layout.removeMember(topTabSet);
				if (first || topTabSet1 == null || topTabSet2 == null) {
					first = !first;
				}
				if (topTabSet1 == topTabSet) {
					topTabSet1.destroy();
					topTabSet1 = null;
					if (topTabSet2 != null) { // move up
						topTabSet1 = topTabSet2;
						topTabSet2 = null;
					}
				} else {
					topTabSet2.destroy();
					topTabSet2 = null;
				}
			}
		});
		topTabSet.setTabBarControls(TabBarControls.TAB_SCROLLER, TabBarControls.TAB_PICKER, menuButton, closeButton);
		topTabSet.setAnimateTabScrolling(true);

		Tab tTab1 = new Tab("Relations", "pieces/16/pawn_red.png");
		tTab1.setPane(imagesLayout);

		final Tab tTab2 = new Tab("DC", "pieces/16/pawn_green.png");
		tTab2.setAttribute(ID_TAB, ID_DC);

		final Tab tTab3 = new Tab("MODS", "pieces/16/pawn_blue.png");
		tTab3.setAttribute(ID_TAB, ID_MODS);

		Tab tTab4 = new Tab("Thumbnail", "pieces/16/pawn_white.png");
		Img tImg4 = new Img("pieces/48/pawn_white.png", 48, 48);
		tTab4.setPane(tImg4);

		Tab tTab5 = new Tab("Full image", "pieces/16/pawn_yellow.png");
		Img tImg5 = new Img("pieces/48/pawn_yellow.png", 48, 48);
		tTab5.setPane(tImg5);

		Tab tTab6 = new Tab("IMG ADM", "pieces/16/piece_blue.png");
		Img tImg6 = new Img("pieces/48/piece_blue.png", 48, 48);
		tTab6.setPane(tImg6);

		Tab tTab7 = new Tab("Policy", "pieces/16/piece_green.png");
		Img tImg7 = new Img("pieces/48/piece_green.png", 48, 48);
		tTab7.setPane(tImg7);

		topTabSet.setTabs(tTab1, tTab2, tTab3, tTab4, tTab5, tTab6, tTab7);
		topTabSet.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(final TabSelectedEvent event) {
				if (ID_MODS.equals(event.getTab().getAttribute(ID_TAB)) && event.getTab().getPane() == null) {
					final ModalWindow mw = new ModalWindow(topTabSet);
					mw.setLoadingIcon("loadingAnimation.gif");
					mw.show(true);
					Timer timer = new Timer() {
						@Override
						public void run() {
							Tab t = new ModsTab(1, true);
							TabSet ts = event.getTab().getTabSet();
							ts.setTabPane(event.getTab().getID(), t.getPane());
							mw.hide();
						}
					};
					timer.schedule(25);
				}
				if (ID_DC.equals(event.getTab().getAttribute(ID_TAB)) && event.getTab().getPane() == null) {
					final ModalWindow mw = new ModalWindow(topTabSet);
					mw.setLoadingIcon("loadingAnimation.gif");
					mw.show(true);
					Timer timer = new Timer() {
						@Override
						public void run() {
							Tab t = new DCTab(dc);
							TabSet ts = event.getTab().getTabSet();
							ts.setTabPane(event.getTab().getID(), t.getPane());
							mw.hide();
						}
					};
					timer.schedule(25);
				}
			}
		});

		// topTabSet.setSelectedTab(2); // TODO: remove
		layout.setMembersMargin(15);
		// layout.addMember(topTabSet);
		if (first) {
			if (topTabSet1 != null) {
				layout.removeMember(topTabSet1);
				topTabSet1.destroy();
				topTabSet1 = null;
			}
			topTabSet1 = topTabSet;
			layout.addMember(topTabSet1, 0);
		} else {
			if (topTabSet2 != null) {
				layout.removeMember(topTabSet2);
				topTabSet2.destroy();
				topTabSet2 = null;
			}
			topTabSet2 = topTabSet;
			layout.addMember(topTabSet2, 1);
		}

		if (tileGridVisible == true) {
			getTileGrid().setData(data);
			// tileGrid
		}
		first = !first;
	}

	/**
	 * Sets the tile grid.
	 */
	private TileGrid getTileGrid() {

		final TileGrid tileGrid = new TileGrid();
		// tileGrid.setCanSelectText(true);
		tileGrid.setTileWidth(110);
		tileGrid.setTileHeight(140);
		tileGrid.setHeight100();
		tileGrid.setWidth100();
		tileGrid.setCanDrag(true);
		tileGrid.setCanAcceptDrop(true);
		tileGrid.setShowAllRecords(true);
		Menu menu = new Menu();
		menu.setShowShadow(true);
		menu.setShadowDepth(10);
		MenuItem editItem = new MenuItem("Edit", "icons/16/edit.png");
		editItem.setAttribute(ID_NAME, ID_EDIT);
		editItem.setEnableIfCondition(new MenuItemIfFunction() {
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return tileGrid.getSelection() != null && tileGrid.getSelection().length == 1;
			}
		});

		MenuItem selectAllItem = new MenuItem("Select all", "icons/16/document_plain_new.png");
		selectAllItem.setAttribute(ID_NAME, ID_SEL_ALL);

		MenuItem deselectAllItem = new MenuItem("Deselect all", "icons/16/document_plain_new_Disabled.png");
		deselectAllItem.setAttribute(ID_NAME, ID_SEL_NONE);

		MenuItem invertSelectionItem = new MenuItem("Invert selection", "icons/16/invert.png");
		invertSelectionItem.setAttribute(ID_NAME, ID_SEL_INV);

		MenuItemSeparator separator = new MenuItemSeparator();
		separator.setAttribute(ID_NAME, ID_SEPARATOR);

		MenuItem copyItem = new MenuItem("Copy selected", "icons/16/copy.png");
		copyItem.setAttribute(ID_NAME, ID_COPY);
		copyItem.setEnableIfCondition(new MenuItemIfFunction() {
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return tileGrid.getSelection().length > 0;
			}
		});

		MenuItem pasteItem = new MenuItem("Paste", "icons/16/paste.png");
		pasteItem.setAttribute(ID_NAME, ID_PASTE);
		pasteItem.setEnableIfCondition(new MenuItemIfFunction() {
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return ModifyView.this.clipboard != null && ModifyView.this.clipboard.length > 0;
			}
		});

		MenuItem removeSelectedItem = new MenuItem("Remove selected", "icons/16/close.png");
		removeSelectedItem.setAttribute(ID_NAME, ID_DELETE);
		removeSelectedItem.setEnableIfCondition(new MenuItemIfFunction() {
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return tileGrid.getSelection().length > 0;
			}
		});

		// MenuItem openItem = new MenuItem("Open", "icons/16/folder_out.png",
		// "Ctrl+O");
		// MenuItem saveItem = new MenuItem("Save", "icons/16/disk_blue.png",
		// "Ctrl+S");
		// MenuItem downloadItem = new MenuItem("Download",
		// "icons/16/download.png");
		// MenuItem removeItem = new MenuItem("Remove", "icons/16/close.png");
		// MenuItem refreshItem = new MenuItem("Refresh", "icons/16/refresh.png");
		// MenuItem publishItem = new MenuItem("Publish", "icons/16/add.png");
		menu.setItems(editItem, separator, selectAllItem, deselectAllItem, invertSelectionItem, separator, copyItem, pasteItem, removeSelectedItem);
		tileGrid.setContextMenu(menu);

		// tileGrid.setCanDragResize(true);
		imagePopup = new PopupPanel(true);
		imagePopup.setGlassEnabled(true);
		imagePopup.setAnimationEnabled(true);
		tileGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

			@Override
			public void onRecordDoubleClick(final RecordDoubleClickEvent event) {
				if (event.getRecord() != null) {
					try {
						final ModalWindow mw = new ModalWindow(layout);
						mw.setLoadingIcon("loadingAnimation.gif");
						mw.show(true);
						final Image full = new Image("images/full/" + event.getRecord().getAttribute(Constants.ATTR_UUID));
						full.setHeight("700px");
						full.addLoadHandler(new LoadHandler() {
							@Override
							public void onLoad(LoadEvent event) {
								mw.hide();
								imagePopup.setVisible(true);
								imagePopup.center();
							}
						});
						imagePopup.setWidget(full);
						imagePopup.addCloseHandler(new CloseHandler<PopupPanel>() {
							@Override
							public void onClose(CloseEvent<PopupPanel> event) {
								mw.hide();
								imagePopup.setWidget(null);
							}
						});
						imagePopup.center();
						imagePopup.setVisible(false);

					} catch (Throwable t) {

						// TODO: handle
						System.out.println("yes sir");
					}
				}
			}

			/*
			 * if (event.getRecord() != null) { try { final String url =
			 * "images/full/" + event.getRecord().getAttribute(Constants.ATTR_UUID);
			 * final Image full = new Image(); Image.prefetch(url); final ModalWindow
			 * mw = new ModalWindow(layout);
			 * mw.setLoadingIcon("loadingAnimation.gif"); mw.show(true); Timer timer1
			 * = new Timer() {
			 * 
			 * @Override public void run() { full.setUrl(url);
			 * full.setHeight("700px"); full.addLoadHandler(new LoadHandler() {
			 * 
			 * @Override public void onLoad(LoadEvent event) { mw.hide();
			 * imagePopup.setWidget(full); imagePopup.center(); } }); } };
			 * timer1.schedule(150);
			 * 
			 * } catch (Throwable t) { System.out.println("yes sir"); } } }
			 */
		});

		DetailViewerField pictureField = new DetailViewerField(Constants.ATTR_PICTURE);
		pictureField.setType("image");
		pictureField.setImageURLPrefix(Constants.SERVLET_THUMBNAIL_PREFIX + '/');
		pictureField.setImageWidth(80);
		pictureField.setImageHeight(120);

		DetailViewerField nameField = new DetailViewerField(Constants.ATTR_NAME);
		nameField.setDetailFormatter(new DetailFormatter() {
			@Override
			public String format(Object value, Record record, DetailViewerField field) {
				return "Title: " + value;
			}
		});
		DetailViewerField descField = new DetailViewerField(Constants.ATTR_DESC);

		tileGrid.setFields(pictureField, nameField, descField);
		imagesLayout.addMember(tileGrid);
		getUiHandlers().onAddDigitalObject(tileGrid, menu);
		return tileGrid;
	}

	/**
	 * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
	 * 
	 * @return the widget
	 */
	@Override
	public Widget asWidget() {
		return layout;
	}

	@Override
	public PopupPanel getPopupPanel() {
		return imagePopup;
	}

	// @Override
	// public void fireEvent(GwtEvent<?> event) {
	// eventBus.fireEvent(this, event);
	// }

}