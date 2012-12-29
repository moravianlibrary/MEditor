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
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.rpc.UserInfoItem;
import cz.mzk.editor.shared.rpc.action.GetUsersInfoAction;
import cz.mzk.editor.shared.rpc.action.GetUsersInfoResult;
import cz.mzk.editor.shared.rpc.action.PutUserInfoAction;
import cz.mzk.editor.shared.rpc.action.PutUserInfoResult;
import cz.mzk.editor.shared.rpc.action.RemoveUserAction;
import cz.mzk.editor.shared.rpc.action.RemoveUserResult;

// TODO: Auto-generated Javadoc
/**
 * The Class RecentlyTreeGwtRPCDS.
 */
public class UsersGwtRPCDS
        extends AbstractGwtRPCDS {

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    @SuppressWarnings("unused")
    private final LangConstants lang;

    /**
     * Instantiates a new recently tree gwt rpcds.
     * 
     * @param dispatcher
     *        the dispatcher
     * @param lang
     *        the lang
     */
    public UsersGwtRPCDS(DispatchAsync dispatcher, LangConstants lang) {
        this.dispatcher = dispatcher;
        this.lang = lang;
        DataSourceField field;
        field = new DataSourceTextField(Constants.ATTR_NAME, lang.firstName());
        field.setRequired(true);
        field.setAttribute("width", "40%");
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_SURNAME, lang.lastName());
        field.setRequired(true);
        field.setAttribute("width", "*");
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_USER_ID, "user id");
        field.setPrimaryKey(true);
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
        dispatcher.execute(new GetUsersInfoAction(), new DispatchCallback<GetUsersInfoResult>() {

            @Override
            public void callbackError(final Throwable cause) {
                Log.error("Handle Failure:", cause);
                response.setStatus(RPCResponse.STATUS_FAILURE);
                super.callbackError(cause);
            }

            @Override
            public void callback(final GetUsersInfoResult result) {
                ArrayList<UserInfoItem> items = result.getItems();
                if (items != null) {
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
        // Retrieve record which should be added.
        JavaScriptObject data = request.getData();
        ListGridRecord rec = new ListGridRecord(data);
        final UserInfoItem testRec = new UserInfoItem();
        copyValues(rec, testRec);

        dispatcher.execute(new PutUserInfoAction(testRec), new DispatchCallback<PutUserInfoResult>() {

            @Override
            public void callbackError(Throwable caught) {
                response.setStatus(RPCResponse.STATUS_FAILURE);
                processResponse(requestId, response);
            }

            @Override
            public void callback(PutUserInfoResult result) {
                if (result.getId() > 0) {
                    ListGridRecord[] list = new ListGridRecord[1];
                    ListGridRecord newRec = new ListGridRecord();
                    copyValues(testRec, newRec);
                    newRec.setAttribute(Constants.ATTR_USER_ID, result.getId());
                    list[0] = newRec;
                    response.setData(list);
                    processResponse(requestId, response);
                } else {
                    SC.warn(lang.operationFailed());
                    processResponse(requestId, response);
                }
            }
        });

    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.gwtrpcds.AbstractGwtRPCDS#executeUpdate
     * (java.lang.String, com.smartgwt.client.data.DSRequest,
     * com.smartgwt.client.data.DSResponse)
     */
    @Override
    protected void executeUpdate(final String requestId, final DSRequest request, final DSResponse response) {
        ListGridRecord rec = getEditedRecord(request);
        final UserInfoItem testRec = new UserInfoItem();
        copyValues(rec, testRec);

        dispatcher.execute(new PutUserInfoAction(testRec), new DispatchCallback<PutUserInfoResult>() {

            @Override
            public void callbackError(Throwable caught) {
                response.setStatus(RPCResponse.STATUS_FAILURE);
                processResponse(requestId, response);
            }

            @Override
            public void callback(PutUserInfoResult result) {
                if (result.getId() > 0) {
                    ListGridRecord[] list = new ListGridRecord[1];
                    ListGridRecord updRec = new ListGridRecord();
                    testRec.setId(result.getId());
                    copyValues(testRec, updRec);
                    list[0] = updRec;
                    response.setData(list);
                    processResponse(requestId, response);
                } else {
                    SC.warn(lang.operationFailed());
                    processResponse(requestId, response);
                }
            }
        });
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
        final UserInfoItem testRec = new UserInfoItem();
        copyValues(rec, testRec);
        dispatcher.execute(new RemoveUserAction(testRec.getId().toString()),
                           new DispatchCallback<RemoveUserResult>() {

                               @Override
                               public void callbackError(Throwable caught) {
                                   response.setStatus(RPCResponse.STATUS_FAILURE);
                                   processResponse(requestId, response);
                               }

                               @Override
                               public void callback(RemoveUserResult result) {
                                   if (!result.isSuccessful()) {
                                       ListGridRecord[] list = new ListGridRecord[1];
                                       ListGridRecord updRec = new ListGridRecord();
                                       copyValues(testRec, updRec);
                                       list[0] = updRec;
                                       response.setData(list);
                                       processResponse(requestId, response);
                                   }
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
    private static void copyValues(ListGridRecord from, UserInfoItem to) {
        to.setSurname(from.getAttributeAsString(Constants.ATTR_SURNAME));
        to.setName(from.getAttributeAsString(Constants.ATTR_NAME));
        if (from.getAttribute(Constants.ATTR_USER_ID) != null)
            to.setId(Long.parseLong(from.getAttributeAsString(Constants.ATTR_USER_ID)));
    }

    /**
     * Copy values.
     * 
     * @param from
     *        the from
     * @param to
     *        the to
     */
    private static void copyValues(UserInfoItem from, ListGridRecord to) {
        to.setAttribute(Constants.ATTR_SURNAME, from.getSurname());
        to.setAttribute(Constants.ATTR_NAME, from.getName());
        to.setAttribute(Constants.ATTR_USER_ID, from.getId());
    }

    /**
     * Gets the edited record.
     * 
     * @param request
     *        the request
     * @return the edited record
     */
    private ListGridRecord getEditedRecord(DSRequest request) {
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
