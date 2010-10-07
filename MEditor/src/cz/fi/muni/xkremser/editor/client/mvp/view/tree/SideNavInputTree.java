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

package cz.fi.muni.xkremser.editor.client.mvp.view.tree;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.gwtrpcds.SimpleGwtRPCDS;

public class SideNavInputTree extends TreeGrid {

	private final String idSuffix = "";

	public SideNavInputTree(DispatchAsync dispatcher) {
		setWidth100();
		setHeight100();
		setCustomIconProperty("icon");
		setAnimateFolderTime(100);
		setAnimateFolders(true);
		setAnimateFolderSpeed(1000);
		setNodeIcon("silk/application_view_list.png");
		setFolderIcon("silk/folder.png");
		setShowOpenIcons(true);
		setShowDropIcons(false);
		setLoadingDataMessage("loading ...");
		setShowSortArrow(SortArrow.CORNER);
		setShowConnectors(true);
		setShowAllRecords(true);
		setLoadDataOnDemand(true);
		setCanSort(true);
		setAutoFetchData(true);
		setShowRoot(false);

		TreeGridField field1 = new TreeGridField();
		field1.setCanFilter(true);
		field1.setName(Constants.ATTR_ISSN);
		field1.setTitle("ISSN");

		TreeGridField field2 = new TreeGridField();
		field2.setCanFilter(true);
		field2.setName(Constants.ATTR_NAME);
		field2.setTitle("Name");

		setFields(field1, field2);
		setDataSource(new SimpleGwtRPCDS(dispatcher));
	}

}
