/*
 * Smart GWT (GWT for SmartClient)
 * Copyright 2008 and beyond, Isomorphic Software, Inc.
 *
 * Smart GWT is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.  Smart GWT is also
 * available under typical commercial license terms - see
 * smartclient.com/license.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */

package cz.fi.muni.xkremser.editor.client.view.tree;

import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;

import cz.fi.muni.xkremser.editor.client.Constants;

public class SideNavRecentlyTree extends ListGrid {

	private final String idSuffix = "";

	public SideNavRecentlyTree() {
		setWidth100();
		setHeight100();
		// setCustomIconProperty("icon");
		setShowSortArrow(SortArrow.CORNER);
		setShowAllRecords(true);
		setAutoFetchData(true);
		setCanHover(true);
		addCellClickHandler(new CellClickHandler() {
			@Override
			public void onCellClick(CellClickEvent event) {
				SC.say(event.getRecord().getAttribute(Constants.ATTR_UUID));
				// TODO: dat do presenteru a udelat at se spusti novy place pres place
				// mngra
			}
		});

		setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return record.getAttribute(Constants.ATTR_DESC);
			}
		});

		// setLoadDataOnDemand(false);
		setCanSort(false);

		// GridField field1 = new TreeGridField();
		// field1.setCanFilter(true);
		// field1.setName("name");
		// field1.setTitle("Recently modified");
		// TreeGridField field2 = new TreeGridField();
		// field2.setCanFilter(true);
		// field2.setName("name2");
		// field2.setTitle("<b>Recently modified</b>");
		// setFields(field1, field2);

		// Tree tree = new Tree();
		// tree.setModelType(TreeModelType.PARENT);
		// tree.setNameProperty("name");
		// tree.setOpenProperty("isOpen");
		// tree.setIdField("nodeID");
		// tree.setParentIdField("parentNodeID");
		// tree.setRootValue("root" + idSuffix);

		// tree.setData(showcaseData);
		// fetchData(criteria, callback, requestProperties)
	}

	// @Override
	// public void fetchData(Criteria criteria, DSCallback callback, DSRequest
	// requestProperties) {
	// new DSCallback() {
	// @Override
	// public void execute(DSResponse response, Object rawData, DSRequest request)
	// {
	// // TODO Auto-generated method stub
	//
	// }
	// };
	// // TODO Auto-generated method stub
	// super.fetchData(criteria, callback, requestProperties);
	// }

}
