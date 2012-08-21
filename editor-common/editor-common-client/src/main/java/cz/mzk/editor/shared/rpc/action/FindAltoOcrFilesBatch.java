/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2012  Martin Rumanek (martin.rumanek@mzk.cz)
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

package cz.mzk.editor.shared.rpc.action;

import java.util.List;
import java.util.Map;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

//TODO: Auto-generated Javadoc
/**
 * The Class GetClientConfig.
 */
@GenDispatch(isSecure = false)
@SuppressWarnings("unused")
public class FindAltoOcrFilesBatch {

    /** The list of uuid to the jp2-image of the digital object */
    @In(1)
    private List<String> paths;

    /** A map of the names of the found alto files mapped to uuid */
    @Out(1)
    private Map<String, String> altoFileNames;

    /** A map of the names of the found ocr files mapped to uuid */
    @Out(2)
    private Map<String, String> ocrFileNames;
}
