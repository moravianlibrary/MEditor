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

package cz.mzk.editor.client.view.other;

import java.util.ArrayList;
import java.util.List;

import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.view.window.ModalWindow;
import cz.mzk.editor.shared.rpc.StoredItem;
import cz.mzk.editor.shared.rpc.action.GetAllStoredWorkingCopyItemsAction;
import cz.mzk.editor.shared.rpc.action.GetAllStoredWorkingCopyItemsResult;
import cz.mzk.editor.shared.rpc.action.RemoveStoredWorkingCopyItemsAction;
import cz.mzk.editor.shared.rpc.action.RemoveStoredWorkingCopyItemsResult;

/**
 * @author Matous Jobanek
 * @version Dec 3, 2012
 */
public class StoredWorkingCopyGrid
        extends ListGrid {

    private DispatchAsync dispatcher;
    private LangConstants lang;
    private Long editorUserId;
    private boolean showMore;

    /**
     * 
     */
    public StoredWorkingCopyGrid(final LangConstants lang, final DispatchAsync dispatcher, boolean showMore) {
        super();
        this.lang = lang;
        this.dispatcher = dispatcher;
        this.showMore = showMore;

        setWidth("95%");
        setHeight("80%");
        setShowSortArrow(SortArrow.CORNER);
        setShowAllRecords(true);
        setCanHover(true);
        setHoverOpacity(75);
        setHoverStyle("interactImageHover");
        setExtraSpace(20);
        setSelectionType(SelectionStyle.SINGLE);

        setHoverWidth(300);
        setHoverCustomizer(new HoverCustomizer() {

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

        ListGridField fileNameField = new ListGridField(Constants.ATTR_NAME, lang.name());
        ListGridField storedField = new ListGridField(Constants.ATTR_STORED, lang.stored());

        if (!showMore) {
            storedField.setWidth(120);
            setFields(fileNameField, storedField);
        } else {
            ListGridField modelField = new ListGridField(Constants.ATTR_MODEL, lang.dcType());
            ListGridField uuidField = new ListGridField(Constants.ATTR_UUID, "PID");
            ListGridField descField = new ListGridField(Constants.ATTR_DESC, lang.description());
            setFields(fileNameField, storedField, uuidField, modelField, descField);
        }

        setData();
    }

    public void deleteItem(Long id, String fileName) {
        final ModalWindow modalWindow = new ModalWindow(this);
        modalWindow.setLoadingIcon("loadingAnimation.gif");
        modalWindow.show(true);

        ArrayList<StoredItem> items = new ArrayList<StoredItem>();
        items.add(new StoredItem(id, fileName));
        dispatcher.execute(new RemoveStoredWorkingCopyItemsAction(items),
                           new DispatchCallback<RemoveStoredWorkingCopyItemsResult>() {

                               @Override
                               public void callback(RemoveStoredWorkingCopyItemsResult result) {
                                   if (!result.isSuccessful()) {
                                       SC.warn(lang.operationFailed());
                                   }
                                   modalWindow.hide();
                                   setData();
                               }

                               @Override
                               public void callbackError(final Throwable cause) {
                                   super.callbackError(cause);
                                   modalWindow.hide();
                               }

                           });
    }

    public void setData() {
        final ModalWindow modalWindow = new ModalWindow(this);
        modalWindow.setLoadingIcon("loadingAnimation.gif");
        modalWindow.show(true);

        dispatcher.execute(new GetAllStoredWorkingCopyItemsAction(editorUserId),
                           new DispatchCallback<GetAllStoredWorkingCopyItemsResult>() {

                               @Override
                               public void callbackError(final Throwable cause) {
                                   super.callbackError(cause);
                                   modalWindow.hide();
                               }

                               @Override
                               public void callback(GetAllStoredWorkingCopyItemsResult result) {

                                   List<StoredItem> items = result.getStoredItems();
                                   ListGridRecord[] records = new ListGridRecord[items.size()];
                                   for (int i = 0; i < items.size(); i++) {
                                       ListGridRecord record = new ListGridRecord();
                                       copyValues(items.get(i), record);
                                       records[i] = record;
                                   }
                                   setData(records);
                                   sort(Constants.ATTR_STORED, SortDirection.ASCENDING);
                                   selectRecord(0);
                                   scrollToRow(0);
                                   modalWindow.hide();
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
        String fileName = from.getFileName();
        to.setAttribute(Constants.ATTR_NAME, fileName.substring(fileName.lastIndexOf("/") + 1));
        to.setAttribute(Constants.ATTR_FILE_NAME, fileName);
        to.setAttribute(Constants.ATTR_STORED, from.getStoredDate());
        to.setAttribute(Constants.ATTR_MODEL, (from.getModel() == null) ? "" : from.getModel().getValue());
        to.setAttribute(Constants.ATTR_UUID, from.getUuid());
        to.setAttribute(Constants.ATTR_DESC, from.getDescription());
        to.setAttribute(Constants.ATTR_ID, from.getId());
    }

    /**
     * @return the editorUserId
     */
    public Long getEditorUserId() {
        return editorUserId;
    }

    /**
     * @param editorUserId
     *        the editorUserId to set
     */
    public void setEditorUserId(Long editorUserId) {
        this.editorUserId = editorUserId;
    }

    /**
     * @param dispatcher
     *        the dispatcher to set
     */
    public void setDispatcher(DispatchAsync dispatcher) {
        this.dispatcher = dispatcher;
    }

}
