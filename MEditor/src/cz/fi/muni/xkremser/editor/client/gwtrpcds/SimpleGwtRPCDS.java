package cz.fi.muni.xkremser.editor.client.gwtrpcds;

import java.util.ArrayList;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.shared.rpc.InputQueueItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueue;
import cz.fi.muni.xkremser.editor.shared.rpc.result.ScanInputQueueResult;

/**
 * Example <code>AbstractGwtRPCDS</code> implementation.
 * 
 * @author Aleksandras Novikovas
 * @author System Tier
 * @version 1.0
 */
public class SimpleGwtRPCDS extends AbstractGwtRPCDS {

	private final DispatchAsync dispatcher;

	@Inject
	public SimpleGwtRPCDS(DispatchAsync dispatcher) {
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
		field = new DataSourceTextField(Constants.ATTR_NAME, "Name");
		field.setRequired(true);
		addField(field);
		field = new DataSourceTextField(Constants.ATTR_ISSN, "issn");
		field.setRequired(true);
		addField(field);

	}

	@Override
	protected void executeFetch(final String requestId, final DSRequest request, final DSResponse response) {
		String id = (String) request.getCriteria().getValues().get(Constants.ATTR_PARENT);
		dispatcher.execute(new ScanInputQueue(id, ScanInputQueue.TYPE.DB_GET), new AsyncCallback<ScanInputQueueResult>() {
			@Override
			public void onFailure(final Throwable cause) {
				Log.error("Handle Failure:", cause);
				response.setStatus(RPCResponse.STATUS_FAILURE);
				// Window.alert(Messages.SERVER_SCANINPUT_ERROR);
			}

			@Override
			public void onSuccess(final ScanInputQueueResult result) {
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

	@Override
	protected void executeAdd(final String requestId, final DSRequest request, final DSResponse response) {
		// Retrieve record which should be added.
		JavaScriptObject data = request.getData();
		ListGridRecord rec = new ListGridRecord(data);
		InputQueueItem testRec = new InputQueueItem();
		copyValues(rec, testRec);
		SimpleGwtRPCDSServiceAsync service = GWT.create(SimpleGwtRPCDSService.class);
		service.add(testRec, new AsyncCallback<InputQueueItem>() {
			@Override
			public void onFailure(Throwable caught) {
				response.setStatus(RPCResponse.STATUS_FAILURE);
				processResponse(requestId, response);
			}

			@Override
			public void onSuccess(InputQueueItem result) {
				ListGridRecord[] list = new ListGridRecord[1];
				ListGridRecord newRec = new ListGridRecord();
				copyValues(result, newRec);
				list[0] = newRec;
				response.setData(list);
				processResponse(requestId, response);
			}
		});
	}

	@Override
	protected void executeUpdate(final String requestId, final DSRequest request, final DSResponse response) {
		// Retrieve record which should be updated.
		// Next line would be nice to replace with line:
		// ListGridRecord rec = request.getEditedRecord ();
		ListGridRecord rec = getEditedRecord(request);
		InputQueueItem testRec = new InputQueueItem();
		copyValues(rec, testRec);
		SimpleGwtRPCDSServiceAsync service = GWT.create(SimpleGwtRPCDSService.class);
		service.update(testRec, new AsyncCallback<InputQueueItem>() {
			@Override
			public void onFailure(Throwable caught) {
				response.setStatus(RPCResponse.STATUS_FAILURE);
				processResponse(requestId, response);
			}

			@Override
			public void onSuccess(InputQueueItem result) {
				ListGridRecord[] list = new ListGridRecord[1];
				ListGridRecord updRec = new ListGridRecord();
				copyValues(result, updRec);
				list[0] = updRec;
				response.setData(list);
				processResponse(requestId, response);
			}
		});
	}

	@Override
	protected void executeRemove(final String requestId, final DSRequest request, final DSResponse response) {
		// Retrieve record which should be removed.
		JavaScriptObject data = request.getData();
		final ListGridRecord rec = new ListGridRecord(data);
		InputQueueItem testRec = new InputQueueItem();
		copyValues(rec, testRec);
		SimpleGwtRPCDSServiceAsync service = GWT.create(SimpleGwtRPCDSService.class);
		service.remove(testRec, new AsyncCallback<Object>() {
			@Override
			public void onFailure(Throwable caught) {
				response.setStatus(RPCResponse.STATUS_FAILURE);
				processResponse(requestId, response);
			}

			@Override
			public void onSuccess(Object result) {
				ListGridRecord[] list = new ListGridRecord[1];
				// We do not receive removed record from server.
				// Return record from request.
				list[0] = rec;
				response.setData(list);
				processResponse(requestId, response);
			}

		});
	}

	private static void copyValues(ListGridRecord from, InputQueueItem to) {
		to.setPath(from.getAttributeAsString(Constants.ATTR_ID));
		to.setName(from.getAttributeAsString(Constants.ATTR_NAME));
		to.setIssn(from.getAttributeAsString(Constants.ATTR_ISSN));
	}

	private static void copyValues(InputQueueItem from, ListGridRecord to) {
		to.setAttribute(Constants.ATTR_ID, from.getPath());
		to.setAttribute(Constants.ATTR_NAME, from.getName());
		to.setAttribute(Constants.ATTR_ISSN, from.getIssn());
	}

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
