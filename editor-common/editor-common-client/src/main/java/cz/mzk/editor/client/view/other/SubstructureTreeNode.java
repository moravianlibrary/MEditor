/*
 * Metadata Editor
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

package cz.mzk.editor.client.view.other;

import com.smartgwt.client.widgets.tree.TreeNode;

import cz.mzk.editor.client.util.Constants;

/**
 * @author Jiri Kremser
 * @version 7.3.2012
 */
public class SubstructureTreeNode
        extends TreeNode {

    public static final String ROOT_ID = "-1";

    public static final String ROOT_OBJECT_ID = "0";

    public SubstructureTreeNode(String id,
                                String parent,
                                String name,
                                String pictureOrUuid,
                                String modelId,
                                String type,
                                String dateOrIntPartName,
                                String noteOrIntSubtitle,
                                String partNumberOrAlto,
                                String aditionalInfoOrOcr,
                                boolean isOpen,
                                boolean exist) {
        setAttribute(Constants.ATTR_ID, id);
        setAttribute(Constants.ATTR_PARENT, parent);
        setAttribute(Constants.ATTR_NAME, name);
        setAttribute(Constants.ATTR_PICTURE_OR_UUID, pictureOrUuid);
        setAttribute(Constants.ATTR_MODEL_ID, modelId);
        setAttribute(Constants.ATTR_TYPE, type);
        setAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME, dateOrIntPartName);
        setAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE, noteOrIntSubtitle);
        setAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO, partNumberOrAlto);
        setAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR, aditionalInfoOrOcr);
        setAttribute("isOpen", isOpen);
        setAttribute(Constants.ATTR_EXIST, exist);
        setAttribute(Constants.ATTR_CREATE, !exist);
    }
}
