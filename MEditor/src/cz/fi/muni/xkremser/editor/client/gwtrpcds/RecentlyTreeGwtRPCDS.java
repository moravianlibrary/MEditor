/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.gwtrpcds;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.JavaScriptObject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.KrameriusModel;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRecentlyModifiedAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRecentlyModifiedResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutRecentlyModifiedAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutRecentlyModifiedResult;

// TODO: Auto-generated Javadoc
/**
 * The Class RecentlyTreeGwtRPCDS.
 */
public class RecentlyTreeGwtRPCDS extends AbstractGwtRPCDS {

	/** The dispatcher. */
	private final DispatchAsync dispatcher;

	/**
	 * Instantiates a new recently tree gwt rpcds.
	 * 
	 * @param dispatcher
	 *          the dispatcher
	 */
	public RecentlyTreeGwtRPCDS(DispatchAsync dispatcher) {
		this.dispatcher = dispatcher;
		DataSourceField field;
		field = new DataSourceTextField(Constants.ATTR_NAME, "name");
		field.setRequired(true);
		addField(field);
		field = new DataSourceTextField(Constants.ATTR_UUID, "uuid");
		field.setPrimaryKey(true);
		field.setRequired(true);
		addField(field);
		field = new DataSourceTextField(Constants.ATTR_DESC, "description");
		field.setHidden(true);
		field.setRequired(true);
		addField(field);
		field = new DataSourceField(Constants.ATTR_MODEL, FieldType.ENUM, "model");
		field.setRequired(true);
		field.setHidden(true);
		addField(field);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.gwtrpcds.AbstractGwtRPCDS#executeFetch
	 * (java.lang.String, com.smartgwt.client.data.DSRequest,
	 * com.smartgwt.client.data.DSResponse)
	 */
	@Override
	protected void executeFetch(final String requestId, final DSRequest request, final DSResponse response) {
		boolean all = request.getCriteria().getAttributeAsBoolean(Constants.ATTR_ALL);

		dispatcher.execute(new GetRecentlyModifiedAction(all), new DispatchCallback<GetRecentlyModifiedResult>() {
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
	 * 
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

		dispatcher.execute(new PutRecentlyModifiedAction(testRec), new DispatchCallback<PutRecentlyModifiedResult>() {

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
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
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

		dispatcher.execute(new PutRecentlyModifiedAction(testRec), new DispatchCallback<PutRecentlyModifiedResult>() {

			@Override
			public void callbackError(Throwable caught) {
				response.setStatus(RPCResponse.STATUS_FAILURE);
				processResponse(requestId, response);
			}

			@Override
			public void callback(PutRecentlyModifiedResult result) {
				if (!result.isFound()) {

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

	/*
	 * (non-Javadoc)
	 * 
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
	 *          the from
	 * @param to
	 *          the to
	 */
	private static void copyValues(ListGridRecord from, RecentlyModifiedItem to) {
		to.setUuid(from.getAttributeAsString(Constants.ATTR_UUID));
		to.setName(from.getAttributeAsString(Constants.ATTR_NAME));
		to.setDescription(from.getAttributeAsString(Constants.ATTR_DESC));
		to.setModel((KrameriusModel) from.getAttributeAsObject(Constants.ATTR_MODEL));
	}

	/**
	 * Copy values.
	 * 
	 * @param from
	 *          the from
	 * @param to
	 *          the to
	 */
	private static void copyValues(RecentlyModifiedItem from, ListGridRecord to) {
		to.setAttribute(Constants.ATTR_UUID, from.getUuid());
		to.setAttribute(Constants.ATTR_NAME, from.getName());
		to.setAttribute(Constants.ATTR_DESC, from.getDescription());
		to.setAttribute(Constants.ATTR_MODEL, from.getModel());
	}

	/**
	 * Gets the edited record.
	 * 
	 * @param request
	 *          the request
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
