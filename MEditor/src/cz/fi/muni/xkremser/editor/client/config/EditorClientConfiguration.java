package cz.fi.muni.xkremser.editor.client.config;

public abstract class EditorClientConfiguration {
	public static class Constants {
		public static final Integer UNDEF = new Integer(-1);
		public static final String DOCUMENT_TYPES = "document_types";
		public static final String[] DOCUMENT_DEFAULT_TYPES = { "periodical", "monograph" };

		// GUI
		public static final String GUI_SHOW_INPUT_QUEUE = "show_input_queue";
		public static final boolean GUI_SHOW_INPUT_QUEUE_DEFAULT = true;

	}

	public abstract MyConfiguration getConfiguration();

	public abstract void setConfiguration(MyConfiguration configuration);

	public boolean getShowInputQueue() {
		return getConfiguration().getBoolean(Constants.GUI_SHOW_INPUT_QUEUE, Constants.GUI_SHOW_INPUT_QUEUE_DEFAULT);
	}

	public String[] getDocumentTypes() {
		return getConfiguration().getStringArray(Constants.DOCUMENT_TYPES);
	}

}
