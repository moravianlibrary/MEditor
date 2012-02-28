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
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.events.DragMoveEvent;
import com.smartgwt.client.widgets.events.DragMoveHandler;
import com.smartgwt.client.widgets.events.DragStartEvent;
import com.smartgwt.client.widgets.events.DragStartHandler;
import com.smartgwt.client.widgets.events.DragStopEvent;
import com.smartgwt.client.widgets.events.DragStopHandler;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
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
import com.smartgwt.client.widgets.tile.TileRecord;
import com.smartgwt.client.widgets.tile.events.RecordClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordClickHandler;
import com.smartgwt.client.widgets.tile.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.tile.events.SelectionChangedEvent;
import com.smartgwt.client.widgets.tile.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;
import com.smartgwt.client.widgets.viewer.DetailFormatter;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.client.mods.ModsTypeClient;
import cz.fi.muni.xkremser.editor.client.presenter.CreateStructurePresenter.MyView;
import cz.fi.muni.xkremser.editor.client.util.ClientUtils;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.CreateStructureView.MyUiHandlers;
import cz.fi.muni.xkremser.editor.client.view.other.DCTab;
import cz.fi.muni.xkremser.editor.client.view.other.EditorDragMoveHandler;
import cz.fi.muni.xkremser.editor.client.view.other.EditorTabSet;
import cz.fi.muni.xkremser.editor.client.view.other.HtmlCode;
import cz.fi.muni.xkremser.editor.client.view.other.ModsTab;
import cz.fi.muni.xkremser.editor.client.view.other.PageNumberingManager;
import cz.fi.muni.xkremser.editor.client.view.other.ScanRecord;
import cz.fi.muni.xkremser.editor.client.view.window.ModalWindow;
import cz.fi.muni.xkremser.editor.client.view.window.UniversalWindow;

import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;

/**
 * @author Jiri Kremser
 */
public class CreateStructureView
        extends ViewWithUiHandlers<MyUiHandlers>
        implements MyView {

    private final LangConstants lang;

    /**
     * The Interface MyUiHandlers.
     */
    public interface MyUiHandlers
            extends UiHandlers {

        void onAddImages(final TileGrid tileGrid, final Menu menu);

        void createObjects(DublinCore dc, ModsTypeClient mods, boolean visible);

        ModsCollectionClient getMods();

        DublinCore getDc();

        void chooseDetail(String pathToImg,
                          int detailHeight,
                          String uuid,
                          boolean isTop,
                          int topSpace,
                          ModalWindow mw);

    }

    /** The Constant ID_MODEL. */
    public static final String ID_MODEL = "model";

    /** The Constant ID_NAME. */
    public static final String ID_NAME = "name";

    /** The Constant ID_SEPARATOR. */
    public static final String ID_SEPARATOR = "separator";

    /** The Constant ID_SEL_ALL. */
    public static final String ID_VIEW = "view";

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

    /** The clipboard. */
    private Record[] clipboard;

    /** The layout. */
    private final VLayout layout;

    private VLayout tileGridLayout;

    private VLayout detailPanel;

    private VLayout metadataPanel;

    private boolean detailPanelShown = false;

    private boolean metadataPanelShown = false;

    private boolean detailPanelImageShown = false;

    /** The image popup. */
    private PopupPanel imagePopup;

    private TileGrid tileGrid;

    private Window winModal;

    private SelectItem pageTypeItem;

    private EditorTabSet topTabSet;

    private final int pageDetailHeight = Constants.PAGE_PREVIEW_HEIGHT_NORMAL;

    private int imageThumbnailHeight = Constants.IMAGE_THUMBNAIL_HEIGHT;

    private int imageThumbnailWidth = Constants.IMAGE_THUMBNAIL_WIDTH;

    private UniversalWindow universalWindow = null;

    private PageNumberingManager numbering;

    private ToolStrip toolStrip;

    private List<Record[]> undoList;
    private ToolStripButton undoButton;
    private List<Record[]> redoList;
    private ToolStripButton redoButton;
    int positionBeforeMoving;
    private final EventBus eventBus;
    private String model;
    private String hostname;
    private int topSpaceTop = Integer.MIN_VALUE;
    private int topSpaceBottom = Integer.MAX_VALUE;
    private int detailHeightTop = Constants.PAGE_PREVIEW_HEIGHT_NORMAL;
    private int detailHeightBottom = Constants.PAGE_PREVIEW_HEIGHT_NORMAL;
    private boolean topIsWraped = false;
    private boolean bottomIsWraped = false;

    /**
     * Instantiates a new create view.
     */
    @Inject
    public CreateStructureView(LangConstants lang, EventBus eventBus) {
        this.lang = lang;
        this.eventBus = eventBus;
        layout = new VLayout();
        layout.setOverflow(Overflow.AUTO);
        layout.setLeaveScrollbarGap(true);
        imagePopup = new PopupPanel(true);
        imagePopup.setGlassEnabled(true);
        imagePopup.setAnimationEnabled(true);
    }

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.client.presenter.CreatePresenter.MyView#
     * fromClipboard()
     */
    @Override
    public Record[] fromClipboard() {
        return this.clipboard;
    }

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.client.presenter.CreatePresenter.MyView#
     * toClipboard (com.smartgwt.client.data.Record[])
     */
    @Override
    public void toClipboard(Record[] data) {
        this.clipboard = data;
    }

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.client.presenter.CreatePresenter.MyView#
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
     * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
     * 
     * @return the widget
     */
    @Override
    public Widget asWidget() {
        return layout;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void onAddImages(String model, Record[] items, final String hostname, boolean resize) {
        if (layout.getMembers().length != 0) {
            layout.removeMember(tileGridLayout);
        }
        this.model = model;
        this.hostname = hostname;
        tileGridLayout = new VLayout();
        tileGrid = new TileGrid();
        tileGrid.setTileWidth(imageThumbnailWidth + 10);
        tileGrid.setTileHeight(imageThumbnailHeight + 35);
        tileGrid.setHeight100();
        tileGrid.setWidth100();
        tileGrid.setCanDrag(true);
        tileGrid.setCanAcceptDrop(true);
        tileGrid.setShowAllRecords(true);
        tileGrid.setShowResizeBar(true);
        positionBeforeMoving = -1;
        detailPanelShown = false;
        metadataPanelShown = false;
        detailPanelImageShown = false;
        detailPanel = null;
        metadataPanel = null;
        topSpaceTop = Integer.MIN_VALUE;
        topSpaceBottom = Integer.MAX_VALUE;
        detailHeightTop = Constants.PAGE_PREVIEW_HEIGHT_NORMAL;
        detailHeightBottom = Constants.PAGE_PREVIEW_HEIGHT_NORMAL;
        topIsWraped = false;
        bottomIsWraped = false;
        final EditorDragMoveHandler dragHandler = new EditorDragMoveHandler(tileGrid);
        tileGrid.addDragMoveHandler(dragHandler);

        tileGrid.addDragStartHandler(new DragStartHandler() {

            @Override
            public void onDragStart(DragStartEvent event) {
                Record[] selection = tileGrid.getSelection();
                if (selection != null && selection.length > 0) {
                    positionBeforeMoving = tileGrid.getRecordIndex(selection[0]);
                    addUndoRedo(tileGrid.getData(), true, false);
                }
            }
        });

        tileGrid.addDragMoveHandler(new DragMoveHandler() {

            @Override
            public void onDragMove(DragMoveEvent event) {
                if (tileGrid.getSelectedRecord() != null) {
                    tileGrid.setDragAppearance(DragAppearance.TRACKER);
                    String pageIcon =
                            Canvas.imgHTML(getImageURLPrefix()
                                                   + tileGrid.getSelectedRecord()
                                                           .getAttributeAsString(Constants.ATTR_PICTURE),
                                           25,
                                           35);
                    dragHandler.setMoveTracker(pageIcon);
                } else {
                    tileGrid.setDragAppearance(DragAppearance.NONE);
                }
            }
        });

        tileGrid.addDragStopHandler(new DragStopHandler() {

            @Override
            public void onDragStop(DragStopEvent event) {
                if (positionBeforeMoving >= 0) {
                    Record[] selection = tileGrid.getSelection();
                    if (selection.length > 0 && tileGrid.getRecordIndex(selection[0]) == positionBeforeMoving) {
                        undoList.remove(undoList.size() - 1);
                        if (undoList.size() == 0) undoButton.disable();
                    }
                    positionBeforeMoving = -1;
                }
            }
        });

        numbering = new PageNumberingManager(tileGrid);
        Menu menu = new Menu();
        menu.setShowShadow(true);
        menu.setShadowDepth(10);

        MenuItem editItem = new MenuItem(lang.editPageName(), "icons/16/edit.png");
        editItem.setEnableIfCondition(isSelected(true));
        editItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                editPageTitle();
            }
        });

        MenuItem viewItem = new MenuItem(lang.menuView(), "icons/16/eye.png");
        viewItem.setAttribute(ID_NAME, ID_VIEW);
        viewItem.setEnableIfCondition(isSelected(true));
        viewItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                final String uuid = tileGrid.getSelection()[0].getAttribute(Constants.ATTR_PICTURE);
                winModal = new Window();
                //                winModal.setWidth(1024);
                //                winModal.setHeight(768);
                winModal.setWidth("95%");
                winModal.setHeight("95%");
                StringBuffer sb = new StringBuffer();
                sb.append(lang.scan()).append(": ")
                        .append(tileGrid.getSelection()[0].getAttribute(Constants.ATTR_NAME));
                winModal.setTitle(sb.toString());
                winModal.setShowMinimizeButton(false);
                winModal.setIsModal(true);
                winModal.setShowModalMask(true);
                winModal.centerInPage();
                winModal.addCloseClickHandler(new CloseClickHandler() {

                    @Override
                    public void onCloseClick(CloseClientEvent event) {
                        escShortCut();
                    }
                });
                HTMLPane viewerPane = new HTMLPane();
                viewerPane.setPadding(15);
                viewerPane.setContentsURL(hostname + "/meditor/viewer/viewer.html");
                java.util.Map<String, String> params = new java.util.HashMap<String, String>();
                params.put("rft_id", uuid);
                viewerPane.setContentsURLParams(params);
                viewerPane.setContentsType(ContentsType.PAGE);
                winModal.addItem(viewerPane);
                winModal.show();
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
                return CreateStructureView.this.clipboard != null
                        && CreateStructureView.this.clipboard.length > 0;
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
                      viewItem,
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
        tileGrid.setCanAcceptDrop(true);
        tileGrid.setCanAcceptDroppedRecords(true);
        tileGrid.setDragType(model);
        tileGrid.setDragAppearance(DragAppearance.TRACKER);
        tileGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

            @Override
            public void onRecordDoubleClick(final RecordDoubleClickEvent event) {
                if (event.getRecord() != null) {
                    try {
                        final ModalWindow mw = new ModalWindow(layout);
                        mw.setLoadingIcon("loadingAnimation.gif");
                        mw.show(true);
                        StringBuffer sb = new StringBuffer();
                        sb.append(Constants.SERVLET_IMAGES_PREFIX).append(Constants.SERVLET_SCANS_PREFIX)
                                .append('/').append(event.getRecord().getAttribute(Constants.ATTR_PICTURE))
                                .append("?" + Constants.URL_PARAM_FULL + "=yes");
                        final Image full = new Image(sb.toString());
                        full.setHeight(Constants.IMAGE_FULL_HEIGHT + "px");
                        full.addLoadHandler(new LoadHandler() {

                            @Override
                            public void onLoad(LoadEvent event) {
                                mw.hide();
                                getPopupPanel().setVisible(true);
                                getPopupPanel().center();
                            }
                        });
                        getPopupPanel().setWidget(full);
                        getPopupPanel().addCloseHandler(new CloseHandler<PopupPanel>() {

                            @Override
                            public void onClose(CloseEvent<PopupPanel> event) {
                                mw.hide();
                                getPopupPanel().setWidget(null);
                            }
                        });
                        getPopupPanel().center();
                        getPopupPanel().setVisible(false);

                    } catch (Throwable t) {

                        // TODO: handle
                    }
                }
            }
        });

        tileGrid.addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                if (detailPanelShown) {
                    showDetail(event.getRecord().getAttribute(Constants.ATTR_PICTURE));
                }
            }
        });
        tileGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionChangedEvent event) {
                if (tileGrid.getSelection() != null) {
                    pageTypeItem.enable();
                    if (tileGrid.getSelection().length == 1) {
                        String pageType = tileGrid.getSelection()[0].getAttribute(Constants.ATTR_PAGE_TYPE);
                        pageTypeItem.setValue(Constants.PAGE_TYPES.MAP.get(pageType));
                    } else {
                        pageTypeItem.setValue("");
                    }
                } else {
                    pageTypeItem.disable();
                }
            }
        });

        final DetailViewerField pictureField = new DetailViewerField(Constants.ATTR_PICTURE);
        pictureField.setType("image");
        pictureField.setImageURLPrefix(getImageURLPrefix());
        pictureField.setImageWidth(imageThumbnailWidth);
        pictureField.setImageHeight(imageThumbnailHeight);
        pictureField.setCellStyle("tileGridImg");

        DetailViewerField nameField = new DetailViewerField(Constants.ATTR_NAME);
        nameField.setCellStyle("tileGridTitle");
        nameField.setDetailFormatter(new DetailFormatter() {

            @Override
            public String format(Object value, Record record, DetailViewerField field) {
                StringBuffer sb = new StringBuffer();
                sb.append(lang.scan()).append(": ").append(value);
                String pageType = record.getAttribute(Constants.ATTR_PAGE_TYPE);
                if (!Constants.PAGE_TYPES.NP.toString().equals(pageType)
                        && !Constants.PAGE_TYPES.BL.toString().equals(pageType)) {
                    sb.append("<br/>").append("<div class='pageType'>").append(pageType).append("</div>");
                }
                return sb.toString();
            }
        });

        tileGrid.setFields(pictureField, nameField);
        tileGrid.setData(items);
        getUiHandlers().onAddImages(tileGrid, menu);

        if (!resize) toolStrip = getToolStrip();

        tileGridLayout.addMember(toolStrip);
        tileGridLayout.addMember(tileGrid);
        layout.addMember(tileGridLayout);
    }

    private String getImageURLPrefix() {
        return Constants.SERVLET_SCANS_PREFIX + '/' + "?" + Constants.URL_PARAM_HEIGHT + "="
                + imageThumbnailHeight + "&" + Constants.URL_PARAM_UUID + "=";
    }

    /**
     * @return
     */

    private ToolStrip getToolStrip() {
        ToolStrip toolStrip = new ToolStrip();
        toolStrip.setWidth100();
        ToolStripMenuButton menuButton = getToolStripMenuButton();
        toolStrip.addMenuButton(menuButton);

        imageThumbnailHeight = Constants.IMAGE_THUMBNAIL_HEIGHT;
        imageThumbnailWidth = Constants.IMAGE_THUMBNAIL_WIDTH;

        pageTypeItem = new SelectItem();
        pageTypeItem.setShowTitle(false);
        pageTypeItem.setWidth(100);
        pageTypeItem.setTitle(lang.specialType());

        LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>(Constants.PAGE_TYPES.MAP);
        pageTypeItem.setValueMap(valueMap);
        pageTypeItem.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(final ChangedEvent event) {
                addUndoRedo(tileGrid.getData(), true, false);
                Record[] selectedRecords = tileGrid.getSelection();
                for (Record rec : selectedRecords) {
                    rec.setAttribute(Constants.ATTR_PAGE_TYPE, event.getValue());
                }
                tileGrid.deselectRecords(selectedRecords);
                tileGrid.selectRecords(selectedRecords);
                updateSelectedTileGrid();
            }

        });

        toolStrip.addFormItem(pageTypeItem);
        toolStrip.addResizer();

        //        SelectItem zoomItems = new SelectItem("pageDetail", lang.detailSize());
        //        zoomItems.setName("selectName");
        //        zoomItems.setShowTitle(true);
        //        zoomItems.setWrapTitle(false);
        //        zoomItems.setWidth(70);
        //        zoomItems.setValueMap("S", "M", "L", "XL", "XXL");
        //        zoomItems.addChangedHandler(new ChangedHandler() {
        //
        //            @Override
        //            public void onChanged(ChangedEvent event) {
        //                String percent = (String) event.getValue();
        //                if ("S".equals(percent)) {
        //                    pageDetailHeight = Constants.PAGE_PREVIEW_HEIGHT_SMALL;
        //                } else if ("M".equals(percent)) {
        //                    pageDetailHeight = Constants.PAGE_PREVIEW_HEIGHT_NORMAL;
        //                } else if ("L".equals(percent)) {
        //                    pageDetailHeight = Constants.PAGE_PREVIEW_HEIGHT_LARGE;
        //                } else if ("XL".equals(percent)) {
        //                    pageDetailHeight = Constants.PAGE_PREVIEW_HEIGHT_XLARGE;
        //                } else if ("XXL".equals(percent)) {
        //                    pageDetailHeight = Constants.PAGE_PREVIEW_HEIGHT_MAX;
        //                }
        //                showDetail(pageDetailHeight, true, Integer.MIN_VALUE);
        //            }
        //        });
        //        zoomItems.setDefaultValue("M");
        //        toolStrip.addFormItem(zoomItems);
        final ToolStripButton zoomButton = new ToolStripButton();
        final ToolStripButton editMetadataButton = new ToolStripButton();
        zoomButton.setIcon("silk/zoom.png");
        zoomButton.setTitle(lang.pageDetail());
        zoomButton.setActionType(SelectionType.CHECKBOX);
        zoomButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (metadataPanelShown) {
                    metadataPanelShown = false;
                    tileGridLayout.removeMember(metadataPanel);
                    editMetadataButton.setSelected(false);
                }
                if (detailPanel == null) {
                    detailPanel = new VLayout();
                    detailPanel.setPadding(5);
                    detailPanel.setAnimateMembers(true);
                    detailPanel.setAnimateHideTime(200);
                    detailPanel.setAnimateShowTime(200);
                    tileGridLayout.addMember(detailPanel);
                    detailPanelShown = true;
                    showDetail(pageDetailHeight, true, Integer.MIN_VALUE);
                } else {
                    detailPanelShown = false;
                    tileGridLayout.removeMember(detailPanel);
                    detailPanel = null;
                }
            }
        });
        toolStrip.addButton(zoomButton);

        ToolStripButton zoomIn = new ToolStripButton();
        zoomIn.setIcon("icons/16/zoomIn.png");
        zoomIn.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                resizeThumbnails(true);
            }
        });
        toolStrip.addButton(zoomIn);

        ToolStripButton zoomOut = new ToolStripButton();
        zoomOut.setIcon("icons/16/zoomOut.png");
        zoomOut.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                resizeThumbnails(false);
            }
        });
        toolStrip.addButton(zoomOut);
        toolStrip.addSeparator();

        editMetadataButton.setIcon("silk/metadata_edit.png");
        editMetadataButton.setTitle(lang.editMeta());
        editMetadataButton.setActionType(SelectionType.CHECKBOX);
        editMetadataButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (detailPanelShown) {
                    detailPanelShown = false;
                    tileGridLayout.removeMember(detailPanel);
                    detailPanel = null;
                    zoomButton.setSelected(false);
                }
                if (metadataPanel == null) {
                    metadataPanel = new VLayout();
                    metadataPanel.setPadding(5);
                    metadataPanel.setAnimateMembers(true);
                    metadataPanel.setAnimateHideTime(200);
                    metadataPanel.setAnimateShowTime(200);
                    metadataPanel.setHeight100();
                    tileGridLayout.addMember(metadataPanel);
                    metadataPanelShown = true;
                    initModsOrDc();
                } else if (metadataPanelShown) {
                    metadataPanelShown = false;
                    tileGridLayout.removeMember(metadataPanel);
                } else {
                    tileGridLayout.addMember(metadataPanel);
                    metadataPanelShown = true;
                }

            }
        });
        toolStrip.addButton(editMetadataButton);

        undoButton = new ToolStripButton();
        redoButton = new ToolStripButton();
        undoList = new ArrayList<Record[]>();
        redoList = new ArrayList<Record[]>();

        undoButton.setIcon("icons/16/undo.png");
        undoButton.setTitle(lang.undo());
        undoButton.disable();

        redoButton.setIcon("icons/16/redo.png");
        redoButton.setTitle(lang.redo());
        redoButton.disable();

        undoButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (undoList.size() > 0) {
                    addUndoRedo(tileGrid.getData(), false, false);
                    tileGrid.setData(undoList.remove(undoList.size() - 1));
                    if (undoList.size() == 0) undoButton.disable();
                    updateTileGrid();
                } else {
                    undoButton.disable();
                }
            }
        });
        toolStrip.addButton(undoButton);

        redoButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (redoList.size() > 0) {
                    addUndoRedo(tileGrid.getData(), true, true);
                    tileGrid.setData(redoList.remove(redoList.size() - 1));
                    if (redoList.size() == 0) redoButton.disable();
                    updateTileGrid();
                } else {
                    redoButton.disable();
                }
            }
        });
        toolStrip.addButton(redoButton);

        toolStrip.addSeparator();

        ToolStripButton createButton = new ToolStripButton();
        createButton.setIcon("silk/forward_green.png");
        createButton.setTitle(lang.create());
        createButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                create();
            }
        });
        toolStrip.addButton(createButton);

        pageTypeItem.disable();
        return toolStrip;
    }

    private void create() {

        universalWindow = new UniversalWindow(160, 350, lang.publishName(), eventBus, 20);

        HTMLFlow label = new HTMLFlow(HtmlCode.title(lang.areYouSure(), 3));
        label.setMargin(5);
        label.setExtraSpace(10);
        final DynamicForm form = new DynamicForm();
        form.setMargin(0);
        form.setWidth(100);
        form.setHeight(20);
        form.setExtraSpace(7);

        final CheckboxItem makePublic = new CheckboxItem("makePublic", lang.makePublic());
        Button publish = new Button();
        publish.setTitle(lang.ok());
        publish.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event2) {

                DublinCore dc = null;
                ModsTypeClient mods = null;
                if (topTabSet != null) {
                    dc = topTabSet.getDcTab().getDc();
                    if (topTabSet.getModsTab() != null) {
                        mods = ((ModsTab) topTabSet.getModsTab()).getMods();
                    }
                }
                getUiHandlers().createObjects(dc, mods, makePublic.getValueAsBoolean());
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
        form.setFields(makePublic);
        universalWindow.addItem(label);
        universalWindow.addItem(form);
        universalWindow.addItem(hLayout);

        universalWindow.centerInPage();
        universalWindow.show();
        publish.focus();
    }

    private ToolStripMenuButton getToolStripMenuButton() {
        Menu menu = new Menu();
        menu.setShowShadow(true);
        menu.setShadowDepth(3);

        MenuItem editTitle = new MenuItem(lang.editPageName(), "icons/16/edit.png");
        MenuItem renumberAll = new MenuItem(lang.renumberAll(), "icons/16/renumberAll.png");
        MenuItem renumber = new MenuItem(lang.renumber() + "...", "icons/16/renumber.png");
        MenuItem toRoman = new MenuItem(lang.convertToRoman(), "icons/16/roman.png");
        MenuItem toRomanOld = new MenuItem(lang.convertToRomanOld(), "icons/16/roman.png");
        MenuItem surround = new MenuItem("1, 2, ...   ⇨   [1], [2], ...", "icons/16/surround.png");
        MenuItem abc = new MenuItem("1, 2, ...   ⇨   1a, 1b, ...", "icons/16/abc.png");
        MenuItem toLeft = new MenuItem(lang.leftShift() + "...", "icons/16/arrow_left.png");
        MenuItem toRight = new MenuItem(lang.rightShift() + "...", "icons/16/arrow_right.png");
        MenuItem shiftTo = new MenuItem(lang.shiftTo() + "...", "icons/16/arrow_in.png");
        editTitle.setEnableIfCondition(isSelected(true));
        renumber.setEnableIfCondition(isSelected(false));
        toRoman.setEnableIfCondition(isSelected(false));
        toRomanOld.setEnableIfCondition(isSelected(false));
        surround.setEnableIfCondition(isSelected(false));
        abc.setEnableIfCondition(isSelected(false));
        toLeft.setEnableIfCondition(isSelected(false));
        toRight.setEnableIfCondition(isSelected(false));
        shiftTo.setEnableIfCondition(isSelected(false));

        editTitle.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                editPageTitle();
            }
        });

        renumberAll.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {

                SC.ask(lang.sureRenAll(), new BooleanCallback() {

                    @Override
                    public void execute(Boolean value) {
                        if (value != null && value) {
                            askForValue();
                        }
                    }

                    private void askForValue() {
                        SC.askforValue(lang.renumber(),
                                       lang.enterNumberForRenumber(),
                                       "1",
                                       new ValueCallback() {

                                           @Override
                                           public void execute(String value) {
                                               if (value == null) {
                                                   return;
                                               }
                                               try {
                                                   int n = Integer.parseInt(value);
                                                   addUndoRedo(tileGrid.getData(), true, false);
                                                   Record[] data = tileGrid.getData();
                                                   int i = 0;
                                                   if (data != null && data.length > 0) {
                                                       for (Record rec : data) {
                                                           rec.setAttribute(Constants.ATTR_NAME, n + i++);
                                                       }
                                                   }
                                                   updateTileGrid();
                                               } catch (NumberFormatException nfe) {
                                                   SC.say(lang.notANumber());
                                               }
                                           }
                                       },
                                       new com.smartgwt.client.widgets.Dialog() {

                                           {
                                               setWidth(300);
                                           }
                                       });
                    }
                });
            }
        });
        renumber.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {

                SC.askforValue(lang.renumber(), lang.enterNumberForRenumber(), new ValueCallback() {

                    @Override
                    public void execute(String value) {
                        if (value == null) {
                            return;
                        }
                        try {
                            int n = Integer.parseInt(value);
                            addUndoRedo(tileGrid.getData(), true, false);
                            Record[] data = tileGrid.getSelection();
                            if (data != null && data.length > 0) {
                                for (int i = 0; i < data.length; i++) {
                                    Record rec = data[i];
                                    rec.setAttribute(Constants.ATTR_NAME, n + i);
                                }
                            }
                            updateSelectedTileGrid();
                        } catch (NumberFormatException nfe) {
                            SC.say(lang.notANumber());
                        }
                    }
                });

            }
        });

        toRoman.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                romanRenumber(false);
            }
        });

        toRomanOld.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                romanRenumber(true);
            }
        });

        surround.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                Record[] data = tileGrid.getSelection();
                if (data != null && data.length > 0) {
                    addUndoRedo(tileGrid.getData(), true, false);
                    for (Record rec : data) {
                        if (!(rec.getAttributeAsString(Constants.ATTR_NAME).contains("[") && rec
                                .getAttributeAsString(Constants.ATTR_NAME).contains("]")))
                            rec.setAttribute(Constants.ATTR_NAME, "[" + rec.getAttribute(Constants.ATTR_NAME)
                                    + "]");
                    }
                }
                updateSelectedTileGrid();
            }
        });

        abc.setSubmenu(getAbcSubmenu());

        toLeft.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                SC.askforValue(lang.shiftLeft(), lang.enterNumber(), new ValueCallback() {

                    @Override
                    public void execute(String value) {
                        if (value == null) {
                            return;
                        }
                        try {
                            int n = Integer.parseInt(value);
                            addUndoRedo(tileGrid.getData(), true, false);
                            numbering.shift(-n, false, layout);
                            updateTileGrid();
                        } catch (NumberFormatException nfe) {
                            SC.say(lang.notANumber());
                        }
                    }
                });
            }
        });

        toRight.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                SC.askforValue(lang.shiftRight(), lang.enterNumber(), new ValueCallback() {

                    @Override
                    public void execute(String value) {
                        if (value == null) {
                            return;
                        }
                        try {
                            int n = Integer.parseInt(value);
                            addUndoRedo(tileGrid.getData(), true, false);
                            numbering.shift(n, false, layout);
                        } catch (NumberFormatException nfe) {
                            SC.say(lang.notANumber());
                        }
                    }
                });
            }
        });
        shiftTo.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                SC.askforValue(lang.shiftTo(), lang.enterShiftToNum(), new ValueCallback() {

                    @Override
                    public void execute(String value) {
                        if (value == null) {
                            return;
                        }
                        try {
                            int n = Integer.parseInt(value);
                            addUndoRedo(tileGrid.getData(), true, false);
                            numbering.shift(n, true, layout);
                        } catch (NumberFormatException nfe) {
                            SC.say(lang.notANumber());
                        }
                    }
                });
            }
        });

        MenuItemSeparator separator = new MenuItemSeparator();

        menu.setItems(editTitle,
                      separator,
                      renumberAll,
                      renumber,
                      separator,
                      toRoman,
                      toRomanOld,
                      separator,
                      surround,
                      abc,
                      separator,
                      toLeft,
                      toRight,
                      shiftTo);

        ToolStripMenuButton menuButton = new ToolStripMenuButton(lang.pageNumbers(), menu);
        menuButton.setWidth(100);
        return menuButton;
    }

    private void romanRenumber(final boolean toRomanOld) {
        SC.askforValue(toRomanOld ? lang.convertToRomanOld() : lang.convertToRoman(),
                       lang.enterLatinNumberForRenumber(),
                       String.valueOf(tileGrid.getDataAsRecordList().indexOf(tileGrid.getSelection()[0]) + 1),
                       new ValueCallback() {

                           @Override
                           public void execute(String value) {
                               if (value == null) {
                                   return;
                               }
                               try {
                                   int n = Integer.parseInt(value);
                                   addUndoRedo(tileGrid.getData(), true, false);
                                   Record[] data = tileGrid.getSelection();
                                   int i = 0;
                                   if (data != null && data.length > 0) {
                                       for (Record rec : data) {
                                           rec.setAttribute(Constants.ATTR_NAME,
                                                            ClientUtils.decimalToRoman(n + i++, toRomanOld));
                                       }
                                   }
                                   updateSelectedTileGrid();
                               } catch (NumberFormatException nfe) {
                                   SC.say(lang.notANumber());
                               }
                           }
                       },
                       new com.smartgwt.client.widgets.Dialog() {

                           {
                               setWidth(330);
                           }
                       });
    }

    private void updateSelectedTileGrid() {
        Record[] selection = tileGrid.getSelection();
        tileGrid.deselectRecords(selection);
        tileGrid.selectRecords(selection);
    }

    private void updateTileGrid() {
        Record[] selection = tileGrid.getSelection();
        tileGrid.selectAllRecords();
        tileGrid.deselectAllRecords();
        tileGrid.selectRecords(selection);
    }

    private Menu getAbcSubmenu() {
        Menu menu = new Menu();
        menu.setShowShadow(true);
        menu.setShadowDepth(3);

        MenuItem rv = new MenuItem("1, 2, 3, 4   ⇨   1r, 1v, 2r, 2v");
        MenuItem abc2 = new MenuItem("1, 2, 3, 4   ⇨   1a, 1b, 2a, 2b");
        MenuItem abc3 = new MenuItem("1, 2, 3, 4   ⇨   1a, 1b, 1c, 2a");
        MenuItem abc4 = new MenuItem("1, 2, 3, 4   ⇨   1a, 1b, 1c, 1d");
        MenuItem abc5 = new MenuItem("1, .. 4, 5   ⇨   1a, ... 1e, 2a");
        MenuItem abcN = new MenuItem(lang.customizeIndex() + "...");
        rv.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                addUndoRedo(tileGrid.getData(), true, false);
                numbering.foliation();
                updateSelectedTileGrid();
            }
        });
        abc2.addClickHandler(getAbcHandler(2));
        abc3.addClickHandler(getAbcHandler(3));
        abc4.addClickHandler(getAbcHandler(4));
        abc5.addClickHandler(getAbcHandler(5));
        abcN.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                // TODO Auto-generated method stub
                SC.askforValue(lang.customizeIndex(), lang.enterNumberForIndex(), new ValueCallback() {

                    @Override
                    public void execute(String value) {
                        try {
                            int n = Integer.parseInt(value);
                            addUndoRedo(tileGrid.getData(), true, false);
                            numbering.toAbcN(n);
                        } catch (NumberFormatException nfe) {
                            SC.say(lang.notANumber());
                        }
                    }
                });
                updateSelectedTileGrid();
            }
        });
        rv.setEnableIfCondition(isSelected(false));
        abc2.setEnableIfCondition(isSelected(false));
        abc3.setEnableIfCondition(isSelected(false));
        abc4.setEnableIfCondition(isSelected(false));
        abc5.setEnableIfCondition(isSelected(false));
        abcN.setEnableIfCondition(isSelected(false));
        menu.setItems(rv, new MenuItemSeparator(), abc2, abc3, abc4, abc5, new MenuItemSeparator(), abcN);
        return menu;
    }

    private com.smartgwt.client.widgets.menu.events.ClickHandler getAbcHandler(final int n) {
        return new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                addUndoRedo(tileGrid.getData(), true, false);
                numbering.toAbcN(n);
                updateSelectedTileGrid();
            }
        };
    }

    /**
     * Method for close currently displayed window
     */
    @Override
    public void escShortCut() {
        if (winModal != null) {
            winModal.hide();
            winModal = null;
        } else if (imagePopup.isVisible()) {
            imagePopup.setVisible(false);
        }
    }

    @Override
    public void showDetail(int height, boolean isTop, int topSpace) {
        if (topSpace >= 0) {
            if (isTop) {
                detailHeightTop = height;
                topSpaceTop = topSpace;
            } else {
                detailHeightBottom = height;
                topSpaceBottom = topSpace;
            }
        }
        TileRecord record = tileGrid.getSelectedRecord();
        showDetail(record == null ? null : record.getAttribute(Constants.ATTR_PICTURE));
    }

    private void showDetail(final String pid) {
        if (detailPanelShown) {
            detailPanel.setHeight(((topIsWraped ? 16 : detailHeightTop) + (bottomIsWraped ? 16
                    : detailHeightBottom)) + 52);
            if (detailPanelImageShown) {
                tileGridLayout.removeMember(detailPanel);
                detailPanel.removeMembers(detailPanel.getMembers());
                tileGridLayout.redraw();
            }
            if (pid != null) {
                StringBuffer sb = new StringBuffer();
                sb.append(Constants.SERVLET_SCANS_PREFIX).append('/').append(pid).append("?")
                        .append(Constants.URL_PARAM_HEIGHT).append('=')
                        .append(String.valueOf(detailHeightTop)).append("&")
                        .append(Constants.URL_PARAM_TOP_SPACE).append("=").append(topSpaceTop);

                final Img top = new Img(sb.toString(), 900, detailHeightTop);

                top.setAnimateTime(400);
                top.setImageWidth(900);
                top.setImageHeight(detailHeightTop);
                top.setImageType(ImageStyle.NORMAL);
                top.setBorder("1px solid gray");
                top.addDoubleClickHandler(new DoubleClickHandler() {

                    @Override
                    public void onDoubleClick(DoubleClickEvent event) {
                        final ModalWindow mw = new ModalWindow(top);
                        mw.setLoadingIcon("loadingAnimation.gif");
                        mw.show(true);
                        getUiHandlers()
                                .chooseDetail(Constants.SERVLET_IMAGES_PREFIX
                                                      .concat(Constants.SERVLET_SCANS_PREFIX).concat("/")
                                                      .concat(pid)
                                                      .concat("?" + Constants.URL_PARAM_FULL + "=yes"),
                                              detailHeightTop,
                                              pid,
                                              true,
                                              topSpaceTop,
                                              mw);
                    }
                });

                sb = new StringBuffer();
                sb.append(Constants.SERVLET_SCANS_PREFIX).append('/').append(pid).append("?")
                        .append(Constants.URL_PARAM_HEIGHT).append('=')
                        .append(String.valueOf(detailHeightBottom)).append("&")
                        .append(Constants.URL_PARAM_TOP_SPACE).append("=").append(topSpaceBottom);

                final Img bottom = new Img(sb.toString(), 900, detailHeightBottom);
                bottom.setAnimateTime(400);
                bottom.setImageWidth(900);
                bottom.setImageHeight(detailHeightBottom);
                bottom.setImageType(ImageStyle.NORMAL);
                bottom.setBorder("1px solid gray");
                bottom.addDoubleClickHandler(new DoubleClickHandler() {

                    @Override
                    public void onDoubleClick(DoubleClickEvent event) {
                        final ModalWindow mw = new ModalWindow(bottom);
                        mw.setLoadingIcon("loadingAnimation.gif");
                        mw.show(true);
                        getUiHandlers()
                                .chooseDetail(Constants.SERVLET_IMAGES_PREFIX
                                                      .concat(Constants.SERVLET_SCANS_PREFIX).concat("/")
                                                      .concat(pid)
                                                      .concat("?" + Constants.URL_PARAM_FULL + "=yes"),
                                              detailHeightBottom,
                                              pid,
                                              false,
                                              topSpaceBottom,
                                              mw);
                    }
                });

                Label topL = new Label(lang.top());
                topL.setHeight(12);
                final ImgButton wrapTop = new ImgButton();
                wrapTop.setShowRollOver(false);
                wrapTop.setShowDown(false);
                wrapTop.setHeight(16);
                wrapTop.setWidth(16);
                if (!topIsWraped) {
                    wrapTop.setSrc("icons/16/wrap.png");
                } else {
                    wrapTop.setSrc("icons/16/unwrap.png");
                }
                wrapTop.setHoverStyle("interactImageHover");
                wrapTop.setCanHover(true);
                wrapTop.setHoverOpacity(85);
                wrapTop.addHoverHandler(new HoverHandler() {

                    @Override
                    public void onHover(HoverEvent event) {
                        wrapTop.setPrompt(topIsWraped ? lang.unwrap() : lang.wrap());
                    }
                });

                wrapTop.addClickHandler(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        topIsWraped = !topIsWraped;
                        showDetail(pid);
                    }
                });

                final ImgButton wrapBottom = new ImgButton();
                wrapBottom.setShowRollOver(false);
                wrapBottom.setShowDown(false);
                wrapBottom.setHeight(16);
                wrapBottom.setWidth(16);
                if (!bottomIsWraped) {
                    wrapBottom.setSrc("icons/16/wrap.png");
                } else {
                    wrapBottom.setSrc("icons/16/unwrap.png");
                }
                wrapBottom.setHoverStyle("interactImageHover");
                wrapBottom.setCanHover(true);
                wrapBottom.setHoverOpacity(85);
                wrapBottom.addHoverHandler(new HoverHandler() {

                    @Override
                    public void onHover(HoverEvent event) {
                        wrapBottom.setPrompt(bottomIsWraped ? lang.unwrap() : lang.wrap());
                    }
                });

                wrapBottom.addClickHandler(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        bottomIsWraped = !bottomIsWraped;
                        showDetail(pid);
                    }
                });

                detailPanel.setAlign(Alignment.CENTER);
                detailPanel.setAlign(VerticalAlignment.CENTER);

                Layout topLayout = new HLayout(2);
                topLayout.setHeight(16);
                topLayout.addMember(topL);
                topLayout.addMember(wrapTop);
                detailPanel.addMember(topLayout);
                if (!topIsWraped) detailPanel.addMember(top);

                Label botL = new Label(lang.bottom());
                botL.setHeight(12);
                Layout bottomLayout = new HLayout(2);
                bottomLayout.setHeight(16);
                bottomLayout.addMember(botL);
                bottomLayout.addMember(wrapBottom);
                detailPanel.addMember(bottomLayout);
                if (!bottomIsWraped) detailPanel.addMember(bottom);
                detailPanelImageShown = true;
                tileGridLayout.addMember(detailPanel);
                tileGridLayout.redraw();
            }
        }
    }

    private void editPageTitle() {
        SC.askforValue(lang.editPageName(), lang.editPageNewName(), new ValueCallback() {

            @Override
            public void execute(String value) {
                if (value != null && !"".equals(value.trim())) {
                    addUndoRedo(tileGrid.getData(), true, false);
                    tileGrid.getSelection()[0].setAttribute(Constants.ATTR_NAME, value);
                }
            }
        });
    }

    private MenuItemIfFunction isSelected(final boolean justOne) {
        return new MenuItemIfFunction() {

            @Override
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                return tileGrid.getSelection() != null && tileGrid.getSelection().length > 0
                        && (!justOne || tileGrid.getSelection().length == 1);
            }
        };
    }

    public void initModsOrDc() {
        if (metadataPanelShown) {
            topTabSet = new EditorTabSet();
            topTabSet.setTabBarPosition(Side.TOP);
            topTabSet.setWidth100();
            topTabSet.setHeight100();
            topTabSet.setAnimateTabScrolling(true);
            topTabSet.setShowPaneContainerEdges(false);
            final Tab moTab = new Tab("MODS", "pieces/16/piece_blue.png");
            moTab.setAttribute("id", "mods");
            DCTab dcTab = new DCTab(getUiHandlers().getDc());
            topTabSet.setDcTab(dcTab);
            topTabSet.setTabs(dcTab, moTab);
            topTabSet.addTabSelectedHandler(new TabSelectedHandler() {

                @Override
                public void onTabSelected(final TabSelectedEvent event) {
                    if ("mods".equals(event.getTab().getAttribute("id")) && event.getTab().getPane() == null) {
                        final ModalWindow mw = new ModalWindow(topTabSet);
                        mw.setLoadingIcon("loadingAnimation.gif");
                        mw.show(true);
                        Timer timer = new Timer() {

                            @Override
                            public void run() {
                                ModsTab modsTab = new ModsTab(1, getUiHandlers().getMods());
                                VLayout modsLayout = modsTab.getModsLayout();
                                topTabSet.setModsTab(modsTab);
                                TabSet ts = event.getTab().getTabSet();
                                ts.setTabPane(event.getTab().getID(), modsLayout);
                                mw.hide();
                            }
                        };
                        timer.schedule(25);
                    }
                }
            });
            metadataPanel.addMember(topTabSet);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TileGrid getTileGrid() {
        return tileGrid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addUndoRedo(Record[] data, boolean isUndoList, boolean isRedoOperation) {
        Record[] newData = new Record[data.length];
        int i = 0;
        for (Record d : data) {
            newData[i] =
                    new ScanRecord(d.getAttribute(Constants.ATTR_NAME),
                                   d.getAttribute(Constants.ATTR_MODEL),
                                   d.getAttribute(Constants.ATTR_BARCODE),
                                   d.getAttribute(Constants.ATTR_PICTURE),
                                   d.getAttribute(Constants.ATTR_DESC),
                                   d.getAttribute(Constants.ATTR_PAGE_TYPE));
            i++;
        }
        if (isUndoList) {
            undoList.add(newData);
            undoButton.enable();
            if (!isRedoOperation && redoList.size() > 0) {
                redoList = new ArrayList<Record[]>();
                redoButton.setDisabled(true);
            }
        } else {
            redoList.add(newData);
            redoButton.enable();
        };

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void resizeThumbnails(boolean larger) {

        final ModalWindow mw = new ModalWindow(layout);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        if (larger) {
            imageThumbnailWidth = (imageThumbnailWidth * 110) / 100;
            imageThumbnailHeight = (imageThumbnailHeight * 110) / 100;
        } else {
            if (imageThumbnailHeight > 60) {
                imageThumbnailWidth = (imageThumbnailWidth * 100) / 110;
                imageThumbnailHeight = (imageThumbnailHeight * 100) / 110;
            }
        }

        Record[] selection = tileGrid.getSelection();
        onAddImages(model, tileGrid.getData(), hostname, true);
        tileGrid.selectRecords(selection);
        mw.hide();
    }
}