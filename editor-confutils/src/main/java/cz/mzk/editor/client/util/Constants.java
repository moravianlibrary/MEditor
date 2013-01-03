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

package cz.mzk.editor.client.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class Constants.
 * 
 * @author Jiri Kremser
 */
public class Constants {

    /** The Constant TYPE_MEDIT_MAIN_CONTENT. */
    public static final Type<RevealContentHandler<?>> TYPE_MEDIT_MAIN_CONTENT =
            new Type<RevealContentHandler<?>>();

    /** The Constant TYPE_MEDIT_LEFT_CONTENT. */
    public static final Type<RevealContentHandler<?>> TYPE_MEDIT_LEFT_CONTENT =
            new Type<RevealContentHandler<?>>();

    /** The Constant TYPE_ADMIN_MAIN_CONTENT. */
    public static final Type<RevealContentHandler<?>> TYPE_ADMIN_MAIN_CONTENT =
            new Type<RevealContentHandler<?>>();

    /** The Constant TYPE_ADMIN_LEFT_CONTENT. */
    public static final Type<RevealContentHandler<?>> TYPE_ADMIN_LEFT_CONTENT =
            new Type<RevealContentHandler<?>>();

    public static final String INVALID_LOGIN_OR_PASSWORD = "Invalid login or password";

    /** The Constant LOGO_HTML. */
    public static final String LOGO_HTML =
            "<a href='/meditor'><img class='noFx' src='images/logo_bw.png' width='162' height='50' alt='logo'></a>";

    /** The Constant SERVLET_IMAGES_PREFIX. */
    public static final String SERVLET_IMAGES_PREFIX = "images/";
    // must be the same as in web.xml
    /** The Constant SERVLET_THUMBNAIL_PREFIX. */
    public static final String SERVLET_THUMBNAIL_PREFIX = "thumbnail";

    /** The Constant SERVLET_SCANS_PREFIX. */
    public static final String SERVLET_SCANS_PREFIX = "scan";

    /** The Constant SERVLET_FULL_PREFIX. */
    public static final String SERVLET_FULL_PREFIX = "full";

    /** The Constant SERVLET_DOWNLOAD_FOXML_PREFIX. */
    public static final String SERVLET_DOWNLOAD_FOXML_PREFIX = "download/foxml";

    /** The Constant SERVLET_DOWNLOAD_DATASTREAMS_PREFIX. */
    public static final String SERVLET_DOWNLOAD_DATASTREAMS_PREFIX = "download/datastreams";

    /** The Constant SERVLET_DOWNLOAD_DATASTREAMS_PREFIX. */
    public static final String SERVLET_GET_PDF_PREFIX = "getPdf/";

    /** The Constant XML_HEADER_WITH_BACKSLASHES *. */
    public static final String XML_HEADER_WITH_BACKSLASHES = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    /** The Constant PARAM_UUID. */
    public static final String PARAM_UUID = "uuid";

    /** The Constant PARAM_PID. */
    public static final String PARAM_PID = "pid";

    /** The Constant PARAM_USER_NAME. */
    public static final String PARAM_USER_NAME = "username";

    /** The Constant PARAM_TIME. */
    public static final String PARAM_TIME = "time";

    /** The Constant PARAM_CONTENT. */
    public static final String PARAM_CONTENT = "content";

    /** The Constant PARAM_DATASTREAM. */
    public static final String PARAM_DATASTREAM = "datastream";

    /** The Constant URL_PARAM_NOT_SCALE. */
    public static final String URL_PARAM_NOT_SCALE = "not_scale";

    /** The Constant URL_PARAM_UUID. */
    public static final String URL_PARAM_UUID = "pids";

    /** The Constant URL_PARAM_REFRESH. */
    public static final String URL_PARAM_REFRESH = "refresh";

    /** The Constant URL_PARAM_ACTION. */
    public static final String URL_PARAM_ACTION = "action";

    /** The Constant URL_PARAM_ACTION_MODIFY. */
    public static final String URL_PARAM_ACTION_MODIFY = "modify";

    /** The Constant URL_PARAM_SYSNO. */
    public static final String URL_PARAM_SYSNO = "sysno";

    /** The Constant URL_PARAM_PATH. */
    public static final String URL_PARAM_PATH = "path";

    /** The Constant URL_PARAM_METADATA. */
    public static final String URL_PARAM_METADATA = "metadata";

    /** The Constant URL_PARAM_METADATA_FOUND. */
    public static final String URL_PARAM_METADATA_FOUND = "found";

    /** The Constant URL_PARAM_METADATA_NOT_FOUND. */
    public static final String URL_PARAM_METADATA_NOT_FOUND = "notFound";

    /** The Constant URL_PARAM_FULL. */
    public static final String URL_PARAM_FULL = "full";

    /** The Constant URL_PARAM_HEIGHT. */
    public static final String URL_PARAM_HEIGHT = "height";

    /** The Constant URL_PARAM_TOP_SPACE. */
    public static final String URL_PARAM_TOP_SPACE = "topSpace";

    /** The Constant URL_PARAM_BASE. */
    public static final String URL_PARAM_BASE = "base";

    /** The Constant URL_PARAM_PDF_PATH. */
    public static final String URL_PARAM_PDF_PATH = "pdfPath";

    /** The Constant URL_PDF_FROM_FEDORA_SUFFIX. */
    public static final String URL_PDF_FROM_FEDORA_PREFIX = "fromFedora/";

    /** The Constant PATH_TO_PDF_VIEWER. */
    public static final String PATH_TO_PDF_VIEWER = "pdfViewer/web/viewer.html";

    /**
     * The Enum EDITOR_RIGHTS.
     */
    public static enum EDITOR_RIGHTS {

        ALL(""),
        //Admin
        EDIT_USERS(""), SHOW_ALL_STORED_AND_LOCKS(""), SHOW_ALL_STATISTICS(""), SHOW_STATISTICS(""),
        SHOW_ALL_HISTORY(""), SHOW_DO_HISTORY_USERS(""), EDIT_ROLES(""),
        //Edit
        OPEN_OBJECT(""), PUBLISH(""), DELETE(""), LONG_RUNNING_PROCESS(""),
        //create
        CREATE_NEW_OBJECTS(""), FIND_METADATA(""), SCAN_FOLDER_TO_CONVERT("");

        /** The desc. */
        private final String desc;

        /**
         * Instantiates a new eDITO r_ rights.
         * 
         * @param desc
         *        the desc
         */
        private EDITOR_RIGHTS(String desc) {
            this.desc = desc;
        }

        /**
         * Gets the desc.
         * 
         * @return the desc
         */
        public String getDesc() {
            return desc;
        }

        /**
         * Parses the string.
         * 
         * @param s
         *        the s
         * @return the eDITO r_ rights
         */
        public static EDITOR_RIGHTS parseString(String s) {
            for (EDITOR_RIGHTS right : EDITOR_RIGHTS.values()) {
                if (right.toString().equalsIgnoreCase(s)) return right;
            }
            return null;
        }

    }

    // db
    /** Path to a file with current DB schema. */
    public static final String SCHEMA_PATH = "schema.sql";

    /** Path to directory of backed up schemas. */
    public static final String DB_BACKUP_DIR = "backupDB";

    /** A name of the script for running a process via ssh on a remote machine. */
    public static final String SCRIPT_FOR_REMOTE_PROCESS = "runRemoteProcess.sh";

    /** Path to a file with current DB schema version number. */
    public static final String SCHEMA_VERSION_PATH = "schemaVersion.txt";

    /**
     * The Enum OLD_DB_TABLES.
     */
    public static enum OLD_DB_TABLES {

        /** The table input_queue_item. */
        TABLE_INPUT_QUEUE_ITEM("input_queue_item"),

        /** The table input_queue_item_name. */
        TABLE_INPUT_QUEUE_ITEM_NAME("input_queue_item_name"),

        /** The table image. */
        TABLE_IMAGE_NAME("image"),

        /** The table recently_modified_item. */
        TABLE_RECENTLY_MODIFIED_NAME("recently_modified_item"),

        /** The table description. */
        TABLE_DESCRIPTION("description"),

        /** The table editor_user. */
        TABLE_EDITOR_USER("editor_user"),

        /** The table lock. */
        TABLE_LOCK("lock"),

        /** The table stored_files. */
        TABLE_STORED_FILES("stored_files"),

        /** The table role. */
        TABLE_ROLE("role"),

        /** The table user_in_role. */
        TABLE_USER_IN_ROLE("user_in_role"),

        /** The table open_id_identity. */
        TABLE_OPEN_ID_IDENTITY("open_id_identity"),

        /** The table version. */
        TABLE_VERSION_NAME("version"),

        /** The table tree_structure_node. */
        TABLE_TREE_STRUCTURE_NODE_NAME("tree_structure_node"),

        /** The table tree_structure. */
        TABLE_TREE_STRUCTURE_NAME("tree_structure"),

        /** The table request_for_adding. */
        TABLE_REQUEST_FOR_ADDING("request_for_adding");

        /** The table name. */
        private final String tableName;

        /**
         * Instantiates a new old_ db_tables.
         * 
         * @param tableName
         *        the table name
         */
        private OLD_DB_TABLES(String tableName) {
            this.tableName = tableName;
        }

        /**
         * Gets the table name.
         * 
         * @return the tableName
         */
        public String getTableName() {
            return tableName;
        }
    }

    /**
     * The Enum CRUD_ACTION_TYPES.
     */
    public static enum CRUD_ACTION_TYPES {

        /** The CREATE. */
        CREATE("c"), /** The READ. */
        READ("r"), /** The UPDATE. */
        UPDATE("u"), /** The DELETE. */
        DELETE("d");

        /** The value. */
        private final String value;

        /**
         * Instantiates a new crUd_action_types.
         * 
         * @param value
         *        the value
         */
        private CRUD_ACTION_TYPES(String value) {
            this.value = value;
        }

        /**
         * Gets the value.
         * 
         * @return the value
         */
        public String getValue() {
            return value;
        }

        /**
         * Parses the string.
         * 
         * @param s
         *        the s
         * @return the cRU d_ actio n_ types
         */
        public static CRUD_ACTION_TYPES parseString(String s) {
            for (CRUD_ACTION_TYPES action : CRUD_ACTION_TYPES.values()) {
                if (action.getValue().equalsIgnoreCase(s)) return action;
            }
            throw new RuntimeException("Unsupported CRUD action type: " + s);
        }

    }

    public static enum STATISTICS_SEGMENTATION {

        YEARS("years"), MONTHS("months"), WEEKS("weeks"), DAYS("days");

        private final String value;

        /**
         * 
         */
        private STATISTICS_SEGMENTATION(String value) {
            this.value = value;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }

        public static STATISTICS_SEGMENTATION parseString(String s) {
            for (STATISTICS_SEGMENTATION seg : STATISTICS_SEGMENTATION.values()) {
                if (seg.getValue().equalsIgnoreCase(s)) return seg;
            }

            throw new RuntimeException("Unsupported STATISTICS SEGMENTATION type: " + s);
        }
    }

    /**
     * The Enum ACTION_SUBJECTS.
     */
    public static enum ACTION_SUBJECTS {

        /** The DIGITA l_ object. */
        DIGITAL_OBJECT,
        /** The LO g_ i n_ out. */
        LOG_IN_OUT,
        /** The USER. */
        USER,
        /** The LON g_ process. */
        LONG_PROCESS,
        /** The LOCK. */
        LOCK,
        /** The SAVE d_ edited. */
        SAVED_EDITED,
        /** The CONVERSION. */
        CONVERSION,
        /** The TRE e_ structure. */
        TREE_STRUCTURE;
    }

    /**
     * The Enum REQUESTS_TO_ADMIN_TYPES.
     */
    public static enum REQUESTS_TO_ADMIN_TYPES {

        /** The ADDIN g_ ne w_ acount. */
        ADDING_NEW_ACOUNT("Adding a new acount");

        /** The value. */
        private final String value;

        /**
         * Instantiates a new requests_to_ admin_types.
         * 
         * @param value
         *        the value
         */
        private REQUESTS_TO_ADMIN_TYPES(String value) {
            this.value = value;
        }

        /**
         * Gets the value.
         * 
         * @return the value
         */
        public String getValue() {
            return value;
        }

        /**
         * Parses the string.
         * 
         * @param s
         *        the s
         * @return the rEQUEST s_ t o_ admi n_ types
         */
        public static REQUESTS_TO_ADMIN_TYPES parseString(String s) {
            for (REQUESTS_TO_ADMIN_TYPES request : REQUESTS_TO_ADMIN_TYPES.values()) {
                if (request.getValue().equalsIgnoreCase(s)) return request;
            }
            return null;
        }

    }

    // tables

    /**
     * The Enum DEFAULT_SYSTEM_USERS.
     */
    public static enum DEFAULT_SYSTEM_USERS {

        /** The NO n_ existent. */
        NON_EXISTENT(new Long(1), "non-existent"), /** The TIME. */
        TIME(new Long(2), "time");

        /** The user id. */
        private final Long userId;

        /** The user name. */
        private final String userName;

        /**
         * Instantiates a new dEFAUL t_ syste m_ users.
         * 
         * @param userId
         *        the user id
         * @param userName
         *        the user name
         */
        private DEFAULT_SYSTEM_USERS(Long userId, String userName) {
            this.userId = userId;
            this.userName = userName;
        }

        /**
         * Gets the user id.
         * 
         * @return the userId
         */
        public Long getUserId() {
            return userId;
        }

        /**
         * Gets the user name.
         * 
         * @return the userName
         */
        public String getUserName() {
            return userName;
        }

        /**
         * Checks if is default sys user.
         * 
         * @param userId
         *        the user id
         * @return true, if is default sys user
         */
        public static boolean isDefaultSysUser(Long userId) {
            for (DEFAULT_SYSTEM_USERS user : DEFAULT_SYSTEM_USERS.values()) {
                if (user.getUserId().equals(userId)) return true;
            }
            return false;
        }

    }

    /** The Constant TABLE_ACTION. */
    public static final String TABLE_ACTION = "action";

    /** The Constant TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT. */
    public static final String TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT = "crud_do_action_with_top_object";

    /** The Constant TABLE_CONVERSION. */
    public static final String TABLE_CONVERSION = "conversion";

    /** The Constant TABLE_CRUD_DIGITAL_OBJECT_ACTION. */
    public static final String TABLE_CRUD_DIGITAL_OBJECT_ACTION = "crud_digital_object_action";

    /** The Constant TABLE_CRUD_LOCK_ACTION. */
    public static final String TABLE_CRUD_LOCK_ACTION = "crud_lock_action";

    /** The Constant TABLE_CRUD_REQUEST_TO_ADMIN_ACTION. */
    public static final String TABLE_CRUD_REQUEST_TO_ADMIN_ACTION = "crud_request_to_admin_action";

    /** The Constant TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION. */
    public static final String TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION = "crud_saved_edited_object_action";

    /** The Constant TABLE_CRUD_TREE_STRUCTURE_ACTION. */
    public static final String TABLE_CRUD_TREE_STRUCTURE_ACTION = "crud_tree_structure_action";

    /** The Constant TABLE_DESCRIPTION. */
    public static final String TABLE_DESCRIPTION = "description";

    /** The Constant TABLE_DIGITAL_OBJECT. */
    public static final String TABLE_DIGITAL_OBJECT = "digital_object";

    /** The Constant TABLE_EDITOR_RIGHT. */
    public static final String TABLE_EDITOR_RIGHT = "editor_right";

    /** The Constant TABLE_EDITOR_USER. */
    public static final String TABLE_EDITOR_USER = "editor_user";

    /** The Constant TABLE_IMAGE. */
    public static final String TABLE_IMAGE = "image ";

    /** The Constant TABLE_INPUT_QUEUE. */
    public static final String TABLE_INPUT_QUEUE = "input_queue";

    /** The Constant TABLE_INPUT_QUEUE_ITEM. */
    public static final String TABLE_INPUT_QUEUE_ITEM = "input_queue_item";

    /** The Constant TABLE_LDAP_IDENTITY. */
    public static final String TABLE_LDAP_IDENTITY = "ldap_identity";

    /** The Constant TABLE_LOCK. */
    public static final String TABLE_LOCK = "lock  ";

    /** The Constant TABLE_LOG_IN_OUT. */
    public static final String TABLE_LOG_IN_OUT = "log_in_out";

    /** The Constant TABLE_LONG_RUNNING_PROCESS. */
    public static final String TABLE_LONG_RUNNING_PROCESS = "long_running_process";

    /** The Constant TABLE_OPEN_ID_IDENTITY. */
    public static final String TABLE_OPEN_ID_IDENTITY = "open_id_identity";

    /** The Constant TABLE_REQUEST_TO_ADMIN. */
    public static final String TABLE_REQUEST_TO_ADMIN = "request_to_admin";

    /** The Constant TABLE_RIGHT_IN_ROLE. */
    public static final String TABLE_RIGHT_IN_ROLE = "right_in_role";

    /** The Constant TABLE_ROLE. */
    public static final String TABLE_ROLE = "role  ";

    /** The Constant TABLE_SAVED_EDITED_OBJECT. */
    public static final String TABLE_SAVED_EDITED_OBJECT = "saved_edited_object";

    /** The Constant TABLE_SHIBBOLETH_IDENTITY. */
    public static final String TABLE_SHIBBOLETH_IDENTITY = "shibboleth_identity";

    /** The Constant TABLE_TREE_STRUCTURE. */
    public static final String TABLE_TREE_STRUCTURE = "tree_structure";

    /** The Constant TABLE_TREE_STRUCTURE_NODE. */
    public static final String TABLE_TREE_STRUCTURE_NODE = "tree_structure_node";

    /** The Constant TABLE_USER_EDIT. */
    public static final String TABLE_USER_EDIT = "user_edit";

    /** The Constant TABLE_USERS_RIGHT. */
    public static final String TABLE_USERS_RIGHT = "users_right";

    /** The Constant TABLE_USERS_ROLE. */
    public static final String TABLE_USERS_ROLE = "users_role";

    /** The Constant TABLE_VERSION. */
    public static final String TABLE_VERSION = "version";

    //new tables

    /** The Constant SEQUENCE_EDITOR_USER. */
    public static final String SEQUENCE_EDITOR_USER = "seq_user";

    /** The Constant SEQUENCE_OPEN_ID_IDENTITY. */
    public static final String SEQUENCE_OPEN_ID_IDENTITY = "seq_open_id_identity";

    /** The Constant SEQUENCE_TREE_STRUCTURE. */
    public static final String SEQUENCE_TREE_STRUCTURE = "seq_tree_structure";

    /** The Constant SEQUENCE_ROLE. */
    public static final String SEQUENCE_ROLE = "seq_user_in_role";

    /** The Constant ATTR_ID. */
    public static final String ATTR_ID = "path"; // path

    /** The Constant ATTR_TAB_ID. */
    public static final String ATTR_TAB_ID = "tabId";

    /** The Constant ALL_PAGES_TAB. */
    public static final String ALL_PAGES_TAB = "allPagesTab";

    /** The Constant SELECTED_PAGES_TAB. */
    public static final String SELECTED_PAGES_TAB = "selectedPagesTab";

    /** The Constant ATTR_PARENT. */
    public static final String ATTR_PARENT = "parent";

    /** The Constant ATTR_NAME. */
    public static final String ATTR_NAME = "atName";

    /** The Constant ATTR_ORDER. */
    public static final String ATTR_ORDER = "order";

    /** The Constant ATTR_SURNAME. */
    public static final String ATTR_SURNAME = "surname";

    /** The Constant ATTR_MODEL_ID. */
    public static final String ATTR_MODEL_ID = "modelId";

    /** The Constant ATTR_CREATE. */
    public static final String ATTR_CREATE = "create";

    /** The Constant ATTR_EXIST. */
    public static final String ATTR_EXIST = "exist";

    /** The Constant ATTR_IDENTITY. */
    public static final String ATTR_IDENTITY = "identity";

    /** The Constant ATTR_DATE. */
    public static final String ATTR_DATE = "date";

    /** The Constant ATTR_SEX. */
    public static final String ATTR_SEX = "sex";

    /** The Constant ATTR_USER_ID. */
    public static final String ATTR_USER_ID = "uid";

    /** The Constant ATTR_GENERIC_ID. */
    public static final String ATTR_GENERIC_ID = "id";

    /** The Constant ATTR_PICTURE_OR_UUID. */
    public static final String ATTR_PICTURE_OR_UUID = "pictureOrUuid";

    /** The Constant ATTR_BARCODE. */
    public static final String ATTR_BARCODE = "barcode";

    /** The Constant ATTR_ALTO_PATH. */
    public static final String ATTR_ALTO_PATH = "altoPath";

    /** The Constant ATTR_OCR_PATH. */
    public static final String ATTR_OCR_PATH = "ocrPath";

    /** The Constant ATTR_PAGE_TYPE. */
    public static final String ATTR_TYPE = "type";

    /** The Constant ATTR_PART_NUMBER_OR_ALTO. */
    public static final String ATTR_PART_NUMBER_OR_ALTO = "partNumberOrAlto";

    /** The Constant ATTR_DATE_OR_INT_PART_NAME. */
    public static final String ATTR_DATE_OR_INT_PART_NAME = "dateOrIntPartName";

    /** The Constant ATTR_NOTE_OR_INT_SUBTITLE. */
    public static final String ATTR_NOTE_OR_INT_SUBTITLE = "noteOrIntSubtitle";

    /** The Constant ATTR_ADITIONAL_INFO_OR_OCR. */
    public static final String ATTR_ADITIONAL_INFO_OR_OCR = "aditionalInfoOrOcr";

    /** The Constant ATTR_INGEST_INFO. */
    public static final String ATTR_INGEST_INFO = "ingestInfo";

    /** The Constant ATTR_OWNER. */
    public static final String ATTR_OWNER = "owner";

    /** The Constant ATTR_INPUT_PATH. */
    public static final String ATTR_INPUT_PATH = "inputPath";

    /** The Constant ATTR_CONVERSION_DATE. */
    public static final String ATTR_CONVERSION_DATE = "conversionDate";

    /** The Constant ATTR_IS_CONVERTED. */
    public static final String ATTR_IS_CONVERTED = "isConverted";

    /** The Constant DIR_MAX_DEPTH. */
    public static final int DIR_MAX_DEPTH = 5;

    /** The Constant CLIPBOARD_MAX_WITHOUT_PROGRESSBAR. */
    public static final int CLIPBOARD_MAX_WITHOUT_PROGRESSBAR = 20;

    /** The Constant FILE_SEPARATOR. */
    public static final String FILE_SEPARATOR = "/";

    // recently modified tree
    /** The Constant ATTR_UUID. */
    public static final String ATTR_UUID = "uuid";

    /** The Constant ATTR_FOO. */
    public static final String ATTR_FOO = "foo";

    /** The Constant ATTR_DESC. */
    public static final String ATTR_DESC = "description";

    /** The Constant ATTR_RIGHT_IN_ROLE. */
    public static final String ATTR_RIGHT_IN_ROLE = "rightInRole";

    /** The Constant ATTR_MODEL. */
    public static final String ATTR_MODEL = "model";

    /** The Constant ATTR_MODIFIED. */
    public static final String ATTR_MODIFIED = "modified";

    /** The Constant ATTR_STORED. */
    public static final String ATTR_STORED = "stored";

    /** The Constant ATTR_FILE_NAME. */
    public static final String ATTR_FILE_NAME = "fileName";

    /** The Constant ATTR_STATE. */
    public static final String ATTR_STATE = "state";

    /**
     * The Constant ATTR_LOCK_OWNER.
     * <code>value == null<code> when there is no lock
     * <code>"".equals(value)<code> when the lock has been created by user
     * <code>value.length() &gt 0<code> when the lock has been created by somebody else
     */
    public static final String ATTR_LOCK_OWNER = "lockOwner";

    /** The Constant ATTR_LOCK_DESCRIPTION. */
    public static final String ATTR_LOCK_DESCRIPTION = "lockDescription";

    /** The Constant ATTR_TIME_TO_EXP_LOCK. */
    public static final String ATTR_TIME_TO_EXP_LOCK = "timeToExpirationLock";

    /** The Constant ATTR_ALL. */
    public static final String ATTR_ALL = "all";

    /** The Constant ATTR_TIMESTAMP. */
    public static final String ATTR_TIMESTAMP = "timestamp";

    /** The Constant ATTR_ACTION. */
    public static final String ATTR_ACTION = "action";

    /** The Constant ATTR_TABLE_NAME. */
    public static final String ATTR_TABLE_NAME = "tableName";

    /** The Constant ATTR_OBJECT. */
    public static final String ATTR_OBJECT = "object";

    /** The Constant ATTR_IS_MORE_INFO. */
    public static final String ATTR_IS_MORE_INFO = "isMoreInfo";

    /** The Constant ATTR_INTERVAL. */
    public static final String ATTR_INTERVAL = "interval";

    // fedora
    /** The Constant FEDORA_MODEL_PREFIX. */
    public static final String FEDORA_MODEL_PREFIX = "model:";

    /** The Constant FEDORA_UUID_PREFIX. */
    public static final String FEDORA_UUID_PREFIX = "uuid:";

    /** The Constant FEDORA_INFO_PREFIX. */
    public static final String FEDORA_INFO_PREFIX = "info:fedora/";

    /** The Constant MONOGRAPH_UNIT_ICON. */
    public static final String MONOGRAPH_UNIT_ICON = "icons/128/monograph_unit.png";

    /** The Constant VOLUME_ICON. */
    public static final String VOLUME_ICON = "icons/128/volume.png";

    /** The Constant INTERNAL_PART_ICON. */
    public static final String INTERNAL_PART_ICON = "icons/128/internal_part.png";

    /** The Constant PERIODICAL_ITEM_ICON. */
    public static final String PERIODICAL_ITEM_ICON = "icons/128/periodical_item.png";

    /** The Constant JPEG_2000_EXTENSION. */
    public static final String JPEG_2000_EXTENSION = ".jp2";

    /** The Constant PDF_EXTENSION. */
    public static final String PDF_EXTENSION = ".pdf";

    /** The Constant PDF_MIMETYPE. */
    public static final String PDF_MIMETYPE = "application/pdf";

    // foxml
    /** The Constant RELS_EXT_LAST_ELEMENT. */
    public static final String RELS_EXT_LAST_ELEMENT = "</rdf:Description>";

    /** The Constant SESSION_EXPIRED_FLAG. */
    public static final char SESSION_EXPIRED_FLAG = '#';

    /**
     * The Enum SEARCH_FIELD.
     */
    public static enum SEARCH_FIELD {

        /** The SYSNO. */
        SYSNO,
        /** The BAR. */
        BAR,
        /** The TITLE. */
        TITLE
    }

    /**
     * Several actions can be aggregated using this enum.
     */
    public static enum VERB {

        /** The GET. */
        GET,
        /** The PUT. */
        PUT,
        /** The DELETE. */
        DELETE
    }

    /** The Constant SYSNO. */
    public static final String SYSNO = "SYSNO";

    /**
     * The Hotkeys enum (pressed with Ctrl+Alt together).
     */
    public static enum HOT_KEYS_WITH_CTRL_ALT {

        /**
         * The value of nativeEvent-keyCode of button M - used for focus on
         * Recently-modified-Tab.
         */
        CODE_KEY_M(77),

        /**
         * The value of nativeEvent-keyCode of button D - used for delete a
         * digital object.
         */
        CODE_KEY_D(68),

        /**
         * The value of nativeEvent-keyCode of button H - used for focus on
         * Referenced-by-Tab.
         */
        CODE_KEY_H(72),

        /**
         * The value of nativeEvent-keyCode of button U - used for display
         * window for entering the new object's PID.
         */
        CODE_KEY_U(85),

        /**
         * The value of nativeEvent-keyCode of button num5 - used for change
         * focused tabSet.
         */
        CODE_KEY_NUM_5(101),

        /**
         * The value of nativeEvent-keyCode of button Page Up - used for shift
         * left in tabs.
         */
        CODE_KEY_PAGE_UP(33),

        /**
         * The value of nativeEvent-keyCode of button Page Down - used for shift
         * right in tabs.
         */
        CODE_KEY_PAGE_DOWN(34),

        /**
         * The value of nativeEvent-keyCode of button C - used for close focused
         * tabSet.
         */
        CODE_KEY_C(67),

        /**
         * The value of nativeEvent-keyCode of button P - used for display
         * publish-window of focused tabSet.
         */
        CODE_KEY_P(80),

        /**
         * The value of nativeEvent-keyCode of button R - used for refresh
         * focused tabSet.
         */
        CODE_KEY_R(82),

        /**
         * The value of nativeEvent-keyCode of button B - used for display
         * basic-mods-window.
         */
        CODE_KEY_B(66),

        /**
         * The value of nativeEvent-keyCode of button O (not zero) - used for
         * unlock digital object in the editing perspective, for reduce a
         * thumbnails in the create-new-object perspective.
         */
        CODE_KEY_O(79),

        /**
         * The value of nativeEvent-keyCode of button Z - used for lock digital
         * object.
         */
        CODE_KEY_Z(90),

        /**
         * The value of nativeEvent-keyCode of button F - used for download
         * foxml and datastreams.
         */
        CODE_KEY_F(70),

        /**
         * The value of nativeEvent-keyCode of button S - used for save a
         * working copy on the server.
         */
        CODE_KEY_S(83),

        /**
         * The value of nativeEvent-keyCode of button W - used for display
         * persistent url.
         */
        CODE_KEY_W(87),

        /**
         * The value of nativeEvent-keyCode of button I - used for larger a
         * thumbnails.
         */
        CODE_KEY_I(73),

        /**
         * The value of nativeEvent-keyCode of button A - used for display a
         * window to change rights.
         */
        CODE_KEY_A(65);

        /** The code. */
        private final int code;

        /**
         * Instantiates a new hO t_ key s_ wit h_ ctr l_ alt.
         * 
         * @param code
         *        the code
         */
        private HOT_KEYS_WITH_CTRL_ALT(final int code) {
            this.code = code;
        }

        /**
         * Gets the code.
         * 
         * @return the code
         */
        public int getCode() {
            return code;
        }

    }

    /**
     * The value of nativeEvent-keyCode of button Enter - used for confirmation
     * of any choice.
     */
    public static final int CODE_KEY_ENTER = 13;

    /**
     * The value of nativeEvent-keyCode of button Esc - used for close pop-up
     * windows.
     */
    public static final int CODE_KEY_ESC = 27;

    /**
     * The value of nativeEvent-keyCode of button Delete - used for deleting
     * selected objects.
     */
    public static final int CODE_KEY_DELETE = 46;

    /** The Constant TILEGRID_ITEM_WIDTH. */
    public static final int TILEGRID_ITEM_WIDTH = 90;

    /** The Constant TILEGRID_ITEM_HEIGHT. */
    public static final int TILEGRID_ITEM_HEIGHT = 145;

    /** The Constant IMAGE_THUMBNAIL_WIDTH. */
    public static final int IMAGE_THUMBNAIL_WIDTH = 80;

    /** The Constant IMAGE_THUMBNAIL_HEIGHT. */
    public static final int IMAGE_THUMBNAIL_HEIGHT = 110;

    /** The Constant IMAGE_FULL_HEIGHT. */
    public static final int IMAGE_FULL_HEIGHT = 750;

    /** The Constant RANGE. */
    public static final String RANGE = "range";

    /** The Constant HTTP_CACHE_SECONDS. */
    public static final String HTTP_CACHE_SECONDS = "7200";

    /** The Constant PAGE_PREVIEW_HEIGHT_SMALL. */
    public static final int PAGE_PREVIEW_HEIGHT_SMALL = 80;

    /** The Constant PAGE_PREVIEW_HEIGHT_NORMAL. */
    public static final int PAGE_PREVIEW_HEIGHT_NORMAL = 120;

    /** The Constant PAGE_PREVIEW_HEIGHT_LARGE. */
    public static final int PAGE_PREVIEW_HEIGHT_LARGE = 160;

    /** The Constant PAGE_PREVIEW_HEIGHT_XLARGE. */
    public static final int PAGE_PREVIEW_HEIGHT_XLARGE = 200;

    /** The Constant PAGE_PREVIEW_HEIGHT_MAX. */
    public static final int PAGE_PREVIEW_HEIGHT_MAX = 280;

    /**
     * Fedora FOXML related constants.
     */
    public static enum DATASTREAM_ID {

        /** The DC. */
        DC("DC"),
        /** The REL s_ ext. */
        RELS_EXT("RELS-EXT"),
        /** The BIBLI o_ mods. */
        BIBLIO_MODS("BIBLIO_MODS"),
        /** The POLICY. */
        POLICY("POLICY"),
        /** The IM g_ full. */
        IMG_FULL("IMG_FULL"),

        /** The IM g_ thumb. */
        IMG_THUMB("IMG_THUMB"),
        /** The IM g_ preview. */
        IMG_PREVIEW("IMG_PREVIEW"),
        /** The TEI. */
        TEI("TEI"),
        /** The TEX t_ ocr. */
        TEXT_OCR("TEXT_OCR"),
        /** The ALTO. */
        ALTO("ALTO");

        /** The value. */
        private final String value;

        /**
         * Instantiates a new dATASTREA m_ id.
         * 
         * @param value
         *        the value
         */
        private DATASTREAM_ID(String value) {
            this.value = value;
        }

        /**
         * Gets the value.
         * 
         * @return the value
         */
        public String getValue() {
            return value;
        }
    }

    /**
     * The Enum DATASTREAM_CONTROLGROUP.
     */
    public static enum DATASTREAM_CONTROLGROUP {

        /** The E. */
        E,
        /** The M. */
        M,
        /** The R. */
        R,
        /** The X. */
        X
    }

    /**
     * The Enum PAGE_TYPES.
     */
    public static enum PAGE_TYPES {

        /** The AD. */
        AD,
        /** The BC. */
        BC,
        /** The BS. */
        BS,
        /** The BL. */
        BL,
        /** The CO. */
        CO,
        /** The FL. */
        FL,
        /** The FC. */
        FC,
        /** The FS. */
        FS,
        /** The IN. */
        IN,
        /** The LI. */
        LI,
        /** The LM. */
        LM,
        /** The LT. */
        LT,
        /** The NP. */
        NP,
        /** The SP. */
        SP,
        /** The TB. */
        TB,
        /** The TC. */
        TC,
        /** The TP. */
        TP,
        /** The MP. */
        MP;

        /** The MAP. */
        public static Map<String, String> MAP = new HashMap<String, String>();
        static {
            MAP.put(PAGE_TYPES.AD.toString(), "Advertisement");
            MAP.put(PAGE_TYPES.BC.toString(), "BackCover");
            MAP.put(PAGE_TYPES.BS.toString(), "BackEndSheet");
            MAP.put(PAGE_TYPES.BL.toString(), "Blank");
            MAP.put(PAGE_TYPES.CO.toString(), "Cover");
            MAP.put(PAGE_TYPES.FL.toString(), "FlyLeaf");
            MAP.put(PAGE_TYPES.FC.toString(), "FrontCover");
            MAP.put(PAGE_TYPES.FS.toString(), "FrontEndSheet");
            MAP.put(PAGE_TYPES.IN.toString(), "Index");
            MAP.put(PAGE_TYPES.LI.toString(), "ListOfIllustrations");
            MAP.put(PAGE_TYPES.LM.toString(), "ListOfMaps");
            MAP.put(PAGE_TYPES.LT.toString(), "ListOfTables");
            MAP.put(PAGE_TYPES.NP.toString(), "NormalPage");
            MAP.put(PAGE_TYPES.SP.toString(), "Spine");
            MAP.put(PAGE_TYPES.TB.toString(), "Table");
            MAP.put(PAGE_TYPES.TC.toString(), "TableOfContents");
            MAP.put(PAGE_TYPES.TP.toString(), "TitlePage");
            MAP.put(PAGE_TYPES.MP.toString(), "Map");
        }

        /**
         * Gets the enum as string from map.
         * 
         * @param type
         *        the type
         * @return the enum as string from map
         */
        public static String getEnumAsStringFromMap(String type) {
            for (String enumAsString : MAP.keySet()) {
                if (type.equals(MAP.get(enumAsString))) return enumAsString;
            }
            return "";
        }
    }

    /**
     * The Enum PERIODICAL_ITEM_GENRE_TYPES.
     */
    public static enum PERIODICAL_ITEM_GENRE_TYPES {

        /** The AFTERNOON. */
        AFTERNOON, /** The CORRECTED. */
        CORRECTED, /** The EVENING. */
        EVENING, /** The MORNING. */
        MORNING, /** The NORMAL. */
        NORMAL, /** The SEQUENC e_ x. */
        SEQUENCE_X, /** The SPECIAL. */
        SPECIAL, /** The SUPPLEMENT. */
        SUPPLEMENT;

        /** The MAP. */
        public static Map<String, String> MAP = new HashMap<String, String>();
        static {
            MAP.put(PERIODICAL_ITEM_GENRE_TYPES.AFTERNOON.toString(), "Afternoon");
            MAP.put(PERIODICAL_ITEM_GENRE_TYPES.CORRECTED.toString(), "Corrected");
            MAP.put(PERIODICAL_ITEM_GENRE_TYPES.EVENING.toString(), "Evening");
            MAP.put(PERIODICAL_ITEM_GENRE_TYPES.MORNING.toString(), "Morning");
            MAP.put(PERIODICAL_ITEM_GENRE_TYPES.NORMAL.toString(), "Normal");
            MAP.put(PERIODICAL_ITEM_GENRE_TYPES.SEQUENCE_X.toString(), "Sequence_X");
            MAP.put(PERIODICAL_ITEM_GENRE_TYPES.SPECIAL.toString(), "Special");
            MAP.put(PERIODICAL_ITEM_GENRE_TYPES.SUPPLEMENT.toString(), "Supplement");
        }

        /**
         * Gets the enum as string from map.
         * 
         * @param type
         *        the type
         * @return the enum as string from map
         */
        public static String getEnumAsStringFromMap(String type) {
            for (String enumAsString : MAP.keySet()) {
                if (type.equals(MAP.get(enumAsString))) return enumAsString;
            }
            return "";
        }
    }

    /**
     * The Enum INTERNAL_PART_LEVEL_NAMES.
     */
    public static enum INTERNAL_PART_LEVEL_NAMES {

        /** The MOD s_ art. */
        MODS_ART("MODS_ART_XXXX"),
        /** The MOD s_ pict. */
        MODS_PICT("MODS_PICT_XXXX"),
        /** The MOD s_ chapter. */
        MODS_CHAPTER("MODS_CHAPTER_XXXX"),

        /** The MOD s_ picture. */
        MODS_PICTURE("MODS_PICTURE_XXXX");

        /** The MAP. */
        public static Map<String, String> MAP = new HashMap<String, String>();
        static {
            MAP.put(INTERNAL_PART_LEVEL_NAMES.MODS_ART.toString(), "article");
            MAP.put(INTERNAL_PART_LEVEL_NAMES.MODS_PICT.toString(), "picture");
            MAP.put(INTERNAL_PART_LEVEL_NAMES.MODS_CHAPTER.toString(), "picture");
            MAP.put(INTERNAL_PART_LEVEL_NAMES.MODS_PICTURE.toString(), "picture");
        }

        /** The value. */
        private final String value;

        /**
         * Instantiates a new iNTERNA l_ par t_ leve l_ names.
         * 
         * @param value
         *        the value
         */
        private INTERNAL_PART_LEVEL_NAMES(String value) {
            this.value = value;
        }

        /**
         * Gets the value.
         * 
         * @return the value
         */
        public String getValue() {
            return value;
        }
    }

    /**
     * The Enum PERIODICAL_ITEM_LEVEL_NAMES.
     */
    public static enum PERIODICAL_ITEM_LEVEL_NAMES {

        /** The MOD s_ issue. */
        MODS_ISSUE("MODS_ISSUE_0001"),
        /** The MOD s_ suppl. */
        MODS_SUPPL("MODS_SUPPL_XXXX");

        /** The MAP. */
        public static Map<String, String> MAP = new HashMap<String, String>();
        static {
            MAP.put(PERIODICAL_ITEM_LEVEL_NAMES.MODS_ISSUE.toString(), "issue");
            MAP.put(PERIODICAL_ITEM_LEVEL_NAMES.MODS_SUPPL.toString(), "supplement");
        }

        /** The value. */
        private final String value;

        /**
         * Instantiates a new pERIODICA l_ ite m_ leve l_ names.
         * 
         * @param value
         *        the value
         */
        private PERIODICAL_ITEM_LEVEL_NAMES(String value) {
            this.value = value;
        }

        /**
         * Gets the value.
         * 
         * @return the value
         */
        public String getValue() {
            return value;
        }

        /**
         * Parses the string.
         * 
         * @param s
         *        the s
         * @return the model
         */
        public static PERIODICAL_ITEM_LEVEL_NAMES parseString(String s) {
            PERIODICAL_ITEM_LEVEL_NAMES[] values = PERIODICAL_ITEM_LEVEL_NAMES.values();
            for (PERIODICAL_ITEM_LEVEL_NAMES levelName : values) {
                if (levelName.getValue().equalsIgnoreCase(s)) return levelName;
            }
            throw new RuntimeException("Unsupported type: " + s);
        }
    }

    /**
     * The Enum MONOGRAPH_UNIT_LEVEL_NAMES.
     */
    public static enum MONOGRAPH_UNIT_LEVEL_NAMES {

        /** The MOD s_ suppl. */
        MODS_SUPPL("MODS_SUPPL_XXXX");

        /** The MAP. */
        public static Map<String, String> MAP = new HashMap<String, String>();
        static {
            MAP.put(PERIODICAL_ITEM_LEVEL_NAMES.MODS_SUPPL.toString(), "supplement");
        }

        /** The value. */
        private final String value;

        /**
         * Instantiates a new mONOGRAP h_ uni t_ leve l_ names.
         * 
         * @param value
         *        the value
         */
        private MONOGRAPH_UNIT_LEVEL_NAMES(String value) {
            this.value = value;
        }

        /**
         * Gets the value.
         * 
         * @return the value
         */
        public String getValue() {
            return value;
        }
    }

    /**
     * The Enum INTERNAL_PART_ARTICLE_GENRE_TYPES.
     */
    public static enum INTERNAL_PART_ARTICLE_GENRE_TYPES {

        /** The ABSTRACT. */
        ABSTRACT, /** The ADVERTISEMENT. */
        ADVERTISEMENT, /** The BIBLIOGRAPHY. */
        BIBLIOGRAPHY, /** The EDITORSNOTE. */
        EDITORSNOTE, /** The INDEX. */
        INDEX, /** The INTRODUCTION. */
        INTRODUCTION, /** The MAI n_ article. */
        MAIN_ARTICLE, /** The NEWS. */
        NEWS, /** The PREFACE. */
        PREFACE,

        /** The REVIEW. */
        REVIEW,
        /** The TABL e_ o f_ content. */
        TABLE_OF_CONTENT,
        /** The UNSPECIFIED. */
        UNSPECIFIED;

        /** The MAP. */
        public static Map<String, String> MAP = new HashMap<String, String>();
        static {

            MAP.put(INTERNAL_PART_ARTICLE_GENRE_TYPES.ABSTRACT.toString(), "Abstract");
            MAP.put(INTERNAL_PART_ARTICLE_GENRE_TYPES.ADVERTISEMENT.toString(), "Advertisement");
            MAP.put(INTERNAL_PART_ARTICLE_GENRE_TYPES.BIBLIOGRAPHY.toString(), "Bibliography");
            MAP.put(INTERNAL_PART_ARTICLE_GENRE_TYPES.EDITORSNOTE.toString(), "EditorsNote");
            MAP.put(INTERNAL_PART_ARTICLE_GENRE_TYPES.INDEX.toString(), "Index");
            MAP.put(INTERNAL_PART_ARTICLE_GENRE_TYPES.INTRODUCTION.toString(), "Introduction");
            MAP.put(INTERNAL_PART_ARTICLE_GENRE_TYPES.MAIN_ARTICLE.toString(), "Main article");
            MAP.put(INTERNAL_PART_ARTICLE_GENRE_TYPES.NEWS.toString(), "News");
            MAP.put(INTERNAL_PART_ARTICLE_GENRE_TYPES.PREFACE.toString(), "Preface");
            MAP.put(INTERNAL_PART_ARTICLE_GENRE_TYPES.REVIEW.toString(), "Review");
            MAP.put(INTERNAL_PART_ARTICLE_GENRE_TYPES.TABLE_OF_CONTENT.toString(), "Table of content");
            MAP.put(INTERNAL_PART_ARTICLE_GENRE_TYPES.UNSPECIFIED.toString(), "Unspecified");

        }

        /**
         * Gets the enum as string from map.
         * 
         * @param type
         *        the type
         * @return the enum as string from map
         */
        public static String getEnumAsStringFromMap(String type) {
            for (String enumAsString : MAP.keySet()) {
                if (type.equals(MAP.get(enumAsString))) return enumAsString;
            }
            return "";
        }
    }

    /**
     * The Enum INTERNAL_PART_PICTURE_GENRE_TYPES.
     */
    public static enum INTERNAL_PART_PICTURE_GENRE_TYPES {

        /** The TABLE. */
        TABLE, /** The ILLUSTRATION. */
        ILLUSTRATION, /** The CHART. */
        CHART, /** The PHOTOGRAPH. */
        PHOTOGRAPH, /** The GRAPHIC. */
        GRAPHIC, /** The MA p_ type. */
        MAP_TYPE, /** The ADVERTISEMENT. */
        ADVERTISEMENT, /** The COVER. */
        COVER, /** The UNSPECIFIED. */
        UNSPECIFIED;

        /** The MAP. */
        public static Map<String, String> MAP = new HashMap<String, String>();
        static {
            MAP.put(INTERNAL_PART_PICTURE_GENRE_TYPES.ADVERTISEMENT.toString(), "Advertisement");
            MAP.put(INTERNAL_PART_PICTURE_GENRE_TYPES.CHART.toString(), "Chart");
            MAP.put(INTERNAL_PART_PICTURE_GENRE_TYPES.COVER.toString(), "Cover");
            MAP.put(INTERNAL_PART_PICTURE_GENRE_TYPES.GRAPHIC.toString(), "Graphic");
            MAP.put(INTERNAL_PART_PICTURE_GENRE_TYPES.ILLUSTRATION.toString(), "Illustration");
            MAP.put(INTERNAL_PART_PICTURE_GENRE_TYPES.MAP_TYPE.toString(), "Map");
            MAP.put(INTERNAL_PART_PICTURE_GENRE_TYPES.PHOTOGRAPH.toString(), "Photograph");
            MAP.put(INTERNAL_PART_PICTURE_GENRE_TYPES.TABLE.toString(), "Table");
            MAP.put(INTERNAL_PART_PICTURE_GENRE_TYPES.UNSPECIFIED.toString(), "Unspecified");
        }

        /**
         * Gets the enum as string from map.
         * 
         * @param type
         *        the type
         * @return the enum as string from map
         */
        public static String getEnumAsStringFromMap(String type) {
            for (String enumAsString : MAP.keySet()) {
                if (type.equals(MAP.get(enumAsString))) return enumAsString;
            }
            return "";
        }
    }

    /**
     * The Enum INTERNAL_PART_CHAPTER_GENRE_TYPES.
     */
    public static enum INTERNAL_PART_CHAPTER_GENRE_TYPES {

        /** The ABSTRACT. */
        ABSTRACT, /** The ADVERTISEMENT. */
        ADVERTISEMENT, /** The ARTICLE. */
        ARTICLE, /** The BIBLIOGRAPHY. */
        BIBLIOGRAPHY, /** The CHAPTER. */
        CHAPTER, /** The DEDICATION. */
        DEDICATION, /** The EDITORSNOTE. */
        EDITORSNOTE, /** The INDEX. */
        INDEX,

        /** The INTRODUCTION. */
        INTRODUCTION,
        /** The PREFACE. */
        PREFACE,
        /** The REVIEW. */
        REVIEW,
        /** The TABL e_ o f_ content. */
        TABLE_OF_CONTENT,
        /** The UNSPECIFIED. */
        UNSPECIFIED;

        /** The MAP. */
        public static Map<String, String> MAP = new HashMap<String, String>();
        static {

            MAP.put(INTERNAL_PART_CHAPTER_GENRE_TYPES.ABSTRACT.toString(), "Abstract");
            MAP.put(INTERNAL_PART_CHAPTER_GENRE_TYPES.ADVERTISEMENT.toString(), "Advertisement");
            MAP.put(INTERNAL_PART_CHAPTER_GENRE_TYPES.ARTICLE.toString(), "Article");
            MAP.put(INTERNAL_PART_CHAPTER_GENRE_TYPES.BIBLIOGRAPHY.toString(), "Bibliography");
            MAP.put(INTERNAL_PART_CHAPTER_GENRE_TYPES.CHAPTER.toString(), "Chapter");
            MAP.put(INTERNAL_PART_CHAPTER_GENRE_TYPES.DEDICATION.toString(), "Dedication");
            MAP.put(INTERNAL_PART_CHAPTER_GENRE_TYPES.EDITORSNOTE.toString(), "EditorsNote");
            MAP.put(INTERNAL_PART_CHAPTER_GENRE_TYPES.INDEX.toString(), "Index");
            MAP.put(INTERNAL_PART_CHAPTER_GENRE_TYPES.INTRODUCTION.toString(), "Introduction");
            MAP.put(INTERNAL_PART_CHAPTER_GENRE_TYPES.PREFACE.toString(), "Preface");
            MAP.put(INTERNAL_PART_CHAPTER_GENRE_TYPES.REVIEW.toString(), "Review");
            MAP.put(INTERNAL_PART_CHAPTER_GENRE_TYPES.TABLE_OF_CONTENT.toString(), "Table of content");
            MAP.put(INTERNAL_PART_CHAPTER_GENRE_TYPES.UNSPECIFIED.toString(), "Unspecified");
        }

        /**
         * Gets the enum as string from map.
         * 
         * @param type
         *        the type
         * @return the enum as string from map
         */
        public static String getEnumAsStringFromMap(String type) {
            for (String enumAsString : MAP.keySet()) {
                if (type.equals(MAP.get(enumAsString))) return enumAsString;
            }
            return "";
        }
    }

    /** The value of background color of focused tabSet. */
    public static final String BG_COLOR_FOCUSED = "#c2c2c2";

    /** The value of background color of focused tabSet which is locked. */
    public static final String BG_COLOR_FOCUSED_LOCK = "#ffe7a3";

    /** The value of background color of focused tabSet which is locked by user. */
    public static final String BG_COLOR_FOCUSED_LOCK_BY_USER = "#daffce";

    /** The value of background color of "unfocused" tabSet. */
    public static final String BG_COLOR_UNFOCUSED = "white";

    /** If the ingest fails more than this number of times, it will fail. */
    public static final int MAX_NUMBER_OF_INGEST_ATTEMPTS = 3;

    /** The Constant WINDOW_ANIMATION_DELAY_IN_MILLIS. */
    public static final int WINDOW_ANIMATION_DELAY_IN_MILLIS = 300;

    /**
     * delay after ingesting the object, fedora may fall down, if there is too
     * much requests.
     */
    public static final int REST_DELAY = 75;

    /**
     * The Enum CONFLICT.
     */
    public static enum CONFLICT {

        /** When there is no conflict. */
        NO_CONFLICT(0),

        /**
         * When there is a child in the tree structure which exists a reference
         * from the external object to.
         */
        CHILD_EXTERNAL_REFERENCE(1),

        /**
         * When there is a parent which has a same parent as the original
         * object.
         */
        SAME_PARENT_GRANDPARENT(2),

        /**
         * When there is a parent which has a same parent as the original object
         * and has some other children.
         */
        COUSIN(3),

        /**
         * When there is a parent which has a same parent as the original object
         * and some other parents and some other children as well.
         */
        UNCLE_COUSINE(4),

        /** When some of the offsprings or the ancestors has a conflict. */
        INHERITED(Integer.MAX_VALUE);

        /** The conflict code. */
        private final int conflictCode;

        /**
         * Instantiates a new cONFLICT.
         * 
         * @param conflictCode
         *        the conflict code
         */
        private CONFLICT(int conflictCode) {
            this.conflictCode = conflictCode;
        }

        /**
         * Gets the conflict code.
         * 
         * @return the conflict code
         */
        public int getConflictCode() {
            return conflictCode;
        }
    }

    /** The name of the file obtaining the info of ingests. */
    public static final String INGEST_INFO_FILE_NAME = "ingestInfo.xml";

    /** The name of the root element from the ingestInfo file. */
    public static final String NAME_ROOT_INGEST_ELEMENT = "ingestInfo";

    /**
     * The name of the element from the ingestInfo file containing info about
     * individual ingestInfo.
     */
    public static final String NAME_INGEST_ELEMENT = "ingest";

    /** The file path to the edit icon. */
    public static final String PATH_IMG_EDIT = "icons/16/edit.png";

    /** The MISSING constant. */
    public static final String MISSING = "missing";

    /** The suffix after some String which was longer than was allowed. */
    public static final String OVER_MAX_LENGTH_SUFFIX = "...";

    /** The maximum of the length of a label. */
    public static final int MAX_LABEL_LENGTH = 100;

    /**
     * The Enum NAME_OF_TREE.
     */
    public static enum NAME_OF_TREE {

        /** The RECENTL y_ modified. */
        RECENTLY_MODIFIED, /** The INPU t_ queue. */
        INPUT_QUEUE;

    }

    /** An array of the illegal characters in the file names. */
    public static final String[] ILLEGAL_CHARACTERS = {"/", "`", "?", "*", "\\", "<", ">", "|", "\"", ":",
            "%", "\'", "[", "]", "{", "}", "(", ")"};

    /**
     * The Enum SERVER_ACTION_RESULT.
     */
    public static enum SERVER_ACTION_RESULT {

        /** The OK. */
        OK,
        /** The WRON g_ fil e_ name. */
        WRONG_FILE_NAME,
        /** The O k_ pdf. */
        OK_PDF;
    }

    /**
     * The Enum STRUCTURE_TREE_ITEM_ACTION.
     */
    public static enum STRUCTURE_TREE_ITEM_ACTION {

        /** The CHANG e_ position. */
        CHANGE_POSITION,
        /** The CHANG e_ selection. */
        CHANGE_SELECTION,
        /** The DELET e_ record. */
        DELETE_RECORD,
        /** The UPDATE. */
        UPDATE;
    }

    /** The separator for two pages on one page, or for columns one a page. */
    public static final String TWO_PAGES_SEPARATOR = "|";

    /** The Constant SECTION_INPUT_ID. */
    public static final String SECTION_INPUT_ID = "input";

    /** The Constant ONLY_NUMBERS. */
    public static final String ONLY_NUMBERS = "\\d*";

    /** The Constant EXTENT_OF_NUMBERS. */
    public static final String EXTENT_OF_NUMBERS = "\\d*-\\d*";

    /** The Constant MONTH_REGEX. */
    private static final String MONTH_REGEX = "(([0]?[1-9])|([1][1-2])|10)";

    /** The Constant DAY_REGEX. */
    private static final String DAY_REGEX = "(([0-2]?[1-9])|([1-3]0)|31)";

    /**
     * The Enum DATE_RIGHT_FORMATS.
     */
    public static enum DATE_RIGHT_FORMATS {

        /** The DAT e_ rrrr. */
        DATE_RRRR("\\d{1,4}"),

        /** The DAT e_ rrr r_ dash. */
        DATE_RRRR_DASH(DATE_RRRR.getRegex() + "-"),

        /** The DAT e_ rrr r_ rrrr. */
        DATE_RRRR_RRRR(DATE_RRRR_DASH.getRegex() + DATE_RRRR.getRegex()),

        /** The DAT e_ rrr r_ rrr r_ rrr r_ rrrr. */
        DATE_RRRR_RRRR_RRRR_RRRR(DATE_RRRR_RRRR.getRegex() + "," + DATE_RRRR_RRRR.getRegex()),

        /** The DAT e_ mmrrrr. */
        DATE_MMRRRR(MONTH_REGEX + "\\." + DATE_RRRR.getRegex()),

        /** The DAT e_ m m_ mmrrrr. */
        DATE_MM_MMRRRR(MONTH_REGEX + "\\.-" + DATE_MMRRRR.getRegex()),

        /** The DAT e_ ddmmrrrr. */
        DATE_DDMMRRRR(DAY_REGEX + "\\." + DATE_MMRRRR.getRegex()),

        /** The DAT e_ d d_ ddmmrrrr. */
        DATE_DD_DDMMRRRR(DAY_REGEX + "\\.-" + DATE_DDMMRRRR.getRegex());

        /** The regex. */
        private final String regex;

        /**
         * Instantiates a new dAT e_ righ t_ formats.
         * 
         * @param regex
         *        the regex
         */
        private DATE_RIGHT_FORMATS(String regex) {
            this.regex = regex;
        }

        /**
         * Gets the regex.
         * 
         * @return the regex
         */
        public String getRegex() {
            return regex;
        }

    }

    /**
     * The Enum USER_IDENTITY_TYPES.
     */
    public static enum USER_IDENTITY_TYPES {

        /** The OPE n_ id. */
        OPEN_ID, /** The SHIBBOLETH. */
        SHIBBOLETH, /** The LDAP. */
        LDAP;

        public static USER_IDENTITY_TYPES parseString(String s) {
            for (USER_IDENTITY_TYPES type : USER_IDENTITY_TYPES.values()) {
                if (type.toString().equalsIgnoreCase(s)) return type;
            }
            return null;
        }

    }

    /** The Constant MODS_PART_DETAIL_PAGE_NUMBER. */
    public static final String MODS_PART_DETAIL_PAGE_NUMBER = "pageNumber";
}
