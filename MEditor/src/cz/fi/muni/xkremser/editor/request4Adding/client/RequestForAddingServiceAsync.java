
package cz.fi.muni.xkremser.editor.request4Adding.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RequestForAddingServiceAsync {

    void ask(AsyncCallback<String> callback);

    void getName(AsyncCallback<String> callback);

}
