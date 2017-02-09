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

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import cz.mzk.editor.server.DAO.DAOUtils;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.LockDAO;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.UserProvider;
import cz.mzk.editor.shared.rpc.LockInfo;
import cz.mzk.editor.shared.rpc.action.GetLockInformationAction;
import cz.mzk.editor.shared.rpc.action.GetLockInformationResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Matous Jobanek
 * @version $Id$
 */
@Service
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

    /** The dao utils. */
    @Inject
    private DAOUtils daoUtils;

    @Inject
    private UserProvider userProvider;

    /**
     * {@inheritDoc}
     */

    @Override
    public GetLockInformationResult execute(GetLockInformationAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: GetLockInformationAction " + action.getUuid());

        String uuid = action.getUuid();

        long usersId = userProvider.getUserId();

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
                    return new GetLockInformationResult(new LockInfo(userProvider.getName(),
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
