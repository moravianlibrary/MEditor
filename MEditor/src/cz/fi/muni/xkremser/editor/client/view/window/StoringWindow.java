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

import com.allen_sauer.gwt.log.client.Log;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.gwtrpcds.StoredTreeGwtRPCDS;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.shared.rpc.DigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.rpc.action.StoredFilesAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.StoredFilesResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class StoringWindow
        extends Window {

    private final LangConstants lang;
    private static StoringWindow storingWindow = null;

    public static void setInstanceOf(DigitalObjectDetail detail,
                                     final LangConstants lang,
                                     DispatchAsync dispatcher) {
        if (storingWindow != null) {
            storingWindow = null;
        }
        storingWindow = new StoringWindow(detail, lang, dispatcher);
    }

    public static StoringWindow getInstanceOf() {
        return storingWindow;
    }

    public static void setInstanceAsNull() {
        storingWindow = null;
    }

    private StoringWindow(final DigitalObjectDetail detail,
                          final LangConstants lang,
                          final DispatchAsync dispatcher) {
        this.lang = lang;
        setHeight(450);
        setWidth(550);
        setCanDragResize(true);
        setShowEdges(true);
        setTitle(lang.save() + ": " + detail.getUuid() + " " + lang.name() + ": " + detail.getLabel());
        setShowMinimizeButton(false);
        setIsModal(true);
        setShowModalMask(true);
        setEdgeOffset(20);
        addCloseClickHandler(new CloseClickHandler() {

            @Override
            public void onCloseClick(CloseClientEvent event) {
                destroy();
            }
        });
        final ListGrid storedFilesGrid;
        storedFilesGrid = new ListGrid();
        HTMLFlow fileNameLabel = new HTMLFlow();
        fileNameLabel.setContents("<h3>" + lang.fileNameLabel() + ": </h3>");
        fileNameLabel.setAutoHeight();
        fileNameLabel.setMargin(10);
        HTMLFlow storedLabel = new HTMLFlow();
        storedLabel.setContents("<h4>" + lang.storedFiles() + ": </h4>");
        storedLabel.setAutoHeight();
        storedLabel.setMargin(10);

        final TextItem fileName = new TextItem();
        fileName.setTitle(lang.fileName());
        fileName.setWidth(350);

        fileName.setDefaultValue(detail.getUuid() + "_" + detail.getLabel());
        DynamicForm saveForm = new DynamicForm();
        saveForm.setItems(fileName);
        saveForm.setExtraSpace(5);

        storedFilesGrid.setWidth(500);
        storedFilesGrid.setHeight(200);
        storedFilesGrid.setShowSortArrow(SortArrow.CORNER);
        storedFilesGrid.setShowAllRecords(true);
        storedFilesGrid.setCanHover(true);
        storedFilesGrid.setHoverOpacity(75);
        storedFilesGrid.setHoverStyle("interactImageHover");
        storedFilesGrid.setExtraSpace(20);

        storedFilesGrid.setDataSource(new StoredTreeGwtRPCDS(dispatcher, lang));
        storedFilesGrid.setAutoFetchData(true);
        storedFilesGrid.setHoverWidth(300);
        storedFilesGrid.setHoverCustomizer(new HoverCustomizer() {

            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                StringBuffer sb = new StringBuffer();
                sb.append(record.getAttribute(Constants.ATTR_UUID));
                sb.append("<br>").append(lang.name()).append(": ");
                sb.append(record.getAttribute(Constants.ATTR_NAME));
                sb.append("<br>").append(lang.description()).append(": ").append("<br>");
                sb.append(record.getAttribute(Constants.ATTR_DESC));
                return sb.toString();
            }
        });
        storedFilesGrid.addCellClickHandler(new CellClickHandler() {

            @Override
            public void onCellClick(CellClickEvent event) {
                fileName.setValue(event.getRecord().getAttribute(Constants.ATTR_FILE_NAME));
            }
        });
        ModalWindow modalWindow = new ModalWindow(storedFilesGrid);
        modalWindow.setLoadingIcon("loadingAnimation.gif");
        modalWindow.show(true);

        Button storeButton = new Button(lang.save());
        storeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Record[] records = storedFilesGrid.getRecords();
                boolean isSame = false;

                for (Record record : records) {
                    if (record.getAttributeAsString(Constants.ATTR_FILE_NAME).equals(fileName.getValue())) {

                        SC.ask(lang.fileExists(), new BooleanCallback() {

                            @Override
                            public void execute(Boolean value) {
                                if (value) {
                                    store(detail, dispatcher);
                                }
                            }
                        });
                        isSame = true;
                        break;
                    }
                }

                if (!isSame) {
                    store(detail, dispatcher);
                }
            }
        });

        addItem(fileNameLabel);
        addItem(saveForm);
        addItem(storedLabel);
        addItem(storedFilesGrid);
        addItem(storeButton);

        fetchStoredItems(storedFilesGrid);
        show();
        centerInPage();
        focus();
        storeButton.setLeft(400);
        modalWindow.destroy();
    }

    private void fetchStoredItems(final ListGrid storedFilesGrid) {
        ListGridField fileNameField = new ListGridField(Constants.ATTR_FILE_NAME, lang.name());
        ListGridField storedField = new ListGridField(Constants.ATTR_STORED, lang.stored());
        storedField.setWidth(120);
        storedFilesGrid.setFields(fileNameField, storedField);
        storedFilesGrid.getDataSource().fetchData(null, new DSCallback() {

            @Override
            public void execute(DSResponse response, Object rawData, DSRequest request) {
                storedFilesGrid.setData(response.getData());
                storedFilesGrid.sort(Constants.ATTR_STORED, SortDirection.ASCENDING);
                storedFilesGrid.selectRecord(0);
                storedFilesGrid.scrollToRow(0);
            }
        });
    }

    private void store(DigitalObjectDetail detail, DispatchAsync dispatcher) {

        StoredFilesAction storedAction = new StoredFilesAction(detail);
        DispatchCallback<StoredFilesResult> storedCallback = new DispatchCallback<StoredFilesResult>() {

            @Override
            public void callback(StoredFilesResult result) {
                EditorSC.operationSuccessful(lang);
            }

            @Override
            public void callbackError(final Throwable cause) {
                Log.error("Store Handle Failure:", cause);
                EditorSC.operationFailed(lang);
            }
        };
        dispatcher.execute(storedAction, storedCallback);
    }
}
