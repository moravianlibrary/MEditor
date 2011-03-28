package cz.fi.muni.xkremser.editor.server;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration.Constants;

public class SessionListener implements HttpSessionListener {

	private static final Logger LOGGER = Logger.getLogger(SessionListener.class);
	private static final Logger ACCESS_LOGGER = Logger.getLogger(Constants.ACCESS_LOG_ID);
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	@Override
	public void sessionCreated(HttpSessionEvent se) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		if (session.getAttribute(HttpCookies.SESSION_ID_KEY) != null) {
			session.setAttribute(HttpCookies.SESSION_ID_KEY, null);
			ACCESS_LOGGER.info("LOG OUT: User " + session.getAttribute(HttpCookies.NAME_KEY) + " with openID " + session.getAttribute(HttpCookies.SESSION_ID_KEY)
					+ " at " + FORMATTER.format(new Date()));
		}
		// LOGGER.debug("Session expired.");
	}
}