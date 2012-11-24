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

package cz.mzk.editor.server.handler;

import javax.inject.Inject;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.LockDAO;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.LockInfo;
import cz.mzk.editor.shared.rpc.action.GetLockInformationAction;
import cz.mzk.editor.shared.rpc.action.GetLockInformationResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class GetLockInformationHandler
        implements ActionHandler<GetLockInformationAction, GetLockInformationResult> {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(GetLockInformationHandler.class.getPackage()
            .toString());

    /** The user DAO **/
    @Inject
    private UserDAO userDAO;

    /** The locks DAO **/
    @Inject
    private LockDAO locksDAO;

    /**
     * {@inheritDoc}
     */

    @Override
    public GetLockInformationResult execute(GetLockInformationAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: GetLockInformationAction " + action.getUuid());
        ServerUtils.checkExpiredSession();

        String uuid = action.getUuid();

        long usersId = 0;
        try {
            usersId = userDAO.getUsersId();
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
                    return new GetLockInformationResult(new LockInfo("",
                                                                     locksDAO.getDescription(uuid),
                                                                     timeToExpiration));

                } else {
                    return new GetLockInformationResult(new LockInfo(userDAO.getName(lockOwnerId),
                                                                     locksDAO.getDescription(uuid),
                                                                     timeToExpiration));
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
