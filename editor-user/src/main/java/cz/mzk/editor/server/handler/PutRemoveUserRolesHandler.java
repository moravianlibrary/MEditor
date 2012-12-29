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

import java.util.List;

import javax.activation.UnsupportedDataTypeException;
import javax.inject.Inject;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.RoleItem;
import cz.mzk.editor.shared.rpc.action.GetUserRolesRightsIdentitiesAction;
import cz.mzk.editor.shared.rpc.action.GetUserRolesRightsIdentitiesResult;
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

    /** The user dao. */
    @Inject
    private UserDAO userDAO;

    /** The configuration. */
    @Inject
    private EditorConfiguration configuration;

    /**
     * {@inheritDoc}
     */
    @Override
    public PutRemoveUserRolesResult execute(final PutRemoveUserRolesAction action,
                                            final ExecutionContext context) throws ActionException {

        LOGGER.debug("Processing action: PutRemoveUserRolesAction");
        ServerUtils.checkExpiredSession();

        if (!ServerUtils.checkUserRightOrAll(EDITOR_RIGHTS.EDIT_USERS)) {
            LOGGER.warn("Bad authorization in " + this.getClass().toString());
            throw new ActionException("Bad authorization in " + this.getClass().toString());
        }

        if (action.getRoleItems() == null) throw new NullPointerException("getRoleItems()");

        boolean successful = true;
        for (RoleItem role : action.getRoleItems()) {

            try {
                successful &= userDAO.addRemoveUserRoleItem(role, action.isToAdd());
            } catch (NumberFormatException e) {
                LOGGER.warn(e);
                throw new ActionException(e);
            } catch (DatabaseException e) {
                LOGGER.warn(e);
                throw new ActionException(e);
            } catch (UnsupportedDataTypeException e) {
                LOGGER.warn(e);
                throw new ActionException(e);
            }
        }

        List<RoleItem> roles = null;
        if (!action.isToAdd()) {
            GetUserRolesRightsIdentitiesHandler getRolesHandler =
                    new GetUserRolesRightsIdentitiesHandler(configuration, userDAO);
            GetUserRolesRightsIdentitiesAction getRolesAction =
                    new GetUserRolesRightsIdentitiesAction(action.getRoleItems().get(0).getUserId()
                            .toString(), null, true);
            GetUserRolesRightsIdentitiesResult rolesResult = getRolesHandler.execute(getRolesAction, context);
            roles = rolesResult.getRoles();
        }

        return new PutRemoveUserRolesResult(successful, roles);
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