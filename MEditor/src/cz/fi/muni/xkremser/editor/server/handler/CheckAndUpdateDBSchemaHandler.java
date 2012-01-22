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

package cz.fi.muni.xkremser.editor.server.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.DAO.DBSchemaDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAndUpdateDBSchemaAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAndUpdateDBSchemaResult;

/**
 * The class CheckAndUpdateDBSchemaHandler
 * 
 * @author Jiri Kremser
 * @version 17. 1. 2011
 */
public class CheckAndUpdateDBSchemaHandler
        implements ActionHandler<CheckAndUpdateDBSchemaAction, CheckAndUpdateDBSchemaResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(CheckAndUpdateDBSchemaHandler.class.getPackage()
            .toString());

    private static final Object LOCK = CheckAndUpdateDBSchemaHandler.class;

    @Inject
    private DBSchemaDAO dbSchemaDao;

    /** The configuration. */
    private final EditorConfiguration configuration;

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    @Inject
    private Provider<ServletContext> contextProvider;

    /**
     * Instantiates a new scan input queue handler.
     * 
     * @param configuration
     *        the configuration
     */
    @Inject
    public CheckAndUpdateDBSchemaHandler(final EditorConfiguration configuration) {
        this.configuration = configuration;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public CheckAndUpdateDBSchemaResult execute(final CheckAndUpdateDBSchemaAction action,
                                                final ExecutionContext context) throws ActionException {
        boolean success = false;
        int version = -1;
        synchronized (LOCK) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Processing action: CheckAndUpdateDBSchemaAction");
            }
            HttpSession ses = httpSessionProvider.get();
            ServerUtils.checkExpiredSession(ses);
            ServletContext servletContext = contextProvider.get();
            String pathPrefix = servletContext.getRealPath("/WEB-INF/classes/");
            if (dbSchemaDao.canConnect()) {
                version = getVersion(pathPrefix);
                boolean upToDate = false;
                try {
                    upToDate = dbSchemaDao.checkVersion(version);
                } catch (DatabaseException e) {
                    LOGGER.error(e.getMessage(), e);
                    e.printStackTrace();
                    upToDate = false;
                }

                if (!upToDate) {
                    try {
                        dbSchemaDao.updateSchema(version, pathPrefix);
                        success = true;
                    } catch (DatabaseException e) {
                        LOGGER.error(e.getMessage(), e);
                        e.printStackTrace();
                        throw new ActionException("Unable update the DB schema", e);
                    }
                }
            }
        }
        return new CheckAndUpdateDBSchemaResult(success, String.valueOf(version));
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<CheckAndUpdateDBSchemaAction> getActionType() {
        return CheckAndUpdateDBSchemaAction.class;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
     * gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.shared.Result,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public void undo(CheckAndUpdateDBSchemaAction action,
                     CheckAndUpdateDBSchemaResult result,
                     ExecutionContext context) throws ActionException {
        // TODO Auto-generated method stub

    }

    private int getVersion(String pathPrefix) throws ActionException {
        File dbSchema = new File(pathPrefix + File.separator + Constants.SCHEMA_VERSION_PATH);
        if (!dbSchema.exists()) {
            throw new ActionException("Unable to find the file with DB schema version "
                    + dbSchema.getAbsolutePath());
        }
        if (!dbSchema.canWrite()) {
            throw new ActionException("Unable to write to the file with DB schema version "
                    + dbSchema.getAbsolutePath());
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(dbSchema));
            return Integer.parseInt(reader.readLine());
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new ActionException("Unable to find the file with DB schema version "
                    + dbSchema.getAbsolutePath(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new ActionException("Unable to read from file with DB schema version "
                    + dbSchema.getAbsolutePath(), e);
        } catch (NumberFormatException nfe) {
            LOGGER.error(nfe.getMessage());
            nfe.printStackTrace();
            throw new ActionException("Unable to convert the content of the first line in "
                    + dbSchema.getAbsolutePath() + " to a number", nfe);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                    e.printStackTrace();
                    reader = null;
                }
            }
        }
    }
}