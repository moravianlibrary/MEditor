package cz.fi.muni.xkremser.editor.client.config;

import java.util.Map;

import cz.fi.muni.xkremser.editor.client.ClientUtils;

public class MyConfiguration {

	private final Map<String, Object> configuration;

	public MyConfiguration(Map<String, Object> configuration) {
		this.configuration = configuration;
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		if (configuration.get(key) == null) {
			return defaultValue;
		}
		return ClientUtils.toBoolean((String) configuration.get(key));
	}

	public String[] getStringArray(String key) {
		return (String[]) configuration.get(key);
	}

	public Map<String, Object> getConfiguration() {
		return configuration;
	}

}
