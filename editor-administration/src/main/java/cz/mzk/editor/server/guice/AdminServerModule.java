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

package cz.mzk.editor.server.guice;

import com.gwtplatform.dispatch.server.guice.HandlerModule;

import cz.mzk.editor.server.DAO.ActionDAO;
import cz.mzk.editor.server.DAO.ActionDAOImpl;
import cz.mzk.editor.server.DAO.DAOUtils;
import cz.mzk.editor.server.DAO.DAOUtilsImpl;
import cz.mzk.editor.server.DAO.DBSchemaDAO;
import cz.mzk.editor.server.DAO.DBSchemaDAOImpl;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.DAO.UserDAOImpl;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.config.EditorConfigurationImpl;
import cz.mzk.editor.server.handler.CheckAndUpdateDBSchemaHandler;
import cz.mzk.editor.server.handler.CheckRightsHandler;
import cz.mzk.editor.server.handler.GetAllRequestItemsHandler;
import cz.mzk.editor.server.handler.GetAllRolesHandler;
import cz.mzk.editor.server.handler.GetHistoryDaysHandler;
import cz.mzk.editor.server.handler.GetHistoryHandler;
import cz.mzk.editor.server.handler.GetHistoryItemInfoHandler;
import cz.mzk.editor.server.handler.GetLoggedUserHandler;
import cz.mzk.editor.server.handler.GetUserRolesRightsIdentitiesHandler;
import cz.mzk.editor.server.handler.GetUsersInfoHandler;
import cz.mzk.editor.server.handler.PutRemoveUserRightsHandler;
import cz.mzk.editor.server.handler.PutUserIdentityHandler;
import cz.mzk.editor.server.handler.PutUserInfoHandler;
import cz.mzk.editor.server.handler.PutUserRoleHandler;
import cz.mzk.editor.server.handler.RemoveRequestItemHandler;
import cz.mzk.editor.server.handler.RemoveUserHandler;
import cz.mzk.editor.server.handler.RemoveUserIdentityHandler;
import cz.mzk.editor.server.handler.RemoveUserRoleHandler;
import cz.mzk.editor.shared.rpc.action.CheckAndUpdateDBSchemaAction;
import cz.mzk.editor.shared.rpc.action.CheckRightsAction;
import cz.mzk.editor.shared.rpc.action.GetAllRequestItemsAction;
import cz.mzk.editor.shared.rpc.action.GetAllRolesAction;
import cz.mzk.editor.shared.rpc.action.GetHistoryAction;
import cz.mzk.editor.shared.rpc.action.GetHistoryDaysAction;
import cz.mzk.editor.shared.rpc.action.GetHistoryItemInfoAction;
import cz.mzk.editor.shared.rpc.action.GetLoggedUserAction;
import cz.mzk.editor.shared.rpc.action.GetUserRolesRightsIdentitiesAction;
import cz.mzk.editor.shared.rpc.action.GetUsersInfoAction;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRightsAction;
import cz.mzk.editor.shared.rpc.action.PutUserIdentityAction;
import cz.mzk.editor.shared.rpc.action.PutUserInfoAction;
import cz.mzk.editor.shared.rpc.action.PutUserRoleAction;
import cz.mzk.editor.shared.rpc.action.RemoveRequestItemAction;
import cz.mzk.editor.shared.rpc.action.RemoveUserAction;
import cz.mzk.editor.shared.rpc.action.RemoveUserIdentityAction;
import cz.mzk.editor.shared.rpc.action.RemoveUserRoleAction;

// TODO: Auto-generated Javadoc
/**
 * Module which binds the handlers and configurations.
 */
public class AdminServerModule
        extends HandlerModule {

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.guice.HandlerModule#configureHandlers()
     */
    @Override
    protected void configureHandlers() {
        bindHandler(GetUsersInfoAction.class, GetUsersInfoHandler.class);
        bindHandler(RemoveUserAction.class, RemoveUserHandler.class);
        bindHandler(GetUserRolesRightsIdentitiesAction.class, GetUserRolesRightsIdentitiesHandler.class);
        bindHandler(PutUserIdentityAction.class, PutUserIdentityHandler.class);
        bindHandler(PutUserInfoAction.class, PutUserInfoHandler.class);
        bindHandler(RemoveUserIdentityAction.class, RemoveUserIdentityHandler.class);
        bindHandler(PutUserRoleAction.class, PutUserRoleHandler.class);
        bindHandler(RemoveUserRoleAction.class, RemoveUserRoleHandler.class);
        bindHandler(GetAllRolesAction.class, GetAllRolesHandler.class);
        bindHandler(GetLoggedUserAction.class, GetLoggedUserHandler.class);
        bindHandler(GetAllRequestItemsAction.class, GetAllRequestItemsHandler.class);
        bindHandler(RemoveRequestItemAction.class, RemoveRequestItemHandler.class);
        bindHandler(CheckAndUpdateDBSchemaAction.class, CheckAndUpdateDBSchemaHandler.class);
        bindHandler(CheckRightsAction.class, CheckRightsHandler.class);
        bindHandler(PutRemoveUserRightsAction.class, PutRemoveUserRightsHandler.class);

        bindHandler(GetHistoryItemInfoAction.class, GetHistoryItemInfoHandler.class);
        bindHandler(GetHistoryAction.class, GetHistoryHandler.class);
        bind(EditorConfiguration.class).to(EditorConfigurationImpl.class).asEagerSingleton();
        bindHandler(GetHistoryDaysAction.class, GetHistoryDaysHandler.class);

        // DAO
        bind(UserDAO.class).to(UserDAOImpl.class);
        bind(DBSchemaDAO.class).to(DBSchemaDAOImpl.class);
        bind(DAOUtils.class).to(DAOUtilsImpl.class);
        bind(ActionDAO.class).to(ActionDAOImpl.class);

        //        // static injection
        //        requestStaticInjection(FedoraUtils.class);
        //        requestStaticInjection(AuthenticationServlet.class);
        //        requestStaticInjection(URLS.class);
        //        requestStaticInjection(CreateObjectUtils.class);
        //        requestStaticInjection(ServerUtils.class);
        //        requestStaticInjection(FOXMLBuilderMapping.class);

    }
}