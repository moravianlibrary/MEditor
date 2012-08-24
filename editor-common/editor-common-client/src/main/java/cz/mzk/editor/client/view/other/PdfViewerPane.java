/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
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

package cz.mzk.editor.client.view.other;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.widgets.HTMLPane;

import cz.mzk.editor.client.util.Constants;

/**
 * @author Matous Jobanek
 * @version Aug 23, 2012
 */
public class PdfViewerPane
        extends HTMLPane {

    private final String uuid;

    /**
     * 
     */
    public PdfViewerPane(String pathToPdfParam, boolean showEdges, String uuid) {
        super();
        this.uuid = uuid;

        setShowResizeBar(true);
        setShowEdges(showEdges);
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.URL_PARAM_PDF_PATH, pathToPdfParam);
        setContentsURLParams(params);
        setContentsURL(Constants.PATH_TO_PDF_VIEWER);
        setContentsType(ContentsType.PAGE);
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

}
