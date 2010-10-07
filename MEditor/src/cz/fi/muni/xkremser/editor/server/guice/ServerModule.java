package cz.fi.muni.xkremser.editor.server.guice;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import org.apache.commons.logging.Log;

import com.google.inject.Singleton;

import cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO;
import cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAOImpl;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.config.EditorConfigurationImpl;
import cz.fi.muni.xkremser.editor.server.handler.GetClientConfigHandler;
import cz.fi.muni.xkremser.editor.server.handler.ScanInputQueueHandler;
import cz.fi.muni.xkremser.editor.server.handler.SendGreetingHandler;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetClientConfig;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueue;
import cz.fi.muni.xkremser.editor.shared.rpc.action.SendGreeting;

/**
 * Module which binds the handlers and configurations
 * 
 */
public class ServerModule extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		bindHandler(SendGreeting.class, SendGreetingHandler.class);
		bindHandler(ScanInputQueue.class, ScanInputQueueHandler.class);
		bindHandler(ScanInputQueue.class, ScanInputQueueHandler.class);
		bindHandler(GetClientConfig.class, GetClientConfigHandler.class);

		bind(Log.class).toProvider(LogProvider.class).in(Singleton.class);
		bind(EditorConfiguration.class).to(EditorConfigurationImpl.class).asEagerSingleton();
		bind(InputQueueItemDAO.class).to(InputQueueItemDAOImpl.class).asEagerSingleton();
		// bind(HibernateConnection.class).toProvider(ConnectionProvider.class).in(Scopes.SINGLETON);
	}
}