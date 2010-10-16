package cz.fi.muni.xkremser.editor.client;

public class Constants {
	public static final String URL_PARAM_UUID = "uuid";
	public static final String URL_PARAM_ACTION = "action";
	public static final String URL_PARAM_ACTION_MODIFY = "modify";

	// input queue tree
	public static final String ATTR_ID = "path"; // path
	public static final String ATTR_PARENT = "parent";
	public static final String ATTR_NAME = "atName";
	public static final String ATTR_ISSN = "issn";
	public static final String TABLE_INPUT_QUEUE_NAME = "input_queue_item";
	public static final int DIR_MAX_DEPTH = 5;
	public static final String FILE_SEPARATOR = "/";

	// fedora
	public static final String FEDORA_MODEL_PREFIX = "model:";

	// kramerius
	public enum KrameriusModel {

		MONOGRAPH("monograph"), MONOGRAPHUNIT("monographunit"), PERIODICAL("periodical"), PERIODICALVOLUME("periodicalvolume"), PERIODICALITEM("periodicalitem"), PAGE(
				"page"), INTERNALPART("internalpart")/* , DONATOR("donator") */;

		private KrameriusModel(String value) {
			this.value = value;
		}

		private final String value;

		public String getValue() {
			return value;
		}

		public static String toString(KrameriusModel km) {
			return km.getValue();
		}
	}

}
