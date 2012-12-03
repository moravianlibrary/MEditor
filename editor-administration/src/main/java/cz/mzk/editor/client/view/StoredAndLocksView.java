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

import java.util.List;

import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLFlow;
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
import cz.mzk.editor.shared.rpc.TreeStructureInfo;
import cz.mzk.editor.shared.rpc.action.GetAllStoredTreeStructureItemsAction;
import cz.mzk.editor.shared.rpc.action.GetAllStoredTreeStructureItemsResult;

/**
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public class StoredAndLocksView
        extends ViewWithUiHandlers<StoredAndLocksUiHandlers>
        implements StoredAndLocksPresenter.MyView {

    private final VStack mainLayout;
    private SelectItem users;
    private final LangConstants lang;
    private final DispatchAsync dispatcher;
    private StoredWorkingCopyGrid storedWorkingCopyGrid;
    private HTMLFlow filesTitle;
    private HTMLFlow treeTitle;
    private ListGrid storedStructures;

    @Inject
    public StoredAndLocksView(EventBus eventBus, final LangConstants lang, DispatchAsync dispatcher) {
        this.mainLayout = new VStack();
        this.lang = lang;
        this.dispatcher = dispatcher;

        setUserSelect();

        HLayout storedLayout = new HLayout(2);
        storedLayout.setHeight("50%");

        storedLayout.addMember(getStoredWorkingCopyLayout());
        storedLayout.addMember(getStoredTreeLayout());

        mainLayout.addMember(storedLayout);

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
                filesTitle.setContents(HtmlCode.title(lang.storedStructures(), 3));
            }
        });
        DynamicForm usersForm = new DynamicForm();
        usersForm.setItems(users);

        mainLayout.addMember(usersForm);
    }

    private VLayout getStoredWorkingCopyLayout() {
        VLayout storedWorCopyLayout = new VLayout();
        storedWorCopyLayout.setWidth("50%");
        storedWorCopyLayout.setMargin(10);

        storedWorkingCopyGrid = new StoredWorkingCopyGrid(lang, dispatcher, true);
        storedWorkingCopyGrid.setHeight100();
        storedWorkingCopyGrid.setWidth100();
        filesTitle = new HTMLFlow(HtmlCode.title(lang.my() + " " + lang.storedFiles().toLowerCase(), 3));

        storedWorCopyLayout.addMember(filesTitle);
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
        storedStructures.setHeight100();
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
        treeStrucLayout.addMember(storedStructures);
        return treeStrucLayout;
    }

    private void setTreeStrucData() {
        GetAllStoredTreeStructureItemsAction getTreeStructuresAction;
        if (users.getValue() != null && !"".equals(users.getValue())) {
            getTreeStructuresAction =
                    new GetAllStoredTreeStructureItemsAction(Long.parseLong(users.getValue().toString()));
        } else {
            getTreeStructuresAction = new GetAllStoredTreeStructureItemsAction(null);
        }

        dispatcher.execute(getTreeStructuresAction,
                           new DispatchCallback<GetAllStoredTreeStructureItemsResult>() {

                               @Override
                               public void callback(GetAllStoredTreeStructureItemsResult result) {
                                   if (result.getItems() != null) {
                                       storedStructures.setData(copyValues(result.getItems()));
                                   } else {
                                       SC.warn(lang.operationFailed());
                                   }
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

    private ListGridRecord[] copyValues(List<TreeStructureInfo> items) {
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
}
