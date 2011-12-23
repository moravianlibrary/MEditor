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

package cz.fi.muni.xkremser.editor.client.view.other;

import java.util.List;

import com.google.gwt.user.client.Timer;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.gwtrpcds.InputTreeGwtRPCDS;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.window.EditorSC;
import cz.fi.muni.xkremser.editor.client.view.window.IngestInfoWindow;
import cz.fi.muni.xkremser.editor.client.view.window.ModalWindow;

import cz.fi.muni.xkremser.editor.shared.rpc.IngestInfo;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetIngestInfoAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetIngestInfoResult;

// TODO: Auto-generated Javadoc
/**
 * The Class SideNavInputTree.
 */
public class SideNavInputTree
        extends TreeGrid {

    private final MenuItem createItem;
    MenuItem ingestInfo = new MenuItem();
    private static final String HTML_TICK_CODE = "<img src=\"images/silk/tick.png\">";
    private final LangConstants lang;

    /**
     * Instantiates a new side nav input tree.
     * 
     * @param dispatcher
     *        the dispatcher
     * @param lang
     */
    public SideNavInputTree(final DispatchAsync dispatcher, LangConstants lang) {
        this.lang = lang;
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
        setShowSortArrow(SortArrow.CORNER);
        setShowConnectors(true);
        setShowAllRecords(true);
        setLoadDataOnDemand(true);
        setCanSort(true);
        setAutoFetchData(true);
        setShowRoot(false);

        MenuItem showItem = new MenuItem(lang.show(), "icons/16/structure.png");
        final Menu showMenu = new Menu();
        showMenu.setShowShadow(true);
        showMenu.setShadowDepth(10);
        showMenu.setItems(showItem);

        createItem = new MenuItem(lang.create(), "icons/16/structure_into.png");
        ingestInfo = new MenuItem(lang.ingestInfo(), "icons/16/export1.png");

        final Menu editMenu = new Menu();
        editMenu.setShowShadow(true);
        editMenu.setShadowDepth(10);
        setContextMenu(editMenu);

        addCellContextClickHandler(new CellContextClickHandler() {

            @Override
            public void onCellContextClick(CellContextClickEvent event) {

                ListGridRecord record = event.getRecord();
                final String path = record.getAttribute(Constants.ATTR_ID);
                if (path != null && path.length() > 1 && path.substring(1).contains("/")) {
                    //                    String model = path.substring(1, path.substring(1).indexOf("/") + 1);
                    String id = path.substring(path.substring(1).indexOf("/") + 2);
                    if (id.contains("/")) {
                        id = id.substring(0, id.indexOf("/"));
                    }
                    if (record.getAttributeAsBoolean(Constants.ATTR_INGEST_INFO)) {
                        ingestInfo.addClickHandler(new ClickHandler() {

                            @Override
                            public void onClick(MenuItemClickEvent event) {
                                getIngestInfo(path, dispatcher);
                            }

                        });
                        editMenu.setItems(createItem, ingestInfo);
                    } else {
                        editMenu.setItems(createItem);
                    }

                    editMenu.setEmptyMessage(path.substring(1, path.length()));
                    editMenu.showContextMenu();
                } else {
                    showMenu.showContextMenu();
                }

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
        field1.setCellFormatter(new CellFormatter() {

            @Override
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                boolean ingestInfo = record.getAttributeAsBoolean(Constants.ATTR_INGEST_INFO);
                if (ingestInfo) {
                    return record.getAttribute(Constants.ATTR_BARCODE) + HTML_TICK_CODE;

                } else {
                    return record.getAttribute(Constants.ATTR_BARCODE);
                }
            }
        });

        setFields(field1);
        setDataSource(new InputTreeGwtRPCDS(dispatcher, lang));
    }

    public void refreshTree() {
        fetchData();
    }

    public MenuItem getCreateMenuItem() {
        return createItem;
    }

    private void getIngestInfo(final String path, final DispatchAsync dispatcher) {
        final ModalWindow mw = new ModalWindow(SideNavInputTree.this);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);
        Timer timer = new Timer() {

            @Override
            public void run() {
                GetIngestInfoAction ingestInfoAction = new GetIngestInfoAction(path);
                DispatchCallback<GetIngestInfoResult> ingestInfoCallback =
                        new DispatchCallback<GetIngestInfoResult>() {

                            @Override
                            public void callback(GetIngestInfoResult result) {
                                mw.hide();
                                List<IngestInfo> ingestInfoList = result.getIngestInfo();
                                if (ingestInfoList.isEmpty()) {
                                    EditorSC.operationFailed(lang, lang.noIngestFile());
                                } else {
                                    IngestInfoWindow.setInstanceOf(ingestInfoList, lang);
                                }
                            }

                            @Override
                            public void callbackError(Throwable t) {
                                mw.hide();
                                super.callbackError(t);
                            }
                        };
                dispatcher.execute(ingestInfoAction, ingestInfoCallback);
            }
        };
        timer.schedule(25);
    }
}
