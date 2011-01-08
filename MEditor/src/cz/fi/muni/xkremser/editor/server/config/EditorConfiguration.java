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
package cz.fi.muni.xkremser.editor.server.config;

import java.util.StringTokenizer;

import org.apache.commons.configuration.Configuration;

import cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class EditorConfiguration.
 */
public abstract class EditorConfiguration {

	/**
	 * The Class Constants.
	 */
	public static class Constants {

		/** The Constant UNDEF. */
		public static final Integer UNDEF = new Integer(-1);

		/** The Constant INPUT_QUEUE. */
		public static final String INPUT_QUEUE = "inputQueue";

		/** The Constant DOCUMENT_TYPES. */
		public static final String DOCUMENT_TYPES = EditorClientConfiguration.Constants.DOCUMENT_TYPES;

		/** The Constant DOCUMENT_DEFAULT_TYPES. */
		public static final String[] DOCUMENT_DEFAULT_TYPES = { "periodical", "monograph" };

		// GUI
		/** The Constant GUI_CONFIGURATION_PPREFIX. */
		public static final String GUI_CONFIGURATION_PPREFIX = "gui";

		/** The Constant GUI_SHOW_INPUT_QUEUE. */
		public static final String GUI_SHOW_INPUT_QUEUE = GUI_CONFIGURATION_PPREFIX + EditorClientConfiguration.Constants.GUI_SHOW_INPUT_QUEUE;

		/** The Constant GUI_SHOW_INPUT_QUEUE_DEFAULT. */
		public static final boolean GUI_SHOW_INPUT_QUEUE_DEFAULT = EditorClientConfiguration.Constants.GUI_SHOW_INPUT_QUEUE_DEFAULT;

		/** The Constant GUI_RECENTLY_MODIFIED_NUMBER. */
		public static final String GUI_RECENTLY_MODIFIED_NUMBER = "recentlyModifiedNumber";

		/** The Constant GUI_RECENTLY_MODIFIED_NUMBER_DEFAULT. */
		public static final int GUI_RECENTLY_MODIFIED_NUMBER_DEFAULT = 10;

		// z39.50
		/** The Constant Z3950_PROFILE. */
		public static final String Z3950_PROFILE = "z39.50Profile";

		/** The Constant Z3950_PROFILE_DEFAULT. */
		public static final String Z3950_PROFILE_DEFAULT = "mzk";

		/** The Constant Z3950_HOST. */
		public static final String Z3950_HOST = "z39.50Host";

		/** The Constant Z3950_PORT. */
		public static final String Z3950_PORT = "z39.50Port";

		/** The Constant Z3950_BASE. */
		public static final String Z3950_BASE = "z39.50Base";

		/** The Constant Z3950_BAR_LENGTH. */
		public static final String Z3950_BAR_LENGTH = "barcodeLength";

		/** The Constant Z3950_DEFAULT_HOSTS. */
		public static final String[] Z3950_DEFAULT_HOSTS = { "aleph.mzk.cz", "aleph.muni.cz", "sigma.nkp.cz", "sigma.nkp.cz" };

		/** The Constant Z3950_DEFAULT_PORTS. */
		public static final String[] Z3950_DEFAULT_PORTS = { "9991", "9991", "9909", "9909" };

		/** The Constant Z3950_DEFAULT_BASES. */
		public static final String[] Z3950_DEFAULT_BASES = { "MZK01-UTF", "MUB01", "SKC", "NKC" };

		/** The Constant Z3950_DEFAULT_BAR_LENGTH. */
		public static final int[] Z3950_DEFAULT_BAR_LENGTH = { 10, 10, 10, 10 };

		// access
		/** The Constant ACCESS_PATTERN_SEPARATOR. */
		public static final String ACCESS_PATTERN_SEPARATOR = "||";

		/** The Constant ACCESS_USER_PATTERNS. */
		public static final String ACCESS_USER_PATTERNS = "accessUserPatterns";

		/** The Constant ACCESS_USER_PATTERNS_DEFAULT. */
		public static final String ACCESS_USER_PATTERNS_DEFAULT = "*";

		/** The Constant ACCESS_ADMIN_PATTERNS. */
		public static final String ACCESS_ADMIN_PATTERNS = "accessAdminPatterns";

		/** The Constant ACCESS_ADMIN_PATTERNS_DEFAULT. */
		public static final String ACCESS_ADMIN_PATTERNS_DEFAULT = "127.*" + ACCESS_PATTERN_SEPARATOR + "localhost";

		/** The Constant KRAMERIUS_HOST. */
		public static final String KRAMERIUS_HOST = EditorClientConfiguration.Constants.KRAMERIUS_HOST;

		/** The Constant KRAMERIUS_LOGIN. */
		public static final String KRAMERIUS_LOGIN = "krameriusLogin";

		/** The Constant KRAMERIUS_PASSWORD. */
		public static final String KRAMERIUS_PASSWORD = "krameriusPassword";

		// fedora
		/** The Constant FEDORA_HOST. */
		public static final String FEDORA_HOST = EditorClientConfiguration.Constants.FEDORA_HOST;

		/** The Constant FEDORA_HOST_DEFAULT. */
		public static final String FEDORA_HOST_DEFAULT = "10.2.2.219"; // virtual
																																		// image
		// public static final String FEDORA_DEFAULT_HOST = "195.113.155.50"; //
		// freon.mzk.cz

		/** The Constant FEDORA_LOGIN. */
		public static final String FEDORA_LOGIN = "fedoraLogin";

		/** The Constant FEDORA_LOGIN_DEFAULT. */
		public static final String FEDORA_LOGIN_DEFAULT = "fedoraAdmin";

		/** The Constant FEDORA_PASSWORD. */
		public static final String FEDORA_PASSWORD = "fedoraPassword";

		// database
		/** The Constant DB_HOST. */
		public static final String DB_HOST = "dbHost";

		/** The Constant DB_PORT. */
		public static final String DB_PORT = "dbPort";

		/** The Constant DB_LOGIN. */
		public static final String DB_LOGIN = "dbLogin";

		/** The Constant DB_PASSWORD. */
		public static final String DB_PASSWORD = "dbPassword";

		/** The Constant DB_NAME. */
		public static final String DB_NAME = "dbName";

		/** The Constant DB_HOST_DEFAULT. */
		public static final String DB_HOST_DEFAULT = "localhost";

		/** The Constant DB_PORT_DEFAULT. */
		public static final String DB_PORT_DEFAULT = "5432";

		/** The Constant DB_LOGIN_DEFAULT. */
		public static final String DB_LOGIN_DEFAULT = "meditor";

		/** The Constant DB_NAME_DEFAULT. */
		public static final String DB_NAME_DEFAULT = "meditor";

		/** The Constant JANRAIN_API_KEY. */
		public static final String JANRAIN_API_KEY = "openIdApiKey";

		/** The Constant JANRAIN_API_KEY_DEFAULT. */
		public static final String JANRAIN_API_KEY_DEFAULT = "775a3d3ec29deeeaf39e506ff514f39fcb5e434d";

		/** The Constant JANRAIN_API_URL. */
		public static final String JANRAIN_API_URL = "openIdApiUrl";

		/** The Constant JANRAIN_API_URL_DEFAULT. */
		public static final String JANRAIN_API_URL_DEFAULT = "https://rpxnow.com";

	}

	/**
	 * Gets the configuration.
	 * 
	 * @return the configuration
	 */
	public abstract Configuration getConfiguration();

	/**
	 * Sets the configuration.
	 * 
	 * @param configuration
	 *          the new configuration
	 */
	public abstract void setConfiguration(Configuration configuration);

	/**
	 * Gets the client configuration.
	 * 
	 * @return the client configuration
	 */
	public Configuration getClientConfiguration() {
		return getConfiguration().subset(Constants.GUI_CONFIGURATION_PPREFIX);
	}

	/**
	 * Gets the show input queue.
	 * 
	 * @return the show input queue
	 */
	public boolean getShowInputQueue() {
		return getConfiguration().getBoolean(Constants.GUI_SHOW_INPUT_QUEUE, Constants.GUI_SHOW_INPUT_QUEUE_DEFAULT);
	}

	/**
	 * Gets the scan input queue path.
	 * 
	 * @return the scan input queue path
	 */
	public String getScanInputQueuePath() {
		return getConfiguration().getString(Constants.INPUT_QUEUE);
	}

	/**
	 * Gets the z3950 profile.
	 * 
	 * @return the z3950 profile
	 */
	public String getZ3950Profile() {
		return getConfiguration().getString(Constants.Z3950_PROFILE, Constants.Z3950_PROFILE_DEFAULT);
	}

	/**
	 * Gets the z3950 host.
	 * 
	 * @return the z3950 host
	 */
	public String getZ3950Host() {
		return getConfiguration().getString(Constants.Z3950_HOST);
	}

	/**
	 * Gets the z3950 port.
	 * 
	 * @return the z3950 port
	 */
	public String getZ3950Port() {
		return getConfiguration().getString(Constants.Z3950_PORT);
	}

	/**
	 * Gets the z3950 base.
	 * 
	 * @return the z3950 base
	 */
	public String getZ3950Base() {
		return getConfiguration().getString(Constants.Z3950_BASE);
	}

	/**
	 * Gets the z3950 bar length.
	 * 
	 * @return the z3950 bar length
	 */
	public Integer getZ3950BarLength() {
		return getConfiguration().getInteger(Constants.Z3950_BAR_LENGTH, Constants.UNDEF);
	}

	/**
	 * Gets the kramerius host.
	 *
	 * @return the kramerius host
	 */
	public String getKrameriusHost() {
		return getConfiguration().getString(Constants.KRAMERIUS_HOST, null);
	}

	/**
	 * Gets the kramerius login.
	 *
	 * @return the kramerius login
	 */
	public String getKrameriusLogin() {
		return getConfiguration().getString(Constants.KRAMERIUS_LOGIN, null);
	}

	/**
	 * Gets the kramerius password.
	 *
	 * @return the kramerius password
	 */
	public String getKrameriusPassword() {
		return getConfiguration().getString(Constants.KRAMERIUS_PASSWORD, null);
	}

	/**
	 * Gets the fedora host.
	 * 
	 * @return the fedora host
	 */
	public String getFedoraHost() {
		return getConfiguration().getString(Constants.FEDORA_HOST, null);
	}

	/**
	 * Gets the fedora login.
	 * 
	 * @return the fedora login
	 */
	public String getFedoraLogin() {
		return getConfiguration().getString(Constants.FEDORA_LOGIN, Constants.FEDORA_LOGIN_DEFAULT);
	}

	/**
	 * Gets the fedora password.
	 * 
	 * @return the fedora password
	 */
	public String getFedoraPassword() {
		return getConfiguration().getString(Constants.FEDORA_PASSWORD);
	}

	/**
	 * Gets the document types.
	 * 
	 * @return the document types
	 */
	public String[] getDocumentTypes() {
		return getConfiguration().getStringArray(Constants.DOCUMENT_TYPES);
	}

	/**
	 * Gets the dB host.
	 * 
	 * @return the dB host
	 */
	public String getDBHost() {
		return getConfiguration().getString(Constants.DB_HOST, Constants.DB_HOST_DEFAULT);
	}

	/**
	 * Gets the dB port.
	 * 
	 * @return the dB port
	 */
	public String getDBPort() {
		return getConfiguration().getString(Constants.DB_PORT, Constants.DB_PORT_DEFAULT);
	}

	/**
	 * Gets the dB login.
	 * 
	 * @return the dB login
	 */
	public String getDBLogin() {
		return getConfiguration().getString(Constants.DB_LOGIN, Constants.DB_LOGIN_DEFAULT);
	}

	/**
	 * Gets the dB password.
	 * 
	 * @return the dB password
	 */
	public String getDBPassword() {
		return getConfiguration().getString(Constants.DB_PASSWORD);
	}

	/**
	 * Gets the dB name.
	 * 
	 * @return the dB name
	 */
	public String getDBName() {
		return getConfiguration().getString(Constants.DB_NAME, Constants.DB_NAME_DEFAULT);
	}

	/**
	 * Gets the open id api key.
	 *
	 * @return the open id api key
	 */
	public String getOpenIDApiKey() {
		return getConfiguration().getString(Constants.JANRAIN_API_KEY, Constants.JANRAIN_API_KEY_DEFAULT);
	}

	/**
	 * Gets the open id api url.
	 *
	 * @return the open id api url
	 */
	public String getOpenIDApiURL() {
		return getConfiguration().getString(Constants.JANRAIN_API_URL, Constants.JANRAIN_API_URL_DEFAULT);
	}

	/**
	 * Gets the user access patterns.
	 * 
	 * @return the user access patterns
	 */
	public String[] getUserAccessPatterns() {
		String property = getConfiguration().getString(Constants.ACCESS_USER_PATTERNS, Constants.ACCESS_USER_PATTERNS_DEFAULT);
		StringTokenizer st = new StringTokenizer(property, Constants.ACCESS_PATTERN_SEPARATOR, false);
		String[] returnVal = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			returnVal[i++] = st.nextToken();
		}
		return returnVal;
	}

	/**
	 * Gets the admin access patterns.
	 * 
	 * @return the admin access patterns
	 */
	public String[] getAdminAccessPatterns() {
		String property = getConfiguration().getString(Constants.ACCESS_ADMIN_PATTERNS, Constants.ACCESS_ADMIN_PATTERNS_DEFAULT);
		StringTokenizer st = new StringTokenizer(property, Constants.ACCESS_PATTERN_SEPARATOR, false);
		String[] returnVal = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			returnVal[i++] = st.nextToken();
		}
		return returnVal;
	}

	/**
	 * Gets the recently modified number.
	 * 
	 * @return the recently modified number
	 */
	public int getRecentlyModifiedNumber() {
		return getConfiguration().getInt(Constants.GUI_RECENTLY_MODIFIED_NUMBER, Constants.GUI_RECENTLY_MODIFIED_NUMBER_DEFAULT);
	}

}
