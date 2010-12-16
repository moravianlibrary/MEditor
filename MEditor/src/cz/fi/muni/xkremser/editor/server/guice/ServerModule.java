/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.guice;

import javax.xml.namespace.NamespaceContext;

import org.apache.commons.logging.Log;

import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;
import cz.fi.muni.xkremser.editor.server.AuthenticationServlet;
import cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO;
import cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAOImpl;
import cz.fi.muni.xkremser.editor.server.DAO.RecentlyModifiedItemDAO;
import cz.fi.muni.xkremser.editor.server.DAO.RecentlyModifiedItemDAOImpl;
import cz.fi.muni.xkremser.editor.server.DAO.UserDAO;
import cz.fi.muni.xkremser.editor.server.DAO.UserDAOImpl;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.config.EditorConfigurationImpl;
import cz.fi.muni.xkremser.editor.server.config.KrameriusModelMapping;
import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccessImpl;
import cz.fi.muni.xkremser.editor.server.fedora.FedoraNamespaceContext;
import cz.fi.muni.xkremser.editor.server.fedora.IPaddressChecker;
import cz.fi.muni.xkremser.editor.server.fedora.RequestIPaddressChecker;
import cz.fi.muni.xkremser.editor.server.fedora.SecuredFedoraAccessImpl;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.server.handler.CheckAvailabilityHandler;
import cz.fi.muni.xkremser.editor.server.handler.GetAllRolesHandler;
import cz.fi.muni.xkremser.editor.server.handler.GetClientConfigHandler;
import cz.fi.muni.xkremser.editor.server.handler.GetDescriptionHandler;
import cz.fi.muni.xkremser.editor.server.handler.GetDigitalObjectDetailHandler;
import cz.fi.muni.xkremser.editor.server.handler.GetLoggedUserHandler;
import cz.fi.muni.xkremser.editor.server.handler.GetRecentlyModifiedHandler;
import cz.fi.muni.xkremser.editor.server.handler.GetUserInfoHandler;
import cz.fi.muni.xkremser.editor.server.handler.GetUserRolesAndIdentitiesHandler;
import cz.fi.muni.xkremser.editor.server.handler.LogoutHandler;
import cz.fi.muni.xkremser.editor.server.handler.PutDescriptionHandler;
import cz.fi.muni.xkremser.editor.server.handler.PutDigitalObjectDetailHandler;
import cz.fi.muni.xkremser.editor.server.handler.PutRecentlyModifiedHandler;
import cz.fi.muni.xkremser.editor.server.handler.PutUserIdentityHandler;
import cz.fi.muni.xkremser.editor.server.handler.PutUserInfoHandler;
import cz.fi.muni.xkremser.editor.server.handler.PutUserRoleHandler;
import cz.fi.muni.xkremser.editor.server.handler.RemoveUserIdentityHandler;
import cz.fi.muni.xkremser.editor.server.handler.RemoveUserInfoHandler;
import cz.fi.muni.xkremser.editor.server.handler.RemoveUserRoleHandler;
import cz.fi.muni.xkremser.editor.server.handler.ScanInputQueueHandler;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAvailabilityAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetAllRolesAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetClientConfigAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDescriptionAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDigitalObjectDetailAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetLoggedUserAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRecentlyModifiedAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetUserInfoAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetUserRolesAndIdentitiesAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.LogoutAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutDescriptionAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutDigitalObjectDetailAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutRecentlyModifiedAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutUserIdentityAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutUserInfoAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutUserRoleAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserIdentityAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserInfoAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserRoleAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueAction;

// TODO: Auto-generated Javadoc
/**
 * Module which binds the handlers and configurations.
 */
public class ServerModule extends HandlerModule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.guice.HandlerModule#configureHandlers()
	 */
	@Override
	protected void configureHandlers() {
		// bindHandler(SendGreeting.class, SendGreetingHandler.class);
		bindHandler(ScanInputQueueAction.class, ScanInputQueueHandler.class);
		bindHandler(GetClientConfigAction.class, GetClientConfigHandler.class);
		bindHandler(GetDigitalObjectDetailAction.class, GetDigitalObjectDetailHandler.class);
		bindHandler(PutDigitalObjectDetailAction.class, PutDigitalObjectDetailHandler.class);
		bindHandler(GetRecentlyModifiedAction.class, GetRecentlyModifiedHandler.class);
		bindHandler(PutRecentlyModifiedAction.class, PutRecentlyModifiedHandler.class);
		bindHandler(GetDescriptionAction.class, GetDescriptionHandler.class);
		bindHandler(PutDescriptionAction.class, PutDescriptionHandler.class);
		bindHandler(CheckAvailabilityAction.class, CheckAvailabilityHandler.class);
		bindHandler(GetUserInfoAction.class, GetUserInfoHandler.class);
		bindHandler(PutUserInfoAction.class, PutUserInfoHandler.class);
		bindHandler(RemoveUserInfoAction.class, RemoveUserInfoHandler.class);
		bindHandler(GetUserRolesAndIdentitiesAction.class, GetUserRolesAndIdentitiesHandler.class);
		bindHandler(PutUserIdentityAction.class, PutUserIdentityHandler.class);
		bindHandler(RemoveUserIdentityAction.class, RemoveUserIdentityHandler.class);
		bindHandler(PutUserRoleAction.class, PutUserRoleHandler.class);
		bindHandler(RemoveUserRoleAction.class, RemoveUserRoleHandler.class);
		bindHandler(GetAllRolesAction.class, GetAllRolesHandler.class);
		bindHandler(GetLoggedUserAction.class, GetLoggedUserHandler.class);

		bindHandler(LogoutAction.class, LogoutHandler.class);

		bind(Log.class).toProvider(LogProvider.class).in(Singleton.class);
		bind(EditorConfiguration.class).to(EditorConfigurationImpl.class).asEagerSingleton();

		// DAO
		bind(InputQueueItemDAO.class).to(InputQueueItemDAOImpl.class).asEagerSingleton();
		bind(RecentlyModifiedItemDAO.class).to(RecentlyModifiedItemDAOImpl.class).asEagerSingleton();
		bind(UserDAO.class).to(UserDAOImpl.class).asEagerSingleton();
		// bind(HibernateConnection.class).toProvider(ConnectionProvider.class).in(Scopes.SINGLETON);

		// Fedora
		bind(FedoraAccess.class).annotatedWith(Names.named("rawFedoraAccess")).to(FedoraAccessImpl.class).in(Scopes.SINGLETON);
		bind(FedoraAccess.class).annotatedWith(Names.named("securedFedoraAccess")).to(SecuredFedoraAccessImpl.class).in(Scopes.SINGLETON);
		bind(NamespaceContext.class).to(FedoraNamespaceContext.class).in(Scopes.SINGLETON);

		// Fedora/Kramerius models
		int total = KrameriusModel.values().length;
		for (int i = 0; i < total; i++) {
			bind(KrameriusModelMapping.TYPES.get(KrameriusModel.values()[i])).in(Scopes.SINGLETON);
		}

		bind(IPaddressChecker.class).to(RequestIPaddressChecker.class);
		// bind(OpenIDServlet.Callback.class).to(OpenIDCallback.class);

		// static injection
		requestStaticInjection(FedoraUtils.class);
		requestStaticInjection(AuthenticationServlet.class);

	}
}