package cz.fi.muni.xkremser.editor.client.gwtrpcds;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author Aleksandras Novikovas
 * @author System Tier
 * @version 1.0
 */
public interface SimpleGwtRPCDSServiceAsync {

	public abstract void fetch(AsyncCallback<List<SimpleGwtRPCDSRecord>> asyncCallback);

	public abstract void add(SimpleGwtRPCDSRecord record, AsyncCallback<SimpleGwtRPCDSRecord> asyncCallback);

	public abstract void update(SimpleGwtRPCDSRecord record, AsyncCallback<SimpleGwtRPCDSRecord> asyncCallback);

	public abstract void remove(SimpleGwtRPCDSRecord record, AsyncCallback asyncCallback);

}
