/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.config;

import java.util.Map;

import cz.fi.muni.xkremser.editor.client.ClientUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class MyConfiguration.
 */
public class MyConfiguration {

	/** The configuration. */
	private final Map<String, Object> configuration;

	/**
	 * Instantiates a new my configuration.
	 * 
	 * @param configuration
	 *          the configuration
	 */
	public MyConfiguration(Map<String, Object> configuration) {
		this.configuration = configuration;
	}

	/**
	 * Gets the boolean.
	 * 
	 * @param key
	 *          the key
	 * @param defaultValue
	 *          the default value
	 * @return the boolean
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		if (configuration.get(key) == null) {
			return defaultValue;
		}
		return ClientUtils.toBoolean((String) configuration.get(key));
	}

	public String getString(String key, String defaultValue) {
		if (configuration.get(key) == null) {
			return defaultValue;
		}
		return (String) configuration.get(key);
	}

	/**
	 * Gets the string array.
	 * 
	 * @param key
	 *          the key
	 * @return the string array
	 */
	public String[] getStringArray(String key) {
		return (String[]) configuration.get(key);
	}

	/**
	 * Gets the configuration.
	 * 
	 * @return the configuration
	 */
	public Map<String, Object> getConfiguration() {
		return configuration;
	}

}
