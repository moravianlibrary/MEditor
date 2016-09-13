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

package cz.mzk.editor.server.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Iterator;
import java.util.Properties;

import com.google.inject.Singleton;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.EnvironmentConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.config.EditorClientConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class EditorConfigurationImpl.
 */
@Singleton
public class EditorConfigurationImpl
        extends EditorConfiguration {

    /** The Constant WORKING_DIR. */
    public static final String WORKING_DIR = System.getProperty("user.home") + File.separator + ".meditor";

    /** The Constant DEFAULT_CONF_LOCATION. */
    public static final String DEFAULT_CONF_LOCATION = "configuration.properties";

    /** The Constant CONFIGURATION. */
    public static final String CONFIGURATION = WORKING_DIR + File.separator + DEFAULT_CONF_LOCATION;

    /** The configuration. */
    private Configuration configuration;

    private static final Logger LOGGER = Logger.getLogger(EditorConfiguration.class);

    public EditorConfigurationImpl() {
        File dir = new File(WORKING_DIR);
        if (!dir.exists()) {
            boolean mkdirs = dir.mkdirs();
            if (!mkdirs) {
                LOGGER.error("cannot create directory '" + dir.getAbsolutePath() + "'");
                throw new RuntimeException("cannot create directory '" + dir.getAbsolutePath() + "'");
            }
        }
        File confFile = new File(CONFIGURATION);
        if (!confFile.exists()) {
            try {
                confFile.createNewFile();
            } catch (IOException e) {
                LOGGER.error("cannot create configuration file '" + confFile.getAbsolutePath() + "'", e);
                throw new RuntimeException("cannot create configuration file '" + confFile.getAbsolutePath()
                        + "'");
            }
            FileOutputStream confFos;
            try {
                confFos = new FileOutputStream(confFile);
            } catch (FileNotFoundException e) {
                LOGGER.error("cannot create configuration file '" + confFile.getAbsolutePath() + "'", e);
                throw new RuntimeException("cannot create configuration file '" + confFile.getAbsolutePath()
                        + "'");
            }
            try {
                try {
                    new Properties().store(confFos, "configuration file for module metadata editor");
                } catch (IOException e) {
                    LOGGER.error("cannot create configuration file '" + confFile.getAbsolutePath() + "'", e);
                    throw new RuntimeException("cannot create configuration file '"
                            + confFile.getAbsolutePath() + "'");
                }
            } finally {
                try {
                    if (confFos != null) confFos.close();
                } catch (IOException e) {
                    LOGGER.error("cannot create configuration file '" + confFile.getAbsolutePath() + "'", e);
                    throw new RuntimeException("cannot create configuration file '"
                            + confFile.getAbsolutePath() + "'");
                } finally {
                    confFos = null;
                }
            }
        }

        CompositeConfiguration compositeConfiguration = new CompositeConfiguration();
        PropertiesConfiguration propertiesConfiguration = null;
        try {
            propertiesConfiguration = new PropertiesConfiguration(confFile);
            propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
        } catch (ConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
        }
        compositeConfiguration.addConfiguration(propertiesConfiguration);
        compositeConfiguration.setProperty(ServerConstants.EDITOR_HOME, WORKING_DIR);
        configuration = compositeConfiguration;

        String hostname =
                configuration.getString(EditorClientConfiguration.Constants.HOSTNAME, "editor.mzk.cz");
        if (hostname.contains("://")) {
            hostname = hostname.substring(hostname.indexOf("://") + "://".length());
        }
        File imagesDir = new File(ServerConstants.DEFAULT_IMAGES_LOCATION + hostname + File.separator);
        if (!imagesDir.exists()) {
            boolean mkdirs = imagesDir.mkdirs();
            if (!mkdirs) {
                LOGGER.error("cannot create directory '" + imagesDir.getAbsolutePath() + "'");
                throw new RuntimeException("cannot create directory '" + imagesDir.getAbsolutePath() + "'");
            }
        }
        compositeConfiguration.setProperty(ServerConstants.IMAGES_LOCATION, ServerConstants.DEFAULT_IMAGES_LOCATION
                + hostname + File.separator);

        EnvironmentConfiguration environmentConfiguration = new EnvironmentConfiguration();
        PropertiesConfiguration propertiesConfigurationEnvironment = new PropertiesConfiguration();
        environmentConfiguration.setDelimiterParsingDisabled(true);
        String key, value;
        for (Iterator it = environmentConfiguration.getKeys(); it.hasNext();) {
            key = (String)it.next();
            value = environmentConfiguration.getString(key);
            key = key.replaceAll("_", ".");
            key = key.replaceAll("\\.\\.", "__");
            propertiesConfigurationEnvironment.addProperty(key, value);
        }
        compositeConfiguration.addConfiguration(propertiesConfigurationEnvironment);
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.server.config.EditorConfiguration#getConfiguration ()
     */
    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.server.config.EditorConfiguration#setConfiguration
     * (org.apache.commons.configuration.Configuration)
     */
    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

}
