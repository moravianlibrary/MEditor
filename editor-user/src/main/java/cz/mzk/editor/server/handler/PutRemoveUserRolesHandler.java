/*
 * Metadata Editor
 * @author Matous Jobanek
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (Matous.Jobanek@mzk.cz)
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
import cz.mzk.editor.shared.rpc.RoleItem;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRolesAction;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRolesResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class PutRemoveUserRolesHandler
        implements ActionHandler<PutRemoveUserRolesAction, PutRemoveUserRolesResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(PutRemoveUserRolesHandler.class.getPackage()
            .toString());

    @Inject
    private UserDAO userDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public PutRemoveUserRolesResult execute(final PutRemoveUserRolesAction action,
                                            final ExecutionContext context) throws ActionException {

        LOGGER.debug("Processing action: PutRemoveUserRolesAction");
        ServerUtils.checkExpiredSession();

        if (action.getRoleItems() == null) throw new NullPointerException("getRoleItems()");

        boolean successful = true;
        for (RoleItem role : action.getRoleItems()) {

            try {
                successful &= userDAO.addRemoveUserRoleItem(role, action.isToAdd());
            } catch (NumberFormatException e) {
                throw new ActionException(e);
            } catch (DatabaseException e) {
                throw new ActionException(e);
            } catch (UnsupportedDataTypeException e) {
                throw new ActionException(e);
            }
        }

        return new PutRemoveUserRolesResult(successful);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<PutRemoveUserRolesAction> getActionType() {
        return PutRemoveUserRolesAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(PutRemoveUserRolesAction action,
                     PutRemoveUserRolesResult result,
                     ExecutionContext context) throws ActionException {
        // TODO undo method

    }
}