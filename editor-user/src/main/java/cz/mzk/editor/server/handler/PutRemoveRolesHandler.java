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

import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.RoleItem;
import cz.mzk.editor.shared.rpc.action.PutRemoveRolesAction;
import cz.mzk.editor.shared.rpc.action.PutRemoveRolesResult;

/**
 * @author Matous Jobanek
 * @version Nov 27, 2012
 */
public class PutRemoveRolesHandler
        implements ActionHandler<PutRemoveRolesAction, PutRemoveRolesResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger
            .getLogger(PutRemoveRolesHandler.class.getPackage().toString());

    /** The recently modified dao. */
    @Inject
    private UserDAO userDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public PutRemoveRolesResult execute(PutRemoveRolesAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: PutRemoveRolesResult");
        ServerUtils.checkExpiredSession();

        if (!ServerUtils.checkUserRightOrAll(EDITOR_RIGHTS.EDIT_ROLES)) {
            LOGGER.warn("Bad authorization in " + this.getClass().toString());
            throw new ActionException("Bad authorization in " + this.getClass().toString());
        }

        boolean success = true;

        if (action.getRoleItems() != null) {
            for (RoleItem roleItem : action.getRoleItems()) {
                try {
                    success &= userDAO.addRemoveRoleItem(roleItem, action.isToPut());
                } catch (DatabaseException e) {
                    success = false;
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        return new PutRemoveRolesResult(success);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<PutRemoveRolesAction> getActionType() {
        return PutRemoveRolesAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(PutRemoveRolesAction action, PutRemoveRolesResult result, ExecutionContext context)
            throws ActionException {
        // TODO Auto-generated method stub

    }

}
