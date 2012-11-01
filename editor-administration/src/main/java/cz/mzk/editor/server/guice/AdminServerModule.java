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
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.config.EditorConfigurationImpl;
import cz.mzk.editor.server.handler.CheckAndUpdateDBSchemaHandler;
import cz.mzk.editor.server.handler.GetHistoryDaysHandler;
import cz.mzk.editor.shared.rpc.action.CheckAndUpdateDBSchemaAction;
import cz.mzk.editor.shared.rpc.action.GetHistoryDaysAction;

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
        //        bindHandler(ScanInputQueueAction.class, ScanInputQueueHandler.class);
        //        bindHandler(ScanFolderAction.class, ScanFolderHandler.class);
        //        bindHandler(GetClientConfigAction.class, GetClientConfigHandler.class);
        //        bindHandler(GetDigitalObjectDetailAction.class, GetDigitalObjectDetailHandler.class);
        //        bindHandler(PutDigitalObjectDetailAction.class, PutDigitalObjectDetailHandler.class);
        //        bindHandler(GetRecentlyModifiedAction.class, GetRecentlyModifiedHandler.class);
        //        bindHandler(PutRecentlyModifiedAction.class, PutRecentlyModifiedHandler.class);
        //        bindHandler(GetDescriptionAction.class, GetDescriptionHandler.class);
        //        bindHandler(PutDescriptionAction.class, PutDescriptionHandler.class);
        //        bindHandler(CheckAvailabilityAction.class, CheckAvailabilityHandler.class);
        //        bindHandler(GetUserInfoAction.class, GetUserInfoHandler.class);
        //        bindHandler(PutUserInfoAction.class, PutUserInfoHandler.class);
        //        bindHandler(RemoveUserInfoAction.class, RemoveUserInfoHandler.class);
        //        bindHandler(GetUserRolesAndIdentitiesAction.class, GetUserRolesAndIdentitiesHandler.class);
        //        bindHandler(PutUserIdentityAction.class, PutUserIdentityHandler.class);
        //        bindHandler(RemoveUserIdentityAction.class, RemoveUserIdentityHandler.class);
        //        bindHandler(PutUserRoleAction.class, PutUserRoleHandler.class);
        //        bindHandler(RemoveUserRoleAction.class, RemoveUserRoleHandler.class);
        //        bindHandler(GetAllRolesAction.class, GetAllRolesHandler.class);
        //        bindHandler(GetLoggedUserAction.class, GetLoggedUserHandler.class);
        //        bindHandler(GetAllRequestItemsAction.class, GetAllRequestItemsHandler.class);
        //        bindHandler(RemoveRequestItemAction.class, RemoveRequestItemHandler.class);
        //        bindHandler(FindMetadataAction.class, FindMetadataHandler.class);
        //        bindHandler(ConvertToJPEG2000Action.class, ConvertToJPEG2000Handler.class);
        //        bindHandler(LockDigitalObjectAction.class, LockDigitalObjectHandler.class);
        //        bindHandler(UnlockDigitalObjectAction.class, UnlockDigitalObjectHandler.class);
        //        bindHandler(DownloadDigitalObjectDetailAction.class, DownloadDigitalObjectDetailHandler.class);
        //        bindHandler(StoredItemsAction.class, StoredItemsHandler.class);
        //        bindHandler(GetDOModelAction.class, GetDOModelHandler.class);
        //        bindHandler(GetLockInformationAction.class, GetLockInformationHandler.class);
        //        bindHandler(InsertNewDigitalObjectAction.class, InsertNewDigitalObjectHandler.class);
        //        bindHandler(GetRelationshipsAction.class, GetRelationshipsHandler.class);
        //        bindHandler(RemoveDigitalObjectAction.class, RemoveDigitalObjectHandler.class);
        //        bindHandler(GetIngestInfoAction.class, GetIngestInfoHandler.class);
        bindHandler(CheckAndUpdateDBSchemaAction.class, CheckAndUpdateDBSchemaHandler.class);
        //        bindHandler(LogoutAction.class, LogoutHandler.class);
        //        bindHandler(FindAltoOcrFilesAction.class, FindAltoOcrFilesHandler.class);
        //        bindHandler(FindAltoOcrFilesBatchAction.class, FindAltoOcrFilesBatchHandler.class);
        //        bindHandler(StoreTreeStructureAction.class, StoreTreeStructureHandler.class);
        //        bindHandler(ChangeRightsAction.class, ChangeRightsHandler.class);
        //        bindHandler(GetFullImgMetadataAction.class, GetFullImgMetadataHandler.class);
        //        bindHandler(InitializeConversionAction.class, InitializeConversionHandler.class);

        //        bindHandler(QuartzConvertImagesAction.class, QuartzConvertImagesHandler.class);
        //        bindHandler(QuartzScheduleJobsAction.class, QuartzScheduleJobsHandler.class);
        bind(EditorConfiguration.class).to(EditorConfigurationImpl.class).asEagerSingleton();
        bindHandler(GetHistoryDaysAction.class, GetHistoryDaysHandler.class);

        // DAO
        //        bind(InputQueueItemDAO.class).to(InputQueueItemDAOImpl.class);
        //        bind(ImageResolverDAO.class).to(ImageResolverDAOImpl.class);
        //        bind(RecentlyModifiedItemDAO.class).to(RecentlyModifiedItemDAOImpl.class);
        //        bind(UserDAO.class).to(UserDAOImpl.class);
        //        bind(RequestDAO.class).to(RequestDAOImpl.class);
        //        bind(LockDAO.class).to(LockDAOImpl.class);
        //        bind(StoredItemsDAO.class).to(StoredItemsDAOImpl.class);
        bind(DBSchemaDAO.class).to(DBSchemaDAOImpl.class);
        //        bind(TreeStructureDAO.class).to(TreeStructureDAOImpl.class);
        bind(DAOUtils.class).to(DAOUtilsImpl.class);
        //        bind(DescriptionDAO.class).to(DescriptionDAOImpl.class);
        bind(ActionDAO.class).to(ActionDAOImpl.class);
        //        bind(LogInOutDAO.class).to(LogInOutDAOImpl.class);
        //        bind(ConversionDAO.class).to(ConversionDAOImpl.class);
        //
        //        // Fedora
        //        bind(FedoraAccess.class).annotatedWith(Names.named("rawFedoraAccess")).to(FedoraAccessImpl.class)
        //                .in(Scopes.SINGLETON);
        //        bind(FedoraAccess.class).annotatedWith(Names.named("securedFedoraAccess"))
        //                .to(SecuredFedoraAccessImpl.class).in(Scopes.SINGLETON);
        //        bind(NamespaceContext.class).to(FedoraNamespaceContext.class).in(Scopes.SINGLETON);
        //
        //        bind(FedoraDigitalObjectHandler.class).to(FedoraDigitalObjectHandlerImpl.class);
        //        bind(StoredDigitalObjectHandler.class).to(StoredDigitalObjectHandlerImpl.class);
        //        bind(Z3950Client.class).to(Z3950ClientImpl.class);
        //        bind(OAIPMHClient.class).to(OAIPMHClientImpl.class);
        //
        //        bind(IPaddressChecker.class).to(RequestIPaddressChecker.class);
        //
        //        // Quartz
        //        bind(SchedulerFactory.class).to(StdSchedulerFactory.class).in(Scopes.SINGLETON);
        //        bind(GuiceJobFactory.class).in(Scopes.SINGLETON);
        //        bind(Quartz.class).in(Scopes.SINGLETON);
        //
        //        // static injection
        //        requestStaticInjection(FedoraUtils.class);
        //        requestStaticInjection(AuthenticationServlet.class);
        //        requestStaticInjection(URLS.class);
        //        requestStaticInjection(CreateObjectUtils.class);
        //        requestStaticInjection(ServerUtils.class);
        //        requestStaticInjection(FOXMLBuilderMapping.class);

    }
}