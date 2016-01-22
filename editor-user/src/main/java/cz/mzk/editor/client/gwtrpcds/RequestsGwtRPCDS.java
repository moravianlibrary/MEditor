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

package cz.mzk.editor.client.gwtrpcds;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.JavaScriptObject;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.rpc.RequestItem;
import cz.mzk.editor.shared.rpc.action.GetAllRequestItemsAction;
import cz.mzk.editor.shared.rpc.action.GetAllRequestItemsResult;
import cz.mzk.editor.shared.rpc.action.RemoveRequestItemAction;
import cz.mzk.editor.shared.rpc.action.RemoveRequestItemResult;

// TODO: Auto-generated Javadoc
/**
 * The Class RecentlyTreeGwtRPCDS.
 */
public class RequestsGwtRPCDS
        extends AbstractGwtRPCDS {

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /**
     * Instantiates a new recently tree gwt rpcds.
     * 
     * @param dispatcher
     *        the dispatcher
     */
    public RequestsGwtRPCDS(DispatchAsync dispatcher, LangConstants lang) {
        this.dispatcher = dispatcher;
        DataSourceField field;
        field = new DataSourceTextField(Constants.ATTR_NAME, lang.name());
        field.setRequired(true);
        field.setAttribute("width", "15%");
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_OBJECT, lang.identities());
        field.setRequired(true);
        field.setAttribute("width", "*");
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_DESC, lang.description());
        field.setRequired(true);
        field.setAttribute("width", "20%");
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_TIMESTAMP, lang.date());
        field.setRequired(true);
        field.setAttribute("width", "15%");
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_TYPE, "Type");
        field.setRequired(true);
        field.setAttribute("width", "15%");
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_GENERIC_ID, "requestId");
        field.setPrimaryKey(true);
        field.setHidden(true);
        field.setRequired(true);
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_USER_ID, "userId");
        field.setHidden(true);
        field.setRequired(true);
        addField(field);

    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.gwtrpcds.AbstractGwtRPCDS#executeFetch
     * (java.lang.String, com.smartgwt.client.data.DSRequest,
     * com.smartgwt.client.data.DSResponse)
     */
    @Override
    protected void executeFetch(final String requestId, final DSRequest request, final DSResponse response) {
        dispatcher.execute(new GetAllRequestItemsAction(), new DispatchCallback<GetAllRequestItemsResult>() {

            @Override
            public void callbackError(final Throwable cause) {
                Log.error("Handle Failure:", cause);
                response.setStatus(RPCResponse.STATUS_FAILURE);
            }

            @Override
            public void callback(final GetAllRequestItemsResult result) {
                ArrayList<RequestItem> items = result.getItems();
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
     * @see cz.mzk.editor.client.gwtrpcds.AbstractGwtRPCDS#executeAdd(
     * java.lang.String, com.smartgwt.client.data.DSRequest,
     * com.smartgwt.client.data.DSResponse)
     */
    @Override
    protected void executeAdd(final String requestId, final DSRequest request, final DSResponse response) {

    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.gwtrpcds.AbstractGwtRPCDS#executeUpdate
     * (java.lang.String, com.smartgwt.client.data.DSRequest,
     * com.smartgwt.client.data.DSResponse)
     */
    @Override
    protected void executeUpdate(final String requestId, final DSRequest request, final DSResponse response) {

    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.gwtrpcds.AbstractGwtRPCDS#executeRemove
     * (java.lang.String, com.smartgwt.client.data.DSRequest,
     * com.smartgwt.client.data.DSResponse)
     */
    @Override
    protected void executeRemove(final String requestId, final DSRequest request, final DSResponse response) {
        JavaScriptObject data = request.getData();
        final ListGridRecord rec = new ListGridRecord(data);
        final RequestItem testRec = new RequestItem();
        copyValues(rec, testRec);
        dispatcher.execute(new RemoveRequestItemAction(testRec.getId()),
                           new DispatchCallback<RemoveRequestItemResult>() {

                               @Override
                               public void callbackError(Throwable caught) {
                                   response.setStatus(RPCResponse.STATUS_FAILURE);
                                   processResponse(requestId, response);
                               }

                               @Override
                               public void callback(RemoveRequestItemResult result) {
                                   // if (!result.isFound()) {
                                   // ListGridRecord[] list = new ListGridRecord[1];
                                   // ListGridRecord updRec = new ListGridRecord();
                                   // copyValues(testRec, updRec);
                                   // list[0] = updRec;
                                   // response.setData(list);
                                   // processResponse(requestId, response);
                                   // }
                                   processResponse(requestId, response);
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
    private static void copyValues(ListGridRecord from, RequestItem to) {
        to.setUserName(from.getAttributeAsString(Constants.ATTR_NAME));
        to.setObject(from.getAttributeAsString(Constants.ATTR_OBJECT));
        to.setDescription(from.getAttributeAsString(Constants.ATTR_DESC));
        to.setTimestamp(from.getAttributeAsString(Constants.ATTR_TIMESTAMP));
        to.setType(from.getAttributeAsString(Constants.ATTR_TYPE));
        to.setId(from.getAttributeAsLong(Constants.ATTR_GENERIC_ID));
        to.setUserId(from.getAttributeAsLong(Constants.ATTR_USER_ID));
    }

    /**
     * Copy values.
     * 
     * @param from
     *        the from
     * @param to
     *        the to
     */
    private static void copyValues(RequestItem from, ListGridRecord to) {
        to.setAttribute(Constants.ATTR_NAME, from.getUserName());
        to.setAttribute(Constants.ATTR_OBJECT, from.getObject());
        to.setAttribute(Constants.ATTR_DESC, from.getDescription());
        to.setAttribute(Constants.ATTR_TIMESTAMP, from.getTimestamp());
        to.setAttribute(Constants.ATTR_TYPE, from.getType());
        to.setAttribute(Constants.ATTR_GENERIC_ID, from.getId());
        to.setAttribute(Constants.ATTR_USER_ID, from.getUserId());
    }

    /**
     * Gets the edited record.
     * 
     * @param request
     *        the request
     * @return the edited record
     */
    @SuppressWarnings("unused")
    private ListGridRecord getEditedRecord(final DSRequest request) {
        // Retrieving values before edit
        JavaScriptObject oldValues = request.getAttributeAsJavaScriptObject("oldValues");
        // Creating new record for combining old values with changes
        ListGridRecord newRecord = new ListGridRecord();
        // Copying properties from old record
        JSOHelper.apply(oldValues, newRecord.getJsObj());
        // Retrieving changed values
        JavaScriptObject data = request.getData();
        // Apply changes
        JSOHelper.apply(data, newRecord.getJsObj());
        return newRecord;
    }
}
