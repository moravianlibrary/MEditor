package cz.fi.muni.xkremser.editor.shared.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ModifyDOServiceAsync {

	void modify(String pid, AsyncCallback<Void> callback);

}
