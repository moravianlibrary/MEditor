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

package cz.fi.muni.xkremser.editor.client.gwtrpcds;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.shared.rpc.InputQueueItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueResult;

// TODO: Auto-generated Javadoc
/**
 * The Class InputTreeGwtRPCDS.
 */
public class InputTreeGwtRPCDS
        extends AbstractGwtRPCDS {

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /**
     * Instantiates a new input tree gwt rpcds.
     * 
     * @param dispatcher
     *        the dispatcher
     */
    @Inject
    public InputTreeGwtRPCDS(DispatchAsync dispatcher, LangConstants lang) {
        this.dispatcher = dispatcher;
        DataSourceField field;
        field = new DataSourceTextField(Constants.ATTR_ID, "id");
        field.setPrimaryKey(true);
        field.setRequired(true);
        field.setHidden(true);
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_PARENT, "parent");
        field.setForeignKey(Constants.ATTR_ID);
        field.setHidden(true);
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_NAME, lang.name());
        field.setRequired(true);
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_ISSN, "ID");
        field.setAttribute("width", "60%");
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
        String id = (String) request.getCriteria().getValues().get(Constants.ATTR_PARENT);
        dispatcher.execute(new ScanInputQueueAction(id, false), new DispatchCallback<ScanInputQueueResult>() {

            @Override
            public void callbackError(final Throwable cause) {
                Log.error("Handle Failure:", cause);
                response.setStatus(RPCResponse.STATUS_FAILURE);
                // Window.alert(Messages.SERVER_SCANINPUT_ERROR);

                // TODO: Scanning input
                // queue: Action failed because attribut input_queue is not set. kdyz
                // nejsou zadne configuration.properties
            }

            @Override
            public void callback(final ScanInputQueueResult result) {
                ArrayList<InputQueueItem> items = result.getItems();
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
        // Retrieve record which should be added.
        // JavaScriptObject data = request.getData();
        // ListGridRecord rec = new ListGridRecord(data);
        // InputQueueItem testRec = new InputQueueItem();
        // copyValues(rec, testRec);
        // SimpleGwtRPCDSServiceAsync service =
        // GWT.create(SimpleGwtRPCDSService.class);
        // service.add(testRec, new DispatchCallback<InputQueueItem>() {
        // @Override
        // public void callbackError(Throwable caught) {
        // response.setStatus(RPCResponse.STATUS_FAILURE);
        // processResponse(requestId, response);
        // }
        //
        // @Override
        // public void callback(InputQueueItem result) {
        // ListGridRecord[] list = new ListGridRecord[1];
        // ListGridRecord newRec = new ListGridRecord();
        // copyValues(result, newRec);
        // list[0] = newRec;
        // response.setData(list);
        // processResponse(requestId, response);
        // }
        // });
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
        // Retrieve record which should be updated.
        // Next line would be nice to replace with line:
        // ListGridRecord rec = request.getEditedRecord ();
        // ListGridRecord rec = getEditedRecord(request);
        // InputQueueItem testRec = new InputQueueItem();
        // copyValues(rec, testRec);
        // SimpleGwtRPCDSServiceAsync service =
        // GWT.create(SimpleGwtRPCDSService.class);
        // service.update(testRec, new DispatchCallback<InputQueueItem>() {
        // @Override
        // public void callbackError(Throwable caught) {
        // response.setStatus(RPCResponse.STATUS_FAILURE);
        // processResponse(requestId, response);
        // }
        //
        // @Override
        // public void callback(InputQueueItem result) {
        // ListGridRecord[] list = new ListGridRecord[1];
        // ListGridRecord updRec = new ListGridRecord();
        // copyValues(result, updRec);
        // list[0] = updRec;
        // response.setData(list);
        // processResponse(requestId, response);
        // }
        // });
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
        // Retrieve record which should be removed.
        // JavaScriptObject data = request.getData();
        // final ListGridRecord rec = new ListGridRecord(data);
        // InputQueueItem testRec = new InputQueueItem();
        // copyValues(rec, testRec);
        // SimpleGwtRPCDSServiceAsync service =
        // GWT.create(SimpleGwtRPCDSService.class);
        // service.remove(testRec, new DispatchCallback<Object>() {
        // @Override
        // public void callbackError(Throwable caught) {
        // response.setStatus(RPCResponse.STATUS_FAILURE);
        // processResponse(requestId, response);
        // }
        //
        // @Override
        // public void callback(Object result) {
        // ListGridRecord[] list = new ListGridRecord[1];
        // // We do not receive removed record from server.
        // // Return record from request.
        // list[0] = rec;
        // response.setData(list);
        // processResponse(requestId, response);
        // }
        //
        // });
    }

    /**
     * Copy values.
     * 
     * @param from
     *        the from
     * @param to
     *        the to
     */
    private static void copyValues(ListGridRecord from, InputQueueItem to) {
        to.setPath(from.getAttributeAsString(Constants.ATTR_ID));
        to.setName(from.getAttributeAsString(Constants.ATTR_NAME));
        to.setIssn(from.getAttributeAsString(Constants.ATTR_ISSN));
    }

    /**
     * Copy values.
     * 
     * @param from
     *        the from
     * @param to
     *        the to
     */
    private static void copyValues(InputQueueItem from, ListGridRecord to) {
        to.setAttribute(Constants.ATTR_ID, from.getPath());
        to.setAttribute(Constants.ATTR_NAME, from.getName());
        to.setAttribute(Constants.ATTR_ISSN, from.getIssn());
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
