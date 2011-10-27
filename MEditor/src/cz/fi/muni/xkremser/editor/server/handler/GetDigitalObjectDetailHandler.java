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

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.ConnectionException;

import cz.fi.muni.xkremser.editor.server.HttpCookies;
import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.DAO.LocksDAO;
import cz.fi.muni.xkremser.editor.server.DAO.UserDAO;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;
import cz.fi.muni.xkremser.editor.server.modelHandler.DigitalObjectHandler;

import cz.fi.muni.xkremser.editor.shared.rpc.DigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDescriptionAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDescriptionResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDigitalObjectDetailAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDigitalObjectDetailResult;

// TODO: Auto-generated Javadoc
/**
 * The Class GetDigitalObjectDetailHandler.
 */
public class GetDigitalObjectDetailHandler
        implements ActionHandler<GetDigitalObjectDetailAction, GetDigitalObjectDetailResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(GetDigitalObjectDetailHandler.class.getPackage()
            .toString());

    /** The DigitalObject handler. */
    private final DigitalObjectHandler objectHandler;

    /** The GetDescriptionHandler handler. */
    private final GetDescriptionHandler descritptionHandler;

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    /** The locks DAO **/
    @Inject
    private LocksDAO locksDAO;

    /** The user DAO **/
    @Inject
    private UserDAO userDAO;

    /**
     * Instantiates a new gets the digital object detail handler.
     * 
     * @param handler
     *        the handler
     */
    @Inject
    public GetDigitalObjectDetailHandler(final DigitalObjectHandler objectHandler) {
        this.objectHandler = objectHandler;
        this.descritptionHandler = new GetDescriptionHandler();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public GetDigitalObjectDetailResult execute(final GetDigitalObjectDetailAction action,
                                                final ExecutionContext context) throws ActionException {
        // parse input
        String uuid = action.getUuid();
        LOGGER.debug("Processing action: GetDigitalObjectDetailAction: " + action.getUuid());

        try {
            HttpSession ses = httpSessionProvider.get();
            Injector injector = (Injector) ses.getServletContext().getAttribute(Injector.class.getName());
            injector.injectMembers(descritptionHandler);
            ServerUtils.checkExpiredSession(ses);
            DigitalObjectDetail obj = null;
            if (action.getModel() == null) { // lazy
                obj = objectHandler.getDigitalObject(uuid);
            } else { // fetch uuids
                obj = objectHandler.getDigitalObjectItems(uuid, action.getModel());
            }
            GetDescriptionResult result =
                    descritptionHandler.execute(new GetDescriptionAction(uuid), context);

            String description = result.getUserDescription();
            Date modified = result.getModified();

            long usersId = 0;
            try {
                usersId = userDAO.getUsersId(String.valueOf(ses.getAttribute(HttpCookies.SESSION_ID_KEY)));
            } catch (DatabaseException e) {
                throw new ActionException(e);
            }

            long lockOwnerId = 0;
            lockOwnerId = locksDAO.getLockOwnersID(uuid);
            String lockDescription = "";

            if (lockOwnerId > 0) {
                lockDescription = locksDAO.getDescription(uuid);
                obj.setTimeToExpirationLock(locksDAO.getTimeToExpirationLock(uuid));
                if (usersId == lockOwnerId) {
                    lockDescription = locksDAO.getDescription(uuid);
                    obj.setLockOwner("");
                    obj.setLockDescription(lockDescription);

                } else {
                    obj.setLockOwner(userDAO.getName(String.valueOf(lockOwnerId), false));
                    obj.setLockDescription(lockDescription);

                }
            }

            return new GetDigitalObjectDetailResult(obj,
                                                    description == null ? "" : description,
                                                    modified == null ? new Date() : modified);
        } catch (IOException e) {
            String msg = null;
            if (ServerUtils.isCausedByException(e, FileNotFoundException.class)) {
                msg = "Digital object with uuid " + uuid + " is not present in the repository. ";
            } else if (ServerUtils.isCausedByException(e, ConnectionException.class)) {
                msg = "Connection cannot be established. Please check whether Fedora is running. ";
            } else {
                msg = "Unable to obtain digital object with uuid " + uuid + ". ";
            }
            LOGGER.error(msg, e);
            throw new ActionException(msg, e);
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<GetDigitalObjectDetailAction> getActionType() {
        return GetDigitalObjectDetailAction.class;
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
    public void undo(GetDigitalObjectDetailAction action,
                     GetDigitalObjectDetailResult result,
                     ExecutionContext context) throws ActionException {
        // idempotency -> no need for undo
    }
}