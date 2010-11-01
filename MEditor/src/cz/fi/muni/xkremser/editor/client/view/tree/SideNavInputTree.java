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

import com.gwtplatform.dispatch.client.DispatchAsync;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.gwtrpcds.InputTreeGwtRPCDS;
import cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.Refreshable;

// TODO: Auto-generated Javadoc
/**
 * The Class SideNavInputTree.
 */
public class SideNavInputTree extends TreeGrid implements Refreshable {

	/** The id suffix. */
	private final String idSuffix = "";

	/**
	 * Instantiates a new side nav input tree.
	 *
	 * @param dispatcher the dispatcher
	 */
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
		// setLoadingDataMessage("loading ...");
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
		setDataSource(new InputTreeGwtRPCDS(dispatcher));
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.Refreshable#refreshTree()
	 */
	@Override
	public void refreshTree() {
		fetchData();
	}

}
