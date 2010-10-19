package cz.fi.muni.xkremser.editor.server.guice;

import javax.xml.namespace.NamespaceContext;

import org.apache.commons.logging.Log;

import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

import cz.fi.muni.xkremser.editor.client.Constants.KrameriusModel;
import cz.fi.muni.xkremser.editor.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.fedora.FedoraAccessImpl;
import cz.fi.muni.xkremser.editor.fedora.FedoraNamespaceContext;
import cz.fi.muni.xkremser.editor.fedora.IPaddressChecker;
import cz.fi.muni.xkremser.editor.fedora.KrameriusModelHelper;
import cz.fi.muni.xkremser.editor.fedora.RequestIPaddressChecker;
import cz.fi.muni.xkremser.editor.fedora.SecuredFedoraAccessImpl;
import cz.fi.muni.xkremser.editor.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO;
import cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAOImpl;
import cz.fi.muni.xkremser.editor.server.DAO.RecentlyModifiedItemDAO;
import cz.fi.muni.xkremser.editor.server.DAO.RecentlyModifiedItemDAOImpl;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.config.EditorConfigurationImpl;
import cz.fi.muni.xkremser.editor.server.handler.GetClientConfigHandler;
import cz.fi.muni.xkremser.editor.server.handler.GetDigitalObjectDetailHandler;
import cz.fi.muni.xkremser.editor.server.handler.GetRecentlyModifiedHandler;
import cz.fi.muni.xkremser.editor.server.handler.PutRecentlyModifiedHandler;
import cz.fi.muni.xkremser.editor.server.handler.ScanInputQueueHandler;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetClientConfigAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDigitalObjectDetailAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRecentlyModifiedAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutRecentlyModifiedAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueAction;

/**
 * Module which binds the handlers and configurations
 * 
 */
public class ServerModule extends HandlerModule {

	@Override
	protected void configureHandlers() {
		// bindHandler(SendGreeting.class, SendGreetingHandler.class);
		bindHandler(ScanInputQueueAction.class, ScanInputQueueHandler.class);
		bindHandler(GetClientConfigAction.class, GetClientConfigHandler.class);
		bindHandler(GetDigitalObjectDetailAction.class, GetDigitalObjectDetailHandler.class);
		bindHandler(GetRecentlyModifiedAction.class, GetRecentlyModifiedHandler.class);
		bindHandler(PutRecentlyModifiedAction.class, PutRecentlyModifiedHandler.class);

		bind(Log.class).toProvider(LogProvider.class).in(Singleton.class);
		bind(EditorConfiguration.class).to(EditorConfigurationImpl.class).asEagerSingleton();

		// DAO
		bind(InputQueueItemDAO.class).to(InputQueueItemDAOImpl.class).asEagerSingleton();
		bind(RecentlyModifiedItemDAO.class).to(RecentlyModifiedItemDAOImpl.class).asEagerSingleton();
		// bind(HibernateConnection.class).toProvider(ConnectionProvider.class).in(Scopes.SINGLETON);

		// Fedora
		bind(FedoraAccess.class).annotatedWith(Names.named("rawFedoraAccess")).to(FedoraAccessImpl.class).in(Scopes.SINGLETON);
		bind(FedoraAccess.class).annotatedWith(Names.named("securedFedoraAccess")).to(SecuredFedoraAccessImpl.class).in(Scopes.SINGLETON);
		bind(NamespaceContext.class).to(FedoraNamespaceContext.class).in(Scopes.SINGLETON);

		// Fedora/Kramerius models
		int total = KrameriusModel.values().length;
		for (int i = 0; i < total; i++) {
			bind(KrameriusModelHelper.TYPES.get(KrameriusModel.values()[i])).in(Scopes.SINGLETON);
		}

		bind(IPaddressChecker.class).to(RequestIPaddressChecker.class);

		// static injection
		requestStaticInjection(FedoraUtils.class);

	}
}