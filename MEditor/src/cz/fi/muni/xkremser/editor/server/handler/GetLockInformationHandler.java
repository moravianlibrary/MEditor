/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
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

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.HttpCookies;
import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.DAO.LocksDAO;
import cz.fi.muni.xkremser.editor.server.DAO.UserDAO;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

import cz.fi.muni.xkremser.editor.shared.rpc.LockInfo;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetLockInformationAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetLockInformationResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class GetLockInformationHandler
        implements ActionHandler<GetLockInformationAction, GetLockInformationResult> {

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    /** The user DAO **/
    @Inject
    private UserDAO userDAO;

    /** The locks DAO **/
    @Inject
    private LocksDAO locksDAO;

    /**
     * {@inheritDoc}
     */

    @Override
    public GetLockInformationResult execute(GetLockInformationAction action, ExecutionContext context)
            throws ActionException {

        String uuid = action.getUuid();

        HttpSession ses = httpSessionProvider.get();
        ServerUtils.checkExpiredSession(ses);

        long usersId = 0;
        try {
            usersId = userDAO.getUsersId(String.valueOf(ses.getAttribute(HttpCookies.SESSION_ID_KEY)));
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }

        long lockOwnerId = 0;
        try {

            lockOwnerId = locksDAO.getLockOwnersID(uuid);
            if (lockOwnerId == 0) {

                return new GetLockInformationResult(new LockInfo(null, null, null));

            } else {
                String[] timeToExpiration = locksDAO.getTimeToExpirationLock(uuid);
                if (usersId == lockOwnerId) {
                    return new GetLockInformationResult(new LockInfo("", null, timeToExpiration));

                } else {
                    return new GetLockInformationResult(new LockInfo(userDAO.getName(String
                            .valueOf(lockOwnerId), false), locksDAO.getDescription(uuid), timeToExpiration));
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
    public Class<GetLockInformationAction> getActionType() {
        return GetLockInformationAction.class;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void undo(GetLockInformationAction arg0, GetLockInformationResult arg1, ExecutionContext arg2)
            throws ActionException {
        // TODO Auto-generated method stub
    }

}
