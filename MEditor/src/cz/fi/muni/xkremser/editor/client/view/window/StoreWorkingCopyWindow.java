/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
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

package cz.fi.muni.xkremser.editor.client.view.window;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.other.EditorTabSet;
import cz.fi.muni.xkremser.editor.client.view.other.HtmlCode;

import cz.fi.muni.xkremser.editor.shared.rpc.DigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.rpc.StoredItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.StoredItemsAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.StoredItemsResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class StoreWorkingCopyWindow
        extends UniversalWindow {

    private final LangConstants lang;
    private static StoreWorkingCopyWindow storingWindow = null;

    public static void setInstanceOf(DigitalObjectDetail detail,
                                     final LangConstants lang,
                                     DispatchAsync dispatcher,
                                     EditorTabSet ts,
                                     EventBus eventBus) {
        if (isInstanceVisible()) {
            closeInstantiatedWindow();
        }
        storingWindow = new StoreWorkingCopyWindow(detail, lang, dispatcher, ts, eventBus);
    }

    public static boolean isInstanceVisible() {
        return (storingWindow != null && storingWindow.isCreated());
    }

    public static void closeInstantiatedWindow() {
        storingWindow.hide();
        storingWindow = null;
    }

    private StoreWorkingCopyWindow(final DigitalObjectDetail detail,
                                   final LangConstants lang,
                                   final DispatchAsync dispatcher,
                                   final EditorTabSet ts,
                                   final EventBus eventBus) {
        super(450,
              550,
              lang.save() + ": " + detail.getUuid() + " " + lang.name() + ": " + detail.getLabel(),
              eventBus,
              40);
        this.lang = lang;

        final ListGrid storedFilesGrid;
        storedFilesGrid = new ListGrid();
        Label fileNameLabel = new Label();
        fileNameLabel.setContents(HtmlCode.title(lang.fileNameLabel() + ": ", 3));
        fileNameLabel.setAutoHeight();
        fileNameLabel.setExtraSpace(8);
        Label storedLabel = new Label();
        storedLabel.setContents(HtmlCode.title(lang.storedFiles() + ": ", 4));
        storedLabel.setAutoHeight();
        storedLabel.setExtraSpace(3);

        final TextItem fileNameItem = new TextItem();
        fileNameItem.setTitle(lang.fileName());
        fileNameItem.setWidth(350);

        fileNameItem.setDefaultValue(detail.getUuid() + "_" + detail.getLabel());
        DynamicForm saveForm = new DynamicForm();
        saveForm.setItems(fileNameItem);
        saveForm.setExtraSpace(12);

        storedFilesGrid.setWidth(500);
        storedFilesGrid.setHeight(200);
        storedFilesGrid.setShowSortArrow(SortArrow.CORNER);
        storedFilesGrid.setShowAllRecords(true);
        storedFilesGrid.setCanHover(true);
        storedFilesGrid.setHoverOpacity(75);
        storedFilesGrid.setHoverStyle("interactImageHover");
        storedFilesGrid.setExtraSpace(20);

        storedFilesGrid.setHoverWidth(300);
        storedFilesGrid.setHoverCustomizer(new HoverCustomizer() {

            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                StringBuffer sb = new StringBuffer();
                sb.append(record.getAttribute(Constants.ATTR_UUID));
                sb.append("<br/>").append(lang.name()).append(": ");
                sb.append(record.getAttribute(Constants.ATTR_NAME));
                sb.append("<br/>").append(lang.description()).append(": ").append("<br/>");
                sb.append(record.getAttribute(Constants.ATTR_DESC));
                return sb.toString();
            }
        });
        storedFilesGrid.addCellContextClickHandler(new CellContextClickHandler() {

            @Override
            public void onCellContextClick(CellContextClickEvent event) {
                Menu menu = new Menu();
                menu.setShowShadow(true);
                menu.setShadowDepth(10);
                MenuItem openItem = new MenuItem("Open", "icons/16/document_plain_new.png");

                openItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

                    @Override
                    public void onClick(MenuItemClickEvent event) {

                    }
                });
                menu.addItem(openItem);
                setContextMenu(menu);
            }
        });
        storedFilesGrid.addCellClickHandler(new CellClickHandler() {

            @Override
            public void onCellClick(CellClickEvent event) {
                fileNameItem.setValue(event.getRecord().getAttribute(Constants.ATTR_FILE_NAME));
            }
        });
        ModalWindow modalWindow = new ModalWindow(storedFilesGrid);
        modalWindow.setLoadingIcon("loadingAnimation.gif");
        modalWindow.show(true);

        HLayout buttonsLayout = new HLayout(2);
        buttonsLayout.setAutoWidth();
        Button storeButton = new Button(lang.save());
        storeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Record[] records = storedFilesGrid.getRecords();
                boolean nameIsSame = false;

                for (Record record : records) {
                    if (record.getAttributeAsString(Constants.ATTR_FILE_NAME).equals(fileNameItem.getValue())) {

                        SC.ask(lang.fileExists(), new BooleanCallback() {

                            @Override
                            public void execute(Boolean value) {
                                if (value) {
                                    store(detail, dispatcher, ts, eventBus);
                                    closeInstantiatedWindow();
                                } else {
                                    fileNameItem.selectValue();
                                }
                            }
                        });
                        nameIsSame = true;
                        break;
                    }
                }

                if (!nameIsSame) {
                    store(detail, dispatcher, ts, eventBus);
                    closeInstantiatedWindow();
                }
            }
        });
        storeButton.setExtraSpace(8);
        buttonsLayout.addMember(storeButton);

        Button closeButton = new Button(lang.close());
        closeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
        buttonsLayout.addMember(closeButton);

        addItem(fileNameLabel);
        addItem(saveForm);
        addItem(storedLabel);
        addItem(storedFilesGrid);
        addItem(buttonsLayout);

        setEdgeOffset(20);
        setData(dispatcher, storedFilesGrid);
        centerInPage();
        show();
        focus();
        buttonsLayout.setLeft(280);
        fileNameItem.selectValue();
        modalWindow.destroy();
    }

    protected void setData(final DispatchAsync dispatcher, final ListGrid storedFilesGrid) {
        ListGridField fileNameField = new ListGridField(Constants.ATTR_FILE_NAME, lang.name());
        ListGridField storedField = new ListGridField(Constants.ATTR_STORED, lang.stored());
        storedField.setWidth(120);
        storedFilesGrid.setFields(fileNameField, storedField);

        dispatcher.execute(new StoredItemsAction(null), new DispatchCallback<StoredItemsResult>() {

            @Override
            public void callbackError(final Throwable cause) {
                super.callbackError(cause);
            }

            @Override
            public void callback(StoredItemsResult result) {

                List<StoredItem> items = result.getStoredItems();
                ListGridRecord[] records = new ListGridRecord[items.size()];
                for (int i = 0; i < items.size(); i++) {
                    ListGridRecord record = new ListGridRecord();
                    copyValues(items.get(i), record);
                    records[i] = record;
                }
                storedFilesGrid.setData(records);
                storedFilesGrid.sort(Constants.ATTR_STORED, SortDirection.ASCENDING);
                storedFilesGrid.selectRecord(0);
                storedFilesGrid.scrollToRow(0);
            }
        });
    }

    /**
     * Copy values.
     * 
     * @param from
     *        the from
     * @param to
     *        the to
     */
    private static void copyValues(StoredItem from, ListGridRecord to) {
        to.setAttribute(Constants.ATTR_FILE_NAME, from.getFileName());
        to.setAttribute(Constants.ATTR_STORED, from.getStoredDate());
        to.setAttribute(Constants.ATTR_NAME, from.getName());
        to.setAttribute(Constants.ATTR_UUID, from.getUuid());
        to.setAttribute(Constants.ATTR_DESC, from.getDescription());
    }

    private void store(DigitalObjectDetail detail,
                       final DispatchAsync dispatcher,
                       final EditorTabSet ts,
                       final EventBus eventBus) {

        StoredItemsAction storedAction = new StoredItemsAction(detail);
        DispatchCallback<StoredItemsResult> storedCallback = new DispatchCallback<StoredItemsResult>() {

            @Override
            public void callback(StoredItemsResult result) {
                if (result.getStoredItems() == null && result.getErrorMessage() == null) {
                    SC.ask(lang.operationSuccessful() + "<br/><br/>" + lang.lockStoredObject(),
                           new BooleanCallback() {

                               @Override
                               public void execute(Boolean value) {
                                   if (value) {
                                       LockDigitalObjectWindow.setInstanceOf(lang, ts, dispatcher, eventBus);
                                   }
                               }
                           });
                } else {
                    EditorSC.operationFailed(lang, null);
                }
            }

            @Override
            public void callbackError(final Throwable cause) {
                Log.error("Store Handle Failure:", cause);
                super.callbackError(cause, lang.operationFailed());
            }
        };
        dispatcher.execute(storedAction, storedCallback);
    }
}
