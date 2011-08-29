/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.fi.muni.xkremser.editor.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
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
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.events.DragMoveEvent;
import com.smartgwt.client.widgets.events.DragMoveHandler;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.events.ItemHoverEvent;
import com.smartgwt.client.widgets.form.fields.events.ItemHoverHandler;
import com.smartgwt.client.widgets.layout.HLayout;
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

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.client.domain.NamedGraphModel;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter.MyView;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.ModifyView.MyUiHandlers;
import cz.fi.muni.xkremser.editor.client.view.tab.DCTab;
import cz.fi.muni.xkremser.editor.client.view.tab.InfoTab;
import cz.fi.muni.xkremser.editor.client.view.tab.ModsTab;
import cz.fi.muni.xkremser.editor.client.view.window.ModalWindow;
import cz.fi.muni.xkremser.editor.client.view.window.ModsWindow;

import cz.fi.muni.xkremser.editor.shared.valueobj.DigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class ModifyView.
 */
public class ModifyView
        extends ViewWithUiHandlers<MyUiHandlers>
        implements MyView {

    private final LangConstants lang;

    /**
     * The Interface MyUiHandlers.
     */
    public interface MyUiHandlers
            extends UiHandlers {

        void onAddDigitalObject(final TileGrid tileGrid, final Menu menu);

        void onAddDigitalObject(final String uuid, final ImgButton closeButton);

        void onSaveDigitalObject(final DigitalObjectDetail digitalObject, boolean versionable);

        void getDescription(final String uuid, final TabSet tabSet, final String tabId);

        void putDescription(final String uuid, final String description, boolean common);

        void onRefresh(final String uuid);

        void getStream(final String uuid, final DigitalObjectModel model, TabSet ts);

        void close(final String uuid);

        void onChangeFocusedTabSet(final String focusedUuid);
    }

    /** The Constant ID_DC. */
    private static final String ID_DC = "dc";

    /** The Constant ID_MODS. */
    private static final String ID_MODS = "mods";

    /** The Constant ID_FULL. */
    private static final String ID_FULL = "full";

    /** The Constant ID_DESC. */
    private static final String ID_DESC = "desc";

    /** The Constant ID_TAB. */
    private static final String ID_TAB = "tab";

    /** The Constant ID_TABSET. */
    public static final String ID_TABSET = "tabset";

    /** The Constant ID_MODEL. */
    public static final String ID_MODEL = "model";

    /** The Constant ID_NAME. */
    public static final String ID_NAME = "name";

    /** The Constant ID_EDIT. */
    public static final String ID_EDIT = "edit";

    /** The Constant ID_SEPARATOR. */
    public static final String ID_SEPARATOR = "separator";

    /** The Constant ID_SEL_ALL. */
    public static final String ID_SEL_ALL = "all";

    /** The Constant ID_SEL_NONE. */
    public static final String ID_SEL_NONE = "none";

    /** The Constant ID_SEL_INV. */
    public static final String ID_SEL_INV = "invert";

    /** The Constant ID_COPY. */
    public static final String ID_COPY = "copy";

    /** The Constant ID_PASTE. */
    public static final String ID_PASTE = "paste";

    /** The Constant ID_DELETE. */
    public static final String ID_DELETE = "delete";

    /** The Constant DC_TAB_INDEX. */
    public static final int DC_TAB_INDEX = 1;

    /** The Constant TAB_INITIALIZED. */
    public static final String TAB_INITIALIZED = "initialized";

    /** The ocr content. */
    private final Map<TabSet, TextAreaItem> ocrContent = new HashMap<TabSet, TextAreaItem>();

    /** The ocr text content. */
    private final Map<TextAreaItem, String> ocrTextContent = new HashMap<TextAreaItem, String>();

    /** The dc tab. */
    private final Map<TabSet, DCTab> dcTab = new HashMap<TabSet, DCTab>();

    /** The mods tab. */
    private final Map<TabSet, Tab> modsTab = new HashMap<TabSet, Tab>();

    /** The info tab. */
    private final Map<TabSet, InfoTab> infoTabs = new HashMap<TabSet, InfoTab>();

    /** The DCs. */
    private final Map<TabSet, DublinCore> dcMap = new HashMap<TabSet, DublinCore>();

    /** The Map of ModsCollectionClients **/
    private final Map<TabSet, ModsCollectionClient> modsCollections =
            new HashMap<TabSet, ModsCollectionClient>();

    private final Map<TabSet, List<Tab>> itemTabs = new HashMap<TabSet, List<Tab>>();

    private final HashMap<TabSet, Map<DigitalObjectModel, TileGrid>> itemGrids =
            new HashMap<TabSet, Map<DigitalObjectModel, TileGrid>>();

    /** The opened objects tabsets. */
    private final Map<String, TabSet> openedObjectsTabsets = new HashMap<String, TabSet>();

    /** The opened objects uuids. */
    private final Map<TabSet, String> openedObjectsUuids = new HashMap<TabSet, String>();

    /** The clipboard. */
    private Record[] clipboard;

    /** The layout. */
    private final VLayout layout;

    /** The top tab set1. */
    private TabSet topTabSet1;

    /** The top tab set2. */
    private TabSet topTabSet2;
    /** The image popup. */
    private PopupPanel imagePopup;

    /** Whether is topTabSet2 focused or not **/
    private boolean isSecondFocused = false;

    /** The publish-window **/
    private Window winModal = null;

    /** The value of background color of focused tabSet **/
    private static final String bgColorFocused = "#ededed";

    /** The value of background color of "unfocused" tabSet **/
    private static final String bgColorUnfocused = "white";

    private ModsWindow modsWindow = null;

    /**
     * Instantiates a new modify view.
     */
    @Inject
    public ModifyView(LangConstants lang) {
        this.lang = lang;
        layout = new VLayout();
        // layout.addMember(new Label("working"));
        layout.setOverflow(Overflow.AUTO);
        layout.setLeaveScrollbarGap(true);
        imagePopup = new PopupPanel(true);
        imagePopup.setGlassEnabled(true);
        imagePopup.setAnimationEnabled(true);
    }

    public void showBasicModsWindow(final TabSet focusedTabSet) {
        modsWindow =
                new ModsWindow(modsCollections.get(focusedTabSet),
                               openedObjectsUuids.get(focusedTabSet),
                               lang) {

                    @Override
                    protected void init() {
                        show();
                        focus();
                        getPublish().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

                            @Override
                            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                                publishShortCut(focusedTabSet);
                            }
                        });
                        getClose().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

                            @Override
                            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                                destroy();
                                modsWindow = null;
                            }
                        });
                    }

                };
    }

    /**
     * Adds background color to tabSets on the basis of focus
     */
    private void changeFocus() {
        if (!isSecondFocused || topTabSet2 == null) {
            if (topTabSet1 != null) {
                topTabSet1.setBackgroundColor(bgColorFocused);
                getUiHandlers().onChangeFocusedTabSet(openedObjectsUuids.get(topTabSet1));
            } else {
                getUiHandlers().onChangeFocusedTabSet(null);
            }
            if (topTabSet2 != null) {
                topTabSet2.setBackgroundColor(bgColorUnfocused);
            }
        } else if (isSecondFocused && topTabSet2 != null) {
            topTabSet2.setBackgroundColor(bgColorFocused);
            topTabSet1.setBackgroundColor(bgColorUnfocused);
            getUiHandlers().onChangeFocusedTabSet(openedObjectsUuids.get(topTabSet2));
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter.MyView#getName
     * ()
     */
    @Override
    public HasValue<String> getName() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter.MyView#
     * fromClipboard()
     */
    @Override
    public Record[] fromClipboard() {
        return this.clipboard;
    }

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter.MyView#
     * toClipboard (com.smartgwt.client.data.Record[])
     */
    @Override
    public void toClipboard(Record[] data) {
        this.clipboard = data;
    }

    /**
     * Method for handle publish short-cut of focusedTabSet
     * 
     * @param focusedTabSet
     */
    private void publishShortCut(TabSet focusedTabSet) {
        if (winModal != null) {
            winModal.destroy();
            winModal = null;
        }
        InfoTab infoT = infoTabs.get(focusedTabSet);
        DCTab dcT = dcTab.get(focusedTabSet);
        publish(focusedTabSet,
                infoT.getModel(),
                dcT.getDc(),
                modsCollections.get(focusedTabSet),
                focusedTabSet);
    }

    /**
     * Method for close currently displayed window
     */
    private void escShortCut() {
        if (winModal == null && modsWindow != null) {
            modsWindow.destroy();
            modsWindow = null;
        } else if (winModal != null) {
            winModal.destroy();
            winModal = null;
        }
    }

    /**
     * Shifts the focus right in set of Tabs
     * 
     * @param tabSet
     */
    private void shiftRight(TabSet tabSet) {
        int currentTab = tabSet.getSelectedTabNumber();
        if (currentTab == tabSet.getNumTabs() - 1) {
            currentTab = -1;
        }
        tabSet.selectTab(currentTab + 1);
    }

    /**
     * Shifts the focus left in set of Tabs
     * 
     * @param tabSet
     */
    private void shiftLeft(TabSet tabSet) {
        int currentTab = tabSet.getSelectedTabNumber();
        if (currentTab == 0) {
            currentTab = tabSet.getNumTabs();
        }
        tabSet.selectTab(currentTab - 1);
    }

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter.MyView#
     * addDigitalObject(boolean, com.smartgwt.client.data.Record[],
     * com.gwtplatform.dispatch.client.DispatchAsync)
     */
    @Override
    public void addDigitalObject(final String uuid, DigitalObjectDetail detail, boolean refresh) {
        final DublinCore dc = detail.getDc();
        final ModsCollectionClient mods = detail.getMods();
        final String label = detail.getLabel();
        String foxmlString = detail.getFoxmlString();
        String ocr = detail.getOcr();
        DigitalObjectModel model = detail.getModel();

        final TabSet topTabSet = new TabSet();
        topTabSet.setTabBarPosition(Side.TOP);
        topTabSet.setWidth100();
        topTabSet.setHeight100();
        topTabSet.setAnimateTabScrolling(true);
        topTabSet.setAnimateTabScrolling(true);
        topTabSet.setShowPaneContainerEdges(false);
        int insertPosition = -1;
        if (refresh) {
            TabSet toDelete = openedObjectsTabsets.get(uuid);
            if (toDelete != null) {
                insertPosition = layout.getMemberNumber(toDelete);
                layout.removeMember(toDelete);
                removeTuple(toDelete);
                toDelete.destroy();
            } else {
                refresh = false;
            }
        }
        makeTuple(uuid, topTabSet);

        List<DigitalObjectModel> models = NamedGraphModel.getChildren(model);
        List<Tab> containerTabs = new ArrayList<Tab>();
        if (models != null) { // has any containers (if not, it is a page)
            Map<String, String> labels = new HashMap<String, String>();
            labels.put(DigitalObjectModel.INTERNALPART.getValue(), lang.internalparts());
            labels.put(DigitalObjectModel.MONOGRAPHUNIT.getValue(), lang.monographunits());
            labels.put(DigitalObjectModel.PERIODICALITEM.getValue(), lang.periodicalitems());
            labels.put(DigitalObjectModel.PERIODICALVOLUME.getValue(), lang.periodicalvolumes());
            int i = 0;
            for (DigitalObjectModel md : models) {
                Tab containerTab = null;
                if (md.equals(DigitalObjectModel.PAGE)) {
                    containerTab = new Tab(lang.pages(), "pieces/16/pawn_red.png");
                    containerTab.setWidth(lang.pages().length() * 6 + 35);
                } else {
                    containerTab =
                            new Tab(labels.get(md.getValue()), "pieces/16/cubes_"
                                    + (i == 0 ? "green" : i == 1 ? "blue" : "yellow") + ".png");
                    containerTab.setWidth(((labels.get(md.getValue())).length() * 6) + 30);
                }
                containerTab.setAttribute(TAB_INITIALIZED, false);
                containerTab.setAttribute(ID_MODEL, md.getValue());
                containerTabs.add(containerTab);
                i++;
            }
            itemTabs.put(topTabSet, containerTabs);
        }
        Map<String, String> labelsSingular = new HashMap<String, String>();
        labelsSingular.put(DigitalObjectModel.INTERNALPART.getValue(), lang.internalpart());
        labelsSingular.put(DigitalObjectModel.MONOGRAPH.getValue(), lang.monograph());
        labelsSingular.put(DigitalObjectModel.MONOGRAPHUNIT.getValue(), lang.monographunit());
        labelsSingular.put(DigitalObjectModel.PAGE.getValue(), lang.page());
        labelsSingular.put(DigitalObjectModel.PERIODICAL.getValue(), lang.periodical());
        labelsSingular.put(DigitalObjectModel.PERIODICALITEM.getValue(), lang.periodicalitem());
        labelsSingular.put(DigitalObjectModel.PERIODICALVOLUME.getValue(), lang.periodicalvolume());
        String previewPID = DigitalObjectModel.PAGE.equals(model) ? uuid : detail.getFirstPageURL();
        modsCollections.put(topTabSet, mods);
        dcMap.put(topTabSet, dc);
        final Tab infoTab =
                new InfoTab("Info", "pieces/16/cubes_all.png", label, dc, lang, labelsSingular.get(model
                        .getValue()), model, previewPID);
        infoTabs.put(topTabSet, (InfoTab) infoTab);
        ((InfoTab) infoTab).getQuickEdit().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                showBasicModsWindow(topTabSet);
            }
        });

        final Tab dublinTab = new DCTab("DC", "pieces/16/piece_green.png");
        dublinTab.setAttribute(TAB_INITIALIZED, false);
        dublinTab.setAttribute(ID_TAB, ID_DC);
        dcTab.put(topTabSet, (DCTab) dublinTab);

        final Tab moTab = new Tab("MODS", "pieces/16/piece_blue.png");
        moTab.setAttribute(TAB_INITIALIZED, false);
        moTab.setAttribute(ID_TAB, ID_MODS);
        modsTab.put(topTabSet, moTab);

        final Tab descriptionTab = new Tab(lang.description(), "pieces/16/pieces.png");
        descriptionTab.setAttribute(TAB_INITIALIZED, false);
        descriptionTab.setAttribute(ID_TAB, ID_DESC);
        descriptionTab.setWidth(100);

        Tab thumbTab = null;
        boolean picture = model.equals(DigitalObjectModel.PAGE);
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

            thumbTab = new Tab(lang.thumbnail(), "pieces/16/pawn_yellow.png");
            thumbTab.setWidth((lang.thumbnail().length() * 6) + 30);
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
            fullTab = new Tab(lang.fullImg(), "pieces/16/pawn_yellow.png");
            fullTab.setWidth((lang.fullImg().length() * 6) + 30);
            fullTab.setAttribute(ID_TAB, ID_FULL);
        }

        Tab foxmlTab = null;
        boolean fox = foxmlString != null && !"".equals(foxmlString);
        if (fox) {
            foxmlTab = new Tab("FOXML", "pieces/16/cube_frame.png");
            HTMLFlow l = new HTMLFlow("<code>" + foxmlString + "</code>");
            l.setCanSelectText(true);
            foxmlTab.setPane(l);
        }

        List<Tab> tabList = new ArrayList<Tab>();
        tabList.add(infoTab);
        if (containerTabs != null && containerTabs.size() > 0) tabList.addAll(containerTabs);
        tabList.addAll(Arrays.asList(dublinTab, moTab, descriptionTab));
        if (picture) {
            tabList.add(ocTab);
            tabList.add(thumbTab);
            tabList.add(fullTab);
        }
        if (fox) tabList.add(foxmlTab);

        topTabSet.setTabs(tabList.toArray(new Tab[] {}));

        topTabSet.addTabSelectedHandler(new TabSelectedHandler() {

            @Override
            public void onTabSelected(final TabSelectedEvent event) {
                isSecondFocused = topTabSet == topTabSet2;
                changeFocus();
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
                } else if (ID_DC.equals(event.getTab().getAttribute(ID_TAB))
                        && event.getTab().getPane() == null) {
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
                } else if (event.getTab().getAttribute(ID_MODEL) != null && event.getTab().getPane() == null) {
                    final ModalWindow mw = new ModalWindow(topTabSet);
                    mw.setLoadingIcon("loadingAnimation.gif");
                    mw.show(true);
                    Timer timer = new Timer() {

                        @Override
                        public void run() {
                            getUiHandlers().getStream(uuid,
                                                      DigitalObjectModel.parseString(event.getTab()
                                                              .getAttribute(ID_MODEL)),
                                                      event.getTab().getTabSet());
                            mw.hide();
                        }
                    };
                    timer.schedule(25);
                } else if (ID_FULL.equals(event.getTab().getAttribute(ID_TAB))
                        && event.getTab().getPane() == null) {
                    final ModalWindow mw = new ModalWindow(topTabSet);
                    mw.setLoadingIcon("loadingAnimation.gif");
                    mw.show(true);
                    ImagePreloader
                            .load("images/full/" + uuid + "?" + Constants.URL_PARAM_NOT_SCALE + "=true",
                                  new ImageLoadHandler() {

                                      @Override
                                      public void imageLoaded(final ImageLoadEvent event1) {
                                          if (!event1.isLoadFailed()) {
                                              final int width = event1.getDimensions().getWidth();
                                              final int height = event1.getDimensions().getHeight();
                                              Timer timer = new Timer() {

                                                  @Override
                                                  public void run() {
                                                      final Img full =
                                                              new Img("full/" + uuid + "?"
                                                                      + Constants.URL_PARAM_NOT_SCALE
                                                                      + "=true", width, height);
                                                      full.draw();
                                                      full.addClickHandler(new ClickHandler() {

                                                          private boolean turn = true;

                                                          @Override
                                                          public void onClick(final ClickEvent event2) {
                                                              if (turn) {
                                                                  full.animateRect(5,
                                                                                   5,
                                                                                   width / 2,
                                                                                   height / 2);
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

                } else if (ID_DESC.equals(event.getTab().getAttribute(ID_TAB))
                        && event.getTab().getPane() == null) {
                    getUiHandlers().getDescription(uuid, event.getTab().getTabSet(), event.getTab().getID());
                    event.getTab().setAttribute(TAB_INITIALIZED, true);
                }
            }
        });

        // MENU
        Menu menu = getMenu(topTabSet, model, dc, mods);
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
                closeButton.setPrompt(lang.closeHoover() + " Ctrl+Alt+C");
            }
        });

        closeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                close(topTabSet);
            }
        });
        topTabSet.setTabBarControls(TabBarControls.TAB_SCROLLER,
                                    TabBarControls.TAB_PICKER,
                                    menuButton,
                                    closeButton);
        topTabSet.setAnimateTabScrolling(true);

        layout.setMembersMargin(15);
        if (!refresh) {
            if (isSecondFocused || topTabSet1 == null) {

                if (topTabSet1 != null) {
                    TabSet toDelete = topTabSet1;
                    layout.removeMember(toDelete);
                    removeTuple(toDelete);
                    toDelete.destroy();
                }
                topTabSet1 = topTabSet;
                layout.addMember(topTabSet1, 0);
            } else {
                if (topTabSet2 != null) {
                    TabSet toDelete = topTabSet2;
                    layout.removeMember(toDelete);
                    removeTuple(toDelete);
                    toDelete.destroy();
                }
                topTabSet2 = topTabSet;
                layout.addMember(topTabSet2, 1);
            }
            isSecondFocused = !isSecondFocused;
            changeFocus();
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
        getUiHandlers().onAddDigitalObject(uuid, closeButton);
    }

    /**
     * Method for close focused tabSet
     * 
     * @param topTabSet
     */
    private void close(TabSet topTabSet) {
        layout.removeMember(topTabSet);
        if (topTabSet1 == topTabSet) {
            removeTuple(topTabSet1);
            topTabSet1.destroy();
            topTabSet1 = null;
            if (topTabSet2 != null) { // move up
                topTabSet1 = topTabSet2;
                topTabSet2 = null;
            }
        } else {
            removeTuple(topTabSet2);
            topTabSet2.destroy();
            topTabSet2 = null;
        }
        isSecondFocused = false;
        changeFocus();
    }

    /**
     * Sets the tile grid.
     * 
     * @param pages
     *        the pages
     * @param model
     *        the model
     * @return the tile grid
     */
    private TileGrid getTileGrid(final boolean pages, final String model) {

        final TileGrid tileGrid = new TileGrid();
        if (pages) {
            tileGrid.setTileWidth(90);
            tileGrid.setTileHeight(135);
        } else {
            tileGrid.setTileWidth(105);
            tileGrid.setTileHeight(115);
        }
        tileGrid.setHeight100();
        tileGrid.setWidth100();
        tileGrid.setCanDrag(true);
        tileGrid.setCanAcceptDrop(true);
        tileGrid.setShowAllRecords(true);
        Menu menu = new Menu();
        menu.setShowShadow(true);
        menu.setShadowDepth(10);
        MenuItem editItem = new MenuItem(lang.menuEdit(), "icons/16/edit.png");
        editItem.setAttribute(ID_NAME, ID_EDIT);
        editItem.setEnableIfCondition(new MenuItemIfFunction() {

            @Override
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                return tileGrid.getSelection() != null && tileGrid.getSelection().length == 1;
            }
        });

        MenuItem selectAllItem = new MenuItem(lang.menuSelectAll(), "icons/16/document_plain_new.png");
        selectAllItem.setAttribute(ID_NAME, ID_SEL_ALL);

        MenuItem deselectAllItem =
                new MenuItem(lang.menuDeselectAll(), "icons/16/document_plain_new_Disabled.png");
        deselectAllItem.setAttribute(ID_NAME, ID_SEL_NONE);

        MenuItem invertSelectionItem = new MenuItem(lang.menuInvertSelection(), "icons/16/invert.png");
        invertSelectionItem.setAttribute(ID_NAME, ID_SEL_INV);

        MenuItemSeparator separator = new MenuItemSeparator();
        separator.setAttribute(ID_NAME, ID_SEPARATOR);

        MenuItem copyItem = new MenuItem(lang.menuCopySelected(), "icons/16/copy.png");
        copyItem.setAttribute(ID_NAME, ID_COPY);
        copyItem.setEnableIfCondition(new MenuItemIfFunction() {

            @Override
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                return tileGrid.getSelection().length > 0;
            }
        });

        MenuItem pasteItem = new MenuItem(lang.menuPaste(), "icons/16/paste.png");
        pasteItem.setAttribute(ID_NAME, ID_PASTE);
        pasteItem.setEnableIfCondition(new MenuItemIfFunction() {

            @Override
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                return ModifyView.this.clipboard != null && ModifyView.this.clipboard.length > 0;
            }
        });

        MenuItem removeSelectedItem = new MenuItem(lang.menuRemoveSelected(), "icons/16/close.png");
        removeSelectedItem.setAttribute(ID_NAME, ID_DELETE);
        removeSelectedItem.setEnableIfCondition(new MenuItemIfFunction() {

            @Override
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                return tileGrid.getSelection().length > 0;
            }
        });

        menu.setItems(editItem,
                      separator,
                      selectAllItem,
                      deselectAllItem,
                      invertSelectionItem,
                      separator,
                      copyItem,
                      pasteItem,
                      removeSelectedItem);
        tileGrid.setContextMenu(menu);
        tileGrid.setDropTypes(model);
        tileGrid.setDragType(model);
        tileGrid.setDragAppearance(DragAppearance.TRACKER);

        tileGrid.addDragMoveHandler(new DragMoveHandler() {

            @Override
            public void onDragMove(DragMoveEvent event) {

                if (event.isCtrlKeyDown()) {
                    tileGrid.setDragDataAction(DragDataAction.COPY);
                    String html = Canvas.imgHTML("pieces/24/copy.png", 24, 24);
                    EventHandler.setDragTracker(html);

                } else {
                    tileGrid.setDragDataAction(DragDataAction.MOVE);
                }
            }
        });

        if (pages) {
            getPopupPanel();
            tileGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

                @Override
                public void onRecordDoubleClick(final RecordDoubleClickEvent event) {
                    if (event.getRecord() != null) {
                        final ModalWindow mw = new ModalWindow(layout);
                        mw.setLoadingIcon("loadingAnimation.gif");
                        mw.show(true);
                        try {
                            final Image full =
                                    new Image("images/full/"
                                            + event.getRecord().getAttribute(Constants.ATTR_UUID));
                            full.setHeight("700px");
                            full.addLoadHandler(new LoadHandler() {

                                @Override
                                public void onLoad(LoadEvent event) {
                                    mw.hide();
                                    imagePopup.setVisible(true);
                                    imagePopup.center();
                                }
                            });
                            full.addErrorHandler(new ErrorHandler() {

                                @Override
                                public void onError(ErrorEvent event) {
                                    mw.show(lang.unableToLoadImg(), true);
                                    imagePopup.setWidget(null);
                                    imagePopup.hide();
                                    imagePopup.setVisible(false);
                                    mw.hide();
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
                            System.out.println("iii");
                            mw.hide();
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
            pictureField.setImageHeight(110);
        } else {
            pictureField.setImageWidth(95);
            pictureField.setImageHeight(95);
        }

        DetailViewerField nameField = new DetailViewerField(Constants.ATTR_NAME);
        nameField.setDetailFormatter(new DetailFormatter() {

            @Override
            public String format(Object value, Record record, DetailViewerField field) {
                return lang.title() + ": " + value;
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

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter.MyView#getEditor
     * (java.lang.String, java.lang.String, boolean)
     */
    @Override
    public Canvas getEditor(String text, final String uuid, final boolean common) {
        final VLayout layout = new VLayout();
        layout.setWidth100();
        layout.setHeight100();
        String title =
                common ? "<h3>" + lang.descriptionAll() + "</h3><br />" : "<h3>" + lang.descriptionSingle()
                        + "</h3><br />";
        HTMLFlow titleHTML = new HTMLFlow(title);
        final RichTextEditor richTextEditor = new RichTextEditor();
        richTextEditor.setHeight100();
        richTextEditor.setWidth100();
        richTextEditor.setOverflow(Overflow.HIDDEN);
        richTextEditor.setValue(text);
        layout.addMember(titleHTML);
        layout.addMember(richTextEditor);
        DynamicForm form = new DynamicForm();
        form.setExtraSpace(10);
        final ButtonItem button = new ButtonItem(lang.save());
        button.setWidth(150);
        button.setHoverOpacity(75);
        button.setHoverStyle("interactImageHover");
        button.addItemHoverHandler(new ItemHoverHandler() {

            @Override
            public void onItemHover(ItemHoverEvent event) {
                button.setPrompt(lang.saveHoover());
            }
        });
        button.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                getUiHandlers().putDescription(uuid, richTextEditor.getValue(), common);
            }
        });
        form.setItems(button);
        layout.addMember(form);
        return layout;
    }

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter.MyView#
     * getPopupPanel()
     */
    @Override
    public PopupPanel getPopupPanel() {
        if (imagePopup == null) {
            imagePopup = new PopupPanel(true);
            imagePopup.setGlassEnabled(true);
            imagePopup.setAnimationEnabled(true);
        }
        return imagePopup;
    }

    /**
     * Make tuple.
     * 
     * @param uuid
     *        the uuid
     * @param tabSet
     *        the tab set
     */
    private void makeTuple(String uuid, TabSet tabSet) {
        openedObjectsTabsets.put(uuid, tabSet);
        openedObjectsUuids.put(tabSet, uuid);
    }

    /**
     * Removes the tuple.
     * 
     * @param tabSet
     *        the tab set
     */
    private void removeTuple(TabSet tabSet) {
        String u = openedObjectsUuids.get(tabSet);
        openedObjectsTabsets.remove(u);
        openedObjectsUuids.remove(tabSet);
        infoTabs.remove(tabSet);
        dcTab.remove(tabSet);
        modsTab.remove(tabSet);
        itemTabs.remove(tabSet);
        itemGrids.remove(tabSet);
    }

    private Menu getMenu(final TabSet topTabSet,
                         final DigitalObjectModel model,
                         final DublinCore dc,
                         final ModsCollectionClient mods) {
        Menu menu = new Menu();
        menu.setShowShadow(true);
        menu.setShadowDepth(10);

        MenuItem newItem = new MenuItem(lang.newItem(), "icons/16/document_plain_new.png", "Ctrl+N");
        MenuItem lockItem = new MenuItem(lang.lockItem(), "icons/16/lock_lock_all.png");
        MenuItem lockTabItem = new MenuItem(lang.lockTabItem(), "icons/16/lock_lock.png");
        MenuItem saveItem = new MenuItem(lang.saveItem(), "icons/16/disk_blue.png", "Ctrl+S");
        MenuItem downloadItem = new MenuItem(lang.downloadItem(), "icons/16/download.png");
        MenuItem removeItem = new MenuItem(lang.removeItem(), "icons/16/close.png");
        MenuItem refreshItem = new MenuItem(lang.refreshItem(), "icons/16/refresh.png", "Ctrl+Alt+R");
        MenuItem publishItem = new MenuItem(lang.publishItem(), "icons/16/add.png", "Ctrl+Alt+P");

        refreshItem.setAttribute(ID_TABSET, topTabSet);
        refreshItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(final MenuItemClickEvent event) {
                refresh((TabSet) event.getItem().getAttributeAsObject(ID_TABSET));
            }
        });

        publishItem.setAttribute(ID_TABSET, topTabSet);
        publishItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(final MenuItemClickEvent event) {
                publish(topTabSet, model, dc, mods, (TabSet) event.getItem().getAttributeAsObject(ID_TABSET));
            }
        });

        menu.setItems(newItem/* , descItem , loadItem */,
                      lockItem,
                      lockTabItem/*
                                  * , openItem
                                  */,
                      saveItem,
                      refreshItem,
                      downloadItem,
                      removeItem,
                      publishItem);
        return menu;
    }

    /**
     * Method for publish focused tabSet
     * 
     * @param topTabSet
     * @param model
     * @param dc
     * @param mods
     * @param ts
     */
    private void publish(final TabSet topTabSet,
                         final DigitalObjectModel model,
                         final DublinCore dc,
                         final ModsCollectionClient mods,
                         final TabSet ts) {
        winModal = new Window();
        winModal.setHeight(160);
        winModal.setWidth(350);
        winModal.setCanDragResize(true);
        winModal.setShowEdges(true);
        winModal.setTitle(lang.publishName());
        winModal.setShowMinimizeButton(false);
        winModal.setIsModal(true);
        winModal.setShowModalMask(true);
        winModal.addCloseClickHandler(new CloseClickHandler() {

            @Override
            public void onCloseClick(CloseClientEvent event) {
                winModal.destroy();
            }
        });

        HTMLFlow label = new HTMLFlow("<h3>" + lang.areYouSure() + "</h3>");
        label.setMargin(5);
        label.setExtraSpace(10);
        final DynamicForm form = new DynamicForm();
        form.setMargin(0);
        form.setWidth(100);
        form.setHeight(20);
        form.setExtraSpace(7);

        final CheckboxItem versionable = new CheckboxItem("versionable", lang.versionable());
        Button publish = new Button();
        publish.setTitle(lang.ok());
        publish.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event2) {

                DublinCore changedDC = null;
                ModsCollectionClient changedMods = null;
                DigitalObjectDetail object = new DigitalObjectDetail(model, null);

                if (modsWindow != null) {

                    changedMods = modsWindow.publishWindow();
                    object.setModsChanged(true);
                    changedDC = dcMap.get(ts);
                    object.setDcChanged(false);

                    if (modsWindow.getReflectInDC()) {
                        changedDC = modsWindow.reflectInDC(changedDC);
                        object.setDcChanged(true);
                    }

                    if (modsWindow != null) {
                        modsWindow.destroy();
                        modsWindow = null;
                    }

                } else {
                    Tab dcT = dcTab.get(ts);
                    Tab modsT = modsTab.get(ts);
                    if (dcT.getAttributeAsBoolean(TAB_INITIALIZED)) {
                        DCTab dcT_ = (DCTab) dcT;
                        changedDC = dcT_.getDc();
                        object.setDcChanged(true);
                    } else {
                        changedDC = dc;
                        object.setDcChanged(false);
                    }

                    if (modsT.getAttributeAsBoolean(TAB_INITIALIZED)) {
                        ModsTab modsT_ = (ModsTab) modsT;
                        changedMods = new ModsCollectionClient();
                        changedMods.setMods(Arrays.asList(modsT_.getMods()));
                        object.setModsChanged(true);
                    } else {
                        changedMods = mods;
                        object.setModsChanged(false);
                    }

                }

                object.setDc(changedDC);
                object.setMods(changedMods);
                //                TabSet ts = (TabSet) event.getItem().getAttributeAsObject(ID_TABSET);
                InfoTab infoT = infoTabs.get(ts);

                if (infoT.getLabelItem() == null && infoT.getOriginalLabel() != null) {
                    object.setLabel("");
                    object.setLabelChanged(true);
                } else {
                    object.setLabelChanged(!infoT.getLabelItem().equals(infoT.getOriginalLabel()));
                    object.setLabel(infoT.getLabelItem());
                }

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

                Map<DigitalObjectModel, TileGrid> tilegrids = itemGrids.get(topTabSet);
                if (tilegrids != null) { // structure has been changed, or at
                                         // least opened
                    List<List<DigitalObjectDetail>> structure = new ArrayList<List<DigitalObjectDetail>>(4);
                    List<DigitalObjectModel> children = NamedGraphModel.getChildren(model);
                    for (DigitalObjectModel md : children) {
                        List<DigitalObjectDetail> data = null;
                        TileGrid tg = tilegrids.get(md);
                        if (tg != null && tg.getData() != null) {
                            data = new ArrayList<DigitalObjectDetail>(tg.getData().length);
                            for (Record rec : tg.getData()) {
                                DigitalObjectDetail child = new DigitalObjectDetail();
                                child.setUuid(rec.getAttributeAsString(Constants.ATTR_UUID));
                                data.add(child);
                            }
                        }
                        structure.add(data);
                    }
                    object.setAllItems(structure);
                }
                getUiHandlers().onSaveDigitalObject(object, versionable.getValueAsBoolean());
                winModal.destroy();
            }
        });
        Button cancel = new Button();
        cancel.setTitle(lang.cancel());
        cancel.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event2) {
                winModal.destroy();
            }
        });
        HLayout hLayout = new HLayout();
        hLayout.setMembersMargin(10);
        hLayout.addMember(publish);
        hLayout.addMember(cancel);
        hLayout.setMargin(5);
        form.setFields(versionable);
        winModal.addItem(label);
        winModal.addItem(form);
        winModal.addItem(hLayout);

        winModal.centerInPage();
        winModal.show();
        publish.focus();
    }

    /**
     * Method for refresh focused tabSet
     * 
     * @param focusedTabSet
     */
    private void refresh(final TabSet focusedTabSet) {
        TabSet ts = focusedTabSet;
        String uuid = openedObjectsUuids.get(ts);
        getUiHandlers().onRefresh(uuid);
    }

    @Override
    public void addStream(Record[] items, String uuid, DigitalObjectModel model) {
        TabSet topTabSet = openedObjectsTabsets.get(uuid);
        List<Tab> containers = itemTabs.get(topTabSet);
        Tab toAdd = null;
        for (Tab tab : containers) {
            if (tab.getAttribute(ID_MODEL).equals(model.getValue())) {
                toAdd = tab;
                break;
            }
        }
        if (toAdd == null) throw new RuntimeException("There is no tab with model " + model);

        final TileGrid grid = getTileGrid(model.equals(DigitalObjectModel.PAGE), model.getValue());
        System.err.println("model: " + model.getValue());
        topTabSet.setTabPane(topTabSet.getSelectedTab().getID(), grid);
        if (items != null) {
            grid.setData(items);
        } else {
            grid.setData(new ContainerRecord[] {});
        }
        toAdd.setPane(grid);
        toAdd.setAttribute(TAB_INITIALIZED, true);
        if (itemGrids.get(topTabSet) == null) {
            itemGrids.put(topTabSet, new HashMap<DigitalObjectModel, TileGrid>());
        }
        itemGrids.get(topTabSet).put(model, grid);
    }

    /** Hot-keys operations **/
    @Override
    public void shortcutPressed(int code) {
        if (topTabSet1 != null) {
            if (code == Constants.CODE_KEY_ESC) {
                escShortCut();

            } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_NUM_5.getCode()
                    && topTabSet2 != null) {
                isSecondFocused = !isSecondFocused;
                changeFocus();
            } else {
                TabSet focusedTabSet = null;
                if (!isSecondFocused || topTabSet2 == null) {
                    focusedTabSet = topTabSet1;
                } else if (isSecondFocused && topTabSet2 != null) {
                    focusedTabSet = topTabSet2;
                }
                if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_PAGE_DOWN.getCode()) {
                    shiftRight(focusedTabSet);
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_PAGE_UP.getCode()) {
                    shiftLeft(focusedTabSet);
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_R.getCode()) {
                    refresh(focusedTabSet);
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_P.getCode()) {
                    publishShortCut(focusedTabSet);
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_C.getCode()) {
                    getUiHandlers().close(openedObjectsUuids.get(focusedTabSet));
                    close(focusedTabSet);
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_B.getCode()) {
                    showBasicModsWindow(focusedTabSet);
                }
            }
        }
    }

}