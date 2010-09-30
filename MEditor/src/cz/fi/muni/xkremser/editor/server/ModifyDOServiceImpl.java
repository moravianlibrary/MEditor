package cz.fi.muni.xkremser.editor.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cz.fi.muni.xkremser.editor.shared.rpc.ModifyDOService;

public class ModifyDOServiceImpl extends RemoteServiceServlet implements ModifyDOService {

	@Override
	public void modify(String pid) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// super.doGet(req, resp);
		modify("ahoj");
	}
}
