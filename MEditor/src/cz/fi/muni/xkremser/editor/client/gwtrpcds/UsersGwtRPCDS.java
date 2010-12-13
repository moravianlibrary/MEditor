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
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.fi.muni.xkremser.editor.client.ClientUtils;
import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.shared.rpc.UserInfoItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetUserInfoAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetUserInfoResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutUserInfoAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutUserInfoResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserInfoAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserInfoResult;

// TODO: Auto-generated Javadoc
/**
 * The Class RecentlyTreeGwtRPCDS.
 */
public class UsersGwtRPCDS extends AbstractGwtRPCDS {

	/** The dispatcher. */
	private final DispatchAsync dispatcher;

	/**
	 * Instantiates a new recently tree gwt rpcds.
	 * 
	 * @param dispatcher
	 *          the dispatcher
	 */
	public UsersGwtRPCDS(DispatchAsync dispatcher) {
		this.dispatcher = dispatcher;
		DataSourceField field;
		field = new DataSourceTextField(Constants.ATTR_NAME, "Firs Name");
		field.setRequired(true);
		field.setAttribute("width", "40%");
		addField(field);
		field = new DataSourceTextField(Constants.ATTR_SURNAME, "Last Name");
		field.setRequired(true);
		field.setAttribute("width", "*");
		addField(field);
		field = new DataSourceTextField(Constants.ATTR_SEX, "sex");
		field.setHidden(true);
		addField(field);
		field = new DataSourceTextField(Constants.ATTR_USER_ID, "user id");
		field.setPrimaryKey(true);
		field.setHidden(true);
		field.setRequired(true);
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
		dispatcher.execute(new GetUserInfoAction(), new DispatchCallback<GetUserInfoResult>() {
			@Override
			public void callbackError(final Throwable cause) {
				Log.error("Handle Failure:", cause);
				response.setStatus(RPCResponse.STATUS_FAILURE);
			}

			@Override
			public void callback(final GetUserInfoResult result) {
				ArrayList<UserInfoItem> items = result.getItems();
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
		JavaScriptObject data = request.getData();
		final ListGridRecord rec = new ListGridRecord(data);
		final UserInfoItem testRec = new UserInfoItem();
		copyValues(rec, testRec);
		dispatcher.execute(new RemoveUserInfoAction(testRec.getId()), new DispatchCallback<RemoveUserInfoResult>() {

			@Override
			public void callbackError(Throwable caught) {
				response.setStatus(RPCResponse.STATUS_FAILURE);
				processResponse(requestId, response);
			}

			@Override
			public void callback(RemoveUserInfoResult result) {
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
	 *          the from
	 * @param to
	 *          the to
	 */
	private static void copyValues(ListGridRecord from, UserInfoItem to) {
		to.setSurname(from.getAttributeAsString(Constants.ATTR_SURNAME));
		to.setName(from.getAttributeAsString(Constants.ATTR_NAME));
		to.setSex(ClientUtils.toBoolean(from.getAttributeAsString(Constants.ATTR_SEX)) ? "m" : "f");
		to.setId(from.getAttributeAsString(Constants.ATTR_USER_ID));
	}

	/**
	 * Copy values.
	 * 
	 * @param from
	 *          the from
	 * @param to
	 *          the to
	 */
	private static void copyValues(UserInfoItem from, ListGridRecord to) {
		to.setAttribute(Constants.ATTR_SURNAME, from.getSurname());
		to.setAttribute(Constants.ATTR_NAME, from.getName());
		to.setAttribute(Constants.ATTR_SEX, from.getSex());
		to.setAttribute(Constants.ATTR_USER_ID, from.getId());
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
