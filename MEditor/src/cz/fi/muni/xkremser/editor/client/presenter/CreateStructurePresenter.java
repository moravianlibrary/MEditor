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

package cz.fi.muni.xkremser.editor.client.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.events.SelectionChangedEvent;
import com.smartgwt.client.widgets.tile.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;

import cz.fi.muni.xkremser.editor.client.CreateObjectException;
import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.MEditor;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.mods.DateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.client.mods.ModsTypeClient;
import cz.fi.muni.xkremser.editor.client.util.ClientUtils;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.CreateObjectMenuView.SubstructureTreeNode;
import cz.fi.muni.xkremser.editor.client.view.CreateStructureView;
import cz.fi.muni.xkremser.editor.client.view.CreateStructureView.MyUiHandlers;
import cz.fi.muni.xkremser.editor.client.view.other.ScanRecord;
import cz.fi.muni.xkremser.editor.client.view.window.ChooseDetailWindow;
import cz.fi.muni.xkremser.editor.client.view.window.EditorSC;
import cz.fi.muni.xkremser.editor.client.view.window.ModalWindow;
import cz.fi.muni.xkremser.editor.client.view.window.StoreTreeStructureWindow;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.domain.NamedGraphModel;
import cz.fi.muni.xkremser.editor.shared.event.ChangeMenuWidthEvent;
import cz.fi.muni.xkremser.editor.shared.event.CreateStructureEvent;
import cz.fi.muni.xkremser.editor.shared.event.CreateStructureEvent.CreateStructureHandler;
import cz.fi.muni.xkremser.editor.shared.event.KeyPressedEvent;
import cz.fi.muni.xkremser.editor.shared.event.LoadStructureEvent;
import cz.fi.muni.xkremser.editor.shared.event.RefreshTreeEvent;
import cz.fi.muni.xkremser.editor.shared.event.SaveStructureEvent;
import cz.fi.muni.xkremser.editor.shared.event.SetEnabledHotKeysEvent;
import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;
import cz.fi.muni.xkremser.editor.shared.rpc.ImageItem;
import cz.fi.muni.xkremser.editor.shared.rpc.MetadataBundle;
import cz.fi.muni.xkremser.editor.shared.rpc.NewDigitalObject;
import cz.fi.muni.xkremser.editor.shared.rpc.ServerActionResult;
import cz.fi.muni.xkremser.editor.shared.rpc.TreeStructureBundle;
import cz.fi.muni.xkremser.editor.shared.rpc.TreeStructureBundle.TreeStructureInfo;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ConvertToJPEG2000Action;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ConvertToJPEG2000Result;
import cz.fi.muni.xkremser.editor.shared.rpc.action.InsertNewDigitalObjectAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.InsertNewDigitalObjectResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanFolderAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanFolderResult;

/**
 * @author Jiri Kremser
 */
public class CreateStructurePresenter
        extends Presenter<CreateStructurePresenter.MyView, CreateStructurePresenter.MyProxy>
        implements MyUiHandlers, CreateStructureHandler {

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
            extends View, HasUiHandlers<MyUiHandlers> {

        Record[] fromClipboard();

        void toClipboard(final Record[] data);

        PopupPanel getPopupPanel();

        void onAddImages(String model, Record[] items, String hostname, boolean resize);

        void escShortCut();

        TileGrid getTileGrid();

        void addUndoRedo(Record[] data, boolean isUndoList, boolean isRedoOperation);

        void resizeThumbnails(boolean larger);

        void showDetail(int height, boolean isTop, int topSpace);
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

    /** The doMenuPresenter presenter. */
    private final DigitalObjectMenuPresenter doMenuPresenter;

    /** The sysno. */
    private String sysno;

    private String inputPath;

    /** The model. */
    private String model;

    private MetadataBundle bundle;

    /** The place manager. */
    private final PlaceManager placeManager;

    private final EditorClientConfiguration config;

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
                                    final DigitalObjectMenuPresenter doMenuPresenter,
                                    final DispatchAsync dispatcher,
                                    final PlaceManager placeManager,
                                    final EditorClientConfiguration config) {
        super(eventBus, view, proxy);
        this.leftPresenter = leftPresenter;
        this.doMenuPresenter = doMenuPresenter;
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
                NewDigitalObject object = null;
                try {
                    object =
                            ClientUtils.createTheStructure(null, leftPresenter.getView().getSubelementsGrid()
                                    .getTree(), true);
                } catch (CreateObjectException e) {
                    SC.warn(e.getMessage());
                    e.printStackTrace();
                }
                Record[] tilegridData = getView().getTileGrid().getData();
                boolean emptyTree = object == null || object.getModel() == null;
                boolean emptyPages = tilegridData == null || tilegridData.length == 0;
                if (emptyTree && emptyPages) {
                    SC.warn(lang.nothingToSave());
                } else {
                    object.setName(ClientUtils.trimLabel(object.getName(), Constants.MAX_LABEL_LENGTH));
                    TreeStructureBundle bundle = new TreeStructureBundle();
                    bundle.setInfo(new TreeStructureInfo(-1, null, null, leftPresenter.getBarcode(), object
                            .getName(), null, inputPath, model));
                    bundle.setNodes(new ArrayList<TreeStructureBundle.TreeStructureNode>());
                    if (!emptyTree) {
                        bundle.getNodes().addAll(ClientUtils.toNodes(leftPresenter.getView()
                                .getSubelementsGrid().getTree().getAllNodes()));
                    }
                    if (!emptyPages) {
                        bundle.getNodes().addAll(ClientUtils.toNodes(tilegridData));
                    }
                    StoreTreeStructureWindow.setInstanceOf(bundle,
                                                           emptyTree ? null : ClientUtils
                                                                   .toStringTree(object),
                                                           emptyPages ? null : ClientUtils
                                                                   .recordsToString(tilegridData),
                                                           lang,
                                                           dispatcher,
                                                           getEventBus());
                }
            }
        });

        addRegisteredHandler(LoadStructureEvent.getType(), new LoadStructureEvent.LoadStructureHandler() {

            @Override
            public void onLoadStructure(LoadStructureEvent event) {
                load(event.getTree(), event.getPages());
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
        RevealContentEvent.fire(this, AppPresenter.TYPE_SetLeftContent, leftPresenter);

        leftPresenter.getView().setInputTree(doMenuPresenter.getView().getInputTree());
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
        leftPresenter.getView().getSubelementsGrid().disable();
        leftPresenter.getView().getSubelementsGrid().selectAllRecords();
        leftPresenter.getView().getSubelementsGrid().removeSelectedData();
        leftPresenter.getView().getSubelementsGrid().enable();
        leftPresenter.getView().getSubelementsGrid().redraw();
        leftPresenter.getView().addSubstructure(SubstructureTreeNode.ROOT_OBJECT_ID,
                                                -1,
                                                name,
                                                null,
                                                model,
                                                model,
                                                SubstructureTreeNode.ROOT_ID,
                                                "",
                                                "",
                                                true,
                                                false);
        leftPresenter.getView().getSubelementsGrid().selectRecord(0);
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
                    convert(result);
                } else {
                    if (serverActionResult.getServerActionResult() == Constants.SERVER_ACTION_RESULT.WRONG_FILE_NAME) {
                        SC.ask(lang.wrongFileName() + serverActionResult.getMessage(), new BooleanCallback() {

                            @Override
                            public void execute(Boolean value) {
                                if (value != null && value) {
                                    convert(result);
                                }
                            }
                        });
                    }
                }

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
                                MEditor.redirect(t.getMessage().substring(1));
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
                                new ScanRecord(i + 1,
                                               String.valueOf(i + 1),
                                               model,
                                               sysno,
                                               itemList.get(i).getIdentifier(),
                                               itemList.get(i).getJpgFsPath(),
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
                                          false);
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
                                            .equals(rec.getAttribute(Constants.ATTR_TYPE_ID))) {
                                        SC.say("TODO Sem muzete pretahovat jen objekty typu stranka.");
                                        event.cancel();
                                        return;
                                    }
                                }

                            }
                        }
                    });
                }
                getView().getPopupPanel().setAutoHideEnabled(true);
                getView().getPopupPanel().setWidget(null);
                getView().getPopupPanel().setVisible(false);
                getView().getPopupPanel().hide();
                SetEnabledHotKeysEvent.fire(CreateStructurePresenter.this, true);
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

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.client.view.CreateView.MyUiHandlers#
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
                            tileGrid.addData(((ScanRecord) data[alreadyDone]).deepCopy());
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
                        tileGrid.addData(((ScanRecord) data[i]).deepCopy());
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
                tileGrid.removeSelectedData();
            }
        });

        getView().getTileGrid().addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionChangedEvent event) {
                Record[] selection = getView().getTileGrid().getSelection();
                if (selection != null && selection.length > 0) {
                    leftPresenter.getView().getKeepCheckbox().enable();
                } else {
                    leftPresenter.getView().getKeepCheckbox().disable();
                }
            }
        });

        if (!leftPresenter.getView().hasCreateButtonAClickHandler()) {
            leftPresenter.getView().getCreateButton().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    addNewStructure();
                }
            });
            leftPresenter.getView().setCreateButtonHasAClickHandler();
        }
    }

    private void addNewStructure() {
        final String type = leftPresenter.getView().getSelectModel().getValueAsString();
        final DigitalObjectModel model = leftPresenter.getModelFromLabel().get(type);

        if (model != null) {
            final String parent =
                    leftPresenter.getView().getSubelementsGrid().getSelectedRecord()
                            .getAttribute(Constants.ATTR_ID);
            final Record[] selection = getView().getTileGrid().getSelection();
            TreeNode parentNode = leftPresenter.getView().getSubelementsGrid().getTree().findById(parent);
            String parentModelString = parentNode.getAttribute(Constants.ATTR_TYPE_ID);
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
                                addNewStructure(model, type, parent, selection, missing);
                                leftPresenter.addPages(missing, model != DigitalObjectModel.PAGE ? parent
                                        : grandpa);
                            }
                        }
                    });
                } else {
                    addNewStructure(model, type, parent, selection, new ArrayList<Record>());
                }
            } else {
                addNewStructure(model, type, parent, selection, new ArrayList<Record>());
            }
        }
    }

    private void addNewStructure(DigitalObjectModel model,
                                 String type,
                                 final String parent,
                                 Record[] selection,
                                 final List<Record> missing) {
        List<DigitalObjectModel> canContain = NamedGraphModel.getChildren(model);

        leftPresenter.getView().addUndoRedo(true, false);
        String dateIssued =
                (model == DigitalObjectModel.PERIODICALVOLUME || model == DigitalObjectModel.PERIODICALITEM) ? leftPresenter
                        .getView().getDateIssued().getValueAsString()
                        : "";
        String possibleParent = "-1";
        if (canContain != null) { //adding selected pages
            possibleParent = String.valueOf(leftPresenter.newId());
            String name = leftPresenter.getView().getNewName().getValueAsString();
            name = name == null || "".equals(name) ? possibleParent : name;
            leftPresenter.getView().addSubstructure(possibleParent,
                                                    -1,
                                                    name,
                                                    null,
                                                    type,
                                                    model.getValue(),
                                                    parent,
                                                    "",
                                                    dateIssued,
                                                    true,
                                                    false);
        } else { // adding something and enrich it with selected pages
            possibleParent = parent;
        }

        if (selection != null && selection.length > 0
                && (canContain == null || canContain.contains(DigitalObjectModel.PAGE))) {
            leftPresenter.addPages(Arrays.asList(selection), possibleParent);

            if (!leftPresenter.getView().getKeepCheckbox().getValueAsBoolean()) {
                getView().addUndoRedo(getView().getTileGrid().getData(), true, false);
                getView().getTileGrid().removeSelectedData();
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
     */
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, AppPresenter.TYPE_SetMainContent, this);
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
    public void createObjects(final DublinCore dc, final ModsTypeClient mods, final boolean visible) {

        Image progress = new Image("images/ingesting.gif");
        getView().getPopupPanel().setWidget(progress);
        getView().getPopupPanel().setVisible(true);
        getView().getPopupPanel().center();
        alreadyDone = 0;

        DublinCore newDc = dc == null && bundle != null ? bundle.getDc() : dc;
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
            treeGrid.setSortState("({fieldName:null,sortDir:false,sortSpecifiers:[{property:\"order\",direction:\"descending\"}]})");
            object =
                    ClientUtils.createTheStructure(new MetadataBundle(newDc == null ? new DublinCore()
                                                           : newDc, newMods, bundle == null ? null : bundle
                                                           .getMarc()),
                                                   treeGrid.getTree(),
                                                   visible);
            treeGrid.setSortState("({fieldName:null,sortDir:false,sortSpecifiers:[{property:\"order\",direction:\"ascending\"}]})");
        } catch (CreateObjectException e) {
            SC.warn(e.getMessage());
            e.printStackTrace();
        }
        if (object != null) {
            object.setSysno(sysno);
            //            System.out.println(ClientUtils.toStringTree(object));
            dispatcher.execute(new InsertNewDigitalObjectAction(object, "/" + model + "/" + inputPath),
                               new DispatchCallback<InsertNewDigitalObjectResult>() {

                                   @Override
                                   public void callback(final InsertNewDigitalObjectResult result) {
                                       if (result.isIngestSuccess()) {
                                           if (!result.isReindexSuccess()) {
                                               SC.warn(lang.reindexFail(), new BooleanCallback() {

                                                   @Override
                                                   public void execute(Boolean value) {
                                                       openCreated(result.getNewPid());
                                                   }
                                               });
                                           } else {
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

    public void load(TreeNode[] treeData, Record[] pagesData) {
        Tree tree = new Tree();
        tree.setModelType(TreeModelType.PARENT);
        tree.setRootValue(SubstructureTreeNode.ROOT_ID);
        tree.setIdField(Constants.ATTR_ID);
        tree.setParentIdField(Constants.ATTR_PARENT);
        tree.setOpenProperty("isOpen");
        tree.setData(treeData);
        leftPresenter.getView().getSubelementsGrid().setData(tree);
        leftPresenter.getView().getSubelementsGrid().selectRecord(0);
        leftPresenter.getView().getSubelementsGrid().redraw();
        getView().getTileGrid().setData(pagesData);
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

}