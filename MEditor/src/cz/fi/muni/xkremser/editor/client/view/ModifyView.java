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

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window.Location;
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
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.TabBarControls;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DragStartEvent;
import com.smartgwt.client.widgets.events.DragStartHandler;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
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
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter.MyView;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.ModifyView.MyUiHandlers;
import cz.fi.muni.xkremser.editor.client.view.other.ContainerRecord;
import cz.fi.muni.xkremser.editor.client.view.other.DCTab;
import cz.fi.muni.xkremser.editor.client.view.other.EditorDragMoveHandler;
import cz.fi.muni.xkremser.editor.client.view.other.EditorTabSet;
import cz.fi.muni.xkremser.editor.client.view.other.HtmlCode;
import cz.fi.muni.xkremser.editor.client.view.other.InfoTab;
import cz.fi.muni.xkremser.editor.client.view.other.ModsTab;
import cz.fi.muni.xkremser.editor.client.view.window.DownloadFoxmlWindow;
import cz.fi.muni.xkremser.editor.client.view.window.LockDigitalObjectWindow;
import cz.fi.muni.xkremser.editor.client.view.window.ModalWindow;
import cz.fi.muni.xkremser.editor.client.view.window.ModsWindow;
import cz.fi.muni.xkremser.editor.client.view.window.RemoveDigitalObjectWindow;
import cz.fi.muni.xkremser.editor.client.view.window.StoreWorkingCopyWindow;
import cz.fi.muni.xkremser.editor.client.view.window.UniversalWindow;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.domain.NamedGraphModel;
import cz.fi.muni.xkremser.editor.shared.rpc.DigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;

/**
 * @author Jiri Kremser
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

        void onHandleWorkingCopyDigObj(final DigitalObjectDetail digitalObject);

        void getDescription(final String uuid, final TabSet tabSet, final String tabId);

        void putDescription(final String uuid, final String description, boolean common);

        void onRefresh(final String uuid);

        void getStream(final String uuid, final DigitalObjectModel model, TabSet ts);

        void close(final String uuid);

        void onChangeFocusedTabSet(final String focusedUuid);

        void openAnotherObject(final String uuid);

        void lockDigitalObject(final EditorTabSet ts);

        void unlockDigitalObject(final EditorTabSet ts);

        void storeFoxmlFile(DigitalObjectDetail detail, EditorTabSet ts);

        void getLockDigitalObjectInformation(final EditorTabSet ts, final boolean calledDuringPublishing);

        void removeDigitalObject(String uuid);
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

    /** The Constant ID_COMPLETELY_DELETE. */
    public static final String ID_COMPLETELY_DELETE = "completelyDelete";

    /** The Constant ID_UUID. */
    public static final String ID_UUID = "uuid";

    /** The Constant ID_MENU_ITEM. */
    public static final String ID_MENU_ITEM = "menuItem";

    /** The attributes enum of items influenced by locks */
    private static enum ATTR_ITEM_INFLUENCED_BY_LOCK {

        /** The value of publish-item. */
        PUBLISH,

        /** The value of remove-item. */
        REMOVE,

        /** The value of lock-item. */
        LOCK,

        /** The value of unlock-item. */
        UNLOCK;
    }

    /** The Constant DC_TAB_INDEX. */
    public static final int DC_TAB_INDEX = 1;

    /** The Constant TAB_INITIALIZED. */
    public static final String TAB_INITIALIZED = "initialized";

    /** The opened objects tabsets. */
    private final Map<String, EditorTabSet> openedObjectsTabsets = new HashMap<String, EditorTabSet>();

    /** The clipboard. */
    private Record[] clipboard;

    /** The layout. */
    private final VLayout layout;

    /** The top tab set1. */
    private EditorTabSet topTabSet1;

    /** The top tab set2. */
    private EditorTabSet topTabSet2;

    /** The image popup. */
    private PopupPanel imagePopup;

    /** Whether is topTabSet2 focused or not **/
    private boolean isSecondFocused = false;

    /** The universal-window **/
    private UniversalWindow universalWindow = null;

    private ModsWindow modsWindow = null;

    private DownloadFoxmlWindow downloadingWindow = null;

    private EditorClientConfiguration config;

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

    public void showBasicModsWindow(final EditorTabSet focusedTabSet) {
        if (modsWindow != null && modsWindow.isCreated()) {
            modsWindow.hide();
            modsWindow = null;
        }
        modsWindow = new ModsWindow(focusedTabSet.getModsCollection(), focusedTabSet.getUuid(), lang) {

            @Override
            protected void init() {
                show();
                focus();
                String lockOwner = focusedTabSet.getLockInfo().getLockOwner();
                if (lockOwner == null || "".equals(lockOwner)) {

                    getPublish().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

                        @Override
                        public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                            publishShortCut(focusedTabSet);
                        }
                    });
                } else {
                    getPublish().setDisabled(true);
                }

                getClose().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                        hide();
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
                updateLockInformation(topTabSet1);
                getUiHandlers().onChangeFocusedTabSet(topTabSet1.getUuid());
            } else {
                getUiHandlers().onChangeFocusedTabSet(null);
            }
            if (topTabSet2 != null) {
                topTabSet2.setBackgroundColor(Constants.BG_COLOR_UNFOCUSED);
            }
        } else if (isSecondFocused && topTabSet2 != null) {

            if (topTabSet2 != null) {
                updateLockInformation(topTabSet2);
            }

            topTabSet1.setBackgroundColor(Constants.BG_COLOR_UNFOCUSED);
            getUiHandlers().onChangeFocusedTabSet(topTabSet2.getUuid());
        }
    }

    @Override
    public void updateLockInformation(EditorTabSet ts) {
        if (ts.getLockInfo().getLockOwner() != null) {
            if ("".equals(ts.getLockInfo().getLockOwner().trim())) {
                ts.setBackgroundColor(Constants.BG_COLOR_FOCUSED_LOCK_BY_USER);
                ts.getInfoTab().showLockInfoButton(true);
            } else {
                ts.setBackgroundColor(Constants.BG_COLOR_FOCUSED_LOCK);
                ts.getInfoTab().showLockInfoButton(false);
            }
        } else {
            ts.setBackgroundColor(Constants.BG_COLOR_FOCUSED);
            if (ts.getInfoTab() != null) {
                ts.getInfoTab().hideLockInfoButton();
            }
        }
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
    private void publishShortCut(EditorTabSet focusedTabSet) {
        if (universalWindow != null) {
            universalWindow.hide();
            universalWindow = null;
        }
        tryToPublish(focusedTabSet);
    }

    /**
     * Method for close currently displayed window
     */
    private void escShortCut() {
        if (downloadingWindow != null && downloadingWindow.isCreated()) {
            downloadingWindow.hide();
            downloadingWindow = null;

        } else if (StoreWorkingCopyWindow.isInstanceVisible()) {
            StoreWorkingCopyWindow.closeInstantiatedWindow();

        } else if (universalWindow != null && universalWindow.isCreated()) {
            universalWindow.hide();
            universalWindow = null;

        } else if (LockDigitalObjectWindow.isInstanceVisible()) {
            LockDigitalObjectWindow.closeInstantiatedWindow();

        } else if (modsWindow != null && modsWindow.isCreated()) {
            modsWindow.hide();
            modsWindow = null;

        } else if (RemoveDigitalObjectWindow.isInstanceVisible()) {
            RemoveDigitalObjectWindow.closeInstantiatedWindow();

        } else if (imagePopup.isVisible()) {
            imagePopup.setVisible(false);
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
        String foxmlString = detail.getFoxmlString();
        String ocr = detail.getOcr();
        DigitalObjectModel model = detail.getModel();

        final EditorTabSet topTabSet = new EditorTabSet();
        topTabSet.setTabBarPosition(Side.TOP);
        topTabSet.setWidth100();
        topTabSet.setHeight100();
        topTabSet.setAnimateTabScrolling(true);
        topTabSet.setShowPaneContainerEdges(false);
        int insertPosition = -1;

        topTabSet.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                isSecondFocused = (topTabSet == topTabSet2);
                changeFocus();
            }
        });

        if (refresh) {
            EditorTabSet toDelete = openedObjectsTabsets.get(uuid);
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
        topTabSet.setLockInfo(detail.getLockInfo());

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
            topTabSet.setItemTab(containerTabs);
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
        topTabSet.setModsCollection(mods);
        topTabSet.setDc(dc);
        final Tab infoTab =
                new InfoTab("Info", "pieces/16/cubes_all.png", lang, detail, labelsSingular.get(model
                        .getValue()), previewPID) {

                    @Override
                    protected void getCurrentLockInfo() {
                        getUiHandlers().getLockDigitalObjectInformation(topTabSet, false);
                    }
                };
        topTabSet.setInfoTab((InfoTab) infoTab);
        ((InfoTab) infoTab).getQuickEdit().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                showBasicModsWindow(topTabSet);
            }
        });

        final Tab dublinTab = new DCTab("DC", "pieces/16/piece_green.png");
        dublinTab.setAttribute(TAB_INITIALIZED, false);
        dublinTab.setAttribute(ID_TAB, ID_DC);
        topTabSet.setDcTab((DCTab) dublinTab);

        final Tab moTab = new Tab("MODS", "pieces/16/piece_blue.png");
        moTab.setAttribute(TAB_INITIALIZED, false);
        moTab.setAttribute(ID_TAB, ID_MODS);
        topTabSet.setModsTab(moTab);

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
            ocrItem.setValue(ocr != null ? ocr : "");
            topTabSet.setOriginalOcrContent(ocr != null ? ocr : "");
            form.setItems(ocrItem);
            ocTab.setPane(form);
            topTabSet.setOcrContent(ocrItem);

            thumbTab = new Tab(lang.thumbnail(), "pieces/16/pawn_yellow.png");
            thumbTab.setWidth((lang.thumbnail().length() * 6) + 30);
            final Image full2 =
                    new Image(Constants.SERVLET_IMAGES_PREFIX + Constants.SERVLET_THUMBNAIL_PREFIX + "/"
                            + uuid);
            final Img image =
                    new Img(Constants.SERVLET_THUMBNAIL_PREFIX + "/" + uuid,
                            full2.getWidth(),
                            full2.getHeight());
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
                            ModsTab t = new ModsTab(1, mods);
                            VLayout modsLayout = t.getModsLayout();
                            topTabSet.setModsTab(t);
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
                            topTabSet.setDcTab(t);
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
                    ImagePreloader.load(Constants.SERVLET_IMAGES_PREFIX + Constants.SERVLET_FULL_PREFIX + '/'
                            + uuid + "?" + Constants.URL_PARAM_NOT_SCALE + "=true", new ImageLoadHandler() {

                        @Override
                        public void imageLoaded(final ImageLoadEvent event1) {
                            if (!event1.isLoadFailed()) {
                                final int width = event1.getDimensions().getWidth();
                                final int height = event1.getDimensions().getHeight();
                                Timer timer = new Timer() {

                                    @Override
                                    public void run() {
                                        final Img full =
                                                new Img(Constants.SERVLET_FULL_PREFIX + '/' + uuid + "?"
                                                                + Constants.URL_PARAM_NOT_SCALE + "=true",
                                                        width,
                                                        height);
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

                } else if (ID_DESC.equals(event.getTab().getAttribute(ID_TAB))
                        && event.getTab().getPane() == null) {
                    getUiHandlers().getDescription(uuid, event.getTab().getTabSet(), event.getTab().getID());
                    event.getTab().setAttribute(TAB_INITIALIZED, true);
                }
            }
        });

        // MENU
        final Menu menu = getMenu(topTabSet, model, dc, mods);
        IMenuButton menuButton = new IMenuButton("Menu", menu);
        menuButton.setWidth(60);
        menuButton.setHeight(16);
        menuButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                IMenuButton eventButton = (IMenuButton) event.getSource();

                EditorTabSet ts = null;
                for (MenuItem item : eventButton.getMenu().getItems()) {
                    if (ts == null) {
                        ts = (EditorTabSet) item.getAttributeAsObject(ID_TABSET);
                    }

                    if (item.getAttributeAsObject(ID_MENU_ITEM) instanceof ATTR_ITEM_INFLUENCED_BY_LOCK) {

                        ATTR_ITEM_INFLUENCED_BY_LOCK attrItem =
                                (ATTR_ITEM_INFLUENCED_BY_LOCK) item.getAttributeAsObject(ID_MENU_ITEM);

                        String lockOwner = ts.getLockInfo().getLockOwner();
                        if (lockOwner != null) {

                            if ("".equals(lockOwner)) {
                                if (ATTR_ITEM_INFLUENCED_BY_LOCK.LOCK == attrItem) {
                                    item.setTitle(lang.updateLock());
                                } else
                                    item.setEnabled(true);
                            } else {
                                if (ATTR_ITEM_INFLUENCED_BY_LOCK.LOCK == attrItem) {
                                    item.setTitle(lang.lockItem());
                                }
                                item.setEnabled(false);
                            }

                        } else {
                            if (ATTR_ITEM_INFLUENCED_BY_LOCK.UNLOCK == attrItem) {
                                item.setEnabled(false);
                            } else {
                                if (ATTR_ITEM_INFLUENCED_BY_LOCK.LOCK == attrItem) {
                                    item.setTitle(lang.lockItem());
                                }
                                item.setEnabled(true);
                            }
                        }
                    }
                }
                eventButton.getMenu().redraw();
            }
        });

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
                    EditorTabSet toDelete = topTabSet1;
                    layout.removeMember(toDelete);
                    removeTuple(toDelete);
                    toDelete.destroy();
                }
                topTabSet1 = topTabSet;
                layout.addMember(topTabSet1, 0);
            } else {
                if (topTabSet2 != null) {
                    EditorTabSet toDelete = topTabSet2;
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
            tileGrid.setTileWidth(Constants.TILEGRID_ITEM_WIDTH);
            tileGrid.setTileHeight(Constants.TILEGRID_ITEM_HEIGHT);
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
        MenuItem editItem = new MenuItem(lang.menuEdit(), Constants.PATH_IMG_EDIT);
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

        MenuItem completelyRemove =
                new MenuItem(lang.menuCompletelyRemove(), "icons/16/completelyRemove.png");
        completelyRemove.setAttribute(ID_NAME, ID_COMPLETELY_DELETE);
        completelyRemove.setEnableIfCondition(new MenuItemIfFunction() {

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
                      removeSelectedItem,
                      completelyRemove);
        tileGrid.setContextMenu(menu);
        tileGrid.setDropTypes(model);
        tileGrid.setDragType(model);
        tileGrid.setDragAppearance(DragAppearance.TRACKER);
        final EditorDragMoveHandler dragHandler =
                new EditorDragMoveHandler(tileGrid, DigitalObjectModel.parseString(model));
        final DigitalObjectModel mod = DigitalObjectModel.parseString(model);
        final boolean isPage = DigitalObjectModel.PAGE.equals(mod);
        final String modelIcon = Canvas.imgHTML(mod.getIcon(), 25, 25);
        tileGrid.addDragMoveHandler(dragHandler);

        tileGrid.addDragStartHandler(new DragStartHandler() {

            @Override
            public void onDragStart(DragStartEvent event) {
                if (tileGrid.getDragData().length != 0) {
                    tileGrid.setDragAppearance(DragAppearance.TRACKER);
                    String name = tileGrid.getSelectedRecord().getAttribute(Constants.ATTR_NAME);
                    String pageIcon = null;

                    if (isPage) {
                        pageIcon =
                                Canvas.imgHTML(Constants.SERVLET_THUMBNAIL_PREFIX
                                                       + "/"
                                                       + tileGrid.getSelectedRecord()
                                                               .getAttribute(Constants.ATTR_UUID),
                                               25,
                                               35);
                    }
                    dragHandler.setMoveTracker(name + (isPage ? pageIcon : modelIcon));
                } else {
                    tileGrid.setDragAppearance(DragAppearance.NONE);
                }
            }
        });

        tileGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

            @Override
            public void onRecordDoubleClick(RecordDoubleClickEvent event) {
                if (pages) {
                    getPopupPanel();

                    if (event.getRecord() != null) {
                        final ModalWindow mw = new ModalWindow(layout);
                        mw.setLoadingIcon("loadingAnimation.gif");
                        mw.show(true);
                        try {
                            final Image full =
                                    new Image(Constants.SERVLET_IMAGES_PREFIX + Constants.SERVLET_FULL_PREFIX
                                            + '/' + event.getRecord().getAttribute(Constants.ATTR_UUID));
                            full.setHeight(Constants.IMAGE_FULL_WIDTH + "px");
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
                            t.printStackTrace();
                            mw.hide();
                        }
                    }
                } else {

                    String uuidToEdit = tileGrid.getSelection()[0].getAttribute(Constants.ATTR_UUID);
                    getUiHandlers().openAnotherObject(uuidToEdit);
                }
            }
        });

        DetailViewerField pictureField = new DetailViewerField(Constants.ATTR_PICTURE);
        pictureField.setType("image");
        if (pages) {
            pictureField.setImageURLPrefix(Constants.SERVLET_THUMBNAIL_PREFIX + '/');
            pictureField.setImageWidth(Constants.IMAGE_THUMBNAIL_WIDTH);
            pictureField.setImageHeight(Constants.IMAGE_THUMBNAIL_HEIGHT);
        } else {
            pictureField.setImageWidth(95);
            pictureField.setImageHeight(95);
        }

        DetailViewerField nameField = new DetailViewerField(Constants.ATTR_NAME);
        nameField.setDetailFormatter(new DetailFormatter() {

            @Override
            public String format(Object value, Record record, DetailViewerField field) {
                StringBuffer sb = new StringBuffer();
                sb.append(lang.title()).append(": ").append(value);
                return sb.toString();
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
        if (text != null) {
            richTextEditor.setValue(text);
        }
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
    private void makeTuple(String uuid, EditorTabSet tabSet) {
        openedObjectsTabsets.put(uuid, tabSet);
        tabSet.setUuid(uuid);
    }

    /**
     * Removes the tuple.
     * 
     * @param tabSet
     *        the tab set
     */
    private void removeTuple(EditorTabSet tabSet) {
        String u = tabSet.getUuid();
        openedObjectsTabsets.remove(u);
    }

    private Menu getMenu(final EditorTabSet topTabSet,
                         final DigitalObjectModel model,
                         final DublinCore dc,
                         final ModsCollectionClient mods) {
        Menu menu = new Menu();
        menu.setShowShadow(true);
        menu.setShadowDepth(10);

        MenuItem newItem = new MenuItem(lang.newItem(), "icons/16/document_plain_new.png", "Ctrl+Alt+N");
        MenuItem lockItem = new MenuItem(lang.lockItem(), "icons/16/lock_lock_all.png", "Ctrl+Alt+Z");
        MenuItem unlockItem = new MenuItem(lang.unlockItem(), "icons/16/lock_unlock_all.png", "Ctrl+Alt+O");
        MenuItem saveItem = new MenuItem(lang.saveItem(), "icons/16/disk_blue.png", "Ctrl+Alt+S");
        MenuItem downloadItem = new MenuItem(lang.downloadItem(), "icons/16/download.png", "Ctrl+Alt+F");
        MenuItem removeItem = new MenuItem(lang.removeItem(), "icons/16/close.png", "Ctrl+Alt+D");
        MenuItem refreshItem = new MenuItem(lang.refreshItem(), "icons/16/refresh.png", "Ctrl+Alt+R");
        MenuItem publishItem = new MenuItem(lang.publishItem(), "icons/16/add.png", "Ctrl+Alt+P");
        MenuItem persistentUrlItem = new MenuItem(lang.persistentUrl(), "icons/16/url.png", "Ctrl+Alt+W");

        removeItem.setAttribute(ID_MENU_ITEM, ATTR_ITEM_INFLUENCED_BY_LOCK.REMOVE);
        removeItem.setAttribute(ID_UUID, topTabSet.getUuid());
        removeItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                removeDigitalObject(event.getItem().getAttributeAsString(ID_UUID));
            }
        });

        persistentUrlItem.setAttribute(ID_UUID, topTabSet.getUuid());
        persistentUrlItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                showPersistentUrl(event.getItem().getAttributeAsString(ID_UUID));
            }
        });

        saveItem.setAttribute(ID_TABSET, topTabSet);
        saveItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                storeWork((EditorTabSet) event.getItem().getAttributeAsObject(ID_TABSET));
            }
        });

        downloadItem.setAttribute(ID_TABSET, topTabSet);
        downloadItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                showDownloadingWindow((EditorTabSet) event.getItem().getAttributeAsObject(ID_TABSET));
            }
        });

        lockItem.setAttribute(ID_TABSET, topTabSet);
        lockItem.setAttribute(ID_MENU_ITEM, ATTR_ITEM_INFLUENCED_BY_LOCK.LOCK);
        lockItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                lockDigitalObject((EditorTabSet) event.getItem().getAttributeAsObject(ID_TABSET));
            }
        });

        unlockItem.setAttribute(ID_TABSET, topTabSet);
        unlockItem.setAttribute(ID_MENU_ITEM, ATTR_ITEM_INFLUENCED_BY_LOCK.UNLOCK);
        unlockItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(final MenuItemClickEvent event) {
                unlockDigitalObject(((EditorTabSet) event.getItem().getAttributeAsObject(ID_TABSET)),
                                    lang.reallyUnlock());
            }
        });

        refreshItem.setAttribute(ID_TABSET, topTabSet);
        refreshItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(final MenuItemClickEvent event) {
                refresh((EditorTabSet) event.getItem().getAttributeAsObject(ID_TABSET));
            }
        });

        publishItem.setAttribute(ID_TABSET, topTabSet);
        publishItem.setAttribute(ID_MENU_ITEM, ATTR_ITEM_INFLUENCED_BY_LOCK.PUBLISH);
        publishItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(final MenuItemClickEvent event) {
                tryToPublish((EditorTabSet) event.getItem().getAttributeAsObject(ID_TABSET));
            }
        });

        menu.setItems(newItem,
                      lockItem,
                      unlockItem,
                      saveItem,
                      refreshItem,
                      downloadItem,
                      removeItem,
                      publishItem,
                      persistentUrlItem);
        return menu;
    }

    private void removeDigitalObject(String uuid) {
        getUiHandlers().removeDigitalObject(uuid);
    }

    private void showPersistentUrl(String uuid) {
        universalWindow = new UniversalWindow(110, 800, lang.persistentUrl());

        TextItem editorUrlItem = new TextItem();

        StringBuffer sbEditor = new StringBuffer();
        sbEditor.append(Location.getProtocol()).append("//");
        sbEditor.append(Location.getHost());
        sbEditor.append(Location.getPath());
        sbEditor.append(Location.getQueryString());
        sbEditor.append('?');
        sbEditor.append(NameTokens.MODIFY);
        sbEditor.append('&');
        sbEditor.append(Constants.URL_PARAM_UUID);
        sbEditor.append('=');
        sbEditor.append(uuid);
        editorUrlItem.setDefaultValue(sbEditor.toString());
        editorUrlItem.setWidth(660);
        editorUrlItem.setTitle("<a href=\"" + sbEditor.toString() + "\" target=\"_blank\">Editor URL</a> ");

        TextItem krameriusUrlItem = new TextItem();

        StringBuffer sbKram = new StringBuffer();
        sbKram.append(config.getKrameriusHost());
        sbKram.append("/handle/");
        sbKram.append(uuid);
        krameriusUrlItem.setDefaultValue(sbKram.toString());
        krameriusUrlItem.setWidth(660);
        krameriusUrlItem.setTitle("<a href=\"" + sbKram.toString()
                + "\" target=\"_blank\">Kramerius URL</a> ");

        DynamicForm editorUrlForm = new DynamicForm();
        editorUrlForm.setItems(editorUrlItem);
        editorUrlForm.setExtraSpace(5);
        DynamicForm kramUrlForm = new DynamicForm();
        kramUrlForm.setItems(krameriusUrlItem);

        universalWindow.addItem(editorUrlForm);
        universalWindow.addItem(kramUrlForm);
        universalWindow.centerInPage();
        universalWindow.show();
        universalWindow.focus();
    }

    @Override
    public void storeWork(EditorTabSet ts) {
        DigitalObjectDetail detail = createDigitalObjectDetail(ts);

        getUiHandlers().storeFoxmlFile(detail, ts);
    }

    private void showDownloadingWindow(final EditorTabSet ts) {
        if (downloadingWindow != null && downloadingWindow.isCreated()) {
            downloadingWindow.hide();
            downloadingWindow = null;
        }
        downloadingWindow = new DownloadFoxmlWindow(lang, ts);
        DigitalObjectDetail detail = createDigitalObjectDetail(ts);
        getUiHandlers().onHandleWorkingCopyDigObj(detail);

    }

    @Override
    public DownloadFoxmlWindow getDownloadingWindow() {
        return downloadingWindow;
    }

    private void unlockDigitalObject(final EditorTabSet ts, String message) {
        SC.ask(lang.unlockObjectWindow() + ": " + ts.getUuid(), message, new BooleanCallback() {

            @Override
            public void execute(Boolean value) {
                if (value == true) {
                    getUiHandlers().unlockDigitalObject(ts);
                }
            }
        });
    }

    private void lockDigitalObject(final EditorTabSet ts) {
        getUiHandlers().lockDigitalObject(ts);
    }

    private void tryToPublish(EditorTabSet ts) {
        getUiHandlers().getLockDigitalObjectInformation(ts, true);
    }

    /**
     * Method for publish focused tabSet
     * 
     * @param ts
     *        the focused EditorTabSet
     */
    @Override
    public void publish(final EditorTabSet ts) {

        universalWindow = new UniversalWindow(160, 350, lang.publishName());

        HTMLFlow label = new HTMLFlow(HtmlCode.title(lang.areYouSure(), 3));
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

                getUiHandlers().onSaveDigitalObject(createDigitalObjectDetail(ts),
                                                    versionable.getValueAsBoolean());
                universalWindow.hide();
                universalWindow = null;
            }
        });
        Button cancel = new Button();
        cancel.setTitle(lang.cancel());
        cancel.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event2) {
                universalWindow.hide();
                universalWindow = null;
            }
        });
        HLayout hLayout = new HLayout();
        hLayout.setMembersMargin(10);
        hLayout.addMember(publish);
        hLayout.addMember(cancel);
        hLayout.setMargin(5);
        form.setFields(versionable);
        universalWindow.addItem(label);
        universalWindow.addItem(form);
        universalWindow.addItem(hLayout);

        universalWindow.centerInPage();
        universalWindow.show();
        publish.focus();
    }

    private DigitalObjectDetail createDigitalObjectDetail(final EditorTabSet ts) {
        final DigitalObjectModel model = ts.getInfoTab().getModel();

        DublinCore changedDC = null;
        ModsCollectionClient changedMods = null;
        DigitalObjectDetail object = new DigitalObjectDetail(model, null);

        if (modsWindow != null) {

            changedMods = modsWindow.publishWindow();
            object.setModsChanged(true);
            changedDC = ts.getDc();
            object.setDcChanged(false);

            if (modsWindow.getReflectInDC()) {
                changedDC = modsWindow.reflectInDC(changedDC);
                object.setDcChanged(true);
            }

            if (modsWindow != null && downloadingWindow == null) {
                modsWindow.hide();
                modsWindow = null;
            }

        } else {
            Tab dcT = ts.getDcTab();
            Tab modsT = ts.getModsTab();
            if (dcT.getAttributeAsBoolean(TAB_INITIALIZED)) {
                DCTab dcT_ = (DCTab) dcT;
                changedDC = dcT_.getDc();
                object.setDcChanged(true);
            } else {
                changedDC = ts.getDcTab().getDc();
                object.setDcChanged(false);
            }

            if (modsT.getAttributeAsBoolean(TAB_INITIALIZED)) {
                ModsTab modsT_ = (ModsTab) modsT;
                changedMods = new ModsCollectionClient();
                changedMods.setMods(Arrays.asList(modsT_.getMods()));
                object.setModsChanged(true);
            } else {
                changedMods = ts.getModsCollection();
                object.setModsChanged(false);
            }

        }

        object.setDc(changedDC);
        object.setMods(changedMods);
        //                TabSet ts = (TabSet) event.getItem().getAttributeAsObject(ID_TABSET);
        InfoTab infoT = ts.getInfoTab();

        if (infoT.getLabelItem() == null && infoT.getOriginalLabel() != null) {
            object.setLabel("");
            object.setLabelChanged(true);
        } else {
            object.setLabelChanged(!infoT.getLabelItem().equals(infoT.getOriginalLabel()));
            object.setLabel(infoT.getLabelItem());
        }

        if (ts.getOcrContent() != null && (ts.getOriginalOcrContent() != null)
                || "".equals(ts.getOriginalOcrContent())) {
            String val = (String) ts.getOcrContent().getValue();
            if ("".equals(ts.getOriginalOcrContent())) {
                object.setThereWasAnyOcr(false);
            } else if (ts.getOriginalOcrContent().length() > 0) {
                object.setThereWasAnyOcr(true);
            }
            if (!ts.getOriginalOcrContent().equals(val)) {
                object.setOcr(val);
                object.setOcrChanged(true);
            }
        } else {
            object.setOcrChanged(false);
        }
        object.setUuid(ts.getUuid());

        Map<DigitalObjectModel, TileGrid> tilegrids = ts.getItemGrid();
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
        return object;
    }

    /**
     * Method for refresh focused tabSet
     * 
     * @param focusedTabSet
     */
    private void refresh(final EditorTabSet focusedTabSet) {
        EditorTabSet ts = focusedTabSet;
        String uuid = ts.getUuid();
        getUiHandlers().onRefresh(uuid);
    }

    @Override
    public void addStream(Record[] items, String uuid, DigitalObjectModel model) {
        EditorTabSet topTabSet = openedObjectsTabsets.get(uuid);
        List<Tab> containers = topTabSet.getItemTab();
        Tab toAdd = null;
        for (Tab tab : containers) {
            if (tab.getAttribute(ID_MODEL).equals(model.getValue())) {
                toAdd = tab;
                break;
            }
        }
        if (toAdd == null) throw new RuntimeException("There is no tab with model " + model);

        final TileGrid grid = getTileGrid(model.equals(DigitalObjectModel.PAGE), model.getValue());
        topTabSet.setTileGrid(grid);
        topTabSet.setTabPane(toAdd.getID(), grid);
        if (items != null) {
            grid.setData(items);
        } else {
            grid.setData(new ContainerRecord[] {});
        }
        toAdd.setPane(grid);
        toAdd.setAttribute(TAB_INITIALIZED, true);
        if (topTabSet.getItemGrid() == null) {
            topTabSet.setItemGrid(new HashMap<DigitalObjectModel, TileGrid>());
        }
        topTabSet.getItemGrid().put(model, grid);
    }

    private void deleteSelectedData(final EditorTabSet focusedTabSet) {
        String modelString = focusedTabSet.getSelectedTab().getAttributeAsString(ID_MODEL);
        if (modelString != null && !"".equals(modelString)) {
            final TileGrid focusedTileGrid =
                    focusedTabSet.getItemGrid().get(DigitalObjectModel.parseString(modelString));
            if (focusedTileGrid != null) {
                if (focusedTileGrid.getSelection().length > 0) {
                    SC.confirm(lang.askDelete(), new BooleanCallback() {

                        @Override
                        public void execute(Boolean value) {
                            if (value == true) {
                                focusedTileGrid.removeSelectedData();
                            }
                        }
                    });
                }
            }
        }
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
                EditorTabSet focusedTabSet = null;
                if (!isSecondFocused || topTabSet2 == null) {
                    focusedTabSet = topTabSet1;
                } else if (isSecondFocused && topTabSet2 != null) {
                    focusedTabSet = topTabSet2;
                }

                if (code == Constants.CODE_KEY_DELETE) {
                    deleteSelectedData(focusedTabSet);
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_PAGE_DOWN.getCode()) {
                    shiftRight(focusedTabSet);
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_PAGE_UP.getCode()) {
                    shiftLeft(focusedTabSet);
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_R.getCode()) {
                    refresh(focusedTabSet);
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_P.getCode()) {
                    publishShortCut(focusedTabSet);
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_C.getCode()) {
                    getUiHandlers().close(focusedTabSet.getUuid());
                    close(focusedTabSet);
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_B.getCode()) {
                    showBasicModsWindow(focusedTabSet);
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_O.getCode()) {
                    String lockOwner = focusedTabSet.getLockInfo().getLockOwner();
                    if (lockOwner != null && "".equals(lockOwner)) {
                        unlockDigitalObject(focusedTabSet, lang.reallyUnlock());
                    }
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_Z.getCode()) {
                    String lockOwner = focusedTabSet.getLockInfo().getLockOwner();
                    if (lockOwner == null || "".equals(lockOwner)) {
                        lockDigitalObject(focusedTabSet);
                    }
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_F.getCode()) {
                    showDownloadingWindow(focusedTabSet);
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_S.getCode()) {
                    storeWork(focusedTabSet);
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_W.getCode()) {
                    showPersistentUrl(focusedTabSet.getUuid());
                } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_D.getCode()) {
                    removeDigitalObject(focusedTabSet.getUuid());
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setConfiguration(EditorClientConfiguration config) {
        this.config = config;
    }

}