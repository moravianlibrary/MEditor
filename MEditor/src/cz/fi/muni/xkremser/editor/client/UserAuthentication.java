package cz.fi.muni.xkremser.editor.client;

import com.google.gwt.user.client.rpc.RemoteService;

import cz.fi.muni.xkremser.editor.shared.valueobj.LoginResult;
import cz.fi.muni.xkremser.editor.shared.valueobj.UserDetails;

public interface UserAuthentication extends RemoteService {

	LoginResult login(String openIdURL);

	UserDetails getCurrentUser();

	void logout();

}
