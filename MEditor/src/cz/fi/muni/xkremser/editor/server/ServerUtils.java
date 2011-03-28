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

import javax.servlet.http.HttpSession;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.client.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerUtils.
 */
public class ServerUtils {

	/**
	 * Checks if is caused by exception.
	 * 
	 * @param t
	 *          the t
	 * @param type
	 *          the type
	 * @return true, if is caused by exception
	 */
	public static boolean isCausedByException(Throwable t, Class<? extends Exception> type) {
		if (t == null)
			return false;
		Throwable aux = t;
		while (aux != null) {
			if (type.isInstance(aux))
				return true;
			aux = aux.getCause();
		}
		return false;
	}

	public static void checkExpiredSession(Provider<HttpSession> httpSessionProvider) throws ActionException {
		checkExpiredSession(httpSessionProvider.get());
	}

	public static void checkExpiredSession(HttpSession session) throws ActionException {
		if (session.getAttribute(HttpCookies.SESSION_ID_KEY) == null) {
			throw new ActionException(Constants.SESSION_EXPIRED_FLAG + URLS.ROOT() + (URLS.LOCALHOST() ? URLS.LOGIN_LOCAL_PAGE : URLS.LOGIN_PAGE));
		}
	}

}
