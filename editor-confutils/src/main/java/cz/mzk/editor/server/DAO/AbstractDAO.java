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

package cz.mzk.editor.server.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.http.HttpSession;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;

import com.google.inject.Provider;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.DEFAULT_SYSTEM_USERS;
import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.server.HttpCookies;
import cz.mzk.editor.server.config.EditorConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDAO.
 * 
 * @author Jiri Kremser
 */
public abstract class AbstractDAO {

    /** The conn. */
    private Connection conn = null;

    /** The conf. */
    @Inject
    private EditorConfiguration conf;

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(AbstractDAO.class);

    /** The Constant DRIVER. */
    private static final String DRIVER = "org.postgresql.Driver";

    /** Must be the same as in the META-INF/context.xml and WEB-INF/web.xml */
    private static final String JNDI_DB_POOL_ID = "jdbc/editor";

    /** The Constant FORMATTER with format: yyyy/MM/dd HH:mm:ss. */
    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /** The Constant POOLABLE_YES. */
    private static final int POOLABLE_YES = 1;

    /** The Constant POOLABLE_NO. */
    private static final int POOLABLE_NO = 0;

    /** The poolable. */
    private static int poolable = -1;

    /** The context is correct. */
    private static boolean contextIsCorrect = false;

    /** The pool. */
    @Resource(name = JNDI_DB_POOL_ID)
    private DataSource pool;

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    /**
     * Inits the connection.
     * 
     * @throws DatabaseException
     *         the database exception
     */
    private void initConnection() throws DatabaseException {
        if (poolable != POOLABLE_NO && pool == null) {
            if (conf != null && conf.isLocalhost()) {
                poolable = POOLABLE_NO;
                initConnection();
                return;
            }
            InitialContext cxt = null;
            try {
                cxt = new InitialContext();
            } catch (NamingException e) {
                poolable = POOLABLE_NO;
                LOGGER.warn("Unable to get initial context.", e);
            }
            if (cxt != null) {
                try {
                    pool = (DataSource) cxt.lookup("java:/comp/env/" + JNDI_DB_POOL_ID);
                } catch (NamingException e) {
                    poolable = POOLABLE_NO;
                    LOGGER.warn("Unable to get connection pool.", e);
                }
            }
        }
        if (poolable == POOLABLE_NO || pool == null) { // DI is working and servlet container manages the
            // connections
            poolable = POOLABLE_NO;
            initConnectionWithoutPool();
        } else {
            poolable = POOLABLE_YES;
            try {
                conn = pool.getConnection();
            } catch (SQLException ex) {
                LOGGER.error("Unable to get a connection from connection pool " + JNDI_DB_POOL_ID, ex);
                poolable = POOLABLE_NO;
                pool = null;
                initConnectionWithoutPool();
            }
        }
    }

    /**
     * Inits the connection without pool.
     * 
     * @throws DatabaseException
     *         the database exception
     */
    private void initConnectionWithoutPool() throws DatabaseException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            LOGGER.error("Could not find the driver " + DRIVER, ex);
        }
        String login = conf.getDBLogin();
        String password = conf.getDBPassword();
        String host = conf.getDBHost();
        String port = conf.getDBPort();
        String name = conf.getDBName();

        if (!contextIsCorrect && !conf.isLocalhost() && pool == null && login != null && password != null
                && port != null && name != null) {
            createCorrectContext(login, password, port, name);
        }

        if (password == null || password.length() < 3) {
            LOGGER.error("Unable to connect to database at 'jdbc:postgresql://" + host + ":" + port + "/"
                    + name + "' reason: no password set.");
            return;
        }
        try {
            conn =
                    DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + name,
                                                login,
                                                password);
        } catch (SQLException ex) {
            LOGGER.error("Unable to connect to database at 'jdbc:postgresql://" + host + ":" + port + "/"
                    + name + "'", ex);
            throw new DatabaseException("Unable to connect to database.");
        }
    }

    /**
     * Gets the connection.
     * 
     * @return the connection
     * @throws DatabaseException
     *         the database exception
     */
    protected Connection getConnection() throws DatabaseException {
        if (conn == null) {
            initConnection();
        }
        return conn;
    }

    /**
     * Close connection.
     */
    protected void closeConnection() {
        try {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch (SQLException ex) {
            LOGGER.error("Connection was not closed", ex);
        }
        conn = null;
    }

    /**
     * Creates the correct context.
     * 
     * @param login
     *        the login
     * @param password
     *        the password
     * @param port
     *        the port
     * @param name
     *        the name
     */
    private void createCorrectContext(String login, String password, String port, String name) {
        String pathPrefix = System.getProperty("catalina.home");
        boolean changed = false;

        InputStream is = null;
        File contextFile =
                new File(pathPrefix + File.separator + "conf" + File.separator + "Catalina" + File.separator
                        + "localhost" + File.separator + "meditor.xml");
        Document contextDoc = null;
        DocumentBuilder builder;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            if (contextFile.exists()) {

                try {

                    is = new FileInputStream(contextFile);
                    contextDoc = builder.parse(is);

                } catch (FileNotFoundException e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();

                } catch (SAXException e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();

                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                }
            }

            if (contextDoc == null) {
                contextDoc = builder.newDocument();
                changed = true;
            }

            NodeList contextEls = contextDoc.getElementsByTagName("Context");
            Element contextEl;
            if (contextEls == null || contextEls.getLength() == 0 || contextEls.item(0) == null) {
                contextEl = contextDoc.createElement("Context");
                contextEl.setAttribute("antiJARLocking", "true");
                contextEl.setAttribute("path", "/meditor");
                contextDoc.appendChild(contextEl);
                changed = true;

            } else {
                contextEl = (Element) contextEls.item(0);
            }

            NodeList resourceEls = contextEl.getElementsByTagName("Resource");
            Element resourceEl;
            if (resourceEls == null || resourceEls.getLength() == 0 || resourceEls.item(0) == null) {
                resourceEl = contextDoc.createElement("Resource");
                resourceEl.setAttribute("name", "jdbc/editor");
                resourceEl.setAttribute("auth", "Container");
                resourceEl.setAttribute("type", "javax.sql.DataSource");
                resourceEl.setAttribute("driverClassName", "org.postgresql.Driver");
                resourceEl.setAttribute("maxActive", "40");
                resourceEl.setAttribute("maxIdle", "20");
                resourceEl.setAttribute("maxWait", "7500");
                resourceEl.setAttribute("removeAbandoned", "true");
                resourceEl.setAttribute("removeAbandonedTimeout", "100");
                resourceEl.setAttribute("logAbandoned", "true");
                contextEl.appendChild(resourceEl);
                changed = true;

            } else {
                resourceEl = (Element) resourceEls.item(0);
            }

            String usernameAttr = resourceEl.getAttribute("username");
            if (usernameAttr == null || !login.equals(usernameAttr)) {
                resourceEl.setAttribute("username", login);
                changed = true;
            }

            String passwordAttr = resourceEl.getAttribute("password");
            if (passwordAttr == null || !password.equals(passwordAttr)) {
                resourceEl.setAttribute("password", password);
                changed = true;
            }

            String url = "jdbc:postgresql://localhost:" + port + "/" + name;
            String urlAttr = resourceEl.getAttribute("url");
            if (urlAttr == null || !url.equals(urlAttr)) {
                resourceEl.setAttribute("url", url);
                changed = true;
            }

            if (changed) {
                // write the context.xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(contextDoc);
                Result result = new StreamResult(contextFile.toURI().getPath());
                contextFile.exists();
                transformer.transform(source, result);
            }

        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (TransformerException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        contextIsCorrect = true;
        poolable = POOLABLE_YES;
    }

    /**
     * Gets the users id.
     * 
     * @param identifier
     *        the identifier
     * @param type
     *        the type
     * @return the users id
     * @throws DatabaseException
     *         the database exception
     */
    protected long getUsersId(String identifier, USER_IDENTITY_TYPES type) throws DatabaseException {
        PreparedStatement selectSt = null;
        long userId = -1;
        try {
            StringBuffer sql = new StringBuffer("SELECT editor_user_id FROM ");

            switch (type) {
                case OPEN_ID:
                    sql.append(Constants.TABLE_OPEN_ID_IDENTITY);
                    break;
                case SHIBBOLETH:
                    sql.append(Constants.TABLE_SHIBBOLETH_IDENTITY);
                    break;
                case LDAP:
                    sql.append(Constants.TABLE_LDAP_IDENTITY);
                    break;
                default:
                    return DEFAULT_SYSTEM_USERS.NON_EXISTENT.getUserId();
            }
            sql.append(" WHERE identity = (?)");
            selectSt = getConnection().prepareStatement(sql.toString());
            selectSt.setString(1, identifier);

        } catch (SQLException e) {
            LOGGER.error("Could not get select statement", e);
        }
        try {
            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                userId = rs.getLong("editor_user_id");
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return userId;
    }

    /**
     * Gets the user id.
     * 
     * @return the user id
     * @throws DatabaseException
     *         the database exception
     */
    protected Long getUserId() throws DatabaseException {
        String openID = (String) httpSessionProvider.get().getAttribute(HttpCookies.SESSION_ID_KEY);
        return getUsersId(openID, USER_IDENTITY_TYPES.OPEN_ID);
    }

    /**
     * Format date with format: yyyy/MM/dd HH:mm:ss.
     * 
     * @param date
     *        the date
     * @return the string
     */
    /**
     * Format date with format: yyyy/MM/dd HH:mm:ss.
     * 
     * @param date
     *        the date
     * @return the string
     */
    protected String formatDate(java.sql.Date date) {
        return FORMATTER.format(date);
    }

    /**
     * Format timestamp.
     * 
     * @param timestamp
     *        the timestamp
     * @return the string
     */
    protected String formatTimestamp(Timestamp timestamp) {
        return FORMATTER.format(timestamp);
    }
}
