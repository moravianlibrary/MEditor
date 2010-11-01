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
import com.smartgwt.client.widgets.grid.ListGrid;

// TODO: Auto-generated Javadoc
/**
 * The Class SideNavRecentlyTree.
 */
public class SideNavRecentlyTree extends ListGrid {

	/** The id suffix. */
	private final String idSuffix = "";

	/**
	 * Instantiates a new side nav recently tree.
	 */
	public SideNavRecentlyTree() {
		setWidth100();
		setHeight100();
		// setCustomIconProperty("icon");
		setShowSortArrow(SortArrow.CORNER);
		setShowAllRecords(true);
		setAutoFetchData(true);
		setCanHover(true);
		// addCellClickHandler(new CellClickHandler() {
		// @Override
		// public void onCellClick(CellClickEvent event) {
		// SC.say(event.getRecord().getAttribute(Constants.ATTR_UUID));
		//
		// // TODO: dat do presenteru a udelat at se spusti novy place pres place
		// // mngra
		// }
		// });

		// setHoverCustomizer(new HoverCustomizer() {
		// @Override
		// public String hoverHTML(Object value, ListGridRecord record, int rowNum,
		// int colNum) {
		// return record.getAttribute(Constants.ATTR_DESC);
		// }
		// });

		// setLoadDataOnDemand(false);
		setCanSort(false);
	}

}
