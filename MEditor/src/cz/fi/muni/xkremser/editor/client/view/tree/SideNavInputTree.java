/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
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

package cz.fi.muni.xkremser.editor.client.view.tree;

import com.gwtplatform.dispatch.client.DispatchAsync;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

import cz.fi.muni.xkremser.editor.client.gwtrpcds.InputTreeGwtRPCDS;
import cz.fi.muni.xkremser.editor.client.util.Constants;
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
	 * @param dispatcher
	 *          the dispatcher
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.Refreshable
	 * #refreshTree()
	 */
	@Override
	public void refreshTree() {
		fetchData();
	}

}
