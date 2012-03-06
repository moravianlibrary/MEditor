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

package cz.fi.muni.xkremser.editor.client.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiri Kremser
 */
public class Constants {

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

    // db
    /** Path to a file with current DB schema */
    public static final String SCHEMA_PATH = "schema.sql";

    /** Path to a file with current DB schema version number */
    public static final String SCHEMA_VERSION_PATH = "schemaVersion.txt";

    /** The Constant TABLE_INPUT_QUEUE_ITEM. */
    public static final String TABLE_INPUT_QUEUE_ITEM = "input_queue_item";

    /** The Constant TABLE_INPUT_QUEUE_ITEM_NAME. */
    public static final String TABLE_INPUT_QUEUE_ITEM_NAME = "input_queue_item_name";

    /** The Constant TABLE_IMAGE_NAME. */
    public static final String TABLE_IMAGE_NAME = "image";

    /** The Constant TABLE_RECENTLY_MODIFIED_NAME. */
    public static final String TABLE_RECENTLY_MODIFIED_NAME = "recently_modified_item";

    /** The Constant TABLE_DESCRIPTION. */
    public static final String TABLE_DESCRIPTION = "description";

    /** The Constant TABLE_EDITOR_USER. */
    public static final String TABLE_EDITOR_USER = "editor_user";

    /** The Constant TABLE_LOCK. */
    public static final String TABLE_LOCK = "lock";

    /** The Constant TABLE_STORED_FILES. */
    public static final String TABLE_STORED_FILES = "stored_files";

    /** The Constant SEQUENCE_EDITOR_USER. */
    public static final String SEQUENCE_EDITOR_USER = "seq_user";

    /** The Constant SEQUENCE_OPEN_ID_IDENTITY. */
    public static final String SEQUENCE_OPEN_ID_IDENTITY = "seq_open_id_identity";

    /** The Constant SEQUENCE_TREE_STRUCTURE. */
    public static final String SEQUENCE_TREE_STRUCTURE = "seq_tree_structure";

    /** The Constant SEQUENCE_ROLE. */
    public static final String SEQUENCE_ROLE = "seq_user_in_role";

    /** The Constant TABLE_ROLE. */
    public static final String TABLE_ROLE = "role";

    /** The Constant TABLE_USER_IN_ROLE. */
    public static final String TABLE_USER_IN_ROLE = "user_in_role";

    /** The Constant TABLE_OPEN_ID_IDENTITY. */
    public static final String TABLE_OPEN_ID_IDENTITY = "open_id_identity";

    /** The Constant TABLE_VERSION_NAME. */
    public static final String TABLE_VERSION_NAME = "version";

    /** The Constant TABLE_TREE_STRUCTURE_NODE_NAME. */
    public static final String TABLE_TREE_STRUCTURE_NODE_NAME = "tree_structure_node";

    /** The Constant TABLE_TREE_STRUCTURE_NAME. */
    public static final String TABLE_TREE_STRUCTURE_NAME = "tree_structure";

    // input queue tree
    /** The Constant ATTR_ID. */
    public static final String ATTR_ID = "path"; // path

    /** The Constant ATTR_PARENT. */
    public static final String ATTR_PARENT = "parent";

    /** The Constant ATTR_SCAN_INDEX. */
    public static final String ATTR_SCAN_INDEX = "scanIndex";

    /** The Constant ATTR_NAME. */
    public static final String ATTR_NAME = "atName";

    /** The Constant ATTR_ORDER. */
    public static final String ATTR_ORDER = "order";

    /** The Constant ATTR_SURNAME. */
    public static final String ATTR_SURNAME = "surname";

    public static final String ATTR_TYPE = "type";

    public static final String ATTR_TYPE_ID = Constants.ATTR_TYPE + Constants.ATTR_ID;

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

    /** The Constant ATTR_PICTURE. */
    public static final String ATTR_PICTURE = "picture";

    /** The Constant ATTR_BARCODE. */
    public static final String ATTR_BARCODE = "barcode";

    /** The Constant ATTR_PATH. */
    public static final String ATTR_PATH = "path";

    /** The Constant ATTR_ALTO_PATH. */
    public static final String ATTR_ALTO_PATH = "altoPath";

    /** The Constant ATTR_OCR_PATH. */
    public static final String ATTR_OCR_PATH = "ocrPath";

    /** The Constant ATTR_PAGE_TYPE. */
    public static final String ATTR_PAGE_TYPE = "pageType";

    /** The Constant ATTR_DATE_ISSUED. */
    public static final String ATTR_DATE_ISSUED = "dateIssued";

    /** The Constant ATTR_INGEST_INFO. */
    public static final String ATTR_INGEST_INFO = "ingestInfo";

    /** The Constant ATTR_OWNER. */
    public static final String ATTR_OWNER = "owner";

    /** The Constant ATTR_INPUT_PATH. */
    public static final String ATTR_INPUT_PATH = "inputPath";

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
        IMG_THUMB("IMG_THUMB"), TEI("TEI"), TEXT_OCR("TEXT_OCR"), ALTO("ALTO");

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
        AD, BC, BS, BL, FC, FS, IN, LI, LM, LT, NP, SP, TB, TC, TP;

        public static Map<String, String> MAP = new HashMap<String, String>();
        static {
            MAP.put(PAGE_TYPES.AD.toString(), "Advertisement");
            MAP.put(PAGE_TYPES.BC.toString(), "BackCover");
            MAP.put(PAGE_TYPES.BS.toString(), "BackEndSheet");
            MAP.put(PAGE_TYPES.BL.toString(), "Blank");
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
        }
    }

    /** The value of background color of focused tabSet */
    public static final String BG_COLOR_FOCUSED = "#ededed";

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
        OK, WRONG_FILE_NAME;
    }

    /** The separator for two pages on one page, or for columns one a page */
    public static final String TWO_PAGES_SEPARATOR = "|";
}
