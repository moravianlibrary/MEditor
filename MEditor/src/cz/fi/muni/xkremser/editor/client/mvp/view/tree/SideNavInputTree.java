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

import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.shared.rpc.ScanInputQueueResult;

public class SideNavInputTree extends TreeGrid {

	private final String idSuffix = "";

	// private final ExplorerTreeNode[] showcaseData =
	// ShowcaseData.getData(idSuffix);
	// private final Tree tree;

	public SideNavInputTree() {
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
		setLoadDataOnDemand(true);
		setCanSort(true);
		setAutoFetchData(true);

		//
		// TreeGridField field1 = new TreeGridField();
		// // field1.setCanFilter(true);
		// field1.setName(Constants.ATTR_ISSN);
		// field1.setTitle("ISSN");
		// field1.setWidth(160);
		// TreeGridField field2 = new TreeGridField();
		// field2.setCanFilter(true);
		// field2.setName(Constants.ATTR_NAME);
		// // field2.setAttribute("name", "name");
		// field2.setTitle("Name");
		// setFields(field1, field2);

		setShowOpenIcons(false);
		setShowDropIcons(false);
		setClosedIconSuffix("");
		// setAutoFetchData(true); com.smartgwt.client.data.ResultTree
		// fet

		// tree = new Tree();
		// tree.setModelType(TreeModelType.PARENT);
		// tree.setNameProperty(Constants.ATTR_NAME);
		// tree.setOpenProperty("isOpen");
		// tree.setIdField("nodeID");
		// tree.setParentIdField("parentNodeID");
		// tree.setRootValue("/");
		// tree.setShowRoot(false);
		setShowRoot(false);

		// tree.setData(new SimpleGwtRPCDS());

		// setData(tree);
	}

	public void refresh(ScanInputQueueResult result) {
		if (result.getItems().size() == 0)
			return;
		TreeNode[] nodeList = new TreeNode[result.getItems().size()];
		for (int i = 0; i < result.getItems().size(); i++) {
			String path = result.getItems().get(i).getPath();
			String name = result.getItems().get(i).getName();
			ExplorerTreeNode aux = new ExplorerTreeNode(name, path, result.getId(), "silk/layout_content.png", true, idSuffix);
			aux.setAttribute(Constants.ATTR_ID, path);
			aux.setCanExpand(true);
			aux.setIsFolder(true);
			nodeList[i] = aux;
		}
		// addData(record);
		// Record r =
		// tree.addList(nodeList, result.getPathPrefix());
		// tree.find("/periodika");

	}
	// public ExplorerTreeNode[] getShowcaseData() {
	// return showcaseData;
	// }
}
