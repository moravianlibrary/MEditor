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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class HttpCookies.
 */
public class HttpCookies {

	/** The Constant dayInSeconds. */
	public static final int dayInSeconds = 86400;

	/** The Constant SESSION_ID_KEY. */
	public static final String SESSION_ID_KEY = "dR#5hJ_F$";

	/** The Constant ADMIN. */
	public static final String ADMIN = "dR#6hJ_F$";

	/** The Constant ADMIN_YES. */
	public static final String ADMIN_YES = "dE#5hJ_F$";

	/** The Constant PARAMS. */
	public static final String PARAMS = "cE#5hJ_F$";

	/** The Constant TARGET_URL. */
	public static final String TARGET_URL = "cR#5hJ_F$";

	/**
	 * Find cookie.
	 * 
	 * @param request
	 *          the request
	 * @param name
	 *          the name
	 * @return the cookie
	 */
	public static Cookie findCookie(HttpServletRequest request, String name) {
		final Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName())) {
				return cookie;
			}
		}

		return null;
	}

	/**
	 * Gets the cookie value.
	 * 
	 * @param request
	 *          the request
	 * @param name
	 *          the name
	 * @return the cookie value
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie cookie = findCookie(request, name);

		return cookie != null ? cookie.getValue() : null;
	}

	/**
	 * Reset cookie.
	 * 
	 * @param request
	 *          the request
	 * @param response
	 *          the response
	 * @param name
	 *          the name
	 */
	public static void resetCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Cookie cookie = findCookie(request, name);
		if (cookie != null) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}

	/**
	 * Sets the cookie.
	 * 
	 * @param request
	 *          the request
	 * @param response
	 *          the response
	 * @param cookieName
	 *          the cookie name
	 * @param cookieValue
	 *          the cookie value
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, final String cookieName, String cookieValue) {
		Cookie cookie = findCookie(request, cookieName);

		if (cookie == null) {
			cookie = new Cookie(cookieName, cookieValue);
		} else {
			cookie.setValue(cookieValue);
		}

		cookie.setMaxAge(dayInSeconds); // one day
		response.addCookie(cookie);
	}

}
