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

import java.util.LinkedHashMap;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
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
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
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
import cz.fi.muni.xkremser.editor.client.view.other.EditorTabSet;
import cz.fi.muni.xkremser.editor.client.view.other.ModsTab;
import cz.fi.muni.xkremser.editor.client.view.other.ScanRecord;
import cz.fi.muni.xkremser.editor.client.view.window.ModalWindow;

import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateView.
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

        void createObjects(DublinCore dc, ModsTypeClient mods);

        ModsCollectionClient getMods();

        DublinCore getDc();

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

    private SelectItem specialPageItem;

    private EditorTabSet topTabSet;

    private int pageDetailHeight = Constants.PAGE_PREVIEW_HEIGHT_NORMAL;

    /**
     * Instantiates a new create view.
     */
    @Inject
    public CreateStructureView(LangConstants lang) {
        this.lang = lang;
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
    public void onAddImages(String model, String code, ScanRecord[] items) {
        if (layout.getMembers().length != 0) {
            layout.removeMember(tileGridLayout);
        }
        tileGridLayout = new VLayout();
        tileGrid = new TileGrid();
        tileGrid.setTileWidth(Constants.TILEGRID_ITEM_WIDTH);
        tileGrid.setTileHeight(Constants.TILEGRID_ITEM_HEIGHT);
        tileGrid.setHeight100();
        tileGrid.setWidth100();
        tileGrid.setCanDrag(true);
        tileGrid.setCanAcceptDrop(true);
        tileGrid.setShowAllRecords(true);
        tileGrid.setShowResizeBar(true);
        Menu menu = new Menu();
        menu.setShowShadow(true);
        menu.setShadowDepth(10);

        MenuItem viewItem = new MenuItem(lang.menuView(), "icons/16/eye.png");
        viewItem.setAttribute(ID_NAME, ID_VIEW);
        viewItem.setEnableIfCondition(new MenuItemIfFunction() {

            @Override
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                return tileGrid.getSelection() != null && tileGrid.getSelection().length == 1;
            }
        });
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
                //                viewerPane.setContentsURL("http://editor.mzk.cz/meditor/viewer/viewer.html?rft_id=" + uuid);
                viewerPane.setContentsURL("http://editor.mzk.cz/meditor/viewer/viewer.html");
                viewerPane.setContentsURLParams(new java.util.HashMap<String, String>() {

                    {
                        put("rft_id", uuid);
                    }
                });
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

        menu.setItems(viewItem,
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
                        full.setHeight(Constants.IMAGE_FULL_WIDTH + "px");
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
                    showDetail(event.getRecord().getAttribute(Constants.ATTR_PICTURE), pageDetailHeight);
                }
            }
        });
        tileGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionChangedEvent event) {
                if (tileGrid.getSelection() != null && tileGrid.getSelection().length == 1) {
                    specialPageItem.enable();
                } else {
                    specialPageItem.disable();
                }
            }
        });

        final DetailViewerField pictureField = new DetailViewerField(Constants.ATTR_PICTURE);
        pictureField.setType("image");
        pictureField.setImageURLPrefix(Constants.SERVLET_SCANS_PREFIX + '/');
        pictureField.setImageWidth(Constants.IMAGE_THUMBNAIL_WIDTH);
        pictureField.setImageHeight(Constants.IMAGE_THUMBNAIL_HEIGHT);

        DetailViewerField nameField = new DetailViewerField(Constants.ATTR_NAME);
        nameField.setDetailFormatter(new DetailFormatter() {

            @Override
            public String format(Object value, Record record, DetailViewerField field) {
                StringBuffer sb = new StringBuffer();
                sb.append(lang.scan()).append(": ").append(value);
                return sb.toString();
            }
        });

        tileGrid.setFields(pictureField, nameField);
        tileGrid.setData(items);
        getUiHandlers().onAddImages(tileGrid, menu);

        ToolStrip toolStrip = new ToolStrip();
        toolStrip.setWidth100();
        ToolStripMenuButton menuButton = getToolStripMenuButton();
        toolStrip.addMenuButton(menuButton);

        specialPageItem = new SelectItem();
        specialPageItem.setShowTitle(false);
        specialPageItem.setWidth(100);
        specialPageItem.setTitle(lang.specialType());

        LinkedHashMap<String, String> valueMap =
                new LinkedHashMap<String, String>(Constants.SPECIAL_PAGE_TYPE_MAP);
        specialPageItem.setValueMap(valueMap);
        specialPageItem.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                tileGrid.getSelectedRecord().setAttribute(Constants.ATTR_NAME, event.getValue());
            }
        });

        toolStrip.addFormItem(specialPageItem);
        toolStrip.addResizer();

        SelectItem zoomItems = new SelectItem("pageDetail", lang.detailSize());
        zoomItems.setName("selectName");
        zoomItems.setShowTitle(true);
        zoomItems.setWrapTitle(false);
        zoomItems.setWidth(70);
        zoomItems.setValueMap("75%", "100%", "125%", "150%");
        zoomItems.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                String percent = (String) event.getValue();
                if ("75%".equals(percent)) {
                    pageDetailHeight = Constants.PAGE_PREVIEW_HEIGHT_SMALL;
                } else if ("100%".equals(percent)) {
                    pageDetailHeight = Constants.PAGE_PREVIEW_HEIGHT_NORMAL;
                } else if ("125%".equals(percent)) {
                    pageDetailHeight = Constants.PAGE_PREVIEW_HEIGHT_LARGE;
                } else if ("150%".equals(percent)) {
                    pageDetailHeight = Constants.PAGE_PREVIEW_HEIGHT_XLARGE;
                }
                showDetail(pageDetailHeight);
            }
        });
        zoomItems.setDefaultValue("100%");
        toolStrip.addFormItem(zoomItems);
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
                    showDetail(pageDetailHeight);
                } else if (detailPanelShown) {
                    detailPanelShown = false;
                    tileGridLayout.removeMember(detailPanel);
                } else {
                    tileGridLayout.addMember(detailPanel);
                    detailPanelShown = true;
                }
            }
        });
        toolStrip.addButton(zoomButton);

        editMetadataButton.setIcon("silk/metadata_edit.png");
        editMetadataButton.setTitle(lang.editMeta());
        editMetadataButton.setActionType(SelectionType.CHECKBOX);
        editMetadataButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (detailPanelShown) {
                    detailPanelShown = false;
                    tileGridLayout.removeMember(detailPanel);
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

        ToolStripButton createButton = new ToolStripButton();
        createButton.setIcon("silk/forward_green.png");
        createButton.setTitle(lang.create());
        createButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                DublinCore dc = null;
                ModsTypeClient mods = null;
                if (topTabSet != null) {
                    dc = topTabSet.getDcTab().getDc();
                    if (topTabSet.getModsTab() != null) {
                        mods = ((ModsTab) topTabSet.getModsTab()).getMods();
                    }
                }
                getUiHandlers().createObjects(dc, mods);
            }
        });

        toolStrip.addButton(createButton);

        tileGridLayout.addMember(toolStrip);
        tileGridLayout.addMember(tileGrid);
        layout.addMember(tileGridLayout);
        specialPageItem.disable();
    }

    private ToolStripMenuButton getToolStripMenuButton() {
        Menu menu = new Menu();
        menu.setShowShadow(true);
        menu.setShadowDepth(3);

        MenuItem renumberAll = new MenuItem(lang.renumberAll(), "icons/16/renumberAll.png");
        MenuItem renumber = new MenuItem(lang.renumber(), "icons/16/renumber.png");
        MenuItem toRoman = new MenuItem(lang.convert(), "icons/16/roman.png");
        MenuItem surround = new MenuItem("1, 2, ... -> [1], [2], ...", "icons/16/surround.png");
        MenuItem abc = new MenuItem("1, 2, ... -> 1a, 2b, ...", "icons/16/abc.png");
        MenuItem toLeft = new MenuItem(lang.leftShift(), "icons/16/arrow_left.png");
        MenuItem toRight = new MenuItem(lang.rightShift(), "icons/16/arrow_right.png");
        renumber.setEnableIfCondition(isSelected());
        toRoman.setEnableIfCondition(isSelected());
        surround.setEnableIfCondition(isSelected());
        abc.setEnableIfCondition(isSelected());
        toLeft.setEnableIfCondition(isSelected());
        toRight.setEnableIfCondition(isSelected());
        renumberAll.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                Record[] data = tileGrid.getData();
                if (data != null) {
                    int i = 1;
                    for (Record rec : data) {
                        rec.setAttribute(Constants.ATTR_NAME, i++);
                    }
                }
            }
        });
        renumber.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                Record[] data = tileGrid.getSelection();
                if (data != null && data.length > 0) {
                    String startingNumber = data[0].getAttributeAsString(Constants.ATTR_NAME);
                    int i = getPageNumberFromText(startingNumber);
                    for (Record rec : data) {
                        rec.setAttribute(Constants.ATTR_NAME, i++);
                    }
                }
            }
        });

        toRoman.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                Record[] data = tileGrid.getSelection();
                if (data != null && data.length > 0) {
                    String startingNumber = data[0].getAttributeAsString(Constants.ATTR_NAME);
                    int i = getPageNumberFromText(startingNumber);
                    for (Record rec : data) {
                        rec.setAttribute(Constants.ATTR_NAME, ClientUtils.numbersParse(i++));
                    }
                }
            }
        });

        surround.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                Record[] data = tileGrid.getSelection();
                if (data != null && data.length > 0) {
                    String startingNumber = data[0].getAttributeAsString(Constants.ATTR_NAME);
                    int i = getPageNumberFromText(startingNumber);
                    for (Record rec : data) {
                        rec.setAttribute(Constants.ATTR_NAME, "[" + i++ + "]");
                    }
                }
            }
        });

        abc.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                Record[] data = tileGrid.getSelection();
                if (data != null && data.length > 0) {
                    String startingNumber = data[0].getAttributeAsString(Constants.ATTR_NAME);
                    int i = getPageNumberFromText(startingNumber);
                    int j = 0;
                    for (Record rec : data) {
                        rec.setAttribute(Constants.ATTR_NAME, (i + (j / 2)) + (j % 2 == 0 ? "a" : "b"));
                        j++;
                    }
                }
            }
        });

        //        toLeft.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
        //
        //            @Override
        //            public void onClick(MenuItemClickEvent event) {
        //                Record[] data = tileGrid.getSelection();
        //                if (data != null && data.length > 0) {
        //                    String startingNumber = data[0].getAttributeAsString(Constants.ATTR_NAME);
        //                    int i = getPageNumberFromText(startingNumber);
        //                    int j = 1;
        //                    for (Record rec : data) {
        //                        rec.setAttribute(Constants.ATTR_NAME, (i + (j / 2)) + (j % 2 == 1 ? "a" : "b"));
        //                        j++;
        //                    }
        //                }
        //            }
        //        });

        MenuItemSeparator separator = new MenuItemSeparator();

        menu.setItems(renumberAll, renumber, separator, toRoman, surround, abc, separator, toLeft, toRight);

        ToolStripMenuButton menuButton = new ToolStripMenuButton(lang.pageNumbers(), menu);
        menuButton.setWidth(100);
        return menuButton;
    }

    /**
     * Method for close currently displayed window
     */
    @Override
    public void escShortCut() {
        if (winModal != null) {
            winModal.destroy();
            winModal = null;
        } else if (imagePopup.isVisible()) {
            imagePopup.setVisible(false);
        }
    }

    private void showDetail(int height) {
        TileRecord record = tileGrid.getSelectedRecord();
        showDetail(record == null ? null : record.getAttribute(Constants.ATTR_PICTURE), height);
    }

    private void showDetail(String pid, int height) {
        if (detailPanelShown) {
            detailPanel.setHeight((height * 2) + 52);
            if (detailPanelImageShown) {
                detailPanel.removeMembers(detailPanel.getMembers());
            }
            if (pid != null) {
                StringBuffer sb = new StringBuffer();
                sb.append(Constants.SERVLET_SCANS_PREFIX).append('/').append(pid).append("?")
                        .append(Constants.URL_PARAM_HEIGHT).append('=').append(String.valueOf(height))
                        .append("&").append(Constants.URL_PARAM_TOP).append("=yes");

                Img top = new Img(sb.toString(), 900, height);
                top.setAnimateTime(400);
                top.setImageWidth(900);
                top.setImageHeight(height);
                top.setImageType(ImageStyle.NORMAL);
                top.setBorder("1px solid gray");

                sb = new StringBuffer();
                sb.append(Constants.SERVLET_SCANS_PREFIX).append('/').append(pid).append("?")
                        .append(Constants.URL_PARAM_HEIGHT).append('=').append(String.valueOf(height))
                        .append("&").append(Constants.URL_PARAM_BOTTOM).append("=yes");

                Img bottom = new Img(sb.toString(), 900, height);
                bottom.setAnimateTime(400);
                bottom.setImageWidth(900);
                bottom.setImageHeight(height);
                bottom.setImageType(ImageStyle.NORMAL);
                bottom.setBorder("1px solid gray");
                Label topL = new Label(lang.top());
                topL.setHeight(12);
                detailPanel.setAlign(Alignment.CENTER);
                detailPanel.setAlign(VerticalAlignment.CENTER);
                detailPanel.addMember(topL);
                detailPanel.addMember(top);
                Label botL = new Label(lang.bottom());
                botL.setHeight(12);
                detailPanel.addMember(botL);
                detailPanel.addMember(bottom);
                detailPanelImageShown = true;
            }
        }
    }

    private int getPageNumberFromText(String text) {
        if (text != null && !"".equals(text)) {
            try {
                if (text.charAt(0) == '[') {
                    text = text.substring(1, text.lastIndexOf(']'));
                }
                while (!Character.isDigit(text.charAt(text.length() - 1))) {
                    text = text.substring(0, text.length() - 1);
                }
                if (!"".equals(text)) {
                    return Integer.parseInt(text);
                }
            } catch (NumberFormatException nfe) {
                return 1;
            }
        }
        return 1;
    }

    private MenuItemIfFunction isSelected() {
        return new MenuItemIfFunction() {

            @Override
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                return tileGrid.getSelectedRecord() != null;
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
}