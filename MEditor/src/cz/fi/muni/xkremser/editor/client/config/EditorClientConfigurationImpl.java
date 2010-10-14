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

@Singleton
public class EditorClientConfigurationImpl extends EditorClientConfiguration implements HasEventBus {

	private MyConfiguration configuration;
	@Inject
	private EventBus eventBus;

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

	@Override
	public MyConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public void setConfiguration(MyConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		eventBus.fireEvent(this, event);
	}

}
