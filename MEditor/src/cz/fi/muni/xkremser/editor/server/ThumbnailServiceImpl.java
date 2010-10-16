package cz.fi.muni.xkremser.editor.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cz.fi.muni.xkremser.editor.shared.rpc.ModifyDOService;

public class ThumbnailServiceImpl extends RemoteServiceServlet implements ModifyDOService {

	@Override
	public void modify(String pid) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// super.doGet(req, resp);

		System.out.println(req.getRequestURI());
		System.out.println(req.getParameterMap());
		String site = "http://krameriusdemo.mzk.cz:8080/fedora/objects/uuid:1fc0c770-cd8b-11df-886d-001b63bd97ba/datastreams/IMG_THUMB/content";
		resp.setContentType("image/jpeg");
		resp.setStatus(resp.SC_MOVED_TEMPORARILY);
		resp.setHeader("Location", site);

	}
}
