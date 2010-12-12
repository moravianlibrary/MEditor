package cz.fi.muni.xkremser.editor.server;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cz.fi.muni.xkremser.editor.client.UserAuthentication;
import cz.fi.muni.xkremser.editor.shared.valueobj.LoginResult;
import cz.fi.muni.xkremser.editor.shared.valueobj.UserDetails;

public class UserAuthenticationImpl extends RemoteServiceServlet implements UserAuthentication {

	@Override
	public LoginResult login(String openIdURL) {
		HttpServletRequest request = getThreadLocalRequest();
		String[] userInfo = OpenIDServlet.getRequestUserInfo(request);
		// if (notlogged)
		String url = OpenIDServlet.getAuthenticationURL("typek");

		LoginResult loginResult = new LoginResult();
		loginResult.setUrl(url);
		return loginResult;
	}

	@Override
	public UserDetails getCurrentUser() {
		String[] userInfo = OpenIDServlet.getRequestUserInfo(getThreadLocalRequest());
		return null;
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub

	}

}
