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
 * The Class Constants.
 */
public class Constants {

	/** The Constant SERVLET_IMAGES_PREFIX. */
	private static final String SERVLET_IMAGES_PREFIX = "images/";
	// must be the same as in web.xml
	/** The Constant SERVLET_THUMBNAIL_PREFIX. */
	public static final String SERVLET_THUMBNAIL_PREFIX = "thumbnail";

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

	// db
	/** The Constant TABLE_INPUT_QUEUE_NAME. */
	public static final String TABLE_INPUT_QUEUE_NAME = "input_queue_item";

	/** The Constant TABLE_RECENTLY_MODIFIED_NAME. */
	public static final String TABLE_RECENTLY_MODIFIED_NAME = "recently_modified_item";

	/** The Constant TABLE_DESCRIPTION. */
	public static final String TABLE_DESCRIPTION = "description";

	/** The Constant TABLE_EDITOR_USER. */
	public static final String TABLE_EDITOR_USER = "editor_user";

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

	/** The Constant ATTR_ISSN. */
	public static final String ATTR_ISSN = "issn";

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

	// foxml
	/** The Constant RELS_EXT_LAST_ELEMENT. */
	public static final String RELS_EXT_LAST_ELEMENT = "</rdf:Description>";

	public static final char SESSION_EXPIRED_FLAG = '#';

}
