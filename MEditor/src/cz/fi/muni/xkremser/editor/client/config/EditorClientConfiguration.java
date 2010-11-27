/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.config;

// TODO: Auto-generated Javadoc
/**
 * The Class EditorClientConfiguration.
 */
public abstract class EditorClientConfiguration {

	/**
	 * The Class Constants.
	 */
	public static class Constants {

		/** The Constant UNDEF. */
		public static final Integer UNDEF = new Integer(-1);

		/** The Constant DOCUMENT_TYPES. */
		public static final String DOCUMENT_TYPES = "documentTypes";

		/** The Constant DOCUMENT_DEFAULT_TYPES. */
		public static final String[] DOCUMENT_DEFAULT_TYPES = { "periodical", "monograph" };

		public static final String KRAMERIUS_HOST = "krameriusHost";

		/** The Constant FEDORA_HOST. */
		public static final String FEDORA_HOST = "fedoraHost";

		// GUI
		/** The Constant GUI_SHOW_INPUT_QUEUE. */
		public static final String GUI_SHOW_INPUT_QUEUE = "showInputQueue";

		/** The Constant GUI_SHOW_INPUT_QUEUE_DEFAULT. */
		public static final boolean GUI_SHOW_INPUT_QUEUE_DEFAULT = true;

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
	 *          the new configuration
	 */
	public abstract void setConfiguration(MyConfiguration configuration);

	/**
	 * Gets the show input queue.
	 * 
	 * @return the show input queue
	 */
	public boolean getShowInputQueue() {
		return getConfiguration().getBoolean(Constants.GUI_SHOW_INPUT_QUEUE, Constants.GUI_SHOW_INPUT_QUEUE_DEFAULT);
	}

	public String getFedoraHost() {
		return getConfiguration().getString(Constants.FEDORA_HOST, null);
	}

	public String getKrameriusHost() {
		return getConfiguration().getString(Constants.KRAMERIUS_HOST, null);
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
