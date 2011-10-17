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
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.window.EditorSC;

import cz.fi.muni.xkremser.editor.shared.event.RefreshRecentlyTreeEvent;
import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRecentlyModifiedAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRecentlyModifiedResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutRecentlyModifiedAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutRecentlyModifiedResult;

// TODO: Auto-generated Javadoc
/**
 * The Class RecentlyTreeGwtRPCDS.
 */
public class RecentlyTreeGwtRPCDS
        extends AbstractGwtRPCDS {

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    private final EventBus bus;

    private final LangConstants lang;

    private static final String FIRST_PART_OF_COLOR_LOCK_BY_USER = "<font color=\'#006800\'>";
    private static final String FIRST_PART_OF_COLOR_LOCK = "<font color=\'#c60000\'>";
    private static final String SECOND_PART_OF_COLOR_LOCK = "</font>";

    /**
     * Instantiates a new recently tree gwt rpcds.
     * 
     * @param dispatcher
     *        the dispatcher
     */
    public RecentlyTreeGwtRPCDS(DispatchAsync dispatcher, LangConstants lang, EventBus bus) {
        this.dispatcher = dispatcher;
        this.lang = lang;
        this.bus = bus;
        DataSourceField field;
        field = new DataSourceTextField(Constants.ATTR_NAME, lang.name());
        field.setRequired(true);
        field.setAttribute("width", "40%");
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_UUID, "PID");
        field.setPrimaryKey(true);
        field.setRequired(true);
        field.setHidden(true);
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_UUID_TO_DISPLAY, "PIDToDisplay");
        field.setRequired(true);
        field.setAttribute("width", "*");
        addField(field);
        field = new DataSourceTextField(Constants.ATTR_DESC, "description");
        field.setHidden(true);
        field.setRequired(true);
        addField(field);
        field = new DataSourceField(Constants.ATTR_MODEL, FieldType.ENUM, "model");
        field.setRequired(true);
        field.setHidden(true);
        addField(field);
        field = new DataSourceDateField(Constants.ATTR_MODIFIED, "modified");
        field.setRequired(true);
        field.setHidden(true);
        addField(field);
        field = new DataSourceDateField(Constants.ATTR_LOCK_OWNER, "lockOwner");
        field.setRequired(true);
        field.setHidden(true);
        addField(field);
        field = new DataSourceDateField(Constants.ATTR_LOCK_DESCRIPTION, "lockDescription");
        field.setRequired(true);
        field.setHidden(true);
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
        boolean all = request.getCriteria().getAttributeAsBoolean(Constants.ATTR_ALL);

        dispatcher.execute(new GetRecentlyModifiedAction(all),
                           new DispatchCallback<GetRecentlyModifiedResult>() {

                               @Override
                               public void callbackError(final Throwable cause) {
                                   Log.error("Handle Failure:", cause);
                                   response.setStatus(RPCResponse.STATUS_FAILURE);
                                   EditorSC.serverRecentlyMofifiedError(lang);
                               }

                               @Override
                               public void callback(final GetRecentlyModifiedResult result) {
                                   ArrayList<RecentlyModifiedItem> items = result.getItems();
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
        JavaScriptObject data = request.getData();
        ListGridRecord rec = new ListGridRecord(data);
        final RecentlyModifiedItem testRec = new RecentlyModifiedItem();
        copyValues(rec, testRec);

        dispatcher.execute(new PutRecentlyModifiedAction(testRec),
                           new DispatchCallback<PutRecentlyModifiedResult>() {

                               @Override
                               public void callbackError(Throwable caught) {
                                   response.setStatus(RPCResponse.STATUS_FAILURE);
                                   processResponse(requestId, response);
                               }

                               @Override
                               public void callback(PutRecentlyModifiedResult result) {
                                   if (!result.isFound()) {
                                       ListGridRecord[] list = new ListGridRecord[1];
                                       ListGridRecord newRec = new ListGridRecord();
                                       copyValues(testRec, newRec);
                                       list[0] = newRec;
                                       response.setData(list);
                                   }
                                   processResponse(requestId, response);
                                   RefreshRecentlyTreeEvent.fire(bus);
                               }
                           });

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
        ListGridRecord rec = getEditedRecord(request);
        final RecentlyModifiedItem testRec = new RecentlyModifiedItem();
        copyValues(rec, testRec);

        dispatcher.execute(new PutRecentlyModifiedAction(testRec),
                           new DispatchCallback<PutRecentlyModifiedResult>() {

                               @Override
                               public void callbackError(Throwable caught) {
                                   response.setStatus(RPCResponse.STATUS_FAILURE);
                                   processResponse(requestId, response);
                               }

                               @Override
                               public void callback(PutRecentlyModifiedResult result) {
                                   if (result.isFound()) {
                                       ListGridRecord[] list = new ListGridRecord[1];
                                       ListGridRecord updRec = new ListGridRecord();
                                       copyValues(testRec, updRec);
                                       list[0] = updRec;
                                       response.setData(list);
                                       processResponse(requestId, response);
                                   }
                                   processResponse(requestId, response);
                                   RefreshRecentlyTreeEvent.fire(bus);
                               }
                           });
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
    private static void copyValues(ListGridRecord from, RecentlyModifiedItem to) {
        to.setUuid(from.getAttributeAsString(Constants.ATTR_UUID));
        to.setName(from.getAttributeAsString(Constants.ATTR_NAME));
        to.setDescription(from.getAttributeAsString(Constants.ATTR_DESC));
        to.setModel((DigitalObjectModel) from.getAttributeAsObject(Constants.ATTR_MODEL));
        to.setModified(from.getAttributeAsDate(Constants.ATTR_MODIFIED));
    }

    /**
     * Copy values.
     * 
     * @param from
     *        the from
     * @param to
     *        the to
     */
    private static void copyValues(RecentlyModifiedItem from, ListGridRecord to) {

        if (from.getLockOwner() != null) {
            if ("".equals(from.getLockOwner())) {
                to.setAttribute(Constants.ATTR_UUID_TO_DISPLAY,
                                FIRST_PART_OF_COLOR_LOCK_BY_USER + from.getUuid() + SECOND_PART_OF_COLOR_LOCK);
                to.setAttribute(Constants.ATTR_NAME, FIRST_PART_OF_COLOR_LOCK_BY_USER + from.getName()
                        + SECOND_PART_OF_COLOR_LOCK);

            } else if (from.getLockOwner().length() > 0) {
                to.setAttribute(Constants.ATTR_UUID_TO_DISPLAY, FIRST_PART_OF_COLOR_LOCK + from.getUuid()
                        + SECOND_PART_OF_COLOR_LOCK);
                to.setAttribute(Constants.ATTR_NAME, FIRST_PART_OF_COLOR_LOCK + from.getName()
                        + SECOND_PART_OF_COLOR_LOCK);
            }
        } else {
            to.setAttribute(Constants.ATTR_UUID_TO_DISPLAY, from.getUuid());
            to.setAttribute(Constants.ATTR_NAME, from.getName());
        }

        to.setAttribute(Constants.ATTR_UUID, from.getUuid());
        to.setAttribute(Constants.ATTR_DESC, from.getDescription());
        to.setAttribute(Constants.ATTR_MODEL, from.getModel());
        to.setAttribute(Constants.ATTR_MODIFIED, from.getModified());
        to.setAttribute(Constants.ATTR_LOCK_OWNER, from.getLockOwner());
        to.setAttribute(Constants.ATTR_LOCK_DESCRIPTION, from.getLockDescription());
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
