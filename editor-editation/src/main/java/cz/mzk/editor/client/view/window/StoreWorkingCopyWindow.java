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

package cz.mzk.editor.client.view.window;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.HtmlCode;
import cz.mzk.editor.client.view.other.EditorTabSet;
import cz.mzk.editor.client.view.other.StoredWorkingCopyGrid;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.event.OpenDigitalObjectEvent;
import cz.mzk.editor.shared.event.OpenFirstDigitalObjectEvent;
import cz.mzk.editor.shared.rpc.DigitalObjectDetail;
import cz.mzk.editor.shared.rpc.StoredItem;
import cz.mzk.editor.shared.rpc.action.StoredItemsAction;
import cz.mzk.editor.shared.rpc.action.StoredItemsResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class StoreWorkingCopyWindow
        extends UniversalWindow {

    private static LangConstants lang;
    private static StoreWorkingCopyWindow storingWindow = null;
    private static StoredWorkingCopyGrid storedWorkingCopyGrid;
    private static Button openButton;
    private static VLayout storeLayout;
    private static DispatchAsync dispatcher;
    private static EventBus eventBus;
    private static HLayout buttonsLayout;
    private static boolean modifyIsVisible;

    public static void setInstanceOf(final LangConstants lang, DispatchAsync dispatcher, EventBus eventBus) {
        if (isInstanceVisible()) {
            closeInstantiatedWindow();
        }
        storingWindow = new StoreWorkingCopyWindow(lang, dispatcher, eventBus);
    }

    public static boolean isInstanceVisible() {
        return (storingWindow != null && storingWindow.isCreated());
    }

    public static void closeInstantiatedWindow() {
        storingWindow.hide();
        storingWindow = null;
    }

    @SuppressWarnings("static-access")
    private StoreWorkingCopyWindow(final LangConstants lang,
                                   final DispatchAsync dispatcher,
                                   final EventBus eventBus) {
        super(450, 550, lang.save(), eventBus, 40);
        this.lang = lang;
        this.dispatcher = dispatcher;
        this.eventBus = eventBus;
        modifyIsVisible = false;
        storeLayout = new VLayout();
        storedWorkingCopyGrid = new StoredWorkingCopyGrid(lang, dispatcher, false);

        ModalWindow modalWindow = new ModalWindow(storedWorkingCopyGrid);
        modalWindow.setLoadingIcon("loadingAnimation.gif");
        modalWindow.show(true);

        buttonsLayout = new HLayout(2);
        buttonsLayout.setAutoWidth();
        openButton = new Button(lang.open());
        openButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                open();
            }
        });
        openButton.setExtraSpace(8);
        buttonsLayout.addMember(openButton);

        Button closeButton = new Button(lang.close());
        closeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
        buttonsLayout.addMember(closeButton);

        storedWorkingCopyGrid.addCellContextClickHandler(new CellContextClickHandler() {

            @Override
            public void onCellContextClick(CellContextClickEvent event) {
                Menu menu = new Menu();
                menu.setShowShadow(true);
                menu.setShadowDepth(10);

                menu.addItem(getOpenItem(eventBus));
                menu.addItem(getDeleteItem(storedWorkingCopyGrid, dispatcher));
                setContextMenu(menu);
            }

        });

        addItem(storeLayout);
        addItem(storedWorkingCopyGrid);
        addItem(buttonsLayout);

        setEdgeOffset(20);
        modalWindow.hide();
        storedWorkingCopyGrid.setData();
        centerInPage();
        show();
        focus();
        buttonsLayout.setLeft(280);

    }

    public static void setLabelsFieldsButtons(final DigitalObjectDetail detail, final EditorTabSet ts) {
        modifyIsVisible = true;
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

        String fileName =
                (detail.getUuid() + "_" + detail.getLabel()).replaceAll("[\\\\/:*?\\\"\\\'<>|\\[\\](){}%]",
                                                                        "");;

        storedWorkingCopyGrid.addCellClickHandler(new CellClickHandler() {

            @Override
            public void onCellClick(CellClickEvent event) {
                fileNameItem.setValue(event.getRecord().getAttribute(Constants.ATTR_NAME));
            }

        });

        fileNameItem.setDefaultValue(fileName);
        DynamicForm saveForm = new DynamicForm();
        saveForm.setItems(fileNameItem);

        HTMLFlow illegalCharsLabel = new HTMLFlow();
        StringBuffer sb = new StringBuffer("");
        for (String s : Constants.ILLEGAL_CHARACTERS) {
            sb.append(", " + s);
        }
        illegalCharsLabel.setContents(HtmlCode.italic("\t" + lang.illegalCharacters() + ": ")
                + sb.toString().substring(1));

        illegalCharsLabel.setExtraSpace(12);

        buttonsLayout.removeMember(openButton);
        Button storeButton = new Button(lang.save());
        storeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final String fileName = fileNameItem.getValueAsString();
                if (!containsIllegalCharacter(fileName)) {

                    Record[] records = storedWorkingCopyGrid.getRecords();
                    boolean nameIsSame = false;

                    for (Record record : records) {
                        if (record.getAttributeAsString(Constants.ATTR_NAME).equals(fileName)) {

                            SC.ask(lang.fileExists(), new BooleanCallback() {

                                @Override
                                public void execute(Boolean value) {
                                    if (value) {
                                        store(detail, dispatcher, ts, eventBus, fileName, "desc");
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
                        store(detail, dispatcher, ts, eventBus, fileName, "desc");
                        closeInstantiatedWindow();
                    }
                } else {
                    SC.warn(lang.correctFileName());
                }
            }
        });
        buttonsLayout.addMember(storeButton, 0);
        storeLayout.addMember(fileNameLabel);
        storeLayout.addMember(saveForm);
        storeLayout.addMember(illegalCharsLabel);
        storeLayout.addMember(storedLabel);
        fileNameItem.selectValue();
    }

    private static boolean containsIllegalCharacter(String string) {
        for (String s : Constants.ILLEGAL_CHARACTERS) {
            if (string.contains(s)) return true;
        }
        return false;
    }

    private MenuItem getOpenItem(final EventBus eventBus) {
        MenuItem openItem = new MenuItem(lang.open(), "icons/16/document_plain_new.png");

        openItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                open();
            }
        });
        return openItem;
    }

    private static void open() {
        ListGridRecord record = storedWorkingCopyGrid.getSelectedRecord();
        if (record != null) {
            String fileName = record.getAttributeAsString(Constants.ATTR_FILE_NAME);
            DigitalObjectModel model = (DigitalObjectModel) record.getAttributeAsObject(Constants.ATTR_MODEL);
            String uuid = record.getAttributeAsString(Constants.ATTR_UUID);
            Long id = record.getAttributeAsLong(Constants.ATTR_ID);
            StoredItem storedItem = new StoredItem(id, fileName, uuid, model, null, null);
            if (modifyIsVisible) {
                eventBus.fireEvent(new OpenDigitalObjectEvent(uuid, storedItem));
            } else {
                eventBus.fireEvent(new OpenFirstDigitalObjectEvent(uuid, storedItem));
            }
        } else {
            SC.warn(lang.nothingSelected());
        }
    }

    private MenuItem getDeleteItem(final ListGrid storedFilesGrid, final DispatchAsync dispatcher) {
        MenuItem deleteItem = new MenuItem(lang.removeItem(), "icons/16/close.png");

        deleteItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                ListGridRecord record = storedFilesGrid.getSelectedRecord();
                storedWorkingCopyGrid.deleteItem(record.getAttributeAsLong(Constants.ATTR_ID),
                                                 record.getAttributeAsString(Constants.ATTR_FILE_NAME));

            }
        });
        return deleteItem;
    }

    private static void store(DigitalObjectDetail detail,
                              final DispatchAsync dispatcher,
                              final EditorTabSet ts,
                              final EventBus eventBus,
                              String fileName,
                              String description) {

        StoredItem storedItem =
                new StoredItem(fileName, detail.getUuid(), detail.getModel(), description, null);

        StoredItemsAction storedAction = new StoredItemsAction(detail, storedItem, Constants.VERB.PUT);
        DispatchCallback<StoredItemsResult> storedCallback = new DispatchCallback<StoredItemsResult>() {

            @Override
            public void callback(StoredItemsResult result) {
                if (result.getStoredItems() != null) {
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

    /**
     * @param id
     * @param fileName
     * @param dispatcher
     */
    public static void deleteItem(Long id, String fileName, DispatchAsync dispatcher) {
        storedWorkingCopyGrid.setDispatcher(dispatcher);
        storedWorkingCopyGrid.deleteItem(id, fileName);
    }
}
