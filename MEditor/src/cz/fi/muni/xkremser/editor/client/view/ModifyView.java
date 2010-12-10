/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.view;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.reveregroup.gwt.imagepreloader.ImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.ImageLoadHandler;
import com.reveregroup.gwt.imagepreloader.ImagePreloader;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.TabBarControls;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.events.ItemHoverEvent;
import com.smartgwt.client.widgets.form.fields.events.ItemHoverHandler;
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
import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PageDetail;
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

		void onSaveDigitalObject(final AbstractDigitalObjectDetail digitalObject);

		void getDescription(final String uuid, final TabSet tabSet, final String tabId);

		void putDescription(final String uuid, final String description);

		void onRefresh(final String uuid);
	}

	private static final String ID_DC = "dc";
	private static final String ID_MODS = "mods";
	private static final String ID_FULL = "full";
	private static final String ID_DESC = "desc";
	private static final String ID_TAB = "tab";
	public static final String ID_TABSET = "tabset";
	public static final String ID_MODEL = "model";

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

	private final Map<TabSet, TextAreaItem> ocrContent = new HashMap<TabSet, TextAreaItem>();
	private final Map<TextAreaItem, String> ocrTextContent = new HashMap<TextAreaItem, String>();
	private final Map<TabSet, Tab> dcTab = new HashMap<TabSet, Tab>();
	private final Map<TabSet, Tab> modsTab = new HashMap<TabSet, Tab>();

	private final Map<String, TabSet> openedObjectsTabsets = new HashMap<String, TabSet>();
	private final Map<TabSet, String> openedObjectsUuids = new HashMap<TabSet, String>();

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
		// layout.addMember(new Label("working"));
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
			final Streams streams, final String uuid, final boolean picture, final String foxml, final String ocr, boolean refresh, final KrameriusModel model) {

		final DublinCore dc = streams.getDc();
		final ModsCollectionClient mods = streams.getMods();

		final TabSet topTabSet = new TabSet();
		topTabSet.setTabBarPosition(Side.TOP);
		topTabSet.setWidth100();
		topTabSet.setHeight100();
		int insertPosition = -1;
		if (refresh) {
			TabSet toDelete = openedObjectsTabsets.get(uuid);
			if (toDelete != null) {
				insertPosition = layout.getMemberNumber(toDelete);
				dcTab.put(toDelete, null);
				modsTab.put(toDelete, null);
				layout.removeMember(toDelete);
				removeTuple(toDelete);
				toDelete.destroy();
			} else {
				refresh = false;
			}
		}
		makeTuple(uuid, topTabSet);

		Tab imageTab = null;
		final TileGrid pageGrid = pageData != null ? getTileGrid(true, "page") : null;
		List<Tab> containerTabs = null;
		if (pageData != null) {
			pageGrid.setData(pageData);
			imageTab = new Tab("Images", "pieces/16/pawn_red.png");
			imageTab.setPane(pageGrid);
		}

		final TileGrid[] containerGrids = containerModelList == null ? null : new TileGrid[containerModelList.size()];
		if (containerDataList != null) {
			containerTabs = new ArrayList<Tab>(containerModelList.size());
			for (int i = 0; i < containerModelList.size(); i++) {
				String label = containerModelList.get(i).getValue();
				String newLabel = label.substring(0, 1).toUpperCase() + label.substring(1) + "s";
				containerGrids[i] = getTileGrid(false, newLabel);
				containerGrids[i].setData(containerDataList.get(i));
				// TODO: localization
				Tab containerTab = new Tab(containerModelList.get(i).getValue(), "pieces/16/pawn_red.png");
				containerTab.setAttribute(ID_MODEL, containerModelList.get(i).getValue());
				containerTab.setPane(containerGrids[i]);
				containerTabs.add(containerTab);
			}
		}

		final Tab dublinTab = new Tab("DC", "pieces/16/pawn_green.png");
		dublinTab.setAttribute(TAB_INITIALIZED, false);
		dublinTab.setAttribute(ID_TAB, ID_DC);
		dcTab.put(topTabSet, dublinTab);

		final Tab moTab = new Tab("MODS", "pieces/16/pawn_blue.png");
		moTab.setAttribute(TAB_INITIALIZED, false);
		moTab.setAttribute(ID_TAB, ID_MODS);
		modsTab.put(topTabSet, moTab);

		final Tab descriptionTab = new Tab("Description", "pieces/16/pawn_blue.png");
		descriptionTab.setAttribute(TAB_INITIALIZED, false);
		descriptionTab.setAttribute(ID_TAB, ID_DESC);

		Tab thumbTab = null;
		Tab ocTab = picture ? new Tab("OCR", "pieces/16/pawn_white.png") : null;

		Tab fullTab = null;
		if (picture) {
			DynamicForm form = new DynamicForm();
			form.setWidth100();
			form.setHeight100();
			TextAreaItem ocrItem = new TextAreaItem();
			ocrItem.setWidth("600");
			ocrItem.setHeight("*");
			ocrItem.setShowTitle(false);
			if (ocr != null) {
				ocrItem.setValue(ocr);
				ocrTextContent.put(ocrItem, ocr);
			}
			form.setItems(ocrItem);
			ocTab.setPane(form);
			ocrContent.put(topTabSet, ocrItem);

			thumbTab = new Tab("Thumbnail", "pieces/16/pawn_white.png");
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
			thumbTab.setPane(image);
			fullTab = new Tab("Full image", "pieces/16/pawn_yellow.png");
			fullTab.setAttribute(ID_TAB, ID_FULL);
		}

		Tab foxmlTab = null;
		boolean fox = foxml != null && !"".equals(foxml);
		if (fox) {
			foxmlTab = new Tab("FOXML", "pieces/16/piece_blue.png");
			Label l = new Label("<code>" + foxml + "</code>");
			l.setCanSelectText(true);
			foxmlTab.setPane(l);
		}

		List<Tab> tabList = new ArrayList<Tab>();
		if (imageTab != null)
			tabList.add(imageTab);
		if (containerTabs != null && containerTabs.size() > 0)
			tabList.addAll(containerTabs);
		tabList.add(dublinTab);
		tabList.add(moTab);
		tabList.add(descriptionTab);
		if (picture) {
			tabList.add(ocTab);
			tabList.add(thumbTab);
			tabList.add(fullTab);
		}
		if (fox)
			tabList.add(foxmlTab);

		topTabSet.setTabs(tabList.toArray(new Tab[] {}));
		topTabSet.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(final TabSelectedEvent event) {
				// TODO: string(ID_MODS) -> int
				if (ID_MODS.equals(event.getTab().getAttribute(ID_TAB)) && event.getTab().getPane() == null) {
					final ModalWindow mw = new ModalWindow(topTabSet);
					mw.setLoadingIcon("loadingAnimation.gif");
					mw.show(true);
					Timer timer = new Timer() {
						@Override
						public void run() {
							ModsTab t = new ModsTab(1, false);
							VLayout modsLayout = t.getModsLayout(mods.getMods().get(0), false, null, 0);
							modsTab.put(topTabSet, t);
							TabSet ts = event.getTab().getTabSet();
							ts.setTabPane(event.getTab().getID(), modsLayout);
							t.setAttribute(TAB_INITIALIZED, true);
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
					ImagePreloader.load("images/full/" + uuid + "?" + Constants.URL_PARAM_NOT_SCALE + "=true", new ImageLoadHandler() {
						@Override
						public void imageLoaded(final ImageLoadEvent event1) {
							if (!event1.isLoadFailed()) {
								final int width = event1.getDimensions().getWidth();
								final int height = event1.getDimensions().getHeight();
								Timer timer = new Timer() {
									@Override
									public void run() {
										final Img full = new Img("full/" + uuid + "?" + Constants.URL_PARAM_NOT_SCALE + "=true", width, height);
										full.draw();
										full.addClickHandler(new ClickHandler() {
											private boolean turn = true;

											@Override
											public void onClick(final ClickEvent event2) {
												if (turn) {
													full.animateRect(5, 5, width / 2, height / 2);
												} else {
													full.animateRect(5, 5, width, height);
												}
												turn = !turn;
											}
										});
										TabSet ts = event.getTab().getTabSet();
										ts.setTabPane(event.getTab().getID(), full);
										mw.hide();
									}
								};
								timer.schedule(20);
							}
						}
					});

				} else if (ID_DESC.equals(event.getTab().getAttribute(ID_TAB)) && event.getTab().getPane() == null) {
					getUiHandlers().getDescription(uuid, event.getTab().getTabSet(), event.getTab().getID());
					event.getTab().setAttribute(TAB_INITIALIZED, true);
				}
			}
		});

		// MENU
		Menu menu = new Menu();
		menu.setShowShadow(true);
		menu.setShadowDepth(10);

		MenuItem newItem = new MenuItem("New", "icons/16/document_plain_new.png", "Ctrl+N");
		// MenuItem descItem = new MenuItem("Desciption", "icons/16/message.png");
		MenuItem loadItem = new MenuItem("Load metadata", "icons/16/document_plain_new.png");
		MenuItem lockItem = new MenuItem("Lock digital object", "icons/16/lock_lock_all.png");
		MenuItem lockTabItem = new MenuItem("Lock opened tab", "icons/16/lock_lock.png");
		MenuItem openItem = new MenuItem("Open", "icons/16/folder_out.png", "Ctrl+O");
		MenuItem saveItem = new MenuItem("Save", "icons/16/disk_blue.png", "Ctrl+S");
		MenuItem downloadItem = new MenuItem("Download", "icons/16/download.png");
		MenuItem removeItem = new MenuItem("Remove", "icons/16/close.png");
		MenuItem refreshItem = new MenuItem("Refresh", "icons/16/refresh.png");
		MenuItem publishItem = new MenuItem("Publish", "icons/16/add.png");

		openItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {

				Dialog dialog = new Dialog();
				// dialog.

				SC.askforValue("Open digital object", "uuid", new ValueCallback() {

					@Override
					public void execute(String value) {
						System.out.println(value);
					}
				});
				// final Window winModal = new Window();
				// // winModal.setResizeFrom("B", "R", "BR");
				// winModal.setHeight(200);
				// winModal.setWidth(600);
				// winModal.setCanDragResize(true);
				// winModal.setShowEdges(true);
				// winModal.setTitle("Description");
				// winModal.setShowMinimizeButton(false);
				// winModal.setIsModal(true);
				// winModal.setShowModalMask(true);
				// winModal.centerInPage();
				// winModal.addCloseClickHandler(new CloseClickHandler() {
				// @Override
				// public void onCloseClick(CloseClientEvent event) {
				// winModal.destroy();
				// // TODO: save
				// }
				// });
				//
				// winModal.show();
			}
		});

		refreshItem.setAttribute(ID_TABSET, topTabSet);
		refreshItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(final MenuItemClickEvent event) {
				TabSet ts = (TabSet) event.getItem().getAttributeAsObject(ID_TABSET);
				String uuid = openedObjectsUuids.get(ts);
				getUiHandlers().onRefresh(uuid);
			}
		});

		publishItem.setAttribute(ID_TABSET, topTabSet);
		publishItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(final MenuItemClickEvent event) {
				SC.confirm("Publish digital object", "Are you sure you want to publish the digital object?", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							AbstractDigitalObjectDetail object = KrameriusModel.getDetail(model);
							TabSet ts = (TabSet) event.getItem().getAttributeAsObject(ID_TABSET);
							Tab dcT = dcTab.get(ts);
							Tab modsT = modsTab.get(ts);
							TextAreaItem ocrTextItem = null;
							if ((ocrTextItem = ocrContent.get(ts)) != null && ocrTextContent.get(ocrTextItem) != null) {
								String val = (String) ocrTextItem.getValue();
								if (!ocrTextContent.get(ocrTextItem).equals(val)) {
									object.setOcr(val);
									object.setOcrChanged(true);
								}
							} else {
								object.setOcrChanged(false);
							}
							object.setUuid(openedObjectsUuids.get(ts));

							DublinCore changedDC = null;
							if (dcT.getAttributeAsBoolean(TAB_INITIALIZED)) {
								DCTab dcT_ = (DCTab) dcT;
								changedDC = dcT_.getDc();
								object.setDcChanged(true);
							} else {
								changedDC = dc;
								object.setDcChanged(false);
							}
							object.setDc(changedDC);
							ModsCollectionClient changedMods = null;
							if (modsT.getAttributeAsBoolean(TAB_INITIALIZED)) {
								ModsTab modsT_ = (ModsTab) modsT;
								changedMods = new ModsCollectionClient();
								changedMods.setMods(Arrays.asList(modsT_.getMods()));
								object.setModsChanged(true);
							} else {
								changedMods = mods;
								object.setModsChanged(false);
							}
							object.setMods(changedMods);
							if (object.hasPages() && pageGrid != null && pageGrid.getData() != null && pageGrid.getData().length > 0) {
								List<PageDetail> pages = new ArrayList<PageDetail>(pageGrid.getData().length);
								for (Record rec : pageGrid.getData()) {
									PageDetail page = new PageDetail(null);
									page.setUuid(((PageRecord) rec).getUuid());
									pages.add(page);
								}
								object.setPages(pages);
							}
							int cont = object.hasContainers();
							int i = 0;
							if (cont != 0) {
								for (KrameriusModel containerModel : object.getChildContainerModels()) {
									ArrayList<AbstractDigitalObjectDetail> list = null;
									if (containerGrids != null && containerGrids[i] != null && containerGrids[i].getData() != null && containerGrids[i].getData().length > 0) {
										list = new ArrayList<AbstractDigitalObjectDetail>(containerGrids[i].getData().length);
										for (Record rec : containerGrids[i].getData()) {
											AbstractDigitalObjectDetail subObject = KrameriusModel.getDetail(containerModel);
											subObject.setUuid(((ContainerRecord) rec).getUuid());
											list.add(subObject);
										}
									}
									object.getContainers().add(list);
									i++;
								}
							}

							getUiHandlers().onSaveDigitalObject(object);
						}
					}
				});
			}
		});

		menu.setItems(newItem/* , descItem */, loadItem, lockItem, lockTabItem, openItem, saveItem, refreshItem, downloadItem, removeItem, publishItem);
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
		closeButton.setHoverOpacity(75);
		closeButton.setHoverStyle("interactImageHover");
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
					modsTab.put(topTabSet1, null);
					removeTuple(topTabSet1);
					topTabSet1.destroy();
					topTabSet1 = null;
					if (topTabSet2 != null) { // move up
						topTabSet1 = topTabSet2;
						topTabSet2 = null;
					}
				} else {
					dcTab.put(topTabSet2, null);
					modsTab.put(topTabSet2, null);
					removeTuple(topTabSet2);
					topTabSet2.destroy();
					topTabSet2 = null;
				}
			}
		});
		topTabSet.setTabBarControls(TabBarControls.TAB_SCROLLER, TabBarControls.TAB_PICKER, menuButton, closeButton);
		topTabSet.setAnimateTabScrolling(true);

		layout.setMembersMargin(15);
		if (!refresh) {
			if (first) {

				if (topTabSet1 != null) {
					TabSet toDelete = topTabSet1;
					dcTab.put(toDelete, null);
					modsTab.put(toDelete, null);
					layout.removeMember(toDelete);
					removeTuple(toDelete);
					toDelete.destroy();
				}
				topTabSet1 = topTabSet;
				layout.addMember(topTabSet1, 0);
			} else {
				if (topTabSet2 != null) {
					TabSet toDelete = topTabSet2;
					dcTab.put(toDelete, null);
					modsTab.put(toDelete, null);
					layout.removeMember(toDelete);
					removeTuple(toDelete);
					toDelete.destroy();
				}
				topTabSet2 = topTabSet;
				layout.addMember(topTabSet2, 1);
			}
			first = !first;
		} else if (insertPosition != -1) {
			if (insertPosition == 0) {
				topTabSet1 = topTabSet;
				layout.addMember(topTabSet1, 0);
			} else if (insertPosition == 1) {
				topTabSet2 = topTabSet;
				layout.addMember(topTabSet2, 1);
			}
		}
		layout.redraw();
		getUiHandlers().onAddDigitalObject(uuid, closeButton, menu);
	}

	/**
	 * Sets the tile grid.
	 */
	private TileGrid getTileGrid(final boolean pages, final String model) {

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
		tileGrid.setDropTypes(model);
		tileGrid.setDragType(model);
		tileGrid.setDragAppearance(DragAppearance.TRACKER);

		// tileGrid.addDropOverHandler(new DropOverHandler() {
		// @Override
		// public void onDropOver(DropOverEvent event) {
		// // event.get
		// // System.out.println("piip");
		// // String html = Canvas.imgHTML("pieces/24/pawn_blue.png", 24, 24);
		// Object draggable = EventHandler.getDragTarget();
		// Canvas droppable = EventHandler.getDragTarget();
		// System.out.println("onDropOver START on " + droppable.getID());
		// System.out.println("drag object : " + draggable.getClass());
		// System.out.println("onDropOver STOP on " + droppable.getID());
		//
		// }
		// });
		tileGrid.addDropHandler(new DropHandler() {

			@Override
			public void onDrop(DropEvent event) {
				if (event.isCtrlKeyDown()) {
					Canvas droppable = EventHandler.getDragTarget();
					droppable.getID();
					tileGrid.setDragDataAction(DragDataAction.COPY);
				} else {
					tileGrid.setDragDataAction(DragDataAction.MOVE);
				}
			}
		});

		// tileGrid.addDragRepositionMoveHandler(new DragRepositionMoveHandler() {
		// @Override
		// public void onDragRepositionMove(DragRepositionMoveEvent event) {
		// if (event.isCtrlKeyDown()) {
		// System.out.println("FUnguju");
		// String html = Canvas.imgHTML("pieces/24/pawn_blue.png", 24, 24);
		// EventHandler.setDragTracker(html);
		// } else {
		// EventHandler.setDragTracker("ee");
		// }
		// }
		// });

		if (pages) {
			getPopupPanel();
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
	public Canvas getEditor(String text, final String uuid) {
		final VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();
		final RichTextEditor richTextEditor = new RichTextEditor();
		richTextEditor.setHeight100();
		richTextEditor.setWidth100();
		richTextEditor.setOverflow(Overflow.HIDDEN);
		richTextEditor.setValue(text);
		layout.addMember(richTextEditor);
		DynamicForm form = new DynamicForm();
		form.setExtraSpace(10);
		final ButtonItem button = new ButtonItem("Save");
		button.setWidth(150);
		button.setHoverOpacity(75);
		button.setHoverStyle("interactImageHover");
		button.addItemHoverHandler(new ItemHoverHandler() {
			@Override
			public void onItemHover(ItemHoverEvent event) {
				button.setPrompt("Save the description into database.");
			}
		});
		button.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				getUiHandlers().putDescription(uuid, richTextEditor.getValue());
			}
		});
		form.setItems(button);
		layout.addMember(form);
		return layout;
	}

	@Override
	public PopupPanel getPopupPanel() {
		if (imagePopup == null) {
			imagePopup = new PopupPanel(true);
			imagePopup.setGlassEnabled(true);
			imagePopup.setAnimationEnabled(true);
		}
		return imagePopup;
	}

	private void makeTuple(String uuid, TabSet tabSet) {
		openedObjectsTabsets.put(uuid, tabSet);
		openedObjectsUuids.put(tabSet, uuid);
	}

	private void removeTuple(TabSet tabSet) {
		String u = openedObjectsUuids.get(tabSet);
		openedObjectsTabsets.remove(u);
		openedObjectsUuids.remove(tabSet);
	}

}