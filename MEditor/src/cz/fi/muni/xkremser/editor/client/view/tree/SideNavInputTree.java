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
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.gwtrpcds.InputTreeGwtRPCDS;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.Refreshable;

// TODO: Auto-generated Javadoc
/**
 * The Class SideNavInputTree.
 */
public class SideNavInputTree
        extends TreeGrid
        implements Refreshable {

    /** The id suffix. */
    private final String idSuffix = "";

    /**
     * Instantiates a new side nav input tree.
     * 
     * @param dispatcher
     *        the dispatcher
     * @param lang
     */
    public SideNavInputTree(DispatchAsync dispatcher, final PlaceManager placeManager, LangConstants lang) {
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
        // addRowContextClickHandler(new RowContextClickHandler() {
        // @Override
        // public void onRowContextClick(RowContextClickEvent event) {
        // SideNavInputTree.this.getContextMenu().showContextMenu();
        // }
        // });
        // addClickHandler(new ClickHandler() {
        //
        // @Override
        // public void onClick(ClickEvent event) {
        // event.cancel();
        //
        // }
        // });
        // addCellContextClickHandler(new CellContextClickHandler() {
        // @Override
        // public void onCellContextClick(CellContextClickEvent event) {
        // event.cancel();
        // }
        // });
        MenuItem showItem = new MenuItem(lang.show(), "icons/16/structure.png");
        // editItem.setAttribute(ID_NAME, ID_EDIT);
        // editItem.setEnableIfCondition(new MenuItemIfFunction() {
        // @Override
        // public boolean execute(Canvas target, Menu menu, MenuItem item) {
        // return true;
        // }
        // });
        final Menu showMenu = new Menu();
        showMenu.setShowShadow(true);
        showMenu.setShadowDepth(10);
        showMenu.setItems(showItem);
        // setContextMenu(editMenu);

        MenuItem createItem = new MenuItem(lang.create(), "icons/16/create2.png");
        createItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(final MenuItemClickEvent event) {
                String msg = event.getMenu().getEmptyMessage();
                String model = msg.substring(0, msg.indexOf("/"));
                String id = msg.substring(msg.indexOf("/") + 1);

                placeManager.revealRelativePlace(new PlaceRequest(NameTokens.FIND_METADATA)
                        .with(Constants.ATTR_MODEL, model).with(Constants.URL_PARAM_CODE, id));
            }
        });

        final Menu editMenu = new Menu();
        editMenu.setShowShadow(true);
        editMenu.setShadowDepth(10);
        editMenu.setItems(createItem);
        setContextMenu(editMenu);

        addCellContextClickHandler(new CellContextClickHandler() {

            @Override
            public void onCellContextClick(CellContextClickEvent event) {
                // to.setPath(from.getAttributeAsString(ServerConstants.ATTR_ID));
                // to.setName(from.getAttributeAsString(ServerConstants.ATTR_NAME));
                // to.setIssn(from.getAttributeAsString(ServerConstants.ATTR_ISSN));

                ListGridRecord record = event.getRecord();
                String path = record.getAttribute(Constants.ATTR_ID);
                if (path != null && path.length() > 1 && path.substring(1).contains("/")) {
                    String model = path.substring(1, path.substring(1).indexOf("/") + 1);
                    String id = path.substring(path.substring(1).indexOf("/") + 2);
                    if (id.contains("/")) {
                        id = id.substring(0, id.indexOf("/"));
                    }

                    editMenu.setEmptyMessage(model + "/" + id);
                    editMenu.showContextMenu();
                } else {
                    showMenu.showContextMenu();
                }

                // getContextMenu().showContextMenu();
            }
        });

        addCellClickHandler(new CellClickHandler() {

            @Override
            public void onCellClick(CellClickEvent event) {
                // show the menu if the click happened on the first column
                // if (event.getColNum() == 0) {
                // // getContextMenu().showContextMenu();
                // }
            }
        });
        addShowContextMenuHandler(new ShowContextMenuHandler() {

            @Override
            public void onShowContextMenu(ShowContextMenuEvent event) {
                event.cancel();
            }
        });

        TreeGridField field1 = new TreeGridField();
        field1.setCanFilter(true);
        field1.setName(Constants.ATTR_BARCODE);
        field1.setTitle("ID");

        //        TreeGridField field2 = new TreeGridField();
        //        field2.setCanFilter(true);
        //        field2.setName(Constants.ATTR_NAME);
        //        field2.setTitle(lang.name());

        setFields(field1/* , field2 */);
        setDataSource(new InputTreeGwtRPCDS(dispatcher, lang));
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.Refreshable
     * #refreshTree()
     */
    @Override
    public void refreshTree() {
        fetchData();
    }

}
