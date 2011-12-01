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
     * The Class ServerConstants.
     */
    public static class ServerConstants {

        /** The Constant UNDEF. */
        public static final Integer UNDEF = new Integer(-1);

        /** The Constant INPUT_QUEUE. */
        public static final String INPUT_QUEUE = "inputQueue";

        /** The Constant IMAGE_SERVER_URL. */
        public static final String IMAGE_SERVER_URL = "imageServer.url";

        /** The Constant IMAGE_SERVER_KNOWN. */
        public static final String IMAGE_SERVER_KNOWN = "imageServer.known";

        /** The Constant IMAGE_SERVER_UNKNOWN. */
        public static final String IMAGE_SERVER_UNKNOWN = "imageServer.unknown";

        /** The Constant DJATOKA_HOME. */
        public static final String DJATOKA_HOME = "djatoka.home";

        public static final String EDITOR_HOME = "editor.home";

        /** The Constant IMAGE_EXTENSIONS. */
        public static final String IMAGE_EXTENSIONS = "imageExtension";

        /** The Constant IMAGE_EXTENSIONS. */
        public static final String[] IMAGE_EXTENSIONS_DEFAULT = {"jpg", "png", "tiff"};

        /** The Constant DOCUMENT_TYPES. */
        public static final String DOCUMENT_TYPES = EditorClientConfiguration.Constants.DOCUMENT_TYPES;

        /** The Constant DOCUMENT_DEFAULT_TYPES. */
        public static final String[] DOCUMENT_TYPES_DEFAULT = {"periodical", "monograph"};

        // GUI
        /** The Constant GUI_CONFIGURATION_PPREFIX. */
        public static final String GUI_CONFIGURATION_PPREFIX = "gui";

        /** The Constant GUI_SHOW_INPUT_QUEUE. */
        public static final String GUI_SHOW_INPUT_QUEUE = GUI_CONFIGURATION_PPREFIX
                + EditorClientConfiguration.Constants.GUI_SHOW_INPUT_QUEUE;

        /** The Constant GUI_SHOW_INPUT_QUEUE_DEFAULT. */
        public static final boolean GUI_SHOW_INPUT_QUEUE_DEFAULT =
                EditorClientConfiguration.Constants.GUI_SHOW_INPUT_QUEUE_DEFAULT;

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
        public static final String[] Z3950_DEFAULT_HOSTS = {"aleph.mzk.cz", "aleph.muni.cz", "sigma.nkp.cz",
                "sigma.nkp.cz"};

        /** The Constant Z3950_DEFAULT_PORTS. */
        public static final String[] Z3950_DEFAULT_PORTS = {"9991", "9991", "9909", "9909"};

        /** The Constant Z3950_DEFAULT_BASES. */
        public static final String[] Z3950_DEFAULT_BASES = {"MZK01-UTF", "MUB01", "SKC", "NKC"};

        /** The Constant Z3950_DEFAULT_BAR_LENGTH. */
        public static final int[] Z3950_DEFAULT_BAR_LENGTH = {10, 10, 10, 10};

        public static final String OAI_PMH_URLS = EditorClientConfiguration.Constants.OAI_PMH_URLS;

        public static final String[] OAI_PMH_URLS_DEFAULT =
                EditorClientConfiguration.Constants.OAI_PMH_URLS_DEFAULT;

        public static final String OAI_PMH_PREFIXES = EditorClientConfiguration.Constants.OAI_PMH_PREFIXES;

        public static final String[] OAI_PMH_PREFIXES_DEFAULT =
                EditorClientConfiguration.Constants.OAI_PMH_PREFIXES_DEFAULT;

        public static final String OAI_PMH_BASES = EditorClientConfiguration.Constants.OAI_PMH_BASES;

        public static final String[] OAI_PMH_BASES_DEFAULT =
                EditorClientConfiguration.Constants.OAI_PMH_BASES_DEFAULT;

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
        public static final String ACCESS_ADMIN_PATTERNS_DEFAULT = "127.*" + ACCESS_PATTERN_SEPARATOR
                + "localhost";

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

        public static final String LOCALHOST = "localhost";

        public static final String ACCESS_LOG_ID = "cz.fi.muni.xkremser.editor.AccessLog";

        public static final String INGEST_LOG_ID = "cz.fi.muni.xkremser.editor.IngestLog";

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
     *        the new configuration
     */
    public abstract void setConfiguration(Configuration configuration);

    /**
     * Gets the client configuration.
     * 
     * @return the client configuration
     */
    public Configuration getClientConfiguration() {
        return getConfiguration().subset(ServerConstants.GUI_CONFIGURATION_PPREFIX);
    }

    /**
     * Gets the show input queue.
     * 
     * @return the show input queue
     */
    public boolean getShowInputQueue() {
        return getConfiguration().getBoolean(ServerConstants.GUI_SHOW_INPUT_QUEUE,
                                             ServerConstants.GUI_SHOW_INPUT_QUEUE_DEFAULT);
    }

    /**
     * Gets the scan input queue path.
     * 
     * @return the scan input queue path
     */
    public String getScanInputQueuePath() {
        return getConfiguration().getString(ServerConstants.INPUT_QUEUE);
    }

    /**
     * Gets the image server url.
     * 
     * @return the image server url
     */
    public String getImageServerUrl() {
        return getConfiguration().getString(ServerConstants.IMAGE_SERVER_URL);
    }

    /**
     * Gets the path to image server directory where data with known sysno is
     * stored.
     * 
     * @return the path to image directory (known)
     */
    public String getImageServerKnown() {
        return getConfiguration().getString(ServerConstants.IMAGE_SERVER_KNOWN);
    }

    /**
     * Gets the path to image server directory where data with unknown sysno is
     * stored.
     * 
     * @return the path to image directory (unknown)
     */
    public String getImageServerUnknown() {
        return getConfiguration().getString(ServerConstants.IMAGE_SERVER_UNKNOWN);
    }

    /**
     * Gets the scan Djatoka home path.
     * 
     * @return the scan Djatoka home path
     */
    public String getDjatokaHome() {
        return getConfiguration().getString(ServerConstants.DJATOKA_HOME);
    }

    /**
     * Gets the editor home path.
     * 
     * @return the editor home path
     */
    public String getEditorHome() {
        return getConfiguration().getString(ServerConstants.EDITOR_HOME);
    }

    /**
     * Gets the z3950 profile.
     * 
     * @return the z3950 profile
     */
    public String getZ3950Profile() {
        return getConfiguration().getString(ServerConstants.Z3950_PROFILE,
                                            ServerConstants.Z3950_PROFILE_DEFAULT);
    }

    /**
     * Gets the z3950 host.
     * 
     * @return the z3950 host
     */
    public String getZ3950Host() {
        return getConfiguration().getString(ServerConstants.Z3950_HOST);
    }

    /**
     * Gets the z3950 port.
     * 
     * @return the z3950 port
     */
    public String getZ3950Port() {
        return getConfiguration().getString(ServerConstants.Z3950_PORT);
    }

    /**
     * Gets the z3950 base.
     * 
     * @return the z3950 base
     */
    public String getZ3950Base() {
        return getConfiguration().getString(ServerConstants.Z3950_BASE);
    }

    /**
     * Gets the z3950 bar length.
     * 
     * @return the z3950 bar length
     */
    public Integer getZ3950BarLength() {
        return getConfiguration().getInteger(ServerConstants.Z3950_BAR_LENGTH, ServerConstants.UNDEF);
    }

    public String[] getOaiUrls() {
        String[] foo = getConfiguration().getStringArray(ServerConstants.OAI_PMH_URLS);
        if (foo == null || foo.length == 0) {
            return ServerConstants.OAI_PMH_URLS_DEFAULT;
        } else
            return foo;
    }

    public String[] getOaiPrefixes() {
        String[] foo = getConfiguration().getStringArray(ServerConstants.OAI_PMH_PREFIXES);
        if (foo == null || foo.length == 0) {
            return ServerConstants.OAI_PMH_PREFIXES_DEFAULT;
        } else
            return foo;
    }

    public String[] getOaiBases() {
        String[] foo = getConfiguration().getStringArray(ServerConstants.OAI_PMH_BASES);
        if (foo == null || foo.length == 0) {
            return ServerConstants.OAI_PMH_BASES_DEFAULT;
        } else
            return foo;
    }

    /**
     * Gets the kramerius host.
     * 
     * @return the kramerius host
     */
    public String getKrameriusHost() {
        return getConfiguration().getString(ServerConstants.KRAMERIUS_HOST, null);
    }

    /**
     * Gets the kramerius login.
     * 
     * @return the kramerius login
     */
    public String getKrameriusLogin() {
        return getConfiguration().getString(ServerConstants.KRAMERIUS_LOGIN, null);
    }

    /**
     * Gets the kramerius password.
     * 
     * @return the kramerius password
     */
    public String getKrameriusPassword() {
        return getConfiguration().getString(ServerConstants.KRAMERIUS_PASSWORD, null);
    }

    /**
     * Gets the fedora host.
     * 
     * @return the fedora host
     */
    public String getFedoraHost() {
        return getConfiguration().getString(ServerConstants.FEDORA_HOST, null);
    }

    /**
     * Gets the fedora login.
     * 
     * @return the fedora login
     */
    public String getFedoraLogin() {
        return getConfiguration().getString(ServerConstants.FEDORA_LOGIN,
                                            ServerConstants.FEDORA_LOGIN_DEFAULT);
    }

    /**
     * Gets the fedora password.
     * 
     * @return the fedora password
     */
    public String getFedoraPassword() {
        return getConfiguration().getString(ServerConstants.FEDORA_PASSWORD);
    }

    /**
     * Gets the document types.
     * 
     * @return the document types
     */
    public String[] getDocumentTypes() {
        String[] foo = getConfiguration().getStringArray(ServerConstants.DOCUMENT_TYPES);
        if (foo == null || foo.length == 0) {
            return ServerConstants.DOCUMENT_TYPES_DEFAULT;
        } else
            return foo;
    }

    /**
     * Gets the image extensions.
     * 
     * @return the image extensions
     */
    public String[] getImageExtensions() {
        String[] foo = getConfiguration().getStringArray(ServerConstants.IMAGE_EXTENSIONS);
        if (foo == null || foo.length == 0) {
            return ServerConstants.IMAGE_EXTENSIONS_DEFAULT;
        } else
            return foo;
    }

    /**
     * Gets the dB host.
     * 
     * @return the dB host
     */
    public String getDBHost() {
        return getConfiguration().getString(ServerConstants.DB_HOST, ServerConstants.DB_HOST_DEFAULT);
    }

    /**
     * Gets the dB port.
     * 
     * @return the dB port
     */
    public String getDBPort() {
        return getConfiguration().getString(ServerConstants.DB_PORT, ServerConstants.DB_PORT_DEFAULT);
    }

    /**
     * Gets the dB login.
     * 
     * @return the dB login
     */
    public String getDBLogin() {
        return getConfiguration().getString(ServerConstants.DB_LOGIN, ServerConstants.DB_LOGIN_DEFAULT);
    }

    /**
     * Gets the dB password.
     * 
     * @return the dB password
     */
    public String getDBPassword() {
        return getConfiguration().getString(ServerConstants.DB_PASSWORD);
    }

    /**
     * Gets the dB name.
     * 
     * @return the dB name
     */
    public String getDBName() {
        return getConfiguration().getString(ServerConstants.DB_NAME, ServerConstants.DB_NAME_DEFAULT);
    }

    /**
     * Gets the open id api key.
     * 
     * @return the open id api key
     */
    public String getOpenIDApiKey() {
        return getConfiguration().getString(ServerConstants.JANRAIN_API_KEY,
                                            ServerConstants.JANRAIN_API_KEY_DEFAULT);
    }

    /**
     * Gets the open id api url.
     * 
     * @return the open id api url
     */
    public String getOpenIDApiURL() {
        return getConfiguration().getString(ServerConstants.JANRAIN_API_URL,
                                            ServerConstants.JANRAIN_API_URL_DEFAULT);
    }

    /**
     * Gets the user access patterns.
     * 
     * @return the user access patterns
     */
    public String[] getUserAccessPatterns() {
        String property =
                getConfiguration().getString(ServerConstants.ACCESS_USER_PATTERNS,
                                             ServerConstants.ACCESS_USER_PATTERNS_DEFAULT);
        StringTokenizer st = new StringTokenizer(property, ServerConstants.ACCESS_PATTERN_SEPARATOR, false);
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
        String property =
                getConfiguration().getString(ServerConstants.ACCESS_ADMIN_PATTERNS,
                                             ServerConstants.ACCESS_ADMIN_PATTERNS_DEFAULT);
        StringTokenizer st = new StringTokenizer(property, ServerConstants.ACCESS_PATTERN_SEPARATOR, false);
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
        return getConfiguration().getInt(ServerConstants.GUI_RECENTLY_MODIFIED_NUMBER,
                                         ServerConstants.GUI_RECENTLY_MODIFIED_NUMBER_DEFAULT);
    }

    public boolean isLocalhost() {
        return getConfiguration().getBoolean(ServerConstants.LOCALHOST, false);
    }
}
