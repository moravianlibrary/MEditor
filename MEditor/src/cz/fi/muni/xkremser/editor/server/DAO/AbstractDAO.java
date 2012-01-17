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

package cz.fi.muni.xkremser.editor.server.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

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

    private static final String DRIVER = "org.postgresql.Driver";

    /** Must be the same as in the META-INF/context.xml and WEB-INF/web.xml */
    private static final String JNDI_DB_POOL_ID = "jdbc/editor";

    private static final int POOLABLE_YES = 1;
    private static final int POOLABLE_NO = 0;
    private static int poolable = -1;

    @Resource(name = JNDI_DB_POOL_ID)
    private DataSource pool;

    /**
     * Inits the connection.
     * 
     * @throws DatabaseException
     */
    private void initConnection() throws DatabaseException {
        if (poolable != POOLABLE_NO && pool == null) {
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
        if (pool == null) { // DI is working and servlet container manages the
                            // connections
            poolable = POOLABLE_NO;
            initConnectionWithoutPool();
        } else {
            poolable = POOLABLE_YES;
            try {
                conn = pool.getConnection();
            } catch (SQLException ex) {
                LOGGER.error("Unable to get a connection from connection pool " + JNDI_DB_POOL_ID, ex);
                throw new DatabaseException("Unable to connect to database.", ex);
            }
        }
    }

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

}
