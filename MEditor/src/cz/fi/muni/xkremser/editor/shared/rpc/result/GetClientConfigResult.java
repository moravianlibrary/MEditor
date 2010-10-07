package cz.fi.muni.xkremser.editor.shared.rpc.result;

import java.util.HashMap;

import net.customware.gwt.dispatch.shared.Result;

public class GetClientConfigResult implements Result {

	// output
	private HashMap<String, Object> config;

	@SuppressWarnings("unused")
	private GetClientConfigResult() {
	}

	public GetClientConfigResult(HashMap<String, Object> config) {
	}

	public HashMap<String, Object> getConfig() {
		return config;
	}

	public void setConfig(HashMap<String, Object> config) {
		this.config = config;
	}

}
