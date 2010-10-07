package cz.fi.muni.xkremser.editor.client.config;

import java.util.HashMap;
import java.util.Map;

public class MyConfiguration extends HashMap<String, Object> {

	private final Map<String, Object> configuration;

	public MyConfiguration(Map<String, Object> configuration) {
		this.configuration = configuration;
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return get(key) == null ? defaultValue : (Boolean) get(key);
	}

	public String[] getStringArray(String key) {
		return (String[]) get(key);
	}

	public Map<String, Object> getConfiguration() {
		return configuration;
	}

}
