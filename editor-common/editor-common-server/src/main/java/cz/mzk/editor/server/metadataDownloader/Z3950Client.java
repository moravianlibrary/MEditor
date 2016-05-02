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

package cz.mzk.editor.server.metadataDownloader;

import java.io.File;
import java.util.ArrayList;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.rpc.MetadataBundle;

/**
 * @author Jiri Kremser
 */
public interface Z3950Client {

    /** The Constant MZK_PROFILE_INDEX. */
    int MZK_PROFILE_INDEX = 0;

    /** The Constant MZK_PROFILE_ID. */
    String MZK_PROFILE_ID = "mzk";

    /** The Constant MUNI_PROFILE_INDEX. */
    int MUNI_PROFILE_INDEX = 1;

    /** The Constant MUNI_PROFILE_ID. */
    String MUNI_PROFILE_ID = "muni";

    /** The Constant NKP_SKC_PROFILE_INDEX. */
    int NKP_SKC_PROFILE_INDEX = 2;

    /** The Constant NKP_SKC_PROFILE_ID. */
    String NKP_SKC_PROFILE_ID = "nkp_skc";

    /** The Constant NKP_NKC_PROFILE_INDEX. */
    int NKP_NKC_PROFILE_INDEX = 3;

    /** The Constant NKP_NKC_PROFILE_ID. */
    String NKP_NKC_PROFILE_ID = "nkp_nkc";

    /** The Constant RECORD_SYNTAX. */
    String DC_RECORD_SYNTAX = "xml";

    String MARC_RECORD_SYNTAX = "usmarc";

    String MARC_TO_MODS_XSLT = File.separator + "xml" + File.separator + "MARC21slim2MODS3.xsl";

    ArrayList<MetadataBundle> search(Constants.SEARCH_FIELD field, String what);

    int getProfileIndex();

}
