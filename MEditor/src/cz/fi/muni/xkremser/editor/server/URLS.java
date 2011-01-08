/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */
package cz.fi.muni.xkremser.editor.server;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class URLS.
 */
public class URLS {

	/** The Constant LOCALHOST. */
	public static final boolean LOCALHOST = false;

	/** The Constant ROOT. */
	public static final String ROOT = "";
	// public static final String ROOT = "/skin1";
	/** The Constant MAIN_PAGE. */
	public static final String MAIN_PAGE = "/MEditor.html";

	/** The Constant LOGIN_PAGE. */
	public static final String LOGIN_PAGE = "/login.html";

	/** The Constant INFO_PAGE. */
	public static final String INFO_PAGE = "/info.html";

	/** The Constant AUTH_SERVLET. */
	public static final String AUTH_SERVLET = "/auth";

	/**
	 * Redirect.
	 * 
	 * @param resp
	 *          the resp
	 * @param where
	 *          the where
	 */
	public static void redirect(HttpServletResponse resp, String where) {
		resp.setContentType("text/plain");
		resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		resp.setHeader("Location", where);
	}

	/**
	 * Convert to ajaxurl.
	 * 
	 * @param parameters
	 *          the parameters
	 * @return the string
	 */
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
