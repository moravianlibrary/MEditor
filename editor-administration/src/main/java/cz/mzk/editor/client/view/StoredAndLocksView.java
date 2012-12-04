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

package cz.mzk.editor.client.view;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.presenter.StoredAndLocksPresenter;
import cz.mzk.editor.client.uihandlers.StoredAndLocksUiHandlers;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.HtmlCode;
import cz.mzk.editor.client.view.other.StoredWorkingCopyGrid;
import cz.mzk.editor.client.view.other.UserSelect;
import cz.mzk.editor.client.view.window.ModalWindow;
import cz.mzk.editor.shared.rpc.ActiveLockItem;
import cz.mzk.editor.shared.rpc.TreeStructureInfo;
import cz.mzk.editor.shared.rpc.action.GetAllLockItemsAction;
import cz.mzk.editor.shared.rpc.action.GetAllLockItemsResult;
import cz.mzk.editor.shared.rpc.action.GetAllStoredTreeStructureItemsAction;
import cz.mzk.editor.shared.rpc.action.GetAllStoredTreeStructureItemsResult;
import cz.mzk.editor.shared.rpc.action.RemoveStoredTreeStructureItemsAction;
import cz.mzk.editor.shared.rpc.action.RemoveStoredTreeStructureItemsResult;
import cz.mzk.editor.shared.rpc.action.UnlockDigitalObjectAction;
import cz.mzk.editor.shared.rpc.action.UnlockDigitalObjectResult;

/**
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public class StoredAndLocksView
        extends ViewWithUiHandlers<StoredAndLocksUiHandlers>
        implements StoredAndLocksPresenter.MyView {

    private abstract static class RemoveIButton
            extends IButton {

        /**
         * Instantiates a new removes the i button.
         */
        public RemoveIButton() {
            setTitle(lang.removeSelected());
            setShowTitle(true);
            setIcon("icons/16/remove.png");
            setShowRollOver(true);
            setShowDisabled(true);
            setShowDown(true);
            setWidth(200);
            setLayoutAlign(Alignment.RIGHT);
            addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    afterClick();
                }

            });
        }

        protected abstract void afterClick();
    }

    private final VStack mainLayout;
    private SelectItem users;
    private static LangConstants lang;
    private final DispatchAsync dispatcher;
    private StoredWorkingCopyGrid storedWorkingCopyGrid;
    private HTMLFlow filesTitle;
    private HTMLFlow treeTitle;
    private HTMLFlow locksTitle;
    private ListGrid storedStructures;
    private ListGrid activeLocks;
    private int count;

    @Inject
    public StoredAndLocksView(EventBus eventBus, final LangConstants lang, DispatchAsync dispatcher) {
        this.mainLayout = new VStack();
        this.lang = lang;
        this.dispatcher = dispatcher;

        setUserSelect();

        HLayout hLayout = new HLayout(2);
        hLayout.setHeight("90%");

        VLayout worCopyLocksLayout = new VLayout();

        worCopyLocksLayout.addMember(getStoredWorkingCopyLayout());
        worCopyLocksLayout.addMember(getActiveLocksLayout());

        hLayout.addMember(worCopyLocksLayout);
        hLayout.addMember(getStoredTreeLayout());

        mainLayout.addMember(hLayout);

    }

    private void setUserSelect() {
        users = new UserSelect(lang.users(), dispatcher);
        users.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                storedWorkingCopyGrid.setEditorUserId(Long.parseLong(event.getValue().toString()));
                storedWorkingCopyGrid.setData();
                filesTitle.setContents(HtmlCode.title(lang.storedFiles(), 3));

                setTreeStrucData();
                treeTitle.setContents(HtmlCode.title(lang.storedStructures(), 3));

                setLocksData();
                locksTitle.setContents(HtmlCode.title(lang.locks(), 3));
            }
        });
        DynamicForm usersForm = new DynamicForm();
        usersForm.setItems(users);

        mainLayout.addMember(usersForm);
    }

    private VLayout getActiveLocksLayout() {
        VLayout locksLayout = new VLayout();
        locksLayout.setMargin(10);

        activeLocks = new ListGrid();
        activeLocks.setShowAllRecords(true);
        activeLocks.setAutoFetchData(false);
        activeLocks.setShowSortArrow(SortArrow.CORNER);
        activeLocks.setShowAllRecords(true);
        activeLocks.setCanHover(true);
        activeLocks.setHoverOpacity(75);
        activeLocks.setHoverStyle("interactImageHover");
        activeLocks.setExtraSpace(20);
        activeLocks.setSelectionType(SelectionStyle.SINGLE);
        activeLocks.setHoverWidth(300);

        locksLayout.setWidth100();
        locksLayout.setHeight("45%");
        locksLayout.setLayoutAlign(Alignment.CENTER);

        ListGridField nameField = new ListGridField(Constants.ATTR_NAME, lang.title());
        ListGridField uuidField = new ListGridField(Constants.ATTR_UUID, "PID");
        ListGridField dateField = new ListGridField(Constants.ATTR_DATE, lang.date());
        ListGridField descField = new ListGridField(Constants.ATTR_DESC, lang.description());
        ListGridField modelField = new ListGridField(Constants.ATTR_MODEL, lang.dcType());
        //        ListGridField timeToExpField = new ListGridField(Constants.ATTR_TIME_TO_EXP_LOCK, lang.lockExpires());

        activeLocks.setFields(nameField, uuidField, dateField, descField, modelField);

        locksTitle = new HTMLFlow(HtmlCode.title(lang.my() + " " + lang.locks().toLowerCase(), 3));

        locksLayout.addMember(locksTitle);
        locksLayout.addMember(getRemoveLockButton());
        locksLayout.addMember(activeLocks);

        setLocksData();

        return locksLayout;
    }

    private void setLocksData() {
        GetAllLockItemsAction lockItemsAction;
        if (users.getValue() != null && !"".equals(users.getValue())) {
            lockItemsAction = new GetAllLockItemsAction(Long.parseLong(users.getValue().toString()));
        } else {
            lockItemsAction = new GetAllLockItemsAction(null);
        }

        final ModalWindow mw = new ModalWindow(activeLocks);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        dispatcher.execute(lockItemsAction, new DispatchCallback<GetAllLockItemsResult>() {

            @Override
            public void callback(GetAllLockItemsResult result) {
                if (result.getItems() != null) {
                    activeLocks.setData(copyLockValues(result.getItems()));
                } else {
                    SC.warn(lang.operationFailed());
                }
                mw.hide();
            }

            @Override
            public void callbackError(final Throwable cause) {
                super.callbackError(cause);
                mw.hide();
            }
        });

    }

    private RemoveIButton getRemoveLockButton() {
        return new RemoveIButton() {

            @Override
            protected void afterClick() {
                if (activeLocks.getSelectedRecords().length == 0) return;
                final ModalWindow mw = new ModalWindow(activeLocks);
                mw.setLoadingIcon("loadingAnimation.gif");
                mw.show(true);
                count = activeLocks.getSelectedRecords().length;
                for (ListGridRecord rec : activeLocks.getSelectedRecords()) {
                    UnlockDigitalObjectAction unlockObjectAction =
                            new UnlockDigitalObjectAction(rec.getAttributeAsString(Constants.ATTR_UUID));
                    dispatcher.execute(unlockObjectAction, new DispatchCallback<UnlockDigitalObjectResult>() {

                        @Override
                        public void callback(UnlockDigitalObjectResult result) {
                            if (count == 1) {
                                activeLocks.removeSelectedData();
                                mw.hide();
                            } else {
                                count--;
                            }
                        }

                        @Override
                        public void callbackError(final Throwable cause) {
                            super.callbackError(cause);
                            if (count == 1) {
                                mw.hide();
                            } else {
                                count--;
                            }
                        }
                    });
                }

            }
        };
    }

    private VLayout getStoredWorkingCopyLayout() {
        VLayout storedWorCopyLayout = new VLayout();
        storedWorCopyLayout.setWidth("100%");
        storedWorCopyLayout.setMargin(10);
        storedWorCopyLayout.setHeight("50%");

        storedWorkingCopyGrid = new StoredWorkingCopyGrid(lang, dispatcher, true);
        storedWorkingCopyGrid.setHeight100();
        storedWorkingCopyGrid.setWidth100();

        RemoveIButton removeIButton = new RemoveIButton() {

            @Override
            protected void afterClick() {
                if (storedWorkingCopyGrid.getSelectedRecords().length == 0) return;
                for (ListGridRecord rec : storedWorkingCopyGrid.getSelectedRecords()) {
                    storedWorkingCopyGrid.deleteItem(Long.parseLong(rec
                            .getAttributeAsString(Constants.ATTR_ID)), rec
                            .getAttributeAsString(Constants.ATTR_FILE_NAME));
                }
            }
        };

        filesTitle = new HTMLFlow(HtmlCode.title(lang.my() + " " + lang.storedFiles().toLowerCase(), 3));

        storedWorCopyLayout.addMember(filesTitle);
        storedWorCopyLayout.addMember(removeIButton);
        storedWorCopyLayout.addMember(storedWorkingCopyGrid);

        return storedWorCopyLayout;
    }

    private VLayout getStoredTreeLayout() {
        VLayout treeStrucLayout = new VLayout();
        treeStrucLayout.setMargin(10);
        treeStrucLayout.setWidth("*");

        storedStructures = new ListGrid();
        storedStructures.setShowAllRecords(true);
        storedStructures.setAutoFetchData(false);
        storedStructures.setWidth100();
        storedStructures.setShowSortArrow(SortArrow.CORNER);
        storedStructures.setShowAllRecords(true);
        storedStructures.setCanHover(true);
        storedStructures.setHoverOpacity(75);
        storedStructures.setHoverStyle("interactImageHover");
        storedStructures.setExtraSpace(20);
        storedStructures.setSelectionType(SelectionStyle.SINGLE);
        storedStructures.setHoverWidth(300);

        ListGridField dateField = new ListGridField(Constants.ATTR_DATE, lang.date());
        ListGridField nameField = new ListGridField(Constants.ATTR_NAME, lang.name());
        ListGridField modelField = new ListGridField(Constants.ATTR_MODEL, lang.dcType());
        ListGridField descField = new ListGridField(Constants.ATTR_DESC, lang.description());
        ListGridField barcodeField = new ListGridField(Constants.ATTR_BARCODE, lang.fbarcode());

        storedStructures.setFields(dateField, nameField, modelField, descField, barcodeField);
        setTreeStrucData();

        treeTitle = new HTMLFlow(HtmlCode.title(lang.my() + " " + lang.storedStructures().toLowerCase(), 3));

        treeStrucLayout.addMember(treeTitle);
        treeStrucLayout.addMember(getRemoveStrucButton());
        treeStrucLayout.addMember(storedStructures);
        return treeStrucLayout;
    }

    private RemoveIButton getRemoveStrucButton() {
        return new RemoveIButton() {

            @Override
            protected void afterClick() {
                if (storedStructures.getSelectedRecords().length == 0) return;
                ArrayList<Long> itemsId = new ArrayList<Long>(storedStructures.getSelectedRecords().length);
                for (ListGridRecord rec : storedStructures.getSelectedRecords()) {
                    itemsId.add(Long.parseLong(rec.getAttributeAsString(Constants.ATTR_ID)));
                }
                final ModalWindow mw = new ModalWindow(storedStructures);
                mw.setLoadingIcon("loadingAnimation.gif");
                mw.show(true);

                RemoveStoredTreeStructureItemsAction removeStructureItems =
                        new RemoveStoredTreeStructureItemsAction(itemsId);
                dispatcher.execute(removeStructureItems,
                                   new DispatchCallback<RemoveStoredTreeStructureItemsResult>() {

                                       @Override
                                       public void callback(RemoveStoredTreeStructureItemsResult result) {
                                           if (result.isSuccessful()) {
                                               storedStructures.removeSelectedData();
                                           } else {
                                               SC.warn(lang.operationSuccessful());
                                           }
                                           mw.hide();
                                       }

                                       @Override
                                       public void callbackError(final Throwable cause) {
                                           super.callbackError(cause);
                                           mw.hide();
                                       }
                                   });
            }
        };
    }

    private void setTreeStrucData() {
        GetAllStoredTreeStructureItemsAction getTreeStructuresAction;
        if (users.getValue() != null && !"".equals(users.getValue())) {
            getTreeStructuresAction =
                    new GetAllStoredTreeStructureItemsAction(Long.parseLong(users.getValue().toString()));
        } else {
            getTreeStructuresAction = new GetAllStoredTreeStructureItemsAction(null);
        }

        final ModalWindow mw = new ModalWindow(storedStructures);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        dispatcher.execute(getTreeStructuresAction,
                           new DispatchCallback<GetAllStoredTreeStructureItemsResult>() {

                               @Override
                               public void callback(GetAllStoredTreeStructureItemsResult result) {
                                   if (result.getItems() != null) {
                                       storedStructures.setData(copyStrucValues(result.getItems()));
                                   } else {
                                       SC.warn(lang.operationFailed());
                                   }
                                   mw.hide();
                               }

                               @Override
                               public void callbackError(final Throwable cause) {
                                   super.callbackError(cause);
                                   mw.hide();
                               }
                           });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Widget asWidget() {
        return mainLayout;
    }

    private ListGridRecord[] copyStrucValues(List<TreeStructureInfo> items) {
        ListGridRecord[] records = new ListGridRecord[items.size()];
        int i = 0;
        for (TreeStructureInfo item : items) {
            records[i] = new ListGridRecord();
            records[i].setAttribute(Constants.ATTR_ID, item.getId());
            records[i].setAttribute(Constants.ATTR_DATE, item.getCreated());
            records[i].setAttribute(Constants.ATTR_DESC, item.getDescription());
            records[i].setAttribute(Constants.ATTR_BARCODE, item.getBarcode());
            records[i].setAttribute(Constants.ATTR_NAME, item.getName());
            records[i].setAttribute(Constants.ATTR_OWNER, item.getOwner());
            records[i].setAttribute(Constants.ATTR_INPUT_PATH, item.getInputPath());
            records[i].setAttribute(Constants.ATTR_MODEL, item.getModel());
            i++;
        }
        return records;
    }

    private ListGridRecord[] copyLockValues(List<ActiveLockItem> items) {
        ListGridRecord[] records = new ListGridRecord[items.size()];
        int i = 0;
        for (ActiveLockItem item : items) {
            records[i] = new ListGridRecord();
            records[i].setAttribute(Constants.ATTR_UUID, item.getUuid());
            records[i].setAttribute(Constants.ATTR_DATE, item.getDate());
            records[i].setAttribute(Constants.ATTR_DESC, item.getDescription());
            records[i].setAttribute(Constants.ATTR_NAME, item.getObjectName());
            records[i].setAttribute(Constants.ATTR_MODEL, item.getModel());
            i++;
        }
        return records;
    }
}
