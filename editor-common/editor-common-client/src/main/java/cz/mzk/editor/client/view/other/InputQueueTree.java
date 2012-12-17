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

package cz.mzk.editor.client.view.other;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Timer;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.gwtrpcds.InputTreeGwtRPCDS;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.NAME_OF_TREE;
import cz.mzk.editor.client.util.Constants.SERVER_ACTION_RESULT;
import cz.mzk.editor.client.view.window.EditorSC;
import cz.mzk.editor.client.view.window.IngestInfoWindow;
import cz.mzk.editor.client.view.window.ModalWindow;
import cz.mzk.editor.shared.event.RefreshTreeEvent;
import cz.mzk.editor.shared.rpc.IngestInfo;
import cz.mzk.editor.shared.rpc.ServerActionResult;
import cz.mzk.editor.shared.rpc.action.GetIngestInfoAction;
import cz.mzk.editor.shared.rpc.action.GetIngestInfoResult;
import cz.mzk.editor.shared.rpc.action.QuartzConvertImagesAction;
import cz.mzk.editor.shared.rpc.action.QuartzConvertImagesResult;
import cz.mzk.editor.shared.rpc.action.ScanInputQueueAction;
import cz.mzk.editor.shared.rpc.action.ScanInputQueueResult;

// TODO: Auto-generated Javadoc
/**
 * The Class InputQueueTree.
 */
public class InputQueueTree
        extends TreeGrid {

    private final MenuItem createItem;
    private static final String HTML_TICK_CODE = "<img src=\"images/silk/tick.png\">";
    private final LangConstants lang;
    private static volatile boolean ready = true;
    // this is not a mistake (the lock should be shared with another presenter)
    private static final Object LOCK = Constants.class;
    private static InputQueueTree inputQueueTree = null;

    /** The roll over canvas */
    private HLayout rollOverCanvas;

    public static void setInputTreeToSection(final DispatchAsync dispatcher,
                                             final LangConstants lang,
                                             final EventBus eventBus,
                                             SectionStack sectionStack,
                                             final PlaceManager placeManager,
                                             boolean force) {

        SectionStackSection section1 = new SectionStackSection();
        section1.setTitle(lang.inputQueue());
        String isInputSection = sectionStack.getSection(0).getAttribute(Constants.SECTION_INPUT_ID);
        boolean notContains = isInputSection == null || !"yes".equals(isInputSection);
        if (notContains || force) {
            if (!notContains) {
                sectionStack.collapseSection(0);
                sectionStack.removeSection(0);

            }
            if (inputQueueTree == null) {
                inputQueueTree = new InputQueueTree(dispatcher, lang, eventBus);
                inputQueueTree.getCreateMenuItem()
                        .addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

                            @Override
                            public void onClick(final MenuItemClickEvent event) {
                                String msg = event.getMenu().getEmptyMessage();
                                String model = msg.substring(0, msg.indexOf("/"));
                                String path = msg.substring(msg.indexOf("/") + 1);
                                String id = path;
                                if (path.contains("/")) {
                                    id = path.substring(0, path.indexOf("/"));
                                }

                                placeManager.revealRelativePlace(new PlaceRequest(NameTokens.FIND_METADATA)
                                        .with(Constants.ATTR_MODEL, model)
                                        .with(Constants.URL_PARAM_SYSNO, id)
                                        .with(Constants.URL_PARAM_PATH, path));
                            }
                        });
            }

            section1.setItems(inputQueueTree);
            section1.setControls(getRefreshButton(lang, eventBus, dispatcher));
            section1.setResizeable(true);
            section1.setExpanded(true);
            sectionStack.addSection(section1, 0);
            section1.setAttribute(Constants.SECTION_INPUT_ID, "yes");
        }

    }

    @Override
    protected Canvas getRollOverCanvas(Integer rowNum, Integer colNum) {
        final ListGridRecord rollOverRecord = this.getRecord(rowNum);
        if (rollOverCanvas == null) {
            rollOverCanvas = new HLayout();
            rollOverCanvas.setSnapTo("TR");
            rollOverCanvas.setWidth(50);
            rollOverCanvas.setHeight(22);
        }

        final String conDate = rollOverRecord.getAttributeAsString(Constants.ATTR_CONVERSION_DATE);
        final Boolean isCon = rollOverRecord.getAttributeAsBoolean(Constants.ATTR_IS_CONVERTED);
        if (conDate != null) {
            ImgButton lockImg = new ImgButton();
            lockImg.setShowDown(false);
            lockImg.setShowRollOver(false);
            lockImg.setLayoutAlign(Alignment.CENTER);

            if (rollOverRecord.getAttributeAsBoolean(Constants.ATTR_IS_CONVERTED)) {
                lockImg.setSrc("icons/16/stillCon.png");
            } else {
                lockImg.setSrc("icons/16/notCon.png");
            }

            lockImg.setPrompt(lang.lastConversion() + ": " + conDate + "<br>"
                    + (isCon ? lang.stillCon() : lang.notCon()));
            lockImg.setHoverWidth(400);
            lockImg.setHeight(16);
            lockImg.setWidth(16);
            rollOverCanvas.addChild(lockImg);
        } else {
            if (rollOverCanvas.getChildren().length > 0)
                rollOverCanvas.removeChild(rollOverCanvas.getChildren()[0]);
        }
        return rollOverCanvas;
    }

    private static ImgButton getRefreshButton(final LangConstants lang,
                                              EventBus eventBus,
                                              final DispatchAsync dispatcher) {
        final ImgButton refreshButton;

        refreshButton = new ImgButton();
        refreshButton.setSrc("[SKIN]headerIcons/refresh.png");
        refreshButton.setSize(16);
        refreshButton.setShowRollOver(true);
        refreshButton.setCanHover(true);
        refreshButton.setShowDownIcon(false);
        refreshButton.setShowDown(false);
        refreshButton.setHoverStyle("interactImageHover");
        refreshButton.setHoverOpacity(75);
        refreshButton.addHoverHandler(new HoverHandler() {

            @Override
            public void onHover(HoverEvent event) {
                refreshButton.setPrompt(lang.inputQueueRescan());
            }
        });

        refreshButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                onRefresh(dispatcher, lang);

            }
        });
        return refreshButton;
    }

    private static void onRefresh(DispatchAsync dispatcher, final LangConstants lang) {
        if (ready) {
            synchronized (LOCK) {
                if (ready) { // double-lock idiom
                    ready = false;
                    final ModalWindow mw = new ModalWindow(inputQueueTree);
                    mw.setLoadingIcon("loadingAnimation.gif");
                    mw.show(true);
                    dispatcher.execute(new ScanInputQueueAction(null, true),
                                       new DispatchCallback<ScanInputQueueResult>() {

                                           @Override
                                           public void callback(ScanInputQueueResult result) {
                                               ServerActionResult serverActionResult =
                                                       result.getServerActionResult();
                                               if (serverActionResult.getServerActionResult() == SERVER_ACTION_RESULT.OK) {
                                                   mw.hide();
                                                   refreshTree();
                                                   ready = true;
                                               } else if (serverActionResult.getServerActionResult() == SERVER_ACTION_RESULT.WRONG_FILE_NAME) {
                                                   mw.hide();
                                                   SC.warn(lang.wrongDirName()
                                                           + serverActionResult.getMessage());
                                               }
                                           }

                                           @Override
                                           public void callbackError(final Throwable t) {
                                               mw.hide();
                                               super.callbackError(t);
                                               ready = true;
                                           }
                                       });
                }
            }
        }
    }

    /**
     * Instantiates a new side nav input tree.
     * 
     * @param dispatcher
     *        the dispatcher
     * @param lang
     */
    private InputQueueTree(final DispatchAsync dispatcher, final LangConstants lang, final EventBus eventBus) {
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
        setSelectionType(SelectionStyle.SINGLE);
        setShowRollOverCanvas(true);

        MenuItem showItem = new MenuItem(lang.show(), "icons/16/structure.png");
        final Menu showMenu = new Menu();
        showMenu.setShowShadow(true);
        showMenu.setShadowDepth(10);
        showMenu.setItems(showItem);

        createItem = new MenuItem(lang.create(), "icons/16/structure_into.png");

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
                    //String model = path.substring(1, path.substring(1).indexOf("/") + 1);
                    String id = path.substring(path.substring(1).indexOf("/") + 2);
                    if (id.contains("/")) {
                        id = id.substring(0, id.indexOf("/"));
                    }

                    MenuItem quartz = new MenuItem(lang.addToScheduler(), "icons/16/clock.png");
                    quartz.addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(MenuItemClickEvent event) {

                            String msg = event.getMenu().getEmptyMessage(); //duplicate code
                            String model = msg.substring(0, msg.indexOf("/"));
                            String path = msg.substring(msg.indexOf("/") + 1);

                            QuartzConvertImagesAction action = new QuartzConvertImagesAction(model, path);

                            DispatchCallback<QuartzConvertImagesResult> quartzConvertCallback =
                                    new DispatchCallback<QuartzConvertImagesResult>() {

                                        @Override
                                        public void callback(QuartzConvertImagesResult result) {
                                            if (result.getNumberOfImages() != null) {
                                                SC.say(result.getNumberOfImages().toString()
                                                        + " images will be converted");
                                            } else {
                                                SC.say("No images found");
                                            }

                                        }
                                    };

                            dispatcher.execute(action, quartzConvertCallback);
                        }
                    });

                    if (record.getAttributeAsBoolean(Constants.ATTR_INGEST_INFO)) {
                        MenuItem ingestInfo = new MenuItem(lang.ingestInfo(), "icons/16/export1.png");
                        ingestInfo.addClickHandler(new ClickHandler() {

                            @Override
                            public void onClick(MenuItemClickEvent event) {
                                getIngestInfo(path, dispatcher, eventBus);
                            }

                        });
                        editMenu.setItems(createItem, ingestInfo, quartz);
                    } else {
                        editMenu.setItems(createItem, quartz);
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

        eventBus.addHandler(RefreshTreeEvent.getType(), new RefreshTreeEvent.RefreshTreeHandler() {

            @Override
            public void onRefreshTree(RefreshTreeEvent event) {
                if (event.getTree() == NAME_OF_TREE.INPUT_QUEUE) refreshTree();
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
        ListGridField nameField = new ListGridField();
        nameField.setCanFilter(true);
        nameField.setName(Constants.ATTR_NAME);
        nameField.setTitle(lang.name());

        setFields(field1, nameField);
        setDataSource(new InputTreeGwtRPCDS(dispatcher, lang));
    }

    private static void refreshTree() {
        final List<String> openedNodes = new ArrayList<String>();
        TreeNode[] allNodes = inputQueueTree.getData().getAllNodes();
        for (int i = 0; i < allNodes.length; i++) {
            if (inputQueueTree.getData().isOpen(allNodes[i])) {
                openedNodes.add(allNodes[i].getAttributeAsString("path"));
            }
        }
        final ListGridRecord selectedRecord = inputQueueTree.getSelectedRecord();
        inputQueueTree.fetchData(null, new DSCallback() {

            @Override
            public void execute(DSResponse response, Object rawData, DSRequest request) {
                inputQueueTree.openSubfolders(openedNodes, null, selectedRecord);
            }
        });
    }

    private void openFolder(final List<String> openedNodes,
                            final TreeNode node,
                            final ListGridRecord selectedRecord) {

        getData().openFolder(node);
        getDataSource().fetchData(new Criteria(Constants.ATTR_PARENT, node.getAttributeAsString("path")),
                                  new DSCallback() {

                                      @Override
                                      public void execute(DSResponse response,
                                                          Object rawData,
                                                          DSRequest request) {
                                          if (openedNodes.size() > 0) {
                                              openSubfolders(openedNodes, node, selectedRecord);
                                          }
                                      }
                                  });
    }

    private void openSubfolders(final List<String> openedNodes,
                                final TreeNode node,
                                final ListGridRecord selectedRecord) {
        TreeNode[] allNewNodes = (node != null) ? getData().getAllNodes(node) : getData().getAllNodes();
        for (int i = 0; i < allNewNodes.length; i++) {
            String newNodeAttribute = allNewNodes[i].getAttributeAsString("path");
            if (selectedRecord != null
                    && selectedRecord.getAttributeAsString("path").equals(newNodeAttribute)) {
                selectRecord(allNewNodes[i]);
            }
            if ((node == null || !node.getAttributeAsString("path").equals(newNodeAttribute))
                    && openedNodes.contains(newNodeAttribute)) {
                openedNodes.remove(newNodeAttribute);
                openFolder(openedNodes, allNewNodes[i], selectedRecord);
            }
        }
    }

    public MenuItem getCreateMenuItem() {
        return createItem;
    }

    private void getIngestInfo(final String path, final DispatchAsync dispatcher, final EventBus eventBus) {
        final ModalWindow mw = new ModalWindow(InputQueueTree.this);
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
                                    IngestInfoWindow.setInstanceOf(ingestInfoList, lang, eventBus);
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
