package cz.fi.muni.xkremser.editor.client.dispatcher;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class DispatchCallback<T> implements AsyncCallback<T> {

	public DispatchCallback() {

	}

	@Override
	public void onFailure(Throwable caught) {
		callbackError(caught);
	}

	@Override
	public void onSuccess(T result) {
		callback(result);
	}

	/**
	 * Must be overriden by clients to handle callbacks
	 * @param result
	 */
	public abstract void callback(T result);

	/**
	 * Should be overriden by clients who want to
	 * handle error cases themselves.
	 */
	public void callbackError(Throwable t) {
		t.printStackTrace();
		Window.alert("RPC failed: " + t.toString());
	}
}
