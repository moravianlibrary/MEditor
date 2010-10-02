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

package cz.fi.muni.xkremser.editor.client.tree;

import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class SideNavRecentlyTree extends TreeGrid {

	private final String idSuffix = "";

	private final ExplorerTreeNode[] showcaseData = ShowcaseData.getData(idSuffix);

	public SideNavRecentlyTree() {
		setWidth100();
		setHeight100();
		setCustomIconProperty("icon");
		setAnimateFolderTime(100);
		setAnimateFolders(true);
		setAnimateFolderSpeed(1000);
		setNodeIcon("silk/application_view_list.png");
		setShowSortArrow(SortArrow.CORNER);
		setShowConnectors(true);
		setShowAllRecords(true);
		setLoadDataOnDemand(false);
		setCanSort(false);

		TreeGridField field1 = new TreeGridField();
		field1.setCanFilter(true);
		field1.setName("name");
		field1.setTitle("Recently modified");
		// TreeGridField field2 = new TreeGridField();
		// field2.setCanFilter(true);
		// field2.setName("name2");
		// field2.setTitle("<b>Recently modified</b>");
		// setFields(field1, field2);

		Tree tree = new Tree();
		tree.setModelType(TreeModelType.PARENT);
		tree.setNameProperty("name");
		tree.setOpenProperty("isOpen");
		tree.setIdField("nodeID");
		tree.setParentIdField("parentNodeID");
		tree.setRootValue("root" + idSuffix);

		tree.setData(showcaseData);

		setData(tree);
	}

	public ExplorerTreeNode[] getShowcaseData() {
		return showcaseData;
	}
}
