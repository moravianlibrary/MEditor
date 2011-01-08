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

public class AuthenticationFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final String path = request.getServletPath();
		final HttpSession session = request.getSession(true);
		final Map parameters = req.getParameterMap();
		final String sessionId = (String) session.getAttribute(HttpCookies.SESSION_ID_KEY);
		final boolean sessionIdBool = sessionId != null;
		final boolean paramSizeGreaterThanOne = parameters.keySet().size() > 1;

		if (!URLS.LOCALHOST && sessionIdBool && paramSizeGreaterThanOne && (URLS.MAIN_PAGE.equals(path) || URLS.ROOT.equals(path))) {
			final String sufixSUrl = URLS.convertToAJAXURL(parameters);
			URLS.redirect((HttpServletResponse) res, "https://" + request.getServerName() + URLS.ROOT + path + sufixSUrl);
			return;
		}

		if (sessionIdBool || URLS.LOGIN_PAGE.equals(path) || URLS.AUTH_SERVLET.equals(path) || URLS.INFO_PAGE.equals(path)) {
			if (!URLS.LOCALHOST && "http".equals(request.getScheme()) && (URLS.MAIN_PAGE.equals(path) || URLS.ROOT.equals(path))) {
				URLS.redirect((HttpServletResponse) res, "https://" + request.getServerName() + URLS.ROOT + path);
				return;
			}
			chain.doFilter(req, res);
		} else {
			final HttpServletResponse response = (HttpServletResponse) res;
			if (paramSizeGreaterThanOne) {
				final String sufixSUrl = URLS.convertToAJAXURL(parameters);
				session.setAttribute(HttpCookies.TARGET_URL, (URLS.LOCALHOST ? "http://" : "https://") + request.getServerName() + URLS.ROOT + path + sufixSUrl);
			}
			URLS.redirect(response, (URLS.LOCALHOST ? "http://" : "https://") + request.getServerName() + URLS.ROOT + URLS.LOGIN_PAGE);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
