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

import javax.activation.UnsupportedDataTypeException;
import javax.inject.Inject;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRightsAction;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRightsResult;

/**
 * @author Matous Jobanek
 * @version Nov 27, 2012
 */
public class PutRemoveUserRightsHandler
        implements ActionHandler<PutRemoveUserRightsAction, PutRemoveUserRightsResult> {

    @Inject
    private UserDAO userDAO;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(PutRemoveUserRightsHandler.class.getPackage()
            .toString());

    /**
     * {@inheritDoc}
     */
    @Override
    public PutRemoveUserRightsResult execute(PutRemoveUserRightsAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: PutRemoveUserRightsAction");
        ServerUtils.checkExpiredSession();

        boolean successful = true;
        try {
            for (String right : action.getRightNames()) {
                successful &=
                        userDAO.addRemoveUserRightItem(right,
                                                       Long.parseLong(action.getUserId()),
                                                       action.isPut());
            }
        } catch (NumberFormatException e) {
            successful = false;
            throw new ActionException(e);
        } catch (DatabaseException e) {
            successful = false;
            throw new ActionException(e);
        } catch (UnsupportedDataTypeException e) {
            successful = false;
            throw new ActionException(e);
        }

        return new PutRemoveUserRightsResult(successful);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<PutRemoveUserRightsAction> getActionType() {
        return PutRemoveUserRightsAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(PutRemoveUserRightsAction action,
                     PutRemoveUserRightsResult result,
                     ExecutionContext context) throws ActionException {
        // TODO Auto-generated method stub
    }

}
