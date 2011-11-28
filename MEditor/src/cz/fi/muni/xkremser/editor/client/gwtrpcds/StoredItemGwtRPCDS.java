/*
 * Metadata Editor
 * @author Matous Jobanek
 * 
 * 
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

package cz.fi.muni.xkremser.editor.client.gwtrpcds;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.shared.rpc.StoredItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.StoredItemsAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.StoredItemsResult;

// TODO: Auto-generated Javadoc
/**
 * The Class StoredItemGwtRPCDS.
 */
public class StoredItemGwtRPCDS
        extends AbstractGwtRPCDS {

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    private final LangConstants lang;

    /**
     * Instantiates a new stored tree gwt rpcds.
     * 
     * @param dispatcher
     *        the dispatcher
     */
    public StoredItemGwtRPCDS(DispatchAsync dispatcher, LangConstants lang) {
        this.dispatcher = dispatcher;
        this.lang = lang;
        DataSourceField field;

        field = new DataSourceTextField(Constants.ATTR_FILE_NAME, "fileName");
        field.setPrimaryKey(true);
        field.setRequired(true);
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_UUID, "PID");
        field.setRequired(true);
        field.setHidden(true);
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_NAME, lang.name());
        field.setRequired(true);
        field.setHidden(true);
        addField(field);
        field = new DataSourceDateField(Constants.ATTR_STORED, "stored");
        field.setRequired(true);
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_DESC, "description");
        field.setHidden(true);
        field.setRequired(true);
        addField(field);
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.gwtrpcds.AbstractGwtRPCDS#executeFetch
     * (java.lang.String, com.smartgwt.client.data.DSRequest,
     * com.smartgwt.client.data.DSResponse)
     */
    @Override
    protected void executeFetch(final String requestId, final DSRequest request, final DSResponse response) {

        dispatcher.execute(new StoredItemsAction(null), new DispatchCallback<StoredItemsResult>() {

            @Override
            public void callbackError(final Throwable cause) {
                Log.error("Handle Failure:", cause);
                response.setStatus(RPCResponse.STATUS_FAILURE);
                super.callbackError(cause, lang.attemptingError());
            }

            @Override
            public void callback(StoredItemsResult result) {
                List<StoredItem> items = result.getStoredItems();
                ListGridRecord[] list = new ListGridRecord[items.size()];
                for (int i = 0; i < items.size(); i++) {
                    ListGridRecord record = new ListGridRecord();
                    copyValues(items.get(i), record);
                    list[i] = record;
                }
                response.setData(list);
                response.setTotalRows(items.size());
                processResponse(requestId, response);
            }

        });
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.gwtrpcds.AbstractGwtRPCDS#executeAdd(
     * java.lang.String, com.smartgwt.client.data.DSRequest,
     * com.smartgwt.client.data.DSResponse)
     */
    @Override
    protected void executeAdd(final String requestId, final DSRequest request, final DSResponse response) {
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.gwtrpcds.AbstractGwtRPCDS#executeUpdate
     * (java.lang.String, com.smartgwt.client.data.DSRequest,
     * com.smartgwt.client.data.DSResponse)
     */
    @Override
    protected void executeUpdate(final String requestId, final DSRequest request, final DSResponse response) {
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.gwtrpcds.AbstractGwtRPCDS#executeRemove
     * (java.lang.String, com.smartgwt.client.data.DSRequest,
     * com.smartgwt.client.data.DSResponse)
     */
    @Override
    protected void executeRemove(final String requestId, final DSRequest request, final DSResponse response) {
    }

    //    /**
    //     * Copy values.
    //     * 
    //     * @param from
    //     *        the from
    //     * @param to
    //     *        the to
    //     */
    //    private static void copyValues(ListGridRecord from, StoredItems to) {
    //        to.setName(from.getAttributeAsString(Constants.ATTR_FILE_NAME));
    //        to.setStoredDate(from.getAttributeAsString(Constants.ATTR_STORED));
    //        //        to.setName(from.getAttributeAsString(Constants.ATTR_NAME));
    //        to.setUuid(from.getAttributeAsString(Constants.ATTR_UUID));
    //        to.setDescription(from.getAttributeAsString(Constants.ATTR_DESC));
    //    }

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

    //    /**
    //     * Gets the edited record.
    //     * 
    //     * @param request
    //     *        the request
    //     * @return the edited record
    //     */
    //    private ListGridRecord getEditedRecord(DSRequest request) {
    //        // Retrieving values before edit
    //        JavaScriptObject oldValues = request.getAttributeAsJavaScriptObject("oldValues");
    //        // Creating new record for combining old values with changes
    //        ListGridRecord newRecord = new ListGridRecord();
    //        // Copying properties from old record
    //        JSOHelper.apply(oldValues, newRecord.getJsObj());
    //        // Retrieving changed values
    //        JavaScriptObject data = request.getData();
    //        // Apply changes
    //        JSOHelper.apply(data, newRecord.getJsObj());
    //        return newRecord;
    //    }
}
