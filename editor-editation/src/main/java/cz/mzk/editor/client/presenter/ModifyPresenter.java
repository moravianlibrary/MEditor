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

import java.util.List;

import javax.inject.Inject;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tile.TileGrid;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.client.config.EditorClientConfiguration;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.dispatcher.TryAgainCallbackError;
import cz.mzk.editor.client.uihandlers.ModifyUiHandlers;
import cz.mzk.editor.client.util.ClientUtils;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.NAME_OF_TREE;
import cz.mzk.editor.client.view.ModifyView;
import cz.mzk.editor.client.view.other.ContainerRecord;
import cz.mzk.editor.client.view.other.EditorTabSet;
import cz.mzk.editor.client.view.window.AddOcrWindow;
import cz.mzk.editor.client.view.window.ChangeRightsWindow;
import cz.mzk.editor.client.view.window.DownloadFoxmlWindow;
import cz.mzk.editor.client.view.window.EditorSC;
import cz.mzk.editor.client.view.window.LockDigitalObjectWindow;
import cz.mzk.editor.client.view.window.ModalWindow;
import cz.mzk.editor.client.view.window.RemoveDigitalObjectWindow;
import cz.mzk.editor.client.view.window.SortingWindow;
import cz.mzk.editor.client.view.window.StoreWorkingCopyWindow;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.event.ChangeFocusedTabSetEvent;
import cz.mzk.editor.shared.event.DigitalObjectClosedEvent;
import cz.mzk.editor.shared.event.DigitalObjectClosedEvent.DigitalObjectClosedHandler;
import cz.mzk.editor.shared.event.DigitalObjectOpenedEvent;
import cz.mzk.editor.shared.event.EscKeyPressedEvent;
import cz.mzk.editor.shared.event.KeyPressedEvent;
import cz.mzk.editor.shared.event.OpenDigitalObjectEvent;
import cz.mzk.editor.shared.event.RefreshTreeEvent;
import cz.mzk.editor.shared.rpc.DigitalObjectDetail;
import cz.mzk.editor.shared.rpc.DigitalObjectRelationships;
import cz.mzk.editor.shared.rpc.DublinCore;
import cz.mzk.editor.shared.rpc.LockInfo;
import cz.mzk.editor.shared.rpc.RecentlyModifiedItem;
import cz.mzk.editor.shared.rpc.StoredItem;
import cz.mzk.editor.shared.rpc.action.DownloadDigitalObjectDetailAction;
import cz.mzk.editor.shared.rpc.action.DownloadDigitalObjectDetailResult;
import cz.mzk.editor.shared.rpc.action.GetDescriptionAction;
import cz.mzk.editor.shared.rpc.action.GetDescriptionResult;
import cz.mzk.editor.shared.rpc.action.GetDigitalObjectDetailAction;
import cz.mzk.editor.shared.rpc.action.GetDigitalObjectDetailResult;
import cz.mzk.editor.shared.rpc.action.GetLockInformationAction;
import cz.mzk.editor.shared.rpc.action.GetLockInformationResult;
import cz.mzk.editor.shared.rpc.action.GetObjectsToSortAction;
import cz.mzk.editor.shared.rpc.action.GetObjectsToSortResult;
import cz.mzk.editor.shared.rpc.action.GetRelationshipsAction;
import cz.mzk.editor.shared.rpc.action.GetRelationshipsResult;
import cz.mzk.editor.shared.rpc.action.PutDescriptionAction;
import cz.mzk.editor.shared.rpc.action.PutDescriptionResult;
import cz.mzk.editor.shared.rpc.action.PutDigitalObjectDetailAction;
import cz.mzk.editor.shared.rpc.action.PutDigitalObjectDetailResult;
import cz.mzk.editor.shared.rpc.action.UnlockDigitalObjectAction;
import cz.mzk.editor.shared.rpc.action.UnlockDigitalObjectResult;

public class ModifyPresenter
        extends Presenter<ModifyPresenter.MyView, ModifyPresenter.MyProxy>
        implements ModifyUiHandlers {

    private LangConstants lang;

    @Inject
    public void setLang(LangConstants lang) {
        this.lang = lang;
    }

    @Inject
    private final EditorClientConfiguration config;

    /**
     * The Interface MyView.
     */
    public interface MyView
            extends View, HasUiHandlers<ModifyUiHandlers> {

        void shortcutPressed(int code);

        public Record[] fromClipboard();

        public void toClipboard(final Record[] data);

        public PopupPanel getPopupPanel();

        public Canvas getEditor(String text, String uuid, boolean common);

        void addDigitalObject(String uuid,
                              DigitalObjectDetail detail,
                              boolean refresh,
                              boolean storedDigitalObject);

        void addStream(Record[] items, String uuid, DigitalObjectModel model);

        DownloadFoxmlWindow getDownloadingWindow();

        void setConfiguration(EditorClientConfiguration config);

        void updateLockInformation(EditorTabSet ts);

        void publish(EditorTabSet ts);

        void storeWork(EditorTabSet ts);
    }

    /**
     * The Interface MyProxy.
     */
    @ProxyCodeSplit
    @NameToken(NameTokens.MODIFY)
    public interface MyProxy
            extends ProxyPlace<ModifyPresenter> {

    }

    /** The done. */
    private int done = 0;

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /** The left presenter. */
    private final DigitalObjectMenuPresenter leftPresenter;

    /** The uuid. */
    private String uuid;

    /** The previous uuid. */
    private String previousUuid1;

    /** The previous uuid2. */
    private String previousUuid2;

    /** The forced refresh. */
    private boolean forcedRefresh;

    /** The place manager. */
    private final PlaceManager placeManager;

    /** The saved edited object. */
    private StoredItem savedEditedObject;

    /** The model of the stored digital object which is going to be opened. */
    private String modelToOpen;

    /**
     * Instantiates a new modify presenter.
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
    public ModifyPresenter(final EventBus eventBus,
                           final MyView view,
                           final MyProxy proxy,
                           final DigitalObjectMenuPresenter leftPresenter,
                           final DispatchAsync dispatcher,
                           final PlaceManager placeManager,
                           final EditorClientConfiguration config) {
        super(eventBus, view, proxy);
        this.leftPresenter = leftPresenter;
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
        this.config = config;
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
     */
    @Override
    protected void onBind() {
        super.onBind();

        getView().setConfiguration(config);

        addRegisteredHandler(DigitalObjectClosedEvent.getType(), new DigitalObjectClosedHandler() {

            @Override
            public void onDigitalObjectClosed(DigitalObjectClosedEvent event) {
                String uuid = event.getUuid();
                if (uuid != null) {
                    if (uuid.equals(previousUuid2)) {
                        previousUuid2 = null;
                    } else if (uuid.equals(previousUuid1)) {
                        if (previousUuid2 == null) {
                            previousUuid1 = null;
                        } else { // move
                            previousUuid1 = previousUuid2;
                            previousUuid2 = null;
                        }
                    }
                }
            }
        });

        addRegisteredHandler(KeyPressedEvent.getType(), new KeyPressedEvent.KeyPressedHandler() {

            @Override
            public void onKeyPressed(KeyPressedEvent event) {
                getView().shortcutPressed(event.getCode());
            }

        });
        addRegisteredHandler(OpenDigitalObjectEvent.getType(),
                             new OpenDigitalObjectEvent.OpenDigitalObjectHandler() {

                                 @Override
                                 public void onOpenDigitalObject(OpenDigitalObjectEvent event) {
                                     openAnotherObject(event.getUuid(), event.getStoredItem());
                                     if (RemoveDigitalObjectWindow.isInstanceVisible())
                                         RemoveDigitalObjectWindow.closeInstantiatedWindow();
                                 }
                             });
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.mvp.client.Presenter#prepareFromRequest(com.gwtplatform
     * .mvp.client.proxy.PlaceRequest)
     */
    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        uuid = request.getParameter(Constants.URL_PARAM_UUID, null);
        String id = request.getParameter(Constants.ATTR_ID, null);
        savedEditedObject =
                new StoredItem(id != null ? Long.parseLong(id) : null,
                               request.getParameter(Constants.ATTR_FILE_NAME, null));
        modelToOpen = request.getParameter(Constants.ATTR_MODEL, null);
        forcedRefresh = ClientUtils.toBoolean(request.getParameter(Constants.URL_PARAM_REFRESH, "no"));
        leftPresenter.getView().showInputQueue(dispatcher, placeManager, true);
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
        if (uuid != null && (forcedRefresh || (!uuid.equals(previousUuid1) && !uuid.equals(previousUuid2)))) {
            Image loader = new Image("images/loadingAnimation3.gif");
            getView().getPopupPanel().setWidget(loader);
            getView().getPopupPanel().setVisible(true);
            getView().getPopupPanel().center();
            getObject(forcedRefresh);
            forcedRefresh = false;
            if (!uuid.equals(previousUuid1) && !uuid.equals(previousUuid2)) {
                previousUuid2 = previousUuid1;
                previousUuid1 = uuid;
            }
        }
        RevealContentEvent.fire(this, Constants.TYPE_MEDIT_LEFT_CONTENT, leftPresenter);

    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.view.ModifyView.MyUiHandlers#
     * onAddDigitalObject(com.smartgwt.client.widgets.tile.TileGrid,
     * com.smartgwt.client.widgets.menu.Menu)
     */
    @Override
    public void onAddDigitalObject(final TileGrid tileGrid, final Menu menu) {
        MenuItem[] items = menu.getItems();
        if (!ModifyView.ID_EDIT.equals(items[0].getAttributeAsObject(ModifyView.ID_NAME))) {
            throw new IllegalStateException("Inconsistent gui.");
        }
        items[0].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                String uuidToEdit = tileGrid.getSelection()[0].getAttribute(Constants.ATTR_UUID);
                placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY)
                        .with(Constants.URL_PARAM_UUID, uuidToEdit));
            }
        });
        if (!ModifyView.ID_SEL_ALL.equals(items[2].getAttributeAsObject(ModifyView.ID_NAME))) {
            throw new IllegalStateException("Inconsistent gui.");
        }
        items[2].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                tileGrid.selectAllRecords();
            }
        });
        if (!ModifyView.ID_SEL_NONE.equals(items[3].getAttributeAsObject(ModifyView.ID_NAME))) {
            throw new IllegalStateException("Inconsistent gui.");
        }
        items[3].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                tileGrid.deselectAllRecords();
            }
        });
        if (!ModifyView.ID_SEL_INV.equals(items[4].getAttributeAsObject(ModifyView.ID_NAME))) {
            throw new IllegalStateException("Inconsistent gui.");
        }
        items[4].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                Record[] selected = tileGrid.getSelection();
                tileGrid.selectAllRecords();
                tileGrid.deselectRecords(selected);
            }
        });
        if (!ModifyView.ID_COPY.equals(items[6].getAttributeAsObject(ModifyView.ID_NAME))) {
            throw new IllegalStateException("Inconsistent gui.");
        }
        items[6].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                getView().toClipboard(tileGrid.getSelection());
            }
        });
        if (!ModifyView.ID_PASTE.equals(items[7].getAttributeAsObject(ModifyView.ID_NAME))) {
            throw new IllegalStateException("Inconsistent gui.");
        }
        items[7].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

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
                    done = 0;
                    Timer timer = new Timer() {

                        @Override
                        public void run() {
                            hBar1.setPercentDone(((100 * (done + 1)) / data.length));
                            tileGrid.addData(((ContainerRecord) data[done]).deepCopy());
                            if (++done != data.length) {
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
                        tileGrid.addData(((ContainerRecord) data[i]).deepCopy());
                    }
                }
            }
        });
        if (!ModifyView.ID_DELETE.equals(items[8].getAttributeAsObject(ModifyView.ID_NAME))) {
            throw new IllegalStateException("Inconsistent gui.");
        }

        items[8].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                tileGrid.removeSelectedData();
            }
        });
        items[9].addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                if (tileGrid.getSelection().length > 1) {
                    SC.warn(lang.deleteOne());
                } else {
                    removeDigitalObject(tileGrid.getSelection()[0].getAttribute(Constants.ATTR_UUID));
                }
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
     */
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, Constants.TYPE_MEDIT_MAIN_CONTENT, this);
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.view.ModifyView.MyUiHandlers#
     * onAddDigitalObject(java.lang.String,
     * com.smartgwt.client.widgets.ImgButton,
     * com.smartgwt.client.widgets.menu.Menu)
     */
    @Override
    public void onAddDigitalObject(final String uuid, final ImgButton closeButton) {
        closeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                close(uuid);
            }
        });
    }

    @Override
    public void close(String uuid) {
        DigitalObjectClosedEvent.fire(ModifyPresenter.this, uuid);
    }

    @Override
    public void onChangeFocusedTabSet(String focusedUuid) {
        ChangeFocusedTabSetEvent.fire(ModifyPresenter.this, focusedUuid, false);
    }

    /**
     * Gets the object.
     *
     * @param refresh
     *        the refresh
     * @return the object
     */
    private void getObject(final boolean refresh) {
        DigitalObjectModel model = (modelToOpen != null ? DigitalObjectModel.parseString(modelToOpen) : null);
        final GetDigitalObjectDetailAction action =
                new GetDigitalObjectDetailAction(uuid, model, savedEditedObject);
        final DispatchCallback<GetDigitalObjectDetailResult> callback =
                new DispatchCallback<GetDigitalObjectDetailResult>() {

                    @Override
                    public void callback(GetDigitalObjectDetailResult result) {
                        DigitalObjectDetail detail = result.getDetail();
                        if (detail != null) {
                            EscKeyPressedEvent.fire(ModifyPresenter.this);
                            if (null != detail.getLockInfo().getLockOwner()) {
                                EditorSC.objectIsLock(lang, detail.getLockInfo());
                            }

                            getView().addDigitalObject(uuid,
                                                       detail,
                                                       refresh,
                                                       savedEditedObject != null
                                                               && savedEditedObject.getId() != null);
                            String title =
                                    (detail.getDc().getTitle() == null || detail.getDc().getTitle().size() == 0) ? "no title"
                                            : detail.getDc().getTitle().get(0);
                            DigitalObjectOpenedEvent.fire(ModifyPresenter.this,
                                                          true,
                                                          new RecentlyModifiedItem(uuid,
                                                                                   title,
                                                                                   result.getDescription(),
                                                                                   detail.getModel(),
                                                                                   result.getModified()),
                                                          result.getDetail().getRelated());
                        } else if (savedEditedObject != null) {
                            SC.ask(lang.operationFailed(), lang.storedNotFound(), new BooleanCallback() {

                                @Override
                                public void execute(Boolean value) {
                                    if (value != null && value) {
                                        StoreWorkingCopyWindow.deleteItem(savedEditedObject.getId(),
                                                                          savedEditedObject.getFileName(),
                                                                          dispatcher);
                                    }
                                }
                            });
                        }
                        getView().getPopupPanel().setVisible(false);
                        getView().getPopupPanel().hide();
                    }

                    @Override
                    public void callbackError(final Throwable t) {
                        super.callbackError(t, new TryAgainCallbackError() {

                            @Override
                            public void theMethodForCalling() {
                                getObject(forcedRefresh);
                            }
                        });
                        getView().getPopupPanel().setVisible(false);
                        getView().getPopupPanel().hide();
                    }
                };

        dispatcher.execute(action, callback);
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.view.ModifyView.MyUiHandlers#getDescription
     * (java.lang.String, com.smartgwt.client.widgets.tab.TabSet,
     * java.lang.String)
     */
    @Override
    public void getDescription(final String uuid, final TabSet tabSet, final String tabId) {
        dispatcher.execute(new GetDescriptionAction(uuid), new DispatchCallback<GetDescriptionResult>() {

            @Override
            public void callback(GetDescriptionResult result) {
                final TabSet descriptionTabSet = new TabSet();
                descriptionTabSet.setTabBarPosition(Side.RIGHT);
                descriptionTabSet.setWidth100();
                descriptionTabSet.setHeight100();
                Tab commonDesc = new Tab("", "other/more_people.png");
                commonDesc.setPane(getView().getEditor(result.getDescription(), uuid, true));
                Tab userDesc = new Tab("", "other/loner.png");
                userDesc.setPane(getView().getEditor(result.getUserDescription(), uuid, false));
                descriptionTabSet.setTabs(commonDesc, userDesc);
                tabSet.setTabPane(tabId, descriptionTabSet);
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.view.ModifyView.MyUiHandlers#putDescription
     * (java.lang.String, java.lang.String, boolean)
     */
    @Override
    public void putDescription(String uuid, String description, final boolean common) {
        dispatcher.execute(new PutDescriptionAction(uuid, description, common),
                           new DispatchCallback<PutDescriptionResult>() {

                               @Override
                               public void callback(PutDescriptionResult result) {
                                   if (!common) {
                                       RefreshTreeEvent.fire(ModifyPresenter.this,
                                                             NAME_OF_TREE.RECENTLY_MODIFIED);
                                   }
                               }
                           });
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.view.ModifyView.MyUiHandlers#
     * onSaveDigitalObject
     * (cz.mzk.editor.shared.valueobj.AbstractDigitalObjectDetail)
     */
    @Override
    public void onSaveDigitalObject(final DigitalObjectDetail digitalObject, boolean versionable, boolean reindex) {
        dispatcher.execute(new PutDigitalObjectDetailAction(digitalObject, versionable, true),
                           new DispatchCallback<PutDigitalObjectDetailResult>() {

                               @Override
                               public void callback(PutDigitalObjectDetailResult result) {
                                   if (!result.isSaved()) {
                                       SC.say(lang.mesCanNotPublish());
                                   } else {
                                       askToRefresh(digitalObject.getUuid());
                                   }
                               }

                               @Override
                               public void callbackError(Throwable t) {
                                   super.callbackError(t);
                               }
                           });
    }

    private void askToRefresh(final String uuid) {
        SC.ask(lang.operationSuccessful() + "<br>" + lang.refreshQuestion(), new BooleanCallback() {

            @Override
            public void execute(Boolean value) {
                if (value) {
                    onRefresh(uuid);
                }
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.view.ModifyView.MyUiHandlers#
     * onDownloadDigitalObject
     * (cz.mzk.editor.shared.valueobj.AbstractDigitalObjectDetail)
     */
    @Override
    public void onHandleWorkingCopyDigObj(final DigitalObjectDetail digitalObject) {
        dispatcher.execute(new DownloadDigitalObjectDetailAction(digitalObject),
                           new DispatchCallback<DownloadDigitalObjectDetailResult>() {

                               @Override
                               public void callback(DownloadDigitalObjectDetailResult result) {
                                   int length = result.getStringsWithXml().length;
                                   String[] stringsWithXml = new String[length];
                                   for (int i = 0; i < length; i++) {
                                       stringsWithXml[i] = result.getStringsWithXml()[i];
                                   }
                                   getView().getDownloadingWindow().setStringsWithXml(stringsWithXml);
                               }

                               @Override
                               public void callbackError(Throwable t) {
                                   super.callbackError(t);
                               }
                           });
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.view.ModifyView.MyUiHandlers#onRefresh
     * (java.lang.String)
     */
    @Override
    public void onRefresh(String uuid) {
        placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY).with(Constants.URL_PARAM_UUID,
                                                                                  uuid)
                .with(Constants.URL_PARAM_REFRESH, "yes"));
    }

    @Override
    public void getStream(final String uuid, final DigitalObjectModel model, final TabSet ts) {

        final ModalWindow mw = new ModalWindow(ts);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        final GetDigitalObjectDetailAction action = new GetDigitalObjectDetailAction(uuid, model, null);
        final DispatchCallback<GetDigitalObjectDetailResult> callback =
                new DispatchCallback<GetDigitalObjectDetailResult>() {

                    @Override
                    public void callback(GetDigitalObjectDetailResult result) {
                        mw.show(lang.rendering(), true);
                        DigitalObjectDetail detail = result.getDetail();
                        List<DigitalObjectDetail> itemList = detail.getItems();
                        Record[] items = null;
                        if (itemList.size() > 0) {
                            items = new Record[itemList.size()];
                            for (int i = 0, total = itemList.size(); i < total; i++) {
                                DublinCore dc = itemList.get(i).getDc();
                                if (dc != null) {
                                    String title =
                                            dc.getTitle() == null ? lang.noTitle() : dc.getTitle().get(0);
                                    String id =
                                            dc.getIdentifier() == null ? lang.noTitle() : dc.getIdentifier()
                                                    .get(0);
                                    items[i] =
                                            new ContainerRecord(title,
                                                                id,
                                                                DigitalObjectModel.PAGE.equals(model) ? id
                                                                        : model.getIcon());
                                } else {
                                    items[i] =
                                            new ContainerRecord(lang.objectNotFound(),
                                                                lang.objectNotFound(),
                                                                DigitalObjectModel.PAGE.equals(model) ? Constants.MISSING
                                                                        : "icons/128/folder_not_found.png");
                                }
                            }
                        }
                        getView().addStream(items, uuid, model);
                        mw.hide();
                    }

                    @Override
                    public void callbackError(final Throwable t) {
                        super.callbackError(t, new TryAgainCallbackError() {

                            @Override
                            public void theMethodForCalling() {
                                getStream(uuid, model, ts);
                            }
                        });
                        mw.hide();
                    }
                };

        dispatcher.execute(action, callback);
    }

    /**
     * {@inheritDoc}
     *
     * @param storedItem
     */

    @Override
    public void openAnotherObject(String uuid, StoredItem storedItem) {
        if (storedItem != null) {
            placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY)
                    .with(Constants.URL_PARAM_UUID, uuid)
                    .with(Constants.ATTR_FILE_NAME, storedItem.getFileName())
                    .with(Constants.ATTR_MODEL, storedItem.getModel().getValue())
                    .with(Constants.ATTR_ID, String.valueOf(storedItem.getId())));
        } else {
            placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY)
                    .with(Constants.URL_PARAM_UUID, uuid));
        }
    }

    @Override
    public void getLockDigitalObjectInformation(final EditorTabSet ts, final boolean calledDuringPublishing) {
        final ModalWindow mw = new ModalWindow(ts);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        GetLockInformationAction lockInfoAction = new GetLockInformationAction(ts.getUuid());
        DispatchCallback<GetLockInformationResult> lockInfoCallback =
                new DispatchCallback<GetLockInformationResult>() {

                    @Override
                    public void callback(GetLockInformationResult result) {

                        LockInfo lockInfo = result.getLockInfo();
                        if (null != lockInfo.getLockOwner()) {
                            if (calledDuringPublishing) {
                                if ("".equals(lockInfo.getLockOwner())) {
                                    getView().publish(ts);
                                } else {
                                    String message =
                                            EditorSC.getObjectIsLockMessage(lang,
                                                                            lockInfo,
                                                                            lang.storeQuestion());
                                    SC.ask(message, new BooleanCallback() {

                                        @Override
                                        public void execute(Boolean value) {
                                            if (value) {
                                                getView().storeWork(ts);
                                            }
                                        }
                                    });
                                }
                            } else {
                                EditorSC.objectIsLock(lang, lockInfo);
                            }
                        } else {
                            if (calledDuringPublishing) {
                                getView().publish(ts);
                            } else {
                                SC.say(lang.noLocked());
                            }
                        }
                        ts.setLockInfo(lockInfo);
                        getView().updateLockInformation(ts);
                        mw.hide();
                    }

                    @Override
                    public void callbackError(final Throwable t) {
                        super.callbackError(t, new TryAgainCallbackError() {

                            @Override
                            public void theMethodForCalling() {
                                getLockDigitalObjectInformation(ts, calledDuringPublishing);
                            }
                        });
                        mw.hide();
                    }
                };
        dispatcher.execute(lockInfoAction, lockInfoCallback);

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void lockDigitalObject(final EditorTabSet ts) {

        LockDigitalObjectWindow.setInstanceOf(lang, ts, dispatcher, getEventBus());

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void unlockDigitalObject(final EditorTabSet ts) {
        final ModalWindow mw = new ModalWindow(ts);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        final UnlockDigitalObjectAction unlockAction = new UnlockDigitalObjectAction(ts.getUuid());
        final DispatchCallback<UnlockDigitalObjectResult> unlockCallback =
                new DispatchCallback<UnlockDigitalObjectResult>() {

                    @Override
                    public void callback(UnlockDigitalObjectResult result) {
                        if (result.isSuccessful()) {
                            SC.say(lang.objectUnlocked(), lang.objectUnlocked());
                            ts.setLockInfo(new LockInfo());
                        } else {
                            EditorSC.operationFailed(lang, null);
                        }
                        mw.hide();
                    }

                    @Override
                    public void callbackError(final Throwable t) {
                        super.callbackError(t, new TryAgainCallbackError() {

                            @Override
                            public void theMethodForCalling() {
                                unlockDigitalObject(ts);
                            }
                        });
                        mw.hide();
                    }
                };
        dispatcher.execute(unlockAction, unlockCallback);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void storeFoxmlFile(DigitalObjectDetail detail, EditorTabSet ts) {
        StoreWorkingCopyWindow.setLabelsFieldsButtons(detail, ts);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void removeDigitalObject(String uuid) {
        RemoveDigitalObjectWindow.setInstanceOf(uuid, lang, dispatcher, getEventBus());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void changeRights(String uuid, String oldRight) {
        new ChangeRightsWindow(uuid, getEventBus(), oldRight, lang, dispatcher);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sortWithChildren(final String uuid) {
        final SortingWindow sortingWindow = new SortingWindow(lang, dispatcher, getEventBus()) {

            @Override
            protected void afterPublishAction() {
                askToRefresh(uuid);
            }
        };

        GetObjectsToSortAction toSortAction = new GetObjectsToSortAction(uuid);
        dispatcher.execute(toSortAction, new DispatchCallback<GetObjectsToSortResult>() {

            @Override
            public void callback(GetObjectsToSortResult result) {
                DigitalObjectRelationships digObjRel = result.getDigObjRel();
                sortingWindow.setObjects(digObjRel);
            }
        });

    }

    @Override
    public void addOcr(final String uuid) {
        final AddOcrWindow addOcrWindow = new AddOcrWindow(lang, dispatcher, getEventBus(), uuid);

        GetRelationshipsAction.Builder builder = new GetRelationshipsAction.Builder(uuid);
        builder.withTitle(true);
        dispatcher.execute(builder.build(), new DispatchCallback<GetRelationshipsResult>() {
            @Override
            public void callback(GetRelationshipsResult result) {
                DigitalObjectRelationships digObjRel = result.getDigObjRel();
                addOcrWindow.setObjects(digObjRel);
            }
        });

        }
    }