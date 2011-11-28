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

import javax.inject.Inject;

import com.allen_sauer.gwt.log.client.Log;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
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
        field = new DataSourceTextField(Constants.ATTR_BARCODE, "ID");
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
        to.setAttribute(Constants.ATTR_BARCODE, from.getBarcode());
    }

}
