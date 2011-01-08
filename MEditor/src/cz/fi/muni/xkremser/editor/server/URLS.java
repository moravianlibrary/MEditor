package cz.fi.muni.xkremser.editor.server;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public class URLS {
	public static final boolean LOCALHOST = false;
	public static final String ROOT = "";
	// public static final String ROOT = "/skin1";
	public static final String MAIN_PAGE = "/MEditor.html";
	public static final String LOGIN_PAGE = "/login.html";
	public static final String INFO_PAGE = "/info.html";
	public static final String AUTH_SERVLET = "/auth";

	public static void redirect(HttpServletResponse resp, String where) {
		resp.setContentType("text/plain");
		resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		resp.setHeader("Location", where);
	}

	public static String convertToAJAXURL(Map parameters) {
		StringBuilder sufix = new StringBuilder();
		sufix.append('#');
		for (Object keyObj : parameters.keySet()) {
			sufix.append(keyObj);
			String[] values = (String[]) parameters.get(keyObj);
			if (values.length > 0 && !"".equals(values[0])) {
				sufix.append('=').append(values[0]);
			}
			sufix.append(';');
		}
		return sufix.toString();
	}

}
