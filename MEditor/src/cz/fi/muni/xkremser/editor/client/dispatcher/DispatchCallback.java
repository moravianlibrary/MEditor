/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.dispatcher;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class DispatchCallback.
 *
 * @param <T> the generic type
 */
public abstract class DispatchCallback<T> implements AsyncCallback<T> {

	/**
	 * Instantiates a new dispatch callback.
	 */
	public DispatchCallback() {

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
	 */
	@Override
	public void onFailure(Throwable caught) {
		callbackError(caught);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	@Override
	public void onSuccess(T result) {
		callback(result);
	}

	/**
	 * Must be overriden by clients to handle callbacks.
	 *
	 * @param result the result
	 */
	public abstract void callback(T result);

	/**
	 * Should be overriden by clients who want to handle error cases themselves.
	 *
	 * @param t the t
	 */
	public void callbackError(Throwable t) {
		t.printStackTrace();
		Window.alert("RPC failed: " + t.toString());
	}
}
