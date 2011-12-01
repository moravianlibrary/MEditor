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
import com.smartgwt.client.widgets.tree.TreeGrid;

import cz.fi.muni.xkremser.editor.client.CreateObjectException;
import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.MEditor;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.client.mods.ModsTypeClient;
import cz.fi.muni.xkremser.editor.client.util.ClientUtils;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.CreateObjectMenuView.SubstructureTreeNode;
import cz.fi.muni.xkremser.editor.client.view.CreateStructureView;
import cz.fi.muni.xkremser.editor.client.view.CreateStructureView.MyUiHandlers;
import cz.fi.muni.xkremser.editor.client.view.other.ScanRecord;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.domain.NamedGraphModel;
import cz.fi.muni.xkremser.editor.shared.event.ChangeMenuWidthEvent;
import cz.fi.muni.xkremser.editor.shared.event.CreateStructureEvent;
import cz.fi.muni.xkremser.editor.shared.event.CreateStructureEvent.CreateStructureHandler;
import cz.fi.muni.xkremser.editor.shared.event.KeyPressedEvent;
import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;
import cz.fi.muni.xkremser.editor.shared.rpc.ImageItem;
import cz.fi.muni.xkremser.editor.shared.rpc.MetadataBundle;
import cz.fi.muni.xkremser.editor.shared.rpc.NewDigitalObject;
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

        void onAddImages(String model, String code, ScanRecord[] items);

        void escShortCut();

        TileGrid getTileGrid();
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
                                    final PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.leftPresenter = leftPresenter;
        this.doMenuPresenter = doMenuPresenter;
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
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
                if (event.getCode() == Constants.CODE_KEY_ESC) {
                    getView().escShortCut();
                }
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
                                                name,
                                                null,
                                                model,
                                                model,
                                                SubstructureTreeNode.ROOT_ID,
                                                true,
                                                false);
        leftPresenter.getView().getSubelementsGrid().selectRecord(0);
        ChangeMenuWidthEvent.fire(getEventBus(), "340");
    }

    /**
      * 
      */

    private void processImages() {
        final ScanFolderAction action = new ScanFolderAction(model, inputPath);
        final DispatchCallback<ScanFolderResult> callback = new DispatchCallback<ScanFolderResult>() {

            private volatile int done = 0;
            private int total = 0;
            private volatile boolean isDone = false;

            @Override
            public void callback(ScanFolderResult result) {
                final List<ImageItem> itemList = result == null ? null : result.getItems();
                final List<ImageItem> toAdd = result == null ? null : result.getToAdd();
                if (toAdd != null && !toAdd.isEmpty()) {
                    int lastItem = toAdd.size();
                    boolean progressbar = lastItem > 5;
                    final Progressbar hBar1 = progressbar ? new Progressbar() : null;
                    if (progressbar) {
                        done = 0;
                        total = lastItem;
                        hBar1.setHeight(24);
                        hBar1.setVertical(false);
                        hBar1.setPercentDone(0);
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
                    SC.confirm("Session has expired. Do you want to be redirected to login page?",
                               new BooleanCallback() {

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
                getView().getPopupPanel().setVisible(false);
                getView().getPopupPanel().hide();
            }

            private void doTheRest(List<ImageItem> itemList) {
                ScanRecord[] items = null;
                if (itemList != null && itemList.size() > 0) {
                    items = new ScanRecord[itemList.size()];
                    for (int i = 0, total = itemList.size(); i < total; i++) {
                        items[i] =
                                new ScanRecord(String.valueOf(i + 1), model, sysno, itemList.get(i)
                                        .getIdentifier(), itemList.get(i).getJpgFsPath());
                    }

                    getView().onAddImages(DigitalObjectModel.PAGE.getValue(), sysno, items);
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
                getView().getPopupPanel().setWidget(null);
                getView().getPopupPanel().setVisible(false);
                getView().getPopupPanel().hide();
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

        leftPresenter.getView().getCreateButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                addNewStructure();
            }
        });
    }

    private void addNewStructure() {
        String parent =
                leftPresenter.getView().getSubelementsGrid().getSelectedRecord()
                        .getAttribute(Constants.ATTR_ID);
        String type = leftPresenter.getView().getSelectModel().getValueAsString();
        DigitalObjectModel model = leftPresenter.getModelFromLabel().get(type);
        List<DigitalObjectModel> canContain = NamedGraphModel.getChildren(model);
        if (model != null) {
            String possibleParent = "-1";
            if (canContain != null) { //adding selected pages
                possibleParent = String.valueOf(leftPresenter.newId());
                String name = leftPresenter.getView().getNewName().getValueAsString();
                name = "".equals(name) ? possibleParent : name;
                leftPresenter.getView().addSubstructure(possibleParent,
                                                        name,
                                                        null,
                                                        type,
                                                        model.getValue(),
                                                        parent,
                                                        true,
                                                        false);
            } else { // adding something and enrich it with selected pages
                possibleParent = parent;
            }

            Record[] selection = getView().getTileGrid().getSelection();
            if (selection != null && selection.length > 0
                    && (canContain == null || canContain.contains(DigitalObjectModel.PAGE))) {
                for (int i = 0; i < selection.length; i++) {
                    leftPresenter.getView()
                            .addSubstructure(String.valueOf(leftPresenter.newId()),
                                             selection[i].getAttribute(Constants.ATTR_NAME),
                                             selection[i].getAttribute(Constants.ATTR_PICTURE),
                                             leftPresenter.getLabelFromModel().get(DigitalObjectModel.PAGE
                                                     .getValue()),
                                             DigitalObjectModel.PAGE.getValue(),
                                             possibleParent,
                                             true,
                                             false);
                    if (!leftPresenter.getView().getKeepCheckbox().getValueAsBoolean()) {
                        getView().getTileGrid().removeSelectedData();
                    }
                }
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
        this.inputPath = event.getInputPath();
        this.bundle = event.getBundle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createObjects(final DublinCore dc, final ModsTypeClient mods) {

        Image progress = new Image("images/ingesting.gif");
        getView().getPopupPanel().setWidget(progress);
        getView().getPopupPanel().setVisible(true);
        getView().getPopupPanel().center();
        alreadyDone = 0;

        DublinCore newDc = dc == null ? CreateStructurePresenter.this.bundle.getDc() : dc;
        ModsCollectionClient newMods;
        if (mods != null) {
            newMods = new ModsCollectionClient();
            newMods.setMods(Arrays.asList(mods));
        } else {
            newMods = CreateStructurePresenter.this.bundle.getMods();
        }
        NewDigitalObject object = null;
        try {
            object =
                    ClientUtils.createTheStructure(new MetadataBundle(newDc,
                                                                      newMods,
                                                                      CreateStructurePresenter.this.bundle
                                                                              .getMarc()),
                                                   leftPresenter.getView().getSubelementsGrid().getTree());
        } catch (CreateObjectException e) {
            SC.warn(e.getMessage());
            e.printStackTrace();
        }
        if (object != null) {
            object.setSysno(sysno);
            System.out.println(ClientUtils.toStringTree(object));
            dispatcher.execute(new InsertNewDigitalObjectAction(object),
                               new DispatchCallback<InsertNewDigitalObjectResult>() {

                                   @Override
                                   public void callback(final InsertNewDigitalObjectResult result) {
                                       if (result.isIngestSuccess()) {
                                           if (!result.isReindexSuccess()) {
                                               // TODO" i18n
                                               SC.warn("Reindexace se nepodařila.", new BooleanCallback() {

                                                   @Override
                                                   public void execute(Boolean value) {
                                                       openCreated(result.getNewPid());
                                                   }
                                               });
                                           } else {
                                               openCreated(result.getNewPid());
                                           }
                                       } else {
                                           SC.warn("Vložení se nepodařilo.");
                                       }
                                       getView().getPopupPanel().setVisible(false);
                                       getView().getPopupPanel().hide();
                                   }

                                   @Override
                                   public void callbackError(Throwable t) {
                                       System.out.println(t);
                                       SC.warn("Vložení se nepodařilo. Chyba: " + t.getMessage());
                                       getView().getPopupPanel().setVisible(false);
                                       getView().getPopupPanel().hide();
                                   }
                               });
        }
    }

    private void openCreated(final String pid) {
        SC.ask("Vložení proběhlo v pořádku, přejete si otevřít nový objekt?", new BooleanCallback() {

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
        return bundle.getMods();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DublinCore getDc() {
        return bundle == null || bundle.getDc() == null ? new DublinCore() : bundle.getDc();
    }
}