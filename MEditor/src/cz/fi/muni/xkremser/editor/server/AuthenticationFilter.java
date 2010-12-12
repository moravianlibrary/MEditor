package cz.fi.muni.xkremser.editor.server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String sessionId = HttpCookies.getCookieValue(request, HttpCookies.SESSION_ID_KEY);
		// "http".equals(request.getScheme())
		if (sessionId != null || "/login.html".equals(request.getServletPath()) || "/auth".equals(request.getServletPath())) {
			chain.doFilter(req, res);
		} else {
			HttpServletResponse response = (HttpServletResponse) res;
			response.setContentType("text/plain");
			response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", "/login.html");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
