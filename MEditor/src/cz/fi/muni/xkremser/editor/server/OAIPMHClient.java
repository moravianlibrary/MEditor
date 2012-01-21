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

package cz.fi.muni.xkremser.editor.server;

import java.io.File;
import java.util.ArrayList;

import cz.fi.muni.xkremser.editor.shared.rpc.MetadataBundle;

/**
 * @author Jiri Kremser
 * @version 12.11.2011
 */
public interface OAIPMHClient {

    String MARC_METADATA_PREFIX = "marc21";

    String OAI_METADATA_PREFIX = "oai_dc";

    String MARC_TO_MODS_XSLT = File.separator + "xml" + File.separator + "MARC21slim2MODS3.xsl";

    String MARC_TO_DC_XSLT = File.separator + "xml" + File.separator + "MARC21slim2OAIDC.xsl";

    ArrayList<MetadataBundle> search(String url);
}
