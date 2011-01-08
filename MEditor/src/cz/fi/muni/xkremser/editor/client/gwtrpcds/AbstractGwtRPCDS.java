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

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSProtocol;

// TODO: Auto-generated Javadoc
/**
 * Data source with ability to communicate with server by GWT RPC.
 * <p/>
 * SmartClient natively supports data protocol "clientCustom". This protocol
 * means that communication with server should be implemented in
 * <code>transformRequest (DSRequest request)</code> method. Here is a few
 * things to note on <code>transformRequest</code> implementation:
 * <ul>
 * <li><code>DSResponse</code> object has to be created and
 * <code>processResponse (requestId, response)</code> must be called to finish
 * data request. <code>requestId</code> should be taken from original
 * <code>DSRequest.getRequestId ()</code>.</li>
 * <li>"clientContext" attribute from <code>DSRequest</code> should be copied to
 * <code>DSResponse</code>.</li>
 * <li>In case of failure <code>DSResponse</code> should contain at least
 * "status" attribute with error code (&lt;0).</li>
 * <li>In case of success <code>DSResponse</code> should contain at least "data"
 * attribute with operation type specific data:
 * <ul>
 * <li>FETCH - <code>ListGridRecord[]</code> retrieved records.</li>
 * <li>ADD - <code>ListGridRecord[]</code> with single added record. Operation
 * is called on every newly added record.</li>
 * <li>UPDATE - <code>ListGridRecord[]</code> with single updated record.
 * Operation is called on every updated record.</li>
 * <li>REMOVE - <code>ListGridRecord[]</code> with single removed record.
 * Operation is called on every removed record.</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author Aleksandras Novikovas
 * @author System Tier
 * @version 1.0
 */
public abstract class AbstractGwtRPCDS extends DataSource {

	/**
	 * Creates new data source which communicates with server by GWT RPC. It is
	 * normal server side SmartClient data source with data protocol set to
	 * <code>DSProtocol.CLIENTCUSTOM</code> ("clientCustom" - natively supported
	 * by SmartClient but should be added to smartGWT) and with data format
	 * <code>DSDataFormat.CUSTOM</code>.
	 */
	public AbstractGwtRPCDS() {
		setDataProtocol(DSProtocol.CLIENTCUSTOM);
		setDataFormat(DSDataFormat.CUSTOM);
		setClientOnly(false);
	}

	/**
	 * Executes request to server.
	 * 
	 * @param request
	 *          <code>DSRequest</code> being processed.
	 * @return <code>Object</code> data from original request.
	 */
	@Override
	protected Object transformRequest(DSRequest request) {
		String requestId = request.getRequestId();
		DSResponse response = new DSResponse();
		response.setAttribute("clientContext", request.getAttributeAsObject("clientContext"));
		// Asume success
		response.setStatus(0);
		switch (request.getOperationType()) {
			case FETCH:
				executeFetch(requestId, request, response);
			break;
			case ADD:
				executeAdd(requestId, request, response);
			break;
			case UPDATE:
				executeUpdate(requestId, request, response);
			break;
			case REMOVE:
				executeRemove(requestId, request, response);
			break;
			default:
				// Operation not implemented.
			break;
		}
		return request.getData();
	}

	/**
	 * Executed on <code>FETCH</code> operation.
	 * <code>processResponse (requestId, response)</code> should be called when
	 * operation completes (either successful or failure).
	 * 
	 * @param requestId
	 *          <code>String</code> extracted from
	 *          <code>DSRequest.getRequestId ()</code>.
	 * @param request
	 *          <code>DSRequest</code> being processed.
	 * @param response
	 *          <code>DSResponse</code>. <code>setData (list)</code> should be
	 *          called on successful execution of this method.
	 *          <code>setStatus (&lt;0)</code> should be called on failure.
	 */
	protected abstract void executeFetch(String requestId, DSRequest request, DSResponse response);

	/**
	 * Executed on <code>ADD</code> operation.
	 * <code>processResponse (requestId, response)</code> should be called when
	 * operation completes (either successful or failure).
	 * 
	 * @param requestId
	 *          <code>String</code> extracted from
	 *          <code>DSRequest.getRequestId ()</code>.
	 * @param request
	 *          <code>DSRequest</code> being processed.
	 *          <code>request.getData ()</code> contains record should be added.
	 * @param response
	 *          <code>DSResponse</code>. <code>setData (list)</code> should be
	 *          called on successful execution of this method. Array should
	 *          contain single element representing added row.
	 *          <code>setStatus (&lt;0)</code> should be called on failure.
	 */
	protected abstract void executeAdd(String requestId, DSRequest request, DSResponse response);

	/**
	 * Executed on <code>UPDATE</code> operation.
	 * <code>processResponse (requestId, response)</code> should be called when
	 * operation completes (either successful or failure).
	 * 
	 * @param requestId
	 *          <code>String</code> extracted from
	 *          <code>DSRequest.getRequestId ()</code>.
	 * @param request
	 *          <code>DSRequest</code> being processed.
	 *          <code>request.getData ()</code> contains record should be updated.
	 * @param response
	 *          <code>DSResponse</code>. <code>setData (list)</code> should be
	 *          called on successful execution of this method. Array should
	 *          contain single element representing updated row.
	 *          <code>setStatus (&lt;0)</code> should be called on failure.
	 */
	protected abstract void executeUpdate(String requestId, DSRequest request, DSResponse response);

	/**
	 * Executed on <code>REMOVE</code> operation.
	 * <code>processResponse (requestId, response)</code> should be called when
	 * operation completes (either successful or failure).
	 * 
	 * @param requestId
	 *          <code>String</code> extracted from
	 *          <code>DSRequest.getRequestId ()</code>.
	 * @param request
	 *          <code>DSRequest</code> being processed.
	 *          <code>request.getData ()</code> contains record should be removed.
	 * @param response
	 *          <code>DSResponse</code>. <code>setData (list)</code> should be
	 *          called on successful execution of this method. Array should
	 *          contain single element representing removed row.
	 *          <code>setStatus (&lt;0)</code> should be called on failure.
	 */
	protected abstract void executeRemove(String requestId, DSRequest request, DSResponse response);

}
