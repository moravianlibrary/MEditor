/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.fi.muni.xkremser.editor.client.config;

import java.util.Map;

import cz.fi.muni.xkremser.editor.client.util.ClientUtils;

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
     *        the configuration
     */
    public MyConfiguration(Map<String, Object> configuration) {
        this.configuration = configuration;
    }

    /**
     * Gets the boolean.
     * 
     * @param key
     *        the key
     * @param defaultValue
     *        the default value
     * @return the boolean
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        if (configuration.get(key) == null) {
            return defaultValue;
        }
        return ClientUtils.toBoolean((String) configuration.get(key));
    }

    /**
     * Gets the string.
     * 
     * @param key
     *        the key
     * @param defaultValue
     *        the default value
     * @return the string
     */
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
     *        the key
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
