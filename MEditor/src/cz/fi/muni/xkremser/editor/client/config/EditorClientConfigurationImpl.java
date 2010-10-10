package cz.fi.muni.xkremser.editor.client.config;

import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gwtplatform.dispatch.client.DispatchAsync;

import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetClientConfigAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetClientConfigResult;

@Singleton
public class EditorClientConfigurationImpl extends EditorClientConfiguration {

	private MyConfiguration configuration;

	@Inject
	public EditorClientConfigurationImpl(final DispatchAsync dispatcher) {
		if (configuration == null) {
			dispatcher.execute(new GetClientConfigAction(), new DispatchCallback<GetClientConfigResult>() {
				@Override
				public void callbackError(final Throwable cause) {
					Log.error("Handle Failure:", cause);
					Window.alert("TODO");
				}

				@Override
				public void callback(GetClientConfigResult result) {
					EditorClientConfigurationImpl.this.configuration = new MyConfiguration(result.getConfig());
					Log.info("return callback" + new Date().toString());
					Log.info(String.valueOf(System.currentTimeMillis()));
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

}
