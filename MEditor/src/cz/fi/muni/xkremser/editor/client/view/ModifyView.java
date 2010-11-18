/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.TabBarControls;
import com.smartgwt.client.util.SC;
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
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
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
import cz.fi.muni.xkremser.editor.client.KrameriusModel;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter.MyView;
import cz.fi.muni.xkremser.editor.client.view.ModifyView.MyUiHandlers;
import cz.fi.muni.xkremser.editor.client.view.tab.DCTab;
import cz.fi.muni.xkremser.editor.client.view.tab.ModsTab;
import cz.fi.muni.xkremser.editor.shared.valueobj.Streams;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class ModifyView.
 */
public class ModifyView extends ViewWithUiHandlers<MyUiHandlers> implements MyView {

	public interface MyUiHandlers extends UiHandlers {

		void onAddDigitalObject(final TileGrid tileGrid, final Menu menu);

		void onAddDigitalObject(final String uuid, final ImgButton closeButton, final Menu menu);

	}

	private static final String ID_DC = "dc";
	private static final String ID_MODS = "mods";
	private static final String ID_FULL = "full";
	private static final String ID_THUMB = "thumb";
	private static final String ID_TAB = "tab";
	public static final String ID_TABSET = "tabset";

	public static final String ID_NAME = "name";
	public static final String ID_EDIT = "edit";
	public static final String ID_SEPARATOR = "separator";
	public static final String ID_SEL_ALL = "all";
	public static final String ID_SEL_NONE = "none";
	public static final String ID_SEL_INV = "invert";
	public static final String ID_COPY = "copy";
	public static final String ID_PASTE = "paste";
	public static final String ID_DELETE = "delete";

	public static final int DC_TAB_INDEX = 1;
	public static final String TAB_INITIALIZED = "initialized";

	private final Map<TabSet, DCTab> dcTab = new HashMap<TabSet, DCTab>();

	private Record[] clipboard;

	/** The layout. */
	private final VLayout layout;

	/** The top tab set1. */
	private TabSet topTabSet1;

	/** The top tab set2. */
	private TabSet topTabSet2;
	/** The image popup. */
	private PopupPanel imagePopup;

	/** The first. */
	private boolean first = true;

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
	public void addDigitalObject(final Record[] pageData, final List<Record[]> containerDataList, final List<KrameriusModel> containerModelList,
			final Streams streams, final String uuid, final boolean picture, final DispatchAsync dispatcher) {
		final DublinCore dc = streams.getDc();
		final ModsCollectionClient mods = streams.getMods();
		// final ModalWindow modal = new ModalWindow(layout);
		// modal.setLoadingIcon("loadingAnimation.gif");
		// modal.show("Loading digital object data...", true);

		final TabSet topTabSet = new TabSet();
		topTabSet.setTabBarPosition(Side.TOP);
		topTabSet.setWidth100();
		topTabSet.setHeight100();

		Tab pageTab = null;
		List<Tab> containerTabs = null;
		if (pageData != null) {
			TileGrid grid = getTileGrid(true);
			grid.setData(pageData);
			pageTab = new Tab("Relations", "pieces/16/pawn_red.png");
			pageTab.setPane(grid);
		}

		if (containerDataList != null) {
			containerTabs = new ArrayList<Tab>(containerModelList.size());
			for (int i = 0; i < containerModelList.size(); i++) {
				TileGrid grid = getTileGrid(false);
				grid.setData(containerDataList.get(i));
				// TODO: localization
				Tab containerTab = new Tab(containerModelList.get(i).getValue(), "pieces/16/pawn_red.png");
				containerTab.setPane(grid);
				containerTabs.add(containerTab);
			}
		}

		final DCTab tTab2 = new DCTab("DC", "pieces/16/pawn_green.png");
		tTab2.setAttribute(TAB_INITIALIZED, false);
		tTab2.setAttribute(ID_TAB, ID_DC);
		dcTab.put(topTabSet, tTab2);

		final Tab tTab3 = new Tab("MODS", "pieces/16/pawn_blue.png");
		tTab3.setAttribute(ID_TAB, ID_MODS);

		Tab tTab4 = null;
		Tab tTab5 = null;
		if (picture) {
			tTab4 = new Tab("Thumbnail", "pieces/16/pawn_white.png");
			final Image full2 = new Image("images/thumbnail/" + uuid);
			final Img image = new Img("thumbnail/" + uuid, full2.getWidth(), full2.getHeight());
			image.setAnimateTime(500);
			image.addClickHandler(new ClickHandler() {
				private boolean turn = false;

				@Override
				public void onClick(ClickEvent event) {
					if (turn) {
						image.animateRect(5, 5, full2.getWidth(), full2.getHeight());
					} else {
						image.animateRect(5, 5, full2.getWidth() * 2, full2.getHeight() * 2);
					}
					turn = !turn;
				}
			});
			tTab4.setPane(image);
			tTab5 = new Tab("Full image", "pieces/16/pawn_yellow.png");
			tTab5.setAttribute(ID_TAB, ID_FULL);
		}

		Tab tTab6 = new Tab("IMG ADM", "pieces/16/piece_blue.png");
		Img tImg6 = new Img("pieces/48/piece_blue.png", 48, 48);
		tTab6.setPane(tImg6);

		Tab tTab7 = new Tab("Policy", "pieces/16/piece_green.png");
		Img tImg7 = new Img("pieces/48/piece_green.png", 48, 48);
		tTab7.setPane(tImg7);

		List<Tab> tabList = new ArrayList<Tab>();
		if (pageTab != null)
			tabList.add(pageTab);
		if (containerTabs != null && containerTabs.size() > 0)
			tabList.addAll(containerTabs);
		tabList.add(tTab2);
		tabList.add(tTab3);
		if (picture) {
			tabList.add(tTab4);
			tabList.add(tTab5);
		}
		tabList.add(tTab6);
		tabList.add(tTab7);

		topTabSet.setTabs(tabList.toArray(new Tab[] {}));
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
							Tab t = new ModsTab(1, true, mods.getMods().get(0));
							TabSet ts = event.getTab().getTabSet();
							ts.setTabPane(event.getTab().getID(), t.getPane());
							mw.hide();
						}
					};
					timer.schedule(25);
				} else if (ID_DC.equals(event.getTab().getAttribute(ID_TAB)) && event.getTab().getPane() == null) {
					final ModalWindow mw = new ModalWindow(topTabSet);
					mw.setLoadingIcon("loadingAnimation.gif");
					mw.show(true);
					Timer timer = new Timer() {
						@Override
						public void run() {
							DCTab t = new DCTab(dc);
							dcTab.put(topTabSet, t);
							TabSet ts = event.getTab().getTabSet();
							ts.setTabPane(event.getTab().getID(), t.getPane());
							t.setAttribute(TAB_INITIALIZED, true);
							mw.hide();
						}
					};
					timer.schedule(25);
				} else if (ID_FULL.equals(event.getTab().getAttribute(ID_TAB)) && event.getTab().getPane() == null) {
					final ModalWindow mw = new ModalWindow(topTabSet);
					mw.setLoadingIcon("loadingAnimation.gif");
					mw.show(true);
					Timer timer = new Timer() {
						@Override
						public void run() {
							final Image full2 = new Image("images/full/" + uuid);
							final Img full = new Img("full/" + uuid, full2.getWidth(), full2.getHeight());
							full.addClickHandler(new ClickHandler() {
								private boolean turn = false;

								@Override
								public void onClick(ClickEvent event) {
									if (turn) {
										full.animateRect(5, 5, full2.getWidth() / 2, full2.getHeight() / 2);
									} else {
										full.animateRect(5, 5, full2.getWidth(), full2.getHeight());
									}
									turn = !turn;
								}
							});

							TabSet ts = event.getTab().getTabSet();
							ts.setTabPane(event.getTab().getID(), full);
							mw.hide();
						}
					};
					timer.schedule(25);
				}
			}
		});

		// MENU
		Menu menu = new Menu();
		menu.setShowShadow(true);
		menu.setShadowDepth(10);

		MenuItem newItem = new MenuItem("New", "icons/16/document_plain_new.png", "Ctrl+N");
		MenuItem descItem = new MenuItem("Add desciption", "icons/16/message.png");
		MenuItem loadItem = new MenuItem("Load metadata", "icons/16/document_plain_new.png");
		MenuItem lockItem = new MenuItem("Lock digital object", "icons/16/lock_lock_all.png");
		MenuItem lockTabItem = new MenuItem("Lock opened tab", "icons/16/lock_lock.png");
		MenuItem openItem = new MenuItem("Open", "icons/16/folder_out.png", "Ctrl+O");
		MenuItem saveItem = new MenuItem("Save", "icons/16/disk_blue.png", "Ctrl+S");
		MenuItem downloadItem = new MenuItem("Download", "icons/16/download.png");
		MenuItem removeItem = new MenuItem("Remove", "icons/16/close.png");
		MenuItem refreshItem = new MenuItem("Refresh", "icons/16/refresh.png");
		MenuItem publishItem = new MenuItem("Publish", "icons/16/add.png");
		publishItem.setAttribute(ID_TABSET, topTabSet);
		publishItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				TabSet ts = (TabSet) event.getItem().getAttributeAsObject(ID_TABSET);
				DCTab tab = dcTab.get(ts);
				if (tab.getAttributeAsBoolean(TAB_INITIALIZED)) {
					SC.say(tab.getDc().toString());
					System.out.println(tab.getDc());
				} else {
					SC.say(streams.toString());
					System.out.println(streams);
				}
			}
		});

		menu.setItems(newItem, descItem, loadItem, lockItem, lockTabItem, openItem, saveItem, refreshItem, downloadItem, removeItem, publishItem);
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
				layout.removeMember(topTabSet);
				if (first || topTabSet1 == null || topTabSet2 == null) {
					first = !first;
				}
				if (topTabSet1 == topTabSet) {
					dcTab.put(topTabSet1, null);
					topTabSet1.destroy();
					topTabSet1 = null;
					if (topTabSet2 != null) { // move up
						topTabSet1 = topTabSet2;
						topTabSet2 = null;
					}
				} else {
					dcTab.put(topTabSet2, null);
					topTabSet2.destroy();
					topTabSet2 = null;
				}
			}
		});
		topTabSet.setTabBarControls(TabBarControls.TAB_SCROLLER, TabBarControls.TAB_PICKER, menuButton, closeButton);
		topTabSet.setAnimateTabScrolling(true);

		// topTabSet.setSelectedTab(2); // TODO: remove
		layout.setMembersMargin(15);
		if (first) {
			if (topTabSet1 != null) {
				dcTab.put(topTabSet1, null);
				layout.removeMember(topTabSet1);
				topTabSet1.destroy();
				topTabSet1 = null;
			}
			topTabSet1 = topTabSet;
			layout.addMember(topTabSet1, 0);
		} else {
			if (topTabSet2 != null) {
				dcTab.put(topTabSet2, null);
				layout.removeMember(topTabSet2);
				topTabSet2.destroy();
				topTabSet2 = null;
			}
			topTabSet2 = topTabSet;
			layout.addMember(topTabSet2, 1);
		}

		getUiHandlers().onAddDigitalObject(uuid, closeButton, menu);
		first = !first;
	}

	/**
	 * Sets the tile grid.
	 */
	private TileGrid getTileGrid(final boolean pages) {

		final TileGrid tileGrid = new TileGrid();
		if (pages) {
			tileGrid.setTileWidth(90);
			tileGrid.setTileHeight(135);
		} else {
			tileGrid.setTileWidth(115);
			tileGrid.setTileHeight(125);
		}
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

		menu.setItems(editItem, separator, selectAllItem, deselectAllItem, invertSelectionItem, separator, copyItem, pasteItem, removeSelectedItem);
		tileGrid.setContextMenu(menu);

		if (pages) {
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
			});
		}

		DetailViewerField pictureField = new DetailViewerField(Constants.ATTR_PICTURE);
		pictureField.setType("image");
		if (pages) {
			pictureField.setImageURLPrefix(Constants.SERVLET_THUMBNAIL_PREFIX + '/');
			pictureField.setImageWidth(80);
			pictureField.setImageHeight(120);
		} else {
			pictureField.setImageWidth(110);
			pictureField.setImageHeight(110);
		}

		DetailViewerField nameField = new DetailViewerField(Constants.ATTR_NAME);
		nameField.setDetailFormatter(new DetailFormatter() {
			@Override
			public String format(Object value, Record record, DetailViewerField field) {
				return "Title: " + value;
			}
		});
		DetailViewerField descField = new DetailViewerField(Constants.ATTR_DESC);

		tileGrid.setFields(pictureField, nameField, descField);
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

}