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

// TODO: Auto-generated Javadoc
/**
 * The Class AuthenticationServlet.
 */
public class AuthenticationServlet extends HttpServlet {

	/** The Constant LOGGER. */
	public static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(AuthenticationServlet.class.getName());

	/** The user dao. */
	@Inject
	private static UserDAO userDAO;

	/** The configuration. */
	@Inject
	private static EditorConfiguration configuration;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
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
		String nameXPath = "//displayName";
		XPathFactory xpfactory = XPathFactory.newInstance();
		XPath xpath = xpfactory.newXPath();
		String identifier = null;
		String name = null;
		try {
			XPathExpression expr1 = xpath.compile(idXPath);
			XPathExpression expr2 = xpath.compile(nameXPath);
			NodeList nodes1 = (NodeList) expr1.evaluate(e.getOwnerDocument(), XPathConstants.NODESET);
			NodeList nodes2 = (NodeList) expr2.evaluate(e.getOwnerDocument(), XPathConstants.NODESET);
			Element el = null;
			if (nodes1.getLength() != 0) {
				el = (Element) nodes1.item(0);
			}
			if (el != null) {
				identifier = el.getTextContent();
			}
			if (nodes2.getLength() != 0) {
				el = (Element) nodes2.item(0);
			}
			if (el != null) {
				name = el.getTextContent();
			}
		} catch (XPathExpressionException e1) {
			e1.printStackTrace();
		}
		String root = (URLS.LOCALHOST() ? "http://" : "https://") + req.getServerName()
				+ (URLS.LOCALHOST() ? (req.getServerPort() == 80 || req.getServerPort() == 443 ? "" : (":" + req.getServerPort())) : "") + URLS.ROOT;
		if (identifier != null && !"".equals(identifier)) {
			LOGGER.log(Level.INFO, "Logged user with openID " + identifier);
			int userStatus = userDAO.isSupported(identifier);
			switch (userStatus) {
				case UserDAO.USER:
					// HttpCookies.setCookie(req, resp, HttpCookies.SESSION_ID_KEY,
					// identifier);
					session.setAttribute(HttpCookies.SESSION_ID_KEY, identifier);
					URLS.redirect(resp, url == null ? root : url);
				break;
				case UserDAO.ADMIN:
					// HttpCookies.setCookie(req, resp, HttpCookies.SESSION_ID_KEY,
					// identifier);
					// HttpCookies.setCookie(req, resp, HttpCookies.ADMIN,
					// HttpCookies.ADMIN_YES);
					session.setAttribute(HttpCookies.SESSION_ID_KEY, identifier);
					session.setAttribute(HttpCookies.ADMIN, HttpCookies.ADMIN_YES);
					URLS.redirect(resp, url == null ? root : url);
				break;
				case UserDAO.NOT_PRESENT:
				default:
					session.setAttribute(HttpCookies.UNKNOWN_ID_KEY, identifier);
					session.setAttribute(HttpCookies.UNKNOWN_NAME_KEY, name);
					URLS.redirect(resp, root + URLS.INFO_PAGE);
				break;
			}
		} else {
			URLS.redirect(resp, root + URLS.LOGIN_PAGE);
		}

		// System.out.println("ID:" + identifier);

		// if user is supported redirect to homepage else show him a page that he
		// has to be added to system first by admin

	}
}
