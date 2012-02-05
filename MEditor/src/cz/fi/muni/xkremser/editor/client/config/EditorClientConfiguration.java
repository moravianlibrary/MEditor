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

// TODO: Auto-generated Javadoc
/**
 * The Class EditorClientConfiguration.
 */
public abstract class EditorClientConfiguration {

    /**
     * The Class ServerConstants.
     */
    public static class Constants {

        /** The Constant UNDEF. */
        public static final Integer UNDEF = new Integer(-1);

        /** The Constant DOCUMENT_TYPES. */
        public static final String DOCUMENT_TYPES = "documentTypes";

        /** The Constant DOCUMENT_DEFAULT_TYPES. */
        public static final String[] DOCUMENT_DEFAULT_TYPES = {"periodical", "monograph"};

        /** The Constant KRAMERIUS_HOST. */
        public static final String KRAMERIUS_HOST = "krameriusHost";

        /** The Constant FEDORA_HOST. */
        public static final String FEDORA_HOST = "fedoraHost";

        // GUI
        /** The Constant GUI_SHOW_INPUT_QUEUE. */
        public static final String GUI_SHOW_INPUT_QUEUE = "showInputQueue";

        /** The Constant GUI_SHOW_INPUT_QUEUE_DEFAULT. */
        public static final boolean GUI_SHOW_INPUT_QUEUE_DEFAULT = true;

        public static final String OAI_PMH_URLS = "oaiUrls";

        public static final String[] OAI_PMH_URLS_DEFAULT = {"http://oai.mzk.cz"};

        public static final String OAI_PMH_PREFIXES = "oaiIdPrefixes";

        public static final String[] OAI_PMH_PREFIXES_DEFAULT = {"oai:aleph.mzk.cz:"};

        public static final String OAI_PMH_BASES = "oaiBases";

        public static final String[] OAI_PMH_BASES_DEFAULT = {"MZK01", "MZK03"};

        public static final String OAI_RECORD_IDENTIFIER_LENGTH = "oaiRecordIdentifierLength";

        public static final String VSUP = "vsup";

        public static final String HOSTNAME = "hostname";

        public static final String OAI_RECORD_IDENTIFIER_LENGTH_DEFAULT = "9";
    }

    /**
     * Gets the configuration.
     * 
     * @return the configuration
     */
    public abstract MyConfiguration getConfiguration();

    /**
     * Sets the configuration.
     * 
     * @param configuration
     *        the new configuration
     */
    public abstract void setConfiguration(MyConfiguration configuration);

    /**
     * Gets the show input queue.
     * 
     * @return the show input queue
     */
    public boolean getShowInputQueue() {
        return getConfiguration().getBoolean(Constants.GUI_SHOW_INPUT_QUEUE,
                                             Constants.GUI_SHOW_INPUT_QUEUE_DEFAULT);
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
     * Gets the kramerius host.
     * 
     * @return the kramerius host
     */
    public String getKrameriusHost() {
        return getConfiguration().getString(Constants.KRAMERIUS_HOST, null);
    }

    public String[] getOaiUrls() {
        return getConfiguration().getStringArray(Constants.OAI_PMH_URLS);
    }

    public String[] getOaiBases() {
        return getConfiguration().getStringArray(Constants.OAI_PMH_BASES);
    }

    public String[] getOaiPrefixes() {
        return getConfiguration().getStringArray(Constants.OAI_PMH_PREFIXES);
    }

    public String getOaiRecordIdentifierLength() {
        return getConfiguration().getString(Constants.OAI_RECORD_IDENTIFIER_LENGTH, "9");
    }

    public boolean getVsup() {
        return getConfiguration().getBoolean(Constants.VSUP, false);
    }

    public String getHostname() {
        return getConfiguration().getString(Constants.HOSTNAME, null);
    }

    /**
     * Gets the document types.
     * 
     * @return the document types
     */
    public String[] getDocumentTypes() {
        return getConfiguration().getStringArray(Constants.DOCUMENT_TYPES);
    }

}
