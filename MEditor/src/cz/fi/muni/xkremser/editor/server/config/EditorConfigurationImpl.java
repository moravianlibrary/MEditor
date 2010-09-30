package cz.fi.muni.xkremser.editor.server.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class EditorConfigurationImpl extends EditorConfiguration {
	public static final String WORKING_DIR = System.getProperty("user.home") + File.separator + ".meditor";
	public static final String DEFAULT_CONF_LOCATION = "configuration.properties";
	public static final String CONFIGURATION = WORKING_DIR + File.separator + DEFAULT_CONF_LOCATION;

	private Configuration configuration;
	private final Log logger;

	@Inject
	public EditorConfigurationImpl(final Log logger) {
		this.logger = logger;
		File dir = new File(WORKING_DIR);
		if (!dir.exists()) {
			boolean mkdirs = dir.mkdirs();
			if (!mkdirs) {
				logger.error("cannot create directory '" + dir.getAbsolutePath() + "'");
				throw new RuntimeException("cannot create directory '" + dir.getAbsolutePath() + "'");
			}
		}
		File confFile = new File(CONFIGURATION);
		if (!confFile.exists()) {
			try {
				confFile.createNewFile();
			} catch (IOException e) {
				logger.error("cannot create configuration file '" + confFile.getAbsolutePath() + "'", e);
				throw new RuntimeException("cannot create configuration file '" + confFile.getAbsolutePath() + "'");
			}
			FileOutputStream confFos;
			try {
				confFos = new FileOutputStream(confFile);
			} catch (FileNotFoundException e) {
				logger.error("cannot create configuration file '" + confFile.getAbsolutePath() + "'", e);
				throw new RuntimeException("cannot create configuration file '" + confFile.getAbsolutePath() + "'");
			}
			try {
				try {
					new Properties().store(confFos, "configuration file for module metadata editor");
				} catch (IOException e) {
					logger.error("cannot create configuration file '" + confFile.getAbsolutePath() + "'", e);
					throw new RuntimeException("cannot create configuration file '" + confFile.getAbsolutePath() + "'");
				}
			} finally {
				try {
					if (confFos != null)
						confFos.close();
				} catch (IOException e) {
					logger.error("cannot create configuration file '" + confFile.getAbsolutePath() + "'", e);
					throw new RuntimeException("cannot create configuration file '" + confFile.getAbsolutePath() + "'");
				} finally {
					confFos = null;
				}
			}
		}
		CompositeConfiguration constconf = new CompositeConfiguration();
		PropertiesConfiguration file = null;
		try {
			file = new PropertiesConfiguration(confFile);
		} catch (ConfigurationException e) {
			logger.error(e.getMessage(), e);
			new RuntimeException("cannot read configuration");
		}
		file.setReloadingStrategy(new FileChangedReloadingStrategy());
		constconf.addConfiguration(file);
		this.configuration = constconf;
	}

	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

}
