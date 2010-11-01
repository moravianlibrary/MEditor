/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.config;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.shared.GwtEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.HasEventBus;

import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.shared.event.ConfigReceivedEvent;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetClientConfigAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetClientConfigResult;

// TODO: Auto-generated Javadoc
/**
 * The Class EditorClientConfigurationImpl.
 */
@Singleton
public class EditorClientConfigurationImpl extends EditorClientConfiguration implements HasEventBus {

	/** The configuration. */
	private MyConfiguration configuration;
	
	/** The event bus. */
	@Inject
	private EventBus eventBus;

	/**
	 * Instantiates a new editor client configuration impl.
	 *
	 * @param dispatcher the dispatcher
	 */
	@Inject
	public EditorClientConfigurationImpl(final DispatchAsync dispatcher) {
		if (configuration == null) {
			dispatcher.execute(new GetClientConfigAction(), new DispatchCallback<GetClientConfigResult>() {
				@Override
				public void callbackError(final Throwable cause) {
					ConfigReceivedEvent.fire(EditorClientConfigurationImpl.this, false);
					Log.error("Client configuration was not returned from server. Cause: ", cause);
				}

				@Override
				public void callback(GetClientConfigResult result) {
					EditorClientConfigurationImpl.this.configuration = new MyConfiguration(result.getConfig());
					ConfigReceivedEvent.fire(EditorClientConfigurationImpl.this, true);
					Log.debug("Client configuration successfully returned from server.");
				}
			});
		}
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration#getConfiguration()
	 */
	@Override
	public MyConfiguration getConfiguration() {
		return configuration;
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration#setConfiguration(cz.fi.muni.xkremser.editor.client.config.MyConfiguration)
	 */
	@Override
	public void setConfiguration(MyConfiguration configuration) {
		this.configuration = configuration;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.HasHandlers#fireEvent(com.google.gwt.event.shared.GwtEvent)
	 */
	@Override
	public void fireEvent(GwtEvent<?> event) {
		eventBus.fireEvent(this, event);
	}

}
