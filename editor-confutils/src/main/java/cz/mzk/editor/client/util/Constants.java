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

/**
 * @author Jiri Kremser
 */
public class Constants {

    public static final Type<RevealContentHandler<?>> TYPE_MEDIT_MAIN_CONTENT =
            new Type<RevealContentHandler<?>>();

    public static final Type<RevealContentHandler<?>> TYPE_MEDIT_LEFT_CONTENT =
            new Type<RevealContentHandler<?>>();

    public static final Type<RevealContentHandler<?>> TYPE_ADMIN_MAIN_CONTENT =
            new Type<RevealContentHandler<?>>();

    public static final Type<RevealContentHandler<?>> TYPE_ADMIN_LEFT_CONTENT =
            new Type<RevealContentHandler<?>>();

    public static final String LOGO_HTML =
            "<a href='/meditor'><img class='noFx' src='images/logo_bw.png' width='162' height='50' alt='logo'></a>";

    /** The Constant SERVLET_IMAGES_PREFIX. */
    public static final String SERVLET_IMAGES_PREFIX = "images/";
    // must be the same as in web.xml
    /** The Constant SERVLET_THUMBNAIL_PREFIX. */
    public static final String SERVLET_THUMBNAIL_PREFIX = "thumbnail";

    public static final String SERVLET_SCANS_PREFIX = "scan";

    /** The Constant SERVLET_FULL_PREFIX. */
    public static final String SERVLET_FULL_PREFIX = "full";

    /** The Constant SERVLET_DOWNLOAD_FOXML_PREFIX. */
    public static final String SERVLET_DOWNLOAD_FOXML_PREFIX = "download/foxml";

    /** The Constant SERVLET_DOWNLOAD_DATASTREAMS_PREFIX. */
    public static final String SERVLET_DOWNLOAD_DATASTREAMS_PREFIX = "download/datastreams";

    /** The Constant SERVLET_DOWNLOAD_DATASTREAMS_PREFIX. */
    public static final String SERVLET_GET_PDF_PREFIX = "getPdf/";

    /** The Constant XML_HEADER_WITH_BACKSLASHES **/
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

    public static final String URL_PARAM_SYSNO = "sysno";

    public static final String URL_PARAM_PATH = "path";

    public static final String URL_PARAM_METADATA = "metadata";

    public static final String URL_PARAM_METADATA_FOUND = "found";

    public static final String URL_PARAM_METADATA_NOT_FOUND = "notFound";

    public static final String URL_PARAM_FULL = "full";

    public static final String URL_PARAM_HEIGHT = "height";

    public static final String URL_PARAM_TOP_SPACE = "topSpace";

    public static final String URL_PARAM_BASE = "base";

    /** The Constant URL_PARAM_PDF_PATH. */
    public static final String URL_PARAM_PDF_PATH = "pdfPath";

    /** The Constant URL_PDF_FROM_FEDORA_SUFFIX. */
    public static final String URL_PDF_FROM_FEDORA_PREFIX = "fromFedora/";

    /** The Constant PATH_TO_PDF_VIEWER. */
    public static final String PATH_TO_PDF_VIEWER = "pdfViewer/web/viewer.html";

    public static enum EDITOR_RIGHTS {

        EDIT_USERS(""), SHOW_ALL_HISTORY(""), PUBLISH(""), DELETE(""), CREATE_NEW_OBJECTS(""),
        RUN_LONG_RUNNING_PROCESS("");

        private final String desc;

        private EDITOR_RIGHTS(String desc) {
            this.desc = desc;
        }

        /**
         * @return the desc
         */
        public String getDesc() {
            return desc;
        }

        public static EDITOR_RIGHTS parseString(String s) {
            for (EDITOR_RIGHTS right : EDITOR_RIGHTS.values()) {
                if (right.toString().equalsIgnoreCase(s)) return right;
            }
            return null;
        }

    }

    // db
    /** Path to a file with current DB schema */
    public static final String SCHEMA_PATH = "schema.sql";

    /** Path to directory of backed up schemas */
    public static final String DB_BACKUP_DIR = "backupDB";

    /** A name of the script for running a process via ssh on a remote machine */
    public static final String SCRIPT_FOR_REMOTE_PROCESS = "runRemoteProcess.sh";

    /** Path to a file with current DB schema version number */
    public static final String SCHEMA_VERSION_PATH = "schemaVersion.txt";

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

        CREATE("c"), READ("r"), UPDATE("u"), DELETE("d");

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
         * @return the value
         */
        public String getValue() {
            return value;
        }

        public static CRUD_ACTION_TYPES parseString(String s) {
            for (CRUD_ACTION_TYPES action : CRUD_ACTION_TYPES.values()) {
                if (action.getValue().equalsIgnoreCase(s)) return action;
            }
            throw new RuntimeException("Unsupported CRUD action type: " + s);
        }

    }

    public static enum ACTION_SUBJECTS {
        DIGITAL_OBJECT, LOG_IN_OUT, USER, LONG_PROCESS, LOCK, SAVED_EDITED, CONVERSION, TREE_STRUCTURE;
    }

    /**
     * The Enum REQUESTS_TO_ADMIN_TYPES.
     */
    public static enum REQUESTS_TO_ADMIN_TYPES {

        ADDING_NEW_ACOUNT("adding a new acount");

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
         * @return the value
         */
        public String getValue() {
            return value;
        }

    }

    // tables

    public static enum DEFAULT_SYSTEM_USERS {

        NON_EXISTENT(new Long(1), "non-existent"), TIME(new Long(2), "time");

        private final Long userId;
        private final String userName;

        /**
         * @param userId
         * @param userName
         */
        private DEFAULT_SYSTEM_USERS(Long userId, String userName) {
            this.userId = userId;
            this.userName = userName;
        }

        /**
         * @return the userId
         */
        public Long getUserId() {
            return userId;
        }

        /**
         * @return the userName
         */
        public String getUserName() {
            return userName;
        }

    }

    public static final String TABLE_ACTION = "action";
    public static final String TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT = "crud_do_action_with_top_object";
    public static final String TABLE_CONVERSION = "conversion";
    public static final String TABLE_CRUD_DIGITAL_OBJECT_ACTION = "crud_digital_object_action";
    public static final String TABLE_CRUD_LOCK_ACTION = "crud_lock_action";
    public static final String TABLE_CRUD_REQUEST_TO_ADMIN_ACTION = "crud_request_to_admin_action";
    public static final String TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION = "crud_saved_edited_object_action";
    public static final String TABLE_CRUD_TREE_STRUCTURE_ACTION = "crud_tree_structure_action";
    public static final String TABLE_DESCRIPTION = "description";
    public static final String TABLE_DIGITAL_OBJECT = "digital_object";
    public static final String TABLE_EDITOR_RIGHT = "editor_right";
    public static final String TABLE_EDITOR_USER = "editor_user";
    public static final String TABLE_IMAGE = "image ";
    public static final String TABLE_INPUT_QUEUE = "input_queue";
    public static final String TABLE_INPUT_QUEUE_ITEM = "input_queue_item";
    public static final String TABLE_LDAP_IDENTITY = "ldap_identity";
    public static final String TABLE_LOCK = "lock  ";
    public static final String TABLE_LOG_IN_OUT = "log_in_out";
    public static final String TABLE_LONG_RUNNING_PROCESS = "long_running_process";
    public static final String TABLE_OPEN_ID_IDENTITY = "open_id_identity";
    public static final String TABLE_REQUEST_TO_ADMIN = "request_to_admin";
    public static final String TABLE_RIGHT_IN_ROLE = "right_in_role";
    public static final String TABLE_ROLE = "role  ";
    public static final String TABLE_SAVED_EDITED_OBJECT = "saved_edited_object";
    public static final String TABLE_SHIBBOLETH_IDENTITY = "shibboleth_identity";
    public static final String TABLE_TREE_STRUCTURE = "tree_structure";
    public static final String TABLE_TREE_STRUCTURE_NODE = "tree_structure_node";
    public static final String TABLE_USER_EDIT = "user_edit";
    public static final String TABLE_USERS_RIGHT = "users_right";
    public static final String TABLE_USERS_ROLE = "users_role";
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

    public static final String ATTR_MODEL_ID = "modelId";

    public static final String ATTR_CREATE = "create";

    public static final String ATTR_EXIST = "exist";

    /** The Constant ATTR_IDENTITY. */
    public static final String ATTR_IDENTITY = "identity";

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
     * Several actions can be aggregated using this enum
     */
    public static enum VERB {
        GET, PUT, DELETE
    }

    public static final String SYSNO = "SYSNO";

    /**
     * The Hotkeys enum (pressed with Ctrl+Alt together)
     */
    public static enum HOT_KEYS_WITH_CTRL_ALT {

        /**
         * The value of nativeEvent-keyCode of button M - used for focus on
         * Recently-modified-Tab
         **/
        CODE_KEY_M(77),

        /**
         * The value of nativeEvent-keyCode of button D - used for delete a
         * digital object
         **/
        CODE_KEY_D(68),

        /**
         * The value of nativeEvent-keyCode of button H - used for focus on
         * Referenced-by-Tab
         **/
        CODE_KEY_H(72),

        /**
         * The value of nativeEvent-keyCode of button U - used for display
         * window for entering the new object's PID
         **/
        CODE_KEY_U(85),

        /**
         * The value of nativeEvent-keyCode of button num5 - used for change
         * focused tabSet
         **/
        CODE_KEY_NUM_5(101),

        /**
         * The value of nativeEvent-keyCode of button Page Up - used for shift
         * left in tabs
         **/
        CODE_KEY_PAGE_UP(33),

        /**
         * The value of nativeEvent-keyCode of button Page Down - used for shift
         * right in tabs
         **/
        CODE_KEY_PAGE_DOWN(34),

        /**
         * The value of nativeEvent-keyCode of button C - used for close focused
         * tabSet
         **/
        CODE_KEY_C(67),

        /**
         * The value of nativeEvent-keyCode of button P - used for display
         * publish-window of focused tabSet
         **/
        CODE_KEY_P(80),

        /**
         * The value of nativeEvent-keyCode of button R - used for refresh
         * focused tabSet
         **/
        CODE_KEY_R(82),

        /**
         * The value of nativeEvent-keyCode of button B - used for display
         * basic-mods-window
         */
        CODE_KEY_B(66),

        /**
         * The value of nativeEvent-keyCode of button O (not zero) - used for
         * unlock digital object in the editing perspective, for reduce a
         * thumbnails in the create-new-object perspective
         */
        CODE_KEY_O(79),

        /**
         * The value of nativeEvent-keyCode of button Z - used for lock digital
         * object
         */
        CODE_KEY_Z(90),

        /**
         * The value of nativeEvent-keyCode of button F - used for download
         * foxml and datastreams
         */
        CODE_KEY_F(70),

        /**
         * The value of nativeEvent-keyCode of button S - used for save a
         * working copy on the server
         */
        CODE_KEY_S(83),

        /**
         * The value of nativeEvent-keyCode of button W - used for display
         * persistent url
         */
        CODE_KEY_W(87),

        /**
         * The value of nativeEvent-keyCode of button I - used for larger a
         * thumbnails
         */
        CODE_KEY_I(73),

        /**
         * The value of nativeEvent-keyCode of button A - used for display a
         * window to change rights
         */
        CODE_KEY_A(65);

        private final int code;

        private HOT_KEYS_WITH_CTRL_ALT(final int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

    }

    /**
     * The value of nativeEvent-keyCode of button Enter - used for confirmation
     * of any choice
     **/
    public static final int CODE_KEY_ENTER = 13;

    /**
     * The value of nativeEvent-keyCode of button Esc - used for close pop-up
     * windows
     **/
    public static final int CODE_KEY_ESC = 27;

    /**
     * The value of nativeEvent-keyCode of button Delete - used for deleting
     * selected objects
     */
    public static final int CODE_KEY_DELETE = 46;

    public static final int TILEGRID_ITEM_WIDTH = 90;
    public static final int TILEGRID_ITEM_HEIGHT = 145;
    public static final int IMAGE_THUMBNAIL_WIDTH = 80;
    public static final int IMAGE_THUMBNAIL_HEIGHT = 110;
    public static final int IMAGE_FULL_HEIGHT = 750;
    public static final String RANGE = "range";

    public static final String HTTP_CACHE_SECONDS = "7200";
    public static final int PAGE_PREVIEW_HEIGHT_SMALL = 80;
    public static final int PAGE_PREVIEW_HEIGHT_NORMAL = 120;
    public static final int PAGE_PREVIEW_HEIGHT_LARGE = 160;
    public static final int PAGE_PREVIEW_HEIGHT_XLARGE = 200;
    public static final int PAGE_PREVIEW_HEIGHT_MAX = 280;

    /**
     * Fedora FOXML related constants
     */
    public static enum DATASTREAM_ID {
        DC("DC"), RELS_EXT("RELS-EXT"), BIBLIO_MODS("BIBLIO_MODS"), POLICY("POLICY"), IMG_FULL("IMG_FULL"),
        IMG_THUMB("IMG_THUMB"), IMG_PREVIEW("IMG_PREVIEW"), TEI("TEI"), TEXT_OCR("TEXT_OCR"), ALTO("ALTO");

        private final String value;

        private DATASTREAM_ID(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum DATASTREAM_CONTROLGROUP {
        E, M, R, X
    }

    public static enum PAGE_TYPES {
        AD, BC, BS, BL, CO, FL, FC, FS, IN, LI, LM, LT, NP, SP, TB, TC, TP, MP;

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

        public static String getEnumAsStringFromMap(String type) {
            for (String enumAsString : MAP.keySet()) {
                if (type.equals(MAP.get(enumAsString))) return enumAsString;
            }
            return "";
        }
    }

    public static enum PERIODICAL_ITEM_GENRE_TYPES {

        AFTERNOON, CORRECTED, EVENING, MORNING, NORMAL, SEQUENCE_X, SPECIAL, SUPPLEMENT;

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

        public static String getEnumAsStringFromMap(String type) {
            for (String enumAsString : MAP.keySet()) {
                if (type.equals(MAP.get(enumAsString))) return enumAsString;
            }
            return "";
        }
    }

    public static enum INTERNAL_PART_LEVEL_NAMES {
        MODS_ART("MODS_ART_XXXX"), MODS_PICT("MODS_PICT_XXXX"), MODS_CHAPTER("MODS_CHAPTER_XXXX"),
        MODS_PICTURE("MODS_PICTURE_XXXX");

        public static Map<String, String> MAP = new HashMap<String, String>();
        static {
            MAP.put(INTERNAL_PART_LEVEL_NAMES.MODS_ART.toString(), "article");
            MAP.put(INTERNAL_PART_LEVEL_NAMES.MODS_PICT.toString(), "picture");
            MAP.put(INTERNAL_PART_LEVEL_NAMES.MODS_CHAPTER.toString(), "picture");
            MAP.put(INTERNAL_PART_LEVEL_NAMES.MODS_PICTURE.toString(), "picture");
        }

        private final String value;

        private INTERNAL_PART_LEVEL_NAMES(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum PERIODICAL_ITEM_LEVEL_NAMES {
        MODS_ISSUE("MODS_ISSUE_0001"), MODS_SUPPL("MODS_SUPPL_XXXX");

        public static Map<String, String> MAP = new HashMap<String, String>();
        static {
            MAP.put(PERIODICAL_ITEM_LEVEL_NAMES.MODS_ISSUE.toString(), "issue");
            MAP.put(PERIODICAL_ITEM_LEVEL_NAMES.MODS_SUPPL.toString(), "supplement");
        }

        private final String value;

        private PERIODICAL_ITEM_LEVEL_NAMES(String value) {
            this.value = value;
        }

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

    public static enum MONOGRAPH_UNIT_LEVEL_NAMES {
        MODS_SUPPL("MODS_SUPPL_XXXX");

        public static Map<String, String> MAP = new HashMap<String, String>();
        static {
            MAP.put(PERIODICAL_ITEM_LEVEL_NAMES.MODS_SUPPL.toString(), "supplement");
        }

        private final String value;

        private MONOGRAPH_UNIT_LEVEL_NAMES(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum INTERNAL_PART_ARTICLE_GENRE_TYPES {

        ABSTRACT, ADVERTISEMENT, BIBLIOGRAPHY, EDITORSNOTE, INDEX, INTRODUCTION, MAIN_ARTICLE, NEWS, PREFACE,
        REVIEW, TABLE_OF_CONTENT, UNSPECIFIED;

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

        public static String getEnumAsStringFromMap(String type) {
            for (String enumAsString : MAP.keySet()) {
                if (type.equals(MAP.get(enumAsString))) return enumAsString;
            }
            return "";
        }
    }

    public static enum INTERNAL_PART_PICTURE_GENRE_TYPES {

        TABLE, ILLUSTRATION, CHART, PHOTOGRAPH, GRAPHIC, MAP_TYPE, ADVERTISEMENT, COVER, UNSPECIFIED;

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

        public static String getEnumAsStringFromMap(String type) {
            for (String enumAsString : MAP.keySet()) {
                if (type.equals(MAP.get(enumAsString))) return enumAsString;
            }
            return "";
        }
    }

    public static enum INTERNAL_PART_CHAPTER_GENRE_TYPES {

        ABSTRACT, ADVERTISEMENT, ARTICLE, BIBLIOGRAPHY, CHAPTER, DEDICATION, EDITORSNOTE, INDEX,
        INTRODUCTION, PREFACE, REVIEW, TABLE_OF_CONTENT, UNSPECIFIED;

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

        public static String getEnumAsStringFromMap(String type) {
            for (String enumAsString : MAP.keySet()) {
                if (type.equals(MAP.get(enumAsString))) return enumAsString;
            }
            return "";
        }
    }

    /** The value of background color of focused tabSet */
    public static final String BG_COLOR_FOCUSED = "#c2c2c2";

    /** The value of background color of focused tabSet which is locked */
    public static final String BG_COLOR_FOCUSED_LOCK = "#ffe7a3";

    /** The value of background color of focused tabSet which is locked by user */
    public static final String BG_COLOR_FOCUSED_LOCK_BY_USER = "#daffce";

    /** The value of background color of "unfocused" tabSet */
    public static final String BG_COLOR_UNFOCUSED = "white";

    /** If the ingest fails more than this number of times, it will fail */
    public static final int MAX_NUMBER_OF_INGEST_ATTEMPTS = 3;

    public static final int WINDOW_ANIMATION_DELAY_IN_MILLIS = 300;

    /**
     * delay after ingesting the object, fedora may fall down, if there is too
     * much requests
     */
    public static final int REST_DELAY = 75;

    public static enum CONFLICT {

        /** When there is no conflict */
        NO_CONFLICT(0),

        /**
         * When there is a child in the tree structure which exists a reference
         * from the external object to
         */
        CHILD_EXTERNAL_REFERENCE(1),

        /**
         * When there is a parent which has a same parent as the original object
         */
        SAME_PARENT_GRANDPARENT(2),

        /**
         * When there is a parent which has a same parent as the original object
         * and has some other children
         */
        COUSIN(3),

        /**
         * When there is a parent which has a same parent as the original object
         * and some other parents and some other children as well
         */
        UNCLE_COUSINE(4),

        /** When some of the offsprings or the ancestors has a conflict */
        INHERITED(Integer.MAX_VALUE);

        private final int conflictCode;

        private CONFLICT(int conflictCode) {
            this.conflictCode = conflictCode;
        }

        public int getConflictCode() {
            return conflictCode;
        }
    }

    /** The name of the file obtaining the info of ingests */
    public static final String INGEST_INFO_FILE_NAME = "ingestInfo.xml";

    /** The name of the root element from the ingestInfo file */
    public static final String NAME_ROOT_INGEST_ELEMENT = "ingestInfo";

    /**
     * The name of the element from the ingestInfo file containing info about
     * individual ingestInfo
     */
    public static final String NAME_INGEST_ELEMENT = "ingest";

    /** The file path to the edit icon */
    public static final String PATH_IMG_EDIT = "icons/16/edit.png";

    /** The MISSING constant */
    public static final String MISSING = "missing";

    /** The suffix after some String which was longer than was allowed */
    public static final String OVER_MAX_LENGTH_SUFFIX = "...";

    /** The maximum of the length of a label */
    public static final int MAX_LABEL_LENGTH = 100;

    public static enum NAME_OF_TREE {

        RECENTLY_MODIFIED, INPUT_QUEUE;

    }

    /** An array of the illegal characters in the file names */
    public static final String[] ILLEGAL_CHARACTERS = {"/", "`", "?", "*", "\\", "<", ">", "|", "\"", ":",
            "%", "\'", "[", "]", "{", "}", "(", ")"};

    public static enum SERVER_ACTION_RESULT {
        OK, WRONG_FILE_NAME, OK_PDF;
    }

    public static enum STRUCTURE_TREE_ITEM_ACTION {
        CHANGE_POSITION, CHANGE_SELECTION, DELETE_RECORD, UPDATE;
    }

    /** The separator for two pages on one page, or for columns one a page */
    public static final String TWO_PAGES_SEPARATOR = "|";

    public static final String SECTION_INPUT_ID = "input";

    public static final String ONLY_NUMBERS = "\\d*";

    public static final String EXTENT_OF_NUMBERS = "\\d*-\\d*";

    private static final String MONTH_REGEX = "(([0]?[1-9])|([1][1-2])|10)";

    private static final String DAY_REGEX = "(([0-2]?[1-9])|([1-3]0)|31)";

    public static enum DATE_RIGHT_FORMATS {

        /** */
        DATE_RRRR("\\d{1,4}"),

        /** */
        DATE_RRRR_DASH(DATE_RRRR.getRegex() + "-"),

        /** */
        DATE_RRRR_RRRR(DATE_RRRR_DASH.getRegex() + DATE_RRRR.getRegex()),

        /** */
        DATE_RRRR_RRRR_RRRR_RRRR(DATE_RRRR_RRRR.getRegex() + "," + DATE_RRRR_RRRR.getRegex()),

        /** */
        DATE_MMRRRR(MONTH_REGEX + "\\." + DATE_RRRR.getRegex()),

        /** */
        DATE_MM_MMRRRR(MONTH_REGEX + "\\.-" + DATE_MMRRRR.getRegex()),

        /** */
        DATE_DDMMRRRR(DAY_REGEX + "\\." + DATE_MMRRRR.getRegex()),

        /** */
        DATE_DD_DDMMRRRR(DAY_REGEX + "\\.-" + DATE_DDMMRRRR.getRegex());

        private final String regex;

        private DATE_RIGHT_FORMATS(String regex) {
            this.regex = regex;
        }

        /**
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

    }

    public static final String MODS_PART_DETAIL_PAGE_NUMBER = "pageNumber";
}
