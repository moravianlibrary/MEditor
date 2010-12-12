package cz.fi.muni.xkremser.editor.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cz.fi.muni.xkremser.editor.shared.valueobj.LoginResult;
import cz.fi.muni.xkremser.editor.shared.valueobj.UserDetails;

public interface UserAuthenticationAsync {

	void getCurrentUser(AsyncCallback<UserDetails> callback);

	void login(String openIdURL, AsyncCallback<LoginResult> callback);

	void logout(AsyncCallback<Void> callback);

}
