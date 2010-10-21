package cz.fi.muni.xkremser.editor.client;

public class Constants {
	private static final String SERVLET_IMAGES_PREFIX = "images/";
	// must be the same as in web.xml
	public static final String SERVLET_THUMBNAIL_PREFIX = "thumbnail";
	public static final String SERVLET_FULL_PREFIX = "full";

	public static final String URL_PARAM_UUID = "uuid";
	public static final String URL_PARAM_ACTION = "action";
	public static final String URL_PARAM_ACTION_MODIFY = "modify";

	// db
	public static final String TABLE_INPUT_QUEUE_NAME = "input_queue_item";
	public static final String TABLE_RECENTLY_MODIFIED_NAME = "recently_modified_item";

	// input queue tree
	public static final String ATTR_ID = "path"; // path
	public static final String ATTR_PARENT = "parent";
	public static final String ATTR_NAME = "atName";
	public static final String ATTR_PICTURE = "picture";
	public static final String ATTR_ISSN = "issn";
	public static final int DIR_MAX_DEPTH = 5;
	public static final String FILE_SEPARATOR = "/";

	// recently modified tree
	public static final String ATTR_UUID = "uuid";
	public static final String ATTR_DESC = "description";
	public static final String ATTR_MODEL = "model";
	public static final String ATTR_ALL = "all";

	// fedora
	public static final String FEDORA_MODEL_PREFIX = "model:";
	public static final String FEDORA_UUID_PREFIX = "uuid:";
}
