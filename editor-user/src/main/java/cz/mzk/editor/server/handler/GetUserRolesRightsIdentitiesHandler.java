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

package cz.mzk.editor.server.handler;

import java.util.ArrayList;
import java.util.List;

import javax.activation.UnsupportedDataTypeException;
import javax.inject.Inject;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.RoleItem;
import cz.mzk.editor.shared.rpc.UserIdentity;
import cz.mzk.editor.shared.rpc.action.GetUserRolesRightsIdentitiesAction;
import cz.mzk.editor.shared.rpc.action.GetUserRolesRightsIdentitiesResult;

// TODO: Auto-generated Javadoc
/**
 * The Class GetRecentlyModifiedHandler.
 */
public class GetUserRolesRightsIdentitiesHandler
        implements ActionHandler<GetUserRolesRightsIdentitiesAction, GetUserRolesRightsIdentitiesResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(GetUserRolesRightsIdentitiesHandler.class
            .getPackage().toString());

    /** The configuration. */
    private final EditorConfiguration configuration;

    /** The recently modified dao. */
    private final UserDAO userDAO;

    /**
     * Instantiates a new gets the recently modified handler.
     * 
     * @param logger
     *        the logger
     * @param configuration
     *        the configuration
     * @param userDAO
     *        the user dao
     */
    @Inject
    public GetUserRolesRightsIdentitiesHandler(final EditorConfiguration configuration, final UserDAO userDAO) {
        this.configuration = configuration;
        this.userDAO = userDAO;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public GetUserRolesRightsIdentitiesResult execute(final GetUserRolesRightsIdentitiesAction action,
                                                      final ExecutionContext context) throws ActionException {

        LOGGER.debug("Processing action: GetUserRolesAndIdentitiesAction " + action.getId());
        ServerUtils.checkExpiredSession();

        if (action.getId() == null) return null;
        boolean getAll = true;

        try {
            List<UserIdentity> identities = null;
            List<USER_IDENTITY_TYPES> identityTypes;
            if (action.getIdentityTypes() != null) {
                if (action.getIdentityTypes().isEmpty()) {
                    identityTypes = configuration.getIdentityTypes();
                } else {
                    identityTypes = action.getIdentityTypes();
                }
                identities = new ArrayList<UserIdentity>(identityTypes.size());

                for (USER_IDENTITY_TYPES type : identityTypes) {
                    identities.add(userDAO.getIdentities(action.getId(), type));
                }
                getAll = !getAll;
            }

            long userId = Long.parseLong(action.getId());

            List<RoleItem> rolesOfUser = null;
            List<EDITOR_RIGHTS> rightsOfUser = null;

            if (action.isGetRoles()) {
                rolesOfUser = userDAO.getRolesOfUser(userId);
                getAll = !getAll;
            }
            if ((!action.isGetRoles() && action.getIdentityTypes() == null) || getAll) {
                rightsOfUser = userDAO.getRightsOfUser(userId);
            }

            return new GetUserRolesRightsIdentitiesResult(rolesOfUser, rightsOfUser, identities);

        } catch (NumberFormatException e) {
            throw new ActionException(e);
        } catch (DatabaseException e) {
            throw new ActionException(e);
        } catch (UnsupportedDataTypeException e) {
            throw new ActionException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<GetUserRolesRightsIdentitiesAction> getActionType() {
        return GetUserRolesRightsIdentitiesAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(GetUserRolesRightsIdentitiesAction action,
                     GetUserRolesRightsIdentitiesResult result,
                     ExecutionContext context) throws ActionException {
        // TODO Auto-generated method stub

    }
}