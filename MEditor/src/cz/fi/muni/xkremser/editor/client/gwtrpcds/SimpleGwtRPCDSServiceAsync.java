package cz.fi.muni.xkremser.editor.client.gwtrpcds;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cz.fi.muni.xkremser.editor.shared.rpc.InputQueueItem;

/**
 * 
 * @author Aleksandras Novikovas
 * @author System Tier
 * @version 1.0
 */
public interface SimpleGwtRPCDSServiceAsync {

	public abstract void fetch(AsyncCallback<List<InputQueueItem>> asyncCallback);

	public abstract void add(InputQueueItem record, AsyncCallback<InputQueueItem> asyncCallback);

	public abstract void update(InputQueueItem record, AsyncCallback<InputQueueItem> asyncCallback);

	public abstract void remove(InputQueueItem record, AsyncCallback asyncCallback);

}
