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

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.viewer.DetailFormatter;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.presenter.CreateStructurePresenter.MyView;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.CreateStructureView.MyUiHandlers;
import cz.fi.muni.xkremser.editor.client.view.window.ModalWindow;

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

    }

    /** The Constant ID_MODEL. */
    public static final String ID_MODEL = "model";

    /** The Constant ID_NAME. */
    public static final String ID_NAME = "name";

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

    /** The clipboard. */
    private Record[] clipboard;

    /** The layout. */
    private final VLayout layout;

    /** The image popup. */
    private PopupPanel imagePopup;

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
        final TileGrid tileGrid = new TileGrid();
        tileGrid.setTileWidth(Constants.PAGE_THUMBNAIL_WIDTH);
        tileGrid.setTileHeight(Constants.PAGE_THUMBNAIL_HEIGHT);
        tileGrid.setHeight100();
        tileGrid.setWidth100();
        tileGrid.setCanDrag(true);
        tileGrid.setCanAcceptDrop(true);
        tileGrid.setShowAllRecords(true);
        Menu menu = new Menu();
        menu.setShowShadow(true);
        menu.setShadowDepth(10);

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

        menu.setItems(selectAllItem,
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
                        final Image full =
                                new Image(Constants.SERVLET_IMAGES_PREFIX + Constants.SERVLET_SCANS_PREFIX
                                        + '/' + event.getRecord().getAttribute(Constants.ATTR_PICTURE));
                        full.setHeight("700px");
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

        DetailViewerField pictureField = new DetailViewerField(Constants.ATTR_PICTURE);
        pictureField.setType("image");
        pictureField.setImageURLPrefix(Constants.SERVLET_SCANS_PREFIX + '/');
        pictureField.setImageWidth(80);
        pictureField.setImageHeight(110);

        DetailViewerField nameField = new DetailViewerField(Constants.ATTR_ISSN);
        nameField.setDetailFormatter(new DetailFormatter() {

            @Override
            public String format(Object value, Record record, DetailViewerField field) {
                return lang.scan() + ": " + value;
            }
        });

        tileGrid.setFields(pictureField, nameField);
        tileGrid.setData(items);
        getUiHandlers().onAddImages(tileGrid, menu);
        layout.addMember(tileGrid);
    }
}