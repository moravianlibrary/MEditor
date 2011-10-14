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

// TODO: Auto-generated Javadoc
/**
 * The Class ServerConstants.
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

    public static final String URL_PARAM_CODE = "code";

    public static final String URL_PARAM_METADATA = "metadata";

    public static final String URL_PARAM_METADATA_FOUND = "found";

    public static final String URL_PARAM_METADATA_NOT_FOUND = "notFound";

    // db
    /** The Constant TABLE_INPUT_QUEUE_NAME. */
    public static final String TABLE_INPUT_QUEUE_NAME = "input_queue_item";

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

    /** The Constant SEQUENCE_EDITOR_USER. */
    public static final String SEQUENCE_EDITOR_USER = "seq_user";

    /** The Constant SEQUENCE_OPEN_ID_IDENTITY. */
    public static final String SEQUENCE_OPEN_ID_IDENTITY = "seq_open_id_identity";

    /** The Constant SEQUENCE_ROLE. */
    public static final String SEQUENCE_ROLE = "seq_user_in_role";

    /** The Constant TABLE_ROLE. */
    public static final String TABLE_ROLE = "role";

    /** The Constant TABLE_USER_IN_ROLE. */
    public static final String TABLE_USER_IN_ROLE = "user_in_role";

    /** The Constant TABLE_OPEN_ID_IDENTITY. */
    public static final String TABLE_OPEN_ID_IDENTITY = "open_id_identity";

    // input queue tree
    /** The Constant ATTR_ID. */
    public static final String ATTR_ID = "path"; // path

    /** The Constant ATTR_PARENT. */
    public static final String ATTR_PARENT = "parent";

    /** The Constant ATTR_NAME. */
    public static final String ATTR_NAME = "atName";

    /** The Constant ATTR_SURNAME. */
    public static final String ATTR_SURNAME = "surname";

    public static final String ATTR_TYPE = "type";

    public static final String ATTR_TYPE_ID = Constants.ATTR_TYPE + Constants.ATTR_ID;

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

    /** The Constant DIR_MAX_DEPTH. */
    public static final int DIR_MAX_DEPTH = 5;

    /** The Constant CLIPBOARD_MAX_WITHOUT_PROGRESSBAR. */
    public static final int CLIPBOARD_MAX_WITHOUT_PROGRESSBAR = 20;

    /** The Constant FILE_SEPARATOR. */
    public static final String FILE_SEPARATOR = "/";


    /** The Constant ATTR_UUID_TO_DISPLAY. */
    public static final String ATTR_UUID_TO_DISPLAY = "uuidToDisplay";

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

    /**
     * The Constant ATTR_LOCK_OWNER.
     * <code>value == null<code> when there is no lock
     * <code>"".equals(value)<code> when the lock has been created by user
     * <code>value.length() &gt 0<code> when the lock has been created by somebody else
     */
    public static final String ATTR_LOCK_OWNER = "lockOwner";

    /** The Constant ATTR_LOCK_DESCRIPTION. */
    public static final String ATTR_LOCK_DESCRIPTION = "lockDescription";

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
         * The value of nativeEvent-keyCode of button D - used for focus on
         * Referenced-by-Tab
         **/
        CODE_KEY_D(68),

        /**
         * The value of nativeEvent-keyCode of button Esc - used for close
         * pop-up windows
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
         * unlock digital object
         */
        CODE_KEY_O(79),

        /**
         * The value of nativeEvent-keyCode of button Z - used for lock digital
         * object
         */
        CODE_KEY_Z(90);

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
    public static final int TILEGRID_ITEM_HEIGHT = 135;
    public static final int IMAGE_THUMBNAIL_WIDTH = 80;
    public static final int IMAGE_THUMBNAIL_HEIGHT = 110;
    public static final int IMAGE_FULL_WIDTH = 750;
    public static final String RANGE = "range";

}
