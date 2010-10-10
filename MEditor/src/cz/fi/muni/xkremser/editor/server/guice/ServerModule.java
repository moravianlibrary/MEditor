package cz.fi.muni.xkremser.editor.server.guice;

import org.apache.commons.logging.Log;

import com.google.inject.Singleton;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

import cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO;
import cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAOImpl;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.config.EditorConfigurationImpl;
import cz.fi.muni.xkremser.editor.server.handler.GetClientConfigHandler;
import cz.fi.muni.xkremser.editor.server.handler.ScanInputQueueHandler;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetClientConfigAction;
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

		bind(Log.class).toProvider(LogProvider.class).in(Singleton.class);
		bind(EditorConfiguration.class).to(EditorConfigurationImpl.class).asEagerSingleton();
		bind(InputQueueItemDAO.class).to(InputQueueItemDAOImpl.class).asEagerSingleton();
		// bind(HibernateConnection.class).toProvider(ConnectionProvider.class).in(Scopes.SINGLETON);
	}
}