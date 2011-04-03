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

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// TODO: Auto-generated Javadoc
/**
 * The Class AuthenticationFilter.
 */
public class AuthenticationFilter implements Filter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final String path = request.getServletPath();
		final HttpSession session = request.getSession(true);
		final Map parameters = req.getParameterMap();
		final String sessionId = (String) session.getAttribute(HttpCookies.SESSION_ID_KEY);
		final boolean sessionIdBool = sessionId != null;
		final boolean paramSizeGreaterThanOne = parameters.keySet().size() > 1;

		if (!URLS.LOCALHOST() && sessionIdBool && paramSizeGreaterThanOne && (URLS.MAIN_PAGE.equals(path) || URLS.ROOT().equals(path))) {
			final String sufixUrl = URLS.convertToAJAXURL(parameters);
			URLS.redirect((HttpServletResponse) res, "https://" + request.getServerName() + URLS.ROOT() + path + sufixUrl);
			return;
		}

		// URLs allowed when user is not logged in
		if (sessionIdBool || URLS.nonRestricted.contains(path) || path.startsWith(URLS.REQUEST_PREFIX)) {
			if (!URLS.LOCALHOST() && "http".equals(request.getScheme()) && (URLS.MAIN_PAGE.equals(path) || URLS.ROOT().equals(path))) {
				URLS.redirect((HttpServletResponse) res, "https://" + request.getServerName() + URLS.ROOT() + path);
				return;
			}
			chain.doFilter(req, res);
		} else {
			final HttpServletResponse response = (HttpServletResponse) res;
			if (paramSizeGreaterThanOne) { // store parameters to session
				final String sufixSUrl = URLS.convertToAJAXURL(parameters);
				session.setAttribute(HttpCookies.TARGET_URL,
						(URLS.LOCALHOST() ? "http://" : "https://") + request.getServerName() + URLS.ROOT() + path
								+ (URLS.LOCALHOST() ? "?gwt.codesvr=127.0.0.1:9997" : "") + sufixSUrl);
			}
			// redirect to login page
			URLS.redirect(response, (URLS.LOCALHOST() ? "http://" : "https://") + request.getServerName() + URLS.ROOT()
					+ (URLS.LOCALHOST() ? URLS.LOGIN_LOCAL_PAGE : URLS.LOGIN_PAGE));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
