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

package cz.mzk.editor.client.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.events.RecordClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordClickHandler;
import com.smartgwt.client.widgets.tile.events.SelectionChangedEvent;
import com.smartgwt.client.widgets.tile.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;

import cz.mzk.editor.client.CreateObjectException;
import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.client.config.EditorClientConfiguration;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.mods.DateTypeClient;
import cz.mzk.editor.client.mods.ModsCollectionClient;
import cz.mzk.editor.client.mods.ModsTypeClient;
import cz.mzk.editor.client.other.LabelAndModelConverter;
import cz.mzk.editor.client.uihandlers.CreateStructureUiHandlers;
import cz.mzk.editor.client.util.ClientCreateUtils;
import cz.mzk.editor.client.util.ClientUtils;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.PERIODICAL_ITEM_GENRE_TYPES;
import cz.mzk.editor.client.util.Constants.PERIODICAL_ITEM_LEVEL_NAMES;
import cz.mzk.editor.client.util.Constants.STRUCTURE_TREE_ITEM_ACTION;
import cz.mzk.editor.client.view.CreateStructureView;
import cz.mzk.editor.client.view.other.PdfViewerPane;
import cz.mzk.editor.client.view.other.ScanRecord;
import cz.mzk.editor.client.view.other.SubstructureTreeNode;
import cz.mzk.editor.client.view.window.ChooseDetailWindow;
import cz.mzk.editor.client.view.window.EditorDateItem;
import cz.mzk.editor.client.view.window.EditorSC;
import cz.mzk.editor.client.view.window.ModalWindow;
import cz.mzk.editor.client.view.window.OcrWindow;
import cz.mzk.editor.client.view.window.StoreTreeStructureWindow;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.domain.DigitalObjectModel.TopLevelObjectModel;
import cz.mzk.editor.shared.domain.NamedGraphModel;
import cz.mzk.editor.shared.event.ChangeFocusedTabSetEvent;
import cz.mzk.editor.shared.event.ChangeMenuWidthEvent;
import cz.mzk.editor.shared.event.ChangeStructureTreeItemEvent;
import cz.mzk.editor.shared.event.CreateStructureEvent;
import cz.mzk.editor.shared.event.CreateStructureEvent.CreateStructureHandler;
import cz.mzk.editor.shared.event.KeyPressedEvent;
import cz.mzk.editor.shared.event.LoadStructureEvent;
import cz.mzk.editor.shared.event.RefreshTreeEvent;
import cz.mzk.editor.shared.event.SaveStructureEvent;
import cz.mzk.editor.shared.event.SetEnabledHotKeysEvent;
import cz.mzk.editor.shared.rpc.DublinCore;
import cz.mzk.editor.shared.rpc.ImageItem;
import cz.mzk.editor.shared.rpc.MetadataBundle;
import cz.mzk.editor.shared.rpc.NewDigitalObject;
import cz.mzk.editor.shared.rpc.ServerActionResult;
import cz.mzk.editor.shared.rpc.TreeStructureBundle;
import cz.mzk.editor.shared.rpc.TreeStructureBundle.TreeStructureNode;
import cz.mzk.editor.shared.rpc.TreeStructureInfo;
import cz.mzk.editor.shared.rpc.action.ConvertToJPEG2000Action;
import cz.mzk.editor.shared.rpc.action.ConvertToJPEG2000Result;
import cz.mzk.editor.shared.rpc.action.GetOcrFromPdfAction;
import cz.mzk.editor.shared.rpc.action.GetOcrFromPdfResult;
import cz.mzk.editor.shared.rpc.action.InitializeConversionAction;
import cz.mzk.editor.shared.rpc.action.InitializeConversionResult;
import cz.mzk.editor.shared.rpc.action.InsertNewDigitalObjectAction;
import cz.mzk.editor.shared.rpc.action.InsertNewDigitalObjectResult;
import cz.mzk.editor.shared.rpc.action.ScanFolderAction;
import cz.mzk.editor.shared.rpc.action.ScanFolderResult;

/**
 * @author Jiri Kremser
 */
public class CreateStructurePresenter
        extends Presenter<CreateStructurePresenter.MyView, CreateStructurePresenter.MyProxy>
        implements CreateStructureUiHandlers, CreateStructureHandler {

    private LangConstants lang;

    private static final Class<CreateStructurePresenter> LOCK = CreateStructurePresenter.class;

    @Inject
    public void setLang(LangConstants lang) {
        this.lang = lang;
    }

    /**
     * The Interface MyView.
     */
    public interface MyView
            extends View, HasUiHandlers<CreateStructureUiHandlers> {

        Record[] fromClipboard();

        void toClipboard(final Record[] data);

        PopupPanel getPopupPanel();

        void onAddImages(String model, Record[] items, String hostname, boolean resize, boolean isPdf);

        void escShortCut();

        TileGrid getTileGrid();

        void addUndoRedo(Record[] data, boolean isUndoList, boolean isRedoOperation);

        void resizeThumbnails(boolean larger);

        void showDetail(int height, boolean isTop, int topSpace);

        ScanRecord deepCopyScanRecord(Record originalRecord);

        void updateRecordsInTileGrid(Record[] records, boolean updateTree);

        TabSet getPagesTabSet();

        void setUndoRedoButtonsDisabled(boolean disabled);

        boolean isChosenSelectedPagesTab();

        ToolStripButton getEditMetadataButton(ToolStripButton zoomOut);

        ToolStripButton getCreateButton(boolean isPdf);

        PdfViewerPane getPdfViewerPane();
    }

    /**
     * The Interface MyProxy.
     */
    @ProxyCodeSplit
    @NameToken(NameTokens.CREATE)
    public interface MyProxy
            extends ProxyPlace<CreateStructurePresenter> {

    }

    /** The done. */
    private int alreadyDone = 0;

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /** The left presenter. */
    private final CreateObjectMenuPresenter leftPresenter;

    /** The sysno. */
    private String sysno;

    private String inputPath;

    /** The model. */
    private String model;

    /** The base. */
    private String base;

    private MetadataBundle bundle;

    /** The place manager. */
    private final PlaceManager placeManager;

    private final EditorClientConfiguration config;

    private List<Record> markedRecords;

    private ButtonItem createAtOnceButton = new ButtonItem();

    private TextItem firstNumber;

    private EditorDateItem baseDate;

    private Record[] pages;

    private CheckboxItem afterDivisionRenumber;

    private CheckboxItem addOcr = null;

    /**
     * Instantiates a new create presenter.
     * 
     * @param eventBus
     *        the event bus
     * @param view
     *        the view
     * @param proxy
     *        the proxy
     * @param leftPresenter
     *        the left presenter
     * @param dispatcher
     *        the dispatcher
     * @param placeManager
     *        the place manager
     */
    @Inject
    public CreateStructurePresenter(final EventBus eventBus,
                                    final MyView view,
                                    final MyProxy proxy,
                                    final CreateObjectMenuPresenter leftPresenter,
                                    final DispatchAsync dispatcher,
                                    final PlaceManager placeManager,
                                    final EditorClientConfiguration config) {
        super(eventBus, view, proxy);
        this.leftPresenter = leftPresenter;
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        this.config = config;
        getView().setUiHandlers(this);
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
     */
    @Override
    protected void onBind() {
        super.onBind();
        addRegisteredHandler(KeyPressedEvent.getType(), new KeyPressedEvent.KeyPressedHandler() {

            @Override
            public void onKeyPressed(KeyPressedEvent event) {
                if (event.getCode() == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_I.getCode()) {
                    getView().resizeThumbnails(true);
                } else if (event.getCode() == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_O.getCode()) {
                    getView().resizeThumbnails(false);
                }
            }
        });

        addRegisteredHandler(SaveStructureEvent.getType(), new SaveStructureEvent.SaveStructureHandler() {

            @Override
            public void onSaveStructure(SaveStructureEvent event) {

                if (getView().getTileGrid() != null) {
                    NewDigitalObject object = null;

                    try {
                        object =
                                ClientCreateUtils.createTheStructure(null, leftPresenter.getView()
                                        .getSubelementsGrid().getTree(), true, false);
                    } catch (CreateObjectException e) {
                        SC.warn(e.getMessage());
                        e.printStackTrace();
                    }

                    Record[] tilegridData =
                            !getView().isChosenSelectedPagesTab() ? getView().getTileGrid().getData() : pages;
                    boolean emptyTree = object == null || object.getModel() == null;
                    boolean emptyPages = tilegridData == null || tilegridData.length == 0;
                    if (emptyTree && emptyPages) {
                        SC.warn(lang.nothingToSave());
                    } else {
                        String name =
                                leftPresenter.getView().getSubelementsGrid().getTree()
                                        .findById(SubstructureTreeNode.ROOT_OBJECT_ID)
                                        .getAttributeAsString(Constants.ATTR_NAME);
                        TreeStructureBundle bundle = new TreeStructureBundle();
                        bundle.setInfo(new TreeStructureInfo(-1,
                                                             null,
                                                             null,
                                                             leftPresenter.getBarcode(),
                                                             name,
                                                             null,
                                                             inputPath,
                                                             model));
                        bundle.setNodes(new ArrayList<TreeStructureBundle.TreeStructureNode>());
                        if (object == null)
                            object =
                                    new NewDigitalObject(leftPresenter.getView().getSubelementsGrid()
                                            .getTree().findById(SubstructureTreeNode.ROOT_ID)
                                            .getAttribute(Constants.ATTR_NAME));
                        else
                            object.setName(ClientCreateUtils.trimLabel(object.getName(),
                                                                       Constants.MAX_LABEL_LENGTH));

                        for (TreeNode node : leftPresenter.getView().getSubelementsGrid().getTree()
                                .getAllNodes()) {
                            node.setAttribute(Constants.ATTR_PARENT,
                                              leftPresenter.getView().getSubelementsGrid().getTree()
                                                      .getParent(node).getAttribute(Constants.ATTR_ID));
                        }

                        List<TreeStructureNode> nodes =
                                ClientCreateUtils.toNodes(leftPresenter.getView().getSubelementsGrid()
                                        .getTree().getAllNodes());

                        if (nodes != null) {
                            bundle.getNodes().addAll(nodes);
                        } else {
                            EditorSC.operationFailed(lang, "Please contact an administrator");
                            return;
                        }

                        if (!emptyPages) {
                            bundle.getNodes().addAll(ClientCreateUtils.toNodes(tilegridData));
                        }
                        StoreTreeStructureWindow.setInstanceOf(bundle,
                                                               emptyTree ? null : ClientCreateUtils
                                                                       .toStringTree(object),
                                                               emptyPages ? null : ClientCreateUtils
                                                                       .recordsToString(tilegridData),
                                                               lang,
                                                               dispatcher,
                                                               getEventBus());
                    }
                } else {
                    SC.warn(lang.nothingToSave());
                }
            }
        });

        addRegisteredHandler(LoadStructureEvent.getType(), new LoadStructureEvent.LoadStructureHandler() {

            @Override
            public void onLoadStructure(LoadStructureEvent event) {
                load(event.getTree(), event.getPages(), event.getLastId());
            }
        });

    };

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.mvp.client.Presenter#prepareFromRequest(com.gwtplatform
     * .mvp.client.proxy.PlaceRequest)
     */
    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        sysno = request.getParameter(Constants.URL_PARAM_SYSNO, null);
        leftPresenter.setBarcode(sysno);
        inputPath = request.getParameter(Constants.URL_PARAM_PATH, null);
        model = request.getParameter(Constants.ATTR_MODEL, null);
        base = request.getParameter(Constants.URL_PARAM_BASE, null);

    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onUnbind()
     */
    @Override
    protected void onUnbind() {
        super.onUnbind();
        // Add unbind functionality here for more complex presenters.
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
     */
    @Override
    protected void onReset() {
        super.onReset();
        processImages();
        RevealContentEvent.fire(this, Constants.TYPE_MEDIT_LEFT_CONTENT, leftPresenter);

        leftPresenter.getView().setInputTree(dispatcher, placeManager);
        leftPresenter.getView().getSectionStack().collapseSection(0);
        leftPresenter.getView().getSectionStack().expandSection(1);
        leftPresenter.getView().getSectionStack().expandSection(2);

        if (leftPresenter.getView().getSubelementsGrid().getRecordList().getLength() > 1) {
            SC.ask(lang.notEmptyTree(), new BooleanCallback() {

                @Override
                public void execute(Boolean value) {
                    if (value != null && value) {
                        initLeftPresenterTree();
                    }
                }
            });
        } else {
            initLeftPresenterTree();
        }
        leftPresenter.setDefaultDateIssued(getDateIssued());

        ChangeMenuWidthEvent.fire(getEventBus(), "340");
    }

    private String getDateIssued() {

        if (bundle == null || bundle.getMods() == null) return "";
        if (bundle.getMods().getMods() == null || bundle.getMods().getMods().size() == 0) return "";

        ModsTypeClient mods = bundle.getMods().getMods().get(0);
        if (mods == null || mods.getOriginInfo() == null || mods.getOriginInfo().size() == 0) return "";
        if (mods.getOriginInfo().get(0) == null || mods.getOriginInfo().get(0).getDateIssued() == null)
            return "";

        List<DateTypeClient> dateIssued = mods.getOriginInfo().get(0).getDateIssued();
        if (dateIssued.size() > 0 && dateIssued.get(0) != null && dateIssued.get(0).getValue() != null) {
            return dateIssued.get(0).getValue();
        } else {
            return "";
        }

    }

    private void initLeftPresenterTree() {
        leftPresenter.getView().init();
        String name = null;
        DublinCore dc = bundle != null ? bundle.getDc() : null;
        if (dc != null && dc.getTitle() != null && dc.getTitle().size() != 0 && dc.getTitle().get(0) != null
                && !"".equals(dc.getTitle().get(0).trim())) {
            name = dc.getTitle().get(0).trim();
        } else {
            name = sysno;
        }
        leftPresenter.setRootModel(DigitalObjectModel.parseString(model));
        leftPresenter.getView().getSubelementsGrid().disable();
        leftPresenter.getView().getSubelementsGrid().selectAllRecords();
        leftPresenter.getView().getSubelementsGrid().removeSelectedData();
        leftPresenter.getView().getSubelementsGrid().enable();
        leftPresenter.getView().getSubelementsGrid().redraw();
        leftPresenter.getView().addSubstructure(SubstructureTreeNode.ROOT_OBJECT_ID,
                                                SubstructureTreeNode.ROOT_ID,
                                                name,
                                                null,
                                                model,
                                                "",
                                                "",
                                                "",
                                                "",
                                                "",
                                                true,
                                                false);
        leftPresenter.getView().getSubelementsGrid().selectRecord(0);
        setSectionCreateLayout();
        leftPresenter.getView().getSubelementsGrid().addCellClickHandler(new CellClickHandler() {

            @Override
            public void onCellClick(CellClickEvent event) {
                if (getView().isChosenSelectedPagesTab()) setSelectedTreePages();
            }
        });
    }

    /**
      * 
      */

    private void processImages() {
        String title = null;
        if (bundle != null && bundle.getDc() != null && bundle.getDc().getTitle() != null
                && bundle.getDc().getTitle().size() > 0) {
            title = bundle.getDc().getTitle().get(0);
        }

        final ScanFolderAction action = new ScanFolderAction(model, inputPath, title);
        final DispatchCallback<ScanFolderResult> callback = new DispatchCallback<ScanFolderResult>() {

            private volatile int done = 0;
            private int total = 0;
            private volatile boolean isDone = false;

            @Override
            public void callback(final ScanFolderResult result) {
                getEventBus().fireEvent(new RefreshTreeEvent(Constants.NAME_OF_TREE.INPUT_QUEUE));

                ServerActionResult serverActionResult = result.getServerActionResult();
                if (serverActionResult.getServerActionResult() == Constants.SERVER_ACTION_RESULT.OK) {
                    if (result != null && result.getToAdd() != null && !result.getToAdd().isEmpty()) {
                        initializeConversion(result);
                    } else if (result != null && result.getItems() != null && !result.getItems().isEmpty()) {
                        doTheRest(result.getItems());
                    } else {
                        EditorSC.operationFailed(lang, "");
                    }

                } else if (serverActionResult.getServerActionResult() == Constants.SERVER_ACTION_RESULT.WRONG_FILE_NAME) {
                    SC.ask(lang.wrongFileName() + serverActionResult.getMessage(), new BooleanCallback() {

                        @Override
                        public void execute(Boolean value) {
                            if (value != null && value && result != null && result.getToAdd() != null
                                    && !result.getToAdd().isEmpty()) {
                                initializeConversion(result);
                            }
                        }
                    });
                } else if (serverActionResult.getServerActionResult() == Constants.SERVER_ACTION_RESULT.OK_PDF) {
                    handlePdf(result);
                }
            }

            private void initializeConversion(final ScanFolderResult result) {
                final DispatchCallback<InitializeConversionResult> callback =
                        new DispatchCallback<InitializeConversionResult>() {

                            @Override
                            public void callback(InitializeConversionResult initResult) {
                                if (initResult != null && initResult.isSuccess()) {
                                    convert(result);
                                } else {
                                    SC.ask("Someone else is now running the conversion task. Please wait a second. Do you want to try it again?",
                                           new BooleanCallback() {

                                               @Override
                                               public void execute(Boolean value) {
                                                   if (value != null && value) {
                                                       initializeConversion(result);
                                                   }
                                               }
                                           });
                                }
                            }

                            @Override
                            public void callbackError(Throwable t) {
                                SC.say("Someone else is now running the conversion task.");
                            }
                        };
                dispatcher.execute(new InitializeConversionAction(true), callback);
            }

            private void endConversion() {
                final DispatchCallback<InitializeConversionResult> callback =
                        new DispatchCallback<InitializeConversionResult>() {

                            @Override
                            public void callback(InitializeConversionResult result) {
                                if (result != null && !result.isSuccess()) {
                                    SC.warn("Some images were not converted.");
                                }
                            }

                            @Override
                            public void callbackError(Throwable t) {
                                SC.say("Someone else is now running the conversion task.");
                            }
                        };
                dispatcher.execute(new InitializeConversionAction(false), callback);
            }

            private void convert(ScanFolderResult result) {
                final List<ImageItem> itemList = result == null ? null : result.getItems();
                final List<ImageItem> toAdd = result == null ? null : result.getToAdd();
                if (toAdd != null && !toAdd.isEmpty()) {
                    SetEnabledHotKeysEvent.fire(CreateStructurePresenter.this, false);
                    int lastItem = toAdd.size();
                    boolean progressbar = lastItem > 5;
                    final Progressbar hBar1 = progressbar ? new Progressbar() : null;
                    if (progressbar) {
                        done = 0;
                        total = lastItem;
                        hBar1.setHeight(24);
                        hBar1.setVertical(false);
                        hBar1.setPercentDone(0);
                        getView().getPopupPanel().setAutoHideEnabled(false);
                        getView().getPopupPanel().setWidget(hBar1);
                        getView().getPopupPanel().setVisible(true);
                        getView().getPopupPanel().center();
                        Timer timer = new Timer() {

                            @Override
                            public void run() {
                                for (ImageItem item : toAdd) {
                                    convertItem(item, hBar1, itemList);
                                }
                            }
                        };
                        timer.schedule(40);
                    } else {
                        for (ImageItem item : toAdd) {
                            convertItem(item, hBar1, itemList);
                        }
                    }

                } else {
                    doTheRest(itemList);
                }
            }

            @Override
            public void callbackError(final Throwable t) {
                if (t.getMessage() != null && t.getMessage().length() > 0
                        && t.getMessage().charAt(0) == Constants.SESSION_EXPIRED_FLAG) {
                    SC.confirm(lang.sessionExpired(), new BooleanCallback() {

                        @Override
                        public void execute(Boolean value) {
                            if (value != null && value) {
                                ClientUtils.redirect(t.getMessage().substring(1));
                            }
                        }
                    });
                } else {
                    SC.ask(t.getMessage() + " " + lang.mesTryAgain(), new BooleanCallback() {

                        @Override
                        public void execute(Boolean value) {
                            if (value != null && value) {
                                processImages();
                            }
                        }
                    });
                }
                getView().getPopupPanel().setAutoHideEnabled(true);
                getView().getPopupPanel().setVisible(false);
                getView().getPopupPanel().hide();
            }

            private void doTheRest(List<ImageItem> itemList) {
                ScanRecord[] items = null;
                if (itemList != null && itemList.size() > 0) {
                    items = new ScanRecord[itemList.size()];
                    for (int i = 0, total = itemList.size(); i < total; i++) {
                        items[i] =
                                new ScanRecord(String.valueOf(i + 1),
                                               model,
                                               itemList.get(i).getIdentifier(),
                                               Constants.PAGE_TYPES.NP.toString());
                    }

                    if (config.getHostname() == null || "".equals(config.getHostname().trim())) {
                        EditorSC.compulsoryConfigFieldWarn(EditorClientConfiguration.Constants.HOSTNAME,
                                                           EditorSC.ConfigFieldType.STRING,
                                                           lang);
                    }
                    getView().onAddImages(DigitalObjectModel.PAGE.getValue(),
                                          items,
                                          config.getHostname(),
                                          false,
                                          false);

                }

                if (!"icons/16/structure.png".equals(leftPresenter.getView().getSubelementsGrid()
                        .getFolderIcon())) {
                    leftPresenter.getView().getSubelementsGrid().setFolderIcon("icons/16/structure.png");
                    leftPresenter.getView().getSubelementsGrid().redraw();
                }
                leftPresenter.getView().getSectionStack().getSection(1).setTitle(lang.createSubStructure());
                getView().getPopupPanel().setAutoHideEnabled(true);
                getView().getPopupPanel().setWidget(null);
                getView().getPopupPanel().setVisible(false);
                getView().getPopupPanel().hide();
                SetEnabledHotKeysEvent.fire(CreateStructurePresenter.this, true);

                getView().getPagesTabSet().addTabSelectedHandler(new TabSelectedHandler() {

                    @Override
                    public void onTabSelected(TabSelectedEvent event) {
                        if (event.getTab().getAttributeAsString(Constants.ATTR_TAB_ID)
                                .equals(Constants.SELECTED_PAGES_TAB)) {

                            pages = getView().getTileGrid().getData();
                            setSelectedTreePages();
                            getView().setUndoRedoButtonsDisabled(true);
                            createAtOnceButton.disable();
                            ChangeFocusedTabSetEvent.fire(CreateStructurePresenter.this, null, false);

                        } else {
                            getView().getTileGrid().setData(pages);
                            getView().setUndoRedoButtonsDisabled(false);
                            createAtOnceButton.enable();
                            ChangeFocusedTabSetEvent.fire(CreateStructurePresenter.this, null, true);
                        }
                    }
                });
            }

            private void convertItem(ImageItem item, final Progressbar hBar1, final List<ImageItem> itemList) {
                ConvertToJPEG2000Action action = new ConvertToJPEG2000Action(item);
                final DispatchCallback<ConvertToJPEG2000Result> callback =
                        new DispatchCallback<ConvertToJPEG2000Result>() {

                            @Override
                            public void callback(ConvertToJPEG2000Result result) {
                                done++;
                                if (hBar1 != null) {
                                    hBar1.setPercentDone(((100 * (done + 1)) / total));
                                }
                                if (done >= total && !isDone) {
                                    synchronized (LOCK) {
                                        if (done >= total && !isDone) {
                                            endConversion();
                                            doTheRest(itemList);
                                            isDone = true;
                                        }
                                    }
                                }
                            }

                            @Override
                            public void callbackError(Throwable t) {
                                // unable to convert images to JPEG 2000 install the libraries bitch!
                                if (!isDone) {
                                    synchronized (LOCK) {
                                        if (!isDone) {
                                            endConversion();
                                            doTheRest(itemList);
                                            isDone = true;
                                        }
                                    }
                                }
                            }
                        };
                dispatcher.execute(action, callback);
            }
        };
        Image loader = new Image("images/loadingAnimation3.gif");
        getView().getPopupPanel().setWidget(loader);
        getView().getPopupPanel().setVisible(true);
        getView().getPopupPanel().center();
        dispatcher.execute(action, callback);
    }

    private void setSelectedTreePages() {
        TreeGrid subelementsGrid = leftPresenter.getView().getSubelementsGrid();
        ListGridRecord selectedRecord = subelementsGrid.getSelectedRecord();

        if (selectedRecord != null) {
            TreeNode selNode =
                    subelementsGrid.getTree()
                            .findById(selectedRecord.getAttributeAsString(Constants.ATTR_ID));

            TreeNode[] treePages;
            if (DigitalObjectModel.parseString(selNode.getAttributeAsString(Constants.ATTR_MODEL_ID)) != DigitalObjectModel.PAGE) {

                TreeNode[] treeRecords = subelementsGrid.getTree().getChildren(selNode);
                treePages = new TreeNode[treeRecords.length];

                int j = 0;
                for (int i = 0; i < treeRecords.length; i++) {
                    if (DigitalObjectModel.parseString(treeRecords[i]
                            .getAttributeAsString(Constants.ATTR_MODEL_ID)) == DigitalObjectModel.PAGE) {
                        treePages[j++] = treeRecords[i];
                    }
                }
            } else {
                treePages = new TreeNode[] {selNode};
            }

            getView().getTileGrid().setData(treePages);
            getView().updateRecordsInTileGrid(treePages, false);
        } else {
            getView().getTileGrid().setData(new Record[] {});
        }
    }

    @Override
    public void setTileGridHandlers() {
        getView().getTileGrid().addDropHandler(new DropHandler() {

            @Override
            public void onDrop(DropEvent event) {
                Object draggable = EventHandler.getDragTarget();
                if (draggable instanceof TreeGrid) {
                    ListGridRecord[] selection =
                            leftPresenter.getView().getSubelementsGrid().getSelectedRecords();
                    if (selection == null || selection.length == 0) {
                        event.cancel();
                        return;
                    }
                    for (ListGridRecord rec : selection) {
                        if (!DigitalObjectModel.PAGE.getValue()
                                .equals(rec.getAttribute(Constants.ATTR_MODEL_ID))) {
                            SC.say("TODO Sem muzete pretahovat jen objekty typu stranka.");
                            event.cancel();
                            return;
                        }
                    }

                }
            }
        });

        getView().getTileGrid().addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionChangedEvent event) {
                if (event.getRecord().getAttributeAsString(Constants.ATTR_PARENT) != null
                        && !"".equals(event.getRecord().getAttributeAsString(Constants.ATTR_PARENT))) {
                    Record rec = event.getRecord();
                    event.getRecord()
                            .setAttribute("__ref",
                                          "ScanRecord [getName()="
                                                  + rec.getAttributeAsString(Constants.ATTR_NAME) + ", "
                                                  + "getModel()=" + model + ", " + "getPicture()="
                                                  + rec.getAttributeAsString(Constants.ATTR_PICTURE_OR_UUID)
                                                  + ", " + "getPath()=, " + "getPageType()="
                                                  + rec.getAttributeAsString(Constants.ATTR_TYPE) + "]");
                    event.getRecord().setAttribute(Constants.ATTR_MODEL, model);
                    event.getRecord().setAttribute(Constants.ATTR_PARENT, "");
                    //                            event.getRecord().setAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE, "");
                }
            }
        });

        markedRecords = new ArrayList<Record>();
        getView().getTileGrid().addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                if (!isMarkingOff() && event.isAltKeyDown() && event.isCtrlKeyDown()) {
                    getView().addUndoRedo(getView().getTileGrid().getData(), true, false);
                    String isMarked =
                            event.getRecord().getAttributeAsString(Constants.ATTR_NOTE_OR_INT_SUBTITLE);
                    if (isMarked == null || Boolean.FALSE.toString().equals(isMarked)) {
                        event.getRecord().setAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE,
                                                       Boolean.TRUE.toString());
                        markedRecords.add(event.getRecord());

                        if (afterDivisionRenumber.getValueAsBoolean()) {
                            Record[] selection = getView().getTileGrid().getSelection();
                            List<Record> allList = Arrays.asList(getView().getTileGrid().getData());
                            List<Record> subList =
                                    allList.subList(getView().getTileGrid().getRecordIndex(event.getRecord()),
                                                    allList.size());

                            int i = 0;
                            if (subList != null && subList.size() > 0) {
                                for (Record rec : subList) {
                                    String recIsMarked =
                                            rec.getAttributeAsString(Constants.ATTR_NOTE_OR_INT_SUBTITLE);
                                    if (recIsMarked != null && !"".equals(recIsMarked)
                                            && Boolean.TRUE.toString().equals(recIsMarked)) {
                                        i = 1;
                                    }
                                    rec.setAttribute(Constants.ATTR_NAME, i++);
                                }
                            }
                            Record[] toUpdate = new Record[subList.size()];
                            getView().updateRecordsInTileGrid(subList.toArray(toUpdate), false);
                            getView().getTileGrid().deselectAllRecords();
                            getView().getTileGrid().selectRecords(selection);
                        }

                    } else {
                        markedRecords.remove(event.getRecord());
                        event.getRecord().setAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE,
                                                       Boolean.FALSE.toString());
                    }
                    getView().getTileGrid().deselectRecord(event.getRecord());

                }
                Record[] selection = getView().getTileGrid().getSelection();
                if (selection != null && selection.length > 0) {
                    leftPresenter.getSequentialCreateLayout().getKeepCheckbox().enable();
                } else {
                    leftPresenter.getSequentialCreateLayout().getKeepCheckbox().disable();
                }
            }
        });
    }

    private void setSectionCreateLayout() {

        if (DigitalObjectModel.parseString(model).getTopLevelType() == TopLevelObjectModel.PERIODICAL) {
            leftPresenter.getView().getCreationModeItem().show();

            final VLayout atOnceCreateLayout = new VLayout(2);

            HTMLFlow createFlow = new HTMLFlow(lang.markingLabel());

            createAtOnceButton = new ButtonItem("createAtOnceButton", lang.create());
            createAtOnceButton.setAlign(Alignment.RIGHT);
            DynamicForm createDynForm = new DynamicForm();
            createDynForm.setItems(createAtOnceButton);

            firstNumber = new TextItem("firstNumber", lang.firstNumber());
            firstNumber.setWidth(50);
            firstNumber.setDefaultValue(1);
            DynamicForm firstNumberDynForm = new DynamicForm();
            firstNumberDynForm.setItems(firstNumber);
            firstNumberDynForm.setWidth(150);

            baseDate = new EditorDateItem("baseDate", lang.baseDate(), lang);

            afterDivisionRenumber = new CheckboxItem("afterDivisionRenumber", lang.afterDivRen());
            afterDivisionRenumber.setValue(true);

            DynamicForm baseDateDynForm = new DynamicForm();
            baseDateDynForm.setItems(baseDate, afterDivisionRenumber);

            HLayout buttonItem = new HLayout(2);
            buttonItem.addMember(createDynForm);
            buttonItem.addMember(firstNumberDynForm);

            atOnceCreateLayout.addMember(createFlow);
            atOnceCreateLayout.addMember(buttonItem);
            atOnceCreateLayout.addMember(baseDateDynForm);
            atOnceCreateLayout.setAlign(Alignment.CENTER);
            atOnceCreateLayout.setWidth(250);
            atOnceCreateLayout.setHeight(70);

            leftPresenter.getView().setSectionCreateLayout(atOnceCreateLayout);
            leftPresenter.getView().getCreationModeItem().setValue(lang.atOnce());

            leftPresenter.getView().getCreationModeItem().addChangedHandler(new ChangedHandler() {

                @Override
                public void onChanged(ChangedEvent event) {

                    if (lang.atOnce().equals(leftPresenter.getView().getCreationModeItem().getValue())) {
                        leftPresenter.getView().setSectionCreateLayout(atOnceCreateLayout);
                    } else {
                        leftPresenter.getView()
                                .setSectionCreateLayout(leftPresenter.getSequentialCreateLayout());
                    }

                    if (markedRecords.size() > 0) {
                        Record[] selection = getView().getTileGrid().getSelection();
                        Record[] toUpdate = new Record[markedRecords.size()];
                        markedRecords.toArray(toUpdate);
                        getView().updateRecordsInTileGrid(toUpdate, true);
                        getView().getTileGrid().selectRecords(selection);
                    }
                }
            });

            createAtOnceButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    if (firstNumber.getValueAsString() == null || "".equals(firstNumber.getValueAsString())
                            || firstNumber.getValueAsString().matches(Constants.ONLY_NUMBERS)) {

                        final ModalWindow mw = new ModalWindow(leftPresenter.getView().getSectionStack());
                        mw.setLoadingIcon("loadingAnimation.gif");
                        mw.show(true);
                        Timer timer = new Timer() {

                            @Override
                            public void run() {
                                createAtOnceProcess();
                                mw.hide();
                            }
                        };
                        timer.run();
                    } else {
                        SC.warn(lang.textBox() + " " + lang.firstNumber() + " " + lang.onlyNum());
                    }

                }

            });
        } else {
            leftPresenter.getView().getCreationModeItem().hide();
            leftPresenter.getView().setSectionCreateLayout(leftPresenter.getSequentialCreateLayout());
            leftPresenter.getView().getCreationModeItem().setValue(lang.sequential());
        }
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.view.CreateView.MyUiHandlers#
     * onAddDigitalObject(com.smartgwt.client.widgets.tile.TileGrid,
     * com.smartgwt.client.widgets.menu.Menu)
     */
    @Override
    public void onAddImages(final TileGrid tileGrid, final Menu menu) {
        MenuItem[] items = menu.getItems();
        if (!CreateStructureView.ID_SEL_ALL
                .equals(items[3].getAttributeAsObject(CreateStructureView.ID_NAME))) {
            throw new IllegalStateException("Inconsistent gui.");
        }
        items[3].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                tileGrid.selectAllRecords();
            }
        });
        if (!CreateStructureView.ID_SEL_NONE.equals(items[4]
                .getAttributeAsObject(CreateStructureView.ID_NAME))) {
            throw new IllegalStateException("Inconsistent gui.");
        }
        items[4].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                tileGrid.deselectAllRecords();
            }
        });
        if (!CreateStructureView.ID_SEL_INV
                .equals(items[5].getAttributeAsObject(CreateStructureView.ID_NAME))) {
            throw new IllegalStateException("Inconsistent gui.");
        }
        items[5].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                Record[] selected = tileGrid.getSelection();
                tileGrid.selectAllRecords();
                tileGrid.deselectRecords(selected);
            }
        });
        if (!CreateStructureView.ID_COPY.equals(items[7].getAttributeAsObject(CreateStructureView.ID_NAME))) {
            throw new IllegalStateException("Inconsistent gui.");
        }
        items[7].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                getView().toClipboard(tileGrid.getSelection());
            }
        });
        if (!CreateStructureView.ID_PASTE.equals(items[8].getAttributeAsObject(CreateStructureView.ID_NAME))) {
            throw new IllegalStateException("Inconsistent gui.");
        }
        items[8].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                getView().addUndoRedo(getView().getTileGrid().getData(), true, false);
                final Record[] data = getView().fromClipboard();
                final boolean progressbar = data.length > Constants.CLIPBOARD_MAX_WITHOUT_PROGRESSBAR;
                final Progressbar hBar1 = progressbar ? new Progressbar() : null;
                if (progressbar) {
                    hBar1.setHeight(24);
                    hBar1.setVertical(false);
                    hBar1.setPercentDone(0);
                    getView().getPopupPanel().setWidget(hBar1);
                    getView().getPopupPanel().setVisible(true);
                    getView().getPopupPanel().center();
                    alreadyDone = 0;
                    Timer timer = new Timer() {

                        @Override
                        public void run() {
                            hBar1.setPercentDone(((100 * (alreadyDone + 1)) / data.length));
                            tileGrid.addData(getView().deepCopyScanRecord(data[alreadyDone]));//((ScanRecord) data[alreadyDone]).deepCopy());
                            if (++alreadyDone != data.length) {
                                schedule(15);
                            } else {
                                getView().getPopupPanel().setVisible(false);
                                getView().getPopupPanel().hide();
                            }
                        }
                    };
                    timer.schedule(40);
                } else {
                    for (int i = 0; i < data.length; i++) {
                        tileGrid.addData(getView().deepCopyScanRecord(data[i]));//((ScanRecord) data[i]).deepCopy());
                    }
                }
            }
        });
        if (!CreateStructureView.ID_DELETE.equals(items[9].getAttributeAsObject(CreateStructureView.ID_NAME))) {
            throw new IllegalStateException("Inconsistent gui.");
        }

        items[9].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                getView().addUndoRedo(getView().getTileGrid().getData(), true, false);

                if (getView().isChosenSelectedPagesTab()) {
                    Map<String, Integer> recordIdAndItsNewValue = new HashMap<String, Integer>();
                    for (Record recToDel : tileGrid.getSelection()) {
                        recordIdAndItsNewValue.put(recToDel.getAttribute(Constants.ATTR_ID), 0);
                    }
                    getEventBus()
                            .fireEvent(new ChangeStructureTreeItemEvent(STRUCTURE_TREE_ITEM_ACTION.DELETE_RECORD,
                                                                        recordIdAndItsNewValue));
                }
                tileGrid.removeSelectedData();
            }
        });

        if (!leftPresenter.getSequentialCreateLayout().hasCreateButtonAClickHandler()) {
            leftPresenter.getSequentialCreateLayout().getCreateButton().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    String message = leftPresenter.getSequentialCreateLayout().verify();
                    if (message == null) {
                        addNewStructure();
                    } else {
                        SC.warn(message);
                    }
                }
            });
            leftPresenter.getSequentialCreateLayout().setCreateButtonHasAClickHandler();
        }
    }

    private void createAtOnceProcess() {
        leftPresenter.getView().addUndoRedo(true, false);
        Record[] data = getView().getTileGrid().getData();

        String parent = null;
        DigitalObjectModel parentModel = null;
        if (leftPresenter.getView().getSubelementsGrid().getSelectedRecord() != null) {
            parent =
                    leftPresenter.getView().getSubelementsGrid().getSelectedRecord()
                            .getAttribute(Constants.ATTR_ID);

            TreeNode parentNode = leftPresenter.getView().getSubelementsGrid().getTree().findById(parent);
            parentModel = DigitalObjectModel.parseString(parentNode.getAttribute(Constants.ATTR_MODEL_ID));
        }
        if (parent == null || parentModel == null || parentModel != DigitalObjectModel.PERIODICALVOLUME) {
            parent =
                    addNewStructure(DigitalObjectModel.PERIODICALVOLUME,
                                    SubstructureTreeNode.ROOT_OBJECT_ID,
                                    null,
                                    true,
                                    "");
        }

        List<Record> toAdd = new ArrayList<Record>();

        int perItemNum =
                (firstNumber.getValueAsString() == null || "".equals(firstNumber.getValueAsString())) ? 0
                        : Integer.parseInt(firstNumber.getValueAsString()) - 1;
        for (Record rec : data) {
            if (toAdd.size() > 0) {
                String isMarked = rec.getAttributeAsString(Constants.ATTR_NOTE_OR_INT_SUBTITLE);
                if (isMarked != null && Boolean.TRUE.toString().equals(isMarked)) {
                    Record[] toAddRecords = new Record[toAdd.size()];
                    toAdd.toArray(toAddRecords);
                    addNewStructure(DigitalObjectModel.PERIODICALITEM,
                                    parent,
                                    toAddRecords,
                                    true,
                                    String.valueOf(++perItemNum));
                    toAdd = new ArrayList<Record>();
                }
            }
            toAdd.add(rec);
        }
        if (toAdd.size() > 0) {
            Record[] toAddRecords = new Record[toAdd.size()];
            toAdd.toArray(toAddRecords);
            addNewStructure(DigitalObjectModel.PERIODICALITEM,
                            parent,
                            toAddRecords,
                            true,
                            String.valueOf(++perItemNum));
        }

        getView().addUndoRedo(getView().getTileGrid().getData(), true, false);
        getView().getTileGrid().selectAllRecords();
        getView().getTileGrid().removeSelectedData();
    }

    private void addNewStructure() {
        final String type = leftPresenter.getSequentialCreateLayout().getSelectModel().getValueAsString();
        final DigitalObjectModel model = LabelAndModelConverter.getModelFromLabel().get(type);

        if (model != null) {
            final String parent =
                    leftPresenter.getView().getSubelementsGrid().getSelectedRecord()
                            .getAttribute(Constants.ATTR_ID);
            final Record[] selection = getView().getTileGrid().getSelection();
            TreeNode parentNode = leftPresenter.getView().getSubelementsGrid().getTree().findById(parent);
            String parentModelString = parentNode.getAttribute(Constants.ATTR_MODEL_ID);
            DigitalObjectModel parentModel = null;
            if (parentModelString != null && !"".equals(parentModelString))
                parentModel = DigitalObjectModel.parseString(parentModelString);

            if (model == DigitalObjectModel.INTERNALPART
                    || (model == DigitalObjectModel.PAGE && parentModel != null && parentModel == DigitalObjectModel.INTERNALPART)) {

                final List<Record> missing = leftPresenter.getView().getMissingPages(parentNode, selection);

                String grandpaPom = null;

                if (model == DigitalObjectModel.PAGE) {
                    grandpaPom =
                            leftPresenter.getView().getSubelementsGrid().getTree().getParent(parentNode)
                                    .getAttribute(Constants.ATTR_ID);
                }
                final String grandpa = grandpaPom;
                if (!missing.isEmpty()) {
                    SC.ask(lang.missingPages(), new BooleanCallback() {

                        @Override
                        public void execute(Boolean value) {
                            if (value != null && value) {
                                addNewStructure(model, parent, selection, false, "");
                                leftPresenter.addPages(missing, model != DigitalObjectModel.PAGE ? parent
                                        : grandpa, false);
                            }
                        }
                    });
                } else {
                    addNewStructure(model, parent, selection, false, "");
                }
            } else {
                addNewStructure(model, parent, selection, false, "");
            }
        }
    }

    private String addNewStructure(DigitalObjectModel model,
                                   final String parent,
                                   Record[] selection,
                                   boolean createAtOnce,
                                   String perItemNum) {

        List<DigitalObjectModel> canContain = NamedGraphModel.getChildren(model);

        if (!createAtOnce) leftPresenter.getView().addUndoRedo(true, false);
        String name = "";
        String dateOrIntPartName = "";
        String noteOrSubtitle = "";
        String partNumOrAlto = "";
        String aditionalInfoOrOcr = "";
        String type = "";

        switch (model) {
            case PERIODICALVOLUME:
                dateOrIntPartName =
                        createAtOnce ? "" : leftPresenter.getSequentialCreateLayout().getDateIssued();
                name = dateOrIntPartName;
                noteOrSubtitle = createAtOnce ? "" : leftPresenter.getSequentialCreateLayout().getNote();
                partNumOrAlto =
                        createAtOnce ? "1" : leftPresenter.getSequentialCreateLayout().getPartNumber();
                break;

            case PERIODICALITEM:
                name = createAtOnce ? "" : leftPresenter.getSequentialCreateLayout().getNameOrTitle();
                dateOrIntPartName =
                        createAtOnce ? baseDate.getEditorDate() : leftPresenter.getSequentialCreateLayout()
                                .getDateIssued();
                noteOrSubtitle = createAtOnce ? "" : leftPresenter.getSequentialCreateLayout().getNote();
                partNumOrAlto =
                        createAtOnce ? perItemNum : leftPresenter.getSequentialCreateLayout().getPartNumber();
                aditionalInfoOrOcr =
                        createAtOnce ? PERIODICAL_ITEM_LEVEL_NAMES.MODS_ISSUE.getValue() : leftPresenter
                                .getSequentialCreateLayout().getLevelName();
                type =
                        createAtOnce ? PERIODICAL_ITEM_GENRE_TYPES.MAP.get(PERIODICAL_ITEM_GENRE_TYPES.NORMAL
                                .toString()) : leftPresenter.getSequentialCreateLayout()
                                .getType(model, aditionalInfoOrOcr);
                break;

            case INTERNALPART:
                name = leftPresenter.getSequentialCreateLayout().getNameOrTitle();
                dateOrIntPartName = leftPresenter.getSequentialCreateLayout().getPartName();
                noteOrSubtitle = leftPresenter.getSequentialCreateLayout().getSubtitle();
                partNumOrAlto = leftPresenter.getSequentialCreateLayout().getPartNumber();
                aditionalInfoOrOcr = leftPresenter.getSequentialCreateLayout().getLevelName();
                type = leftPresenter.getSequentialCreateLayout().getType(model, aditionalInfoOrOcr);
                break;

            case MONOGRAPHUNIT:
                name = leftPresenter.getSequentialCreateLayout().getNameOrTitle();
                dateOrIntPartName = leftPresenter.getSequentialCreateLayout().getDateIssued();
                noteOrSubtitle = leftPresenter.getSequentialCreateLayout().getNote();
                partNumOrAlto = leftPresenter.getSequentialCreateLayout().getPartNumber();
                aditionalInfoOrOcr = leftPresenter.getSequentialCreateLayout().getLevelName();
                break;

            default:
                break;
        }
        String possibleParent = "-1";
        if (canContain != null) { //adding selected pages
            possibleParent = String.valueOf(leftPresenter.newId());

            leftPresenter.getView().addSubstructure(possibleParent,
                                                    parent,
                                                    name,
                                                    null,
                                                    model.getValue(),
                                                    type,
                                                    dateOrIntPartName,
                                                    noteOrSubtitle,
                                                    partNumOrAlto,
                                                    aditionalInfoOrOcr,
                                                    true,
                                                    false);
        } else { // adding something and enrich it with selected pages
            possibleParent = parent;
        }

        if (selection != null && selection.length > 0
                && (canContain == null || canContain.contains(DigitalObjectModel.PAGE))) {
            leftPresenter.addPages(Arrays.asList(selection), possibleParent, createAtOnce);

            if (!leftPresenter.getSequentialCreateLayout().getKeepCheckbox().getValueAsBoolean()
                    && !createAtOnce) {
                getView().addUndoRedo(getView().getTileGrid().getData(), true, false);
                getView().getTileGrid().removeSelectedData();
            }
        }
        return possibleParent;
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
     */
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, Constants.TYPE_MEDIT_MAIN_CONTENT, this);
    }

    @ProxyEvent
    @Override
    public void onCreateStructure(CreateStructureEvent event) {
        this.model = event.getModel();
        this.sysno = event.getCode();
        this.leftPresenter.setBarcode(sysno);
        this.inputPath = event.getInputPath();
        this.bundle = event.getBundle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createObjects(final DublinCore dc,
                              final ModsTypeClient mods,
                              final boolean visible,
                              int thumbPageNum) {

        DublinCore newDc = dc == null && bundle != null ? bundle.getDc() : dc;
        if (newDc != null) {
            String errorMessage = ClientCreateUtils.checkDC(newDc, lang);
            if (!"".equals(errorMessage)) {
                SC.warn(errorMessage);
                return;
            }
        }

        Image progress = new Image("images/ingesting.gif");
        getView().getPopupPanel().setWidget(progress);
        getView().getPopupPanel().setVisible(true);
        getView().getPopupPanel().center();
        alreadyDone = 0;

        ModsCollectionClient newMods;
        if (mods != null) {
            newMods = new ModsCollectionClient();
            newMods.setMods(Arrays.asList(mods));
        } else {
            newMods =
                    bundle != null && bundle.getMods() != null ? bundle.getMods()
                            : new ModsCollectionClient();
        }
        NewDigitalObject object = null;

        try {
            TreeGrid treeGrid = leftPresenter.getView().getSubelementsGrid();
            boolean isPdf = getView().getTileGrid() == null && getView().getPdfViewerPane() != null;
            MetadataBundle metadataBundle =
                    new MetadataBundle(newDc == null ? new DublinCore() : newDc,
                                       newMods,
                                       bundle == null ? null : bundle.getMarc());
            object = ClientCreateUtils.createTheStructure(metadataBundle, treeGrid.getTree(), visible, isPdf);
            if (isPdf) {
                object.setPath(getView().getPdfViewerPane().getUuid());
                object.setPageIndex(thumbPageNum);
                if (addOcr.getValueAsBoolean() && addOcr.getAttribute(Constants.ATTR_OCR_PATH) != null
                        && !"".equals(addOcr.getAttributeAsString(Constants.ATTR_OCR_PATH))) {
                    object.setAditionalInfoOrOcr(addOcr.getAttributeAsString(Constants.ATTR_OCR_PATH));
                }
            }

        } catch (CreateObjectException e) {
            SC.warn(e.getMessage());
            e.printStackTrace();
        }

        if (object != null) {
            object.setSysno(sysno);
            object.setBase(base);
            object.setSignature(bundle != null ? bundle.getMarc().getSignature() : null);
            dispatcher.execute(new InsertNewDigitalObjectAction(object, "/" + model + "/" + inputPath),
                               new DispatchCallback<InsertNewDigitalObjectResult>() {

                                   @Override
                                   public void callback(final InsertNewDigitalObjectResult result) {
                                       if (result.isIngestSuccess()) {
                                           if (!result.isReindexSuccess() || !result.isDeepZoomSuccess()) {
                                               String error;
                                               if (!result.isReindexSuccess() && !result.isDeepZoomSuccess()) {
                                                   error = lang.reindexFail() + " " + lang.deepZoomFail();
                                               } else if (!result.isReindexSuccess()) {
                                                   error = lang.reindexFail();
                                               } else {
                                                   /** (internal image server is enabled) */
                                                   error = lang.deepZoomFail();
                                               }

                                               SC.warn(error, new BooleanCallback() {

                                                   @Override
                                                   public void execute(Boolean value) {
                                                       openCreated(result.getNewPid());
                                                   }
                                               });
                                           }
                                           else {
                                               openCreated(result.getNewPid());
                                           }
                                       } else {
                                           SC.warn(lang.ingestFail());
                                       }
                                       getView().getPopupPanel().setVisible(false);
                                       getView().getPopupPanel().hide();
                                   }

                                   @Override
                                   public void callbackError(Throwable t) {
                                       SC.warn(lang.ingestFail() + " " + lang.error() + ": " + t.getMessage());
                                       getView().getPopupPanel().setVisible(false);
                                       getView().getPopupPanel().hide();
                                   }
                               });
        } else {
            SC.warn(lang.emptyObj());
        }
    }

    private void openCreated(final String pid) {
        SC.ask(lang.ingestOk(), new BooleanCallback() {

            @Override
            public void execute(Boolean value) {
                if (value) {
                    placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY)
                            .with(Constants.URL_PARAM_UUID, pid));
                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModsCollectionClient getMods() {
        return bundle == null ? null : bundle.getMods();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DublinCore getDc() {
        return bundle == null || bundle.getDc() == null ? new DublinCore() : bundle.getDc();
    }

    public void load(TreeNode[] treeData, Record[] pagesData, int lastId) {
        getView().getPagesTabSet().selectTab(0);
        Tree tree = new Tree();
        tree.setModelType(TreeModelType.PARENT);
        tree.setRootValue(SubstructureTreeNode.ROOT_ID);
        tree.setIdField(Constants.ATTR_ID);
        tree.setParentIdField(Constants.ATTR_PARENT);
        tree.setOpenProperty("isOpen");
        tree.setData(treeData);
        leftPresenter.getView().setStructureTree(tree);
        leftPresenter.getView().getSubelementsGrid().setData(tree);
        leftPresenter.getView().getSubelementsGrid().selectRecord(0);
        leftPresenter.getView().getSubelementsGrid().redraw();
        leftPresenter.setLastId(lastId);
        if (pagesData != null) getView().getTileGrid().setData(pagesData);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void chooseDetail(String pathToImg,
                             int detailHeight,
                             String uuid,
                             boolean isTop,
                             int topSpace,
                             ModalWindow mw) {
        new ChooseDetailWindow(detailHeight,
                               getEventBus(),
                               dispatcher,
                               pathToImg,
                               uuid,
                               lang,
                               isTop,
                               topSpace,
                               mw) {

            @Override
            protected void setDetail(int recentHeight, boolean isTop, int topSpace) {
                getView().showDetail(recentHeight, isTop, topSpace);
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMarkingOff() {
        return lang.sequential().equals(leftPresenter.getView().getCreationModeItem().getValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addUndoInLeftPanel() {
        leftPresenter.getView().addUndoRedo(true, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Record> getMarkedRecords() {
        return markedRecords;
    }

    private void handlePdf(final ScanFolderResult result) {
        ImageItem item = result.getItems().get(0);
        getView().onAddImages(DigitalObjectModel.PAGE.getValue(),
                              new ScanRecord[] {new ScanRecord(item.getJpeg2000FsPath(), "", item
                                      .getIdentifier(), "")},
                              config.getHostname(),
                              false,
                              true);
        getView().getPopupPanel().setAutoHideEnabled(true);
        getView().getPopupPanel().setWidget(null);
        getView().getPopupPanel().setVisible(false);
        getView().getPopupPanel().hide();

        final VLayout menuLayout = new VLayout();
        final HLayout topLayout = new HLayout();

        menuLayout.setHeight(120);

        topLayout.setAlign(Alignment.CENTER);
        topLayout.setAlign(VerticalAlignment.CENTER);
        topLayout.setDefaultLayoutAlign(VerticalAlignment.CENTER);

        topLayout.addMember(getView().getEditMetadataButton(null));
        topLayout.addMember(getView().getCreateButton(true));
        menuLayout.addMember(topLayout);

        addOcr = new CheckboxItem("addOcr", lang.addOcr());
        addOcr.setValue(false);
        addOcr.setVisible(false);

        DynamicForm createOcrForm = new DynamicForm();
        createOcrForm.setItems(getOcrFromPdfButton(result.getItems().get(0).getIdentifier(), menuLayout));
        createOcrForm.setWidth(50);
        menuLayout.addMember(createOcrForm);

        DynamicForm addOcrForm = new DynamicForm();
        addOcrForm.setItems(addOcr);
        addOcrForm.setExtraSpace(15);
        menuLayout.addMember(addOcrForm);

        menuLayout.setAlign(Alignment.CENTER);
        menuLayout.setAlign(VerticalAlignment.CENTER);
        menuLayout.setDefaultLayoutAlign(VerticalAlignment.CENTER);

        leftPresenter.getView().setSectionCreateLayout(menuLayout);

        leftPresenter.getView().getCreationModeItem().hide();
        leftPresenter.getView().getSectionStack().getSection(1).setTitle("Menu");
        leftPresenter.getView().getSubelementsGrid().setFolderIcon("icons/16/pdf.png");
        leftPresenter.getView().getSubelementsGrid().redraw();

    }

    private ButtonItem getOcrFromPdfButton(final String uuid, final VLayout leftLayout) {
        final ButtonItem createOcr = new ButtonItem("createOcr", lang.retrieveOCR());
        createOcr.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (addOcr.getAttribute(Constants.ATTR_OCR_PATH) == null) {
                    final ModalWindow mw = new ModalWindow(leftLayout);
                    mw.setLoadingIcon("loadingAnimation.gif");
                    mw.show(true);
                    dispatcher.execute(new GetOcrFromPdfAction(uuid),
                                       new DispatchCallback<GetOcrFromPdfResult>() {

                                           @Override
                                           public void callback(cz.mzk.editor.shared.rpc.action.GetOcrFromPdfResult result) {
                                               addOcr.setAttribute(Constants.ATTR_OCR_PATH, result.getOcr()
                                                       .trim());
                                               addOcr.show();
                                               addOcr.setValue(true);
                                               leftLayout.redraw();
                                               createOcr.setTitle(lang.menuEdit() + " OCR");
                                               showOcrWindow(result.getOcr().trim());
                                               mw.hide();
                                           }

                                           @Override
                                           public void callbackError(Throwable t) {
                                               super.callbackError(t);
                                               mw.hide();
                                           }
                                       });

                } else {
                    showOcrWindow(addOcr.getAttribute(Constants.ATTR_OCR_PATH));
                }
            }
        });
        return createOcr;
    }

    private void showOcrWindow(String ocr) {
        new OcrWindow(getEventBus(), ocr, lang) {

            @Override
            protected void doSaveAction(String value) {
                addOcr.setAttribute(Constants.ATTR_OCR_PATH, value.trim());
            }
        };
    }
}
