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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.SectionStack;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.client.config.EditorClientConfiguration;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.uihandlers.DigitalObjectMenuUiHandlers;
import cz.mzk.editor.client.util.ClientUtils;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.NAME_OF_TREE;
import cz.mzk.editor.client.util.Constants.SERVER_ACTION_RESULT;
import cz.mzk.editor.client.view.other.InputQueueTree;
import cz.mzk.editor.client.view.other.RecentlyModifiedRecord;
import cz.mzk.editor.client.view.window.ModalWindow;
import cz.mzk.editor.shared.event.ChangeFocusedTabSetEvent;
import cz.mzk.editor.shared.event.ChangeFocusedTabSetEvent.ChangeFocusedTabSetHandler;
import cz.mzk.editor.shared.event.ChangeMenuWidthEvent;
import cz.mzk.editor.shared.event.ConfigReceivedEvent;
import cz.mzk.editor.shared.event.ConfigReceivedEvent.ConfigReceivedHandler;
import cz.mzk.editor.shared.event.DigitalObjectClosedEvent;
import cz.mzk.editor.shared.event.DigitalObjectClosedEvent.DigitalObjectClosedHandler;
import cz.mzk.editor.shared.event.DigitalObjectOpenedEvent;
import cz.mzk.editor.shared.event.DigitalObjectOpenedEvent.DigitalObjectOpenedHandler;
import cz.mzk.editor.shared.event.KeyPressedEvent;
import cz.mzk.editor.shared.event.RefreshTreeEvent;
import cz.mzk.editor.shared.event.RefreshTreeEvent.RefreshTreeHandler;
import cz.mzk.editor.shared.rpc.RecentlyModifiedItem;
import cz.mzk.editor.shared.rpc.ServerActionResult;
import cz.mzk.editor.shared.rpc.action.ScanInputQueueAction;
import cz.mzk.editor.shared.rpc.action.ScanInputQueueResult;

// TODO: Auto-generated Javadoc
/**
 * The Class DigitalObjectMenuPresenter.
 */
public class DigitalObjectMenuPresenter
        extends Presenter<DigitalObjectMenuPresenter.MyView, DigitalObjectMenuPresenter.MyProxy>
        implements DigitalObjectMenuUiHandlers {

    /**
     * The Interface MyView.
     */
    public interface MyView
            extends View, HasUiHandlers<DigitalObjectMenuUiHandlers> {

        /**
         * Gets the refresh widget.
         * 
         * @return the refresh widget
         */
        HasClickHandlers getRefreshWidget();

        /**
         * Show input queue.
         * 
         * @param dispatcher
         *        the dispatcher
         */
        void showInputQueue(InputQueueTree tree, DispatchAsync dispatcher, PlaceManager placeManager);

        /**
         * Gets the input tree.
         * 
         * @return the input tree
         */
        InputQueueTree getInputTree();

        // TODO: ListGrid -> na nejake rozhrani
        /**
         * Gets the recently modified tree.
         * 
         * @return the recently modified tree
         */
        ListGrid getRecentlyModifiedGrid();

        /**
         * Gets the related grid.
         * 
         * @return the related grid
         */
        ListGrid getRelatedGrid();

        /**
         * Sets the dS.
         * 
         * @param dispatcher
         *        the new dS
         */
        void setDS(DispatchAsync dispatcher, EventBus bus);

        /**
         * Sets the related documents.
         * 
         * @param data
         *        the new related documents
         */
        void setRelatedDocuments(List<? extends List<String>> data);

        /**
         * Gets the section stack
         * 
         * @return the section stack
         */
        SectionStack getSectionStack();

        SelectItem getSelectItem();

    }

    /**
     * The Interface MyProxy.
     */
    @ProxyStandard
    public interface MyProxy
            extends Proxy<DigitalObjectMenuPresenter> {

    }

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /** The input queue shown. */
    private boolean inputQueueCanShow = false;

    /** The place manager. */
    private final PlaceManager placeManager;

    /** The config. */
    private final EditorClientConfiguration config;

    private boolean isRefByFocused = false;

    private final LangConstants lang;

    private final Map<String, List<? extends List<String>>> openedObjectsUuidAndRelated =
            new HashMap<String, List<? extends List<String>>>();

    private static final Object LOCK = Constants.class;

    private static volatile boolean ready = true;

    /**
     * Instantiates a new digital object menu presenter.
     * 
     * @param view
     *        the view
     * @param eventBus
     *        the event bus
     * @param proxy
     *        the proxy
     * @param dispatcher
     *        the dispatcher
     * @param config
     *        the config
     * @param placeManager
     *        the place manager
     * @param lang
     *        the lang constants
     */
    @Inject
    public DigitalObjectMenuPresenter(final MyView view,
                                      final EventBus eventBus,
                                      final MyProxy proxy,
                                      final DispatchAsync dispatcher,
                                      final EditorClientConfiguration config,
                                      PlaceManager placeManager,
                                      LangConstants lang) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        this.config = config;
        this.placeManager = placeManager;
        this.lang = lang;
        bind();
        getView().setUiHandlers(this);
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
     */
    @Override
    protected void onBind() {
        super.onBind();
        getView().setDS(dispatcher, getEventBus());
        getView().getRecentlyModifiedGrid().setHoverCustomizer(new HoverCustomizer() {

            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                return record.getAttribute(Constants.ATTR_DESC);
            }
        });
        getView().getRecentlyModifiedGrid().addCellClickHandler(new CellClickHandler() {

            @Override
            public void onCellClick(CellClickEvent event) {
                revealItem(event.getRecord().getAttribute(Constants.ATTR_UUID));
            }
        });
        getView().getRelatedGrid().addCellClickHandler(new CellClickHandler() {

            @Override
            public void onCellClick(CellClickEvent event) {
                revealItem(event.getRecord().getAttribute(Constants.ATTR_UUID));
            }
        });

        addRegisteredHandler(ConfigReceivedEvent.getType(), new ConfigReceivedHandler() {

            @Override
            public void onConfigReceived(ConfigReceivedEvent event) {
                if (event.isStatusOK()) {
                    setInputQueueCanShow(config.getShowInputQueue());
                } else {
                    setInputQueueCanShow(EditorClientConfiguration.Constants.GUI_SHOW_INPUT_QUEUE_DEFAULT);
                }
                if (getInputQueueCanShow()) {
                    onShowInputQueue(null);
                }
            }
        });

        addRegisteredHandler(DigitalObjectOpenedEvent.getType(), new DigitalObjectOpenedHandler() {

            @Override
            public void onDigitalObjectOpened(DigitalObjectOpenedEvent event) {
                if (event.isStatusOK()) {
                    onAddDigitalObject(event.getItem(), event.getRelated());
                }
            }
        });

        addRegisteredHandler(ChangeFocusedTabSetEvent.getType(), new ChangeFocusedTabSetHandler() {

            @Override
            public void onChangeFocusedTabSet(ChangeFocusedTabSetEvent event) {
                getView().setRelatedDocuments(openedObjectsUuidAndRelated.get(event.getFocusedUuid()));
            }
        });

        addRegisteredHandler(DigitalObjectClosedEvent.getType(), new DigitalObjectClosedHandler() {

            @Override
            public void onDigitalObjectClosed(DigitalObjectClosedEvent event) {
                openedObjectsUuidAndRelated.remove(event.getUuid());
            }
        });

        addRegisteredHandler(KeyPressedEvent.getType(), new KeyPressedEvent.KeyPressedHandler() {

            @Override
            public void onKeyPressed(KeyPressedEvent event) {
                shortcutPressed(event.getCode());
            }
        });

        addRegisteredHandler(RefreshTreeEvent.getType(), new RefreshTreeHandler() {

            @Override
            public void onRefreshTree(RefreshTreeEvent event) {
                if (event.getTree() == NAME_OF_TREE.RECENTLY_MODIFIED) refreshRecentlyModified();
            }
        });
    }

    @Override
    public void refreshRecentlyModified() {

        Criteria criteria = new Criteria();
        boolean all = lang.all().equals(getView().getSelectItem().getValue());
        criteria.addCriteria(Constants.ATTR_ALL, all);
        getView().getRecentlyModifiedGrid().getDataSource().fetchData(criteria, new DSCallback() {

            @Override
            public void execute(DSResponse response, Object rawData, DSRequest request) {
                getView().getRecentlyModifiedGrid().setData(response.getData());
                getView().getRecentlyModifiedGrid().sort(Constants.ATTR_MODIFIED, SortDirection.ASCENDING);
                getView().getRecentlyModifiedGrid().selectRecord(0);
                getView().getRecentlyModifiedGrid().scrollToRow(0);
            }
        });
    }

    @Override
    protected void onReset() {
        ChangeMenuWidthEvent.fire(getEventBus(), "275");
    };

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onUnbind()
     */
    @Override
    protected void onUnbind() {
        super.onUnbind();
        getView().getRecentlyModifiedGrid().setHoverCustomizer(null);
    }

    /**
     * Checks if is input queue shown.
     * 
     * @return true, if is input queue shown
     */
    public boolean getInputQueueCanShow() {
        return inputQueueCanShow;
    }

    /**
     * Sets the input queue shown.
     * 
     * @param inputQueueShown
     *        the new input queue shown
     */
    public void setInputQueueCanShow(boolean inputQueueCanShow) {
        this.inputQueueCanShow = inputQueueCanShow;
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.view.DigitalObjectMenuView.MyUiHandlers
     * #onRefresh()
     */
    @Override
    public void onRefresh() {
        if (ready) {
            synchronized (LOCK) {
                if (ready) { // double-lock idiom
                    ready = false;
                    final ModalWindow mw = new ModalWindow(getView().getInputTree());
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
                                                   getView().getInputTree().refreshTree();
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

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
     */
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, Constants.TYPE_LEFT_CONTENT, this);
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.view.DigitalObjectMenuView.MyUiHandlers
     * #onShowInputQueue()
     */
    @Override
    public void onShowInputQueue(InputQueueTree tree) {
        getView().showInputQueue(tree, dispatcher, placeManager);
        registerHandler(getView().getRefreshWidget().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                onRefresh();
            }
        }));
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.view.DigitalObjectMenuView.MyUiHandlers
     * #onAddDigitalObject (cz.mzk.editor.shared.rpc.RecentlyModifiedItem)
     */
    @Override
    public void onAddDigitalObject(final RecentlyModifiedItem item, final List<? extends List<String>> related) {
        openedObjectsUuidAndRelated.put(item.getUuid(), related);
        getView().setRelatedDocuments(related);
        RecentlyModifiedRecord record = ClientUtils.toRecord(item);
        if (getView().getRecentlyModifiedGrid().getDataAsRecordList().contains(record)) {
            getView().getRecentlyModifiedGrid().updateData(record);
        } else {
            getView().getRecentlyModifiedGrid().addData(record);
        }
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.view.DigitalObjectMenuView.MyUiHandlers
     * #revealModifiedItem(java.lang.String)
     */
    @Override
    public void revealItem(String uuid) {
        placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY).with(Constants.URL_PARAM_UUID,
                                                                                  uuid));
    }

    private void shortcutPressed(final int code) {
        if (isVisible()) {
            if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_M.getCode()) {
                Canvas[] items2 = getView().getSectionStack().getSection(2).getItems();
                if (items2.length > 0) {
                    items2[0].focus();
                    isRefByFocused = false;
                }
            } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_H.getCode()) {
                Canvas[] items1 = getView().getSectionStack().getSection(1).getItems();
                if (items1.length > 0) {
                    items1[0].focus();
                    isRefByFocused = true;
                }
            } else if (code == Constants.CODE_KEY_ENTER) {
                if (getView().getRecentlyModifiedGrid().getSelectedRecords().length > 0 && !isRefByFocused) {
                    ListGridRecord[] listGridRecords =
                            getView().getRecentlyModifiedGrid().getSelectedRecords();
                    revealItem(listGridRecords[0].getAttribute(Constants.ATTR_UUID));
                } else if (getView().getRelatedGrid().getSelectedRecords().length > 0 && isRefByFocused) {
                    ListGridRecord[] listGridRecords = getView().getRelatedGrid().getSelectedRecords();
                    revealItem(listGridRecords[0].getAttribute(Constants.ATTR_UUID));
                }
            }
        }
    }

}