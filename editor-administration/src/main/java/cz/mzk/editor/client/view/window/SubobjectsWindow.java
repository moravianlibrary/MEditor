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

package cz.mzk.editor.client.view.window;

import java.util.List;

import com.google.web.bindery.event.shared.EventBus;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.rpc.HistoryItemInfo;

/**
 * @author Matous Jobanek
 * @version Nov 8, 2012
 */
public class SubobjectsWindow
        extends UniversalWindow {

    private ListGrid childrenItemsGrid;

    /**
     * @param height
     * @param width
     * @param title
     * @param eventBus
     * @param milisToWait
     */
    public SubobjectsWindow(LangConstants lang, EventBus eventBus, List<HistoryItemInfo> children) {
        super(600, 600, lang.subobjects(), eventBus, 50);

        if (childrenItemsGrid == null) {
            childrenItemsGrid = new ListGrid();
            childrenItemsGrid.setWidth(200);

            ListGridField modelField = new ListGridField(Constants.ATTR_MODEL);
            modelField.setTitle("Model");

            ListGridField nameField = new ListGridField(Constants.ATTR_NAME);
            nameField.setTitle(lang.title());

            ListGridField pidField = new ListGridField(Constants.ATTR_UUID);
            pidField.setTitle(lang.title());
            pidField.setWidth("50%");

            childrenItemsGrid.setFields(modelField, nameField, pidField);
        }
        childrenItemsGrid.setData(getChildRecords(children));
        childrenItemsGrid.setHeight("100%");
        childrenItemsGrid.setWidth("100%");
        childrenItemsGrid.setEdgeSize(4);
        childrenItemsGrid.setEdgeOpacity(60);
        childrenItemsGrid.setShowEdges(true);
        childrenItemsGrid.setCanHover(true);
        childrenItemsGrid.setHoverWidth(300);
        childrenItemsGrid.setMargin(15);

        addItem(childrenItemsGrid);

        centerInPage();
        show();
        focus();
    }

    private ListGridRecord[] getChildRecords(List<HistoryItemInfo> children) {
        ListGridRecord[] childRecords = new ListGridRecord[children.size()];
        int index = 0;

        for (HistoryItemInfo child : children) {
            ListGridRecord childRecord = new ListGridRecord();
            childRecord.setAttribute(Constants.ATTR_MODEL, child.getModel());
            childRecord.setAttribute(Constants.ATTR_NAME, child.getName());
            childRecord.setAttribute(Constants.ATTR_UUID, child.getUuid());

            childRecords[index++] = childRecord;
        }
        return childRecords;
    }

}
