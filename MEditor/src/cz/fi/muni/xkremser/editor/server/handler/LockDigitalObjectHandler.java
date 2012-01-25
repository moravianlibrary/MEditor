/*
 * Metadata Editor
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

import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import com.google.inject.Injector;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.server.HttpCookies;
import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.DAO.LockDAO;
import cz.fi.muni.xkremser.editor.server.DAO.UserDAO;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

import cz.fi.muni.xkremser.editor.shared.rpc.LockInfo;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetLockInformationAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.LockDigitalObjectAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.LockDigitalObjectResult;

/**
 * @author Jiri Kremser
 * @version $Id$
 */

public class LockDigitalObjectHandler
        implements ActionHandler<LockDigitalObjectAction, LockDigitalObjectResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(LockDigitalObjectHandler.class.getPackage()
            .toString());

    /** The locks DAO **/
    @Inject
    private LockDAO locksDAO;

    /** The user DAO **/
    @Inject
    private UserDAO userDAO;

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    /** The GetLockInformationHandler handler */
    private final GetLockInformationHandler getLockInformationHandler;

    /** Instantiate a new lock digital object handler **/
    @Inject
    public LockDigitalObjectHandler() {
        this.getLockInformationHandler = new GetLockInformationHandler();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public LockDigitalObjectResult execute(LockDigitalObjectAction action, ExecutionContext context)
            throws ActionException {

        String uuid = action.getUuid();
        String description = (action.getDescription() == null ? "" : action.getDescription());

        HttpSession ses = httpSessionProvider.get();
        ServerUtils.checkExpiredSession(ses);

        Injector injector = (Injector) ses.getServletContext().getAttribute(Injector.class.getName());
        injector.injectMembers(getLockInformationHandler);

        LockInfo lockInfo =
                getLockInformationHandler.execute(new GetLockInformationAction(uuid), context).getLockInfo();

        long usersId = 0;
        try {
            usersId = userDAO.getUsersId(String.valueOf(ses.getAttribute(HttpCookies.SESSION_ID_KEY)));
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }

        boolean successful = false;
        try {

            if (lockInfo.getLockOwner() == null) {

                successful = locksDAO.lockDigitalObject(uuid, usersId, description);
                LOGGER.debug("Processing action: LockDigitalObject: " + uuid + " has been successful="
                        + successful);
                return new LockDigitalObjectResult(lockInfo);

            } else {
                if ("".equals(lockInfo.getLockOwner())) {
                    successful = locksDAO.lockDigitalObject(uuid, null, description);
                    LOGGER.debug("Processing action: LockDigitalObject: " + uuid + " has been successful="
                            + successful);
                    return new LockDigitalObjectResult(lockInfo);

                } else {
                    return new LockDigitalObjectResult(lockInfo);
                }
            }
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Class<LockDigitalObjectAction> getActionType() {
        return LockDigitalObjectAction.class;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void undo(LockDigitalObjectAction arg0, LockDigitalObjectResult arg1, ExecutionContext arg2)
            throws ActionException {
        // TODO Auto-generated method stub
    }

}
