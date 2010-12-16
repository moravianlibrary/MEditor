package cz.fi.muni.xkremser.editor.server;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.inject.Inject;

import cz.fi.muni.xkremser.editor.server.DAO.UserDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;

public class AuthenticationServlet extends HttpServlet {

	public static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(AuthenticationServlet.class.getName());

	@Inject
	private static UserDAO userDAO;

	@Inject
	private static EditorConfiguration configuration;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// super.doPost(req, resp);
		HttpSession session = req.getSession(true);
		String url = (String) session.getAttribute(HttpCookies.TARGET_URL);
		session.setAttribute(HttpCookies.TARGET_URL, null);
		String token = req.getParameter("token");

		String appID = configuration.getOpenIDApiKey();
		String openIdurl = configuration.getOpenIDApiURL();
		RPX rpx = new RPX(appID, openIdurl);
		Element e = rpx.authInfo(token);
		String idXPath = "//identifier";
		XPathFactory xpfactory = XPathFactory.newInstance();
		XPath xpath = xpfactory.newXPath();
		String identifier = null;
		try {
			XPathExpression expr = xpath.compile(idXPath);
			NodeList nodes = (NodeList) expr.evaluate(e.getOwnerDocument(), XPathConstants.NODESET);
			Element idElement = null;
			if (nodes.getLength() != 0) {
				idElement = (Element) nodes.item(0);
			}
			if (idElement != null) {
				identifier = idElement.getTextContent();
			}
		} catch (XPathExpressionException e1) {
			e1.printStackTrace();
		}
		if (identifier != null && !"".equals(identifier)) {
			LOGGER.log(Level.INFO, "Logged user with openID " + identifier);
			int userStatus = userDAO.isSupported(identifier);
			switch (userStatus) {
				case UserDAO.USER:
					// HttpCookies.setCookie(req, resp, HttpCookies.SESSION_ID_KEY,
					// identifier);
					session.setAttribute(HttpCookies.SESSION_ID_KEY, identifier);
					URLS.redirect(resp, url == null ? URLS.ROOT : url);
				break;
				case UserDAO.ADMIN:
					// HttpCookies.setCookie(req, resp, HttpCookies.SESSION_ID_KEY,
					// identifier);
					// HttpCookies.setCookie(req, resp, HttpCookies.ADMIN,
					// HttpCookies.ADMIN_YES);
					session.setAttribute(HttpCookies.SESSION_ID_KEY, identifier);
					session.setAttribute(HttpCookies.ADMIN, HttpCookies.ADMIN_YES);
					URLS.redirect(resp, url == null ? URLS.ROOT : url);
				break;
				case UserDAO.NOT_PRESENT:
				default:
					URLS.redirect(resp, URLS.INFO_PAGE);
				break;
			}
		} else {
			URLS.redirect(resp, URLS.LOGIN_PAGE);
		}

		// System.out.println("ID:" + identifier);

		// if user is supported redirect to homepage else show him a page that he
		// has to be added to system first by admin

	}

}
